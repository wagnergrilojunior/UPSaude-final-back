package com.upsaude.controller.api.fhir;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upsaude.enums.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api/enums")
@Slf4j
@Tag(name = "Enums FHIR", description = "Endpoints para consulta de enums com códigos FHIR e descrições em português")
public class FhirEnumsController {

    

    @GetMapping("/estado-civil")
    @Operation(summary = "Listar opções de Estado Civil")
    public ResponseEntity<List<Map<String, Object>>> listarEstadoCivil() {
        return ResponseEntity.ok(Arrays.stream(EstadoCivilEnum.values())
                .map(this::toMap).collect(Collectors.toList()));
    }

    @GetMapping("/sexo")
    @Operation(summary = "Listar opções de Sexo")
    public ResponseEntity<List<Map<String, Object>>> listarSexo() {
        return ResponseEntity.ok(Arrays.stream(SexoEnum.values())
                .map(this::toMap).collect(Collectors.toList()));
    }

    @GetMapping("/raca-cor")
    @Operation(summary = "Listar opções de Raça/Cor")
    public ResponseEntity<List<Map<String, Object>>> listarRacaCor() {
        return ResponseEntity.ok(Arrays.stream(RacaCorEnum.values())
                .map(this::toMap).collect(Collectors.toList()));
    }

    @GetMapping("/identidade-genero")
    @Operation(summary = "Listar opções de Identidade de Gênero")
    public ResponseEntity<List<Map<String, Object>>> listarIdentidadeGenero() {
        return ResponseEntity.ok(Arrays.stream(IdentidadeGeneroEnum.values())
                .map(this::toMap).collect(Collectors.toList()));
    }

    

    @GetMapping("/tipo-identificador")
    @Operation(summary = "Listar opções de Tipo de Identificador")
    public ResponseEntity<List<Map<String, Object>>> listarTipoIdentificador() {
        return ResponseEntity.ok(Arrays.stream(TipoIdentificadorEnum.values())
                .map(this::toMap).collect(Collectors.toList()));
    }

    

    @GetMapping("/tipo-logradouro")
    @Operation(summary = "Listar opções de Tipo de Logradouro")
    public ResponseEntity<List<Map<String, Object>>> listarTipoLogradouro() {
        return ResponseEntity.ok(Arrays.stream(TipoLogradouroEnum.values())
                .map(this::toMap).collect(Collectors.toList()));
    }

    

    @GetMapping("/severidade-alergia")
    @Operation(summary = "Listar opções de Severidade de Alergia")
    public ResponseEntity<List<Map<String, Object>>> listarSeveridadeAlergia() {
        return ResponseEntity.ok(Arrays.stream(SeveridadeAlergiaEnum.values())
                .map(this::toMap).collect(Collectors.toList()));
    }

    @GetMapping("/tipo-alergia")
    @Operation(summary = "Listar opções de Tipo/Categoria de Alergia")
    public ResponseEntity<List<Map<String, Object>>> listarTipoAlergia() {
        return ResponseEntity.ok(Arrays.stream(TipoAlergiaEnum.values())
                .map(this::toMap).collect(Collectors.toList()));
    }

    

    @GetMapping("/tipo-estabelecimento")
    @Operation(summary = "Listar opções de Tipo de Estabelecimento")
    public ResponseEntity<List<Map<String, Object>>> listarTipoEstabelecimento() {
        return ResponseEntity.ok(Arrays.stream(TipoEstabelecimentoEnum.values())
                .map(this::toMap).collect(Collectors.toList()));
    }

    

    @GetMapping("/tipo-convenio")
    @Operation(summary = "Listar opções de Tipo de Convênio")
    public ResponseEntity<List<Map<String, Object>>> listarTipoConvenio() {
        return ResponseEntity.ok(Arrays.stream(TipoConvenioEnum.values())
                .map(this::toMap).collect(Collectors.toList()));
    }

    @GetMapping("/modalidade-financeira")
    @Operation(summary = "Listar opções de Modalidade Financeira")
    public ResponseEntity<List<Map<String, Object>>> listarModalidadeFinanceira() {
        return ResponseEntity.ok(Arrays.stream(ModalidadeFinanceiraEnum.values())
                .map(this::toMap).collect(Collectors.toList()));
    }

    

    @GetMapping("/estado-civil/{codigo}")
    @Operation(summary = "Buscar Estado Civil por código")
    public ResponseEntity<Map<String, Object>> buscarEstadoCivil(@PathVariable Integer codigo) {
        EstadoCivilEnum value = EstadoCivilEnum.fromCodigo(codigo);
        return value != null ? ResponseEntity.ok(toMap(value)) : ResponseEntity.notFound().build();
    }

