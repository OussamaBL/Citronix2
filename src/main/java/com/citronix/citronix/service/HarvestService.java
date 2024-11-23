package com.citronix.citronix.service;

import com.citronix.citronix.domain.Enum.Saison;
import com.citronix.citronix.domain.Harvest;

import java.util.List;
import java.util.UUID;

public interface HarvestService {


    Harvest save(Harvest harvest, UUID fieldUuid);

    Harvest findById(UUID uuid);

    void delete(UUID uuid);

    List<Harvest> findHarvestsBySeason(Saison season);
}
