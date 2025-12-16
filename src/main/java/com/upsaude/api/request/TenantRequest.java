package com.upsaude.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import com.upsaude.validation.annotation.CNPJValido;
import com.upsaude.validation.annotation.CNESValido;
import com.upsaude.validation.annotation.CPFValido;
import com.upsaude.validation.annotation.EmailValido;
import com.upsaude.validation.annotation.SiteValido;
import com.upsaude.validation.annotation.TelefoneValido;
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
@Schema(description = "Dados de tenant")
public class TenantRequest {
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String nome;

    @NotBlank(message = "Slug é obrigatório")
    @Size(max = 100, message = "Slug deve ter no máximo 100 caracteres")
    @Pattern(regexp = "^[a-z0-9-]+$", message = "Slug deve conter apenas letras minúsculas, números e hífens")
    private String slug;
    private String descricao;
    private String urlLogo;
    private String metadados;
    private Boolean ativo;
    @CNPJValido
    private String cnpj;

    @Size(max = 7, message = "CNES deve ter no máximo 7 caracteres")
    @CNESValido
    private String cnes;

    @Size(max = 100, message = "Tipo de instituição deve ter no máximo 100 caracteres")
    private String tipoInstituicao;

    @TelefoneValido
    private String telefone;

    @EmailValido
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    private String email;

    @Size(max = 255, message = "Site deve ter no máximo 255 caracteres")
    @SiteValido
    private String site;

    @Size(max = 20, message = "Inscrição estadual deve ter no máximo 20 caracteres")
    private String inscricaoEstadual;

    @Size(max = 20, message = "Inscrição municipal deve ter no máximo 20 caracteres")
    private String inscricaoMunicipal;

    @Size(max = 255, message = "Nome do responsável deve ter no máximo 255 caracteres")
    private String responsavelNome;

    @CPFValido
    private String responsavelCpf;

    @TelefoneValido
    private String responsavelTelefone;
    private String horarioFuncionamento;
    @Size(max = 1000, message = "Observações deve ter no máximo 1000 caracteres")
    private String observacoes;
}
