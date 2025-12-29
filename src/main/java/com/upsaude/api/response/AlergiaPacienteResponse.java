package com.upsaude.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.upsaude.enums.SeveridadeAlergiaEnum;
import com.upsaude.enums.TipoAlergiaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Informação clínica declarada do paciente sobre alergia")
public class AlergiaPacienteResponse {

    @Schema(description = "ID da alergia", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Schema(description = "ID do paciente", example = "660e8400-e29b-41d4-a716-446655440001")
    private UUID pacienteId;

    @Schema(description = "Nome da substância que causa alergia", example = "Penicilina")
    private String substancia;

    @Schema(description = "Categoria geral da alergia", example = "MEDICAMENTO")
    private TipoAlergiaEnum tipo;

    @Schema(description = "Descrição da reação alérgica observada", example = "Urticária e coceira")
    private String reacao;

    @Schema(description = "Gravidade da alergia", example = "GRAVE")
    private SeveridadeAlergiaEnum gravidade;

    @Schema(description = "Observações adicionais sobre a alergia")
    private String observacao;

    @Schema(description = "Indica se a alergia está ativa", example = "true")
    private Boolean ativo;

    @Schema(description = "Data de registro da alergia")
    private OffsetDateTime dataRegistro;

    @Schema(description = "Data de criação do registro")
    private OffsetDateTime createdAt;

    @Schema(description = "Data de última atualização do registro")
    private OffsetDateTime updatedAt;
}

