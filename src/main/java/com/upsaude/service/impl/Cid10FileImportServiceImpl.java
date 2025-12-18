package com.upsaude.service.impl;

import com.upsaude.entity.cid.Cid10Capitulos;
import com.upsaude.entity.cid.Cid10Categorias;
import com.upsaude.entity.cid.Cid10Grupos;
import com.upsaude.entity.cid.Cid10Subcategorias;
import com.upsaude.entity.cid.CidOCategorias;
import com.upsaude.entity.cid.CidOGrupos;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.InvalidArgumentException;
import com.upsaude.importacao.cid10.file.Cid10CsvParser;
import com.upsaude.mapper.cid10.Cid10EntityMapper;
import com.upsaude.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

@Slf4j
@Service
public class Cid10FileImportServiceImpl {

    private static final Charset UTF_8 = Charset.forName("UTF-8");

    @Value("${cid10.import.base-path:data_import/cid10}")
    private String basePath;

    @Value("${cid10.import.batch-size:500}")
    private int batchSize;

    private final Cid10CsvParser csvParser;
    private final Cid10EntityMapper entityMapper;
    
    private final Cid10CapitulosRepository capitulosRepository;
    private final Cid10GruposRepository gruposRepository;
    private final Cid10CategoriasRepository categoriasRepository;
    private final Cid10SubcategoriasRepository subcategoriasRepository;
    private final CidOGruposRepository cidOGruposRepository;
    private final CidOCategoriasRepository cidOCategoriasRepository;

    public Cid10FileImportServiceImpl(
            Cid10CsvParser csvParser,
            Cid10EntityMapper entityMapper,
            Cid10CapitulosRepository capitulosRepository,
            Cid10GruposRepository gruposRepository,
            Cid10CategoriasRepository categoriasRepository,
            Cid10SubcategoriasRepository subcategoriasRepository,
            CidOGruposRepository cidOGruposRepository,
            CidOCategoriasRepository cidOCategoriasRepository) {
        this.csvParser = csvParser;
        this.entityMapper = entityMapper;
        this.capitulosRepository = capitulosRepository;
        this.gruposRepository = gruposRepository;
        this.categoriasRepository = categoriasRepository;
        this.subcategoriasRepository = subcategoriasRepository;
        this.cidOGruposRepository = cidOGruposRepository;
        this.cidOCategoriasRepository = cidOCategoriasRepository;
    }

    public ImportResult importarCompetencia(String competencia) {
        log.info("Iniciando importação da competência: {}", competencia);
        ImportResult result = new ImportResult(competencia);

        Path competenciaPath = Paths.get(basePath, competencia);
        if (!Files.exists(competenciaPath) || !Files.isDirectory(competenciaPath)) {
            throw new BadRequestException("Pasta da competência não encontrada: " + competenciaPath);
        }

        try {
            importarArquivo(competenciaPath, "CID-10-CAPITULOS.CSV", competencia, result, this::importarCapitulos);
            importarArquivo(competenciaPath, "CID-10-GRUPOS.CSV", competencia, result, this::importarGrupos);
            importarArquivo(competenciaPath, "CID-10-CATEGORIAS.CSV", competencia, result, this::importarCategorias);
            importarArquivo(competenciaPath, "CID-10-SUBCATEGORIAS.CSV", competencia, result, this::importarSubcategorias);
            importarArquivo(competenciaPath, "CID-O-GRUPOS.CSV", competencia, result, this::importarCidOGrupos);
            importarArquivo(competenciaPath, "CID-O-CATEGORIAS.CSV", competencia, result, this::importarCidOCategorias);

            log.info("Importação da competência {} concluída. Total: {} linhas processadas, {} erros", 
                    competencia, result.getTotalLinhasProcessadas(), result.getTotalErros());
        } catch (Exception e) {
            log.error("Erro ao importar competência {}: {}", competencia, e.getMessage(), e);
            result.addErro("Erro geral: " + e.getMessage());
        }

        return result;
    }

