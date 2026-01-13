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
@Schema(description = "Resposta com dados do CIAP-2")
public class Ciap2Response {
    private UUID id;
    private String codigo;
    private String descricao;
    private String capitulo;
    private Boolean ativo;
    private OffsetDateTime dataSincronizacao;
}
