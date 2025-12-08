package com.upsaude.api.request.embeddable;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Deserializador customizado para ReacoesAdversasVacinaRequest.
 * Aceita tanto o formato antigo (arrays) quanto o formato novo (strings).
 * 
 * Formato antigo (arrays):
 * {
 *   "reacoesComuns": ["reação 1", "reação 2"],
 *   "reacoesSeveras": ["reação grave"],
 *   "informarMedico": true
 * }
 * 
 * Formato novo (strings):
 * {
 *   "reacoesAdversasComuns": "reação 1, reação 2",
 *   "reacoesAdversasRaras": "reação rara",
 *   "reacoesAdversasGraves": "reação grave"
 * }
 */
public class ReacoesAdversasVacinaRequestDeserializer extends JsonDeserializer<ReacoesAdversasVacinaRequest> {

    @Override
    public ReacoesAdversasVacinaRequest deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        
        ReacoesAdversasVacinaRequest.ReacoesAdversasVacinaRequestBuilder builder = ReacoesAdversasVacinaRequest.builder();
        
        // Formato novo: campos diretos como strings
        if (node.has("reacoesAdversasComuns")) {
            builder.reacoesAdversasComuns(node.get("reacoesAdversasComuns").asText(null));
        }
        if (node.has("reacoesAdversasRaras")) {
            builder.reacoesAdversasRaras(node.get("reacoesAdversasRaras").asText(null));
        }
        if (node.has("reacoesAdversasGraves")) {
            builder.reacoesAdversasGraves(node.get("reacoesAdversasGraves").asText(null));
        }
        
        // Formato antigo: arrays que precisam ser convertidos para strings
        if (node.has("reacoesComuns")) {
            JsonNode reacoesComunsNode = node.get("reacoesComuns");
            if (reacoesComunsNode.isArray()) {
                List<String> reacoes = new ArrayList<>();
                for (JsonNode item : reacoesComunsNode) {
                    if (item.isTextual()) {
                        reacoes.add(item.asText());
                    }
                }
                builder.reacoesAdversasComuns(String.join(", ", reacoes));
            } else if (reacoesComunsNode.isTextual()) {
                builder.reacoesAdversasComuns(reacoesComunsNode.asText());
            }
        }
        
        if (node.has("reacoesSeveras")) {
            JsonNode reacoesSeverasNode = node.get("reacoesSeveras");
            if (reacoesSeverasNode.isArray()) {
                List<String> reacoes = new ArrayList<>();
                for (JsonNode item : reacoesSeverasNode) {
                    if (item.isTextual()) {
                        reacoes.add(item.asText());
                    }
                }
                builder.reacoesAdversasGraves(String.join(", ", reacoes));
            } else if (reacoesSeverasNode.isTextual()) {
                builder.reacoesAdversasGraves(reacoesSeverasNode.asText());
            }
        }
        
        // Campo informarMedico é ignorado (não existe na entidade)
        
        return builder.build();
    }
}
