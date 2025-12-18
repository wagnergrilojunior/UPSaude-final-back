package com.upsaude.dto.saude_publica.vacina;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import com.upsaude.dto.estabelecimento.EstabelecimentosDTO;
import com.upsaude.dto.paciente.PacienteDTO;


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
