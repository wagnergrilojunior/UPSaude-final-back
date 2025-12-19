package com.upsaude.entity.embeddable;

import com.upsaude.enums.GravidadeDoencaEnum;
import com.upsaude.enums.TipoDoencaEnum;
import com.upsaude.util.converter.GravidadeDoencaEnumConverter;
import com.upsaude.util.converter.TipoDoencaEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Embeddable com campos complementares de classificação da doença.
 * 
 * NOTA: Os campos categoria, subcategoria e codigoCidPrincipal foram removidos
 * pois agora vêm diretamente da tabela oficial CID-10 (cid10_subcategorias)
 * através do relacionamento ManyToOne em Doencas.
 */
@Embeddable
@Data
@Builder
@AllArgsConstructor
public class ClassificacaoDoenca {

    public ClassificacaoDoenca() {
        this.doencaNotificavel = false;
        this.doencaTransmissivel = false;
    }

    @Convert(converter = TipoDoencaEnumConverter.class)
    @Column(name = "tipo_doenca")
    private TipoDoencaEnum tipoDoenca;

    @Convert(converter = GravidadeDoencaEnumConverter.class)
    @Column(name = "gravidade")
    private GravidadeDoencaEnum gravidade;

    @Column(name = "doenca_notificavel", nullable = false)
    @Builder.Default
    private Boolean doencaNotificavel = false;

    @Column(name = "doenca_transmissivel", nullable = false)
    @Builder.Default
    private Boolean doencaTransmissivel = false;
}
