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

    private String slug;

    @Size(max = 500, message = "Metadados deve ter no máximo 500 caracteres")
    private String metadados;

    private Boolean ativo;

    @Schema(description = "Indica se o tenant é um consórcio")
    private Boolean consorcio;

    @CNESValido
    @Size(max = 7, message = "CNES deve ter no máximo 7 caracteres")
    private String cnes;

    @Size(max = 100, message = "Tipo de instituição deve ter no máximo 100 caracteres")
    private String tipoInstituicao;

    private UUID enderecoId;

    @Size(max = 10, message = "Código IBGE do município deve ter no máximo 10 caracteres")
    @Schema(description = "Código IBGE do município (referência à tabela cidades)")
    private String codigoIbgeMunicipio;

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
