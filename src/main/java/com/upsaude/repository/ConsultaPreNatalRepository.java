package com.upsaude.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.ConsultaPreNatal;
import com.upsaude.entity.Tenant;

public interface ConsultaPreNatalRepository extends JpaRepository<ConsultaPreNatal, UUID> {

    Page<ConsultaPreNatal> findByEstabelecimentoIdOrderByDataConsultaDesc(UUID estabelecimentoId, Pageable pageable);

    Page<ConsultaPreNatal> findByTenantOrderByDataConsultaDesc(Tenant tenant, Pageable pageable);

    List<ConsultaPreNatal> findByPreNatalIdOrderByDataConsultaAsc(UUID preNatalId);

    long countByPreNatalId(UUID preNatalId);
}
