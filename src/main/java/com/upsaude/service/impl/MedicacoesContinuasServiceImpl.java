package com.upsaude.service.impl;

import com.upsaude.api.request.MedicacoesContinuasRequest;
import com.upsaude.api.response.MedicacoesContinuasResponse;
import com.upsaude.entity.MedicacoesContinuas;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.MedicacoesContinuasMapper;
import com.upsaude.repository.MedicacoesContinuasRepository;
import com.upsaude.service.MedicacoesContinuasService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de MedicacoesContinuas.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MedicacoesContinuasServiceImpl implements MedicacoesContinuasService {

    private final MedicacoesContinuasRepository medicacoesContinuasRepository;
    private final MedicacoesContinuasMapper medicacoesContinuasMapper;

    @Override
    @Transactional
    public MedicacoesContinuasResponse criar(MedicacoesContinuasRequest request) {
        log.debug("Criando novo medicacoescontinuas");

        validarDadosBasicos(request);

        MedicacoesContinuas medicacoesContinuas = medicacoesContinuasMapper.fromRequest(request);
        medicacoesContinuas.setActive(true);

        MedicacoesContinuas medicacoesContinuasSalvo = medicacoesContinuasRepository.save(medicacoesContinuas);
        log.info("MedicacoesContinuas criado com sucesso. ID: {}", medicacoesContinuasSalvo.getId());

        return medicacoesContinuasMapper.toResponse(medicacoesContinuasSalvo);
    }

    @Override
    @Transactional
    public MedicacoesContinuasResponse obterPorId(UUID id) {
        log.debug("Buscando medicacoescontinuas por ID: {}", id);

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
    public MedicacoesContinuasResponse atualizar(UUID id, MedicacoesContinuasRequest request) {
        log.debug("Atualizando medicacoescontinuas. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do medicacoescontinuas é obrigatório");
        }

        validarDadosBasicos(request);

        MedicacoesContinuas medicacoesContinuasExistente = medicacoesContinuasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("MedicacoesContinuas não encontrado com ID: " + id));

        atualizarDadosMedicacoesContinuas(medicacoesContinuasExistente, request);

        MedicacoesContinuas medicacoesContinuasAtualizado = medicacoesContinuasRepository.save(medicacoesContinuasExistente);
        log.info("MedicacoesContinuas atualizado com sucesso. ID: {}", medicacoesContinuasAtualizado.getId());

        return medicacoesContinuasMapper.toResponse(medicacoesContinuasAtualizado);
    }

    @Override
    @Transactional
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

    private void validarDadosBasicos(MedicacoesContinuasRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do medicacoescontinuas são obrigatórios");
        }
    }

        private void atualizarDadosMedicacoesContinuas(MedicacoesContinuas medicacoesContinuas, MedicacoesContinuasRequest request) {
        MedicacoesContinuas medicacoesContinuasAtualizado = medicacoesContinuasMapper.fromRequest(request);
        
        // Preserva campos de controle
        java.util.UUID idOriginal = medicacoesContinuas.getId();
        com.upsaude.entity.Tenant tenantOriginal = medicacoesContinuas.getTenant();
        Boolean activeOriginal = medicacoesContinuas.getActive();
        java.time.OffsetDateTime createdAtOriginal = medicacoesContinuas.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(medicacoesContinuasAtualizado, medicacoesContinuas);
        
        // Restaura campos de controle
        medicacoesContinuas.setId(idOriginal);
        medicacoesContinuas.setTenant(tenantOriginal);
        medicacoesContinuas.setActive(activeOriginal);
        medicacoesContinuas.setCreatedAt(createdAtOriginal);
    }
}
