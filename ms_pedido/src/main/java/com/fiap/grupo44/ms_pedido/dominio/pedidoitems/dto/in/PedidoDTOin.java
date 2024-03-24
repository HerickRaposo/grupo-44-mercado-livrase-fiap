package com.fiap.grupo44.ms_pedido.dominio.pedidoitems.dto.in;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.enumerations.EstadoPedido;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.enumerations.FormaPagamento;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDTOin {
	private LocalDate dataPedido;
	private BigDecimal valorPedido;
	private LocalDate dataPagamento;
	private FormaPagamento formaPagamento;
    private EstadoPedido estadoPedido;
    private Long idProduto;
	private String emailUsuario;
}
