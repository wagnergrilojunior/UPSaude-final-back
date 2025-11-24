package com.upsaude.service.impl;

import com.upsaude.api.request.CidadesRequest;
import com.upsaude.api.response.CidadesResponse;
import com.upsaude.entity.Cidades;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.CidadesMapper;
import com.upsaude.repository.CidadesRepository;
import com.upsaude.service.CidadesService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Cidades.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CidadesServiceImpl implements CidadesService {

    private final CidadesRepository cidadesRepository;
    private final CidadesMapper cidadesMapper;

    @Override
    @Transactional
    public CidadesResponse criar(CidadesRequest request) {
        log.debug("Criando novo cidades");

        validarDadosBasicos(request);

        Cidades cidades = cidadesMapper.fromRequest(request);
        cidades.setActive(true);

        Cidades cidadesSalvo = cidadesRepository.save(cidades);
        log.info("Cidades criado com sucesso. ID: {}", cidadesSalvo.getId());

        return cidadesMapper.toResponse(cidadesSalvo);
    }

    @Override
    @Transactional
    public CidadesResponse obterPorId(UUID id) {
        log.debug("Buscando cidades por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do cidades é obrigatório");
        }

        Cidades cidades = cidadesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cidades não encontrado com ID: " + id));

        return cidadesMapper.toResponse(cidades);
    }

    @Override
    public Page<CidadesResponse> listar(Pageable pageable) {
        log.debug("Listando Cidades paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<Cidades> cidades = cidadesRepository.findAll(pageable);
        return cidades.map(cidadesMapper::toResponse);
    }

    @Override
    @Transactional
    public CidadesResponse atualizar(UUID id, CidadesRequest request) {
        log.debug("Atualizando cidades. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do cidades é obrigatório");
        }

        validarDadosBasicos(request);

        Cidades cidadesExistente = cidadesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cidades não encontrado com ID: " + id));

        atualizarDadosCidades(cidadesExistente, request);

        Cidades cidadesAtualizado = cidadesRepository.save(cidadesExistente);
        log.info("Cidades atualizado com sucesso. ID: {}", cidadesAtualizado.getId());

        return cidadesMapper.toResponse(cidadesAtualizado);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo cidades. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do cidades é obrigatório");
        }

        Cidades cidades = cidadesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cidades não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(cidades.getActive())) {
            throw new BadRequestException("Cidades já está inativo");
        }

        cidades.setActive(false);
        cidadesRepository.save(cidades);
        log.info("Cidades excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(CidadesRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do cidades são obrigatórios");
        }
    }

        private void atualizarDadosCidades(Cidades cidades, CidadesRequest request) {
        Cidades cidadesAtualizado = cidadesMapper.fromRequest(request);
        
        // Preserva campos de controle (Cidades não tem tenant)
        java.util.UUID idOriginal = cidades.getId();
        Boolean activeOriginal = cidades.getActive();
        java.time.OffsetDateTime createdAtOriginal = cidades.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(cidadesAtualizado, cidades);
        
        // Restaura campos de controle
        cidades.setId(idOriginal);
        cidades.setActive(activeOriginal);
        cidades.setCreatedAt(createdAtOriginal);
    }
}
