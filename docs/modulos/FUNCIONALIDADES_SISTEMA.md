# Funcionalidades e Módulos do Sistema UP Saúde

Este documento apresenta uma análise comparativa das funcionalidades e módulos do sistema, indicando o que já está implementado e o que ainda precisa ser desenvolvido.

## Legenda
- ✅ = Funcionalidade implementada
- ❌ = Funcionalidade não implementada
- ⚠️ = Funcionalidade parcialmente implementada

---

## 1. CADASTROS E CARTÃO NACIONAL DE SAÚDE

| Funcionalidade | Status | Descrição |
|----------------|--------|-----------|
| Cadastro de pacientes | ✅ | Sistema possui cadastro completo de pacientes com CPF, CNS, dados pessoais, endereço, etc. |
| Cadastro único do paciente | ✅ | Entidade Paciente com identificadores únicos (CPF, CNS, email) por tenant |
| Validação de CNS | ⚠️ | Campo existe mas validação automática não implementada |
| Atualização de dados do paciente | ✅ | CRUD completo para pacientes |
| Cadastro de responsável legal | ✅ | Entidade ResponsavelLegal vinculada ao paciente |
| Dados sociodemográficos | ✅ | Entidade DadosSociodemograficos |
| Dados clínicos básicos | ✅ | Entidade DadosClinicosBasicos |
| Integração com sistemas governamentais | ⚠️ | Entidade IntegracaoGov existe mas integração não implementada |
| Consentimento LGPD | ✅ | Entidade LGPDConsentimento |

---

## 2. AGENDAMENTO DE CONSULTAS E RECEPÇÃO

| Funcionalidade | Status | Descrição |
|----------------|--------|-----------|
| Agendamento de consultas | ✅ | Entidade Agendamento completa com controle de conflitos |
| Visualizar relação de pacientes agendados | ✅ | Controller e Service de Agendamento implementados |
| Confirmar presença do paciente | ✅ | Campo dataConfirmacao e confirmadoPor na entidade Agendamento |
| Criar atendimento para paciente não agendado | ✅ | Entidade Atendimento permite criação independente |
| Registrar atendimento a partir da agenda | ✅ | Agendamento vinculado a Atendimento |
| Cadastrar motivos de cancelamento de vagas | ❌ | Não implementado |
| Check-in de atendimento | ✅ | Entidade CheckInAtendimento |
| Fila de espera | ✅ | Entidade FilaEspera |
| Controle de conflitos de horário | ✅ | Campos temConflitoHorario, sobreposicaoPermitida |
| Reagendamento | ✅ | Campos de reagendamento e agendamentoOriginal |
| Notificações de agendamento | ⚠️ | Campos existem mas sistema de notificação não implementado |

---

## 3. PRONTUÁRIO ATENÇÃO PRIMÁRIA/SECUNDÁRIA

