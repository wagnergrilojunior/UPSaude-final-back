package com.upsaude.api.request;

import com.upsaude.api.request.embeddable.ClassificacaoDoencaRequest;
import com.upsaude.api.request.embeddable.EpidemiologiaDoencaRequest;
import com.upsaude.api.request.embeddable.SintomasDoencaRequest;
import com.upsaude.api.request.embeddable.TratamentoPadraoDoencaRequest;
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
public class DoencasRequest {
    @NotBlank(message = "Nome da doença é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String nome;
    
    @Size(max = 255, message = "Nome científico deve ter no máximo 255 caracteres")
    private String nomeCientifico;
    
    @Size(max = 50, message = "Código interno deve ter no máximo 50 caracteres")
    private String codigoInterno;
    
    private ClassificacaoDoencaRequest classificacao;
    private UUID cidPrincipal;
    private SintomasDoencaRequest sintomas;
    private TratamentoPadraoDoencaRequest tratamentoPadrao;
    private EpidemiologiaDoencaRequest epidemiologia;
    
    private String descricao;
    private String causas;
    private String fisiopatologia;
    private String prognostico;
    private String observacoes;
}
