package br.edu.ifpe.recife.testes.services;

import br.edu.ifpe.recife.testes.model.Personal;
import br.edu.ifpe.recife.testes.repository.PersonalRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PersonalServiceTest {
    @MockBean
    PersonalRepository repository;

    @InjectMocks
    private PersonalService personalService;

    @DisplayName("Test if register works when passing correct data")
    @Test
    void shouldRegisterPersonWithValidData() {
        Personal personal = Personal.builder()
            .cpf("30048945056")
            .password("lalala123")
            .email("fulano@emaill.com")
            .build();

        personalService.register(personal);

        Mockito.verify(repository, Mockito.times(1)).save(personal);
    }
}
