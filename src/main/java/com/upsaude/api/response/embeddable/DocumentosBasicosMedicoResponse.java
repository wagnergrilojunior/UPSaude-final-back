package com.upsaude.api.response.embeddable;

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
public class DocumentosBasicosMedicoResponse {
    private String cpf;
    private String rg;
    private String orgaoEmissorRg;
    private String ufEmissorRg;
}
