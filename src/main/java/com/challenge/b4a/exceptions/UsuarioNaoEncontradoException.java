package com.challenge.b4a.exceptions;

public class UsuarioNaoEncontradoException extends RuntimeException {

    public UsuarioNaoEncontradoException() {
    }

    public UsuarioNaoEncontradoException(String message) {
        super(message);
    }
}
