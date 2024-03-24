package com.fiap.grupo44.ms_pedido.dominio.pedidoitems.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.enumerations.EstadoPedido;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.enumerations.FormaPagamento;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name="TB_PEDIDO")
@Getter
@Setter
public class Pedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "DATA_PEDIDO")
	private LocalDate dataPedido=LocalDate.now();
	
	@Column(name = "VALOR_PEDIDO")
	private BigDecimal valorPedido;
	
	
	@Column(name = "DATA_PAGAMENTO")
	private LocalDate dataPagamento;
	
    @Column(name = "FORMA_PAGAMENTO")
	@Enumerated(EnumType.STRING)
	private FormaPagamento formaPagamento;
	
    @Column(name = "ESTADO_PEDIDO")
	@Enumerated(EnumType.STRING) 
    private EstadoPedido estadoPedido=EstadoPedido.AGUARDANDO_PAGAMENTO;
    
    @Column(name = "email_usuario",length = 200)
	private String emailUsuario;
}
