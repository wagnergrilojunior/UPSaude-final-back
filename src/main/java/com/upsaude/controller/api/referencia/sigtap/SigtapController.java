package com.upsaude.controller.api.referencia.sigtap;

import com.upsaude.api.response.referencia.sigtap.*;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.referencia.sigtap.SigtapConsultaService;
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
@Tag(
        name = "SIGTAP Consulta",
        description = "API para consulta de dados do SIGTAP (Sistema de Gestão da Tabela Unificada de Procedimentos do SUS)."
)
@RequiredArgsConstructor
@Slf4j
public class SigtapController {

    private final SigtapConsultaService sigtapConsultaService;

    @GetMapping("/grupos")
    @Operation(
            summary = "Listar todos os grupos SIGTAP",
            description = "Retorna a lista completa de todos os grupos SIGTAP ordenados por código."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de grupos retornada com sucesso. Retorna todos os grupos ativos ordenados por código oficial.",
                    content = @Content(schema = @Schema(implementation = SigtapGrupoResponse.class))
            ),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Token de autenticação inválido ou ausente."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
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
    @Operation(
            summary = "Pesquisar procedimentos SIGTAP",
            description = "Retorna uma lista paginada de procedimentos SIGTAP. Permite filtrar por grupo, subgrupo, forma de organização, código, nome e competência."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de procedimentos retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = SigtapProcedimentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Page<SigtapProcedimentoResponse>> pesquisarProcedimentos(
            @Parameter(description = "Termo de busca (código ou nome)", example = "consulta médica")
            @RequestParam(name = "q", required = false) String q,
            @Parameter(description = "Código do grupo (2 dígitos)", example = "04")
            @RequestParam(name = "grupoCodigo", required = false) String grupoCodigo,
            @Parameter(description = "Código do subgrupo (2 dígitos)", example = "01")
            @RequestParam(name = "subgrupoCodigo", required = false) String subgrupoCodigo,
            @Parameter(description = "Código da forma de organização (2 dígitos)", example = "01")
            @RequestParam(name = "formaOrganizacaoCodigo", required = false) String formaOrganizacaoCodigo,
            @Parameter(description = "Competência no formato AAAAMM", example = "202512")
            @RequestParam(name = "competencia", required = false) String competencia,
            @Parameter(description = "Parâmetros de paginação")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/sigtap/procedimentos - q: {}, grupoCodigo: {}, subgrupoCodigo: {}, formaOrganizacaoCodigo: {}, competencia: {}, pageable: {}", 
                q, grupoCodigo, subgrupoCodigo, formaOrganizacaoCodigo, competencia, pageable);
        try {
            Page<SigtapProcedimentoResponse> response = sigtapConsultaService.pesquisarProcedimentos(
                    q, grupoCodigo, subgrupoCodigo, formaOrganizacaoCodigo, competencia, pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao pesquisar procedimentos SIGTAP - q: {}, grupoCodigo: {}, subgrupoCodigo: {}, formaOrganizacaoCodigo: {}, competencia: {}", 
                    q, grupoCodigo, subgrupoCodigo, formaOrganizacaoCodigo, competencia, ex);
            throw ex;
        }
    }

