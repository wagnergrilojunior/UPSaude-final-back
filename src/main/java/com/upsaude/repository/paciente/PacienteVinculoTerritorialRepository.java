package com.upsaude.repository.paciente;

import com.upsaude.entity.paciente.PacienteVinculoTerritorial;
import com.upsaude.enums.OrigemVinculoTerritorialEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PacienteVinculoTerritorialRepository extends JpaRepository<PacienteVinculoTerritorial, UUID> {

    List<PacienteVinculoTerritorial> findByPacienteId(UUID pacienteId);

    List<PacienteVinculoTerritorial> findByPacienteIdOrderByDataInicioDesc(UUID pacienteId);

    Optional<PacienteVinculoTerritorial> findByPacienteIdAndActiveTrue(UUID pacienteId);

    List<PacienteVinculoTerritorial> findByCnesEstabelecimento(String cnesEstabelecimento);

    List<PacienteVinculoTerritorial> findByIneEquipe(String ineEquipe);

    List<PacienteVinculoTerritorial> findByMicroarea(String microarea);

    List<PacienteVinculoTerritorial> findByPacienteIdAndOrigem(UUID pacienteId, OrigemVinculoTerritorialEnum origem);

    @Query("SELECT pvt FROM PacienteVinculoTerritorial pvt WHERE pvt.paciente.id = :pacienteId AND pvt.active = true")
    Optional<PacienteVinculoTerritorial> findAtivoByPacienteId(@Param("pacienteId") UUID pacienteId);
}

