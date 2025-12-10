package com.upsaude.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.upsaude.api.request.embeddable.ContatoMedicoRequest;
import com.upsaude.api.request.embeddable.DadosPessoaisMedicoRequest;
import com.upsaude.api.request.embeddable.FormacaoMedicoRequest;
import com.upsaude.api.request.embeddable.RegistroProfissionalMedicoRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
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
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MedicosRequest {
    // ========== CAMPOS OBRIGATÓRIOS ==========
    
    @NotBlank(message = "Nome completo é obrigatório")
    @Pattern(regexp = "^[\\p{L}0-9 .'-]+$", message = "Caracteres inválidos no nome completo")
    @Size(max = 255, message = "Nome completo deve ter no máximo 255 caracteres")
    private String nomeCompleto;
    
    // ========== COLETES DE RELACIONAMENTO (SET) ==========
    
    /**
     * IDs das especialidades médicas do médico.
     * Permite que um médico tenha múltiplas especialidades.
     * O backend é responsável por buscar as especialidades e fazer o vínculo correto.
     * 
     * IMPORTANTE: Apenas IDs devem ser enviados. O backend fará a busca interna
     * das especialidades e criará o relacionamento ManyToMany.
     */
    @Builder.Default
    private Set<UUID> especialidades = new HashSet<>();
    
    /**
     * IDs dos estabelecimentos onde o médico atua.
     * Permite que um médico tenha vínculos com múltiplos estabelecimentos.
     * O backend é responsável por buscar os estabelecimentos e criar os vínculos corretos.
     * 
     * IMPORTANTE: Apenas IDs devem ser enviados. O backend fará a busca interna
     * dos estabelecimentos e criará os objetos MedicoEstabelecimento com valores padrão.
     */
    @Builder.Default
    private Set<UUID> estabelecimentos = new HashSet<>();
    
    // ========== OBJETOS EMBEDDABLE ==========
    
    @Valid
    private DadosPessoaisMedicoRequest dadosPessoais;
    
    @Valid
    private RegistroProfissionalMedicoRequest registroProfissional;
    
    @Valid
    private FormacaoMedicoRequest formacao;
    
    @Valid
    private ContatoMedicoRequest contato;
    
    // ========== CAMPOS TEXTUAIS LONGOS ==========
    
    @Size(max = 1000, message = "Observações deve ter no máximo 1000 caracteres")
    private String observacoes;
}
