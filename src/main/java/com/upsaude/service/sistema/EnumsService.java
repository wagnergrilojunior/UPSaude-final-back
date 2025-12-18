package com.upsaude.service.sistema;

import com.upsaude.api.response.EnumInfoResponse;
import com.upsaude.api.response.EnumsResponse;

public interface EnumsService {

    EnumsResponse listarTodosEnums();

    java.util.List<String> listarNomesEnums();

    EnumInfoResponse obterEnumPorNome(String nomeEnum);
}
