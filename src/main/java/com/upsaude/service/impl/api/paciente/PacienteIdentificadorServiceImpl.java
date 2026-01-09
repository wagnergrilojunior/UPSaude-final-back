package com.upsaude.service.impl.api.paciente;

import com.upsaude.entity.paciente.PacienteIdentificador;
import com.upsaude.enums.TipoIdentificadorEnum;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.paciente.PacienteIdentificadorRepository;
import com.upsaude.service.api.paciente.PacienteIdentificadorService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PacienteIdentificadorServiceImpl implements PacienteIdentificadorService {

    private final PacienteIdentificadorRepository repository;
    private final TenantService tenantService;

    @Override
    @Transactional
    public PacienteIdentificador criar(PacienteIdentificador identificador) {
        tenantService.validarTenantAtual();
        identificador.setTenant(tenantService.obterTenantDoUsuarioAutenticado());
        return repository.save(identificador);
    }

    @Override
    @Transactional
    public PacienteIdentificador atualizar(UUID id, PacienteIdentificador identificador) {
        tenantService.validarTenantAtual();
        PacienteIdentificador existente = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Identificador não encontrado"));

        existente.setTipo(identificador.getTipo());
        existente.setValor(identificador.getValor());
        existente.setOrigem(identificador.getOrigem());
        existente.setValidado(identificador.getValidado());
        existente.setDataValidacao(identificador.getDataValidacao());
        existente.setPrincipal(identificador.getPrincipal());
        existente.setObservacoes(identificador.getObservacoes());

        return repository.save(existente);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        tenantService.validarTenantAtual();
        PacienteIdentificador identificador = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Identificador não encontrado"));
        repository.delete(identificador);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PacienteIdentificador> obterPorId(UUID id) {
        tenantService.validarTenantAtual();
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PacienteIdentificador> listarPorPaciente(UUID pacienteId) {
        tenantService.validarTenantAtual();
        return repository.findByPacienteIdAndActiveTrue(pacienteId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PacienteIdentificador> obterPrincipalPorTipo(UUID pacienteId, TipoIdentificadorEnum tipo) {
        tenantService.validarTenantAtual();
        return repository.findPrincipalByPacienteIdAndTipo(pacienteId, tipo);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PacienteIdentificador> buscarPorTipoEValor(TipoIdentificadorEnum tipo, String valor, UUID tenantId) {
        return repository.findByTipoAndValorAndTenantId(tipo, valor, tenantId);
    }
}
