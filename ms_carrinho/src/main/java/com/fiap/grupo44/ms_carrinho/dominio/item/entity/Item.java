package com.fiap.grupo44.ms_carrinho.dominio.item.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idProduto;
    @Column(name = "email_usuario")
    private String emailUsuario;
    private Long quantidade;
    private Double valor;
    private boolean compraFechada;
    private String descricao;
}
