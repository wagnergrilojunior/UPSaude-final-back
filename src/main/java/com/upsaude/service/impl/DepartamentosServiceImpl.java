package com.upsaude.service.impl;

import com.upsaude.api.request.DepartamentosRequest;
import com.upsaude.api.response.DepartamentosResponse;
import com.upsaude.entity.Departamentos;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.DepartamentosMapper;
import com.upsaude.repository.DepartamentosRepository;
import com.upsaude.service.DepartamentosService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Departamentos.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DepartamentosServiceImpl implements DepartamentosService {

    private final DepartamentosRepository departamentosRepository;
    private final DepartamentosMapper departamentosMapper;

    @Override
    @Transactional
    public DepartamentosResponse criar(DepartamentosRequest request) {
        log.debug("Criando novo departamentos");

        validarDadosBasicos(request);

        Departamentos departamentos = departamentosMapper.fromRequest(request);
        departamentos.setActive(true);

        Departamentos departamentosSalvo = departamentosRepository.save(departamentos);
        log.info("Departamentos criado com sucesso. ID: {}", departamentosSalvo.getId());

        return departamentosMapper.toResponse(departamentosSalvo);
    }

    @Override
    @Transactional
    public DepartamentosResponse obterPorId(UUID id) {
        log.debug("Buscando departamentos por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do departamentos é obrigatório");
        }

        Departamentos departamentos = departamentosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Departamentos não encontrado com ID: " + id));

        return departamentosMapper.toResponse(departamentos);
    }

    @Override
    public Page<DepartamentosResponse> listar(Pageable pageable) {
        log.debug("Listando Departamentos paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<Departamentos> departamentos = departamentosRepository.findAll(pageable);
        return departamentos.map(departamentosMapper::toResponse);
    }

    @Override
    @Transactional
    public DepartamentosResponse atualizar(UUID id, DepartamentosRequest request) {
        log.debug("Atualizando departamentos. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do departamentos é obrigatório");
        }

        validarDadosBasicos(request);

        Departamentos departamentosExistente = departamentosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Departamentos não encontrado com ID: " + id));

        atualizarDadosDepartamentos(departamentosExistente, request);

        Departamentos departamentosAtualizado = departamentosRepository.save(departamentosExistente);
        log.info("Departamentos atualizado com sucesso. ID: {}", departamentosAtualizado.getId());

        return departamentosMapper.toResponse(departamentosAtualizado);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo departamentos. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do departamentos é obrigatório");
        }

        Departamentos departamentos = departamentosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Departamentos não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(departamentos.getActive())) {
            throw new BadRequestException("Departamentos já está inativo");
        }

        departamentos.setActive(false);
        departamentosRepository.save(departamentos);
        log.info("Departamentos excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(DepartamentosRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do departamentos são obrigatórios");
        }
    }

        private void atualizarDadosDepartamentos(Departamentos departamentos, DepartamentosRequest request) {
        Departamentos departamentosAtualizado = departamentosMapper.fromRequest(request);
        
        // Preserva campos de controle
        java.util.UUID idOriginal = departamentos.getId();
        com.upsaude.entity.Tenant tenantOriginal = departamentos.getTenant();
        Boolean activeOriginal = departamentos.getActive();
        java.time.OffsetDateTime createdAtOriginal = departamentos.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(departamentosAtualizado, departamentos);
        
        // Restaura campos de controle
        departamentos.setId(idOriginal);
        departamentos.setTenant(tenantOriginal);
        departamentos.setActive(activeOriginal);
        departamentos.setCreatedAt(createdAtOriginal);
    }
}
