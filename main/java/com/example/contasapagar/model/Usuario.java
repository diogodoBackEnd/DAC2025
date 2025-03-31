package com.example.contasapagar.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbUsuarios", schema = "seguranca")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long id;

    @Column(nullable = false, length = 200)
    private String nome;

    @Column(unique = true, length = 50, nullable = false)
    private String login;

    @Column(nullable = false, length = 255)
    private String senha;

    @Column(name = "atualizado_em")
    private Date atualizadoEm;

    @ManyToOne
    @JoinColumn(name = "atualizado_por")
    private Usuario atualizadoPor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(Date atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }

    public Usuario getAtualizadoPor() {
        return atualizadoPor;
    }

    public void setAtualizadoPor(Usuario atualizadoPor) {
        this.atualizadoPor = atualizadoPor;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", login='" + login + '\'' +
                '}';
    }
}
    // Getters e Setters

