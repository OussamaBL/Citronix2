package com.citronix.citronix.service;

import com.citronix.citronix.domain.Farm;
import com.citronix.citronix.domain.Field;
import com.citronix.citronix.web.vm.Field.AddFieldVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface FieldService {
    Field addField(AddFieldVM addFieldVM);

    Field saveField(Field field);

    Field updateField(Field field);

    Field findById(UUID fieldUuid);

    Page<Field> findAllByFarm(UUID farmUuid, Pageable pageable);

    void deleteField(UUID id);
}
