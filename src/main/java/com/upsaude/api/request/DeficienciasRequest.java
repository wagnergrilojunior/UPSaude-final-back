package com.upsaude.api.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.TipoDeficienciaEnum;
import com.upsaude.util.converter.TipoDeficienciaEnumDeserializer;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de deficiências")
public class DeficienciasRequest {
    private String nome;
    private String descricao;
    @JsonDeserialize(using = TipoDeficienciaEnumDeserializer.class)
    private TipoDeficienciaEnum tipoDeficiencia;
    private String cid10Relacionado;
}
