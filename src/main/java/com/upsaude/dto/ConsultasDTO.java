package com.upsaude.dto;

import com.upsaude.entity.embeddable.AnamneseConsulta;
import com.upsaude.entity.embeddable.AtestadoConsulta;
import com.upsaude.entity.embeddable.DiagnosticoConsulta;
import com.upsaude.entity.embeddable.EncaminhamentoConsulta;
import com.upsaude.entity.embeddable.ExamesSolicitadosConsulta;
import com.upsaude.entity.embeddable.InformacoesConsulta;
import com.upsaude.entity.embeddable.PrescricaoConsulta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultasDTO {
    private UUID id;
    private UUID pacienteId;
    private UUID medicoId;
    private UUID profissionalSaudeId;
    private UUID especialidadeId;
    private UUID convenioId;
    private InformacoesConsulta informacoes;
    private AnamneseConsulta anamnese;
    private DiagnosticoConsulta diagnostico;
    private PrescricaoConsulta prescricao;
    private ExamesSolicitadosConsulta examesSolicitados;
    private EncaminhamentoConsulta encaminhamento;
    private AtestadoConsulta atestado;
    private UUID cidPrincipalId;
    private String observacoes;
    private String observacoesInternas;
    private Boolean active;
}
