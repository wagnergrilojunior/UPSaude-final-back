package com.upsaude.dto.embeddable;

import com.upsaude.enums.GravidadeDoencaEnum;
import com.upsaude.enums.TipoDoencaEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para transferência de dados de ClassificacaoDoenca.
 * 
 * NOTA: Os campos categoria, subcategoria e codigoCidPrincipal foram removidos
 * pois agora vêm diretamente da tabela CID-10 oficial através do 
 * relacionamento na entidade Doencas.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassificacaoDoencaDTO {
    private TipoDoencaEnum tipoDoenca;
    private GravidadeDoencaEnum gravidade;
    private Boolean doencaNotificavel;
    private Boolean doencaTransmissivel;
}
