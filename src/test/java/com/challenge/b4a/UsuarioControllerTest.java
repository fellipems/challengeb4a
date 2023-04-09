package com.challenge.b4a;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.challenge.b4a.controllers.UsuarioController;
import com.challenge.b4a.domains.Usuario;
import com.challenge.b4a.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UsuarioServiceTest {

    private UsuarioController usuarioController;

    @Mock
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        usuarioController = new UsuarioController(usuarioService);
    }

    @Test
    void criaUsuario_deveRetornarUsuarioCriado() {
        // given
        Usuario usuario = new Usuario();
        when(usuarioService.criaUsuario(any(Usuario.class))).thenReturn(usuario);

        // when
        ResponseEntity<Usuario> response = usuarioController.criaUsuario(usuario);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(usuario);
    }

    @Test
    void atualizaUsuario_deveRetornarUsuarioAtualizado() {
        // given
        Long usuarioId = 1L;
        Usuario usuarioAtualizado = new Usuario();
        when(usuarioService.atualizaUsuario(anyLong(), any(Usuario.class))).thenReturn(usuarioAtualizado);

        // when
        ResponseEntity<Usuario> response = usuarioController.atualizaUsuario(usuarioId, usuarioAtualizado);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(usuarioAtualizado);
    }

    @Test
    void deletaUsuario_deveRetornarNoContent() {
        // given
        Long usuarioId = 1L;

        // when
        ResponseEntity<Void> response = usuarioController.deletaUsuario(usuarioId);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void listaUsuarios_deveRetornarListaDeUsuarios() {
        // given
        List<Usuario> usuarios = Arrays.asList(new Usuario(), new Usuario());
        when(usuarioService.listaTodosUsuarios()).thenReturn(usuarios);

        // when
        ResponseEntity<List<Usuario>> response = usuarioController.listaUsuarios();

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(usuarios);
    }

    @Test
    void listaUsuarioById_deveRetornarUsuarioComId() {
        // given
        Long usuarioId = 1L;
        Usuario usuario = new Usuario();
        when(usuarioService.listaUsuarioById(usuarioId)).thenReturn(usuario);

        // when
        ResponseEntity<Usuario> response = usuarioController.listaUsuarioById(usuarioId);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(usuario);
    }

}
