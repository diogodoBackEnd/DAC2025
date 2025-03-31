package com.example.contasapagar.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tbPessoaTipo", schema = "dominio")
public class PessoaTipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pessoa_tipo_id")
    private Long id;

    @Column(nullable = false, length = 200)
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

    @Override
    public String toString() {
        return "PessoaTipo{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}

    // Getters e Setters
