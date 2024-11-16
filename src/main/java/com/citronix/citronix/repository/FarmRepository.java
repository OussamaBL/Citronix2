package com.citronix.citronix.repository;

import com.citronix.citronix.domain.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FarmRepository extends JpaRepository<Farm, UUID> {
}
