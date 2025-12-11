package com.upsaude.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.ConsultaPuericultura;
import com.upsaude.entity.Tenant;

public interface ConsultaPuericulturaRepository extends JpaRepository<ConsultaPuericultura, UUID> {

    Page<ConsultaPuericultura> findByEstabelecimentoIdOrderByDataConsultaDesc(UUID estabelecimentoId, Pageable pageable);

    Page<ConsultaPuericultura> findByTenantOrderByDataConsultaDesc(Tenant tenant, Pageable pageable);

    List<ConsultaPuericultura> findByPuericulturaIdOrderByDataConsultaAsc(UUID puericulturaId);

    long countByPuericulturaId(UUID puericulturaId);
}
