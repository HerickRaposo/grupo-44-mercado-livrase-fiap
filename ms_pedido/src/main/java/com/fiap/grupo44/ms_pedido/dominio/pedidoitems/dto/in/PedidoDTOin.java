package com.fiap.grupo44.ms_pedido.dominio.pedidoitems.dto.in;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.entity.Pedido;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.enumerations.EstadoPedido;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.enumerations.FormaPagamento;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDTOin {
	private FormaPagamento formaPagamento;
    private EstadoPedido estadoPedido;
	private String emailUsuario;
	private List<ItensPedidoDTOin> itensPedido=new ArrayList<ItensPedidoDTOin>(); 
	
	@JsonIgnore
	public Pedido getEntity() {
		Pedido pedido = new Pedido();
		BeanUtils.copyProperties(this, pedido);
		pedido.setEstadoPedido(EstadoPedido.AGUARDANDO_PAGAMENTO);
		return pedido;
	}
}
