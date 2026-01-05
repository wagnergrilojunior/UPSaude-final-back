package com.upsaude.api.request.farmacia;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.upsaude.validation.annotation.CNESValido;
import com.upsaude.validation.annotation.EmailValido;
import com.upsaude.validation.annotation.TelefoneValido;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados de farmácia")
public class FarmaciaRequest {

    @NotNull(message = "Nome é obrigatório")
    @NotBlank(message = "Nome não pode estar vazio")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String nome;

    @CNESValido
    @Size(max = 20, message = "Código CNES deve ter no máximo 20 caracteres")
    private String codigoCnes;

    @Size(max = 50, message = "Código da farmácia deve ter no máximo 50 caracteres")
    private String codigoFarmaciaInterno;

    @Size(max = 255, message = "Responsável técnico deve ter no máximo 255 caracteres")
    private String responsavelTecnico;

    @Size(max = 30, message = "CRF do responsável deve ter no máximo 30 caracteres")
    private String crfResponsavel;

    @TelefoneValido
    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    private String telefone;

    @EmailValido
    @Size(max = 150, message = "Email deve ter no máximo 150 caracteres")
    private String email;

    private String observacoes;

    private UUID estabelecimentoId;
}

