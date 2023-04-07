package com.challenge.b4a.controller;

import com.challenge.b4a.domain.Endereco;
import com.challenge.b4a.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/usuarios/{usuarioId}/enderecos")
public class EnderecoController {
    @Autowired
    private EnderecoService enderecoService;

    @PostMapping
    public ResponseEntity<Endereco> criaEndereco(@PathVariable(value = "usuarioId") Long usuarioId, @RequestBody Endereco endereco) {
        return ResponseEntity
                .status(CREATED)
                .body(enderecoService.criaEndereco(usuarioId, endereco));
    }

    @PutMapping("/{enderecoId}")
    public ResponseEntity<Endereco> atualizaEndereco(@PathVariable(value = "usuarioId") Long usuarioId,
                                 @PathVariable(value = "enderecoId") Long enderecoId,
                                 @RequestBody Endereco novoEndereco) {
        return ResponseEntity.ok(enderecoService.atualizaEndereco(usuarioId, enderecoId, novoEndereco));
    }

    @DeleteMapping("/{enderecoId}")
    public ResponseEntity<Endereco> deletaEndereco(@PathVariable(value = "usuarioId") Long usuarioId,
                                           @PathVariable(value = "enderecoId") Long enderecoId) {
        enderecoService.deletaEndereco(usuarioId, enderecoId);

        return ResponseEntity.ok().build();
    }

}
