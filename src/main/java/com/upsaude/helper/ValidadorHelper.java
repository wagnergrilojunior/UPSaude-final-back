package com.upsaude.helper;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.util.StringUtils;

import com.upsaude.entity.Tenant;
import com.upsaude.exception.BadRequestException;

public class ValidadorHelper {

    public static <T> void validarCampoUnico(
            UUID entidadeId,
            String valor,
            Function<String, Optional<T>> buscarPorCampo,
            Function<T, UUID> obterId,
            String nomeCampo,
            String nomeEntidade) {
        
        if (!StringUtils.hasText(valor)) {
            return;
        }

        Optional<T> entidadeExistente = buscarPorCampo.apply(valor);

        if (entidadeExistente.isPresent()) {
            T entidadeEncontrada = entidadeExistente.get();
            UUID idEncontrado = obterId.apply(entidadeEncontrada);

            if (entidadeId != null && !idEncontrado.equals(entidadeId)) {
                throw new BadRequestException(
                    String.format("Já existe um %s cadastrado com o %s: %s", nomeEntidade, nomeCampo, valor));
            }

            if (entidadeId == null) {
                throw new BadRequestException(
                    String.format("Já existe um %s cadastrado com o %s: %s", nomeEntidade, nomeCampo, valor));
            }
        }
    }

    public static <T> void validarCampoUnicoPorTenant(
            UUID entidadeId,
            String valor,
            Tenant tenant,
            Function<Object[], Optional<T>> buscarPorCampoETenant,
            Function<T, UUID> obterId,
            String nomeCampo,
            String nomeEntidade) {
        
        if (!StringUtils.hasText(valor) || tenant == null) {
            return;
        }

        Optional<T> entidadeExistente = buscarPorCampoETenant.apply(new Object[]{valor, tenant});

        if (entidadeExistente.isPresent()) {
            T entidadeEncontrada = entidadeExistente.get();
            UUID idEncontrado = obterId.apply(entidadeEncontrada);

            if (entidadeId != null && !idEncontrado.equals(entidadeId)) {
                throw new BadRequestException(
                    String.format("Já existe um %s cadastrado com o %s %s neste tenant.", nomeEntidade, nomeCampo, valor));
            }

            if (entidadeId == null) {
                throw new BadRequestException(
                    String.format("Já existe um %s cadastrado com o %s %s neste tenant.", nomeEntidade, nomeCampo, valor));
            }
        }
    }
}
