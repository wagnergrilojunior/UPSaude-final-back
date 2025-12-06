package com.upsaude.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
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
    @Pattern(regexp = "^\\d{14}$", message = "CNPJ deve ter 14 dígitos")
    private String cnpj;
    
    @Size(max = 7, message = "CNES deve ter no máximo 7 caracteres")
    private String cnes;
    private String tipoInstituicao;
    private String telefone;
    private String email;
    private String site;
    private String inscricaoEstadual;
    private String inscricaoMunicipal;
    private String responsavelNome;
    private String responsavelCpf;
    private String responsavelTelefone;
    private String horarioFuncionamento;
    private String observacoes;
}
