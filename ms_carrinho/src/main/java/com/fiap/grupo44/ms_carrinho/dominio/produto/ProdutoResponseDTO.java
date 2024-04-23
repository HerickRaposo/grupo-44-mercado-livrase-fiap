package com.fiap.grupo44.ms_carrinho.dominio.produto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoResponseDTO {
    private Long id;
    private String descricao;
    private Long quantidadeEstoque;
    private Double valorUnitario;

}
