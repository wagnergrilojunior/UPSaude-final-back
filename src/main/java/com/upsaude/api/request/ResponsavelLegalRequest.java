package com.upsaude.api.request;

import com.upsaude.enums.TipoResponsavelEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponsavelLegalRequest {
    private UUID pacienteId;
    private String nome;
    private String cpf;
    private String telefone;
    private TipoResponsavelEnum tipoResponsavel;
    private Boolean autorizacaoUsoDadosLGPD;
    private Boolean autorizacaoResponsavel;
}

