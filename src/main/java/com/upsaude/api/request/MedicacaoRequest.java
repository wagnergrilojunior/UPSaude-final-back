package com.upsaude.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.upsaude.api.request.embeddable.ClassificacaoMedicamentoRequest;
import com.upsaude.api.request.embeddable.ConservacaoArmazenamentoMedicamentoRequest;
import com.upsaude.api.request.embeddable.ContraindicacoesPrecaucoesMedicamentoRequest;
import com.upsaude.api.request.embeddable.DosagemAdministracaoMedicamentoRequest;
import com.upsaude.api.request.embeddable.IdentificacaoMedicamentoRequest;
import com.upsaude.api.request.embeddable.RegistroControleMedicamentoRequest;
import jakarta.validation.Valid;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class MedicacaoRequest {
    private UUID fabricanteEntity;

    @Valid
    private IdentificacaoMedicamentoRequest identificacao;

    @Valid
    private DosagemAdministracaoMedicamentoRequest dosagemAdministracao;

    @Valid
    private ClassificacaoMedicamentoRequest classificacao;

    @Valid
    private RegistroControleMedicamentoRequest registroControle;

    @Valid
    private ContraindicacoesPrecaucoesMedicamentoRequest contraindicacoesPrecaucoes;

    @Valid
    private ConservacaoArmazenamentoMedicamentoRequest conservacaoArmazenamento;

    @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
    private String descricao;

    @Size(max = 1000, message = "Indicações deve ter no máximo 1000 caracteres")
    private String indicacoes;

    @Size(max = 1000, message = "Observações deve ter no máximo 1000 caracteres")
    private String observacoes;
}
