package com.upsaude.api.request;

import com.upsaude.api.request.embeddable.ClassificacaoAlergiaRequest;
import com.upsaude.api.request.embeddable.PrevencaoTratamentoAlergiaRequest;
import com.upsaude.api.request.embeddable.ReacoesAlergiaRequest;
import jakarta.validation.constraints.NotBlank;
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
public class AlergiasRequest {
    @NotBlank(message = "Nome da alergia é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String nome;
    
    @Size(max = 255, message = "Nome científico deve ter no máximo 255 caracteres")
    private String nomeCientifico;
    
    @Size(max = 50, message = "Código interno deve ter no máximo 50 caracteres")
    private String codigoInterno;
    
    private ClassificacaoAlergiaRequest classificacao;
    private ReacoesAlergiaRequest reacoes;
    private PrevencaoTratamentoAlergiaRequest prevencaoTratamento;
    
    private String descricao;
    private String substanciasRelacionadas;
    private String observacoes;
}
