package com.upsaude.api.request.estabelecimento.equipamento;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.api.request.embeddable.DadosIdentificacaoEquipamentoRequest;
import com.upsaude.api.request.embeddable.DescricoesEquipamentoRequest;
import com.upsaude.api.request.embeddable.EspecificacoesTecnicasEquipamentoRequest;
import com.upsaude.api.request.embeddable.ManutencaoCalibracaoEquipamentoRequest;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.util.converter.StatusAtivoEnumDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados de equipamentos")
public class EquipamentosRequest {

    @NotNull(message = "Dados de identificação são obrigatórios")
    @Valid
    private DadosIdentificacaoEquipamentoRequest dadosIdentificacao;

    private UUID fabricante;

    @Valid
    private EspecificacoesTecnicasEquipamentoRequest especificacoesTecnicas;

    @Valid
    private ManutencaoCalibracaoEquipamentoRequest manutencaoCalibracao;

    @NotNull(message = "Status é obrigatório")
    @JsonDeserialize(using = StatusAtivoEnumDeserializer.class)
    private StatusAtivoEnum status;

    @Builder.Default
    private Boolean disponivelUso = true;

    @Valid
    private DescricoesEquipamentoRequest descricoes;
}
