package com.example.contasapagar.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbPessoas", schema = "cadastro")
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pessoa_id")
    private Long id;

    @Column(nullable = false, length = 200)
    private String nome;

    @Column(unique = true, length = 14)
    private String cpf;

    @Column
    private Date nascimento;

    @Column(length = 20)
    private String telefone;

    @ManyToOne
    @JoinColumn(name = "pessoa_tipo_id", nullable = false)
    private PessoaTipo pessoaTipo;

    // Construtores
    public Pessoa() {
    }

    public Pessoa(String nome, String cpf, PessoaTipo pessoaTipo) {
        this.nome = nome;
        this.cpf = cpf;
        this.pessoaTipo = pessoaTipo;
    }

    // Getters e Setters
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public PessoaTipo getPessoaTipo() {
        return pessoaTipo;
    }

    public void setPessoaTipo(PessoaTipo pessoaTipo) {
        this.pessoaTipo = pessoaTipo;
    }

    // Métodos auxiliares
    public boolean isCliente() {
        return "Cliente".equalsIgnoreCase(pessoaTipo.getDescricao());
    }

    public boolean isFornecedor() {
        return "Fornecedor".equalsIgnoreCase(pessoaTipo.getDescricao());
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", tipo='" + pessoaTipo.getDescricao() + '\'' +
                '}';
    }
}

