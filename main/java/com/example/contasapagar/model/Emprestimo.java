package com.example.contasapagar.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbEmprestimos", schema = "financeiro")
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emprestimo_id")
    private Long id;

    @Column(nullable = false)
    private Double valor;

    @Column(name = "data_contratacao", nullable = false)
    private Date dataContratacao;

    @Column(name = "data_vencimento", nullable = false)
    private Date dataVencimento;

    @ManyToOne
    @JoinColumn(name = "credor_id", nullable = false)
    private Pessoa credor;

    // Construtores, getters, setters e métodos auxiliares
    public Emprestimo() {
    }

    public Emprestimo(Double valor, Date dataContratacao, Date dataVencimento, Pessoa credor) {
        this.valor = valor;
        this.dataContratacao = dataContratacao;
        this.dataVencimento = dataVencimento;
        this.credor = credor;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Date getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(Date dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Pessoa getCredor() {
        return credor;
    }

    public void setCredor(Pessoa credor) {
        this.credor = credor;
    }
}
