package com.citronix.citronix.web.rest.v1.Sale;

import com.citronix.citronix.domain.Sale;
import com.citronix.citronix.service.SaleService;
import com.citronix.citronix.web.vm.Mapper.Sale.SaleMapper;
import com.citronix.citronix.web.vm.Sale.ResponseSaleVM;
import com.citronix.citronix.web.vm.Sale.SaleVM;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/sales")
public class SaleController {
    private final SaleService saleService;
    private final SaleMapper saleMapper;

    public SaleController(SaleService saleService, SaleMapper saleMapper) {
        this.saleService = saleService;
        this.saleMapper = saleMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseSaleVM> createSale(@RequestBody @Valid SaleVM saleVM) {
        Sale sale = saleService.createSale(saleVM.getHarvestId(), saleVM.getClientName(), saleVM.getUnitPrice(), saleVM.getQuantity());
        ResponseSaleVM response = saleMapper.toResponseVM(sale);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ResponseSaleVM>> getAllSales() {
        List<Sale> sales = saleService.getAllSales();
        List<ResponseSaleVM> responses = saleMapper.salesToSaleResponseVMs(sales);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/find/{saleId}")
    public ResponseEntity<ResponseSaleVM> getSaleById(@PathVariable UUID saleId) {
        Sale sale = saleService.getSaleById(saleId);
        ResponseSaleVM response = saleMapper.toResponseVM(sale);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("/update/{saleId}")
    public ResponseEntity<ResponseSaleVM> updateSale(@PathVariable UUID saleId, @RequestBody @Valid SaleVM saleVM) {
        Sale updatedSale = saleService.updateSale(saleId, saleVM.getUnitPrice(), saleVM.getQuantity());
        ResponseSaleVM response = saleMapper.toResponseVM(updatedSale);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}