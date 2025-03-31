package com.example.contasapagar.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbViagens", schema = "financeiro")
public class Viagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "viagem_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String destino;

    @Column(name = "data_partida", nullable = false)
    private Date dataPartida;

    @Column(name = "data_retorno", nullable = false)
    private Date dataRetorno;

    @ManyToOne
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Usuario funcionario;

    // Construtores, getters, setters e métodos auxiliares
    public Viagem() {
    }

    public Viagem(String destino, Date dataPartida, Date dataRetorno, Usuario funcionario) {
        this.destino = destino;
        this.dataPartida = dataPartida;
        this.dataRetorno = dataRetorno;
        this.funcionario = funcionario;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Date getDataPartida() {
        return dataPartida;
    }

    public void setDataPartida(Date dataPartida) {
        this.dataPartida = dataPartida;
    }

    public Date getDataRetorno() {
        return dataRetorno;
    }

    public void setDataRetorno(Date dataRetorno) {
        this.dataRetorno = dataRetorno;
    }

    public Usuario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Usuario funcionario) {
        this.funcionario = funcionario;
    }
}