package com.upsaude.api.request.embeddable;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de diagnostico atendimento")
public class DiagnosticoAtendimentoRequest {
    private String diagnostico;
    private UUID mainCid10Id;
    private UUID mainCiap2Id;
    private String mainClinicalStatus;
    private String mainVerificationStatus;
    private String diagnosticosSecundarios;
    private String hipoteseDiagnostica;
    private String conduta;
    private String evolucao;
}
