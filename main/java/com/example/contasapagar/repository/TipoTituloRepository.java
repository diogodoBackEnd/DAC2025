package com.example.contasapagar.repository;

import com.example.contasapagar.model.TipoTitulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoTituloRepository extends JpaRepository<TipoTitulo, Long> {
    List<TipoTitulo> findByDescricaoContainingIgnoreCase(String descricao);
}