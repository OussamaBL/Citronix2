package com.citronix.citronix.repository;

import com.citronix.citronix.domain.Enum.Saison;
import com.citronix.citronix.domain.Harvest;
import com.citronix.citronix.domain.HarvestDetails;
import com.citronix.citronix.domain.Tree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface HarvestDetailsRepository extends JpaRepository<HarvestDetails, UUID> {
    Boolean existsByTreeAndAndHarvest_Saison(Tree tree, Saison saison);
}
