package br.edu.ifpe.recife.testes.services;

import br.edu.ifpe.recife.testes.model.Client;
import br.edu.ifpe.recife.testes.repository.ClientRepository;
import br.edu.ifpe.recife.testes.services.exception.InvalidClientDataException;
import br.edu.ifpe.recife.testes.services.exception.ClientFieldError;
import br.edu.ifpe.recife.testes.services.util.CPFValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ClientService {
	private static final String INVALID_ERROR_MESSAGE = "%s is invalid";
    private static final String BLANK_ERROR_MESSAGE = "%s is blank or null";
    
    @Autowired
    private ClientRepository clientRepository;
    
    private ClientFieldError validateEmail(Client client) {
    	String email = client.getEmail();
    	
    	if(email == null || email.isBlank()) {
    		return new ClientFieldError("email", String.format(BLANK_ERROR_MESSAGE, "EMAIL"));
    	}
    	
    	if (!Pattern.compile("^(.+)@(\\S+)$").matcher(email).matches()) {
            return new ClientFieldError("email", String.format(INVALID_ERROR_MESSAGE, "EMAIL"));
        }
    	
    	return null;
    };
    
    private ClientFieldError validateName(Client client) {
    	String nome = client.getNome();
    	
    	if(nome == null || nome.isBlank()) {
    		return new ClientFieldError("nome", String.format(BLANK_ERROR_MESSAGE, "NOME"));
    	}
   
    	return null;
    };
    
    private ClientFieldError validatePassword(Client client) {
    	 String password = client.getPassword();

         if (password == null || password.isBlank()) {
             return new ClientFieldError("password", String.format(BLANK_ERROR_MESSAGE, "PASSWORD"));
         }

         return null;
    };
    
    private ClientFieldError validateCPF(Client client) {
    	String cpf = client.getCpf();

        if (cpf == null || cpf.isBlank()) {
            return new ClientFieldError("cpf", String.format(BLANK_ERROR_MESSAGE, "CPF"));
        }

        if (!CPFValidator.isValidCPF(cpf)) {
            return new ClientFieldError("cpf", String.format(INVALID_ERROR_MESSAGE, "CPF"));
        }

        return null;
   };

   private void validateUser(Client client) {
       List<ClientFieldError> errors = Stream.of(
           validateCPF(client),
           validateEmail(client),
           validateName(client),
           validatePassword(client)
       )
       .filter(Objects::nonNull)
       .collect(Collectors.toList());

       if (!errors.isEmpty()) {
           throw new InvalidClientDataException(errors);
       }
   }

   public Client register(Client client) {
       validateUser(client);
       return this.clientRepository.save(client);
   }
   
   public Client update(Client client) {
	   validateUser(client);
	   return this.clientRepository.save(client);
   }
}
