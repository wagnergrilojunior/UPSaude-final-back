package com.upsaude.api.response.profissional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.upsaude.api.response.embeddable.ContatoMedicoResponse;
import com.upsaude.api.response.embeddable.DadosPessoaisMedicoResponse;
import com.upsaude.api.response.embeddable.FormacaoMedicoResponse;
import com.upsaude.api.response.embeddable.RegistroProfissionalMedicoResponse;
import com.upsaude.api.response.estabelecimento.MedicoEstabelecimentoResponse;
import com.upsaude.api.response.geral.EnderecoResponse;

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
public class MedicosResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;


    private String nomeCompleto;
    private DadosPessoaisMedicoResponse dadosPessoais;
    private RegistroProfissionalMedicoResponse registroProfissional;
    private FormacaoMedicoResponse formacao;
    private ContatoMedicoResponse contato;

    @Builder.Default
    private List<EnderecoResponse> enderecos = new ArrayList<>();

    @Builder.Default
    private List<MedicoEstabelecimentoResponse> medicosEstabelecimentos = new ArrayList<>();

    private String observacoes;
}
