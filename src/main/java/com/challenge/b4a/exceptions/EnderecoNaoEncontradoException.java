package com.challenge.b4a.exception;

public class EnderecoNaoEncontradoException extends RuntimeException {

    public EnderecoNaoEncontradoException() {
    }

    public EnderecoNaoEncontradoException(String message) {
        super(message);
    }
}
