package br.edu.ifpe.recife.testes.services.exception;

import lombok.Getter;

import java.util.List;

public class InvalidClientDataException extends RuntimeException {
	@Getter
    private final List<ClientFieldError> errors;
	
	public InvalidClientDataException(List<ClientFieldError> errors) {
        this.errors = errors;
    }
}
