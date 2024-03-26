package com.fiap.grupo44.ms_carrinho.dominio.item.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PedidoDTOin {
	private FormaPagamento formaPagamento;
	private EstadoPedido estadoPedido;
	private String emailUsuario;
	private List<ItensPedidoDTOin> itensPedido = new ArrayList<ItensPedidoDTOin>();
}
