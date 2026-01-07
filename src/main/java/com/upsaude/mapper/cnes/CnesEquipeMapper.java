package com.upsaude.mapper.cnes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upsaude.entity.profissional.equipe.EquipeSaude;
import com.upsaude.enums.TipoEquipeEnum;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.integration.cnes.wsdl.equipe.EquipeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

/**
 * Mapper para converter dados de Equipe do CNES (WSDL) para entidades locais.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class CnesEquipeMapper {

    private final ObjectMapper objectMapper;

    /**
     * Mapeia dados básicos de equipe do CNES para a entidade EquipeSaude.
     * 
     * @param dados  Dados da equipe retornados pelo CNES
     * @param equipe Entidade local a ser atualizada
     * @return Equipe atualizada
     */
    public EquipeSaude mapToEquipe(EquipeType dados, EquipeSaude equipe) {
        if (dados == null)
            return equipe;

        equipe.setIne(dados.getCodigoEquipe()); // O WSDL chama de codigoEquipe mas parece ser o INE
        equipe.setNomeReferencia(dados.getDescricaoEquipe());

        // Mapear Tipo
        if (dados.getCodigoTipoEquipe() != null) {
            try {
                Integer cod = Integer.parseInt(dados.getCodigoTipoEquipe());
                TipoEquipeEnum tipo = TipoEquipeEnum.fromCodigo(cod);
                equipe.setTipoEquipe(tipo != null ? tipo : TipoEquipeEnum.OUTRO);
            } catch (NumberFormatException e) {
                equipe.setTipoEquipe(TipoEquipeEnum.OUTRO);
            }
        }

        if (equipe.getStatus() == null) {
            equipe.setStatus(StatusAtivoEnum.ATIVO);
        }

        if (equipe.getDataAtivacao() == null) {
            equipe.setDataAtivacao(OffsetDateTime.now());
        }

        equipe.setDataUltimaSincronizacaoCnes(OffsetDateTime.now());

        return equipe;
    }

    /**
     * Serializa objeto para JSON.
     */
    public String serializeToJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.error("Erro ao serializar objeto para JSON", e);
            return "{}";
        }
    }
}
