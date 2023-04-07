package com.challenge.b4a.service;

import com.challenge.b4a.domain.Usuario;
import com.challenge.b4a.exception.DadoObrigatorioNaoInformadoException;
import com.challenge.b4a.exception.UsuarioNaoEncontradoException;
import com.challenge.b4a.exception.UsuarioNaoInformadoException;
import com.challenge.b4a.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;

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
            throw new UsuarioNaoInformadoException("Usuario nao informado");
        }

        try {
            return usuarioRepository.save(usuario);
        } catch (ConstraintViolationException e) {
            throw new DadoObrigatorioNaoInformadoException("Dados obrigatórios não informados");
        }
    }

    public Usuario atualizaUsuario(Long usuarioId, Usuario usuario) {
        Usuario usuarioDoBancoDados = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuario nao encontrado com id " + usuarioId));

        if (!isBlank(usuario.getNome())) {
            usuarioDoBancoDados.setNome(usuario.getNome());
        }

        if (!isBlank(usuario.getEmail())) {
            usuarioDoBancoDados.setEmail(usuario.getEmail());
        }

        if (!isEmpty(usuario.getEnderecos())) {
            usuarioDoBancoDados.setEnderecos(usuario.getEnderecos());
        }

        return usuarioRepository.save(usuario);
    }

    public void deletaUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuario nao encontrado com id " + usuarioId));

        usuarioRepository.delete(usuario);
    }

    public List<Usuario> listaTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario listaUsuarioById(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuario nao encontrado com id " + usuarioId));
    }
}
