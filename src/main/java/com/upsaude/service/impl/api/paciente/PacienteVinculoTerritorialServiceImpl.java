package com.upsaude.service.impl.api.paciente;

import com.upsaude.entity.paciente.PacienteVinculoTerritorial;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.paciente.PacienteVinculoTerritorialRepository;
import com.upsaude.service.api.paciente.PacienteVinculoTerritorialService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PacienteVinculoTerritorialServiceImpl implements PacienteVinculoTerritorialService {

    private final PacienteVinculoTerritorialRepository repository;
    private final TenantService tenantService;

    @Override
    @Transactional
    public PacienteVinculoTerritorial criar(PacienteVinculoTerritorial vinculo) {
        tenantService.validarTenantAtual();

        Optional<PacienteVinculoTerritorial> vinculoAtivo = repository.findAtivoByPacienteId(vinculo.getPaciente().getId());
        if (vinculoAtivo.isPresent()) {
            PacienteVinculoTerritorial anterior = vinculoAtivo.get();
            anterior.setActive(false);
            anterior.setDataFim(LocalDate.now());
            repository.save(anterior);
        }

        vinculo.setTenant(tenantService.obterTenantDoUsuarioAutenticado());
        vinculo.setActive(true);
        return repository.save(vinculo);
    }

    @Override
    @Transactional
    public PacienteVinculoTerritorial atualizar(UUID id, PacienteVinculoTerritorial vinculo) {
        tenantService.validarTenantAtual();
        PacienteVinculoTerritorial existente = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vínculo territorial não encontrado"));

        existente.setMunicipioIbge(vinculo.getMunicipioIbge());
        existente.setCnesEstabelecimento(vinculo.getCnesEstabelecimento());
        existente.setIneEquipe(vinculo.getIneEquipe());
        existente.setMicroarea(vinculo.getMicroarea());
        existente.setDataInicio(vinculo.getDataInicio());
        existente.setDataFim(vinculo.getDataFim());
        existente.setOrigem(vinculo.getOrigem());
        existente.setActive(vinculo.getActive());
        existente.setObservacoes(vinculo.getObservacoes());

        return repository.save(existente);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        tenantService.validarTenantAtual();
        PacienteVinculoTerritorial vinculo = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vínculo territorial não encontrado"));
        repository.delete(vinculo);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PacienteVinculoTerritorial> obterPorId(UUID id) {
        tenantService.validarTenantAtual();
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PacienteVinculoTerritorial> listarPorPaciente(UUID pacienteId) {
        tenantService.validarTenantAtual();
        return repository.findByPacienteIdOrderByDataInicioDesc(pacienteId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PacienteVinculoTerritorial> obterVinculoAtivo(UUID pacienteId) {
        tenantService.validarTenantAtual();
        return repository.findAtivoByPacienteId(pacienteId);
    }

    @Override
    @Transactional
    public void desativarVinculoAtivo(UUID pacienteId) {
        tenantService.validarTenantAtual();
        Optional<PacienteVinculoTerritorial> vinculoAtivo = repository.findAtivoByPacienteId(pacienteId);
        if (vinculoAtivo.isPresent()) {
            PacienteVinculoTerritorial vinculo = vinculoAtivo.get();
            vinculo.setActive(false);
            vinculo.setDataFim(LocalDate.now());
            repository.save(vinculo);
        }
    }
}
