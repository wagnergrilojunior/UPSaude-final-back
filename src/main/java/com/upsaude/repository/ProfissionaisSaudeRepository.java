package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.entity.Tenant;

public interface ProfissionaisSaudeRepository extends JpaRepository<ProfissionaisSaude, UUID> {
    
    /**
     * Busca todos os profissionais de saúde de um estabelecimento através da tabela de vínculos.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de profissionais do estabelecimento
     */
    @Query("SELECT DISTINCT p FROM ProfissionaisSaude p " +
           "INNER JOIN p.vinculosEstabelecimentos ve " +
           "WHERE ve.estabelecimento.id = :estabelecimentoId " +
           "AND ve.active = true")
    Page<ProfissionaisSaude> findByEstabelecimentoId(@Param("estabelecimentoId") UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os profissionais de saúde de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de profissionais do tenant
     */
    Page<ProfissionaisSaude> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todos os profissionais de saúde de um estabelecimento e tenant através da tabela de vínculos.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de profissionais
     */
    @Query("SELECT DISTINCT p FROM ProfissionaisSaude p " +
           "INNER JOIN p.vinculosEstabelecimentos ve " +
           "WHERE ve.estabelecimento.id = :estabelecimentoId " +
           "AND p.tenant = :tenant " +
           "AND ve.active = true")
    Page<ProfissionaisSaude> findByEstabelecimentoIdAndTenant(@Param("estabelecimentoId") UUID estabelecimentoId, 
                                                                 @Param("tenant") Tenant tenant, 
                                                                 Pageable pageable);

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
