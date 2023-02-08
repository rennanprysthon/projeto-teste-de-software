package br.edu.ifpe.recife.testes.repository;

import br.edu.ifpe.recife.testes.model.Contract;
import br.edu.ifpe.recife.testes.model.Treino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContractRepository extends JpaRepository<Contract, UUID> {
    List<Contract> findAllByPersonalEmail(String email);
}
