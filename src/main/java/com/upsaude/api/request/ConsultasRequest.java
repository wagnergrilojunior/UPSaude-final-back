package com.upsaude.api.request;

import com.upsaude.api.request.embeddable.AnamneseConsultaRequest;
import com.upsaude.api.request.embeddable.AtestadoConsultaRequest;
import com.upsaude.api.request.embeddable.DiagnosticoConsultaRequest;
import com.upsaude.api.request.embeddable.EncaminhamentoConsultaRequest;
import com.upsaude.api.request.embeddable.ExamesSolicitadosConsultaRequest;
import com.upsaude.api.request.embeddable.InformacoesConsultaRequest;
import com.upsaude.api.request.embeddable.PrescricaoConsultaRequest;
import jakarta.validation.constraints.NotNull;
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
public class ConsultasRequest {
    @NotNull(message = "Paciente é obrigatório")
    private UUID paciente;
    
    private UUID medico;
    private UUID profissionalSaude;
    private UUID especialidade;
    private UUID convenio;
    
    private InformacoesConsultaRequest informacoes;
    private AnamneseConsultaRequest anamnese;
    private DiagnosticoConsultaRequest diagnostico;
    private PrescricaoConsultaRequest prescricao;
    private ExamesSolicitadosConsultaRequest examesSolicitados;
    private EncaminhamentoConsultaRequest encaminhamento;
    private AtestadoConsultaRequest atestado;
    
    private UUID cidPrincipal;
    private String observacoes;
    private String observacoesInternas;
}
