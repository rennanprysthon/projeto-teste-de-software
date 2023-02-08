package br.edu.ifpe.recife.testes.repository;

import br.edu.ifpe.recife.testes.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> { 
	Client findByEmail(String email);
}
