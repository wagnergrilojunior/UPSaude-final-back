package com.upsaude.dto;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CidDoencasDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private String codigo;
    private String descricao;
    private String descricaoAbreviada;
    private String categoria;
    private String subcategoria;
    private String sexoRestricao;
    private Integer idadeMinima;
    private Integer idadeMaxima;
}
