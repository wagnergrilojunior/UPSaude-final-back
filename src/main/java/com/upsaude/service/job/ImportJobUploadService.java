package com.upsaude.service.job;

import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.entity.sistema.importacao.ImportJob;
import com.upsaude.enums.ImportJobTipoEnum;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
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
     * Cria múltiplos jobs SIGTAP a partir de um arquivo ZIP.
     * Extrai o ZIP, identifica pares de arquivos (dados + layout), faz upload para Storage
     * e cria jobs ordenados por prioridade baseada em dependências.
     */
    CriarJobsZipResultado criarJobsFromZipSigtap(
            MultipartFile zipFile,
            String competenciaAno,
            String competenciaMes,
            Tenant tenant,
            UUID createdByUserId
    );

    /**
     * Cria múltiplos jobs CID-10/CID-O a partir de um arquivo ZIP.
     * Extrai o ZIP, identifica arquivos CSV, faz upload para Storage
     * e cria jobs ordenados por prioridade baseada em dependências.
     */
    CriarJobsZipResultado criarJobsFromZipCid10(
            MultipartFile zipFile,
            String competenciaAno,
            String competenciaMes,
            Tenant tenant,
            UUID createdByUserId
    );

    /**
     * Resultado da criação de jobs a partir de ZIP.
     */
    class CriarJobsZipResultado {
        private List<ImportJob> jobsCriados;
        private int totalArquivosProcessados;
        private List<String> erros;

        public CriarJobsZipResultado(List<ImportJob> jobsCriados, int totalArquivosProcessados, List<String> erros) {
            this.jobsCriados = jobsCriados != null ? jobsCriados : new ArrayList<>();
            this.totalArquivosProcessados = totalArquivosProcessados;
            this.erros = erros != null ? erros : new ArrayList<>();
        }

        public List<ImportJob> getJobsCriados() {
            return jobsCriados;
        }

        public int getTotalArquivosProcessados() {
            return totalArquivosProcessados;
        }

        public List<String> getErros() {
            return erros;
        }
    }
}


