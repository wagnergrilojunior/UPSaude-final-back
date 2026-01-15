package com.upsaude.service.impl.api.sia;

import com.upsaude.api.response.sia.SiaPaEnriquecidoResponse;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.referencia.sia.SiaPaEnriquecido;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.estabelecimento.EstabelecimentosRepository;
import com.upsaude.repository.referencia.sia.SiaPaEnriquecidoRepository;
import com.upsaude.service.api.sia.SiaPaEnriquecimentoService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SiaPaEnriquecimentoServiceImpl implements SiaPaEnriquecimentoService {

    private final SiaPaEnriquecidoRepository repository;
    private final TenantService tenantService;
    private final EstabelecimentosRepository estabelecimentosRepository;

    @Override
    public Page<SiaPaEnriquecidoResponse> listarEnriquecido(String competencia, String uf, String codigoCnes, String procedimentoCodigo, Pageable pageable) {
        if (!StringUtils.hasText(competencia)) {
            throw new IllegalArgumentException("competencia é obrigatória (AAAAMM)");
        }
        if (!StringUtils.hasText(uf)) {
            throw new IllegalArgumentException("uf é obrigatória (2 letras)");
        }

        Page<SiaPaEnriquecido> page;
        if (StringUtils.hasText(codigoCnes)) {
            page = repository.findByCompetenciaAndUfAndCodigoCnes(competencia, uf, codigoCnes, pageable);
        } else if (StringUtils.hasText(procedimentoCodigo)) {
            page = repository.findByCompetenciaAndUfAndProcedimentoCodigo(competencia, uf, procedimentoCodigo, pageable);
        } else {
            page = repository.findByCompetenciaAndUf(competencia, uf, pageable);
        }

        Tenant tenant = null;
        try {
            tenant = tenantService.obterTenantDoUsuarioAutenticado();
        } catch (Exception e) {
            log.debug("Não foi possível obter tenant autenticado para enriquecer estabelecimento por tenant: {}", e.getMessage());
        }

        Tenant finalTenant = tenant;
        return page.map(entity -> toResponse(entity, finalTenant));
    }

    @Override
    public SiaPaEnriquecidoResponse obterPorId(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("id é obrigatório");
        }
        SiaPaEnriquecido entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Registro SIA-PA não encontrado: " + id));

        Tenant tenant = null;
        try {
            tenant = tenantService.obterTenantDoUsuarioAutenticado();
        } catch (Exception e) {
            log.debug("Não foi possível obter tenant autenticado para enriquecer estabelecimento por tenant: {}", e.getMessage());
        }

        return toResponse(entity, tenant);
    }

    private SiaPaEnriquecidoResponse toResponse(SiaPaEnriquecido e, Tenant tenant) {
        String estabelecimentoNome = e.getEstabelecimentoNome();
        String estabelecimentoCnpj = e.getEstabelecimentoCnpj();
        String estabelecimentoCodigoIbgeMunicipio = e.getEstabelecimentoCodigoIbgeMunicipio();
        String estabelecimentoEsferaAdministrativa = e.getEstabelecimentoEsferaAdministrativa();

        if (tenant != null && StringUtils.hasText(e.getCodigoCnes())) {
            Optional<Estabelecimentos> estabOpt = estabelecimentosRepository.findByCodigoCnesAndTenant(e.getCodigoCnes(), tenant);
            if (estabOpt.isPresent()) {
                Estabelecimentos estab = estabOpt.get();
                if (estab.getDadosIdentificacao() != null) {
                    if (StringUtils.hasText(estab.getDadosIdentificacao().getNome())) {
                        estabelecimentoNome = estab.getDadosIdentificacao().getNome();
                    }
                    if (StringUtils.hasText(estab.getDadosIdentificacao().getCnpj())) {
                        estabelecimentoCnpj = estab.getDadosIdentificacao().getCnpj();
                    }
                    if (StringUtils.hasText(estab.getDadosIdentificacao().getCnes())) {
                        // nada (mesmo código)
                    }
                }
                if (StringUtils.hasText(estab.getCodigoIbgeMunicipio())) {
                    estabelecimentoCodigoIbgeMunicipio = estab.getCodigoIbgeMunicipio();
                }
                if (estab.getEsferaAdministrativa() != null) {
                    estabelecimentoEsferaAdministrativa = estab.getEsferaAdministrativa().name();
                }
            }
        }

        return SiaPaEnriquecidoResponse.builder()
                .id(e.getId())
                .criadoEm(e.getCreatedAt())
                .atualizadoEm(e.getUpdatedAt())
                .ativo(e.getActive())

                .competencia(e.getCompetencia())
                .uf(e.getUf())
                .mesMovimentacao(e.getMesMovimentacao())

                .codigoCnes(e.getCodigoCnes())
                .municipioUfmunCodigo(e.getMunicipioUfmunCodigo())
                .municipioGestaoCodigo(e.getMunicipioGestaoCodigo())

                .procedimentoCodigo(e.getProcedimentoCodigo())
                .cidPrincipalCodigo(e.getCidPrincipalCodigo())
                .sexo(e.getSexo())
                .idade(e.getIdade())

                .quantidadeProduzida(e.getQuantidadeProduzida())
                .quantidadeAprovada(e.getQuantidadeAprovada())
                .flagErro(e.getFlagErro())
                .valorProduzido(e.getValorProduzido())
                .valorAprovado(e.getValorAprovado())

                .procedimentoNome(e.getProcedimentoNome())
                .procedimentoTipoComplexidade(e.getProcedimentoTipoComplexidade())
                .procedimentoSexoPermitido(e.getProcedimentoSexoPermitido())
                .procedimentoIdadeMinima(e.getProcedimentoIdadeMinima())
                .procedimentoIdadeMaxima(e.getProcedimentoIdadeMaxima())
                .procedimentoValorSigtapAmbulatorial(e.getProcedimentoValorSigtapAmbulatorial())
                .procedimentoFormaOrganizacaoNome(e.getProcedimentoFormaOrganizacaoNome())
                .procedimentoSubgrupoNome(e.getProcedimentoSubgrupoNome())
                .procedimentoGrupoNome(e.getProcedimentoGrupoNome())

                .cidPrincipalDescricao(e.getCidPrincipalDescricao())

                .estabelecimentoNome(estabelecimentoNome)
                .estabelecimentoCnpj(estabelecimentoCnpj)
                .estabelecimentoCodigoIbgeMunicipio(estabelecimentoCodigoIbgeMunicipio)
                .estabelecimentoEsferaAdministrativa(estabelecimentoEsferaAdministrativa)
                .build();
    }
}

