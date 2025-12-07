package com.upsaude.service.impl;

import com.upsaude.api.request.MedicacaoPacienteRequest;
import com.upsaude.api.request.MedicacaoPacienteSimplificadoRequest;
import com.upsaude.api.response.MedicacaoPacienteResponse;
import com.upsaude.entity.MedicacaoPaciente;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.Medicacao;
import com.upsaude.entity.CidDoencas;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.MedicacaoPacienteMapper;
import com.upsaude.repository.MedicacaoPacienteRepository;
import com.upsaude.repository.PacienteRepository;
import com.upsaude.repository.MedicacaoRepository;
import com.upsaude.repository.CidDoencasRepository;
import com.upsaude.repository.TenantRepository;
import com.upsaude.repository.UsuariosSistemaRepository;
import com.upsaude.service.MedicacaoPacienteService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Medicações de Paciente.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MedicacaoPacienteServiceImpl implements MedicacaoPacienteService {

    private final MedicacaoPacienteRepository medicacaoPacienteRepository;
    private final MedicacaoPacienteMapper medicacaoPacienteMapper;
    private final PacienteRepository pacienteRepository;
    private final MedicacaoRepository medicacaoRepository;
    private final CidDoencasRepository cidDoencasRepository;
    private final TenantRepository tenantRepository;
    private final UsuariosSistemaRepository usuariosSistemaRepository;

    @Override
    @Transactional
    @CacheEvict(value = "medicacaopaciente", allEntries = true)
    public MedicacaoPacienteResponse criar(MedicacaoPacienteRequest request) {
        log.debug("Criando nova ligação paciente-medicação");

        validarDadosBasicos(request);

        MedicacaoPaciente medicacaoPaciente = medicacaoPacienteMapper.fromRequest(request);
        
        // Carrega e define as entidades relacionadas
        Paciente paciente = pacienteRepository.findById(request.getPaciente())
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPaciente()));
        medicacaoPaciente.setPaciente(paciente);

        Medicacao medicacao = medicacaoRepository.findById(request.getMedicacao())
                .orElseThrow(() -> new NotFoundException("Medicação não encontrada com ID: " + request.getMedicacao()));
        medicacaoPaciente.setMedicacao(medicacao);

        // CID relacionado é opcional
        if (request.getCidRelacionado() != null) {
            CidDoencas cidRelacionado = cidDoencasRepository.findById(request.getCidRelacionado())
                    .orElseThrow(() -> new NotFoundException("CID não encontrado com ID: " + request.getCidRelacionado()));
            medicacaoPaciente.setCidRelacionado(cidRelacionado);
        }

        // medicacaoAtiva não faz parte do Request - definido como true por padrão
        medicacaoPaciente.setMedicacaoAtiva(true);

        // Obtém o tenant do usuário autenticado (obrigatório para MedicacaoPaciente que estende BaseEntity)
        Tenant tenant = obterTenantDoUsuarioAutenticado();
        if (tenant == null) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado. É necessário estar autenticado para criar relacionamentos de medicações.");
        }
        medicacaoPaciente.setTenant(tenant);

        medicacaoPaciente.setActive(true);

        MedicacaoPaciente medicacaoPacienteSalvo = medicacaoPacienteRepository.save(medicacaoPaciente);
        log.info("Ligação paciente-medicação criada com sucesso. ID: {}", medicacaoPacienteSalvo.getId());

        return medicacaoPacienteMapper.toResponse(medicacaoPacienteSalvo);
    }

    @Override
    @Transactional
    @CacheEvict(value = "medicacaopaciente", allEntries = true)
    public MedicacaoPacienteResponse criarSimplificado(MedicacaoPacienteSimplificadoRequest request) {
        log.debug("Criando nova ligação paciente-medicação simplificada - Paciente: {}, Tenant: {}, Medicação: {}", 
                request.getPaciente(), request.getTenant(), request.getMedicacao());

        if (request == null) {
            throw new BadRequestException("Dados da ligação paciente-medicação são obrigatórios");
        }

        if (request.getPaciente() == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        if (request.getTenant() == null) {
            throw new BadRequestException("ID do tenant é obrigatório");
        }

        if (request.getMedicacao() == null) {
            throw new BadRequestException("ID da medicação é obrigatório");
        }

        // Busca paciente
        Paciente paciente = pacienteRepository.findById(request.getPaciente())
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPaciente()));

        // Busca tenant
        Tenant tenant = tenantRepository.findById(request.getTenant())
                .orElseThrow(() -> new NotFoundException("Tenant não encontrado com ID: " + request.getTenant()));

        // Busca medicação
        Medicacao medicacao = medicacaoRepository.findById(request.getMedicacao())
                .orElseThrow(() -> new NotFoundException("Medicação não encontrada com ID: " + request.getMedicacao()));

        // Cria nova entidade com valores padrão
        MedicacaoPaciente medicacaoPaciente = new MedicacaoPaciente();
        medicacaoPaciente.setPaciente(paciente);
        medicacaoPaciente.setTenant(tenant);
        medicacaoPaciente.setMedicacao(medicacao);
        medicacaoPaciente.setActive(true);
        medicacaoPaciente.setMedicacaoAtiva(true); // Valor padrão
        // dose, frequencia, via, cidRelacionado, dataInicio, dataFim e observacoes ficam null

        MedicacaoPaciente medicacaoPacienteSalvo = medicacaoPacienteRepository.save(medicacaoPaciente);
        log.info("Ligação paciente-medicação criada com sucesso (simplificado). ID: {}", medicacaoPacienteSalvo.getId());

        return medicacaoPacienteMapper.toResponse(medicacaoPacienteSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "medicacaopaciente", key = "#id")
    public MedicacaoPacienteResponse obterPorId(UUID id) {
        log.debug("Buscando ligação paciente-medicação por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID da ligação paciente-medicação é obrigatório");
        }

        MedicacaoPaciente medicacaoPaciente = medicacaoPacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ligação paciente-medicação não encontrada com ID: " + id));

        return medicacaoPacienteMapper.toResponse(medicacaoPaciente);
    }

    @Override
    public Page<MedicacaoPacienteResponse> listar(Pageable pageable) {
        log.debug("Listando ligações paciente-medicação paginadas. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<MedicacaoPaciente> medicacoesPaciente = medicacaoPacienteRepository.findAll(pageable);
        return medicacoesPaciente.map(medicacaoPacienteMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "medicacaopaciente", key = "#id")
    public MedicacaoPacienteResponse atualizar(UUID id, MedicacaoPacienteRequest request) {
        log.debug("Atualizando ligação paciente-medicação. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da ligação paciente-medicação é obrigatório");
        }

        validarDadosBasicos(request);

        MedicacaoPaciente medicacaoPacienteExistente = medicacaoPacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ligação paciente-medicação não encontrada com ID: " + id));

        atualizarDadosMedicacaoPaciente(medicacaoPacienteExistente, request);

        MedicacaoPaciente medicacaoPacienteAtualizado = medicacaoPacienteRepository.save(medicacaoPacienteExistente);
        log.info("Ligação paciente-medicação atualizada com sucesso. ID: {}", medicacaoPacienteAtualizado.getId());

        return medicacaoPacienteMapper.toResponse(medicacaoPacienteAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "medicacaopaciente", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo ligação paciente-medicação. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da ligação paciente-medicação é obrigatório");
        }

        MedicacaoPaciente medicacaoPaciente = medicacaoPacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ligação paciente-medicação não encontrada com ID: " + id));

        if (Boolean.FALSE.equals(medicacaoPaciente.getActive())) {
            throw new BadRequestException("Ligação paciente-medicação já está inativa");
        }

        medicacaoPaciente.setActive(false);
        medicacaoPacienteRepository.save(medicacaoPaciente);
        log.info("Ligação paciente-medicação excluída (desativada) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(MedicacaoPacienteRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da ligação paciente-medicação são obrigatórios");
        }
        if (request.getPaciente() == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }
        if (request.getMedicacao() == null) {
            throw new BadRequestException("ID da medicação é obrigatório");
        }
    }

    private void atualizarDadosMedicacaoPaciente(MedicacaoPaciente medicacaoPaciente, MedicacaoPacienteRequest request) {
        // Atualiza campos simples
        if (request.getDose() != null) {
            medicacaoPaciente.setDose(request.getDose());
        }
        if (request.getFrequencia() != null) {
            medicacaoPaciente.setFrequencia(request.getFrequencia());
        }
        if (request.getVia() != null) {
            medicacaoPaciente.setVia(request.getVia());
        }
        if (request.getDataInicio() != null) {
            medicacaoPaciente.setDataInicio(request.getDataInicio());
        }
        if (request.getDataFim() != null) {
            medicacaoPaciente.setDataFim(request.getDataFim());
        }
        // medicacaoAtiva não faz parte do Request
        if (request.getObservacoes() != null) {
            medicacaoPaciente.setObservacoes(request.getObservacoes());
        }

        // Atualiza relacionamentos se fornecidos
        if (request.getPaciente() != null) {
            Paciente paciente = pacienteRepository.findById(request.getPaciente())
                    .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPaciente()));
            medicacaoPaciente.setPaciente(paciente);
        }

        if (request.getMedicacao() != null) {
            Medicacao medicacao = medicacaoRepository.findById(request.getMedicacao())
                    .orElseThrow(() -> new NotFoundException("Medicação não encontrada com ID: " + request.getMedicacao()));
            medicacaoPaciente.setMedicacao(medicacao);
        }

        // CID relacionado é opcional
        if (request.getCidRelacionado() != null) {
            CidDoencas cidRelacionado = cidDoencasRepository.findById(request.getCidRelacionado())
                    .orElseThrow(() -> new NotFoundException("CID não encontrado com ID: " + request.getCidRelacionado()));
            medicacaoPaciente.setCidRelacionado(cidRelacionado);
        } else {
            // Se não fornecido, remove a relação
            medicacaoPaciente.setCidRelacionado(null);
        }
    }

    /**
     * Obtém o tenant do usuário autenticado.
     * 
     * @return Tenant do usuário autenticado ou null se não encontrado
     */
    private Tenant obterTenantDoUsuarioAutenticado() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                log.warn("Usuário não autenticado. Não é possível obter tenant.");
                return null;
            }

            // Obtém o userId do token JWT
            UUID userId = null;
            Object details = authentication.getDetails();
            if (details instanceof com.upsaude.integration.supabase.SupabaseAuthResponse.User) {
                com.upsaude.integration.supabase.SupabaseAuthResponse.User user = 
                    (com.upsaude.integration.supabase.SupabaseAuthResponse.User) details;
                userId = user.getId();
                log.debug("UserId obtido do SupabaseAuthResponse.User: {}", userId);
            } else if (authentication.getPrincipal() instanceof String) {
                try {
                    userId = UUID.fromString(authentication.getPrincipal().toString());
                    log.debug("UserId obtido do Principal (String): {}", userId);
                } catch (IllegalArgumentException e) {
                    log.warn("Principal não é um UUID válido: {}", authentication.getPrincipal());
                    return null;
                }
            } else {
                log.warn("Tipo de Principal não reconhecido: {}", authentication.getPrincipal() != null ? authentication.getPrincipal().getClass().getName() : "null");
            }

            if (userId != null) {
                java.util.Optional<com.upsaude.entity.UsuariosSistema> usuarioOpt = usuariosSistemaRepository.findByUserId(userId);
                if (usuarioOpt.isPresent()) {
                    com.upsaude.entity.UsuariosSistema usuario = usuarioOpt.get();
                    Tenant tenant = usuario.getTenant();
                    if (tenant != null) {
                        log.debug("Tenant obtido com sucesso: {} (ID: {})", tenant.getNome(), tenant.getId());
                        return tenant;
                    } else {
                        log.warn("Usuário encontrado mas sem tenant associado. UserId: {}", userId);
                    }
                } else {
                    log.warn("Usuário não encontrado no sistema. UserId: {}", userId);
                }
            } else {
                log.warn("Não foi possível obter userId do contexto de autenticação");
            }
        } catch (Exception e) {
            log.error("Erro ao obter tenant do usuário autenticado", e);
        }
        return null;
    }
}

