package br.edu.ifpe.recife.testes.services;

import br.edu.ifpe.recife.testes.model.Client;
import br.edu.ifpe.recife.testes.repository.ClientRepository;

import br.edu.ifpe.recife.testes.services.exception.InvalidPasswordRetriveException;
import br.edu.ifpe.recife.testes.services.exception.FieldError;

import java.util.List;

import org.junit.jupiter.api.Assertions;
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
public class PasswordRetriveServiceTest {
	@MockBean
    ClientRepository repository;
	
	@InjectMocks
	private PasswordRetrieveService passwordRetrieveService;
	
	@DisplayName("Test if PasswordRetrive works when passing correct data")
    @Test
    void shouldRetrieveClientWithValidData() {
		Client client = Client.builder()
				.cpf("30048945056")
	            .password("lalala123")
	            .email("fulano@emaill.com")
	            .nome("Nome")
	            .build();
		
		passwordRetrieveService.passwordRetrive(client);
		
		Mockito.verify(repository, Mockito.times(1)).findByEmail(client.getEmail());
	}
	
	@DisplayName("Test that check if PasswordRetrive method returns a list of errors with email information")
    @Test
    void shouldThrowExceptionWithEmailInformation() {
		Client client = Client.builder()
        		.cpf("30048945056")
                .password("lalala123")
                .email("invalidemail")
                .nome("Nome")
                .build();
		
		InvalidPasswordRetriveException exception = Assertions.assertThrows(
				InvalidPasswordRetriveException.class,
        		() -> passwordRetrieveService.passwordRetrive(client)
        	);
    	
    	List<FieldError> errors = exception.getErrors();
    	Assertions.assertEquals(1, errors.size());
    	Assertions.assertTrue(errors.stream().anyMatch(error -> error.getField().equalsIgnoreCase("email")));
	}
	
	@DisplayName("Test that check if PasswordRetrive method returns a list of errors when passing blank values")
    @Test
    void shouldThrowExceptionIfProvidedBlankValues() {
		Client client = Client.builder()
        		.cpf("")
                .password("")
                .email("")
                .nome("")
                .build();
		
		InvalidPasswordRetriveException exception = Assertions.assertThrows(
				InvalidPasswordRetriveException.class,
        		() -> passwordRetrieveService.passwordRetrive(client)
        	);
		
		List<FieldError> errors = exception.getErrors();
    	Assertions.assertEquals(1, errors.size());
    	Assertions.assertTrue(errors.stream().allMatch(error -> error.getMessage().contains("blank")));
	}
	
}
