package com.challenge.b4a.errorHandler;

import com.challenge.b4a.exceptions.DadoObrigatorioNaoInformadoException;
import com.challenge.b4a.exceptions.EnderecoNaoEncontradoException;
import com.challenge.b4a.exceptions.UsuarioNaoEncontradoException;
import com.challenge.b4a.exceptions.UsuarioNaoInformadoException;
import com.challenge.b4a.resources.exceptions.StandardError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@ControllerAdvice
public class ErrorExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exception, HttpServletRequest request) {
        StandardError standardErrorBuilder = StandardError.Builder
                .newBuilder()
                .withTimestamp(new Date().getTime())
                .withStatus(HttpStatus.NOT_FOUND.value())
                .withMessage("Ocorreu um erro inesperado, tente novamente mais tarde " + exception.getMessage())
                .withError(exception.getClass().getCanonicalName())
                .withPath(request.getRequestURI()).build();

        return new ResponseEntity<>(standardErrorBuilder, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception, HttpServletRequest request) {
        StandardError standardErrorBuilder = StandardError.Builder
                .newBuilder()
                .withTimestamp(new Date().getTime())
                .withStatus(HttpStatus.NOT_FOUND.value())
                .withMessage("Não é possível converter texto no campo número")
                .withError(exception.getClass().getCanonicalName())
                .withPath(request.getRequestURI()).build();

        return new ResponseEntity<>(standardErrorBuilder, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DadoObrigatorioNaoInformadoException.class)
    public ResponseEntity<?> handleDadoObrigatorioNaoInformadoException(DadoObrigatorioNaoInformadoException exception, HttpServletRequest request) {
        StandardError standardErrorBuilder = StandardError.Builder
                .newBuilder()
                .withTimestamp(new Date().getTime())
                .withStatus(HttpStatus.BAD_REQUEST.value())
                .withMessage(exception.getMessage())
                .withPath(request.getRequestURI()).build();

        return new ResponseEntity<>(standardErrorBuilder, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsuarioNaoInformadoException.class)
    public ResponseEntity<?> handleUsuarioNaoInformadoException(UsuarioNaoInformadoException exception, HttpServletRequest request) {
        StandardError standardErrorBuilder = StandardError.Builder
                .newBuilder()
                .withTimestamp(new Date().getTime())
                .withStatus(HttpStatus.BAD_REQUEST.value())
                .withMessage(exception.getMessage())
                .withPath(request.getRequestURI()).build();

        return new ResponseEntity<>(standardErrorBuilder, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<?> handleUsuarioNaoEncontradoException(UsuarioNaoEncontradoException exception, HttpServletRequest request) {
        StandardError standardErrorBuilder = StandardError.Builder
                .newBuilder()
                .withTimestamp(new Date().getTime())
                .withStatus(HttpStatus.NOT_FOUND.value())
                .withMessage(exception.getMessage())
                .withPath(request.getRequestURI()).build();

        return new ResponseEntity<>(standardErrorBuilder, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EnderecoNaoEncontradoException.class)
    public ResponseEntity<?> handleEnderecoNaoEncontradoException(EnderecoNaoEncontradoException exception, HttpServletRequest request) {
        StandardError standardErrorBuilder = StandardError.Builder
                .newBuilder()
                .withTimestamp(new Date().getTime())
                .withStatus(HttpStatus.NOT_FOUND.value())
                .withMessage(exception.getMessage())
                .withPath(request.getRequestURI()).build();

        return new ResponseEntity<>(standardErrorBuilder, HttpStatus.NOT_FOUND);
    }
}
