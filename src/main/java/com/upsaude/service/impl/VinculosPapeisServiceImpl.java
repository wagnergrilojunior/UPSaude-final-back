package com.upsaude.service.impl;

import com.upsaude.api.request.VinculosPapeisRequest;
import com.upsaude.api.response.VinculosPapeisResponse;
import com.upsaude.entity.VinculosPapeis;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.VinculosPapeisMapper;
import com.upsaude.repository.VinculosPapeisRepository;
import com.upsaude.service.VinculosPapeisService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de VinculosPapeis.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VinculosPapeisServiceImpl implements VinculosPapeisService {

    private final VinculosPapeisRepository vinculosPapeisRepository;
    private final VinculosPapeisMapper vinculosPapeisMapper;

    @Override
    @Transactional
    public VinculosPapeisResponse criar(VinculosPapeisRequest request) {
        log.debug("Criando novo vinculospapeis");

        validarDadosBasicos(request);

        VinculosPapeis vinculosPapeis = vinculosPapeisMapper.fromRequest(request);
        vinculosPapeis.setActive(true);

        VinculosPapeis vinculosPapeisSalvo = vinculosPapeisRepository.save(vinculosPapeis);
        log.info("VinculosPapeis criado com sucesso. ID: {}", vinculosPapeisSalvo.getId());

        return vinculosPapeisMapper.toResponse(vinculosPapeisSalvo);
    }

    @Override
    @Transactional
    public VinculosPapeisResponse obterPorId(UUID id) {
        log.debug("Buscando vinculospapeis por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do vinculospapeis é obrigatório");
        }

        VinculosPapeis vinculosPapeis = vinculosPapeisRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("VinculosPapeis não encontrado com ID: " + id));

        return vinculosPapeisMapper.toResponse(vinculosPapeis);
    }

    @Override
    public Page<VinculosPapeisResponse> listar(Pageable pageable) {
        log.debug("Listando VinculosPapeis paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<VinculosPapeis> vinculosPapeis = vinculosPapeisRepository.findAll(pageable);
        return vinculosPapeis.map(vinculosPapeisMapper::toResponse);
    }

    @Override
    @Transactional
    public VinculosPapeisResponse atualizar(UUID id, VinculosPapeisRequest request) {
        log.debug("Atualizando vinculospapeis. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do vinculospapeis é obrigatório");
        }

        validarDadosBasicos(request);

        VinculosPapeis vinculosPapeisExistente = vinculosPapeisRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("VinculosPapeis não encontrado com ID: " + id));

        atualizarDadosVinculosPapeis(vinculosPapeisExistente, request);

        VinculosPapeis vinculosPapeisAtualizado = vinculosPapeisRepository.save(vinculosPapeisExistente);
        log.info("VinculosPapeis atualizado com sucesso. ID: {}", vinculosPapeisAtualizado.getId());

        return vinculosPapeisMapper.toResponse(vinculosPapeisAtualizado);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo vinculospapeis. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do vinculospapeis é obrigatório");
        }

        VinculosPapeis vinculosPapeis = vinculosPapeisRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("VinculosPapeis não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(vinculosPapeis.getActive())) {
            throw new BadRequestException("VinculosPapeis já está inativo");
        }

        vinculosPapeis.setActive(false);
        vinculosPapeisRepository.save(vinculosPapeis);
        log.info("VinculosPapeis excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(VinculosPapeisRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do vinculospapeis são obrigatórios");
        }
    }

        private void atualizarDadosVinculosPapeis(VinculosPapeis vinculosPapeis, VinculosPapeisRequest request) {
        VinculosPapeis vinculosPapeisAtualizado = vinculosPapeisMapper.fromRequest(request);
        
        // Preserva campos de controle
        java.util.UUID idOriginal = vinculosPapeis.getId();
        com.upsaude.entity.Tenant tenantOriginal = vinculosPapeis.getTenant();
        Boolean activeOriginal = vinculosPapeis.getActive();
        java.time.OffsetDateTime createdAtOriginal = vinculosPapeis.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(vinculosPapeisAtualizado, vinculosPapeis);
        
        // Restaura campos de controle
        vinculosPapeis.setId(idOriginal);
        vinculosPapeis.setTenant(tenantOriginal);
        vinculosPapeis.setActive(activeOriginal);
        vinculosPapeis.setCreatedAt(createdAtOriginal);
    }
}
