package com.upsaude.api.request.embeddable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
@Schema(description = "Dados de identificacao medicamento")
public class IdentificacaoMedicamentoRequest {
    @NotBlank(message = "Princípio ativo é obrigatório")
    @Size(max = 255, message = "Princípio ativo deve ter no máximo 255 caracteres")
    private String principioAtivo;

    @NotBlank(message = "Nome comercial é obrigatório")
    @Size(max = 255, message = "Nome comercial deve ter no máximo 255 caracteres")
    private String nomeComercial;

    @Size(max = 255, message = "Nome genérico deve ter no máximo 255 caracteres")
    private String nomeGenerico;

    @Size(max = 50, message = "Código interno deve ter no máximo 50 caracteres")
    private String codigoInterno;

    @Size(max = 20, message = "Código CATMAT deve ter no máximo 20 caracteres")
    private String catmatCodigo;

    @Size(max = 50, message = "Código ANVISA deve ter no máximo 50 caracteres")
    private String codigoAnvisa;

    @Size(max = 50, message = "Código TUSS deve ter no máximo 50 caracteres")
    private String codigoTuss;

    @Size(max = 50, message = "Código SIGTAP deve ter no máximo 50 caracteres")
    private String codigoSigtap;
}
