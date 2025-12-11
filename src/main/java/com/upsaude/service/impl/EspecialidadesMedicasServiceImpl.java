package com.upsaude.service.impl;

import com.upsaude.api.request.EspecialidadesMedicasRequest;
import com.upsaude.api.response.EspecialidadesMedicasResponse;
import com.upsaude.entity.EspecialidadesMedicas;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.EspecialidadesMedicasMapper;
import com.upsaude.repository.EspecialidadesMedicasRepository;
import com.upsaude.service.EspecialidadesMedicasService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EspecialidadesMedicasServiceImpl implements EspecialidadesMedicasService {

    private final EspecialidadesMedicasRepository especialidadesMedicasRepository;
    private final EspecialidadesMedicasMapper especialidadesMedicasMapper;

    @Override
    @Transactional
    @CacheEvict(value = "especialidadesmedicas", allEntries = true)
    public EspecialidadesMedicasResponse criar(EspecialidadesMedicasRequest request) {
        log.debug("Criando nova especialidade médica. Request: {}", request);

        if (request == null) {
            log.warn("Tentativa de criar especialidade médica com request nulo");
            throw new BadRequestException("Dados da especialidade médica são obrigatórios");
        }

        try {

            EspecialidadesMedicas especialidadesMedicas = especialidadesMedicasMapper.fromRequest(request);
            especialidadesMedicas.setActive(true);

            EspecialidadesMedicas especialidadesMedicasSalvo = especialidadesMedicasRepository.save(especialidadesMedicas);
            log.info("Especialidade médica criada com sucesso. ID: {}", especialidadesMedicasSalvo.getId());

            return especialidadesMedicasMapper.toResponse(especialidadesMedicasSalvo);
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao criar especialidade médica. Request: {}. Erro: {}", request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar especialidade médica. Request: {}", request, e);
            throw new InternalServerErrorException("Erro ao persistir especialidade médica", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar especialidade médica. Request: {}", request, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "especialidadesmedicas", key = "#id")
    public EspecialidadesMedicasResponse obterPorId(UUID id) {
        log.debug("Buscando especialidade médica por ID: {} (cache miss)", id);

        if (id == null) {
            log.warn("ID nulo recebido para busca de especialidade médica");
            throw new BadRequestException("ID da especialidade médica é obrigatório");
        }

        try {
            EspecialidadesMedicas especialidadesMedicas = especialidadesMedicasRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Especialidade médica não encontrada com ID: " + id));

            log.debug("Especialidade médica encontrada. ID: {}", id);
            return especialidadesMedicasMapper.toResponse(especialidadesMedicas);
        } catch (NotFoundException e) {
            log.warn("Especialidade médica não encontrada. ID: {}", id);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar especialidade médica. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao buscar especialidade médica", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao buscar especialidade médica. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EspecialidadesMedicasResponse> listar(Pageable pageable) {
        log.debug("Listando especialidades médicas paginadas. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            Page<EspecialidadesMedicas> especialidadesMedicas = especialidadesMedicasRepository.findAll(pageable);
            log.debug("Listagem de especialidades médicas concluída. Total de elementos: {}", especialidadesMedicas.getTotalElements());
            return especialidadesMedicas.map(especialidadesMedicasMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar especialidades médicas. Pageable: {}", pageable, e);
            throw new InternalServerErrorException("Erro ao listar especialidades médicas", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar especialidades médicas. Pageable: {}", pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "especialidadesmedicas", key = "#id")
    public EspecialidadesMedicasResponse atualizar(UUID id, EspecialidadesMedicasRequest request) {
        log.debug("Atualizando especialidade médica. ID: {}, Request: {}", id, request);

        if (id == null) {
            log.warn("ID nulo recebido para atualização de especialidade médica");
            throw new BadRequestException("ID da especialidade médica é obrigatório");
        }
        if (request == null) {
            log.warn("Request nulo recebido para atualização de especialidade médica. ID: {}", id);
            throw new BadRequestException("Dados da especialidade médica são obrigatórios");
        }

        try {

            EspecialidadesMedicas especialidadesMedicasExistente = especialidadesMedicasRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Especialidade médica não encontrada com ID: " + id));

            especialidadesMedicasMapper.updateFromRequest(request, especialidadesMedicasExistente);

            EspecialidadesMedicas especialidadesMedicasAtualizado = especialidadesMedicasRepository.save(especialidadesMedicasExistente);
            log.info("Especialidade médica atualizada com sucesso. ID: {}", especialidadesMedicasAtualizado.getId());

            return especialidadesMedicasMapper.toResponse(especialidadesMedicasAtualizado);
        } catch (NotFoundException e) {
            log.warn("Tentativa de atualizar especialidade médica não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao atualizar especialidade médica. ID: {}, Request: {}. Erro: {}", id, request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar especialidade médica. ID: {}, Request: {}", id, request, e);
            throw new InternalServerErrorException("Erro ao atualizar especialidade médica", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao atualizar especialidade médica. ID: {}, Request: {}", id, request, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "especialidadesmedicas", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo especialidade médica. ID: {}", id);

        if (id == null) {
            log.warn("ID nulo recebido para exclusão de especialidade médica");
            throw new BadRequestException("ID da especialidade médica é obrigatório");
        }

        try {
            EspecialidadesMedicas especialidadesMedicas = especialidadesMedicasRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Especialidade médica não encontrada com ID: " + id));

            if (Boolean.FALSE.equals(especialidadesMedicas.getActive())) {
                log.warn("Tentativa de excluir especialidade médica já inativa. ID: {}", id);
                throw new BadRequestException("Especialidade médica já está inativa");
            }

            especialidadesMedicas.setActive(false);
            especialidadesMedicasRepository.save(especialidadesMedicas);
            log.info("Especialidade médica excluída (desativada) com sucesso. ID: {}", id);
        } catch (NotFoundException e) {
            log.warn("Tentativa de excluir especialidade médica não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao excluir especialidade médica. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir especialidade médica. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir especialidade médica", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao excluir especialidade médica. ID: {}", id, e);
            throw e;
        }
    }

}
