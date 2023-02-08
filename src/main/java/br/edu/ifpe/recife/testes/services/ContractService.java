package br.edu.ifpe.recife.testes.services;

import br.edu.ifpe.recife.testes.model.Contract;
import br.edu.ifpe.recife.testes.model.Personal;
import br.edu.ifpe.recife.testes.repository.ContractRepository;
import br.edu.ifpe.recife.testes.services.exception.ContractInvalidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractService {

    @Autowired
    private ContractRepository contractRepository;

    private void validate(Contract contract) {
        if (
            contract.getAmountOfExercises() == 0 |
            contract.getAmountOfQuantity() == 0 |
            contract.getPersonal() == null |
            contract.getPrice() == null
        ) {
            throw new ContractInvalidException("Could not register a contract without data");
        }
    }

    public void register(Contract contract) {
        validateIfPersonalHasContracts(contract.getPersonal());
        validate(contract);
        contractRepository.save(contract);
    }

    private void validateIfPersonalHasContracts(Personal personal) {
        List<Contract> contracts = contractRepository.findAllByPersonalEmail(personal.getEmail());

        if (!contracts.isEmpty()) {
            throw new ContractInvalidException("Personal already have a contract");
        }
    }
}
