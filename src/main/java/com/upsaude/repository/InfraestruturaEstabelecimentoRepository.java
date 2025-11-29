package com.upsaude.repository;

import com.upsaude.entity.InfraestruturaEstabelecimento;
import com.upsaude.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repositório para operações de banco de dados relacionadas a InfraestruturaEstabelecimento.
 *
 * @author UPSaúde
 */
@Repository
public interface InfraestruturaEstabelecimentoRepository extends JpaRepository<InfraestruturaEstabelecimento, UUID> {

    /**
     * Busca toda a infraestrutura de um estabelecimento, ordenada por tipo.
     */
    Page<InfraestruturaEstabelecimento> findByEstabelecimentoIdOrderByTipoAsc(
            UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca infraestrutura por tipo em um estabelecimento.
     */
    List<InfraestruturaEstabelecimento> findByEstabelecimentoIdAndTipo(
            UUID estabelecimentoId, String tipo);

    /**
     * Busca infraestrutura por código CNES em um estabelecimento.
     */
    List<InfraestruturaEstabelecimento> findByEstabelecimentoIdAndCodigoCnes(
            UUID estabelecimentoId, String codigoCnes);

    /**
     * Busca toda a infraestrutura ativa de um estabelecimento.
     */
    List<InfraestruturaEstabelecimento> findByEstabelecimentoIdAndActiveTrueOrderByTipoAsc(
            UUID estabelecimentoId);

    /**
     * Busca todos os registros de infraestrutura de um tenant.
     */
    Page<InfraestruturaEstabelecimento> findByTenantOrderByCreatedAtDesc(Tenant tenant, Pageable pageable);
}

