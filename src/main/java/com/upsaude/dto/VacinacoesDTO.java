package com.upsaude.dto;

import lombok.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacinacoesDTO {
    private UUID id;
    private UUID estabelecimentoId;
    private UUID pacienteId;
    private UUID vacinaId;
    private UUID fabricanteId;
    private String lote;
    private Integer numeroDose;
    private OffsetDateTime dataAplicacao;
    private String localAplicacao;
    private UUID profissionalAplicador;
    private String reacaoAdversa;
    private String observacoes;
    private Boolean active;
}

