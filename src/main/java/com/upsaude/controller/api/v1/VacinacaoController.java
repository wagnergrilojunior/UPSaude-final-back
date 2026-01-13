package com.upsaude.controller.api.v1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upsaude.entity.vacinacao.EstrategiaVacinacao;
import com.upsaude.entity.vacinacao.FabricanteImunobiologico;
import com.upsaude.entity.vacinacao.Imunobiologico;
import com.upsaude.entity.vacinacao.LocalAplicacao;
import com.upsaude.entity.vacinacao.TipoDose;
import com.upsaude.entity.vacinacao.ViaAdministracao;
import com.upsaude.repository.vacinacao.EstrategiaVacinacaoRepository;
import com.upsaude.repository.vacinacao.FabricanteImunobiologicoRepository;
import com.upsaude.repository.vacinacao.ImunobiologicoRepository;
import com.upsaude.repository.vacinacao.LocalAplicacaoRepository;
import com.upsaude.repository.vacinacao.TipoDoseRepository;
import com.upsaude.repository.vacinacao.ViaAdministracaoRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/vacinacao")
@RequiredArgsConstructor
@Tag(name = "Vacinação", description = "Gestão de imunobiológicos e vacinação")
public class VacinacaoController {

    private final ImunobiologicoRepository imunobiologicoRepository;
    private final FabricanteImunobiologicoRepository fabricanteRepository;
    private final TipoDoseRepository tipoDoseRepository;
    private final LocalAplicacaoRepository localAplicacaoRepository;
    private final ViaAdministracaoRepository viaAdministracaoRepository;
    private final EstrategiaVacinacaoRepository estrategiaRepository;

    @GetMapping("/imunobiologicos")
    @Operation(summary = "Listar vacinas sincronizadas")
    public ResponseEntity<List<Imunobiologico>> listarImunobiologicos() {
        return ResponseEntity.ok(imunobiologicoRepository.findByAtivoTrueOrderByNomeAsc());
    }

    @GetMapping("/fabricantes")
    @Operation(summary = "Listar fabricantes sincronizados")
    public ResponseEntity<List<FabricanteImunobiologico>> listarFabricantes() {
        return ResponseEntity.ok(fabricanteRepository.findByAtivoTrueOrderByNomeAsc());
    }

    @GetMapping("/doses")
    @Operation(summary = "Listar tipos de dose sincronizados")
    public ResponseEntity<List<TipoDose>> listarDoses() {
        return ResponseEntity.ok(tipoDoseRepository.findByAtivoTrueOrderByOrdemSequenciaAsc());
    }

    @GetMapping("/locais-aplicacao")
    @Operation(summary = "Listar locais de aplicação sincronizados")
    public ResponseEntity<List<LocalAplicacao>> listarLocaisAplicacao() {
        return ResponseEntity.ok(localAplicacaoRepository.findByAtivoTrueOrderByNomeAsc());
    }

    @GetMapping("/vias-administracao")
    @Operation(summary = "Listar vias de administração sincronizadas")
    public ResponseEntity<List<ViaAdministracao>> listarViasAdministracao() {
        return ResponseEntity.ok(viaAdministracaoRepository.findByAtivoTrueOrderByNomeAsc());
    }

    @GetMapping("/estrategias")
    @Operation(summary = "Listar estratégias de vacinação sincronizadas")
    public ResponseEntity<List<EstrategiaVacinacao>> listarEstrategias() {
        return ResponseEntity.ok(estrategiaRepository.findByAtivoTrueOrderByNomeAsc());
    }

    @GetMapping("/status")
    @Operation(summary = "Status dos dados sincronizados")
    public ResponseEntity<Map<String, Object>> status() {
        Map<String, Object> response = new HashMap<>();
        response.put("imunobiologicos", imunobiologicoRepository.countByAtivoTrue());
        response.put("fabricantes", fabricanteRepository.countByAtivoTrue());
        response.put("tiposDose", tipoDoseRepository.countByAtivoTrue());
        response.put("locaisAplicacao", localAplicacaoRepository.countByAtivoTrue());
        response.put("viasAdministracao", viaAdministracaoRepository.countByAtivoTrue());
        response.put("estrategias", estrategiaRepository.countByAtivoTrue());
        return ResponseEntity.ok(response);
    }
}