| Funcionalidade | Status | Descrição |
|----------------|--------|-----------|
| Acessar prontuário do paciente | ✅ | Entidade Prontuarios e Controller implementados |
| Registrar triagem do paciente | ⚠️ | ClassificacaoRiscoAtendimento existe mas triagem específica não |
| Registrar atendimento do profissional | ✅ | Entidade Atendimento completa com anamnese, diagnóstico |
| Registrar receituários | ✅ | Entidade ReceitasMedicas implementada |
| Ferramentas para facilitar registro de receituário | ❌ | Não implementado |
| Emitir atestado de comparecimento | ❌ | Não implementado |
| Solicitação de exames e procedimentos | ✅ | Entidade Exames e CatalogoExames implementados |
| Configurar permissão de solicitação por CBO | ❌ | Não implementado |
| Cadastrar kits para solicitação de procedimentos | ❌ | Não implementado |
| Consultar fichas e-SUS | ❌ | Não implementado |
| Cadastramento de fichas padrão personalizáveis | ❌ | Não implementado |
| Emitir lista de presença | ❌ | Não implementado |
| Assinatura digital com certificado A3 | ❌ | Não implementado |
| Registro de evolução | ⚠️ | Prontuarios tem campo conteudo mas estrutura específica não |
| Registro de prescrição de medicamentos | ✅ | Entidade ReceitasMedicas com vínculo a Medicacao |
| Registro de pedidos de exames laboratoriais | ✅ | Entidade Exames implementada |
| Registro de pedidos de exames de imagens | ⚠️ | Exames existe mas módulo específico de imagens não |
| Registro de dietas | ❌ | Não implementado |
| Solicitação de bolsas de sangue | ❌ | Não implementado |
| Solicitação e registro de interconsultas | ❌ | Não implementado |
| Solicitação de procedimentos médicos e de enfermagem | ⚠️ | CatalogoProcedimentos existe mas solicitação específica não |
| Registro de materiais especiais | ❌ | Não implementado |
| Controle de pedidos de exames com justificativa | ❌ | Não implementado |
| Controle de solicitações de antibióticos | ❌ | Não implementado |
| Kits de prescrição de medicamentos | ❌ | Não implementado |
| Prescrição padrão por médico/doença | ❌ | Não implementado |
| Visualização dos laudos de exames | ⚠️ | Campo laudo existe mas visualizador não implementado |
| Resumo de alta | ❌ | Não implementado |
| Solicitação de gases medicinais | ❌ | Não implementado |
| Repetição da prescrição anterior | ❌ | Não implementado |
| Cadastro de vias de acesso para medicações | ❌ | Não implementado |
| Cadastro de tipos de dieta | ❌ | Não implementado |
| Cadastro de procedimentos de enfermagem | ❌ | Não implementado |
| Cadastro de procedimentos médicos | ⚠️ | CatalogoProcedimentos existe mas procedimentos médicos específicos não |
| Cadastro de kits para solicitação de medicações | ❌ | Não implementado |
| Cadastro de horários padrão por medicação | ❌ | Não implementado |
| Cadastro de gases medicinais | ❌ | Não implementado |
| Gerar etiquetas para pedidos avulsos | ❌ | Não implementado |
| Registrar, calcular e imprimir indicadores (APACHE, SOFA, SAPS-3) | ❌ | Não implementado |
| Controle dos hemoderivados solicitados/atendidos | ❌ | Não implementado |
| Controle de dietas atendidas | ❌ | Não implementado |
| Controle dos pedidos de antibióticos | ❌ | Não implementado |
| Reimprimir uma prescrição | ❌ | Não implementado |
| Cadastrar materiais necessários para aplicação de medicação | ❌ | Não implementado |
| Sugerir automaticamente quando prescrito pelo médico | ❌ | Não implementado |
| Definir taxas de faturamento automáticas | ❌ | Não implementado |
| Cadastrar textos padrão para evolução multidisciplinar | ❌ | Não implementado |

---

## 4. REGISTRO DE OUTROS PROCEDIMENTOS / ESUS

| Funcionalidade | Status | Descrição |
|----------------|--------|-----------|
| Registro de procedimentos odontológicos | ✅ | Entidade ProcedimentosOdontologicos e TratamentosOdontologicos |
| Registro de consultas | ✅ | Entidade Consultas implementada |
| Registro de pré-natal | ✅ | Entidade PreNatal e ConsultaPreNatal |
| Registro de puericultura | ✅ | Entidade Puericultura e ConsultaPuericultura |
| Registro de planejamento familiar | ✅ | Entidade PlanejamentoFamiliar |
| Registro de educação em saúde | ✅ | Entidade EducacaoSaude |
| Registro de ações de promoção e prevenção | ✅ | Entidade AcaoPromocaoPrevencao |
| Registro de visitas domiciliares | ✅ | Entidade VisitasDomiciliares |
| Registro de cuidados de enfermagem | ✅ | Entidade CuidadosEnfermagem |
| Registro de medições clínicas | ✅ | Entidade MedicaoClinica |
| Registro da ficha CDS (ESUS) | ❌ | Não implementado |
| Integração com e-SUS | ❌ | Não implementado |

---

## 5. SAÚDE MENTAL

| Funcionalidade | Status | Descrição |
|----------------|--------|-----------|
| Registro específico de saúde mental | ❌ | Não implementado como módulo específico |
| Cadastro de questionários de avaliação | ❌ | Não implementado |
| Registro de condições de saúde mental | ❌ | Não implementado |

---

## 6. EXAMES DE IMAGEM

| Funcionalidade | Status | Descrição |
|----------------|--------|-----------|
| Visualização de imagens | ❌ | Não implementado |
| Registro de laudos | ⚠️ | Campo laudo existe na entidade Exames |
| Recebimento automático dos pedidos | ❌ | Não implementado |
| Visualização de laudos e imagens em qualquer ambiente | ❌ | Não implementado |
| Visualizador de imagens DICOM | ❌ | Não implementado |
| Gravação de laudos em áudio | ❌ | Não implementado |
| Configuração do tempo de entrega dos laudos | ❌ | Não implementado |
| Acesso online aos resultados | ⚠️ | Exames tem campo resultados mas acesso web não implementado |
| Aplicativo para controle dos pedidos | ❌ | Não implementado |
| Dashboards e relatórios | ❌ | Não implementado |

