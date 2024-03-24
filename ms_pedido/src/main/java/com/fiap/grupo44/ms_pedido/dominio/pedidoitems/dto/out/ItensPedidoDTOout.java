package com.fiap.grupo44.ms_pedido.dominio.pedidoitems.dto.out;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ItensPedidoDTOout {
	private Long id;
    private String descricao;
    private Long quantidade;
    private BigDecimal valorUnitario;
    private Long pedido;
}
