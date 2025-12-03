package com.upsaude.dto;

import com.upsaude.enums.TipoExameEnum;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogoExamesDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private TipoExameEnum tipoExame;
    private String nome;
    private String codigo;
    private String descricao;
    private String instrucoesPreparacao;
    private Integer prazoResultadoDias;
    private String observacoes;
}
