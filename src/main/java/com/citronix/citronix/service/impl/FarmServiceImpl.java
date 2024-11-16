package com.citronix.citronix.service.impl;

import com.citronix.citronix.domain.Farm;
import com.citronix.citronix.exception.Farm.FarmAlreadyExistException;
import com.citronix.citronix.exception.Farm.FarmInvalidException;
import com.citronix.citronix.repository.FarmRepository;
import com.citronix.citronix.service.FarmService;
import org.springframework.stereotype.Service;

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
        return null;
    }

    @Override
    public void deleteFarm(UUID id) {

    }
}
