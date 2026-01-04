package com.upsaude.api.request.embeddable;

import com.upsaude.validation.annotation.CPFValido;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados dos responsáveis do estabelecimento")
public class ResponsaveisEstabelecimentoRequest {

    private UUID responsavelTecnico;

    private UUID responsavelAdministrativo;

    @Size(max = 255, message = "Nome do responsável legal deve ter no máximo 255 caracteres")
    private String responsavelLegalNome;

    @CPFValido
    @Size(max = 11, message = "CPF do responsável legal deve ter no máximo 11 caracteres")
    private String responsavelLegalCpf;
}