    private void importarArquivo(Path competenciaPath, String nomeArquivo, String competencia, 
                                ImportResult result, Function<ImportContext, Integer> importador) {
        Path arquivoPath = competenciaPath.resolve(nomeArquivo);

        if (!Files.exists(arquivoPath)) {
            log.warn("Arquivo não encontrado: {}", arquivoPath);
            return;
        }

        try {
            ImportContext context = new ImportContext(arquivoPath, competencia);
            int linhasProcessadas = importador.apply(context);
            result.addLinhasProcessadas(nomeArquivo, linhasProcessadas);
            log.info("Arquivo {} importado: {} linhas processadas", nomeArquivo, linhasProcessadas);
        } catch (IllegalStateException e) {
            log.error("ERRO CRÍTICO ao importar arquivo {}: Contexto Spring fechado. {}", nomeArquivo, e.getMessage(), e);
            result.addErro(nomeArquivo + ": ERRO CRÍTICO - Contexto Spring fechado: " + e.getMessage());
        } catch (ConfigurationPropertiesBindException e) {
            log.error("ERRO CRÍTICO ao importar arquivo {}: Spring tentando recriar bean quando contexto fechado. {}", nomeArquivo, e.getMessage(), e);
            result.addErro(nomeArquivo + ": ERRO CRÍTICO - Spring tentando recriar bean quando contexto fechado: " + e.getMessage());
        } catch (Exception e) {
            log.error("Erro ao importar arquivo {}: {}", nomeArquivo, e.getMessage(), e);
            result.addErro(nomeArquivo + ": " + e.getMessage());
        }
    }

    @Transactional
    private int importarCapitulos(ImportContext context) {
        return importarComBatchComLookup(
                context,
                fields -> entityMapper.mapToCapitulo(fields, context.competencia),
                capitulo -> String.valueOf(capitulo.getNumcap()),
                capitulos -> {
                    if (capitulos.isEmpty()) return Collections.emptyMap();
                    List<Integer> numcaps = new ArrayList<>();
                    for (Cid10Capitulos c : capitulos) {
                        numcaps.add(c.getNumcap());
                    }
                    List<Cid10Capitulos> existentes = capitulosRepository.findByNumcapIn(numcaps);
                    Map<String, Cid10Capitulos> map = new HashMap<>();
                    for (Cid10Capitulos e : existentes) {
                        map.put(String.valueOf(e.getNumcap()), e);
                    }
                    return map;
                },
                (capitulo, existing) -> {
                    existing.setCatinic(capitulo.getCatinic());
                    existing.setCatfim(capitulo.getCatfim());
                    existing.setDescricao(capitulo.getDescricao());
                    existing.setDescricaoAbreviada(capitulo.getDescricaoAbreviada());
                    return existing;
                },
                capitulosRepository::saveAll
        );
    }

    @Transactional
    private int importarGrupos(ImportContext context) {
        return importarComBatchComLookup(
                context,
                fields -> entityMapper.mapToGrupo(fields, context.competencia),
                grupo -> grupo.getCatinic() + "|" + grupo.getCatfim(),
                grupos -> {
                    if (grupos.isEmpty()) return Collections.emptyMap();
                    List<String> catinics = new ArrayList<>();
                    List<String> catfims = new ArrayList<>();
                    for (Cid10Grupos g : grupos) {
                        catinics.add(g.getCatinic());
                        catfims.add(g.getCatfim());
                    }
                    List<Cid10Grupos> existentes = gruposRepository.findByCatinicAndCatfimPairs(catinics, catfims);
                    Map<String, Cid10Grupos> map = new HashMap<>();
                    for (Cid10Grupos e : existentes) {
                        map.put(e.getCatinic() + "|" + e.getCatfim(), e);
                    }
                    return map;
                },
                (grupo, existing) -> {
                    existing.setDescricao(grupo.getDescricao());
                    existing.setDescricaoAbreviada(grupo.getDescricaoAbreviada());
                    return existing;
                },
                gruposRepository::saveAll
        );
    }

