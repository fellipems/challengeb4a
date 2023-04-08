package com.challenge.b4a.controllers;

import com.challenge.b4a.domains.Endereco;
import com.challenge.b4a.dto.EnderecoDto;
import com.challenge.b4a.services.EnderecoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/usuarios/{usuarioId}/enderecos")
public class EnderecoController {

    private EnderecoService enderecoService;

    private EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @PostMapping
    public ResponseEntity<Endereco> criaEndereco(@PathVariable(value = "usuarioId") Long usuarioId, @RequestBody Endereco endereco) {
        return ResponseEntity
                .status(CREATED)
                .body(enderecoService.criaEndereco(usuarioId, endereco));
    }

    @PutMapping("/{enderecoId}")
    public ResponseEntity<Endereco> atualizaEndereco(@PathVariable(value = "usuarioId") Long usuarioId,
                                 @PathVariable(value = "enderecoId") Long enderecoId,
                                 @RequestBody EnderecoDto novoEndereco) {
        return ResponseEntity.ok(enderecoService.atualizaEndereco(usuarioId, enderecoId, novoEndereco));
    }

    @DeleteMapping("/{enderecoId}")
    public ResponseEntity<Endereco> deletaEndereco(@PathVariable(value = "usuarioId") Long usuarioId,
                                           @PathVariable(value = "enderecoId") Long enderecoId) {
        enderecoService.deletaEndereco(usuarioId, enderecoId);

        return ResponseEntity.noContent().build();
    }

}
