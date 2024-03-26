package com.fiap.grupo44.ms_carrinho.dominio.item.dto;

import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FecharPedidoDTO {
    private String emailUsuario;
    @Enumerated
    private FormaPagamento formaPagamento;
}
