package com.upsaude.api.request.embeddable;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import java.io.IOException;

public class EficaciaVacinaRequestDeserializer extends JsonDeserializer<EficaciaVacinaRequest> {

    @Override
    public EficaciaVacinaRequest deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        if (node.isTextual()) {
            return EficaciaVacinaRequest.builder()
                    .doencasProtege(node.asText())
                    .build();
        }

        if (node.isObject()) {
            EficaciaVacinaRequest.EficaciaVacinaRequestBuilder builder = EficaciaVacinaRequest.builder();

            if (node.has("eficaciaPercentual")) {
                builder.eficaciaPercentual(node.get("eficaciaPercentual").decimalValue());
            }
            if (node.has("protecaoInicioDias")) {
                builder.protecaoInicioDias(node.get("protecaoInicioDias").asInt());
            }
            if (node.has("protecaoDuracaoAnos")) {
                builder.protecaoDuracaoAnos(node.get("protecaoDuracaoAnos").asInt());
            }
            if (node.has("doencasProtege")) {
                builder.doencasProtege(node.get("doencasProtege").asText(null));
            }

            return builder.build();
        }

        if (node.isNull()) {
            return null;
        }

        throw MismatchedInputException.from(p, EficaciaVacinaRequest.class,
                "EficaciaVacinaRequest deve ser uma String ou um objeto JSON");
    }
}
