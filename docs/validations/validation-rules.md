# Regras de Validação - Front-end

Este documento contém todas as regras de validação das classes Request e Embeddable do projeto UPSaude-back.

## Índice

- [Ação Promoção e Prevenção](#acao-promocao-e-prevencao)
- [Agendamento](#agendamento)
- [Alergias do Paciente](#alergias-do-paciente)
- [Alergias Paciente Simplificado](#alergias-paciente-simplificado)
- [Alergias](#alergias)
- [Atendimento](#atendimento)
- [AtividadeProfissional](#atividadeprofissional)
- [CatalogoExames](#catalogoexames)
- [CatalogoProcedimentos](#catalogoprocedimentos)
- [Check-in Atendimento](#check-in-atendimento)
- [CID Doenças](#cid-doencas)
- [Cidades](#cidades)
- [Cirurgia](#cirurgia)
- [ConfiguracaoEstabelecimento](#configuracaoestabelecimento)
- [ConselhosProfissionais](#conselhosprofissionais)
- [ConsultaPreNatal](#consultaprenatal)
- [ConsultaPuericultura](#consultapuericultura)
- [Consultas](#consultas)
- [ControlePonto](#controleponto)
- [Convênio](#convenio)
- [Cuidados de Enfermagem](#cuidados-de-enfermagem)
- [DadosClinicosBasicos](#dadosclinicosbasicos)
- [DadosSociodemograficos](#dadossociodemograficos)
- [DeficienciasPaciente](#deficienciaspaciente)
- [Deficiências Paciente Simplificado](#deficiencias-paciente-simplificado)
- [Deficiencias](#deficiencias)
- [Departamentos](#departamentos)
- [DispensacoesMedicamentos](#dispensacoesmedicamentos)
- [Doenças do Paciente](#doencas-do-paciente)
- [Doenças Paciente Simplificado](#doencas-paciente-simplificado)
- [Doenças](#doencas)
- [Educação em Saúde](#educacao-em-saúde)
- [Endereço](#endereco)
- [EnumItem](#enumitem)
- [Enums](#enums)
- [EquipamentosEstabelecimento](#equipamentosestabelecimento)
- [Equipamentos](#equipamentos)
- [Equipe Cirúrgica](#equipe-cirúrgica)
- [Equipe de Saúde](#equipe-de-saúde)
- [EscalaTrabalho](#escalatrabalho)
- [Especialidades Médicas](#especialidades-medicas)
- [Estabelecimentos](#estabelecimentos)
- [Estados](#estados)
- [EstoquesVacina](#estoquesvacina)
- [Exames](#exames)
- [FabricantesEquipamento](#fabricantesequipamento)
- [FabricantesMedicamento](#fabricantesmedicamento)
- [Fabricantes de Vacina](#fabricantes-de-vacina)
- [Falta](#falta)
- [FilaEspera](#filaespera)
- [HistoricoClinico](#historicoclinico)
- [HistoricoHabilitacaoProfissional](#historicohabilitacaoprofissional)
- [InfraestruturaEstabelecimento](#infraestruturaestabelecimento)
- [IntegracaoGov](#integracaogov)
- [LGPDConsentimento](#lgpdconsentimento)
- [Login](#login)
- [Logs de Auditoria](#logs-de-auditoria)
- [MedicacaoPaciente](#medicacaopaciente)
- [Medicação Paciente Simplificado](#medicacao-paciente-simplificado)
- [Medicação](#medicacao)
- [MedicacoesContinuasPaciente](#medicacoescontinuaspaciente)
- [MedicacoesContinuas](#medicacoescontinuas)
- [MedicaoClinica](#medicaoclinica)
- [MedicoEstabelecimento](#medicoestabelecimento)
- [Médicos](#medicos)
- [MovimentacoesEstoque](#movimentacoesestoque)
- [Notificação](#notificacao)
- [Pacientes](#pacientes)
- [PerfisUsuarios](#perfisusuarios)
- [Permissoes](#permissoes)
- [PlanejamentoFamiliar](#planejamentofamiliar)
- [Plantao](#plantao)
- [PreNatal](#prenatal)
- [Procedimento Cirúrgico](#procedimento-cirúrgico)
- [Procedimentos Odontológicos](#procedimentos-odontológicos)
- [Profissionais de Saúde](#profissionais-de-saúde)
- [ProfissionalEstabelecimento](#profissionalestabelecimento)
- [Prontuários](#prontuários)
- [Puericultura](#puericultura)
- [Receitas Médicas](#receitas-medicas)
- [RelatorioEstatisticas](#relatorioestatisticas)
- [ResponsavelLegal](#responsavellegal)
- [ServicosEstabelecimento](#servicosestabelecimento)
- [TemplateNotificacao](#templatenotificacao)
- [Tenant](#tenant)
- [Tratamentos Odontológicos](#tratamentos-odontológicos)
- [TratamentosProcedimentos](#tratamentosprocedimentos)
- [Usuário](#usuário)
- [UsuarioEstabelecimento](#usuarioestabelecimento)
- [Usuários do Sistema](#usuários-do-sistema)
- [Vacinações](#vacinacoes)
- [Vacinas](#vacinas)
- [VinculoProfissionalEquipe](#vinculoprofissionalequipe)
- [Visitas Domiciliares](#visitas-domiciliares)
- [AcompanhamentoDoencaPaciente](#acompanhamentodoencapaciente)
- [AnamneseAtendimento](#anamneseatendimento)
- [AnamneseConsulta](#anamneseconsulta)
- [AtestadoConsulta](#atestadoconsulta)
- [CalendarioVacinal](#calendariovacinal)
- [ClassificacaoAlergia](#classificacaoalergia)
- [ClassificacaoDoenca](#classificacaodoenca)
- [ClassificacaoEspecialidadeMedica](#classificacaoespecialidademedica)
- [ClassificacaoMedicamento](#classificacaomedicamento)
- [ClassificacaoRiscoAtendimento](#classificacaoriscoatendimento)
- [CoberturaConvenio](#coberturaconvenio)
- [ComposicaoVacina](#composicaovacina)
- [ConservacaoArmazenamentoMedicamento](#conservacaoarmazenamentomedicamento)
- [ConservacaoVacina](#conservacaovacina)
- [ContatoConvenio](#contatoconvenio)
- [ContatoMedico](#contatomedico)
- [ContraindicacoesPrecaucoesMedicamento](#contraindicacoesprecaucoesmedicamento)
- [ContraindicacoesVacina](#contraindicacoesvacina)
- [DadosPessoaisMedico](#dadospessoaismedico)
- [DiagnosticoAlergiaPaciente](#diagnosticoalergiapaciente)
- [DiagnosticoAtendimento](#diagnosticoatendimento)
- [DiagnosticoConsulta](#diagnosticoconsulta)
- [DiagnosticoDoencaPaciente](#diagnosticodoencapaciente)
- [DosagemAdministracaoMedicamento](#dosagemadministracaomedicamento)
- [EficaciaVacina](#eficaciavacina)
- [EncaminhamentoConsulta](#encaminhamentoconsulta)
- [EpidemiologiaDoenca](#epidemiologiadoenca)
- [EsquemaVacinal](#esquemavacinal)
- [ExamesSolicitadosConsulta](#examessolicitadosconsulta)
- [FormacaoMedico](#formacaomedico)
- [HistoricoReacoesAlergiaPaciente](#historicoreacoesalergiapaciente)
- [IdadeAplicacaoVacina](#idadeaplicacaovacina)
- [IdentificacaoMedicamento](#identificacaomedicamento)
- [InformacoesAtendimento](#informacoesatendimento)
- [InformacoesConsulta](#informacoesconsulta)
- [InformacoesFinanceirasConvenio](#informacoesfinanceirasconvenio)
- [IntegracaoGovernamentalConvenio](#integracaogovernamentalconvenio)
- [IntegracaoGovernamentalVacina](#integracaogovernamentalvacina)
- [PrescricaoConsulta](#prescricaoconsulta)
- [PrevencaoTratamentoAlergia](#prevencaotratamentoalergia)
- [ProcedimentosRealizadosAtendimento](#procedimentosrealizadosatendimento)
- [ReacoesAdversasVacina](#reacoesadversasvacina)
- [ReacoesAlergia](#reacoesalergia)
- [RegistroANSConvenio](#registroansconvenio)
- [RegistroControleMedicamento](#registrocontrolemedicamento)
- [RegistroProfissionalMedico](#registroprofissionalmedico)
- [SintomasDoenca](#sintomasdoenca)
- [TratamentoAtualDoencaPaciente](#tratamentoatualdoencapaciente)
- [TratamentoPadraoDoenca](#tratamentopadraodoenca)

## Ação Promoção e Prevenção

### Classe Request: AcaoPromocaoPrevencaoRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| profissionalResponsavel | UUID | Sim | @NotNull | Não | — | Profissional responsavel |
| equipeSaude | UUID | Sim | @NotNull | Não | — | Equipe saude |
| tipoAcao | TipoAcaoPromocaoSaudeEnum | Sim | @NotNull, @NotNull | Sim | — | Tipo acao |
| nome | String | Sim | @NotNull, @NotNull, @NotBlank, @Size(max=255) | Não | — | Nome |
| descricao | String | Sim | @NotNull, @NotNull, @NotBlank, @Size(max=255) | Não | — | Descricao |
| objetivos | String | Sim | @NotNull, @NotNull, @NotBlank, @Size(max=255) | Não | — | Objetivos |
| justificativa | String | Sim | @NotNull, @NotNull, @NotBlank, @Size(max=255) | Não | — | Justificativa |
| metodologia | String | Sim | @NotNull, @NotNull, @NotBlank, @Size(max=255) | Não | — | Metodologia |
| dataInicio | LocalDate | Sim | @NotNull, @NotBlank, @Size(max=255), @NotNull | Não | — | Data inicio |
| dataFim | LocalDate | Sim | @NotNull, @NotBlank, @Size(max=255), @NotNull | Não | — | Data fim |
| periodicidade | String | Sim | @NotBlank, @Size(max=255), @NotNull, @Size(max=100) | Não | — | Periodicidade |
| local | String | Sim | @Size(max=255), @NotNull, @Size(max=100), @Size(max=255) | Não | — | Local |
| abrangenciaTerritorial | String | Sim | @NotNull, @Size(max=100), @Size(max=255) | Não | — | Abrangencia territorial |
| publicoAlvo | String | Sim | @NotNull, @Size(max=100), @Size(max=255), @Size(max=255) | Não | — | Publico alvo |
| populacaoEstimada | Integer | Não (Validar apenas se preenchido) | @Size(max=100), @Size(max=255), @Size(max=255), @Min(0) | Não | — | Populacao estimada |
| criteriosInclusao | String | Não (Validar apenas se preenchido) | @Size(max=100), @Size(max=255), @Size(max=255), @Min(0) | Não | — | Criterios inclusao |
| metaCobertura | Integer | Não (Validar apenas se preenchido) | @Size(max=100), @Size(max=255), @Size(max=255), @Min(0) | Não | — | Meta cobertura |
| metaAtendimentos | Integer | Não (Validar apenas se preenchido) | @Size(max=255), @Size(max=255), @Min(0), @Min(0) | Não | — | Meta atendimentos |
| indicadoresAcompanhamento | String | Não (Validar apenas se preenchido) | @Size(max=255), @Size(max=255), @Min(0), @Min(0) | Não | — | Indicadores acompanhamento |
| numeroAtendimentosRealizados | Integer | Não (Validar apenas se preenchido) | @Size(max=255), @Min(0), @Min(0), @Min(0) | Não | — | Numero atendimentos realizados |
| coberturaAlcancada | Integer | Não (Validar apenas se preenchido) | @Size(max=255), @Min(0), @Min(0), @Min(0) | Não | — | Cobertura alcancada |
| resultadosAlcancados | String | Não (Validar apenas se preenchido) | @Min(0), @Min(0), @Min(0) | Não | — | Resultados alcancados |
| dificuldadesEncontradas | String | Não (Validar apenas se preenchido) | @Min(0), @Min(0), @Min(0) | Não | — | Dificuldades encontradas |
| licoesAprendidas | String | Não (Validar apenas se preenchido) | @Min(0), @Min(0), @Min(0) | Não | — | Licoes aprendidas |
| recursosNecessarios | String | Não (Validar apenas se preenchido) | @Min(0), @Min(0) | Não | — | Recursos necessarios |
| recursosUtilizados | String | Não (Validar apenas se preenchido) | @Min(0), @Min(0) | Não | — | Recursos utilizados |
| parcerias | String | Não (Validar apenas se preenchido) | @Min(0), @Min(0) | Não | — | Parcerias |
| dataInicioExecucao | LocalDate | Não (Validar apenas se preenchido) | @Min(0), @Min(0) | Não | — | Data inicio execucao |
| dataConclusao | LocalDate | Não (Validar apenas se preenchido) | @Min(0) | Não | — | Data conclusao |
| observacoes | String | Não (Validar apenas se preenchido) | @Min(0) | Não | — | Observacoes |

## Agendamento

### Classe Request: AgendamentoRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| paciente | UUID | Sim | @NotNull | Não | — | Paciente |
| profissional | UUID | Sim | @NotNull | Não | — | Profissional |
| medico | UUID | Sim | @NotNull | Não | — | Medico |
| especialidade | UUID | Sim | @NotNull | Não | — | Especialidade |
| convenio | UUID | Sim | @NotNull | Não | — | Convenio |
| atendimento | UUID | Sim | @NotNull | Não | — | Atendimento |
| agendamentoOriginal | UUID | Sim | @NotNull | Não | — | Agendamento original |
| dataHora | OffsetDateTime | Sim | @NotNull, @NotNull | Não | — | Data hora |
| dataHoraFim | OffsetDateTime | Sim | @NotNull, @NotNull | Não | — | Data hora fim |
| duracaoPrevistaMinutos | Integer | Sim | @NotNull, @NotNull | Não | — | Duracao prevista minutos |
| status | StatusAgendamentoEnum | Sim | @NotNull, @NotNull | Sim | — | Status |
| prioridade | PrioridadeAtendimentoEnum | Sim | @NotNull, @NotNull | Sim | — | Prioridade |
| ehEncaixe | Boolean | Sim | @NotNull, @NotNull | Não | — | Eh encaixe |
| ehRetorno | Boolean | Sim | @NotNull, @NotNull | Não | — | Eh retorno |
| motivoConsulta | String | Sim | @NotNull, @NotNull | Não | — | Motivo consulta |
| observacoesAgendamento | String | Sim | @NotNull, @NotNull | Não | — | Observacoes agendamento |
| observacoesInternas | String | Sim | @NotNull, @NotNull | Não | — | Observacoes internas |
| temConflitoHorario | Boolean | Sim | @NotNull, @NotNull | Não | — | Tem conflito horario |
| sobreposicaoPermitida | Boolean | Sim | @NotNull, @NotNull | Não | — | Sobreposicao permitida |
| justificativaConflito | String | Sim | @NotNull, @NotNull | Não | — | Justificativa conflito |
| dataCancelamento | OffsetDateTime | Sim | @NotNull | Não | — | Data cancelamento |
| canceladoPor | UUID | Sim | @NotNull | Não | — | Cancelado por |
| motivoCancelamento | String | Sim | @NotNull | Não | — | Motivo cancelamento |
| dataReagendamento | OffsetDateTime | Sim | @NotNull | Não | — | Data reagendamento |
| reagendadoPor | UUID | Sim | @NotNull | Não | — | Reagendado por |
| motivoReagendamento | String | Não | — | Não | — | Motivo reagendamento |
| agendadoPor | UUID | Não | — | Não | — | Agendado por |
| confirmadoPor | UUID | Não | — | Não | — | Confirmado por |
| dataConfirmacao | OffsetDateTime | Não | — | Não | — | Data confirmacao |
| dataUltimaAlteracao | OffsetDateTime | Não | — | Não | — | Data ultima alteracao |
| alteradoPor | UUID | Não | — | Não | — | Alterado por |
| notificacaoEnviada24h | Boolean | Não | — | Não | — | Notificacao enviada24h |
| notificacaoEnviada1h | Boolean | Não | — | Não | — | Notificacao enviada1h |
| confirmacaoEnviada | Boolean | Não | — | Não | — | Confirmacao enviada |

## Alergias do Paciente

### Classe Request: AlergiasPacienteRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| paciente | UUID | Sim | @NotNull | Não | — | Paciente |
| alergia | UUID | Sim | @NotNull, @NotNull | Não | — | Alergia |
| diagnostico | DiagnosticoAlergiaPacienteRequest | Sim | @NotNull, @NotNull | Não | — | Diagnostico |
| historicoReacoes | HistoricoReacoesAlergiaPacienteRequest | Sim | @NotNull, @NotNull | Não | — | Historico reacoes |
| observacoes | String | Sim | @NotNull, @NotNull, @Size(max=1000) | Não | — | Observacoes |
| alertaMedico | Boolean | Sim | @NotNull, @NotNull, @Size(max=1000) | Não | — | Alerta medico |

#### DiagnosticoAlergiaPacienteRequest (diagnostico)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| dataDiagnostico | LocalDate | Não | — | Não | — | Data diagnostico |
| dataPrimeiraReacao | LocalDate | Não | — | Não | — | Data primeira reacao |
| severidade | SeveridadeAlergiaEnum | Não | — | Sim | — | Severidade |
| tipoReacaoObservada | TipoReacaoAlergicaEnum | Não | — | Sim | — | Tipo reacao observada |
| metodoDiagnostico | String | Não (Validar apenas se preenchido) | @Size(max=255) | Não | — | Metodo diagnostico |
| localDiagnostico | String | Não (Validar apenas se preenchido) | @Size(max=255), @Size(max=255) | Não | — | Local diagnostico |
| profissionalDiagnostico | String | Não (Validar apenas se preenchido) | @Size(max=255), @Size(max=255), @Size(max=255) | Não | — | Profissional diagnostico |

#### HistoricoReacoesAlergiaPacienteRequest (historicoReacoes)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| dataUltimaReacao | LocalDate | Não | — | Não | — | Data ultima reacao |
| numeroReacoes | Integer | Não | — | Não | — | Numero reacoes |
| tipoUltimaReacao | TipoReacaoAlergicaEnum | Não | — | Sim | — | Tipo ultima reacao |
| severidadeUltimaReacao | SeveridadeAlergiaEnum | Não | — | Sim | — | Severidade ultima reacao |
| reacaoMaisGrave | String | Não | — | Não | — | Reacao mais grave |
| tratamentoUtilizado | String | Não | — | Não | — | Tratamento utilizado |

## Alergias Paciente Simplificado

### Classe Request: AlergiasPacienteSimplificadoRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| paciente | UUID | Sim | @NotNull | Não | — | Paciente |
| tenant | UUID | Sim | @NotNull, @NotNull | Não | — | Tenant |
| alergia | UUID | Sim | @NotNull, @NotNull, @NotNull | Não | — | Alergia |

## Alergias

### Classe Request: AlergiasRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| nome | String | Sim | @NotBlank, @Size(max=255) | Não | — | Nome |
| nomeCientifico | String | Sim | @NotBlank, @Size(max=255), @Size(max=255) | Não | — | Nome cientifico |
| codigoInterno | String | Sim | @NotBlank, @Size(max=255), @Size(max=255), @Size(max=50) | Não | — | Codigo interno |
| classificacao | ClassificacaoAlergiaRequest | Sim | @NotBlank, @Size(max=255), @Size(max=255), @Size(max=50) | Não | — | Classificacao |
| reacoes | ReacoesAlergiaRequest | Sim | @NotBlank, @Size(max=255), @Size(max=255), @Size(max=50) | Não | — | Reacoes |
| prevencaoTratamento | PrevencaoTratamentoAlergiaRequest | Não (Validar apenas se preenchido) | @Size(max=255), @Size(max=50) | Não | — | Prevencao tratamento |
| descricao | String | Não (Validar apenas se preenchido) | @Size(max=50), @Size(max=1000) | Não | — | Descricao |
| substanciasRelacionadas | String | Não (Validar apenas se preenchido) | @Size(max=1000), @Size(max=1000) | Não | — | Substancias relacionadas |
| observacoes | String | Não (Validar apenas se preenchido) | @Size(max=1000), @Size(max=1000), @Size(max=1000) | Não | — | Observacoes |

#### ClassificacaoAlergiaRequest (classificacao)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| tipoAlergia | TipoAlergiaEnum | Não | — | Sim | — | Tipo alergia |
| categoria | String | Não (Validar apenas se preenchido) | @Size(max=100) | Não | — | Categoria |
| subcategoria | String | Não (Validar apenas se preenchido) | @Size(max=100), @Size(max=100) | Não | — | Subcategoria |
| codigoCid | String | Não (Validar apenas se preenchido) | @Size(max=100), @Size(max=100), @Size(max=50) | Não | — | Codigo cid |

#### ReacoesAlergiaRequest (reacoes)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| tipoReacaoPrincipal | TipoReacaoAlergicaEnum | Não | — | Sim | — | Tipo reacao principal |
| reacoesComuns | String | Não | — | Não | — | Reacoes comuns |
| reacoesGraves | String | Não | — | Não | — | Reacoes graves |
| sintomas | String | Não | — | Não | — | Sintomas |
| tempoAposExposicao | String | Não (Validar apenas se preenchido) | @Size(max=100) | Não | — | Tempo apos exposicao |

#### PrevencaoTratamentoAlergiaRequest (prevencaoTratamento)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| medidasPrevencao | String | Não | — | Não | — | Medidas prevencao |
| tratamentoImediato | String | Não | — | Não | — | Tratamento imediato |
| medicamentosEvitar | String | Não | — | Não | — | Medicamentos evitar |
| alimentosEvitar | String | Não | — | Não | — | Alimentos evitar |
| substanciasEvitar | String | Não | — | Não | — | Substancias evitar |
| antihistaminicoRecomendado | String | Sim | @NotNull, @Size(max=255) | Não | — | Antihistaminico recomendado |

## Atendimento

### Classe Request: AtendimentoRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| paciente | UUID | Sim | @NotNull | Não | — | Paciente |
| profissional | UUID | Sim | @NotNull, @NotNull | Não | — | Profissional |
| especialidade | UUID | Sim | @NotNull, @NotNull | Não | — | Especialidade |
| equipeSaude | UUID | Sim | @NotNull, @NotNull | Não | — | Equipe saude |
| convenio | UUID | Sim | @NotNull, @NotNull | Não | — | Convenio |
| informacoes | InformacoesAtendimentoRequest | Sim | @NotNull, @NotNull | Não | — | Informacoes |
| anamnese | AnamneseAtendimentoRequest | Sim | @NotNull, @NotNull | Não | — | Anamnese |
| diagnostico | DiagnosticoAtendimentoRequest | Sim | @NotNull | Não | — | Diagnostico |
| procedimentosRealizados | ProcedimentosRealizadosAtendimentoRequest | Não | — | Não | — | Procedimentos realizados |
| classificacaoRisco | ClassificacaoRiscoAtendimentoRequest | Não | — | Não | — | Classificacao risco |
| cidPrincipal | UUID | Não | — | Não | — | Cid principal |
| anotacoes | String | Não (Validar apenas se preenchido) | @Size(max=1000) | Não | — | Anotacoes |
| observacoesInternas | String | Não (Validar apenas se preenchido) | @Size(max=1000), @Size(max=1000) | Não | — | Observacoes internas |

#### InformacoesAtendimentoRequest (informacoes)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| dataHora | OffsetDateTime | Sim | @NotNull | Não | — | Data hora |
| dataAgendamento | OffsetDateTime | Sim | @NotNull | Não | — | Data agendamento |
| dataInicio | OffsetDateTime | Sim | @NotNull | Não | — | Data inicio |
| dataFim | OffsetDateTime | Sim | @NotNull | Não | — | Data fim |
| duracaoMinutos | Integer | Sim | @NotNull | Não | — | Duracao minutos |
| duracaoRealMinutos | Integer | Sim | @NotNull | Não | — | Duracao real minutos |
| tipoAtendimento | TipoAtendimentoEnum | Sim | @NotNull | Sim | — | Tipo atendimento |
| motivo | String | Sim | @NotNull | Não | — | Motivo |
| localAtendimento | String | Sim | @NotNull, @Size(max=255) | Não | — | Local atendimento |
| numeroAtendimento | String | Sim | @NotNull, @Size(max=255), @Size(max=50) | Não | — | Numero atendimento |

#### AnamneseAtendimentoRequest (anamnese)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| queixaPrincipal | String | Não | — | Não | — | Queixa principal |
| historiaDoencaAtual | String | Não | — | Não | — | Historia doenca atual |
| antecedentesRelevantes | String | Não | — | Não | — | Antecedentes relevantes |
| medicamentosUso | String | Não | — | Não | — | Medicamentos uso |
| alergiasConhecidas | String | Não | — | Não | — | Alergias conhecidas |
| exameFisico | String | Não | — | Não | — | Exame fisico |
| sinaisVitais | String | Não | — | Não | — | Sinais vitais |
| observacoesAnamnese | String | Não | — | Não | — | Observacoes anamnese |

#### DiagnosticoAtendimentoRequest (diagnostico)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| diagnostico | String | Não | — | Não | — | Diagnostico |
| diagnosticosSecundarios | String | Não | — | Não | — | Diagnosticos secundarios |
| hipoteseDiagnostica | String | Não | — | Não | — | Hipotese diagnostica |
| conduta | String | Não | — | Não | — | Conduta |
| evolucao | String | Não | — | Não | — | Evolucao |

#### ProcedimentosRealizadosAtendimentoRequest (procedimentosRealizados)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| procedimentosRealizados | String | Não | — | Não | — | Procedimentos realizados |
| examesSolicitados | String | Não | — | Não | — | Exames solicitados |
| medicamentosPrescritos | String | Não | — | Não | — | Medicamentos prescritos |
| orientacoes | String | Não | — | Não | — | Orientacoes |
| encaminhamentos | String | Não | — | Não | — | Encaminhamentos |

#### ClassificacaoRiscoAtendimentoRequest (classificacaoRisco)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| classificacaoRisco | String | Não (Validar apenas se preenchido) | @Size(max=50) | Não | — | Classificacao risco |
| prioridade | String | Não (Validar apenas se preenchido) | @Size(max=50), @Size(max=50) | Não | — | Prioridade |
| gravidade | String | Não (Validar apenas se preenchido) | @Size(max=50), @Size(max=50), @Size(max=50) | Não | — | Gravidade |

## AtividadeProfissional

### Classe Request: AtividadeProfissionalRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| profissional | UUID | Não | — | Não | — | Profissional |
| medico | UUID | Não | — | Não | — | Medico |
| paciente | UUID | Não | — | Não | — | Paciente |
| atendimento | UUID | Não | — | Não | — | Atendimento |
| cirurgia | UUID | Não | — | Não | — | Cirurgia |
| dataHora | OffsetDateTime | Não | — | Não | — | Data hora |
| dataAtividade | LocalDate | Não | — | Não | — | Data atividade |
| tipoAtividade | TipoAtividadeProfissionalEnum | Não | — | Sim | — | Tipo atividade |
| descricao | String | Não | — | Não | — | Descricao |
| duracaoMinutos | Integer | Não | — | Não | — | Duracao minutos |
| dataHoraInicio | OffsetDateTime | Não | — | Não | — | Data hora inicio |
| dataHoraFim | OffsetDateTime | Não | — | Não | — | Data hora fim |
| localRealizacao | String | Não | — | Não | — | Local realizacao |
| setor | String | Não | — | Não | — | Setor |
| quantidadeAtendimentos | Integer | Não | — | Não | — | Quantidade atendimentos |
| observacoes | String | Não | — | Não | — | Observacoes |
| observacoesInternas | String | Não | — | Não | — | Observacoes internas |

## CatalogoExames

### Classe Request: CatalogoExamesRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| tipoExame | TipoExameEnum | Não | — | Sim | — | Tipo exame |
| nome | String | Não | — | Não | — | Nome |
| codigo | String | Não | — | Não | — | Codigo |
| descricao | String | Não | — | Não | — | Descricao |
| instrucoesPreparacao | String | Não | — | Não | — | Instrucoes preparacao |
| prazoResultadoDias | Integer | Não | — | Não | — | Prazo resultado dias |
| observacoes | String | Não | — | Não | — | Observacoes |

## CatalogoProcedimentos

### Classe Request: CatalogoProcedimentosRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| tipoProcedimento | TipoProcedimentoEnum | Não | — | Sim | — | Tipo procedimento |
| nome | String | Não | — | Não | — | Nome |
| codigo | String | Não | — | Não | — | Codigo |
| descricao | String | Não | — | Não | — | Descricao |
| duracaoMinutos | Integer | Não | — | Não | — | Duracao minutos |
| custoSugerido | BigDecimal | Não | — | Não | — | Custo sugerido |
| profissionalRequerido | String | Não | — | Não | — | Profissional requerido |
| instrucoesPreparacao | String | Não | — | Não | — | Instrucoes preparacao |
| observacoes | String | Não | — | Não | — | Observacoes |

## Check-in Atendimento

### Classe Request: CheckInAtendimentoRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| agendamento | UUID | Sim | @NotNull | Não | — | Agendamento |
| atendimento | UUID | Sim | @NotNull | Não | — | Atendimento |
| paciente | UUID | Sim | @NotNull, @NotNull | Não | — | Paciente |
| dataCheckin | OffsetDateTime | Sim | @NotNull, @NotNull, @NotNull | Não | — | Data checkin |
| dataCheckout | OffsetDateTime | Sim | @NotNull, @NotNull, @NotNull | Não | — | Data checkout |
| tipoCheckin | String | Sim | @NotNull, @NotNull, @NotNull, @NotNull, @Size(max=50) | Não | — | Tipo checkin |
| ehPresencial | Boolean | Sim | @NotNull, @NotNull, @NotNull, @NotNull, @Size(max=50) | Não | — | Eh presencial |
| horarioPrevisto | OffsetDateTime | Sim | @NotNull, @NotNull, @NotNull, @NotNull, @Size(max=50) | Não | — | Horario previsto |
| tempoAntecedenciaMinutos | Integer | Sim | @NotNull, @NotNull, @NotNull, @Size(max=50) | Não | — | Tempo antecedencia minutos |
| estaAtrasado | Boolean | Sim | @NotNull, @NotNull, @NotNull, @Size(max=50) | Não | — | Esta atrasado |
| tempoAtrasoMinutos | Integer | Sim | @NotNull, @NotNull, @NotNull, @Size(max=50) | Não | — | Tempo atraso minutos |
| latitude | Double | Sim | @NotNull, @NotNull, @NotNull, @Size(max=50) | Não | — | Latitude |
| longitude | Double | Sim | @NotNull, @NotNull, @NotNull, @Size(max=50) | Não | — | Longitude |
| enderecoIp | String | Sim | @NotNull, @NotNull, @Size(max=50) | Não | — | Endereco ip |
| userAgent | String | Sim | @NotNull, @NotNull, @Size(max=50) | Não | — | User agent |
| observacoes | String | Sim | @NotNull, @NotNull, @Size(max=50) | Não | — | Observacoes |
| acompanhantePresente | Boolean | Sim | @NotNull, @Size(max=50) | Não | — | Acompanhante presente |
| numeroAcompanhantes | Integer | Sim | @NotNull, @Size(max=50) | Não | — | Numero acompanhantes |
| checkinRealizadoPor | UUID | Sim | @NotNull, @Size(max=50) | Não | — | Checkin realizado por |
| checkoutRealizadoPor | UUID | Não (Validar apenas se preenchido) | @Size(max=50) | Não | — | Checkout realizado por |

## CID Doenças

### Classe Request: CidDoencasRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| codigo | String | Não | — | Não | — | Codigo |
| descricao | String | Não | — | Não | — | Descricao |
| descricaoAbreviada | String | Não | — | Não | — | Descricao abreviada |
| categoria | String | Não | — | Não | — | Categoria |
| subcategoria | String | Não | — | Não | — | Subcategoria |
| sexoRestricao | String | Não | — | Não | — | Sexo restricao |
| idadeMinima | Integer | Não | — | Não | — | Idade minima |
| idadeMaxima | Integer | Não | — | Não | — | Idade maxima |

## Cidades

### Classe Request: CidadesRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| nome | String | Não | — | Não | — | Nome |
| codigoIbge | String | Não | — | Não | — | Codigo ibge |
| latitude | Double | Não | — | Não | — | Latitude |
| longitude | Double | Não | — | Não | — | Longitude |
| estado | UUID | Não | — | Não | — | Estado |

## Cirurgia

### Classe Request: CirurgiaRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| paciente | UUID | Sim | @NotNull | Não | — | Paciente |
| cirurgiaoPrincipal | UUID | Sim | @NotNull, @NotNull | Não | — | Cirurgiao principal |
| medicoCirurgiao | UUID | Sim | @NotNull, @NotNull | Não | — | Medico cirurgiao |
| especialidade | UUID | Sim | @NotNull, @NotNull | Não | — | Especialidade |
| convenio | UUID | Sim | @NotNull, @NotNull | Não | — | Convenio |
| descricao | String | Sim | @NotNull, @NotNull, @NotBlank | Não | — | Descricao |
| codigoProcedimento | String | Sim | @NotNull, @NotNull, @NotBlank, @Size(max=50) | Não | — | Codigo procedimento |
| dataHoraPrevista | OffsetDateTime | Sim | @NotNull, @NotBlank, @Size(max=50), @NotNull | Não | — | Data hora prevista |
| dataHoraInicio | OffsetDateTime | Sim | @NotNull, @NotBlank, @Size(max=50), @NotNull | Não | — | Data hora inicio |
| dataHoraFim | OffsetDateTime | Sim | @NotBlank, @Size(max=50), @NotNull | Não | — | Data hora fim |
| duracaoPrevistaMinutos | Integer | Sim | @NotBlank, @Size(max=50), @NotNull | Não | — | Duracao prevista minutos |
| duracaoRealMinutos | Integer | Sim | @NotBlank, @Size(max=50), @NotNull | Não | — | Duracao real minutos |
| salaCirurgica | String | Sim | @NotBlank, @Size(max=50), @NotNull, @Size(max=100) | Não | — | Sala cirurgica |
| leitoCentroCirurgico | String | Sim | @Size(max=50), @NotNull, @Size(max=100), @Size(max=100) | Não | — | Leito centro cirurgico |
| status | StatusCirurgiaEnum | Sim | @NotNull, @Size(max=100), @Size(max=100), @NotNull | Sim | — | Status |
| valorCirurgia | BigDecimal | Sim | @NotNull, @Size(max=100), @Size(max=100), @NotNull | Não | — | Valor cirurgia |
| valorMaterial | BigDecimal | Sim | @Size(max=100), @Size(max=100), @NotNull | Não | — | Valor material |
| valorTotal | BigDecimal | Sim | @Size(max=100), @Size(max=100), @NotNull | Não | — | Valor total |
| observacoesPreOperatorio | String | Sim | @Size(max=100), @Size(max=100), @NotNull | Não | — | Observacoes pre operatorio |
| observacoesPosOperatorio | String | Sim | @Size(max=100), @Size(max=100), @NotNull | Não | — | Observacoes pos operatorio |
| observacoes | String | Sim | @Size(max=100), @Size(max=100), @NotNull | Não | — | Observacoes |
| observacoesInternas | String | Sim | @Size(max=100), @Size(max=100), @NotNull | Não | — | Observacoes internas |

## ConfiguracaoEstabelecimento

### Classe Request: ConfiguracaoEstabelecimentoRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| estabelecimento | UUID | Não | — | Não | — | Estabelecimento |
| duracaoPadraoAtendimentoMinutos | Integer | Não | — | Não | — | Duracao padrao atendimento minutos |
| intervaloMinimoEntreAtendimentosMinutos | Integer | Não | — | Não | — | Intervalo minimo entre atendimentos minutos |
| permiteAgendamentoOnline | Boolean | Não | — | Não | — | Permite agendamento online |
| antecendenciaMaximaAgendamentoDias | Integer | Não | — | Não | — | Antecendencia maxima agendamento dias |
| antecendenciaMinimaAgendamentoHoras | Integer | Não | — | Não | — | Antecendencia minima agendamento horas |
| permiteReagendamento | Boolean | Não | — | Não | — | Permite reagendamento |
| prazoReagendamentoHoras | Integer | Não | — | Não | — | Prazo reagendamento horas |
| permiteCancelamentoOnline | Boolean | Não | — | Não | — | Permite cancelamento online |
| prazoCancelamentoHoras | Integer | Não | — | Não | — | Prazo cancelamento horas |
| permiteEncaixe | Boolean | Não | — | Não | — | Permite encaixe |
| maximoEncaixesPorDia | Integer | Não | — | Não | — | Maximo encaixes por dia |
| permiteFilaEspera | Boolean | Não | — | Não | — | Permite fila espera |
| tamanhoMaximoFilaEspera | Integer | Não | — | Não | — | Tamanho maximo fila espera |
| permitePriorizacao | Boolean | Não | — | Não | — | Permite priorizacao |
| verificaConflitoHorario | Boolean | Não | — | Não | — | Verifica conflito horario |
| permiteSobreposicaoAtendimentos | Boolean | Não | — | Não | — | Permite sobreposicao atendimentos |
| horarioFuncionamentoInicio | LocalTime | Não | — | Não | — | Horario funcionamento inicio |
| horarioFuncionamentoFim | LocalTime | Não | — | Não | — | Horario funcionamento fim |
| horarioAlmocoInicio | LocalTime | Não | — | Não | — | Horario almoco inicio |
| horarioAlmocoFim | LocalTime | Não | — | Não | — | Horario almoco fim |
| funcionaSabado | Boolean | Não | — | Não | — | Funciona sabado |
| horarioSabadoInicio | LocalTime | Não | — | Não | — | Horario sabado inicio |
| horarioSabadoFim | LocalTime | Não | — | Não | — | Horario sabado fim |
| funcionaDomingo | Boolean | Não | — | Não | — | Funciona domingo |
| horarioDomingoInicio | LocalTime | Não | — | Não | — | Horario domingo inicio |
| horarioDomingoFim | LocalTime | Não | — | Não | — | Horario domingo fim |
| funcionaFeriados | Boolean | Não | — | Não | — | Funciona feriados |
| enviaNotificacaoEmail | Boolean | Não | — | Não | email | Envia notificacao email |
| enviaNotificacaoSms | Boolean | Não | — | Não | — | Envia notificacao sms |
| enviaNotificacaoWhatsapp | Boolean | Não | — | Não | (00) 00000-0000 | Envia notificacao whatsapp |
| enviaLembrete24h | Boolean | Não | — | Não | — | Envia lembrete24h |
| enviaLembrete1h | Boolean | Não | — | Não | — | Envia lembrete1h |
| enviaConfirmacaoAgendamento | Boolean | Não | — | Não | — | Envia confirmacao agendamento |
| enviaNotificacaoCancelamento | Boolean | Não | — | Não | — | Envia notificacao cancelamento |
| enviaNotificacaoReagendamento | Boolean | Não | — | Não | — | Envia notificacao reagendamento |
| enviaNotificacaoResultadoExame | Boolean | Não | — | Não | — | Envia notificacao resultado exame |
| enviaAvisoFalta | Boolean | Não | — | Não | — | Envia aviso falta |
| tempoEsperaAvisoFaltaMinutos | Integer | Não | — | Não | — | Tempo espera aviso falta minutos |
| enviaNotificacaoFilaEspera | Boolean | Não | — | Não | — | Envia notificacao fila espera |
| servidorSmtp | String | Não | — | Não | — | Servidor smtp |
| portaSmtp | Integer | Não | — | Não | — | Porta smtp |
| emailRemetente | String | Não | — | Não | email | Email remetente |
| nomeRemetente | String | Não | — | Não | — | Nome remetente |
| usuarioSmtp | String | Não | — | Não | — | Usuario smtp |
| senhaSmtp | String | Não | — | Não | — | Senha smtp |
| sslSmtp | Boolean | Não | — | Não | — | Ssl smtp |
| tlsSmtp | Boolean | Não | — | Não | — | Tls smtp |
| provedorSms | String | Não | — | Não | — | Provedor sms |
| apiKeySms | String | Não | — | Não | — | Api key sms |
| apiSecretSms | String | Não | — | Não | — | Api secret sms |
| telefoneRemetenteSms | String | Não | — | Não | (00) 00000-0000 | Telefone remetente sms |
| provedorWhatsapp | String | Não | — | Não | (00) 00000-0000 | Provedor whatsapp |
| apiKeyWhatsapp | String | Não | — | Não | (00) 00000-0000 | Api key whatsapp |
| apiSecretWhatsapp | String | Não | — | Não | (00) 00000-0000 | Api secret whatsapp |
| telefoneRemetenteWhatsapp | String | Não | — | Não | (00) 00000-0000 | Telefone remetente whatsapp |
| webhookUrlWhatsapp | String | Não | — | Não | (00) 00000-0000 | Webhook url whatsapp |
| permiteCheckinOnline | Boolean | Não | — | Não | — | Permite checkin online |
| tempoAntecipacaoCheckinMinutos | Integer | Não | — | Não | — | Tempo antecipacao checkin minutos |
| registraPresencaAutomatica | Boolean | Não | — | Não | — | Registra presenca automatica |
| tempoToleranciaAtrasoMinutos | Integer | Não | — | Não | — | Tempo tolerancia atraso minutos |
| cancelaAutomaticoAposAtraso | Boolean | Não | — | Não | — | Cancela automatico apos atraso |
| tempoCancelamentoAutomaticoMinutos | Integer | Não | — | Não | — | Tempo cancelamento automatico minutos |
| registraAuditoriaCompleta | Boolean | Não | — | Não | — | Registra auditoria completa |
| retencaoDadosMeses | Integer | Não | — | Não | — | Retencao dados meses |
| exigeJustificativaCancelamento | Boolean | Não | — | Não | — | Exige justificativa cancelamento |
| exigeJustificativaReagendamento | Boolean | Não | — | Não | — | Exige justificativa reagendamento |
| permiteAcessoPacientePortal | Boolean | Não | — | Não | — | Permite acesso paciente portal |
| permiteAgendamentoProfissional | Boolean | Não | — | Não | — | Permite agendamento profissional |
| permiteCancelamentoProfissional | Boolean | Não | — | Não | — | Permite cancelamento profissional |
| geraRelatorioProdutividade | Boolean | Não | — | Não | — | Gera relatorio produtividade |
| geraRelatorioNaoComparecimento | Boolean | Não | — | Não | — | Gera relatorio nao comparecimento |
| geraRelatorioEstatisticas | Boolean | Não | — | Não | — | Gera relatorio estatisticas |
| mantemHistoricoCompleto | Boolean | Não | — | Não | — | Mantem historico completo |
| compartilhaHistoricoEntreEstabelecimentos | Boolean | Não | — | Não | — | Compartilha historico entre estabelecimentos |
| permiteVisualizacaoHistoricoPaciente | Boolean | Não | — | Não | — | Permite visualizacao historico paciente |
| isolamentoDadosEstabelecimento | Boolean | Não | — | Não | — | Isolamento dados estabelecimento |
| compartilhaRecursosTenant | Boolean | Não | — | Não | — | Compartilha recursos tenant |
| timezone | String | Não | — | Não | — | Timezone |
| idioma | String | Não | — | Não | — | Idioma |
| formatoData | String | Não | — | Não | — | Formato data |
| formatoHora | String | Não | — | Não | — | Formato hora |
| configuracaoJson | String | Não | — | Não | — | Configuracao json |
| observacoes | String | Não | — | Não | — | Observacoes |

## ConselhosProfissionais

### Classe Request: ConselhosProfissionaisRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| sigla | String | Não | — | Não | — | Sigla |
| nome | String | Não | — | Não | — | Nome |
| descricao | String | Não | — | Não | — | Descricao |

## ConsultaPreNatal

### Classe Request: ConsultaPreNatalRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| preNatal | UUID | Não | — | Não | — | Pre natal |
| profissional | UUID | Não | — | Não | — | Profissional |
| dataConsulta | OffsetDateTime | Não | — | Não | — | Data consulta |
| numeroConsulta | Integer | Não | — | Não | — | Numero consulta |
| idadeGestacionalSemanas | Integer | Não | — | Não | — | Idade gestacional semanas |
| idadeGestacionalDias | Integer | Não | — | Não | — | Idade gestacional dias |
| peso | BigDecimal | Não | — | Não | — | Peso |
| pressaoArterialSistolica | Integer | Não | — | Não | — | Pressao arterial sistolica |
| pressaoArterialDiastolica | Integer | Não | — | Não | — | Pressao arterial diastolica |
| edema | Boolean | Não | — | Não | — | Edema |
| edemaLocalizacao | String | Não | — | Não | — | Edema localizacao |
| alturaUterina | BigDecimal | Não | — | Não | — | Altura uterina |
| bcf | Integer | Não | — | Não | — | Bcf |
| movimentosFetais | Boolean | Não | — | Não | — | Movimentos fetais |
| apresentacaoFetal | String | Não | — | Não | — | Apresentacao fetal |
| posicaoFetal | String | Não | — | Não | — | Posicao fetal |
| queixaPrincipal | String | Não | — | Não | — | Queixa principal |
| nauseasVomitos | Boolean | Não | — | Não | — | Nauseas vomitos |
| sangramento | Boolean | Não | — | Não | — | Sangramento |
| contracaoUterina | Boolean | Não | — | Não | — | Contracao uterina |
| perdaLiquido | Boolean | Não | — | Não | — | Perda liquido |
| cefaleia | Boolean | Não | — | Não | — | Cefaleia |
| epigastralgia | Boolean | Não | — | Não | — | Epigastralgia |
| disturbiosVisuais | Boolean | Não | — | Não | — | Disturbios visuais |
| examesSolicitados | String | Não | — | Não | — | Exames solicitados |
| resultadosExames | String | Não | — | Não | — | Resultados exames |
| suplementacaoAcidoFolico | Boolean | Não | — | Não | — | Suplementacao acido folico |
| suplementacaoSulfatoFerroso | Boolean | Não | — | Não | — | Suplementacao sulfato ferroso |
| medicamentosPrescritos | String | Não | — | Não | — | Medicamentos prescritos |
| orientacoes | String | Não | — | Não | — | Orientacoes |
| encaminhamentos | String | Não | — | Não | — | Encaminhamentos |
| dataProximaConsulta | OffsetDateTime | Não | — | Não | — | Data proxima consulta |
| observacoes | String | Não | — | Não | — | Observacoes |

## ConsultaPuericultura

### Classe Request: ConsultaPuericulturaRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| puericultura | UUID | Não | — | Não | — | Puericultura |
| profissional | UUID | Não | — | Não | — | Profissional |
| dataConsulta | OffsetDateTime | Não | — | Não | — | Data consulta |
| numeroConsulta | Integer | Não | — | Não | — | Numero consulta |
| idadeMeses | Integer | Não | — | Não | — | Idade meses |
| idadeDias | Integer | Não | — | Não | — | Idade dias |
| peso | BigDecimal | Não | — | Não | — | Peso |
| comprimentoEstatura | BigDecimal | Não | — | Não | — | Comprimento estatura |
| perimetroCefalico | BigDecimal | Não | — | Não | — | Perimetro cefalico |
| perimetroToracico | BigDecimal | Não | — | Não | — | Perimetro toracico |
| imc | BigDecimal | Não | — | Não | — | Imc |
| pesoIdade | String | Não | — | Não | — | Peso idade |
| estaturaIdade | String | Não | — | Não | — | Estatura idade |
| pesoEstatura | String | Não | — | Não | — | Peso estatura |
| imcIdade | String | Não | — | Não | — | Imc idade |
| perimetroCefalicoIdade | String | Não | — | Não | — | Perimetro cefalico idade |
| desenvolvimentoAdequado | Boolean | Não | — | Não | — | Desenvolvimento adequado |
| marcosDesenvolvimento | String | Não | — | Não | — | Marcos desenvolvimento |
| alteracoesDesenvolvimento | String | Não | — | Não | — | Alteracoes desenvolvimento |
| tipoAleitamento | String | Não | — | Não | — | Tipo aleitamento |
| alimentacaoComplementar | String | Não | — | Não | — | Alimentacao complementar |
| dificuldadesAlimentacao | String | Não | — | Não | — | Dificuldades alimentacao |
| vacinacaoEmDia | Boolean | Não | — | Não | — | Vacinacao em dia |
| vacinasAtrasadas | String | Não | — | Não | — | Vacinas atrasadas |
| vacinasAplicadasConsulta | String | Não | — | Não | — | Vacinas aplicadas consulta |
| queixaPrincipal | String | Não | — | Não | — | Queixa principal |
| exameFisico | String | Não | — | Não | — | Exame fisico |
| reflexos | String | Não | — | Não | — | Reflexos |
| suplementacaoVitaminaA | Boolean | Não | — | Não | — | Suplementacao vitamina a |
| suplementacaoFerro | Boolean | Não | — | Não | — | Suplementacao ferro |
| suplementacaoVitaminaD | Boolean | Não | — | Não | — | Suplementacao vitamina d |
| orientacoes | String | Não | — | Não | — | Orientacoes |
| medicamentosPrescritos | String | Não | — | Não | — | Medicamentos prescritos |
| encaminhamentos | String | Não | — | Não | — | Encaminhamentos |
| dataProximaConsulta | OffsetDateTime | Não | — | Não | — | Data proxima consulta |
| observacoes | String | Não | — | Não | — | Observacoes |

## Consultas

### Classe Request: ConsultasRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| paciente | UUID | Sim | @NotNull | Não | — | Paciente |
| medico | UUID | Sim | @NotNull | Não | — | Medico |
| profissionalSaude | UUID | Sim | @NotNull | Não | — | Profissional saude |
| especialidade | UUID | Sim | @NotNull | Não | — | Especialidade |
| convenio | UUID | Sim | @NotNull | Não | — | Convenio |
| informacoes | InformacoesConsultaRequest | Sim | @NotNull | Não | — | Informacoes |
| anamnese | AnamneseConsultaRequest | Sim | @NotNull | Não | — | Anamnese |
| diagnostico | DiagnosticoConsultaRequest | Sim | @NotNull | Não | — | Diagnostico |
| prescricao | PrescricaoConsultaRequest | Não | — | Não | — | Prescricao |
| examesSolicitados | ExamesSolicitadosConsultaRequest | Não | — | Não | — | Exames solicitados |
| encaminhamento | EncaminhamentoConsultaRequest | Não | — | Não | — | Encaminhamento |
| atestado | AtestadoConsultaRequest | Não | — | Não | — | Atestado |
| cidPrincipal | UUID | Não | — | Não | — | Cid principal |
| observacoes | String | Não (Validar apenas se preenchido) | @Size(max=1000) | Não | — | Observacoes |
| observacoesInternas | String | Não (Validar apenas se preenchido) | @Size(max=1000), @Size(max=1000) | Não | — | Observacoes internas |

#### InformacoesConsultaRequest (informacoes)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| dataConsulta | OffsetDateTime | Sim | @NotNull | Não | — | Data consulta |
| dataAgendamento | OffsetDateTime | Sim | @NotNull | Não | — | Data agendamento |
| dataInicio | OffsetDateTime | Sim | @NotNull | Não | — | Data inicio |
| dataFim | OffsetDateTime | Sim | @NotNull | Não | — | Data fim |
| duracaoMinutos | Integer | Sim | @NotNull | Não | — | Duracao minutos |
| duracaoRealMinutos | Integer | Sim | @NotNull | Não | — | Duracao real minutos |
| tipoConsulta | TipoConsultaEnum | Sim | @NotNull | Sim | — | Tipo consulta |
| motivo | String | Sim | @NotNull | Não | — | Motivo |
| localAtendimento | String | Sim | @NotNull, @Size(max=255) | Não | — | Local atendimento |
| numeroConsulta | String | Sim | @NotNull, @Size(max=255), @Size(max=50) | Não | — | Numero consulta |

#### AnamneseConsultaRequest (anamnese)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| queixaPrincipal | String | Não | — | Não | — | Queixa principal |
| historiaDoencaAtual | String | Não | — | Não | — | Historia doenca atual |
| antecedentesPessoais | String | Não | — | Não | — | Antecedentes pessoais |
| antecedentesFamiliares | String | Não | — | Não | — | Antecedentes familiares |
| medicamentosUso | String | Não | — | Não | — | Medicamentos uso |
| alergias | String | Não | — | Não | — | Alergias |
| habitosVida | String | Não | — | Não | — | Habitos vida |
| exameFisico | String | Não | — | Não | — | Exame fisico |
| sinaisVitais | String | Não | — | Não | — | Sinais vitais |

#### DiagnosticoConsultaRequest (diagnostico)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| diagnostico | String | Não | — | Não | — | Diagnostico |
| diagnosticosSecundarios | String | Não | — | Não | — | Diagnosticos secundarios |
| hipoteseDiagnostica | String | Não | — | Não | — | Hipotese diagnostica |
| diagnosticoDiferencial | String | Não | — | Não | — | Diagnostico diferencial |
| conduta | String | Não | — | Não | — | Conduta |

#### PrescricaoConsultaRequest (prescricao)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| medicamentosPrescritos | String | Não | — | Não | — | Medicamentos prescritos |
| orientacoes | String | Não | — | Não | — | Orientacoes |
| dieta | String | Não | — | Não | — | Dieta |
| atividadeFisica | String | Não | — | Não | — | Atividade fisica |
| repouso | String | Não | — | Não | — | Repouso |
| outrasOrientacoes | String | Não | — | Não | — | Outras orientacoes |

#### ExamesSolicitadosConsultaRequest (examesSolicitados)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| examesSolicitados | String | Não | — | Não | — | Exames solicitados |
| examesLaboratoriais | String | Não | — | Não | — | Exames laboratoriais |
| examesImagem | String | Não | — | Não | — | Exames imagem |
| examesOutros | String | Não | — | Não | — | Exames outros |

#### EncaminhamentoConsultaRequest (encaminhamento)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| encaminhamentos | String | Não | — | Não | — | Encaminhamentos |
| especialistaEncaminhado | String | Não (Validar apenas se preenchido) | @Size(max=255) | Não | — | Especialista encaminhado |
| motivoEncaminhamento | String | Não (Validar apenas se preenchido) | @Size(max=255) | Não | — | Motivo encaminhamento |
| prazoEncaminhamento | String | Sim | @Size(max=255), @NotNull, @Size(max=50) | Não | — | Prazo encaminhamento |

#### AtestadoConsultaRequest (atestado)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| tipoAtestado | String | Sim | @NotNull, @Size(max=100) | Não | — | Tipo atestado |
| diasAfastamento | Integer | Sim | @NotNull, @Size(max=100) | Não | — | Dias afastamento |
| dataInicioAfastamento | LocalDate | Sim | @NotNull, @Size(max=100) | Não | — | Data inicio afastamento |
| dataFimAfastamento | LocalDate | Sim | @NotNull, @Size(max=100) | Não | — | Data fim afastamento |
| motivoAtestado | String | Sim | @NotNull, @Size(max=100) | Não | — | Motivo atestado |
| cidAtestado | String | Não (Validar apenas se preenchido) | @Size(max=100), @Size(max=10) | Não | — | Cid atestado |

## ControlePonto

### Classe Request: ControlePontoRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| profissional | UUID | Não | — | Não | — | Profissional |
| medico | UUID | Não | — | Não | — | Medico |
| dataHora | OffsetDateTime | Não | — | Não | — | Data hora |
| dataPonto | LocalDate | Não | — | Não | — | Data ponto |
| tipoPonto | TipoPontoEnum | Não | — | Sim | — | Tipo ponto |
| latitude | Double | Não | — | Não | — | Latitude |
| longitude | Double | Não | — | Não | — | Longitude |
| enderecoIp | String | Não | — | Não | — | Endereco ip |
| observacoes | String | Não | — | Não | — | Observacoes |
| justificativa | String | Não | — | Não | — | Justificativa |
| aprovado | Boolean | Não | — | Não | — | Aprovado |
| aprovadoPor | UUID | Não | — | Não | — | Aprovado por |
| dataAprovacao | OffsetDateTime | Não | — | Não | — | Data aprovacao |

## Convênio

### Classe Request: ConvenioRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| nome | String | Sim | @NotBlank, @Size(max=255) | Não | — | Nome |
| nomeFantasia | String | Sim | @NotBlank, @Size(max=255), @Size(max=255) | Não | — | Nome fantasia |
| codigo | String | Sim | @NotBlank, @Size(max=255), @Size(max=255), @Size(max=50) | Não | — | Codigo |
| cnpj | String | Sim | @NotBlank, @Size(max=255), @Size(max=255), @Size(max=50), @Pattern | Não | 00.000.000/0000-00 | Cnpj |
| inscricaoEstadual | String | Sim | @NotBlank, @Size(max=255), @Size(max=255), @Size(max=50), @Pattern, @Size(max=20) | Não | — | Inscricao estadual |
| inscricaoMunicipal | String | Não (Validar apenas se preenchido) | @Size(max=255), @Size(max=50), @Pattern, @Size(max=20), @Size(max=20) | Não | — | Inscricao municipal |
| tipo | TipoConvenioEnum | Sim | @Size(max=50), @Pattern, @Size(max=20), @Size(max=20), @NotNull | Sim | — | Tipo |
| modalidade | ModalidadeConvenioEnum | Sim | @Pattern, @Size(max=20), @Size(max=20), @NotNull, @NotNull | Sim | — | Modalidade |
| categoria | String | Sim | @Size(max=20), @Size(max=20), @NotNull, @NotNull, @Size(max=100) | Não | — | Categoria |
| endereco | UUID | Sim | @Size(max=20), @Size(max=20), @NotNull, @NotNull, @Size(max=100) | Não | — | Endereco |
| contato | ContatoConvenioRequest | Sim | @Size(max=20), @NotNull, @NotNull, @Size(max=100) | Não | — | Contato |
| registroAns | RegistroANSConvenioRequest | Sim | @NotNull, @NotNull, @Size(max=100) | Não | — | Registro ans |
| cobertura | CoberturaConvenioRequest | Sim | @NotNull, @Size(max=100) | Não | — | Cobertura |
| informacoesFinanceiras | InformacoesFinanceirasConvenioRequest | Não (Validar apenas se preenchido) | @Size(max=100) | Não | — | Informacoes financeiras |
| status | StatusAtivoEnum | Não | — | Sim | — | Status |
| dataCadastro | LocalDate | Não | — | Não | — | Data cadastro |
| dataAtivacao | LocalDate | Não | — | Não | — | Data ativacao |
| dataDesativacao | LocalDate | Não | — | Não | — | Data desativacao |
| quantidadeEstabelecimentosCredenciados | Integer | Não | — | Não | — | Quantidade estabelecimentos credenciados |
| quantidadeProfissionaisCredenciados | Integer | Não | — | Não | — | Quantidade profissionais credenciados |
| contrato | String | Não (Validar apenas se preenchido) | @Size(max=500) | Não | — | Contrato |
| tabelaPrecos | String | Não (Validar apenas se preenchido) | @Size(max=500), @Size(max=500) | Não | — | Tabela precos |
| manualConvenio | String | Não (Validar apenas se preenchido) | @Size(max=500), @Size(max=500), @Size(max=500) | Não | — | Manual convenio |
| descricao | String | Não (Validar apenas se preenchido) | @Size(max=500), @Size(max=500), @Size(max=500), @Size(max=1000) | Não | — | Descricao |
| observacoes | String | Não (Validar apenas se preenchido) | @Size(max=500), @Size(max=500), @Size(max=500), @Size(max=1000), @Size(max=1000) | Não | — | Observacoes |
| integracaoGovernamental | IntegracaoGovernamentalConvenioRequest | Não (Validar apenas se preenchido) | @Size(max=500), @Size(max=500), @Size(max=1000), @Size(max=1000) | Não | — | Integracao governamental |

#### ContatoConvenioRequest (contato)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| telefonePrincipal | String | Não (Validar apenas se preenchido) | @Size(max=20) | Não | (00) 00000-0000 | Telefone principal |
| telefoneSecundario | String | Não (Validar apenas se preenchido) | @Size(max=20), @Size(max=20) | Não | (00) 00000-0000 | Telefone secundario |
| fax | String | Não (Validar apenas se preenchido) | @Size(max=20), @Size(max=20), @Size(max=20) | Não | (00) 00000-0000 | Fax |
| whatsapp | String | Não (Validar apenas se preenchido) | @Size(max=20), @Size(max=20), @Size(max=20), @Size(max=20) | Não | (00) 00000-0000 | Whatsapp |
| emailPrincipal | String | Não (Validar apenas se preenchido) | @Size(max=20), @Size(max=20), @Size(max=20), @Size(max=20), @Email, @Size(max=255) | Não | email | Email principal |
| emailSecundario | String | Não (Validar apenas se preenchido) | @Size(max=20), @Size(max=20), @Size(max=20), @Email, @Size(max=255), @Email, @Size(max=255) | Não | email | Email secundario |
| site | String | Não (Validar apenas se preenchido) | @Size(max=20), @Size(max=20), @Email, @Size(max=255), @Email, @Size(max=255), @Size(max=255) | Não | email | Site |
| nomeContato | String | Não (Validar apenas se preenchido) | @Size(max=20), @Email, @Size(max=255), @Email, @Size(max=255), @Size(max=255), @Size(max=100) | Não | email | Nome contato |
| cargoContato | String | Não (Validar apenas se preenchido) | @Email, @Size(max=255), @Email, @Size(max=255), @Size(max=255), @Size(max=100), @Size(max=100) | Não | email | Cargo contato |
| telefoneContato | String | Não (Validar apenas se preenchido) | @Email, @Size(max=255), @Size(max=255), @Size(max=100), @Size(max=100), @Size(max=20) | Não | email | Telefone contato |
| emailContato | String | Não (Validar apenas se preenchido) | @Size(max=255), @Size(max=100), @Size(max=100), @Size(max=20), @Email, @Size(max=255) | Não | email | Email contato |

#### RegistroANSConvenioRequest (registroAns)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| registroAns | String | Não (Validar apenas se preenchido) | @Size(max=50) | Não | — | Registro ans |
| codigoAns | String | Não (Validar apenas se preenchido) | @Size(max=50), @Size(max=50) | Não | — | Codigo ans |
| dataRegistroAns | LocalDate | Não (Validar apenas se preenchido) | @Size(max=50), @Size(max=50) | Não | — | Data registro ans |
| dataValidadeRegistroAns | LocalDate | Não (Validar apenas se preenchido) | @Size(max=50), @Size(max=50) | Não | — | Data validade registro ans |
| statusAns | StatusAtivoEnum | Não (Validar apenas se preenchido) | @Size(max=50), @Size(max=50) | Sim | — | Status ans |
| razaoSocialAns | String | Não (Validar apenas se preenchido) | @Size(max=50), @Size(max=50), @Size(max=100) | Não | — | Razao social ans |
| nomeFantasiaAns | String | Não (Validar apenas se preenchido) | @Size(max=50), @Size(max=100), @Size(max=50) | Não | — | Nome fantasia ans |
| codigoTiss | String | Não (Validar apenas se preenchido) | @Size(max=100), @Size(max=50), @Size(max=50) | Não | — | Codigo tiss |
| observacoesAns | String | Sim | @Size(max=100), @Size(max=50), @Size(max=50), @NotNull, @Size(max=255) | Não | — | Observacoes ans |

#### CoberturaConvenioRequest (cobertura)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| coberturaOutras | String | Sim | @NotNull, @NotNull, @NotNull | Não | — | Cobertura outras |

#### InformacoesFinanceirasConvenioRequest (informacoesFinanceiras)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| dataInicioContrato | LocalDate | Não | — | Não | — | Data inicio contrato |
| dataFimContrato | LocalDate | Não | — | Não | — | Data fim contrato |
| dataRenovacaoContrato | LocalDate | Não | — | Não | — | Data renovacao contrato |
| prazoContratoMeses | Integer | Não | — | Não | — | Prazo contrato meses |
| valorContratoMensal | BigDecimal | Não | — | Não | — | Valor contrato mensal |
| valorContratoAnual | BigDecimal | Não | — | Não | — | Valor contrato anual |
| percentualDesconto | BigDecimal | Não | — | Não | — | Percentual desconto |
| percentualCoparticipacao | BigDecimal | Não | — | Não | — | Percentual coparticipacao |
| franquiaAnual | BigDecimal | Não | — | Não | — | Franquia anual |
| carenciaGeralDias | Integer | Não | — | Não | — | Carencia geral dias |
| carenciaPartosDias | Integer | Não | — | Não | — | Carencia partos dias |
| carenciaCirurgiasDias | Integer | Não | — | Não | — | Carencia cirurgias dias |
| carenciaExamesDias | Integer | Não | — | Não | — | Carencia exames dias |
| limiteAnualConsultas | Integer | Não | — | Não | — | Limite anual consultas |
| limiteAnualExames | Integer | Não | — | Não | — | Limite anual exames |
| limiteAnualInternacoes | Integer | Não | — | Não | — | Limite anual internacoes |
| formaPagamento | String | Não (Validar apenas se preenchido) | @Size(max=50) | Não | — | Forma pagamento |
| diaVencimento | String | Não (Validar apenas se preenchido) | @Size(max=50), @Size(max=50) | Não | — | Dia vencimento |
| observacoesFinanceiras | String | Não (Validar apenas se preenchido) | @Size(max=50), @Size(max=50), @Size(max=255) | Não | — | Observacoes financeiras |

#### IntegracaoGovernamentalConvenioRequest (integracaoGovernamental)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| ultimaSincronizacaoAns | OffsetDateTime | Sim | @NotNull | Não | — | Ultima sincronizacao ans |
| ultimaSincronizacaoTiss | OffsetDateTime | Sim | @NotNull, @NotNull | Não | — | Ultima sincronizacao tiss |
| ultimaSincronizacaoSus | OffsetDateTime | Sim | @NotNull, @NotNull | Não | — | Ultima sincronizacao sus |
| codigoSistemaExterno | String | Sim | @NotNull, @NotNull, @Size(max=50) | Não | — | Codigo sistema externo |
| urlApi | String | Sim | @NotNull, @Size(max=50), @Size(max=255) | Não | — | Url api |
| tokenApi | String | Sim | @NotNull, @Size(max=50), @Size(max=255), @Size(max=255) | Não | — | Token api |
| chaveApi | String | Não (Validar apenas se preenchido) | @Size(max=50), @Size(max=255), @Size(max=255), @Size(max=255) | Não | — | Chave api |

## Cuidados de Enfermagem

### Classe Request: CuidadosEnfermagemRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| paciente | UUID | Sim | @NotNull | Não | — | Paciente |
| profissional | UUID | Sim | @NotNull, @NotNull | Não | — | Profissional |
| atendimento | UUID | Sim | @NotNull, @NotNull | Não | — | Atendimento |
| tipoCuidado | TipoCuidadoEnfermagemEnum | Sim | @NotNull, @NotNull, @NotNull | Sim | — | Tipo cuidado |
| descricaoProcedimento | String | Sim | @NotNull, @NotNull, @NotNull | Não | — | Descricao procedimento |
| dataHora | OffsetDateTime | Sim | @NotNull, @NotNull, @NotNull | Não | — | Data hora |
| pressaoSistolica | Integer | Sim | @NotNull, @NotNull, @NotNull | Não | — | Pressao sistolica |
| pressaoDiastolica | Integer | Sim | @NotNull, @NotNull, @NotNull | Não | — | Pressao diastolica |
| frequenciaCardiaca | Integer | Sim | @NotNull, @NotNull, @NotNull | Não | — | Frequencia cardiaca |
| frequenciaRespiratoria | Integer | Sim | @NotNull, @NotNull, @NotNull | Não | — | Frequencia respiratoria |
| temperatura | BigDecimal | Sim | @NotNull, @NotNull, @NotNull | Não | — | Temperatura |
| saturacaoOxigenio | Integer | Sim | @NotNull, @NotNull | Não | — | Saturacao oxigenio |
| glicemiaCapilar | Integer | Sim | @NotNull, @NotNull | Não | — | Glicemia capilar |
| peso | BigDecimal | Sim | @NotNull, @NotNull | Não | — | Peso |
| altura | BigDecimal | Sim | @NotNull | Não | — | Altura |
| localizacaoFerida | String | Sim | @NotNull | Não | — | Localizacao ferida |
| tipoFerida | String | Sim | @NotNull | Não | — | Tipo ferida |
| tamanhoFerida | String | Sim | @NotNull | Não | — | Tamanho ferida |
| aspectoFerida | String | Não | — | Não | — | Aspecto ferida |
| secrecaoPresente | Boolean | Não | — | Não | — | Secrecao presente |
| tipoSecrecao | String | Não | — | Não | — | Tipo secrecao |
| materialUtilizado | String | Não | — | Não | — | Material utilizado |
| medicamentoAdministrado | String | Não | — | Não | — | Medicamento administrado |
| dose | String | Não | — | Não | — | Dose |
| viaAdministracao | String | Não | — | Não | — | Via administracao |
| localAplicacao | String | Não | — | Não | — | Local aplicacao |
| loteMedicamento | String | Não | — | Não | — | Lote medicamento |
| tipoTesteRapido | String | Não | — | Não | — | Tipo teste rapido |
| resultadoTeste | String | Não | — | Não | — | Resultado teste |
| loteTeste | String | Não | — | Não | — | Lote teste |
| queixaPaciente | String | Não | — | Não | — | Queixa paciente |
| evolucao | String | Não | — | Não | — | Evolucao |
| intercorrencias | String | Não | — | Não | — | Intercorrencias |
| reacaoAdversa | Boolean | Não | — | Não | — | Reacao adversa |
| descricaoReacao | String | Não | — | Não | — | Descricao reacao |
| orientacoes | String | Não | — | Não | — | Orientacoes |
| necessitaRetorno | Boolean | Não | — | Não | — | Necessita retorno |
| dataRetorno | OffsetDateTime | Não | — | Não | — | Data retorno |
| observacoes | String | Não | — | Não | — | Observacoes |

## DadosClinicosBasicos

### Classe Request: DadosClinicosBasicosRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| paciente | UUID | Não | — | Não | — | Paciente |
| gestante | Boolean | Não | — | Não | — | Gestante |
| fumante | Boolean | Não | — | Não | — | Fumante |
| alcoolista | Boolean | Não | — | Não | — | Alcoolista |
| usuarioDrogas | Boolean | Não | — | Não | — | Usuario drogas |
| historicoViolencia | Boolean | Não | — | Não | — | Historico violencia |
| acompanhamentoPsicossocial | Boolean | Não | — | Não | — | Acompanhamento psicossocial |

## DadosSociodemograficos

### Classe Request: DadosSociodemograficosRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| paciente | UUID | Não | — | Não | — | Paciente |
| racaCor | RacaCorEnum | Não | — | Sim | — | Raca cor |
| nacionalidade | NacionalidadeEnum | Não | — | Sim | — | Nacionalidade |
| paisNascimento | String | Não | — | Não | — | Pais nascimento |
| naturalidade | String | Não | — | Não | — | Naturalidade |
| municipioNascimentoIbge | String | Não | — | Não | — | Municipio nascimento ibge |
| escolaridade | EscolaridadeEnum | Não | — | Sim | — | Escolaridade |
| ocupacaoProfissao | String | Não | — | Não | — | Ocupacao profissao |
| situacaoRua | Boolean | Não | — | Não | — | Situacao rua |
| tempoSituacaoRua | Integer | Não | — | Não | — | Tempo situacao rua |
| condicaoMoradia | CondicaoMoradiaEnum | Não | — | Sim | — | Condicao moradia |
| situacaoFamiliar | SituacaoFamiliarEnum | Não | — | Sim | — | Situacao familiar |

## DeficienciasPaciente

### Classe Request: DeficienciasPacienteRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| paciente | UUID | Não | — | Não | — | Paciente |
| deficiencia | UUID | Não | — | Não | — | Deficiencia |
| possuiLaudo | Boolean | Não | — | Não | — | Possui laudo |
| dataDiagnostico | LocalDate | Não | — | Não | — | Data diagnostico |
| observacoes | String | Não | — | Não | — | Observacoes |

## Deficiências Paciente Simplificado

### Classe Request: DeficienciasPacienteSimplificadoRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| paciente | UUID | Sim | @NotNull | Não | — | Paciente |
| tenant | UUID | Sim | @NotNull, @NotNull | Não | — | Tenant |
| deficiencia | UUID | Sim | @NotNull, @NotNull, @NotNull | Não | — | Deficiencia |

## Deficiencias

### Classe Request: DeficienciasRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| nome | String | Não | — | Não | — | Nome |
| descricao | String | Não | — | Não | — | Descricao |
| tipoDeficiencia | TipoDeficienciaEnum | Não | — | Sim | — | Tipo deficiencia |
| cid10Relacionado | String | Não | — | Não | — | Cid10 relacionado |

## Departamentos

### Classe Request: DepartamentosRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| estabelecimento | UUID | Não | — | Não | — | Estabelecimento |
| nome | String | Não | — | Não | — | Nome |
| descricao | String | Não | — | Não | — | Descricao |

## DispensacoesMedicamentos

### Classe Request: DispensacoesMedicamentosRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| paciente | UUID | Não | — | Não | — | Paciente |
| medicacao | UUID | Não | — | Não | — | Medicacao |
| quantidade | Integer | Não | — | Não | — | Quantidade |
| dataDispensacao | OffsetDateTime | Não | — | Não | — | Data dispensacao |
| observacoes | String | Não | — | Não | — | Observacoes |

## Doenças do Paciente

### Classe Request: DoencasPacienteRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| paciente | UUID | Sim | @NotNull | Não | — | Paciente |
| doenca | UUID | Sim | @NotNull, @NotNull | Não | — | Doenca |
| cidPrincipal | UUID | Sim | @NotNull, @NotNull | Não | — | Cid principal |
| diagnostico | DiagnosticoDoencaPacienteRequest | Sim | @NotNull, @NotNull | Não | — | Diagnostico |
| acompanhamento | AcompanhamentoDoencaPacienteRequest | Sim | @NotNull, @NotNull | Não | — | Acompanhamento |
| tratamentoAtual | TratamentoAtualDoencaPacienteRequest | Sim | @NotNull, @NotNull | Não | — | Tratamento atual |
| observacoes | String | Sim | @NotNull, @Size(max=1000) | Não | — | Observacoes |

#### DiagnosticoDoencaPacienteRequest (diagnostico)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| dataDiagnostico | LocalDate | Não | — | Não | — | Data diagnostico |
| dataInicioSintomas | LocalDate | Não | — | Não | — | Data inicio sintomas |
| statusDiagnostico | StatusDiagnosticoEnum | Não | — | Sim | — | Status diagnostico |
| gravidadeAtual | GravidadeDoencaEnum | Não | — | Sim | — | Gravidade atual |
| localDiagnostico | String | Não (Validar apenas se preenchido) | @Size(max=255) | Não | — | Local diagnostico |
| metodoDiagnostico | String | Não (Validar apenas se preenchido) | @Size(max=255), @Size(max=255) | Não | — | Metodo diagnostico |

#### AcompanhamentoDoencaPacienteRequest (acompanhamento)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| dataUltimaConsulta | LocalDate | Não | — | Não | — | Data ultima consulta |
| dataProximaConsulta | LocalDate | Não | — | Não | — | Data proxima consulta |
| frequenciaAcompanhamento | String | Não (Validar apenas se preenchido) | @Size(max=50) | Não | — | Frequencia acompanhamento |
| especialistaResponsavel | String | Não (Validar apenas se preenchido) | @Size(max=50), @Size(max=255) | Não | — | Especialista responsavel |
| estabelecimentoAcompanhamento | String | Não (Validar apenas se preenchido) | @Size(max=50), @Size(max=255), @Size(max=255) | Não | — | Estabelecimento acompanhamento |
| evolucaoClinica | String | Não (Validar apenas se preenchido) | @Size(max=50), @Size(max=255), @Size(max=255) | Não | — | Evolucao clinica |
| complicacoes | String | Não (Validar apenas se preenchido) | @Size(max=50), @Size(max=255), @Size(max=255) | Não | — | Complicacoes |

#### TratamentoAtualDoencaPacienteRequest (tratamentoAtual)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| medicacaoAtual | String | Não | — | Não | — | Medicacao atual |
| doseMedicacao | String | Não | — | Não | — | Dose medicacao |
| frequenciaMedicacao | String | Não (Validar apenas se preenchido) | @Size(max=100) | Não | — | Frequencia medicacao |
| dataInicioTratamento | LocalDate | Não (Validar apenas se preenchido) | @Size(max=100) | Não | — | Data inicio tratamento |
| dataFimTratamento | LocalDate | Não (Validar apenas se preenchido) | @Size(max=100) | Não | — | Data fim tratamento |
| procedimentosEmAndamento | String | Não (Validar apenas se preenchido) | @Size(max=100) | Não | — | Procedimentos em andamento |
| adherenciaTratamento | String | Não (Validar apenas se preenchido) | @Size(max=100), @Size(max=50) | Não | — | Adherencia tratamento |
| efeitosColaterais | String | Não (Validar apenas se preenchido) | @Size(max=100), @Size(max=50) | Não | — | Efeitos colaterais |
| contraindicacoes | String | Não (Validar apenas se preenchido) | @Size(max=100), @Size(max=50) | Não | — | Contraindicacoes |

## Doenças Paciente Simplificado

### Classe Request: DoencasPacienteSimplificadoRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| paciente | UUID | Sim | @NotNull | Não | — | Paciente |
| tenant | UUID | Sim | @NotNull, @NotNull | Não | — | Tenant |
| doenca | UUID | Sim | @NotNull, @NotNull, @NotNull | Não | — | Doenca |

## Doenças

### Classe Request: DoencasRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| nome | String | Sim | @NotBlank, @Size(max=255) | Não | — | Nome |
| nomeCientifico | String | Sim | @NotBlank, @Size(max=255), @Size(max=255) | Não | — | Nome cientifico |
| codigoInterno | String | Sim | @NotBlank, @Size(max=255), @Size(max=255), @Size(max=50) | Não | — | Codigo interno |
| classificacao | ClassificacaoDoencaRequest | Sim | @NotBlank, @Size(max=255), @Size(max=255), @Size(max=50) | Não | — | Classificacao |
| cidPrincipal | UUID | Sim | @NotBlank, @Size(max=255), @Size(max=255), @Size(max=50) | Não | — | Cid principal |
| sintomas | SintomasDoencaRequest | Não (Validar apenas se preenchido) | @Size(max=255), @Size(max=255), @Size(max=50) | Não | — | Sintomas |
| tratamentoPadrao | TratamentoPadraoDoencaRequest | Não (Validar apenas se preenchido) | @Size(max=255), @Size(max=50) | Não | — | Tratamento padrao |
| epidemiologia | EpidemiologiaDoencaRequest | Não (Validar apenas se preenchido) | @Size(max=50) | Não | — | Epidemiologia |
| descricao | String | Não (Validar apenas se preenchido) | @Size(max=1000) | Não | — | Descricao |
| causas | String | Não (Validar apenas se preenchido) | @Size(max=1000), @Size(max=1000) | Não | — | Causas |
| fisiopatologia | String | Não (Validar apenas se preenchido) | @Size(max=1000), @Size(max=1000), @Size(max=2000) | Não | — | Fisiopatologia |
| prognostico | String | Não (Validar apenas se preenchido) | @Size(max=1000), @Size(max=1000), @Size(max=2000), @Size(max=1000) | Não | — | Prognostico |
| observacoes | String | Não (Validar apenas se preenchido) | @Size(max=1000), @Size(max=1000), @Size(max=2000), @Size(max=1000), @Size(max=1000) | Não | — | Observacoes |

#### ClassificacaoDoencaRequest (classificacao)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| tipoDoenca | TipoDoencaEnum | Não | — | Sim | — | Tipo doenca |
| gravidade | GravidadeDoencaEnum | Não | — | Sim | — | Gravidade |
| categoria | String | Não (Validar apenas se preenchido) | @Size(max=100) | Não | — | Categoria |
| subcategoria | String | Não (Validar apenas se preenchido) | @Size(max=100), @Size(max=100) | Não | — | Subcategoria |
| codigoCidPrincipal | String | Não (Validar apenas se preenchido) | @Size(max=100), @Size(max=100), @Size(max=50) | Não | — | Codigo cid principal |

#### SintomasDoencaRequest (sintomas)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| sintomasComuns | String | Não | — | Não | — | Sintomas comuns |
| sintomasGraves | String | Não | — | Não | — | Sintomas graves |
| sinaisClinicos | String | Não | — | Não | — | Sinais clinicos |
| manifestacoesExtras | String | Não | — | Não | — | Manifestacoes extras |

#### TratamentoPadraoDoencaRequest (tratamentoPadrao)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| tratamentoPadrao | String | Não | — | Não | — | Tratamento padrao |
| medicamentosComuns | String | Não | — | Não | — | Medicamentos comuns |
| procedimentosComuns | String | Não | — | Não | — | Procedimentos comuns |
| cuidadosEspeciais | String | Não | — | Não | — | Cuidados especiais |
| prevencao | String | Não | — | Não | — | Prevencao |

#### EpidemiologiaDoencaRequest (epidemiologia)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| incidenciaAnual | Integer | Não | — | Não | — | Incidencia anual |
| prevalencia | Integer | Não | — | Não | — | Prevalencia |
| faixaEtariaMaisAfetada | String | Não (Validar apenas se preenchido) | @Size(max=100) | Não | — | Faixa etaria mais afetada |
| sexoMaisAfetado | String | Não (Validar apenas se preenchido) | @Size(max=100), @Size(max=1) | Não | — | Sexo mais afetado |
| fatoresRisco | String | Não (Validar apenas se preenchido) | @Size(max=100), @Size(max=1) | Não | — | Fatores risco |
| sazonalidade | String | Não (Validar apenas se preenchido) | @Size(max=100), @Size(max=1), @Size(max=100) | Não | — | Sazonalidade |
| distribuicaoGeografica | String | Não (Validar apenas se preenchido) | @Size(max=100), @Size(max=1), @Size(max=100) | Não | — | Distribuicao geografica |

## Educação em Saúde

### Classe Request: EducacaoSaudeRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| profissionalResponsavel | UUID | Sim | @NotNull | Não | — | Profissional responsavel |
| equipeSaude | UUID | Sim | @NotNull | Não | — | Equipe saude |
| tipoAtividade | TipoEducacaoSaudeEnum | Sim | @NotNull, @NotNull | Sim | — | Tipo atividade |
| titulo | String | Sim | @NotNull, @NotNull, @NotBlank, @Size(max=255) | Não | — | Titulo |
| descricao | String | Sim | @NotNull, @NotNull, @NotBlank, @Size(max=255) | Não | — | Descricao |
| tema | String | Sim | @NotNull, @NotNull, @NotBlank, @Size(max=255) | Não | — | Tema |
| objetivos | String | Sim | @NotNull, @NotNull, @NotBlank, @Size(max=255) | Não | — | Objetivos |
| metodologia | String | Sim | @NotNull, @NotNull, @NotBlank, @Size(max=255) | Não | — | Metodologia |
| recursosUtilizados | String | Sim | @NotNull, @NotNull, @NotBlank, @Size(max=255) | Não | — | Recursos utilizados |
| dataHoraInicio | OffsetDateTime | Sim | @NotNull, @NotBlank, @Size(max=255), @NotNull | Não | — | Data hora inicio |
| dataHoraFim | OffsetDateTime | Sim | @NotNull, @NotBlank, @Size(max=255), @NotNull | Não | — | Data hora fim |
| duracaoMinutos | Integer | Sim | @NotBlank, @Size(max=255), @NotNull, @Min(0) | Não | — | Duracao minutos |
| local | String | Sim | @NotNull, @Min(0), @Size(max=255) | Não | — | Local |
| endereco | String | Sim | @NotNull, @Min(0), @Size(max=255), @Size(max=500) | Não | — | Endereco |
| publicoAlvo | String | Sim | @NotNull, @Min(0), @Size(max=255), @Size(max=500), @Size(max=255) | Não | — | Publico alvo |
| faixaEtariaInicio | Integer | Não (Validar apenas se preenchido) | @Min(0), @Size(max=255), @Size(max=500), @Size(max=255) | Não | — | Faixa etaria inicio |
| faixaEtariaFim | Integer | Não (Validar apenas se preenchido) | @Min(0), @Size(max=255), @Size(max=500), @Size(max=255) | Não | — | Faixa etaria fim |
| numeroParticipantesPrevisto | Integer | Não (Validar apenas se preenchido) | @Size(max=255), @Size(max=500), @Size(max=255), @Min(0) | Não | — | Numero participantes previsto |
| numeroParticipantesPresente | Integer | Não (Validar apenas se preenchido) | @Size(max=500), @Size(max=255), @Min(0), @Min(0) | Não | — | Numero participantes presente |
| listaPresencaExterna | String | Não (Validar apenas se preenchido) | @Size(max=500), @Size(max=255), @Min(0), @Min(0) | Não | — | Lista presenca externa |
| avaliacao | String | Não (Validar apenas se preenchido) | @Size(max=500), @Size(max=255), @Min(0), @Min(0) | Não | — | Avaliacao |
| pontosPositivos | String | Não (Validar apenas se preenchido) | @Size(max=255), @Min(0), @Min(0) | Não | — | Pontos positivos |
| pontosMelhoria | String | Não (Validar apenas se preenchido) | @Size(max=255), @Min(0), @Min(0) | Não | — | Pontos melhoria |
| encaminhamentosRealizados | String | Não (Validar apenas se preenchido) | @Size(max=255), @Min(0), @Min(0) | Não | — | Encaminhamentos realizados |
| materialDistribuido | String | Não (Validar apenas se preenchido) | @Min(0), @Min(0) | Não | — | Material distribuido |
| quantidadeMaterialDistribuido | Integer | Não (Validar apenas se preenchido) | @Min(0), @Min(0), @Min(0) | Não | — | Quantidade material distribuido |
| observacoes | String | Não (Validar apenas se preenchido) | @Min(0), @Min(0), @Min(0) | Não | — | Observacoes |

## Endereço

### Classe Request: EnderecoRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| tipoLogradouro | TipoLogradouroEnum | Não | — | Sim | — | Tipo logradouro |
| logradouro | String | Não (Validar apenas se preenchido) | @Size(max=200) | Não | — | Logradouro |
| numero | String | Não (Validar apenas se preenchido) | @Size(max=200), @Size(max=10) | Não | — | Numero |
| complemento | String | Não (Validar apenas se preenchido) | @Size(max=200), @Size(max=10), @Size(max=100) | Não | — | Complemento |
| bairro | String | Não (Validar apenas se preenchido) | @Size(max=200), @Size(max=10), @Size(max=100), @Size(max=100) | Não | — | Bairro |
| cep | String | Não (Validar apenas se preenchido) | @Size(max=200), @Size(max=10), @Size(max=100), @Size(max=100), @Pattern | Não | 00000-000 | Cep |
| pais | String | Não (Validar apenas se preenchido) | @Size(max=200), @Size(max=10), @Size(max=100), @Size(max=100), @Pattern | Não | — | Pais |
| distrito | String | Não (Validar apenas se preenchido) | @Size(max=200), @Size(max=10), @Size(max=100), @Size(max=100), @Pattern | Não | — | Distrito |
| pontoReferencia | String | Não (Validar apenas se preenchido) | @Size(max=10), @Size(max=100), @Size(max=100), @Pattern | Não | — | Ponto referencia |
| latitude | Double | Não (Validar apenas se preenchido) | @Size(max=10), @Size(max=100), @Size(max=100), @Pattern | Não | — | Latitude |
| longitude | Double | Não (Validar apenas se preenchido) | @Size(max=10), @Size(max=100), @Size(max=100), @Pattern | Não | — | Longitude |
| tipoEndereco | TipoEnderecoEnum | Não (Validar apenas se preenchido) | @Size(max=100), @Size(max=100), @Pattern | Sim | — | Tipo endereco |
| zona | ZonaDomicilioEnum | Não (Validar apenas se preenchido) | @Size(max=100), @Size(max=100), @Pattern | Sim | — | Zona |
| codigoIbgeMunicipio | String | Não (Validar apenas se preenchido) | @Size(max=100), @Pattern, @Size(max=7) | Não | — | Codigo ibge municipio |
| microarea | String | Não (Validar apenas se preenchido) | @Pattern, @Size(max=7), @Size(max=10) | Não | — | Microarea |
| ineEquipe | String | Não (Validar apenas se preenchido) | @Size(max=7), @Size(max=10), @Size(max=15) | Não | — | Ine equipe |
| quadra | String | Não (Validar apenas se preenchido) | @Size(max=7), @Size(max=10), @Size(max=15), @Size(max=20) | Não | — | Quadra |
| lote | String | Não (Validar apenas se preenchido) | @Size(max=7), @Size(max=10), @Size(max=15), @Size(max=20), @Size(max=20) | Não | — | Lote |
| zonaRuralDescricao | String | Não (Validar apenas se preenchido) | @Size(max=10), @Size(max=15), @Size(max=20), @Size(max=20), @Size(max=200) | Não | — | Zona rural descricao |
| andar | String | Não (Validar apenas se preenchido) | @Size(max=15), @Size(max=20), @Size(max=20), @Size(max=200), @Size(max=5) | Não | — | Andar |
| bloco | String | Não (Validar apenas se preenchido) | @Size(max=20), @Size(max=20), @Size(max=200), @Size(max=5), @Size(max=20) | Não | — | Bloco |
| estado | UUID | Sim | @Size(max=20), @Size(max=200), @Size(max=5), @Size(max=20), @NotNull | Não | — | Estado |
| cidade | UUID | Sim | @Size(max=200), @Size(max=5), @Size(max=20), @NotNull | Não | — | Cidade |

## EnumItem

### Classe Request: EnumItemRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| nome | String | Não | — | Não | — | Nome |
| codigo | Integer | Não | — | Não | — | Codigo |
| descricao | String | Não | — | Não | — | Descricao |

## Enums

### Classe Request: EnumsRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| enums | Lista de UUID | Não | — | Não | — | Enums |
| totalEnums | Integer | Não | — | Não | — | Total enums |

## EquipamentosEstabelecimento

### Classe Request: EquipamentosEstabelecimentoRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| estabelecimento | UUID | Não | — | Não | — | Estabelecimento |
| equipamento | UUID | Não | — | Não | — | Equipamento |
| codigoInterno | String | Não | — | Não | — | Codigo interno |
| numeroSerie | String | Não | — | Não | — | Numero serie |
| numeroPatrimonio | String | Não | — | Não | — | Numero patrimonio |
| numeroTombo | String | Não | — | Não | — | Numero tombo |
| quantidadeDisponivel | Integer | Não | — | Não | — | Quantidade disponivel |
| quantidadeEmManutencao | Integer | Não | — | Não | — | Quantidade em manutencao |
| quantidadeInativa | Integer | Não | — | Não | — | Quantidade inativa |
| setorDepartamento | String | Não | — | Não | — | Setor departamento |
| salaAmbiente | String | Não | — | Não | — | Sala ambiente |
| andar | String | Não | — | Não | — | Andar |
| localizacaoFisica | String | Não | — | Não | — | Localizacao fisica |
| dataUltimaManutencao | LocalDate | Não | — | Não | — | Data ultima manutencao |
| dataProximaManutencao | LocalDate | Não | — | Não | — | Data proxima manutencao |
| dataUltimaCalibracao | LocalDate | Não | — | Não | — | Data ultima calibracao |
| dataProximaCalibracao | LocalDate | Não | — | Não | — | Data proxima calibracao |
| empresaManutencao | String | Não | — | Não | — | Empresa manutencao |
| contatoManutencao | String | Não | — | Não | — | Contato manutencao |
| dataAquisicao | LocalDate | Não | — | Não | — | Data aquisicao |
| dataInstalacao | LocalDate | Não | — | Não | — | Data instalacao |
| dataInicioGarantia | LocalDate | Não | — | Não | — | Data inicio garantia |
| dataFimGarantia | LocalDate | Não | — | Não | — | Data fim garantia |
| valorAquisicao | BigDecimal | Não | — | Não | — | Valor aquisicao |
| numeroNotaFiscal | String | Não | — | Não | — | Numero nota fiscal |
| responsavelTecnico | String | Não | — | Não | — | Responsavel tecnico |
| registroResponsavel | String | Não | — | Não | — | Registro responsavel |
| horasUsoTotal | Integer | Não | — | Não | — | Horas uso total |
| horasUsoUltimoMes | Integer | Não | — | Não | — | Horas uso ultimo mes |
| observacoes | String | Não | — | Não | — | Observacoes |
| historicoManutencao | String | Não | — | Não | — | Historico manutencao |
| problemasConhecidos | String | Não | — | Não | — | Problemas conhecidos |

## Equipamentos

### Classe Request: EquipamentosRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| nome | String | Não | — | Não | — | Nome |
| nomeComercial | String | Não | — | Não | — | Nome comercial |
| codigoInterno | String | Não | — | Não | — | Codigo interno |
| codigoCnes | String | Não | — | Não | — | Codigo cnes |
| registroAnvisa | String | Não | — | Não | — | Registro anvisa |
| tipo | TipoEquipamentoEnum | Não | — | Sim | — | Tipo |
| categoria | String | Não | — | Não | — | Categoria |
| subcategoria | String | Não | — | Não | — | Subcategoria |
| classeRisco | String | Não | — | Não | — | Classe risco |
| fabricante | UUID | Não | — | Não | — | Fabricante |
| modelo | String | Não | — | Não | — | Modelo |
| versao | String | Não | — | Não | — | Versao |
| potencia | BigDecimal | Não | — | Não | — | Potencia |
| unidadePotencia | String | Não | — | Não | — | Unidade potencia |
| peso | BigDecimal | Não | — | Não | — | Peso |
| altura | BigDecimal | Não | — | Não | — | Altura |
| largura | BigDecimal | Não | — | Não | — | Largura |
| profundidade | BigDecimal | Não | — | Não | — | Profundidade |
| tensaoEletrica | String | Não | — | Não | — | Tensao eletrica |
| frequencia | String | Não | — | Não | — | Frequencia |
| corrente | String | Não | — | Não | — | Corrente |
| tipoAlimentacao | String | Não | — | Não | — | Tipo alimentacao |
| certificacaoIso | String | Não | — | Não | — | Certificacao iso |
| certificacaoCe | String | Não | — | Não | — | Certificacao ce |
| certificacaoFda | String | Não | — | Não | — | Certificacao fda |
| dataCertificacao | LocalDate | Não | — | Não | — | Data certificacao |
| dataValidadeCertificacao | LocalDate | Não | — | Não | — | Data validade certificacao |
| periodoCalibracaoMeses | Integer | Não | — | Não | — | Periodo calibracao meses |
| periodoManutencaoMeses | Integer | Não | — | Não | — | Periodo manutencao meses |
| tipoManutencao | String | Não | — | Não | — | Tipo manutencao |
| valorAquisicao | BigDecimal | Não | — | Não | — | Valor aquisicao |
| dataAquisicao | LocalDate | Não | — | Não | — | Data aquisicao |
| fornecedor | String | Não | — | Não | — | Fornecedor |
| numeroNotaFiscal | String | Não | — | Não | — | Numero nota fiscal |
| numeroContrato | String | Não | — | Não | — | Numero contrato |
| tempoGarantiaMeses | Integer | Não | — | Não | — | Tempo garantia meses |
| dataInicioGarantia | LocalDate | Não | — | Não | — | Data inicio garantia |
| dataFimGarantia | LocalDate | Não | — | Não | — | Data fim garantia |
| condicoesGarantia | String | Não | — | Não | — | Condicoes garantia |
| status | StatusAtivoEnum | Não | — | Sim | — | Status |
| manualTecnico | String | Não | — | Não | — | Manual tecnico |
| manualUsuario | String | Não | — | Não | — | Manual usuario |
| fichaTecnica | String | Não | — | Não | — | Ficha tecnica |
| descricao | String | Não | — | Não | — | Descricao |
| caracteristicas | String | Não | — | Não | — | Caracteristicas |
| aplicacoes | String | Não | — | Não | — | Aplicacoes |
| observacoes | String | Não | — | Não | — | Observacoes |

## Equipe Cirúrgica

### Classe Request: EquipeCirurgicaRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| cirurgia | UUID | Sim | @NotNull | Não | — | Cirurgia |
| profissional | UUID | Sim | @NotNull, @NotNull | Não | — | Profissional |
| medico | UUID | Sim | @NotNull, @NotNull | Não | — | Medico |
| funcao | String | Sim | @NotNull, @NotNull, @NotBlank, @Size(max=100) | Não | — | Funcao |
| ehPrincipal | Boolean | Sim | @NotNull, @NotNull, @NotBlank, @Size(max=100) | Não | — | Eh principal |
| valorParticipacao | BigDecimal | Sim | @NotNull, @NotNull, @NotBlank, @Size(max=100) | Não | — | Valor participacao |
| percentualParticipacao | BigDecimal | Sim | @NotNull, @NotNull, @NotBlank, @Size(max=100) | Não | — | Percentual participacao |
| dataHoraEntrada | OffsetDateTime | Sim | @NotNull, @NotNull, @NotBlank, @Size(max=100) | Não | — | Data hora entrada |
| dataHoraSaida | OffsetDateTime | Sim | @NotNull, @NotNull, @NotBlank, @Size(max=100) | Não | — | Data hora saida |
| observacoes | String | Sim | @NotNull, @NotBlank, @Size(max=100) | Não | — | Observacoes |

## Equipe de Saúde

### Classe Request: EquipeSaudeRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| ine | String | Sim | @NotBlank, @Size(max=15) | Não | — | Ine |
| nomeReferencia | String | Sim | @NotBlank, @Size(max=15), @NotBlank, @Size(max=255) | Não | — | Nome referencia |
| tipoEquipe | TipoEquipeEnum | Sim | @NotBlank, @Size(max=15), @NotBlank, @Size(max=255), @NotNull | Sim | — | Tipo equipe |
| estabelecimento | UUID | Sim | @NotBlank, @Size(max=15), @NotBlank, @Size(max=255), @NotNull, @NotNull | Não | — | Estabelecimento |
| dataAtivacao | OffsetDateTime | Sim | @NotBlank, @Size(max=15), @NotBlank, @Size(max=255), @NotNull, @NotNull, @NotNull | Não | — | Data ativacao |
| dataInativacao | OffsetDateTime | Sim | @NotBlank, @Size(max=255), @NotNull, @NotNull, @NotNull | Não | — | Data inativacao |
| status | StatusAtivoEnum | Sim | @Size(max=255), @NotNull, @NotNull, @NotNull, @NotNull | Sim | — | Status |
| observacoes | String | Sim | @NotNull, @NotNull, @NotNull, @NotNull | Não | — | Observacoes |

## EscalaTrabalho

### Classe Request: EscalaTrabalhoRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| profissional | UUID | Não | — | Não | — | Profissional |
| medico | UUID | Não | — | Não | — | Medico |
| dataInicio | LocalDate | Não | — | Não | — | Data inicio |
| dataFim | LocalDate | Não | — | Não | — | Data fim |
| diaSemana | DayOfWeek | Não | — | Não | — | Dia semana |
| horaEntrada | LocalTime | Não | — | Não | — | Hora entrada |
| horaSaida | LocalTime | Não | — | Não | — | Hora saida |
| intervaloInicio | LocalTime | Não | — | Não | — | Intervalo inicio |
| intervaloFim | LocalTime | Não | — | Não | — | Intervalo fim |
| cargaHorariaDiaria | Integer | Não | — | Não | — | Carga horaria diaria |
| observacoes | String | Não | — | Não | — | Observacoes |

## Especialidades Médicas

### Classe Request: EspecialidadesMedicasRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| nome | String | Sim | @NotBlank, @Size(max=255) | Não | — | Nome |
| codigo | String | Sim | @NotBlank, @Size(max=255), @Size(max=50) | Não | — | Codigo |
| nomeCientifico | String | Sim | @NotBlank, @Size(max=255), @Size(max=50), @Size(max=255) | Não | — | Nome cientifico |
| classificacao | ClassificacaoEspecialidadeMedicaRequest | Sim | @NotBlank, @Size(max=255), @Size(max=50), @Size(max=255) | Não | — | Classificacao |
| descricao | String | Sim | @NotBlank, @Size(max=255), @Size(max=50), @Size(max=255), @Size(max=1000) | Não | — | Descricao |
| areaAtuacaoDescricao | String | Não (Validar apenas se preenchido) | @Size(max=50), @Size(max=255), @Size(max=1000), @Size(max=1000) | Não | — | Area atuacao descricao |
| requisitosFormacao | String | Não (Validar apenas se preenchido) | @Size(max=255), @Size(max=1000), @Size(max=1000), @Size(max=1000) | Não | — | Requisitos formacao |
| observacoes | String | Não (Validar apenas se preenchido) | @Size(max=1000), @Size(max=1000), @Size(max=1000), @Size(max=1000) | Não | — | Observacoes |

#### ClassificacaoEspecialidadeMedicaRequest (classificacao)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| tipoEspecialidade | TipoEspecialidadeMedicaEnum | Não | — | Sim | — | Tipo especialidade |
| codigoCfm | String | Não (Validar apenas se preenchido) | @Size(max=50) | Não | — | Codigo cfm |
| codigoCnes | String | Não (Validar apenas se preenchido) | @Size(max=50), @Size(max=50) | Não | — | Codigo cnes |
| areaAtuacao | String | Não (Validar apenas se preenchido) | @Size(max=50), @Size(max=50), @Size(max=100) | Não | — | Area atuacao |
| subarea | String | Não (Validar apenas se preenchido) | @Size(max=50), @Size(max=50), @Size(max=100), @Size(max=100) | Não | — | Subarea |

## Estabelecimentos

### Classe Request: EstabelecimentosRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| nome | String | Sim | @NotBlank, @Size(max=255) | Não | — | Nome |
| nomeFantasia | String | Sim | @NotBlank, @Size(max=255), @Size(max=255) | Não | — | Nome fantasia |
| tipo | TipoEstabelecimentoEnum | Sim | @NotBlank, @Size(max=255), @Size(max=255), @NotNull | Sim | — | Tipo |
| codigoCnes | String | Sim | @NotBlank, @Size(max=255), @Size(max=255), @NotNull, @Size(max=7) | Não | — | Codigo cnes |
| cnpj | String | Sim | @NotBlank, @Size(max=255), @Size(max=255), @NotNull, @Size(max=7), @Pattern | Não | 00.000.000/0000-00 | Cnpj |
| naturezaJuridica | NaturezaJuridicaEnum | Sim | @NotBlank, @Size(max=255), @Size(max=255), @NotNull, @Size(max=7), @Pattern | Sim | — | Natureza juridica |
| registroOficial | String | Sim | @Size(max=255), @NotNull, @Size(max=7), @Pattern, @Size(max=50) | Não | — | Registro oficial |
| enderecoPrincipal | UUID | Não | — | Não | — | / |
| enderecoPrincipalCompleto | EnderecoRequest | Não | — | Não | — | / |
| telefone | String | Não (Validar apenas se preenchido) | @Pattern | Não | (00) 00000-0000 | / |
| telefoneSecundario | String | Não (Validar apenas se preenchido) | @Pattern, @Pattern | Não | (00) 00000-0000 | / |
| fax | String | Não (Validar apenas se preenchido) | @Pattern, @Pattern, @Pattern | Não | (00) 00000-0000 | / |
| email | String | Não (Validar apenas se preenchido) | @Pattern, @Pattern, @Pattern, @Email, @Size(max=255) | Não | email | Email |
| site | String | Não (Validar apenas se preenchido) | @Pattern, @Pattern, @Pattern, @Email, @Size(max=255), @Size(max=255) | Não | email | Site |
| responsavelTecnico | UUID | Não (Validar apenas se preenchido) | @Pattern, @Pattern, @Pattern, @Email, @Size(max=255), @Size(max=255) | Não | email | Responsavel tecnico |
| responsavelAdministrativo | UUID | Não (Validar apenas se preenchido) | @Pattern, @Pattern, @Email, @Size(max=255), @Size(max=255) | Não | email | Responsavel administrativo |
| responsavelLegalNome | String | Não (Validar apenas se preenchido) | @Pattern, @Pattern, @Email, @Size(max=255), @Size(max=255), @Size(max=255) | Não | email | Responsavel legal nome |
| responsavelLegalCpf | String | Não (Validar apenas se preenchido) | @Pattern, @Email, @Size(max=255), @Size(max=255), @Size(max=255), @Pattern | Não | 000.000.000-00 | Responsavel legal cpf |
| statusFuncionamento | StatusFuncionamentoEnum | Não (Validar apenas se preenchido) | @Email, @Size(max=255), @Size(max=255), @Size(max=255), @Pattern | Sim | email | Status funcionamento |
| dataAbertura | OffsetDateTime | Não (Validar apenas se preenchido) | @Email, @Size(max=255), @Size(max=255), @Size(max=255), @Pattern | Não | email | Data abertura |
| dataLicenciamento | OffsetDateTime | Não (Validar apenas se preenchido) | @Email, @Size(max=255), @Size(max=255), @Size(max=255), @Pattern | Não | email | Data licenciamento |
| dataValidadeLicenca | OffsetDateTime | Não (Validar apenas se preenchido) | @Size(max=255), @Size(max=255), @Size(max=255), @Pattern | Não | — | Data validade licenca |
| numeroAlvara | String | Não (Validar apenas se preenchido) | @Size(max=255), @Size(max=255), @Pattern | Não | — | Numero alvara |
| numeroLicencaSanitaria | String | Não (Validar apenas se preenchido) | @Size(max=255), @Size(max=255), @Pattern | Não | — | Numero licenca sanitaria |
| dataValidadeLicencaSanitaria | OffsetDateTime | Não (Validar apenas se preenchido) | @Size(max=255), @Size(max=255), @Pattern | Não | — | Data validade licenca sanitaria |
| quantidadeLeitos | Integer | Não (Validar apenas se preenchido) | @Size(max=255), @Pattern | Não | — | Quantidade leitos |
| quantidadeConsultorios | Integer | Não (Validar apenas se preenchido) | @Size(max=255), @Pattern | Não | — | Quantidade consultorios |
| quantidadeSalasCirurgia | Integer | Não (Validar apenas se preenchido) | @Size(max=255), @Pattern | Não | — | Quantidade salas cirurgia |
| quantidadeAmbulatorios | Integer | Não (Validar apenas se preenchido) | @Size(max=255), @Pattern | Não | — | Quantidade ambulatorios |
| areaConstruidaMetrosQuadrados | Double | Não (Validar apenas se preenchido) | @Pattern | Não | — | Area construida metros quadrados |
| areaTotalMetrosQuadrados | Double | Não (Validar apenas se preenchido) | @Pattern | Não | — | Area total metros quadrados |
| latitude | Double | Não (Validar apenas se preenchido) | @Pattern | Não | — | Latitude |
| longitude | Double | Não | — | Não | — | Longitude |
| observacoes | String | Não (Validar apenas se preenchido) | @Size(max=1000) | Não | — | Observacoes |

## Estados

### Classe Request: EstadosRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| sigla | String | Não | — | Não | — | Sigla |
| nome | String | Não | — | Não | — | Nome |
| codigoIbge | String | Não | — | Não | — | Codigo ibge |

## EstoquesVacina

### Classe Request: EstoquesVacinaRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| estabelecimento | UUID | Não | — | Não | — | Estabelecimento |
| vacina | UUID | Não | — | Não | — | Vacina |
| quantidadeDisponivel | Integer | Não | — | Não | — | Quantidade disponivel |
| quantidadeMinima | Integer | Não | — | Não | — | Quantidade minima |
| quantidadeMaxima | Integer | Não | — | Não | — | Quantidade maxima |
| localArmazenamento | String | Não | — | Não | — | Local armazenamento |
| temperaturaArmazenamento | String | Não | — | Não | — | Temperatura armazenamento |
| dataValidade | LocalDate | Não | — | Não | — | Data validade |

## Exames

### Classe Request: ExamesRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| paciente | UUID | Sim | @NotNull | Não | — | Paciente |
| catalogoExame | UUID | Sim | @NotNull | Não | — | Catalogo exame |
| atendimento | UUID | Sim | @NotNull | Não | — | Atendimento |
| consulta | UUID | Sim | @NotNull | Não | — | Consulta |
| profissionalSolicitante | UUID | Sim | @NotNull | Não | — | Profissional solicitante |
| medicoSolicitante | UUID | Sim | @NotNull | Não | — | Medico solicitante |
| tipoExame | String | Sim | @NotNull, @Size(max=100) | Não | — | Tipo exame |
| nomeExame | String | Sim | @NotNull, @Size(max=100), @Size(max=255) | Não | — | Nome exame |
| dataSolicitacao | OffsetDateTime | Sim | @NotNull, @Size(max=100), @Size(max=255) | Não | — | Data solicitacao |
| dataExame | OffsetDateTime | Sim | @NotNull, @Size(max=100), @Size(max=255) | Não | — | Data exame |
| dataResultado | OffsetDateTime | Sim | @NotNull, @Size(max=100), @Size(max=255) | Não | — | Data resultado |
| unidadeLaboratorio | String | Não (Validar apenas se preenchido) | @Size(max=100), @Size(max=255), @Size(max=255) | Não | — | Unidade laboratorio |
| estabelecimentoRealizador | UUID | Não (Validar apenas se preenchido) | @Size(max=100), @Size(max=255), @Size(max=255) | Não | — | Estabelecimento realizador |
| profissionalResponsavel | UUID | Não (Validar apenas se preenchido) | @Size(max=100), @Size(max=255), @Size(max=255) | Não | — | Profissional responsavel |
| medicoResponsavel | UUID | Não (Validar apenas se preenchido) | @Size(max=100), @Size(max=255), @Size(max=255) | Não | — | Medico responsavel |
| resultados | String | Não (Validar apenas se preenchido) | @Size(max=100), @Size(max=255), @Size(max=255), @Size(max=5000) | Não | — | Resultados |
| laudo | String | Não (Validar apenas se preenchido) | @Size(max=255), @Size(max=255), @Size(max=5000), @Size(max=5000) | Não | — | Laudo |
| observacoes | String | Não (Validar apenas se preenchido) | @Size(max=255), @Size(max=5000), @Size(max=5000), @Size(max=1000) | Não | — | Observacoes |

## FabricantesEquipamento

### Classe Request: FabricantesEquipamentoRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| nome | String | Não | — | Não | — | Nome |
| cnpj | String | Não | — | Não | 00.000.000/0000-00 | Cnpj |
| pais | String | Não | — | Não | — | Pais |
| estado | String | Não | — | Não | — | Estado |
| cidade | String | Não | — | Não | — | Cidade |
| endereco | String | Não | — | Não | — | Endereco |
| telefone | String | Não | — | Não | (00) 00000-0000 | Telefone |
| email | String | Não | — | Não | email | Email |
| site | String | Não | — | Não | — | Site |
| registroAnvisa | String | Não | — | Não | — | Registro anvisa |
| observacoes | String | Não | — | Não | — | Observacoes |

## FabricantesMedicamento

### Classe Request: FabricantesMedicamentoRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| nome | String | Não | — | Não | — | Nome |
| pais | String | Não | — | Não | — | Pais |
| contatoJson | String | Não | — | Não | — | Contato json |

## Fabricantes de Vacina

### Classe Request: FabricantesVacinaRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| nome | String | Não | — | Não | — | Nome |
| cnpj | String | Não | — | Não | 00.000.000/0000-00 | Cnpj |
| pais | String | Não | — | Não | — | Pais |
| estado | String | Não | — | Não | — | Estado |
| cidade | String | Não | — | Não | — | Cidade |
| endereco | EnderecoRequest | Não | — | Não | — | Endereco |
| telefone | String | Não | — | Não | (00) 00000-0000 | Telefone |
| email | String | Não | — | Não | email | Email |
| site | String | Não | — | Não | — | Site |
| registroAnvisa | String | Não | — | Não | — | Registro anvisa |
| registroMs | String | Não | — | Não | — | Registro ms |
| observacoes | String | Não | — | Não | — | Observacoes |

## Falta

### Classe Request: FaltaRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| profissional | UUID | Não | — | Não | — | Profissional |
| medico | UUID | Não | — | Não | — | Medico |
| dataFalta | LocalDate | Não | — | Não | — | Data falta |
| dataInicio | LocalDate | Não | — | Não | — | Data inicio |
| dataFim | LocalDate | Não | — | Não | — | Data fim |
| tipoFalta | TipoFaltaEnum | Não | — | Sim | — | Tipo falta |
| justificativa | String | Não | — | Não | — | Justificativa |
| documentoComprobatorio | String | Não | — | Não | — | Documento comprobatorio |
| numeroAtestado | String | Não | — | Não | — | Numero atestado |
| aprovado | Boolean | Não | — | Não | — | Aprovado |
| aprovadoPor | UUID | Não | — | Não | — | Aprovado por |
| dataAprovacao | OffsetDateTime | Não | — | Não | — | Data aprovacao |
| observacoes | String | Não | — | Não | — | Observacoes |

## FilaEspera

### Classe Request: FilaEsperaRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| paciente | UUID | Não | — | Não | — | Paciente |
| profissional | UUID | Não | — | Não | — | Profissional |
| medico | UUID | Não | — | Não | — | Medico |
| especialidade | UUID | Não | — | Não | — | Especialidade |
| agendamento | UUID | Não | — | Não | — | Agendamento |
| dataEntrada | OffsetDateTime | Não | — | Não | — | Data entrada |
| dataFimDesejada | LocalDate | Não | — | Não | — | Data fim desejada |
| prioridade | PrioridadeAtendimentoEnum | Não | — | Sim | — | Prioridade |
| posicaoFila | Integer | Não | — | Não | — | Posicao fila |
| motivo | String | Não | — | Não | — | Motivo |
| observacoes | String | Não | — | Não | — | Observacoes |
| notificado | Boolean | Não | — | Não | — | Notificado |
| dataNotificacao | OffsetDateTime | Não | — | Não | — | Data notificacao |
| notificacoesEnviadas | Integer | Não | — | Não | — | Notificacoes enviadas |
| aceitaQualquerHorario | Boolean | Não | — | Não | — | Aceita qualquer horario |
| telefoneContato | String | Não | — | Não | (00) 00000-0000 | Telefone contato |
| emailContato | String | Não | — | Não | email | Email contato |

## HistoricoClinico

### Classe Request: HistoricoClinicoRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| paciente | UUID | Não | — | Não | — | Paciente |
| profissional | UUID | Não | — | Não | — | Profissional |
| atendimento | UUID | Não | — | Não | — | Atendimento |
| agendamento | UUID | Não | — | Não | — | Agendamento |
| exame | UUID | Não | — | Não | — | Exame |
| receita | UUID | Não | — | Não | — | Receita |
| cirurgia | UUID | Não | — | Não | — | Cirurgia |
| dataRegistro | OffsetDateTime | Não | — | Não | — | Data registro |
| tipoRegistro | String | Não | — | Não | — | Tipo registro |
| titulo | String | Não | — | Não | — | Titulo |
| descricao | String | Não | — | Não | — | Descricao |
| observacoes | String | Não | — | Não | — | Observacoes |
| observacoesInternas | String | Não | — | Não | — | Observacoes internas |
| tags | String | Não | — | Não | — | Tags |
| registradoPor | UUID | Não | — | Não | — | Registrado por |
| revisadoPor | UUID | Não | — | Não | — | Revisado por |
| dataRevisao | OffsetDateTime | Não | — | Não | — | Data revisao |
| versao | Integer | Não | — | Não | — | Versao |
| visivelParaPaciente | Boolean | Não | — | Não | — | Visivel para paciente |
| compartilhadoOutrosEstabelecimentos | Boolean | Não | — | Não | — | Compartilhado outros estabelecimentos |

## HistoricoHabilitacaoProfissional

### Classe Request: HistoricoHabilitacaoProfissionalRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| profissional | UUID | Não | — | Não | — | Profissional |
| tipoEvento | String | Não | — | Não | — | Tipo evento |
| dataEvento | OffsetDateTime | Não | — | Não | — | Data evento |
| statusAnterior | StatusAtivoEnum | Não | — | Sim | — | Status anterior |
| statusNovo | StatusAtivoEnum | Não | — | Sim | — | Status novo |
| dataValidadeAnterior | OffsetDateTime | Não | — | Não | — | Data validade anterior |
| dataValidadeNova | OffsetDateTime | Não | — | Não | — | Data validade nova |
| numeroProcesso | String | Não | — | Não | — | Numero processo |
| observacoes | String | Não | — | Não | — | Observacoes |
| documentoReferencia | String | Não | — | Não | — | Documento referencia |
| usuarioResponsavel | UUID | Não | — | Não | — | Usuario responsavel |

## InfraestruturaEstabelecimento

### Classe Request: InfraestruturaEstabelecimentoRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| estabelecimento | UUID | Não | — | Não | — | Estabelecimento |
| tipo | String | Não | — | Não | — | Tipo |
| codigo | String | Não | — | Não | — | Codigo |
| codigoCnes | String | Não | — | Não | — | Codigo cnes |
| quantidade | Integer | Não | — | Não | — | Quantidade |
| capacidade | Integer | Não | — | Não | — | Capacidade |
| descricao | String | Não | — | Não | — | Descricao |

## IntegracaoGov

### Classe Request: IntegracaoGovRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| paciente | UUID | Não | — | Não | — | Paciente |
| uuidRnds | UUID | Não | — | Não | — | Uuid rnds |
| idIntegracaoGov | String | Não | — | Não | — | Id integracao gov |
| dataSincronizacaoGov | LocalDateTime | Não | — | Não | — | Data sincronizacao gov |
| ineEquipe | String | Não | — | Não | — | Ine equipe |
| microarea | String | Não | — | Não | — | Microarea |
| cnesEstabelecimentoOrigem | String | Não | — | Não | — | Cnes estabelecimento origem |
| origemCadastro | String | Não | — | Não | — | Origem cadastro |

## LGPDConsentimento

### Classe Request: LGPDConsentimentoRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| paciente | UUID | Não | — | Não | — | Paciente |
| autorizacaoUsoDados | Boolean | Não | — | Não | — | Autorizacao uso dados |
| autorizacaoContatoWhatsApp | Boolean | Não | — | Não | (00) 00000-0000 | Autorizacao contato whats app |
| autorizacaoContatoEmail | Boolean | Não | — | Não | email | Autorizacao contato email |
| dataConsentimento | LocalDateTime | Não | — | Não | — | Data consentimento |

## Login

### Classe Request: LoginRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| email | String | Sim | @NotBlank | Não | email | / |
| password | String | Sim | @NotBlank, @NotBlank | Não | — | / |

## Logs de Auditoria

### Classe Request: LogsAuditoriaRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| acao | String | Não | — | Não | — | Acao |
| entidade | String | Não | — | Não | — | Entidade |
| entidadeId | UUID | Não | — | Não | — | Entidade id |
| detalhes | String | Não | — | Não | — | Detalhes |
| realizadoPor | UUID | Não | — | Não | — | Realizado por |

## MedicacaoPaciente

### Classe Request: MedicacaoPacienteRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| paciente | UUID | Não | — | Não | — | Paciente |
| medicacao | UUID | Não | — | Não | — | Medicacao |
| dose | String | Não | — | Não | — | Dose |
| frequencia | FrequenciaMedicacaoEnum | Não | — | Sim | — | Frequencia |
| via | ViaAdministracaoEnum | Não | — | Sim | — | Via |
| cidRelacionado | UUID | Não | — | Não | — | Cid relacionado |
| dataInicio | LocalDate | Não | — | Não | — | Data inicio |
| dataFim | LocalDate | Não | — | Não | — | Data fim |
| medicacaoAtiva | Boolean | Não | — | Não | — | Medicacao ativa |
| observacoes | String | Não | — | Não | — | Observacoes |

## Medicação Paciente Simplificado

### Classe Request: MedicacaoPacienteSimplificadoRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| paciente | UUID | Sim | @NotNull | Não | — | Paciente |
| tenant | UUID | Sim | @NotNull, @NotNull | Não | — | Tenant |
| medicacao | UUID | Sim | @NotNull, @NotNull, @NotNull | Não | — | Medicacao |

## Medicação

### Classe Request: MedicacaoRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| fabricanteEntity | UUID | Não | — | Não | — | Fabricante entity |
| identificacao | IdentificacaoMedicamentoRequest | Não | — | Não | — | Identificacao |
| dosagemAdministracao | DosagemAdministracaoMedicamentoRequest | Não | — | Não | — | Dosagem administracao |
| classificacao | ClassificacaoMedicamentoRequest | Não | — | Não | — | Classificacao |
| registroControle | RegistroControleMedicamentoRequest | Não | — | Não | — | Registro controle |
| contraindicacoesPrecaucoes | ContraindicacoesPrecaucoesMedicamentoRequest | Não | — | Não | — | Contraindicacoes precaucoes |
| conservacaoArmazenamento | ConservacaoArmazenamentoMedicamentoRequest | Não | — | Não | — | Conservacao armazenamento |
| descricao | String | Não (Validar apenas se preenchido) | @Size(max=1000) | Não | — | Descricao |
| indicacoes | String | Não (Validar apenas se preenchido) | @Size(max=1000), @Size(max=1000) | Não | — | Indicacoes |
| observacoes | String | Não (Validar apenas se preenchido) | @Size(max=1000), @Size(max=1000), @Size(max=1000) | Não | — | Observacoes |

#### IdentificacaoMedicamentoRequest (identificacao)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| principioAtivo | String | Sim | @NotBlank, @Size(max=255) | Não | — | Principio ativo |
| nomeComercial | String | Sim | @NotBlank, @Size(max=255), @NotBlank, @Size(max=255) | Não | — | Nome comercial |
| nomeGenerico | String | Sim | @NotBlank, @Size(max=255), @NotBlank, @Size(max=255), @Size(max=255) | Não | — | Nome generico |
| codigoInterno | String | Sim | @NotBlank, @Size(max=255), @NotBlank, @Size(max=255), @Size(max=255), @Size(max=50) | Não | — | Codigo interno |
| catmatCodigo | String | Sim | @NotBlank, @Size(max=255), @NotBlank, @Size(max=255), @Size(max=255), @Size(max=50), @Size(max=20) | Não | — | Catmat codigo |
| codigoAnvisa | String | Sim | @NotBlank, @Size(max=255), @Size(max=255), @Size(max=50), @Size(max=20), @Size(max=50) | Não | — | Codigo anvisa |
| codigoTuss | String | Não (Validar apenas se preenchido) | @Size(max=255), @Size(max=50), @Size(max=20), @Size(max=50), @Size(max=50) | Não | — | Codigo tuss |
| codigoSigtap | String | Não (Validar apenas se preenchido) | @Size(max=50), @Size(max=20), @Size(max=50), @Size(max=50), @Size(max=50) | Não | — | Codigo sigtap |

#### DosagemAdministracaoMedicamentoRequest (dosagemAdministracao)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| dosagem | String | Sim | @NotBlank, @Size(max=50) | Não | — | Dosagem |
| unidadeMedida | UnidadeMedidaEnum | Sim | @NotBlank, @Size(max=50) | Sim | — | Unidade medida |
| viaAdministracao | ViaAdministracaoEnum | Sim | @NotBlank, @Size(max=50) | Sim | — | Via administracao |
| concentracao | BigDecimal | Sim | @NotBlank, @Size(max=50) | Não | — | Concentracao |
| unidadeConcentracao | String | Sim | @NotBlank, @Size(max=50), @Size(max=50) | Não | — | Unidade concentracao |
| posologiaPadrao | String | Sim | @NotBlank, @Size(max=50), @Size(max=50), @Size(max=100) | Não | — | Posologia padrao |
| instrucoesUso | String | Não (Validar apenas se preenchido) | @Size(max=50), @Size(max=100), @Size(max=255) | Não | — | Instrucoes uso |

#### ClassificacaoMedicamentoRequest (classificacao)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| classeTerapeutica | ClasseTerapeuticaEnum | Não | — | Sim | — | Classe terapeutica |
| formaFarmaceutica | FormaFarmaceuticaEnum | Não | — | Sim | — | Forma farmaceutica |
| categoria | String | Não (Validar apenas se preenchido) | @Size(max=100) | Não | — | Categoria |
| subcategoria | String | Não (Validar apenas se preenchido) | @Size(max=100), @Size(max=100) | Não | — | Subcategoria |
| codigoAtc | String | Não (Validar apenas se preenchido) | @Size(max=100), @Size(max=100), @Size(max=50) | Não | — | Codigo atc |

#### RegistroControleMedicamentoRequest (registroControle)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| registroAnvisa | String | Não (Validar apenas se preenchido) | @Size(max=50) | Não | — | Registro anvisa |
| dataRegistroAnvisa | LocalDate | Não (Validar apenas se preenchido) | @Size(max=50) | Não | — | Data registro anvisa |
| dataValidadeRegistroAnvisa | LocalDate | Não (Validar apenas se preenchido) | @Size(max=50) | Não | — | Data validade registro anvisa |
| tipoControle | TipoControleMedicamentoEnum | Não (Validar apenas se preenchido) | @Size(max=50) | Sim | — | Tipo controle |

#### ContraindicacoesPrecaucoesMedicamentoRequest (contraindicacoesPrecaucoes)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| contraindicacoes | String | Não | — | Não | — | Contraindicacoes |
| precaucoes | String | Não | — | Não | — | Precaucoes |
| interacoesMedicamentosas | String | Sim | @NotNull, @NotNull, @NotNull | Não | — | Interacoes medicamentosas |
| efeitosColaterais | String | Sim | @NotNull, @NotNull, @NotNull | Não | — | Efeitos colaterais |

#### ConservacaoArmazenamentoMedicamentoRequest (conservacaoArmazenamento)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| temperaturaConservacaoMin | BigDecimal | Não | — | Não | — | Temperatura conservacao min |
| temperaturaConservacaoMax | BigDecimal | Não | — | Não | — | Temperatura conservacao max |
| condicoesArmazenamento | String | Sim | @NotNull, @NotNull, @Size(max=255) | Não | — | Condicoes armazenamento |
| validadeAposAberturaDias | Integer | Sim | @NotNull, @NotNull, @Size(max=255) | Não | — | Validade apos abertura dias |
| instrucoesConservacao | String | Sim | @NotNull, @NotNull, @Size(max=255) | Não | — | Instrucoes conservacao |

## MedicacoesContinuasPaciente

### Classe Request: MedicacoesContinuasPacienteRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| paciente | UUID | Não | — | Não | — | Paciente |
| medicacaoContinua | UUID | Não | — | Não | — | Medicacao continua |
| dosagemAtual | String | Não | — | Não | — | Dosagem atual |
| frequenciaAdministracao | String | Não | — | Não | — | Frequencia administracao |
| dataInicio | LocalDate | Não | — | Não | — | Data inicio |
| observacoes | String | Não | — | Não | — | Observacoes |

## MedicacoesContinuas

### Classe Request: MedicacoesContinuasRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| nome | String | Não | — | Não | — | Nome |
| dosagem | String | Não | — | Não | — | Dosagem |
| fabricante | String | Não | — | Não | — | Fabricante |

## MedicaoClinica

### Classe Request: MedicaoClinicaRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| paciente | UUID | Não | — | Não | — | Paciente |
| dataHora | OffsetDateTime | Não | — | Não | — | Data hora |
| pressaoSistolica | Integer | Não | — | Não | — | Pressao sistolica |
| pressaoDiastolica | Integer | Não | — | Não | — | Pressao diastolica |
| frequenciaCardiaca | Integer | Não | — | Não | — | Frequencia cardiaca |
| frequenciaRespiratoria | Integer | Não | — | Não | — | Frequencia respiratoria |
| temperatura | BigDecimal | Não | — | Não | — | Temperatura |
| saturacaoOxigenio | Integer | Não | — | Não | — | Saturacao oxigenio |
| glicemiaCapilar | BigDecimal | Não | — | Não | — | Glicemia capilar |
| peso | BigDecimal | Não | — | Não | — | Peso |
| altura | BigDecimal | Não | — | Não | — | Altura |
| circunferenciaAbdominal | BigDecimal | Não | — | Não | — | Circunferencia abdominal |
| imc | BigDecimal | Não | — | Não | — | Imc |
| observacoes | String | Não | — | Não | — | Observacoes |

## MedicoEstabelecimento

### Classe Request: MedicoEstabelecimentoRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| medico | UUID | Não | — | Não | — | Medico |
| estabelecimento | UUID | Não | — | Não | — | Estabelecimento |
| dataInicio | OffsetDateTime | Não | — | Não | — | Data inicio |
| dataFim | OffsetDateTime | Não | — | Não | — | Data fim |
| tipoVinculo | TipoVinculoProfissionalEnum | Não | — | Sim | — | Tipo vinculo |
| cargaHorariaSemanal | Integer | Não | — | Não | — | Carga horaria semanal |
| salario | BigDecimal | Não | — | Não | — | Salario |
| numeroMatricula | String | Não | — | Não | — | Numero matricula |
| setorDepartamento | String | Não | — | Não | — | Setor departamento |
| cargoFuncao | String | Não | — | Não | — | Cargo funcao |
| observacoes | String | Não | — | Não | — | Observacoes |

## Médicos

### Classe Request: MedicosRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| especialidade | UUID | Não | — | Não | — | Especialidade |
| nomeCompleto | String | Sim | @NotBlank, @Size(max=255) | Não | — | Nome completo |
| dadosPessoais | DadosPessoaisMedicoRequest | Sim | @NotBlank, @Size(max=255) | Não | — | Dados pessoais |
| registroProfissional | RegistroProfissionalMedicoRequest | Sim | @NotBlank, @Size(max=255) | Não | — | Registro profissional |
| formacao | FormacaoMedicoRequest | Sim | @NotBlank, @Size(max=255) | Não | — | Formacao |
| contato | ContatoMedicoRequest | Sim | @NotBlank, @Size(max=255) | Não | — | Contato |
| observacoes | String | Não (Validar apenas se preenchido) | @Size(max=1000) | Não | — | Observacoes |

#### DadosPessoaisMedicoRequest (dadosPessoais)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| nomeSocial | String | Não (Validar apenas se preenchido) | @Size(max=255) | Não | — | Nome social |
| cpf | String | Não (Validar apenas se preenchido) | @Size(max=255), @Pattern | Não | 000.000.000-00 | Cpf |
| sexo | SexoEnum | Não (Validar apenas se preenchido) | @Size(max=255), @Pattern | Sim | — | Sexo |
| dataNascimento | LocalDate | Não (Validar apenas se preenchido) | @Size(max=255), @Pattern | Não | — | Data nascimento |
| estadoCivil | EstadoCivilEnum | Não (Validar apenas se preenchido) | @Size(max=255), @Pattern | Sim | — | Estado civil |
| rg | String | Não (Validar apenas se preenchido) | @Size(max=255), @Pattern, @Size(max=20) | Não | — | Rg |
| orgaoEmissorRg | String | Não (Validar apenas se preenchido) | @Pattern, @Size(max=20), @Size(max=10) | Não | — | Orgao emissor rg |
| ufEmissorRg | String | Não (Validar apenas se preenchido) | @Size(max=20), @Size(max=10), @Pattern, @Size(max=2) | Não | — | Uf emissor rg |
| nacionalidade | String | Não (Validar apenas se preenchido) | @Size(max=20), @Size(max=10), @Pattern, @Size(max=2), @Size(max=20) | Não | — | Nacionalidade |
| naturalidade | String | Não (Validar apenas se preenchido) | @Size(max=20), @Size(max=10), @Pattern, @Size(max=2), @Size(max=20), @Size(max=50) | Não | — | Naturalidade |

#### RegistroProfissionalMedicoRequest (registroProfissional)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| crm | String | Sim | @NotBlank, @Pattern | Não | — | Crm |
| crmUf | String | Sim | @NotBlank, @Pattern, @Pattern | Não | — | Crm uf |
| statusCrm | StatusRegistroMedicoEnum | Sim | @NotBlank, @Pattern, @Pattern | Sim | — | Status crm |
| dataEmissaoCrm | LocalDate | Sim | @NotBlank, @Pattern, @Pattern | Não | — | Data emissao crm |
| dataValidadeCrm | LocalDate | Sim | @NotBlank, @Pattern, @Pattern | Não | — | Data validade crm |
| crmComplementar | String | Sim | @NotBlank, @Pattern, @Pattern, @Size(max=50) | Não | — | Crm complementar |
| observacoesCrm | String | Não (Validar apenas se preenchido) | @Pattern, @Size(max=50), @Size(max=255) | Não | — | Observacoes crm |

#### FormacaoMedicoRequest (formacao)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| instituicaoEnsino | String | Não (Validar apenas se preenchido) | @Size(max=255) | Não | — | Instituicao ensino |
| anoFormatura | Integer | Não (Validar apenas se preenchido) | @Size(max=255) | Não | — | Ano formatura |
| dataFormatura | LocalDate | Não (Validar apenas se preenchido) | @Size(max=255) | Não | — | Data formatura |
| especializacao | String | Não (Validar apenas se preenchido) | @Size(max=255), @Size(max=255) | Não | — | Especializacao |
| residenciaMedica | String | Não (Validar apenas se preenchido) | @Size(max=255), @Size(max=255), @Size(max=255) | Não | — | Residencia medica |
| mestrado | String | Não (Validar apenas se preenchido) | @Size(max=255), @Size(max=255), @Size(max=255), @Size(max=255) | Não | — | Mestrado |
| doutorado | String | Não (Validar apenas se preenchido) | @Size(max=255), @Size(max=255), @Size(max=255), @Size(max=255) | Não | — | Doutorado |
| posGraduacao | String | Não (Validar apenas se preenchido) | @Size(max=255), @Size(max=255), @Size(max=255), @Size(max=255), @Size(max=255) | Não | — | Pos graduacao |
| rqe | String | Sim | @Size(max=255), @Size(max=255), @Size(max=255), @NotNull, @Size(max=50) | Não | — | Rqe |

#### ContatoMedicoRequest (contato)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| telefone | String | Não (Validar apenas se preenchido) | @Pattern | Não | (00) 00000-0000 | Telefone |
| telefoneCelular | String | Não (Validar apenas se preenchido) | @Pattern, @Pattern | Não | (00) 00000-0000 | Telefone celular |
| whatsapp | String | Não (Validar apenas se preenchido) | @Pattern, @Pattern, @Pattern | Não | (00) 00000-0000 | Whatsapp |
| email | String | Não (Validar apenas se preenchido) | @Pattern, @Pattern, @Pattern, @Email, @Size(max=255) | Não | email | Email |
| emailInstitucional | String | Não (Validar apenas se preenchido) | @Pattern, @Pattern, @Pattern, @Email, @Size(max=255), @Email, @Size(max=255) | Não | email | Email institucional |
| site | String | Não (Validar apenas se preenchido) | @Pattern, @Pattern, @Email, @Size(max=255), @Email, @Size(max=255), @Size(max=255) | Não | email | Site |

## MovimentacoesEstoque

### Classe Request: MovimentacoesEstoqueRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| estoqueVacina | UUID | Não | — | Não | — | Estoque vacina |
| tipoMovimento | String | Não | — | Não | — | Tipo movimento |
| quantidade | Integer | Não | — | Não | — | Quantidade |
| motivo | String | Não | — | Não | — | Motivo |
| responsavel | UUID | Não | — | Não | — | Responsavel |
| dataMovimento | OffsetDateTime | Não | — | Não | — | Data movimento |

## Notificação

### Classe Request: NotificacaoRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| paciente | UUID | Não | — | Não | — | Paciente |
| profissional | UUID | Não | — | Não | — | Profissional |
| agendamento | UUID | Não | — | Não | — | Agendamento |
| template | UUID | Não | — | Não | — | Template |
| tipoNotificacao | TipoNotificacaoEnum | Sim | @NotNull | Sim | — | Tipo notificacao |
| canal | CanalNotificacaoEnum | Sim | @NotNull, @NotNull | Sim | — | Canal |
| destinatario | String | Sim | @NotNull, @NotNull, @NotBlank, @Size(max=255) | Não | — | Destinatario |
| assunto | String | Sim | @NotNull, @NotNull, @NotBlank, @Size(max=255), @Size(max=500) | Não | — | Assunto |
| mensagem | String | Sim | @NotNull, @NotNull, @NotBlank, @Size(max=255), @Size(max=500), @NotBlank | Não | — | Mensagem |
| statusEnvio | String | Sim | @NotNull, @NotNull, @NotBlank, @Size(max=255), @Size(max=500), @NotBlank | Não | — | Status envio |
| dataEnvioPrevista | OffsetDateTime | Sim | @NotNull, @NotNull, @NotBlank, @Size(max=255), @Size(max=500), @NotBlank | Não | — | Data envio prevista |
| dataEnvio | OffsetDateTime | Sim | @NotNull, @NotBlank, @Size(max=255), @Size(max=500), @NotBlank | Não | — | Data envio |
| dataLeitura | OffsetDateTime | Sim | @NotNull, @NotBlank, @Size(max=255), @Size(max=500), @NotBlank | Não | — | Data leitura |
| tentativasEnvio | Integer | Sim | @NotNull, @NotBlank, @Size(max=255), @Size(max=500), @NotBlank | Não | — | Tentativas envio |
| maximoTentativas | Integer | Sim | @NotBlank, @Size(max=255), @Size(max=500), @NotBlank | Não | — | Maximo tentativas |
| erroEnvio | String | Sim | @NotBlank, @Size(max=255), @Size(max=500), @NotBlank | Não | — | Erro envio |
| logEnvio | String | Sim | @NotBlank, @Size(max=255), @Size(max=500), @NotBlank | Não | — | Log envio |
| idExterno | String | Sim | @Size(max=255), @Size(max=500), @NotBlank | Não | — | Id externo |
| parametrosJson | String | Sim | @Size(max=500), @NotBlank | Não | — | Parametros json |
| enviadoPor | UUID | Sim | @Size(max=500), @NotBlank | Não | — | Enviado por |

## Pacientes

### Classe Request: PacienteRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| nomeCompleto | String | Sim | @NotBlank, @Size(max=255) | Não | — | Nome completo |
| cpf | String | Sim | @NotBlank, @Size(max=255), @Pattern | Não | 000.000.000-00 | Cpf |
| rg | String | Sim | @NotBlank, @Size(max=255), @Pattern, @Size(max=20) | Não | — | Rg |
| cns | String | Sim | @NotBlank, @Size(max=255), @Pattern, @Size(max=20), @Pattern | Não | — | Cns |
| dataNascimento | LocalDate | Sim | @NotBlank, @Size(max=255), @Pattern, @Size(max=20), @Pattern | Não | — | Data nascimento |
| sexo | SexoEnum | Não (Validar apenas se preenchido) | @Size(max=255), @Pattern, @Size(max=20), @Pattern | Sim | — | Sexo |
| estadoCivil | EstadoCivilEnum | Não (Validar apenas se preenchido) | @Pattern, @Size(max=20), @Pattern | Sim | — | Estado civil |
| telefone | String | Não (Validar apenas se preenchido) | @Size(max=20), @Pattern, @Pattern | Não | (00) 00000-0000 | Telefone |
| email | String | Não (Validar apenas se preenchido) | @Pattern, @Email, @Size(max=255) | Não | email | Email |
| nomeMae | String | Não (Validar apenas se preenchido) | @Pattern, @Email, @Size(max=255), @Size(max=255) | Não | email | Nome mae |
| nomePai | String | Não (Validar apenas se preenchido) | @Pattern, @Email, @Size(max=255), @Size(max=255), @Size(max=255) | Não | email | Nome pai |
| responsavelNome | String | Não (Validar apenas se preenchido) | @Pattern, @Email, @Size(max=255), @Size(max=255), @Size(max=255), @Size(max=255) | Não | email | Responsavel nome |
| responsavelCpf | String | Não (Validar apenas se preenchido) | @Email, @Size(max=255), @Size(max=255), @Size(max=255), @Size(max=255), @Pattern | Não | 000.000.000-00 | Responsavel cpf |
| responsavelTelefone | String | Não (Validar apenas se preenchido) | @Size(max=255), @Size(max=255), @Size(max=255), @Pattern, @Pattern | Não | (00) 00000-0000 | Responsavel telefone |
| convenio | UUID | Não (Validar apenas se preenchido) | @Size(max=255), @Size(max=255), @Size(max=255), @Pattern, @Pattern | Não | — | Convenio |
| numeroCarteirinha | String | Não (Validar apenas se preenchido) | @Size(max=255), @Size(max=255), @Size(max=255), @Pattern, @Pattern | Não | — | Numero carteirinha |
| dataValidadeCarteirinha | LocalDate | Não (Validar apenas se preenchido) | @Size(max=255), @Size(max=255), @Pattern, @Pattern | Não | — | Data validade carteirinha |
| observacoes | String | Não (Validar apenas se preenchido) | @Size(max=255), @Size(max=255), @Pattern, @Pattern, @Size(max=1000) | Não | — | Observacoes |
| racaCor | RacaCorEnum | Não (Validar apenas se preenchido) | @Size(max=255), @Pattern, @Pattern, @Size(max=1000) | Sim | — | Raca cor |
| nacionalidade | NacionalidadeEnum | Não (Validar apenas se preenchido) | @Pattern, @Pattern, @Size(max=1000) | Sim | — | Nacionalidade |
| paisNascimento | String | Não (Validar apenas se preenchido) | @Pattern, @Pattern, @Size(max=1000) | Não | — | Pais nascimento |
| naturalidade | String | Não (Validar apenas se preenchido) | @Pattern, @Pattern, @Size(max=1000) | Não | — | Naturalidade |
| municipioNascimentoIbge | String | Não (Validar apenas se preenchido) | @Pattern, @Size(max=1000) | Não | — | Municipio nascimento ibge |
| escolaridade | EscolaridadeEnum | Não (Validar apenas se preenchido) | @Pattern, @Size(max=1000) | Sim | — | Escolaridade |
| ocupacaoProfissao | String | Não (Validar apenas se preenchido) | @Size(max=1000) | Não | — | Ocupacao profissao |
| situacaoRua | Boolean | Não (Validar apenas se preenchido) | @Size(max=1000) | Não | — | Situacao rua |
| statusPaciente | StatusPacienteEnum | Não (Validar apenas se preenchido) | @Size(max=1000) | Sim | — | Status paciente |
| dataObito | LocalDate | Não (Validar apenas se preenchido) | @Size(max=1000) | Não | — | Data obito |
| causaObitoCid10 | String | Não | — | Não | — | Causa obito cid10 |
| cartaoSusAtivo | Boolean | Não | — | Não | — | Cartao sus ativo |
| dataAtualizacaoCns | LocalDate | Não | — | Não | — | Data atualizacao cns |
| tipoAtendimentoPreferencial | TipoAtendimentoPreferencialEnum | Não | — | Sim | — | Tipo atendimento preferencial |
| origemCadastro | String | Não | — | Não | — | Origem cadastro |
| nomeSocial | String | Não | — | Não | — | Nome social |
| identidadeGenero | IdentidadeGeneroEnum | Não | — | Sim | — | Identidade genero |
| orientacaoSexual | OrientacaoSexualEnum | Não | — | Sim | — | Orientacao sexual |
| possuiDeficiencia | Boolean | Não | — | Não | — | Possui deficiencia |
| tipoDeficiencia | String | Não | — | Não | — | Tipo deficiencia |
| cnsValidado | Boolean | Não | — | Não | — | Cns validado |
| tipoCns | TipoCnsEnum | Não | — | Sim | — | Tipo cns |
| acompanhadoPorEquipeEsf | Boolean | Não | — | Não | — | Acompanhado por equipe esf |
| dadosSociodemograficos | UUID | Não | — | Não | — | Dados sociodemograficos |
| dadosClinicosBasicos | UUID | Não | — | Não | — | Dados clinicos basicos |
| responsavelLegal | UUID | Não | — | Não | — | Responsavel legal |
| lgpdConsentimento | UUID | Não | — | Não | — | Lgpd consentimento |
| integracaoGov | UUID | Não | — | Não | — | Integracao gov |

## PerfisUsuarios

### Classe Request: PerfisUsuariosRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| usuarioId | UUID | Não | — | Não | — | Usuario id |

## Permissoes

### Classe Request: PermissoesRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| nome | String | Não | — | Não | — | Nome |
| descricao | String | Não | — | Não | — | Descricao |
| modulo | String | Não | — | Não | — | Modulo |

## PlanejamentoFamiliar

### Classe Request: PlanejamentoFamiliarRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| paciente | UUID | Não | — | Não | — | Paciente |
| profissionalResponsavel | UUID | Não | — | Não | — | Profissional responsavel |
| equipeSaude | UUID | Não | — | Não | — | Equipe saude |
| metodoAtual | TipoMetodoContraceptivoEnum | Não | — | Sim | — | Metodo atual |
| dataInicioMetodo | LocalDate | Não | — | Não | — | Data inicio metodo |
| nomeComercialMetodo | String | Não | — | Não | — | Nome comercial metodo |
| metodoAnterior | TipoMetodoContraceptivoEnum | Não | — | Sim | — | Metodo anterior |
| motivoTrocaMetodo | String | Não | — | Não | — | Motivo troca metodo |
| numeroGestacoes | Integer | Não | — | Não | — | Numero gestacoes |
| numeroPartos | Integer | Não | — | Não | — | Numero partos |
| numeroAbortos | Integer | Não | — | Não | — | Numero abortos |
| numeroFilhosVivos | Integer | Não | — | Não | — | Numero filhos vivos |
| dataUltimoParto | LocalDate | Não | — | Não | — | Data ultimo parto |
| ultimaGestacaoPlanejada | Boolean | Não | — | Não | — | Ultima gestacao planejada |
| desejaEngravidar | Boolean | Não | — | Não | — | Deseja engravidar |
| prazoDesejoGestacao | String | Não | — | Não | — | Prazo desejo gestacao |
| desejaMetodoDefinitivo | Boolean | Não | — | Não | — | Deseja metodo definitivo |
| temContraindicacoes | Boolean | Não | — | Não | — | Tem contraindicacoes |
| contraindicacoes | String | Não | — | Não | — | Contraindicacoes |
| doencasPreexistentes | String | Não | — | Não | — | Doencas preexistentes |
| medicamentosUso | String | Não | — | Não | — | Medicamentos uso |
| alergias | String | Não | — | Não | — | Alergias |
| cicloMenstrualRegular | Boolean | Não | — | Não | — | Ciclo menstrual regular |
| duracaoCiclo | Integer | Não | — | Não | — | Duracao ciclo |
| dataUltimaMenstruacao | LocalDate | Não | — | Não | — | Data ultima menstruacao |
| dismenorreia | Boolean | Não | — | Não | — | Dismenorreia |
| sangramentoIrregular | Boolean | Não | — | Não | — | Sangramento irregular |
| dataInicioAcompanhamento | LocalDate | Não | — | Não | — | Data inicio acompanhamento |
| dataProximaConsulta | OffsetDateTime | Não | — | Não | — | Data proxima consulta |
| dataProximaDispensacao | LocalDate | Não | — | Não | — | Data proxima dispensacao |
| dataInsercaoDiu | LocalDate | Não | — | Não | — | Data insercao diu |
| dataValidadeDiu | LocalDate | Não | — | Não | — | Data validade diu |
| dataCirurgia | LocalDate | Não | — | Não | — | Data cirurgia |
| localCirurgia | String | Não | — | Não | — | Local cirurgia |
| documentacaoCompleta | Boolean | Não | — | Não | — | Documentacao completa |
| prazoMinimoCumprido | Boolean | Não | — | Não | — | Prazo minimo cumprido |
| orientacaoMetodosRealizada | Boolean | Não | — | Não | — | Orientacao metodos realizada |
| dataOrientacao | LocalDate | Não | — | Não | — | Data orientacao |
| consentimentoInformado | Boolean | Não | — | Não | — | Consentimento informado |
| dataConsentimento | LocalDate | Não | — | Não | — | Data consentimento |
| observacoes | String | Não | — | Não | — | Observacoes |

## Plantao

### Classe Request: PlantaoRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| profissional | UUID | Não | — | Não | — | Profissional |
| medico | UUID | Não | — | Não | — | Medico |
| dataHoraInicio | OffsetDateTime | Não | — | Não | — | Data hora inicio |
| dataHoraFim | OffsetDateTime | Não | — | Não | — | Data hora fim |
| dataHoraFimPrevisto | OffsetDateTime | Não | — | Não | — | Data hora fim previsto |
| tipoPlantao | TipoPlantaoEnum | Não | — | Sim | — | Tipo plantao |
| setor | String | Não | — | Não | — | Setor |
| leitoInicio | Integer | Não | — | Não | — | Leito inicio |
| leitoFim | Integer | Não | — | Não | — | Leito fim |
| valorPlantao | BigDecimal | Não | — | Não | — | Valor plantao |
| temHoraExtra | Boolean | Não | — | Não | — | Tem hora extra |
| valorHoraExtra | BigDecimal | Não | — | Não | — | Valor hora extra |
| confirmado | Boolean | Não | — | Não | — | Confirmado |
| dataConfirmacao | OffsetDateTime | Não | — | Não | — | Data confirmacao |
| cancelado | Boolean | Não | — | Não | — | Cancelado |
| dataCancelamento | OffsetDateTime | Não | — | Não | — | Data cancelamento |
| motivoCancelamento | String | Não | — | Não | — | Motivo cancelamento |
| observacoes | String | Não | — | Não | — | Observacoes |

## PreNatal

### Classe Request: PreNatalRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| paciente | UUID | Não | — | Não | — | Paciente |
| profissionalResponsavel | UUID | Não | — | Não | — | Profissional responsavel |
| equipeSaude | UUID | Não | — | Não | — | Equipe saude |
| dataUltimaMenstruacao | LocalDate | Não | — | Não | — | Data ultima menstruacao |
| dataProvavelParto | LocalDate | Não | — | Não | — | Data provavel parto |
| idadeGestacionalCadastro | Integer | Não | — | Não | — | Idade gestacional cadastro |
| gestacoesAnteriores | Integer | Não | — | Não | — | Gestacoes anteriores |
| partos | Integer | Não | — | Não | — | Partos |
| abortos | Integer | Não | — | Não | — | Abortos |
| filhosVivos | Integer | Não | — | Não | — | Filhos vivos |
| partosVaginais | Integer | Não | — | Não | — | Partos vaginais |
| cesareas | Integer | Não | — | Não | — | Cesareas |
| partosPrematuros | Integer | Não | — | Não | — | Partos prematuros |
| natimortos | Integer | Não | — | Não | — | Natimortos |
| classificacaoRisco | ClassificacaoRiscoGestacionalEnum | Não | — | Sim | — | Classificacao risco |
| motivosAltoRisco | String | Não | — | Não | — | Motivos alto risco |
| dataInicioAcompanhamento | LocalDate | Não | — | Não | — | Data inicio acompanhamento |
| dataEncerramento | LocalDate | Não | — | Não | — | Data encerramento |
| dataParto | OffsetDateTime | Não | — | Não | — | Data parto |
| idadeGestacionalParto | Integer | Não | — | Não | — | Idade gestacional parto |
| tipoParto | String | Não | — | Não | — | Tipo parto |
| localParto | String | Não | — | Não | — | Local parto |
| pesoNascimento | BigDecimal | Não | — | Não | — | Peso nascimento |
| comprimentoNascimento | BigDecimal | Não | — | Não | — | Comprimento nascimento |
| apgar1Minuto | Integer | Não | — | Não | — | Apgar1 minuto |
| apgar5Minutos | Integer | Não | — | Não | — | Apgar5 minutos |
| perimetroCefalico | BigDecimal | Não | — | Não | — | Perimetro cefalico |
| tipoSanguineo | String | Não | — | Não | — | Tipo sanguineo |
| fatorRh | String | Não | — | Não | — | Fator rh |
| pesoPreGestacional | BigDecimal | Não | — | Não | — | Peso pre gestacional |
| altura | BigDecimal | Não | — | Não | — | Altura |
| imcPreGestacional | BigDecimal | Não | — | Não | — | Imc pre gestacional |
| dataPrimeiraUltrassonografia | LocalDate | Não | — | Não | — | Data primeira ultrassonografia |
| antecedentesFamiliares | String | Não | — | Não | — | Antecedentes familiares |
| antecedentesPessoais | String | Não | — | Não | — | Antecedentes pessoais |
| observacoes | String | Não | — | Não | — | Observacoes |
| observacoesInternas | String | Não | — | Não | — | Observacoes internas |

## Procedimento Cirúrgico

### Classe Request: ProcedimentoCirurgicoRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| cirurgia | UUID | Sim | @NotNull | Não | — | Cirurgia |
| descricao | String | Sim | @NotNull, @NotBlank | Não | — | Descricao |
| codigoProcedimento | String | Sim | @NotNull, @NotBlank, @Size(max=50) | Não | — | Codigo procedimento |
| nomeProcedimento | String | Sim | @NotNull, @NotBlank, @Size(max=50), @Size(max=500) | Não | — | Nome procedimento |
| valorUnitario | BigDecimal | Sim | @NotNull, @NotBlank, @Size(max=50), @Size(max=500), @NotNull | Não | — | Valor unitario |
| valorTotal | BigDecimal | Sim | @NotBlank, @Size(max=50), @Size(max=500), @NotNull | Não | — | Valor total |
| lado | String | Sim | @Size(max=50), @Size(max=500), @NotNull, @Size(max=20) | Não | — | Lado |
| observacoes | String | Sim | @Size(max=50), @Size(max=500), @NotNull, @Size(max=20) | Não | — | Observacoes |

## Procedimentos Odontológicos

### Classe Request: ProcedimentosOdontologicosRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| nome | String | Não | — | Não | — | Nome |
| codigo | String | Não | — | Não | — | Codigo |
| descricao | String | Não | — | Não | — | Descricao |
| duracaoMinutos | Integer | Não | — | Não | — | Duracao minutos |
| custoSugerido | BigDecimal | Não | — | Não | — | Custo sugerido |

## Profissionais de Saúde

### Classe Request: ProfissionaisSaudeRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| nomeCompleto | String | Sim | @NotBlank, @Size(max=255) | Não | — | Nome completo |
| cpf | String | Sim | @NotBlank, @Size(max=255), @Pattern | Não | 000.000.000-00 | Cpf |
| dataNascimento | LocalDate | Sim | @NotBlank, @Size(max=255), @Pattern | Não | — | Data nascimento |
| sexo | SexoEnum | Sim | @NotBlank, @Size(max=255), @Pattern | Sim | — | Sexo |
| estadoCivil | EstadoCivilEnum | Sim | @NotBlank, @Size(max=255), @Pattern | Sim | — | Estado civil |
| escolaridade | EscolaridadeEnum | Sim | @NotBlank, @Size(max=255), @Pattern | Sim | — | Escolaridade |
| identidadeGenero | IdentidadeGeneroEnum | Sim | @NotBlank, @Size(max=255), @Pattern | Sim | — | Identidade genero |
| racaCor | RacaCorEnum | Sim | @NotBlank, @Size(max=255), @Pattern | Sim | — | Raca cor |
| tipoDeficiencia | TipoDeficienciaEnum | Sim | @NotBlank, @Size(max=255), @Pattern | Sim | — | Tipo deficiencia |
| rg | String | Sim | @NotBlank, @Size(max=255), @Pattern | Não | — | Rg |
| orgaoEmissorRg | String | Sim | @NotBlank, @Size(max=255), @Pattern | Não | — | Orgao emissor rg |
| ufEmissaoRg | String | Sim | @NotBlank, @Size(max=255), @Pattern | Não | — | Uf emissao rg |
| dataEmissaoRg | LocalDate | Não (Validar apenas se preenchido) | @Size(max=255), @Pattern | Não | — | Data emissao rg |
| nacionalidade | NacionalidadeEnum | Não (Validar apenas se preenchido) | @Pattern | Sim | — | Nacionalidade |
| naturalidade | String | Não (Validar apenas se preenchido) | @Pattern | Não | — | Naturalidade |
| registroProfissional | String | Sim | @NotBlank, @Size(max=20) | Não | — | Registro profissional |
| conselho | UUID | Sim | @NotBlank, @Size(max=20), @NotNull | Não | — | Conselho |
| ufRegistro | String | Sim | @NotBlank, @Size(max=20), @NotNull | Não | — | Uf registro |
| dataEmissaoRegistro | OffsetDateTime | Sim | @NotBlank, @Size(max=20), @NotNull | Não | — | Data emissao registro |
| dataValidadeRegistro | OffsetDateTime | Sim | @NotBlank, @Size(max=20), @NotNull | Não | — | Data validade registro |
| statusRegistro | StatusAtivoEnum | Sim | @NotBlank, @Size(max=20), @NotNull | Sim | — | Status registro |
| tipoProfissional | TipoProfissionalEnum | Sim | @NotBlank, @Size(max=20), @NotNull | Sim | — | Tipo profissional |
| cns | String | Sim | @NotBlank, @Size(max=20), @NotNull | Não | — | Cns |
| codigoCbo | String | Sim | @NotBlank, @Size(max=20), @NotNull | Não | — | Codigo cbo |
| descricaoCbo | String | Sim | @NotBlank, @Size(max=20), @NotNull | Não | — | Descricao cbo |
| codigoOcupacional | String | Sim | @NotBlank, @Size(max=20), @NotNull | Não | — | Codigo ocupacional |
| telefone | String | Sim | @Size(max=20), @NotNull, @Pattern | Não | (00) 00000-0000 | Telefone |
| email | String | Não (Validar apenas se preenchido) | @Pattern, @Email, @Size(max=255) | Não | email | Email |
| telefoneInstitucional | String | Não (Validar apenas se preenchido) | @Pattern, @Email, @Size(max=255), @Pattern | Não | (00) 00000-0000 | Telefone institucional |
| emailInstitucional | String | Não (Validar apenas se preenchido) | @Pattern, @Email, @Size(max=255), @Pattern, @Email, @Size(max=255) | Não | email | Email institucional |
| enderecoProfissional | UUID | Não | — | Não | — | / |
| enderecoProfissionalCompleto | EnderecoRequest | Não | — | Não | — | / |
| observacoes | String | Não (Validar apenas se preenchido) | @Size(max=1000) | Não | — | / |

## ProfissionalEstabelecimento

### Classe Request: ProfissionalEstabelecimentoRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| profissional | UUID | Não | — | Não | — | Profissional |
| estabelecimento | UUID | Não | — | Não | — | Estabelecimento |
| dataInicio | OffsetDateTime | Não | — | Não | — | Data inicio |
| dataFim | OffsetDateTime | Não | — | Não | — | Data fim |
| tipoVinculo | TipoVinculoProfissionalEnum | Não | — | Sim | — | Tipo vinculo |
| cargaHorariaSemanal | Integer | Não | — | Não | — | Carga horaria semanal |
| salario | BigDecimal | Não | — | Não | — | Salario |
| numeroMatricula | String | Não | — | Não | — | Numero matricula |
| setorDepartamento | String | Não | — | Não | — | Setor departamento |
| cargoFuncao | String | Não | — | Não | — | Cargo funcao |
| observacoes | String | Não | — | Não | — | Observacoes |

## Prontuários

### Classe Request: ProntuariosRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| paciente | UUID | Sim | @NotNull, @NotNull | Não | — | Paciente |
| tipoRegistro | String | Sim | @NotNull, @NotNull, @Size(max=50) | Não | — | Tipo registro |
| conteudo | String | Sim | @NotNull, @NotNull, @Size(max=50), @Size(max=10000) | Não | — | Conteudo |
| criadoPor | UUID | Sim | @NotNull, @NotNull, @Size(max=50), @Size(max=10000) | Não | — | Criado por |

## Puericultura

### Classe Request: PuericulturaRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| paciente | UUID | Não | — | Não | — | Paciente |
| profissionalResponsavel | UUID | Não | — | Não | — | Profissional responsavel |
| equipeSaude | UUID | Não | — | Não | — | Equipe saude |
| dataNascimento | LocalDate | Não | — | Não | — | Data nascimento |
| pesoNascimento | BigDecimal | Não | — | Não | — | Peso nascimento |
| comprimentoNascimento | BigDecimal | Não | — | Não | — | Comprimento nascimento |
| perimetroCefalicoNascimento | BigDecimal | Não | — | Não | — | Perimetro cefalico nascimento |
| apgar1Minuto | Integer | Não | — | Não | — | Apgar1 minuto |
| apgar5Minutos | Integer | Não | — | Não | — | Apgar5 minutos |
| tipoParto | String | Não | — | Não | — | Tipo parto |
| idadeGestacionalNascimento | Integer | Não | — | Não | — | Idade gestacional nascimento |
| prematuro | Boolean | Não | — | Não | — | Prematuro |
| nomeMae | String | Não | — | Não | — | Nome mae |
| tipoSanguineoMae | String | Não | — | Não | — | Tipo sanguineo mae |
| numeroConsultasPreNatal | Integer | Não | — | Não | — | Numero consultas pre natal |
| intercorrenciasGestacao | String | Não | — | Não | — | Intercorrencias gestacao |
| aleitamentoMaternoExclusivo | Boolean | Não | — | Não | — | Aleitamento materno exclusivo |
| dataInicioAlimentacaoComplementar | LocalDate | Não | — | Não | — | Data inicio alimentacao complementar |
| dataDesmame | LocalDate | Não | — | Não | — | Data desmame |
| tipoAleitamentoAtual | String | Não | — | Não | — | Tipo aleitamento atual |
| testePezinhoRealizado | Boolean | Não | — | Não | — | Teste pezinho realizado |
| dataTestePezinho | LocalDate | Não | — | Não | — | Data teste pezinho |
| resultadoTestePezinho | String | Não | — | Não | — | Resultado teste pezinho |
| testeOrelhinhaRealizado | Boolean | Não | — | Não | — | Teste orelhinha realizado |
| dataTesteOrelhinha | LocalDate | Não | — | Não | — | Data teste orelhinha |
| resultadoTesteOrelhinha | String | Não | — | Não | — | Resultado teste orelhinha |
| testeOlhinhoRealizado | Boolean | Não | — | Não | — | Teste olhinho realizado |
| dataTesteOlhinho | LocalDate | Não | — | Não | — | Data teste olhinho |
| resultadoTesteOlhinho | String | Não | — | Não | — | Resultado teste olhinho |
| testeCoracaoRealizado | Boolean | Não | — | Não | — | Teste coracao realizado |
| dataTesteCoracao | LocalDate | Não | — | Não | — | Data teste coracao |
| resultadoTesteCoracao | String | Não | — | Não | — | Resultado teste coracao |
| testeLinguinhaRealizado | Boolean | Não | — | Não | — | Teste linguinha realizado |
| dataTesteLinguinha | LocalDate | Não | — | Não | — | Data teste linguinha |
| resultadoTesteLinguinha | String | Não | — | Não | — | Resultado teste linguinha |
| dataInicioAcompanhamento | LocalDate | Não | — | Não | — | Data inicio acompanhamento |
| dataEncerramento | LocalDate | Não | — | Não | — | Data encerramento |
| motivoEncerramento | String | Não | — | Não | — | Motivo encerramento |
| antecedentesFamiliares | String | Não | — | Não | — | Antecedentes familiares |
| alergiasConhecidas | String | Não | — | Não | — | Alergias conhecidas |
| doencasCronicas | String | Não | — | Não | — | Doencas cronicas |
| observacoes | String | Não | — | Não | — | Observacoes |

## Receitas Médicas

### Classe Request: ReceitasMedicasRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| medico | UUID | Sim | @NotNull | Não | — | Medico |
| paciente | UUID | Sim | @NotNull, @NotNull | Não | — | Paciente |
| numeroReceita | String | Sim | @NotNull, @NotNull, @NotBlank, @Size(max=50) | Não | — | Numero receita |
| dataPrescricao | OffsetDateTime | Sim | @NotNull, @NotNull, @NotBlank, @Size(max=50), @NotNull | Não | — | Data prescricao |
| dataValidade | OffsetDateTime | Sim | @NotNull, @NotNull, @NotBlank, @Size(max=50), @NotNull, @NotNull | Não | — | Data validade |
| usoContinuo | Boolean | Sim | @NotNull, @NotBlank, @Size(max=50), @NotNull, @NotNull, @NotNull | Não | — | Uso continuo |
| observacoes | String | Sim | @NotBlank, @Size(max=50), @NotNull, @NotNull, @NotNull, @Size(max=1000) | Não | — | Observacoes |
| status | StatusReceitaEnum | Sim | @NotNull, @NotNull, @NotNull, @Size(max=1000), @NotNull | Sim | — | Status |
| origemReceita | String | Sim | @NotNull, @NotNull, @Size(max=1000), @NotNull, @Size(max=50) | Não | — | Origem receita |
| cidPrincipal | UUID | Sim | @NotNull, @NotNull, @Size(max=1000), @NotNull, @Size(max=50) | Não | — | Cid principal |

## RelatorioEstatisticas

### Classe Request: RelatorioEstatisticasRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| dataInicio | LocalDate | Não | — | Não | — | Data inicio |
| dataFim | LocalDate | Não | — | Não | — | Data fim |
| totalAtendimentos | Long | Não | — | Não | — | Total atendimentos |
| totalConsultas | Long | Não | — | Não | — | Total consultas |
| totalExames | Long | Não | — | Não | — | Total exames |
| totalProcedimentos | Long | Não | — | Não | — | Total procedimentos |
| totalAgendamentos | Long | Não | — | Não | — | Total agendamentos |
| totalPacientes | Long | Não | — | Não | — | Total pacientes |
| totalVisitasDomiciliares | Long | Não | — | Não | — | Total visitas domiciliares |
| atendimentosPorTipo | Map<String, Long> | Não | — | Não | — | Atendimentos por tipo |
| atendimentosPorEspecialidade | Map<String, Long> | Não | — | Não | — | Atendimentos por especialidade |
| examesPorTipo | Map<String, Long> | Não | — | Não | — | Exames por tipo |
| procedimentosPorTipo | Map<String, Long> | Não | — | Não | — | Procedimentos por tipo |
| atendimentosPorProfissional | Map<String, Long> | Não | — | Não | — | Atendimentos por profissional |
| indicadoresSaude | Map<String, BigDecimal> | Não | — | Não | — | Indicadores saude |

## ResponsavelLegal

### Classe Request: ResponsavelLegalRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| paciente | UUID | Não | — | Não | — | Paciente |
| nome | String | Não | — | Não | — | Nome |
| cpf | String | Não | — | Não | 000.000.000-00 | Cpf |
| telefone | String | Não | — | Não | (00) 00000-0000 | Telefone |
| tipoResponsavel | TipoResponsavelEnum | Não | — | Sim | — | Tipo responsavel |
| autorizacaoUsoDadosLGPD | Boolean | Não | — | Não | — | Autorizacao uso dados l g p d |
| autorizacaoResponsavel | Boolean | Não | — | Não | — | Autorizacao responsavel |

## ServicosEstabelecimento

### Classe Request: ServicosEstabelecimentoRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| estabelecimento | UUID | Não | — | Não | — | Estabelecimento |
| nome | String | Não | — | Não | — | Nome |
| codigo | String | Não | — | Não | — | Codigo |
| codigoCnes | String | Não | — | Não | — | Codigo cnes |
| descricao | String | Não | — | Não | — | Descricao |

## TemplateNotificacao

### Classe Request: TemplateNotificacaoRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| estabelecimento | UUID | Não | — | Não | — | Estabelecimento |
| nome | String | Não | — | Não | — | Nome |
| descricao | String | Não | — | Não | — | Descricao |
| tipoNotificacao | TipoNotificacaoEnum | Não | — | Sim | — | Tipo notificacao |
| canal | CanalNotificacaoEnum | Não | — | Sim | — | Canal |
| assunto | String | Não | — | Não | — | Assunto |
| mensagem | String | Não | — | Não | — | Mensagem |
| variaveisDisponiveis | String | Não | — | Não | — | Variaveis disponiveis |
| exemploMensagem | String | Não | — | Não | — | Exemplo mensagem |
| horarioEnvioPrevistoHoras | Integer | Não | — | Não | — | Horario envio previsto horas |
| permiteEdicao | Boolean | Não | — | Não | — | Permite edicao |
| ordemPrioridade | Integer | Não | — | Não | — | Ordem prioridade |
| enviaAutomaticamente | Boolean | Não | — | Não | — | Envia automaticamente |
| condicoesEnvioJson | String | Não | — | Não | — | Condicoes envio json |
| observacoes | String | Não | — | Não | — | Observacoes |

## Tenant

### Classe Request: TenantRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| nome | String | Sim | @NotBlank, @Size(max=255) | Não | — | Nome |
| slug | String | Sim | @NotBlank, @Size(max=255), @NotBlank, @Size(max=100), @Pattern | Não | — | Slug |
| descricao | String | Sim | @NotBlank, @Size(max=255), @NotBlank, @Size(max=100), @Pattern | Não | — | Descricao |
| urlLogo | String | Sim | @NotBlank, @Size(max=255), @NotBlank, @Size(max=100), @Pattern | Não | — | Url logo |
| metadados | String | Sim | @NotBlank, @Size(max=255), @NotBlank, @Size(max=100), @Pattern | Não | — | Metadados |
| ativo | Boolean | Sim | @NotBlank, @Size(max=255), @NotBlank, @Size(max=100), @Pattern | Não | — | Ativo |
| cnpj | String | Sim | @NotBlank, @Size(max=255), @NotBlank, @Size(max=100), @Pattern, @Pattern | Não | 00.000.000/0000-00 | Cnpj |
| cnes | String | Sim | @Size(max=255), @NotBlank, @Size(max=100), @Pattern, @Pattern, @Size(max=7) | Não | — | Cnes |
| tipoInstituicao | String | Sim | @NotBlank, @Size(max=100), @Pattern, @Pattern, @Size(max=7), @Size(max=100) | Não | — | Tipo instituicao |
| telefone | String | Não (Validar apenas se preenchido) | @Pattern, @Size(max=7), @Size(max=100), @Pattern | Não | (00) 00000-0000 | Telefone |
| email | String | Não (Validar apenas se preenchido) | @Pattern, @Size(max=7), @Size(max=100), @Pattern, @Email, @Size(max=255) | Não | email | Email |
| site | String | Não (Validar apenas se preenchido) | @Size(max=7), @Size(max=100), @Pattern, @Email, @Size(max=255), @Size(max=255) | Não | email | Site |
| inscricaoEstadual | String | Não (Validar apenas se preenchido) | @Size(max=100), @Pattern, @Email, @Size(max=255), @Size(max=255), @Size(max=20) | Não | email | Inscricao estadual |
| inscricaoMunicipal | String | Não (Validar apenas se preenchido) | @Pattern, @Email, @Size(max=255), @Size(max=255), @Size(max=20), @Size(max=20) | Não | email | Inscricao municipal |
| responsavelNome | String | Não (Validar apenas se preenchido) | @Email, @Size(max=255), @Size(max=255), @Size(max=20), @Size(max=20), @Size(max=255) | Não | email | Responsavel nome |
| responsavelCpf | String | Não (Validar apenas se preenchido) | @Size(max=255), @Size(max=20), @Size(max=20), @Size(max=255), @Pattern | Não | 000.000.000-00 | Responsavel cpf |
| responsavelTelefone | String | Não (Validar apenas se preenchido) | @Size(max=20), @Size(max=20), @Size(max=255), @Pattern, @Pattern | Não | (00) 00000-0000 | Responsavel telefone |
| horarioFuncionamento | String | Não (Validar apenas se preenchido) | @Size(max=20), @Size(max=20), @Size(max=255), @Pattern, @Pattern | Não | — | Horario funcionamento |
| observacoes | String | Não (Validar apenas se preenchido) | @Size(max=20), @Size(max=255), @Pattern, @Pattern, @Size(max=1000) | Não | — | Observacoes |

## Tratamentos Odontológicos

### Classe Request: TratamentosOdontologicosRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| paciente | UUID | Sim | @NotNull | Não | — | Paciente |
| profissional | UUID | Sim | @NotNull, @NotNull | Não | — | Profissional |
| titulo | String | Sim | @NotNull, @NotNull, @NotBlank, @Size(max=255) | Não | — | Titulo |
| descricao | String | Sim | @NotNull, @NotNull, @NotBlank, @Size(max=255) | Não | — | Descricao |
| dataInicio | OffsetDateTime | Sim | @NotNull, @NotNull, @NotBlank, @Size(max=255) | Não | — | Data inicio |
| dataFim | OffsetDateTime | Sim | @NotNull, @NotNull, @NotBlank, @Size(max=255) | Não | — | Data fim |
| status | StatusTratamento | Sim | @NotNull, @NotNull, @NotBlank, @Size(max=255) | Não | — | Status |
| observacoes | String | Sim | @NotNull, @NotNull, @NotBlank, @Size(max=255) | Não | — | Observacoes |

## TratamentosProcedimentos

### Classe Request: TratamentosProcedimentosRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| tratamento | UUID | Não | — | Não | — | Tratamento |
| procedimento | UUID | Não | — | Não | — | Procedimento |
| dente | String | Não | — | Não | — | Dente |
| faces | String | Não | — | Não | — | Faces |
| quantidade | Integer | Não | — | Não | — | Quantidade |
| custo | BigDecimal | Não | — | Não | — | Custo |
| dataExecucao | OffsetDateTime | Não | — | Não | — | Data execucao |
| profissional | UUID | Não | — | Não | — | Profissional |
| observacoes | String | Não | — | Não | — | Observacoes |

## Usuário

### Classe Request: UserRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| email | String | Não (Validar apenas se preenchido) | @Email, @Size(max=255) | Não | email | Email |
| role | String | Não (Validar apenas se preenchido) | @Email, @Size(max=255), @Size(max=255) | Não | email | Role |
| senha | String | Não (Validar apenas se preenchido) | @Email, @Size(max=255), @Size(max=255), @Size(max=255) | Não | email | Senha |

## UsuarioEstabelecimento

### Classe Request: UsuarioEstabelecimentoRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| usuario | UUID | Não | — | Não | — | Usuario |
| estabelecimento | UUID | Não | — | Não | — | Estabelecimento |

## Usuários do Sistema

### Classe Request: UsuariosSistemaRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| userId | UUID | Não | — | Não | — | User id |
| profissionalSaude | UUID | Não | — | Não | — | Profissional saude |
| medico | UUID | Não | — | Não | — | Medico |
| paciente | UUID | Não | — | Não | — | Paciente |
| tenantId | UUID | Sim | @NotNull | Não | — | Tenant id |
| adminTenant | Boolean | Sim | @NotNull | Não | — | Admin tenant |
| tipoVinculo | String | Sim | @NotNull, @Size(max=50) | Não | — | Tipo vinculo |
| nomeExibicao | String | Sim | @NotNull, @Size(max=50), @Size(max=255) | Não | — | Nome exibicao |
| username | String | Sim | @NotNull, @Size(max=50), @Size(max=255), @NotBlank, @Size(max=100) | Não | — | Username |
| email | String | Sim | @Size(max=50), @Size(max=255), @NotBlank, @Size(max=100), @Email, @Size(max=255) | Não | email | Email |
| senha | String | Sim | @Size(max=50), @Size(max=255), @NotBlank, @Size(max=100), @Email, @Size(max=255), @Size(max=255) | Não | email | Senha |
| fotoUrl | String | Sim | @Size(max=255), @NotBlank, @Size(max=100), @Email, @Size(max=255), @Size(max=255), @Size(max=500) | Não | email | Foto url |
| estabelecimentoId | UUID | Sim | @NotNull | Não | — | Estabelecimento id |
| tipoUsuario | TipoUsuarioSistemaEnum | Sim | @NotNull | Sim | — | Tipo usuario |

## Vacinações

### Classe Request: VacinacoesRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| paciente | UUID | Sim | @NotNull | Não | — | Paciente |
| vacina | UUID | Sim | @NotNull, @NotNull | Não | — | Vacina |
| fabricante | UUID | Sim | @NotNull, @NotNull | Não | — | Fabricante |
| lote | String | Sim | @NotNull, @NotNull, @Size(max=100) | Não | — | Lote |
| numeroDose | Integer | Sim | @NotNull, @NotNull, @Size(max=100), @NotNull | Não | — | Numero dose |
| dataAplicacao | OffsetDateTime | Sim | @NotNull, @NotNull, @Size(max=100), @NotNull, @NotNull | Não | — | Data aplicacao |
| localAplicacao | String | Sim | @NotNull, @Size(max=100), @NotNull, @NotNull, @Size(max=100) | Não | — | Local aplicacao |
| profissionalAplicador | UUID | Sim | @Size(max=100), @NotNull, @NotNull, @Size(max=100) | Não | — | Profissional aplicador |
| reacaoAdversa | String | Sim | @Size(max=100), @NotNull, @NotNull, @Size(max=100) | Não | — | Reacao adversa |
| observacoes | String | Sim | @Size(max=100), @NotNull, @NotNull, @Size(max=100) | Não | — | Observacoes |

## Vacinas

### Classe Request: VacinasRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| nome | String | Sim | @NotBlank, @Size(max=255) | Não | — | Nome |
| nomeComercial | String | Sim | @NotBlank, @Size(max=255), @Size(max=255) | Não | — | Nome comercial |
| codigoInterno | String | Sim | @NotBlank, @Size(max=255), @Size(max=255), @Size(max=50) | Não | — | Codigo interno |
| codigoPni | String | Sim | @NotBlank, @Size(max=255), @Size(max=255), @Size(max=50), @Size(max=20) | Não | — | Codigo pni |
| codigoSus | String | Sim | @NotBlank, @Size(max=255), @Size(max=255), @Size(max=50), @Size(max=20), @Size(max=20) | Não | — | Codigo sus |
| registroAnvisa | String | Não (Validar apenas se preenchido) | @Size(max=255), @Size(max=50), @Size(max=20), @Size(max=20), @Size(max=50) | Não | — | Registro anvisa |
| tipo | TipoVacinaEnum | Não (Validar apenas se preenchido) | @Size(max=255), @Size(max=50), @Size(max=20), @Size(max=20), @Size(max=50) | Sim | — | Tipo |
| categoria | String | Não (Validar apenas se preenchido) | @Size(max=50), @Size(max=20), @Size(max=20), @Size(max=50) | Não | — | Categoria |
| grupoAlvo | String | Não (Validar apenas se preenchido) | @Size(max=50), @Size(max=20), @Size(max=20), @Size(max=50) | Não | — | Grupo alvo |
| fabricante | UUID | Não (Validar apenas se preenchido) | @Size(max=50), @Size(max=20), @Size(max=20), @Size(max=50) | Não | — | Fabricante |
| lotePadrao | String | Não (Validar apenas se preenchido) | @Size(max=20), @Size(max=20), @Size(max=50) | Não | — | Lote padrao |
| viaAdministracao | ViaAdministracaoEnum | Não (Validar apenas se preenchido) | @Size(max=20), @Size(max=20), @Size(max=50) | Sim | — | Via administracao |
| unidadeMedida | UnidadeMedidaEnum | Não (Validar apenas se preenchido) | @Size(max=20), @Size(max=20), @Size(max=50) | Sim | — | Unidade medida |
| esquemaVacinal | EsquemaVacinalRequest | Não (Validar apenas se preenchido) | @Size(max=20), @Size(max=50) | Não | — | Esquema vacinal |
| idadeAplicacao | IdadeAplicacaoVacinaRequest | Não (Validar apenas se preenchido) | @Size(max=50) | Não | — | Idade aplicacao |
| contraindicacoes | ContraindicacoesVacinaRequest | Não | — | Não | — | Contraindicacoes |
| conservacao | ConservacaoVacinaRequest | Não | — | Não | — | Conservacao |
| composicao | ComposicaoVacinaRequest | Não | — | Não | — | Composicao |
| eficacia | EficaciaVacinaRequest | Não | — | Não | — | Eficacia |
| reacoesAdversas | ReacoesAdversasVacinaRequest | Não | — | Não | — | Reacoes adversas |
| calendario | CalendarioVacinalRequest | Não | — | Não | — | Calendario |
| status | StatusAtivoEnum | Não | — | Sim | — | Status |
| bula | String | Não (Validar apenas se preenchido) | @Size(max=5000) | Não | — | Bula |
| fichaTecnica | String | Não (Validar apenas se preenchido) | @Size(max=5000), @Size(max=5000) | Não | — | Ficha tecnica |
| manualUso | String | Não (Validar apenas se preenchido) | @Size(max=5000), @Size(max=5000), @Size(max=5000) | Não | — | Manual uso |
| descricao | String | Não (Validar apenas se preenchido) | @Size(max=5000), @Size(max=5000), @Size(max=5000), @Size(max=1000) | Não | — | Descricao |
| indicacoes | String | Não (Validar apenas se preenchido) | @Size(max=5000), @Size(max=5000), @Size(max=5000), @Size(max=1000), @Size(max=1000) | Não | — | Indicacoes |
| observacoes | String | Não (Validar apenas se preenchido) | @Size(max=5000), @Size(max=5000), @Size(max=1000), @Size(max=1000), @Size(max=1000) | Não | — | Observacoes |
| integracaoGovernamental | IntegracaoGovernamentalVacinaRequest | Não (Validar apenas se preenchido) | @Size(max=5000), @Size(max=1000), @Size(max=1000), @Size(max=1000) | Não | — | Integracao governamental |

#### EsquemaVacinalRequest (esquemaVacinal)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| intervaloDosesDias | Integer | Sim | @NotNull | Não | — | Intervalo doses dias |
| intervaloReforcoAnos | Integer | Sim | @NotNull, @NotNull | Não | — | Intervalo reforco anos |
| doseMl | BigDecimal | Sim | @NotNull, @NotNull | Não | — | Dose ml |
| localAplicacaoPadrao | String | Sim | @NotNull, @NotNull, @Size(max=100) | Não | — | Local aplicacao padrao |

#### IdadeAplicacaoVacinaRequest (idadeAplicacao)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| idadeMinimaDias | Integer | Não | — | Não | — | Idade minima dias |
| idadeMaximaDias | Integer | Não | — | Não | — | Idade maxima dias |
| idadeMinimaMeses | Integer | Não | — | Não | — | Idade minima meses |
| idadeMaximaMeses | Integer | Não | — | Não | — | Idade maxima meses |
| idadeMinimaAnos | Integer | Não | — | Não | — | Idade minima anos |
| idadeMaximaAnos | Integer | Não | — | Não | — | Idade maxima anos |

#### ContraindicacoesVacinaRequest (contraindicacoes)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| contraindicacoes | String | Não | — | Não | — | Contraindicacoes |
| precaucoes | String | Não | — | Não | — | Precaucoes |

#### ConservacaoVacinaRequest (conservacao)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| temperaturaConservacaoMin | BigDecimal | Não | — | Não | — | Temperatura conservacao min |
| temperaturaConservacaoMax | BigDecimal | Não | — | Não | — | Temperatura conservacao max |
| tipoConservacao | String | Não (Validar apenas se preenchido) | @Size(max=50) | Não | — | Tipo conservacao |
| validadeAposAberturaHoras | Integer | Sim | @Size(max=50), @NotNull, @NotNull | Não | — | Validade apos abertura horas |
| validadeAposReconstituicaoHoras | Integer | Sim | @Size(max=50), @NotNull, @NotNull | Não | — | Validade apos reconstituicao horas |

#### ComposicaoVacinaRequest (composicao)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| composicao | String | Não | — | Não | — | Composicao |
| tecnologia | String | Não (Validar apenas se preenchido) | @Size(max=100) | Não | — | Tecnologia |
| adjuvante | String | Não (Validar apenas se preenchido) | @Size(max=100), @Size(max=100) | Não | — | Adjuvante |
| conservante | String | Não (Validar apenas se preenchido) | @Size(max=100), @Size(max=100), @Size(max=100) | Não | — | Conservante |

#### EficaciaVacinaRequest (eficacia)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| eficaciaPercentual | BigDecimal | Não | — | Não | — | Eficacia percentual |
| protecaoInicioDias | Integer | Não | — | Não | — | Protecao inicio dias |
| protecaoDuracaoAnos | Integer | Não | — | Não | — | Protecao duracao anos |
| doencasProtege | String | Não | — | Não | — | Doencas protege |

#### ReacoesAdversasVacinaRequest (reacoesAdversas)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| reacoesAdversasComuns | String | Não | — | Não | — | Reacoes adversas comuns |
| reacoesAdversasRaras | String | Não | — | Não | — | Reacoes adversas raras |
| reacoesAdversasGraves | String | Não | — | Não | — | Reacoes adversas graves |

#### CalendarioVacinalRequest (calendario)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| faixaEtariaCalendario | String | Sim | @NotNull, @NotNull, @Size(max=100) | Não | — | Faixa etaria calendario |
| situacaoEpidemiologica | String | Sim | @NotNull, @NotNull, @Size(max=100), @Size(max=50) | Não | — | Situacao epidemiologica |

#### IntegracaoGovernamentalVacinaRequest (integracaoGovernamental)

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| codigoSiPni | String | Não (Validar apenas se preenchido) | @Size(max=50) | Não | — | Codigo si pni |
| codigoESus | String | Não (Validar apenas se preenchido) | @Size(max=50), @Size(max=50) | Não | — | Codigo e sus |
| ultimaSincronizacaoPni | OffsetDateTime | Sim | @Size(max=50), @Size(max=50), @NotNull | Não | — | Ultima sincronizacao pni |

## VinculoProfissionalEquipe

### Classe Request: VinculoProfissionalEquipeRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| profissional | UUID | Não | — | Não | — | Profissional |
| equipe | UUID | Não | — | Não | — | Equipe |
| dataInicio | OffsetDateTime | Não | — | Não | — | Data inicio |
| dataFim | OffsetDateTime | Não | — | Não | — | Data fim |
| tipoVinculo | TipoVinculoProfissionalEnum | Não | — | Sim | — | Tipo vinculo |
| funcaoEquipe | String | Não | — | Não | — | Funcao equipe |
| cargaHorariaSemanal | Integer | Não | — | Não | — | Carga horaria semanal |
| status | StatusAtivoEnum | Não | — | Sim | — | Status |
| observacoes | String | Não | — | Não | — | Observacoes |

## Visitas Domiciliares

### Classe Request: VisitasDomiciliaresRequest

| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |
|-------|------|-------------|------------|------|------------------|-----------|
| paciente | UUID | Não | — | Não | — | Paciente |
| profissional | UUID | Não | — | Não | — | Profissional |
| equipeSaude | UUID | Não | — | Não | — | Equipe saude |
| tipoVisita | TipoVisitaDomiciliarEnum | Não | — | Sim | — | Tipo visita |
| dataVisita | OffsetDateTime | Não | — | Não | — | Data visita |
| turno | String | Não | — | Não | — | Turno |
| duracaoMinutos | Integer | Não | — | Não | — | Duracao minutos |
| endereco | String | Não | — | Não | — | Endereco |
| bairro | String | Não | — | Não | — | Bairro |
| cep | String | Não | — | Não | 00000-000 | Cep |
| latitude | BigDecimal | Não | — | Não | — | Latitude |
| longitude | BigDecimal | Não | — | Não | — | Longitude |
| motivo | String | Não | — | Não | — | Motivo |
| motivoNaoRealizacao | String | Não | — | Não | — | Motivo nao realizacao |
| informanteNome | String | Não | — | Não | — | Informante nome |
| informanteParentesco | String | Não | — | Não | — | Informante parentesco |
| pressaoSistolica | Integer | Não | — | Não | — | Pressao sistolica |
| pressaoDiastolica | Integer | Não | — | Não | — | Pressao diastolica |
| glicemiaCapilar | Integer | Não | — | Não | — | Glicemia capilar |
| temperatura | BigDecimal | Não | — | Não | — | Temperatura |
| peso | BigDecimal | Não | — | Não | — | Peso |
| procedimentosDetalhes | String | Não | — | Não | — | Procedimentos detalhes |
| encaminhamentoDetalhes | String | Não | — | Não | — | Encaminhamento detalhes |
| desfecho | String | Não | — | Não | — | Desfecho |
| dataProximaVisita | OffsetDateTime | Não | — | Não | — | Data proxima visita |
| observacoes | String | Não | — | Não | — | Observacoes |
| observacoesInternas | String | Não | — | Não | — | Observacoes internas |
