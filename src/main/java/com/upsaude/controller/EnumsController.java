package com.upsaude.controller;

import com.upsaude.api.response.EnumInfoResponse;
import com.upsaude.api.response.EnumItemResponse;
import com.upsaude.enums.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST para operações relacionadas a Enums.
 * Cada método retorna os valores de um enum específico.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/enums")
@Tag(name = "Enums", description = "API para listagem de todos os enums do sistema. " +
        "Cada endpoint retorna os valores de um enum específico com seus códigos e descrições.")
public class EnumsController {

    // Método helper para converter enum com código e descrição
    private <T extends Enum<T>> EnumInfoResponse converterEnum(Class<T> enumClass, T[] values) {
        List<EnumItemResponse> itens = Arrays.stream(values)
                .map(valor -> {
                    try {
                        String nome = valor.name();
                        Integer codigo = null;
                        String descricao = null;

                        // Tenta obter código
                        try {
                            Method getCodigo = enumClass.getMethod("getCodigo");
                            Object codigoObj = getCodigo.invoke(valor);
                            if (codigoObj instanceof Integer) {
                                codigo = (Integer) codigoObj;
                            }
                        } catch (Exception e) {
                            // Enum não tem código
                        }

                        // Tenta obter descrição
                        try {
                            Method getDescricao = enumClass.getMethod("getDescricao");
                            Object descricaoObj = getDescricao.invoke(valor);
                            if (descricaoObj instanceof String) {
                                descricao = (String) descricaoObj;
                            }
                        } catch (Exception e) {
                            // Enum não tem descrição
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
        return ResponseEntity.ok(converterEnum(CanalNotificacaoEnum.class, CanalNotificacaoEnum.values()));
    }

    @GetMapping("/classe-terapeutica")
    @Operation(summary = "Listar valores de ClasseTerapeuticaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarClasseTerapeutica() {
        return ResponseEntity.ok(converterEnum(ClasseTerapeuticaEnum.class, ClasseTerapeuticaEnum.values()));
    }

    @GetMapping("/classificacao-risco-gestacional")
    @Operation(summary = "Listar valores de ClassificacaoRiscoGestacionalEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarClassificacaoRiscoGestacional() {
        return ResponseEntity.ok(converterEnum(ClassificacaoRiscoGestacionalEnum.class, ClassificacaoRiscoGestacionalEnum.values()));
    }

    @GetMapping("/condicao-moradia")
    @Operation(summary = "Listar valores de CondicaoMoradiaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarCondicaoMoradia() {
        return ResponseEntity.ok(converterEnum(CondicaoMoradiaEnum.class, CondicaoMoradiaEnum.values()));
    }

    @GetMapping("/escolaridade")
    @Operation(summary = "Listar valores de EscolaridadeEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarEscolaridade() {
        return ResponseEntity.ok(converterEnum(EscolaridadeEnum.class, EscolaridadeEnum.values()));
    }

    @GetMapping("/estado-civil")
    @Operation(summary = "Listar valores de EstadoCivilEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarEstadoCivil() {
        return ResponseEntity.ok(converterEnum(EstadoCivilEnum.class, EstadoCivilEnum.values()));
    }

    @GetMapping("/forma-farmaceutica")
    @Operation(summary = "Listar valores de FormaFarmaceuticaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarFormaFarmaceutica() {
        return ResponseEntity.ok(converterEnum(FormaFarmaceuticaEnum.class, FormaFarmaceuticaEnum.values()));
    }

    @GetMapping("/frequencia-medicacao")
    @Operation(summary = "Listar valores de FrequenciaMedicacaoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarFrequenciaMedicacao() {
        return ResponseEntity.ok(converterEnum(FrequenciaMedicacaoEnum.class, FrequenciaMedicacaoEnum.values()));
    }

    @GetMapping("/gravidade-doenca")
    @Operation(summary = "Listar valores de GravidadeDoencaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarGravidadeDoenca() {
        return ResponseEntity.ok(converterEnum(GravidadeDoencaEnum.class, GravidadeDoencaEnum.values()));
    }

    @GetMapping("/identidade-genero")
    @Operation(summary = "Listar valores de IdentidadeGeneroEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarIdentidadeGenero() {
        return ResponseEntity.ok(converterEnum(IdentidadeGeneroEnum.class, IdentidadeGeneroEnum.values()));
    }

    @GetMapping("/modalidade-convenio")
    @Operation(summary = "Listar valores de ModalidadeConvenioEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarModalidadeConvenio() {
        return ResponseEntity.ok(converterEnum(ModalidadeConvenioEnum.class, ModalidadeConvenioEnum.values()));
    }

    @GetMapping("/nacionalidade")
    @Operation(summary = "Listar valores de NacionalidadeEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarNacionalidade() {
        return ResponseEntity.ok(converterEnum(NacionalidadeEnum.class, NacionalidadeEnum.values()));
    }

    @GetMapping("/natureza-juridica")
    @Operation(summary = "Listar valores de NaturezaJuridicaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarNaturezaJuridica() {
        return ResponseEntity.ok(converterEnum(NaturezaJuridicaEnum.class, NaturezaJuridicaEnum.values()));
    }

    @GetMapping("/orientacao-sexual")
    @Operation(summary = "Listar valores de OrientacaoSexualEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarOrientacaoSexual() {
        return ResponseEntity.ok(converterEnum(OrientacaoSexualEnum.class, OrientacaoSexualEnum.values()));
    }

    @GetMapping("/prioridade-atendimento")
    @Operation(summary = "Listar valores de PrioridadeAtendimentoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarPrioridadeAtendimento() {
        return ResponseEntity.ok(converterEnum(PrioridadeAtendimentoEnum.class, PrioridadeAtendimentoEnum.values()));
    }

    @GetMapping("/raca-cor")
    @Operation(summary = "Listar valores de RacaCorEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarRacaCor() {
        return ResponseEntity.ok(converterEnum(RacaCorEnum.class, RacaCorEnum.values()));
    }

    @GetMapping("/severidade-alergia")
    @Operation(summary = "Listar valores de SeveridadeAlergiaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarSeveridadeAlergia() {
        return ResponseEntity.ok(converterEnum(SeveridadeAlergiaEnum.class, SeveridadeAlergiaEnum.values()));
    }

    @GetMapping("/sexo")
    @Operation(summary = "Listar valores de SexoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarSexo() {
        return ResponseEntity.ok(converterEnum(SexoEnum.class, SexoEnum.values()));
    }

    @GetMapping("/situacao-familiar")
    @Operation(summary = "Listar valores de SituacaoFamiliarEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarSituacaoFamiliar() {
        return ResponseEntity.ok(converterEnum(SituacaoFamiliarEnum.class, SituacaoFamiliarEnum.values()));
    }

    @GetMapping("/status-agendamento")
    @Operation(summary = "Listar valores de StatusAgendamentoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarStatusAgendamento() {
        return ResponseEntity.ok(converterEnum(StatusAgendamentoEnum.class, StatusAgendamentoEnum.values()));
    }

    @GetMapping("/status-atendimento")
    @Operation(summary = "Listar valores de StatusAtendimentoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarStatusAtendimento() {
        return ResponseEntity.ok(converterEnum(StatusAtendimentoEnum.class, StatusAtendimentoEnum.values()));
    }

    @GetMapping("/status-ativo")
    @Operation(summary = "Listar valores de StatusAtivoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarStatusAtivo() {
        return ResponseEntity.ok(converterEnum(StatusAtivoEnum.class, StatusAtivoEnum.values()));
    }

    @GetMapping("/status-cirurgia")
    @Operation(summary = "Listar valores de StatusCirurgiaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarStatusCirurgia() {
        return ResponseEntity.ok(converterEnum(StatusCirurgiaEnum.class, StatusCirurgiaEnum.values()));
    }

    @GetMapping("/status-consulta")
    @Operation(summary = "Listar valores de StatusConsultaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarStatusConsulta() {
        return ResponseEntity.ok(converterEnum(StatusConsultaEnum.class, StatusConsultaEnum.values()));
    }

    @GetMapping("/status-diagnostico")
    @Operation(summary = "Listar valores de StatusDiagnosticoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarStatusDiagnostico() {
        return ResponseEntity.ok(converterEnum(StatusDiagnosticoEnum.class, StatusDiagnosticoEnum.values()));
    }

    @GetMapping("/status-funcionamento")
    @Operation(summary = "Listar valores de StatusFuncionamentoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarStatusFuncionamento() {
        return ResponseEntity.ok(converterEnum(StatusFuncionamentoEnum.class, StatusFuncionamentoEnum.values()));
    }

    @GetMapping("/status-manutencao")
    @Operation(summary = "Listar valores de StatusManutencaoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarStatusManutencao() {
        return ResponseEntity.ok(converterEnum(StatusManutencaoEnum.class, StatusManutencaoEnum.values()));
    }

    @GetMapping("/status-paciente")
    @Operation(summary = "Listar valores de StatusPacienteEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarStatusPaciente() {
        return ResponseEntity.ok(converterEnum(StatusPacienteEnum.class, StatusPacienteEnum.values()));
    }

    @GetMapping("/status-pre-natal")
    @Operation(summary = "Listar valores de StatusPreNatalEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarStatusPreNatal() {
        return ResponseEntity.ok(converterEnum(StatusPreNatalEnum.class, StatusPreNatalEnum.values()));
    }

    @GetMapping("/status-receita")
    @Operation(summary = "Listar valores de StatusReceitaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarStatusReceita() {
        return ResponseEntity.ok(converterEnum(StatusReceitaEnum.class, StatusReceitaEnum.values()));
    }

    @GetMapping("/status-registro-medico")
    @Operation(summary = "Listar valores de StatusRegistroMedicoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarStatusRegistroMedico() {
        return ResponseEntity.ok(converterEnum(StatusRegistroMedicoEnum.class, StatusRegistroMedicoEnum.values()));
    }

    @GetMapping("/tipo-acao-promocao-saude")
    @Operation(summary = "Listar valores de TipoAcaoPromocaoSaudeEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoAcaoPromocaoSaude() {
        return ResponseEntity.ok(converterEnum(TipoAcaoPromocaoSaudeEnum.class, TipoAcaoPromocaoSaudeEnum.values()));
    }

    @GetMapping("/tipo-alergia")
    @Operation(summary = "Listar valores de TipoAlergiaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoAlergia() {
        return ResponseEntity.ok(converterEnum(TipoAlergiaEnum.class, TipoAlergiaEnum.values()));
    }

    @GetMapping("/tipo-atendimento")
    @Operation(summary = "Listar valores de TipoAtendimentoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoAtendimento() {
        return ResponseEntity.ok(converterEnum(TipoAtendimentoEnum.class, TipoAtendimentoEnum.values()));
    }

    @GetMapping("/tipo-atendimento-preferencial")
    @Operation(summary = "Listar valores de TipoAtendimentoPreferencialEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoAtendimentoPreferencial() {
        return ResponseEntity.ok(converterEnum(TipoAtendimentoPreferencialEnum.class, TipoAtendimentoPreferencialEnum.values()));
    }

    @GetMapping("/tipo-atividade-profissional")
    @Operation(summary = "Listar valores de TipoAtividadeProfissionalEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoAtividadeProfissional() {
        return ResponseEntity.ok(converterEnum(TipoAtividadeProfissionalEnum.class, TipoAtividadeProfissionalEnum.values()));
    }

    @GetMapping("/tipo-cns")
    @Operation(summary = "Listar valores de TipoCnsEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoCns() {
        return ResponseEntity.ok(converterEnum(TipoCnsEnum.class, TipoCnsEnum.values()));
    }

    @GetMapping("/tipo-consulta")
    @Operation(summary = "Listar valores de TipoConsultaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoConsulta() {
        return ResponseEntity.ok(converterEnum(TipoConsultaEnum.class, TipoConsultaEnum.values()));
    }

    @GetMapping("/tipo-controle-medicamento")
    @Operation(summary = "Listar valores de TipoControleMedicamentoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoControleMedicamento() {
        return ResponseEntity.ok(converterEnum(TipoControleMedicamentoEnum.class, TipoControleMedicamentoEnum.values()));
    }

    @GetMapping("/tipo-convenio")
    @Operation(summary = "Listar valores de TipoConvenioEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoConvenio() {
        return ResponseEntity.ok(converterEnum(TipoConvenioEnum.class, TipoConvenioEnum.values()));
    }

    @GetMapping("/tipo-cuidado-enfermagem")
    @Operation(summary = "Listar valores de TipoCuidadoEnfermagemEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoCuidadoEnfermagem() {
        return ResponseEntity.ok(converterEnum(TipoCuidadoEnfermagemEnum.class, TipoCuidadoEnfermagemEnum.values()));
    }

    @GetMapping("/tipo-deficiencia")
    @Operation(summary = "Listar valores de TipoDeficienciaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoDeficiencia() {
        return ResponseEntity.ok(converterEnum(TipoDeficienciaEnum.class, TipoDeficienciaEnum.values()));
    }

    @GetMapping("/tipo-doenca")
    @Operation(summary = "Listar valores de TipoDoencaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoDoenca() {
        return ResponseEntity.ok(converterEnum(TipoDoencaEnum.class, TipoDoencaEnum.values()));
    }

    @GetMapping("/tipo-educacao-saude")
    @Operation(summary = "Listar valores de TipoEducacaoSaudeEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoEducacaoSaude() {
        return ResponseEntity.ok(converterEnum(TipoEducacaoSaudeEnum.class, TipoEducacaoSaudeEnum.values()));
    }

    @GetMapping("/tipo-endereco")
    @Operation(summary = "Listar valores de TipoEnderecoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoEndereco() {
        return ResponseEntity.ok(converterEnum(TipoEnderecoEnum.class, TipoEnderecoEnum.values()));
    }

    @GetMapping("/tipo-equipamento")
    @Operation(summary = "Listar valores de TipoEquipamentoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoEquipamento() {
        return ResponseEntity.ok(converterEnum(TipoEquipamentoEnum.class, TipoEquipamentoEnum.values()));
    }

    @GetMapping("/tipo-equipe")
    @Operation(summary = "Listar valores de TipoEquipeEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoEquipe() {
        return ResponseEntity.ok(converterEnum(TipoEquipeEnum.class, TipoEquipeEnum.values()));
    }

    @GetMapping("/tipo-especialidade-medica")
    @Operation(summary = "Listar valores de TipoEspecialidadeMedicaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoEspecialidadeMedica() {
        return ResponseEntity.ok(converterEnum(TipoEspecialidadeMedicaEnum.class, TipoEspecialidadeMedicaEnum.values()));
    }

    @GetMapping("/tipo-estabelecimento")
    @Operation(summary = "Listar valores de TipoEstabelecimentoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoEstabelecimento() {
        return ResponseEntity.ok(converterEnum(TipoEstabelecimentoEnum.class, TipoEstabelecimentoEnum.values()));
    }

    @GetMapping("/tipo-exame")
    @Operation(summary = "Listar valores de TipoExameEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoExame() {
        return ResponseEntity.ok(converterEnum(TipoExameEnum.class, TipoExameEnum.values()));
    }

    @GetMapping("/tipo-falta")
    @Operation(summary = "Listar valores de TipoFaltaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoFalta() {
        return ResponseEntity.ok(converterEnum(TipoFaltaEnum.class, TipoFaltaEnum.values()));
    }

    @GetMapping("/tipo-logradouro")
    @Operation(summary = "Listar valores de TipoLogradouroEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoLogradouro() {
        return ResponseEntity.ok(converterEnum(TipoLogradouroEnum.class, TipoLogradouroEnum.values()));
    }

    @GetMapping("/tipo-metodo-contraceptivo")
    @Operation(summary = "Listar valores de TipoMetodoContraceptivoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoMetodoContraceptivo() {
        return ResponseEntity.ok(converterEnum(TipoMetodoContraceptivoEnum.class, TipoMetodoContraceptivoEnum.values()));
    }

    @GetMapping("/tipo-notificacao")
    @Operation(summary = "Listar valores de TipoNotificacaoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoNotificacao() {
        return ResponseEntity.ok(converterEnum(TipoNotificacaoEnum.class, TipoNotificacaoEnum.values()));
    }

    @GetMapping("/tipo-planto")
    @Operation(summary = "Listar valores de TipoPlantaoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoPlantao() {
        return ResponseEntity.ok(converterEnum(TipoPlantaoEnum.class, TipoPlantaoEnum.values()));
    }

    @GetMapping("/tipo-ponto")
    @Operation(summary = "Listar valores de TipoPontoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoPonto() {
        return ResponseEntity.ok(converterEnum(TipoPontoEnum.class, TipoPontoEnum.values()));
    }

    @GetMapping("/tipo-procedimento")
    @Operation(summary = "Listar valores de TipoProcedimentoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoProcedimento() {
        return ResponseEntity.ok(converterEnum(TipoProcedimentoEnum.class, TipoProcedimentoEnum.values()));
    }

    @GetMapping("/tipo-profissional")
    @Operation(summary = "Listar valores de TipoProfissionalEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoProfissional() {
        return ResponseEntity.ok(converterEnum(TipoProfissionalEnum.class, TipoProfissionalEnum.values()));
    }

    @GetMapping("/tipo-reacao-alergica")
    @Operation(summary = "Listar valores de TipoReacaoAlergicaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoReacaoAlergica() {
        return ResponseEntity.ok(converterEnum(TipoReacaoAlergicaEnum.class, TipoReacaoAlergicaEnum.values()));
    }

    @GetMapping("/tipo-responsavel")
    @Operation(summary = "Listar valores de TipoResponsavelEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoResponsavel() {
        return ResponseEntity.ok(converterEnum(TipoResponsavelEnum.class, TipoResponsavelEnum.values()));
    }

    @GetMapping("/tipo-servico-saude")
    @Operation(summary = "Listar valores de TipoServicoSaudeEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoServicoSaude() {
        return ResponseEntity.ok(converterEnum(TipoServicoSaudeEnum.class, TipoServicoSaudeEnum.values()));
    }

    @GetMapping("/tipo-usuario-sistema")
    @Operation(summary = "Listar valores de TipoUsuarioSistemaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoUsuarioSistema() {
        return ResponseEntity.ok(converterEnum(TipoUsuarioSistemaEnum.class, TipoUsuarioSistemaEnum.values()));
    }

    @GetMapping("/tipo-vacina")
    @Operation(summary = "Listar valores de TipoVacinaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoVacina() {
        return ResponseEntity.ok(converterEnum(TipoVacinaEnum.class, TipoVacinaEnum.values()));
    }

    @GetMapping("/tipo-vinculo-profissional")
    @Operation(summary = "Listar valores de TipoVinculoProfissionalEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoVinculoProfissional() {
        return ResponseEntity.ok(converterEnum(TipoVinculoProfissionalEnum.class, TipoVinculoProfissionalEnum.values()));
    }

    @GetMapping("/tipo-visita-domiciliar")
    @Operation(summary = "Listar valores de TipoVisitaDomiciliarEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarTipoVisitaDomiciliar() {
        return ResponseEntity.ok(converterEnum(TipoVisitaDomiciliarEnum.class, TipoVisitaDomiciliarEnum.values()));
    }

    @GetMapping("/unidade-medida")
    @Operation(summary = "Listar valores de UnidadeMedidaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarUnidadeMedida() {
        return ResponseEntity.ok(converterEnum(UnidadeMedidaEnum.class, UnidadeMedidaEnum.values()));
    }

    @GetMapping("/via-administracao")
    @Operation(summary = "Listar valores de ViaAdministracaoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarViaAdministracao() {
        return ResponseEntity.ok(converterEnum(ViaAdministracaoEnum.class, ViaAdministracaoEnum.values()));
    }

    @GetMapping("/via-administracao-medicamento")
    @Operation(summary = "Listar valores de ViaAdministracaoMedicamentoEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarViaAdministracaoMedicamento() {
        return ResponseEntity.ok(converterEnum(ViaAdministracaoMedicamentoEnum.class, ViaAdministracaoMedicamentoEnum.values()));
    }

    @GetMapping("/via-administracao-vacina")
    @Operation(summary = "Listar valores de ViaAdministracaoVacinaEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarViaAdministracaoVacina() {
        return ResponseEntity.ok(converterEnum(ViaAdministracaoVacinaEnum.class, ViaAdministracaoVacinaEnum.values()));
    }

    @GetMapping("/zona-domicilio")
    @Operation(summary = "Listar valores de ZonaDomicilioEnum")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores do enum retornados com sucesso",
                    content = @Content(schema = @Schema(implementation = EnumInfoResponse.class)))
    })
    public ResponseEntity<EnumInfoResponse> listarZonaDomicilio() {
        return ResponseEntity.ok(converterEnum(ZonaDomicilioEnum.class, ZonaDomicilioEnum.values()));
    }
}
