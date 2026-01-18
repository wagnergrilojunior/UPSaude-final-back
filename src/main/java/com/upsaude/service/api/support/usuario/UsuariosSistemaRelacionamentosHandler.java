package com.upsaude.service.api.support.usuario;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.sistema.usuario.UsuariosSistemaRequest;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.sistema.auth.User;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.entity.sistema.usuario.UsuariosSistema;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.paciente.PacienteRepository;
import com.upsaude.repository.profissional.MedicosRepository;
import com.upsaude.repository.profissional.ProfissionaisSaudeRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuariosSistemaRelacionamentosHandler {

    private final TenantRepository tenantRepository;
    private final MedicosRepository medicosRepository;
    private final ProfissionaisSaudeRepository profissionaisSaudeRepository;
    private final PacienteRepository pacienteRepository;

    public void resolverParaCriacao(UsuariosSistema entity, UsuariosSistemaRequest request, User user) {
        entity.setUser(user);
        resolverTenant(entity, request);
        resolverMedico(entity, request);
        resolverProfissionalSaude(entity, request);
        resolverPaciente(entity, request);
    }

    public void resolverParaAtualizacao(UsuariosSistema entity, UsuariosSistemaRequest request) {
        resolverTenant(entity, request);
        resolverMedico(entity, request);
        resolverProfissionalSaude(entity, request);
        resolverPaciente(entity, request);
    }

    private void resolverTenant(UsuariosSistema entity, UsuariosSistemaRequest request) {
        UUID tenantId = request.getTenantId();
        if (tenantId != null) {
            Tenant tenant = tenantRepository.findById(tenantId)
                    .orElseThrow(() -> new NotFoundException("Tenant não encontrado com ID: " + tenantId));
            entity.setTenant(tenant);
        }
    }

    private void resolverMedico(UsuariosSistema entity, UsuariosSistemaRequest request) {
        UUID medicoId = request.getMedico();
        if (medicoId != null) {
            Medicos medico = medicosRepository.findById(medicoId)
                    .orElseThrow(() -> new NotFoundException("Médico não encontrado com ID: " + medicoId));
            entity.setMedico(medico);
        }
    }

    private void resolverProfissionalSaude(UsuariosSistema entity, UsuariosSistemaRequest request) {
        UUID profissionalId = request.getProfissionalSaude();
        if (profissionalId != null) {
            ProfissionaisSaude profissional = profissionaisSaudeRepository.findById(profissionalId)
                    .orElseThrow(
                            () -> new NotFoundException("Profissional de saúde não encontrado com ID: " + profissionalId));
            entity.setProfissionalSaude(profissional);
        }
    }

    private void resolverPaciente(UsuariosSistema entity, UsuariosSistemaRequest request) {
        UUID pacienteId = request.getPaciente();
        if (pacienteId != null) {
            Paciente paciente = pacienteRepository.findById(pacienteId)
                    .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + pacienteId));
            entity.setPaciente(paciente);
        }
    }
}
