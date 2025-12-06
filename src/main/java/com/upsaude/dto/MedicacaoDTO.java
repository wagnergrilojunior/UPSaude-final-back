package com.upsaude.dto;

import com.upsaude.dto.embeddable.ClassificacaoMedicamentoDTO;
import com.upsaude.dto.embeddable.ConservacaoArmazenamentoMedicamentoDTO;
import com.upsaude.dto.embeddable.ContraindicacoesPrecaucoesMedicamentoDTO;
import com.upsaude.dto.embeddable.DosagemAdministracaoMedicamentoDTO;
import com.upsaude.dto.embeddable.IdentificacaoMedicamentoDTO;
import com.upsaude.dto.embeddable.RegistroControleMedicamentoDTO;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicacaoDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private FabricantesMedicamentoDTO fabricanteEntity;
    private IdentificacaoMedicamentoDTO identificacao;
    private DosagemAdministracaoMedicamentoDTO dosagemAdministracao;
    private ClassificacaoMedicamentoDTO classificacao;
    private RegistroControleMedicamentoDTO registroControle;
    private ContraindicacoesPrecaucoesMedicamentoDTO contraindicacoesPrecaucoes;
    private ConservacaoArmazenamentoMedicamentoDTO conservacaoArmazenamento;
    private String descricao;
    private String indicacoes;
    private String observacoes;
}
