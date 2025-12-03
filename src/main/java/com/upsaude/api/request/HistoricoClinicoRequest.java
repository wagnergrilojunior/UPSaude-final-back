package com.upsaude.api.request;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoClinicoRequest {
    private UUID paciente;
    private UUID profissional;
    private UUID atendimento;
    private UUID agendamento;
    private UUID exame;
    private UUID receita;
    private UUID cirurgia;
    private OffsetDateTime dataRegistro;
    private String tipoRegistro;
    private String titulo;
    private String descricao;
    private String observacoes;
    private String observacoesInternas;
    private String tags;
    private UUID registradoPor;
    private UUID revisadoPor;
    private OffsetDateTime dataRevisao;
    private Integer versao;
    private Boolean visivelParaPaciente;
    private Boolean compartilhadoOutrosEstabelecimentos;
}
