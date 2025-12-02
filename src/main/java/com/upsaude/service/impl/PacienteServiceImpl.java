package com.upsaude.service.impl;

import com.upsaude.api.request.PacienteRequest;
import com.upsaude.api.response.PacienteResponse;
import com.upsaude.entity.Paciente;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.PacienteMapper;
import com.upsaude.repository.PacienteRepository;
import com.upsaude.service.PacienteService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Pacientes.
 * Responsável por aplicar regras de negócio e coordenar operações CRUD.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;
    private final PacienteMapper pacienteMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @CacheEvict(value = "paciente", allEntries = true)
    public PacienteResponse criar(PacienteRequest request) {
        log.debug("Criando novo paciente: {}", request.getNomeCompleto());

        validarDadosBasicos(request);
        validarCPFUnico(null, request.getCpf());

        Paciente paciente = pacienteMapper.fromRequest(request);
        paciente.setActive(true);

        Paciente pacienteSalvo = pacienteRepository.save(paciente);
        log.info("Paciente criado com sucesso. ID: {}", pacienteSalvo.getId());

        return pacienteMapper.toResponse(pacienteSalvo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Cacheable(value = "paciente", key = "#id")
    public PacienteResponse obterPorId(UUID id) {
        log.debug("Buscando paciente por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + id));

        return pacienteMapper.toResponse(paciente);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<PacienteResponse> listar(Pageable pageable) {
        log.debug("Listando pacientes paginados. Página: {}, Tamanho: {}", 
                pageable.getPageNumber(), pageable.getPageSize());

        Page<Paciente> pacientes = pacienteRepository.findAll(pageable);
        return pacientes.map(pacienteMapper::toResponse);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @CacheEvict(value = "paciente", key = "#id")
    public PacienteResponse atualizar(UUID id, PacienteRequest request) {
        log.debug("Atualizando paciente. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        validarDadosBasicos(request);

        Paciente pacienteExistente = pacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + id));

        validarCPFUnico(id, request.getCpf());

        atualizarDadosPaciente(pacienteExistente, request);

        Paciente pacienteAtualizado = pacienteRepository.save(pacienteExistente);
        log.info("Paciente atualizado com sucesso. ID: {}", pacienteAtualizado.getId());

        return pacienteMapper.toResponse(pacienteAtualizado);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @CacheEvict(value = "paciente", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo paciente. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(paciente.getActive())) {
            throw new BadRequestException("Paciente já está inativo");
        }

        paciente.setActive(false);
        pacienteRepository.save(paciente);
        log.info("Paciente excluído (desativado) com sucesso. ID: {}", id);
    }

    /**
     * Valida os dados básicos do paciente.
     *
     * @param request dados do paciente a serem validados
     * @throws BadRequestException se os dados forem inválidos
     */
    private void validarDadosBasicos(PacienteRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do paciente são obrigatórios");
        }

        if (!StringUtils.hasText(request.getNomeCompleto())) {
            throw new BadRequestException("Nome completo é obrigatório");
        }

        if (request.getNomeCompleto().length() > 255) {
            throw new BadRequestException("Nome completo deve ter no máximo 255 caracteres");
        }

        if (StringUtils.hasText(request.getCpf()) && !request.getCpf().matches("^\\d{11}$")) {
            throw new BadRequestException("CPF deve conter exatamente 11 dígitos numéricos");
        }

        if (StringUtils.hasText(request.getCns()) && !request.getCns().matches("^\\d{15}$")) {
            throw new BadRequestException("CNS deve conter exatamente 15 dígitos numéricos");
        }
    }

    /**
     * Valida se o CPF é único no sistema.
     *
     * @param pacienteId ID do paciente atual (null se for criação)
     * @param cpf CPF a ser validado
     * @throws ConflictException se já existir outro paciente com o mesmo CPF
     */
    private void validarCPFUnico(UUID pacienteId, String cpf) {
        if (!StringUtils.hasText(cpf)) {
            return;
        }

        Optional<Paciente> pacienteExistente = pacienteRepository.findByCpf(cpf);

        if (pacienteExistente.isPresent()) {
            Paciente pacienteEncontrado = pacienteExistente.get();

            // Se for atualização, verifica se o CPF pertence a outro paciente
            if (pacienteId != null && !pacienteEncontrado.getId().equals(pacienteId)) {
                throw new ConflictException("Já existe um paciente cadastrado com o CPF: " + cpf);
            }

            // Se for criação, sempre lança exceção se encontrar CPF
            if (pacienteId == null) {
                throw new ConflictException("Já existe um paciente cadastrado com o CPF: " + cpf);
            }
        }
    }

    /**
     * Atualiza os dados do paciente com base no request.
     *
     * @param paciente paciente existente a ser atualizado
     * @param request dados atualizados
     */
    private void atualizarDadosPaciente(Paciente paciente, PacienteRequest request) {
        Paciente pacienteAtualizado = pacienteMapper.fromRequest(request);

        paciente.setNomeCompleto(pacienteAtualizado.getNomeCompleto());
        paciente.setCpf(pacienteAtualizado.getCpf());
        paciente.setRg(pacienteAtualizado.getRg());
        paciente.setCns(pacienteAtualizado.getCns());
        paciente.setDataNascimento(pacienteAtualizado.getDataNascimento());
        paciente.setSexo(pacienteAtualizado.getSexo());
        paciente.setEstadoCivil(pacienteAtualizado.getEstadoCivil());
        paciente.setTelefone(pacienteAtualizado.getTelefone());
        paciente.setEmail(pacienteAtualizado.getEmail());
        paciente.setNomeMae(pacienteAtualizado.getNomeMae());
        paciente.setNomePai(pacienteAtualizado.getNomePai());
        paciente.setResponsavelNome(pacienteAtualizado.getResponsavelNome());
        paciente.setResponsavelCpf(pacienteAtualizado.getResponsavelCpf());
        paciente.setResponsavelTelefone(pacienteAtualizado.getResponsavelTelefone());
        paciente.setConvenio(pacienteAtualizado.getConvenio());
        paciente.setNumeroCarteirinha(pacienteAtualizado.getNumeroCarteirinha());
        paciente.setDataValidadeCarteirinha(pacienteAtualizado.getDataValidadeCarteirinha());
        paciente.setObservacoes(pacienteAtualizado.getObservacoes());
        paciente.setEstabelecimento(pacienteAtualizado.getEstabelecimento());
        paciente.setEnderecos(pacienteAtualizado.getEnderecos());
    }
}

