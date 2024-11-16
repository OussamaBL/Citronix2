package com.citronix.citronix.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface Harvest extends JpaRepository<Harvest, UUID> {
}
