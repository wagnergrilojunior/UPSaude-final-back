package com.upsaude.api.request;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServicosEstabelecimentoRequest {
    private UUID estabelecimento;
    private String nome;
    private String codigo;
    private String codigoCnes;
    private String descricao;
}
