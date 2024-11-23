package com.citronix.citronix.web.rest.v1.Sale;

import com.citronix.citronix.service.impl.SaleServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/sales")
public class SaleController {
    private final SaleServiceImpl saleServiceImpl;

    public SaleController(SaleServiceImpl saleServiceImpl) {
        this.saleServiceImpl = saleServiceImpl;
    }

    @PostMapping("/save")
    public ResponseEntity<SaleDTO> createSale(@RequestBody SaleVM sale) {
        SaleDTO createdSale = saleService.save(sale);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSale);
    }
}