---

## 7. TRATAMENTO FORA DO DOMICÍLIO (TFD)

| Funcionalidade | Status | Descrição |
|----------------|--------|-----------|
| Cadastro de unidades assistenciais | ❌ | Não implementado |
| Cadastro de tipos de TFD | ❌ | Não implementado |
| Cadastro de motoristas | ❌ | Não implementado |
| Cadastro de tipos de despesa | ❌ | Não implementado |
| Cadastro de tipos de transporte | ❌ | Não implementado |
| Definir tipos de despesas liberadas por unidade | ❌ | Não implementado |
| Definir perfis de acesso (usuário/auditor/manutenção) | ⚠️ | Sistema de permissões existe mas específico para TFD não |
| Registrar ficha do TFD | ❌ | Não implementado |
| Visualizar, autorizar ou rejeitar solicitação | ❌ | Não implementado |
| Registrar viagens a serem realizadas | ❌ | Não implementado |
| Organizar viagens por motorista | ❌ | Não implementado |
| Registrar gastos e despesas das viagens | ❌ | Não implementado |
| Gerar relatório de programação de viagens | ❌ | Não implementado |
| Gerar relatório de custos das viagens | ❌ | Não implementado |
| Avaliação do assistente social | ❌ | Não implementado |
| Gerar relatório de tratamento de pacientes por CID | ❌ | Não implementado |
| Gerar relatório de pacientes em tratamento | ❌ | Não implementado |
| Gerar relatório por tipo de TFD | ❌ | Não implementado |
| Lançamento de procedimentos realizados no TFD | ❌ | Não implementado |
| Geração automática do faturamento BPA | ❌ | Não implementado |

---

## 8. APLICATIVO CIDADÃO

| Funcionalidade | Status | Descrição |
|----------------|--------|-----------|
| Aplicativo móvel para cidadãos | ❌ | Não implementado |
| Visualização de agendamentos | ❌ | Não implementado |
| Visualização de resultados de exames | ❌ | Não implementado |
| Visualização de receitas | ❌ | Não implementado |
| Histórico de atendimentos | ❌ | Não implementado |

---

## 9. LABORATÓRIO

| Funcionalidade | Status | Descrição |
|----------------|--------|-----------|
| Cadastro de exames laboratoriais | ✅ | Entidade CatalogoExames implementada |
| Registro de exames realizados | ✅ | Entidade Exames implementada |
| Cadastrar kits de exames laboratoriais | ❌ | Não implementado |
| Recebimento de pedidos | ⚠️ | Exames tem dataSolicitacao mas fluxo completo não |
| Registro de resultados | ⚠️ | Campo resultados existe mas estrutura específica não |
| Laudos de exames | ⚠️ | Campo laudo existe |
| Controle de qualidade | ❌ | Não implementado |
| Integração com equipamentos | ❌ | Não implementado |

---

## 10. CONTROLE DE ESTOQUE E ASSISTÊNCIA FARMACÊUTICA

| Funcionalidade | Status | Descrição |
|----------------|--------|-----------|
| Controle de estoque de vacinas | ✅ | Entidade EstoquesVacina implementada |
| Movimentações de estoque | ✅ | Entidade MovimentacoesEstoque implementada |
| Dispensação de medicamentos | ✅ | Entidade DispensacoesMedicamentos implementada |
| Cadastro de medicamentos | ✅ | Entidade Medicacao implementada |
| Cadastro de fabricantes de medicamentos | ✅ | Entidade FabricantesMedicamento implementada |
| Cadastro de fabricantes de vacinas | ✅ | Entidade FabricantesVacina implementada |
| Medicações contínuas | ✅ | Entidade MedicacoesContinuas implementada |
| Medicações do paciente | ✅ | Entidade MedicacaoPaciente implementada |
| Controle de estoque geral | ⚠️ | Apenas estoque de vacinas implementado |
| Controle de validade | ⚠️ | Campo dataValidade existe em EstoquesVacina |
| Controle de temperatura | ⚠️ | Campo temperaturaArmazenamento existe |
| Relatórios de consumo | ❌ | Não implementado |
| Alertas de estoque mínimo | ❌ | Não implementado |
| Integração com prescrições | ⚠️ | ReceitasMedicas existe mas integração automática não |

