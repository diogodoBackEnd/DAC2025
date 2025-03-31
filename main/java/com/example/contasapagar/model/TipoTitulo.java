package com.example.contasapagar.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tbTipoTitulo", schema = "financeiro")
public class TipoTitulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipo_titulo_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String descricao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String toString() {
        return "TipoTitulo{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}

    // Getters e Setters

