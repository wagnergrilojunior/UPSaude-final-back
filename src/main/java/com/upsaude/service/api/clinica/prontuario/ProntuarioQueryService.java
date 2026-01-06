package com.upsaude.service.api.clinica.prontuario;

import com.upsaude.api.response.clinica.prontuario.ProntuarioResumoResponse;
import com.upsaude.api.response.clinica.prontuario.ProntuarioTimelineResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface ProntuarioQueryService {

    ProntuarioTimelineResponse buscarTimeline(UUID pacienteId);

    ProntuarioResumoResponse buscarResumo(UUID pacienteId);

    Page<ProntuarioTimelineResponse.ProntuarioEventoResponse> buscarEventos(
            UUID pacienteId,
            String tipoRegistro,
            OffsetDateTime dataInicio,
            OffsetDateTime dataFim,
            Pageable pageable);
}
