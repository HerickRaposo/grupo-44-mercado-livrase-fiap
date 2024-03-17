//package com.fiap.ms_user.dominio.usuario.dto;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fiap.ms_user.dominio.usuario.entity.Usuario;
//import com.fiap.ms_user.dominio.usuario.enumeration.UserRole;
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotNull;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.hibernate.validator.constraints.br.CPF;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class UsuarioDTO {
//    private Long id;
//    @NotNull(message = "Nome não pode ser nulo")
//    private String nome;
//    @Email(message = "Email invalido")
//    @NotNull(message = "Email não pode ser nulo")
//    private String email;
//    @CPF(message = "CPF invalido")
//    @NotNull(message = "CPF não pode ser nulo")
//    private String cpf;
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    @NotNull(message = "Senha não pode ser nulo")
//    private String senha;
//    @NotNull(message = "acesso não pode ser nulo")
//    private UserRole role;
//
//    public UsuarioDTO(Usuario entity){
//        this.id = entity.getId();
//        this.nome = entity.getNome();
//        this.email = entity.getEmail();
//        this.cpf = entity.getCpf();
//        this.senha = entity.getPassword();
//        this.role = entity.getRole();
//    }
//}
