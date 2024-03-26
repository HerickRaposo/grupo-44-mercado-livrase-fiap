package com.fiap.grupo44.ms_carrinho.dominio.item.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDTOout {
	private Long id;
	private LocalDate dataPedido;
	private BigDecimal valorPedido;
    @JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDate dataPagamento;
	private FormaPagamento formaPagamento;
    private EstadoPedido estadoPedido;
	private String emailUsuario;
	
	private List<ItensPedidoDTOout> itensPedido=new ArrayList<ItensPedidoDTOout>();
}
