package com.upsaude.repository;

import com.upsaude.entity.Doencas;
import com.upsaude.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DoencasRepository extends JpaRepository<Doencas, UUID> {

    Optional<Doencas> findByNome(String nome);

    Page<Doencas> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    Page<Doencas> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    @Query("SELECT d FROM Doencas d WHERE d.cidPrincipal.codigo = :codigoCid")
    Page<Doencas> findByCodigoCid(@Param("codigoCid") String codigoCid, Pageable pageable);

    Page<Doencas> findByTenant(Tenant tenant, Pageable pageable);

    @Query("SELECT d FROM Doencas d WHERE d.estabelecimento.id = :estabelecimentoId AND d.tenant = :tenant")
    Page<Doencas> findByEstabelecimentoIdAndTenant(@Param("estabelecimentoId") UUID estabelecimentoId, @Param("tenant") Tenant tenant, Pageable pageable);
}

