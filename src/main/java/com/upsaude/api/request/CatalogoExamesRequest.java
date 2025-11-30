package com.upsaude.api.request;

import com.upsaude.enums.TipoExameEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogoExamesRequest {
    @NotNull(message = "Tipo de exame é obrigatório")
    private TipoExameEnum tipoExame;

    @NotNull(message = "Nome é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String nome;

    @Size(max = 50, message = "Código deve ter no máximo 50 caracteres")
    private String codigo;

    private String descricao;
    private Boolean requerPreparacao;
    private String instrucoesPreparacao;
    private Integer prazoResultadoDias;
    private String observacoes;
    private UUID estabelecimentoId;
}

