package com.upsaude.service.impl;

import com.upsaude.api.request.MedicamentosRequest;
import com.upsaude.api.response.MedicamentosResponse;
import com.upsaude.entity.Medicamentos;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.MedicamentosMapper;
import com.upsaude.repository.MedicamentosRepository;
import com.upsaude.service.MedicamentosService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Medicamentos.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MedicamentosServiceImpl implements MedicamentosService {

    private final MedicamentosRepository medicamentosRepository;
    private final MedicamentosMapper medicamentosMapper;

    @Override
    @Transactional
    public MedicamentosResponse criar(MedicamentosRequest request) {
        log.debug("Criando novo medicamentos");

        validarDadosBasicos(request);

        Medicamentos medicamentos = medicamentosMapper.fromRequest(request);
        medicamentos.setActive(true);

        Medicamentos medicamentosSalvo = medicamentosRepository.save(medicamentos);
        log.info("Medicamentos criado com sucesso. ID: {}", medicamentosSalvo.getId());

        return medicamentosMapper.toResponse(medicamentosSalvo);
    }

    @Override
    @Transactional
    public MedicamentosResponse obterPorId(UUID id) {
        log.debug("Buscando medicamentos por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do medicamentos é obrigatório");
        }

        Medicamentos medicamentos = medicamentosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Medicamentos não encontrado com ID: " + id));

        return medicamentosMapper.toResponse(medicamentos);
    }

    @Override
    public Page<MedicamentosResponse> listar(Pageable pageable) {
        log.debug("Listando Medicamentos paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<Medicamentos> medicamentos = medicamentosRepository.findAll(pageable);
        return medicamentos.map(medicamentosMapper::toResponse);
    }

    @Override
    @Transactional
    public MedicamentosResponse atualizar(UUID id, MedicamentosRequest request) {
        log.debug("Atualizando medicamentos. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do medicamentos é obrigatório");
        }

        validarDadosBasicos(request);

        Medicamentos medicamentosExistente = medicamentosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Medicamentos não encontrado com ID: " + id));

        atualizarDadosMedicamentos(medicamentosExistente, request);

        Medicamentos medicamentosAtualizado = medicamentosRepository.save(medicamentosExistente);
        log.info("Medicamentos atualizado com sucesso. ID: {}", medicamentosAtualizado.getId());

        return medicamentosMapper.toResponse(medicamentosAtualizado);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo medicamentos. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do medicamentos é obrigatório");
        }

        Medicamentos medicamentos = medicamentosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Medicamentos não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(medicamentos.getActive())) {
            throw new BadRequestException("Medicamentos já está inativo");
        }

        medicamentos.setActive(false);
        medicamentosRepository.save(medicamentos);
        log.info("Medicamentos excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(MedicamentosRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do medicamentos são obrigatórios");
        }
    }

        private void atualizarDadosMedicamentos(Medicamentos medicamentos, MedicamentosRequest request) {
        Medicamentos medicamentosAtualizado = medicamentosMapper.fromRequest(request);
        
        // Preserva campos de controle
        java.util.UUID idOriginal = medicamentos.getId();
        com.upsaude.entity.Tenant tenantOriginal = medicamentos.getTenant();
        Boolean activeOriginal = medicamentos.getActive();
        java.time.OffsetDateTime createdAtOriginal = medicamentos.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(medicamentosAtualizado, medicamentos);
        
        // Restaura campos de controle
        medicamentos.setId(idOriginal);
        medicamentos.setTenant(tenantOriginal);
        medicamentos.setActive(activeOriginal);
        medicamentos.setCreatedAt(createdAtOriginal);
    }
}
