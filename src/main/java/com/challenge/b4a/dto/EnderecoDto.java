package com.challenge.b4a.dto;

import com.challenge.b4a.domains.Usuario;
import io.swagger.annotations.ApiModelProperty;

public class EnderecoDto {
    @ApiModelProperty(value = "Número", required = true, example = "103")
    private Long numero;

    @ApiModelProperty(value = "Complemento", required = true, example = "APTO 111")
    private String complemento;

    @ApiModelProperty(value = "Bairro", required = true, example = "Florinda")
    private String bairro;

    @ApiModelProperty(value = "Cidade", required = true, example = "São José")
    private String cidade;

    @ApiModelProperty(value = "Estado", required = true, example = "SP")
    private String estado;

    @ApiModelProperty(value = "CEP", required = true, example = "12345678")
    private String cep;

    @ApiModelProperty(value = "Usuário", required = true, example = "Objeto Usuário")
    private Usuario usuario;

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
