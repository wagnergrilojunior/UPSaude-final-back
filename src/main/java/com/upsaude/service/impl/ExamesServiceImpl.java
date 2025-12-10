package com.upsaude.service.impl;

import com.upsaude.api.request.ExamesRequest;
import com.upsaude.api.response.ExamesResponse;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.Exames;
import com.upsaude.entity.Paciente;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import org.springframework.dao.DataAccessException;
import com.upsaude.mapper.ExamesMapper;
import com.upsaude.repository.EstabelecimentosRepository;
import com.upsaude.repository.ExamesRepository;
import com.upsaude.repository.PacienteRepository;
import com.upsaude.service.ExamesService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Exames.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ExamesServiceImpl implements ExamesService {

    private final ExamesRepository examesRepository;
    private final ExamesMapper examesMapper;
    private final EstabelecimentosRepository estabelecimentosRepository;
    private final PacienteRepository pacienteRepository;

    @Override
    @Transactional
    @CacheEvict(value = "exames", allEntries = true)
    public ExamesResponse criar(ExamesRequest request) {
        log.debug("Criando novo Exame");
        
        if (request == null) {
            log.warn("Request nulo recebido para criação de Exame");
            throw new BadRequestException("Dados do exame são obrigatórios");
        }

        // Validação de dados básicos é feita automaticamente pelo Bean Validation no Request
        Exames exames = examesMapper.fromRequest(request);

        // Carrega e define o estabelecimento
        if (request.getEstabelecimentoRealizador() != null) {
            Estabelecimentos estabelecimento = estabelecimentosRepository.findById(request.getEstabelecimentoRealizador())
                    .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado com ID: " + request.getEstabelecimentoRealizador()));
            exames.setEstabelecimentoRealizador(estabelecimento);
        }

        // Carrega e define o paciente
        Paciente paciente = pacienteRepository.findById(request.getPaciente())
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPaciente()));
        exames.setPaciente(paciente);

        exames.setActive(true);

        Exames examesSalvo = examesRepository.save(exames);
        log.info("Exame criado com sucesso. ID: {}", examesSalvo.getId());

        return examesMapper.toResponse(examesSalvo);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "exames", key = "#id")
    public ExamesResponse obterPorId(UUID id) {
        log.debug("Buscando Exame por ID: {} (cache miss)", id);
        
        if (id == null) {
            log.warn("ID nulo recebido para busca de Exame");
            throw new BadRequestException("ID do exame é obrigatório");
        }

        Exames exames = examesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Exame não encontrado com ID: " + id));

        log.debug("Exame encontrado. ID: {}", id);
        return examesMapper.toResponse(exames);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExamesResponse> listar(Pageable pageable) {
        log.debug("Listando Exames paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<Exames> exames = examesRepository.findAll(pageable);
        log.debug("Listagem de Exames concluída. Total de elementos: {}", exames.getTotalElements());
        return exames.map(examesMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExamesResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando exames do estabelecimento: {}. Página: {}, Tamanho: {}",
                estabelecimentoId, pageable.getPageNumber(), pageable.getPageSize());

        if (estabelecimentoId == null) {
            log.warn("ID de estabelecimento nulo recebido para listagem de exames");
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        Page<Exames> exames = examesRepository.findByEstabelecimentoIdOrderByDataExameDesc(estabelecimentoId, pageable);
        log.debug("Listagem de exames do estabelecimento concluída. Estabelecimento ID: {}, Total: {}", estabelecimentoId, exames.getTotalElements());
        return exames.map(examesMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "exames", key = "#id")
    public ExamesResponse atualizar(UUID id, ExamesRequest request) {
        log.debug("Atualizando Exame. ID: {}", id);

        if (id == null) {
            log.warn("ID nulo recebido para atualização de Exame");
            throw new BadRequestException("ID do exame é obrigatório");
        }
        
        if (request == null) {
            log.warn("Request nulo recebido para atualização de Exame. ID: {}", id);
            throw new BadRequestException("Dados do exame são obrigatórios");
        }

        // Validação de dados básicos é feita automaticamente pelo Bean Validation no Request
        Exames examesExistente = examesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Exame não encontrado com ID: " + id));

        atualizarDadosExames(examesExistente, request);

        Exames examesAtualizado = examesRepository.save(examesExistente);
        log.info("Exame atualizado com sucesso. ID: {}", examesAtualizado.getId());

        return examesMapper.toResponse(examesAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "exames", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo Exame. ID: {}", id);

        if (id == null) {
            log.warn("ID nulo recebido para exclusão de Exame");
            throw new BadRequestException("ID do exame é obrigatório");
        }

        Exames exames = examesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Exame não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(exames.getActive())) {
            log.warn("Tentativa de excluir Exame já inativo. ID: {}", id);
            throw new BadRequestException("Exame já está inativo");
        }

        exames.setActive(false);
        examesRepository.save(exames);
        log.info("Exame excluído (desativado) com sucesso. ID: {}", id);
    }

    // Validações de dados básicos foram movidas para o Request usando Bean Validation
    // (@NotNull, @NotBlank, etc). Isso garante validação automática no Controller
    // e retorno de erro 400 padronizado via ApiExceptionHandler.

    private void atualizarDadosExames(Exames exames, ExamesRequest request) {
        // Usar mapper para atualizar campos básicos
        examesMapper.updateFromRequest(request, exames);
        
        // Processar relacionamentos que são ignorados pelo mapper
        if (request.getEstabelecimentoRealizador() != null) {
            Estabelecimentos estabelecimento = estabelecimentosRepository.findById(request.getEstabelecimentoRealizador())
                    .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado com ID: " + request.getEstabelecimentoRealizador()));
            exames.setEstabelecimentoRealizador(estabelecimento);
        }

        if (request.getPaciente() != null) {
            Paciente paciente = pacienteRepository.findById(request.getPaciente())
                    .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPaciente()));
            exames.setPaciente(paciente);
        }
    }
}
