package com.upsaude.api.request;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamesRequest {
    private UUID paciente;
    private UUID catalogoExame;
    private UUID atendimento;
    private UUID consulta;
    private UUID profissionalSolicitante;
    private UUID medicoSolicitante;
    private String tipoExame;
    private String nomeExame;
    private OffsetDateTime dataSolicitacao;
    private OffsetDateTime dataExame;
    private OffsetDateTime dataResultado;
    private String unidadeLaboratorio;
    private UUID estabelecimentoRealizador;
    private UUID profissionalResponsavel;
    private UUID medicoResponsavel;
    private String resultados;
    private String laudo;
    private String observacoes;
}
