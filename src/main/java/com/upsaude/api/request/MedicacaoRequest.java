package com.upsaude.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.upsaude.api.request.embeddable.ClassificacaoMedicamentoRequest;
import com.upsaude.api.request.embeddable.ConservacaoArmazenamentoMedicamentoRequest;
import com.upsaude.api.request.embeddable.ContraindicacoesPrecaucoesMedicamentoRequest;
import com.upsaude.api.request.embeddable.DosagemAdministracaoMedicamentoRequest;
import com.upsaude.api.request.embeddable.IdentificacaoMedicamentoRequest;
import com.upsaude.api.request.embeddable.RegistroControleMedicamentoRequest;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class MedicacaoRequest {
    private UUID fabricanteEntity;
    
    private IdentificacaoMedicamentoRequest identificacao;
    private DosagemAdministracaoMedicamentoRequest dosagemAdministracao;
    private ClassificacaoMedicamentoRequest classificacao;
    private RegistroControleMedicamentoRequest registroControle;
    private ContraindicacoesPrecaucoesMedicamentoRequest contraindicacoesPrecaucoes;
    private ConservacaoArmazenamentoMedicamentoRequest conservacaoArmazenamento;
    
    private String descricao;
    private String indicacoes;
    private String observacoes;
}
