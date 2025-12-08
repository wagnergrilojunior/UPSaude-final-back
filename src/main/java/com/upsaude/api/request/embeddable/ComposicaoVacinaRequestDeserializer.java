package com.upsaude.api.request.embeddable;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import java.io.IOException;

/**
 * Deserializador customizado para ComposicaoVacinaRequest.
 * Aceita tanto String quanto objeto JSON.
 * 
 * Se receber uma String, cria um ComposicaoVacinaRequest com apenas o campo 'composicao' preenchido.
 * Se receber um objeto, deserializa normalmente todos os campos.
 */
public class ComposicaoVacinaRequestDeserializer extends JsonDeserializer<ComposicaoVacinaRequest> {

    @Override
    public ComposicaoVacinaRequest deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        
        // Se for uma string simples, cria um objeto com apenas o campo 'composicao'
        if (node.isTextual()) {
            return ComposicaoVacinaRequest.builder()
                    .composicao(node.asText())
                    .build();
        }
        
        // Se for um objeto, deserializa normalmente
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
        
        // Se for null, retorna null
        if (node.isNull()) {
            return null;
        }
        
        // Caso contrário, lança exceção
        throw MismatchedInputException.from(p, ComposicaoVacinaRequest.class, 
                "ComposicaoVacinaRequest deve ser uma String ou um objeto JSON");
    }
}
