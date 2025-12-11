package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.MovimentacoesEstoque;
import com.upsaude.entity.Tenant;

public interface MovimentacoesEstoqueRepository extends JpaRepository<MovimentacoesEstoque, UUID> {

    Page<MovimentacoesEstoque> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    Page<MovimentacoesEstoque> findByTenant(Tenant tenant, Pageable pageable);

    Page<MovimentacoesEstoque> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
