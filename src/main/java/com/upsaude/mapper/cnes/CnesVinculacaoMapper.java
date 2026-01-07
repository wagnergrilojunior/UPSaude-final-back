package com.upsaude.mapper.cnes;

import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.estabelecimento.ProfissionalEstabelecimento;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.enums.TipoVinculoProfissionalEnum;
import com.upsaude.integration.cnes.wsdl.profissional.DadosBasicosEstabelecimentoType;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class CnesVinculacaoMapper {

    /**
     * Mapeia um DadosBasicosEstabelecimentoType (vinculação vinda do Profissional)
     * para a entidade ProfissionalEstabelecimento.
     */
    public void mapToVinculacao(DadosBasicosEstabelecimentoType cnesEstab, ProfissionaisSaude profissional,
            Estabelecimentos estabelecimento, ProfissionalEstabelecimento vinculacao) {
        vinculacao.setProfissional(profissional);
        vinculacao.setEstabelecimento(estabelecimento);

        // No CNES, se não tivermos a data de início, usamos agora como fallback
        if (vinculacao.getDataInicio() == null) {
            vinculacao.setDataInicio(OffsetDateTime.now());
        }

        // Tipo de vínculo padrão se não vier detalhado
        if (vinculacao.getTipoVinculo() == null) {
            vinculacao.setTipoVinculo(TipoVinculoProfissionalEnum.OUTRO);
        }
    }
}
