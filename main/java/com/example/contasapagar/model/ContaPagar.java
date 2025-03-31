package com.example.contasapagar.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbContasPagar", schema = "financeiro")
public class ContaPagar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conta_pagar_id")
    private Long id;

    @Column(nullable = false)
    private Double valor;

    @Column(name = "data_vencimento", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataVencimento;

    @Column(name = "data_pagamento")
    @Temporal(TemporalType.DATE)
    private Date dataPagamento;

    @ManyToOne
    @JoinColumn(name = "tipo_titulo_id", nullable = false)
    private TipoTitulo tipoTitulo;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Pessoa cliente;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private Pessoa fornecedor;

    @ManyToOne
    @JoinColumn(name = "emprestimo_id")
    private Emprestimo emprestimo;

    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private Usuario funcionario;

    @ManyToOne
    @JoinColumn(name = "viagem_id")
    private Viagem viagem;

    @ManyToOne
    @JoinColumn(name = "atualizado_por")
    private Usuario atualizadoPor;

    @Column(name = "atualizado_em")
    @Temporal(TemporalType.TIMESTAMP)
    private Date atualizadoEm;

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

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public TipoTitulo getTipoTitulo() {
        return tipoTitulo;
    }

    public void setTipoTitulo(TipoTitulo tipoTitulo) {
        this.tipoTitulo = tipoTitulo;
    }

    public Pessoa getCliente() {
        return cliente;
    }

    public void setCliente(Pessoa cliente) {
        this.cliente = cliente;
    }

    public Pessoa getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Pessoa fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Emprestimo getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }

    public Usuario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Usuario funcionario) {
        this.funcionario = funcionario;
    }

    public Viagem getViagem() {
        return viagem;
    }

    public void setViagem(Viagem viagem) {
        this.viagem = viagem;
    }

    public Usuario getAtualizadoPor() {
        return atualizadoPor;
    }

    public void setAtualizadoPor(Usuario atualizadoPor) {
        this.atualizadoPor = atualizadoPor;
    }

    public Date getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(Date atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }
    public ContaPagar (){

    }

    public ContaPagar(Double valor, Date dataVencimento, TipoTitulo tipoTitulo, Usuario atualizadoPor) {
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.tipoTitulo = tipoTitulo;
        this.atualizadoPor = atualizadoPor;
        this.atualizadoEm = new Date();
    }

    public boolean isPago() {
        return this.dataPagamento != null;
    }

    public boolean isVencido() {
        if (isPago()) {
            return false;
        }
        return this.dataVencimento.before(new Date());
    }

    public String getStatus() {
        if (isPago()) {
            return "Pago";
        } else if (isVencido()) {
            return "Vencido";
        } else {
            return "Pendente";
        }
    }

    @Override
    public String toString() {
        return "ContaPagar{" +
                "id=" + id +
                ", valor=" + valor +
                ", dataVencimento=" + dataVencimento +
                ", dataPagamento=" + dataPagamento +
                ", status=" + getStatus() +
                '}';
    }
}




