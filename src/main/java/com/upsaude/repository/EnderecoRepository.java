package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, UUID> {}
