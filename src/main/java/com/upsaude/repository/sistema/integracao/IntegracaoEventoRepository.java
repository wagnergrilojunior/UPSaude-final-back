package com.upsaude.repository.sistema.integracao;

import com.upsaude.entity.sistema.integracao.IntegracaoEvento;
import com.upsaude.enums.SistemaIntegracaoEnum;
import com.upsaude.enums.StatusIntegracaoEventoEnum;
import com.upsaude.enums.TipoRecursoIntegracaoEnum;
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
public interface IntegracaoEventoRepository extends JpaRepository<IntegracaoEvento, UUID> {

    Optional<IntegracaoEvento> findFirstByEntidadeTipoAndEntidadeIdAndSistemaAndRecursoOrderByVersaoDesc(
            TipoRecursoIntegracaoEnum entidadeTipo,
            UUID entidadeId,
            SistemaIntegracaoEnum sistema,
            String recurso
    );

    List<IntegracaoEvento> findByEntidadeTipoAndEntidadeIdOrderByVersaoDesc(
            TipoRecursoIntegracaoEnum entidadeTipo,
            UUID entidadeId
    );

    Page<IntegracaoEvento> findByEntidadeTipoAndEntidadeId(
            TipoRecursoIntegracaoEnum entidadeTipo,
            UUID entidadeId,
            Pageable pageable
    );

    Page<IntegracaoEvento> findBySistemaAndStatus(
            SistemaIntegracaoEnum sistema,
            StatusIntegracaoEventoEnum status,
            Pageable pageable
    );

    @Query("SELECT e FROM IntegracaoEvento e WHERE e.status IN :statuses AND (e.proximaTentativaEm IS NULL OR e.proximaTentativaEm <= :agora) ORDER BY e.createdAt ASC")
    List<IntegracaoEvento> findPendentesParaProcessar(
            @Param("statuses") List<StatusIntegracaoEventoEnum> statuses,
            @Param("agora") OffsetDateTime agora,
            Pageable pageable
    );

    @Query("SELECT e FROM IntegracaoEvento e WHERE e.entidadeTipo = :entidadeTipo AND e.entidadeId = :entidadeId AND e.sistema = :sistema AND e.recurso = :recurso ORDER BY e.versao DESC")
    List<IntegracaoEvento> findByEntidadeESistemaRecurso(
            @Param("entidadeTipo") TipoRecursoIntegracaoEnum entidadeTipo,
            @Param("entidadeId") UUID entidadeId,
            @Param("sistema") SistemaIntegracaoEnum sistema,
            @Param("recurso") String recurso
    );

    @Query("SELECT MAX(e.versao) FROM IntegracaoEvento e WHERE e.entidadeTipo = :entidadeTipo AND e.entidadeId = :entidadeId AND e.sistema = :sistema AND e.recurso = :recurso")
    Integer findMaxVersao(
            @Param("entidadeTipo") TipoRecursoIntegracaoEnum entidadeTipo,
            @Param("entidadeId") UUID entidadeId,
            @Param("sistema") SistemaIntegracaoEnum sistema,
            @Param("recurso") String recurso
    );
}
