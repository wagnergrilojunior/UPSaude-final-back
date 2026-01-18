package com.upsaude.controller.api.integracoes;

import com.upsaude.api.response.integracoes.IbgeSincronizacaoResponse;
import com.upsaude.integration.ibge.dto.IbgeMunicipioDTO;
import com.upsaude.integration.ibge.service.IbgeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/integracoes/ibge")
@Tag(name = "Integração IBGE", description = "API para sincronização de dados geográficos com IBGE")
@RequiredArgsConstructor
@Slf4j
public class IbgeSincronizacaoController {

    private final IbgeService ibgeService;

    @PostMapping("/sincronizar")
    @Operation(
            summary = "Sincronização completa com IBGE",
            description = "Executa sincronização completa de regiões, estados, municípios e população. " +
                    "Todos os parâmetros são opcionais e default true para sincronização completa."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sincronização executada com sucesso",
                    content = @Content(schema = @Schema(implementation = IbgeSincronizacaoResponse.class))),
            @ApiResponse(responseCode = "503", description = "Erro na integração com IBGE"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<IbgeSincronizacaoResponse> sincronizar(
            @Parameter(description = "Sincronizar regiões", example = "true")
            @RequestParam(defaultValue = "true") boolean regioes,
            @Parameter(description = "Sincronizar estados", example = "true")
            @RequestParam(defaultValue = "true") boolean estados,
            @Parameter(description = "Sincronizar municípios", example = "true")
            @RequestParam(defaultValue = "true") boolean municipios,
            @Parameter(description = "Atualizar população", example = "true")
            @RequestParam(defaultValue = "true") boolean populacao) {
        log.info("REQUEST POST /api/v1/integracoes/ibge/sincronizar - regioes: {}, estados: {}, municipios: {}, populacao: {}",
                regioes, estados, municipios, populacao);
        try {
            IbgeSincronizacaoResponse response = ibgeService.sincronizarTudo(regioes, estados, municipios, populacao);
            log.info("Sincronização completa executada. Regiões: {}, Estados: {}, Municípios: {}, População: {}",
                    response.getRegioesSincronizadas(), response.getEstadosSincronizados(),
                    response.getMunicipiosSincronizados(), response.getPopulacaoAtualizada());
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado na sincronização completa", ex);
            throw ex;
        }
    }

    @PostMapping("/sincronizar/regioes")
    @Operation(
            summary = "Sincronizar apenas regiões",
            description = "Sincroniza apenas as regiões do Brasil (Norte, Nordeste, Centro-Oeste, Sudeste, Sul)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Regiões sincronizadas com sucesso",
                    content = @Content(schema = @Schema(implementation = IbgeSincronizacaoResponse.class))),
            @ApiResponse(responseCode = "503", description = "Erro na integração com IBGE"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<IbgeSincronizacaoResponse> sincronizarRegioes() {
        log.info("REQUEST POST /api/v1/integracoes/ibge/sincronizar/regioes");
        try {
            IbgeSincronizacaoResponse response = ibgeService.sincronizarRegioes();
            log.info("Regiões sincronizadas: {}", response.getRegioesSincronizadas());
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado na sincronização de regiões", ex);
            throw ex;
        }
    }

    @PostMapping("/sincronizar/estados")
    @Operation(
            summary = "Sincronizar apenas estados",
            description = "Sincroniza apenas os estados do Brasil. Requer que as regiões já tenham sido sincronizadas."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estados sincronizados com sucesso",
                    content = @Content(schema = @Schema(implementation = IbgeSincronizacaoResponse.class))),
            @ApiResponse(responseCode = "503", description = "Erro na integração com IBGE"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<IbgeSincronizacaoResponse> sincronizarEstados() {
        log.info("REQUEST POST /api/v1/integracoes/ibge/sincronizar/estados");
        try {
            IbgeSincronizacaoResponse response = ibgeService.sincronizarEstados();
            log.info("Estados sincronizados: {}", response.getEstadosSincronizados());
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado na sincronização de estados", ex);
            throw ex;
        }
    }

    @PostMapping("/sincronizar/municipios")
    @Operation(
            summary = "Sincronizar apenas municípios",
            description = "Sincroniza apenas os municípios do Brasil. Requer que os estados já tenham sido sincronizados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Municípios sincronizados com sucesso",
                    content = @Content(schema = @Schema(implementation = IbgeSincronizacaoResponse.class))),
            @ApiResponse(responseCode = "503", description = "Erro na integração com IBGE"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<IbgeSincronizacaoResponse> sincronizarMunicipios() {
        log.info("REQUEST POST /api/v1/integracoes/ibge/sincronizar/municipios");
        try {
            IbgeSincronizacaoResponse response = ibgeService.sincronizarMunicipios();
            log.info("Municípios sincronizados: {}", response.getMunicipiosSincronizados());
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado na sincronização de municípios", ex);
            throw ex;
        }
    }

    @PostMapping("/sincronizar/populacao")
    @Operation(
            summary = "Atualizar apenas população",
            description = "Atualiza apenas a população estimada dos municípios. Requer que os municípios já tenham sido sincronizados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "População atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = IbgeSincronizacaoResponse.class))),
            @ApiResponse(responseCode = "503", description = "Erro na integração com IBGE"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<IbgeSincronizacaoResponse> atualizarPopulacao() {
        log.info("REQUEST POST /api/v1/integracoes/ibge/sincronizar/populacao");
        try {
            IbgeSincronizacaoResponse response = ibgeService.atualizarPopulacao();
            log.info("População atualizada para {} municípios", response.getPopulacaoAtualizada());
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado na atualização de população", ex);
            throw ex;
        }
    }

    @GetMapping("/validar-municipio/{codigoIbge}")
    @Operation(
            summary = "Validar município por código IBGE",
            description = "Valida um município específico consultando a API IBGE pelo código IBGE"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Município validado com sucesso",
                    content = @Content(schema = @Schema(implementation = IbgeMunicipioDTO.class))),
            @ApiResponse(responseCode = "404", description = "Município não encontrado no IBGE"),
            @ApiResponse(responseCode = "503", description = "Erro na integração com IBGE"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<IbgeMunicipioDTO> validarMunicipio(
            @Parameter(description = "Código IBGE do município", required = true, example = "3550308")
            @PathVariable String codigoIbge) {
        log.info("REQUEST GET /api/v1/integracoes/ibge/validar-municipio/{}", codigoIbge);
        try {
            IbgeMunicipioDTO municipio = ibgeService.validarMunicipioPorCodigoIbge(codigoIbge);
            if (municipio == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(municipio);
        } catch (Exception ex) {
            log.error("Erro inesperado na validação de município - código IBGE: {}", codigoIbge, ex);
            throw ex;
        }
    }
}

