package com.fiap.grupo44.ms_pedido.dominio.pedidoitems.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.enumerations.EstadoPedido;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.enumerations.FormaPagamento;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name="TB_ITENS_PEDIDO")
@Getter
@Setter
public class ItensPedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	@Column(name = "DESCRICAO",length = 300)
    private String descricao;
	@Column(name = "QUANTIDADE")
    private Long quantidade;
	@Column(name = "VALOR_UNITARIO")
    private BigDecimal valorUnitario;
    @ManyToOne
    @JoinColumn(name="PEDIDO_ID")
    private Pedido pedido;
}
