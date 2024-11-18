package com.citronix.citronix.web.vm.Farm;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
public class addFarmVM {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "location is required")
    private String location;

    @NotNull
    @Min(value = 1, message = "area must be 0 or greater.")
    private Double area;

    private LocalDateTime created_at=LocalDateTime.now();
}