---

## 11. REGULAÇÃO/PPI

| Funcionalidade | Status | Descrição |
|----------------|--------|-----------|
| Cadastrar prestadores de serviço | ❌ | Não implementado |
| Cadastrar cotas por prestador | ❌ | Não implementado |
| Cadastrar cotas por unidade executora | ❌ | Não implementado |
| Replicar cotas para meses posteriores | ❌ | Não implementado |
| Cadastrar cotas por município origem | ❌ | Não implementado |
| Definir procedimentos sem autorização | ❌ | Não implementado |
| Restringir solicitação por CBO | ❌ | Não implementado |
| Configurar procedimentos em exceção | ❌ | Não implementado |
| Cadastrar sub-tipo de procedimentos SIGTAP | ❌ | Não implementado |
| Cadastrar procedimentos fora da tabela SUS | ❌ | Não implementado |
| Definir valores de procedimentos extra SUS | ❌ | Não implementado |
| Configurar regras de permissão por CBO | ❌ | Não implementado |
| Definir critérios de priorização | ❌ | Não implementado |
| Solicitação de procedimentos especiais | ❌ | Não implementado |
| Gerenciar fila de solicitações | ❌ | Não implementado |
| Gerar autorização de execução | ❌ | Não implementado |
| Registrar execução do procedimento | ❌ | Não implementado |
| Upload de laudos | ❌ | Não implementado |
| Cadastrar unidades assistenciais | ❌ | Não implementado |

---

## 12. PRONTO ATENDIMENTO

| Funcionalidade | Status | Descrição |
|----------------|--------|-----------|
| Registro de atendimentos de urgência | ✅ | Entidade Atendimento permite qualquer tipo |
| Classificação de risco | ⚠️ | ClassificacaoRiscoAtendimento existe mas implementação completa não |
| Acolhimento | ⚠️ | Estrutura existe mas módulo específico não |
| Controle de fila | ✅ | Entidade FilaEspera implementada |
| Registro de atendimento | ✅ | Entidade Atendimento completa |

---

## 13. VIGILÂNCIA SANITÁRIA

| Funcionalidade | Status | Descrição |
|----------------|--------|-----------|
| Módulo de vigilância sanitária | ❌ | Não implementado |

---

## 14. PAINEL DE CHAMADAS E TOTEM SENHA

| Funcionalidade | Status | Descrição |
|----------------|--------|-----------|
| Painel eletrônico | ❌ | Não implementado |
| Totem de senha | ❌ | Não implementado |
| Sistema de chamadas | ❌ | Não implementado |

---

## 15. ARQUIVO MÉDICO E ESTATÍSTICA (SAME)

| Funcionalidade | Status | Descrição |
|----------------|--------|-----------|
| Pesquisa e geração de relatórios da movimentação do prontuário físico | ❌ | Não implementado |
| Manutenção de dados de solicitação e entrega de cópia de documentos | ❌ | Não implementado |
| Movimentação do prontuário físico para atendimentos | ❌ | Não implementado |
| Localização do prontuário físico por busca | ❌ | Não implementado |
| Prontuário eletrônico | ✅ | Entidade Prontuarios implementada |

---

## 16. BUSINESS INTELLIGENCE (BI)

| Funcionalidade | Status | Descrição |
|----------------|--------|-----------|
| Análise dinâmica das informações | ❌ | Não implementado |
| Ordenação automática e filtro dos dados | ⚠️ | Paginação existe mas filtros avançados não |
| Funcionamento responsivo para mobile | ❌ | Não implementado |
| Capacidade de impressão dos gráficos | ❌ | Não implementado |
| Acesso aos diversos dashboards através de menus | ❌ | Não implementado |
| Criação de dashboards com diferentes visualizações | ❌ | Não implementado |
| Visualização centralizada das estatísticas de marcação e atendimento | ❌ | Não implementado |
| Visualização centralizada do consumo dos estoques | ❌ | Não implementado |
| Visualização centralizada dos procedimentos faturados | ❌ | Não implementado |
| Visualização da situação geral de leitos | ❌ | Não implementado |
| Visualização da utilização das unidades de urgência/emergência | ❌ | Não implementado |
| Visualização do tempo de espera de pacientes | ❌ | Não implementado |
| Visualização dos dados da regulação | ❌ | Não implementado |
| Sistema Desktop/Web | ✅ | API REST implementada |

