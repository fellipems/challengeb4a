package com.challenge.b4a.exceptions;

public class EnderecoNaoEncontradoException extends RuntimeException {

    public EnderecoNaoEncontradoException() {
    }

    public EnderecoNaoEncontradoException(String message) {
        super(message);
    }
}
