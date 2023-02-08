package br.edu.ifpe.recife.testes.services;

import br.edu.ifpe.recife.testes.model.Personal;
import br.edu.ifpe.recife.testes.repository.PersonalRepository;
import br.edu.ifpe.recife.testes.services.exception.InvalidPersonalDataException;
import br.edu.ifpe.recife.testes.services.exception.PersonalFieldError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.LongStream;


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

    @DisplayName("Test that check if register method returns a list of errors with cpf information")
    @Test
    void shouldThrowExceptionWithCPFInformation() {
        Personal personal = Personal.builder()
            .cpf("")
            .password("lalala123")
            .email("fulano@email.com")
            .build();

        InvalidPersonalDataException exception = Assertions.assertThrows(
            InvalidPersonalDataException.class,
            () -> personalService.register(personal)
        );

        List<PersonalFieldError> errors = exception.getErrors();
        Assertions.assertEquals(1, errors.size());
        Assertions.assertTrue(errors.stream().anyMatch(error -> error.getField().equalsIgnoreCase("cpf")));
    }

    @DisplayName("Test that check if register method returns a list of errors with email information")
    @Test
    void shouldThrowExceptionWithEmailInformation() {
        Personal personal = Personal.builder()
                .cpf("30048945056")
                .password("lalala123")
                .email("invalidemail.com")
                .build();

        InvalidPersonalDataException exception = Assertions.assertThrows(
                InvalidPersonalDataException.class,
                () -> personalService.register(personal)
        );

        List<PersonalFieldError> errors = exception.getErrors();
        Assertions.assertEquals(1, errors.size());
        Assertions.assertTrue(errors.stream().anyMatch(error -> error.getField().equalsIgnoreCase("email")));
    }

    @DisplayName("Test that check if register method returns a list of errors when passing blank values")
    @Test
    void shouldThrowExceptionIfProvidedBlankValues() {
        Personal personal = Personal.builder()
            .cpf("")
            .password("")
            .email("")
            .build();

        InvalidPersonalDataException exception = Assertions.assertThrows(
            InvalidPersonalDataException.class,
            () -> personalService.register(personal)
        );

        List<PersonalFieldError> errors = exception.getErrors();
        Assertions.assertEquals(3, errors.size());
        Assertions.assertTrue(errors.stream().allMatch(error -> error.getMessage().contains("blank")));
    }

    @DisplayName("Should return all personal registered when not passing geolocalization")
    @Test
    void shouldReturlAllPersonalRegistered() {
        List<Personal> personals = LongStream.range(1, 5)
                .mapToObj(i -> Personal.builder().cpf("123123123123").email(String.format("personal%d@gmail.com", i)).id(UUID.randomUUID()).build()).collect(Collectors.toList());

        Mockito.when(repository.findAll()).thenReturn(personals);

        List<Personal> fetched = personalService.findAll();

        Assertions.assertEquals(4, fetched.size());
    }
}
