package com.upsaude.api.request;

import jakarta.validation.constraints.Email;
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
    @Pattern(regexp = "^$|^\\d{14}$", message = "CNPJ deve ter 14 dígitos")
    private String cnpj;

    @Size(max = 7, message = "CNES deve ter no máximo 7 caracteres")
    private String cnes;

    @Size(max = 100, message = "Tipo de instituição deve ter no máximo 100 caracteres")
    private String tipoInstituicao;

    @Pattern(regexp = "^$|^\\d{10,11}$", message = "Telefone deve ter 10 ou 11 dígitos")
    private String telefone;

    @Email(message = "Email inválido")
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    private String email;

    @Size(max = 255, message = "Site deve ter no máximo 255 caracteres")
    private String site;

    @Size(max = 20, message = "Inscrição estadual deve ter no máximo 20 caracteres")
    private String inscricaoEstadual;

    @Size(max = 20, message = "Inscrição municipal deve ter no máximo 20 caracteres")
    private String inscricaoMunicipal;

    @Size(max = 255, message = "Nome do responsável deve ter no máximo 255 caracteres")
    private String responsavelNome;

    @Pattern(regexp = "^$|^\\d{11}$", message = "CPF do responsável deve ter 11 dígitos")
    private String responsavelCpf;

    @Pattern(regexp = "^$|^\\d{10,11}$", message = "Telefone do responsável deve ter 10 ou 11 dígitos")
    private String responsavelTelefone;
    private String horarioFuncionamento;
    @Size(max = 1000, message = "Observações deve ter no máximo 1000 caracteres")
    private String observacoes;
}
