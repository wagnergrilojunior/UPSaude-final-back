package com.upsaude.api.response;

import com.upsaude.entity.embeddable.ContatoMedico;
import com.upsaude.entity.embeddable.DadosPessoaisMedico;
import com.upsaude.entity.embeddable.FormacaoMedico;
import com.upsaude.entity.embeddable.RegistroProfissionalMedico;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicosResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private EspecialidadesMedicasResponse especialidade;
    private String nomeCompleto;
    private DadosPessoaisMedico dadosPessoais;
    private RegistroProfissionalMedico registroProfissional;
    private FormacaoMedico formacao;
    private ContatoMedico contato;
    private String observacoes;
}
