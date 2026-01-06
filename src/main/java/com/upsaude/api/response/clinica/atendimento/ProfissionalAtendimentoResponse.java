package com.upsaude.api.response.clinica.atendimento;

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
public class ProfissionalAtendimentoResponse {
    private UUID id;
    private String nomeCompleto;
    private String registroProfissional;
    private String ufRegistro;
}

