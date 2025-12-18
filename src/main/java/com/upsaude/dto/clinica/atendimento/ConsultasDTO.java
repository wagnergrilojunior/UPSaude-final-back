package com.upsaude.dto.clinica.atendimento;

import com.upsaude.dto.embeddable.AnamneseConsultaDTO;
import com.upsaude.dto.embeddable.AtestadoConsultaDTO;
import com.upsaude.dto.embeddable.DiagnosticoConsultaDTO;
import com.upsaude.dto.embeddable.EncaminhamentoConsultaDTO;
import com.upsaude.dto.embeddable.ExamesSolicitadosConsultaDTO;
import com.upsaude.dto.embeddable.InformacoesConsultaDTO;
import com.upsaude.dto.embeddable.PrescricaoConsultaDTO;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import com.upsaude.dto.convenio.ConvenioDTO;
import com.upsaude.dto.profissional.EspecialidadesMedicasDTO;
import com.upsaude.dto.profissional.MedicosDTO;
import com.upsaude.dto.paciente.PacienteDTO;
import com.upsaude.dto.profissional.ProfissionaisSaudeDTO;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultasDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteDTO paciente;
    private MedicosDTO medico;
    private ProfissionaisSaudeDTO profissionalSaude;
    private EspecialidadesMedicasDTO especialidade;
    private ConvenioDTO convenio;
    private InformacoesConsultaDTO informacoes;
    private AnamneseConsultaDTO anamnese;
    private DiagnosticoConsultaDTO diagnostico;
    private PrescricaoConsultaDTO prescricao;
    private ExamesSolicitadosConsultaDTO examesSolicitados;
    private EncaminhamentoConsultaDTO encaminhamento;
    private AtestadoConsultaDTO atestado;
    private String observacoes;
    private String observacoesInternas;
}
