package com.citronix.citronix.web.vm.Mapper.Farm;

import com.citronix.citronix.domain.Farm;
import com.citronix.citronix.web.vm.Farm.ResponseFarmVM;
import com.citronix.citronix.web.vm.Farm.addFarmVM;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FarmMapper {
    ResponseFarmVM toResponseFarmVM(Farm farm);
    Farm toFarm(addFarmVM addfarmVM);
}
