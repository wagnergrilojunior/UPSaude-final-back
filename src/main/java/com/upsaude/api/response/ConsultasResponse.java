package com.upsaude.api.response;

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

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultasResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private UUID pacienteId;
    private String pacienteNome;
    private UUID medicoId;
    private String medicoNome;
    private UUID profissionalSaudeId;
    private String profissionalSaudeNome;
    private UUID especialidadeId;
    private String especialidadeNome;
    private UUID convenioId;
    private String convenioNome;
    private InformacoesConsulta informacoes;
    private AnamneseConsulta anamnese;
    private DiagnosticoConsulta diagnostico;
    private PrescricaoConsulta prescricao;
    private ExamesSolicitadosConsulta examesSolicitados;
    private EncaminhamentoConsulta encaminhamento;
    private AtestadoConsulta atestado;
    private UUID cidPrincipalId;
    private String cidPrincipalCodigo;
    private String cidPrincipalDescricao;
    private String observacoes;
    private String observacoesInternas;
}
