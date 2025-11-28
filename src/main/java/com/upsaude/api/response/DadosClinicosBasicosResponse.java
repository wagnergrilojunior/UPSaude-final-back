package com.upsaude.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DadosClinicosBasicosResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
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
}

