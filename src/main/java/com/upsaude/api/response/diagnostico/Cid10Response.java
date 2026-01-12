package com.upsaude.api.response.diagnostico;

import java.time.OffsetDateTime;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resposta com dados do CID-10")
public class Cid10Response {
    private UUID id;
    private String subcat;
    private String descricao;
    private Boolean ativo;
    private OffsetDateTime dataSincronizacao;
}