    @GetMapping("/sexo/{codigo}")
    @Operation(summary = "Buscar Sexo por código")
    public ResponseEntity<Map<String, Object>> buscarSexo(@PathVariable Integer codigo) {
        SexoEnum value = SexoEnum.fromCodigo(codigo);
        return value != null ? ResponseEntity.ok(toMap(value)) : ResponseEntity.notFound().build();
    }

    @GetMapping("/raca-cor/{codigo}")
    @Operation(summary = "Buscar Raça/Cor por código")
    public ResponseEntity<Map<String, Object>> buscarRacaCor(@PathVariable Integer codigo) {
        RacaCorEnum value = RacaCorEnum.fromCodigo(codigo);
        return value != null ? ResponseEntity.ok(toMap(value)) : ResponseEntity.notFound().build();
    }

    

    private Map<String, Object> toMap(EstadoCivilEnum e) {
        Map<String, Object> map = new HashMap<>();
        map.put("codigo", e.getCodigo());
        map.put("descricao", e.getDescricao());
        map.put("codigoFhir", e.getCodigoFhir());
        map.put("systemFhir", e.getSystemFhir());
        return map;
    }

    private Map<String, Object> toMap(SexoEnum e) {
        Map<String, Object> map = new HashMap<>();
        map.put("codigo", e.getCodigo());
        map.put("descricao", e.getDescricao());
        map.put("codigoFhir", e.getCodigoFhir());
        map.put("systemFhir", e.getSystemFhir());
        return map;
    }

    private Map<String, Object> toMap(RacaCorEnum e) {
        Map<String, Object> map = new HashMap<>();
        map.put("codigo", e.getCodigo());
        map.put("descricao", e.getDescricao());
        map.put("codigoFhir", e.getCodigoFhir());
        map.put("systemFhir", e.getSystemFhir());
        return map;
    }

    private Map<String, Object> toMap(IdentidadeGeneroEnum e) {
        Map<String, Object> map = new HashMap<>();
        map.put("codigo", e.getCodigo());
        map.put("descricao", e.getDescricao());
        map.put("codigoFhir", e.getCodigoFhir());
        map.put("systemFhir", e.getSystemFhir());
        return map;
    }

    private Map<String, Object> toMap(TipoIdentificadorEnum e) {
        Map<String, Object> map = new HashMap<>();
        map.put("codigo", e.getCodigo());
        map.put("descricao", e.getDescricao());
        map.put("codigoFhir", e.getCodigoFhir());
        map.put("systemFhir", e.getSystemFhir());
        return map;
    }

    private Map<String, Object> toMap(TipoLogradouroEnum e) {
        Map<String, Object> map = new HashMap<>();
        map.put("codigo", e.getCodigo());
        map.put("descricao", e.getDescricao());
        map.put("codigoFhir", e.getCodigoFhir());
        map.put("systemFhir", e.getSystemFhir());
        return map;
    }

    private Map<String, Object> toMap(SeveridadeAlergiaEnum e) {
        Map<String, Object> map = new HashMap<>();
        map.put("codigo", e.getCodigo());
        map.put("descricao", e.getDescricao());
        map.put("codigoFhir", e.getCodigoFhir());
        map.put("systemFhir", e.getSystemFhir());
        return map;
    }

    private Map<String, Object> toMap(TipoAlergiaEnum e) {
        Map<String, Object> map = new HashMap<>();
        map.put("codigo", e.getCodigo());
        map.put("descricao", e.getDescricao());
        map.put("codigoFhir", e.getCodigoFhir());
        map.put("systemFhir", e.getSystemFhir());
        return map;
    }

    private Map<String, Object> toMap(TipoEstabelecimentoEnum e) {
        Map<String, Object> map = new HashMap<>();
        map.put("codigo", e.getCodigo());
        map.put("descricao", e.getDescricao());
        map.put("codigoFhir", e.getCodigoFhir());
        map.put("systemFhir", e.getSystemFhir());
        return map;
    }

    private Map<String, Object> toMap(TipoConvenioEnum e) {
        Map<String, Object> map = new HashMap<>();
        map.put("codigo", e.getCodigo());
        map.put("descricao", e.getDescricao());
        map.put("codigoFhir", e.getCodigoFhir());
        map.put("systemFhir", e.getSystemFhir());
        return map;
    }

    private Map<String, Object> toMap(ModalidadeFinanceiraEnum e) {
        Map<String, Object> map = new HashMap<>();
        map.put("codigo", e.getCodigo());
        map.put("descricao", e.getDescricao());
        map.put("codigoFhir", e.getCodigoFhir());
        map.put("systemFhir", e.getSystemFhir());
        return map;
    }
}
