package com.upsaude.api.request;

import com.upsaude.entity.embeddable.ContatoMedico;
import com.upsaude.entity.embeddable.DadosPessoaisMedico;
import com.upsaude.entity.embeddable.FormacaoMedico;
import com.upsaude.entity.embeddable.RegistroProfissionalMedico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class MedicosRequest {
    private List<UUID> estabelecimentosIds;
    
    @NotBlank(message = "Nome completo é obrigatório")
    @Size(max = 255, message = "Nome completo deve ter no máximo 255 caracteres")
    private String nomeCompleto;
    
    private DadosPessoaisMedico dadosPessoais;
    
    @NotBlank(message = "CRM é obrigatório")
    private RegistroProfissionalMedico registroProfissional;
    
    private FormacaoMedico formacao;
    
    private ContatoMedico contato;
    
    private UUID especialidadeId;
    
    private List<UUID> enderecosIds;
    
    private String observacoes;
}
