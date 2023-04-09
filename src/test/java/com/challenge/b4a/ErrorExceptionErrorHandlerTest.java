package com.challenge.b4a;

import com.challenge.b4a.errorHandler.ErrorExceptionHandler;
import com.challenge.b4a.exceptions.DadoObrigatorioNaoInformadoException;
import com.challenge.b4a.exceptions.EnderecoNaoEncontradoException;
import com.challenge.b4a.exceptions.UsuarioNaoEncontradoException;
import com.challenge.b4a.exceptions.UsuarioNaoInformadoException;
import com.challenge.b4a.resources.exceptions.StandardError;
import com.challenge.b4a.utils.Mensagens;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class ErrorExceptionErrorHandlerTest {
    @Mock
    private HttpServletRequest request = mock(HttpServletRequest.class);

    @Test
    @DisplayName("Deve retornar um ResponseEntity contendo um StandardError com status NOT_FOUND para qualquer tipo de Exception")
    void testHandleException() {
        Exception exceptionMockSimulada = new Exception("Erro mockado");

        // Chama o método handleException e obtém o ResponseEntity retornado
        ResponseEntity<?> response = new ErrorExceptionHandler().handleException(exceptionMockSimulada, request);

        // Verifica se o ResponseEntity possui o status correto (NOT_FOUND)
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        // Verifica se o StandardError contido no ResponseEntity possui o status, mensagem e erro esperados
        StandardError standardError = (StandardError) response.getBody();

        assertEquals(HttpStatus.NOT_FOUND.value(), standardError.getStatus());
        assertEquals("Ocorreu um erro inesperado, tente novamente mais tarde " + exceptionMockSimulada.getMessage(), standardError.getMessage());
        assertEquals(exceptionMockSimulada.getClass().getCanonicalName(), standardError.getError());
    }

    @Test
    @DisplayName("Deve retornar um ResponseEntity contendo um erro com status BAD_REQUEST ao enviar campo String em um campo Long")
    void testHandleHttpMessageNotReadableException() {
        HttpMessageNotReadableException exceptionMock = new HttpMessageNotReadableException("Erro mockado");

        ResponseEntity<?> response = new ErrorExceptionHandler()
                .handleHttpMessageNotReadableException(exceptionMock, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        StandardError standardError = (StandardError) response.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), standardError.getStatus());
        assertEquals("Não é possível converter texto no campo número", standardError.getMessage());
        assertEquals(exceptionMock.getClass().getCanonicalName(), standardError.getError());
    }

    @Test
    @DisplayName("Deve retornar um ResponseEntity contendo um StandardError com status BAD_REQUEST ao não enviar campos obrigatórios")
    void testHandleDadoObrigatorioNaoInformadoException() {
        DadoObrigatorioNaoInformadoException exceptionMock = new DadoObrigatorioNaoInformadoException("Erro mockado");

        ResponseEntity<?> response = new ErrorExceptionHandler()
                .handleDadoObrigatorioNaoInformadoException(exceptionMock, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        StandardError standardError = (StandardError) response.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), standardError.getStatus());
        assertEquals("Erro mockado", standardError.getMessage());
    }

    @Test
    @DisplayName("Deve retornar um ResponseEntity contendo um StandardError com status BAD_REQUEST ao não informar Id de usuário")
    void testHandleUsuarioNaoInformadoException() {
        UsuarioNaoInformadoException exceptionMock = new UsuarioNaoInformadoException(Mensagens.usar("USUARIO_NAO_INFORMADO"));

        ResponseEntity<?> response = new ErrorExceptionHandler()
                .handleUsuarioNaoInformadoException(exceptionMock, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        StandardError standardError = (StandardError) response.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), standardError.getStatus());
        assertEquals(Mensagens.usar("USUARIO_NAO_INFORMADO"), standardError.getMessage());
        assertEquals(exceptionMock.getClass().getCanonicalName(), standardError.getError());
    }

    @Test
    @DisplayName("Deve retornar um ResponseEntity contendo um StandardError com status NOT_FOUND ao não encontrar usuário com ID informado")
    void testHandleUsuarioNaoEncontradoException() {
        UsuarioNaoEncontradoException exception = new UsuarioNaoEncontradoException(String.format(Mensagens.usar("USUARIO_NAO_ENCONTRADO_COM_ID"), 1));

        ResponseEntity<?> response = new ErrorExceptionHandler()
                .handleUsuarioNaoEncontradoException(exception, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        StandardError standardError = (StandardError) response.getBody();
        assertEquals(HttpStatus.NOT_FOUND.value(), standardError.getStatus());
        assertEquals(String.format(Mensagens.usar("USUARIO_NAO_ENCONTRADO_COM_ID"), 1), standardError.getMessage());
        assertEquals(exception.getClass().getCanonicalName(), standardError.getError());
    }

    @Test
    @DisplayName("Deve retornar resposta HTTP 404 NOT FOUND ao não encontrar endereços")
    public void testHandleEnderecoNaoEncontradoException() {
        EnderecoNaoEncontradoException exception = new EnderecoNaoEncontradoException("Endereço não encontrado");

        ResponseEntity<?> responseEntity = new ErrorExceptionHandler().handleEnderecoNaoEncontradoException(exception, request);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        StandardError standardError = (StandardError) responseEntity.getBody();
        assertEquals(HttpStatus.NOT_FOUND.value(), standardError.getStatus());
        assertEquals("Endereço não encontrado", standardError.getMessage());
        assertEquals(exception.getClass().getCanonicalName(), standardError.getError());
    }
}