    @Transactional
    private int importarCategorias(ImportContext context) {
        return importarComBatchComLookup(
                context,
                fields -> entityMapper.mapToCategoria(fields, context.competencia),
                categoria -> categoria.getCat(),
                categorias -> {
                    if (categorias.isEmpty()) return Collections.emptyMap();
                    List<String> cats = new ArrayList<>();
                    for (Cid10Categorias c : categorias) {
                        cats.add(c.getCat());
                    }
                    List<Cid10Categorias> existentes = categoriasRepository.findByCatIn(cats);
                    Map<String, Cid10Categorias> map = new HashMap<>();
                    for (Cid10Categorias e : existentes) {
                        map.put(e.getCat(), e);
                    }
                    return map;
                },
                (categoria, existing) -> {
                    existing.setClassif(categoria.getClassif());
                    existing.setDescricao(categoria.getDescricao());
                    existing.setDescrAbrev(categoria.getDescrAbrev());
                    existing.setRefer(categoria.getRefer());
                    existing.setExcluidos(categoria.getExcluidos());
                    return existing;
                },
                categoriasRepository::saveAll
        );
    }

    @Transactional(timeout = 3600)
    private int importarSubcategorias(ImportContext context) {
        return importarComBatchComLookup(
                context,
                fields -> entityMapper.mapToSubcategoria(fields, context.competencia),
                subcategoria -> subcategoria.getSubcat(),
                subcategorias -> {
                    if (subcategorias.isEmpty()) return Collections.emptyMap();
                    List<String> subcats = new ArrayList<>();
                    for (Cid10Subcategorias s : subcategorias) {
                        subcats.add(s.getSubcat());
                    }
                    List<Cid10Subcategorias> existentes = subcategoriasRepository.findBySubcatIn(subcats);
                    Map<String, Cid10Subcategorias> map = new HashMap<>();
                    for (Cid10Subcategorias e : existentes) {
                        map.put(e.getSubcat(), e);
                    }
                    return map;
                },
                (subcategoria, existing) -> {
                    existing.setClassif(subcategoria.getClassif());
                    existing.setRestrSexo(subcategoria.getRestrSexo());
                    existing.setCausaObito(subcategoria.getCausaObito());
                    existing.setDescricao(subcategoria.getDescricao());
                    existing.setDescrAbrev(subcategoria.getDescrAbrev());
                    existing.setRefer(subcategoria.getRefer());
                    existing.setExcluidos(subcategoria.getExcluidos());
                    return existing;
                },
                subcategoriasRepository::saveAll
        );
    }

    @Transactional
    private int importarCidOGrupos(ImportContext context) {
        return importarComBatchComLookup(
                context,
                fields -> entityMapper.mapToCidOGrupo(fields, context.competencia),
                grupo -> grupo.getCatinic() + "|" + grupo.getCatfim(),
                grupos -> {
                    if (grupos.isEmpty()) return Collections.emptyMap();
                    List<String> catinics = new ArrayList<>();
                    List<String> catfims = new ArrayList<>();
                    for (CidOGrupos g : grupos) {
                        catinics.add(g.getCatinic());
                        catfims.add(g.getCatfim());
                    }
                    List<CidOGrupos> existentes = cidOGruposRepository.findByCatinicAndCatfimPairs(catinics, catfims);
                    Map<String, CidOGrupos> map = new HashMap<>();
                    for (CidOGrupos e : existentes) {
                        map.put(e.getCatinic() + "|" + e.getCatfim(), e);
                    }
                    return map;
                },
                (grupo, existing) -> {
                    existing.setDescricao(grupo.getDescricao());
                    existing.setRefer(grupo.getRefer());
                    return existing;
                },
                cidOGruposRepository::saveAll
        );
    }

    @Transactional
    private int importarCidOCategorias(ImportContext context) {
        return importarComBatchComLookup(
                context,
                fields -> entityMapper.mapToCidOCategoria(fields, context.competencia),
                categoria -> categoria.getCat(),
                categorias -> {
                    if (categorias.isEmpty()) return Collections.emptyMap();
                    List<String> cats = new ArrayList<>();
                    for (CidOCategorias c : categorias) {
                        cats.add(c.getCat());
                    }
                    List<CidOCategorias> existentes = cidOCategoriasRepository.findByCatIn(cats);
                    Map<String, CidOCategorias> map = new HashMap<>();
                    for (CidOCategorias e : existentes) {
                        map.put(e.getCat(), e);
                    }
                    return map;
                },
                (categoria, existing) -> {
                    existing.setDescricao(categoria.getDescricao());
                    existing.setRefer(categoria.getRefer());
                    return existing;
                },
                cidOCategoriasRepository::saveAll
        );
    }

