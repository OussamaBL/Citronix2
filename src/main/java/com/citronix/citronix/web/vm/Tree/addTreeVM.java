package com.citronix.citronix.web.vm.Tree;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class addTreeVM {
    @NotNull(message = "planting_date is required")
    private LocalDateTime planting_date;

    private LocalDateTime created_at=LocalDateTime.now();
}
