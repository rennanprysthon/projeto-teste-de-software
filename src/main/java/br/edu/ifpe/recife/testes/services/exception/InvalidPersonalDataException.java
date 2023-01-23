package br.edu.ifpe.recife.testes.services.exception;

import lombok.Getter;

import java.util.List;

public class InvalidPersonalDataException extends RuntimeException {
    @Getter
    private final List<PersonalFieldError> errors;

    public InvalidPersonalDataException(List<PersonalFieldError> errors) {
        this.errors = errors;
    }
}