---

## 17. ESF E APLICATIVO ESF (AGENTE COMUNITÁRIO DE SAÚDE)

| Funcionalidade | Status | Descrição |
|----------------|--------|-----------|
| Cadastramento e atualização de dados | ✅ | CRUD completo implementado |
| Cadastro de agentes comunitários | ⚠️ | ProfissionaisSaude existe mas tipo específico não |
| Cadastro de questionários de avaliação | ❌ | Não implementado |
| Cadastro de microáreas | ❌ | Não implementado |
| Cadastro de domicílios | ❌ | Não implementado |
| Registro da ficha CDS (ESUS) | ❌ | Não implementado |
| Cadastro de famílias | ❌ | Não implementado |
| Cadastro de membros da família | ❌ | Não implementado |
| Registro de condições de saúde | ✅ | Entidades relacionadas existem |
| Relatório de produção por período | ❌ | Não implementado |
| Relatórios das informações dos questionários | ❌ | Não implementado |
| Equipes de saúde | ✅ | Entidade EquipeSaude implementada |
| Vínculo profissional-equipe | ✅ | Entidade VinculoProfissionalEquipe implementada |

---

## 18. CENTRO CIRÚRGICO

| Funcionalidade | Status | Descrição |
|----------------|--------|-----------|
| Registro de Cadastro de Caixas (Sub-Kits) | ❌ | Não implementado |
| Registro de Cadastro de Materiais Esterilizáveis | ❌ | Não implementado |
| Registro de Cadastro de Itens de Lavanderia | ❌ | Não implementado |
| Registro de Cadastro de Hemocomponentes | ❌ | Não implementado |
| Registro de Cadastro de Equipamentos Cirúrgicos | ⚠️ | Entidade Equipamentos existe mas específico cirúrgico não |
| Cadastro de Kits Cirúrgicos | ❌ | Não implementado |
| Registro de Cadastro de Cirurgias | ⚠️ | Entidade Cirurgia existe mas funcionalidades completas não |
| Registro de Cadastro de Salas | ❌ | Não implementado |
| Registro de Cadastro de Leitos de Apoio / Mesa Cirúrgica | ❌ | Não implementado |
| Registro de Cadastro de Unidades Cirúrgicas | ❌ | Não implementado |
| Registro de Cadastro de Técnicas Anestésicas | ❌ | Não implementado |
| Registro de Origens | ❌ | Não implementado |
| Registro de Cadastro de Equipes Cirúrgicas | ⚠️ | Entidade EquipeCirurgica existe mas funcionalidades não |

---

## 19. CUSTO DEPARTAMENTAL

| Funcionalidade | Status | Descrição |
|----------------|--------|-----------|
| Definir acesso a dados de unidades por usuário | ⚠️ | Sistema de permissões existe mas específico não |
| Cadastrar sub-grupos de centro de custos | ❌ | Não implementado |
| Cadastrar centros de custos hierarquizados | ❌ | Não implementado |
| Cadastrar grupos de centros de custos | ❌ | Não implementado |
| Cadastrar unidades de negócio | ❌ | Não implementado |
| Cadastrar sub-unidades de negócio | ❌ | Não implementado |
| Cadastrar moedas | ❌ | Não implementado |
| Cadastrar itens de receitas financeiras | ❌ | Não implementado |
| Cadastrar itens de custo | ❌ | Não implementado |
| Cadastrar grupos de itens de custo | ❌ | Não implementado |
| Cadastrar índices econômicos | ❌ | Não implementado |
| Cadastrar pesos das unidades de produção | ❌ | Não implementado |
| Cadastrar critérios de rateio | ❌ | Não implementado |
| Cadastrar bases de rateio | ❌ | Não implementado |
| Cadastrar unidades de produção | ❌ | Não implementado |
| Cadastrar itens de produção | ❌ | Não implementado |
| Definir comandos SQL para busca | ❌ | Não implementado |
| Definir forma de rateio por acomodação | ❌ | Não implementado |
| Definir critério de rateio por centro de custo | ❌ | Não implementado |
| Definir unidade de produção por centro de custos | ❌ | Não implementado |
| Definir custos fixos e variáveis | ❌ | Não implementado |
| Definir custos diretos | ❌ | Não implementado |
| Definir base de rateio para itens de custo indireto | ❌ | Não implementado |
| Definir itens de custos opcionais | ❌ | Não implementado |
| Definir itens de custos gerados automaticamente | ❌ | Não implementado |
| Informar cotação das moedas | ❌ | Não implementado |
| Informar receita | ❌ | Não implementado |

