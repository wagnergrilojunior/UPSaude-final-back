package com.upsaude.api.response.clinica.atendimento;

import com.upsaude.api.response.embeddable.DiagnosticoAtendimentoResponse;
import com.upsaude.api.response.embeddable.InformacoesAtendimentoResponse;
import com.upsaude.api.response.embeddable.ClassificacaoRiscoAtendimentoResponse;
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
public class AtendimentoCheckInResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private InformacoesAtendimentoResponse informacoes;
    private DiagnosticoAtendimentoResponse diagnostico;
    private ClassificacaoRiscoAtendimentoResponse classificacaoRisco;
    private String anotacoes;
    private ProfissionalAtendimentoResponse profissional;
    private EquipeSaudeCheckInResponse equipeSaude;
    private ConvenioCheckInResponse convenio;
    private UUID estabelecimentoId;
    private UUID tenantId;
}
