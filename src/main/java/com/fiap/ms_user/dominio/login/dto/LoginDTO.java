package com.fiap.ms_user.dominio.login.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginDTO {
    @Email(message = "Email formatado incorretamente")
    @NotNull(message = "E-mail não pode ser nulo")
    private String email;
    @NotNull(message = "Password não pode ser nulo")
    private String senha;
}
