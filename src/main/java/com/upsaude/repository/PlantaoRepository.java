package com.upsaude.repository;

import com.upsaude.entity.Plantao;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.TipoPlantaoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlantaoRepository extends JpaRepository<Plantao, UUID> {

    Page<Plantao> findByProfissionalIdOrderByDataHoraInicioDesc(UUID profissionalId, Pageable pageable);

    Page<Plantao> findByMedicoIdOrderByDataHoraInicioDesc(UUID medicoId, Pageable pageable);

    Page<Plantao> findByEstabelecimentoIdOrderByDataHoraInicioDesc(UUID estabelecimentoId, Pageable pageable);

    Page<Plantao> findByTipoPlantaoOrderByDataHoraInicioDesc(TipoPlantaoEnum tipoPlantao, Pageable pageable);

    Page<Plantao> findByProfissionalIdAndDataHoraInicioBetweenOrderByDataHoraInicioAsc(
            UUID profissionalId, OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    Page<Plantao> findByEstabelecimentoIdAndDataHoraInicioBetweenOrderByDataHoraInicioAsc(
            UUID estabelecimentoId, OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    List<Plantao> findByProfissionalIdAndDataHoraInicioLessThanEqualAndDataHoraFimGreaterThanEqual(
            UUID profissionalId, OffsetDateTime agora, OffsetDateTime agora2);

    Page<Plantao> findByTenantOrderByDataHoraInicioDesc(Tenant tenant, Pageable pageable);

    @Query("SELECT p FROM Plantao p WHERE p.id = :id AND p.tenant.id = :tenantId")
    Optional<Plantao> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT p FROM Plantao p WHERE p.tenant.id = :tenantId")
    Page<Plantao> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    Page<Plantao> findByProfissionalIdAndTenantIdOrderByDataHoraInicioDesc(UUID profissionalId, UUID tenantId, Pageable pageable);

    Page<Plantao> findByMedicoIdAndTenantIdOrderByDataHoraInicioDesc(UUID medicoId, UUID tenantId, Pageable pageable);

    Page<Plantao> findByEstabelecimentoIdAndTenantIdOrderByDataHoraInicioDesc(UUID estabelecimentoId, UUID tenantId, Pageable pageable);

    Page<Plantao> findByTipoPlantaoAndTenantIdOrderByDataHoraInicioDesc(TipoPlantaoEnum tipoPlantao, UUID tenantId, Pageable pageable);

    Page<Plantao> findByProfissionalIdAndDataHoraInicioBetweenAndTenantIdOrderByDataHoraInicioAsc(
        UUID profissionalId, OffsetDateTime dataInicio, OffsetDateTime dataFim, UUID tenantId, Pageable pageable);

    Page<Plantao> findByEstabelecimentoIdAndDataHoraInicioBetweenAndTenantIdOrderByDataHoraInicioAsc(
        UUID estabelecimentoId, OffsetDateTime dataInicio, OffsetDateTime dataFim, UUID tenantId, Pageable pageable);

    List<Plantao> findByProfissionalIdAndTenantIdAndDataHoraInicioLessThanEqualAndDataHoraFimGreaterThanEqual(
        UUID profissionalId, UUID tenantId, OffsetDateTime agora, OffsetDateTime agora2);
}
