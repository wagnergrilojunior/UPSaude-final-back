package com.upsaude.api.response.clinica.prontuario;

import com.upsaude.api.response.profissional.ProfissionaisSaudeResponse;
import com.upsaude.api.response.referencia.cid.Cid10SubcategoriaResponse;
import com.upsaude.api.response.referencia.sigtap.SigtapProcedimentoResponse;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamePacienteResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private String tipoExame;
    private String nomeExame;
    private OffsetDateTime dataSolicitacao;
    private OffsetDateTime dataExame;
    private OffsetDateTime dataResultado;
    private ProfissionaisSaudeResponse profissionalSolicitante;
    private ProfissionaisSaudeResponse profissionalResponsavel;
    private String laudo;
    private String resultados;
    private String unidadeLaboratorio;
    private String observacoes;
    private SigtapProcedimentoResponse procedimento;
    private Cid10SubcategoriaResponse diagnosticoRelacionado;
}

