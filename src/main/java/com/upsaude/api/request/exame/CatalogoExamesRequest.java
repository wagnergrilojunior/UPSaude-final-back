package com.upsaude.api.request.exame;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.TipoExameEnum;
import com.upsaude.util.converter.TipoExameEnumDeserializer;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de catalogo exames")
public class CatalogoExamesRequest {
    @JsonDeserialize(using = TipoExameEnumDeserializer.class)
    private TipoExameEnum tipoExame;
    private String nome;
    private String codigo;
    private String descricao;
    private String instrucoesPreparacao;
    private Integer prazoResultadoDias;
    private String observacoes;
}
