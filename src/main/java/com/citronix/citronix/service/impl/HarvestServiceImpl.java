package com.citronix.citronix.service.impl;

import com.citronix.citronix.Util.SeasonUtils;
import com.citronix.citronix.domain.Enum.Saison;
import com.citronix.citronix.domain.Field;
import com.citronix.citronix.domain.Harvest;
import com.citronix.citronix.domain.HarvestDetails;
import com.citronix.citronix.domain.Tree;
import com.citronix.citronix.exception.Field.FieldNotFoundException;
import com.citronix.citronix.exception.Harvest.HarvestInvalidException;
import com.citronix.citronix.exception.Harvest.HarvestNotFoundException;
import com.citronix.citronix.exception.SaisonInvalidException;
import com.citronix.citronix.exception.Tree.TreeNotFoundException;
import com.citronix.citronix.repository.HarvestDetailsRepository;
import com.citronix.citronix.repository.HarvestRepository;
import com.citronix.citronix.service.HarvestService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HarvestServiceImpl implements HarvestService {
    private final HarvestRepository harvestRepository;
    private final HarvestDetailsRepository harvestDetailRepository;
    private final FieldServiceImpl fieldServiceImpl;

    public HarvestServiceImpl(HarvestRepository harvestRepository, HarvestDetailsRepository harvestDetailRepository, FieldServiceImpl fieldServiceImpl) {
        this.harvestRepository = harvestRepository;
        this.harvestDetailRepository = harvestDetailRepository;
        this.fieldServiceImpl = fieldServiceImpl;
    }

    @Override
    public Harvest save(Harvest harvest, UUID fieldUuid) {
        validateFieldAndSeason(harvest.getDate(), fieldUuid);

        List<Tree> availableTrees = getAvailableTreesForHarvest(fieldUuid, harvest.getDate());
        if (availableTrees.isEmpty()) {
            throw new TreeNotFoundException("All trees have been harvested this season.");
        }

        double totalQuantity = calculateTotalQuantity(availableTrees);

        Harvest harvestToSave = createHarvest(harvest, totalQuantity);
        Harvest savedHarvest = harvestRepository.save(harvestToSave);

        saveHarvestDetails(savedHarvest, availableTrees);

        return savedHarvest;

    }

    private void validateFieldAndSeason(LocalDateTime harvestDate, UUID fieldUuid) {
        if (fieldServiceImpl.findById(fieldUuid) == null) {
            throw new FieldNotFoundException("Field not found.");
        }
        if (SeasonUtils.seasonFromDate(harvestDate) == null) {
            throw new SaisonInvalidException("Invalid season derived from the harvest date.");
        }
    }

    private List<Tree> getAvailableTreesForHarvest(UUID fieldUuid, LocalDateTime harvestDate) {
        Field fieldToHarvest = fieldServiceImpl.findById(fieldUuid);
        Saison harvestSeason = SeasonUtils.seasonFromDate(harvestDate);

        return fieldToHarvest.getTreeList().stream()
                .filter(tree -> !harvestDetailRepository.existsByTreeAndAndHarvest_Saison(tree, harvestSeason))
                .collect(Collectors.toList());
    }
    private double calculateTotalQuantity(List<Tree> trees) {
        return trees.stream()
                .mapToDouble(Tree::getProductivity)
                .sum();
    }

    private Harvest createHarvest(Harvest harvest, double totalQuantity) {
        Harvest harvest1=new Harvest();
        harvest1.setDate(harvest.getDate());
        harvest1.setSaison(SeasonUtils.seasonFromDate(harvest.getDate()));
        harvest1.setTotal_quantity(totalQuantity);
        return harvest1;
    }

    private void saveHarvestDetails(Harvest savedHarvest, List<Tree> trees) {
        List<HarvestDetails> harvestDetails = trees.stream()
                .map(tree -> HarvestDetails.builder()
                        .harvest(savedHarvest)
                        .tree(tree)
                        .quantity(tree.getProductivity())
                        .build())
                .collect(Collectors.toList());

        harvestDetailRepository.saveAll(harvestDetails);
    }

    @Override
    public Harvest findById(UUID uuid) {
        if (uuid == null){
            throw new HarvestInvalidException("harvest ID is Required");
        }
        return harvestRepository.findById(uuid)
                .orElseThrow(()-> new HarvestNotFoundException("harvest Not Found"));
    }

    @Override
    public void delete(UUID uuid) {
        Harvest harvestToDelete = findById(uuid);
        harvestRepository.delete(harvestToDelete);
    }

    @Override
    public List<Harvest> findHarvestsBySeason(Saison season) {
        return harvestRepository.findAllBySaison(season);
    }
}
