package com.upsaude.api.request.clinica.prontuario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de historico clinico")
public class HistoricoClinicoRequest {
    @NotNull(message = "Paciente é obrigatório")
    private UUID paciente;

    private UUID profissional;
    private UUID atendimento;
    private UUID agendamento;
    private UUID cirurgia;

    @NotNull(message = "Data do registro é obrigatória")
    private OffsetDateTime dataRegistro;

    @NotBlank(message = "Tipo de registro é obrigatório")
    @Size(max = 50, message = "Tipo de registro deve ter no máximo 50 caracteres")
    private String tipoRegistro;

    @Size(max = 255, message = "Título deve ter no máximo 255 caracteres")
    private String titulo;

    @NotBlank(message = "Descrição é obrigatória")
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
