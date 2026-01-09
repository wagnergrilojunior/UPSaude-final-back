package com.upsaude.api.response.clinica.atendimento;

import com.upsaude.api.response.embeddable.AnamneseAtendimentoResponse;
import com.upsaude.api.response.embeddable.ClassificacaoRiscoAtendimentoResponse;
import com.upsaude.api.response.convenio.ConvenioResponse;
import com.upsaude.api.response.embeddable.DiagnosticoAtendimentoResponse;
import com.upsaude.api.response.profissional.equipe.EquipeSaudeResponse;
import com.upsaude.api.response.embeddable.InformacoesAtendimentoResponse;
import com.upsaude.api.response.embeddable.ProcedimentosRealizadosAtendimentoResponse;
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
public class AtendimentoResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteAtendimentoResponse paciente;
    private ProfissionalAtendimentoResponse profissional;
    private EquipeSaudeResponse equipeSaude;
    private ConvenioResponse convenio;
    private InformacoesAtendimentoResponse informacoes;
    private AnamneseAtendimentoResponse anamnese;
    private DiagnosticoAtendimentoResponse diagnostico;
    private ProcedimentosRealizadosAtendimentoResponse procedimentosRealizados;
    private ClassificacaoRiscoAtendimentoResponse classificacaoRisco;
    private String anotacoes;
    private String observacoesInternas;
}
