package com.upsaude.controller.api.cnes;

import com.upsaude.api.response.cnes.CnesEstabelecimentoDTO;
import com.upsaude.api.response.cnes.CnesSyncResultDTO;
import com.upsaude.integration.cnes.service.CnesIntegrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/cnes/integration")
@Tag(name = "CNES Integração (Novo)", description = "API para a nova integração completa com o CNES (Estabelecimento, Leitos, Equipamentos)")
@RequiredArgsConstructor
@Slf4j
public class CnesIntegrationController {

    private final CnesIntegrationService cnesIntegrationService;

    @GetMapping("/importar/{cnes}")
    @Operation(summary = "Importar dados do CNES (Preview)", description = "Busca dados no CNES e retorna um DTO para visualização, sem persistir.")
    @PreAuthorize("hasAuthority('CNES_CONSULTAR')") 
    public ResponseEntity<CnesEstabelecimentoDTO> importarDados(
            @Parameter(description = "Código CNES (7 dígitos)", required = true) @PathVariable String cnes) {
        log.info("Recebida requisição para importar dados CNES: {}", cnes);
        CnesEstabelecimentoDTO dto = cnesIntegrationService.importarDadosCnes(cnes);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/sincronizar/{estabelecimentoId}")
    @Operation(summary = "Sincronização Completa de Estabelecimento", description = "Executa a sincronização oficial e completa (Estabelecimento, Leitos, Equipamentos, Histórico) para um estabelecimento já cadastrado.")
    @PreAuthorize("hasAuthority('CNES_EDITAR')") 
    public ResponseEntity<CnesSyncResultDTO> sincronizarCompleto(
            @Parameter(description = "ID do Estabelecimento", required = true) @PathVariable UUID estabelecimentoId) {
        log.info("Recebida requisição para sincronização completa do estabelecimento: {}", estabelecimentoId);
        CnesSyncResultDTO result = cnesIntegrationService.sincronizarEstabelecimento(estabelecimentoId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/validar/{estabelecimentoId}")
    @Operation(summary = "Validar Estabelecimento com CNES", description = "Compara os dados locais com os dados do CNES e aponta divergências.")
    @PreAuthorize("hasAuthority('CNES_CONSULTAR')") 
    public ResponseEntity<CnesSyncResultDTO> validar(
            @Parameter(description = "ID do Estabelecimento", required = true) @PathVariable UUID estabelecimentoId) {
        log.info("Recebida requisição para validar estabelecimento: {}", estabelecimentoId);
        CnesSyncResultDTO result = cnesIntegrationService.validarEstabelecimento(estabelecimentoId);
        return ResponseEntity.ok(result);
    }
}
