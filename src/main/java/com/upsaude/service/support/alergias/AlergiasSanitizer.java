package com.upsaude.service.support.alergias;

import com.upsaude.api.request.AlergiasRequest;
import org.springframework.stereotype.Service;

@Service
public class AlergiasSanitizer {

    public void sanitizarRequest(AlergiasRequest request) {
        if (request == null) {
            return;
        }

        if (request.getNome() != null) {
            String nomeSanitizado = sanitizarString(request.getNome());
            if (nomeSanitizado != null && !nomeSanitizado.trim().isEmpty()) {
                request.setNome(nomeSanitizado.trim());
            }
        }

        if (request.getNomeCientifico() != null) {
            String nomeCientificoSanitizado = sanitizarString(request.getNomeCientifico());
            if (nomeCientificoSanitizado != null && !nomeCientificoSanitizado.trim().isEmpty()) {
                request.setNomeCientifico(nomeCientificoSanitizado.trim());
            } else if (nomeCientificoSanitizado != null && nomeCientificoSanitizado.trim().isEmpty()) {
                request.setNomeCientifico(null);
            }
        }

        if (request.getCodigoInterno() != null) {
            String codigoSanitizado = sanitizarString(request.getCodigoInterno());
            if (codigoSanitizado != null && !codigoSanitizado.trim().isEmpty()) {
                request.setCodigoInterno(codigoSanitizado.trim());
            } else if (codigoSanitizado != null && codigoSanitizado.trim().isEmpty()) {
                request.setCodigoInterno(null);
            }
        }

        if (request.getDescricao() != null) {
            request.setDescricao(sanitizarString(request.getDescricao()));
        }

        if (request.getSubstanciasRelacionadas() != null) {
            request.setSubstanciasRelacionadas(sanitizarString(request.getSubstanciasRelacionadas()));
        }

        if (request.getObservacoes() != null) {
            request.setObservacoes(sanitizarString(request.getObservacoes()));
        }
    }

    public String sanitizarString(String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll("[\\x00-\\x08\\x0B\\x0C\\x0E-\\x1F]", "");
    }
}
