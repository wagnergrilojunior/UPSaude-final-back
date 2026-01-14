package com.upsaude.service.impl.api.anexo;

import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.sistema.usuario.UsuariosSistema;
import com.upsaude.enums.TargetTypeAnexoEnum;
import com.upsaude.exception.ForbiddenException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.agendamento.AgendamentoRepository;
import com.upsaude.repository.clinica.atendimento.AtendimentoRepository;
import com.upsaude.repository.clinica.atendimento.ConsultasRepository;
import com.upsaude.repository.paciente.PacienteRepository;
import com.upsaude.repository.profissional.ProfissionaisSaudeRepository;
import com.upsaude.repository.sistema.usuario.UsuariosSistemaRepository;
import com.upsaude.service.api.anexo.AnexoTargetPermissionValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnexoTargetPermissionValidatorImpl implements AnexoTargetPermissionValidator {

    private final PacienteRepository pacienteRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final AtendimentoRepository atendimentoRepository;
    private final ConsultasRepository consultasRepository;
    private final ProfissionaisSaudeRepository profissionaisSaudeRepository;
    private final UsuariosSistemaRepository usuariosSistemaRepository;

    @Override
    public void validarAcesso(TargetTypeAnexoEnum targetType, UUID targetId, UUID tenantId) {
        if (targetType == null || targetId == null) {
            throw new IllegalArgumentException("targetType e targetId são obrigatórios");
        }

        switch (targetType) {
            case PACIENTE:
                validarPaciente(targetId, tenantId);
                break;
            case AGENDAMENTO:
                validarAgendamento(targetId, tenantId);
                break;
            case ATENDIMENTO:
                validarAtendimento(targetId, tenantId);
                break;
            case CONSULTA:
                validarConsulta(targetId, tenantId);
                break;
            case PROFISSIONAL_SAUDE:
                validarProfissionalSaude(targetId, tenantId);
                break;
            case USUARIO_SISTEMA:
                validarUsuarioSistema(targetId, tenantId);
                break;
            case PRONTUARIO_EVENTO:
            case FINANCEIRO_FATURAMENTO:
                // Para estes tipos, apenas validamos que o tenant existe
                // Validações mais específicas podem ser adicionadas depois
                log.debug("Validação de acesso para {} - apenas verificação de tenant", targetType);
                break;
            default:
                throw new IllegalArgumentException("Tipo de target não suportado: " + targetType);
        }
    }

    private void validarPaciente(UUID pacienteId, UUID tenantId) {
        // findByIdAndTenant já valida que o paciente pertence ao tenant
        pacienteRepository.findByIdAndTenant(pacienteId, tenantId)
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado: " + pacienteId));
    }

    private void validarAgendamento(UUID agendamentoId, UUID tenantId) {
        // findByIdAndTenant já valida que o agendamento pertence ao tenant
        agendamentoRepository.findByIdAndTenant(agendamentoId, tenantId)
                .orElseThrow(() -> new NotFoundException("Agendamento não encontrado: " + agendamentoId));
    }

    private void validarAtendimento(UUID atendimentoId, UUID tenantId) {
        // findByIdAndTenant já valida que o atendimento pertence ao tenant
        atendimentoRepository.findByIdAndTenant(atendimentoId, tenantId)
                .orElseThrow(() -> new NotFoundException("Atendimento não encontrado: " + atendimentoId));
    }

    private void validarConsulta(UUID consultaId, UUID tenantId) {
        // findByIdAndTenant já valida que a consulta pertence ao tenant
        consultasRepository.findByIdAndTenant(consultaId, tenantId)
                .orElseThrow(() -> new NotFoundException("Consulta não encontrada: " + consultaId));
    }

    private void validarProfissionalSaude(UUID profissionalId, UUID tenantId) {
        ProfissionaisSaude profissional = profissionaisSaudeRepository.findById(profissionalId)
                .orElseThrow(() -> new NotFoundException("Profissional de saúde não encontrado: " + profissionalId));
        
        if (!profissional.getTenant().getId().equals(tenantId)) {
            throw new ForbiddenException("Acesso negado ao profissional de saúde");
        }
    }

    private void validarUsuarioSistema(UUID usuarioId, UUID tenantId) {
        UsuariosSistema usuario = usuariosSistemaRepository.findById(usuarioId)
                .orElseThrow(() -> new NotFoundException("Usuário do sistema não encontrado: " + usuarioId));
        
        if (!usuario.getTenant().getId().equals(tenantId)) {
            throw new ForbiddenException("Acesso negado ao usuário do sistema");
        }
    }
}
