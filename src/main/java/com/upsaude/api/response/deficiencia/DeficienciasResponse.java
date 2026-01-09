package com.upsaude.api.response.deficiencia;

import com.upsaude.util.converter.TipoDeficienciaEnumDeserializer;
import com.upsaude.util.converter.TipoDeficienciaEnumSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.upsaude.enums.TipoDeficienciaEnum;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeficienciasResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private String nome;
    private String descricao;
    @JsonSerialize(using = TipoDeficienciaEnumSerializer.class)
    @JsonDeserialize(using = TipoDeficienciaEnumDeserializer.class)
    private TipoDeficienciaEnum tipoDeficiencia;
    private String cid10Relacionado;
    private Boolean permanente;
    private Boolean acompanhamentoContinuo;
}
