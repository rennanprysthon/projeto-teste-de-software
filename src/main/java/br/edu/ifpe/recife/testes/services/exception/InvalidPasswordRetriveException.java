package br.edu.ifpe.recife.testes.services.exception;

import java.util.List;

import lombok.Getter;

public class InvalidPasswordRetriveException extends RuntimeException {
	@Getter
    private final List<FieldError> errors;
	
	public InvalidPasswordRetriveException(List<FieldError> errors) {
        this.errors = errors;
    }
}
