package com.fiap.grupo44.ms_carrinho.dominio.item.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.grupo44.ms_carrinho.dominio.item.entity.Item;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {
    private Long id;
    @NotNull(message = "ID do produto não pode ser nulo")
    private Long idProduto;
    private String emailUsuario;
    @NotNull(message = "Quantidade não pode ser nula")
    private Long quantidade;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double valor;
    private String descricao;

    public ItemDTO(Item entity){
        this.id = entity.getId();
        this.idProduto = entity.getIdProduto();
        this.emailUsuario = entity.getEmailUsuario();
        this.quantidade = entity.getQuantidade();;
        this.valor = entity.getValor();
    }

}

