package com.upsaude.api.response.referencia.cid;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosticoPrincipalResponse {
    private UUID id;
    private String subcat;
    private String descricao;
    private String descrAbrev;
}

