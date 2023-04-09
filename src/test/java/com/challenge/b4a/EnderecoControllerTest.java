package com.challenge.b4a;

import com.challenge.b4a.controllers.EnderecoController;
import com.challenge.b4a.domains.Endereco;
import com.challenge.b4a.dto.EnderecoDto;
import com.challenge.b4a.services.EnderecoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EnderecoControllerTest {

    @Mock
    private EnderecoService enderecoService;

    private EnderecoController enderecoController;

    Long usuarioId = 1L;

    Long enderecoId = 2L;

    @BeforeEach
    void setUp() {
        enderecoController = new EnderecoController(enderecoService);
    }

    @Test
    @DisplayName("Teste criaEndereco - deve retornar um ResponseEntity com status CREATED e o endereço criado")
    void deveCriarEndereco() {
        Endereco endereco = new Endereco();
        when(enderecoService.criaEndereco(any(Endereco.class))).thenReturn(endereco);

        ResponseEntity<Endereco> response = enderecoController.criaEndereco(endereco);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(endereco, response.getBody());
    }

    @Test
    @DisplayName("Teste vinculaEnderecoNoUsuario - deve retornar um ResponseEntity com status CREATED e o endereço vinculado no Usuário")
    void deveVincularEnderecoEmUsuarioInformado() {
        Endereco endereco = new Endereco();
        when(enderecoService.vinculaEnderecoNoUsuario(anyLong(), anyLong())).thenReturn(endereco);

        ResponseEntity<Endereco> response = enderecoController.vinculaEnderecoNoUsuario(usuarioId, enderecoId);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(endereco, response.getBody());
    }

    @Test
    @DisplayName("Teste atualizaEndereco - deve retornar um ResponseEntity com status OK e o endereço atualizado")
    void deveAtualizarEndereco() {
        EnderecoDto novoEndereco = new EnderecoDto();
        Endereco endereco = new Endereco();
        when(enderecoService.atualizaEndereco(any(Long.class), any(EnderecoDto.class))).thenReturn(endereco);

        ResponseEntity<Endereco> response = enderecoController.atualizaEndereco(enderecoId, novoEndereco);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(endereco, response.getBody());
    }

    @Test
    @DisplayName("Teste deletaEndereco - deve retornar um ResponseEntity com status NO_CONTENT")
    void deveDeletarEndereco() {
        ResponseEntity<Endereco> response = enderecoController.deletaEndereco(usuarioId, enderecoId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(enderecoService).deletaEndereco(usuarioId, enderecoId);
    }

    @Test
    @DisplayName("Teste listaTodosEnderecos - deve retornar um ResponseEntity com status OK e lista de endereços")
    void deveListarTodosUsuarios() {
        List<Endereco> enderecos = Arrays.asList(new Endereco(), new Endereco());
        when(enderecoService.listaTodosEnderecos()).thenReturn(enderecos);

        ResponseEntity<List<Endereco>> response = enderecoController.listaEnderecos();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(enderecos);
    }

    @Test
    @DisplayName("Teste listaUsuarioById - deve retornar um ResponseEntity com status OK e usuário com Id informado")
    void deveListarUsuarioComIdInformado() {
        Endereco endereco = new Endereco();
        when(enderecoService.listaEnderecoById(enderecoId)).thenReturn(endereco);

        ResponseEntity<Endereco> response = enderecoController.listaEnderecoById(enderecoId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(endereco);
    }
}