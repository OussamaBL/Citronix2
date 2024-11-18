package com.citronix.citronix.service.impl;

import com.citronix.citronix.domain.Farm;
import com.citronix.citronix.domain.Field;
import com.citronix.citronix.exception.Farm.FarmNotFoundException;
import com.citronix.citronix.repository.FarmRepository;
import com.citronix.citronix.repository.FieldRepository;
import com.citronix.citronix.service.FieldService;
import com.citronix.citronix.exception.Field.FieldNotFoundException;
import com.citronix.citronix.web.vm.Field.AddFieldVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FieldServiceImpl implements FieldService {

    private final FieldRepository fieldRepository;
    private final FarmRepository farmRepository;

    public FieldServiceImpl(FieldRepository fieldRepository, FarmRepository farmRepository) {
        this.fieldRepository = fieldRepository;
        this.farmRepository = farmRepository;
    }

    @Override
    public Field addField(AddFieldVM addFieldVM) {
        Farm farm = farmRepository.findById(addFieldVM.getFarm_id()).orElseThrow(() ->
                new FarmNotFoundException("Farm not found"));
        Field field=new Field();
        field.setArea(addFieldVM.getArea());
        validateField(farm, field);
        field.setFarm(farm);
        return fieldRepository.save(field);
    }

    @Override
    public Field updateField(Field field) {
        Field existingField = fieldRepository.findById(field.getId()).orElseThrow(() ->
                new FieldNotFoundException("Field not found"));

        Farm farm = existingField.getFarm();

        validateField(farm, field);

        existingField.setArea(field.getArea());
        return fieldRepository.save(existingField);
    }

    public Field findById(UUID fieldUuid) {
        return fieldRepository.findById(fieldUuid).orElseThrow(() ->
                new FieldNotFoundException("Field not found"));
    }


    public Page<Field> findAllByFarm(UUID farmUuid, Pageable pageable) {
        return fieldRepository.findAllByFarmId(farmUuid, pageable);
    }

    @Override
    public void deleteField(UUID fieldUuid) {
        Field field = fieldRepository.findById(fieldUuid).orElseThrow(() ->
                new FieldNotFoundException("Field not found"));
        fieldRepository.delete(field);
    }

    private void validateField(Farm farm, Field newField) {
        double totalFarmArea = farm.getFieldList().stream()
                .mapToDouble(Field::getArea)
                .sum();
        if (newField.getArea() < 0.1) {
            throw new RuntimeException("Field area must be at least 0.1 hectares (1,000 m²)");
        }

       /* if (newField.getArea() > (totalFarmArea * 0.5)) {
            throw new RuntimeException("Field area cannot exceed 50% of the farm's total area");
        }*/

        if (totalFarmArea+newField.getArea() >= farm.getArea()) {
            throw new RuntimeException("Total field area must be less than the farm's total area");
        }

        if (farm.getFieldList().size() >= 10) {
            throw new RuntimeException("A farm cannot have more than 10 fields");
        }
    }
}
