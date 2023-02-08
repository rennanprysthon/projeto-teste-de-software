package br.edu.ifpe.recife.testes.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Treino {
	private Client client;
	private String treinoA;
	private String treinoB;
	private String treinoC;
}
