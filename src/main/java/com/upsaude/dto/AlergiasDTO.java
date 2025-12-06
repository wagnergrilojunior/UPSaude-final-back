package com.upsaude.dto;

import com.upsaude.dto.embeddable.ClassificacaoAlergiaDTO;
import com.upsaude.dto.embeddable.PrevencaoTratamentoAlergiaDTO;
import com.upsaude.dto.embeddable.ReacoesAlergiaDTO;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlergiasDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private String nome;
    private String nomeCientifico;
    private String codigoInterno;
    private ClassificacaoAlergiaDTO classificacao;
    private ReacoesAlergiaDTO reacoes;
    private PrevencaoTratamentoAlergiaDTO prevencaoTratamento;
    private String descricao;
    private String substanciasRelacionadas;
    private String observacoes;
}
