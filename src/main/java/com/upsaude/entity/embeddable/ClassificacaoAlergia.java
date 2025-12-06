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

/**
 * Classe embeddable para classificação da alergia.
 *
 * @author UPSaúde
 */
@Embeddable
@Data
@Builder
@AllArgsConstructor
public class ClassificacaoAlergia {

    public ClassificacaoAlergia() {
        this.alergiaComum = false;
        this.alergiaGrave = false;
    }

    @Convert(converter = TipoAlergiaEnumConverter.class)
    @Column(name = "tipo_alergia")
    private TipoAlergiaEnum tipoAlergia;

    @Size(max = 100, message = "Categoria deve ter no máximo 100 caracteres")
    @Column(name = "categoria", length = 100)
    private String categoria;

    @Size(max = 100, message = "Subcategoria deve ter no máximo 100 caracteres")
    @Column(name = "subcategoria", length = 100)
    private String subcategoria;

    @Size(max = 50, message = "Codigo_cid deve ter no máximo 50 caracteres")
    @Column(name = "codigo_cid", length = 50)
    private String codigoCid;

    @Column(name = "alergia_comum", nullable = false)
    private Boolean alergiaComum;

    @Column(name = "alergia_grave", nullable = false)
    private Boolean alergiaGrave;
}


