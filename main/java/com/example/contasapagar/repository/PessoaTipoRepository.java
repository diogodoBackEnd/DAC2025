package com.example.contasapagar.repository;

import com.example.contasapagar.model.PessoaTipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaTipoRepository extends JpaRepository<PessoaTipo, Long> {

}
