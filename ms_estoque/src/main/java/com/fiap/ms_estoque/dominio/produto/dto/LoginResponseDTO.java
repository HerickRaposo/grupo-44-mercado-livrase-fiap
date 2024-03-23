package com.fiap.ms_estoque.dominio.produto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

//TODO: Verificar comunicação com outros ms
@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private String tocken;
    private String email;
    private String role;
}
