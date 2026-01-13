package com.upsaude.controller.api.v1;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.upsaude.entity.farmacia.Medicamento;
import com.upsaude.entity.farmacia.PrincipioAtivo;
import com.upsaude.entity.farmacia.UnidadeMedida;
import com.upsaude.repository.farmacia.MedicamentoRepository;
import com.upsaude.repository.farmacia.PrincipioAtivoRepository;
import com.upsaude.repository.farmacia.UnidadeMedidaRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/medicamento")
@RequiredArgsConstructor
@Tag(name = "Medicamentos", description = "Gestão de medicamentos e princípios ativos")
public class MedicamentoCatalogoController {

    private final MedicamentoRepository medicamentoRepository;
    private final PrincipioAtivoRepository principioAtivoRepository;
    private final UnidadeMedidaRepository unidadeMedidaRepository;

    @GetMapping("/medicamentos")
    @Operation(summary = "Listar medicamentos sincronizados")
    public ResponseEntity<Page<Medicamento>> listarMedicamentos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "nome") String sort,
            @RequestParam(defaultValue = "ASC") String direction) {
        Sort.Direction dir = Sort.Direction.fromString(direction);
        return ResponseEntity.ok(medicamentoRepository.findByAtivoTrue(
                PageRequest.of(page, size, Sort.by(dir, sort))));
    }

    @GetMapping("/medicamentos/{id}")
    @Operation(summary = "Buscar medicamento por ID")
    public ResponseEntity<Medicamento> buscarMedicamentoPorId(@PathVariable UUID id) {
        return medicamentoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/medicamentos/buscar")
    @Operation(summary = "Buscar medicamentos por termo")
    public ResponseEntity<Page<Medicamento>> buscarMedicamentos(
            @RequestParam String termo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "nome") String sort,
            @RequestParam(defaultValue = "ASC") String direction) {
        Sort.Direction dir = Sort.Direction.fromString(direction);
        return ResponseEntity.ok(medicamentoRepository.buscarPorTermo(termo,
                PageRequest.of(page, size, Sort.by(dir, sort))));
    }

    @GetMapping("/principios-ativos")
    @Operation(summary = "Listar princípios ativos sincronizados")
    public ResponseEntity<Page<PrincipioAtivo>> listarPrincipiosAtivos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "nome") String sort,
            @RequestParam(defaultValue = "ASC") String direction) {
        Sort.Direction dir = Sort.Direction.fromString(direction);
        return ResponseEntity.ok(principioAtivoRepository.findByAtivoTrue(
                PageRequest.of(page, size, Sort.by(dir, sort))));
    }

    @GetMapping("/principios-ativos/{id}")
    @Operation(summary = "Buscar princípio ativo por ID")
    public ResponseEntity<PrincipioAtivo> buscarPrincipioAtivoPorId(@PathVariable UUID id) {
        return principioAtivoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/unidades-medida")
    @Operation(summary = "Listar unidades de medida sincronizadas")
    public ResponseEntity<Page<UnidadeMedida>> listarUnidadesMedida(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "nome") String sort,
            @RequestParam(defaultValue = "ASC") String direction) {
        Sort.Direction dir = Sort.Direction.fromString(direction);
        return ResponseEntity.ok(unidadeMedidaRepository.findByAtivoTrue(
                PageRequest.of(page, size, Sort.by(dir, sort))));
    }

    @GetMapping("/status")
    @Operation(summary = "Status dos dados de medicamentos sincronizados")
    public ResponseEntity<Map<String, Object>> status() {
        Map<String, Object> response = new HashMap<>();
        response.put("medicamentos", medicamentoRepository.count());
        response.put("principiosAtivos", principioAtivoRepository.count());
        response.put("unidadesMedida", unidadeMedidaRepository.count());
        return ResponseEntity.ok(response);
    }
}
