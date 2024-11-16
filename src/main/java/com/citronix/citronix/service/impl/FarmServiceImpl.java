package com.citronix.citronix.service.impl;

import com.citronix.citronix.domain.Farm;
import com.citronix.citronix.exception.Farm.FarmAlreadyExistException;
import com.citronix.citronix.exception.Farm.FarmInvalidException;
import com.citronix.citronix.exception.Farm.FarmNotFoundException;
import com.citronix.citronix.repository.FarmRepository;
import com.citronix.citronix.service.FarmService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FarmServiceImpl implements FarmService {
    private final FarmRepository farmRepository;

    public FarmServiceImpl(FarmRepository farmRepository){
        this.farmRepository=farmRepository;
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
        farmRepository.delete(farm.get());
    }

    public List<Farm> getFarms(){
        return farmRepository.findAll();
    }
}
