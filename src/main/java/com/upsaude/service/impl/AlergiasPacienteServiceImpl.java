package com.upsaude.service.impl;

import com.upsaude.api.request.AlergiasPacienteRequest;
import com.upsaude.api.response.AlergiasPacienteResponse;
import com.upsaude.entity.AlergiasPaciente;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.AlergiasPacienteMapper;
import com.upsaude.repository.AlergiasPacienteRepository;
import com.upsaude.service.AlergiasPacienteService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de AlergiasPaciente.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AlergiasPacienteServiceImpl implements AlergiasPacienteService {

    private final AlergiasPacienteRepository alergiasPacienteRepository;
    private final AlergiasPacienteMapper alergiasPacienteMapper;

    @Override
    @Transactional
    public AlergiasPacienteResponse criar(AlergiasPacienteRequest request) {
        log.debug("Criando novo alergiaspaciente");

        validarDadosBasicos(request);

        AlergiasPaciente alergiasPaciente = alergiasPacienteMapper.fromRequest(request);
        alergiasPaciente.setActive(true);

        AlergiasPaciente alergiasPacienteSalvo = alergiasPacienteRepository.save(alergiasPaciente);
        log.info("AlergiasPaciente criado com sucesso. ID: {}", alergiasPacienteSalvo.getId());

        return alergiasPacienteMapper.toResponse(alergiasPacienteSalvo);
    }

    @Override
    @Transactional
    public AlergiasPacienteResponse obterPorId(UUID id) {
        log.debug("Buscando alergiaspaciente por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do alergiaspaciente é obrigatório");
        }

        AlergiasPaciente alergiasPaciente = alergiasPacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("AlergiasPaciente não encontrado com ID: " + id));

        return alergiasPacienteMapper.toResponse(alergiasPaciente);
    }

    @Override
    public Page<AlergiasPacienteResponse> listar(Pageable pageable) {
        log.debug("Listando AlergiasPacientes paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<AlergiasPaciente> alergiasPacientes = alergiasPacienteRepository.findAll(pageable);
        return alergiasPacientes.map(alergiasPacienteMapper::toResponse);
    }

    @Override
    @Transactional
    public AlergiasPacienteResponse atualizar(UUID id, AlergiasPacienteRequest request) {
        log.debug("Atualizando alergiaspaciente. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do alergiaspaciente é obrigatório");
        }

        validarDadosBasicos(request);

        AlergiasPaciente alergiasPacienteExistente = alergiasPacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("AlergiasPaciente não encontrado com ID: " + id));

        atualizarDadosAlergiasPaciente(alergiasPacienteExistente, request);

        AlergiasPaciente alergiasPacienteAtualizado = alergiasPacienteRepository.save(alergiasPacienteExistente);
        log.info("AlergiasPaciente atualizado com sucesso. ID: {}", alergiasPacienteAtualizado.getId());

        return alergiasPacienteMapper.toResponse(alergiasPacienteAtualizado);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo alergiaspaciente. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do alergiaspaciente é obrigatório");
        }

        AlergiasPaciente alergiasPaciente = alergiasPacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("AlergiasPaciente não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(alergiasPaciente.getActive())) {
            throw new BadRequestException("AlergiasPaciente já está inativo");
        }

        alergiasPaciente.setActive(false);
        alergiasPacienteRepository.save(alergiasPaciente);
        log.info("AlergiasPaciente excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(AlergiasPacienteRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do alergiaspaciente são obrigatórios");
        }
    }

        private void atualizarDadosAlergiasPaciente(AlergiasPaciente alergiasPaciente, AlergiasPacienteRequest request) {
        AlergiasPaciente alergiasPacienteAtualizado = alergiasPacienteMapper.fromRequest(request);
        
        // Preserva campos de controle
        java.util.UUID idOriginal = alergiasPaciente.getId();
        com.upsaude.entity.Tenant tenantOriginal = alergiasPaciente.getTenant();
        Boolean activeOriginal = alergiasPaciente.getActive();
        java.time.OffsetDateTime createdAtOriginal = alergiasPaciente.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(alergiasPacienteAtualizado, alergiasPaciente);
        
        // Restaura campos de controle
        alergiasPaciente.setId(idOriginal);
        alergiasPaciente.setTenant(tenantOriginal);
        alergiasPaciente.setActive(activeOriginal);
        alergiasPaciente.setCreatedAt(createdAtOriginal);
    }
}
