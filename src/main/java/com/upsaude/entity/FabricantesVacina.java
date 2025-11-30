package com.upsaude.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entidade que representa um fabricante de vacinas.
 * Permite cadastrar fabricantes para referência em vacinas.
 * Baseado em padrões do PNI e ANVISA.
 *
 * @author UPSaúde
 */
@Entity
@Table(name = "fabricantes_vacina", schema = "public",
       indexes = {
           @Index(name = "idx_fabricante_vacina_nome", columnList = "nome"),
           @Index(name = "idx_fabricante_vacina_cnpj", columnList = "cnpj"),
           @Index(name = "idx_fabricante_vacina_registro_anvisa", columnList = "registro_anvisa")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class FabricantesVacina extends BaseEntityWithoutTenant {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

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

    @Size(max = 50, message = "Registro MS deve ter no máximo 50 caracteres")
    @Column(name = "registro_ms", length = 50)
    private String registroMs; // Registro no Ministério da Saúde

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
