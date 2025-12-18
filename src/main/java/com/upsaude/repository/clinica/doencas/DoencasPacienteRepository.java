package com.upsaude.repository.clinica.doencas;

import com.upsaude.entity.clinica.doencas.DoencasPaciente;
import com.upsaude.entity.sistema.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DoencasPacienteRepository extends JpaRepository<DoencasPaciente, UUID> {

    Page<DoencasPaciente> findByPacienteId(UUID pacienteId, Pageable pageable);

    Page<DoencasPaciente> findByDoencaId(UUID doencaId, Pageable pageable);

    Page<DoencasPaciente> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    @Query("SELECT dp FROM DoencasPaciente dp WHERE dp.paciente.id = :pacienteId AND dp.doenca.id = :doencaId")
    Optional<DoencasPaciente> findByPacienteIdAndDoencaId(@Param("pacienteId") UUID pacienteId, @Param("doencaId") UUID doencaId);

    @Query("SELECT dp FROM DoencasPaciente dp WHERE dp.diagnostico.dataDiagnostico BETWEEN :dataInicio AND :dataFim")
    Page<DoencasPaciente> findByDataDiagnosticoBetween(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim, Pageable pageable);

    Page<DoencasPaciente> findByTenant(Tenant tenant, Pageable pageable);

    @Query("SELECT dp FROM DoencasPaciente dp WHERE dp.estabelecimento.id = :estabelecimentoId AND dp.tenant = :tenant")
    Page<DoencasPaciente> findByEstabelecimentoIdAndTenant(@Param("estabelecimentoId") UUID estabelecimentoId, @Param("tenant") Tenant tenant, Pageable pageable);

    @Query("SELECT dp FROM DoencasPaciente dp WHERE dp.paciente.id = :pacienteId AND dp.active = true")
    List<DoencasPaciente> findActiveByPacienteId(@Param("pacienteId") UUID pacienteId);
}
