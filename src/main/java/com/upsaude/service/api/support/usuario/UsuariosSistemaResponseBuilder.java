package com.upsaude.service.api.support.usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.upsaude.api.response.sistema.usuario.MedicoSimplificadoResponse;
import com.upsaude.api.response.sistema.usuario.PacienteSimplificadoResponse;
import com.upsaude.api.response.sistema.usuario.ProfissionalSaudeSimplificadoResponse;
import com.upsaude.api.response.sistema.usuario.UsuarioSimplificadoResponse;
import com.upsaude.api.response.sistema.usuario.UsuariosSistemaResponse;
import com.upsaude.entity.estabelecimento.UsuarioEstabelecimento;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.paciente.PacienteContato;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.sistema.auth.User;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.entity.sistema.usuario.UsuariosSistema;
import com.upsaude.enums.TipoContatoEnum;
import com.upsaude.enums.TipoUsuarioSistemaEnum;
import com.upsaude.mapper.sistema.usuario.UsuariosSistemaMapper;
import com.upsaude.repository.estabelecimento.UsuarioEstabelecimentoRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuariosSistemaResponseBuilder {

    private final UsuariosSistemaMapper mapper;
    private final UsuarioEstabelecimentoRepository usuarioEstabelecimentoRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public UsuariosSistemaResponse build(UsuariosSistema entity) {
        inicializarColecoes(entity);

        UsuariosSistemaResponse response = mapper.toResponse(entity);
        enriquecerResponse(response, entity);

        return response;
    }

    private void inicializarColecoes(UsuariosSistema entity) {
        if (entity.getDadosIdentificacao() != null) {
            Hibernate.initialize(entity.getDadosIdentificacao());
        }
        if (entity.getDadosExibicao() != null) {
            Hibernate.initialize(entity.getDadosExibicao());
        }
        if (entity.getConfiguracao() != null) {
            Hibernate.initialize(entity.getConfiguracao());
        }
        if (entity.getEstabelecimentosVinculados() != null) {
            Hibernate.initialize(entity.getEstabelecimentosVinculados());
        }
    }

    private void enriquecerResponse(UsuariosSistemaResponse response, UsuariosSistema entity) {
        enriquecerDadosIdentificacao(response, entity);
        enriquecerTenant(response, entity);
        enriquecerProfissionalSaude(response, entity);
        enriquecerMedico(response, entity);
        enriquecerPaciente(response, entity);
        enriquecerUser(response, entity);
        enriquecerVinculos(response, entity);
        enriquecerTipoUsuario(response, entity);
        enriquecerUsuarioConsorcio(response, entity);
    }

    private void enriquecerDadosIdentificacao(UsuariosSistemaResponse response, UsuariosSistema entity) {
        if (response.getDadosIdentificacao() == null && entity.getDadosIdentificacao() != null) {
            response.setDadosIdentificacao(
                    com.upsaude.api.response.embeddable.DadosIdentificacaoUsuarioResponse.builder()
                            .username(entity.getDadosIdentificacao().getUsername())
                            .cpf(entity.getDadosIdentificacao().getCpf())
                            .build());
        } else if (response.getDadosIdentificacao() != null && entity.getDadosIdentificacao() != null) {
            if (response.getDadosIdentificacao().getCpf() == null && entity.getDadosIdentificacao().getCpf() != null) {
                response.getDadosIdentificacao().setCpf(entity.getDadosIdentificacao().getCpf());
            }
            if (response.getDadosIdentificacao().getUsername() == null
                    && entity.getDadosIdentificacao().getUsername() != null) {
                response.getDadosIdentificacao().setUsername(entity.getDadosIdentificacao().getUsername());
            }
        }
    }

    private void enriquecerTenant(UsuariosSistemaResponse response, UsuariosSistema entity) {
        Tenant tenant = entity.getTenant();
        if (tenant != null) {
            response.setTenantId(tenant.getId());
            if (tenant.getDadosIdentificacao() != null) {
                response.setTenantNome(tenant.getDadosIdentificacao().getNome());
            } else {
                response.setTenantNome(null);
            }
            response.setTenantSlug(null);
        }
    }

    private void enriquecerProfissionalSaude(UsuariosSistemaResponse response, UsuariosSistema entity) {
        ProfissionaisSaude profissional = entity.getProfissionalSaude();
        if (profissional != null) {
            Hibernate.initialize(profissional);
            if (profissional.getDadosPessoaisBasicos() != null) {
                Hibernate.initialize(profissional.getDadosPessoaisBasicos());
            }
            if (profissional.getContato() != null) {
                Hibernate.initialize(profissional.getContato());
            }

            String nome = null;
            if (profissional.getDadosPessoaisBasicos() != null) {
                nome = profissional.getDadosPessoaisBasicos().getNomeCompleto();
            }

            String email = null;
            if (profissional.getContato() != null) {
                email = profissional.getContato().getEmail();
            }

            ProfissionalSaudeSimplificadoResponse profissionalResponse = ProfissionalSaudeSimplificadoResponse.builder()
                    .id(profissional.getId())
                    .nome(nome)
                    .email(email)
                    .build();

            response.setProfissionalSaude(profissionalResponse);
        }
    }

    private void enriquecerMedico(UsuariosSistemaResponse response, UsuariosSistema entity) {
        Medicos medico = entity.getMedico();
        if (medico != null) {
            Hibernate.initialize(medico);
            if (medico.getDadosPessoaisBasicos() != null) {
                Hibernate.initialize(medico.getDadosPessoaisBasicos());
            }
            if (medico.getContato() != null) {
                Hibernate.initialize(medico.getContato());
            }

            String nome = null;
            if (medico.getDadosPessoaisBasicos() != null) {
                nome = medico.getDadosPessoaisBasicos().getNomeCompleto();
            }

            String email = null;
            if (medico.getContato() != null) {
                email = medico.getContato().getEmail();
            }

            MedicoSimplificadoResponse medicoResponse = MedicoSimplificadoResponse.builder()
                    .id(medico.getId())
                    .nome(nome)
                    .email(email)
                    .build();

            response.setMedico(medicoResponse);
        }
    }

    private void enriquecerPaciente(UsuariosSistemaResponse response, UsuariosSistema entity) {
        Paciente paciente = entity.getPaciente();
        if (paciente != null) {
            Hibernate.initialize(paciente);
            if (paciente.getContatos() != null) {
                Hibernate.initialize(paciente.getContatos());
            }

            String emailPaciente = null;
            if (paciente.getContatos() != null) {
                List<PacienteContato> contatos = paciente.getContatos();
                for (int i = 0; i < contatos.size(); i++) {
                    PacienteContato contato = contatos.get(i);
                    if (contato.getTipo() == TipoContatoEnum.EMAIL) {
                        String email = contato.getEmail();
                        if (email != null && !email.trim().isEmpty()) {
                            emailPaciente = email;
                            break;
                        }
                    }
                }
            }

            PacienteSimplificadoResponse pacienteResponse = PacienteSimplificadoResponse.builder()
                    .id(paciente.getId())
                    .nome(paciente.getNomeCompleto())
                    .email(emailPaciente)
                    .build();

            response.setPaciente(pacienteResponse);
        }
    }

    private void enriquecerUser(UsuariosSistemaResponse response, UsuariosSistema entity) {
        User user = entity.getUser();
        if (user != null) {
            Hibernate.initialize(user);

            String nomeUsuario = null;
            if (entity.getDadosExibicao() != null) {
                nomeUsuario = entity.getDadosExibicao().getNomeExibicao();
            }
            if (nomeUsuario == null && entity.getDadosIdentificacao() != null) {
                nomeUsuario = entity.getDadosIdentificacao().getUsername();
            }

            UsuarioSimplificadoResponse usuarioResponse = UsuarioSimplificadoResponse.builder()
                    .id(user.getId())
                    .nome(nomeUsuario)
                    .email(user.getEmail())
                    .build();

            response.setUsuario(usuarioResponse);
        }
    }

    private void enriquecerVinculos(UsuariosSistemaResponse response, UsuariosSistema entity) {
        entityManager.flush();

        UUID userId = null;
        if (entity.getUser() != null) {
            userId = entity.getUser().getId();
        }

        List<UsuarioEstabelecimento> vinculos = usuarioEstabelecimentoRepository.findByUsuarioUserId(userId);

        if (vinculos != null && !vinculos.isEmpty()) {
            List<UsuariosSistemaResponse.EstabelecimentoVinculoSimples> vinculosResponse = new ArrayList<UsuariosSistemaResponse.EstabelecimentoVinculoSimples>();

            for (int i = 0; i < vinculos.size(); i++) {
                UsuarioEstabelecimento vinculo = vinculos.get(i);
                if (Boolean.TRUE.equals(vinculo.getActive())) {
                    String estabelecimentoNome = null;
                    if (vinculo.getEstabelecimento() != null
                            && vinculo.getEstabelecimento().getDadosIdentificacao() != null) {
                        estabelecimentoNome = vinculo.getEstabelecimento().getDadosIdentificacao().getNome();
                    }

                    UsuariosSistemaResponse.EstabelecimentoVinculoSimples vinculoResponse = UsuariosSistemaResponse.EstabelecimentoVinculoSimples
                            .builder()
                            .id(vinculo.getId())
                            .estabelecimentoId(vinculo.getEstabelecimento().getId())
                            .estabelecimentoNome(estabelecimentoNome)
                            .tipoUsuario(vinculo.getTipoUsuario())
                            .active(vinculo.getActive())
                            .build();

                    vinculosResponse.add(vinculoResponse);
                }
            }

            response.setEstabelecimentosVinculados(vinculosResponse);
            log.debug("Vínculos de estabelecimentos carregados: {} ativos", vinculosResponse.size());
        }
    }

    private void enriquecerTipoUsuario(UsuariosSistemaResponse response, UsuariosSistema entity) {
        TipoUsuarioSistemaEnum tipoUsuario = null;

        if (entity.getConfiguracao() != null && Boolean.TRUE.equals(entity.getConfiguracao().getAdminTenant())) {
            tipoUsuario = TipoUsuarioSistemaEnum.ADMIN_TENANT;
        } else if (entity.getMedico() != null) {
            tipoUsuario = TipoUsuarioSistemaEnum.MEDICO;
        } else if (entity.getProfissionalSaude() != null) {
            tipoUsuario = TipoUsuarioSistemaEnum.PROFISSIONAL_SAUDE;
        } else if (entity.getPaciente() != null) {
            tipoUsuario = TipoUsuarioSistemaEnum.PACIENTE;
        }

        response.setTipoUsuario(tipoUsuario);
    }

    private void enriquecerUsuarioConsorcio(UsuariosSistemaResponse response, UsuariosSistema entity) {
        if (response.getUsuarioConsorcio() == null && entity.getUsuarioConsorcio() != null) {
            response.setUsuarioConsorcio(entity.getUsuarioConsorcio());
        }
    }
}
