package com.fiap.grupo44.ms_pedido.dominio.pedidoitems.dto.out;

import java.math.BigDecimal;

import org.springframework.beans.BeanUtils;

import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.entity.ItensPedido;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ItensPedidoDTOout {
	private Long id;
    private String descricao;
    private Long quantidade;
    private BigDecimal valorUnitario;
	private Long idProduto;

    public ItensPedidoDTOout() {
	}
    
    public ItensPedidoDTOout(ItensPedido itemPedido) {
		BeanUtils.copyProperties(itemPedido, this);
	}
}
