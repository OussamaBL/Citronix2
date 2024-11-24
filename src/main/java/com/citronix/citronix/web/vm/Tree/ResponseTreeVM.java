package com.citronix.citronix.web.vm.Tree;

import jakarta.validation.constraints.NotNull;
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
public class ResponseTreeVM {
    private UUID id;
    private LocalDateTime planting_date;

    private LocalDateTime created_at=LocalDateTime.now();
}
