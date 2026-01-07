package com.upsaude.mapper.profissional;

import com.upsaude.api.response.profissional.ProfissionalSaudeResponse;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

/**
 * Mapper para converter entidade ProfissionaisSaude em DTO de resposta.
 */
@Component
public class ProfissionalSaudeMapper {

    public ProfissionalSaudeResponse toResponse(ProfissionaisSaude entity) {
        if (entity == null) {
            return null;
        }

        ProfissionalSaudeResponse.ProfissionalSaudeResponseBuilder builder = ProfissionalSaudeResponse.builder()
                .id(entity.getId())
                .dataUltimaSincronizacaoCnes(entity.getDataUltimaSincronizacaoCnes())
                .ativo(entity.getActive())
                .criadoEm(entity.getCreatedAt())
                .atualizadoEm(entity.getUpdatedAt());

        // Dados Pessoais Básicos
        if (entity.getDadosPessoaisBasicos() != null) {
            builder.nomeCompleto(entity.getDadosPessoaisBasicos().getNomeCompleto())
                    .dataNascimento(entity.getDadosPessoaisBasicos().getDataNascimento())
                    .sexo(entity.getDadosPessoaisBasicos().getSexo() != null
                            ? entity.getDadosPessoaisBasicos().getSexo().name()
                            : null);
        }

        // Documentos Básicos
        if (entity.getDocumentosBasicos() != null) {
            builder.cpf(entity.getDocumentosBasicos().getCpf())
                    .cns(entity.getDocumentosBasicos().getCns())
                    .rg(entity.getDocumentosBasicos().getRg())
                    .orgaoEmissorRg(entity.getDocumentosBasicos().getOrgaoEmissorRg())
                    .ufEmissaoRg(entity.getDocumentosBasicos().getUfEmissaoRg())
                    .dataEmissaoRg(entity.getDocumentosBasicos().getDataEmissaoRg());
        }

        // Registro Profissional
        if (entity.getRegistroProfissional() != null) {
            builder.registroProfissional(entity.getRegistroProfissional().getRegistroProfissional())
                    .ufRegistro(entity.getRegistroProfissional().getUfRegistro())
                    .statusRegistro(entity.getRegistroProfissional().getStatusRegistro() != null
                            ? entity.getRegistroProfissional().getStatusRegistro().name()
                            : null)
                    .dataEmissaoRegistro(entity.getRegistroProfissional().getDataEmissaoRegistro())
                    .dataValidadeRegistro(entity.getRegistroProfissional().getDataValidadeRegistro());
        }

        // Contato
        if (entity.getContato() != null) {
            builder.telefone(entity.getContato().getTelefone())
                    .celular(entity.getContato().getCelular())
                    .email(entity.getContato().getEmail())
                    .emailInstitucional(entity.getContato().getEmailInstitucional());
        }

        return builder.build();
    }

    public Page<ProfissionalSaudeResponse> toResponsePage(Page<ProfissionaisSaude> page) {
        return page.map(this::toResponse);
    }
}
