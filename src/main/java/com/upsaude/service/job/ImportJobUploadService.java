package com.upsaude.service.job;

import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.entity.sistema.importacao.ImportJob;
import com.upsaude.enums.ImportJobTipoEnum;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


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

    
    CriarJobsZipResultado criarJobsFromZipSigtap(
            MultipartFile zipFile,
            String competenciaAno,
            String competenciaMes,
            Tenant tenant,
            UUID createdByUserId
    );

    
    CriarJobsZipResultado criarJobsFromZipCid10(
            MultipartFile zipFile,
            String competenciaAno,
            String competenciaMes,
            Tenant tenant,
            UUID createdByUserId
    );

    
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


