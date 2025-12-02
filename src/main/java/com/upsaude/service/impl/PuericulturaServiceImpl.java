package com.upsaude.service.impl;

import com.upsaude.api.request.PuericulturaRequest;
import com.upsaude.api.response.PuericulturaResponse;
import com.upsaude.entity.Puericultura;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.PuericulturaMapper;
import com.upsaude.repository.PuericulturaRepository;
import com.upsaude.service.PuericulturaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementação do serviço de gerenciamento de Puericultura.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PuericulturaServiceImpl implements PuericulturaService {

    private final PuericulturaRepository puericulturaRepository;
    private final PuericulturaMapper puericulturaMapper;

    @Override
    @Transactional
    @CacheEvict(value = "puericultura", allEntries = true)
    public PuericulturaResponse criar(PuericulturaRequest request) {
        log.debug("Criando nova puericultura");

        validarDadosBasicos(request);

        Puericultura puericultura = puericulturaMapper.fromRequest(request);
        puericultura.setActive(true);

        if (puericultura.getAcompanhamentoAtivo() == null) {
            puericultura.setAcompanhamentoAtivo(true);
        }

        Puericultura puericulturaSalva = puericulturaRepository.save(puericultura);
        log.info("Puericultura criada com sucesso. ID: {}", puericulturaSalva.getId());

        return puericulturaMapper.toResponse(puericulturaSalva);
    }

    @Override
    @Transactional
    @Cacheable(value = "puericultura", key = "#id")
    public PuericulturaResponse obterPorId(UUID id) {
        log.debug("Buscando puericultura por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID da puericultura é obrigatório");
        }

        Puericultura puericultura = puericulturaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Puericultura não encontrada com ID: " + id));

        return puericulturaMapper.toResponse(puericultura);
    }

    @Override
    public Page<PuericulturaResponse> listar(Pageable pageable) {
        log.debug("Listando puericulturas paginadas");

        Page<Puericultura> puericulturas = puericulturaRepository.findAll(pageable);
        return puericulturas.map(puericulturaMapper::toResponse);
    }

    @Override
    public Page<PuericulturaResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando puericulturas por estabelecimento: {}", estabelecimentoId);

        Page<Puericultura> puericulturas = puericulturaRepository.findByEstabelecimentoIdOrderByDataInicioAcompanhamentoDesc(estabelecimentoId, pageable);
        return puericulturas.map(puericulturaMapper::toResponse);
    }

    @Override
    public List<PuericulturaResponse> listarPorPaciente(UUID pacienteId) {
        log.debug("Listando puericulturas por paciente: {}", pacienteId);

        List<Puericultura> puericulturas = puericulturaRepository.findByPacienteIdOrderByDataInicioAcompanhamentoDesc(pacienteId);
        return puericulturas.stream().map(puericulturaMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public Page<PuericulturaResponse> listarAtivos(UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando puericulturas ativos: {}", estabelecimentoId);

        Page<Puericultura> puericulturas = puericulturaRepository.findByAcompanhamentoAtivoAndEstabelecimentoId(true, estabelecimentoId, pageable);
        return puericulturas.map(puericulturaMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "puericultura", key = "#id")
    public PuericulturaResponse atualizar(UUID id, PuericulturaRequest request) {
        log.debug("Atualizando puericultura. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da puericultura é obrigatório");
        }

        validarDadosBasicos(request);

        Puericultura puericulturaExistente = puericulturaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Puericultura não encontrada com ID: " + id));

        atualizarDadosPuericultura(puericulturaExistente, request);

        Puericultura puericulturaAtualizada = puericulturaRepository.save(puericulturaExistente);
        log.info("Puericultura atualizada com sucesso. ID: {}", puericulturaAtualizada.getId());

        return puericulturaMapper.toResponse(puericulturaAtualizada);
    }

    @Override
    @Transactional
    @CacheEvict(value = "puericultura", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo puericultura. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da puericultura é obrigatório");
        }

        Puericultura puericultura = puericulturaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Puericultura não encontrada com ID: " + id));

        if (Boolean.FALSE.equals(puericultura.getActive())) {
            throw new BadRequestException("Puericultura já está inativa");
        }

        puericultura.setActive(false);
        puericulturaRepository.save(puericultura);
        log.info("Puericultura excluída (desativada) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(PuericulturaRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da puericultura são obrigatórios");
        }
        if (request.getPacienteId() == null) {
            throw new BadRequestException("Paciente é obrigatório");
        }
    }

    private void atualizarDadosPuericultura(Puericultura puericultura, PuericulturaRequest request) {
        Puericultura puericulturaAtualizada = puericulturaMapper.fromRequest(request);

        UUID idOriginal = puericultura.getId();
        com.upsaude.entity.Tenant tenantOriginal = puericultura.getTenant();
        Boolean activeOriginal = puericultura.getActive();
        java.time.OffsetDateTime createdAtOriginal = puericultura.getCreatedAt();

        BeanUtils.copyProperties(puericulturaAtualizada, puericultura);

        puericultura.setId(idOriginal);
        puericultura.setTenant(tenantOriginal);
        puericultura.setActive(activeOriginal);
        puericultura.setCreatedAt(createdAtOriginal);
    }
}

