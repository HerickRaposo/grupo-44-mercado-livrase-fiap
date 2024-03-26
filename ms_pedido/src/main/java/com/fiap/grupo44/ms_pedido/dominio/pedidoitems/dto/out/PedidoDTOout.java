package com.fiap.grupo44.ms_pedido.dominio.pedidoitems.dto.out;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.entity.ItensPedido;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.entity.Pedido;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.enumerations.EstadoPedido;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.enumerations.FormaPagamento;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.service.ItensPedidoService;

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
	
	public PedidoDTOout() {
	} 
	
	public PedidoDTOout(Pedido pedido) {
		BeanUtils.copyProperties(pedido, this);
	} 
	
	
	public PedidoDTOout(Pedido pedido,ItensPedidoService itensPedidoService) {
		BeanUtils.copyProperties(pedido, this);
		List<ItensPedido> itensPedido = itensPedidoService.buscarItensPorPedido(pedido.getId());
		ItensPedidoDTOout itensPedidoDTOout;
		for (ItensPedido o : itensPedido) {
			itensPedidoDTOout = new ItensPedidoDTOout(o);
			this.itensPedido.add(itensPedidoDTOout);
		}
	} 
}
