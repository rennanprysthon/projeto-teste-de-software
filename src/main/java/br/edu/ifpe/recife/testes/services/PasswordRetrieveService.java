package br.edu.ifpe.recife.testes.services;

import br.edu.ifpe.recife.testes.model.Client;
import br.edu.ifpe.recife.testes.repository.ClientRepository;
import br.edu.ifpe.recife.testes.services.exception.FieldError;
import br.edu.ifpe.recife.testes.services.exception.InvalidPasswordRetriveException;
import br.edu.ifpe.recife.testes.services.exception.LoginFieldError;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordRetrieveService {
	 private static final String INVALID_ERROR_MESSAGE = "%s is invalid";
	 private static final String BLANK_ERROR_MESSAGE = "%s is blank or null";
	 
	 @Autowired
	 private ClientRepository clientRepository;
	 
	 private FieldError validateEmail(Client client) {
	    	String email = client.getEmail();
	    	
	    	if(email == null || email.isBlank()) {
	    		return new FieldError("email", String.format(BLANK_ERROR_MESSAGE, "EMAIL"));
	    	}
	    	
	    	if (!Pattern.compile("^(.+)@(\\S+)$").matcher(email).matches()) {
	            return new FieldError("email", String.format(INVALID_ERROR_MESSAGE, "EMAIL"));
	        }
	    	
	    	return null;
	    };
	
	    private void validateUser(Client client) {
	        List<FieldError> errors = Stream.of(
	            validateEmail(client)
	        )
	        .filter(Objects::nonNull)
	        .collect(Collectors.toList());

	        if (!errors.isEmpty()) {
	            throw new InvalidPasswordRetriveException(errors);
	        }
	    }
	    
	    public Client passwordRetrive(Client client) {
	        validateUser(client);
	        return this.clientRepository.findByEmail(client.getEmail());
	    }
}
