package com.citronix.citronix.web.vm.Harvest;

import com.citronix.citronix.domain.Enum.Saison;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HarvestResponseVM {
    private UUID id;
    private LocalDateTime date;
    private Saison saison;
    private double total_quantity;
}
