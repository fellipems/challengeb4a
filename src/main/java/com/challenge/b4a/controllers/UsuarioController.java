package com.challenge.b4a.controller;

import com.challenge.b4a.domain.Usuario;
import com.challenge.b4a.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Usuario> criaUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioService.criaUsuario(usuario));
    }

    @PutMapping("/{usuarioId}")
    public ResponseEntity<Usuario> atualizaUsuario(@PathVariable Long usuarioId, @RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.atualizaUsuario(usuarioId, usuario));
    }

    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Void> deletaUsuario(@PathVariable Long usuarioId) {
        usuarioService.deletaUsuario(usuarioId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listaUsuarios() {
        return ResponseEntity.ok(usuarioService.listaTodosUsuarios());
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<Usuario> listaUsuarioById(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.listaUsuarioById(usuarioId);
        return ResponseEntity.ok(usuario);
    }
}
