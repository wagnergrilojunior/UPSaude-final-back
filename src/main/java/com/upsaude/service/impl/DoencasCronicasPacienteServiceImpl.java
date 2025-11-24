package com.upsaude.service.impl;

import com.upsaude.api.request.DoencasCronicasPacienteRequest;
import com.upsaude.api.response.DoencasCronicasPacienteResponse;
import com.upsaude.entity.DoencasCronicasPaciente;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.DoencasCronicasPacienteMapper;
import com.upsaude.repository.DoencasCronicasPacienteRepository;
import com.upsaude.service.DoencasCronicasPacienteService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de DoencasCronicasPaciente.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DoencasCronicasPacienteServiceImpl implements DoencasCronicasPacienteService {

    private final DoencasCronicasPacienteRepository doencasCronicasPacienteRepository;
    private final DoencasCronicasPacienteMapper doencasCronicasPacienteMapper;

    @Override
    @Transactional
    public DoencasCronicasPacienteResponse criar(DoencasCronicasPacienteRequest request) {
        log.debug("Criando novo doencascronicaspaciente");

        validarDadosBasicos(request);

        DoencasCronicasPaciente doencasCronicasPaciente = doencasCronicasPacienteMapper.fromRequest(request);
        doencasCronicasPaciente.setActive(true);

        DoencasCronicasPaciente doencasCronicasPacienteSalvo = doencasCronicasPacienteRepository.save(doencasCronicasPaciente);
        log.info("DoencasCronicasPaciente criado com sucesso. ID: {}", doencasCronicasPacienteSalvo.getId());

        return doencasCronicasPacienteMapper.toResponse(doencasCronicasPacienteSalvo);
    }

    @Override
    @Transactional
    public DoencasCronicasPacienteResponse obterPorId(UUID id) {
        log.debug("Buscando doencascronicaspaciente por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do doencascronicaspaciente é obrigatório");
        }

        DoencasCronicasPaciente doencasCronicasPaciente = doencasCronicasPacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("DoencasCronicasPaciente não encontrado com ID: " + id));

        return doencasCronicasPacienteMapper.toResponse(doencasCronicasPaciente);
    }

    @Override
    public Page<DoencasCronicasPacienteResponse> listar(Pageable pageable) {
        log.debug("Listando DoencasCronicasPacientes paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<DoencasCronicasPaciente> doencasCronicasPacientes = doencasCronicasPacienteRepository.findAll(pageable);
        return doencasCronicasPacientes.map(doencasCronicasPacienteMapper::toResponse);
    }

    @Override
    @Transactional
    public DoencasCronicasPacienteResponse atualizar(UUID id, DoencasCronicasPacienteRequest request) {
        log.debug("Atualizando doencascronicaspaciente. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do doencascronicaspaciente é obrigatório");
        }

        validarDadosBasicos(request);

        DoencasCronicasPaciente doencasCronicasPacienteExistente = doencasCronicasPacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("DoencasCronicasPaciente não encontrado com ID: " + id));

        atualizarDadosDoencasCronicasPaciente(doencasCronicasPacienteExistente, request);

        DoencasCronicasPaciente doencasCronicasPacienteAtualizado = doencasCronicasPacienteRepository.save(doencasCronicasPacienteExistente);
        log.info("DoencasCronicasPaciente atualizado com sucesso. ID: {}", doencasCronicasPacienteAtualizado.getId());

        return doencasCronicasPacienteMapper.toResponse(doencasCronicasPacienteAtualizado);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo doencascronicaspaciente. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do doencascronicaspaciente é obrigatório");
        }

        DoencasCronicasPaciente doencasCronicasPaciente = doencasCronicasPacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("DoencasCronicasPaciente não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(doencasCronicasPaciente.getActive())) {
            throw new BadRequestException("DoencasCronicasPaciente já está inativo");
        }

        doencasCronicasPaciente.setActive(false);
        doencasCronicasPacienteRepository.save(doencasCronicasPaciente);
        log.info("DoencasCronicasPaciente excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(DoencasCronicasPacienteRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do doencascronicaspaciente são obrigatórios");
        }
    }

        private void atualizarDadosDoencasCronicasPaciente(DoencasCronicasPaciente doencasCronicasPaciente, DoencasCronicasPacienteRequest request) {
        DoencasCronicasPaciente doencasCronicasPacienteAtualizado = doencasCronicasPacienteMapper.fromRequest(request);
        
        // Preserva campos de controle
        java.util.UUID idOriginal = doencasCronicasPaciente.getId();
        com.upsaude.entity.Tenant tenantOriginal = doencasCronicasPaciente.getTenant();
        Boolean activeOriginal = doencasCronicasPaciente.getActive();
        java.time.OffsetDateTime createdAtOriginal = doencasCronicasPaciente.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(doencasCronicasPacienteAtualizado, doencasCronicasPaciente);
        
        // Restaura campos de controle
        doencasCronicasPaciente.setId(idOriginal);
        doencasCronicasPaciente.setTenant(tenantOriginal);
        doencasCronicasPaciente.setActive(activeOriginal);
        doencasCronicasPaciente.setCreatedAt(createdAtOriginal);
    }
}
