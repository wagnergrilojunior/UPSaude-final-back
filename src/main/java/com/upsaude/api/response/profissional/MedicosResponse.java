package com.upsaude.api.response.profissional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.upsaude.api.response.embeddable.ContatoMedicoResponse;
import com.upsaude.api.response.embeddable.DadosDemograficosMedicoResponse;
import com.upsaude.api.response.embeddable.DadosPessoaisBasicosMedicoResponse;
import com.upsaude.api.response.embeddable.DocumentosBasicosMedicoResponse;
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
    private DadosPessoaisBasicosMedicoResponse dadosPessoaisBasicos;
    private DocumentosBasicosMedicoResponse documentosBasicos;
    private DadosDemograficosMedicoResponse dadosDemograficos;
    private RegistroProfissionalMedicoResponse registroProfissional;
    private ContatoMedicoResponse contato;
    private EnderecoResponse enderecoMedico;
    @Builder.Default
    private List<MedicoEstabelecimentoResponse> estabelecimentos = new ArrayList<>();
    @Builder.Default
    private List<EspecialidadeResponse> especialidades = new ArrayList<>();
    private String observacoes;
}
