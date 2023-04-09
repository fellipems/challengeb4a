package com.challenge.b4a.services;

import com.challenge.b4a.domains.Endereco;
import com.challenge.b4a.domains.Usuario;
import com.challenge.b4a.dto.EnderecoDto;
import com.challenge.b4a.exceptions.*;
import com.challenge.b4a.repositories.EnderecoRepository;
import com.challenge.b4a.repositories.UsuarioRepository;
import com.challenge.b4a.utils.Mensagens;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class EnderecoService {

    private EnderecoRepository enderecoRepository;

    private UsuarioRepository usuarioRepository;

    public EnderecoService(EnderecoRepository enderecoRepository, UsuarioRepository usuarioRepository) {
        this.enderecoRepository = enderecoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Endereco criaEndereco(Endereco endereco) {
        if (isNull(endereco)) {
            throw new EnderecoNaoInformadoException(Mensagens.usar("ENDERECO_NAO_INFORMADO"));
        }

        try {
            return enderecoRepository.save(endereco);
        } catch (ConstraintViolationException e) {
            throw new DadoObrigatorioNaoInformadoException(e.getConstraintViolations().stream().map(message -> message.getPropertyPath().toString() + " " + message.getMessage()).collect(Collectors.toList()).toString());
        }
    }

    public Endereco atualizaEndereco(Long enderecoId, EnderecoDto enderecoDto) {
        if (isNull(enderecoId) || isNull(enderecoDto)) {
            throw new EnderecoNaoInformadoException(Mensagens.usar("ENDERECO_NAO_INFORMADO"));
        }

        Endereco enderecoDoBanco = enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new EnderecoNaoEncontradoException(String.format(Mensagens.usar("ENDERECO_NAO_ENCONTRADO_COM_ID"), enderecoId)));

        validAndSetFields(enderecoDoBanco, enderecoDto);

        return enderecoRepository.save(enderecoDoBanco);
    }

    public void deletaEndereco(Long usuarioId, Long enderecoId) {
        Endereco endereco = enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new EnderecoNaoEncontradoException(String.format(Mensagens.usar("ENDERECO_NAO_ENCONTRADO_COM_ID"), enderecoId)));

        if (!endereco.getUsuario().getId().equals(usuarioId)) {
            throw new EnderecoNaoPertenceAoUsuarioException(String.format(Mensagens.usar("ENDERECO_NAO_PERTENCE_AO_USUARIO"), enderecoId, usuarioId));
        }

        enderecoRepository.delete(endereco);
    }

    public List<Endereco> listaTodosEnderecos() {
        return enderecoRepository.findAll();
    }

    public Endereco listaEnderecoById(Long enderecoId) {
        return enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new EnderecoNaoEncontradoException(String.format(Mensagens.usar("ENDERECO_NAO_ENCONTRADO_COM_ID"), enderecoId)));
    }

    private void validAndSetFields(Endereco enderecoDoBanco, EnderecoDto enderecoDto) {
        if (!isBlank(enderecoDto.getBairro())) {
            enderecoDoBanco.setBairro(enderecoDto.getBairro());
        }

        if (nonNull(enderecoDto.getNumero())) {
            enderecoDoBanco.setNumero(enderecoDto.getNumero());
        }

        if (!isBlank(enderecoDto.getComplemento())) {
            enderecoDoBanco.setComplemento(enderecoDto.getComplemento());
        }

        if (!isBlank(enderecoDto.getCidade())) {
            enderecoDoBanco.setCidade(enderecoDto.getCidade());
        }

        if (!isBlank(enderecoDto.getEstado())) {
            enderecoDoBanco.setEstado(enderecoDto.getEstado());
        }

        if (!isBlank(enderecoDto.getLogradouro())) {
            enderecoDoBanco.setLogradouro(enderecoDto.getLogradouro());
        }

        if (!isBlank(enderecoDto.getCep())) {
            enderecoDoBanco.setCep(enderecoDto.getCep());
        }
    }

    public Endereco vinculaEnderecoNoUsuario(Long usuarioId, Long enderecoId) {
        if (isNull(usuarioId)) {
            throw new UsuarioNaoInformadoException(Mensagens.usar("USUARIO_NAO_INFORMADO"));
        }

        if (isNull(enderecoId)) {
            throw new EnderecoNaoInformadoException(Mensagens.usar("ENDERECO_NAO_INFORMADO"));
        }

        Endereco endereco = enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new EnderecoNaoEncontradoException(String.format(Mensagens.usar("ENDERECO_NAO_ENCONTRADO_COM_ID"), enderecoId)));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(String.format(Mensagens.usar("USUARIO_NAO_ENCONTRADO_COM_ID"), usuarioId)));

        endereco.setUsuario(usuario);

        enderecoRepository.save(endereco);

        return endereco;
    }
}
