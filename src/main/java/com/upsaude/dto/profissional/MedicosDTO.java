package com.upsaude.dto.profissional;

import com.upsaude.dto.embeddable.ContatoMedicoDTO;
import com.upsaude.dto.embeddable.DadosPessoaisMedicoDTO;
import com.upsaude.dto.embeddable.FormacaoMedicoDTO;
import com.upsaude.dto.embeddable.RegistroProfissionalMedicoDTO;
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
    private String nomeCompleto;
    private DadosPessoaisMedicoDTO dadosPessoais;
    private RegistroProfissionalMedicoDTO registroProfissional;
    private FormacaoMedicoDTO formacao;
    private ContatoMedicoDTO contato;
    private String observacoes;
}
