package com.upsaude.api.response;

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
public class ExamesResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteResponse paciente;
    private CatalogoExamesResponse catalogoExame;
    private AtendimentoResponse atendimento;
    private ConsultasResponse consulta;
    private ProfissionaisSaudeResponse profissionalSolicitante;
    private MedicosResponse medicoSolicitante;
    private String tipoExame;
    private String nomeExame;
    private OffsetDateTime dataSolicitacao;
    private OffsetDateTime dataExame;
    private OffsetDateTime dataResultado;
    private String unidadeLaboratorio;
    private EstabelecimentosResponse estabelecimentoRealizador;
    private ProfissionaisSaudeResponse profissionalResponsavel;
    private MedicosResponse medicoResponsavel;
    private String resultados;
    private String laudo;
    private String observacoes;
}
