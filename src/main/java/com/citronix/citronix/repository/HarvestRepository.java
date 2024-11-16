package com.citronix.citronix.repository;

import com.citronix.citronix.domain.Harvest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HarvestRepository extends JpaRepository<Harvest, UUID> {
}
