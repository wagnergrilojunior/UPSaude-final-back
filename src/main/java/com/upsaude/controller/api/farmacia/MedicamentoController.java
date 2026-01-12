package com.upsaude.controller.api.farmacia;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.upsaude.api.response.farmacia.MedicamentoResponse;
import com.upsaude.api.response.farmacia.PrincipioAtivoResponse;
import com.upsaude.api.response.farmacia.UnidadeMedidaResponse;
import com.upsaude.api.response.farmacia.ViaAdministracaoResponse;
import com.upsaude.service.api.farmacia.MedicamentoCatalogoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/medicamentos/catalogo")
@RequiredArgsConstructor
@Tag(name = "Catálogo de Medicamentos", description = "Endpoints para consulta de catálogos padronizados de medicamentos (OBM/RNDS)")
public class MedicamentoController {

    private final MedicamentoCatalogoService medicamentoCatalogoService;

    @GetMapping("/principios-ativos")
    @Operation(summary = "Listar princípios ativos (VTM)")
    public ResponseEntity<Page<PrincipioAtivoResponse>> listarPrincipiosAtivos(
            @RequestParam(required = false) String termo,
            Pageable pageable) {
        return ResponseEntity.ok(medicamentoCatalogoService.listarPrincipiosAtivos(termo, pageable));
    }

    @GetMapping("/medicamentos")
    @Operation(summary = "Listar medicamentos (VMP/AMP/CATMAT)")
    public ResponseEntity<Page<MedicamentoResponse>> listarMedicamentos(
            @RequestParam(required = false) String termo,
            Pageable pageable) {
        return ResponseEntity.ok(medicamentoCatalogoService.listarMedicamentos(termo, pageable));
    }

    @GetMapping("/sigtap")
    @Operation(summary = "Listar medicamentos do catálogo SIGTAP (Grupo 06)")
    public ResponseEntity<Page<com.upsaude.api.response.referencia.sigtap.SigtapProcedimentoResponse>> listarMedicamentosSigtap(
            @RequestParam(required = false) String termo,
            Pageable pageable) {
        return ResponseEntity.ok(medicamentoCatalogoService.listarMedicamentosSigtap(termo, pageable));
    }

    @GetMapping("/unidades-medida")
    @Operation(summary = "Listar unidades de medida")
    public ResponseEntity<Page<UnidadeMedidaResponse>> listarUnidadesMedida(
            @RequestParam(required = false) String termo,
            Pageable pageable) {
        return ResponseEntity.ok(medicamentoCatalogoService.listarUnidadesMedida(termo, pageable));
    }

    @GetMapping("/vias-administracao")
    @Operation(summary = "Listar vias de administração")
    public ResponseEntity<Page<ViaAdministracaoResponse>> listarViasAdministracao(
            @RequestParam(required = false) String termo,
            Pageable pageable) {
        return ResponseEntity.ok(medicamentoCatalogoService.listarViasAdministracao(termo, pageable));
    }
}
