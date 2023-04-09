package com.challenge.b4a.controllers;

import com.challenge.b4a.domains.Usuario;
import com.challenge.b4a.services.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Api(value = "Usuário", description = "Informaçoes e cadastros de usuários", tags = "Usuário")
public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @ApiOperation("Permite a criação de um usuário com os dados informados no corpo da requisição")
    public ResponseEntity<Usuario> criaUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioService.criaUsuario(usuario));
    }

    @PutMapping("/{usuarioId}")
    @ApiOperation("Atualiza as informações do usuário conforme enviadas no corpo da requisição")
    public ResponseEntity<Usuario> atualizaUsuario(@PathVariable Long usuarioId, @RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.atualizaUsuario(usuarioId, usuario));
    }

    @DeleteMapping("/{usuarioId}")
    @ApiOperation("Deleta um usuário conforme o ID informado")
    public ResponseEntity<Void> deletaUsuario(@PathVariable Long usuarioId) {
        usuarioService.deletaUsuario(usuarioId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @ApiOperation("Lista todos os usuários cadastrados no sistema")
    public ResponseEntity<List<Usuario>> listaUsuarios() {
        return ResponseEntity.ok(usuarioService.listaTodosUsuarios());
    }

    @GetMapping("/{usuarioId}")
    @ApiOperation("Lista um usuário conforme informado o ID de cadastro")
    public ResponseEntity<Usuario> listaUsuarioById(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.listaUsuarioById(usuarioId);
        return ResponseEntity.ok(usuario);
    }
}
