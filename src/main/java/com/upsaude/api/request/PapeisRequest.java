package com.upsaude.api.request;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PapeisRequest {
    private String nome;
    private String slug;
    private String descricao;
    private String permissoesJson;
    private Boolean papelDoSistema;
}
