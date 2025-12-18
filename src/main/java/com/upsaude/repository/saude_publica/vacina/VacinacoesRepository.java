package com.upsaude.repository.saude_publica.vacina;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.sistema.Tenant;
import com.upsaude.entity.saude_publica.vacina.Vacinacoes;

public interface VacinacoesRepository extends JpaRepository<Vacinacoes, UUID> {

    Page<Vacinacoes> findByEstabelecimentoIdOrderByDataAplicacaoDesc(UUID estabelecimentoId, Pageable pageable);

    Page<Vacinacoes> findByTenantOrderByDataAplicacaoDesc(Tenant tenant, Pageable pageable);

    Page<Vacinacoes> findByEstabelecimentoIdAndTenantOrderByDataAplicacaoDesc(UUID estabelecimentoId, Tenant tenant, Pageable pageable);

    @Query("SELECT v FROM Vacinacoes v WHERE v.id = :id AND v.tenant.id = :tenantId")
    Optional<Vacinacoes> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT v FROM Vacinacoes v WHERE v.tenant.id = :tenantId ORDER BY v.dataAplicacao DESC")
    Page<Vacinacoes> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    Page<Vacinacoes> findByEstabelecimentoIdAndTenantIdOrderByDataAplicacaoDesc(UUID estabelecimentoId, UUID tenantId, Pageable pageable);

    Page<Vacinacoes> findByPacienteIdAndTenantIdOrderByDataAplicacaoDesc(UUID pacienteId, UUID tenantId, Pageable pageable);

    Page<Vacinacoes> findByVacinaIdAndTenantIdOrderByDataAplicacaoDesc(UUID vacinaId, UUID tenantId, Pageable pageable);

    Page<Vacinacoes> findByDataAplicacaoBetweenAndTenantIdOrderByDataAplicacaoDesc(OffsetDateTime inicio, OffsetDateTime fim, UUID tenantId, Pageable pageable);
}
