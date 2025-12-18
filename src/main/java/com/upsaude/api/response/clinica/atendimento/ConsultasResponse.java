package com.upsaude.api.response.clinica.atendimento;

import com.upsaude.api.response.profissional.EspecialidadesMedicasResponse;
import com.upsaude.api.response.profissional.MedicosResponse;
import com.upsaude.api.response.profissional.ProfissionaisSaudeResponse;
import com.upsaude.api.response.paciente.PacienteResponse;
import com.upsaude.api.response.embeddable.AnamneseConsultaResponse;
import com.upsaude.api.response.embeddable.AtestadoConsultaResponse;
import com.upsaude.api.response.convenio.ConvenioResponse;
import com.upsaude.api.response.embeddable.DiagnosticoConsultaResponse;
import com.upsaude.api.response.embeddable.EncaminhamentoConsultaResponse;
import com.upsaude.api.response.embeddable.ExamesSolicitadosConsultaResponse;
import com.upsaude.api.response.embeddable.InformacoesConsultaResponse;
import com.upsaude.api.response.embeddable.PrescricaoConsultaResponse;


import com.upsaude.api.response.embeddable.AnamneseConsultaResponse;
import com.upsaude.api.response.embeddable.AtestadoConsultaResponse;
import com.upsaude.api.response.embeddable.DiagnosticoConsultaResponse;
import com.upsaude.api.response.embeddable.EncaminhamentoConsultaResponse;
import com.upsaude.api.response.embeddable.ExamesSolicitadosConsultaResponse;
import com.upsaude.api.response.embeddable.InformacoesConsultaResponse;
import com.upsaude.api.response.embeddable.PrescricaoConsultaResponse;
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
public class ConsultasResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteResponse paciente;
    private MedicosResponse medico;
    private ProfissionaisSaudeResponse profissionalSaude;
    private EspecialidadesMedicasResponse especialidade;
    private ConvenioResponse convenio;
    private InformacoesConsultaResponse informacoes;
    private AnamneseConsultaResponse anamnese;
    private DiagnosticoConsultaResponse diagnostico;
    private PrescricaoConsultaResponse prescricao;
    private ExamesSolicitadosConsultaResponse examesSolicitados;
    private EncaminhamentoConsultaResponse encaminhamento;
    private AtestadoConsultaResponse atestado;
    private String observacoes;
    private String observacoesInternas;
}
