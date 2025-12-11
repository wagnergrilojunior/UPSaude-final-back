package com.upsaude.entity;

import com.upsaude.enums.TipoDeficienciaEnum;
import com.upsaude.util.converter.TipoDeficienciaEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "deficiencias", schema = "public")
@Data
@EqualsAndHashCode(callSuper = true)
public class Deficiencias extends BaseEntityWithoutTenant {

    @NotBlank(message = "Nome da deficiência é obrigatório")
    @Size(max = 100, message = "Nome da deficiência deve ter no máximo 100 caracteres")
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Convert(converter = TipoDeficienciaEnumConverter.class)
    @Column(name = "tipo_deficiencia")
    private TipoDeficienciaEnum tipoDeficiencia;

    @Pattern(regexp = "^[A-Z]\\d{2}(\\.\\d{1,2})?$", message = "Código CID-10 deve seguir o formato A99 ou A99.99")
    @Size(max = 10, message = "Código CID-10 deve ter no máximo 10 caracteres")
    @Column(name = "cid10_relacionado", length = 10)
    private String cid10Relacionado;

    @NotNull(message = "Campo permanente é obrigatório")
    @Column(name = "permanente", nullable = false)
    private Boolean permanente = false;

    @NotNull(message = "Campo acompanhamentoContinuo é obrigatório")
    @Column(name = "acompanhamento_continuo", nullable = false)
    private Boolean acompanhamentoContinuo = false;
}
