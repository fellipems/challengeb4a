package com.challenge.b4a;

import com.challenge.b4a.controllers.UsuarioController;
import com.challenge.b4a.domains.Usuario;
import com.challenge.b4a.services.UsuarioService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {

    private UsuarioController usuarioController;

    Long usuarioId = 1L;

    @Mock
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        usuarioController = new UsuarioController(usuarioService);
    }

    @Test
    @DisplayName("Teste criaUsuario - deve retornar um ResponseEntity com status CREATED e o usuário criado")
    void deveCriarUsuario() {
        Usuario usuario = new Usuario();
        when(usuarioService.criaUsuario(any(Usuario.class))).thenReturn(usuario);

        ResponseEntity<Usuario> response = usuarioController.criaUsuario(usuario);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(usuario);
    }

    @Test
    @DisplayName("Teste atualizaUsuario - deve retornar um ResponseEntity com status OK e o usuário atualizado")
    void deveAtualizarERetornarUsuarioAtualizado() {
        Usuario usuarioAtualizado = new Usuario();
        when(usuarioService.atualizaUsuario(anyLong(), any(Usuario.class))).thenReturn(usuarioAtualizado);

        ResponseEntity<Usuario> response = usuarioController.atualizaUsuario(usuarioId, usuarioAtualizado);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(usuarioAtualizado);
    }

    @Test
    @DisplayName("Teste deletaUsuario - deve retornar um ResponseEntity com status NO_CONTENT e o usuário removido")
    void deveDeletarUsuarioERetornarUmNoContent() {
        ResponseEntity<Void> response = usuarioController.deletaUsuario(usuarioId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @DisplayName("Teste listaUsuarios - deve retornar um ResponseEntity com status OK e lista de usuários")
    void deveListarTodosUsuarios() {
        List<Usuario> usuarios = Arrays.asList(new Usuario(), new Usuario());
        when(usuarioService.listaTodosUsuarios()).thenReturn(usuarios);

        ResponseEntity<List<Usuario>> response = usuarioController.listaUsuarios();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(usuarios);
    }

    @Test
    @DisplayName("Teste listaUsuarioById - deve retornar um ResponseEntity com status OK e usuário com Id informado")
    void deveListarUsuarioComIdInformado() {
        Usuario usuario = new Usuario();
        when(usuarioService.listaUsuarioById(usuarioId)).thenReturn(usuario);

        ResponseEntity<Usuario> response = usuarioController.listaUsuarioById(usuarioId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(usuario);
    }
}
