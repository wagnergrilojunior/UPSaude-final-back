package com.upsaude.controller.api.referencia.cid;

import com.upsaude.api.response.referencia.cid.*;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.referencia.cid.Cid10ConsultaService;
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
@RequestMapping("/v1/cid")
@Tag(name = "CID Consulta", description = "API para consulta de dados do CID-10 e CID-O (capítulos, categorias, subcategorias, grupos)")
@RequiredArgsConstructor
@Slf4j
public class CidController {

    private final Cid10ConsultaService cid10ConsultaService;

    @GetMapping("/cid10/capitulos")
    @Operation(summary = "Listar capítulos CID-10", description = "Retorna lista de todos os capítulos CID-10")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de capítulos retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<List<Cid10CapituloResponse>> listarCapitulos() {
        log.debug("REQUEST GET /v1/cid/cid10/capitulos");
        try {
            List<Cid10CapituloResponse> response = cid10ConsultaService.listarCapitulos();
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar capítulos CID-10", ex);
            throw ex;
        }
    }

    @GetMapping("/cid10/capitulos/{numcap}")
    @Operation(summary = "Obter capítulo CID-10 por número", description = "Retorna um capítulo CID-10 específico pelo número")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Capítulo encontrado",
                    content = @Content(schema = @Schema(implementation = Cid10CapituloResponse.class))),
            @ApiResponse(responseCode = "404", description = "Capítulo não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Cid10CapituloResponse> obterCapituloPorNumero(
            @Parameter(description = "Número do capítulo", required = true, example = "1")
            @PathVariable Integer numcap) {
        log.debug("REQUEST GET /v1/cid/cid10/capitulos/{}", numcap);
        try {
            Cid10CapituloResponse response = cid10ConsultaService.obterCapituloPorNumero(numcap);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Capítulo CID-10 não encontrado — número: {}, mensagem: {}", numcap, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter capítulo CID-10 — número: {}", numcap, ex);
            throw ex;
        }
    }

    @GetMapping("/cid10/categorias")
    @Operation(summary = "Pesquisar categorias CID-10", description = "Retorna uma lista paginada de categorias CID-10")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de categorias retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<Cid10CategoriaResponse>> pesquisarCategorias(
            @Parameter(description = "Termo de busca (código ou descrição)", example = "A00")
            @RequestParam(name = "q", required = false) String q,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/cid/cid10/categorias - q: {}, pageable: {}", q, pageable);
        try {
            Page<Cid10CategoriaResponse> response = cid10ConsultaService.pesquisarCategorias(q, pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao pesquisar categorias CID-10 - q: {}", q, ex);
            throw ex;
        }
    }

    @GetMapping("/cid10/categorias/{cat}")
    @Operation(summary = "Obter categoria CID-10 por código", description = "Retorna uma categoria CID-10 específica pelo código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada",
                    content = @Content(schema = @Schema(implementation = Cid10CategoriaResponse.class))),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Cid10CategoriaResponse> obterCategoriaPorCodigo(
            @Parameter(description = "Código da categoria", required = true, example = "A00")
            @PathVariable String cat) {
        log.debug("REQUEST GET /v1/cid/cid10/categorias/{}", cat);
        try {
            Cid10CategoriaResponse response = cid10ConsultaService.obterCategoriaPorCodigo(cat);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Categoria CID-10 não encontrada — código: {}, mensagem: {}", cat, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter categoria CID-10 — código: {}", cat, ex);
            throw ex;
        }
    }

    @GetMapping("/cid10/categorias/intervalo")
    @Operation(summary = "Listar categorias CID-10 por intervalo", description = "Retorna lista de categorias CID-10 em um intervalo de códigos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de categorias retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<List<Cid10CategoriaResponse>> listarCategoriasPorIntervalo(
            @Parameter(description = "Código inicial do intervalo", required = true, example = "A00")
            @RequestParam(name = "catinic") String catinic,
            @Parameter(description = "Código final do intervalo", required = true, example = "B99")
            @RequestParam(name = "catfim") String catfim) {
        log.debug("REQUEST GET /v1/cid/cid10/categorias/intervalo - catinic: {}, catfim: {}", catinic, catfim);
        try {
            List<Cid10CategoriaResponse> response = cid10ConsultaService.listarCategoriasPorIntervalo(catinic, catfim);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar categorias CID-10 por intervalo - catinic: {}, catfim: {}", catinic, catfim, ex);
            throw ex;
        }
    }

    @GetMapping("/cid10/subcategorias")
    @Operation(summary = "Pesquisar subcategorias CID-10", description = "Retorna uma lista paginada de subcategorias CID-10")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de subcategorias retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<Cid10SubcategoriaResponse>> pesquisarSubcategorias(
            @Parameter(description = "Termo de busca (código ou descrição)", example = "A000")
            @RequestParam(name = "q", required = false) String q,
            @Parameter(description = "Filtrar por código da categoria", example = "A00")
            @RequestParam(name = "categoriaCodigo", required = false) String categoriaCodigo,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/cid/cid10/subcategorias - q: {}, categoriaCodigo: {}, pageable: {}", q, categoriaCodigo, pageable);
        try {
            Page<Cid10SubcategoriaResponse> response = cid10ConsultaService.pesquisarSubcategorias(q, categoriaCodigo, pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao pesquisar subcategorias CID-10 - q: {}, categoriaCodigo: {}", q, categoriaCodigo, ex);
            throw ex;
        }
    }

    @GetMapping("/cid10/subcategorias/{subcat}")
    @Operation(summary = "Obter subcategoria CID-10 por código", description = "Retorna uma subcategoria CID-10 específica pelo código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subcategoria encontrada",
                    content = @Content(schema = @Schema(implementation = Cid10SubcategoriaResponse.class))),
            @ApiResponse(responseCode = "404", description = "Subcategoria não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Cid10SubcategoriaResponse> obterSubcategoriaPorCodigo(
            @Parameter(description = "Código da subcategoria", required = true, example = "A000")
            @PathVariable String subcat) {
        log.debug("REQUEST GET /v1/cid/cid10/subcategorias/{}", subcat);
        try {
            Cid10SubcategoriaResponse response = cid10ConsultaService.obterSubcategoriaPorCodigo(subcat);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Subcategoria CID-10 não encontrada — código: {}, mensagem: {}", subcat, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter subcategoria CID-10 — código: {}", subcat, ex);
            throw ex;
        }
    }

    @GetMapping("/cid10/subcategorias/categoria/{categoriaCodigo}")
    @Operation(summary = "Listar subcategorias por categoria", description = "Retorna lista de subcategorias CID-10 de uma categoria específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de subcategorias retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<List<Cid10SubcategoriaResponse>> listarSubcategoriasPorCategoria(
            @Parameter(description = "Código da categoria", required = true, example = "A00")
            @PathVariable String categoriaCodigo) {
        log.debug("REQUEST GET /v1/cid/cid10/subcategorias/categoria/{}", categoriaCodigo);
        try {
            List<Cid10SubcategoriaResponse> response = cid10ConsultaService.listarSubcategoriasPorCategoria(categoriaCodigo);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar subcategorias CID-10 por categoria - categoriaCodigo: {}", categoriaCodigo, ex);
            throw ex;
        }
    }

    @GetMapping("/cid10/grupos")
    @Operation(summary = "Listar grupos CID-10", description = "Retorna lista de todos os grupos CID-10")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de grupos retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<List<Cid10GrupoResponse>> listarGrupos(
            @Parameter(description = "Termo de busca opcional", example = "infecciosas")
            @RequestParam(name = "q", required = false) String q) {
        log.debug("REQUEST GET /v1/cid/cid10/grupos - q: {}", q);
        try {
            List<Cid10GrupoResponse> response = (q != null && !q.isBlank()) 
                    ? cid10ConsultaService.pesquisarGrupos(q) 
                    : cid10ConsultaService.listarGrupos();
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar grupos CID-10 - q: {}", q, ex);
            throw ex;
        }
    }

    @GetMapping("/cid-o/grupos")
    @Operation(summary = "Listar grupos CID-O", description = "Retorna lista de todos os grupos CID-O")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de grupos retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<List<CidOGrupoResponse>> listarGruposCidO(
            @Parameter(description = "Termo de busca opcional", example = "neoplasia")
            @RequestParam(name = "q", required = false) String q) {
        log.debug("REQUEST GET /v1/cid/cid-o/grupos - q: {}", q);
        try {
            List<CidOGrupoResponse> response = (q != null && !q.isBlank()) 
                    ? cid10ConsultaService.pesquisarGruposCidO(q) 
                    : cid10ConsultaService.listarGruposCidO();
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar grupos CID-O - q: {}", q, ex);
            throw ex;
        }
    }

    @GetMapping("/cid-o/categorias")
    @Operation(summary = "Pesquisar categorias CID-O", description = "Retorna uma lista paginada de categorias CID-O")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de categorias retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<CidOCategoriaResponse>> pesquisarCategoriasCidO(
            @Parameter(description = "Termo de busca (código ou descrição)", example = "C00")
            @RequestParam(name = "q", required = false) String q,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/cid/cid-o/categorias - q: {}, pageable: {}", q, pageable);
        try {
            Page<CidOCategoriaResponse> response = cid10ConsultaService.pesquisarCategoriasCidO(q, pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao pesquisar categorias CID-O - q: {}", q, ex);
            throw ex;
        }
    }

    @GetMapping("/cid-o/categorias/{cat}")
    @Operation(summary = "Obter categoria CID-O por código", description = "Retorna uma categoria CID-O específica pelo código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada",
                    content = @Content(schema = @Schema(implementation = CidOCategoriaResponse.class))),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CidOCategoriaResponse> obterCategoriaCidOPorCodigo(
            @Parameter(description = "Código da categoria", required = true, example = "C00")
            @PathVariable String cat) {
        log.debug("REQUEST GET /v1/cid/cid-o/categorias/{}", cat);
        try {
            CidOCategoriaResponse response = cid10ConsultaService.obterCategoriaCidOPorCodigo(cat);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Categoria CID-O não encontrada — código: {}, mensagem: {}", cat, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter categoria CID-O — código: {}", cat, ex);
            throw ex;
        }
    }
}
