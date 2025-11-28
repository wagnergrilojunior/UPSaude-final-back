package com.upsaude.api.response;

import com.upsaude.entity.embeddable.ContatoMedico;
import com.upsaude.entity.embeddable.DadosPessoaisMedico;
import com.upsaude.entity.embeddable.FormacaoMedico;
import com.upsaude.entity.embeddable.RegistroProfissionalMedico;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicosResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private UUID estabelecimentoId;
    private String estabelecimentoNome;
    private UUID especialidadeId;
    private String especialidadeNome;
    private String nomeCompleto;
    private DadosPessoaisMedico dadosPessoais;
    private RegistroProfissionalMedico registroProfissional;
    private FormacaoMedico formacao;
    private ContatoMedico contato;
    private List<UUID> enderecosIds;
    private String observacoes;
}
