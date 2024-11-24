package com.citronix.citronix.domain;

import com.citronix.citronix.domain.Enum.Saison;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "harvests")
public class Harvest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDateTime date;
    private double total_quantity;

    @Enumerated(EnumType.STRING)
    private Saison saison;

    @OneToMany(mappedBy = "harvest",cascade = CascadeType.ALL)
    private List<HarvestDetails> harvestDetailsList;

    @OneToMany(mappedBy = "harvest")
    private List<Sale> saleList;
}
