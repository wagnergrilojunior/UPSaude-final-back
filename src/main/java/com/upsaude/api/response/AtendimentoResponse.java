package com.upsaude.api.response;

import com.upsaude.entity.embeddable.AnamneseAtendimento;
import com.upsaude.entity.embeddable.ClassificacaoRiscoAtendimento;
import com.upsaude.entity.embeddable.DiagnosticoAtendimento;
import com.upsaude.entity.embeddable.InformacoesAtendimento;
import com.upsaude.entity.embeddable.ProcedimentosRealizadosAtendimento;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtendimentoResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteResponse paciente;
    private ProfissionaisSaudeResponse profissional;
    private EspecialidadesMedicasResponse especialidade;
    private EquipeSaudeResponse equipeSaude;
    private ConvenioResponse convenio;
    private InformacoesAtendimento informacoes;
    private AnamneseAtendimento anamnese;
    private DiagnosticoAtendimento diagnostico;
    private ProcedimentosRealizadosAtendimento procedimentosRealizados;
    private ClassificacaoRiscoAtendimento classificacaoRisco;
    private CidDoencasResponse cidPrincipal;
    private String anotacoes;
    private String observacoesInternas;
}
