package com.upsaude.repository.cnes;

import com.upsaude.entity.cnes.Leitos;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.enums.StatusLeitoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LeitosRepository extends JpaRepository<Leitos, UUID>, JpaSpecificationExecutor<Leitos> {

    Page<Leitos> findByTenant(Tenant tenant, Pageable pageable);

    @Query("SELECT l FROM Leitos l WHERE l.id = :id AND l.tenant.id = :tenantId")
    Optional<Leitos> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT l FROM Leitos l WHERE l.estabelecimento.id = :estabelecimentoId AND l.tenant.id = :tenantId")
    List<Leitos> findByEstabelecimentoIdAndTenant(
            @Param("estabelecimentoId") UUID estabelecimentoId,
            @Param("tenantId") UUID tenantId);

    @Query("SELECT l FROM Leitos l WHERE l.estabelecimento = :estabelecimento AND l.tenant = :tenant")
    Page<Leitos> findByEstabelecimentoAndTenant(
            @Param("estabelecimento") Estabelecimentos estabelecimento,
            @Param("tenant") Tenant tenant,
            Pageable pageable);

    @Query("SELECT l FROM Leitos l WHERE l.estabelecimento.id = :estabelecimentoId AND l.status = :status AND l.tenant.id = :tenantId")
    List<Leitos> findByEstabelecimentoIdAndStatusAndTenant(
            @Param("estabelecimentoId") UUID estabelecimentoId,
            @Param("status") StatusLeitoEnum status,
            @Param("tenantId") UUID tenantId);

    @Query("SELECT l FROM Leitos l WHERE l.codigoCnesLeito = :codigoCnesLeito AND l.tenant.id = :tenantId")
    Optional<Leitos> findByCodigoCnesLeitoAndTenant(
            @Param("codigoCnesLeito") String codigoCnesLeito,
            @Param("tenantId") UUID tenantId);
}

