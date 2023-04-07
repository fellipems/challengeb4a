package com.challenge.b4a.exception;

public class DadoObrigatorioNaoInformadoException extends RuntimeException {
    public DadoObrigatorioNaoInformadoException() {
    }

    public DadoObrigatorioNaoInformadoException(String message) {
        super(message);
    }
}
