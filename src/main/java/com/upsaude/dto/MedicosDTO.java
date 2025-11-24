package com.upsaude.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicosDTO {
    private UUID id;
    private UUID estabelecimentoId;
    private String nomeCompleto;
    private String crm;
    private String crmUf;
    private UUID especialidadeId;
    private String telefone;
    private String email;
    private List<UUID> enderecosIds;
    private Boolean active;
}

