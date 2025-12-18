package com.upsaude.api.request.sistema;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de enum item")
public class EnumItemRequest {
    private String nome;
    private Integer codigo;
    private String descricao;
}
