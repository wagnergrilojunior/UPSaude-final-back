package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Papeis;

public interface PapeisRepository extends JpaRepository<Papeis, UUID> {}
