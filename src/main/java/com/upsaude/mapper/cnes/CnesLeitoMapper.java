package com.upsaude.mapper.cnes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upsaude.entity.cnes.Leitos;
import com.upsaude.enums.StatusLeitoEnum;
import com.upsaude.integration.cnes.wsdl.leito.LeitoType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

/**
 * Mapper para converter dados de Leito do CNES (WSDL) para entidades locais.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class CnesLeitoMapper {

    private final ObjectMapper objectMapper;

    /**
     * Mapeia dados do CNES para a entidade Leitos.
     * 
     * @param dados Dados do leito retornados pelo CNES
     * @param leito Entidade local a ser atualizada
     * @return Leito atualizado
     */
    public Leitos mapToLeito(LeitoType dados, Leitos leito) {
        if (dados == null)
            return leito;

        leito.setCodigoCnesLeito(dados.getCodigo());
        leito.setObservacoes(dados.getDescricao());

        if (leito.getStatus() == null) {
            leito.setStatus(StatusLeitoEnum.DISPONIVEL);
        }

        if (leito.getDataAtivacao() == null) {
            leito.setDataAtivacao(OffsetDateTime.now());
        }

        return leito;
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
