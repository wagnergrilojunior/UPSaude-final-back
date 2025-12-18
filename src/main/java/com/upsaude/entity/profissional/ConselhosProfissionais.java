package com.upsaude.entity.profissional;
import com.upsaude.entity.BaseEntityWithoutTenant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "conselhos_profissionais", schema = "public")
@Data
@EqualsAndHashCode(callSuper = true)
public class ConselhosProfissionais extends BaseEntityWithoutTenant {

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
