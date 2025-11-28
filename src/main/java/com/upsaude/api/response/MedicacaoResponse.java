package com.upsaude.api.response;

import com.upsaude.entity.embeddable.ClassificacaoMedicamento;
import com.upsaude.entity.embeddable.ConservacaoArmazenamentoMedicamento;
import com.upsaude.entity.embeddable.ContraindicacoesPrecaucoesMedicamento;
import com.upsaude.entity.embeddable.DosagemAdministracaoMedicamento;
import com.upsaude.entity.embeddable.IdentificacaoMedicamento;
import com.upsaude.entity.embeddable.RegistroControleMedicamento;
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
public class MedicacaoResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private UUID fabricanteId;
    private String fabricanteNome;
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
