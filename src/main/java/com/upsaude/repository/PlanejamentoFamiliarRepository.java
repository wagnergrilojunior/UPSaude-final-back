package com.upsaude.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.PlanejamentoFamiliar;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.TipoMetodoContraceptivoEnum;

/**
 * Repositório para operações de banco de dados da entidade PlanejamentoFamiliar.
 *
 * @author UPSaúde
 */
public interface PlanejamentoFamiliarRepository extends JpaRepository<PlanejamentoFamiliar, UUID> {
    
    /**
     * Busca todos os planejamentos familiares de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de planejamentos familiares
     */
    Page<PlanejamentoFamiliar> findByEstabelecimentoIdOrderByDataInicioAcompanhamentoDesc(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os planejamentos familiares de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de planejamentos familiares
     */
    Page<PlanejamentoFamiliar> findByTenantOrderByDataInicioAcompanhamentoDesc(Tenant tenant, Pageable pageable);

    /**
     * Busca planejamento familiar por paciente.
     *
     * @param pacienteId ID do paciente
     * @return planejamento familiar do paciente (se existir)
     */
    Optional<PlanejamentoFamiliar> findByPacienteIdAndAcompanhamentoAtivo(UUID pacienteId, Boolean ativo);

    /**
     * Busca planejamentos familiares ativos de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de planejamentos familiares ativos
     */
    Page<PlanejamentoFamiliar> findByAcompanhamentoAtivoAndEstabelecimentoId(Boolean ativo, UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca planejamentos familiares por método contraceptivo.
     *
     * @param metodo método contraceptivo
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de planejamentos familiares
     */
    Page<PlanejamentoFamiliar> findByMetodoAtualAndEstabelecimentoId(TipoMetodoContraceptivoEnum metodo, UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca planejamentos familiares por paciente.
     *
     * @param pacienteId ID do paciente
     * @return lista de planejamentos familiares do paciente
     */
    List<PlanejamentoFamiliar> findByPacienteIdOrderByDataInicioAcompanhamentoDesc(UUID pacienteId);
}

