package com.upsaude.api.request.clinica.prontuario;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de historico clinico")
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
