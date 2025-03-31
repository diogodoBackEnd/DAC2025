package com.example.contasapagar.repository;

import com.example.contasapagar.model.ContaPagar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ContaPagarRepository extends JpaRepository<ContaPagar, Long> {

    // Encontra contas por data de vencimento
    List<ContaPagar> findByDataVencimento(Date dataVencimento);

    // Encontra contas com vencimento entre duas datas
    List<ContaPagar> findByDataVencimentoBetween(Date startDate, Date endDate);

    // Encontra contas pagas
    List<ContaPagar> findByDataPagamentoIsNotNull();

    // Encontra contas pendentes
    List<ContaPagar> findByDataPagamentoIsNull();

    // Encontra contas por tipo de título
    List<ContaPagar> findByTipoTitulo_Id(Long tipoTituloId);

    // Encontra contas por fornecedor
    List<ContaPagar> findByFornecedor_Id(Long fornecedorId);

    // Consulta personalizada para relatório
    @Query("SELECT cp FROM ContaPagar cp WHERE cp.valor > :valorMinimo ORDER BY cp.dataVencimento")
    List<ContaPagar> findContasComValorMaiorQue(Double valorMinimo);

    // Consulta para contas vencidas
    @Query("SELECT cp FROM ContaPagar cp WHERE cp.dataVencimento < CURRENT_DATE AND cp.dataPagamento IS NULL")
    List<ContaPagar> findContasVencidas();
}