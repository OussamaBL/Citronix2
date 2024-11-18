package com.citronix.citronix.web.vm.Mapper.Field;

import com.citronix.citronix.domain.Farm;
import com.citronix.citronix.domain.Field;
import com.citronix.citronix.web.vm.Farm.ResponseFarmVM;
import com.citronix.citronix.web.vm.Farm.addFarmVM;
import com.citronix.citronix.web.vm.Field.AddFieldVM;
import com.citronix.citronix.web.vm.Field.FieldResponseVM;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FieldMapper {
    FieldResponseVM toResponseFieldVM(Field field);
    Field toField(AddFieldVM addFieldVM);
}
