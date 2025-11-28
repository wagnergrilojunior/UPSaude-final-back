package com.upsaude.entity.embeddable;

import com.upsaude.enums.TipoAlergiaEnum;
import com.upsaude.util.converter.TipoAlergiaEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe embeddable para classificação da alergia.
 *
 * @author UPSaúde
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassificacaoAlergia {

    @Convert(converter = TipoAlergiaEnumConverter.class)
    @Column(name = "tipo_alergia")
    private TipoAlergiaEnum tipoAlergia;

    @Size(max = 100, message = "Categoria deve ter no máximo 100 caracteres")
    @Column(name = "categoria", length = 100)
    private String categoria; // Ex: Alergia alimentar, Alergia medicamentosa

    @Size(max = 100, message = "Subcategoria deve ter no máximo 100 caracteres")
    @Column(name = "subcategoria", length = 100)
    private String subcategoria;

    @Size(max = 50, message = "Codigo_cid deve ter no máximo 50 caracteres")
    @Column(name = "codigo_cid", length = 50)
    private String codigoCid; // CID relacionado à alergia (ex: T78.4 - Alergia não especificada)

    @Column(name = "alergia_comum", nullable = false)
    @Builder.Default
    private Boolean alergiaComum = false; // Se é uma alergia comum

    @Column(name = "alergia_grave", nullable = false)
    @Builder.Default
    private Boolean alergiaGrave = false; // Se pode causar reações graves
}

