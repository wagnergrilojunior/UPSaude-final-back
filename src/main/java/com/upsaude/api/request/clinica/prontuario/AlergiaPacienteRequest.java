package com.upsaude.api.request.clinica.prontuario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
@Schema(description = "Dados de alergia do paciente")
public class AlergiaPacienteRequest {

    @NotNull(message = "Prontuário é obrigatório")
    private UUID prontuario;

    @NotBlank(message = "Tipo de alergia é obrigatório")
    @Size(max = 50, message = "Tipo de alergia deve ter no máximo 50 caracteres")
    private String tipoAlergia;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(max = 10000, message = "Descrição deve ter no máximo 10000 caracteres")
    private String descricao;

    private LocalDate dataDiagnostico;

    @Size(max = 100, message = "Gravidade deve ter no máximo 100 caracteres")
    private String gravidade;

    @Size(max = 1000, message = "Observações deve ter no máximo 1000 caracteres")
    private String observacoes;

    private UUID diagnosticoRelacionado;

    private UUID alergeno;

    private UUID reacaoAdversaCatalogo;

    private UUID criticidade;

    private UUID categoriaAgente;
    private String clinicalStatus;
    private String verificationStatus;
    private String grauCerteza;
}
