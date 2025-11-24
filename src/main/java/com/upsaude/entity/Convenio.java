package com.upsaude.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "convenios", schema = "public",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_convenios_codigo", columnNames = {"codigo", "tenant_id"})
       })
@Data
public class Convenio extends BaseEntity {

    @NotBlank(message = "Nome do convênio é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Size(max = 50, message = "Código deve ter no máximo 50 caracteres")
    @Column(name = "codigo", length = 50)
    private String codigo;

    @Pattern(regexp = "^\\d{14}$", message = "CNPJ deve ter 14 dígitos")
    @Column(name = "cnpj", length = 14)
    private String cnpj;

    @Size(max = 50, message = "Registro ANS deve ter no máximo 50 caracteres")
    @Column(name = "registro_ans", length = 50)
    private String registroAns;

    @Column(name = "contato_json", columnDefinition = "jsonb")
    private String contatoJson;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
