package com.upsaude.repository;

import com.upsaude.entity.IntegracaoGov;
import com.upsaude.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IntegracaoGovRepository extends JpaRepository<IntegracaoGov, UUID> {

    Optional<IntegracaoGov> findByPacienteId(UUID pacienteId);

    Optional<IntegracaoGov> findByUuidRnds(UUID uuidRnds);

    Page<IntegracaoGov> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    Page<IntegracaoGov> findByTenant(Tenant tenant, Pageable pageable);

    Page<IntegracaoGov> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
