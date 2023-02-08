package br.edu.ifpe.recife.testes.services;

import br.edu.ifpe.recife.testes.model.Client;
import br.edu.ifpe.recife.testes.repository.ClientRepository;
import br.edu.ifpe.recife.testes.services.exception.InvalidClientDataException;
import br.edu.ifpe.recife.testes.services.exception.ClientFieldError;

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

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
	@MockBean
    ClientRepository repository;

    @InjectMocks
    private ClientService clientService;
    
    @DisplayName("Test if register works when passing correct data")
    @Test
    void shouldRegisterClientWithValidData() {
    	Client client = Client.builder()
    		.cpf("30048945056")
            .password("lalala123")
            .email("fulano@emaill.com")
            .nome("Nome")
            .build();
    	clientService.register(client);
    	
    	Mockito.verify(repository, Mockito.times(1)).save(client);
    }
    
    @DisplayName("Test that check if register method returns a list of errors with cpf information")
    @Test
    void shouldThrowExceptionWithCPFInformation() {
    	Client client = Client.builder()
        		.cpf("")
                .password("lalala123")
                .email("fulano@emaill.com")
                .nome("Nome")
                .build();
    	InvalidClientDataException exception = Assertions.assertThrows(
    		InvalidClientDataException.class,
    		() -> clientService.register(client)
    	);
    	
    	List<ClientFieldError> errors = exception.getErrors();
    	Assertions.assertEquals(1, errors.size());
        Assertions.assertTrue(errors.stream().anyMatch(error -> error.getField().equalsIgnoreCase("cpf")));	
    }
    
    @DisplayName("Test that check if register method returns a list of errors with email information")
    @Test
    void shouldThrowExceptionWithEmailInformation() {
    	Client client = Client.builder()
        		.cpf("30048945056")
                .password("lalala123")
                .email("invalidemail")
                .nome("Nome")
                .build();
    	
    	InvalidClientDataException exception = Assertions.assertThrows(
        		InvalidClientDataException.class,
        		() -> clientService.register(client)
        	);
    	
    	List<ClientFieldError> errors = exception.getErrors();
    	Assertions.assertEquals(1, errors.size());
    	Assertions.assertTrue(errors.stream().anyMatch(error -> error.getField().equalsIgnoreCase("email")));
    }
    
    @DisplayName("Test that check if register method returns a list of errors when passing blank values")
    @Test
    void shouldThrowExceptionIfProvidedBlankValues() {
    	Client client = Client.builder()
        		.cpf("")
                .password("")
                .email("")
                .nome("")
                .build();
    	
    	InvalidClientDataException exception = Assertions.assertThrows(
        		InvalidClientDataException.class,
        		() -> clientService.register(client)
        	);
    	
    	List<ClientFieldError> errors = exception.getErrors();
    	Assertions.assertEquals(4, errors.size());
    	Assertions.assertTrue(errors.stream().allMatch(error -> error.getMessage().contains("blank")));
    }

    @DisplayName("Test if Update works when passing correct data")
    @Test
    void shouldUpdateClientWithValidData() {
    	Client client = Client.builder()
    		.cpf("30048945056")
            .password("lalala123")
            .email("fulano@emaill.com")
            .nome("Nome")
            .build();
    	clientService.update(client);
    	
    	Mockito.verify(repository, Mockito.times(1)).save(client);
    }
    
    @DisplayName("Test that check if Update method returns a list of errors with cpf information")
    @Test
    void shouldThrowExceptionWithCPFInformationforUpdate() {
    	Client client = Client.builder()
        		.cpf("")
                .password("lalala123")
                .email("fulano@emaill.com")
                .nome("Nome")
                .build();
    	InvalidClientDataException exception = Assertions.assertThrows(
    		InvalidClientDataException.class,
    		() -> clientService.update(client)
    	);
    	
    	List<ClientFieldError> errors = exception.getErrors();
    	Assertions.assertEquals(1, errors.size());
        Assertions.assertTrue(errors.stream().anyMatch(error -> error.getField().equalsIgnoreCase("cpf")));	
    }
    
    @DisplayName("Test that check if Update method returns a list of errors with email information")
    @Test
    void shouldThrowExceptionWithEmailInformationforUpdate() {
    	Client client = Client.builder()
        		.cpf("30048945056")
                .password("lalala123")
                .email("invalidemail")
                .nome("Nome")
                .build();
    	
    	InvalidClientDataException exception = Assertions.assertThrows(
        		InvalidClientDataException.class,
        		() -> clientService.update(client)
        	);
    	
    	List<ClientFieldError> errors = exception.getErrors();
    	Assertions.assertEquals(1, errors.size());
    	Assertions.assertTrue(errors.stream().anyMatch(error -> error.getField().equalsIgnoreCase("email")));
    }
    
    @DisplayName("Test that check if Update method returns a list of errors when passing blank values")
    @Test
    void shouldThrowExceptionIfProvidedBlankValuesforUpdate() {
    	Client client = Client.builder()
        		.cpf("")
                .password("")
                .email("")
                .nome("")
                .build();
    	
    	InvalidClientDataException exception = Assertions.assertThrows(
        		InvalidClientDataException.class,
        		() -> clientService.update(client)
        	);
    	
    	List<ClientFieldError> errors = exception.getErrors();
    	Assertions.assertEquals(4, errors.size());
    	Assertions.assertTrue(errors.stream().allMatch(error -> error.getMessage().contains("blank")));
    }
}
