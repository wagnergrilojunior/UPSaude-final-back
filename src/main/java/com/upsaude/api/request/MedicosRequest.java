package com.upsaude.api.request;

import com.upsaude.api.request.embeddable.ContatoMedicoRequest;
import com.upsaude.api.request.embeddable.DadosPessoaisMedicoRequest;
import com.upsaude.api.request.embeddable.FormacaoMedicoRequest;
import com.upsaude.api.request.embeddable.RegistroProfissionalMedicoRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.UUID;
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
public class MedicosRequest {
    private UUID especialidade;
    
    @NotBlank(message = "Nome completo é obrigatório")
    @Size(max = 255, message = "Nome completo deve ter no máximo 255 caracteres")
    private String nomeCompleto;
    
    private DadosPessoaisMedicoRequest dadosPessoais;
    private RegistroProfissionalMedicoRequest registroProfissional;
    private FormacaoMedicoRequest formacao;
    private ContatoMedicoRequest contato;
    
    private String observacoes;
}
