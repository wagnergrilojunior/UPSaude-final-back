package com.upsaude.repository.saude_publica.vacina;

import java.util.UUID;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.saude_publica.vacina.EstoquesVacina;
import com.upsaude.entity.sistema.Tenant;

public interface EstoquesVacinaRepository extends JpaRepository<EstoquesVacina, UUID> {

    Page<EstoquesVacina> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    Page<EstoquesVacina> findByTenant(Tenant tenant, Pageable pageable);

    Page<EstoquesVacina> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);

    @Query("SELECT e FROM EstoquesVacina e WHERE e.id = :id AND e.tenant.id = :tenantId")
    Optional<EstoquesVacina> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT e FROM EstoquesVacina e WHERE e.tenant.id = :tenantId")
    Page<EstoquesVacina> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);
}
