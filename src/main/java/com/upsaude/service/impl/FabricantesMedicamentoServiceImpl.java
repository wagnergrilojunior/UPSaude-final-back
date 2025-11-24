package com.upsaude.service.impl;

import com.upsaude.api.request.FabricantesMedicamentoRequest;
import com.upsaude.api.response.FabricantesMedicamentoResponse;
import com.upsaude.entity.FabricantesMedicamento;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.FabricantesMedicamentoMapper;
import com.upsaude.repository.FabricantesMedicamentoRepository;
import com.upsaude.service.FabricantesMedicamentoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de FabricantesMedicamento.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FabricantesMedicamentoServiceImpl implements FabricantesMedicamentoService {

    private final FabricantesMedicamentoRepository fabricantesMedicamentoRepository;
    private final FabricantesMedicamentoMapper fabricantesMedicamentoMapper;

    @Override
    @Transactional
    public FabricantesMedicamentoResponse criar(FabricantesMedicamentoRequest request) {
        log.debug("Criando novo fabricantesmedicamento");

        validarDadosBasicos(request);

        FabricantesMedicamento fabricantesMedicamento = fabricantesMedicamentoMapper.fromRequest(request);
        fabricantesMedicamento.setActive(true);

        FabricantesMedicamento fabricantesMedicamentoSalvo = fabricantesMedicamentoRepository.save(fabricantesMedicamento);
        log.info("FabricantesMedicamento criado com sucesso. ID: {}", fabricantesMedicamentoSalvo.getId());

        return fabricantesMedicamentoMapper.toResponse(fabricantesMedicamentoSalvo);
    }

    @Override
    @Transactional
    public FabricantesMedicamentoResponse obterPorId(UUID id) {
        log.debug("Buscando fabricantesmedicamento por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do fabricantesmedicamento é obrigatório");
        }

        FabricantesMedicamento fabricantesMedicamento = fabricantesMedicamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("FabricantesMedicamento não encontrado com ID: " + id));

        return fabricantesMedicamentoMapper.toResponse(fabricantesMedicamento);
    }

    @Override
    public Page<FabricantesMedicamentoResponse> listar(Pageable pageable) {
        log.debug("Listando FabricantesMedicamentos paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<FabricantesMedicamento> fabricantesMedicamentos = fabricantesMedicamentoRepository.findAll(pageable);
        return fabricantesMedicamentos.map(fabricantesMedicamentoMapper::toResponse);
    }

    @Override
    @Transactional
    public FabricantesMedicamentoResponse atualizar(UUID id, FabricantesMedicamentoRequest request) {
        log.debug("Atualizando fabricantesmedicamento. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do fabricantesmedicamento é obrigatório");
        }

        validarDadosBasicos(request);

        FabricantesMedicamento fabricantesMedicamentoExistente = fabricantesMedicamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("FabricantesMedicamento não encontrado com ID: " + id));

        atualizarDadosFabricantesMedicamento(fabricantesMedicamentoExistente, request);

        FabricantesMedicamento fabricantesMedicamentoAtualizado = fabricantesMedicamentoRepository.save(fabricantesMedicamentoExistente);
        log.info("FabricantesMedicamento atualizado com sucesso. ID: {}", fabricantesMedicamentoAtualizado.getId());

        return fabricantesMedicamentoMapper.toResponse(fabricantesMedicamentoAtualizado);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo fabricantesmedicamento. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do fabricantesmedicamento é obrigatório");
        }

        FabricantesMedicamento fabricantesMedicamento = fabricantesMedicamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("FabricantesMedicamento não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(fabricantesMedicamento.getActive())) {
            throw new BadRequestException("FabricantesMedicamento já está inativo");
        }

        fabricantesMedicamento.setActive(false);
        fabricantesMedicamentoRepository.save(fabricantesMedicamento);
        log.info("FabricantesMedicamento excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(FabricantesMedicamentoRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do fabricantesmedicamento são obrigatórios");
        }
    }

        private void atualizarDadosFabricantesMedicamento(FabricantesMedicamento fabricantesMedicamento, FabricantesMedicamentoRequest request) {
        FabricantesMedicamento fabricantesMedicamentoAtualizado = fabricantesMedicamentoMapper.fromRequest(request);
        
        // Preserva campos de controle
        java.util.UUID idOriginal = fabricantesMedicamento.getId();
        com.upsaude.entity.Tenant tenantOriginal = fabricantesMedicamento.getTenant();
        Boolean activeOriginal = fabricantesMedicamento.getActive();
        java.time.OffsetDateTime createdAtOriginal = fabricantesMedicamento.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(fabricantesMedicamentoAtualizado, fabricantesMedicamento);
        
        // Restaura campos de controle
        fabricantesMedicamento.setId(idOriginal);
        fabricantesMedicamento.setTenant(tenantOriginal);
        fabricantesMedicamento.setActive(activeOriginal);
        fabricantesMedicamento.setCreatedAt(createdAtOriginal);
    }
}
