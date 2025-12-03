package com.upsaude.dto;

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
public class MedicosDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private EspecialidadesMedicasDTO especialidade;
    private String nomeCompleto;
    private DadosPessoaisMedico dadosPessoais;
    private RegistroProfissionalMedico registroProfissional;
    private FormacaoMedico formacao;
    private ContatoMedico contato;
    private String observacoes;
}
