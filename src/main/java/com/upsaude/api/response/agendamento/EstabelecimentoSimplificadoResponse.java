package com.upsaude.api.response.agendamento;

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
public class EstabelecimentoSimplificadoResponse {
    private UUID id;
    private String razaoSocial;
    private String nomeFantasia;
}

