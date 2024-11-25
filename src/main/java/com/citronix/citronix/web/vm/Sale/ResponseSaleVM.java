package com.citronix.citronix.web.vm.Sale;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ResponseSaleVM {
    private UUID id;
    private LocalDateTime date;
    private double unit_price;
    private double quantity;
    private String client_name;
    //private UUID harvest_id;
}
