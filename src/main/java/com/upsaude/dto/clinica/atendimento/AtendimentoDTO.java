package com.upsaude.dto.clinica.atendimento;

import com.upsaude.dto.embeddable.AnamneseAtendimentoDTO;
import com.upsaude.dto.embeddable.ClassificacaoRiscoAtendimentoDTO;
import com.upsaude.dto.embeddable.DiagnosticoAtendimentoDTO;
import com.upsaude.dto.embeddable.InformacoesAtendimentoDTO;
import com.upsaude.dto.embeddable.ProcedimentosRealizadosAtendimentoDTO;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import com.upsaude.dto.convenio.ConvenioDTO;
import com.upsaude.dto.profissional.equipe.EquipeSaudeDTO;
import com.upsaude.dto.profissional.EspecialidadesMedicasDTO;
import com.upsaude.dto.paciente.PacienteDTO;
import com.upsaude.dto.profissional.ProfissionaisSaudeDTO;


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
    private InformacoesAtendimentoDTO informacoes;
    private AnamneseAtendimentoDTO anamnese;
    private DiagnosticoAtendimentoDTO diagnostico;
    private ProcedimentosRealizadosAtendimentoDTO procedimentosRealizados;
    private ClassificacaoRiscoAtendimentoDTO classificacaoRisco;
    private String anotacoes;
    private String observacoesInternas;
}
