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

/**
 * Serviço responsável por extrair arquivos CSV de um ZIP do CID-10/CID-O e validar a estrutura.
 */
@Slf4j
@Service
public class Cid10ZipExtractionService {

    /**
     * Extrai todos os arquivos CSV de um ZIP e retorna resultado com metadados.
     */
    public ExtrairResultado extrairZip(InputStream zipInputStream) {
        if (zipInputStream == null) {
            throw new BadRequestException("InputStream do ZIP é obrigatório");
        }

        List<ArquivoExtraido> arquivos = new ArrayList<>();
        Set<String> nomesArquivos = new HashSet<>();
        Path tempZipFile = null;

        try {
            // Tenta primeiro com ZipArchiveInputStream (mais eficiente para streams)
            try (ZipArchiveInputStream zis = new ZipArchiveInputStream(zipInputStream, "UTF-8", false, true)) {
                ZipArchiveEntry entry;
                while ((entry = zis.getNextEntry()) != null) {
                    if (entry.isDirectory()) {
                        continue;
                    }

                    String nomeArquivo = entry.getName();
                    // Remove caminhos de diretório, mantém apenas o nome do arquivo
                    if (nomeArquivo.contains("/")) {
                        nomeArquivo = nomeArquivo.substring(nomeArquivo.lastIndexOf("/") + 1);
                    }

                    if (!StringUtils.hasText(nomeArquivo)) {
                        continue;
                    }

                    // Ignora arquivos de metadados do macOS (começam com ._)
                    if (nomeArquivo.startsWith("._")) {
                        log.debug("Arquivo de metadados do macOS ignorado: {}", nomeArquivo);
                        continue;
                    }

                    // Processa apenas arquivos .CSV (case-insensitive)
                    if (!nomeArquivo.toLowerCase().endsWith(".csv")) {
                        log.debug("Arquivo não-CSV ignorado: {}", nomeArquivo);
                        continue;
                    }

                    // Evita duplicatas (mesmo nome de arquivo)
                    if (nomesArquivos.contains(nomeArquivo.toLowerCase())) {
                        log.warn("Arquivo duplicado ignorado no ZIP: {}", nomeArquivo);
                        continue;
                    }
                    nomesArquivos.add(nomeArquivo.toLowerCase());

                    // Lê conteúdo do arquivo
                    byte[] conteudo = zis.readAllBytes();
                    if (conteudo == null || conteudo.length == 0) {
                        log.warn("Arquivo vazio ignorado no ZIP: {}", nomeArquivo);
                        continue;
                    }

                    // Calcula checksum
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
                // Se ZipArchiveInputStream falhar com data descriptors, tenta com ZipFile
                log.warn("ZipArchiveInputStream não suporta este ZIP (data descriptors). Tentando com ZipFile...");
                
                // Salva o InputStream em um arquivo temporário para usar ZipFile
                tempZipFile = Files.createTempFile("cid10_zip_", ".zip");
                try (FileOutputStream fos = new FileOutputStream(tempZipFile.toFile())) {
                    zipInputStream.transferTo(fos);
                }
                
                // Usa ZipFile que tem melhor suporte a data descriptors
                try (ZipFile zipFile = ZipFile.builder().setFile(tempZipFile.toFile()).get()) {
                    java.util.Enumeration<ZipArchiveEntry> entries = zipFile.getEntries();
                    while (entries.hasMoreElements()) {
                        ZipArchiveEntry entry = entries.nextElement();
                        if (entry.isDirectory()) {
                            continue;
                        }

                        String nomeArquivo = entry.getName();
                        // Remove caminhos de diretório, mantém apenas o nome do arquivo
                        if (nomeArquivo.contains("/")) {
                            nomeArquivo = nomeArquivo.substring(nomeArquivo.lastIndexOf("/") + 1);
                        }

                        if (!StringUtils.hasText(nomeArquivo)) {
                            continue;
                        }

                        // Ignora arquivos de metadados do macOS (começam com ._)
                        if (nomeArquivo.startsWith("._")) {
                            log.debug("Arquivo de metadados do macOS ignorado: {}", nomeArquivo);
                            continue;
                        }

                        // Processa apenas arquivos .CSV (case-insensitive)
                        if (!nomeArquivo.toLowerCase().endsWith(".csv")) {
                            log.debug("Arquivo não-CSV ignorado: {}", nomeArquivo);
                            continue;
                        }

                        // Evita duplicatas (mesmo nome de arquivo)
                        if (nomesArquivos.contains(nomeArquivo.toLowerCase())) {
                            log.warn("Arquivo duplicado ignorado no ZIP: {}", nomeArquivo);
                            continue;
                        }
                        nomesArquivos.add(nomeArquivo.toLowerCase());

                        // Lê conteúdo do arquivo usando ZipFile
                        try (InputStream entryStream = zipFile.getInputStream(entry)) {
                            byte[] conteudo = entryStream.readAllBytes();
                            if (conteudo == null || conteudo.length == 0) {
                                log.warn("Arquivo vazio ignorado no ZIP: {}", nomeArquivo);
                                continue;
                            }

                            // Calcula checksum
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
            // Limpa arquivo temporário se foi criado
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

    /**
     * Valida se o ZIP contém arquivos esperados do CID-10/CID-O.
     * Verifica se há pelo menos alguns arquivos conhecidos.
     */
    public void validarEstruturaZip(List<ArquivoExtraido> arquivos) {
        if (arquivos == null || arquivos.isEmpty()) {
            throw new BadRequestException("ZIP não contém arquivos");
        }

        // Lista de arquivos esperados (pelo menos alguns devem estar presentes)
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
            // Não falha, apenas avisa - pode ser uma versão diferente do CID-10
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

    /**
     * Representa um arquivo extraído do ZIP.
     */
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

    /**
     * Resultado da extração do ZIP.
     */
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

