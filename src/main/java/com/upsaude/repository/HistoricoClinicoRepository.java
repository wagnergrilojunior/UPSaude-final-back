package com.upsaude.repository;

import com.upsaude.entity.HistoricoClinico;
import com.upsaude.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Repositório para operações de banco de dados relacionadas a HistoricoClinico.
 *
 * @author UPSaúde
 */
@Repository
public interface HistoricoClinicoRepository extends JpaRepository<HistoricoClinico, UUID> {

    /**
     * Busca todo o histórico clínico de um paciente, ordenado por data do registro decrescente.
     */
    Page<HistoricoClinico> findByPacienteIdOrderByDataRegistroDesc(UUID pacienteId, Pageable pageable);

    /**
     * Busca todo o histórico clínico de um paciente por tipo de registro, ordenado por data decrescente.
     */
    Page<HistoricoClinico> findByPacienteIdAndTipoRegistroOrderByDataRegistroDesc(
            UUID pacienteId, String tipoRegistro, Pageable pageable);

    /**
     * Busca todo o histórico clínico de um paciente em um período, ordenado por data decrescente.
     */
    Page<HistoricoClinico> findByPacienteIdAndDataRegistroBetweenOrderByDataRegistroDesc(
            UUID pacienteId, OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    /**
     * Busca todo o histórico clínico de um estabelecimento, ordenado por data do registro decrescente.
     */
    Page<HistoricoClinico> findByEstabelecimentoIdOrderByDataRegistroDesc(
            UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca histórico clínico por profissional responsável, ordenado por data decrescente.
     */
    Page<HistoricoClinico> findByProfissionalIdOrderByDataRegistroDesc(
            UUID profissionalId, Pageable pageable);

    /**
     * Busca todo o histórico clínico de um tenant.
     */
    Page<HistoricoClinico> findByTenantOrderByDataRegistroDesc(Tenant tenant, Pageable pageable);
}

