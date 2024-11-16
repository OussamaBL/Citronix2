package com.citronix.citronix.service;

import com.citronix.citronix.domain.Farm;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface FarmService {
    Farm addFarm(Farm farm);
    Farm updateFarm(Farm farm);
    void deleteFarm(UUID id);
}
