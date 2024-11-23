package com.citronix.citronix.web.vm.Mapper.Harvest;

import com.citronix.citronix.domain.Harvest;
import com.citronix.citronix.web.vm.Harvest.HarvestResponseVM;
import com.citronix.citronix.web.vm.Harvest.HarvestVM;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HarvestMapper{
    Harvest toHarvest(HarvestVM harvestVM);
    HarvestResponseVM toResponseVM(Harvest harvest);
}
