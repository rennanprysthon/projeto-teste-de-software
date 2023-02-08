package br.edu.ifpe.recife.testes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpe.recife.testes.model.Treino;

import java.util.UUID;

@Repository
public interface TreinoRepository extends JpaRepository<Treino, UUID> {}
