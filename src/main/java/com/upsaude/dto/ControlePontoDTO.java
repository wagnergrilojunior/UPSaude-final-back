package com.upsaude.dto;

import com.upsaude.enums.TipoPontoEnum;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ControlePontoDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private ProfissionaisSaudeDTO profissional;
    private MedicosDTO medico;
    private OffsetDateTime dataHora;
    private LocalDate dataPonto;
    private TipoPontoEnum tipoPonto;
    private Double latitude;
    private Double longitude;
    private String enderecoIp;
    private String observacoes;
    private String justificativa;
    private Boolean aprovado;
    private UUID aprovadoPor;
    private OffsetDateTime dataAprovacao;
}
