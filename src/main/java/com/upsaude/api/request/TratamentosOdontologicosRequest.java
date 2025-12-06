package com.upsaude.api.request;

import com.upsaude.entity.TratamentosOdontologicos.StatusTratamento;
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
public class TratamentosOdontologicosRequest {
    @NotNull(message = "Paciente é obrigatório")
    private UUID paciente;
    
    @NotNull(message = "Profissional é obrigatório")
    private UUID profissional;
    
    @NotBlank(message = "Título é obrigatório")
    @Size(max = 255, message = "Título deve ter no máximo 255 caracteres")
    private String titulo;
    private String descricao;
    private OffsetDateTime dataInicio;
    private OffsetDateTime dataFim;
    private StatusTratamento status;
    private String observacoes;
}
