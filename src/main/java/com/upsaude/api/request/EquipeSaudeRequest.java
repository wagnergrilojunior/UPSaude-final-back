package com.upsaude.api.request;

import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoEquipeEnum;
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
    private TipoEquipeEnum tipoEquipe;
    
    @NotNull(message = "Estabelecimento é obrigatório")
    private UUID estabelecimento;
    
    @NotNull(message = "Data de ativação é obrigatória")
    private OffsetDateTime dataAtivacao;
    
    private OffsetDateTime dataInativacao;
    
    @NotNull(message = "Status é obrigatório")
    private StatusAtivoEnum status;
    private String observacoes;
}
