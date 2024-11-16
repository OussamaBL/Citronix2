package com.citronix.citronix.repository;

import com.citronix.citronix.domain.Tree;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TreeRepository extends JpaRepository<Tree, UUID> {
}
