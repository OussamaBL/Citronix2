package com.citronix.citronix.repository;

import com.citronix.citronix.domain.Harvest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface HarvestDetailsRepository extends JpaRepository<Harvest, UUID> {
}
