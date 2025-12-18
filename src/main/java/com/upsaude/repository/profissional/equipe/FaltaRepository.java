package com.upsaude.repository.profissional.equipe;

import com.upsaude.entity.profissional.equipe.Falta;
import com.upsaude.enums.TipoFaltaEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface FaltaRepository extends JpaRepository<Falta, UUID> {

    @Query("SELECT f FROM Falta f WHERE f.profissional.id = :profissionalId AND f.dataFalta BETWEEN :dataInicio AND :dataFim AND f.tenant.id = :tenantId ORDER BY f.dataFalta DESC")
    Page<Falta> findByProfissionalIdAndDataFaltaBetweenAndTenantIdOrderByDataFaltaDesc(@Param("profissionalId") UUID profissionalId, @Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT f FROM Falta f WHERE f.estabelecimento.id = :estabelecimentoId AND f.dataFalta BETWEEN :dataInicio AND :dataFim AND f.tenant.id = :tenantId ORDER BY f.dataFalta DESC")
    Page<Falta> findByEstabelecimentoIdAndDataFaltaBetweenAndTenantIdOrderByDataFaltaDesc(@Param("estabelecimentoId") UUID estabelecimentoId, @Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT f FROM Falta f WHERE f.profissional.id = :profissionalId AND f.tenant.id = :tenantId ORDER BY f.dataFalta DESC")
    Page<Falta> findByProfissionalIdAndTenantIdOrderByDataFaltaDesc(@Param("profissionalId") UUID profissionalId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT f FROM Falta f WHERE f.medico.id = :medicoId AND f.tenant.id = :tenantId ORDER BY f.dataFalta DESC")
    Page<Falta> findByMedicoIdAndTenantIdOrderByDataFaltaDesc(@Param("medicoId") UUID medicoId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT f FROM Falta f WHERE f.estabelecimento.id = :estabelecimentoId AND f.tenant.id = :tenantId ORDER BY f.dataFalta DESC")
    Page<Falta> findByEstabelecimentoIdAndTenantIdOrderByDataFaltaDesc(@Param("estabelecimentoId") UUID estabelecimentoId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT f FROM Falta f WHERE f.tipoFalta = :tipoFalta AND f.tenant.id = :tenantId ORDER BY f.dataFalta DESC")
    Page<Falta> findByTipoFaltaAndTenantIdOrderByDataFaltaDesc(@Param("tipoFalta") TipoFaltaEnum tipoFalta, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT f FROM Falta f WHERE f.tenant.id = :tenantId")
    Page<Falta> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);
}
