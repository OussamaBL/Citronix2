package com.citronix.citronix.service.impl;

import com.citronix.citronix.domain.Farm;
import com.citronix.citronix.domain.Field;
import com.citronix.citronix.exception.Farm.FarmAlreadyExistException;
import com.citronix.citronix.exception.Farm.FarmInvalidException;
import com.citronix.citronix.exception.Farm.FarmNotFoundException;
import com.citronix.citronix.repository.FarmRepository;
import com.citronix.citronix.repository.impl.FarmRepositoryImpl;
import com.citronix.citronix.service.FarmService;
import com.citronix.citronix.service.FieldService;
import com.citronix.citronix.web.vm.Field.SearchDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FarmServiceImpl implements FarmService {
    private final FarmRepository farmRepository;
    private final FieldService fieldService;
    private final FarmRepositoryImpl farmRepositoryImpl;

    public FarmServiceImpl(FarmRepository farmRepository,FarmRepositoryImpl farmRepositoryImpl,FieldService fieldService){
        this.farmRepository=farmRepository;
        this.farmRepositoryImpl=farmRepositoryImpl;
        this.fieldService=fieldService;
    }
    @Override
    public Farm addFarm(Farm farm) {
        if(farmRepository.existsByName(farm.getName()))
            throw new FarmAlreadyExistException("Name already exist");
        if(farm.getArea()<=0) throw new FarmInvalidException("Area should be greater than 0");
        return farmRepository.save(farm);
    }

    @Override
    public Farm updateFarm(Farm farm) {
        Optional<Farm> farm1=farmRepository.findById(farm.getId());
        farm1.orElseThrow(()-> new FarmNotFoundException("farm not exist"));
        Farm existingFarm=farm1.get();

        if (farm.getName() != null && !farm.getName().equals(existingFarm.getName())) {
            farmRepository.findByName(farm.getName()).ifPresent(u -> {
                throw new FarmAlreadyExistException("Name already exists");
            });
            existingFarm.setName(farm.getName());
        }

        if (farm.getLocation() != null) existingFarm.setLocation(farm.getLocation());

        if(farm.getArea() != null){
            if(farm.getArea()<=0) throw new FarmInvalidException("Area should be greater than 0");
            existingFarm.setArea(farm.getArea());
        }

        return farmRepository.save(existingFarm);
    }

    @Override
    public void deleteFarm(UUID id) {
        if(id==null) throw new RuntimeException("id is null");
        Optional<Farm> farm=farmRepository.findById(id);
        farm.orElseThrow( ()-> new FarmNotFoundException("found not exists with this id"));
        farm.get().getFieldList().forEach(field -> { fieldService.deleteField(field.getId()); });
        farmRepository.delete(farm.get());
    }
    @Override
    public List<Farm> getFarms(){
        return farmRepository.findAll();
    }

    @Override
    public List<Farm> findByCriteria(SearchDTO searchDTO) {
        return farmRepositoryImpl.findByCriteria(searchDTO);
    }

    @Override
    public Farm saveWithoutList(Farm farm){
        if(farm.getFieldList()!=null) throw new RuntimeException("field list not empty");
        return this.addFarm(farm);
    }
    @Override
    public Farm saveWithList(Farm farm){
        if(farm.getFieldList().isEmpty()) throw new RuntimeException("field list empty");
        farm.getFieldList().forEach(field -> {
            field.setFarm(farm);
        });
        return farmRepository.save(farm);
    }
    @Override
    public List<Farm> getFarmLess4000(){
        List<Farm> farmList=farmRepository.findAll();
        List<Farm> farmList1= new ArrayList<>();
        farmList.forEach(farm -> {
            double sum= farm.getFieldList().stream().mapToDouble(Field::getArea).sum();
            if(sum<4000) farmList1.add(farm);
        });
        return farmList1;
    }
}
