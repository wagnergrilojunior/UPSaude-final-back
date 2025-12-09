package com.upsaude.service.impl;

import com.upsaude.api.request.ProfissionaisSaudeRequest;
import com.upsaude.api.response.ProfissionaisSaudeResponse;
import com.upsaude.entity.EspecialidadesMedicas;
import com.upsaude.entity.Endereco;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.ProfissionaisSaudeMapper;
import com.upsaude.repository.EspecialidadesMedicasRepository;
import com.upsaude.repository.EnderecoRepository;
import com.upsaude.repository.ProfissionaisSaudeRepository;
import com.upsaude.service.ProfissionaisSaudeService;
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
import org.springframework.util.StringUtils;

/**
 * Implementação do serviço de gerenciamento de Profissionais de Saúde.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProfissionaisSaudeServiceImpl implements ProfissionaisSaudeService {

    private final ProfissionaisSaudeRepository profissionaisSaudeRepository;
    private final ProfissionaisSaudeMapper profissionaisSaudeMapper;
    private final EspecialidadesMedicasRepository especialidadesMedicasRepository;
    private final EnderecoRepository enderecoRepository;

    @Override
    @Transactional
    @CacheEvict(value = "profissionaissaude", allEntries = true)
    public ProfissionaisSaudeResponse criar(ProfissionaisSaudeRequest request) {
        log.debug("Criando novo profissional de saúde. Request: {}", request);
        
        if (request == null) {
            log.warn("Tentativa de criar profissional de saúde com request nulo");
            throw new BadRequestException("Dados do profissional de saúde são obrigatórios");
        }

        try {
            validarDadosBasicos(request);
            validarUnicidade(request);

            ProfissionaisSaude profissional = profissionaisSaudeMapper.fromRequest(request);
            profissional.setActive(true);

            // Processa endereço profissional se fornecido (Fase 2.3)
            if (request.getEnderecoProfissional() != null) {
                Endereco endereco = enderecoRepository.findById(request.getEnderecoProfissional())
                        .orElseThrow(() -> new NotFoundException("Endereço profissional não encontrado com ID: " + request.getEnderecoProfissional()));
                profissional.setEnderecoProfissional(endereco);
            }

            ProfissionaisSaude profissionalSalvo = profissionaisSaudeRepository.save(profissional);
            log.info("Profissional de saúde criado com sucesso. ID: {}", profissionalSalvo.getId());

            return profissionaisSaudeMapper.toResponse(profissionalSalvo);
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao criar profissional de saúde. Request: {}. Erro: {}", request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar profissional de saúde. Request: {}", request, e);
            throw new InternalServerErrorException("Erro ao persistir profissional de saúde", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar profissional de saúde. Request: {}", request, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "profissionaissaude", key = "#id")
    public ProfissionaisSaudeResponse obterPorId(UUID id) {
        log.debug("Buscando profissional de saúde por ID: {} (cache miss)", id);
        
        if (id == null) {
            log.warn("ID nulo recebido para busca de profissional de saúde");
            throw new BadRequestException("ID do profissional de saúde é obrigatório");
        }

        try {
            ProfissionaisSaude profissional = profissionaisSaudeRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Profissional de saúde não encontrado com ID: " + id));

            log.debug("Profissional de saúde encontrado. ID: {}", id);
            return profissionaisSaudeMapper.toResponse(profissional);
        } catch (NotFoundException e) {
            log.warn("Profissional de saúde não encontrado. ID: {}", id);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar profissional de saúde. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao buscar profissional de saúde", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao buscar profissional de saúde. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProfissionaisSaudeResponse> listar(Pageable pageable) {
        log.debug("Listando profissionais de saúde paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            Page<ProfissionaisSaude> profissionais = profissionaisSaudeRepository.findAll(pageable);
            log.debug("Listagem de profissionais de saúde concluída. Total de elementos: {}", profissionais.getTotalElements());
            return profissionais.map(profissionaisSaudeMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar profissionais de saúde. Pageable: {}", pageable, e);
            throw new InternalServerErrorException("Erro ao listar profissionais de saúde", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar profissionais de saúde. Pageable: {}", pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "profissionaissaude", key = "#id")
    public ProfissionaisSaudeResponse atualizar(UUID id, ProfissionaisSaudeRequest request) {
        log.debug("Atualizando profissional de saúde. ID: {}, Request: {}", id, request);

        if (id == null) {
            log.warn("ID nulo recebido para atualização de profissional de saúde");
            throw new BadRequestException("ID do profissional de saúde é obrigatório");
        }
        if (request == null) {
            log.warn("Request nulo recebido para atualização de profissional de saúde. ID: {}", id);
            throw new BadRequestException("Dados do profissional de saúde são obrigatórios");
        }

        try {
            validarDadosBasicos(request);

            ProfissionaisSaude profissionalExistente = profissionaisSaudeRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Profissional de saúde não encontrado com ID: " + id));

            // Usa mapper do MapStruct que preserva campos de controle automaticamente
            profissionaisSaudeMapper.updateFromRequest(request, profissionalExistente);

            // Processa endereço profissional se fornecido (Fase 2.3 e 6.1)
            if (request.getEnderecoProfissional() != null) {
                Endereco endereco = enderecoRepository.findById(request.getEnderecoProfissional())
                        .orElseThrow(() -> new NotFoundException("Endereço profissional não encontrado com ID: " + request.getEnderecoProfissional()));
                profissionalExistente.setEnderecoProfissional(endereco);
            }
            // Se endereço não foi fornecido, mantém o existente (não remove)

            ProfissionaisSaude profissionalAtualizado = profissionaisSaudeRepository.save(profissionalExistente);
            log.info("Profissional de saúde atualizado com sucesso. ID: {}", profissionalAtualizado.getId());

            return profissionaisSaudeMapper.toResponse(profissionalAtualizado);
        } catch (NotFoundException e) {
            log.warn("Tentativa de atualizar profissional de saúde não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao atualizar profissional de saúde. ID: {}, Request: {}. Erro: {}", id, request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar profissional de saúde. ID: {}, Request: {}", id, request, e);
            throw new InternalServerErrorException("Erro ao atualizar profissional de saúde", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao atualizar profissional de saúde. ID: {}, Request: {}", id, request, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "profissionaissaude", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo profissional de saúde. ID: {}", id);

        if (id == null) {
            log.warn("ID nulo recebido para exclusão de profissional de saúde");
            throw new BadRequestException("ID do profissional de saúde é obrigatório");
        }

        try {
            ProfissionaisSaude profissional = profissionaisSaudeRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Profissional de saúde não encontrado com ID: " + id));

            if (Boolean.FALSE.equals(profissional.getActive())) {
                log.warn("Tentativa de excluir profissional de saúde já inativo. ID: {}", id);
                throw new BadRequestException("Profissional de saúde já está inativo");
            }

            profissional.setActive(false);
            profissionaisSaudeRepository.save(profissional);
            log.info("Profissional de saúde excluído (desativado) com sucesso. ID: {}", id);
        } catch (NotFoundException e) {
            log.warn("Tentativa de excluir profissional de saúde não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao excluir profissional de saúde. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir profissional de saúde. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir profissional de saúde", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao excluir profissional de saúde. ID: {}", id, e);
            throw e;
        }
    }

    private void validarDadosBasicos(ProfissionaisSaudeRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do profissional de saúde são obrigatórios");
        }

        // Validar dados regulatórios
        if (request.getDataValidadeRegistro() != null && request.getDataEmissaoRegistro() != null) {
            if (request.getDataValidadeRegistro().isBefore(request.getDataEmissaoRegistro())) {
                throw new BadRequestException("Data de validade do registro não pode ser anterior à data de emissão");
            }
        }

        // Validar que profissional com registro suspenso ou inativo não pode ser vinculado
        if (request.getStatusRegistro() != null && 
            (request.getStatusRegistro() == com.upsaude.enums.StatusAtivoEnum.SUSPENSO || 
             request.getStatusRegistro() == com.upsaude.enums.StatusAtivoEnum.INATIVO)) {
            log.warn("Profissional sendo cadastrado com status de registro: {}", request.getStatusRegistro());
        }
    }

    private void validarUnicidade(ProfissionaisSaudeRequest request) {
        // Validar unicidade de CPF
        if (request.getCpf() != null && !request.getCpf().trim().isEmpty()) {
            if (profissionaisSaudeRepository.existsByCpf(request.getCpf())) {
                throw new BadRequestException("Já existe um profissional cadastrado com o CPF informado");
            }
        }

        // Validar unicidade de registro profissional (registro + conselho + UF)
        if (request.getRegistroProfissional() != null && request.getConselho() != null && request.getUfRegistro() != null) {
            if (profissionaisSaudeRepository.existsByRegistroProfissionalAndConselhoIdAndUfRegistro(
                    request.getRegistroProfissional(), request.getConselho(), request.getUfRegistro())) {
                throw new BadRequestException("Já existe um profissional cadastrado com o registro profissional, conselho e UF informados");
            }
        }
    }

    // Método removido - agora usa profissionaisSaudeMapper.updateFromRequest diretamente
    // O MapStruct já preserva campos de controle automaticamente
    // Especialidades são gerenciadas por relacionamento separado
}

