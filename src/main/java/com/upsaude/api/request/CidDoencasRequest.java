package com.upsaude.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de cid doencas")
public class CidDoencasRequest {
    @NotBlank(message = "Código CID é obrigatório")
    @Pattern(regexp = "^[A-Z]\\d{2}(\\.\\d{1,2})?$", message = "Código CID deve seguir o formato A99 ou A99.99")
    @Size(max = 10, message = "Código CID deve ter no máximo 10 caracteres")
    private String codigo;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
    private String descricao;

    @Size(max = 255, message = "Descrição abreviada deve ter no máximo 255 caracteres")
    private String descricaoAbreviada;

    @Size(max = 100, message = "Categoria deve ter no máximo 100 caracteres")
    private String categoria;

    @Size(max = 100, message = "Subcategoria deve ter no máximo 100 caracteres")
    private String subcategoria;

    @Pattern(regexp = "^[MF]?$", message = "Restrição de sexo deve ser M, F ou vazio")
    private String sexoRestricao;

    private Integer idadeMinima;

    private Integer idadeMaxima;
}
