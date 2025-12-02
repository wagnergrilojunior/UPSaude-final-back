package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.Medicos;
import com.upsaude.entity.Tenant;

public interface MedicosRepository extends JpaRepository<Medicos, UUID> {
    
    /**
     * Busca todos os médicos de um estabelecimento através da tabela de vínculos.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de médicos do estabelecimento
     */
    @Query("SELECT DISTINCT m FROM Medicos m " +
           "INNER JOIN m.vinculosEstabelecimentos ve " +
           "WHERE ve.estabelecimento.id = :estabelecimentoId " +
           "AND ve.active = true")
    Page<Medicos> findByEstabelecimentoId(@Param("estabelecimentoId") UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os médicos de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de médicos do tenant
     */
    Page<Medicos> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todos os médicos de um estabelecimento e tenant através da tabela de vínculos.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de médicos
     */
    @Query("SELECT DISTINCT m FROM Medicos m " +
           "INNER JOIN m.vinculosEstabelecimentos ve " +
           "WHERE ve.estabelecimento.id = :estabelecimentoId " +
           "AND m.tenant = :tenant " +
           "AND ve.active = true")
    Page<Medicos> findByEstabelecimentoIdAndTenant(@Param("estabelecimentoId") UUID estabelecimentoId, 
                                                    @Param("tenant") Tenant tenant, 
                                                    Pageable pageable);
}
