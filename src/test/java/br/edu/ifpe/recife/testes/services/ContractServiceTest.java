package br.edu.ifpe.recife.testes.services;

import br.edu.ifpe.recife.testes.model.Contract;
import br.edu.ifpe.recife.testes.model.Personal;
import br.edu.ifpe.recife.testes.repository.ContractRepository;
import br.edu.ifpe.recife.testes.repository.PersonalRepository;
import br.edu.ifpe.recife.testes.services.exception.ContractInvalidException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ContractServiceTest {
    @MockBean
    PersonalRepository personalRepository;

    @MockBean
    ContractRepository contractRepository;

    @InjectMocks
    private ContractService contractService;

    @DisplayName("Test if register works when passing correct data")
    @Test
    void shouldRegisterPersonWithValidData() {
        Personal personal = Personal.builder().id(UUID.randomUUID()).cpf("99912313132").email("personal1@gmail.com").build();
        Contract contract = Contract.builder()
            .amountOfQuantity(2)
            .amountOfExercises(10)
            .personal(personal)
            .price(new BigDecimal("20.00")).build();

        contractService.register(contract);

        Mockito.verify(contractRepository, Mockito.times(1)).save(contract);
    }

    @DisplayName("Test if throw error when passing null values to contract")
    @Test
    void sholdThrowAnError() {
        Contract contract = Contract.builder().build();

        ContractInvalidException contractInvalidException = Assertions.assertThrows(
            ContractInvalidException.class,
            () -> contractService.register(contract)
        );

        Assertions.assertEquals("Could not register a contract without data", contractInvalidException.getMessage());
    }

    @DisplayName("It should throw exception when personal already have a contract")
    @Test
    void shouldThrowErrorWhenPersonalAlreadyHaveContract() {
        Personal personal = Personal.builder().id(UUID.randomUUID()).cpf("99912313132").email("personal1@gmail.com").build();
        Contract contract = Contract.builder()
            .amountOfQuantity(2)
            .amountOfExercises(10)
            .personal(personal)
            .price(new BigDecimal("20.00")).build();

        Mockito.when(contractRepository.findAllByPersonalEmail("personal1@gmail.com")).thenReturn(
           List.of( Contract.builder()
               .amountOfQuantity(3)
               .amountOfExercises(1)
               .personal(personal)
               .price(new BigDecimal("20.00")).build()
            )
        );

        ContractInvalidException contractInvalidException = Assertions.assertThrows(
            ContractInvalidException.class,
            () -> contractService.register(contract)
        );

        Assertions.assertEquals("Personal already have a contract", contractInvalidException.getMessage());
    }
}
