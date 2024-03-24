package com.fiap.grupo44.ms_pedido.dominio.pedidoitems.dto.in;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItensPedidoDTOin {
    private String descricao;
    private Long quantidade;
    private BigDecimal valorUnitario;
}
