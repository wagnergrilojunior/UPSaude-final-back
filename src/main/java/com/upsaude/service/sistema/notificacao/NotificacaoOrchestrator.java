package com.upsaude.service.sistema.notificacao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.entity.sistema.notificacao.Notificacao;
import com.upsaude.entity.sistema.notificacao.TemplateNotificacao;
import com.upsaude.entity.sistema.usuario.UsuariosSistema;
import com.upsaude.enums.CanalNotificacaoEnum;
import com.upsaude.enums.TipoNotificacaoEnum;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.repository.sistema.notificacao.NotificacaoRepository;
import com.upsaude.repository.sistema.notificacao.TemplateNotificacaoRepository;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificacaoOrchestrator {

    private final NotificacaoRepository notificacaoRepository;
    private final TemplateNotificacaoRepository templateNotificacaoRepository;
    private final TenantService tenantService;
    private final TenantRepository tenantRepository;
    private final ObjectMapper objectMapper;

    
    @Transactional
    public Notificacao criarNotificacao(
            TipoNotificacaoEnum tipoNotificacao,
            CanalNotificacaoEnum canal,
            String destinatarioEmail,
            String destinatarioNome,
            String assunto,
            String mensagem,
            UUID pacienteId,
            UUID profissionalId,
            UUID agendamentoId,
            UUID estabelecimentoId,
            OffsetDateTime dataEnvioPrevista,
            Map<String, Object> parametros) {
        
        UUID tenantId = Objects.requireNonNull(tenantService.validarTenantAtual(), "Tenant ID não pode ser null");
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) {
            
            tenant = tenantRepository.findById(tenantId)
                    .orElseThrow(() -> new IllegalStateException("Tenant não encontrado com ID: " + tenantId));
        }

        
        Optional<TemplateNotificacao> templateOpt = Optional.empty();
        if (estabelecimentoId != null) {
            templateOpt = templateNotificacaoRepository
                    .findByEstabelecimentoIdAndTipoNotificacaoAndCanalAndTenantIdAndActiveTrue(
                            estabelecimentoId, tipoNotificacao, canal, tenantId);
        }
        if (templateOpt.isEmpty()) {
            templateOpt = templateNotificacaoRepository
                    .findByEstabelecimentoIdIsNullAndTipoNotificacaoAndCanalAndTenantIdAndActiveTrue(
                            tipoNotificacao, canal, tenantId);
        }

        String parametrosJson = null;
        if (parametros != null && !parametros.isEmpty()) {
            try {
                parametrosJson = objectMapper.writeValueAsString(parametros);
            } catch (JsonProcessingException e) {
                log.warn("Erro ao serializar parâmetros da notificação", e);
            }
        }

        

        Notificacao notificacao = new Notificacao();
        notificacao.setTenant(tenant);
        notificacao.setActive(true);
        notificacao.setTipoNotificacao(tipoNotificacao);
        notificacao.setCanal(canal);
        notificacao.setDestinatario(destinatarioEmail);
        notificacao.setAssunto(assunto);
        notificacao.setMensagem(mensagem);
        notificacao.setStatusEnvio("PENDENTE");
        notificacao.setDataEnvioPrevista(dataEnvioPrevista != null ? dataEnvioPrevista : OffsetDateTime.now());
        notificacao.setTentativasEnvio(0);
        notificacao.setMaximoTentativas(3);
        notificacao.setParametrosJson(parametrosJson);

        if (pacienteId != null) {
            Paciente paciente = new Paciente();
            paciente.setId(pacienteId);
            notificacao.setPaciente(paciente);
        }
        if (profissionalId != null) {
            com.upsaude.entity.profissional.ProfissionaisSaude profissional = new com.upsaude.entity.profissional.ProfissionaisSaude();
            profissional.setId(profissionalId);
            notificacao.setProfissional(profissional);
        }
        if (agendamentoId != null) {
            Agendamento agendamento = new Agendamento();
            agendamento.setId(agendamentoId);
            notificacao.setAgendamento(agendamento);
        }
        if (estabelecimentoId != null) {
            com.upsaude.entity.estabelecimento.Estabelecimentos estabelecimento = new com.upsaude.entity.estabelecimento.Estabelecimentos();
            estabelecimento.setId(estabelecimentoId);
            notificacao.setEstabelecimento(estabelecimento);
        }
        if (templateOpt.isPresent()) {
            notificacao.setTemplate(templateOpt.get());
        }

        Notificacao saved = notificacaoRepository.save(notificacao);
        log.info("Notificação criada com sucesso. ID: {}, Tipo: {}, Destinatário: {}", 
                saved.getId(), tipoNotificacao, destinatarioEmail);
        return saved;
    }

    
    @Transactional
    public void notificarUsuarioCriado(UsuariosSistema usuario, String email, String nome) {
        if (email == null || email.trim().isEmpty()) {
            log.warn("Email não informado para notificação de usuário criado. Usuário ID: {}", usuario.getId());
            return;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("nome", nome != null ? nome : "");
        params.put("email", email);
        params.put("dataHora", OffsetDateTime.now().toString());

        criarNotificacao(
                TipoNotificacaoEnum.USUARIO_CRIADO,
                CanalNotificacaoEnum.EMAIL,
                email,
                nome,
                "Bem-vindo ao UPSaude",
                "Seu usuário foi criado com sucesso.",
                null,
                null,
                null,
                null, 
                OffsetDateTime.now(),
                params
        );
    }

    
    @Transactional
    public void notificarSenhaAlterada(String email, String nome) {
        if (email == null || email.trim().isEmpty()) {
            log.warn("Email não informado para notificação de senha alterada");
            return;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("nome", nome != null ? nome : "");
        params.put("email", email);
        params.put("dataHora", OffsetDateTime.now().toString());

        criarNotificacao(
                TipoNotificacaoEnum.SENHA_ALTERADA,
                CanalNotificacaoEnum.EMAIL,
                email,
                nome,
                "Senha alterada com sucesso",
                "Sua senha foi alterada com sucesso.",
                null,
                null,
                null,
                null,
                OffsetDateTime.now(),
                params
        );
    }

    
    @Transactional
    public void notificarDadosPessoaisAtualizados(String email, String nome, UUID pacienteId, UUID estabelecimentoId) {
        if (email == null || email.trim().isEmpty()) {
            log.warn("Email não informado para notificação de dados pessoais atualizados");
            return;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("nome", nome != null ? nome : "");
        params.put("email", email);
        params.put("dataHora", OffsetDateTime.now().toString());

        criarNotificacao(
                TipoNotificacaoEnum.DADOS_PESSOAIS_ATUALIZADOS,
                CanalNotificacaoEnum.EMAIL,
                email,
                nome,
                "Dados pessoais atualizados",
                "Seus dados pessoais foram atualizados com sucesso.",
                pacienteId,
                null,
                null,
                estabelecimentoId,
                OffsetDateTime.now(),
                params
        );
    }

    
    @Transactional
    public void notificarAgendamentoConfirmado(Agendamento agendamento) {
        if (agendamento.getPaciente() == null) {
            log.warn("Agendamento sem paciente. Não é possível enviar notificação. Agendamento ID: {}", agendamento.getId());
            return;
        }

        String email = obterEmailPaciente(agendamento.getPaciente());
        if (email == null || email.trim().isEmpty()) {
            log.warn("Paciente sem email. Não é possível enviar notificação. Agendamento ID: {}", agendamento.getId());
            return;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("pacienteNome", agendamento.getPaciente().getNomeCompleto());
        params.put("dataHora", agendamento.getDataHora() != null ? agendamento.getDataHora().toString() : "");
        params.put("statusAgendamento", agendamento.getStatus() != null ? agendamento.getStatus().getDescricao() : "");

        if (agendamento.getEstabelecimento() != null && agendamento.getEstabelecimento().getDadosIdentificacao() != null) {
            params.put("estabelecimentoNome", agendamento.getEstabelecimento().getDadosIdentificacao().getNome());
        }
        if (agendamento.getProfissional() != null && agendamento.getProfissional().getDadosPessoaisBasicos() != null) {
            params.put("profissionalNome", agendamento.getProfissional().getDadosPessoaisBasicos().getNomeCompleto());
        }
        if (agendamento.getMedico() != null && agendamento.getMedico().getDadosPessoaisBasicos() != null) {
            params.put("medicoNome", agendamento.getMedico().getDadosPessoaisBasicos().getNomeCompleto());
        }

        criarNotificacao(
                TipoNotificacaoEnum.AGENDAMENTO_CONFIRMADO,
                CanalNotificacaoEnum.EMAIL,
                email,
                agendamento.getPaciente().getNomeCompleto(),
                "Agendamento confirmado",
                "Seu agendamento foi confirmado.",
                agendamento.getPaciente().getId(),
                agendamento.getProfissional() != null ? agendamento.getProfissional().getId() : null,
                agendamento.getId(),
                agendamento.getEstabelecimento() != null ? agendamento.getEstabelecimento().getId() : null,
                OffsetDateTime.now(),
                params
        );
    }

    
    @Transactional
    public void notificarAgendamentoCancelado(Agendamento agendamento) {
        if (agendamento.getPaciente() == null) {
            log.warn("Agendamento sem paciente. Não é possível enviar notificação. Agendamento ID: {}", agendamento.getId());
            return;
        }

        String email = obterEmailPaciente(agendamento.getPaciente());
        if (email == null || email.trim().isEmpty()) {
            log.warn("Paciente sem email. Não é possível enviar notificação. Agendamento ID: {}", agendamento.getId());
            return;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("pacienteNome", agendamento.getPaciente().getNomeCompleto());
        params.put("dataHora", agendamento.getDataHora() != null ? agendamento.getDataHora().toString() : "");
        params.put("motivoCancelamento", agendamento.getMotivoCancelamento() != null ? agendamento.getMotivoCancelamento() : "");

        if (agendamento.getEstabelecimento() != null && agendamento.getEstabelecimento().getDadosIdentificacao() != null) {
            params.put("estabelecimentoNome", agendamento.getEstabelecimento().getDadosIdentificacao().getNome());
        }

        criarNotificacao(
                TipoNotificacaoEnum.AGENDAMENTO_CANCELADO,
                CanalNotificacaoEnum.EMAIL,
                email,
                agendamento.getPaciente().getNomeCompleto(),
                "Agendamento cancelado",
                "Seu agendamento foi cancelado.",
                agendamento.getPaciente().getId(),
                null,
                agendamento.getId(),
                agendamento.getEstabelecimento() != null ? agendamento.getEstabelecimento().getId() : null,
                OffsetDateTime.now(),
                params
        );
    }

    
    @Transactional
    public void agendarLembretesAgendamento(Agendamento agendamento) {
        if (agendamento.getPaciente() == null || agendamento.getDataHora() == null) {
            return;
        }

        String email = obterEmailPaciente(agendamento.getPaciente());
        if (email == null || email.trim().isEmpty()) {
            return;
        }

        OffsetDateTime agora = OffsetDateTime.now();
        OffsetDateTime dataHoraAgendamento = agendamento.getDataHora();
        
        
        OffsetDateTime lembrete24h = dataHoraAgendamento.minusHours(24);
        if (lembrete24h.isAfter(agora)) {
            Map<String, Object> params24h = criarParamsAgendamento(agendamento);
            criarNotificacao(
                    TipoNotificacaoEnum.LEMBRETE_24H,
                    CanalNotificacaoEnum.EMAIL,
                    email,
                    agendamento.getPaciente().getNomeCompleto(),
                    "Lembrete: Agendamento em 24 horas",
                    "Você tem um agendamento em 24 horas.",
                    agendamento.getPaciente().getId(),
                    null,
                    agendamento.getId(),
                    agendamento.getEstabelecimento() != null ? agendamento.getEstabelecimento().getId() : null,
                    lembrete24h,
                    params24h
            );
        }

        
        OffsetDateTime lembrete1h = dataHoraAgendamento.minusHours(1);
        if (lembrete1h.isAfter(agora)) {
            Map<String, Object> params1h = criarParamsAgendamento(agendamento);
            criarNotificacao(
                    TipoNotificacaoEnum.LEMBRETE_1H,
                    CanalNotificacaoEnum.EMAIL,
                    email,
                    agendamento.getPaciente().getNomeCompleto(),
                    "Lembrete: Agendamento em 1 hora",
                    "Você tem um agendamento em 1 hora.",
                    agendamento.getPaciente().getId(),
                    null,
                    agendamento.getId(),
                    agendamento.getEstabelecimento() != null ? agendamento.getEstabelecimento().getId() : null,
                    lembrete1h,
                    params1h
            );
        }
    }

    private Map<String, Object> criarParamsAgendamento(Agendamento agendamento) {
        Map<String, Object> params = new HashMap<>();
        params.put("pacienteNome", agendamento.getPaciente().getNomeCompleto());
        params.put("dataHora", agendamento.getDataHora() != null ? agendamento.getDataHora().toString() : "");
        
        if (agendamento.getEstabelecimento() != null && agendamento.getEstabelecimento().getDadosIdentificacao() != null) {
            params.put("estabelecimentoNome", agendamento.getEstabelecimento().getDadosIdentificacao().getNome());
        }
        if (agendamento.getProfissional() != null && agendamento.getProfissional().getDadosPessoaisBasicos() != null) {
            params.put("profissionalNome", agendamento.getProfissional().getDadosPessoaisBasicos().getNomeCompleto());
        }
        if (agendamento.getMedico() != null && agendamento.getMedico().getDadosPessoaisBasicos() != null) {
            params.put("medicoNome", agendamento.getMedico().getDadosPessoaisBasicos().getNomeCompleto());
        }
        
        return params;
    }

    private String obterEmailPaciente(Paciente paciente) {
        if (paciente.getContatos() != null) {
            return paciente.getContatos().stream()
                    .filter(c -> c.getTipo() == com.upsaude.enums.TipoContatoEnum.EMAIL)
                    .map(c -> c.getEmail())
                    .filter(e -> e != null && !e.trim().isEmpty())
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
}