    private <T> int importarComBatchComLookup(
            ImportContext context,
            Function<Map<String, String>, T> mapper,
            Function<T, String> keyExtractor,
            Function<List<T>, Map<String, T>> batchLookup,
            BiFunction<T, T, T> updater,
            Function<List<T>, List<T>> saver) {
        List<T> batch = new ArrayList<>();
        int totalLinhas = 0;
        int linhasComErro = 0;
        int linhasLidas = 0;
        long inicioTempo = System.currentTimeMillis();
        List<String> errosDetalhados = new ArrayList<>();
        String[] headers = null;

        try (BufferedReader reader = Files.newBufferedReader(context.arquivoPath, UTF_8)) {
            String linha;
            boolean primeiraLinha = true;
            
            while ((linha = reader.readLine()) != null) {
                linhasLidas++;
                
                if (primeiraLinha) {
                    headers = parseHeaders(linha);
                    primeiraLinha = false;
                    continue;
                }

                if (linhasLidas % 1000 == 0) {
                    log.info("Processando arquivo {}: {} linhas lidas, {} processadas, {} erros", 
                            context.arquivoPath.getFileName(), linhasLidas, totalLinhas, linhasComErro);
                }

                try {
                    if (linha.trim().isEmpty()) {
                        continue;
                    }

                    Map<String, String> fields = csvParser.parseLine(linha, headers);
                    
                    if (!validarCamposBasicos(fields, linhasLidas, errosDetalhados)) {
                        linhasComErro++;
                        continue;
                    }

                    T entity;
                    try {
                        entity = mapper.apply(fields);
                    } catch (InvalidArgumentException | IllegalArgumentException e) {
                        linhasComErro++;
                        String erroMsg = String.format("Linha %d: %s", linhasLidas, e.getMessage());
                        errosDetalhados.add(erroMsg);
                        log.debug("Erro de validação na linha {}: {}", linhasLidas, e.getMessage());
                        continue;
                    } catch (IllegalStateException e) {
                        if (e.getMessage() != null && e.getMessage().contains("closed")) {
                            linhasComErro++;
                            String erroMsg = String.format("Linha %d: ERRO CRÍTICO - Contexto Spring fechado durante mapeamento: %s", linhasLidas, e.getMessage());
                            errosDetalhados.add(erroMsg);
                            log.error("ERRO CRÍTICO ao mapear linha {} do arquivo {}: Contexto Spring fechado. Parando processamento deste arquivo.", 
                                    linhasLidas, context.arquivoPath.getFileName(), e);
                            break;
                        }
                        throw e;
                    } catch (ConfigurationPropertiesBindException e) {
                        linhasComErro++;
                        String erroMsg = String.format("Linha %d: ERRO CRÍTICO - Spring tentando recriar bean quando contexto fechado durante mapeamento: %s", linhasLidas, e.getMessage());
                        errosDetalhados.add(erroMsg);
                        log.error("ERRO CRÍTICO ao mapear linha {} do arquivo {}: Spring tentando recriar bean quando contexto fechado. Parando processamento deste arquivo.", 
                                linhasLidas, context.arquivoPath.getFileName(), e);
                        break;
                    }
                    
                    if (entity == null) {
                        linhasComErro++;
                        errosDetalhados.add(String.format("Linha %d: Entidade nula após mapeamento", linhasLidas));
                        continue;
                    }

                    batch.add(entity);

                    if (batch.size() >= batchSize) {
                        try {
                            Map<String, T> existentes = batchLookup.apply(batch);
                            List<T> toSave = new ArrayList<>();
                            for (T e : batch) {
                                String key = keyExtractor.apply(e);
                                T existing = existentes.get(key);
                                if (existing != null) {
                                    T updated = updater.apply(e, existing);
                                    toSave.add(updated);
                                } else {
                                    toSave.add(e);
                                }
                            }
                            existentes.clear();
                            saver.apply(toSave);
                            totalLinhas += batch.size();
                            batch.clear();
                            toSave.clear();
                        } catch (IllegalStateException e) {
                            if (e.getMessage() != null && e.getMessage().contains("closed")) {
                                log.error("ERRO CRÍTICO ao salvar batch no arquivo {}: Contexto Spring fechado. Parando processamento deste arquivo.", 
                                        context.arquivoPath.getFileName(), e);
                                linhasComErro += batch.size();
                                batch.clear();
                                break;
                            }
                            log.error("Erro ao salvar batch no arquivo {} (linhas {}-{}): {}", 
                                    context.arquivoPath.getFileName(), 
                                    linhasLidas - batchSize + 1, 
                                    linhasLidas, 
                                    e.getMessage(), e);
                            linhasComErro += batch.size();
                            batch.clear();
                        } catch (ConfigurationPropertiesBindException e) {
                            log.error("ERRO CRÍTICO ao salvar batch no arquivo {}: Spring tentando recriar bean quando contexto fechado. Parando processamento deste arquivo.", 
                                    context.arquivoPath.getFileName(), e);
                            linhasComErro += batch.size();
                            batch.clear();
                            break;
                        } catch (Exception e) {
                            log.error("Erro ao salvar batch no arquivo {} (linhas {}-{}): {}", 
                                    context.arquivoPath.getFileName(), 
                                    linhasLidas - batchSize + 1, 
                                    linhasLidas, 
                                    e.getMessage(), e);
                            linhasComErro += batch.size();
                            batch.clear();
                        }
                    }
                } catch (InvalidArgumentException | IllegalArgumentException e) {
                    linhasComErro++;
                    String erroMsg = String.format("Linha %d: %s", linhasLidas, e.getMessage());
                    errosDetalhados.add(erroMsg);
                    log.debug("Erro de validação na linha {}: {}", linhasLidas, e.getMessage());
                    
                    if (errosDetalhados.size() > 100) {
                        errosDetalhados.remove(0);
                    }
                } catch (IllegalStateException e) {
                    linhasComErro++;
                    String erroMsg = String.format("Linha %d: ERRO CRÍTICO - Contexto Spring fechado ou problema de estado: %s", linhasLidas, e.getMessage());
                    errosDetalhados.add(erroMsg);
                    log.error("ERRO CRÍTICO ao processar linha {} do arquivo {}: Contexto Spring pode estar fechado. Parando processamento deste arquivo.", 
                            linhasLidas, context.arquivoPath.getFileName(), e);
                    break;
                } catch (ConfigurationPropertiesBindException e) {
                    linhasComErro++;
                    String erroMsg = String.format("Linha %d: ERRO CRÍTICO - Spring tentando recriar bean quando contexto fechado: %s", linhasLidas, e.getMessage());
                    errosDetalhados.add(erroMsg);
                    log.error("ERRO CRÍTICO ao processar linha {} do arquivo {}: Spring tentando recriar bean quando contexto fechado. Parando processamento deste arquivo.", 
                            linhasLidas, context.arquivoPath.getFileName(), e);
                    break;
                } catch (Exception e) {
                    linhasComErro++;
                    String erroMsg = String.format("Linha %d: %s", linhasLidas, e.getMessage());
                    errosDetalhados.add(erroMsg);
                    log.warn("Erro ao processar linha {} do arquivo {}: {} (tipo: {})", 
                            linhasLidas, context.arquivoPath.getFileName(), e.getMessage(), e.getClass().getName());
                    
                    if (e instanceof IllegalStateException && e.getMessage() != null && e.getMessage().contains("closed")) {
                        log.error("ERRO CRÍTICO: Contexto Spring fechado detectado. Parando processamento deste arquivo.");
                        break;
                    }
                    
                    if (linhasComErro % 100 == 0) {
                        log.warn("Total de {} erros até agora no arquivo {} (último erro: {})", 
                                linhasComErro, context.arquivoPath.getFileName(), e.getMessage());
                    }
                    
                    if (errosDetalhados.size() > 100) {
                        errosDetalhados.remove(0);
                    }
                }
            }

            if (!batch.isEmpty()) {
                try {
                    Map<String, T> existentes = batchLookup.apply(batch);
                    List<T> toSave = new ArrayList<>();
                    for (T e : batch) {
                        String key = keyExtractor.apply(e);
                        T existing = existentes.get(key);
                        if (existing != null) {
                            T updated = updater.apply(e, existing);
                            toSave.add(updated);
                        } else {
                            toSave.add(e);
                        }
                    }
                    existentes.clear();
                    saver.apply(toSave);
                    totalLinhas += batch.size();
                    batch.clear();
                    toSave.clear();
                } catch (IllegalStateException e) {
                    if (e.getMessage() != null && e.getMessage().contains("closed")) {
                        log.error("ERRO CRÍTICO ao salvar batch final no arquivo {}: Contexto Spring fechado.", 
                                context.arquivoPath.getFileName(), e);
                    } else {
                        log.error("Erro ao salvar batch final no arquivo {}: {}", 
                                context.arquivoPath.getFileName(), e.getMessage(), e);
                    }
                    linhasComErro += batch.size();
                } catch (ConfigurationPropertiesBindException e) {
                    log.error("ERRO CRÍTICO ao salvar batch final no arquivo {}: Spring tentando recriar bean quando contexto fechado.", 
                            context.arquivoPath.getFileName(), e);
                    linhasComErro += batch.size();
                } catch (Exception e) {
                    log.error("Erro ao salvar batch final no arquivo {}: {}", 
                            context.arquivoPath.getFileName(), e.getMessage(), e);
                    linhasComErro += batch.size();
                }
            }
        } catch (IOException e) {
            log.error("Erro ao ler arquivo {}: {}", context.arquivoPath, e.getMessage(), e);
            throw new InternalServerErrorException("Erro ao ler arquivo: " + context.arquivoPath, e);
        }

        long tempoDecorrido = System.currentTimeMillis() - inicioTempo;
        
        if (linhasComErro > 0) {
            log.warn("Arquivo {} concluído com {} erros: {} linhas processadas, {} erros, tempo: {}ms", 
                    context.arquivoPath.getFileName(), totalLinhas, linhasComErro, tempoDecorrido);
            
            int errosParaLogar = Math.min(10, errosDetalhados.size());
            for (int i = 0; i < errosParaLogar; i++) {
                log.warn("  Erro {}: {}", i + 1, errosDetalhados.get(i));
            }
            if (errosDetalhados.size() > 10) {
                log.warn("  ... e mais {} erros (total: {})", 
                        errosDetalhados.size() - 10, errosDetalhados.size());
            }
        } else {
            log.info("Arquivo {} concluído com sucesso: {} linhas processadas, tempo: {}ms", 
                    context.arquivoPath.getFileName(), totalLinhas, tempoDecorrido);
        }

        return totalLinhas;
    }

