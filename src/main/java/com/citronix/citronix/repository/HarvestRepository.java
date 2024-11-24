package com.citronix.citronix.repository;

import com.citronix.citronix.domain.Enum.Saison;
import com.citronix.citronix.domain.Harvest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface HarvestRepository extends JpaRepository<Harvest, UUID> {
    List<Harvest> findAllBySaison(Saison saison);
}
