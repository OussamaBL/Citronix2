package com.citronix.citronix.web.vm.Farm;

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
public class ResponseFarmVM {
    private UUID id;
    private String name;
    private String location;
    private double area;
    private LocalDateTime created_at;
}
