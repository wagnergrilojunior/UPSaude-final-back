package com.upsaude.service.impl;

import com.upsaude.api.request.DeficienciasPacienteRequest;
import com.upsaude.api.request.DeficienciasPacienteSimplificadoRequest;
import com.upsaude.api.response.DeficienciasPacienteResponse;
import com.upsaude.entity.DeficienciasPaciente;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.Deficiencias;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.DeficienciasPacienteMapper;
import com.upsaude.repository.DeficienciasPacienteRepository;
import com.upsaude.repository.PacienteRepository;
import com.upsaude.repository.DeficienciasRepository;
import com.upsaude.repository.TenantRepository;
import com.upsaude.service.DeficienciasPacienteService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeficienciasPacienteServiceImpl implements DeficienciasPacienteService {

    private final DeficienciasPacienteRepository deficienciasPacienteRepository;
    private final DeficienciasPacienteMapper deficienciasPacienteMapper;
    private final PacienteRepository pacienteRepository;
    private final DeficienciasRepository deficienciasRepository;
    private final TenantRepository tenantRepository;

    @Override
    @Transactional
    @CacheEvict(value = "deficienciaspaciente", allEntries = true)
    public DeficienciasPacienteResponse criar(DeficienciasPacienteRequest request) {
        log.debug("Criando nova ligação paciente-deficiência");

        DeficienciasPaciente deficienciasPaciente = deficienciasPacienteMapper.fromRequest(request);

        Paciente paciente = pacienteRepository.findById(request.getPaciente())
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPaciente()));
        deficienciasPaciente.setPaciente(paciente);

        Deficiencias deficiencia = deficienciasRepository.findById(request.getDeficiencia())
                .orElseThrow(() -> new NotFoundException("Deficiência não encontrada com ID: " + request.getDeficiencia()));
        deficienciasPaciente.setDeficiencia(deficiencia);

        deficienciasPaciente.setActive(true);

        DeficienciasPaciente deficienciasPacienteSalvo = deficienciasPacienteRepository.save(deficienciasPaciente);
        log.info("Ligação paciente-deficiência criada com sucesso. ID: {}", deficienciasPacienteSalvo.getId());

        return deficienciasPacienteMapper.toResponse(deficienciasPacienteSalvo);
    }

    @Override
    @Transactional
    @CacheEvict(value = "deficienciaspaciente", allEntries = true)
    public DeficienciasPacienteResponse criarSimplificado(DeficienciasPacienteSimplificadoRequest request) {
        log.debug("Criando nova ligação paciente-deficiência simplificada - Paciente: {}, Tenant: {}, Deficiência: {}",
                request.getPaciente(), request.getTenant(), request.getDeficiencia());

        if (request == null) {
            throw new BadRequestException("Dados da ligação paciente-deficiência são obrigatórios");
        }

        if (request.getPaciente() == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        if (request.getTenant() == null) {
            throw new BadRequestException("ID do tenant é obrigatório");
        }

        if (request.getDeficiencia() == null) {
            throw new BadRequestException("ID da deficiência é obrigatório");
        }

        Paciente paciente = pacienteRepository.findById(request.getPaciente())
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPaciente()));

        Tenant tenant = tenantRepository.findById(request.getTenant())
                .orElseThrow(() -> new NotFoundException("Tenant não encontrado com ID: " + request.getTenant()));

        Deficiencias deficiencia = deficienciasRepository.findById(request.getDeficiencia())
                .orElseThrow(() -> new NotFoundException("Deficiência não encontrada com ID: " + request.getDeficiencia()));

        DeficienciasPaciente deficienciasPaciente = new DeficienciasPaciente();
        deficienciasPaciente.setPaciente(paciente);
        deficienciasPaciente.setTenant(tenant);
        deficienciasPaciente.setDeficiencia(deficiencia);
        deficienciasPaciente.setActive(true);
        deficienciasPaciente.setPossuiLaudo(false);

        DeficienciasPaciente deficienciasPacienteSalvo = deficienciasPacienteRepository.save(deficienciasPaciente);
        log.info("Ligação paciente-deficiência criada com sucesso (simplificado). ID: {}", deficienciasPacienteSalvo.getId());

        return deficienciasPacienteMapper.toResponse(deficienciasPacienteSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "deficienciaspaciente", key = "#id")
    public DeficienciasPacienteResponse obterPorId(UUID id) {
        log.debug("Buscando ligação paciente-deficiência por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID da ligação paciente-deficiência é obrigatório");
        }

        DeficienciasPaciente deficienciasPaciente = deficienciasPacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ligação paciente-deficiência não encontrada com ID: " + id));

        return deficienciasPacienteMapper.toResponse(deficienciasPaciente);
    }

    @Override
    public Page<DeficienciasPacienteResponse> listar(Pageable pageable) {
        log.debug("Listando ligações paciente-deficiência paginadas. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<DeficienciasPaciente> deficienciasPacientes = deficienciasPacienteRepository.findAll(pageable);
        return deficienciasPacientes.map(deficienciasPacienteMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "deficienciaspaciente", key = "#id")
    public DeficienciasPacienteResponse atualizar(UUID id, DeficienciasPacienteRequest request) {
        log.debug("Atualizando ligação paciente-deficiência. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da ligação paciente-deficiência é obrigatório");
        }

        DeficienciasPaciente deficienciasPacienteExistente = deficienciasPacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ligação paciente-deficiência não encontrada com ID: " + id));

        atualizarDadosDeficienciasPaciente(deficienciasPacienteExistente, request);

        DeficienciasPaciente deficienciasPacienteAtualizado = deficienciasPacienteRepository.save(deficienciasPacienteExistente);
        log.info("Ligação paciente-deficiência atualizada com sucesso. ID: {}", deficienciasPacienteAtualizado.getId());

        return deficienciasPacienteMapper.toResponse(deficienciasPacienteAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "deficienciaspaciente", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo ligação paciente-deficiência. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da ligação paciente-deficiência é obrigatório");
        }

        DeficienciasPaciente deficienciasPaciente = deficienciasPacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ligação paciente-deficiência não encontrada com ID: " + id));

        if (Boolean.FALSE.equals(deficienciasPaciente.getActive())) {
            throw new BadRequestException("Ligação paciente-deficiência já está inativa");
        }

        deficienciasPaciente.setActive(false);
        deficienciasPacienteRepository.save(deficienciasPaciente);
        log.info("Ligação paciente-deficiência excluída (desativada) com sucesso. ID: {}", id);
    }

    private void atualizarDadosDeficienciasPaciente(DeficienciasPaciente deficienciasPaciente, DeficienciasPacienteRequest request) {

        if (request.getDataDiagnostico() != null) {
            deficienciasPaciente.setDataDiagnostico(request.getDataDiagnostico());
        }

        if (request.getObservacoes() != null) {
            deficienciasPaciente.setObservacoes(request.getObservacoes());
        }

        if (request.getPaciente() != null) {
            Paciente paciente = pacienteRepository.findById(request.getPaciente())
                    .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPaciente()));
            deficienciasPaciente.setPaciente(paciente);
        }

        if (request.getDeficiencia() != null) {
            Deficiencias deficiencia = deficienciasRepository.findById(request.getDeficiencia())
                    .orElseThrow(() -> new NotFoundException("Deficiência não encontrada com ID: " + request.getDeficiencia()));
            deficienciasPaciente.setDeficiencia(deficiencia);
        }
    }
}