---

## 20. CUSTO POR PROCEDIMENTOS

| Funcionalidade | Status | Descrição |
|----------------|--------|-----------|
| Apuração de custos por procedimento/atendimento | ❌ | Não implementado |
| Apuração da receita por procedimento/atendimento | ❌ | Não implementado |
| Apuração da rentabilidade por procedimento/atendimento | ❌ | Não implementado |
| Apuração da margem de contribuição | ❌ | Não implementado |
| Visualização dos dados apurados | ❌ | Não implementado |
| Previsão de receita (procedimento SUS) | ❌ | Não implementado |
| Previsão de gastos (consumo farmácia) | ❌ | Não implementado |
| Custo médio do procedimento | ❌ | Não implementado |

---

## 21. INTERNAÇÃO

| Funcionalidade | Status | Descrição |
|----------------|--------|-----------|
| Registro de internações | ❌ | Não implementado |
| Alterar e incluir cadastros de paciente | ✅ | CRUD de pacientes implementado |
| Cancelar internações | ❌ | Não implementado |
| Manutenção de contas já fechadas | ❌ | Não implementado |
| Visualização da situação de leitos | ❌ | Não implementado |
| Reservas de leitos | ❌ | Não implementado |
| Emissão de relatórios diversos | ❌ | Não implementado |

---

## 22. MANUTENÇÃO PATRIMONIAL

| Funcionalidade | Status | Descrição |
|----------------|--------|-----------|
| CADASTRO PARA PATRIMÔNIOS E EQUIPAMENTOS | ⚠️ | Entidade Equipamentos existe mas patrimônio não |
| CADASTRO PARA FUNCIONÁRIOS DA MANUTENÇÃO | ❌ | Não implementado |
| CADASTRO PARA RELACIONAMENTO FUNCIONÁRIO X CENTRO DE CUSTO | ❌ | Não implementado |
| CADASTRO PARA OS TIPOS DE MANUTENÇÕES | ❌ | Não implementado |
| CADASTRO PARA MARCAS | ⚠️ | FabricantesEquipamento existe mas marcas não |
| CADASTRO PARA OS PROPRIETÁRIOS DO EQUIPAMENTO | ❌ | Não implementado |
| CADASTRO PARA OS MOTIVOS DE BAIXA | ❌ | Não implementado |
| CADASTRO PARA OS SETORES | ⚠️ | Departamentos existe mas específico para patrimônio não |
| CADASTRO PARA OS GRUPOS DE PATRIMÔNIOS E EQUIPAMENTOS | ❌ | Não implementado |
| EMISSÃO DE RELATÓRIO DE VALORES POR CENTRO DE CUSTOS | ❌ | Não implementado |
| EMISSÃO DE RELATÓRIO DE VALORES POR PATRIMÔNIO | ❌ | Não implementado |
| EMISSÃO DE RELATÓRIO DE VALORES POR FUNCIONÁRIOS | ❌ | Não implementado |
| EMISSÃO DE RELATÓRIO DE VALORES POR FORNECEDORES | ❌ | Não implementado |
| EMISSÃO DE RELATÓRIO DE VIDA ÚTIL DOS PATRIMÔNIOS | ❌ | Não implementado |
| EMISSÃO DE RELATÓRIO DE DEPRECIAÇÃO POR CONTA CONTÁBIL | ❌ | Não implementado |
| EMISSÃO DE RELATÓRIO DE LOCALIZAÇÃO DOS PATRIMÔNIOS | ❌ | Não implementado |
| EMISSÃO DE RELATÓRIO DE BENS 100% DEPRECIADOS | ❌ | Não implementado |
| EMISSÃO DE RELATÓRIO DE COMPRAS DE PATRIMÔNIOS | ❌ | Não implementado |
| EMISSÃO DE RELATÓRIO DE BAIXAS DE PATRIMÔNIOS | ❌ | Não implementado |
| EMISSÃO DE RELATÓRIO DE TRANSFERÊNCIAS DE PATRIMÔNIOS | ❌ | Não implementado |
| EMISSÃO DE RELATÓRIO DE DEPRECIAÇÃO | ❌ | Não implementado |
| EMISSÃO DE RELATÓRIO DE MANUTENÇÕES REALIZADAS | ❌ | Não implementado |
| Solicitação das manutenções necessárias | ❌ | Não implementado |
| Gerenciamento da geração de ordens de serviços | ❌ | Não implementado |
| Contabilização dos gastos nas ordens de serviços internas | ❌ | Não implementado |
| Reabertura de ordens de serviços fechadas | ❌ | Não implementado |
| Emissão de relatórios | ❌ | Não implementado |

