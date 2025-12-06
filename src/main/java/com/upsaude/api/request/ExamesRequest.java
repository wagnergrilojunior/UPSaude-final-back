package com.upsaude.api.request;

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
public class ExamesRequest {
    @NotNull(message = "Paciente é obrigatório")
    private UUID paciente;
    private UUID catalogoExame;
    private UUID atendimento;
    private UUID consulta;
    private UUID profissionalSolicitante;
    private UUID medicoSolicitante;
    @Size(max = 100, message = "Tipo exame deve ter no máximo 100 caracteres")
    private String tipoExame;
    
    @Size(max = 255, message = "Nome exame deve ter no máximo 255 caracteres")
    private String nomeExame;
    private OffsetDateTime dataSolicitacao;
    private OffsetDateTime dataExame;
    private OffsetDateTime dataResultado;
    @Size(max = 255, message = "Unidade laboratório deve ter no máximo 255 caracteres")
    private String unidadeLaboratorio;
    private UUID estabelecimentoRealizador;
    private UUID profissionalResponsavel;
    private UUID medicoResponsavel;
    private String resultados;
    private String laudo;
    private String observacoes;
}
