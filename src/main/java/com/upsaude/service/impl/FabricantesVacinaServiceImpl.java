package com.upsaude.service.impl;

import com.upsaude.api.request.FabricantesVacinaRequest;
import com.upsaude.api.response.FabricantesVacinaResponse;
import com.upsaude.entity.FabricantesVacina;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.FabricantesVacinaMapper;
import com.upsaude.repository.FabricantesVacinaRepository;
import com.upsaude.service.FabricantesVacinaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de FabricantesVacina.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FabricantesVacinaServiceImpl implements FabricantesVacinaService {

    private final FabricantesVacinaRepository fabricantesVacinaRepository;
    private final FabricantesVacinaMapper fabricantesVacinaMapper;

    @Override
    @Transactional
    public FabricantesVacinaResponse criar(FabricantesVacinaRequest request) {
        log.debug("Criando novo fabricantesvacina");

        validarDadosBasicos(request);

        FabricantesVacina fabricantesVacina = fabricantesVacinaMapper.fromRequest(request);
        fabricantesVacina.setActive(true);

        FabricantesVacina fabricantesVacinaSalvo = fabricantesVacinaRepository.save(fabricantesVacina);
        log.info("FabricantesVacina criado com sucesso. ID: {}", fabricantesVacinaSalvo.getId());

        return fabricantesVacinaMapper.toResponse(fabricantesVacinaSalvo);
    }

    @Override
    @Transactional
    public FabricantesVacinaResponse obterPorId(UUID id) {
        log.debug("Buscando fabricantesvacina por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do fabricantesvacina é obrigatório");
        }

        FabricantesVacina fabricantesVacina = fabricantesVacinaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("FabricantesVacina não encontrado com ID: " + id));

        return fabricantesVacinaMapper.toResponse(fabricantesVacina);
    }

    @Override
    public Page<FabricantesVacinaResponse> listar(Pageable pageable) {
        log.debug("Listando FabricantesVacinas paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<FabricantesVacina> fabricantesVacinas = fabricantesVacinaRepository.findAll(pageable);
        return fabricantesVacinas.map(fabricantesVacinaMapper::toResponse);
    }

    @Override
    @Transactional
    public FabricantesVacinaResponse atualizar(UUID id, FabricantesVacinaRequest request) {
        log.debug("Atualizando fabricantesvacina. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do fabricantesvacina é obrigatório");
        }

        validarDadosBasicos(request);

        FabricantesVacina fabricantesVacinaExistente = fabricantesVacinaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("FabricantesVacina não encontrado com ID: " + id));

        atualizarDadosFabricantesVacina(fabricantesVacinaExistente, request);

        FabricantesVacina fabricantesVacinaAtualizado = fabricantesVacinaRepository.save(fabricantesVacinaExistente);
        log.info("FabricantesVacina atualizado com sucesso. ID: {}", fabricantesVacinaAtualizado.getId());

        return fabricantesVacinaMapper.toResponse(fabricantesVacinaAtualizado);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo fabricantesvacina. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do fabricantesvacina é obrigatório");
        }

        FabricantesVacina fabricantesVacina = fabricantesVacinaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("FabricantesVacina não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(fabricantesVacina.getActive())) {
            throw new BadRequestException("FabricantesVacina já está inativo");
        }

        fabricantesVacina.setActive(false);
        fabricantesVacinaRepository.save(fabricantesVacina);
        log.info("FabricantesVacina excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(FabricantesVacinaRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do fabricantesvacina são obrigatórios");
        }
    }

    private void atualizarDadosFabricantesVacina(FabricantesVacina fabricantesVacina, FabricantesVacinaRequest request) {
        FabricantesVacina fabricantesVacinaAtualizado = fabricantesVacinaMapper.fromRequest(request);
        
        // Preserva campos de controle
        java.util.UUID idOriginal = fabricantesVacina.getId();
        Boolean activeOriginal = fabricantesVacina.getActive();
        java.time.OffsetDateTime createdAtOriginal = fabricantesVacina.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(fabricantesVacinaAtualizado, fabricantesVacina);
        
        // Restaura campos de controle
        fabricantesVacina.setId(idOriginal);
        fabricantesVacina.setActive(activeOriginal);
        fabricantesVacina.setCreatedAt(createdAtOriginal);
    }
}
