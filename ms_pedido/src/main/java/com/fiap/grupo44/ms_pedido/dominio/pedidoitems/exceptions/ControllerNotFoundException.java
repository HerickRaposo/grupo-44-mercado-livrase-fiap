package com.fiap.grupo44.ms_pedido.dominio.pedidoitems.exceptions;

public class ControllerNotFoundException  extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ControllerNotFoundException(String message){
        super(message);
    }
}
