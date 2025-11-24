package com.upsaude.service.impl;

import com.upsaude.api.request.ConselhosProfissionaisRequest;
import com.upsaude.api.response.ConselhosProfissionaisResponse;
import com.upsaude.entity.ConselhosProfissionais;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.ConselhosProfissionaisMapper;
import com.upsaude.repository.ConselhosProfissionaisRepository;
import com.upsaude.service.ConselhosProfissionaisService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de ConselhosProfissionais.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ConselhosProfissionaisServiceImpl implements ConselhosProfissionaisService {

    private final ConselhosProfissionaisRepository conselhosProfissionaisRepository;
    private final ConselhosProfissionaisMapper conselhosProfissionaisMapper;

    @Override
    @Transactional
    public ConselhosProfissionaisResponse criar(ConselhosProfissionaisRequest request) {
        log.debug("Criando novo conselhosprofissionais");

        validarDadosBasicos(request);

        ConselhosProfissionais conselhosProfissionais = conselhosProfissionaisMapper.fromRequest(request);
        conselhosProfissionais.setActive(true);

        ConselhosProfissionais conselhosProfissionaisSalvo = conselhosProfissionaisRepository.save(conselhosProfissionais);
        log.info("ConselhosProfissionais criado com sucesso. ID: {}", conselhosProfissionaisSalvo.getId());

        return conselhosProfissionaisMapper.toResponse(conselhosProfissionaisSalvo);
    }

    @Override
    @Transactional
    public ConselhosProfissionaisResponse obterPorId(UUID id) {
        log.debug("Buscando conselhosprofissionais por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do conselhosprofissionais é obrigatório");
        }

        ConselhosProfissionais conselhosProfissionais = conselhosProfissionaisRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ConselhosProfissionais não encontrado com ID: " + id));

        return conselhosProfissionaisMapper.toResponse(conselhosProfissionais);
    }

    @Override
    public Page<ConselhosProfissionaisResponse> listar(Pageable pageable) {
        log.debug("Listando ConselhosProfissionais paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<ConselhosProfissionais> conselhosProfissionais = conselhosProfissionaisRepository.findAll(pageable);
        return conselhosProfissionais.map(conselhosProfissionaisMapper::toResponse);
    }

    @Override
    @Transactional
    public ConselhosProfissionaisResponse atualizar(UUID id, ConselhosProfissionaisRequest request) {
        log.debug("Atualizando conselhosprofissionais. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do conselhosprofissionais é obrigatório");
        }

        validarDadosBasicos(request);

        ConselhosProfissionais conselhosProfissionaisExistente = conselhosProfissionaisRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ConselhosProfissionais não encontrado com ID: " + id));

        atualizarDadosConselhosProfissionais(conselhosProfissionaisExistente, request);

        ConselhosProfissionais conselhosProfissionaisAtualizado = conselhosProfissionaisRepository.save(conselhosProfissionaisExistente);
        log.info("ConselhosProfissionais atualizado com sucesso. ID: {}", conselhosProfissionaisAtualizado.getId());

        return conselhosProfissionaisMapper.toResponse(conselhosProfissionaisAtualizado);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo conselhosprofissionais. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do conselhosprofissionais é obrigatório");
        }

        ConselhosProfissionais conselhosProfissionais = conselhosProfissionaisRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ConselhosProfissionais não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(conselhosProfissionais.getActive())) {
            throw new BadRequestException("ConselhosProfissionais já está inativo");
        }

        conselhosProfissionais.setActive(false);
        conselhosProfissionaisRepository.save(conselhosProfissionais);
        log.info("ConselhosProfissionais excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(ConselhosProfissionaisRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do conselhosprofissionais são obrigatórios");
        }
    }

        private void atualizarDadosConselhosProfissionais(ConselhosProfissionais conselhosProfissionais, ConselhosProfissionaisRequest request) {
        ConselhosProfissionais conselhosProfissionaisAtualizado = conselhosProfissionaisMapper.fromRequest(request);
        
        // Preserva campos de controle
        java.util.UUID idOriginal = conselhosProfissionais.getId();
        com.upsaude.entity.Tenant tenantOriginal = conselhosProfissionais.getTenant();
        Boolean activeOriginal = conselhosProfissionais.getActive();
        java.time.OffsetDateTime createdAtOriginal = conselhosProfissionais.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(conselhosProfissionaisAtualizado, conselhosProfissionais);
        
        // Restaura campos de controle
        conselhosProfissionais.setId(idOriginal);
        conselhosProfissionais.setTenant(tenantOriginal);
        conselhosProfissionais.setActive(activeOriginal);
        conselhosProfissionais.setCreatedAt(createdAtOriginal);
    }
}
