package com.upsaude.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "profissionais_saude", schema = "public")
@Data
public class ProfissionaisSaude extends BaseEntity {

    @Column(name = "nome_completo", nullable = false, length = 255)
    @NotBlank(message = "Nome completo é obrigatório")
    @Size(max = 255, message = "Nome completo deve ter no máximo 255 caracteres")
    private String nomeCompleto;

    @Column(name = "cpf", length = 11, unique = true)
    @Pattern(regexp = "^\\d{11}$", message = "CPF deve ter 11 dígitos")
    private String cpf;

    @Column(name = "registro_profissional", nullable = false, length = 20)
    @NotBlank(message = "Registro profissional é obrigatório")
    @Size(max = 20, message = "Registro deve ter no máximo 20 caracteres")
    private String registroProfissional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conselho_id", nullable = false)
    @NotBlank(message = "Conselho profissional é obrigatório")
    private ConselhosProfissionais conselho;

    @Column(name = "uf_registro", length = 2)
    @Pattern(regexp = "^[A-Z]{2}$", message = "UF do registro deve ter 2 letras maiúsculas")
    private String ufRegistro;

    @Column(name = "telefone", length = 20)
    @Pattern(regexp = "^\\d{10,11}$", message = "Telefone deve ter 10 ou 11 dígitos")
    private String telefone;

    @Column(name = "email", length = 255)
    @Email(message = "Email inválido")
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    private String email;

    
}
