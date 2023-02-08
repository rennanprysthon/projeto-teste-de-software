package br.edu.ifpe.recife.testes.services.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FieldError {
	private String field;
    private String message;
}
