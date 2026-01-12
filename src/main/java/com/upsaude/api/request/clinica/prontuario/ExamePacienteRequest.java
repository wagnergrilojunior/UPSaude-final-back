package com.upsaude.api.request.clinica.prontuario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados de exame do paciente")
public class ExamePacienteRequest {

    @NotNull(message = "Prontuário é obrigatório")
    private UUID prontuario;

    private UUID atendimentoId;

    private String status;
    private String intent;
    private String priority;
    private String category;

    @Size(max = 50, message = "Tipo de exame deve ter no máximo 50 caracteres")
    private String tipoExame;

    @Size(max = 200, message = "Nome do exame deve ter no máximo 200 caracteres")
    private String nomeExame;

    private OffsetDateTime dataSolicitacao;

    private OffsetDateTime dataExame;

    private OffsetDateTime dataResultado;

    private UUID profissionalSolicitante;

    private UUID profissionalResponsavel;

    @Size(max = 10000, message = "Laudo deve ter no máximo 10000 caracteres")
    private String laudo;

    private String resultados;

    @Size(max = 200, message = "Unidade laboratório deve ter no máximo 200 caracteres")
    private String unidadeLaboratorio;

    @Size(max = 1000, message = "Observações deve ter no máximo 1000 caracteres")
    private String observacoes;

    private UUID procedimento;

    private UUID diagnosticoRelacionado;

    private UUID catalogoExameId;
}
