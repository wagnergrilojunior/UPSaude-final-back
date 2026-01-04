package com.upsaude.api.request.embeddable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.TipoEquipamentoEnum;
import com.upsaude.util.converter.TipoEquipamentoEnumDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Schema(description = "Dados de identificação do equipamento")
public class DadosIdentificacaoEquipamentoRequest {

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String nome;

    @Size(max = 100, message = "Nome comercial deve ter no máximo 100 caracteres")
    private String nomeComercial;

    @Size(max = 50, message = "Código interno deve ter no máximo 50 caracteres")
    private String codigoInterno;

    @Size(max = 20, message = "Código CNES deve ter no máximo 20 caracteres")
    private String codigoCnes;

    @Size(max = 50, message = "Registro ANVISA deve ter no máximo 50 caracteres")
    private String registroAnvisa;

    @NotNull(message = "Tipo é obrigatório")
    @JsonDeserialize(using = TipoEquipamentoEnumDeserializer.class)
    private TipoEquipamentoEnum tipo;

    @Size(max = 100, message = "Categoria deve ter no máximo 100 caracteres")
    private String categoria;

    @Size(max = 100, message = "Subcategoria deve ter no máximo 100 caracteres")
    private String subcategoria;

    @Size(max = 50, message = "Classe de risco deve ter no máximo 50 caracteres")
    private String classeRisco;
}

