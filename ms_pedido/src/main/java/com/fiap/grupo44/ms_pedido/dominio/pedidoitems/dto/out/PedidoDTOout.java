package com.fiap.grupo44.ms_pedido.dominio.pedidoitems.dto.out;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.entity.Pedido;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.enumerations.EstadoPedido;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.enumerations.FormaPagamento;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDTOout {
	
	private Long id;
	private LocalDate dataPedido;
	private BigDecimal valorPedido;
	private LocalDate dataPagamento;
	private FormaPagamento formaPagamento;
    private EstadoPedido estadoPedido;
	private String emailUsuario;
	private List<ItensPedidoDTOout> itensPedido=new ArrayList<ItensPedidoDTOout>();
	
	public PedidoDTOout(Pedido pedido) {
		BeanUtils.copyProperties(pedido, this);
	}
}
