package com.citronix.citronix.web.vm.Field;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFieldVM {
    @NotNull(message = "area is required")
    @Positive(message = "Field area must be positive.")
    @Min(0)
    private Double area;

}
