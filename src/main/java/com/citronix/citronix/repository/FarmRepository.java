package com.citronix.citronix.repository;

import com.citronix.citronix.domain.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface FarmRepository extends JpaRepository<Farm, UUID> {
    boolean existsByName(String name);
    Optional<Farm> findById(UUID id);
    Optional<Farm> findByName(String name);


}
