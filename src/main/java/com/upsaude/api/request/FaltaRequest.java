package com.upsaude.api.request;

import com.upsaude.enums.TipoFaltaEnum;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FaltaRequest {
    private UUID profissional;
    private UUID medico;
    private LocalDate dataFalta;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private TipoFaltaEnum tipoFalta;
    private String justificativa;
    private String documentoComprobatorio;
    private String numeroAtestado;
    private Boolean aprovado;
    private UUID aprovadoPor;
    private OffsetDateTime dataAprovacao;
    private String observacoes;
}
