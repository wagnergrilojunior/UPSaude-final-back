package com.upsaude.repository.estabelecimento.equipamento;

import com.upsaude.entity.estabelecimento.equipamento.Equipamentos;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoEquipamentoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EquipamentosRepository extends JpaRepository<Equipamentos, UUID> {

    @Query("SELECT e FROM Equipamentos e WHERE e.id = :id AND e.tenant.id = :tenantId")
    Optional<Equipamentos> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT e FROM Equipamentos e WHERE e.tenant.id = :tenantId")
    Page<Equipamentos> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT e FROM Equipamentos e WHERE e.dadosIdentificacao.codigoCnes = :codigoCnes AND e.tenant.id = :tenantId")
    Optional<Equipamentos> findByCodigoCnesAndTenantId(@Param("codigoCnes") String codigoCnes, @Param("tenantId") UUID tenantId);

    @Query("SELECT e FROM Equipamentos e WHERE e.dadosIdentificacao.registroAnvisa = :registroAnvisa AND e.tenant.id = :tenantId")
    Optional<Equipamentos> findByRegistroAnvisaAndTenantId(@Param("registroAnvisa") String registroAnvisa, @Param("tenantId") UUID tenantId);

    @Query("SELECT e FROM Equipamentos e WHERE LOWER(e.dadosIdentificacao.nome) LIKE LOWER(CONCAT('%', :nome, '%')) AND e.tenant.id = :tenantId ORDER BY e.dadosIdentificacao.nome ASC")
    Page<Equipamentos> findByNomeContainingIgnoreCaseAndTenantIdOrderByNomeAsc(@Param("nome") String nome, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT e FROM Equipamentos e WHERE e.dadosIdentificacao.tipo = :tipo AND e.tenant.id = :tenantId ORDER BY e.dadosIdentificacao.nome ASC")
    Page<Equipamentos> findByTipoAndTenantIdOrderByNomeAsc(@Param("tipo") TipoEquipamentoEnum tipo, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT e FROM Equipamentos e WHERE e.fabricante.id = :fabricanteId AND e.tenant.id = :tenantId ORDER BY e.dadosIdentificacao.nome ASC")
    Page<Equipamentos> findByFabricanteIdAndTenantIdOrderByNomeAsc(@Param("fabricanteId") UUID fabricanteId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT e FROM Equipamentos e WHERE e.status = :status AND e.tenant.id = :tenantId ORDER BY e.dadosIdentificacao.nome ASC")
    Page<Equipamentos> findByStatusAndTenantIdOrderByNomeAsc(@Param("status") StatusAtivoEnum status, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT e FROM Equipamentos e WHERE e.status = :status AND e.active = true AND e.tenant.id = :tenantId ORDER BY e.dadosIdentificacao.nome ASC")
    Page<Equipamentos> findByStatusAndActiveTrueAndTenantIdOrderByNomeAsc(@Param("status") StatusAtivoEnum status, @Param("tenantId") UUID tenantId, Pageable pageable);
}

