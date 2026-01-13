package com.upsaude.repository.clinica.atendimento;

import com.upsaude.entity.clinica.atendimento.SinalVital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface SinalVitalRepository extends JpaRepository<SinalVital, UUID> {
    List<SinalVital> findByPacienteIdOrderByDataMedicaoDesc(UUID pacienteId);

    List<SinalVital> findByAtendimentoId(UUID atendimentoId);
}
