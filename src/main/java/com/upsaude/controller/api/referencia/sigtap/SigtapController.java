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
        description = "API completa para consulta de dados do SIGTAP (Sistema de Gestão da Tabela Unificada de Procedimentos). " +
                "O SIGTAP é o sistema oficial do Ministério da Saúde que mantém o cadastro de procedimentos, medicamentos, " +
                "órteses, próteses e materiais especiais (OPME) do SUS. " +
                "\n\n" +
                "**Hierarquia SIGTAP:**\n" +
                "- **Grupo** (1º nível): Categorias principais (ex: 03=Procedimentos clínicos, 04=Procedimentos cirúrgicos, 06=Medicamentos)\n" +
                "- **Subgrupo** (2º nível): Subcategorias dentro de cada grupo\n" +
                "- **Forma de Organização** (3º nível): Tipos de organização do procedimento\n" +
                "- **Procedimento** (4º nível): Procedimentos específicos\n" +
                "\n\n" +
                "**Estrutura do Código Oficial:**\n" +
                "- Primeiros 2 dígitos: código do grupo\n" +
                "- Próximos 2 dígitos: código do subgrupo\n" +
                "- Próximos 2 dígitos: código da forma de organização\n" +
                "- Resto: código específico do procedimento\n" +
                "\n\n" +
                "**Exemplo:** Código `0401010015` = Grupo 04 (Cirúrgicos) + Subgrupo 01 + Forma Org 01 + Procedimento 0015"
)
@RequiredArgsConstructor
@Slf4j
public class SigtapController {

    private final SigtapConsultaService sigtapConsultaService;

