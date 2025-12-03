package com.upsaude.dto;

import com.upsaude.enums.CondicaoMoradiaEnum;
import com.upsaude.enums.EscolaridadeEnum;
import com.upsaude.enums.NacionalidadeEnum;
import com.upsaude.enums.RacaCorEnum;
import com.upsaude.enums.SituacaoFamiliarEnum;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DadosSociodemograficosDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteDTO paciente;
    private RacaCorEnum racaCor;
    private NacionalidadeEnum nacionalidade;
    private String paisNascimento;
    private String naturalidade;
    private String municipioNascimentoIbge;
    private EscolaridadeEnum escolaridade;
    private String ocupacaoProfissao;
    private Integer tempoSituacaoRua;
    private CondicaoMoradiaEnum condicaoMoradia;
    private SituacaoFamiliarEnum situacaoFamiliar;
}
