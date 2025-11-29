package com.upsaude.repository;

import com.upsaude.entity.ServicosEstabelecimento;
import com.upsaude.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repositório para operações de banco de dados relacionadas a ServicosEstabelecimento.
 *
 * @author UPSaúde
 */
@Repository
public interface ServicosEstabelecimentoRepository extends JpaRepository<ServicosEstabelecimento, UUID> {

    /**
     * Busca todos os serviços de um estabelecimento, ordenados por nome.
     */
    Page<ServicosEstabelecimento> findByEstabelecimentoIdOrderByNomeAsc(
            UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca serviços por nome (busca parcial) em um estabelecimento.
     */
    Page<ServicosEstabelecimento> findByEstabelecimentoIdAndNomeContainingIgnoreCaseOrderByNomeAsc(
            UUID estabelecimentoId, String nome, Pageable pageable);

    /**
     * Busca serviço por código CNES em um estabelecimento.
     */
    List<ServicosEstabelecimento> findByEstabelecimentoIdAndCodigoCnes(
            UUID estabelecimentoId, String codigoCnes);

    /**
     * Busca todos os serviços ativos de um estabelecimento.
     */
    List<ServicosEstabelecimento> findByEstabelecimentoIdAndActiveTrueOrderByNomeAsc(
            UUID estabelecimentoId);

    /**
     * Busca todos os serviços de um tenant.
     */
    Page<ServicosEstabelecimento> findByTenantOrderByCreatedAtDesc(Tenant tenant, Pageable pageable);
}

