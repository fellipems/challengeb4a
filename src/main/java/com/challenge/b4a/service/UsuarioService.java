package com.challenge.b4a.service;

import com.challenge.b4a.domain.Usuario;
import com.challenge.b4a.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario criaUsuario(String nome, String email) {
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizaUsuario(Long usuarioId, String nome, String email) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new NotFoundException("User not found"));
        usuario.setNome(nome);
        usuario.setEmail(email);
        return usuarioRepository.save(usuario);
    }

    public void deletaUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new NotFoundException("User not found"));
        usuarioRepository.delete(usuario);
    }

    public List<Usuario> listaTodosUsuarios() {
        return usuarioRepository.findAll();
    }
}
