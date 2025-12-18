package com.upsaude.controller.sistema;

import com.upsaude.api.response.EnumInfoResponse;
import com.upsaude.api.response.EnumItemResponse;
import com.upsaude.enums.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/v1/enums")
@Tag(name = "Enums", description = "API para listagem de todos os enums do sistema. " +
        "Cada endpoint retorna os valores de um enum específico com seus códigos e descrições.")
public class EnumsController {

    private <T extends Enum<T>> EnumInfoResponse converterEnum(Class<T> enumClass, T[] values) {
        List<EnumItemResponse> itens = Arrays.stream(values)
                .map(valor -> {
                    try {
                        String nome = valor.name();
                        Integer codigo = null;
                        String descricao = null;

                        try {
                            Method getCodigo = enumClass.getMethod("getCodigo");
                            Object codigoObj = getCodigo.invoke(valor);
                            if (codigoObj instanceof Integer) {
                                codigo = (Integer) codigoObj;
                            }
                        } catch (Exception e) {

                        }

                        try {
                            Method getDescricao = enumClass.getMethod("getDescricao");
                            Object descricaoObj = getDescricao.invoke(valor);
                            if (descricaoObj instanceof String) {
                                descricao = (String) descricaoObj;
                            }
                        } catch (Exception e) {

                        }

                        return EnumItemResponse.builder()
                                .nome(nome)
                                .codigo(codigo)
                                .descricao(descricao)
                                .build();
                    } catch (Exception e) {
                        return EnumItemResponse.builder()
                                .nome(valor.name())
                                .build();
                    }
                })
                .collect(Collectors.toList());

        String nomeClasse = enumClass.getSimpleName();
        String nomeEnum = formatarNomeEnum(nomeClasse);

        return EnumInfoResponse.builder()
                .nomeEnum(nomeEnum)
                .nomeClasse(nomeClasse)
                .valores(itens)
                .build();
    }

