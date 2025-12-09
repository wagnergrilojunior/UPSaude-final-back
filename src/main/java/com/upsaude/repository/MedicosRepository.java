package com.upsaude.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

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
     * Busca um médico por CRM, UF do CRM e tenant.
     * Usado para validar duplicatas antes de cadastrar ou atualizar.
     *
     * @param crm número do CRM
     * @param crmUf UF do CRM
     * @param tenant tenant
     * @return Optional com o médico encontrado, se existir
     */
    @Query("SELECT m FROM Medicos m WHERE m.registroProfissional.crm = :crm " +
           "AND m.registroProfissional.crmUf = :crmUf AND m.tenant = :tenant")
    Optional<Medicos> findByRegistroProfissionalCrmAndRegistroProfissionalCrmUfAndTenant(
            @Param("crm") String crm, 
            @Param("crmUf") String crmUf, 
            @Param("tenant") Tenant tenant);

    /**
     * Busca um médico por CPF e tenant.
     * Usado para validar duplicatas antes de cadastrar ou atualizar.
     *
     * @param cpf CPF do médico
     * @param tenant tenant
     * @return Optional com o médico encontrado, se existir
     */
    @Query("SELECT m FROM Medicos m WHERE m.dadosPessoais.cpf = :cpf AND m.tenant = :tenant")
    Optional<Medicos> findByDadosPessoaisCpfAndTenant(
            @Param("cpf") String cpf, 
            @Param("tenant") Tenant tenant);

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

    /**
     * Busca médicos paginados com relacionamentos carregados usando EntityGraph.
     * Otimiza a query carregando relacionamentos necessários, evitando o problema N+1 queries.
     * 
     * IMPORTANTE: 
     * - especialidades: carregado via EntityGraph (fetch join)
     * - enderecos e medicosEstabelecimentos: carregados via batch fetching 
     *   (hibernate.default_batch_fetch_size) para evitar MultipleBagFetchException
     * 
     * O Hibernate não permite fazer fetch de múltiplas coleções List simultaneamente.
     * Por isso, apenas especialidades é carregado via EntityGraph, e os outros são
     * carregados via batch fetching quando necessário.
     *
     * @param pageable informações de paginação
     * @return página de médicos com relacionamentos carregados
     */
    @Override
    @EntityGraph(attributePaths = {
        "especialidades"
    })
    @NonNull
    Page<Medicos> findAll(@NonNull Pageable pageable);
}