    private String[] parseHeaders(String linha) {
        if (linha == null || linha.isEmpty()) {
            throw new InvalidArgumentException("Linha de headers vazia");
        }
        return linha.split(";", -1);
    }

    private boolean validarCamposBasicos(Map<String, String> fields, int numeroLinha, List<String> errosDetalhados) {
        boolean temCampos = fields.values().stream().anyMatch(v -> v != null && !v.trim().isEmpty());
        
        if (!temCampos) {
            errosDetalhados.add(String.format("Linha %d: Linha vazia ou sem campos válidos", numeroLinha));
            return false;
        }
        
        return true;
    }

    private static class ImportContext {
        final Path arquivoPath;
        final String competencia;

        ImportContext(Path arquivoPath, String competencia) {
            this.arquivoPath = arquivoPath;
            this.competencia = competencia;
        }
    }

    public static class ImportResult {
        private final String competencia;
        private int totalLinhasProcessadas = 0;
        private int totalErros = 0;
        private final Map<String, Integer> linhasPorArquivo = new HashMap<>();
        private final List<String> erros = new ArrayList<>();

        public ImportResult(String competencia) {
            this.competencia = competencia;
        }

        public void addLinhasProcessadas(String arquivo, int linhas) {
            linhasPorArquivo.put(arquivo, linhas);
            totalLinhasProcessadas += linhas;
        }

        public void addErro(String erro) {
            erros.add(erro);
            totalErros++;
        }

        public String getCompetencia() { return competencia; }
        public int getTotalLinhasProcessadas() { return totalLinhasProcessadas; }
        public int getTotalErros() { return totalErros; }
        public Map<String, Integer> getLinhasPorArquivo() { return linhasPorArquivo; }
        public List<String> getErros() { return erros; }
    }
}
