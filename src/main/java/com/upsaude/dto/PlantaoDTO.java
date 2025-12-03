package com.upsaude.dto;

import com.upsaude.enums.TipoPlantaoEnum;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlantaoDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private ProfissionaisSaudeDTO profissional;
    private MedicosDTO medico;
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
