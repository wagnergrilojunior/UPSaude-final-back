package com.upsaude.service.impl;

import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.upsaude.api.request.clinica.medicacao.MedicacoesContinuasRequest;
import com.upsaude.api.response.clinica.medicacao.MedicacoesContinuasResponse;
import com.upsaude.entity.clinica.medicacao.MedicacoesContinuas;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.clinica.medicacao.MedicacoesContinuasMapper;
import com.upsaude.repository.clinica.medicacao.MedicacoesContinuasRepository;
import com.upsaude.service.clinica.medicacao.MedicacoesContinuasService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicacoesContinuasServiceImpl implements MedicacoesContinuasService {

    private final MedicacoesContinuasRepository medicacoesContinuasRepository;
    private final MedicacoesContinuasMapper medicacoesContinuasMapper;

    @Override
    @Transactional
    @CacheEvict(value = "medicacoescontinuas", allEntries = true)
    public MedicacoesContinuasResponse criar(MedicacoesContinuasRequest request) {
        log.debug("Criando novo medicacoescontinuas");

        MedicacoesContinuas medicacoesContinuas = medicacoesContinuasMapper.fromRequest(request);
        medicacoesContinuas.setActive(true);

        MedicacoesContinuas medicacoesContinuasSalvo = medicacoesContinuasRepository.save(medicacoesContinuas);
        log.info("MedicacoesContinuas criado com sucesso. ID: {}", medicacoesContinuasSalvo.getId());

        return medicacoesContinuasMapper.toResponse(medicacoesContinuasSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "medicacoescontinuas", key = "#id")
    public MedicacoesContinuasResponse obterPorId(UUID id) {
        log.debug("Buscando medicacoescontinuas por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do medicacoescontinuas é obrigatório");
        }

        MedicacoesContinuas medicacoesContinuas = medicacoesContinuasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("MedicacoesContinuas não encontrado com ID: " + id));

        return medicacoesContinuasMapper.toResponse(medicacoesContinuas);
    }

    @Override
    public Page<MedicacoesContinuasResponse> listar(Pageable pageable) {
        log.debug("Listando MedicacoesContinuas paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<MedicacoesContinuas> medicacoesContinuas = medicacoesContinuasRepository.findAll(pageable);
        return medicacoesContinuas.map(medicacoesContinuasMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "medicacoescontinuas", key = "#id")
    public MedicacoesContinuasResponse atualizar(UUID id, MedicacoesContinuasRequest request) {
        log.debug("Atualizando medicacoescontinuas. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do medicacoescontinuas é obrigatório");
        }

        MedicacoesContinuas medicacoesContinuasExistente = medicacoesContinuasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("MedicacoesContinuas não encontrado com ID: " + id));

        atualizarDadosMedicacoesContinuas(medicacoesContinuasExistente, request);

        MedicacoesContinuas medicacoesContinuasAtualizado = medicacoesContinuasRepository.save(medicacoesContinuasExistente);
        log.info("MedicacoesContinuas atualizado com sucesso. ID: {}", medicacoesContinuasAtualizado.getId());

        return medicacoesContinuasMapper.toResponse(medicacoesContinuasAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "medicacoescontinuas", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo medicacoescontinuas. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do medicacoescontinuas é obrigatório");
        }

        MedicacoesContinuas medicacoesContinuas = medicacoesContinuasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("MedicacoesContinuas não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(medicacoesContinuas.getActive())) {
            throw new BadRequestException("MedicacoesContinuas já está inativo");
        }

        medicacoesContinuas.setActive(false);
        medicacoesContinuasRepository.save(medicacoesContinuas);
        log.info("MedicacoesContinuas excluído (desativado) com sucesso. ID: {}", id);
    }

    private void atualizarDadosMedicacoesContinuas(MedicacoesContinuas medicacoesContinuas, MedicacoesContinuasRequest request) {
        MedicacoesContinuas medicacoesContinuasAtualizado = medicacoesContinuasMapper.fromRequest(request);

        java.util.UUID idOriginal = medicacoesContinuas.getId();
        Boolean activeOriginal = medicacoesContinuas.getActive();
        java.time.OffsetDateTime createdAtOriginal = medicacoesContinuas.getCreatedAt();

        BeanUtils.copyProperties(medicacoesContinuasAtualizado, medicacoesContinuas);

        medicacoesContinuas.setId(idOriginal);
        medicacoesContinuas.setActive(activeOriginal);
        medicacoesContinuas.setCreatedAt(createdAtOriginal);
    }
}
