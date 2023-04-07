package com.challenge.b4a.exceptions;

public class EnderecoNaoPertenceAoUsuarioException extends RuntimeException {
    public EnderecoNaoPertenceAoUsuarioException() {
    }

    public EnderecoNaoPertenceAoUsuarioException(String message) {
        super(message);
    }
}
