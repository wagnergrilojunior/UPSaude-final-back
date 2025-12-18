package com.upsaude.service.impl;

import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.upsaude.api.request.clinica.doencas.DoencasPacienteRequest;
import com.upsaude.api.request.clinica.doencas.DoencasPacienteSimplificadoRequest;
import com.upsaude.api.response.clinica.doencas.DoencasPacienteResponse;
import com.upsaude.entity.clinica.doencas.Doencas;
import com.upsaude.entity.clinica.doencas.DoencasPaciente;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.clinica.doencas.DoencasPacienteMapper;
import com.upsaude.mapper.embeddable.AcompanhamentoDoencaPacienteMapper;
import com.upsaude.mapper.embeddable.DiagnosticoDoencaPacienteMapper;
import com.upsaude.mapper.embeddable.TratamentoAtualDoencaPacienteMapper;
import com.upsaude.repository.clinica.doencas.DoencasPacienteRepository;
import com.upsaude.repository.clinica.doencas.DoencasRepository;
import com.upsaude.repository.paciente.PacienteRepository;
import com.upsaude.repository.sistema.TenantRepository;
import com.upsaude.service.clinica.doencas.DoencasPacienteService;
import com.upsaude.service.sistema.TenantService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DoencasPacienteServiceImpl implements DoencasPacienteService {

    private final DoencasPacienteRepository doencasPacienteRepository;
    private final DoencasPacienteMapper doencasPacienteMapper;
    private final DiagnosticoDoencaPacienteMapper diagnosticoDoencaPacienteMapper;
    private final AcompanhamentoDoencaPacienteMapper acompanhamentoDoencaPacienteMapper;
    private final TratamentoAtualDoencaPacienteMapper tratamentoAtualDoencaPacienteMapper;
    private final PacienteRepository pacienteRepository;
    private final DoencasRepository doencasRepository;
    private final TenantRepository tenantRepository;
    private final TenantService tenantService;

    @Override
    @Transactional
    @CacheEvict(value = "doencaspaciente", allEntries = true)
    public DoencasPacienteResponse criar(DoencasPacienteRequest request) {
        log.debug("Criando novo registro de doença do paciente");

        DoencasPaciente doencasPaciente = doencasPacienteMapper.fromRequest(request);

        if (request.getPaciente() != null) {
            Paciente paciente = pacienteRepository.findById(request.getPaciente())
                    .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPaciente()));
            doencasPaciente.setPaciente(paciente);
        }

        if (request.getDoenca() != null) {
            Doencas doenca = doencasRepository.findById(request.getDoenca())
                    .orElseThrow(() -> new NotFoundException("Doença não encontrada com ID: " + request.getDoenca()));
            doencasPaciente.setDoenca(doenca);
        }

        // CidPrincipal removido - CidDoencas foi deletado

        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado. É necessário estar autenticado para criar relacionamentos de doenças.");
        }
        doencasPaciente.setTenant(tenant);

        doencasPaciente.setActive(true);

        DoencasPaciente doencasPacienteSalvo = doencasPacienteRepository.save(doencasPaciente);
        log.info("Registro de doença do paciente criado com sucesso. ID: {}", doencasPacienteSalvo.getId());

        return doencasPacienteMapper.toResponse(doencasPacienteSalvo);
    }

    @Override
    @Transactional
    @CacheEvict(value = "doencaspaciente", allEntries = true)
    public DoencasPacienteResponse criarSimplificado(DoencasPacienteSimplificadoRequest request) {
        log.debug("Criando novo registro de doença do paciente simplificado - Paciente: {}, Tenant: {}, Doença: {}",
                request.getPaciente(), request.getTenant(), request.getDoenca());

        if (request == null) {
            throw new BadRequestException("Dados do registro são obrigatórios");
        }

        if (request.getPaciente() == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        if (request.getTenant() == null) {
            throw new BadRequestException("ID do tenant é obrigatório");
        }

        if (request.getDoenca() == null) {
            throw new BadRequestException("ID da doença é obrigatório");
        }

        Paciente paciente = pacienteRepository.findById(request.getPaciente())
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPaciente()));

        Tenant tenant = tenantRepository.findById(request.getTenant())
                .orElseThrow(() -> new NotFoundException("Tenant não encontrado com ID: " + request.getTenant()));

        Doencas doenca = doencasRepository.findById(request.getDoenca())
                .orElseThrow(() -> new NotFoundException("Doença não encontrada com ID: " + request.getDoenca()));

        DoencasPaciente doencasPaciente = new DoencasPaciente();
        doencasPaciente.setPaciente(paciente);
        doencasPaciente.setTenant(tenant);
        doencasPaciente.setDoenca(doenca);
        doencasPaciente.setActive(true);

        DoencasPaciente doencasPacienteSalvo = doencasPacienteRepository.save(doencasPaciente);
        log.info("Registro de doença do paciente criado com sucesso (simplificado). ID: {}", doencasPacienteSalvo.getId());

        return doencasPacienteMapper.toResponse(doencasPacienteSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "doencaspaciente", key = "#id")
    public DoencasPacienteResponse obterPorId(UUID id) {
        log.debug("Buscando registro de doença do paciente por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do registro é obrigatório");
        }

        DoencasPaciente doencasPaciente = doencasPacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Registro de doença do paciente não encontrado com ID: " + id));

        return doencasPacienteMapper.toResponse(doencasPaciente);
    }

    @Override
    @Transactional
    public Page<DoencasPacienteResponse> listar(Pageable pageable) {
        log.debug("Listando registros de doenças do paciente paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<DoencasPaciente> registros = doencasPacienteRepository.findAll(pageable);
        return registros.map(doencasPacienteMapper::toResponse);
    }

    @Override
    @Transactional
    public Page<DoencasPacienteResponse> listarPorPaciente(UUID pacienteId, Pageable pageable) {
        log.debug("Listando doenças do paciente: {}. Página: {}, Tamanho: {}",
                pacienteId, pageable.getPageNumber(), pageable.getPageSize());

        if (pacienteId == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        Page<DoencasPaciente> registros = doencasPacienteRepository.findByPacienteId(pacienteId, pageable);
        return registros.map(doencasPacienteMapper::toResponse);
    }

    @Override
    @Transactional
    public Page<DoencasPacienteResponse> listarPorDoenca(UUID doencaId, Pageable pageable) {
        log.debug("Listando pacientes com a doença: {}. Página: {}, Tamanho: {}",
                doencaId, pageable.getPageNumber(), pageable.getPageSize());

        if (doencaId == null) {
            throw new BadRequestException("ID da doença é obrigatório");
        }

        Page<DoencasPaciente> registros = doencasPacienteRepository.findByDoencaId(doencaId, pageable);
        return registros.map(doencasPacienteMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "doencaspaciente", key = "#id")
    public DoencasPacienteResponse atualizar(UUID id, DoencasPacienteRequest request) {
        log.debug("Atualizando registro de doença do paciente. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do registro é obrigatório");
        }

        DoencasPaciente doencasPacienteExistente = doencasPacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Registro de doença do paciente não encontrado com ID: " + id));

        atualizarDadosDoencasPaciente(doencasPacienteExistente, request);

        DoencasPaciente doencasPacienteAtualizado = doencasPacienteRepository.save(doencasPacienteExistente);
        log.info("Registro de doença do paciente atualizado com sucesso. ID: {}", doencasPacienteAtualizado.getId());

        return doencasPacienteMapper.toResponse(doencasPacienteAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "doencaspaciente", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo registro de doença do paciente. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do registro é obrigatório");
        }

        DoencasPaciente doencasPaciente = doencasPacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Registro de doença do paciente não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(doencasPaciente.getActive())) {
            throw new BadRequestException("Registro já está inativo");
        }

        doencasPaciente.setActive(false);
        doencasPacienteRepository.save(doencasPaciente);
        log.info("Registro de doença do paciente excluído (desativado) com sucesso. ID: {}", id);
    }

    private void atualizarDadosDoencasPaciente(DoencasPaciente doencasPaciente, DoencasPacienteRequest request) {

        if (request.getDiagnostico() != null) {
            if (doencasPaciente.getDiagnostico() == null) {
                doencasPaciente.setDiagnostico(diagnosticoDoencaPacienteMapper.toEntity(request.getDiagnostico()));
            } else {
                diagnosticoDoencaPacienteMapper.updateFromRequest(request.getDiagnostico(), doencasPaciente.getDiagnostico());
            }
        }
        if (request.getAcompanhamento() != null) {
            if (doencasPaciente.getAcompanhamento() == null) {
                doencasPaciente.setAcompanhamento(acompanhamentoDoencaPacienteMapper.toEntity(request.getAcompanhamento()));
            } else {
                acompanhamentoDoencaPacienteMapper.updateFromRequest(request.getAcompanhamento(), doencasPaciente.getAcompanhamento());
            }
        }
        if (request.getTratamentoAtual() != null) {
            if (doencasPaciente.getTratamentoAtual() == null) {
                doencasPaciente.setTratamentoAtual(tratamentoAtualDoencaPacienteMapper.toEntity(request.getTratamentoAtual()));
            } else {
                tratamentoAtualDoencaPacienteMapper.updateFromRequest(request.getTratamentoAtual(), doencasPaciente.getTratamentoAtual());
            }
        }
        if (request.getObservacoes() != null) {
            doencasPaciente.setObservacoes(request.getObservacoes());
        }

        // CidPrincipal removido - CidDoencas foi deletado
    }

}
