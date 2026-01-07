package com.upsaude.mapper.cnes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.embeddable.DadosPessoaisBasicosProfissional;
import com.upsaude.entity.embeddable.DocumentosBasicosProfissional;
import com.upsaude.entity.embeddable.ContatoProfissional;
import com.upsaude.integration.cnes.wsdl.profissional.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

/**
 * Mapper para converter dados do CNES (WSDL) para entidades de Profissional.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class CnesProfissionalMapper {

    private final ObjectMapper objectMapper;

    /**
     * Mapeia dados do CNES para a entidade ProfissionaisSaude.
     * 
     * @param dados        Dados do profissional retornados pelo CNES
     * @param profissional Entidade existente (pode ser nova)
     * @return Profissional atualizado
     */
    public ProfissionaisSaude mapToProfissional(ProfissionalSaudeType dados, ProfissionaisSaude profissional) {
        if (dados == null) {
            log.warn("ProfissionalSaudeType é null, não é possível mapear");
            return profissional;
        }

        // Dados Pessoais Básicos
        if (profissional.getDadosPessoaisBasicos() == null) {
            profissional.setDadosPessoaisBasicos(new DadosPessoaisBasicosProfissional());
        }

        if (dados.getNome() != null && dados.getNome().getNome() != null) {
            profissional.getDadosPessoaisBasicos().setNomeCompleto(dados.getNome().getNome());
        }

        // Documentos Básicos (CPF e CNS)
        if (profissional.getDocumentosBasicos() == null) {
            profissional.setDocumentosBasicos(new DocumentosBasicosProfissional());
        }

        if (dados.getCPF() != null && dados.getCPF().getNumeroCPF() != null) {
            profissional.getDocumentosBasicos().setCpf(dados.getCPF().getNumeroCPF());
        }

        if (dados.getCNS() != null && !dados.getCNS().isEmpty()) {
            CNSType primeiroCns = dados.getCNS().get(0);
            if (primeiroCns != null && primeiroCns.getNumeroCNS() != null) {
                profissional.getDocumentosBasicos().setCns(primeiroCns.getNumeroCNS());
            }
        }

        // Contato
        if (profissional.getContato() == null) {
            profissional.setContato(new ContatoProfissional());
        }

        if (dados.getEmail() != null && !dados.getEmail().isEmpty()) {
            EmailType primeiroEmail = dados.getEmail().get(0);
            if (primeiroEmail != null && primeiroEmail.getDescricaoEmail() != null) {
                profissional.getContato().setEmail(primeiroEmail.getDescricaoEmail());
            }
        }

        // TODO: Mapear CBO se necessário buscar no repositório
        // if (dados.getCBO() != null && !dados.getCBO().isEmpty()) {
        // CBOType primeiroCbo = dados.getCBO().get(0);
        // ...
        // }

        profissional.setDataUltimaSincronizacaoCnes(OffsetDateTime.now());

        return profissional;
    }

    /**
     * Serializa dados do CNES para JSON.
     */
    public String serializeToJson(Object dadosCnes) {
        try {
            return objectMapper.writeValueAsString(dadosCnes);
        } catch (Exception e) {
            log.error("Erro ao serializar dados CNES para JSON", e);
            return "{}";
        }
    }
}
