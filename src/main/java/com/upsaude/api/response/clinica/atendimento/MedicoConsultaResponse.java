package com.upsaude.api.response.clinica.atendimento;

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
public class MedicoConsultaResponse {
    private UUID id;
    private String nomeCompleto;
    private String crm;
    private String crmUf;
}

