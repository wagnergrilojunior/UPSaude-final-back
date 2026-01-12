package com.upsaude.api.request.clinica.prontuario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
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
@Schema(description = "Dados de doença do paciente")
public class DoencaPacienteRequest {

    @NotNull(message = "Prontuário é obrigatório")
    private UUID prontuario;

    @Schema(description = "ID do Diagnóstico CID-10 (opcional se CIAP-2 for fornecido)")
    private UUID diagnostico;

    @Schema(description = "ID do Diagnóstico CIAP-2 (opcional se CID-10 for fornecido)")
    private UUID ciap2;

    @NotNull(message = "Tipo de catálogo é obrigatório")
    @Schema(description = "Tipo de catálogo: CID10, CIAP2, OUTRO", example = "CID10")
    private String tipoCatalogo;

    @Schema(description = "Código do diagnóstico no catálogo original")
    private String codigo;

    @Schema(description = "Descrição personalizada do diagnóstico pelo médico")
    private String descricaoPersonalizada;

    @NotNull(message = "Data de diagnóstico é obrigatória")
    private LocalDate dataDiagnostico;

    @NotNull(message = "Status ativa é obrigatório")
    private Boolean ativa;

    @Schema(description = "Status clínico: ativo, resolvido, erro", example = "ativo")
    private String status;

    @Schema(description = "Indica se é uma condição crônica")
    private Boolean cronico;

    @Size(max = 1000, message = "Observações deve ter no máximo 1000 caracteres")
    private String observacoes;
}
