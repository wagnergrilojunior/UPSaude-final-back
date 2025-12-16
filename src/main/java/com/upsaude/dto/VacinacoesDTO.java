package com.upsaude.dto;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacinacoesDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private EstabelecimentosDTO estabelecimento;
    private PacienteDTO paciente;
    private VacinasDTO vacina;
    private FabricantesVacinaDTO fabricante;
    private String lote;
    private Integer numeroDose;
    private OffsetDateTime dataAplicacao;
    private String localAplicacao;
    private UUID profissionalAplicador;
    private String reacaoAdversa;
    private String observacoes;
}
