package com.citronix.citronix.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "harvestsDetails")
public class HarvestDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private double quantity;

    @ManyToOne
    private Harvest harvest;

    @ManyToOne(fetch = FetchType.LAZY)
    private Tree tree;
}
