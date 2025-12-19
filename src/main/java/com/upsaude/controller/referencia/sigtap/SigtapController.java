package com.upsaude.controller.referencia.sigtap;

import com.upsaude.dto.referencia.sigtap.*;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.referencia.sigtap.SigtapConsultaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/sigtap")
@Tag(name = "SIGTAP Consulta", description = "API para consulta de dados do SIGTAP (procedimentos, serviços, RENASES, grupos, etc.)")
@RequiredArgsConstructor
@Slf4j
public class SigtapController {

    private final SigtapConsultaService sigtapConsultaService;

    @GetMapping("/grupos")
    @Operation(summary = "Listar grupos", description = "Retorna lista de todos os grupos SIGTAP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de grupos retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<List<SigtapGrupoResponse>> listarGrupos() {
        log.debug("REQUEST GET /v1/sigtap/grupos");
        try {
            List<SigtapGrupoResponse> response = sigtapConsultaService.listarGrupos();
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar grupos SIGTAP", ex);
            throw ex;
        }
    }

    @GetMapping("/procedimentos")
    @Operation(summary = "Pesquisar procedimentos", description = "Retorna uma lista paginada de procedimentos SIGTAP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de procedimentos retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<SigtapProcedimentoResponse>> pesquisarProcedimentos(
            @Parameter(description = "Termo de busca (código ou nome)", example = "0301010010")
            @RequestParam(name = "q", required = false) String q,
            @Parameter(description = "Código do grupo para filtrar", example = "06")
            @RequestParam(name = "grupoCodigo", required = false) String grupoCodigo,
            @Parameter(description = "Código do subgrupo para filtrar (deve ser usado junto com grupoCodigo)", example = "01")
            @RequestParam(name = "subgrupoCodigo", required = false) String subgrupoCodigo,
            @Parameter(description = "Competência no formato AAAAMM (ex: 202512)", example = "202512")
            @RequestParam(name = "competencia", required = false) String competencia,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/sigtap/procedimentos - q: {}, grupoCodigo: {}, subgrupoCodigo: {}, competencia: {}, pageable: {}", q, grupoCodigo, subgrupoCodigo, competencia, pageable);
        try {
            Page<SigtapProcedimentoResponse> response = sigtapConsultaService.pesquisarProcedimentos(q, grupoCodigo, subgrupoCodigo, competencia, pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao pesquisar procedimentos SIGTAP - q: {}, grupoCodigo: {}, subgrupoCodigo: {}, competencia: {}", q, grupoCodigo, subgrupoCodigo, competencia, ex);
            throw ex;
        }
    }

    @GetMapping("/procedimentos/{codigo}")
    @Operation(summary = "Obter procedimento detalhado", description = "Retorna um procedimento SIGTAP com seus detalhes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Procedimento encontrado",
                    content = @Content(schema = @Schema(implementation = SigtapProcedimentoDetalhadoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Procedimento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<SigtapProcedimentoDetalhadoResponse> obterProcedimentoDetalhado(
            @Parameter(description = "Código do procedimento", required = true, example = "0301010010")
            @PathVariable String codigo,
            @Parameter(description = "Competência no formato AAAAMM (ex: 202512)", example = "202512")
            @RequestParam(name = "competencia", required = false) String competencia) {
        log.debug("REQUEST GET /v1/sigtap/procedimentos/{} - competencia: {}", codigo, competencia);
        try {
            SigtapProcedimentoDetalhadoResponse response = sigtapConsultaService.obterProcedimentoDetalhado(codigo, competencia);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Procedimento SIGTAP não encontrado — código: {}, mensagem: {}", codigo, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter procedimento SIGTAP — código: {}", codigo, ex);
            throw ex;
        }
    }

    @GetMapping("/servicos")
    @Operation(summary = "Pesquisar serviços/exames", description = "Retorna uma lista paginada de serviços/exames SIGTAP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de serviços retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<SigtapServicoResponse>> pesquisarServicos(
            @Parameter(description = "Termo de busca (código ou nome)", example = "01")
            @RequestParam(name = "q", required = false) String q,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/sigtap/servicos - q: {}, pageable: {}", q, pageable);
        try {
            Page<SigtapServicoResponse> response = sigtapConsultaService.pesquisarServicos(q, pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao pesquisar serviços SIGTAP - q: {}", q, ex);
            throw ex;
        }
    }

    @GetMapping("/servicos/{codigo}")
    @Operation(summary = "Obter serviço por código", description = "Retorna um serviço SIGTAP específico pelo código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviço encontrado",
                    content = @Content(schema = @Schema(implementation = SigtapServicoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Serviço não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<SigtapServicoResponse> obterServicoPorCodigo(
            @Parameter(description = "Código do serviço", required = true, example = "01")
            @PathVariable String codigo) {
        log.debug("REQUEST GET /v1/sigtap/servicos/{}", codigo);
        try {
            SigtapServicoResponse response = sigtapConsultaService.obterServicoPorCodigo(codigo);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Serviço SIGTAP não encontrado — código: {}, mensagem: {}", codigo, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter serviço SIGTAP — código: {}", codigo, ex);
            throw ex;
        }
    }

    @GetMapping("/renases")
    @Operation(summary = "Pesquisar RENASES", description = "Retorna uma lista paginada de RENASES SIGTAP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de RENASES retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<SigtapRenasesResponse>> pesquisarRenases(
            @Parameter(description = "Termo de busca (código ou nome)", example = "01")
            @RequestParam(name = "q", required = false) String q,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/sigtap/renases - q: {}, pageable: {}", q, pageable);
        try {
            Page<SigtapRenasesResponse> response = sigtapConsultaService.pesquisarRenases(q, pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao pesquisar RENASES SIGTAP - q: {}", q, ex);
            throw ex;
        }
    }

    @GetMapping("/renases/{codigo}")
    @Operation(summary = "Obter RENASES por código", description = "Retorna um RENASES SIGTAP específico pelo código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "RENASES encontrado",
                    content = @Content(schema = @Schema(implementation = SigtapRenasesResponse.class))),
            @ApiResponse(responseCode = "404", description = "RENASES não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<SigtapRenasesResponse> obterRenasesPorCodigo(
            @Parameter(description = "Código do RENASES", required = true, example = "01")
            @PathVariable String codigo) {
        log.debug("REQUEST GET /v1/sigtap/renases/{}", codigo);
        try {
            SigtapRenasesResponse response = sigtapConsultaService.obterRenasesPorCodigo(codigo);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("RENASES SIGTAP não encontrado — código: {}, mensagem: {}", codigo, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter RENASES SIGTAP — código: {}", codigo, ex);
            throw ex;
        }
    }

    @GetMapping("/subgrupos")
    @Operation(summary = "Pesquisar subgrupos", description = "Retorna uma lista paginada de subgrupos SIGTAP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de subgrupos retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<SigtapSubgrupoResponse>> pesquisarSubgrupos(
            @Parameter(description = "Termo de busca (código ou nome)", example = "01")
            @RequestParam(name = "q", required = false) String q,
            @Parameter(description = "Código do grupo para filtrar", example = "06")
            @RequestParam(name = "grupoCodigo", required = false) String grupoCodigo,
            @Parameter(description = "Código do subgrupo para filtrar (deve ser usado junto com grupoCodigo)", example = "01")
            @RequestParam(name = "subgrupoCodigo", required = false) String subgrupoCodigo,
            @Parameter(description = "Competência no formato AAAAMM (ex: 202512)", example = "202512")
            @RequestParam(name = "competencia", required = false) String competencia,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/sigtap/subgrupos - q: {}, grupoCodigo: {}, subgrupoCodigo: {}, competencia: {}, pageable: {}", q, grupoCodigo, subgrupoCodigo, competencia, pageable);
        try {
            Page<SigtapSubgrupoResponse> response = sigtapConsultaService.pesquisarSubgrupos(q, grupoCodigo, subgrupoCodigo, competencia, pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao pesquisar subgrupos SIGTAP - q: {}, grupoCodigo: {}, subgrupoCodigo: {}", q, grupoCodigo, subgrupoCodigo, ex);
            throw ex;
        }
    }

    @GetMapping("/subgrupos/{codigo}")
    @Operation(summary = "Obter subgrupo por código", description = "Retorna um subgrupo SIGTAP específico pelo código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subgrupo encontrado",
                    content = @Content(schema = @Schema(implementation = SigtapSubgrupoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Subgrupo não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<SigtapSubgrupoResponse> obterSubgrupoPorCodigo(
            @Parameter(description = "Código do subgrupo", required = true, example = "01")
            @PathVariable String codigo,
            @Parameter(description = "Código do grupo (opcional, para busca mais precisa)", example = "01")
            @RequestParam(name = "grupoCodigo", required = false) String grupoCodigo) {
        log.debug("REQUEST GET /v1/sigtap/subgrupos/{} - grupoCodigo: {}", codigo, grupoCodigo);
        try {
            SigtapSubgrupoResponse response = sigtapConsultaService.obterSubgrupoPorCodigo(codigo, grupoCodigo);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Subgrupo SIGTAP não encontrado — código: {}, mensagem: {}", codigo, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter subgrupo SIGTAP — código: {}", codigo, ex);
            throw ex;
        }
    }

    @GetMapping("/formas-organizacao")
    @Operation(summary = "Pesquisar formas de organização", description = "Retorna uma lista paginada de formas de organização SIGTAP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de formas de organização retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<SigtapFormaOrganizacaoResponse>> pesquisarFormasOrganizacao(
            @Parameter(description = "Termo de busca (código ou nome)", example = "01")
            @RequestParam(name = "q", required = false) String q,
            @Parameter(description = "Código do subgrupo para filtrar", example = "01")
            @RequestParam(name = "subgrupoCodigo", required = false) String subgrupoCodigo,
            @Parameter(description = "Competência no formato AAAAMM (ex: 202512)", example = "202512")
            @RequestParam(name = "competencia", required = false) String competencia,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/sigtap/formas-organizacao - q: {}, subgrupoCodigo: {}, competencia: {}, pageable: {}", q, subgrupoCodigo, competencia, pageable);
        try {
            Page<SigtapFormaOrganizacaoResponse> response = sigtapConsultaService.pesquisarFormasOrganizacao(q, subgrupoCodigo, competencia, pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao pesquisar formas de organização SIGTAP - q: {}, subgrupoCodigo: {}", q, subgrupoCodigo, ex);
            throw ex;
        }
    }

    @GetMapping("/formas-organizacao/{codigo}")
    @Operation(summary = "Obter forma de organização por código", description = "Retorna uma forma de organização SIGTAP específica pelo código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Forma de organização encontrada",
                    content = @Content(schema = @Schema(implementation = SigtapFormaOrganizacaoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Forma de organização não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<SigtapFormaOrganizacaoResponse> obterFormaOrganizacaoPorCodigo(
            @Parameter(description = "Código da forma de organização", required = true, example = "01")
            @PathVariable String codigo,
            @Parameter(description = "Código do subgrupo (opcional, para busca mais precisa)", example = "01")
            @RequestParam(name = "subgrupoCodigo", required = false) String subgrupoCodigo) {
        log.debug("REQUEST GET /v1/sigtap/formas-organizacao/{} - subgrupoCodigo: {}", codigo, subgrupoCodigo);
        try {
            SigtapFormaOrganizacaoResponse response = sigtapConsultaService.obterFormaOrganizacaoPorCodigo(codigo, subgrupoCodigo);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Forma de organização SIGTAP não encontrada — código: {}, mensagem: {}", codigo, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter forma de organização SIGTAP — código: {}", codigo, ex);
            throw ex;
        }
    }

    @GetMapping("/habilitacoes")
    @Operation(summary = "Pesquisar habilitações", description = "Retorna uma lista paginada de habilitações SIGTAP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de habilitações retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<SigtapHabilitacaoResponse>> pesquisarHabilitacoes(
            @Parameter(description = "Termo de busca (código ou nome)", example = "01")
            @RequestParam(name = "q", required = false) String q,
            @Parameter(description = "Competência no formato AAAAMM (ex: 202512)", example = "202512")
            @RequestParam(name = "competencia", required = false) String competencia,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/sigtap/habilitacoes - q: {}, competencia: {}, pageable: {}", q, competencia, pageable);
        try {
            Page<SigtapHabilitacaoResponse> response = sigtapConsultaService.pesquisarHabilitacoes(q, competencia, pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao pesquisar habilitações SIGTAP - q: {}, competencia: {}", q, competencia, ex);
            throw ex;
        }
    }

    @GetMapping("/habilitacoes/{codigo}")
    @Operation(summary = "Obter habilitação por código", description = "Retorna uma habilitação SIGTAP específica pelo código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Habilitação encontrada",
                    content = @Content(schema = @Schema(implementation = SigtapHabilitacaoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Habilitação não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<SigtapHabilitacaoResponse> obterHabilitacaoPorCodigo(
            @Parameter(description = "Código da habilitação", required = true, example = "01")
            @PathVariable String codigo,
            @Parameter(description = "Competência no formato AAAAMM (ex: 202512)", example = "202512")
            @RequestParam(name = "competencia", required = false) String competencia) {
        log.debug("REQUEST GET /v1/sigtap/habilitacoes/{} - competencia: {}", codigo, competencia);
        try {
            SigtapHabilitacaoResponse response = sigtapConsultaService.obterHabilitacaoPorCodigo(codigo, competencia);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Habilitação SIGTAP não encontrada — código: {}, mensagem: {}", codigo, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter habilitação SIGTAP — código: {}", codigo, ex);
            throw ex;
        }
    }

    @GetMapping("/tuss")
    @Operation(summary = "Pesquisar TUSS", description = "Retorna uma lista paginada de TUSS SIGTAP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de TUSS retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<SigtapTussResponse>> pesquisarTuss(
            @Parameter(description = "Termo de busca (código ou nome)", example = "01")
            @RequestParam(name = "q", required = false) String q,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/sigtap/tuss - q: {}, pageable: {}", q, pageable);
        try {
            Page<SigtapTussResponse> response = sigtapConsultaService.pesquisarTuss(q, pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao pesquisar TUSS SIGTAP - q: {}", q, ex);
            throw ex;
        }
    }

    @GetMapping("/tuss/{codigo}")
    @Operation(summary = "Obter TUSS por código", description = "Retorna um TUSS SIGTAP específico pelo código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "TUSS encontrado",
                    content = @Content(schema = @Schema(implementation = SigtapTussResponse.class))),
            @ApiResponse(responseCode = "404", description = "TUSS não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<SigtapTussResponse> obterTussPorCodigo(
            @Parameter(description = "Código do TUSS", required = true, example = "01")
            @PathVariable String codigo) {
        log.debug("REQUEST GET /v1/sigtap/tuss/{}", codigo);
        try {
            SigtapTussResponse response = sigtapConsultaService.obterTussPorCodigo(codigo);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("TUSS SIGTAP não encontrado — código: {}, mensagem: {}", codigo, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter TUSS SIGTAP — código: {}", codigo, ex);
            throw ex;
        }
    }

    @GetMapping("/ocupacoes")
    @Operation(summary = "Pesquisar ocupações", description = "Retorna uma lista paginada de ocupações (CBO) SIGTAP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ocupações retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<SigtapOcupacaoResponse>> pesquisarOcupacoes(
            @Parameter(description = "Termo de busca (código ou nome)", example = "01")
            @RequestParam(name = "q", required = false) String q,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/sigtap/ocupacoes - q: {}, pageable: {}", q, pageable);
        try {
            Page<SigtapOcupacaoResponse> response = sigtapConsultaService.pesquisarOcupacoes(q, pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao pesquisar ocupações SIGTAP - q: {}", q, ex);
            throw ex;
        }
    }

    @GetMapping("/ocupacoes/{codigo}")
    @Operation(summary = "Obter ocupação por código", description = "Retorna uma ocupação (CBO) SIGTAP específica pelo código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ocupação encontrada",
                    content = @Content(schema = @Schema(implementation = SigtapOcupacaoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Ocupação não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<SigtapOcupacaoResponse> obterOcupacaoPorCodigo(
            @Parameter(description = "Código da ocupação", required = true, example = "01")
            @PathVariable String codigo) {
        log.debug("REQUEST GET /v1/sigtap/ocupacoes/{}", codigo);
        try {
            SigtapOcupacaoResponse response = sigtapConsultaService.obterOcupacaoPorCodigo(codigo);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Ocupação SIGTAP não encontrada — código: {}, mensagem: {}", codigo, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter ocupação SIGTAP — código: {}", codigo, ex);
            throw ex;
        }
    }

    @GetMapping("/modalidades")
    @Operation(summary = "Pesquisar modalidades", description = "Retorna uma lista paginada de modalidades SIGTAP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de modalidades retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<SigtapModalidadeResponse>> pesquisarModalidades(
            @Parameter(description = "Termo de busca (código ou nome)", example = "01")
            @RequestParam(name = "q", required = false) String q,
            @Parameter(description = "Competência no formato AAAAMM (ex: 202512)", example = "202512")
            @RequestParam(name = "competencia", required = false) String competencia,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/sigtap/modalidades - q: {}, competencia: {}, pageable: {}", q, competencia, pageable);
        try {
            Page<SigtapModalidadeResponse> response = sigtapConsultaService.pesquisarModalidades(q, competencia, pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao pesquisar modalidades SIGTAP - q: {}, competencia: {}", q, competencia, ex);
            throw ex;
        }
    }

    @GetMapping("/modalidades/{codigo}")
    @Operation(summary = "Obter modalidade por código", description = "Retorna uma modalidade SIGTAP específica pelo código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Modalidade encontrada",
                    content = @Content(schema = @Schema(implementation = SigtapModalidadeResponse.class))),
            @ApiResponse(responseCode = "404", description = "Modalidade não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<SigtapModalidadeResponse> obterModalidadePorCodigo(
            @Parameter(description = "Código da modalidade", required = true, example = "01")
            @PathVariable String codigo,
            @Parameter(description = "Competência no formato AAAAMM (ex: 202512)", example = "202512")
            @RequestParam(name = "competencia", required = false) String competencia) {
        log.debug("REQUEST GET /v1/sigtap/modalidades/{} - competencia: {}", codigo, competencia);
        try {
            SigtapModalidadeResponse response = sigtapConsultaService.obterModalidadePorCodigo(codigo, competencia);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Modalidade SIGTAP não encontrada — código: {}, mensagem: {}", codigo, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter modalidade SIGTAP — código: {}", codigo, ex);
            throw ex;
        }
    }

    @GetMapping("/compatibilidades")
    @Operation(summary = "Pesquisar compatibilidades", description = "Retorna uma lista paginada de compatibilidades entre procedimentos SIGTAP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de compatibilidades retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<SigtapCompatibilidadeResponse>> pesquisarCompatibilidades(
            @Parameter(description = "Código do procedimento principal para filtrar", example = "0301010010")
            @RequestParam(name = "codigoProcedimentoPrincipal", required = false) String codigoProcedimentoPrincipal,
            @Parameter(description = "Competência no formato AAAAMM (ex: 202512)", example = "202512")
            @RequestParam(name = "competencia", required = false) String competencia,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/sigtap/compatibilidades - codigoProcedimentoPrincipal: {}, competencia: {}, pageable: {}", codigoProcedimentoPrincipal, competencia, pageable);
        try {
            Page<SigtapCompatibilidadeResponse> response = sigtapConsultaService.pesquisarCompatibilidades(codigoProcedimentoPrincipal, competencia, pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao pesquisar compatibilidades SIGTAP - codigoProcedimentoPrincipal: {}, competencia: {}", codigoProcedimentoPrincipal, competencia, ex);
            throw ex;
        }
    }
}
