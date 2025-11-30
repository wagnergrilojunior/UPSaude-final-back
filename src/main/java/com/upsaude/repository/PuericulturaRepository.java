package com.upsaude.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Puericultura;
import com.upsaude.entity.Tenant;

/**
 * Repositório para operações de banco de dados da entidade Puericultura.
 *
 * @author UPSaúde
 */
public interface PuericulturaRepository extends JpaRepository<Puericultura, UUID> {
    
    /**
     * Busca todas as puericulturas de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de puericulturas
     */
    Page<Puericultura> findByEstabelecimentoIdOrderByDataInicioAcompanhamentoDesc(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todas as puericulturas de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de puericulturas
     */
    Page<Puericultura> findByTenantOrderByDataInicioAcompanhamentoDesc(Tenant tenant, Pageable pageable);

    /**
     * Busca puericultura por paciente (criança).
     *
     * @param pacienteId ID do paciente
     * @return puericultura do paciente (se existir)
     */
    Optional<Puericultura> findByPacienteId(UUID pacienteId);

    /**
     * Busca puericulturas ativas de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de puericulturas ativas
     */
    Page<Puericultura> findByAcompanhamentoAtivoAndEstabelecimentoId(Boolean ativo, UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca puericulturas por paciente.
     *
     * @param pacienteId ID do paciente
     * @return lista de puericulturas do paciente
     */
    List<Puericultura> findByPacienteIdOrderByDataInicioAcompanhamentoDesc(UUID pacienteId);
}

