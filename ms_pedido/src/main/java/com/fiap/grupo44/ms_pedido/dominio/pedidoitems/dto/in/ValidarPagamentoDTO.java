package com.fiap.grupo44.ms_pedido.dominio.pedidoitems.dto.in;

import java.time.LocalDate;

public record ValidarPagamentoDTO(Long idPedido,LocalDate dataPagamento) {
}
