package com.upsaude.repository;

import com.upsaude.entity.Doencas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositório para a entidade Doencas.
 * Esta entidade é de escopo global e não possui relacionamento com Tenant ou Estabelecimento.
 */
@Repository
public interface DoencasRepository extends JpaRepository<Doencas, UUID> {

    Optional<Doencas> findByNome(String nome);

    Page<Doencas> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    Page<Doencas> findByCronica(Boolean cronica, Pageable pageable);

    @Query("SELECT d FROM Doencas d WHERE d.cidPrincipal.codigo = :codigoCid")
    Page<Doencas> findByCodigoCid(@Param("codigoCid") String codigoCid, Pageable pageable);

    /**
     * Verifica se existe uma doença com o nome informado.
     * Usado para validar duplicatas antes de cadastrar ou atualizar.
     *
     * @param nome nome da doença
     * @return true se existe uma doença com este nome, false caso contrário
     */
    boolean existsByNome(String nome);

    /**
     * Verifica se existe uma doença com o nome informado, excluindo o registro com o ID especificado.
     * Usado para validar duplicatas durante atualização.
     *
     * @param nome nome da doença
     * @param id ID da doença a ser excluída da verificação
     * @return true se existe outra doença com este nome, false caso contrário
     */
    boolean existsByNomeAndIdNot(String nome, UUID id);

    /**
     * Verifica se existe uma doença com o código interno informado.
     * Usado para validar duplicatas antes de cadastrar ou atualizar.
     *
     * @param codigoInterno código interno da doença
     * @return true se existe uma doença com este código interno, false caso contrário
     */
    boolean existsByCodigoInterno(String codigoInterno);

    /**
     * Verifica se existe uma doença com o código interno informado, excluindo o registro com o ID especificado.
     * Usado para validar duplicatas durante atualização.
     *
     * @param codigoInterno código interno da doença
     * @param id ID da doença a ser excluída da verificação
     * @return true se existe outra doença com este código interno, false caso contrário
     */
    boolean existsByCodigoInternoAndIdNot(String codigoInterno, UUID id);
}

