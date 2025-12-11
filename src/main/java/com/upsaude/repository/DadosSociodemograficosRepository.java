package com.upsaude.repository;

import com.upsaude.entity.DadosSociodemograficos;
import com.upsaude.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DadosSociodemograficosRepository extends JpaRepository<DadosSociodemograficos, UUID> {

    Optional<DadosSociodemograficos> findByPacienteId(UUID pacienteId);

    Page<DadosSociodemograficos> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    Page<DadosSociodemograficos> findByTenant(Tenant tenant, Pageable pageable);

    Page<DadosSociodemograficos> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
