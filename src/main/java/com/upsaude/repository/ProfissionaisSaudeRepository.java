package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.entity.Tenant;

public interface ProfissionaisSaudeRepository extends JpaRepository<ProfissionaisSaude, UUID> {
    
    /**
     * REMOVIDO: Este método foi desativado após refatoração do sistema de permissões.
     * Os vínculos com estabelecimentos agora são gerenciados através de UsuariosSistema -> UsuarioEstabelecimento.
     * 
     * Para buscar profissionais de um estabelecimento, use:
     * 1. Buscar UsuarioEstabelecimento pelo estabelecimentoId
     * 2. Obter o UsuariosSistema.profissionalSaudeId
     * 3. Buscar ProfissionaisSaude pelo ID
     *
     * @deprecated Usar UsuarioEstabelecimentoRepository e UsuariosSistemaRepository
     */
    // @Query("SELECT DISTINCT p FROM ProfissionaisSaude p " +
    //        "INNER JOIN p.vinculosEstabelecimentos ve " +
    //        "WHERE ve.estabelecimento.id = :estabelecimentoId " +
    //        "AND ve.active = true")
    // Page<ProfissionaisSaude> findByEstabelecimentoId(@Param("estabelecimentoId") UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os profissionais de saúde de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de profissionais do tenant
     */
    Page<ProfissionaisSaude> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * REMOVIDO: Este método foi desativado após refatoração do sistema de permissões.
     * Os vínculos com estabelecimentos agora são gerenciados através de UsuariosSistema -> UsuarioEstabelecimento.
     * 
     * Para buscar profissionais de um estabelecimento, use:
     * 1. Buscar UsuarioEstabelecimento pelo estabelecimentoId
     * 2. Obter o UsuariosSistema.profissionalSaudeId
     * 3. Buscar ProfissionaisSaude pelo ID e tenant
     *
     * @deprecated Usar UsuarioEstabelecimentoRepository e UsuariosSistemaRepository
     */
    // @Query("SELECT DISTINCT p FROM ProfissionaisSaude p " +
    //        "INNER JOIN p.vinculosEstabelecimentos ve " +
    //        "WHERE ve.estabelecimento.id = :estabelecimentoId " +
    //        "AND p.tenant = :tenant " +
    //        "AND ve.active = true")
    // Page<ProfissionaisSaude> findByEstabelecimentoIdAndTenant(@Param("estabelecimentoId") UUID estabelecimentoId, 
    //                                                              @Param("tenant") Tenant tenant, 
    //                                                              Pageable pageable);

    /**
     * Verifica se já existe profissional com o CPF informado.
     *
     * @param cpf CPF do profissional
     * @return true se já existe, false caso contrário
     */
    boolean existsByCpf(String cpf);

    /**
     * Verifica se já existe profissional com o registro profissional, conselho e UF informados.
     *
     * @param registroProfissional número do registro profissional
     * @param conselhoId ID do conselho
     * @param ufRegistro UF do registro
     * @return true se já existe, false caso contrário
     */
    boolean existsByRegistroProfissionalAndConselhoIdAndUfRegistro(String registroProfissional, UUID conselhoId, String ufRegistro);

    /**
     * Busca profissional por CPF.
     *
     * @param cpf CPF do profissional
     * @return Optional contendo o profissional encontrado, se existir
     */
    java.util.Optional<ProfissionaisSaude> findByCpf(String cpf);
}
