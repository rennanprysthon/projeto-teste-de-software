package br.edu.ifpe.recife.testes.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private BigDecimal price;
    private int amountOfQuantity;
    private int amountOfExercises;
    @ManyToOne(fetch = FetchType.LAZY)
    private Personal personal;
}
