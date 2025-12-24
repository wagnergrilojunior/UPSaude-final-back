package com.upsaude.service.api.support.responsavellegal;

import com.upsaude.api.request.paciente.ResponsavelLegalRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ResponsavelLegalValidationService {

    public void validarObrigatorios(ResponsavelLegalRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do responsável legal são obrigatórios");
        }
        if (request.getPaciente() == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }
        if (request.getEstabelecimento() == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }
        if (!StringUtils.hasText(request.getNome())) {
            throw new BadRequestException("Nome do responsável legal é obrigatório");
        }
    }

    public String normalizarCpf(String cpf) {
        String cpfLimpo = StringUtils.hasText(cpf) ? limparNumeros(cpf) : null;
        if (StringUtils.hasText(cpfLimpo) && cpfLimpo.length() != 11) {
            throw new BadRequestException("CPF deve conter exatamente 11 dígitos numéricos");
        }
        return cpfLimpo;
    }

    public String normalizarTelefone(String telefone) {
        String telefoneLimpo = StringUtils.hasText(telefone) ? limparNumeros(telefone) : null;
        if (StringUtils.hasText(telefoneLimpo) && telefoneLimpo.length() != 10 && telefoneLimpo.length() != 11) {
            throw new BadRequestException("Telefone deve conter 10 ou 11 dígitos numéricos");
        }
        return telefoneLimpo;
    }

    private String limparNumeros(String valor) {
        if (valor == null) return null;
        return valor.replaceAll("\\\\D", "");
    }
}

