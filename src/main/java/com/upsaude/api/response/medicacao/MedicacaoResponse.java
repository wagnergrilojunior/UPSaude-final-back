package com.upsaude.api.response.medicacao;

import com.upsaude.api.response.embeddable.ClassificacaoMedicamentoResponse;
import com.upsaude.api.response.embeddable.ConservacaoArmazenamentoMedicamentoResponse;
import com.upsaude.api.response.embeddable.ContraindicacoesPrecaucoesMedicamentoResponse;
import com.upsaude.api.response.embeddable.DosagemAdministracaoMedicamentoResponse;
import com.upsaude.api.response.embeddable.IdentificacaoMedicamentoResponse;
import com.upsaude.api.response.embeddable.RegistroControleMedicamentoResponse;
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
public class MedicacaoResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private FabricantesMedicamentoResponse fabricanteEntity;
    private IdentificacaoMedicamentoResponse identificacao;
    private DosagemAdministracaoMedicamentoResponse dosagemAdministracao;
    private ClassificacaoMedicamentoResponse classificacao;
    private RegistroControleMedicamentoResponse registroControle;
    private ContraindicacoesPrecaucoesMedicamentoResponse contraindicacoesPrecaucoes;
    private ConservacaoArmazenamentoMedicamentoResponse conservacaoArmazenamento;
    private String descricao;
    private String indicacoes;
    private String observacoes;
}
