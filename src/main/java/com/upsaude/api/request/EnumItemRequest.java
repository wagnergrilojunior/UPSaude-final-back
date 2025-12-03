package com.upsaude.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnumItemRequest {
    private String nome;
    private Integer codigo;
    private String descricao;
}
