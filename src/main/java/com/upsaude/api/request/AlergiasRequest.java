package com.upsaude.api.request;

import com.upsaude.entity.embeddable.ClassificacaoAlergia;
import com.upsaude.entity.embeddable.PrevencaoTratamentoAlergia;
import com.upsaude.entity.embeddable.ReacoesAlergia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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

    private ClassificacaoAlergia classificacao;

    private ReacoesAlergia reacoes;

    private PrevencaoTratamentoAlergia prevencaoTratamento;

    private String descricao;

    private String substanciasRelacionadas;

    private String observacoes;
}
