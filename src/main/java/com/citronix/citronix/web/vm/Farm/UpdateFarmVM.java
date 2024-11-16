package com.citronix.citronix.web.vm.Farm;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFarmVM {
    @Nullable
    private String name;

    @Nullable
    private String location;

    @Nullable
    @Min(value = 1, message = "area must be 0 or greater.")
    private Double area;
}
