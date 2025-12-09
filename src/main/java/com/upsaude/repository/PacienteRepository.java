package com.upsaude.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.upsaude.entity.Paciente;
import com.upsaude.repository.projection.PacienteSimplificadoProjection;

public interface PacienteRepository extends JpaRepository<Paciente, UUID> {
    
    /**
     * Busca um paciente por CPF.
     * Usado para validar duplicatas antes de cadastrar ou atualizar.
     *
     * @param cpf CPF do paciente
     * @return Optional contendo o paciente encontrado, se existir
     */
    Optional<Paciente> findByCpf(String cpf);

    /**
     * Busca um paciente por email.
     * Usado para validar duplicatas antes de cadastrar ou atualizar.
     *
     * @param email email do paciente
     * @return Optional contendo o paciente encontrado, se existir
     */
    Optional<Paciente> findByEmail(String email);

    /**
     * Busca um paciente por CNS (Cartão Nacional de Saúde).
     * Usado para validar duplicatas antes de cadastrar ou atualizar.
     *
     * @param cns CNS do paciente
     * @return Optional contendo o paciente encontrado, se existir
     */
    Optional<Paciente> findByCns(String cns);

    /**
     * Busca um paciente por RG.
     * Usado para validar duplicatas antes de cadastrar ou atualizar.
     *
     * @param rg RG do paciente
     * @return Optional contendo o paciente encontrado, se existir
     */
    Optional<Paciente> findByRg(String rg);

    /**
     * Busca pacientes paginados usando projeção otimizada.
     * Seleciona apenas os campos necessários sem carregar relacionamentos lazy,
     * melhorando significativamente a performance da listagem.
     * 
     * IMPORTANTE: Esta query usa uma interface projection que força o Hibernate
     * a criar proxies leves que só carregam os campos da interface, evitando
     * carregar relacionamentos lazy desnecessários.
     * 
     * A interface projection garante que apenas os campos definidos na interface
     * sejam acessíveis, evitando queries N+1 e carregamento desnecessário de dados.
     *
     * @param pageable informações de paginação
     * @return página de projeções simplificadas de pacientes
     */
    @Query(value = "SELECT p FROM Paciente p",
           countQuery = "SELECT COUNT(p) FROM Paciente p")
    Page<PacienteSimplificadoProjection> findAllSimplificado(Pageable pageable);
}
