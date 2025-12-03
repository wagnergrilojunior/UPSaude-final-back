package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Medicos;
import com.upsaude.entity.Tenant;

public interface MedicosRepository extends JpaRepository<Medicos, UUID> {
    
    /**
     * REMOVIDO: Este método foi desativado após refatoração do sistema de permissões.
     * Os vínculos com estabelecimentos agora são gerenciados através de UsuariosSistema -> UsuarioEstabelecimento.
     * 
     * Para buscar médicos de um estabelecimento, use:
     * 1. Buscar UsuarioEstabelecimento pelo estabelecimentoId
     * 2. Obter o UsuariosSistema.medicoId
     * 3. Buscar Medicos pelo ID
     *
     * @deprecated Usar UsuarioEstabelecimentoRepository e UsuariosSistemaRepository
     */
    // @Query("SELECT DISTINCT m FROM Medicos m " +
    //        "INNER JOIN m.vinculosEstabelecimentos ve " +
    //        "WHERE ve.estabelecimento.id = :estabelecimentoId " +
    //        "AND ve.active = true")
    // Page<Medicos> findByEstabelecimentoId(@Param("estabelecimentoId") UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os médicos de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de médicos do tenant
     */
    Page<Medicos> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * REMOVIDO: Este método foi desativado após refatoração do sistema de permissões.
     * Os vínculos com estabelecimentos agora são gerenciados através de UsuariosSistema -> UsuarioEstabelecimento.
     * 
     * Para buscar médicos de um estabelecimento, use:
     * 1. Buscar UsuarioEstabelecimento pelo estabelecimentoId
     * 2. Obter o UsuariosSistema.medicoId
     * 3. Buscar Medicos pelo ID e tenant
     *
     * @deprecated Usar UsuarioEstabelecimentoRepository e UsuariosSistemaRepository
     */
    // @Query("SELECT DISTINCT m FROM Medicos m " +
    //        "INNER JOIN m.vinculosEstabelecimentos ve " +
    //        "WHERE ve.estabelecimento.id = :estabelecimentoId " +
    //        "AND m.tenant = :tenant " +
    //        "AND ve.active = true")
    // Page<Medicos> findByEstabelecimentoIdAndTenant(@Param("estabelecimentoId") UUID estabelecimentoId, 
    //                                                 @Param("tenant") Tenant tenant, 
    //                                                 Pageable pageable);
}
