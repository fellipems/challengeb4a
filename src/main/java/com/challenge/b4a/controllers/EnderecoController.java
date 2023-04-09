package com.challenge.b4a.controllers;

import com.challenge.b4a.domains.Endereco;
import com.challenge.b4a.dto.EnderecoDto;
import com.challenge.b4a.services.EnderecoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/enderecos")
@Api(value = "Endereço", description = "Informaçoes e cadastros de endereços", tags = "Endereço")
public class EnderecoController {

    private EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @PostMapping
    @ApiOperation("Permite a criação de um endereço com os dados informados no corpo da requisição")
    public ResponseEntity<Endereco> criaEndereco(@ApiParam(value = "Dados do endereço", required = true) @RequestBody Endereco endereco) {
        return ResponseEntity
                .status(CREATED)
                .body(enderecoService.criaEndereco(endereco));
    }

    @PostMapping("/{enderecoId}/{usuarioId}")
    @ApiOperation("Permite o vínculo de um endereço a uma pessoa informando o id de ambos")
    public ResponseEntity<Endereco> vinculaEnderecoNoUsuario(@ApiParam(value = "Id do usuário", required = true) @PathVariable(value = "usuarioId") Long usuarioId,
                                                             @ApiParam(value = "Id do endereço", required = true) @PathVariable(value = "enderecoId") Long enderecoId) {
        return ResponseEntity
                .status(CREATED)
                .body(enderecoService.vinculaEnderecoNoUsuario(usuarioId, enderecoId));
    }

    @PutMapping("/{enderecoId}")
    @ApiOperation("Atualiza o endereço conforme informações enviadas em nosso corpo da requisição")
    public ResponseEntity<Endereco> atualizaEndereco(@ApiParam(value = "Id do endereço", required = true) @PathVariable(value = "enderecoId") Long enderecoId,
                                                     @ApiParam(value = "Dados do novo endereço", required = true) @RequestBody EnderecoDto novoEndereco) {
        return ResponseEntity.ok(enderecoService.atualizaEndereco(enderecoId, novoEndereco));
    }

    @DeleteMapping("/{usuarioId}/{enderecoId}")
    @ApiOperation("Deleta um endereço vinculado a um usuário")
    public ResponseEntity<Endereco> deletaEndereco(@ApiParam(value = "Id do usuário", required = true) @PathVariable(value = "usuarioId") Long usuarioId,
                                                   @ApiParam(value = "Id do endereço", required = true)  @PathVariable(value = "enderecoId") Long enderecoId) {
        enderecoService.deletaEndereco(usuarioId, enderecoId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @ApiOperation("Lista todos os endereços cadastrados")
    public ResponseEntity<List<Endereco>> listaEnderecos() {
        return ResponseEntity.ok(enderecoService.listaTodosEnderecos());
    }

    @GetMapping("/{enderecoId}")
    @ApiOperation("Lista um endereço conforme informado o ID de cadastro")
    public ResponseEntity<Endereco> listaEnderecoById(@ApiParam(value = "Id do endereço", required = true)  @PathVariable Long enderecoId) {
        return ResponseEntity.ok(enderecoService.listaEnderecoById(enderecoId));
    }
}