---

## 23. ACOLHIMENTO E CLASSIFICAÇÃO DE RISCO

| Funcionalidade | Status | Descrição |
|----------------|--------|-----------|
| MÓDULO DE ACOLHIMENTO E CLASSIFICAÇÃO DE RISCO | ⚠️ | Estrutura existe mas módulo completo não |
| ACOLHIMENTO | ⚠️ | Parcialmente implementado |
| CLASSIFICAÇÃO DE RISCO | ⚠️ | ClassificacaoRiscoAtendimento existe mas funcionalidades não |
| Cadastrar as condutas possíveis para encaminhamento | ❌ | Não implementado |
| Cadastrar tabela para classificação de pacientes | ❌ | Não implementado |
| Gerenciar a fila de pacientes aguardando | ✅ | Entidade FilaEspera implementada |

---

## 24. PRESCRIÇÃO DE AMBULATORIAL HOSPITALAR

| Funcionalidade | Status | Descrição |
|----------------|--------|-----------|
| Registro da prescrição de medicamentos | ✅ | Entidade ReceitasMedicas implementada |
| Registro dos pedidos de exames laboratoriais | ✅ | Entidade Exames implementada |
| Registro dos pedidos de exames de imagens | ⚠️ | Exames existe mas específico imagens não |
| Registro das dietas | ❌ | Não implementado |
| Solicitação de bolsas de sangue | ❌ | Não implementado |
| Solicitação e registro de interconsultas | ❌ | Não implementado |
| Solicitação de procedimentos médicos e de enfermagem | ⚠️ | CatalogoProcedimentos existe |
| Registro de materiais especiais | ❌ | Não implementado |
| Controle de pedidos de exames com justificativa | ❌ | Não implementado |
| Controle de solicitações de antibióticos | ❌ | Não implementado |
| Kits de prescrição de medicamentos | ❌ | Não implementado |
| Prescrição padrão por médico/doença | ❌ | Não implementado |
| Visualização dos laudos de exames | ⚠️ | Campo laudo existe |
| Resumo de alta | ❌ | Não implementado |
| Solicitação de gases medicinais | ❌ | Não implementado |
| Repetição da prescrição anterior | ❌ | Não implementado |

---

## 25. PRESCRIÇÃO DE INTERNADOS

| Funcionalidade | Status | Descrição |
|----------------|--------|-----------|
| Registro da evolução | ⚠️ | Prontuarios existe mas específico não |
| Registro da prescrição de medicamentos | ✅ | ReceitasMedicas existe |
| Registro dos pedidos de exames laboratoriais | ✅ | Exames existe |
| Registro dos pedidos de exames de imagens | ⚠️ | Exames existe mas específico não |
| Registro das dietas (convencionais/enteral/parenteral) | ❌ | Não implementado |
| Solicitação de bolsas de sangue | ❌ | Não implementado |
| Solicitação e registro de interconsultas | ❌ | Não implementado |
| Solicitação de procedimentos médicos e de enfermagem | ⚠️ | CatalogoProcedimentos existe |
| Registro de materiais especiais | ❌ | Não implementado |
| Controle de pedidos de exames com justificativa | ❌ | Não implementado |
| Controle de solicitações de antibióticos | ❌ | Não implementado |
| Kits de prescrição de medicamentos | ❌ | Não implementado |
| Prescrição padrão por médico/doença | ❌ | Não implementado |
| Visualização dos laudos de exames | ⚠️ | Campo laudo existe |
| Resumo de alta | ❌ | Não implementado |
| Solicitação de gases medicinais | ❌ | Não implementado |
| Repetição da prescrição anterior | ❌ | Não implementado |
| Cadastro de vias de acesso para medicações | ❌ | Não implementado |
| Cadastro de tipos de dieta | ❌ | Não implementado |
| Cadastro de procedimentos de enfermagem | ❌ | Não implementado |
| Cadastro de procedimentos médicos | ⚠️ | CatalogoProcedimentos existe |
| Cadastro de kits para solicitação de medicações | ❌ | Não implementado |
| Cadastro de horários padrão por medicação | ❌ | Não implementado |
| Cadastro de gases medicinais | ❌ | Não implementado |
| Gerar etiquetas para pedidos avulsos | ❌ | Não implementado |
| Registrar, calcular e imprimir indicadores (APACHE, SOFA, SAPS-3) | ❌ | Não implementado |
| Controle dos hemoderivados solicitados/atendidos | ❌ | Não implementado |
| Controle de dietas atendidas | ❌ | Não implementado |
| Controle dos pedidos de antibióticos | ❌ | Não implementado |
| Reimprimir uma prescrição | ❌ | Não implementado |
| Cadastrar materiais necessários para aplicação | ❌ | Não implementado |
| Sugerir automaticamente quando prescrito | ❌ | Não implementado |
| Definir taxas de faturamento automáticas | ❌ | Não implementado |
| Cadastrar textos padrão para evolução multidisciplinar | ❌ | Não implementado |

