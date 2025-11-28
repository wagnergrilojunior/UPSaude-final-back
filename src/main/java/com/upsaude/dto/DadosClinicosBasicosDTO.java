package com.upsaude.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DadosClinicosBasicosDTO {
    private UUID id;
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
    private Double imc;
    private Boolean historicoViolencia;
    private Boolean acompanhamentoPsicossocial;
    private Boolean active;
}

