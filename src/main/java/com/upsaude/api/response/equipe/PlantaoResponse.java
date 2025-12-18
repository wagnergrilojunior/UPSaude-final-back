package com.upsaude.api.response.equipe;
import com.upsaude.api.response.estabelecimento.EstabelecimentosResponse;
import com.upsaude.api.response.profissional.MedicosResponse;
import com.upsaude.api.response.profissional.ProfissionaisSaudeResponse;

import com.upsaude.enums.TipoPlantaoEnum;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlantaoResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private EstabelecimentosResponse estabelecimento;
    private ProfissionaisSaudeResponse profissional;
    private MedicosResponse medico;
    private OffsetDateTime dataHoraInicio;
    private OffsetDateTime dataHoraFim;
    private OffsetDateTime dataHoraFimPrevisto;
    private TipoPlantaoEnum tipoPlantao;
    private String setor;
    private Integer leitoInicio;
    private Integer leitoFim;
    private BigDecimal valorPlantao;
    private Boolean temHoraExtra;
    private BigDecimal valorHoraExtra;
    private Boolean confirmado;
    private OffsetDateTime dataConfirmacao;
    private Boolean cancelado;
    private OffsetDateTime dataCancelamento;
    private String motivoCancelamento;
    private String observacoes;
}
