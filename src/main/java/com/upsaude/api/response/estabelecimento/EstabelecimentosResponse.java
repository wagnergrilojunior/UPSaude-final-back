package com.upsaude.api.response.estabelecimento;

import com.upsaude.api.response.embeddable.ContatoEstabelecimentoResponse;
import com.upsaude.api.response.embeddable.DadosIdentificacaoEstabelecimentoResponse;
import com.upsaude.api.response.embeddable.InfraestruturaFisicaEstabelecimentoResponse;
import com.upsaude.api.response.embeddable.LicenciamentoEstabelecimentoResponse;
import com.upsaude.api.response.embeddable.LocalizacaoEstabelecimentoResponse;
import com.upsaude.api.response.embeddable.ResponsaveisEstabelecimentoResponse;
import com.upsaude.api.response.geral.EnderecoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstabelecimentosResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    
    private DadosIdentificacaoEstabelecimentoResponse dadosIdentificacao;
    
    private String registroOficial;
    
    private EnderecoResponse enderecoPrincipal;
    
    private ContatoEstabelecimentoResponse contato;
    
    private ResponsaveisEstabelecimentoResponse responsaveis;
    
    private LicenciamentoEstabelecimentoResponse licenciamento;
    
    private OffsetDateTime dataAbertura;
    
    private OffsetDateTime dataLicenciamento;
    
    private OffsetDateTime dataValidadeLicencaSanitaria;
    
    private InfraestruturaFisicaEstabelecimentoResponse infraestruturaFisica;
    
    private LocalizacaoEstabelecimentoResponse localizacao;
    
    private String observacoes;

    @Builder.Default
    private List<EquipamentosEstabelecimentoResponse> equipamentos = new ArrayList<>();
}
