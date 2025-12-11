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
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
        log.debug("Criando novo FabricantesVacina");

        if (request == null) {
            log.warn("Request nulo recebido para criação de FabricantesVacina");
            throw new BadRequestException("Dados do fabricante de vacina são obrigatórios");
        }

        validarDuplicidade(null, request);

        FabricantesVacina fabricantesVacina = fabricantesVacinaMapper.fromRequest(request);
        fabricantesVacina.setActive(true);

        if (request.getEndereco() != null) {
            Endereco endereco = processarEndereco(request.getEndereco());
            fabricantesVacina.setEndereco(endereco);
        }

        FabricantesVacina fabricantesVacinaSalvo = fabricantesVacinaRepository.save(fabricantesVacina);
        log.info("FabricantesVacina criado com sucesso. ID: {}", fabricantesVacinaSalvo.getId());

        return fabricantesVacinaMapper.toResponse(fabricantesVacinaSalvo);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "fabricantesvacina", key = "#id")
    public FabricantesVacinaResponse obterPorId(UUID id) {
        log.debug("Buscando FabricantesVacina por ID: {} (cache miss)", id);

        if (id == null) {
            log.warn("ID nulo recebido para busca de FabricantesVacina");
            throw new BadRequestException("ID do fabricante de vacina é obrigatório");
        }

        FabricantesVacina fabricantesVacina = fabricantesVacinaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("FabricantesVacina não encontrado com ID: " + id));

        log.debug("FabricantesVacina encontrado. ID: {}", id);
        return fabricantesVacinaMapper.toResponse(fabricantesVacina);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FabricantesVacinaResponse> listar(Pageable pageable) {
        log.debug("Listando FabricantesVacinas paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<FabricantesVacina> fabricantesVacinas = fabricantesVacinaRepository.findAll(pageable);
        log.debug("Listagem de FabricantesVacinas concluída. Total de elementos: {}", fabricantesVacinas.getTotalElements());
        return fabricantesVacinas.map(fabricantesVacinaMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "fabricantesvacina", key = "#id")
    public FabricantesVacinaResponse atualizar(UUID id, FabricantesVacinaRequest request) {
        log.debug("Atualizando FabricantesVacina. ID: {}", id);

        if (id == null) {
            log.warn("ID nulo recebido para atualização de FabricantesVacina");
            throw new BadRequestException("ID do fabricante de vacina é obrigatório");
        }

        if (request == null) {
            log.warn("Request nulo recebido para atualização de FabricantesVacina. ID: {}", id);
            throw new BadRequestException("Dados do fabricante de vacina são obrigatórios");
        }

        FabricantesVacina fabricantesVacinaExistente = fabricantesVacinaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("FabricantesVacina não encontrado com ID: " + id));

        validarDuplicidade(id, request);

        atualizarDadosFabricantesVacina(fabricantesVacinaExistente, request);

        if (request.getEndereco() != null) {
            Endereco endereco = processarEndereco(request.getEndereco());
            fabricantesVacinaExistente.setEndereco(endereco);
        }

        FabricantesVacina fabricantesVacinaAtualizado = fabricantesVacinaRepository.save(fabricantesVacinaExistente);
        log.info("FabricantesVacina atualizado com sucesso. ID: {}", fabricantesVacinaAtualizado.getId());

        return fabricantesVacinaMapper.toResponse(fabricantesVacinaAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "fabricantesvacina", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo FabricantesVacina. ID: {}", id);

        if (id == null) {
            log.warn("ID nulo recebido para exclusão de FabricantesVacina");
            throw new BadRequestException("ID do fabricante de vacina é obrigatório");
        }

        FabricantesVacina fabricantesVacina = fabricantesVacinaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("FabricantesVacina não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(fabricantesVacina.getActive())) {
            log.warn("Tentativa de excluir FabricantesVacina já inativo. ID: {}", id);
            throw new BadRequestException("FabricantesVacina já está inativo");
        }

        fabricantesVacina.setActive(false);
        fabricantesVacinaRepository.save(fabricantesVacina);
        log.info("FabricantesVacina excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDuplicidade(UUID id, FabricantesVacinaRequest request) {
        if (request == null) {
            return;
        }

        if (request.getNome() != null && !request.getNome().trim().isEmpty()) {
            boolean nomeDuplicado;
            if (id == null) {

                nomeDuplicado = fabricantesVacinaRepository.existsByNome(request.getNome().trim());
            } else {

                nomeDuplicado = fabricantesVacinaRepository.existsByNomeAndIdNot(request.getNome().trim(), id);
            }

            if (nomeDuplicado) {
                log.warn("Tentativa de cadastrar/atualizar fabricante de vacina com nome duplicado. Nome: {}", request.getNome());
                throw new BadRequestException(
                    String.format("Já existe um fabricante de vacina cadastrado com o nome '%s' no banco de dados", request.getNome())
                );
            }
        }

        if (request.getCnpj() != null && !request.getCnpj().trim().isEmpty()) {
            boolean cnpjDuplicado;
            if (id == null) {

                cnpjDuplicado = fabricantesVacinaRepository.existsByCnpj(request.getCnpj().trim());
            } else {

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

        fabricantesVacinaMapper.updateFromRequest(request, fabricantesVacina);
    }

    private Endereco processarEndereco(com.upsaude.api.request.EnderecoRequest enderecoRequest) {
        if (enderecoRequest == null) {
            return null;
        }

        try {

            Endereco endereco = enderecoMapper.fromRequest(enderecoRequest);
            endereco.setActive(true);

            if (endereco.getSemNumero() == null) {
                endereco.setSemNumero(false);
            }

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

            String logradouroNormalizado = normalizarString(endereco.getLogradouro());
            String numeroNormalizado = normalizarNumero(endereco.getNumero());
            String bairroNormalizado = normalizarString(endereco.getBairro());
            String cepNormalizado = normalizarCep(endereco.getCep());

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
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao processar endereço. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao processar endereço", e);
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
