package com.upsaude.api.response.embeddable;

import com.upsaude.api.response.diagnostico.Ciap2Response;
import com.upsaude.api.response.referencia.cid.Cid10SubcategoriaResponse;
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
public class DiagnosticoAtendimentoResponse {
    private String diagnostico;
    private Cid10SubcategoriaResponse mainCid10;
    private Ciap2Response mainCiap2;
    private String mainClinicalStatus;
    private String mainVerificationStatus;
    private String diagnosticosSecundarios;
    private String hipoteseDiagnostica;
    private String conduta;
    private String evolucao;
}
