package com.citronix.citronix.service;

import com.citronix.citronix.domain.Farm;
import com.citronix.citronix.domain.Field;
import com.citronix.citronix.web.vm.Field.AddFieldVM;

import java.util.UUID;

public interface FieldService {
    Field addField(AddFieldVM addFieldVM);
    Field updateField(Field field);
    void deleteField(UUID id);
}
