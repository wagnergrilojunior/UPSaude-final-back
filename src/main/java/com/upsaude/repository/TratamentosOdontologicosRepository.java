package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.TratamentosOdontologicos;

public interface TratamentosOdontologicosRepository extends JpaRepository<TratamentosOdontologicos, UUID> {}
