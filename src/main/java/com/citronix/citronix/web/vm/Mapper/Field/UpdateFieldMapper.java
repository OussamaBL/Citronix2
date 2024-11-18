package com.citronix.citronix.web.vm.Mapper.Field;

import com.citronix.citronix.domain.Field;
import com.citronix.citronix.web.vm.Field.AddFieldVM;
import com.citronix.citronix.web.vm.Field.FieldResponseVM;
import com.citronix.citronix.web.vm.Field.UpdateFieldVM;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateFieldMapper {
    FieldResponseVM toResponseFieldVM(Field field);
    Field toField(UpdateFieldVM updateFieldVM);
}
