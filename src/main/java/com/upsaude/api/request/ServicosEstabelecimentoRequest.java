package com.upsaude.api.request;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de servicos estabelecimento")
public class ServicosEstabelecimentoRequest {
    private UUID estabelecimento;
    private String nome;
    private String codigo;
    private String codigoCnes;
    private String descricao;
}
