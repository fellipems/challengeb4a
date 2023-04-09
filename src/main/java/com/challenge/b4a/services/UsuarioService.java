package com.challenge.b4a.services;

import com.challenge.b4a.domains.Usuario;
import com.challenge.b4a.exceptions.DadoObrigatorioNaoInformadoException;
import com.challenge.b4a.exceptions.UsuarioNaoEncontradoException;
import com.challenge.b4a.exceptions.UsuarioNaoInformadoException;
import com.challenge.b4a.repositories.UsuarioRepository;
import com.challenge.b4a.utils.Mensagens;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.util.CollectionUtils.isEmpty;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario criaUsuario(Usuario usuario) {
        if (isNull(usuario)) {
            throw new UsuarioNaoInformadoException(Mensagens.usar("USUARIO_NAO_INFORMADO"));
        }

        try {
            return usuarioRepository.save(usuario);
        } catch (ConstraintViolationException e) {
            throw new DadoObrigatorioNaoInformadoException(e.getConstraintViolations().stream().map(message -> message.getPropertyPath().toString() + " " + message.getMessage()).collect(Collectors.toList()).toString());
        }
    }

    public Usuario atualizaUsuario(Long usuarioId, Usuario usuario) {
        Usuario usuarioDoBancoDados = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(String.format(Mensagens.usar("USUARIO_NAO_ENCONTRADO_COM_ID"), usuarioId)));

        if (!isBlank(usuario.getNome())) {
            usuarioDoBancoDados.setNome(usuario.getNome());
        }

        if (!isBlank(usuario.getEmail())) {
            usuarioDoBancoDados.setEmail(usuario.getEmail());
        }

        if (!isEmpty(usuario.getEnderecos())) {
            usuarioDoBancoDados.setEnderecos(usuario.getEnderecos());
        }

        return usuarioRepository.save(usuarioDoBancoDados);
    }

    public void deletaUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(String.format(Mensagens.usar("USUARIO_NAO_ENCONTRADO_COM_ID"), usuarioId)));

        usuarioRepository.delete(usuario);
    }

    public List<Usuario> listaTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario listaUsuarioById(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(String.format(Mensagens.usar("USUARIO_NAO_ENCONTRADO_COM_ID"), usuarioId)));
    }
}