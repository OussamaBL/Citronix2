package com.citronix.citronix.web.vm.Mapper.Sale;

import com.citronix.citronix.domain.Harvest;
import com.citronix.citronix.domain.Sale;
import com.citronix.citronix.web.vm.Harvest.HarvestResponseVM;
import com.citronix.citronix.web.vm.Harvest.HarvestVM;
import com.citronix.citronix.web.vm.Sale.ResponseSaleVM;
import com.citronix.citronix.web.vm.Sale.SaleVM;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SaleMapper {
    Sale toSale(SaleVM saleVM);
    ResponseSaleVM toResponseVM(Sale sale);
    List<ResponseSaleVM> salesToSaleResponseVMs(List<Sale> sales);
}
