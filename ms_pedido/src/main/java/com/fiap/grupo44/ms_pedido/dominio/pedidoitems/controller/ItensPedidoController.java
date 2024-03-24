package com.fiap.grupo44.ms_pedido.dominio.pedidoitems.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping(value = "/itens-pedido",produces = {"application/json"})
@Tag(name = "API Gestão de Itens de um pedido")
public class ItensPedidoController {

}
