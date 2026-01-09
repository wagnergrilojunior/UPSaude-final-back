package com.upsaude.api.request.sistema.multitenancy;

import com.upsaude.api.request.embeddable.ContatoTenantRequest;
import com.upsaude.api.request.embeddable.DadosFiscaisTenantRequest;
import com.upsaude.api.request.embeddable.DadosIdentificacaoTenantRequest;
import com.upsaude.api.request.embeddable.InformacoesAdicionaisTenantRequest;
import com.upsaude.api.request.embeddable.ResponsavelTenantRequest;
import com.upsaude.validation.annotation.CNESValido;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de tenant")
public class TenantRequest {

    @NotBlank(message = "Slug é obrigatório")
    @Size(max = 100, message = "Slug deve ter no máximo 100 caracteres")
    @Pattern(regexp = "^[a-z0-9-]+$", message = "Slug deve conter apenas letras minúsculas, números e hífens")
    private String slug;

    @Size(max = 500, message = "Metadados deve ter no máximo 500 caracteres")
    private String metadados;

    private Boolean ativo;

    @CNESValido
    @Size(max = 7, message = "CNES deve ter no máximo 7 caracteres")
    private String cnes;

    @Size(max = 100, message = "Tipo de instituição deve ter no máximo 100 caracteres")
    private String tipoInstituicao;

    private UUID enderecoId;

    @Valid
    private DadosIdentificacaoTenantRequest dadosIdentificacao;

    @Valid
    private ContatoTenantRequest contato;

    @Valid
    private DadosFiscaisTenantRequest dadosFiscais;

    @Valid
    private ResponsavelTenantRequest responsavel;

    @Valid
    private InformacoesAdicionaisTenantRequest informacoesAdicionais;
}
