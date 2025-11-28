package com.upsaude.repository;

import com.upsaude.entity.Atendimento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repositório para operações de banco de dados relacionadas a Atendimento.
 *
 * @author UPSaúde
 */
@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, UUID> {

    /**
     * Busca todos os atendimentos de um paciente, ordenados por data/hora decrescente.
     *
     * @param pacienteId ID do paciente
     * @param pageable informações de paginação
     * @return página de atendimentos do paciente
     */
    Page<Atendimento> findByPacienteIdOrderByDataHoraDesc(UUID pacienteId, Pageable pageable);

    /**
     * Busca todos os atendimentos realizados por um profissional, ordenados por data/hora decrescente.
     *
     * @param profissionalId ID do profissional de saúde
     * @param pageable informações de paginação
     * @return página de atendimentos do profissional
     */
    Page<Atendimento> findByProfissionalIdOrderByDataHoraDesc(UUID profissionalId, Pageable pageable);
}

