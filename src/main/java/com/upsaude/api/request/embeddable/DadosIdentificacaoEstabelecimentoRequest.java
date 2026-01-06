package com.upsaude.api.request.embeddable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.NaturezaJuridicaEnum;
import com.upsaude.enums.TipoEstabelecimentoEnum;
import com.upsaude.util.converter.NaturezaJuridicaEnumDeserializer;
import com.upsaude.util.converter.TipoEstabelecimentoEnumDeserializer;
import com.upsaude.validation.annotation.CNESValido;
import com.upsaude.validation.annotation.CNPJValido;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Dados de identificação do estabelecimento")
public class DadosIdentificacaoEstabelecimentoRequest {

    @NotBlank(message = "Nome do estabelecimento é obrigatório")
    @Pattern(regexp = "^[\\p{L}0-9 .'-]+$", message = "Caracteres inválidos no nome")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String nome;

    @Pattern(regexp = "^$|^[\\p{L}0-9 .'-]+$", message = "Caracteres inválidos no nome fantasia")
    @Size(max = 255, message = "Nome fantasia deve ter no máximo 255 caracteres")
    private String nomeFantasia;

    @NotBlank(message = "Tipo de estabelecimento é obrigatório")
    @JsonDeserialize(using = TipoEstabelecimentoEnumDeserializer.class)
    private TipoEstabelecimentoEnum tipo;

    @CNESValido
    @Size(max = 7, message = "Código CNES deve ter no máximo 7 caracteres")
    private String cnes;

    @CNPJValido
    @Size(max = 14, message = "CNPJ deve ter no máximo 14 caracteres")
    private String cnpj;

    @JsonDeserialize(using = NaturezaJuridicaEnumDeserializer.class)
    private NaturezaJuridicaEnum naturezaJuridica;
}
