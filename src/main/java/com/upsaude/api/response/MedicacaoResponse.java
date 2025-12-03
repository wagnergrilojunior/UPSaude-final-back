package com.upsaude.api.response;

import com.upsaude.entity.embeddable.ClassificacaoMedicamento;
import com.upsaude.entity.embeddable.ConservacaoArmazenamentoMedicamento;
import com.upsaude.entity.embeddable.ContraindicacoesPrecaucoesMedicamento;
import com.upsaude.entity.embeddable.DosagemAdministracaoMedicamento;
import com.upsaude.entity.embeddable.IdentificacaoMedicamento;
import com.upsaude.entity.embeddable.RegistroControleMedicamento;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicacaoResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private FabricantesMedicamentoResponse fabricanteEntity;
    private IdentificacaoMedicamento identificacao;
    private DosagemAdministracaoMedicamento dosagemAdministracao;
    private ClassificacaoMedicamento classificacao;
    private RegistroControleMedicamento registroControle;
    private ContraindicacoesPrecaucoesMedicamento contraindicacoesPrecaucoes;
    private ConservacaoArmazenamentoMedicamento conservacaoArmazenamento;
    private String descricao;
    private String indicacoes;
    private String observacoes;
}
