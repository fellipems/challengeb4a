package com.challenge.b4a;

import com.challenge.b4a.domains.Endereco;
import com.challenge.b4a.domains.Usuario;
import com.challenge.b4a.dto.EnderecoDto;
import com.challenge.b4a.exceptions.*;
import com.challenge.b4a.repositories.EnderecoRepository;
import com.challenge.b4a.repositories.UsuarioRepository;
import com.challenge.b4a.services.EnderecoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EnderecoServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    private EnderecoService enderecoService;

    private Endereco enderecoDoBanco;

    private EnderecoDto enderecoDto;

    Endereco endereco;

    Usuario usuario;

    Long usuarioId = 1L;

    Long enderecoId = 2L;

    List<Endereco> enderecos = new ArrayList<>();


    @BeforeEach
    void setUp() {
        enderecoService = new EnderecoService(enderecoRepository, usuarioRepository);

        enderecoDoBanco = new Endereco();
        enderecoDoBanco.setId(1L);
        enderecoDoBanco.setBairro("Centro");
        enderecoDoBanco.setCep("12345-678");
        enderecoDoBanco.setCidade("São Paulo");
        enderecoDoBanco.setComplemento("Ap 123");
        enderecoDoBanco.setEstado("SP");
        enderecoDoBanco.setNumero(123L);
        enderecoDoBanco.setUsuario(new Usuario(1L));

        enderecoDto = new EnderecoDto();
        enderecoDto.setBairro("Liberdade");
        enderecoDto.setCep("12345-678");
        enderecoDto.setCidade("São Paulo");
        enderecoDto.setComplemento("Ap 123");
        enderecoDto.setEstado("SP");
        enderecoDto.setNumero(456L);

        endereco = new Endereco();
        endereco.setBairro("Centro");
        endereco.setCidade("São Paulo");
        endereco.setEstado("SP");
        endereco.setNumero(123L);
        endereco.setCep("01000-000");
        endereco.setComplemento("apto 123");

        usuario = new Usuario();
        endereco.setUsuario(usuario);

        enderecos.add(endereco);
    }

    @Test
    @DisplayName("Teste criaEndereco - deve retornar um endereço cadastrado com sucesso")
    void deveCriarEnderecoComSucesso() {
        when(enderecoRepository.save(endereco)).thenReturn(endereco);

        Endereco result = enderecoService.criaEndereco(endereco);

        assertNotNull(result);
        assertEquals(usuario, result.getUsuario());
        assertEquals(endereco.getBairro(), result.getBairro());
        assertEquals(endereco.getCidade(), result.getCidade());
        assertEquals(endereco.getEstado(), result.getEstado());
        assertEquals(endereco.getNumero(), result.getNumero());
        assertEquals(endereco.getCep(), result.getCep());
        assertEquals(endereco.getComplemento(), result.getComplemento());
    }

    @Test
    @DisplayName("Teste criaEndereco - deve retornar exception caso não informado um endereco")
    void deveLancarExcecaoQuandoEnderecoNaoInformado() {
        endereco = null;

        assertThrows(EnderecoNaoInformadoException.class, () -> enderecoService.criaEndereco(endereco));
    }

    @Test
    @DisplayName("Teste atualizaEndereco - deve retornar e salvar endereço atualizado quando informado dados válidos")
    void deveAtualizarEnderecoComDadosValidos() {
        when(enderecoRepository.findById(enderecoId)).thenReturn(Optional.of(enderecoDoBanco));
        when(enderecoRepository.save(enderecoDoBanco)).thenReturn(enderecoDoBanco);

        Endereco enderecoAtualizado = enderecoService.atualizaEndereco(enderecoId, enderecoDto);
        enderecoAtualizado.setId(2L);

        assertNotNull(enderecoAtualizado);
        assertEquals(enderecoId, enderecoAtualizado.getId());
        assertEquals(usuarioId, enderecoAtualizado.getUsuario().getId());
        assertEquals(enderecoDto.getBairro(), enderecoAtualizado.getBairro());
        assertEquals(enderecoDto.getNumero(), enderecoAtualizado.getNumero());
        assertEquals(enderecoDto.getComplemento(), enderecoAtualizado.getComplemento());
        assertEquals(enderecoDto.getCidade(), enderecoAtualizado.getCidade());
        assertEquals(enderecoDto.getEstado(), enderecoAtualizado.getEstado());
        assertEquals(enderecoDto.getCep(), enderecoAtualizado.getCep());

        verify(enderecoRepository, times(1)).findById(enderecoId);
        verify(enderecoRepository, times(1)).save(enderecoDoBanco);
    }

    @Test
    @DisplayName("Teste atualizaEndereco - deve retornar excecao caso nao informe um id de endereço ou endereço")
    void deveLancarExcecaoCasoNaoInformeIdDoEnderecoOuEnderecoNaoInformado() {
        assertThrows(EnderecoNaoInformadoException.class, () -> enderecoService.atualizaEndereco(null, enderecoDto));

        assertThrows(EnderecoNaoInformadoException.class, () -> enderecoService.atualizaEndereco(enderecoId, null));
    }

    @Test
    @DisplayName("Teste atualizaEndereco - deve retornar excecao caso nao encontre um endereco com id informado")
    void deveLancarExcecaoCasoNaoEncontreEnderecoComIdInformado() {
        when(enderecoRepository.findById(enderecoId)).thenReturn(Optional.empty());

        assertThrows(EnderecoNaoEncontradoException.class, () -> enderecoService.atualizaEndereco(enderecoId, enderecoDto));
    }

    @Test
    @DisplayName("Teste deletaEndereco - deve excluir endereco com id informado")
    void deveExcluirEnderecoComIdInformado() {
        Endereco endereco = new Endereco();
        endereco.setId(enderecoId);

        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);

        endereco.setUsuario(usuario);

        when(enderecoRepository.findById(enderecoId)).thenReturn(Optional.of(endereco));

        enderecoService.deletaEndereco(usuarioId, enderecoId);

        verify(enderecoRepository, times(1)).delete(endereco);
    }

    @Test
    @DisplayName("Teste deletaEndereco - deve retornar excecao caso nao exista um endereco com id informado")
    void deveRetornarExcecaoCasoNaoExistaUmEnderecoComIdInformado() {
        when(enderecoRepository.findById(enderecoId)).thenReturn(Optional.empty());

        assertThrows(EnderecoNaoEncontradoException.class, () -> enderecoService.deletaEndereco(usuarioId, enderecoId));
    }

    @Test
    @DisplayName("Teste deletaEndereco - deve retornar excecao caso endereco nao pertença ao usuário informado")
    void deveRetornarExcecaoCasoEnderecoNaoPertenceAoUsuarioInformado() {
        Endereco endereco = new Endereco();
        endereco.setId(enderecoId);

        Usuario usuario = new Usuario();
        usuario.setId(usuarioId + 1); // usuário diferente

        endereco.setUsuario(usuario);

        when(enderecoRepository.findById(enderecoId)).thenReturn(Optional.of(endereco));

        assertThrows(EnderecoNaoPertenceAoUsuarioException.class, () -> enderecoService.deletaEndereco(usuarioId, enderecoId));
    }

    @Test
    @DisplayName("Teste listaTodosEnderecos - deve retornar todos os enderecos")
    void deveListarERetornarTodosOsEnderecos() {
        when(enderecoRepository.findAll()).thenReturn(enderecos);

        List<Endereco> enderecosEncontrados = enderecoService.listaTodosEnderecos();

        assertNotNull(enderecosEncontrados);
        assertEquals(1, enderecosEncontrados.size());
        assertEquals(endereco.getBairro(), enderecosEncontrados.get(0).getBairro());
        assertEquals(endereco.getCidade(), enderecosEncontrados.get(0).getCidade());
        assertEquals(endereco.getEstado(), enderecosEncontrados.get(0).getEstado());
    }

    @Test
    @DisplayName("Teste listaEnderecoById - deve retornar endereço com id informado")
    void deveRetornarEnderecoComIdInformado() {
        when(enderecoRepository.findById(enderecoId)).thenReturn(Optional.of(endereco));

        Endereco enderecoEncontrado = enderecoService.listaEnderecoById(enderecoId);

        assertNotNull(enderecoEncontrado);
        assertEquals(endereco.getBairro(), enderecoEncontrado.getBairro());
        assertEquals(endereco.getCidade(), enderecoEncontrado.getCidade());
        assertEquals(endereco.getEstado(), enderecoEncontrado.getEstado());
    }

    @Test
    @DisplayName("Teste listaEnderecoById - deve retornar excecao caso nao encontre endereço com id informado")
    void deveRetornarExcecaoCasoNaoEncontreUsuario() {
        when(enderecoRepository.findById(enderecoId)).thenReturn(Optional.empty());

        assertThrows(EnderecoNaoEncontradoException.class, () -> enderecoService.listaEnderecoById(enderecoId));
    }

    @Test
    @DisplayName("Deve vincular o endereço ao usuário")
    void testVinculaEnderecoNoUsuario() {
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(enderecoRepository.findById(enderecoId)).thenReturn(Optional.of(endereco));
        when(enderecoRepository.save(any(Endereco.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Endereco resultado = enderecoService.vinculaEnderecoNoUsuario(usuarioId, enderecoId);

        assertNotNull(resultado);
        assertEquals(usuario, resultado.getUsuario());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar vincular endereço sem informar usuário")
    void testVinculaEnderecoNoUsuarioSemUsuario() {
        UsuarioNaoInformadoException excecaoEsperada = assertThrows(UsuarioNaoInformadoException.class,
                () -> enderecoService.vinculaEnderecoNoUsuario(null, enderecoId));

        assertEquals("Usuario nao informado", excecaoEsperada.getMessage());

        verifyNoInteractions(usuarioRepository);
        verifyNoInteractions(enderecoRepository);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar vincular endereço sem informar endereço")
    void testVinculaEnderecoNoUsuarioSemEndereco() {
        EnderecoNaoInformadoException excecaoEsperada = assertThrows(EnderecoNaoInformadoException.class,
                () -> enderecoService.vinculaEnderecoNoUsuario(usuarioId, null));

        assertEquals("endereco nao informado", excecaoEsperada.getMessage());

        verifyNoInteractions(usuarioRepository);
        verifyNoInteractions(enderecoRepository);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar vincular endereço a usuário inexistente")
    void testVinculaEnderecoNoUsuarioUsuarioInexistente() {
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.empty());
        when(enderecoRepository.findById(enderecoId)).thenReturn(Optional.of(new Endereco()));

        UsuarioNaoEncontradoException excecaoEsperada = assertThrows(UsuarioNaoEncontradoException.class,
                () -> enderecoService.vinculaEnderecoNoUsuario(usuarioId, enderecoId));

        assertEquals("Usuario nao encontrado com id " + usuarioId, excecaoEsperada.getMessage());

        verify(usuarioRepository).findById(usuarioId);
        verify(enderecoRepository).findById(enderecoId);
        verifyNoMoreInteractions(usuarioRepository);
        verifyNoMoreInteractions(enderecoRepository);
    }
}
