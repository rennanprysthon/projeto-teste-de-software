package br.edu.ifpe.recife.testes.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Client {
	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
	private String nome;
    private String cpf;
    private String email;
    private String password;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Treino> treino = new ArrayList<>();
}
