package com.upsaude.api.request;

import com.upsaude.api.request.embeddable.ContatoMedicoRequest;
import com.upsaude.api.request.embeddable.DadosPessoaisMedicoRequest;
import com.upsaude.api.request.embeddable.FormacaoMedicoRequest;
import com.upsaude.api.request.embeddable.RegistroProfissionalMedicoRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
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
    /**
     * Lista de IDs das especialidades médicas do médico.
     * Permite que um médico tenha múltiplas especialidades.
     * O backend é responsável por buscar as especialidades e fazer o vínculo correto.
     * 
     * IMPORTANTE: Apenas IDs devem ser enviados. O backend fará a busca interna
     * das especialidades e criará o relacionamento ManyToMany.
     */
    @Builder.Default
    private List<UUID> especialidades = new ArrayList<>();
    
    @NotBlank(message = "Nome completo é obrigatório")
    @Size(max = 255, message = "Nome completo deve ter no máximo 255 caracteres")
    private String nomeCompleto;
    
    @Valid
    private DadosPessoaisMedicoRequest dadosPessoais;
    
    @Valid
    private RegistroProfissionalMedicoRequest registroProfissional;
    
    @Valid
    private FormacaoMedicoRequest formacao;
    
    @Valid
    private ContatoMedicoRequest contato;
    
    /**
     * Lista de endereços do médico (consultório, residência, etc).
     */
    @Valid
    @Builder.Default
    private List<EnderecoRequest> enderecos = new ArrayList<>();
    
    @Size(max = 1000, message = "Observações deve ter no máximo 1000 caracteres")
    private String observacoes;
}
