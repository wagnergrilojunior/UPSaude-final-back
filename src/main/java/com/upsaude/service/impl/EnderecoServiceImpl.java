package com.upsaude.service.impl;

import com.upsaude.api.request.EnderecoRequest;
import com.upsaude.api.response.EnderecoResponse;
import com.upsaude.entity.Endereco;
import com.upsaude.entity.Cidades;
import com.upsaude.entity.Estados;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.EnderecoMapper;
import com.upsaude.repository.EnderecoRepository;
import com.upsaude.repository.CidadesRepository;
import com.upsaude.repository.EstadosRepository;
import com.upsaude.service.EnderecoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Endereco.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EnderecoServiceImpl implements EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;
    private final EstadosRepository estadosRepository;
    private final CidadesRepository cidadesRepository;

    @Override
    @Transactional
    @CacheEvict(value = "endereco", allEntries = true)
    public EnderecoResponse criar(EnderecoRequest request) {
        log.debug("Criando novo endereço. Request: {}", request);
        
        if (request == null) {
            log.warn("Tentativa de criar endereço com request nulo");
            throw new BadRequestException("Dados do endereço são obrigatórios");
        }

        try {
            validarDadosBasicos(request);

            Endereco endereco = enderecoMapper.fromRequest(request);
            endereco.setActive(true);

            // Garante valores padrão para campos obrigatórios
            if (endereco.getSemNumero() == null) {
                endereco.setSemNumero(false);
            }

            // Processa relacionamentos estado e cidade
            if (request.getEstado() != null) {
                Estados estado = estadosRepository.findById(request.getEstado())
                        .orElseThrow(() -> new NotFoundException("Estado não encontrado com ID: " + request.getEstado()));
                endereco.setEstado(estado);
            }

            if (request.getCidade() != null) {
                Cidades cidade = cidadesRepository.findById(request.getCidade())
                        .orElseThrow(() -> new NotFoundException("Cidade não encontrada com ID: " + request.getCidade()));
                endereco.setCidade(cidade);
            }

            Endereco enderecoSalvo = enderecoRepository.save(endereco);
            log.info("Endereço criado com sucesso. ID: {}", enderecoSalvo.getId());

            return enderecoMapper.toResponse(enderecoSalvo);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao criar endereço. Request: {}. Erro: {}", request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar endereço. Request: {}", request, e);
            throw new InternalServerErrorException("Erro ao persistir endereço", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar endereço. Request: {}", request, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "endereco", key = "#id")
    public EnderecoResponse obterPorId(UUID id) {
        log.debug("Buscando endereço por ID: {} (cache miss)", id);
        
        if (id == null) {
            log.warn("ID nulo recebido para busca de endereço");
            throw new BadRequestException("ID do endereço é obrigatório");
        }

        try {
            Endereco endereco = enderecoRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Endereço não encontrado com ID: " + id));

            log.debug("Endereço encontrado. ID: {}", id);
            return enderecoMapper.toResponse(endereco);
        } catch (NotFoundException e) {
            log.warn("Endereço não encontrado. ID: {}", id);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar endereço. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao buscar endereço", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao buscar endereço. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EnderecoResponse> listar(Pageable pageable) {
        log.debug("Listando endereços paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            Page<Endereco> enderecos = enderecoRepository.findAll(pageable);
            log.debug("Listagem de endereços concluída. Total de elementos: {}", enderecos.getTotalElements());
            return enderecos.map(enderecoMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar endereços. Pageable: {}", pageable, e);
            throw new InternalServerErrorException("Erro ao listar endereços", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar endereços. Pageable: {}", pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "endereco", key = "#id")
    public EnderecoResponse atualizar(UUID id, EnderecoRequest request) {
        log.debug("Atualizando endereço. ID: {}, Request: {}", id, request);

        if (id == null) {
            log.warn("ID nulo recebido para atualização de endereço");
            throw new BadRequestException("ID do endereço é obrigatório");
        }
        if (request == null) {
            log.warn("Request nulo recebido para atualização de endereço. ID: {}", id);
            throw new BadRequestException("Dados do endereço são obrigatórios");
        }

        try {
            validarDadosBasicos(request);

            Endereco enderecoExistente = enderecoRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Endereço não encontrado com ID: " + id));

            // Usa mapper do MapStruct que preserva campos de controle automaticamente
            enderecoMapper.updateFromRequest(request, enderecoExistente);
            
            // Garante valores padrão para campos obrigatórios
            if (enderecoExistente.getSemNumero() == null) {
                enderecoExistente.setSemNumero(false);
            }
            
            // Processa relacionamentos estado e cidade
            if (request.getEstado() != null) {
                Estados estado = estadosRepository.findById(request.getEstado())
                        .orElseThrow(() -> new NotFoundException("Estado não encontrado com ID: " + request.getEstado()));
                enderecoExistente.setEstado(estado);
            } else {
                enderecoExistente.setEstado(null);
            }

            if (request.getCidade() != null) {
                Cidades cidade = cidadesRepository.findById(request.getCidade())
                        .orElseThrow(() -> new NotFoundException("Cidade não encontrada com ID: " + request.getCidade()));
                enderecoExistente.setCidade(cidade);
            } else {
                enderecoExistente.setCidade(null);
            }

            Endereco enderecoAtualizado = enderecoRepository.save(enderecoExistente);
            log.info("Endereço atualizado com sucesso. ID: {}", enderecoAtualizado.getId());

            return enderecoMapper.toResponse(enderecoAtualizado);
        } catch (NotFoundException e) {
            log.warn("Tentativa de atualizar endereço não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao atualizar endereço. ID: {}, Request: {}. Erro: {}", id, request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar endereço. ID: {}, Request: {}", id, request, e);
            throw new InternalServerErrorException("Erro ao atualizar endereço", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao atualizar endereço. ID: {}, Request: {}", id, request, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "endereco", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo endereço. ID: {}", id);

        if (id == null) {
            log.warn("ID nulo recebido para exclusão de endereço");
            throw new BadRequestException("ID do endereço é obrigatório");
        }

        try {
            Endereco endereco = enderecoRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Endereço não encontrado com ID: " + id));

            if (Boolean.FALSE.equals(endereco.getActive())) {
                log.warn("Tentativa de excluir endereço já inativo. ID: {}", id);
                throw new BadRequestException("Endereço já está inativo");
            }

            endereco.setActive(false);
            enderecoRepository.save(endereco);
            log.info("Endereço excluído (desativado) com sucesso. ID: {}", id);
        } catch (NotFoundException e) {
            log.warn("Tentativa de excluir endereço não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao excluir endereço. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir endereço. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir endereço", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao excluir endereço. ID: {}", id, e);
            throw e;
        }
    }

    private void validarDadosBasicos(EnderecoRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do endereco são obrigatórios");
        }
    }

    // Método removido - agora usa enderecoMapper.updateFromRequest diretamente
    // O MapStruct já preserva campos de controle automaticamente
    // A lógica de relacionamentos foi movida para o método atualizar
}
