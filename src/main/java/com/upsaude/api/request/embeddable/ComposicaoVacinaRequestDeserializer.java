package com.upsaude.api.request.embeddable;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import java.io.IOException;

public class ComposicaoVacinaRequestDeserializer extends JsonDeserializer<ComposicaoVacinaRequest> {

    @Override
    public ComposicaoVacinaRequest deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        if (node.isTextual()) {
            return ComposicaoVacinaRequest.builder()
                    .composicao(node.asText())
                    .build();
        }

        if (node.isObject()) {
            ComposicaoVacinaRequest.ComposicaoVacinaRequestBuilder builder = ComposicaoVacinaRequest.builder();

            if (node.has("composicao")) {
                builder.composicao(node.get("composicao").asText(null));
            }
            if (node.has("tecnologia")) {
                builder.tecnologia(node.get("tecnologia").asText(null));
            }
            if (node.has("adjuvante")) {
                builder.adjuvante(node.get("adjuvante").asText(null));
            }
            if (node.has("conservante")) {
                builder.conservante(node.get("conservante").asText(null));
            }

            return builder.build();
        }

        if (node.isNull()) {
            return null;
        }

        throw MismatchedInputException.from(p, ComposicaoVacinaRequest.class,
                "ComposicaoVacinaRequest deve ser uma String ou um objeto JSON");
    }
}