---

## 26. CADASTROS BÁSICOS

| Funcionalidade | Status | Descrição |
|----------------|--------|-----------|
| Cadastro de município | ✅ | Entidade Cidades implementada |
| Cadastro de centros de custos | ❌ | Não implementado |
| Cadastro de especialidades | ✅ | Entidade EspecialidadesMedicas implementada |
| Cadastro de classificação internacional de doenças | ✅ | Entidade CidDoencas implementada |
| Cadastro de fornecedores | ❌ | Não implementado |
| Cadastro de estabelecimento de saúde | ✅ | Entidade Estabelecimentos implementada |
| Cadastro de profissionais não médicos | ✅ | Entidade ProfissionaisSaude implementada |
| Cadastro de empresas | ❌ | Não implementado |
| Cadastro de feriados | ❌ | Não implementado |
| Cadastro de médicos | ✅ | Entidade Medicos implementada |
| Cadastro de estados | ✅ | Entidade Estados implementada |
| Cadastro de cidades | ✅ | Entidade Cidades implementada |
| Cadastro de convênios | ✅ | Entidade Convenio implementada |
| Cadastro de alergias | ✅ | Entidade Alergias implementada |
| Cadastro de deficiências | ✅ | Entidade Deficiencias implementada |
| Cadastro de doenças | ✅ | Entidade Doencas implementada |
| Cadastro de vacinas | ✅ | Entidade Vacinas implementada |
| Cadastro de fabricantes de equipamentos | ✅ | Entidade FabricantesEquipamento implementada |
| Cadastro de conselhos profissionais | ✅ | Entidade ConselhosProfissionais implementada |
| Cadastro de departamentos | ✅ | Entidade Departamentos implementada |
| Cadastro de endereços | ✅ | Entidade Endereco implementada |

---

## RESUMO GERAL

### Módulos Completamente Implementados (✅)
- Cadastros básicos (pacientes, profissionais, estabelecimentos, etc.)
- Agendamento de consultas
- Atendimentos básicos
- Prontuário eletrônico básico
- Receitas médicas
- Exames (estrutura básica)
- Medicações e dispensação
- Vacinas e estoque de vacinas
- Procedimentos odontológicos
- Atenção primária (pré-natal, puericultura, etc.)
- Equipes de saúde e vínculos

### Módulos Parcialmente Implementados (⚠️)
- Prontuário (estrutura existe mas funcionalidades avançadas não)
- Exames de imagem (estrutura existe mas visualizador não)
- Classificação de risco (estrutura existe mas funcionalidades não)
- Controle de estoque (apenas vacinas)
- Laboratório (estrutura básica)
- Centro cirúrgico (entidades existem mas funcionalidades não)
- BI/Dashboards (não implementado)

### Módulos Não Implementados (❌)
- TFD (Tratamento Fora do Domicílio)
- Regulação/PPI completo
- Custo departamental
- Custo por procedimentos
- Internação
- Manutenção patrimonial completa
- Saúde mental específico
- Aplicativo cidadão
- Painel de chamadas/Totem
- SAME completo
- ESF completo (fichas específicas)
- Vigilância sanitária
- Acolhimento completo
- Prescrições avançadas (dietas, gases, etc.)

---

**Data de atualização:** 2024
**Versão do sistema:** 1.0.0

