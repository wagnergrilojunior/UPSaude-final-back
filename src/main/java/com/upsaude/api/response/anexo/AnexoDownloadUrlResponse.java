package com.upsaude.api.response.anexo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response com URL assinada para download do anexo")
public class AnexoDownloadUrlResponse {

    @Schema(description = "URL assinada para download (válida por tempo limitado)")
    private String signedUrl;

    @Schema(description = "Data/hora de expiração da URL")
    private OffsetDateTime expiresAt;

    @Schema(description = "Nome do arquivo")
    private String fileName;

    @Schema(description = "Tipo MIME do arquivo")
    private String mimeType;

    @Schema(description = "Tamanho do arquivo em bytes")
    private Long sizeBytes;
}
