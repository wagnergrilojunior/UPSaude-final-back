package com.upsaude.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "fabricantes_equipamento", schema = "public",
       indexes = {
           @Index(name = "idx_fabricante_equipamento_nome", columnList = "nome"),
           @Index(name = "idx_fabricante_equipamento_cnpj", columnList = "cnpj")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class FabricantesEquipamento extends BaseEntityWithoutTenant {

    @NotBlank(message = "Nome do fabricante é obrigatório")
    @Size(max = 255, message = "Nome do fabricante deve ter no máximo 255 caracteres")
    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Size(max = 18, message = "CNPJ deve ter no máximo 18 caracteres")
    @Column(name = "cnpj", length = 18)
    private String cnpj;

    @Size(max = 100, message = "País deve ter no máximo 100 caracteres")
    @Column(name = "pais", length = 100)
    private String pais;

    @Size(max = 100, message = "Estado/UF deve ter no máximo 100 caracteres")
    @Column(name = "estado", length = 100)
    private String estado;

    @Size(max = 100, message = "Cidade deve ter no máximo 100 caracteres")
    @Column(name = "cidade", length = 100)
    private String cidade;

    @Size(max = 255, message = "Endereço deve ter no máximo 255 caracteres")
    @Column(name = "endereco", length = 255)
    private String endereco;

    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    @Column(name = "telefone", length = 20)
    private String telefone;

    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    @Column(name = "email", length = 255)
    private String email;

    @Size(max = 255, message = "Site deve ter no máximo 255 caracteres")
    @Column(name = "site", length = 255)
    private String site;

    @Size(max = 50, message = "Registro ANVISA deve ter no máximo 50 caracteres")
    @Column(name = "registro_anvisa", length = 50)
    private String registroAnvisa;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
