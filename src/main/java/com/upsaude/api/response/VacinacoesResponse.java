package com.upsaude.api.response;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacinacoesResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteResponse paciente;
    private VacinasResponse vacina;
    private FabricantesVacinaResponse fabricante;
    private String lote;
    private Integer numeroDose;
    private OffsetDateTime dataAplicacao;
    private String localAplicacao;
    private UUID profissionalAplicador;
    private String reacaoAdversa;
    private String observacoes;
}
