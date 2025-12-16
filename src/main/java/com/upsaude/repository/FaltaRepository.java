package com.upsaude.repository;

import com.upsaude.entity.Falta;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.TipoFaltaEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;
import java.util.Optional;

@Repository
public interface FaltaRepository extends JpaRepository<Falta, UUID> {

    Page<Falta> findByProfissionalIdOrderByDataFaltaDesc(UUID profissionalId, Pageable pageable);

    Page<Falta> findByMedicoIdOrderByDataFaltaDesc(UUID medicoId, Pageable pageable);

    Page<Falta> findByEstabelecimentoIdOrderByDataFaltaDesc(UUID estabelecimentoId, Pageable pageable);

    Page<Falta> findByTipoFaltaOrderByDataFaltaDesc(TipoFaltaEnum tipoFalta, Pageable pageable);

    Page<Falta> findByProfissionalIdAndDataFaltaBetweenOrderByDataFaltaDesc(
            UUID profissionalId, LocalDate dataInicio, LocalDate dataFim, Pageable pageable);

    Page<Falta> findByEstabelecimentoIdAndDataFaltaBetweenOrderByDataFaltaDesc(
            UUID estabelecimentoId, LocalDate dataInicio, LocalDate dataFim, Pageable pageable);

    Page<Falta> findByProfissionalIdAndTipoFaltaOrderByDataFaltaDesc(
            UUID profissionalId, TipoFaltaEnum tipoFalta, Pageable pageable);

    Page<Falta> findByTenantOrderByDataFaltaDesc(Tenant tenant, Pageable pageable);

    @Query("SELECT f FROM Falta f WHERE f.id = :id AND f.tenant.id = :tenantId")
    Optional<Falta> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT f FROM Falta f WHERE f.tenant.id = :tenantId")
    Page<Falta> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    Page<Falta> findByProfissionalIdAndTenantIdOrderByDataFaltaDesc(UUID profissionalId, UUID tenantId, Pageable pageable);

    Page<Falta> findByMedicoIdAndTenantIdOrderByDataFaltaDesc(UUID medicoId, UUID tenantId, Pageable pageable);

    Page<Falta> findByEstabelecimentoIdAndTenantIdOrderByDataFaltaDesc(UUID estabelecimentoId, UUID tenantId, Pageable pageable);

    Page<Falta> findByTipoFaltaAndTenantIdOrderByDataFaltaDesc(TipoFaltaEnum tipoFalta, UUID tenantId, Pageable pageable);

    Page<Falta> findByProfissionalIdAndDataFaltaBetweenAndTenantIdOrderByDataFaltaDesc(UUID profissionalId, LocalDate dataInicio, LocalDate dataFim, UUID tenantId, Pageable pageable);

    Page<Falta> findByEstabelecimentoIdAndDataFaltaBetweenAndTenantIdOrderByDataFaltaDesc(UUID estabelecimentoId, LocalDate dataInicio, LocalDate dataFim, UUID tenantId, Pageable pageable);
}
