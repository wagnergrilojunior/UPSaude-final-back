package com.upsaude.service.impl;

import com.upsaude.api.request.EspecialidadesMedicasRequest;
import com.upsaude.api.response.EspecialidadesMedicasResponse;
import com.upsaude.entity.EspecialidadesMedicas;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.EspecialidadesMedicasMapper;
import com.upsaude.repository.EspecialidadesMedicasRepository;
import com.upsaude.service.EspecialidadesMedicasService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de EspecialidadesMedicas.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EspecialidadesMedicasServiceImpl implements EspecialidadesMedicasService {

    private final EspecialidadesMedicasRepository especialidadesMedicasRepository;
    private final EspecialidadesMedicasMapper especialidadesMedicasMapper;

    @Override
    @Transactional
    public EspecialidadesMedicasResponse criar(EspecialidadesMedicasRequest request) {
        log.debug("Criando novo especialidadesmedicas");

        validarDadosBasicos(request);

        EspecialidadesMedicas especialidadesMedicas = especialidadesMedicasMapper.fromRequest(request);
        especialidadesMedicas.setActive(true);

        EspecialidadesMedicas especialidadesMedicasSalvo = especialidadesMedicasRepository.save(especialidadesMedicas);
        log.info("EspecialidadesMedicas criado com sucesso. ID: {}", especialidadesMedicasSalvo.getId());

        return especialidadesMedicasMapper.toResponse(especialidadesMedicasSalvo);
    }

    @Override
    @Transactional
    public EspecialidadesMedicasResponse obterPorId(UUID id) {
        log.debug("Buscando especialidadesmedicas por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do especialidadesmedicas é obrigatório");
        }

        EspecialidadesMedicas especialidadesMedicas = especialidadesMedicasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("EspecialidadesMedicas não encontrado com ID: " + id));

        return especialidadesMedicasMapper.toResponse(especialidadesMedicas);
    }

    @Override
    public Page<EspecialidadesMedicasResponse> listar(Pageable pageable) {
        log.debug("Listando EspecialidadesMedicas paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<EspecialidadesMedicas> especialidadesMedicas = especialidadesMedicasRepository.findAll(pageable);
        return especialidadesMedicas.map(especialidadesMedicasMapper::toResponse);
    }

    @Override
    @Transactional
    public EspecialidadesMedicasResponse atualizar(UUID id, EspecialidadesMedicasRequest request) {
        log.debug("Atualizando especialidadesmedicas. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do especialidadesmedicas é obrigatório");
        }

        validarDadosBasicos(request);

        EspecialidadesMedicas especialidadesMedicasExistente = especialidadesMedicasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("EspecialidadesMedicas não encontrado com ID: " + id));

        atualizarDadosEspecialidadesMedicas(especialidadesMedicasExistente, request);

        EspecialidadesMedicas especialidadesMedicasAtualizado = especialidadesMedicasRepository.save(especialidadesMedicasExistente);
        log.info("EspecialidadesMedicas atualizado com sucesso. ID: {}", especialidadesMedicasAtualizado.getId());

        return especialidadesMedicasMapper.toResponse(especialidadesMedicasAtualizado);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo especialidadesmedicas. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do especialidadesmedicas é obrigatório");
        }

        EspecialidadesMedicas especialidadesMedicas = especialidadesMedicasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("EspecialidadesMedicas não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(especialidadesMedicas.getActive())) {
            throw new BadRequestException("EspecialidadesMedicas já está inativo");
        }

        especialidadesMedicas.setActive(false);
        especialidadesMedicasRepository.save(especialidadesMedicas);
        log.info("EspecialidadesMedicas excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(EspecialidadesMedicasRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do especialidadesmedicas são obrigatórios");
        }
    }

        private void atualizarDadosEspecialidadesMedicas(EspecialidadesMedicas especialidadesMedicas, EspecialidadesMedicasRequest request) {
        EspecialidadesMedicas especialidadesMedicasAtualizado = especialidadesMedicasMapper.fromRequest(request);
        
        // Preserva campos de controle
        java.util.UUID idOriginal = especialidadesMedicas.getId();
        com.upsaude.entity.Tenant tenantOriginal = especialidadesMedicas.getTenant();
        Boolean activeOriginal = especialidadesMedicas.getActive();
        java.time.OffsetDateTime createdAtOriginal = especialidadesMedicas.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(especialidadesMedicasAtualizado, especialidadesMedicas);
        
        // Restaura campos de controle
        especialidadesMedicas.setId(idOriginal);
        especialidadesMedicas.setTenant(tenantOriginal);
        especialidadesMedicas.setActive(activeOriginal);
        especialidadesMedicas.setCreatedAt(createdAtOriginal);
    }
}
