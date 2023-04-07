package com.challenge.b4a.exceptions;

public class DadoObrigatorioNaoInformadoException extends RuntimeException {
    public DadoObrigatorioNaoInformadoException() {
    }

    public DadoObrigatorioNaoInformadoException(String message) {
        super(message);
    }
}
