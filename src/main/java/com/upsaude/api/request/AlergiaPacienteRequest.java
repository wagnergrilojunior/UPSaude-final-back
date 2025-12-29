package com.upsaude.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.upsaude.enums.SeveridadeAlergiaEnum;
import com.upsaude.enums.TipoAlergiaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@Schema(description = "Informação clínica declarada do paciente sobre alergia")
public class AlergiaPacienteRequest {

    @NotBlank(message = "Substância é obrigatória")
    @Size(max = 255, message = "Substância deve ter no máximo 255 caracteres")
    @Schema(description = "Nome da substância que causa alergia", example = "Penicilina", required = true)
    private String substancia;

    @NotNull(message = "Tipo de alergia é obrigatório")
    @Schema(description = "Categoria geral da alergia (MEDICAMENTO, ALIMENTO, AMBIENTAL, CONTATO, INSETO, OUTRO)", example = "MEDICAMENTO", required = true)
    private TipoAlergiaEnum tipo;

    @Size(max = 500, message = "Reação deve ter no máximo 500 caracteres")
    @Schema(description = "Descrição da reação alérgica observada", example = "Urticária e coceira")
    private String reacao;

    @NotNull(message = "Gravidade é obrigatória")
    @Schema(description = "Gravidade da alergia (LEVE, MODERADA, GRAVE)", example = "GRAVE", required = true)
    private SeveridadeAlergiaEnum gravidade;

    @Size(max = 1000, message = "Observação deve ter no máximo 1000 caracteres")
    @Schema(description = "Observações adicionais sobre a alergia")
    private String observacao;
}

