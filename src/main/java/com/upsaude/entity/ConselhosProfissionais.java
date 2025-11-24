package com.upsaude.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "conselhos_profissionais", schema = "public")
@Data
public class ConselhosProfissionais extends BaseEntity {

    @Column(name = "sigla", nullable = false, length = 10, unique = true)
    @NotBlank(message = "Sigla do conselho é obrigatória")
    @Size(max = 10, message = "Sigla deve ter no máximo 10 caracteres")
    private String sigla;

    @Column(name = "nome", nullable = false, length = 255)
    @NotBlank(message = "Nome do conselho é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String nome;

    @Column(name = "descricao", columnDefinition = "TEXT")
    @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
    private String descricao;

    
}
