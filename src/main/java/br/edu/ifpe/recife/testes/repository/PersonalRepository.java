package br.edu.ifpe.recife.testes.repository;

import br.edu.ifpe.recife.testes.model.Personal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonalRepository extends JpaRepository<Personal, UUID> {}
