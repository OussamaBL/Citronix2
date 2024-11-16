package com.citronix.citronix.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trees")
public class Tree {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDateTime planting_date;

    private LocalDateTime created_at;

    @ManyToOne
    private Field field;

    public int getAge() {
        if (this.planting_date == null) {
            throw new IllegalStateException("Planting date is not set");
        }
        return (int) ChronoUnit.YEARS.between(planting_date, LocalDateTime.now());
    }
    public double getProductivity(){
        if(this.getAge()<3) return 2.5;
        if(this.getAge()>=3 && this.getAge()<=10) return 12;
        if(this.getAge()>10) return 20;
        return 0;
    }
}
