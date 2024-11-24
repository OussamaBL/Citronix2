package com.citronix.citronix.service;

import com.citronix.citronix.domain.Sale;

import java.util.List;
import java.util.UUID;

public interface SaleService {

    Sale createSale(UUID harvestId, String clientName, double unitPrice, double quantity);

    List<Sale> getAllSales();

    Sale getSaleById(UUID saleId);

    Sale updateSale(UUID saleId, double unitPrice, double quantity);
}
