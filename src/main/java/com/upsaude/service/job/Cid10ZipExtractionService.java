package com.upsaude.service.job;

import com.upsaude.exception.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.archivers.zip.UnsupportedZipFeatureException;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class Cid10ZipExtractionService {

    public ExtrairResultado extrairZip(InputStream zipInputStream) {
        if (zipInputStream == null) {
            throw new BadRequestException("InputStream do ZIP é obrigatório");
        }

        List<ArquivoExtraido> arquivos = new ArrayList<>();
        Set<String> nomesArquivos = new HashSet<>();
        Path tempZipFile = null;

        try {
            try (ZipArchiveInputStream zis = new ZipArchiveInputStream(zipInputStream, "UTF-8", false, true)) {
                ZipArchiveEntry entry;
                while ((entry = zis.getNextEntry()) != null) {
                    if (entry.isDirectory()) {
                        continue;
                    }

                    String nomeArquivo = entry.getName();
                    if (nomeArquivo.contains("/")) {
                        nomeArquivo = nomeArquivo.substring(nomeArquivo.lastIndexOf("/") + 1);
                    }

                    if (!StringUtils.hasText(nomeArquivo)) {
                        continue;
                    }

                    if (nomeArquivo.startsWith("._")) {
                        log.debug("Arquivo de metadados do macOS ignorado: {}", nomeArquivo);
                        continue;
                    }

                    if (!nomeArquivo.toLowerCase().endsWith(".csv")) {
                        log.debug("Arquivo não-CSV ignorado: {}", nomeArquivo);
                        continue;
                    }

                    if (nomesArquivos.contains(nomeArquivo.toLowerCase())) {
                        log.warn("Arquivo duplicado ignorado no ZIP: {}", nomeArquivo);
                        continue;
                    }
                    nomesArquivos.add(nomeArquivo.toLowerCase());

                    byte[] conteudo = zis.readAllBytes();
                    if (conteudo == null || conteudo.length == 0) {
                        log.warn("Arquivo vazio ignorado no ZIP: {}", nomeArquivo);
                        continue;
                    }

                    String checksum = calcularChecksum(conteudo);

                    ArquivoExtraido arquivo = new ArquivoExtraido();
                    arquivo.setNome(nomeArquivo);
                    arquivo.setConteudo(conteudo);
                    arquivo.setTamanho((long) conteudo.length);
                    arquivo.setChecksum(checksum);

                    arquivos.add(arquivo);
                    log.debug("Arquivo extraído do ZIP: {} ({} bytes)", nomeArquivo, conteudo.length);
                }
            } catch (UnsupportedZipFeatureException e) {
                log.warn("ZipArchiveInputStream não suporta este ZIP (data descriptors). Tentando com ZipFile...");
                
                tempZipFile = Files.createTempFile("cid10_zip_", ".zip");
                try (FileOutputStream fos = new FileOutputStream(tempZipFile.toFile())) {
                    zipInputStream.transferTo(fos);
                }
                
                try (ZipFile zipFile = ZipFile.builder().setFile(tempZipFile.toFile()).get()) {
                    java.util.Enumeration<ZipArchiveEntry> entries = zipFile.getEntries();
                    while (entries.hasMoreElements()) {
                        ZipArchiveEntry entry = entries.nextElement();
                        if (entry.isDirectory()) {
                            continue;
                        }

                        String nomeArquivo = entry.getName();
                        if (nomeArquivo.contains("/")) {
                            nomeArquivo = nomeArquivo.substring(nomeArquivo.lastIndexOf("/") + 1);
                        }

                        if (!StringUtils.hasText(nomeArquivo)) {
                            continue;
                        }

                        if (nomeArquivo.startsWith("._")) {
                            log.debug("Arquivo de metadados do macOS ignorado: {}", nomeArquivo);
                            continue;
                        }

                        if (!nomeArquivo.toLowerCase().endsWith(".csv")) {
                            log.debug("Arquivo não-CSV ignorado: {}", nomeArquivo);
                            continue;
                        }

                        if (nomesArquivos.contains(nomeArquivo.toLowerCase())) {
                            log.warn("Arquivo duplicado ignorado no ZIP: {}", nomeArquivo);
                            continue;
                        }
                        nomesArquivos.add(nomeArquivo.toLowerCase());

                        try (InputStream entryStream = zipFile.getInputStream(entry)) {
                            byte[] conteudo = entryStream.readAllBytes();
                            if (conteudo == null || conteudo.length == 0) {
                                log.warn("Arquivo vazio ignorado no ZIP: {}", nomeArquivo);
                                continue;
                            }

                            String checksum = calcularChecksum(conteudo);

                            ArquivoExtraido arquivo = new ArquivoExtraido();
                            arquivo.setNome(nomeArquivo);
                            arquivo.setConteudo(conteudo);
                            arquivo.setTamanho((long) conteudo.length);
                            arquivo.setChecksum(checksum);

                            arquivos.add(arquivo);
                            log.debug("Arquivo extraído do ZIP (ZipFile): {} ({} bytes)", nomeArquivo, conteudo.length);
                        }
                    }
                }
            }
        } catch (IOException e) {
            log.error("Erro ao extrair ZIP: {}", e.getMessage(), e);
            throw new BadRequestException("Erro ao extrair arquivo ZIP: " + e.getMessage());
        } finally {
            if (tempZipFile != null) {
                try {
                    Files.deleteIfExists(tempZipFile);
                } catch (IOException e) {
                    log.warn("Erro ao deletar arquivo temporário: {}", tempZipFile, e);
                }
            }
        }

        if (arquivos.isEmpty()) {
            throw new BadRequestException("ZIP não contém arquivos CSV válidos");
        }

        log.info("Total de arquivos CSV extraídos do ZIP: {}", arquivos.size());
        return new ExtrairResultado(arquivos);
    }

    public void validarEstruturaZip(List<ArquivoExtraido> arquivos) {
        if (arquivos == null || arquivos.isEmpty()) {
            throw new BadRequestException("ZIP não contém arquivos");
        }

        Set<String> arquivosEsperados = Set.of(
            "CID-10-CAPITULOS.CSV", "CID-10-GRUPOS.CSV", "CID-10-CATEGORIAS.CSV",
            "CID-10-SUBCATEGORIAS.CSV", "CID-O-GRUPOS.CSV", "CID-O-CATEGORIAS.CSV"
        );

        Set<String> nomesArquivos = arquivos.stream()
            .map(a -> a.getNome().toUpperCase())
            .collect(Collectors.toSet());

        long arquivosEncontrados = arquivosEsperados.stream()
            .filter(nomesArquivos::contains)
            .count();

        if (arquivosEncontrados == 0) {
            log.warn("ZIP não contém arquivos conhecidos do CID-10/CID-O. Arquivos encontrados: {}", nomesArquivos);
        } else {
            log.info("ZIP validado: {} arquivos conhecidos encontrados de {}", arquivosEncontrados, arquivosEsperados.size());
        }
    }

    private String calcularChecksum(byte[] conteudo) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(conteudo);
            return java.util.HexFormat.of().formatHex(hash);
        } catch (Exception e) {
            log.warn("Erro ao calcular checksum: {}", e.getMessage());
            return null;
        }
    }

    @Data
    @AllArgsConstructor
    public static class ArquivoExtraido {
        private String nome;
        private byte[] conteudo;
        private Long tamanho;
        private String checksum;

        public ArquivoExtraido() {
        }

        public InputStream getConteudoAsInputStream() {
            return new ByteArrayInputStream(conteudo);
        }
    }

    @Data
    @AllArgsConstructor
    public static class ExtrairResultado {
        private List<ArquivoExtraido> arquivos;
        private List<String> erros;

        public ExtrairResultado(List<ArquivoExtraido> arquivos) {
            this.arquivos = arquivos;
            this.erros = new ArrayList<>();
        }
    }
}

