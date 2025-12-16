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

                    builder.ultimaSincronizacaoPni(OffsetDateTime.parse(dataStr));
                } catch (Exception e) {
                    try {

                        LocalDate localDate = LocalDate.parse(dataStr);
                        builder.ultimaSincronizacaoPni(localDate.atStartOfDay().atOffset(ZoneOffset.UTC));
                    } catch (Exception e2) {

                        throw MismatchedInputException.from(p, IntegracaoGovernamentalVacinaRequest.class,
                                "Formato de data inválido para ultimaSincronizacaoPni. Use YYYY-MM-DD ou formato ISO 8601 completo.");
                    }
                }
            }
        }

        return builder.build();
    }
}
