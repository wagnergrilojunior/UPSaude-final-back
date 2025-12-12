package com.upsaude.repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.AcaoPromocaoPrevencao;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.TipoAcaoPromocaoSaudeEnum;

public interface AcaoPromocaoPrevencaoRepository extends JpaRepository<AcaoPromocaoPrevencao, UUID> {

    Page<AcaoPromocaoPrevencao> findByEstabelecimentoIdOrderByDataInicioDesc(UUID estabelecimentoId, Pageable pageable);

    Page<AcaoPromocaoPrevencao> findByTenantOrderByDataInicioDesc(Tenant tenant, Pageable pageable);

    Page<AcaoPromocaoPrevencao> findByTipoAcaoAndEstabelecimentoIdOrderByDataInicioDesc(TipoAcaoPromocaoSaudeEnum tipo, UUID estabelecimentoId, Pageable pageable);

    Page<AcaoPromocaoPrevencao> findByStatusAcaoAndEstabelecimentoIdOrderByDataInicioDesc(String status, UUID estabelecimentoId, Pageable pageable);

    Page<AcaoPromocaoPrevencao> findByDataInicioBetweenAndEstabelecimentoIdOrderByDataInicioDesc(LocalDate dataInicio, LocalDate dataFim, UUID estabelecimentoId, Pageable pageable);

    Page<AcaoPromocaoPrevencao> findByAcaoContinuaAndEstabelecimentoIdOrderByDataInicioDesc(Boolean continua, UUID estabelecimentoId, Pageable pageable);

    Page<AcaoPromocaoPrevencao> findByProfissionalResponsavelIdOrderByDataInicioDesc(UUID profissionalId, Pageable pageable);

    @Query("SELECT a FROM AcaoPromocaoPrevencao a WHERE a.id = :id AND a.tenant.id = :tenantId")
    Optional<AcaoPromocaoPrevencao> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @EntityGraph(attributePaths = {
        "profissionalResponsavel",
        "equipeSaude",
        "profissionaisParticipantes",
        "estabelecimento"
    })
    @Query("SELECT a FROM AcaoPromocaoPrevencao a WHERE a.id = :id AND a.tenant.id = :tenantId")
    Optional<AcaoPromocaoPrevencao> findByIdCompletoAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT a FROM AcaoPromocaoPrevencao a WHERE a.tenant.id = :tenantId")
    Page<AcaoPromocaoPrevencao> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT a FROM AcaoPromocaoPrevencao a WHERE a.estabelecimento.id = :estabelecimentoId AND a.tenant.id = :tenantId ORDER BY a.dataInicio DESC")
    Page<AcaoPromocaoPrevencao> findByEstabelecimentoIdAndTenantIdOrderByDataInicioDesc(
            @Param("estabelecimentoId") UUID estabelecimentoId,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);

    @Query("SELECT a FROM AcaoPromocaoPrevencao a WHERE a.profissionalResponsavel.id = :profissionalId AND a.tenant.id = :tenantId ORDER BY a.dataInicio DESC")
    Page<AcaoPromocaoPrevencao> findByProfissionalResponsavelIdAndTenantIdOrderByDataInicioDesc(
            @Param("profissionalId") UUID profissionalId,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);

    @Query("SELECT a FROM AcaoPromocaoPrevencao a WHERE a.statusAcao = :status AND a.estabelecimento.id = :estabelecimentoId AND a.tenant.id = :tenantId ORDER BY a.dataInicio DESC")
    Page<AcaoPromocaoPrevencao> findByStatusAcaoAndEstabelecimentoIdAndTenantIdOrderByDataInicioDesc(
            @Param("status") String status,
            @Param("estabelecimentoId") UUID estabelecimentoId,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);

    @Query("SELECT a FROM AcaoPromocaoPrevencao a WHERE a.acaoContinua = :continua AND a.estabelecimento.id = :estabelecimentoId AND a.tenant.id = :tenantId ORDER BY a.dataInicio DESC")
    Page<AcaoPromocaoPrevencao> findByAcaoContinuaAndEstabelecimentoIdAndTenantIdOrderByDataInicioDesc(
            @Param("continua") Boolean continua,
            @Param("estabelecimentoId") UUID estabelecimentoId,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);
}
