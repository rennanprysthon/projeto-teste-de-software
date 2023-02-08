package br.edu.ifpe.recife.testes.services.exception;

public class ContractInvalidException extends RuntimeException {
    public ContractInvalidException(String message) {
        super(message);
    }

    public ContractInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}
