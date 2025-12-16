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
@Schema(description = "Dados de vacinacoes")
public class VacinacoesRequest {
    @NotNull(message = "Paciente é obrigatório")
    private UUID paciente;

    @NotNull(message = "Vacina é obrigatória")
    private UUID vacina;

    private UUID fabricante;
    private UUID estabelecimento;

    @Size(max = 100, message = "Lote deve ter no máximo 100 caracteres")
    private String lote;

    @NotNull(message = "Número da dose é obrigatório")
    private Integer numeroDose;

    @NotNull(message = "Data de aplicação é obrigatória")
    private OffsetDateTime dataAplicacao;

    @Size(max = 100, message = "Local aplicação deve ter no máximo 100 caracteres")
    private String localAplicacao;

    private UUID profissionalAplicador;
    private String reacaoAdversa;
    private String observacoes;
}
