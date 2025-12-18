package com.upsaude.repository.saude_publica.planejamento;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.saude_publica.planejamento.PlanejamentoFamiliar;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.enums.TipoMetodoContraceptivoEnum;

public interface PlanejamentoFamiliarRepository extends JpaRepository<PlanejamentoFamiliar, UUID> {

    Page<PlanejamentoFamiliar> findByEstabelecimentoIdOrderByDataInicioAcompanhamentoDesc(UUID estabelecimentoId, Pageable pageable);

    Page<PlanejamentoFamiliar> findByTenantOrderByDataInicioAcompanhamentoDesc(Tenant tenant, Pageable pageable);

    Optional<PlanejamentoFamiliar> findByPacienteIdAndAcompanhamentoAtivo(UUID pacienteId, Boolean ativo);

    Page<PlanejamentoFamiliar> findByAcompanhamentoAtivoAndEstabelecimentoId(Boolean ativo, UUID estabelecimentoId, Pageable pageable);

    Page<PlanejamentoFamiliar> findByMetodoAtualAndEstabelecimentoId(TipoMetodoContraceptivoEnum metodo, UUID estabelecimentoId, Pageable pageable);

    List<PlanejamentoFamiliar> findByPacienteIdOrderByDataInicioAcompanhamentoDesc(UUID pacienteId);

    @Query("SELECT p FROM PlanejamentoFamiliar p WHERE p.id = :id AND p.tenant.id = :tenantId")
    Optional<PlanejamentoFamiliar> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT p FROM PlanejamentoFamiliar p WHERE p.tenant.id = :tenantId")
    Page<PlanejamentoFamiliar> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    Page<PlanejamentoFamiliar> findByEstabelecimentoIdAndTenantIdOrderByDataInicioAcompanhamentoDesc(UUID estabelecimentoId, UUID tenantId, Pageable pageable);

    List<PlanejamentoFamiliar> findByPacienteIdAndTenantIdOrderByDataInicioAcompanhamentoDesc(UUID pacienteId, UUID tenantId);

    Page<PlanejamentoFamiliar> findByAcompanhamentoAtivoAndEstabelecimentoIdAndTenantId(Boolean ativo, UUID estabelecimentoId, UUID tenantId, Pageable pageable);

    Page<PlanejamentoFamiliar> findByMetodoAtualAndEstabelecimentoIdAndTenantId(TipoMetodoContraceptivoEnum metodo, UUID estabelecimentoId, UUID tenantId, Pageable pageable);

    Optional<PlanejamentoFamiliar> findByPacienteIdAndAcompanhamentoAtivoAndTenantId(UUID pacienteId, Boolean ativo, UUID tenantId);
}
