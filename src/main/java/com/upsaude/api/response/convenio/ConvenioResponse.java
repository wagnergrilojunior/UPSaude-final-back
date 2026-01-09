package com.upsaude.api.response.convenio;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.upsaude.enums.ModalidadeConvenioEnum;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoConvenioEnum;

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
public class ConvenioResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private String nome;
    private String cnpj;
    private String inscricaoEstadual;
    private String inscricaoMunicipal;
    private TipoConvenioEnum tipo;
    private ModalidadeConvenioEnum modalidade;
    private StatusAtivoEnum status;
    private LocalDate dataCadastro;
    private Boolean redeCredenciadaNacional;
    private Boolean redeCredenciadaRegional;
    private UUID tenantId;
    private UUID estabelecimentoId;
}
