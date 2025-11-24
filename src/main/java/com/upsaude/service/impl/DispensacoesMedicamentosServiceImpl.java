package com.upsaude.service.impl;

import com.upsaude.api.request.DispensacoesMedicamentosRequest;
import com.upsaude.api.response.DispensacoesMedicamentosResponse;
import com.upsaude.entity.DispensacoesMedicamentos;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.DispensacoesMedicamentosMapper;
import com.upsaude.repository.DispensacoesMedicamentosRepository;
import com.upsaude.service.DispensacoesMedicamentosService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de DispensacoesMedicamentos.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DispensacoesMedicamentosServiceImpl implements DispensacoesMedicamentosService {

    private final DispensacoesMedicamentosRepository dispensacoesMedicamentosRepository;
    private final DispensacoesMedicamentosMapper dispensacoesMedicamentosMapper;

    @Override
    @Transactional
    public DispensacoesMedicamentosResponse criar(DispensacoesMedicamentosRequest request) {
        log.debug("Criando novo dispensacoesmedicamentos");

        validarDadosBasicos(request);

        DispensacoesMedicamentos dispensacoesMedicamentos = dispensacoesMedicamentosMapper.fromRequest(request);
        dispensacoesMedicamentos.setActive(true);

        DispensacoesMedicamentos dispensacoesMedicamentosSalvo = dispensacoesMedicamentosRepository.save(dispensacoesMedicamentos);
        log.info("DispensacoesMedicamentos criado com sucesso. ID: {}", dispensacoesMedicamentosSalvo.getId());

        return dispensacoesMedicamentosMapper.toResponse(dispensacoesMedicamentosSalvo);
    }

    @Override
    @Transactional
    public DispensacoesMedicamentosResponse obterPorId(UUID id) {
        log.debug("Buscando dispensacoesmedicamentos por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do dispensacoesmedicamentos é obrigatório");
        }

        DispensacoesMedicamentos dispensacoesMedicamentos = dispensacoesMedicamentosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("DispensacoesMedicamentos não encontrado com ID: " + id));

        return dispensacoesMedicamentosMapper.toResponse(dispensacoesMedicamentos);
    }

    @Override
    public Page<DispensacoesMedicamentosResponse> listar(Pageable pageable) {
        log.debug("Listando DispensacoesMedicamentos paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<DispensacoesMedicamentos> dispensacoesMedicamentos = dispensacoesMedicamentosRepository.findAll(pageable);
        return dispensacoesMedicamentos.map(dispensacoesMedicamentosMapper::toResponse);
    }

    @Override
    @Transactional
    public DispensacoesMedicamentosResponse atualizar(UUID id, DispensacoesMedicamentosRequest request) {
        log.debug("Atualizando dispensacoesmedicamentos. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do dispensacoesmedicamentos é obrigatório");
        }

        validarDadosBasicos(request);

        DispensacoesMedicamentos dispensacoesMedicamentosExistente = dispensacoesMedicamentosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("DispensacoesMedicamentos não encontrado com ID: " + id));

        atualizarDadosDispensacoesMedicamentos(dispensacoesMedicamentosExistente, request);

        DispensacoesMedicamentos dispensacoesMedicamentosAtualizado = dispensacoesMedicamentosRepository.save(dispensacoesMedicamentosExistente);
        log.info("DispensacoesMedicamentos atualizado com sucesso. ID: {}", dispensacoesMedicamentosAtualizado.getId());

        return dispensacoesMedicamentosMapper.toResponse(dispensacoesMedicamentosAtualizado);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo dispensacoesmedicamentos. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do dispensacoesmedicamentos é obrigatório");
        }

        DispensacoesMedicamentos dispensacoesMedicamentos = dispensacoesMedicamentosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("DispensacoesMedicamentos não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(dispensacoesMedicamentos.getActive())) {
            throw new BadRequestException("DispensacoesMedicamentos já está inativo");
        }

        dispensacoesMedicamentos.setActive(false);
        dispensacoesMedicamentosRepository.save(dispensacoesMedicamentos);
        log.info("DispensacoesMedicamentos excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(DispensacoesMedicamentosRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do dispensacoesmedicamentos são obrigatórios");
        }
    }

        private void atualizarDadosDispensacoesMedicamentos(DispensacoesMedicamentos dispensacoesMedicamentos, DispensacoesMedicamentosRequest request) {
        DispensacoesMedicamentos dispensacoesMedicamentosAtualizado = dispensacoesMedicamentosMapper.fromRequest(request);
        
        // Preserva campos de controle
        java.util.UUID idOriginal = dispensacoesMedicamentos.getId();
        com.upsaude.entity.Tenant tenantOriginal = dispensacoesMedicamentos.getTenant();
        Boolean activeOriginal = dispensacoesMedicamentos.getActive();
        java.time.OffsetDateTime createdAtOriginal = dispensacoesMedicamentos.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(dispensacoesMedicamentosAtualizado, dispensacoesMedicamentos);
        
        // Restaura campos de controle
        dispensacoesMedicamentos.setId(idOriginal);
        dispensacoesMedicamentos.setTenant(tenantOriginal);
        dispensacoesMedicamentos.setActive(activeOriginal);
        dispensacoesMedicamentos.setCreatedAt(createdAtOriginal);
    }
}
