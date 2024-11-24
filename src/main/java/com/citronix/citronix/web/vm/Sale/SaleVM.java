package com.citronix.citronix.web.vm.Sale;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaleVM {
    @NotNull(message = "Harvest ID is required")
    private UUID harvestId;

    @NotBlank(message = "Client Name is required")
    private String clientName;

    @Positive(message = "Unit price must be greater than zero")
    private double unitPrice;

    @DecimalMin(value = "0.1", message = "Quantity must be at least 0.1")
    private double quantity;
}
