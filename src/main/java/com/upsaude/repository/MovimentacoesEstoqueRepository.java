package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.MovimentacoesEstoque;

public interface MovimentacoesEstoqueRepository extends JpaRepository<MovimentacoesEstoque, UUID> {}
