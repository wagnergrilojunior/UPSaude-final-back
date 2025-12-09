package com.upsaude.service.impl;

import com.upsaude.api.request.FabricantesVacinaRequest;
import com.upsaude.api.response.FabricantesVacinaResponse;
import com.upsaude.entity.FabricantesVacina;
import com.upsaude.entity.Endereco;
import com.upsaude.entity.Cidades;
import com.upsaude.entity.Estados;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.FabricantesVacinaMapper;
import com.upsaude.mapper.EnderecoMapper;
import com.upsaude.repository.FabricantesVacinaRepository;
import com.upsaude.repository.EnderecoRepository;
import com.upsaude.repository.CidadesRepository;
import com.upsaude.repository.EstadosRepository;
import com.upsaude.service.FabricantesVacinaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;
    private final EstadosRepository estadosRepository;
    private final CidadesRepository cidadesRepository;

    @Override
    @Transactional
    @CacheEvict(value = "fabricantesvacina", allEntries = true)
    public FabricantesVacinaResponse criar(FabricantesVacinaRequest request) {
        log.debug("Criando novo fabricantesvacina");

        validarDadosBasicos(request);
        validarDuplicidade(null, request);

        FabricantesVacina fabricantesVacina = fabricantesVacinaMapper.fromRequest(request);
        fabricantesVacina.setActive(true);

        // Processa endereço se fornecido usando findByFields para evitar duplicação
        if (request.getEndereco() != null) {
            Endereco endereco = processarEndereco(request.getEndereco());
            fabricantesVacina.setEndereco(endereco);
        }

        FabricantesVacina fabricantesVacinaSalvo = fabricantesVacinaRepository.save(fabricantesVacina);
        log.info("FabricantesVacina criado com sucesso. ID: {}", fabricantesVacinaSalvo.getId());

        return fabricantesVacinaMapper.toResponse(fabricantesVacinaSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "fabricantesvacina", key = "#id")
    public FabricantesVacinaResponse obterPorId(UUID id) {
        log.debug("Buscando fabricantesvacina por ID: {} (cache miss)", id);
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
    @CacheEvict(value = "fabricantesvacina", key = "#id")
    public FabricantesVacinaResponse atualizar(UUID id, FabricantesVacinaRequest request) {
        log.debug("Atualizando fabricantesvacina. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do fabricantesvacina é obrigatório");
        }

        validarDadosBasicos(request);

        FabricantesVacina fabricantesVacinaExistente = fabricantesVacinaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("FabricantesVacina não encontrado com ID: " + id));

        validarDuplicidade(id, request);

        atualizarDadosFabricantesVacina(fabricantesVacinaExistente, request);

        // Processa endereço se fornecido usando findByFields para evitar duplicação
        if (request.getEndereco() != null) {
            Endereco endereco = processarEndereco(request.getEndereco());
            fabricantesVacinaExistente.setEndereco(endereco);
        }
        // Se endereço não foi fornecido, mantém o existente (não remove)

        FabricantesVacina fabricantesVacinaAtualizado = fabricantesVacinaRepository.save(fabricantesVacinaExistente);
        log.info("FabricantesVacina atualizado com sucesso. ID: {}", fabricantesVacinaAtualizado.getId());

        return fabricantesVacinaMapper.toResponse(fabricantesVacinaAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "fabricantesvacina", key = "#id")
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

    /**
     * Valida se já existe um fabricante de vacina com o mesmo nome ou CNPJ no banco de dados.
     * 
     * @param id ID do fabricante de vacina sendo atualizado (null para criação)
     * @param request dados do fabricante de vacina sendo cadastrado/atualizado
     * @throws BadRequestException se já existe um fabricante de vacina com o mesmo nome ou CNPJ
     */
    private void validarDuplicidade(UUID id, FabricantesVacinaRequest request) {
        if (request == null) {
            return;
        }

        // Valida duplicidade do nome
        if (request.getNome() != null && !request.getNome().trim().isEmpty()) {
            boolean nomeDuplicado;
            if (id == null) {
                // Criação: verifica se existe qualquer registro com este nome
                nomeDuplicado = fabricantesVacinaRepository.existsByNome(request.getNome().trim());
            } else {
                // Atualização: verifica se existe outro registro (diferente do atual) com este nome
                nomeDuplicado = fabricantesVacinaRepository.existsByNomeAndIdNot(request.getNome().trim(), id);
            }

            if (nomeDuplicado) {
                log.warn("Tentativa de cadastrar/atualizar fabricante de vacina com nome duplicado. Nome: {}", request.getNome());
                throw new BadRequestException(
                    String.format("Já existe um fabricante de vacina cadastrado com o nome '%s' no banco de dados", request.getNome())
                );
            }
        }

        // Valida duplicidade do CNPJ (apenas se fornecido)
        if (request.getCnpj() != null && !request.getCnpj().trim().isEmpty()) {
            boolean cnpjDuplicado;
            if (id == null) {
                // Criação: verifica se existe qualquer registro com este CNPJ
                cnpjDuplicado = fabricantesVacinaRepository.existsByCnpj(request.getCnpj().trim());
            } else {
                // Atualização: verifica se existe outro registro (diferente do atual) com este CNPJ
                cnpjDuplicado = fabricantesVacinaRepository.existsByCnpjAndIdNot(request.getCnpj().trim(), id);
            }

            if (cnpjDuplicado) {
                log.warn("Tentativa de cadastrar/atualizar fabricante de vacina com CNPJ duplicado. CNPJ: {}", request.getCnpj());
                throw new BadRequestException(
                    String.format("Já existe um fabricante de vacina cadastrado com o CNPJ '%s' no banco de dados", request.getCnpj())
                );
            }
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

    /**
     * Processa o endereço usando findByFields para evitar duplicação.
     * Se já existir um endereço com os mesmos campos, reutiliza o existente.
     * Caso contrário, cria um novo endereço.
     *
     * @param enderecoRequest dados do endereço a ser processado
     * @return endereço existente ou novo endereço criado
     */
    private Endereco processarEndereco(com.upsaude.api.request.EnderecoRequest enderecoRequest) {
        if (enderecoRequest == null) {
            return null;
        }

        try {
            // Converte EnderecoRequest para Endereco usando mapper
            Endereco endereco = enderecoMapper.fromRequest(enderecoRequest);
            endereco.setActive(true);

            // Garante valores padrão para campos obrigatórios
            if (endereco.getSemNumero() == null) {
                endereco.setSemNumero(false);
            }

            // Processa relacionamentos estado e cidade
            if (enderecoRequest.getEstado() != null) {
                Estados estado = estadosRepository.findById(enderecoRequest.getEstado())
                        .orElseThrow(() -> new NotFoundException("Estado não encontrado com ID: " + enderecoRequest.getEstado()));
                endereco.setEstado(estado);
            }

            if (enderecoRequest.getCidade() != null) {
                Cidades cidade = cidadesRepository.findById(enderecoRequest.getCidade())
                        .orElseThrow(() -> new NotFoundException("Cidade não encontrada com ID: " + enderecoRequest.getCidade()));
                endereco.setCidade(cidade);
            }

            // Normaliza dados antes de buscar
            String logradouroNormalizado = normalizarString(endereco.getLogradouro());
            String numeroNormalizado = normalizarNumero(endereco.getNumero());
            String bairroNormalizado = normalizarString(endereco.getBairro());
            String cepNormalizado = normalizarCep(endereco.getCep());

            // Busca endereço existente pelos campos principais usando findByFields
            UUID cidadeId = endereco.getCidade() != null ? endereco.getCidade().getId() : null;
            UUID estadoId = endereco.getEstado() != null ? endereco.getEstado().getId() : null;

            java.util.Optional<Endereco> enderecoExistente = enderecoRepository.findByFields(
                    endereco.getTipoLogradouro(),
                    logradouroNormalizado,
                    numeroNormalizado,
                    bairroNormalizado,
                    cepNormalizado,
                    cidadeId,
                    estadoId
            );

            if (enderecoExistente.isPresent()) {
                Endereco encontrado = enderecoExistente.get();
                log.info("Endereço existente encontrado e reutilizado. ID: {}", encontrado.getId());
                return encontrado;
            }

            // Não encontrou endereço existente, cria novo
            // Aplica normalizações no endereço antes de salvar
            endereco.setLogradouro(logradouroNormalizado);
            endereco.setNumero(numeroNormalizado);
            endereco.setBairro(bairroNormalizado);
            endereco.setCep(cepNormalizado);

            Endereco enderecoSalvo = enderecoRepository.save(endereco);
            log.info("Novo endereço criado. ID: {}", enderecoSalvo.getId());
            return enderecoSalvo;
        } catch (NotFoundException e) {
            log.warn("Erro ao processar endereço. Erro: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao processar endereço", e);
            throw new InternalServerErrorException("Erro ao processar endereço", e);
        }
    }

    /**
     * Normaliza string removendo espaços extras.
     *
     * @param str string a ser normalizada
     * @return string normalizada ou null se entrada for null
     */
    private String normalizarString(String str) {
        if (str == null) {
            return null;
        }
        return str.trim();
    }

    /**
     * Normaliza número de endereço removendo caracteres especiais e espaços.
     *
     * @param numero número a ser normalizado
     * @return número normalizado ou null se entrada for null
     */
    private String normalizarNumero(String numero) {
        if (numero == null) {
            return null;
        }
        return numero.trim().replaceAll("[^\\d]", "");
    }

    /**
     * Normaliza CEP removendo caracteres especiais e espaços.
     *
     * @param cep CEP a ser normalizado
     * @return CEP normalizado ou null se entrada for null
     */
    private String normalizarCep(String cep) {
        if (cep == null) {
            return null;
        }
        return cep.trim().replaceAll("[^\\d]", "");
    }
}
