package com.challenge.b4a.services;

import com.challenge.b4a.domains.Endereco;
import com.challenge.b4a.domains.Usuario;
import com.challenge.b4a.dto.EnderecoDto;
import com.challenge.b4a.exceptions.*;
import com.challenge.b4a.repositories.EnderecoRepository;
import com.challenge.b4a.repositories.UsuarioRepository;
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
        if (isNull(usuarioId)) {
            throw new UsuarioNaoInformadoException("Usuario nao informado");
        }

        if (isNull(endereco)) {
            throw new EnderecoNaoInformadoException("endereco nao informado");
        }

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuario nao encontrado com id " + usuarioId));

        endereco.setUsuario(usuario);

        return enderecoRepository.save(endereco);
    }

    public Endereco atualizaEndereco(Long usuarioId, Long enderecoId, EnderecoDto enderecoDto) {
        if (Objects.isNull(usuarioId)) {
            throw new UsuarioNaoInformadoException("Usuario nao informado");
        }

        if (Objects.isNull(enderecoId) || isNull(enderecoDto)) {
            throw new EnderecoNaoInformadoException("endereco nao informado");
        }

        Endereco enderecoDoBanco = enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new EnderecoNaoEncontradoException("Endereco nao encontrado com id " + enderecoId));

        if (!enderecoDoBanco.getUsuario().getId().equals(usuarioId)) {
            throw new EnderecoNaoPertenceAoUsuarioException("Endereco com id " + enderecoId + " nao pertence ao Usuario de id " + usuarioId);
        }

        enderecoDoBanco.setBairro(enderecoDto.getBairro());
        enderecoDoBanco.setNumero(enderecoDto.getNumero());
        enderecoDoBanco.setComplemento(enderecoDto.getComplemento());
        enderecoDoBanco.setCidade(enderecoDto.getCidade());
        enderecoDoBanco.setEstado(enderecoDto.getEstado());
        enderecoDoBanco.setCep(enderecoDto.getCep());

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
