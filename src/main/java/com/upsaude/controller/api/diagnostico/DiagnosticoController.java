package com.upsaude.controller.api.diagnostico;

import com.upsaude.api.response.diagnostico.Ciap2Response;
import com.upsaude.api.response.diagnostico.Cid10Response;
import com.upsaude.service.api.diagnostico.DiagnosticoCatalogoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.lang.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/diagnosticos/catalogo")
@RequiredArgsConstructor
@Tag(name = "Catálogo de Diagnósticos", description = "Endpoints para consulta de CID-10 e CIAP-2")
public class DiagnosticoController {

    private final DiagnosticoCatalogoService catalogoService;

    @GetMapping("/cid10")
    @Operation(summary = "Lista ou busca CID-10 no catálogo sincronizado")
    public ResponseEntity<Page<Cid10Response>> listarCid10(
            @RequestParam(required = false) String termo,
            @PageableDefault(size = 20) @NonNull Pageable pageable) {
        return ResponseEntity.ok(catalogoService.listarCid10(termo, pageable));
    }

    @GetMapping("/cid10/{codigo}")
    @Operation(summary = "Busca um item específico do CID-10 pelo código (subcat)")
    public ResponseEntity<Cid10Response> buscarCid10(@PathVariable String codigo) {
        return ResponseEntity.ok(catalogoService.buscarCid10PorCodigo(codigo));
    }

    @GetMapping("/ciap2")
    @Operation(summary = "Lista ou busca CIAP-2 no catálogo sincronizado")
    public ResponseEntity<Page<Ciap2Response>> listarCiap2(
            @RequestParam(required = false) String termo,
            @PageableDefault(size = 20) @NonNull Pageable pageable) {
        return ResponseEntity.ok(catalogoService.listarCiap2(termo, pageable));
    }

    @GetMapping("/ciap2/{codigo}")
    @Operation(summary = "Busca um item específico do CIAP-2 pelo código")
    public ResponseEntity<Ciap2Response> buscarCiap2(@PathVariable String codigo) {
        return ResponseEntity.ok(catalogoService.buscarCiap2PorCodigo(codigo));
    }
}