    private String formatarNomeEnum(String nomeClasse) {
        String nome = nomeClasse;
        if (nome.endsWith("Enum")) {
            nome = nome.substring(0, nome.length() - 4);
        }
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < nome.length(); i++) {
            char c = nome.charAt(i);
            if (i > 0 && Character.isUpperCase(c)) {
                resultado.append(' ');
            }
            resultado.append(c);
        }
        return resultado.toString();
    }

    @GetMapping("/canal-notificacao")
    @Operation(summary = "Listar valores de CanalNotificacaoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarCanalNotificacao() {
        log.debug("REQUEST GET /v1/enums/canal-notificacao");
        try {
            return ResponseEntity.ok(converterEnum(CanalNotificacaoEnum.class, CanalNotificacaoEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar CanalNotificacaoEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/classe-terapeutica")
    @Operation(summary = "Listar valores de ClasseTerapeuticaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarClasseTerapeutica() {
        log.debug("REQUEST GET /v1/enums/classe-terapeutica");
        try {
            return ResponseEntity.ok(converterEnum(ClasseTerapeuticaEnum.class, ClasseTerapeuticaEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar ClasseTerapeuticaEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/classificacao-risco-gestacional")
    @Operation(summary = "Listar valores de ClassificacaoRiscoGestacionalEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarClassificacaoRiscoGestacional() {
        log.debug("REQUEST GET /v1/enums/classificacao-risco-gestacional");
        try {
            return ResponseEntity.ok(converterEnum(ClassificacaoRiscoGestacionalEnum.class, ClassificacaoRiscoGestacionalEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar ClassificacaoRiscoGestacionalEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/condicao-moradia")
    @Operation(summary = "Listar valores de CondicaoMoradiaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarCondicaoMoradia() {
        log.debug("REQUEST GET /v1/enums/condicao-moradia");
        try {
            return ResponseEntity.ok(converterEnum(CondicaoMoradiaEnum.class, CondicaoMoradiaEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar CondicaoMoradiaEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/escolaridade")
    @Operation(summary = "Listar valores de EscolaridadeEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarEscolaridade() {
        log.debug("REQUEST GET /v1/enums/escolaridade");
        try {
            return ResponseEntity.ok(converterEnum(EscolaridadeEnum.class, EscolaridadeEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar EscolaridadeEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/estado-civil")
    @Operation(summary = "Listar valores de EstadoCivilEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarEstadoCivil() {
        log.debug("REQUEST GET /v1/enums/estado-civil");
        try {
            return ResponseEntity.ok(converterEnum(EstadoCivilEnum.class, EstadoCivilEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar EstadoCivilEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/forma-farmaceutica")
    @Operation(summary = "Listar valores de FormaFarmaceuticaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarFormaFarmaceutica() {
        log.debug("REQUEST GET /v1/enums/forma-farmaceutica");
        try {
            return ResponseEntity.ok(converterEnum(FormaFarmaceuticaEnum.class, FormaFarmaceuticaEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar FormaFarmaceuticaEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/frequencia-medicacao")
    @Operation(summary = "Listar valores de FrequenciaMedicacaoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarFrequenciaMedicacao() {
        log.debug("REQUEST GET /v1/enums/frequencia-medicacao");
        try {
            return ResponseEntity.ok(converterEnum(FrequenciaMedicacaoEnum.class, FrequenciaMedicacaoEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar FrequenciaMedicacaoEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/gravidade-doenca")
    @Operation(summary = "Listar valores de GravidadeDoencaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarGravidadeDoenca() {
        log.debug("REQUEST GET /v1/enums/gravidade-doenca");
        try {
            return ResponseEntity.ok(converterEnum(GravidadeDoencaEnum.class, GravidadeDoencaEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar GravidadeDoencaEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/identidade-genero")
    @Operation(summary = "Listar valores de IdentidadeGeneroEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarIdentidadeGenero() {
        log.debug("REQUEST GET /v1/enums/identidade-genero");
        try {
            return ResponseEntity.ok(converterEnum(IdentidadeGeneroEnum.class, IdentidadeGeneroEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar IdentidadeGeneroEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/modalidade-convenio")
    @Operation(summary = "Listar valores de ModalidadeConvenioEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarModalidadeConvenio() {
        log.debug("REQUEST GET /v1/enums/modalidade-convenio");
        try {
            return ResponseEntity.ok(converterEnum(ModalidadeConvenioEnum.class, ModalidadeConvenioEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar ModalidadeConvenioEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/nacionalidade")
    @Operation(summary = "Listar valores de NacionalidadeEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarNacionalidade() {
        log.debug("REQUEST GET /v1/enums/nacionalidade");
        try {
            return ResponseEntity.ok(converterEnum(NacionalidadeEnum.class, NacionalidadeEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar NacionalidadeEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/natureza-juridica")
    @Operation(summary = "Listar valores de NaturezaJuridicaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarNaturezaJuridica() {
        log.debug("REQUEST GET /v1/enums/natureza-juridica");
        try {
            return ResponseEntity.ok(converterEnum(NaturezaJuridicaEnum.class, NaturezaJuridicaEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar NaturezaJuridicaEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/orientacao-sexual")
    @Operation(summary = "Listar valores de OrientacaoSexualEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarOrientacaoSexual() {
        log.debug("REQUEST GET /v1/enums/orientacao-sexual");
        try {
            return ResponseEntity.ok(converterEnum(OrientacaoSexualEnum.class, OrientacaoSexualEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar OrientacaoSexualEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/prioridade-atendimento")
    @Operation(summary = "Listar valores de PrioridadeAtendimentoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarPrioridadeAtendimento() {
        log.debug("REQUEST GET /v1/enums/prioridade-atendimento");
        try {
            return ResponseEntity.ok(converterEnum(PrioridadeAtendimentoEnum.class, PrioridadeAtendimentoEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar PrioridadeAtendimentoEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/raca-cor")
    @Operation(summary = "Listar valores de RacaCorEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarRacaCor() {
        log.debug("REQUEST GET /v1/enums/raca-cor");
        try {
            return ResponseEntity.ok(converterEnum(RacaCorEnum.class, RacaCorEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar RacaCorEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/severidade-alergia")
    @Operation(summary = "Listar valores de SeveridadeAlergiaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarSeveridadeAlergia() {
        log.debug("REQUEST GET /v1/enums/severidade-alergia");
        try {
            return ResponseEntity.ok(converterEnum(SeveridadeAlergiaEnum.class, SeveridadeAlergiaEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar SeveridadeAlergiaEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/sexo")
    @Operation(summary = "Listar valores de SexoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarSexo() {
        log.debug("REQUEST GET /v1/enums/sexo");
        try {
            return ResponseEntity.ok(converterEnum(SexoEnum.class, SexoEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar SexoEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/situacao-familiar")
    @Operation(summary = "Listar valores de SituacaoFamiliarEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarSituacaoFamiliar() {
        log.debug("REQUEST GET /v1/enums/situacao-familiar");
        try {
            return ResponseEntity.ok(converterEnum(SituacaoFamiliarEnum.class, SituacaoFamiliarEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar SituacaoFamiliarEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/status-agendamento")
    @Operation(summary = "Listar valores de StatusAgendamentoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarStatusAgendamento() {
        log.debug("REQUEST GET /v1/enums/status-agendamento");
        try {
            return ResponseEntity.ok(converterEnum(StatusAgendamentoEnum.class, StatusAgendamentoEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar StatusAgendamentoEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/status-atendimento")
    @Operation(summary = "Listar valores de StatusAtendimentoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarStatusAtendimento() {
        log.debug("REQUEST GET /v1/enums/status-atendimento");
        try {
            return ResponseEntity.ok(converterEnum(StatusAtendimentoEnum.class, StatusAtendimentoEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar StatusAtendimentoEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/status-ativo")
    @Operation(summary = "Listar valores de StatusAtivoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarStatusAtivo() {
        log.debug("REQUEST GET /v1/enums/status-ativo");
        try {
            return ResponseEntity.ok(converterEnum(StatusAtivoEnum.class, StatusAtivoEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar StatusAtivoEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/status-cirurgia")
    @Operation(summary = "Listar valores de StatusCirurgiaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarStatusCirurgia() {
        log.debug("REQUEST GET /v1/enums/status-cirurgia");
        try {
            return ResponseEntity.ok(converterEnum(StatusCirurgiaEnum.class, StatusCirurgiaEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar StatusCirurgiaEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/status-consulta")
    @Operation(summary = "Listar valores de StatusConsultaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarStatusConsulta() {
        log.debug("REQUEST GET /v1/enums/status-consulta");
        try {
            return ResponseEntity.ok(converterEnum(StatusConsultaEnum.class, StatusConsultaEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar StatusConsultaEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/status-diagnostico")
    @Operation(summary = "Listar valores de StatusDiagnosticoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarStatusDiagnostico() {
        log.debug("REQUEST GET /v1/enums/status-diagnostico");
        try {
            return ResponseEntity.ok(converterEnum(StatusDiagnosticoEnum.class, StatusDiagnosticoEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar StatusDiagnosticoEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/status-funcionamento")
    @Operation(summary = "Listar valores de StatusFuncionamentoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarStatusFuncionamento() {
        log.debug("REQUEST GET /v1/enums/status-funcionamento");
        try {
            return ResponseEntity.ok(converterEnum(StatusFuncionamentoEnum.class, StatusFuncionamentoEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar StatusFuncionamentoEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/status-manutencao")
    @Operation(summary = "Listar valores de StatusManutencaoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarStatusManutencao() {
        log.debug("REQUEST GET /v1/enums/status-manutencao");
        try {
            return ResponseEntity.ok(converterEnum(StatusManutencaoEnum.class, StatusManutencaoEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar StatusManutencaoEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/status-paciente")
    @Operation(summary = "Listar valores de StatusPacienteEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarStatusPaciente() {
        log.debug("REQUEST GET /v1/enums/status-paciente");
        try {
            return ResponseEntity.ok(converterEnum(StatusPacienteEnum.class, StatusPacienteEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar StatusPacienteEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/status-pre-natal")
    @Operation(summary = "Listar valores de StatusPreNatalEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarStatusPreNatal() {
        log.debug("REQUEST GET /v1/enums/status-pre-natal");
        try {
            return ResponseEntity.ok(converterEnum(StatusPreNatalEnum.class, StatusPreNatalEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar StatusPreNatalEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/status-receita")
    @Operation(summary = "Listar valores de StatusReceitaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarStatusReceita() {
        log.debug("REQUEST GET /v1/enums/status-receita");
        try {
            return ResponseEntity.ok(converterEnum(StatusReceitaEnum.class, StatusReceitaEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar StatusReceitaEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/status-registro-medico")
    @Operation(summary = "Listar valores de StatusRegistroMedicoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarStatusRegistroMedico() {
        log.debug("REQUEST GET /v1/enums/status-registro-medico");
        try {
            return ResponseEntity.ok(converterEnum(StatusRegistroMedicoEnum.class, StatusRegistroMedicoEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar StatusRegistroMedicoEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/tipo-acao-promocao-saude")
    @Operation(summary = "Listar valores de TipoAcaoPromocaoSaudeEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoAcaoPromocaoSaude() {
        log.debug("REQUEST GET /v1/enums/tipo-acao-promocao-saude");
        try {
            return ResponseEntity.ok(converterEnum(TipoAcaoPromocaoSaudeEnum.class, TipoAcaoPromocaoSaudeEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar TipoAcaoPromocaoSaudeEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/tipo-alergia")
    @Operation(summary = "Listar valores de TipoAlergiaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoAlergia() {
        log.debug("REQUEST GET /v1/enums/tipo-alergia");
        try {
            return ResponseEntity.ok(converterEnum(TipoAlergiaEnum.class, TipoAlergiaEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar TipoAlergiaEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/tipo-atendimento")
    @Operation(summary = "Listar valores de TipoAtendimentoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoAtendimento() {
        log.debug("REQUEST GET /v1/enums/tipo-atendimento");
        try {
            return ResponseEntity.ok(converterEnum(TipoAtendimentoEnum.class, TipoAtendimentoEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar TipoAtendimentoEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/tipo-atendimento-preferencial")
    @Operation(summary = "Listar valores de TipoAtendimentoPreferencialEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoAtendimentoPreferencial() {
        log.debug("REQUEST GET /v1/enums/tipo-atendimento-preferencial");
        try {
            return ResponseEntity.ok(converterEnum(TipoAtendimentoPreferencialEnum.class, TipoAtendimentoPreferencialEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar TipoAtendimentoPreferencialEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/tipo-atividade-profissional")
    @Operation(summary = "Listar valores de TipoAtividadeProfissionalEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoAtividadeProfissional() {
        log.debug("REQUEST GET /v1/enums/tipo-atividade-profissional");
        try {
            return ResponseEntity.ok(converterEnum(TipoAtividadeProfissionalEnum.class, TipoAtividadeProfissionalEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar TipoAtividadeProfissionalEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/tipo-cns")
    @Operation(summary = "Listar valores de TipoCnsEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoCns() {
        log.debug("REQUEST GET /v1/enums/tipo-cns");
        try {
            return ResponseEntity.ok(converterEnum(TipoCnsEnum.class, TipoCnsEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar TipoCnsEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/tipo-consulta")
    @Operation(summary = "Listar valores de TipoConsultaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoConsulta() {
        log.debug("REQUEST GET /v1/enums/tipo-consulta");
        try {
            return ResponseEntity.ok(converterEnum(TipoConsultaEnum.class, TipoConsultaEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar TipoConsultaEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/tipo-controle-medicamento")
    @Operation(summary = "Listar valores de TipoControleMedicamentoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoControleMedicamento() {
        log.debug("REQUEST GET /v1/enums/tipo-controle-medicamento");
        try {
            return ResponseEntity.ok(converterEnum(TipoControleMedicamentoEnum.class, TipoControleMedicamentoEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar TipoControleMedicamentoEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/tipo-convenio")
    @Operation(summary = "Listar valores de TipoConvenioEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoConvenio() {
        log.debug("REQUEST GET /v1/enums/tipo-convenio");
        try {
            return ResponseEntity.ok(converterEnum(TipoConvenioEnum.class, TipoConvenioEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar TipoConvenioEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/tipo-cuidado-enfermagem")
    @Operation(summary = "Listar valores de TipoCuidadoEnfermagemEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoCuidadoEnfermagem() {
        log.debug("REQUEST GET /v1/enums/tipo-cuidado-enfermagem");
        try {
            return ResponseEntity.ok(converterEnum(TipoCuidadoEnfermagemEnum.class, TipoCuidadoEnfermagemEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar TipoCuidadoEnfermagemEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/tipo-deficiencia")
    @Operation(summary = "Listar valores de TipoDeficienciaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoDeficiencia() {
        log.debug("REQUEST GET /v1/enums/tipo-deficiencia");
        try {
            return ResponseEntity.ok(converterEnum(TipoDeficienciaEnum.class, TipoDeficienciaEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar TipoDeficienciaEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/tipo-doenca")
    @Operation(summary = "Listar valores de TipoDoencaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoDoenca() {
        log.debug("REQUEST GET /v1/enums/tipo-doenca");
        try {
            return ResponseEntity.ok(converterEnum(TipoDoencaEnum.class, TipoDoencaEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar TipoDoencaEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/tipo-educacao-saude")
    @Operation(summary = "Listar valores de TipoEducacaoSaudeEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoEducacaoSaude() {
        log.debug("REQUEST GET /v1/enums/tipo-educacao-saude");
        try {
            return ResponseEntity.ok(converterEnum(TipoEducacaoSaudeEnum.class, TipoEducacaoSaudeEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar TipoEducacaoSaudeEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/tipo-endereco")
    @Operation(summary = "Listar valores de TipoEnderecoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoEndereco() {
        log.debug("REQUEST GET /v1/enums/tipo-endereco");
        try {
            return ResponseEntity.ok(converterEnum(TipoEnderecoEnum.class, TipoEnderecoEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar TipoEnderecoEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/tipo-equipamento")
    @Operation(summary = "Listar valores de TipoEquipamentoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoEquipamento() {
        log.debug("REQUEST GET /v1/enums/tipo-equipamento");
        try {
            return ResponseEntity.ok(converterEnum(TipoEquipamentoEnum.class, TipoEquipamentoEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar TipoEquipamentoEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/tipo-equipe")
    @Operation(summary = "Listar valores de TipoEquipeEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoEquipe() {
        log.debug("REQUEST GET /v1/enums/tipo-equipe");
        try {
            return ResponseEntity.ok(converterEnum(TipoEquipeEnum.class, TipoEquipeEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar TipoEquipeEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/tipo-especialidade-medica")
    @Operation(summary = "Listar valores de TipoEspecialidadeMedicaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoEspecialidadeMedica() {
        log.debug("REQUEST GET /v1/enums/tipo-especialidade-medica");
        try {
            return ResponseEntity.ok(converterEnum(TipoEspecialidadeMedicaEnum.class, TipoEspecialidadeMedicaEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar TipoEspecialidadeMedicaEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/tipo-estabelecimento")
    @Operation(summary = "Listar valores de TipoEstabelecimentoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoEstabelecimento() {
        log.debug("REQUEST GET /v1/enums/tipo-estabelecimento");
        try {
            return ResponseEntity.ok(converterEnum(TipoEstabelecimentoEnum.class, TipoEstabelecimentoEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar TipoEstabelecimentoEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/tipo-exame")
    @Operation(summary = "Listar valores de TipoExameEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoExame() {
        log.debug("REQUEST GET /v1/enums/tipo-exame");
        try {
            return ResponseEntity.ok(converterEnum(TipoExameEnum.class, TipoExameEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar TipoExameEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/tipo-falta")
    @Operation(summary = "Listar valores de TipoFaltaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoFalta() {
        log.debug("REQUEST GET /v1/enums/tipo-falta");
        try {
            return ResponseEntity.ok(converterEnum(TipoFaltaEnum.class, TipoFaltaEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar TipoFaltaEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/tipo-logradouro")
    @Operation(summary = "Listar valores de TipoLogradouroEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoLogradouro() {
        log.debug("REQUEST GET /v1/enums/tipo-logradouro");
        try {
            return ResponseEntity.ok(converterEnum(TipoLogradouroEnum.class, TipoLogradouroEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar TipoLogradouroEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/tipo-metodo-contraceptivo")
    @Operation(summary = "Listar valores de TipoMetodoContraceptivoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoMetodoContraceptivo() {
        log.debug("REQUEST GET /v1/enums/tipo-metodo-contraceptivo");
        try {
            return ResponseEntity.ok(converterEnum(TipoMetodoContraceptivoEnum.class, TipoMetodoContraceptivoEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar TipoMetodoContraceptivoEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/tipo-notificacao")
    @Operation(summary = "Listar valores de TipoNotificacaoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoNotificacao() {
        log.debug("REQUEST GET /v1/enums/tipo-notificacao");
        try {
            return ResponseEntity.ok(converterEnum(TipoNotificacaoEnum.class, TipoNotificacaoEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar TipoNotificacaoEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/tipo-planto")
    @Operation(summary = "Listar valores de TipoPlantaoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoPlantao() {
        log.debug("REQUEST GET /v1/enums/tipo-planto");
        try {
            return ResponseEntity.ok(converterEnum(TipoPlantaoEnum.class, TipoPlantaoEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar TipoPlantaoEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/tipo-ponto")
    @Operation(summary = "Listar valores de TipoPontoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoPonto() {
        log.debug("REQUEST GET /v1/enums/tipo-ponto");
        try {
            return ResponseEntity.ok(converterEnum(TipoPontoEnum.class, TipoPontoEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar TipoPontoEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/tipo-procedimento")
    @Operation(summary = "Listar valores de TipoProcedimentoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoProcedimento() {
        log.debug("REQUEST GET /v1/enums/tipo-procedimento");
        try {
            return ResponseEntity.ok(converterEnum(TipoProcedimentoEnum.class, TipoProcedimentoEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar TipoProcedimentoEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/tipo-profissional")
    @Operation(summary = "Listar valores de TipoProfissionalEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoProfissional() {
        log.debug("REQUEST GET /v1/enums/tipo-profissional");
        try {
            return ResponseEntity.ok(converterEnum(TipoProfissionalEnum.class, TipoProfissionalEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar TipoProfissionalEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/tipo-reacao-alergica")
    @Operation(summary = "Listar valores de TipoReacaoAlergicaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoReacaoAlergica() {
        log.debug("REQUEST GET /v1/enums/tipo-reacao-alergica");
        try {
            return ResponseEntity.ok(converterEnum(TipoReacaoAlergicaEnum.class, TipoReacaoAlergicaEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar TipoReacaoAlergicaEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/tipo-responsavel")
    @Operation(summary = "Listar valores de TipoResponsavelEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoResponsavel() {
        log.debug("REQUEST GET /v1/enums/tipo-responsavel");
        try {
            return ResponseEntity.ok(converterEnum(TipoResponsavelEnum.class, TipoResponsavelEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar TipoResponsavelEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/tipo-servico-saude")
    @Operation(summary = "Listar valores de TipoServicoSaudeEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoServicoSaude() {
        log.debug("REQUEST GET /v1/enums/tipo-servico-saude");
        try {
            return ResponseEntity.ok(converterEnum(TipoServicoSaudeEnum.class, TipoServicoSaudeEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar TipoServicoSaudeEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/tipo-usuario-sistema")
    @Operation(summary = "Listar valores de TipoUsuarioSistemaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoUsuarioSistema() {
        log.debug("REQUEST GET /v1/enums/tipo-usuario-sistema");
        try {
            return ResponseEntity.ok(converterEnum(TipoUsuarioSistemaEnum.class, TipoUsuarioSistemaEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar TipoUsuarioSistemaEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/tipo-vacina")
    @Operation(summary = "Listar valores de TipoVacinaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoVacina() {
        log.debug("REQUEST GET /v1/enums/tipo-vacina");
        try {
            return ResponseEntity.ok(converterEnum(TipoVacinaEnum.class, TipoVacinaEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar TipoVacinaEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/tipo-vinculo-profissional")
    @Operation(summary = "Listar valores de TipoVinculoProfissionalEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoVinculoProfissional() {
        log.debug("REQUEST GET /v1/enums/tipo-vinculo-profissional");
        try {
            return ResponseEntity.ok(converterEnum(TipoVinculoProfissionalEnum.class, TipoVinculoProfissionalEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar TipoVinculoProfissionalEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/tipo-visita-domiciliar")
    @Operation(summary = "Listar valores de TipoVisitaDomiciliarEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoVisitaDomiciliar() {
        log.debug("REQUEST GET /v1/enums/tipo-visita-domiciliar");
        try {
            return ResponseEntity.ok(converterEnum(TipoVisitaDomiciliarEnum.class, TipoVisitaDomiciliarEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar TipoVisitaDomiciliarEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/unidade-medida")
    @Operation(summary = "Listar valores de UnidadeMedidaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarUnidadeMedida() {
        log.debug("REQUEST GET /v1/enums/unidade-medida");
        try {
            return ResponseEntity.ok(converterEnum(UnidadeMedidaEnum.class, UnidadeMedidaEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar UnidadeMedidaEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/via-administracao")
    @Operation(summary = "Listar valores de ViaAdministracaoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarViaAdministracao() {
        log.debug("REQUEST GET /v1/enums/via-administracao");
        try {
            return ResponseEntity.ok(converterEnum(ViaAdministracaoEnum.class, ViaAdministracaoEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar ViaAdministracaoEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/via-administracao-medicamento")
    @Operation(summary = "Listar valores de ViaAdministracaoMedicamentoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarViaAdministracaoMedicamento() {
        log.debug("REQUEST GET /v1/enums/via-administracao-medicamento");
        try {
            return ResponseEntity.ok(converterEnum(ViaAdministracaoMedicamentoEnum.class, ViaAdministracaoMedicamentoEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar ViaAdministracaoMedicamentoEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/via-administracao-vacina")
    @Operation(summary = "Listar valores de ViaAdministracaoVacinaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarViaAdministracaoVacina() {
        log.debug("REQUEST GET /v1/enums/via-administracao-vacina");
        try {
            return ResponseEntity.ok(converterEnum(ViaAdministracaoVacinaEnum.class, ViaAdministracaoVacinaEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar ViaAdministracaoVacinaEnum", ex);
            throw ex;
        }
    }

    @GetMapping("/zona-domicilio")
    @Operation(summary = "Listar valores de ZonaDomicilioEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarZonaDomicilio() {
        log.debug("REQUEST GET /v1/enums/zona-domicilio");
        try {
            return ResponseEntity.ok(converterEnum(ZonaDomicilioEnum.class, ZonaDomicilioEnum.values()));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar ZonaDomicilioEnum", ex);
            throw ex;
        }
    }
}
