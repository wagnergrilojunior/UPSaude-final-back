package com.upsaude.dto;

import com.upsaude.entity.embeddable.ContatoMedico;
import com.upsaude.entity.embeddable.DadosPessoaisMedico;
import com.upsaude.entity.embeddable.FormacaoMedico;
import com.upsaude.entity.embeddable.RegistroProfissionalMedico;
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
    private UUID especialidadeId;
    private String nomeCompleto;
    private DadosPessoaisMedico dadosPessoais;
    private RegistroProfissionalMedico registroProfissional;
    private FormacaoMedico formacao;
    private ContatoMedico contato;
    private List<UUID> enderecosIds;
    private String observacoes;
    private Boolean active;
}
