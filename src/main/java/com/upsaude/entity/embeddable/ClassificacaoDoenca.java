package com.upsaude.entity.embeddable;

import com.upsaude.enums.GravidadeDoencaEnum;
import com.upsaude.enums.TipoDoencaEnum;
import com.upsaude.util.converter.GravidadeDoencaEnumConverter;
import com.upsaude.util.converter.TipoDoencaEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe embeddable para classificação da doença.
 *
 * @author UPSaúde
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassificacaoDoenca {

    @Convert(converter = TipoDoencaEnumConverter.class)
    @Column(name = "tipo_doenca")
    private TipoDoencaEnum tipoDoenca;

    @Convert(converter = GravidadeDoencaEnumConverter.class)
    @Column(name = "gravidade")
    private GravidadeDoencaEnum gravidade;

    @Size(max = 100, message = "Categoria deve ter no máximo 100 caracteres")
    @Column(name = "categoria", length = 100)
    private String categoria; // Ex: Doenças do aparelho circulatório

    @Size(max = 100, message = "Subcategoria deve ter no máximo 100 caracteres")
    @Column(name = "subcategoria", length = 100)
    private String subcategoria;

    @Size(max = 50, message = "Código CID principal deve ter no máximo 50 caracteres")
    @Column(name = "codigo_cid_principal", length = 50)
    private String codigoCidPrincipal; // Código CID-10 principal

    @Column(name = "doenca_notificavel", nullable = false)
    @Builder.Default
    private Boolean doencaNotificavel = false; // Se é doença de notificação compulsória

    @Column(name = "doenca_transmissivel", nullable = false)
    @Builder.Default
    private Boolean doencaTransmissivel = false; // Se é doença transmissível
}

