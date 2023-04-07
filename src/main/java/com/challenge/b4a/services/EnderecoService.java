package com.challenge.b4a.service;

import com.challenge.b4a.domain.Endereco;
import com.challenge.b4a.domain.Usuario;
import com.challenge.b4a.exception.EnderecoNaoEncontradoException;
import com.challenge.b4a.exception.EnderecoNaoInformadoException;
import com.challenge.b4a.exception.EnderecoNaoPertenceAoUsuarioException;
import com.challenge.b4a.exception.UsuarioNaoEncontradoException;
import com.challenge.b4a.repository.EnderecoRepository;
import com.challenge.b4a.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static java.util.Objects.isNull;

@Service
public class EnderecoService {

    private EnderecoRepository enderecoRepository;

    private UsuarioRepository usuarioRepository;

    public EnderecoService(EnderecoRepository enderecoRepository, UsuarioRepository usuarioRepository) {
        this.enderecoRepository = enderecoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Endereco criaEndereco(Long usuarioId, Endereco endereco) {
        if (isNull(endereco)) {
            throw new EnderecoNaoInformadoException("endereco nao informado");
        }

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuario nao encontrado com id " + usuarioId));

        endereco.setUsuario(usuario);

        return enderecoRepository.save(endereco);
    }

    public Endereco atualizaEndereco(Long usuarioId, Long enderecoId, Endereco endereco) {
        if (isNull(endereco)) {
            throw new EnderecoNaoInformadoException("endereco nao informado");
        }

        Endereco enderecoDoBanco = enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new EnderecoNaoEncontradoException("Endereco nao encontrado com id " + enderecoId));

        if (!enderecoDoBanco.getUsuario().getId().equals(usuarioId)) {
            throw new EnderecoNaoPertenceAoUsuarioException("Endereco com id " + enderecoId + " nao pertence ao Usuario de id " + usuarioId);
        }

        enderecoDoBanco.setBairro(endereco.getBairro());
        enderecoDoBanco.setNumero(endereco.getNumero());
        enderecoDoBanco.setComplemento(endereco.getComplemento());
        enderecoDoBanco.setCidade(endereco.getCidade());
        enderecoDoBanco.setEstado(endereco.getEstado());
        enderecoDoBanco.setCep(endereco.getCep());

        return enderecoRepository.save(enderecoDoBanco);
    }

    public void deletaEndereco(Long usuarioId, Long enderecoId) {
        Endereco endereco = enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new EnderecoNaoEncontradoException("Endereco nao encontrado com id " + enderecoId));

        if (!endereco.getUsuario().getId().equals(usuarioId)) {
            throw new EnderecoNaoPertenceAoUsuarioException("Endereco com id " + enderecoId + " nao pertence ao Usuario de id " + usuarioId);
        }

        enderecoRepository.delete(endereco);
    }
}
