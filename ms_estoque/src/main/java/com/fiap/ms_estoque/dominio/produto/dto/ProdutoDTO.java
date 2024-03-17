package com.fiap.ms_estoque.dominio.produto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.ms_estoque.dominio.produto.entity.Produto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {
    private Long id;
    @NotNull(message = "Descrição não pode ser nula")
    private String descricao;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Quantidade de produtos em estoque não deve ser nula")
    private Long quantidadeEstoque;
    @Positive(message = "Valor deve ser maior que zero")
    private Double valorUnitario;

    public ProdutoDTO(Produto entity){
        this.id = entity.getId();
        this.descricao = entity.getDescricao();
        this.quantidadeEstoque = entity.getQuantidadeEstoque();
        this.valorUnitario = entity.getValorUnitario();
    }
}
