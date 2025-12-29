package com.upsaude.service.impl.api.paciente;

import com.upsaude.entity.paciente.PacienteContato;
import com.upsaude.enums.TipoContatoEnum;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.paciente.PacienteContatoRepository;
import com.upsaude.service.api.paciente.PacienteContatoService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PacienteContatoServiceImpl implements PacienteContatoService {

    private final PacienteContatoRepository repository;
    private final TenantService tenantService;

    @Override
    @Transactional
    public PacienteContato criar(PacienteContato contato) {
        tenantService.validarTenantAtual();
        contato.setTenant(tenantService.obterTenantDoUsuarioAutenticado());
        return repository.save(contato);
    }

    @Override
    @Transactional
    public PacienteContato atualizar(UUID id, PacienteContato contato) {
        tenantService.validarTenantAtual();
        PacienteContato existente = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contato não encontrado"));
        
        existente.setTipo(contato.getTipo());
        existente.setValor(contato.getValor());
        existente.setPrincipal(contato.getPrincipal());
        existente.setVerificado(contato.getVerificado());
        existente.setObservacoes(contato.getObservacoes());
        
        return repository.save(existente);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        tenantService.validarTenantAtual();
        PacienteContato contato = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contato não encontrado"));
        repository.delete(contato);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PacienteContato> obterPorId(UUID id) {
        tenantService.validarTenantAtual();
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PacienteContato> listarPorPaciente(UUID pacienteId) {
        tenantService.validarTenantAtual();
        return repository.findByPacienteIdAndActiveTrue(pacienteId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PacienteContato> obterPrincipalPorTipo(UUID pacienteId, TipoContatoEnum tipo) {
        tenantService.validarTenantAtual();
        return repository.findPrincipalByPacienteIdAndTipo(pacienteId, tipo);
    }

    @Override
    @Transactional
    public PacienteContato verificarContato(UUID id) {
        tenantService.validarTenantAtual();
        PacienteContato contato = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contato não encontrado"));
        
        contato.setVerificado(true);
        contato.setDataVerificacao(OffsetDateTime.now());
        
        return repository.save(contato);
    }
}

