package com.upsaude.service.impl;

import com.upsaude.api.request.MedicosRequest;
import com.upsaude.api.response.MedicosResponse;
import com.upsaude.entity.Medicos;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.MedicosMapper;
import com.upsaude.repository.MedicosRepository;
import com.upsaude.service.MedicosService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Medicos.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MedicosServiceImpl implements MedicosService {

    private final MedicosRepository medicosRepository;
    private final MedicosMapper medicosMapper;

    @Override
    @Transactional
    public MedicosResponse criar(MedicosRequest request) {
        log.debug("Criando novo medicos");

        validarDadosBasicos(request);

        Medicos medicos = medicosMapper.fromRequest(request);
        medicos.setActive(true);

        Medicos medicosSalvo = medicosRepository.save(medicos);
        log.info("Medicos criado com sucesso. ID: {}", medicosSalvo.getId());

        return medicosMapper.toResponse(medicosSalvo);
    }

    @Override
    @Transactional
    public MedicosResponse obterPorId(UUID id) {
        log.debug("Buscando medicos por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do medicos é obrigatório");
        }

        Medicos medicos = medicosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Medicos não encontrado com ID: " + id));

        return medicosMapper.toResponse(medicos);
    }

    @Override
    public Page<MedicosResponse> listar(Pageable pageable) {
        log.debug("Listando Medicos paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<Medicos> medicos = medicosRepository.findAll(pageable);
        return medicos.map(medicosMapper::toResponse);
    }

    @Override
    @Transactional
    public MedicosResponse atualizar(UUID id, MedicosRequest request) {
        log.debug("Atualizando medicos. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do medicos é obrigatório");
        }

        validarDadosBasicos(request);

        Medicos medicosExistente = medicosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Medicos não encontrado com ID: " + id));

        atualizarDadosMedicos(medicosExistente, request);

        Medicos medicosAtualizado = medicosRepository.save(medicosExistente);
        log.info("Medicos atualizado com sucesso. ID: {}", medicosAtualizado.getId());

        return medicosMapper.toResponse(medicosAtualizado);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo medicos. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do medicos é obrigatório");
        }

        Medicos medicos = medicosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Medicos não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(medicos.getActive())) {
            throw new BadRequestException("Medicos já está inativo");
        }

        medicos.setActive(false);
        medicosRepository.save(medicos);
        log.info("Medicos excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(MedicosRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do medicos são obrigatórios");
        }
    }

    private void atualizarDadosMedicos(Medicos medicos, MedicosRequest request) {
        Medicos medicosAtualizado = medicosMapper.fromRequest(request);
        
        // Preserva campos de controle
        UUID idOriginal = medicos.getId();
        com.upsaude.entity.Tenant tenantOriginal = medicos.getTenant();
        Boolean activeOriginal = medicos.getActive();
        java.time.OffsetDateTime createdAtOriginal = medicos.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(medicosAtualizado, medicos);
        
        // Restaura campos de controle
        medicos.setId(idOriginal);
        medicos.setTenant(tenantOriginal);
        medicos.setActive(activeOriginal);
        medicos.setCreatedAt(createdAtOriginal);
    }
}
