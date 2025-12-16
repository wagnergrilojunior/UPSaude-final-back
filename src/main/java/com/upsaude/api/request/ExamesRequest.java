package com.upsaude.api.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de exames")
public class ExamesRequest {
    @NotNull(message = "Paciente é obrigatório")
    private UUID paciente;
    private UUID catalogoExame;
    private UUID atendimento;
    private UUID consulta;
    private UUID profissionalSolicitante;
    private UUID medicoSolicitante;
    @Size(max = 100, message = "Tipo exame deve ter no máximo 100 caracteres")
    private String tipoExame;

    @Size(max = 255, message = "Nome exame deve ter no máximo 255 caracteres")
    private String nomeExame;
    private OffsetDateTime dataSolicitacao;
    private OffsetDateTime dataExame;
    private OffsetDateTime dataResultado;
    @Size(max = 255, message = "Unidade laboratório deve ter no máximo 255 caracteres")
    private String unidadeLaboratorio;
    private UUID estabelecimentoRealizador;
    private UUID profissionalResponsavel;
    private UUID medicoResponsavel;
    @Size(max = 5000, message = "Resultados deve ter no máximo 5000 caracteres")
    private String resultados;

    @Size(max = 5000, message = "Laudo deve ter no máximo 5000 caracteres")
    private String laudo;

    @Size(max = 1000, message = "Observações deve ter no máximo 1000 caracteres")
    private String observacoes;
}
