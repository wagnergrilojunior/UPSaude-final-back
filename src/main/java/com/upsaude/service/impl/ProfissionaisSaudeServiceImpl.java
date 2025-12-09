package com.upsaude.service.impl;

import com.upsaude.api.request.EnderecoRequest;
import com.upsaude.api.request.ProfissionaisSaudeRequest;
import com.upsaude.api.response.ProfissionaisSaudeResponse;
import com.upsaude.entity.ConselhosProfissionais;
import com.upsaude.entity.EspecialidadesMedicas;
import com.upsaude.entity.Endereco;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.entity.Cidades;
import com.upsaude.entity.Estados;
import com.upsaude.mapper.EnderecoMapper;
import com.upsaude.mapper.ProfissionaisSaudeMapper;
import com.upsaude.repository.CidadesRepository;
import com.upsaude.repository.ConselhosProfissionaisRepository;
import com.upsaude.repository.EspecialidadesMedicasRepository;
import com.upsaude.repository.EstadosRepository;
import com.upsaude.repository.EnderecoRepository;
import com.upsaude.repository.ProfissionaisSaudeRepository;
import com.upsaude.repository.TenantRepository;
import com.upsaude.service.EnderecoService;
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
    private final ConselhosProfissionaisRepository conselhosProfissionaisRepository;
    private final EspecialidadesMedicasRepository especialidadesMedicasRepository;
    private final EnderecoRepository enderecoRepository;
    private final EnderecoService enderecoService;
    private final EnderecoMapper enderecoMapper;
    private final EstadosRepository estadosRepository;
    private final CidadesRepository cidadesRepository;
    private final TenantRepository tenantRepository;

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
            validarUnicidadeParaCriacao(request);

            ProfissionaisSaude profissional = profissionaisSaudeMapper.fromRequest(request);
            profissional.setActive(true);

            // Define o tenant (para testes, usa o primeiro tenant disponível)
            // Em produção, deve usar tenantService.obterTenantDoUsuarioAutenticado()
            Tenant tenant = tenantRepository.findAll().stream()
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException("Nenhum tenant encontrado no sistema"));
            profissional.setTenant(tenant);

            // Processa conselho profissional (obrigatório)
            if (request.getConselho() != null) {
                ConselhosProfissionais conselho = conselhosProfissionaisRepository.findById(request.getConselho())
                        .orElseThrow(() -> new NotFoundException("Conselho profissional não encontrado com ID: " + request.getConselho()));
                profissional.setConselho(conselho);
            }

            // Processa especialidades se fornecidas
            if (request.getEspecialidades() != null && !request.getEspecialidades().isEmpty()) {
                List<EspecialidadesMedicas> especialidades = new ArrayList<>();
                for (UUID especialidadeId : request.getEspecialidades()) {
                    EspecialidadesMedicas especialidade = especialidadesMedicasRepository.findById(especialidadeId)
                            .orElseThrow(() -> new NotFoundException("Especialidade médica não encontrada com ID: " + especialidadeId));
                    especialidades.add(especialidade);
                }
                profissional.setEspecialidades(especialidades);
            }

            // Processa endereço profissional usando findOrCreate para evitar duplicação
            processarEnderecoProfissional(request, profissional, tenant);

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

            // Valida unicidade dos campos únicos antes de atualizar
            validarUnicidadeParaAtualizacao(request, id);

            // Obtém o tenant do profissional existente
            Tenant tenant = profissionalExistente.getTenant();
            if (tenant == null) {
                // Se não tiver tenant, usa o primeiro disponível (para testes)
                tenant = tenantRepository.findAll().stream()
                        .findFirst()
                        .orElseThrow(() -> new NotFoundException("Nenhum tenant encontrado no sistema"));
            }

            // Usa mapper do MapStruct que preserva campos de controle automaticamente
            profissionaisSaudeMapper.updateFromRequest(request, profissionalExistente);

            // Processa conselho profissional se fornecido
            if (request.getConselho() != null) {
                ConselhosProfissionais conselho = conselhosProfissionaisRepository.findById(request.getConselho())
                        .orElseThrow(() -> new NotFoundException("Conselho profissional não encontrado com ID: " + request.getConselho()));
                profissionalExistente.setConselho(conselho);
            }

            // Processa especialidades se fornecidas
            if (request.getEspecialidades() != null) {
                List<EspecialidadesMedicas> especialidades = new ArrayList<>();
                for (UUID especialidadeId : request.getEspecialidades()) {
                    EspecialidadesMedicas especialidade = especialidadesMedicasRepository.findById(especialidadeId)
                            .orElseThrow(() -> new NotFoundException("Especialidade médica não encontrada com ID: " + especialidadeId));
                    especialidades.add(especialidade);
                }
                profissionalExistente.setEspecialidades(especialidades);
            }

            // Processa endereço profissional usando findOrCreate para evitar duplicação
            processarEnderecoProfissional(request, profissionalExistente, tenant);

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

    /**
     * Valida unicidade dos campos únicos para criação de novo profissional.
     * Verifica se já existe registro com CPF, email, RG ou CNS informados.
     *
     * @param request Request com os dados do profissional
     * @throws BadRequestException se algum campo único já estiver em uso
     */
    private void validarUnicidadeParaCriacao(ProfissionaisSaudeRequest request) {
        // Validar unicidade de CPF
        if (request.getCpf() != null && !request.getCpf().trim().isEmpty()) {
            if (profissionaisSaudeRepository.existsByCpf(request.getCpf())) {
                throw new BadRequestException(
                    String.format("Já existe um profissional cadastrado com o CPF '%s'", request.getCpf())
                );
            }
        }

        // Validar unicidade de email
        if (request.getEmail() != null && !request.getEmail().trim().isEmpty()) {
            if (profissionaisSaudeRepository.existsByEmail(request.getEmail())) {
                throw new BadRequestException(
                    String.format("Já existe um profissional cadastrado com o email '%s'", request.getEmail())
                );
            }
        }

        // Validar unicidade de RG
        if (request.getRg() != null && !request.getRg().trim().isEmpty()) {
            if (profissionaisSaudeRepository.existsByRg(request.getRg())) {
                throw new BadRequestException(
                    String.format("Já existe um profissional cadastrado com o RG '%s'", request.getRg())
                );
            }
        }

        // Validar unicidade de CNS
        if (request.getCns() != null && !request.getCns().trim().isEmpty()) {
            if (profissionaisSaudeRepository.existsByCns(request.getCns())) {
                throw new BadRequestException(
                    String.format("Já existe um profissional cadastrado com o CNS '%s'", request.getCns())
                );
            }
        }

        // Validar unicidade de registro profissional (registro + conselho + UF)
        if (request.getRegistroProfissional() != null && request.getConselho() != null && request.getUfRegistro() != null) {
            if (profissionaisSaudeRepository.existsByRegistroProfissionalAndConselhoIdAndUfRegistro(
                    request.getRegistroProfissional(), request.getConselho(), request.getUfRegistro())) {
                throw new BadRequestException(
                    String.format("Já existe um profissional cadastrado com o registro profissional '%s', conselho e UF '%s' informados",
                        request.getRegistroProfissional(), request.getUfRegistro())
                );
            }
        }
    }

    /**
     * Valida unicidade dos campos únicos para atualização de profissional existente.
     * Verifica se já existe outro registro (diferente do atual) com CPF, email, RG ou CNS informados.
     *
     * @param request Request com os dados do profissional
     * @param id ID do profissional sendo atualizado
     * @throws BadRequestException se algum campo único já estiver em uso por outro profissional
     */
    private void validarUnicidadeParaAtualizacao(ProfissionaisSaudeRequest request, UUID id) {
        // Validar unicidade de CPF (excluindo o próprio registro)
        if (request.getCpf() != null && !request.getCpf().trim().isEmpty()) {
            if (profissionaisSaudeRepository.existsByCpfAndIdNot(request.getCpf(), id)) {
                throw new BadRequestException(
                    String.format("Já existe outro profissional cadastrado com o CPF '%s'", request.getCpf())
                );
            }
        }

        // Validar unicidade de email (excluindo o próprio registro)
        if (request.getEmail() != null && !request.getEmail().trim().isEmpty()) {
            if (profissionaisSaudeRepository.existsByEmailAndIdNot(request.getEmail(), id)) {
                throw new BadRequestException(
                    String.format("Já existe outro profissional cadastrado com o email '%s'", request.getEmail())
                );
            }
        }

        // Validar unicidade de RG (excluindo o próprio registro)
        if (request.getRg() != null && !request.getRg().trim().isEmpty()) {
            if (profissionaisSaudeRepository.existsByRgAndIdNot(request.getRg(), id)) {
                throw new BadRequestException(
                    String.format("Já existe outro profissional cadastrado com o RG '%s'", request.getRg())
                );
            }
        }

        // Validar unicidade de CNS (excluindo o próprio registro)
        if (request.getCns() != null && !request.getCns().trim().isEmpty()) {
            if (profissionaisSaudeRepository.existsByCnsAndIdNot(request.getCns(), id)) {
                throw new BadRequestException(
                    String.format("Já existe outro profissional cadastrado com o CNS '%s'", request.getCns())
                );
            }
        }

        // Validar unicidade de registro profissional (registro + conselho + UF)
        // Para atualização, precisamos verificar se existe outro registro com a mesma combinação
        if (request.getRegistroProfissional() != null && request.getConselho() != null && request.getUfRegistro() != null) {
            profissionaisSaudeRepository.findByRegistroProfissionalAndConselhoIdAndUfRegistro(
                    request.getRegistroProfissional(), request.getConselho(), request.getUfRegistro())
                    .filter(p -> !p.getId().equals(id))
                    .ifPresent(p -> {
                        throw new BadRequestException(
                            String.format("Já existe outro profissional cadastrado com o registro profissional '%s', conselho e UF '%s' informados",
                                request.getRegistroProfissional(), request.getUfRegistro())
                        );
                    });
        }
    }

    /**
     * Processa o endereço profissional do profissional de saúde.
     * Se fornecido como objeto completo EnderecoRequest, usa findOrCreate para evitar duplicação.
     * Se fornecido como UUID, busca o endereço existente.
     * Se não fornecido, mantém o endereço existente (apenas em atualização).
     *
     * @param request request com dados do profissional
     * @param profissional profissional a ser atualizado
     * @param tenant tenant do profissional
     */
    private void processarEnderecoProfissional(ProfissionaisSaudeRequest request, ProfissionaisSaude profissional, Tenant tenant) {
        // Prioriza objeto completo sobre UUID
        if (request.getEnderecoProfissionalCompleto() != null) {
            log.debug("Processando endereço profissional como objeto completo. Usando findOrCreate para evitar duplicação");

            // Converte EnderecoRequest para Endereco
            Endereco endereco = enderecoMapper.fromRequest(request.getEnderecoProfissionalCompleto());
            endereco.setActive(true);
            endereco.setTenant(tenant);

            // Garante valores padrão
            if (endereco.getSemNumero() == null) {
                endereco.setSemNumero(false);
            }

            // Processa relacionamentos estado e cidade
            if (request.getEnderecoProfissionalCompleto().getEstado() != null) {
                Estados estado = estadosRepository.findById(request.getEnderecoProfissionalCompleto().getEstado())
                        .orElseThrow(() -> new NotFoundException("Estado não encontrado com ID: " + request.getEnderecoProfissionalCompleto().getEstado()));
                endereco.setEstado(estado);
            }

            if (request.getEnderecoProfissionalCompleto().getCidade() != null) {
                Cidades cidade = cidadesRepository.findById(request.getEnderecoProfissionalCompleto().getCidade())
                        .orElseThrow(() -> new NotFoundException("Cidade não encontrada com ID: " + request.getEnderecoProfissionalCompleto().getCidade()));
                endereco.setCidade(cidade);
            }

            // Usa findOrCreate para evitar duplicação
            // Salva o ID antes de chamar findOrCreate para verificar se foi criado novo ou encontrado existente
            UUID idAntes = endereco.getId();
            Endereco enderecoProcessado = enderecoService.findOrCreate(endereco);
            profissional.setEnderecoProfissional(enderecoProcessado);

            // Verifica se foi criado novo endereço ou reutilizado existente
            boolean foiCriadoNovo = idAntes == null && enderecoProcessado.getId() != null;
            log.info("Endereço profissional processado. ID: {} - {}",
                    enderecoProcessado.getId(),
                    foiCriadoNovo ? "Novo endereço criado" : "Endereço existente reutilizado");

        } else if (request.getEnderecoProfissional() != null) {
            // Se fornecido apenas UUID, busca endereço existente
            log.debug("Processando endereço profissional como UUID: {}", request.getEnderecoProfissional());
            Endereco enderecoProfissional = enderecoRepository.findById(request.getEnderecoProfissional())
                    .orElseThrow(() -> new NotFoundException("Endereço profissional não encontrado com ID: " + request.getEnderecoProfissional()));
            profissional.setEnderecoProfissional(enderecoProfissional);
        } else {
            // Se não fornecido, mantém o existente (apenas em atualização)
            log.debug("Endereço profissional não fornecido. Mantendo endereço existente.");
        }
    }
}

