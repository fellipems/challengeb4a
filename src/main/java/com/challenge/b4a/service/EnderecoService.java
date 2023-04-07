package com.challenge.b4a.service;

import com.challenge.b4a.domain.Endereco;
import com.challenge.b4a.domain.Usuario;
import com.challenge.b4a.repository.EnderecoRepository;
import com.challenge.b4a.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    private EnderecoRepository enderecoRepository;

    private UsuarioRepository usuarioRepository;

    public EnderecoService(EnderecoRepository enderecoRepository, UsuarioRepository usuarioRepository) {
        this.enderecoRepository = enderecoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Endereco criaEndereco(Long usuarioId, Endereco endereco) {
        Usuario user = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", usuarioId));
        endereco.setUsuario(user);
        return enderecoRepository.save(endereco);
    }

    public Endereco atualizaEndereco(Long usuarioId, Long enderecoId, Endereco endereco) {
        Endereco retornoEndereco = enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new ResourceNotFoundException("Endereco", "id", enderecoId));
        if (!retornoEndereco.getUsuario().getId().equals(usuarioId)) {
            throw new BadRequestException("Endereco com id " + enderecoId + " nao pertence ao Usuario de id " + usuarioId);
        }
        retornoEndereco.setBairro(endereco.getBairro());
        retornoEndereco.setNumero(endereco.getNumero());
        retornoEndereco.setComplemento(endereco.getComplemento());
        retornoEndereco.setCidade(endereco.getCidade());
        retornoEndereco.setEstado(endereco.getEstado());
        retornoEndereco.setCep(endereco.getCep());
        return enderecoRepository.save(retornoEndereco);
    }

    public void deletaEndereco(Long usuarioId, Long enderecoId) {
        Endereco endereco = enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new ResourceNotFoundException("Endereco", "id", enderecoId));
        if (!endereco.getUsuario().getId().equals(usuarioId)) {
            throw new BadRequestException("Endereco com id " + enderecoId + " nao pertence ao Usuario de id " + usuarioId);
        }
        enderecoRepository.delete(endereco);
    }
}
