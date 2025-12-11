package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Consultas;
import com.upsaude.entity.Tenant;

public interface ConsultasRepository extends JpaRepository<Consultas, UUID> {

    Page<Consultas> findByEstabelecimentoIdOrderByInformacoesDataConsultaDesc(UUID estabelecimentoId, Pageable pageable);

    Page<Consultas> findByTenantOrderByInformacoesDataConsultaDesc(Tenant tenant, Pageable pageable);

    Page<Consultas> findByEstabelecimentoIdAndTenantOrderByInformacoesDataConsultaDesc(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
