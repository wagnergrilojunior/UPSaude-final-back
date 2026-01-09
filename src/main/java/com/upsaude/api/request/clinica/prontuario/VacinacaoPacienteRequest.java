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
@Schema(description = "Dados de vacinação do paciente")
public class VacinacaoPacienteRequest {

    @NotNull(message = "Prontuário é obrigatório")
    private UUID prontuario;

    @NotNull(message = "Vacina é obrigatória")
    private UUID vacinaId;

    private UUID profissionalAplicador;

    @NotNull(message = "Data de aplicação é obrigatória")
    private OffsetDateTime dataAplicacao;

    @NotNull(message = "Número da dose é obrigatório")
    private Integer numeroDose;

    @Size(max = 100, message = "Local de aplicação deve ter no máximo 100 caracteres")
    private String localAplicacao;

    @Size(max = 50, message = "Lote deve ter no máximo 50 caracteres")
    private String lote;

    @Size(max = 1000, message = "Reação adversa deve ter no máximo 1000 caracteres")
    private String reacaoAdversa;

    @Size(max = 1000, message = "Observações deve ter no máximo 1000 caracteres")
    private String observacoes;

    private UUID procedimento;

    private UUID diagnosticoRelacionado;
}

