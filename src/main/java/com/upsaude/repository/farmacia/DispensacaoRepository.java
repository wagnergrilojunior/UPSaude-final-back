package com.upsaude.repository.farmacia;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import com.upsaude.entity.farmacia.Dispensacao;
import com.upsaude.entity.farmacia.Receita;

public interface DispensacaoRepository extends JpaRepository<Dispensacao, UUID> {

    @Query("SELECT d FROM Dispensacao d WHERE d.farmacia.id = :farmaciaId")
    Page<Dispensacao> findByFarmaciaId(@Param("farmaciaId") UUID farmaciaId, Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {
            "paciente",
            "farmacia",
            "receita",
            "profissionalSaude",
            "itens",
            "itens.sigtapProcedimento",
            "itens.receitaItem"
    })
    @NonNull
    Optional<Dispensacao> findById(@NonNull UUID id);

    @EntityGraph(attributePaths = {
            "paciente",
            "farmacia",
            "receita",
            "receita.itens",
            "profissionalSaude",
            "itens",
            "itens.sigtapProcedimento",
            "itens.receitaItem"
    })
    @Query("SELECT d FROM Dispensacao d WHERE d.id = :id")
    Optional<Dispensacao> findByIdCompleto(@Param("id") UUID id);

    @Query("SELECT d FROM Dispensacao d WHERE d.id = :id AND d.tenant.id = :tenantId")
    Optional<Dispensacao> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT r FROM Receita r " +
           "WHERE r.tenant.id = :tenantId " +
           "AND (r.dataValidade IS NULL OR r.dataValidade >= CURRENT_DATE) " +
           "AND EXISTS (SELECT 1 FROM ReceitaItem ri WHERE ri.receita = r " +
           "AND (SELECT COALESCE(SUM(di.quantidadeDispensada), 0) FROM DispensacaoItem di " +
           "WHERE di.receitaItem = ri) < ri.quantidadePrescrita)")
    Page<Receita> findReceitasPendentes(@Param("tenantId") UUID tenantId, Pageable pageable);
}

