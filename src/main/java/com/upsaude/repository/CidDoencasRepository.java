package com.upsaude.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.CidDoencas;

/**
 * Repositório para a entidade CidDoencas.
 * Esta entidade é de escopo global e não possui relacionamento com Tenant ou Estabelecimento.
 */
public interface CidDoencasRepository extends JpaRepository<CidDoencas, UUID> {
    
    /**
     * Busca um CID de doença pelo código.
     * 
     * @param codigo código CID a ser buscado
     * @return Optional contendo o CID de doença se encontrado
     */
    Optional<CidDoencas> findByCodigo(String codigo);
    
    /**
     * Verifica se existe um CID de doença com o código especificado e ID diferente.
     * Útil para validar duplicidade na atualização.
     * 
     * @param codigo código CID a ser verificado
     * @param id ID a ser excluído da busca (para atualização)
     * @return true se existe outro registro com o mesmo código
     */
    boolean existsByCodigoAndIdNot(String codigo, UUID id);
}
