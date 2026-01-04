package com.upsaude.api.request.deficiencia;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.TipoDeficienciaEnum;
import com.upsaude.util.converter.TipoDeficienciaEnumDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de deficiências")
public class DeficienciasRequest {
    @NotBlank(message = "Nome da deficiência é obrigatório")
    @Size(max = 100, message = "Nome da deficiência deve ter no máximo 100 caracteres")
    private String nome;

    @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
    private String descricao;

    @JsonDeserialize(using = TipoDeficienciaEnumDeserializer.class)
    private TipoDeficienciaEnum tipoDeficiencia;

    @Pattern(regexp = "^[A-Z]\\d{2}(\\.\\d{1,2})?$", message = "Código CID-10 deve seguir o formato A99 ou A99.99")
    @Size(max = 10, message = "Código CID-10 deve ter no máximo 10 caracteres")
    private String cid10Relacionado;

    @NotNull(message = "Campo permanente é obrigatório")
    private Boolean permanente;

    @NotNull(message = "Campo acompanhamentoContinuo é obrigatório")
    private Boolean acompanhamentoContinuo;
}
