package com.upsaude.repository.vacinacao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.upsaude.entity.vacinacao.LoteVacina;

@Repository
public interface LoteVacinaRepository extends JpaRepository<LoteVacina, UUID> {

        @Query("SELECT l FROM LoteVacina l WHERE l.numeroLote = :numeroLote " +
                        "AND l.imunobiologico.id = :imunobiologicoId " +
                        "AND l.fabricante.id = :fabricanteId " +
                        "AND l.tenant.id = :tenantId")
        Optional<LoteVacina> findByNumeroLoteAndImunobiologicoIdAndFabricanteIdAndTenantId(
                        @Param("numeroLote") String numeroLote,
                        @Param("imunobiologicoId") UUID imunobiologicoId,
                        @Param("fabricanteId") UUID fabricanteId,
                        @Param("tenantId") UUID tenantId);

        @Query("SELECT l FROM LoteVacina l WHERE l.tenant.id = :tenantId AND l.ativo = true ORDER BY l.dataValidade ASC")
        List<LoteVacina> findByTenantIdAndAtivoTrueOrderByDataValidadeAsc(@Param("tenantId") UUID tenantId);

        @Query("SELECT l FROM LoteVacina l WHERE l.tenant.id = :tenantId " +
                        "AND l.imunobiologico.id = :imunobiologicoId AND l.ativo = true")
        List<LoteVacina> findByTenantIdAndImunobiologicoIdAndAtivoTrue(
                        @Param("tenantId") UUID tenantId,
                        @Param("imunobiologicoId") UUID imunobiologicoId);

        @Query("SELECT l FROM LoteVacina l WHERE l.tenant.id = :tenantId " +
                        "AND l.estabelecimento.id = :estabelecimentoId AND l.ativo = true")
        List<LoteVacina> findByTenantIdAndEstabelecimentoIdAndAtivoTrue(
                        @Param("tenantId") UUID tenantId,
                        @Param("estabelecimentoId") UUID estabelecimentoId);

        @Query("SELECT l FROM LoteVacina l WHERE l.tenant.id = :tenantId " +
                        "AND l.dataValidade < :data AND l.quantidadeDisponivel > 0 AND l.ativo = true")
        List<LoteVacina> findLotesVencidosComEstoque(
                        @Param("tenantId") UUID tenantId,
                        @Param("data") LocalDate data);

        @Query("SELECT l FROM LoteVacina l WHERE l.tenant.id = :tenantId " +
                        "AND l.dataValidade > :hoje AND l.quantidadeDisponivel > 0 AND l.ativo = true " +
                        "ORDER BY l.dataValidade ASC")
        List<LoteVacina> findLotesDisponiveis(
                        @Param("tenantId") UUID tenantId,
                        @Param("hoje") LocalDate hoje);
}
