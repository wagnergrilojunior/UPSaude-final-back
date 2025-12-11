package com.upsaude.api.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.TipoExameEnum;
import com.upsaude.util.converter.TipoExameEnumDeserializer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogoExamesRequest {
    @JsonDeserialize(using = TipoExameEnumDeserializer.class)
    @NotNull(message = "Tipo de exame é obrigatório")
    private TipoExameEnum tipoExame;
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String nome;
    @Size(max = 50, message = "Código deve ter no máximo 50 caracteres")
    private String codigo;
    private String descricao;
    private String instrucoesPreparacao;
    private Integer prazoResultadoDias;
    private String observacoes;
}
