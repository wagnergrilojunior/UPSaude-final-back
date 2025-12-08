package com.upsaude.api.request.embeddable;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import java.io.IOException;

/**
 * Deserializador customizado para EficaciaVacinaRequest.
 * Aceita tanto String quanto objeto JSON.
 * 
 * Se receber uma String, cria um EficaciaVacinaRequest com o campo 'doencasProtege' preenchido.
 * Se receber um objeto, deserializa normalmente todos os campos.
 */
public class EficaciaVacinaRequestDeserializer extends JsonDeserializer<EficaciaVacinaRequest> {

    @Override
    public EficaciaVacinaRequest deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        
        // Se for uma string simples, cria um objeto com apenas o campo 'doencasProtege'
        if (node.isTextual()) {
            return EficaciaVacinaRequest.builder()
                    .doencasProtege(node.asText())
                    .build();
        }
        
        // Se for um objeto, deserializa normalmente
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
        
        // Se for null, retorna null
        if (node.isNull()) {
            return null;
        }
        
        // Caso contrário, lança exceção
        throw MismatchedInputException.from(p, EficaciaVacinaRequest.class, 
                "EficaciaVacinaRequest deve ser uma String ou um objeto JSON");
    }
}
