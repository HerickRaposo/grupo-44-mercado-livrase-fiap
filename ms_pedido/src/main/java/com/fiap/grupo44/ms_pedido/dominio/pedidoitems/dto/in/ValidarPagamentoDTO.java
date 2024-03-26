package com.fiap.grupo44.ms_pedido.dominio.pedidoitems.dto.in;

import java.time.LocalDate;

import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.enumerations.EstadoPedido;

public record ValidarPagamentoDTO(Long idPedido,LocalDate dataPagamento,EstadoPedido estadoPedido) {
}
