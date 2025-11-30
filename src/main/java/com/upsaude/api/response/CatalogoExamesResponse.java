package com.upsaude.api.response;

import com.upsaude.enums.TipoExameEnum;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogoExamesResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private TipoExameEnum tipoExame;
    private String nome;
    private String codigo;
    private String descricao;
    private Boolean requerPreparacao;
    private String instrucoesPreparacao;
    private Integer prazoResultadoDias;
    private String observacoes;
}

