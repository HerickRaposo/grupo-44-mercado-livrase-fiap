package com.fiap.grupo44.ms_carrinho.dominio.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private String tocken;
    private String email;
    private String role;
}
