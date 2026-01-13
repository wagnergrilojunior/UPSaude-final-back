package com.upsaude.repository.financeiro;

import com.upsaude.entity.financeiro.ContaContabil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContaContabilRepository extends JpaRepository<ContaContabil, UUID> {

    @Query("SELECT c FROM ContaContabil c WHERE c.id = :id AND c.tenant.id = :tenantId")
    Optional<ContaContabil> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT c FROM ContaContabil c WHERE c.tenant.id = :tenantId")
    Page<ContaContabil> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT c FROM ContaContabil c WHERE c.planoContas.id = :planoContasId AND c.tenant.id = :tenantId ORDER BY c.codigo ASC")
    Page<ContaContabil> findByPlanoContas(@Param("planoContasId") UUID planoContasId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT c FROM ContaContabil c WHERE c.contaPai.id = :contaPaiId AND c.tenant.id = :tenantId ORDER BY c.codigo ASC")
    List<ContaContabil> findByContaPai(@Param("contaPaiId") UUID contaPaiId, @Param("tenantId") UUID tenantId);

    @Query("SELECT c FROM ContaContabil c WHERE c.codigo = :codigo AND c.planoContas.id = :planoContasId AND c.tenant.id = :tenantId")
    Optional<ContaContabil> findByCodigoAndPlanoContas(
            @Param("codigo") String codigo,
            @Param("planoContasId") UUID planoContasId,
            @Param("tenantId") UUID tenantId);

    @Query("SELECT c FROM ContaContabil c WHERE c.natureza = :natureza AND c.tenant.id = :tenantId ORDER BY c.codigo ASC")
    Page<ContaContabil> findByNatureza(@Param("natureza") String natureza, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT c FROM ContaContabil c WHERE c.aceitaLancamento = true AND c.tenant.id = :tenantId ORDER BY c.codigo ASC")
    Page<ContaContabil> findByAceitaLancamento(@Param("tenantId") UUID tenantId, Pageable pageable);
}
