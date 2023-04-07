package com.challenge.b4a.exceptions;

public class EnderecoNaoInformadoException extends RuntimeException {
    public EnderecoNaoInformadoException() {
    }

    public EnderecoNaoInformadoException(String message) {
        super(message);
    }
}
