package com.example.contasapagar.repository;

import com.example.contasapagar.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    List<Pessoa> findByNomeContainingIgnoreCase(String nome);
    List<Pessoa> findByPessoaTipo_Id(Long pessoaTipoId);
    Pessoa findByCpf(String cpf);
}
