package com.upsaude.service.impl.api.geral;

import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.api.request.geral.EnderecoRequest;
import com.upsaude.api.response.geral.EnderecoResponse;
import com.upsaude.entity.paciente.Endereco;
import com.upsaude.entity.referencia.geografico.Cidades;
import com.upsaude.entity.referencia.geografico.Estados;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.geral.EnderecoMapper;
import com.upsaude.repository.paciente.EnderecoRepository;
import com.upsaude.repository.referencia.geografico.CidadesRepository;
import com.upsaude.repository.referencia.geografico.EstadosRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.service.api.geral.EnderecoService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnderecoServiceImpl implements EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;
    private final EstadosRepository estadosRepository;
    private final CidadesRepository cidadesRepository;
    private final TenantService tenantService;
    private final TenantRepository tenantRepository;

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
            
            UUID tenantId = tenantService.validarTenantAtual();
            com.upsaude.entity.sistema.multitenancy.Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            if (tenant == null) {
                
                tenant = tenantRepository.findById(tenantId).orElse(null);
            }
            if (tenant == null) {
                log.warn("Tenant não encontrado. ID: {}", tenantId);
                throw new BadRequestException("Tenant não encontrado");
            }

            Endereco endereco = enderecoMapper.fromRequest(request);
            endereco.setActive(true);
            endereco.setTenant(tenant);

            if (endereco.getSemNumero() == null) {
                endereco.setSemNumero(false);
            }

            if (request.getEstado() != null) {
                Estados estado = estadosRepository.findById(request.getEstado())
                        .orElseThrow(
                                () -> new NotFoundException("Estado não encontrado com ID: " + request.getEstado()));
                endereco.setEstado(estado);
            }

            if (request.getCidade() != null) {
                Cidades cidade = cidadesRepository.findById(request.getCidade())
                        .orElseThrow(
                                () -> new NotFoundException("Cidade não encontrada com ID: " + request.getCidade()));
                endereco.setCidade(cidade);
            }

            Endereco enderecoSalvo = findOrCreate(endereco);
            log.info("Endereço processado. ID: {} - {}", enderecoSalvo.getId(),
                    enderecoSalvo.getId().equals(endereco.getId()) ? "Novo endereço criado"
                            : "Endereço existente reutilizado");

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

            Endereco enderecoExistente = enderecoRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Endereço não encontrado com ID: " + id));

            enderecoMapper.updateFromRequest(request, enderecoExistente);

            if (enderecoExistente.getSemNumero() == null) {
                enderecoExistente.setSemNumero(false);
            }

            if (request.getEstado() != null) {
                Estados estado = estadosRepository.findById(request.getEstado())
                        .orElseThrow(
                                () -> new NotFoundException("Estado não encontrado com ID: " + request.getEstado()));
                enderecoExistente.setEstado(estado);
            } else {
                enderecoExistente.setEstado(null);
            }

            if (request.getCidade() != null) {
                Cidades cidade = cidadesRepository.findById(request.getCidade())
                        .orElseThrow(
                                () -> new NotFoundException("Cidade não encontrada com ID: " + request.getCidade()));
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
            log.warn("Erro de validação ao atualizar endereço. ID: {}, Request: {}. Erro: {}", id, request,
                    e.getMessage());
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

    @Override
    @Transactional
    public Endereco findOrCreate(Endereco endereco) {
        log.debug("Buscando endereço existente ou criando novo. Logradouro: {}, Número: {}, Bairro: {}, CEP: {}",
                endereco.getLogradouro(), endereco.getNumero(), endereco.getBairro(), endereco.getCep());

        if (endereco == null) {
            log.warn("Tentativa de buscar/criar endereço com objeto nulo");
            throw new BadRequestException("Endereço não pode ser nulo");
        }

        try {

            String logradouroNormalizado = normalizarString(endereco.getLogradouro());
            String numeroNormalizado = normalizarNumero(endereco.getNumero());
            String bairroNormalizado = normalizarString(endereco.getBairro());
            String cepNormalizado = normalizarCep(endereco.getCep());

            if ((logradouroNormalizado == null || logradouroNormalizado.trim().isEmpty()) &&
                    (cepNormalizado == null || cepNormalizado.trim().isEmpty())) {
                log.debug("Endereço sem logradouro nem CEP. Criando novo sem buscar duplicados.");

            } else {

                UUID cidadeId = endereco.getCidade() != null ? endereco.getCidade().getId() : null;
                UUID estadoId = endereco.getEstado() != null ? endereco.getEstado().getId() : null;

                String tipoLogradouroStr = endereco.getTipoLogradouro() != null
                        ? endereco.getTipoLogradouro().getCodigo().toString()
                        : null;
                String cidadeIdStr = cidadeId != null ? cidadeId.toString() : null;
                String estadoIdStr = estadoId != null ? estadoId.toString() : null;
                String tipoEnderecoStr = endereco.getTipoEndereco() != null
                        ? endereco.getTipoEndereco().getCodigo().toString()
                        : null;
                String zonaStr = endereco.getZona() != null ? endereco.getZona().getCodigo().toString() : null;

                List<Endereco> enderecosExistentes = enderecoRepository.findByFieldsList(
                        tipoLogradouroStr,
                        logradouroNormalizado,
                        numeroNormalizado,
                        bairroNormalizado,
                        cepNormalizado,
                        cidadeIdStr,
                        estadoIdStr,
                        tipoEnderecoStr,
                        zonaStr,
                        tenantService.validarTenantAtual().toString());

                if (!enderecosExistentes.isEmpty()) {

                    Endereco encontrado = enderecosExistentes.get(0);
                    if (enderecosExistentes.size() > 1) {
                        log.warn(
                                "Múltiplos endereços encontrados ({}) para os mesmos campos. Usando o primeiro (ID: {})",
                                enderecosExistentes.size(), encontrado.getId());
                    } else {
                        log.info("Endereço existente encontrado. ID: {} - Reutilizando endereço existente",
                                encontrado.getId());
                    }
                    return encontrado;
                }
            }

            endereco.setLogradouro(logradouroNormalizado);
            endereco.setNumero(numeroNormalizado);
            endereco.setBairro(bairroNormalizado);
            endereco.setCep(cepNormalizado);

            if (endereco.getSemNumero() == null) {
                endereco.setSemNumero(false);
            }
            if (endereco.getActive() == null) {
                endereco.setActive(true);
            }

            Endereco enderecoSalvo = enderecoRepository.save(endereco);
            log.info("Novo endereço criado. ID: {}", enderecoSalvo.getId());
            return enderecoSalvo;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao buscar/criar endereço. Erro: {}", e.getMessage());
            throw e;
        } catch (IncorrectResultSizeDataAccessException e) {

            log.warn("Múltiplos endereços encontrados para os mesmos campos. Tentando buscar lista: {}",
                    e.getMessage());
            UUID cidadeId = endereco.getCidade() != null ? endereco.getCidade().getId() : null;
            UUID estadoId = endereco.getEstado() != null ? endereco.getEstado().getId() : null;
            String logradouroNormalizado = normalizarString(endereco.getLogradouro());
            String numeroNormalizado = normalizarNumero(endereco.getNumero());
            String bairroNormalizado = normalizarString(endereco.getBairro());
            String cepNormalizado = normalizarCep(endereco.getCep());

            String tipoLogradouroStr = endereco.getTipoLogradouro() != null
                    ? endereco.getTipoLogradouro().getCodigo().toString()
                    : null;
            String cidadeIdStr = cidadeId != null ? cidadeId.toString() : null;
            String estadoIdStr = estadoId != null ? estadoId.toString() : null;
            String tipoEnderecoStr = endereco.getTipoEndereco() != null
                    ? endereco.getTipoEndereco().getCodigo().toString()
                    : null;
            String zonaStr = endereco.getZona() != null ? endereco.getZona().getCodigo().toString() : null;

            List<Endereco> enderecosExistentes = enderecoRepository.findByFieldsList(
                    tipoLogradouroStr,
                    logradouroNormalizado,
                    numeroNormalizado,
                    bairroNormalizado,
                    cepNormalizado,
                    cidadeIdStr,
                    estadoIdStr,
                    tipoEnderecoStr,
                    zonaStr,
                    tenantService.validarTenantAtual().toString());

            if (!enderecosExistentes.isEmpty()) {
                Endereco encontrado = enderecosExistentes.get(0);
                log.info("Endereço existente encontrado após tratamento de múltiplos resultados. ID: {}",
                        encontrado.getId());
                return encontrado;
            }

            log.debug("Nenhum endereço encontrado após tratamento de exceção. Criando novo endereço.");

            endereco.setLogradouro(logradouroNormalizado);
            endereco.setNumero(numeroNormalizado);
            endereco.setBairro(bairroNormalizado);
            endereco.setCep(cepNormalizado);

            if (endereco.getSemNumero() == null) {
                endereco.setSemNumero(false);
            }
            if (endereco.getActive() == null) {
                endereco.setActive(true);
            }

            Endereco enderecoSalvo = enderecoRepository.save(endereco);
            log.info("Novo endereço criado após tratamento de exceção. ID: {}", enderecoSalvo.getId());
            return enderecoSalvo;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar/criar endereço", e);
            throw new InternalServerErrorException("Erro ao buscar/criar endereço", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao buscar/criar endereço", e);
            throw e;
        }
    }

    private String normalizarString(String str) {
        if (str == null) {
            return null;
        }
        return str.trim();
    }

    private String normalizarNumero(String numero) {
        if (numero == null) {
            return null;
        }
        return numero.trim().replaceAll("[^\\d]", "");
    }

    private String normalizarCep(String cep) {
        if (cep == null) {
            return null;
        }
        return cep.trim().replaceAll("[^\\d]", "");
    }

}
