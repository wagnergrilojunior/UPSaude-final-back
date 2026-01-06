package com.upsaude.api.response.embeddable;

import com.upsaude.api.response.profissional.ProfissionaisSaudeResponse;
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
public class ResponsaveisEstabelecimentoResponse {

    private ProfissionaisSaudeResponse responsavelTecnico;

    private ProfissionaisSaudeResponse responsavelAdministrativo;

    private String responsavelLegalNome;

    private String responsavelLegalCpf;
}
