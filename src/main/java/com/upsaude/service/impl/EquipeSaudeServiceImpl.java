package com.upsaude.service.impl;

import com.upsaude.api.request.EquipeSaudeRequest;
import com.upsaude.api.response.EquipeSaudeResponse;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.EquipeSaude;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.entity.VinculoProfissionalEquipe;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoVinculoProfissionalEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.EquipeSaudeMapper;
import com.upsaude.repository.EstabelecimentosRepository;
import com.upsaude.repository.EquipeSaudeRepository;
import com.upsaude.repository.ProfissionaisSaudeRepository;
import com.upsaude.service.EquipeSaudeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Equipes de Saúde.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EquipeSaudeServiceImpl implements EquipeSaudeService {

    private final EquipeSaudeRepository equipeSaudeRepository;
    private final EquipeSaudeMapper equipeSaudeMapper;
    private final EstabelecimentosRepository estabelecimentosRepository;
    private final ProfissionaisSaudeRepository profissionaisSaudeRepository;

    @Override
    @Transactional
    @CacheEvict(value = "equipesaude", allEntries = true)
    public EquipeSaudeResponse criar(EquipeSaudeRequest request) {
        log.debug("Criando nova equipe de saúde. Request: {}", request);
        
        if (request == null) {
            log.warn("Tentativa de criar equipe de saúde com request nulo");
            throw new BadRequestException("Dados da equipe de saúde são obrigatórios");
        }

        try {
            // Validação de dados básicos é feita automaticamente pelo Bean Validation no Request

            // Valida se já existe equipe com mesmo INE no estabelecimento
            if (equipeSaudeRepository.findByIneAndEstabelecimentoId(request.getIne(), request.getEstabelecimento()).isPresent()) {
                log.warn("Tentativa de criar equipe com INE duplicado. INE: {}, Estabelecimento: {}", request.getIne(), request.getEstabelecimento());
                throw new BadRequestException("Já existe uma equipe com o INE " + request.getIne() + " neste estabelecimento");
            }

            // Carrega e valida estabelecimento
            Estabelecimentos estabelecimento = estabelecimentosRepository.findById(request.getEstabelecimento())
                    .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado com ID: " + request.getEstabelecimento()));

            EquipeSaude equipe = equipeSaudeMapper.fromRequest(request);
            equipe.setEstabelecimento(estabelecimento);
            equipe.setActive(true);

            EquipeSaude equipeSalva = equipeSaudeRepository.save(equipe);

            // Cria vínculos com profissionais se fornecidos
            // Nota: Os vínculos devem ser criados através de endpoint específico ou service dedicado
            // Aqui apenas salvamos a equipe sem vínculos iniciais

            log.info("Equipe de saúde criada com sucesso. ID: {}", equipeSalva.getId());

            return equipeSaudeMapper.toResponse(equipeSalva);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao criar equipe de saúde. Request: {}. Erro: {}", request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar equipe de saúde. Request: {}", request, e);
            throw new InternalServerErrorException("Erro ao persistir equipe de saúde", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar equipe de saúde. Request: {}", request, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "equipesaude", key = "#id")
    public EquipeSaudeResponse obterPorId(UUID id) {
        log.debug("Buscando equipe de saúde por ID: {} (cache miss)", id);
        
        if (id == null) {
            log.warn("ID nulo recebido para busca de equipe de saúde");
            throw new BadRequestException("ID da equipe é obrigatório");
        }

        try {
            EquipeSaude equipe = equipeSaudeRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Equipe de saúde não encontrada com ID: " + id));

            log.debug("Equipe de saúde encontrada. ID: {}", id);
            return equipeSaudeMapper.toResponse(equipe);
        } catch (NotFoundException e) {
            log.warn("Equipe de saúde não encontrada. ID: {}", id);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar equipe de saúde. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao buscar equipe de saúde", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao buscar equipe de saúde. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EquipeSaudeResponse> listar(Pageable pageable) {
        log.debug("Listando equipes de saúde paginadas. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            Page<EquipeSaude> equipes = equipeSaudeRepository.findAll(pageable);
            log.debug("Listagem de equipes de saúde concluída. Total de elementos: {}", equipes.getTotalElements());
            return equipes.map(equipeSaudeMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar equipes de saúde. Pageable: {}", pageable, e);
            throw new InternalServerErrorException("Erro ao listar equipes de saúde", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar equipes de saúde. Pageable: {}", pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EquipeSaudeResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando equipes de saúde do estabelecimento: {}", estabelecimentoId);

        if (estabelecimentoId == null) {
            log.warn("ID de estabelecimento nulo recebido para listagem de equipes de saúde");
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        try {
            Page<EquipeSaude> equipes = equipeSaudeRepository.findByEstabelecimentoIdOrderByNomeReferenciaAsc(estabelecimentoId, pageable);
            log.debug("Listagem de equipes de saúde do estabelecimento concluída. Estabelecimento ID: {}, Total: {}", estabelecimentoId, equipes.getTotalElements());
            return equipes.map(equipeSaudeMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar equipes de saúde por estabelecimento. Estabelecimento ID: {}, Pageable: {}", estabelecimentoId, pageable, e);
            throw new InternalServerErrorException("Erro ao listar equipes de saúde do estabelecimento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar equipes de saúde por estabelecimento. Estabelecimento ID: {}, Pageable: {}", estabelecimentoId, pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EquipeSaudeResponse> listarPorStatus(StatusAtivoEnum status, UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando equipes de saúde por status: {} e estabelecimento: {}", status, estabelecimentoId);

        if (status == null) {
            log.warn("Status nulo recebido para listagem de equipes de saúde");
            throw new BadRequestException("Status é obrigatório");
        }

        if (estabelecimentoId == null) {
            log.warn("ID de estabelecimento nulo recebido para listagem de equipes de saúde");
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        try {
            Page<EquipeSaude> equipes = equipeSaudeRepository.findByStatusAndEstabelecimentoId(status, estabelecimentoId, pageable);
            log.debug("Listagem de equipes de saúde por status concluída. Status: {}, Estabelecimento ID: {}, Total: {}", status, estabelecimentoId, equipes.getTotalElements());
            return equipes.map(equipeSaudeMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar equipes de saúde por status. Status: {}, Estabelecimento ID: {}, Pageable: {}", status, estabelecimentoId, pageable, e);
            throw new InternalServerErrorException("Erro ao listar equipes de saúde por status", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar equipes de saúde por status. Status: {}, Estabelecimento ID: {}, Pageable: {}", status, estabelecimentoId, pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "equipesaude", key = "#id")
    public EquipeSaudeResponse atualizar(UUID id, EquipeSaudeRequest request) {
        log.debug("Atualizando equipe de saúde. ID: {}, Request: {}", id, request);

        if (id == null) {
            log.warn("ID nulo recebido para atualização de equipe de saúde");
            throw new BadRequestException("ID da equipe é obrigatório");
        }
        if (request == null) {
            log.warn("Request nulo recebido para atualização de equipe de saúde. ID: {}", id);
            throw new BadRequestException("Dados da equipe são obrigatórios");
        }

        try {
            // Validação de dados básicos é feita automaticamente pelo Bean Validation no Request

            EquipeSaude equipeExistente = equipeSaudeRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Equipe de saúde não encontrada com ID: " + id));

            // Valida se o INE foi alterado e se já existe outro com o mesmo INE no estabelecimento
            if (!equipeExistente.getIne().equals(request.getIne())) {
                if (equipeSaudeRepository.findByIneAndEstabelecimentoId(request.getIne(), request.getEstabelecimento()).isPresent()) {
                    log.warn("Tentativa de atualizar equipe com INE duplicado. INE: {}, Estabelecimento: {}", request.getIne(), request.getEstabelecimento());
                    throw new BadRequestException("Já existe uma equipe com o INE " + request.getIne() + " neste estabelecimento");
                }
            }

            // Usa mapper do MapStruct que preserva campos de controle automaticamente
            equipeSaudeMapper.updateFromRequest(request, equipeExistente);
            
            // Atualiza estabelecimento se foi alterado
            if (!equipeExistente.getEstabelecimento().getId().equals(request.getEstabelecimento())) {
                Estabelecimentos estabelecimento = estabelecimentosRepository.findById(request.getEstabelecimento())
                        .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado com ID: " + request.getEstabelecimento()));
                equipeExistente.setEstabelecimento(estabelecimento);
            }

            EquipeSaude equipeAtualizada = equipeSaudeRepository.save(equipeExistente);
            log.info("Equipe de saúde atualizada com sucesso. ID: {}", equipeAtualizada.getId());

            return equipeSaudeMapper.toResponse(equipeAtualizada);
        } catch (NotFoundException e) {
            log.warn("Tentativa de atualizar equipe de saúde não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao atualizar equipe de saúde. ID: {}, Request: {}. Erro: {}", id, request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar equipe de saúde. ID: {}, Request: {}", id, request, e);
            throw new InternalServerErrorException("Erro ao atualizar equipe de saúde", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao atualizar equipe de saúde. ID: {}, Request: {}", id, request, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "equipesaude", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo equipe de saúde. ID: {}", id);

        if (id == null) {
            log.warn("ID nulo recebido para exclusão de equipe de saúde");
            throw new BadRequestException("ID da equipe é obrigatório");
        }

        try {
            EquipeSaude equipe = equipeSaudeRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Equipe de saúde não encontrada com ID: " + id));

            if (Boolean.FALSE.equals(equipe.getActive())) {
                log.warn("Tentativa de excluir equipe de saúde já inativa. ID: {}", id);
                throw new BadRequestException("Equipe já está inativa");
            }

            equipe.setActive(false);
            equipeSaudeRepository.save(equipe);
            log.info("Equipe de saúde excluída (desativada) com sucesso. ID: {}", id);
        } catch (NotFoundException e) {
            log.warn("Tentativa de excluir equipe de saúde não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao excluir equipe de saúde. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir equipe de saúde. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir equipe de saúde", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao excluir equipe de saúde. ID: {}", id, e);
            throw e;
        }
    }

    // Validações de dados básicos foram movidas para o Request usando Bean Validation
    // (@NotNull, @NotBlank, @Pattern, etc). Isso garante validação automática no Controller
    // e retorno de erro 400 padronizado via ApiExceptionHandler.

    // Método removido - agora usa equipeSaudeMapper.updateFromRequest diretamente
    // O MapStruct já preserva campos de controle automaticamente
    // A lógica de atualização de estabelecimento foi movida para o método atualizar
    // Nota: Vínculos com profissionais devem ser gerenciados através de endpoints específicos
}

