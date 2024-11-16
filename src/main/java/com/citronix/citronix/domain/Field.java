package com.citronix.citronix.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "fields")
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private double area;

    @ManyToOne
    private Farm farm;

    @OneToMany(mappedBy = "field")
    private List<Tree> treeList;
}
