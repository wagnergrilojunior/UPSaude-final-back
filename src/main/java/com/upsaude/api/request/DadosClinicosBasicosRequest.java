package com.upsaude.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DadosClinicosBasicosRequest {
    private UUID pacienteId;
    private Boolean gestante;
    private Boolean fumante;
    private Boolean alcoolista;
    private Boolean usuarioDrogas;
    private Boolean historicoViolencia;
    private Boolean acompanhamentoPsicossocial;
}

