package com.upsaude.api.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.StatusReceitaEnum;
import com.upsaude.util.converter.StatusReceitaEnumDeserializer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
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
public class ReceitasMedicasRequest {
    @NotNull(message = "Médico é obrigatório")
    private UUID medico;
    
    @NotNull(message = "Paciente é obrigatório")
    private UUID paciente;
    
    @NotBlank(message = "Número da receita é obrigatório")
    @Size(max = 50, message = "Número da receita deve ter no máximo 50 caracteres")
    private String numeroReceita;
    
    @NotNull(message = "Data de prescrição é obrigatória")
    private OffsetDateTime dataPrescricao;
    
    @NotNull(message = "Data de validade é obrigatória")
    private OffsetDateTime dataValidade;
    
    @NotNull(message = "Indicação de uso contínuo é obrigatória")
    private Boolean usoContinuo;
    
    @Size(max = 1000, message = "Observações devem ter no máximo 1000 caracteres")
    private String observacoes;
    
    @NotNull(message = "Status é obrigatório")
    @JsonDeserialize(using = StatusReceitaEnumDeserializer.class)
    private StatusReceitaEnum status;
    
    @Size(max = 50, message = "Origem da receita deve ter no máximo 50 caracteres")
    private String origemReceita;
    
    private UUID cidPrincipal;
    
    @Builder.Default
    private List<UUID> medicacoes = new ArrayList<>();
}
