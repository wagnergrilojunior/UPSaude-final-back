package com.upsaude.controller.api.cnes;

import com.upsaude.api.response.cnes.CnesSincronizacaoResponse;
import com.upsaude.api.response.estabelecimento.EstabelecimentosResponse;
import com.upsaude.service.api.cnes.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/cnes")
@Tag(name = "CNES Sincronização", description = "API para sincronização de dados com o CNES (Cadastro Nacional de Estabelecimentos de Saúde)")
@RequiredArgsConstructor
@Slf4j
public class CnesController {

    private final CnesEstabelecimentoService estabelecimentoService;
    private final CnesProfissionalService profissionalService;
    private final CnesEquipeService equipeService;
    private final CnesVinculacaoService vinculacaoService;
    private final CnesEquipamentoService equipamentoService;
    private final CnesLeitoService leitoService;

    // ========== ESTABELECIMENTOS ==========

    @PostMapping("/estabelecimentos/{codigoCnes}/sincronizar")
    @Operation(
            summary = "Sincronizar estabelecimento por CNES",
            description = "Sincroniza um estabelecimento específico do CNES pelo código CNES (7 dígitos). " +
                    "Busca dados no serviço SOA-CNES e atualiza/cria o estabelecimento local."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sincronização iniciada com sucesso",
                    content = @Content(schema = @Schema(implementation = CnesSincronizacaoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Código CNES inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro ao sincronizar")
    })
    public ResponseEntity<CnesSincronizacaoResponse> sincronizarEstabelecimento(
            @Parameter(description = "Código CNES (7 dígitos)", required = true, example = "2530031")
            @PathVariable String codigoCnes,
            @Parameter(description = "Competência no formato AAAAMM (opcional)", example = "202501")
            @RequestParam(required = false) String competencia) {
        log.debug("REQUEST POST /v1/cnes/estabelecimentos/{}/sincronizar - competencia: {}", codigoCnes, competencia);
        try {
            CnesSincronizacaoResponse response = estabelecimentoService.sincronizarEstabelecimentoPorCnes(codigoCnes, competencia);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro ao sincronizar estabelecimento CNES: {}", codigoCnes, ex);
            throw ex;
        }
    }

    @PostMapping("/estabelecimentos/municipio/{codigoMunicipio}/sincronizar")
    @Operation(
            summary = "Sincronizar estabelecimentos por município",
            description = "Sincroniza todos os estabelecimentos de um município do CNES pelo código IBGE."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sincronização iniciada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Código do município inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<List<CnesSincronizacaoResponse>> sincronizarEstabelecimentosPorMunicipio(
            @Parameter(description = "Código IBGE do município", required = true, example = "530010")
            @PathVariable String codigoMunicipio,
            @Parameter(description = "Competência no formato AAAAMM (opcional)", example = "202501")
            @RequestParam(required = false) String competencia) {
        log.debug("REQUEST POST /v1/cnes/estabelecimentos/municipio/{}/sincronizar - competencia: {}", codigoMunicipio, competencia);
        try {
            List<CnesSincronizacaoResponse> response = estabelecimentoService.sincronizarEstabelecimentosPorMunicipio(codigoMunicipio, competencia);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro ao sincronizar estabelecimentos do município: {}", codigoMunicipio, ex);
            throw ex;
        }
    }

    @PostMapping("/estabelecimentos/{codigoCnes}/dados-complementares")
    @Operation(
            summary = "Atualizar dados complementares de estabelecimento",
            description = "Atualiza dados complementares de um estabelecimento do CNES."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados complementares atualizados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Código CNES inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CnesSincronizacaoResponse> atualizarDadosComplementares(
            @Parameter(description = "Código CNES (7 dígitos)", required = true, example = "2530031")
            @PathVariable String codigoCnes,
            @Parameter(description = "Competência no formato AAAAMM (opcional)", example = "202501")
            @RequestParam(required = false) String competencia) {
        log.debug("REQUEST POST /v1/cnes/estabelecimentos/{}/dados-complementares - competencia: {}", codigoCnes, competencia);
        try {
            CnesSincronizacaoResponse response = estabelecimentoService.atualizarDadosComplementares(codigoCnes, competencia);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro ao atualizar dados complementares: {}", codigoCnes, ex);
            throw ex;
        }
    }

    @GetMapping("/estabelecimentos/{codigoCnes}/buscar")
    @Operation(
            summary = "Buscar estabelecimento no CNES",
            description = "Busca um estabelecimento no CNES sem sincronizar (apenas consulta)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estabelecimento encontrado"),
            @ApiResponse(responseCode = "404", description = "Estabelecimento não encontrado no CNES"),
            @ApiResponse(responseCode = "400", description = "Código CNES inválido")
    })
    public ResponseEntity<EstabelecimentosResponse> buscarEstabelecimentoNoCnes(
            @Parameter(description = "Código CNES (7 dígitos)", required = true, example = "2530031")
            @PathVariable String codigoCnes) {
        log.debug("REQUEST GET /v1/cnes/estabelecimentos/{}/buscar", codigoCnes);
        try {
            EstabelecimentosResponse response = estabelecimentoService.buscarEstabelecimentoNoCnes(codigoCnes);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro ao buscar estabelecimento no CNES: {}", codigoCnes, ex);
            throw ex;
        }
    }

    // ========== PROFISSIONAIS ==========

    @PostMapping("/profissionais/cns/{numeroCns}/sincronizar")
    @Operation(summary = "Sincronizar profissional por CNS", description = "Sincroniza um profissional do CNES pelo CNS (15 dígitos)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sincronização iniciada com sucesso"),
            @ApiResponse(responseCode = "400", description = "CNS inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CnesSincronizacaoResponse> sincronizarProfissionalPorCns(
            @Parameter(description = "Número do CNS (15 dígitos)", required = true, example = "701009864978597")
            @PathVariable String numeroCns) {
        log.debug("REQUEST POST /v1/cnes/profissionais/cns/{}/sincronizar", numeroCns);
        try {
            CnesSincronizacaoResponse response = profissionalService.sincronizarProfissionalPorCns(numeroCns);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro ao sincronizar profissional CNS: {}", numeroCns, ex);
            throw ex;
        }
    }

    @PostMapping("/profissionais/cpf/{numeroCpf}/sincronizar")
    @Operation(summary = "Sincronizar profissional por CPF", description = "Sincroniza um profissional do CNES pelo CPF")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sincronização iniciada com sucesso"),
            @ApiResponse(responseCode = "400", description = "CPF inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CnesSincronizacaoResponse> sincronizarProfissionalPorCpf(
            @Parameter(description = "Número do CPF", required = true, example = "12345678901")
            @PathVariable String numeroCpf) {
        log.debug("REQUEST POST /v1/cnes/profissionais/cpf/{}/sincronizar", numeroCpf);
        try {
            CnesSincronizacaoResponse response = profissionalService.sincronizarProfissionalPorCpf(numeroCpf);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro ao sincronizar profissional CPF: {}", numeroCpf, ex);
            throw ex;
        }
    }

    @GetMapping("/profissionais/cns/{numeroCns}")
    @Operation(summary = "Buscar profissional no CNES por CNS", description = "Busca um profissional no CNES sem sincronizar (apenas consulta)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profissional encontrado"),
            @ApiResponse(responseCode = "404", description = "Profissional não encontrado no CNES")
    })
    public ResponseEntity<Object> buscarProfissionalPorCns(
            @Parameter(description = "Número do CNS (15 dígitos)", required = true)
            @PathVariable String numeroCns) {
        log.debug("REQUEST GET /v1/cnes/profissionais/cns/{}", numeroCns);
        try {
            Object response = profissionalService.buscarProfissionalNoCnes(numeroCns);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro ao buscar profissional no CNES: {}", numeroCns, ex);
            throw ex;
        }
    }

    // ========== EQUIPES ==========

    @PostMapping("/equipes/estabelecimento/{codigoCnes}/sincronizar")
    @Operation(summary = "Sincronizar equipes de um estabelecimento", description = "Sincroniza todas as equipes de um estabelecimento do CNES")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sincronização iniciada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Código CNES inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CnesSincronizacaoResponse> sincronizarEquipesPorEstabelecimento(
            @Parameter(description = "Código CNES (7 dígitos)", required = true, example = "2530031")
            @PathVariable String codigoCnes) {
        log.debug("REQUEST POST /v1/cnes/equipes/estabelecimento/{}/sincronizar", codigoCnes);
        try {
            CnesSincronizacaoResponse response = equipeService.sincronizarEquipesPorEstabelecimento(codigoCnes);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro ao sincronizar equipes: {}", codigoCnes, ex);
            throw ex;
        }
    }

    @PostMapping("/equipes/estabelecimento/{codigoCnes}/equipe/{ine}/sincronizar")
    @Operation(summary = "Sincronizar equipe específica", description = "Sincroniza uma equipe específica do CNES")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sincronização iniciada com sucesso"),
            @ApiResponse(responseCode = "400", description = "CNES ou INE inválido")
    })
    public ResponseEntity<CnesSincronizacaoResponse> sincronizarEquipe(
            @Parameter(description = "Código CNES (7 dígitos)", required = true)
            @PathVariable String codigoCnes,
            @Parameter(description = "INE (15 caracteres)", required = true)
            @PathVariable String ine) {
        log.debug("REQUEST POST /v1/cnes/equipes/estabelecimento/{}/equipe/{}/sincronizar", codigoCnes, ine);
        try {
            CnesSincronizacaoResponse response = equipeService.sincronizarEquipe(codigoCnes, ine);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro ao sincronizar equipe: {} / {}", codigoCnes, ine, ex);
            throw ex;
        }
    }

    // ========== VINCULAÇÕES ==========

    @PostMapping("/vinculacoes/profissional/{cpfOuCns}/sincronizar")
    @Operation(summary = "Sincronizar vinculações de um profissional", description = "Sincroniza todas as vinculações profissionais de um profissional do CNES")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sincronização iniciada com sucesso"),
            @ApiResponse(responseCode = "400", description = "CPF ou CNS inválido")
    })
    public ResponseEntity<CnesSincronizacaoResponse> sincronizarVinculacoesPorProfissional(
            @Parameter(description = "CPF ou CNS do profissional", required = true)
            @PathVariable String cpfOuCns) {
        log.debug("REQUEST POST /v1/cnes/vinculacoes/profissional/{}/sincronizar", cpfOuCns);
        try {
            CnesSincronizacaoResponse response = vinculacaoService.sincronizarVinculacoesPorProfissional(cpfOuCns);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro ao sincronizar vinculações de profissional: {}", cpfOuCns, ex);
            throw ex;
        }
    }

    @PostMapping("/vinculacoes/estabelecimento/{codigoCnes}/sincronizar")
    @Operation(summary = "Sincronizar vinculações de um estabelecimento", description = "Sincroniza todas as vinculações profissionais de um estabelecimento do CNES")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sincronização iniciada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Código CNES inválido")
    })
    public ResponseEntity<CnesSincronizacaoResponse> sincronizarVinculacoesPorEstabelecimento(
            @Parameter(description = "Código CNES (7 dígitos)", required = true, example = "2530031")
            @PathVariable String codigoCnes) {
        log.debug("REQUEST POST /v1/cnes/vinculacoes/estabelecimento/{}/sincronizar", codigoCnes);
        try {
            CnesSincronizacaoResponse response = vinculacaoService.sincronizarVinculacoesPorEstabelecimento(codigoCnes);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro ao sincronizar vinculações de estabelecimento: {}", codigoCnes, ex);
            throw ex;
        }
    }

    // ========== EQUIPAMENTOS ==========

    @PostMapping("/equipamentos/estabelecimento/{codigoCnes}/sincronizar")
    @Operation(summary = "Sincronizar equipamentos de um estabelecimento", description = "Sincroniza todos os equipamentos de um estabelecimento do CNES")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sincronização iniciada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Código CNES inválido")
    })
    public ResponseEntity<CnesSincronizacaoResponse> sincronizarEquipamentosPorEstabelecimento(
            @Parameter(description = "Código CNES (7 dígitos)", required = true, example = "2530031")
            @PathVariable String codigoCnes) {
        log.debug("REQUEST POST /v1/cnes/equipamentos/estabelecimento/{}/sincronizar", codigoCnes);
        try {
            CnesSincronizacaoResponse response = equipamentoService.sincronizarEquipamentosPorEstabelecimento(codigoCnes);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro ao sincronizar equipamentos: {}", codigoCnes, ex);
            throw ex;
        }
    }

    // ========== LEITOS ==========

    @PostMapping("/leitos/estabelecimento/{codigoCnes}/sincronizar")
    @Operation(summary = "Sincronizar leitos de um estabelecimento", description = "Sincroniza todos os leitos de um estabelecimento do CNES")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sincronização iniciada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Código CNES inválido"),
            @ApiResponse(responseCode = "404", description = "Estabelecimento não encontrado")
    })
    public ResponseEntity<CnesSincronizacaoResponse> sincronizarLeitosPorEstabelecimento(
            @Parameter(description = "Código CNES (7 dígitos)", required = true, example = "2530031")
            @PathVariable String codigoCnes) {
        log.debug("REQUEST POST /v1/cnes/leitos/estabelecimento/{}/sincronizar", codigoCnes);
        try {
            CnesSincronizacaoResponse response = leitoService.sincronizarLeitosPorEstabelecimento(codigoCnes);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro ao sincronizar leitos: {}", codigoCnes, ex);
            throw ex;
        }
    }

    @GetMapping("/leitos/estabelecimento/{codigoCnes}")
    @Operation(summary = "Listar leitos de um estabelecimento", description = "Lista todos os leitos sincronizados de um estabelecimento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de leitos retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estabelecimento não encontrado")
    })
    public ResponseEntity<List<Object>> listarLeitosPorEstabelecimento(
            @Parameter(description = "Código CNES (7 dígitos)", required = true, example = "2530031")
            @PathVariable String codigoCnes) {
        log.debug("REQUEST GET /v1/cnes/leitos/estabelecimento/{}", codigoCnes);
        try {
            // TODO: Implementar listagem quando repositories estiverem prontos
            return ResponseEntity.ok(List.of());
        } catch (Exception ex) {
            log.error("Erro ao listar leitos: {}", codigoCnes, ex);
            throw ex;
        }
    }
}

