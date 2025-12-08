package com.upsaude.api.request.embeddable;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * Deserializador customizado para IntegracaoGovernamentalVacinaRequest.
 * Aceita tanto data simples (YYYY-MM-DD) quanto OffsetDateTime completo.
 */
public class IntegracaoGovernamentalVacinaRequestDeserializer extends JsonDeserializer<IntegracaoGovernamentalVacinaRequest> {

    @Override
    public IntegracaoGovernamentalVacinaRequest deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        
        IntegracaoGovernamentalVacinaRequest.IntegracaoGovernamentalVacinaRequestBuilder builder = 
                IntegracaoGovernamentalVacinaRequest.builder();
        
        if (node.has("codigoSiPni")) {
            builder.codigoSiPni(node.get("codigoSiPni").asText(null));
        }
        if (node.has("codigoESus")) {
            builder.codigoESus(node.get("codigoESus").asText(null));
        }
        if (node.has("sincronizarPni")) {
            builder.sincronizarPni(node.get("sincronizarPni").asBoolean(false));
        }
        
        if (node.has("ultimaSincronizacaoPni")) {
            JsonNode dataNode = node.get("ultimaSincronizacaoPni");
            if (!dataNode.isNull()) {
                String dataStr = dataNode.asText();
                try {
                    // Tenta parsear como OffsetDateTime completo primeiro
                    builder.ultimaSincronizacaoPni(OffsetDateTime.parse(dataStr));
                } catch (Exception e) {
                    try {
                        // Se falhar, tenta parsear como LocalDate e converte para OffsetDateTime
                        LocalDate localDate = LocalDate.parse(dataStr);
                        builder.ultimaSincronizacaoPni(localDate.atStartOfDay().atOffset(ZoneOffset.UTC));
                    } catch (Exception e2) {
                        // Se ambos falharem, lança exceção
                        throw MismatchedInputException.from(p, IntegracaoGovernamentalVacinaRequest.class, 
                                "Formato de data inválido para ultimaSincronizacaoPni. Use YYYY-MM-DD ou formato ISO 8601 completo.");
                    }
                }
            }
        }
        
        return builder.build();
    }
}
