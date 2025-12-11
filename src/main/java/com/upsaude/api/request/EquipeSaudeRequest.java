package com.upsaude.api.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoEquipeEnum;
import com.upsaude.util.converter.StatusAtivoEnumDeserializer;
import com.upsaude.util.converter.TipoEquipeEnumDeserializer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipeSaudeRequest {
    @NotBlank(message = "INE é obrigatório")
    @Size(max = 15, message = "INE deve ter no máximo 15 caracteres")
    private String ine;

    @NotBlank(message = "Nome de referência é obrigatório")
    @Size(max = 255, message = "Nome de referência deve ter no máximo 255 caracteres")
    private String nomeReferencia;

    @NotNull(message = "Tipo de equipe é obrigatório")
    @JsonDeserialize(using = TipoEquipeEnumDeserializer.class)
    private TipoEquipeEnum tipoEquipe;

    @NotNull(message = "Estabelecimento é obrigatório")
    private UUID estabelecimento;

    @NotNull(message = "Data de ativação é obrigatória")
    private OffsetDateTime dataAtivacao;

    private OffsetDateTime dataInativacao;

    @NotNull(message = "Status é obrigatório")
    @JsonDeserialize(using = StatusAtivoEnumDeserializer.class)
    private StatusAtivoEnum status;
    private String observacoes;
}
