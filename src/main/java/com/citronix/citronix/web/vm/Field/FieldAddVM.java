package com.citronix.citronix.web.vm.Field;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FieldAddVM {
    @Positive(message = "Field area must be positive.")
    @Min(0)
    private Double area;
}
