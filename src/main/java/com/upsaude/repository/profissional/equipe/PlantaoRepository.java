package com.upsaude.repository.profissional.equipe;

import com.upsaude.entity.profissional.equipe.Plantao;
import com.upsaude.enums.TipoPlantaoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.UUID;

@Repository
public interface PlantaoRepository extends JpaRepository<Plantao, UUID> {

    @Query("SELECT p FROM Plantao p WHERE p.id = :id AND p.tenant.id = :tenantId")
    java.util.Optional<Plantao> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT p FROM Plantao p WHERE p.tenant.id = :tenantId")
    Page<Plantao> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT p FROM Plantao p WHERE p.profissional.id = :profissionalId AND p.tenant.id = :tenantId ORDER BY p.dataHoraInicio DESC")
    Page<Plantao> findByProfissionalIdAndTenantIdOrderByDataHoraInicioDesc(@Param("profissionalId") UUID profissionalId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT p FROM Plantao p WHERE p.medico.id = :medicoId AND p.tenant.id = :tenantId ORDER BY p.dataHoraInicio DESC")
    Page<Plantao> findByMedicoIdAndTenantIdOrderByDataHoraInicioDesc(@Param("medicoId") UUID medicoId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT p FROM Plantao p WHERE p.estabelecimento.id = :estabelecimentoId AND p.tenant.id = :tenantId ORDER BY p.dataHoraInicio DESC")
    Page<Plantao> findByEstabelecimentoIdAndTenantIdOrderByDataHoraInicioDesc(@Param("estabelecimentoId") UUID estabelecimentoId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT p FROM Plantao p WHERE p.tipoPlantao = :tipoPlantao AND p.tenant.id = :tenantId ORDER BY p.dataHoraInicio DESC")
    Page<Plantao> findByTipoPlantaoAndTenantIdOrderByDataHoraInicioDesc(@Param("tipoPlantao") TipoPlantaoEnum tipoPlantao, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT p FROM Plantao p WHERE p.dataHoraInicio BETWEEN :dataInicio AND :dataFim AND p.tenant.id = :tenantId ORDER BY p.dataHoraInicio DESC")
    Page<Plantao> findByDataHoraInicioBetweenAndTenantIdOrderByDataHoraInicioDesc(@Param("dataInicio") OffsetDateTime dataInicio, @Param("dataFim") OffsetDateTime dataFim, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT p FROM Plantao p WHERE p.dataHoraInicio <= :agora AND (p.dataHoraFim IS NULL OR p.dataHoraFim >= :agora) AND p.tenant.id = :tenantId ORDER BY p.dataHoraInicio DESC")
    Page<Plantao> findEmAndamentoAndTenantIdOrderByDataHoraInicioDesc(@Param("agora") OffsetDateTime agora, @Param("tenantId") UUID tenantId, Pageable pageable);
}
