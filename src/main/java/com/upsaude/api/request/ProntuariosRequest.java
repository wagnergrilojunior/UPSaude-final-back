package com.upsaude.api.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProntuariosRequest {

    @NotNull(message = "ID do estabelecimento é obrigatório")
    private UUID estabelecimentoId;

    @NotNull(message = "ID do paciente é obrigatório")
    private UUID pacienteId;

    private String tipoRegistro;

    private String conteudo;

    private UUID criadoPor;
}

