package com.challenge.b4a;

import com.challenge.b4a.domains.Endereco;
import com.challenge.b4a.domains.Usuario;
import com.challenge.b4a.exceptions.UsuarioNaoEncontradoException;
import com.challenge.b4a.exceptions.UsuarioNaoInformadoException;
import com.challenge.b4a.repositories.UsuarioRepository;
import com.challenge.b4a.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;
    private List<Usuario> usuarios;

    Long usuarioId = 1L;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setNome("Fulano");
        usuario.setEmail("fulano@gmail.com");

        usuarios = new ArrayList<>();
        usuarios.add(usuario);
    }

    @Test
    @DisplayName("Teste criaUsuario - deve retornar um usuário cadastrado com sucesso")
    void deveCriarUsuario() {
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario usuarioCriado = usuarioService.criaUsuario(usuario);

        assertNotNull(usuarioCriado);
        assertEquals(usuario.getNome(), usuarioCriado.getNome());
        assertEquals(usuario.getEmail(), usuarioCriado.getEmail());
    }

    @Test
    @DisplayName("Teste criaUsuario - deve retornar excecao ao nao informar um usuário")
    void deveRetornarExcecaoCasoNaoInformeUmUsuario() {
        assertThrows(UsuarioNaoInformadoException.class, () -> usuarioService.criaUsuario(null));
    }

    @Test
    @DisplayName("Teste atualizaUsuario - deve atualizar e salvar usuário")
    void deveAtualizarESalvarUsuario() {
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario usuarioAtualizado = usuarioService.atualizaUsuario(usuarioId, usuario);

        assertNotNull(usuarioAtualizado);
        assertEquals(usuario.getNome(), usuarioAtualizado.getNome());
        assertEquals(usuario.getEmail(), usuarioAtualizado.getEmail());
        verify(usuarioRepository, times(1)).save(usuarioAtualizado);
    }

    @Test
    @DisplayName("Teste atualizaUsuario - deve atualizar e salvar usuário com endereço, caso endereço seja enviado")
    void deveAtualizaEnderecoDoUsuarioCasoEnvie() {
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Endereco endereco = new Endereco();
        endereco.setUsuario(usuario);
        endereco.setBairro("Teste");

        usuario.setEnderecos(Collections.singletonList(endereco));

        Usuario usuarioAtualizado = usuarioService.atualizaUsuario(usuarioId, usuario);
        usuarioAtualizado.setEnderecos(Collections.singletonList(endereco));

        assertNotNull(usuarioAtualizado);
        assertEquals(usuario.getNome(), usuarioAtualizado.getNome());
        assertEquals(usuario.getEmail(), usuarioAtualizado.getEmail());
        assertNotNull(usuarioAtualizado.getEnderecos());
    }

    @Test
    @DisplayName("Teste atualizaUsuario - deve retornar excecao caso nao encontre usuário com id informado")
    void deveRetornarExcecaoCasoNaoEncontreUsuarioAoAtualizar() {
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioService.atualizaUsuario(usuarioId, usuario));
    }

    @Test
    @DisplayName("Teste deletaUsuario - deve deletar usuário com id informado")
    void deveDeletarUsuario() {
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));

        assertDoesNotThrow(() -> usuarioService.deletaUsuario(usuarioId));
    }

    @Test
    @DisplayName("Teste deletaUsuario - deve retornar excecao caso nao encontre usuário com id informado ao excluir")
    void deveRetornarExcecaoCasoNaoEncontreUsuarioAoDeletar() {
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioService.deletaUsuario(usuarioId));
    }

    @Test
    @DisplayName("Teste listaTodosUsuarios - deve retornar todos usuários")
    void deveListarERetornarTodosOsUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<Usuario> usuariosEncontrados = usuarioService.listaTodosUsuarios();

        assertNotNull(usuariosEncontrados);
        assertEquals(1, usuariosEncontrados.size());
        assertEquals(usuario.getNome(), usuariosEncontrados.get(0).getNome());
        assertEquals(usuario.getEmail(), usuariosEncontrados.get(0).getEmail());
    }

    @Test
    @DisplayName("Teste listaUsuarioById - deve retornar usuário com id informado")
    void deveRetornarUsuarioComIdInformado() {
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));

        Usuario usuarioEncontrado = usuarioService.listaUsuarioById(usuarioId);

        assertNotNull(usuarioEncontrado);
        assertEquals(usuario.getNome(), usuarioEncontrado.getNome());
        assertEquals(usuario.getEmail(), usuarioEncontrado.getEmail());
    }

    @Test
    @DisplayName("Teste listaUsuarioById - deve retornar excecao caso nao encrontre usuário com id informado")
    void deveRetornarExcecaoCasoNaoEncontreUsuario() {
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioService.listaUsuarioById(usuarioId));
    }
}