package com.upsaude.dto;

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
public class AtendimentoDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteDTO paciente;
    private ProfissionaisSaudeDTO profissional;
    private EspecialidadesMedicasDTO especialidade;
    private EquipeSaudeDTO equipeSaude;
    private ConvenioDTO convenio;
    private InformacoesAtendimento informacoes;
    private AnamneseAtendimento anamnese;
    private DiagnosticoAtendimento diagnostico;
    private ProcedimentosRealizadosAtendimento procedimentosRealizados;
    private ClassificacaoRiscoAtendimento classificacaoRisco;
    private CidDoencasDTO cidPrincipal;
    private String anotacoes;
    private String observacoesInternas;
}
