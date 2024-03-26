package com.fiap.grupo44.ms_carrinho.dominio.item.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PedidoDTOin {
	private FormaPagamento formaPagamento;
    @JsonInclude(JsonInclude.Include.NON_NULL)
	private EstadoPedido estadoPedido;
	private String emailUsuario;
	private List<ItensPedidoDTOin> itensPedido = new ArrayList<ItensPedidoDTOin>();
}
