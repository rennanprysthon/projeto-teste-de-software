package br.edu.ifpe.recife.testes.services;

import br.edu.ifpe.recife.testes.model.Personal;
import br.edu.ifpe.recife.testes.repository.PersonalRepository;
import br.edu.ifpe.recife.testes.services.exception.InvalidPersonalDataException;
import br.edu.ifpe.recife.testes.services.exception.PersonalFieldError;
import br.edu.ifpe.recife.testes.services.util.CPFValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PersonalService {
    private static final String INVALID_ERROR_MESSAGE = "%s is invalid";
    private static final String BLANK_ERROR_MESSAGE = "%s is blank or null";

    @Autowired
    private  PersonalRepository personalRepository;

    private PersonalFieldError validateEmail(Personal personal) {
        String email = personal.getEmail();

        if (email == null || email.isBlank()) {
            return new PersonalFieldError("email", String.format(BLANK_ERROR_MESSAGE, "EMAIL"));
        }

        if (!Pattern.compile("^(.+)@(\\S+)$").matcher(email).matches()) {
            return new PersonalFieldError("email", String.format(INVALID_ERROR_MESSAGE, "EMAIL"));
        }

        return null;
    }

    private PersonalFieldError validatePassword(Personal personal) {
        String password = personal.getPassword();

        if (password == null || password.isBlank()) {
            return new PersonalFieldError("password", String.format(BLANK_ERROR_MESSAGE, "PASSWORD"));
        }

        return null;
    }

    private PersonalFieldError validateCPF(Personal personal) {
        String cpf = personal.getCpf();

        if (cpf == null || cpf.isBlank()) {
            return new PersonalFieldError("cpf", String.format(BLANK_ERROR_MESSAGE, "CPF"));
        }

        if (!CPFValidator.isValidCPF(cpf)) {
            return new PersonalFieldError("cpf", String.format(INVALID_ERROR_MESSAGE, "CPF"));
        }

        return null;
    }

    private void validateUser(Personal personal) {
        List<PersonalFieldError> errors = Stream.of(
            validateCPF(personal),
            validateEmail(personal),
            validatePassword(personal)
        )
        .filter(Objects::nonNull)
        .collect(Collectors.toList());

        if (!errors.isEmpty()) {
            throw new InvalidPersonalDataException(errors);
        }
    }

    public Personal register(Personal personal) {
        validateUser(personal);
        return this.personalRepository.save(personal);
    }
}