    @GetMapping("/procedimentos/{codigo}")
    @Operation(
            summary = "Obter procedimento detalhado por código",
            description = "Retorna um procedimento SIGTAP específico com todos os seus detalhes. Se múltiplas competências existirem, retorna a mais recente."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Procedimento encontrado com sucesso. Retorna o procedimento e seus detalhes completos.",
                    content = @Content(schema = @Schema(implementation = SigtapProcedimentoDetalhadoResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Procedimento não encontrado. O código informado não existe ou não está ativo na competência especificada."
            ),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Token de autenticação inválido ou ausente."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    public ResponseEntity<SigtapProcedimentoDetalhadoResponse> obterProcedimentoDetalhado(
            @Parameter(description = "Código oficial do procedimento (10 dígitos)", required = true, example = "0401010015")
            @PathVariable String codigo,
            @Parameter(description = "Competência no formato AAAAMM", example = "202512")
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
    @Operation(
            summary = "Pesquisar serviços/exames SIGTAP",
            description = "Retorna uma lista paginada de serviços/exames SIGTAP."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de serviços retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = SigtapServicoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Page<SigtapServicoResponse>> pesquisarServicos(
            @Parameter(description = "Termo de busca (código ou nome)", example = "hospitalar")
            @RequestParam(name = "q", required = false) String q,
            @Parameter(description = "Parâmetros de paginação")
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
    @Operation(
            summary = "Obter serviço por código",
            description = "Retorna um serviço/exame SIGTAP específico pelo código oficial."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Serviço encontrado com sucesso.",
                    content = @Content(schema = @Schema(implementation = SigtapServicoResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Serviço não encontrado. O código informado não existe no sistema."
            ),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Token de autenticação inválido ou ausente."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    public ResponseEntity<SigtapServicoResponse> obterServicoPorCodigo(
            @Parameter(description = "Código oficial do serviço", required = true, example = "01")
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
    @Operation(
            summary = "Pesquisar RENASES SIGTAP",
            description = "Retorna uma lista paginada de RENASES (Rede Nacional de Atenção Especializada em Saúde) SIGTAP."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de RENASES retornada com sucesso.",
                    content = @Content(schema = @Schema(implementation = SigtapRenasesResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos."),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Token de autenticação inválido ou ausente."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    public ResponseEntity<Page<SigtapRenasesResponse>> pesquisarRenases(
            @Parameter(description = "Termo de busca (código ou nome)", example = "cardiologia")
            @RequestParam(name = "q", required = false) String q,
            @Parameter(description = "Parâmetros de paginação")
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
    @Operation(
            summary = "Obter RENASES por código",
            description = "Retorna um RENASES SIGTAP específico pelo código oficial."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "RENASES encontrado com sucesso.",
                    content = @Content(schema = @Schema(implementation = SigtapRenasesResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "RENASES não encontrado. O código informado não existe no sistema."
            ),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Token de autenticação inválido ou ausente."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    public ResponseEntity<SigtapRenasesResponse> obterRenasesPorCodigo(
            @Parameter(description = "Código oficial do RENASES", required = true, example = "01")
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
    @Operation(
            summary = "Pesquisar subgrupos ou formas de organização SIGTAP",
            description = "Retorna subgrupos se apenas grupoCodigo for informado, ou formas de organização se grupoCodigo e subgrupoCodigo forem informados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso (subgrupos ou formas de organização conforme parâmetros)",
                    content = @Content(schema = @Schema(oneOf = {SigtapSubgrupoResponse.class, SigtapFormaOrganizacaoResponse.class}))),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> pesquisarSubgrupos(
            @Parameter(description = "Termo de busca (código ou nome)", example = "pequenas cirurgias")
            @RequestParam(name = "q", required = false) String q,
            @Parameter(description = "Código do grupo (2 dígitos)", example = "04")
            @RequestParam(name = "grupoCodigo", required = false) String grupoCodigo,
            @Parameter(description = "Código do subgrupo (2 dígitos)", example = "01")
            @RequestParam(name = "subgrupoCodigo", required = false) String subgrupoCodigo,
            @Parameter(description = "Competência no formato AAAAMM", example = "202512")
            @RequestParam(name = "competencia", required = false) String competencia,
            @Parameter(description = "Parâmetros de paginação")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/sigtap/subgrupos - q: {}, grupoCodigo: {}, subgrupoCodigo: {}, competencia: {}, pageable: {}", q, grupoCodigo, subgrupoCodigo, competencia, pageable);
        try {
            if (grupoCodigo != null && !grupoCodigo.isBlank() && 
                subgrupoCodigo != null && !subgrupoCodigo.isBlank()) {
                Page<SigtapFormaOrganizacaoResponse> response = sigtapConsultaService.pesquisarFormasOrganizacao(q, grupoCodigo, subgrupoCodigo, competencia, pageable);
                return ResponseEntity.ok(response);
            }
            
            Page<SigtapSubgrupoResponse> response = sigtapConsultaService.pesquisarSubgrupos(q, grupoCodigo, subgrupoCodigo, competencia, pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao pesquisar subgrupos/formas de organização SIGTAP - q: {}, grupoCodigo: {}, subgrupoCodigo: {}", q, grupoCodigo, subgrupoCodigo, ex);
            throw ex;
        }
    }

    @GetMapping("/subgrupos/{codigo}")
    @Operation(
            summary = "Obter subgrupo por código",
            description = "Retorna um subgrupo SIGTAP específico pelo código oficial."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Subgrupo encontrado com sucesso. Retorna informações do subgrupo incluindo dados do grupo pai.",
                    content = @Content(schema = @Schema(implementation = SigtapSubgrupoResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Subgrupo não encontrado. O código informado não existe ou não está ativo."
            ),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Token de autenticação inválido ou ausente."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    public ResponseEntity<SigtapSubgrupoResponse> obterSubgrupoPorCodigo(
            @Parameter(description = "Código oficial do subgrupo (2 dígitos)", required = true, example = "01")
            @PathVariable String codigo,
            @Parameter(description = "Código do grupo (2 dígitos, opcional)", example = "04")
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
    @Operation(
            summary = "Pesquisar formas de organização SIGTAP",
            description = "Retorna uma lista paginada de formas de organização SIGTAP com filtros opcionais."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de formas de organização retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = SigtapFormaOrganizacaoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Page<SigtapFormaOrganizacaoResponse>> pesquisarFormasOrganizacao(
            @Parameter(description = "Termo de busca (código ou nome)", example = "pequenas cirurgias")
            @RequestParam(name = "q", required = false) String q,
            @Parameter(description = "Código do grupo (2 dígitos)", example = "04")
            @RequestParam(name = "grupoCodigo", required = false) String grupoCodigo,
            @Parameter(description = "Código do subgrupo (2 dígitos)", example = "01")
            @RequestParam(name = "subgrupoCodigo", required = false) String subgrupoCodigo,
            @Parameter(description = "Competência no formato AAAAMM", example = "202512")
            @RequestParam(name = "competencia", required = false) String competencia,
            @Parameter(description = "Parâmetros de paginação")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/sigtap/formas-organizacao - q: {}, grupoCodigo: {}, subgrupoCodigo: {}, competencia: {}, pageable: {}", q, grupoCodigo, subgrupoCodigo, competencia, pageable);
        try {
            Page<SigtapFormaOrganizacaoResponse> response = sigtapConsultaService.pesquisarFormasOrganizacao(q, grupoCodigo, subgrupoCodigo, competencia, pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao pesquisar formas de organização SIGTAP - q: {}, grupoCodigo: {}, subgrupoCodigo: {}", q, grupoCodigo, subgrupoCodigo, ex);
            throw ex;
        }
    }

    @GetMapping("/formas-organizacao/{codigo}")
    @Operation(
            summary = "Obter forma de organização por código",
            description = "Retorna uma forma de organização SIGTAP específica pelo código oficial."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Forma de organização encontrada com sucesso. Retorna informações incluindo dados do subgrupo e grupo.",
                    content = @Content(schema = @Schema(implementation = SigtapFormaOrganizacaoResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Forma de organização não encontrada. O código informado não existe ou não está ativo."
            ),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Token de autenticação inválido ou ausente."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    public ResponseEntity<SigtapFormaOrganizacaoResponse> obterFormaOrganizacaoPorCodigo(
            @Parameter(description = "Código oficial da forma de organização (2 dígitos)", required = true, example = "01")
            @PathVariable String codigo,
            @Parameter(description = "Código do subgrupo (2 dígitos, opcional)", example = "01")
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
    @Operation(
            summary = "Pesquisar habilitações SIGTAP",
            description = "Retorna uma lista paginada de habilitações SIGTAP."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de habilitações retornada com sucesso.",
                    content = @Content(schema = @Schema(implementation = SigtapHabilitacaoResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos."),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Token de autenticação inválido ou ausente."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    public ResponseEntity<Page<SigtapHabilitacaoResponse>> pesquisarHabilitacoes(
            @Parameter(description = "Termo de busca (código ou nome)", example = "hospital")
            @RequestParam(name = "q", required = false) String q,
            @Parameter(description = "Competência no formato AAAAMM", example = "202512")
            @RequestParam(name = "competencia", required = false) String competencia,
            @Parameter(description = "Parâmetros de paginação")
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
    @Operation(
            summary = "Obter habilitação por código",
            description = "Retorna uma habilitação SIGTAP específica pelo código oficial. Se múltiplas competências existirem, retorna a mais recente."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Habilitação encontrada com sucesso.",
                    content = @Content(schema = @Schema(implementation = SigtapHabilitacaoResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Habilitação não encontrada. O código informado não existe ou não está ativo na competência especificada."
            ),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Token de autenticação inválido ou ausente."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    public ResponseEntity<SigtapHabilitacaoResponse> obterHabilitacaoPorCodigo(
            @Parameter(description = "Código oficial da habilitação", required = true, example = "01")
            @PathVariable String codigo,
            @Parameter(description = "Competência no formato AAAAMM", example = "202512")
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
    @Operation(
            summary = "Pesquisar TUSS SIGTAP",
            description = "Retorna uma lista paginada de TUSS (Terminologia Unificada da Saúde Suplementar) SIGTAP."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de TUSS retornada com sucesso.",
                    content = @Content(schema = @Schema(implementation = SigtapTussResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos."),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Token de autenticação inválido ou ausente."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    public ResponseEntity<Page<SigtapTussResponse>> pesquisarTuss(
            @Parameter(description = "Termo de busca (código ou nome)", example = "10101010")
            @RequestParam(name = "q", required = false) String q,
            @Parameter(description = "Parâmetros de paginação")
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
    @Operation(
            summary = "Obter TUSS por código",
            description = "Retorna um TUSS SIGTAP específico pelo código oficial."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "TUSS encontrado com sucesso.",
                    content = @Content(schema = @Schema(implementation = SigtapTussResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "TUSS não encontrado. O código informado não existe no sistema."
            ),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Token de autenticação inválido ou ausente."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    public ResponseEntity<SigtapTussResponse> obterTussPorCodigo(
            @Parameter(description = "Código oficial do TUSS", required = true, example = "10101010")
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

    @GetMapping("/cbo")
    @Operation(
            summary = "Pesquisar CBO (Classificação Brasileira de Ocupações) SIGTAP",
            description = "Retorna uma lista paginada de CBOs SIGTAP."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de CBOs retornada com sucesso.",
                    content = @Content(schema = @Schema(implementation = SigtapCboResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos."),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Token de autenticação inválido ou ausente."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    public ResponseEntity<Page<SigtapCboResponse>> pesquisarCbo(
            @Parameter(description = "Termo de busca (código CBO ou nome)", example = "médico")
            @RequestParam(name = "q", required = false) String q,
            @Parameter(description = "Código do grupo CBO", example = "MEDICOS")
            @RequestParam(name = "grupo", required = false) String grupo,
            @Parameter(description = "Parâmetros de paginação")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/sigtap/cbo - q: {}, grupo: {}, pageable: {}", q, grupo, pageable);
        try {
            Page<SigtapCboResponse> response = sigtapConsultaService.pesquisarCbo(q, grupo, pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao pesquisar CBO SIGTAP - q: {}, grupo: {}", q, grupo, ex);
            throw ex;
        }
    }

    @GetMapping("/cbo/todos")
    @Operation(
            summary = "Listar todos os CBOs sem paginação",
            description = "Retorna uma lista completa de todos os CBOs SIGTAP sem paginação. Use com cuidado para grupos grandes."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de CBOs retornada com sucesso.",
                    content = @Content(schema = @Schema(implementation = SigtapCboResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos."),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Token de autenticação inválido ou ausente."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    public ResponseEntity<List<SigtapCboResponse>> listarCbo(
            @Parameter(description = "Termo de busca (código CBO ou nome)", example = "médico")
            @RequestParam(name = "q", required = false) String q,
            @Parameter(description = "Código do grupo CBO", example = "MEDICOS")
            @RequestParam(name = "grupo", required = false) String grupo) {
        log.debug("REQUEST GET /v1/sigtap/cbo/todos - q: {}, grupo: {}", q, grupo);
        try {
            List<SigtapCboResponse> response = sigtapConsultaService.listarCbo(q, grupo);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar todos os CBOs SIGTAP - q: {}, grupo: {}", q, grupo, ex);
            throw ex;
        }
    }

    @GetMapping("/cbo/{codigo}")
    @Operation(
            summary = "Obter CBO por código",
            description = "Retorna um CBO SIGTAP específico pelo código (Classificação Brasileira de Ocupações)."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "CBO encontrado com sucesso.",
                    content = @Content(schema = @Schema(implementation = SigtapCboResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "CBO não encontrado. O código informado não existe no sistema."
            ),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Token de autenticação inválido ou ausente."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    public ResponseEntity<SigtapCboResponse> obterCboPorCodigo(
            @Parameter(description = "Código CBO", required = true, example = "225110")
            @PathVariable String codigo) {
        log.debug("REQUEST GET /v1/sigtap/cbo/{}", codigo);
        try {
            SigtapCboResponse response = sigtapConsultaService.obterCboPorCodigo(codigo);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("CBO SIGTAP não encontrado — código: {}, mensagem: {}", codigo, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter CBO SIGTAP — código: {}", codigo, ex);
            throw ex;
        }
    }

    @GetMapping("/cbo/grupos")
    @Operation(
            summary = "Listar grupos CBO disponíveis",
            description = "Retorna uma lista de todos os grupos CBO disponíveis no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de grupos retornada com sucesso.",
                    content = @Content(schema = @Schema(implementation = SigtapGrupoCboResponse.class))
            ),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Token de autenticação inválido ou ausente."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    public ResponseEntity<List<SigtapGrupoCboResponse>> listarGruposCbo() {
        log.debug("REQUEST GET /v1/sigtap/cbo/grupos");
        try {
            List<SigtapGrupoCboResponse> response = sigtapConsultaService.listarGruposCbo();
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar grupos CBO", ex);
            throw ex;
        }
    }

    @GetMapping("/cbo/grupos/{codigoGrupo}")
    @Operation(
            summary = "Obter detalhes de um grupo CBO",
            description = "Retorna informações detalhadas sobre um grupo CBO específico."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Grupo encontrado com sucesso.",
                    content = @Content(schema = @Schema(implementation = SigtapGrupoCboResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Grupo não encontrado. O código do grupo informado não existe."
            ),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Token de autenticação inválido ou ausente."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    public ResponseEntity<SigtapGrupoCboResponse> obterGrupoCboPorCodigo(
            @Parameter(description = "Código do grupo CBO", required = true, example = "MEDICOS")
            @PathVariable String codigoGrupo) {
        log.debug("REQUEST GET /v1/sigtap/cbo/grupos/{}", codigoGrupo);
        try {
            SigtapGrupoCboResponse response = sigtapConsultaService.obterGrupoCboPorCodigo(codigoGrupo);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Grupo CBO não encontrado — código: {}, mensagem: {}", codigoGrupo, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter grupo CBO — código: {}", codigoGrupo, ex);
            throw ex;
        }
    }

    @GetMapping("/cbo/grupos/{codigoGrupo}/cbo")
    @Operation(
            summary = "Pesquisar CBOs de um grupo específico",
            description = "Retorna uma lista paginada de CBOs pertencentes a um grupo específico."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de CBOs do grupo retornada com sucesso.",
                    content = @Content(schema = @Schema(implementation = SigtapCboResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Grupo não encontrado. O código do grupo informado não existe."
            ),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos."),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Token de autenticação inválido ou ausente."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    public ResponseEntity<Page<SigtapCboResponse>> pesquisarCboPorGrupo(
            @Parameter(description = "Código do grupo CBO", required = true, example = "MEDICOS")
            @PathVariable String codigoGrupo,
            @Parameter(description = "Termo de busca (código CBO ou nome)", example = "cardiologista")
            @RequestParam(name = "q", required = false) String q,
            @Parameter(description = "Parâmetros de paginação")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/sigtap/cbo/grupos/{}/cbo - q: {}, pageable: {}", codigoGrupo, q, pageable);
        try {
            Page<SigtapCboResponse> response = sigtapConsultaService.pesquisarCboPorGrupo(codigoGrupo, q, pageable);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Grupo CBO não encontrado — código: {}, mensagem: {}", codigoGrupo, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao pesquisar CBOs do grupo — código: {}, q: {}", codigoGrupo, q, ex);
            throw ex;
        }
    }

    @GetMapping("/modalidades")
    @Operation(
            summary = "Pesquisar modalidades SIGTAP",
            description = "Retorna uma lista paginada de modalidades SIGTAP."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de modalidades retornada com sucesso.",
                    content = @Content(schema = @Schema(implementation = SigtapModalidadeResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos."),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Token de autenticação inválido ou ausente."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    public ResponseEntity<Page<SigtapModalidadeResponse>> pesquisarModalidades(
            @Parameter(description = "Termo de busca (código ou nome)", example = "ambulatorial")
            @RequestParam(name = "q", required = false) String q,
            @Parameter(description = "Competência no formato AAAAMM", example = "202512")
            @RequestParam(name = "competencia", required = false) String competencia,
            @Parameter(description = "Parâmetros de paginação")
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
    @Operation(
            summary = "Obter modalidade por código",
            description = "Retorna uma modalidade SIGTAP específica pelo código oficial. Se múltiplas competências existirem, retorna a mais recente."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Modalidade encontrada com sucesso.",
                    content = @Content(schema = @Schema(implementation = SigtapModalidadeResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Modalidade não encontrada. O código informado não existe ou não está ativo na competência especificada."
            ),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Token de autenticação inválido ou ausente."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    public ResponseEntity<SigtapModalidadeResponse> obterModalidadePorCodigo(
            @Parameter(description = "Código oficial da modalidade", required = true, example = "01")
            @PathVariable String codigo,
            @Parameter(description = "Competência no formato AAAAMM", example = "202512")
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
    @Operation(
            summary = "Pesquisar compatibilidades entre procedimentos SIGTAP",
            description = "Retorna uma lista paginada de compatibilidades entre procedimentos SIGTAP."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de compatibilidades retornada com sucesso.",
                    content = @Content(schema = @Schema(implementation = SigtapCompatibilidadeResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos."),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Token de autenticação inválido ou ausente."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    public ResponseEntity<Page<SigtapCompatibilidadeResponse>> pesquisarCompatibilidades(
            @Parameter(description = "Código do procedimento principal", example = "0301010010")
            @RequestParam(name = "codigoProcedimentoPrincipal", required = false) String codigoProcedimentoPrincipal,
            @Parameter(description = "Competência no formato AAAAMM", example = "202512")
            @RequestParam(name = "competencia", required = false) String competencia,
            @Parameter(description = "Parâmetros de paginação")
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
