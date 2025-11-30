package com.upsaude.dto;

import com.upsaude.enums.TipoExameEnum;
import lombok.*;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogoExamesDTO {
    private UUID id;
    private TipoExameEnum tipoExame;
    private String nome;
    private String codigo;
    private String descricao;
    private Boolean requerPreparacao;
    private String instrucoesPreparacao;
    private Integer prazoResultadoDias;
    private String observacoes;
    private Boolean active;
}

