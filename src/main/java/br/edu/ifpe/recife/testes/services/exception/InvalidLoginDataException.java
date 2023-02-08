package br.edu.ifpe.recife.testes.services.exception;

import java.util.List;

import lombok.Getter;

public class InvalidLoginDataException extends RuntimeException {
	@Getter
    private final List<LoginFieldError> errors;
	
	public InvalidLoginDataException(List<LoginFieldError> errors) {
        this.errors = errors;
    }
}