    @GetMapping("/grupos")
    @Operation(
            summary = "Listar todos os grupos SIGTAP",
            description = "Retorna a lista completa de todos os grupos SIGTAP ordenados por código. " +
                    "Os grupos representam o primeiro nível da hierarquia SIGTAP e categorizam os procedimentos. " +
                    "\n\n" +
                    "**Grupos Principais:**\n" +
                    "- **01**: Ações de promoção e prevenção\n" +
                    "- **02**: Procedimentos diagnósticos\n" +
                    "- **03**: Procedimentos clínicos\n" +
                    "- **04**: Procedimentos cirúrgicos\n" +
                    "- **05**: Transplantes\n" +
                    "- **06**: Medicamentos\n" +
                    "- **07**: Órteses, próteses e materiais especiais (OPME)\n" +
                    "- **08**: Ações complementares\n" +
                    "- **09**: Ofertas de cuidados integrados"
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
            description = "Retorna uma lista paginada de procedimentos SIGTAP com filtros hierárquicos opcionais. " +
                    "Permite filtrar por grupo, grupo+subgrupo, ou grupo+subgrupo+forma de organização. " +
                    "Também suporta busca por código ou nome do procedimento e filtro por competência." +
                    "\n\n" +
                    "**Filtros Hierárquicos:**\n" +
                    "1. **Apenas `grupoCodigo`**: Retorna todos os procedimentos do grupo (ex: todos os procedimentos cirúrgicos)\n" +
                    "2. **`grupoCodigo` + `subgrupoCodigo`**: Retorna procedimentos do subgrupo específico (ex: pequenas cirurgias)\n" +
                    "3. **`grupoCodigo` + `subgrupoCodigo` + `formaOrganizacaoCodigo`**: Retorna procedimentos da forma de organização específica\n" +
                    "\n\n" +
                    "**Exemplos de Uso:**\n" +
                    "- Buscar todos os medicamentos: `?grupoCodigo=06`\n" +
                    "- Buscar medicamentos de dispensação excepcional: `?grupoCodigo=06&subgrupoCodigo=01`\n" +
                    "- Buscar procedimentos cirúrgicos específicos: `?grupoCodigo=04&subgrupoCodigo=01&formaOrganizacaoCodigo=01`\n" +
                    "- Buscar por nome: `?grupoCodigo=04&q=cirurgia`\n" +
                    "\n\n" +
                    "**Nota:** Os filtros hierárquicos são baseados nos primeiros dígitos do código oficial do procedimento."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de procedimentos retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = SigtapProcedimentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Page<SigtapProcedimentoResponse>> pesquisarProcedimentos(
            @Parameter(
                    description = "Termo de busca livre (código ou nome do procedimento). " +
                            "Busca parcial e case-insensitive. Exemplo: 'consulta' ou '0301010010'",
                    example = "consulta médica"
            )
            @RequestParam(name = "q", required = false) String q,
            @Parameter(
                    description = "Código do grupo para filtrar (2 dígitos). " +
                            "Exemplos: '03' (Procedimentos clínicos), '04' (Procedimentos cirúrgicos), '06' (Medicamentos). " +
                            "Pode ser usado sozinho ou combinado com subgrupoCodigo e formaOrganizacaoCodigo",
                    example = "04"
            )
            @RequestParam(name = "grupoCodigo", required = false) String grupoCodigo,
            @Parameter(
                    description = "Código do subgrupo para filtrar (2 dígitos). " +
                            "Deve ser usado junto com grupoCodigo. " +
                            "Exemplo: grupoCodigo='04' e subgrupoCodigo='01' retorna procedimentos que começam com '0401'",
                    example = "01"
            )
            @RequestParam(name = "subgrupoCodigo", required = false) String subgrupoCodigo,
            @Parameter(
                    description = "Código da forma de organização para filtrar (2 dígitos). " +
                            "Deve ser usado junto com grupoCodigo e subgrupoCodigo. " +
                            "Exemplo: grupoCodigo='04', subgrupoCodigo='01', formaOrganizacaoCodigo='01' " +
                            "retorna procedimentos que começam com '040101'",
                    example = "01"
            )
            @RequestParam(name = "formaOrganizacaoCodigo", required = false) String formaOrganizacaoCodigo,
            @Parameter(
                    description = "Competência no formato AAAAMM (Ano + Mês). " +
                            "Filtra procedimentos válidos na competência especificada. " +
                            "Exemplo: '202512' para dezembro de 2025",
                    example = "202512"
            )
            @RequestParam(name = "competencia", required = false) String competencia,
            @Parameter(
                    description = "Parâmetros de paginação. " +
                            "page: número da página (padrão: 0), " +
                            "size: tamanho da página (padrão: 20), " +
                            "sort: ordenação (ex: 'codigoOficial,asc' ou 'nome,desc')"
            )
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
            description = "Retorna um procedimento SIGTAP específico com todos os seus detalhes, incluindo informações adicionais " +
                    "armazenadas na tabela de detalhes. Se múltiplas competências existirem para o mesmo código, retorna a mais recente. " +
                    "\n\n" +
                    "**Campos Retornados:**\n" +
                    "- Informações básicas do procedimento (código, nome, valores)\n" +
                    "- Hierarquia completa (grupo, subgrupo, forma de organização)\n" +
                    "- Restrições (sexo permitido, idade mínima/máxima)\n" +
                    "- Detalhes adicionais (quando disponíveis)\n" +
                    "\n\n" +
                    "**Parâmetros:**\n" +
                    "- `codigo`: Código oficial do procedimento (ex: '0401010015')\n" +
                    "- `competencia` (opcional): Filtra por competência específica. Se não informado, retorna a competência mais recente."
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
            @Parameter(
                    description = "Código oficial do procedimento SIGTAP (formato: 10 dígitos). " +
                            "Exemplos: '0301010010' (Consulta médica), '0401010015' (Procedimento cirúrgico), '0601010101' (Medicamento)",
                    required = true,
                    example = "0401010015"
            )
            @PathVariable String codigo,
            @Parameter(
                    description = "Competência no formato AAAAMM (Ano + Mês). " +
                            "Filtra o procedimento válido na competência especificada. " +
                            "Se não informado, retorna a competência mais recente disponível. " +
                            "Exemplo: '202512' para dezembro de 2025",
                    example = "202512"
            )
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
            description = "Retorna uma lista paginada de serviços/exames SIGTAP. " +
                    "Permite buscar por código ou nome do serviço. " +
                    "Serviços representam tipos de atendimento (hospitalar, ambulatorial, etc.)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de serviços retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = SigtapServicoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Page<SigtapServicoResponse>> pesquisarServicos(
            @Parameter(
                    description = "Termo de busca livre (código ou nome do serviço). " +
                            "Busca parcial e case-insensitive. Exemplo: 'hospitalar' ou '01'",
                    example = "hospitalar"
            )
            @RequestParam(name = "q", required = false) String q,
            @Parameter(
                    description = "Parâmetros de paginação. " +
                            "page: número da página (padrão: 0), " +
                            "size: tamanho da página (padrão: 20), " +
                            "sort: ordenação (ex: 'codigoOficial,asc' ou 'nome,desc')"
            )
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
            description = "Retorna um serviço/exame SIGTAP específico pelo código oficial. " +
                    "Serviços representam tipos de atendimento como hospitalar, ambulatorial, etc. " +
                    "São usados para classificar onde o procedimento pode ser realizado."
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
            @Parameter(
                    description = "Código oficial do serviço SIGTAP (geralmente 1-2 dígitos). " +
                            "Exemplos: '01' (Serviço hospitalar), '02' (Serviço ambulatorial)",
                    required = true,
                    example = "01"
            )
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
            description = "Retorna uma lista paginada de RENASES (Rede Nacional de Atenção Especializada em Saúde) SIGTAP. " +
                    "RENASES identifica estabelecimentos de saúde especializados e suas habilitações. " +
                    "Permite buscar por código ou nome do RENASES."
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
            @Parameter(
                    description = "Termo de busca livre (código ou nome do RENASES). " +
                            "Busca parcial e case-insensitive. Exemplo: 'cardiologia' ou '01'",
                    example = "cardiologia"
            )
            @RequestParam(name = "q", required = false) String q,
            @Parameter(
                    description = "Parâmetros de paginação. " +
                            "page: número da página (padrão: 0), " +
                            "size: tamanho da página (padrão: 20), " +
                            "sort: ordenação (ex: 'codigoOficial,asc' ou 'nome,desc')"
            )
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
            description = "Retorna um RENASES SIGTAP específico pelo código oficial. " +
                    "RENASES identifica estabelecimentos de saúde especializados e suas habilitações para execução de procedimentos."
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
            @Parameter(
                    description = "Código oficial do RENASES. " +
                            "Exemplo: '01' para um estabelecimento específico",
                    required = true,
                    example = "01"
            )
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
            description = "Retorna uma lista paginada com comportamento dinâmico baseado nos parâmetros: " +
                    "- Se apenas `grupoCodigo` for informado: retorna subgrupos do grupo especificado. " +
                    "- Se `grupoCodigo` e `subgrupoCodigo` forem informados: retorna formas de organização do subgrupo especificado. " +
                    "Também suporta busca por código ou nome."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso (subgrupos ou formas de organização conforme parâmetros)",
                    content = @Content(schema = @Schema(oneOf = {SigtapSubgrupoResponse.class, SigtapFormaOrganizacaoResponse.class}))),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> pesquisarSubgrupos(
            @Parameter(
                    description = "Termo de busca livre (código ou nome). " +
                            "Busca parcial e case-insensitive. Exemplo: 'pequenas cirurgias'",
                    example = "pequenas cirurgias"
            )
            @RequestParam(name = "q", required = false) String q,
            @Parameter(
                    description = "Código do grupo para filtrar (2 dígitos). " +
                            "Quando informado sozinho, retorna subgrupos do grupo. " +
                            "Quando informado com subgrupoCodigo, retorna formas de organização. " +
                            "Exemplo: '04' retorna todos os subgrupos de Procedimentos cirúrgicos",
                    example = "04"
            )
            @RequestParam(name = "grupoCodigo", required = false) String grupoCodigo,
            @Parameter(
                    description = "Código do subgrupo para filtrar (2 dígitos). " +
                            "Quando informado junto com grupoCodigo, retorna formas de organização do subgrupo. " +
                            "Exemplo: grupoCodigo='04' e subgrupoCodigo='01' retorna formas de organização do subgrupo '01' do grupo '04'",
                    example = "01"
            )
            @RequestParam(name = "subgrupoCodigo", required = false) String subgrupoCodigo,
            @Parameter(
                    description = "Competência no formato AAAAMM (Ano + Mês). " +
                            "Filtra resultados válidos na competência especificada. " +
                            "Exemplo: '202512' para dezembro de 2025",
                    example = "202512"
            )
            @RequestParam(name = "competencia", required = false) String competencia,
            @Parameter(
                    description = "Parâmetros de paginação. " +
                            "page: número da página (padrão: 0), " +
                            "size: tamanho da página (padrão: 20), " +
                            "sort: ordenação (ex: 'codigoOficial,asc' ou 'nome,desc')"
            )
            Pageable pageable) {
        log.debug("REQUEST GET /v1/sigtap/subgrupos - q: {}, grupoCodigo: {}, subgrupoCodigo: {}, competencia: {}, pageable: {}", q, grupoCodigo, subgrupoCodigo, competencia, pageable);
        try {
            // Se tiver grupoCodigo e subgrupoCodigo, retornar formas de organização
            if (grupoCodigo != null && !grupoCodigo.isBlank() && 
                subgrupoCodigo != null && !subgrupoCodigo.isBlank()) {
                Page<SigtapFormaOrganizacaoResponse> response = sigtapConsultaService.pesquisarFormasOrganizacao(q, grupoCodigo, subgrupoCodigo, competencia, pageable);
                return ResponseEntity.ok(response);
            }
            
            // Caso contrário, retornar subgrupos
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
            description = "Retorna um subgrupo SIGTAP específico pelo código oficial. " +
                    "O subgrupo é o segundo nível da hierarquia SIGTAP e representa subcategorias dentro de um grupo. " +
                    "\n\n" +
                    "**Exemplo:** Para o grupo 04 (Procedimentos cirúrgicos), o subgrupo '01' representa " +
                    "'Pequenas cirurgias e cirurgias de pele, tecido subcutâneo e mucosa'."
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
            @Parameter(
                    description = "Código oficial do subgrupo (2 dígitos). " +
                            "Exemplo: '01' para o primeiro subgrupo de um grupo",
                    required = true,
                    example = "01"
            )
            @PathVariable String codigo,
            @Parameter(
                    description = "Código do grupo (2 dígitos, opcional). " +
                            "Quando informado, torna a busca mais precisa, pois o mesmo código de subgrupo pode existir em diferentes grupos. " +
                            "Exemplo: grupoCodigo='04' para buscar o subgrupo '01' do grupo de Procedimentos cirúrgicos",
                    example = "04"
            )
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
            description = "Retorna uma lista paginada de formas de organização SIGTAP com filtros opcionais. " +
                    "Permite filtrar por grupo, subgrupo ou ambos. " +
                    "A forma de organização é o terceiro nível da hierarquia SIGTAP (Grupo > Subgrupo > Forma de Organização)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de formas de organização retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = SigtapFormaOrganizacaoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Page<SigtapFormaOrganizacaoResponse>> pesquisarFormasOrganizacao(
            @Parameter(
                    description = "Termo de busca livre (código ou nome da forma de organização). " +
                            "Busca parcial e case-insensitive. Exemplo: 'pequenas cirurgias'",
                    example = "pequenas cirurgias"
            )
            @RequestParam(name = "q", required = false) String q,
            @Parameter(
                    description = "Código do grupo para filtrar (2 dígitos). " +
                            "Quando informado, retorna apenas formas de organização do grupo especificado. " +
                            "Pode ser usado sozinho ou combinado com subgrupoCodigo. " +
                            "Exemplo: '04' retorna todas as formas de organização de Procedimentos cirúrgicos",
                    example = "04"
            )
            @RequestParam(name = "grupoCodigo", required = false) String grupoCodigo,
            @Parameter(
                    description = "Código do subgrupo para filtrar (2 dígitos). " +
                            "Quando informado, retorna apenas formas de organização do subgrupo especificado. " +
                            "Pode ser usado sozinho ou combinado com grupoCodigo. " +
                            "Exemplo: '01' retorna todas as formas de organização do subgrupo '01'",
                    example = "01"
            )
            @RequestParam(name = "subgrupoCodigo", required = false) String subgrupoCodigo,
            @Parameter(
                    description = "Competência no formato AAAAMM (Ano + Mês). " +
                            "Filtra formas de organização válidas na competência especificada. " +
                            "Exemplo: '202512' para dezembro de 2025",
                    example = "202512"
            )
            @RequestParam(name = "competencia", required = false) String competencia,
            @Parameter(
                    description = "Parâmetros de paginação. " +
                            "page: número da página (padrão: 0), " +
                            "size: tamanho da página (padrão: 20), " +
                            "sort: ordenação (ex: 'codigoOficial,asc' ou 'nome,desc')"
            )
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
            description = "Retorna uma forma de organização SIGTAP específica pelo código oficial. " +
                    "A forma de organização é o terceiro nível da hierarquia SIGTAP e representa como o procedimento é organizado. " +
                    "\n\n" +
                    "**Exemplo:** Para o grupo 04, subgrupo 01, a forma de organização '01' pode representar " +
                    "'Pequenas cirurgias' enquanto '02' pode representar 'Cirurgias de pele, tecido subcutâneo e mucosa'."
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
            @Parameter(
                    description = "Código oficial da forma de organização (2 dígitos). " +
                            "Exemplo: '01' para a primeira forma de organização de um subgrupo",
                    required = true,
                    example = "01"
            )
            @PathVariable String codigo,
            @Parameter(
                    description = "Código do subgrupo (2 dígitos, opcional). " +
                            "Quando informado, torna a busca mais precisa, pois o mesmo código de forma de organização pode existir em diferentes subgrupos. " +
                            "Exemplo: subgrupoCodigo='01' para buscar a forma de organização '01' do subgrupo '01'",
                    example = "01"
            )
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
            description = "Retorna uma lista paginada de habilitações SIGTAP. " +
                    "Habilitações representam requisitos e condições necessárias para a execução de procedimentos. " +
                    "Permite buscar por código ou nome da habilitação e filtrar por competência."
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
            @Parameter(
                    description = "Termo de busca livre (código ou nome da habilitação). " +
                            "Busca parcial e case-insensitive. Exemplo: 'hospital' ou '01'",
                    example = "hospital"
            )
            @RequestParam(name = "q", required = false) String q,
            @Parameter(
                    description = "Competência no formato AAAAMM (Ano + Mês). " +
                            "Filtra habilitações válidas na competência especificada. " +
                            "Exemplo: '202512' para dezembro de 2025",
                    example = "202512"
            )
            @RequestParam(name = "competencia", required = false) String competencia,
            @Parameter(
                    description = "Parâmetros de paginação. " +
                            "page: número da página (padrão: 0), " +
                            "size: tamanho da página (padrão: 20), " +
                            "sort: ordenação (ex: 'codigoOficial,asc' ou 'nome,desc')"
            )
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
            description = "Retorna uma habilitação SIGTAP específica pelo código oficial. " +
                    "Se múltiplas competências existirem para o mesmo código, retorna a mais recente. " +
                    "Habilitações definem requisitos e condições para execução de procedimentos."
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
            @Parameter(
                    description = "Código oficial da habilitação. " +
                            "Exemplo: '01' para uma habilitação específica",
                    required = true,
                    example = "01"
            )
            @PathVariable String codigo,
            @Parameter(
                    description = "Competência no formato AAAAMM (Ano + Mês). " +
                            "Filtra a habilitação válida na competência especificada. " +
                            "Se não informado, retorna a competência mais recente. " +
                            "Exemplo: '202512' para dezembro de 2025",
                    example = "202512"
            )
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
            description = "Retorna uma lista paginada de TUSS (Terminologia Unificada da Saúde Suplementar) SIGTAP. " +
                    "TUSS é uma terminologia padronizada usada no sistema de saúde suplementar brasileiro. " +
                    "Permite buscar por código ou nome do TUSS."
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
            @Parameter(
                    description = "Termo de busca livre (código ou nome do TUSS). " +
                            "Busca parcial e case-insensitive. Exemplo: '10101010' ou 'consulta'",
                    example = "10101010"
            )
            @RequestParam(name = "q", required = false) String q,
            @Parameter(
                    description = "Parâmetros de paginação. " +
                            "page: número da página (padrão: 0), " +
                            "size: tamanho da página (padrão: 20), " +
                            "sort: ordenação (ex: 'codigoOficial,asc' ou 'nome,desc')"
            )
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
            description = "Retorna um TUSS SIGTAP específico pelo código oficial. " +
                    "TUSS (Terminologia Unificada da Saúde Suplementar) é uma terminologia padronizada " +
                    "usada no sistema de saúde suplementar brasileiro para padronizar procedimentos e serviços."
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
            @Parameter(
                    description = "Código oficial do TUSS. " +
                            "Exemplo: '10101010' para um código TUSS específico",
                    required = true,
                    example = "10101010"
            )
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
    @Operation(
            summary = "Pesquisar ocupações (CBO) SIGTAP",
            description = "Retorna uma lista paginada de ocupações SIGTAP baseadas na Classificação Brasileira de Ocupações (CBO). " +
                    "As ocupações representam profissões e especialidades relacionadas aos procedimentos. " +
                    "Permite buscar por código CBO ou nome da ocupação."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de ocupações retornada com sucesso.",
                    content = @Content(schema = @Schema(implementation = SigtapOcupacaoResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos."),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Token de autenticação inválido ou ausente."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    public ResponseEntity<Page<SigtapOcupacaoResponse>> pesquisarOcupacoes(
            @Parameter(
                    description = "Termo de busca livre (código CBO ou nome da ocupação). " +
                            "Busca parcial e case-insensitive. Exemplo: '225110' (código CBO) ou 'médico' (nome)",
                    example = "médico"
            )
            @RequestParam(name = "q", required = false) String q,
            @Parameter(
                    description = "Parâmetros de paginação. " +
                            "page: número da página (padrão: 0), " +
                            "size: tamanho da página (padrão: 20), " +
                            "sort: ordenação (ex: 'codigoOficial,asc' ou 'nome,desc')"
            )
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
    @Operation(
            summary = "Obter ocupação (CBO) por código",
            description = "Retorna uma ocupação SIGTAP específica pelo código CBO (Classificação Brasileira de Ocupações). " +
                    "As ocupações representam profissões e especialidades que podem executar procedimentos específicos."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ocupação encontrada com sucesso.",
                    content = @Content(schema = @Schema(implementation = SigtapOcupacaoResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ocupação não encontrada. O código CBO informado não existe no sistema."
            ),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Token de autenticação inválido ou ausente."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    public ResponseEntity<SigtapOcupacaoResponse> obterOcupacaoPorCodigo(
            @Parameter(
                    description = "Código CBO (Classificação Brasileira de Ocupações). " +
                            "Exemplo: '225110' para 'Médico clínico geral'",
                    required = true,
                    example = "225110"
            )
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
    @Operation(
            summary = "Pesquisar modalidades SIGTAP",
            description = "Retorna uma lista paginada de modalidades SIGTAP. " +
                    "Modalidades representam formas de atendimento ou tipos de procedimentos. " +
                    "Permite buscar por código ou nome da modalidade e filtrar por competência."
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
            @Parameter(
                    description = "Termo de busca livre (código ou nome da modalidade). " +
                            "Busca parcial e case-insensitive. Exemplo: '01' ou 'ambulatorial'",
                    example = "ambulatorial"
            )
            @RequestParam(name = "q", required = false) String q,
            @Parameter(
                    description = "Competência no formato AAAAMM (Ano + Mês). " +
                            "Filtra modalidades válidas na competência especificada. " +
                            "Exemplo: '202512' para dezembro de 2025",
                    example = "202512"
            )
            @RequestParam(name = "competencia", required = false) String competencia,
            @Parameter(
                    description = "Parâmetros de paginação. " +
                            "page: número da página (padrão: 0), " +
                            "size: tamanho da página (padrão: 20), " +
                            "sort: ordenação (ex: 'codigoOficial,asc' ou 'nome,desc')"
            )
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
            description = "Retorna uma modalidade SIGTAP específica pelo código oficial. " +
                    "Se múltiplas competências existirem para o mesmo código, retorna a mais recente. " +
                    "Modalidades representam formas de atendimento ou tipos de procedimentos."
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
            @Parameter(
                    description = "Código oficial da modalidade. " +
                            "Exemplo: '01' para uma modalidade específica",
                    required = true,
                    example = "01"
            )
            @PathVariable String codigo,
            @Parameter(
                    description = "Competência no formato AAAAMM (Ano + Mês). " +
                            "Filtra a modalidade válida na competência especificada. " +
                            "Se não informado, retorna a competência mais recente. " +
                            "Exemplo: '202512' para dezembro de 2025",
                    example = "202512"
            )
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
            description = "Retorna uma lista paginada de compatibilidades entre procedimentos SIGTAP. " +
                    "Compatibilidades definem quais procedimentos podem ser realizados juntos ou são incompatíveis. " +
                    "Permite filtrar por procedimento principal e competência." +
                    "\n\n" +
                    "**Tipos de Compatibilidade:**\n" +
                    "- **PERMITIDA**: Procedimentos que podem ser realizados juntos\n" +
                    "- **INCOMPATÍVEL**: Procedimentos que não podem ser realizados juntos\n" +
                    "- **EXCLUSIVA**: Procedimentos que são mutuamente exclusivos"
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
            @Parameter(
                    description = "Código do procedimento principal para filtrar. " +
                            "Quando informado, retorna apenas compatibilidades relacionadas a este procedimento. " +
                            "Exemplo: '0301010010' para ver compatibilidades da consulta médica",
                    example = "0301010010"
            )
            @RequestParam(name = "codigoProcedimentoPrincipal", required = false) String codigoProcedimentoPrincipal,
            @Parameter(
                    description = "Competência no formato AAAAMM (Ano + Mês). " +
                            "Filtra compatibilidades válidas na competência especificada. " +
                            "Exemplo: '202512' para dezembro de 2025",
                    example = "202512"
            )
            @RequestParam(name = "competencia", required = false) String competencia,
            @Parameter(
                    description = "Parâmetros de paginação. " +
                            "page: número da página (padrão: 0), " +
                            "size: tamanho da página (padrão: 20), " +
                            "sort: ordenação (ex: 'codigoOficial,asc' ou 'nome,desc')"
            )
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
