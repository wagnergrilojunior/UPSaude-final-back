package com.upsaude.service.job;

import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.entity.sistema.importacao.ImportJob;
import com.upsaude.enums.ImportJobTipoEnum;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * Service responsável por receber o arquivo (via controller), fazer upload para Supabase Storage
 * e criar o job ENFILEIRADO no banco.
 *
 * Importante: não processa o arquivo; apenas cria o job.
 */
public interface ImportJobUploadService {

    ImportJob criarJobUpload(
            MultipartFile file,
            ImportJobTipoEnum tipo,
            String competenciaAno,
            String competenciaMes,
            String uf,
            Tenant tenant,
            UUID createdByUserId
    );

    /**
     * SIGTAP exige um arquivo de layout (tb_*.txt + tb_*_layout.txt).
     * Este método faz upload de ambos e grava payloadJson com o layoutPath.
     */
    ImportJob criarJobUploadComLayoutSigtap(
            MultipartFile fileDados,
            MultipartFile fileLayout,
            String competenciaAno,
            String competenciaMes,
            Tenant tenant,
            UUID createdByUserId
    );
}


