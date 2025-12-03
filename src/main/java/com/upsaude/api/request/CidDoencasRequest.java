package com.upsaude.api.request;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CidDoencasRequest {
    private String codigo;
    private String descricao;
    private String descricaoAbreviada;
    private String categoria;
    private String subcategoria;
    private String sexoRestricao;
    private Integer idadeMinima;
    private Integer idadeMaxima;
}
