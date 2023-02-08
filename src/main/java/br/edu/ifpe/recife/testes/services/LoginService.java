package br.edu.ifpe.recife.testes.services;

import br.edu.ifpe.recife.testes.model.Client;
import br.edu.ifpe.recife.testes.repository.ClientRepository;
import br.edu.ifpe.recife.testes.services.exception.LoginFieldError;
import br.edu.ifpe.recife.testes.services.exception.InvalidLoginDataException;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private static final String INVALID_CREDENTIALS_ERROR_MESSAGE = "Invalid email or password";
    private static final String INVALID_ERROR_MESSAGE = "%s is invalid";
    private static final String BLANK_ERROR_MESSAGE = "%s is blank or null";

    @Autowired
    private ClientRepository clientRepository;
    
    private LoginFieldError validateEmail(Client client) {
    	String email = client.getEmail();
    	
    	if(email == null || email.isBlank()) {
    		return new LoginFieldError("email", String.format(BLANK_ERROR_MESSAGE, "EMAIL"));
    	}
    	
    	if (!Pattern.compile("^(.+)@(\\S+)$").matcher(email).matches()) {
            return new LoginFieldError("email", String.format(INVALID_ERROR_MESSAGE, "EMAIL"));
        }
    	
    	return null;
    };
    
    private LoginFieldError validatePassword(Client client) {
   	 String password = client.getPassword();

        if (password == null || password.isBlank()) {
            return new LoginFieldError("password", String.format(BLANK_ERROR_MESSAGE, "PASSWORD"));
        }

        return null;
   };
   
   private void validateUser(Client client) {
       List<LoginFieldError> errors = Stream.of(
           validateEmail(client),
           validatePassword(client)
       )
       .filter(Objects::nonNull)
       .collect(Collectors.toList());

       if (!errors.isEmpty()) {
           throw new InvalidLoginDataException(errors);
       }
   }
   
   public Client login(Client client) {
       validateUser(client);
       return this.clientRepository.findByEmail(client.getEmail());
   }
}
