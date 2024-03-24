package com.fiap.grupo44.ms_pedido.dominio.pedidoitems.dto.in;

import java.math.BigDecimal;

import org.springframework.beans.BeanUtils;

import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.entity.ItensPedido;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.entity.Pedido;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItensPedidoDTOin {
    private String descricao;
    private Long quantidade;
    private BigDecimal valorUnitario;
    private Long idProduto;
    
	public ItensPedido getEntity(Pedido pedido) {
       ItensPedido itensPedido = new ItensPedido();
       BeanUtils.copyProperties(this, itensPedido);
       itensPedido.setPedido(pedido);
       return itensPedido;
	}
}
