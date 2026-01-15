package com.upsaude.integration.brevo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BrevoEmailRequest {
    
    private Sender sender;
    private List<Recipient> to;
    private String subject;
    private Integer templateId;
    private Map<String, Object> params;
    private String htmlContent;
    private String textContent;
    private List<String> tags;
    private Map<String, String> headers;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Sender {
        private String name;
        private String email;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Recipient {
        private String name;
        private String email;
    }
}
