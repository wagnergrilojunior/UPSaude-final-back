package com.upsaude.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.upsaude.api.request.embeddable.ClassificacaoAlergiaRequest;
import com.upsaude.api.request.embeddable.PrevencaoTratamentoAlergiaRequest;
import com.upsaude.api.request.embeddable.ReacoesAlergiaRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class AlergiasRequest {
    @NotBlank(message = "Nome da alergia é obrigatório")
    @Pattern(regexp = "^[\\p{L}0-9 .'-]+$", message = "Caracteres inválidos no nome")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String nome;
    
    @Pattern(regexp = "^$|^[\\p{L}0-9 .'-]+$", message = "Caracteres inválidos no nome científico")
    @Size(max = 255, message = "Nome científico deve ter no máximo 255 caracteres")
    private String nomeCientifico;
    
    @Size(max = 50, message = "Código interno deve ter no máximo 50 caracteres")
    private String codigoInterno;
    
    @Valid
    private ClassificacaoAlergiaRequest classificacao;
    
    @Valid
    private ReacoesAlergiaRequest reacoes;
    
    @Valid
    private PrevencaoTratamentoAlergiaRequest prevencaoTratamento;
    
    @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
    private String descricao;
    
    @Size(max = 1000, message = "Substâncias relacionadas deve ter no máximo 1000 caracteres")
    private String substanciasRelacionadas;
    
    @Size(max = 1000, message = "Observações deve ter no máximo 1000 caracteres")
    private String observacoes;
}
