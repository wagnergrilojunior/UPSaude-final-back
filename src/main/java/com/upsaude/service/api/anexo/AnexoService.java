package com.upsaude.service.api.anexo;

import com.upsaude.api.request.anexo.AnexoUpdateRequest;
import com.upsaude.api.response.anexo.AnexoDownloadUrlResponse;
import com.upsaude.api.response.anexo.AnexoGestaoResponse;
import com.upsaude.api.response.anexo.AnexoResponse;
import com.upsaude.enums.CategoriaAnexoEnum;
import com.upsaude.enums.StatusAnexoEnum;
import com.upsaude.enums.TargetTypeAnexoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface AnexoService {

    AnexoResponse upload(MultipartFile file, TargetTypeAnexoEnum targetType, UUID targetId, 
                         CategoriaAnexoEnum categoria, Boolean visivelParaPaciente, 
                         String descricao, String tags);

    Page<AnexoResponse> listar(TargetTypeAnexoEnum targetType, UUID targetId, 
                               CategoriaAnexoEnum categoria, StatusAnexoEnum status,
                               Boolean visivelParaPaciente, Pageable pageable);

    AnexoResponse obterPorId(UUID id);

    AnexoDownloadUrlResponse gerarUrlDownload(UUID id, int expiresInSeconds);

    java.io.InputStream downloadStream(UUID id);

    Page<AnexoGestaoResponse> listarGestao(TargetTypeAnexoEnum targetType, UUID targetId,
                                            CategoriaAnexoEnum categoria, StatusAnexoEnum status,
                                            Boolean visivelParaPaciente, String fileName,
                                            UUID criadoPor, OffsetDateTime dataInicio, OffsetDateTime dataFim,
                                            Pageable pageable);

    String gerarThumbnailUrl(UUID id, int width, int height);

    AnexoResponse atualizar(UUID id, AnexoUpdateRequest request);

    void excluir(UUID id, boolean deleteFromStorage);
}
