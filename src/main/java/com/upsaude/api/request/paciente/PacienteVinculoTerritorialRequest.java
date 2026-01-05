package com.upsaude.api.request.paciente;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.upsaude.enums.OrigemVinculoTerritorialEnum;
import com.upsaude.validation.annotation.CNESValido;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados de vínculo territorial do paciente")
public class PacienteVinculoTerritorialRequest {

    @NotNull(message = "Paciente é obrigatório")
    private UUID paciente;

    @Size(max = 7, message = "Código IBGE do município deve ter no máximo 7 caracteres")
    private String municipioIbge;

    @CNESValido
    @Size(max = 7, message = "CNES do estabelecimento deve ter no máximo 7 caracteres")
    private String cnesEstabelecimento;

    @Size(max = 10, message = "INE da equipe deve ter no máximo 10 caracteres")
    private String ineEquipe;

    @Size(max = 10, message = "Microárea deve ter no máximo 10 caracteres")
    private String microarea;

    @NotNull(message = "Data de início é obrigatória")
    private LocalDate dataInicio;

    private LocalDate dataFim;

    private OrigemVinculoTerritorialEnum origem;

    private String observacoes;
}

