package com.citronix.citronix.service.impl;

import com.citronix.citronix.domain.Harvest;
import com.citronix.citronix.domain.Sale;
import com.citronix.citronix.exception.Harvest.HarvestNotFoundException;
import com.citronix.citronix.exception.Sale.SaleInvalidException;
import com.citronix.citronix.exception.Sale.SaleNotFoundException;
import com.citronix.citronix.repository.HarvestRepository;
import com.citronix.citronix.repository.SaleRepository;
import com.citronix.citronix.service.SaleService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final HarvestRepository harvestRepository;

    public SaleServiceImpl(SaleRepository saleRepository, HarvestRepository harvestRepository) {
        this.saleRepository = saleRepository;
        this.harvestRepository = harvestRepository;
    }

    @Override
    public Sale createSale(UUID harvestId, String clientName, double unitPrice, double quantity) {
        Harvest harvest = harvestRepository.findById(harvestId)
                .orElseThrow(() -> new HarvestNotFoundException("Harvest not found"));

        double sumSaled = saleRepository.findTotalQuantitySoldByHarvestId(harvestId).orElse(0.0);

        double quantityLeft = harvest.getTotal_quantity() - sumSaled;
        if (quantity > quantityLeft) {
            throw new SaleInvalidException("Harvest available : " + quantityLeft + " kg ");
        }

        Sale sale = Sale.builder()
                .harvest(harvest)
                .client_name(clientName)
                .unit_price(unitPrice)
                .quantity(quantity)
                .date(LocalDateTime.now())
                .build();

        return saleRepository.save(sale);
    }

    @Override
    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    @Override
    public Sale getSaleById(UUID saleId) {
        return saleRepository.findById(saleId)
                .orElseThrow(() -> new SaleNotFoundException("Sale not found"));
    }

    @Override
    public Sale updateSale(UUID saleId, double unitPrice, double quantity) {
        Sale existingSale = saleRepository.findById(saleId)
                .orElseThrow(() -> new SaleNotFoundException("Sale not found"));

        Harvest harvest = existingSale.getHarvest();

        double sumSaledExcludingCurrent = saleRepository.findTotalQuantitySoldByHarvestId(harvest.getId()).orElse(0.0) - existingSale.getQuantity();

        double quantityLeft = harvest.getTotal_quantity() - sumSaledExcludingCurrent;
        if (quantity > quantityLeft) {
            throw new SaleInvalidException("Harvest available " + quantityLeft + " kg for sale.");
        }

        existingSale.setUnit_price(unitPrice);
        existingSale.setQuantity(quantity);

        return saleRepository.save(existingSale);
    }
}
