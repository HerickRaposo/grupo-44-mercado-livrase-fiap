package com.fiap.grupo44.ms_carrinho.dominio.item.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ItensPedidoDTOin {
	private String descricao;
	private Long quantidade;
	private Double valorUnitario;
	private Long idProduto;
}
