package com.upsaude.service.impl;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.upsaude.api.request.MedicosRequest;
import com.upsaude.api.response.MedicosResponse;
import com.upsaude.entity.EspecialidadesMedicas;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.MedicoEstabelecimento;
import com.upsaude.entity.Medicos;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.TipoVinculoProfissionalEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.EnderecoMapper;
import com.upsaude.mapper.MedicosMapper;
import com.upsaude.repository.CidadesRepository;
import com.upsaude.repository.EspecialidadesMedicasRepository;
import com.upsaude.repository.EstabelecimentosRepository;
import com.upsaude.repository.EstadosRepository;
import com.upsaude.repository.MedicoEstabelecimentoRepository;
import com.upsaude.repository.MedicosRepository;
import com.upsaude.service.EnderecoService;
import com.upsaude.service.MedicosService;
import com.upsaude.service.TenantService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicosServiceImpl implements MedicosService {

    private final MedicosRepository medicosRepository;
    private final MedicosMapper medicosMapper;
    private final EspecialidadesMedicasRepository especialidadesMedicasRepository;
    private final EstabelecimentosRepository estabelecimentosRepository;
    private final MedicoEstabelecimentoRepository medicoEstabelecimentoRepository;
    private final TenantService tenantService;
    private final EnderecoService enderecoService;
    private final EnderecoMapper enderecoMapper;
    private final EstadosRepository estadosRepository;
    private final CidadesRepository cidadesRepository;

    @Override
    @Transactional
    @CacheEvict(value = "medicos", allEntries = true)
    public MedicosResponse criar(MedicosRequest request) {
        log.debug("Criando novo médico. Request: {}", request);

        if (request == null) {
            log.warn("Tentativa de criar médico com request nulo");
            throw new BadRequestException("Dados do médico são obrigatórios");
        }

        try {
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

            if (tenant == null) {
                throw new BadRequestException("Não foi possível obter tenant do usuário autenticado. É necessário estar autenticado para criar médicos.");
            }

            validarCrmUnicoPorTenant(null, request, tenant);
            validarCpfUnicoPorTenant(null, request, tenant);

            Medicos medicos = medicosMapper.fromRequest(request);
            medicos.setActive(true);
            medicos.setTenant(tenant);

            if (medicos.getDadosPessoais() == null) {
                medicos.setDadosPessoais(new com.upsaude.entity.embeddable.DadosPessoaisMedico());
            }
            if (medicos.getRegistroProfissional() == null) {
                medicos.setRegistroProfissional(new com.upsaude.entity.embeddable.RegistroProfissionalMedico());
            }
            if (medicos.getFormacao() == null) {
                medicos.setFormacao(new com.upsaude.entity.embeddable.FormacaoMedico());
            }
            if (medicos.getContato() == null) {
                medicos.setContato(new com.upsaude.entity.embeddable.ContatoMedico());
            }

            if (medicos.getRegistroProfissional() != null) {
                if (!StringUtils.hasText(medicos.getRegistroProfissional().getCrm())) {
                    medicos.getRegistroProfissional().setCrm(null);
                }
                if (!StringUtils.hasText(medicos.getRegistroProfissional().getCrmUf())) {
                    medicos.getRegistroProfissional().setCrmUf(null);
                }
            }

            if (medicos.getFormacao() != null && medicos.getFormacao().getTituloEspecialista() == null) {
                medicos.getFormacao().setTituloEspecialista(false);
            }

            processarRelacionamentos(medicos, request);

            Medicos medicosSalvo = medicosRepository.save(medicos);
            log.info("Médico criado com sucesso. ID: {}", medicosSalvo.getId());

            return medicosMapper.toResponse(medicosSalvo);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao criar Medico. Erro: {}", e.getMessage());
            throw e;
        } catch (DataIntegrityViolationException e) {
            log.warn("Erro de integridade de dados ao criar Medico. Exception: {}", e.getClass().getSimpleName(), e);
            throw new BadRequestException("Erro de integridade de dados: " + e.getMessage(), e);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar Medico. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao persistir Medico", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar Medico. Exception: {}", e.getClass().getSimpleName(), e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "medicos", key = "#id")
    public MedicosResponse obterPorId(UUID id) {
        log.debug("Buscando médico por ID: {} (cache miss)", id);

        if (id == null) {
            log.warn("ID nulo recebido para busca de médico");
            throw new BadRequestException("ID do médico é obrigatório");
        }

        try {
            Medicos medicos = medicosRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Médico não encontrado com ID: " + id));

            log.debug("Médico encontrado. ID: {}", id);
            return medicosMapper.toResponse(medicos);
        } catch (NotFoundException e) {
            log.warn("Medico não encontrado. ID: {}", id);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar Medico. ID: {}, Exception: {}", id, e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao buscar Medico", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao buscar Medico. ID: {}, Exception: {}", id, e.getClass().getSimpleName(), e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicosResponse> listar(Pageable pageable) {
        log.debug("Listando médicos paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            Page<Medicos> medicos = medicosRepository.findAll(pageable);
            log.debug("Listagem de médicos concluída. Total de elementos: {}", medicos.getTotalElements());
            return medicos.map(medicosMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar Medicos. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao listar Medicos", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar Medicos. Exception: {}", e.getClass().getSimpleName(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "medicos", key = "#id")
    public MedicosResponse atualizar(UUID id, MedicosRequest request) {
        log.debug("Atualizando médico. ID: {}, Request: {}", id, request);

        if (id == null) {
            log.warn("ID nulo recebido para atualização de médico");
            throw new BadRequestException("ID do médico é obrigatório");
        }
        if (request == null) {
            log.warn("Request nulo recebido para atualização de médico. ID: {}", id);
            throw new BadRequestException("Dados do médico são obrigatórios");
        }

        try {
            Medicos medicosExistente = medicosRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Médico não encontrado com ID: " + id));

            Tenant tenant = medicosExistente.getTenant();
            if (tenant == null) {
                throw new BadRequestException("Médico não possui tenant associado. Não é possível atualizar.");
            }

            validarCrmUnicoPorTenant(id, request, tenant);
            validarCpfUnicoPorTenant(id, request, tenant);

            medicosMapper.updateFromRequest(request, medicosExistente);

            if (medicosExistente.getRegistroProfissional() != null) {
                if (!StringUtils.hasText(medicosExistente.getRegistroProfissional().getCrm())) {
                    medicosExistente.getRegistroProfissional().setCrm(null);
                }
                if (!StringUtils.hasText(medicosExistente.getRegistroProfissional().getCrmUf())) {
                    medicosExistente.getRegistroProfissional().setCrmUf(null);
                }
            }

            processarRelacionamentos(medicosExistente, request);

            Medicos medicosAtualizado = medicosRepository.save(medicosExistente);
            log.info("Médico atualizado com sucesso. ID: {}", medicosAtualizado.getId());

            return medicosMapper.toResponse(medicosAtualizado);
        } catch (NotFoundException e) {
            log.warn("Tentativa de atualizar Medico não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao atualizar Medico. ID: {}, Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar Medico. ID: {}, Exception: {}", id, e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao atualizar Medico", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao atualizar Medico. ID: {}, Exception: {}", id, e.getClass().getSimpleName(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "medicos", key = "#id")
    public void excluir(UUID id) {
        inativar(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "medicos", key = "#id")
    public void inativar(UUID id) {
        log.debug("Inativando médico. ID: {}", id);

        if (id == null) {
            log.warn("ID nulo recebido para inativação de médico");
            throw new BadRequestException("ID do médico é obrigatório");
        }

        try {
            Medicos medicos = medicosRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Médico não encontrado com ID: " + id));

            if (Boolean.FALSE.equals(medicos.getActive())) {
                log.warn("Tentativa de inativar médico já inativo. ID: {}", id);
                throw new BadRequestException("Médico já está inativo");
            }

            medicos.setActive(false);
            medicosRepository.save(medicos);
            log.info("Médico inativado com sucesso. ID: {}", id);
        } catch (NotFoundException e) {
            log.warn("Tentativa de inativar médico não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao inativar médico. ID: {}, Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao inativar médico. ID: {}, Exception: {}", id, e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao inativar médico", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao inativar médico. ID: {}, Exception: {}", id, e.getClass().getSimpleName(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "medicos", key = "#id")
    public void deletarPermanentemente(UUID id) {
        log.debug("Deletando médico permanentemente. ID: {}", id);

        if (id == null) {
            log.warn("ID nulo recebido para exclusão permanente de médico");
            throw new BadRequestException("ID do médico é obrigatório");
        }

        try {
            Medicos medicos = medicosRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Médico não encontrado com ID: " + id));

            medicosRepository.delete(medicos);
            log.info("Médico deletado permanentemente do banco de dados. ID: {}", id);
        } catch (NotFoundException e) {
            log.warn("Tentativa de deletar médico não existente. ID: {}", id);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao deletar médico permanentemente. ID: {}, Exception: {}", id, e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao deletar médico permanentemente", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao deletar médico permanentemente. ID: {}, Exception: {}", id, e.getClass().getSimpleName(), e);
            throw e;
        }
    }

    private void validarCrmUnicoPorTenant(UUID medicoId, MedicosRequest request, Tenant tenant) {
        if (request == null || request.getRegistroProfissional() == null) {
            return;
        }

        String crm = request.getRegistroProfissional().getCrm();
        String crmUf = request.getRegistroProfissional().getCrmUf();

        if (!StringUtils.hasText(crm) || !StringUtils.hasText(crmUf)) {
            return;
        }

        Optional<Medicos> medicoExistente = medicosRepository
                .findByRegistroProfissionalCrmAndRegistroProfissionalCrmUfAndTenant(crm, crmUf, tenant);

        if (medicoExistente.isPresent()) {
            Medicos medicoEncontrado = medicoExistente.get();

            if (medicoId != null && !medicoEncontrado.getId().equals(medicoId)) {
                throw new BadRequestException(
                        String.format("Já existe um médico cadastrado com CRM %s/%s neste tenant.", crm, crmUf));
            }

            if (medicoId == null) {
                throw new BadRequestException(
                        String.format("Já existe um médico cadastrado com CRM %s/%s neste tenant.", crm, crmUf));
            }
        }
    }

    private void validarCpfUnicoPorTenant(UUID medicoId, MedicosRequest request, Tenant tenant) {
        if (request == null || request.getDadosPessoais() == null) {
            return;
        }

        String cpf = request.getDadosPessoais().getCpf();

        if (!StringUtils.hasText(cpf)) {
            return;
        }

        Optional<Medicos> medicoExistente = medicosRepository.findByDadosPessoaisCpfAndTenant(cpf, tenant);

        if (medicoExistente.isPresent()) {
            Medicos medicoEncontrado = medicoExistente.get();

            if (medicoId != null && !medicoEncontrado.getId().equals(medicoId)) {
                throw new BadRequestException(
                        String.format("Já existe um médico cadastrado com CPF %s neste tenant.", cpf));
            }

            if (medicoId == null) {
                throw new BadRequestException(
                        String.format("Já existe um médico cadastrado com CPF %s neste tenant.", cpf));
            }
        }
    }

    private void processarRelacionamentos(Medicos medicos, MedicosRequest request) {
        log.debug("Processando relacionamentos do médico");

        if (request.getEspecialidades() != null && !request.getEspecialidades().isEmpty()) {
            log.debug("Processando {} especialidade(s) para o médico", request.getEspecialidades().size());
            List<EspecialidadesMedicas> especialidades = new ArrayList<>();

            Set<UUID> especialidadesIdsUnicos = new LinkedHashSet<>(request.getEspecialidades());

            if (especialidadesIdsUnicos.size() != request.getEspecialidades().size()) {
                log.warn("Lista de especialidades contém IDs duplicados. Removendo duplicatas.");
            }

            for (UUID especialidadeId : especialidadesIdsUnicos) {
                if (especialidadeId == null) {
                    log.warn("ID de especialidade nulo encontrado na lista. Ignorando.");
                    continue;
                }

                EspecialidadesMedicas especialidade = especialidadesMedicasRepository
                        .findById(especialidadeId)
                        .orElseThrow(() -> new NotFoundException(
                                "Especialidade médica não encontrada com ID: " + especialidadeId));
                especialidades.add(especialidade);
                log.debug("Especialidade {} associada ao médico", especialidadeId);
            }

            medicos.setEspecialidades(especialidades);
            log.debug("{} especialidade(s) associada(s) ao médico com sucesso", especialidades.size());
        } else {

            medicos.setEspecialidades(new ArrayList<>());
            log.debug("Nenhuma especialidade fornecida. Lista de especialidades será limpa.");
        }

        if (request.getEstabelecimentos() != null && !request.getEstabelecimentos().isEmpty()) {
            log.debug("Processando {} estabelecimento(s) para o médico", request.getEstabelecimentos().size());

            Set<UUID> estabelecimentosIdsUnicos = new LinkedHashSet<>(request.getEstabelecimentos());

            if (estabelecimentosIdsUnicos.size() != request.getEstabelecimentos().size()) {
                log.warn("Lista de estabelecimentos contém IDs duplicados. Removendo duplicatas.");
            }

            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            if (tenant == null) {
                throw new BadRequestException("Não foi possível obter tenant do usuário autenticado. É necessário estar autenticado para criar vínculos com estabelecimentos.");
            }

            Set<UUID> estabelecimentosParaManter = new LinkedHashSet<>(estabelecimentosIdsUnicos);

            medicos.getMedicosEstabelecimentos().removeIf(vinculo ->
                !estabelecimentosParaManter.contains(vinculo.getEstabelecimento().getId())
            );

            for (UUID estabelecimentoId : estabelecimentosIdsUnicos) {
                if (estabelecimentoId == null) {
                    log.warn("ID de estabelecimento nulo encontrado na lista. Ignorando.");
                    continue;
                }

                Estabelecimentos estabelecimento = estabelecimentosRepository
                        .findById(estabelecimentoId)
                        .orElseThrow(() -> new NotFoundException(
                                "Estabelecimento não encontrado com ID: " + estabelecimentoId));

                MedicoEstabelecimento medicoEstabelecimento = null;

                for (MedicoEstabelecimento vinculoExistente : medicos.getMedicosEstabelecimentos()) {
                    if (vinculoExistente.getEstabelecimento().getId().equals(estabelecimentoId)) {
                        medicoEstabelecimento = vinculoExistente;
                        log.debug("Vínculo existente encontrado para estabelecimento {}", estabelecimentoId);
                        break;
                    }
                }

                if (medicoEstabelecimento == null && medicos.getId() != null) {
                    Optional<MedicoEstabelecimento> vinculoBanco = medicoEstabelecimentoRepository
                            .findByMedicoIdAndEstabelecimentoId(medicos.getId(), estabelecimentoId);

                    if (vinculoBanco.isPresent()) {
                        medicoEstabelecimento = vinculoBanco.get();

                        if (!medicos.getMedicosEstabelecimentos().contains(medicoEstabelecimento)) {
                            medicos.getMedicosEstabelecimentos().add(medicoEstabelecimento);
                        }
                        log.debug("Vínculo encontrado no banco para estabelecimento {}", estabelecimentoId);
                    }
                }

                if (medicoEstabelecimento == null) {
                    medicoEstabelecimento = new MedicoEstabelecimento();
                    medicoEstabelecimento.setMedico(medicos);
                    medicoEstabelecimento.setEstabelecimento(estabelecimento);
                    medicoEstabelecimento.setTenant(tenant);
                    medicoEstabelecimento.setActive(true);
                    medicoEstabelecimento.setDataInicio(OffsetDateTime.now());
                    medicoEstabelecimento.setTipoVinculo(TipoVinculoProfissionalEnum.CONTRATO);

                    medicos.getMedicosEstabelecimentos().add(medicoEstabelecimento);
                    log.debug("Novo vínculo criado para estabelecimento {}", estabelecimentoId);
                } else {

                    medicoEstabelecimento.setActive(true);
                    if (medicoEstabelecimento.getDataFim() != null) {

                        medicoEstabelecimento.setDataFim(null);
                        log.debug("Vínculo reativado para estabelecimento {}", estabelecimentoId);
                    }
                }
            }

            log.debug("{} estabelecimento(s) vinculado(s) ao médico com sucesso", medicos.getMedicosEstabelecimentos().size());
        } else {

            medicos.getMedicosEstabelecimentos().clear();
            log.debug("Nenhum estabelecimento fornecido. Lista de vínculos será limpa.");
        }

        log.debug("Relacionamentos processados. JPA gerenciará persistência automaticamente.");
    }
}
