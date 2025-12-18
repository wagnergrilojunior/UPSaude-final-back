package com.upsaude.api.request.doencas;

import com.upsaude.api.request.embeddable.ClassificacaoDoencaRequest;
import com.upsaude.api.request.embeddable.EpidemiologiaDoencaRequest;
import com.upsaude.api.request.embeddable.SintomasDoencaRequest;
import com.upsaude.api.request.embeddable.TratamentoPadraoDoencaRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de doencas")
public class DoencasRequest {
    @NotBlank(message = "Nome da doença é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String nome;

    @Size(max = 255, message = "Nome científico deve ter no máximo 255 caracteres")
    private String nomeCientifico;

    @Size(max = 50, message = "Código interno deve ter no máximo 50 caracteres")
    private String codigoInterno;

    @Valid
    private ClassificacaoDoencaRequest classificacao;

    @Valid
    private SintomasDoencaRequest sintomas;

    @Valid
    private TratamentoPadraoDoencaRequest tratamentoPadrao;

    @Valid
    private EpidemiologiaDoencaRequest epidemiologia;

    @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
    private String descricao;

    @Size(max = 1000, message = "Causas deve ter no máximo 1000 caracteres")
    private String causas;

    @Size(max = 2000, message = "Fisiopatologia deve ter no máximo 2000 caracteres")
    private String fisiopatologia;

    @Size(max = 1000, message = "Prognóstico deve ter no máximo 1000 caracteres")
    private String prognostico;

    @Size(max = 1000, message = "Observações deve ter no máximo 1000 caracteres")
    private String observacoes;
}
