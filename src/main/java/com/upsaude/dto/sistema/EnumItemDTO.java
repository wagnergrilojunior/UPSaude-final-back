package com.upsaude.dto.sistema;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnumItemDTO {
    private String nome;
    private Integer codigo;
    private String descricao;
}
