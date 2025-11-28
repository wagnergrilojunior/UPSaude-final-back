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
    private String alergiasJson;
    private String doencasCronicasJson;
    private String deficienciasJson;
    private String medicacoesContinuasJson;
    private Boolean fumante;
    private Boolean alcoolista;
    private Boolean usuarioDrogas;
    private Double peso;
    private Double altura;
    private Boolean historicoViolencia;
    private Boolean acompanhamentoPsicossocial;
}

