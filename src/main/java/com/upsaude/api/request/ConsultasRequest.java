package com.upsaude.api.request;

import com.upsaude.api.request.embeddable.AnamneseConsultaRequest;
import com.upsaude.api.request.embeddable.AtestadoConsultaRequest;
import com.upsaude.api.request.embeddable.DiagnosticoConsultaRequest;
import com.upsaude.api.request.embeddable.EncaminhamentoConsultaRequest;
import com.upsaude.api.request.embeddable.ExamesSolicitadosConsultaRequest;
import com.upsaude.api.request.embeddable.InformacoesConsultaRequest;
import com.upsaude.api.request.embeddable.PrescricaoConsultaRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    
    @Valid
    private InformacoesConsultaRequest informacoes;
    
    @Valid
    private AnamneseConsultaRequest anamnese;
    
    @Valid
    private DiagnosticoConsultaRequest diagnostico;
    
    @Valid
    private PrescricaoConsultaRequest prescricao;
    
    @Valid
    private ExamesSolicitadosConsultaRequest examesSolicitados;
    
    @Valid
    private EncaminhamentoConsultaRequest encaminhamento;
    
    @Valid
    private AtestadoConsultaRequest atestado;
    
    private UUID cidPrincipal;
    
    @Size(max = 1000, message = "Observações deve ter no máximo 1000 caracteres")
    private String observacoes;
    
    @Size(max = 1000, message = "Observações internas deve ter no máximo 1000 caracteres")
    private String observacoesInternas;
}
