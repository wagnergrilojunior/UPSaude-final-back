package com.upsaude.api.request;

import com.upsaude.entity.embeddable.AnamneseConsulta;
import com.upsaude.entity.embeddable.AtestadoConsulta;
import com.upsaude.entity.embeddable.DiagnosticoConsulta;
import com.upsaude.entity.embeddable.EncaminhamentoConsulta;
import com.upsaude.entity.embeddable.ExamesSolicitadosConsulta;
import com.upsaude.entity.embeddable.InformacoesConsulta;
import com.upsaude.entity.embeddable.PrescricaoConsulta;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultasRequest {
    private UUID paciente;
    private UUID medico;
    private UUID profissionalSaude;
    private UUID especialidade;
    private UUID convenio;
    private InformacoesConsulta informacoes;
    private AnamneseConsulta anamnese;
    private DiagnosticoConsulta diagnostico;
    private PrescricaoConsulta prescricao;
    private ExamesSolicitadosConsulta examesSolicitados;
    private EncaminhamentoConsulta encaminhamento;
    private AtestadoConsulta atestado;
    private UUID cidPrincipal;
    private String observacoes;
    private String observacoesInternas;
}
