package com.upsaude.mapper.cnes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upsaude.entity.estabelecimento.equipamento.Equipamentos;
import com.upsaude.entity.embeddable.DadosIdentificacaoEquipamento;
import com.upsaude.enums.TipoEquipamentoEnum;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.integration.cnes.wsdl.equipamento.EquipamentoType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

/**
 * Mapper para converter dados de Equipamento do CNES (WSDL) para entidades
 * locais.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class CnesEquipamentoMapper {

    private final ObjectMapper objectMapper;

    /**
     * Mapeia dados do CNES para a entidade Equipamentos.
     * 
     * @param dados       Dados do equipamento retornados pelo CNES
     * @param equipamento Entidade local a ser atualizada
     * @return Equipamento atualizado
     */
    public Equipamentos mapToEquipamento(EquipamentoType dados, Equipamentos equipamento) {
        if (dados == null)
            return equipamento;

        if (equipamento.getDadosIdentificacao() == null) {
            equipamento.setDadosIdentificacao(new DadosIdentificacaoEquipamento());
        }

        equipamento.getDadosIdentificacao().setCodigoCnes(dados.getCodigo());
        equipamento.getDadosIdentificacao().setNome(dados.getDescricao());

        // Mapear Tipo
        if (dados.getTipoEquipamento() != null && dados.getTipoEquipamento().getCodigo() != null) {
            try {
                Integer cod = Integer.parseInt(dados.getTipoEquipamento().getCodigo());
                TipoEquipamentoEnum tipo = TipoEquipamentoEnum.fromCodigo(cod);
                equipamento.getDadosIdentificacao().setTipo(tipo != null ? tipo : TipoEquipamentoEnum.OUTRO);
            } catch (NumberFormatException e) {
                equipamento.getDadosIdentificacao().setTipo(TipoEquipamentoEnum.OUTRO);
            }
        } else {
            equipamento.getDadosIdentificacao().setTipo(TipoEquipamentoEnum.OUTRO);
        }

        if (equipamento.getStatus() == null) {
            equipamento.setStatus(StatusAtivoEnum.ATIVO);
        }

        equipamento.setDataUltimaSincronizacaoCnes(OffsetDateTime.now());

        return equipamento;
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
