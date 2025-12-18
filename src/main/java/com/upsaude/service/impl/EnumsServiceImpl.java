package com.upsaude.service.impl;

import com.upsaude.api.response.EnumInfoResponse;
import com.upsaude.api.response.EnumItemResponse;
import com.upsaude.api.response.EnumsResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.service.sistema.EnumsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.*;

@Slf4j
@Service
public class EnumsServiceImpl implements EnumsService {

    private static final String ENUMS_PACKAGE = "com.upsaude.enums";

    @Override
    public EnumsResponse listarTodosEnums() {
        log.debug("Listando todos os enums do sistema");

        List<EnumInfoResponse> enumsInfo = new ArrayList<>();

        try {

            Set<Class<?>> enumClasses = getEnumClasses();

            for (Class<?> enumClass : enumClasses) {
                try {
                    EnumInfoResponse enumInfo = processarEnum(enumClass);
                    if (enumInfo != null) {
                        enumsInfo.add(enumInfo);
                    }
                } catch (Exception e) {
                    log.warn("Erro ao processar enum {}: {}", enumClass.getSimpleName(), e.getMessage());
                }
            }

            enumsInfo.sort(Comparator.comparing(EnumInfoResponse::getNomeEnum));

            log.info("Total de {} enums processados com sucesso", enumsInfo.size());

            return EnumsResponse.builder()
                    .enums(enumsInfo)
                    .totalEnums(enumsInfo.size())
                    .build();

        } catch (Exception e) {
            log.error("Erro inesperado ao listar enums do sistema. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao listar enums do sistema", e);
        }
    }

    private Set<Class<?>> getEnumClasses() {
        Set<Class<?>> enumClasses = new HashSet<>();

        try {
            String packagePath = ENUMS_PACKAGE.replace('.', '/');
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            java.net.URL resource = classLoader.getResource(packagePath);

            if (resource != null) {

                if ("file".equals(resource.getProtocol())) {
                    try {
                        java.io.File directory = new java.io.File(resource.toURI());
                        if (directory.exists() && directory.isDirectory()) {
                            String[] files = directory.list();
                            if (files != null) {
                                for (String file : files) {
                                    if (file.endsWith(".class")) {
                                        String className = file.substring(0, file.length() - 6);
                                        try {
                                            Class<?> clazz = Class.forName(ENUMS_PACKAGE + "." + className);
                                            if (clazz.isEnum()) {
                                                enumClasses.add(clazz);
                                            }
                                        } catch (ClassNotFoundException e) {
                                            log.debug("Classe não encontrada: {}", className);
                                        }
                                    }
                                }
                            }
                        }
                    } catch (java.net.URISyntaxException e) {
                        log.debug("Erro ao converter URL para URI: {}", e.getMessage());
                    }
                }

                else if ("jar".equals(resource.getProtocol())) {
                    String jarPath = resource.getPath().substring(5, resource.getPath().indexOf("!"));
                    try (java.util.jar.JarFile jar = new java.util.jar.JarFile(java.net.URLDecoder.decode(jarPath, "UTF-8"))) {
                        java.util.Enumeration<java.util.jar.JarEntry> entries = jar.entries();
                        while (entries.hasMoreElements()) {
                            java.util.jar.JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            if (name.startsWith(packagePath) && name.endsWith(".class") && !name.contains("$")) {
                                String className = name.replace('/', '.').substring(0, name.length() - 6);
                                try {
                                    Class<?> clazz = Class.forName(className);
                                    if (clazz.isEnum()) {
                                        enumClasses.add(clazz);
                                    }
                                } catch (ClassNotFoundException e) {
                                    log.debug("Classe não encontrada: {}", className);
                                }
                            }
                        }
                    }
                }
            }

            if (enumClasses.isEmpty()) {
                log.warn("Não foi possível descobrir enums automaticamente. Usando lista hardcoded.");
                enumClasses = getEnumsHardcoded();
            }

        } catch (Exception e) {
            log.error("Erro ao buscar classes enum: {}", e.getMessage(), e);

            enumClasses = getEnumsHardcoded();
        }

        return enumClasses;
    }

    private Set<Class<?>> getEnumsHardcoded() {
        Set<Class<?>> enumClasses = new HashSet<>();
        String[] enumNames = {
                "CanalNotificacaoEnum", "ClasseTerapeuticaEnum", "ClassificacaoRiscoGestacionalEnum",
                "CondicaoMoradiaEnum", "EscolaridadeEnum", "EstadoCivilEnum", "FormaFarmaceuticaEnum",
                "FrequenciaMedicacaoEnum", "GravidadeDoencaEnum", "IdentidadeGeneroEnum",
                "ModalidadeConvenioEnum", "NacionalidadeEnum", "NaturezaJuridicaEnum",
                "OrientacaoSexualEnum", "PrioridadeAtendimentoEnum", "RacaCorEnum",
                "SeveridadeAlergiaEnum", "SexoEnum", "SituacaoFamiliarEnum", "StatusAgendamentoEnum",
                "StatusAtendimentoEnum", "StatusAtivoEnum", "StatusCirurgiaEnum", "StatusConsultaEnum",
                "StatusDiagnosticoEnum", "StatusFuncionamentoEnum", "StatusManutencaoEnum",
                "StatusPacienteEnum", "StatusPreNatalEnum", "StatusReceitaEnum",
                "StatusRegistroMedicoEnum", "TipoAcaoPromocaoSaudeEnum", "TipoAlergiaEnum",
                "TipoAtendimentoEnum", "TipoAtendimentoPreferencialEnum", "TipoAtividadeProfissionalEnum",
                "TipoCnsEnum", "TipoConsultaEnum", "TipoControleMedicamentoEnum", "TipoConvenioEnum",
                "TipoCuidadoEnfermagemEnum", "TipoDeficienciaEnum", "TipoDoencaEnum",
                "TipoEducacaoSaudeEnum", "TipoEnderecoEnum", "TipoEquipamentoEnum", "TipoEquipeEnum",
                "TipoEspecialidadeMedicaEnum", "TipoEstabelecimentoEnum", "TipoExameEnum",
                "TipoFaltaEnum", "TipoLogradouroEnum", "TipoMetodoContraceptivoEnum",
                "TipoNotificacaoEnum", "TipoPlantaoEnum", "TipoPontoEnum", "TipoProcedimentoEnum",
                "TipoProfissionalEnum", "TipoReacaoAlergicaEnum", "TipoResponsavelEnum",
                "TipoServicoSaudeEnum", "TipoUsuarioSistemaEnum", "TipoVacinaEnum",
                "TipoVinculoProfissionalEnum", "TipoVisitaDomiciliarEnum", "UnidadeMedidaEnum",
                "ViaAdministracaoEnum", "ViaAdministracaoMedicamentoEnum", "ViaAdministracaoVacinaEnum",
                "ZonaDomicilioEnum"
        };

        for (String enumName : enumNames) {
            try {
                Class<?> clazz = Class.forName(ENUMS_PACKAGE + "." + enumName);
                if (clazz.isEnum()) {
                    enumClasses.add(clazz);
                }
            } catch (ClassNotFoundException e) {
                log.debug("Enum não encontrado na lista hardcoded: {}", enumName);
            }
        }

        return enumClasses;
    }

    private EnumInfoResponse processarEnum(Class<?> enumClass) {
        try {
            if (!enumClass.isEnum()) {
                return null;
            }

            String nomeClasse = enumClass.getSimpleName();
            String nomeEnum = formatarNomeEnum(nomeClasse);

            Object[] enumValues = enumClass.getEnumConstants();
            List<EnumItemResponse> valores = new ArrayList<>();

            for (Object enumValue : enumValues) {
                EnumItemResponse item = extrairItemEnum(enumValue);
                if (item != null) {
                    valores.add(item);
                }
            }

            return EnumInfoResponse.builder()
                    .nomeEnum(nomeEnum)
                    .nomeClasse(nomeClasse)
                    .valores(valores)
                    .build();

        } catch (Exception e) {
            log.warn("Erro ao processar enum {}: {}", enumClass.getSimpleName(), e.getMessage());
            return null;
        }
    }

    private EnumItemResponse extrairItemEnum(Object enumValue) {
        try {
            String nome = enumValue.toString();
            Integer codigo = null;
            String descricao = null;

            try {
                Method getCodigoMethod = enumValue.getClass().getMethod("getCodigo");
                Object codigoObj = getCodigoMethod.invoke(enumValue);
                if (codigoObj instanceof Integer) {
                    codigo = (Integer) codigoObj;
                }
            } catch (NoSuchMethodException e) {

            } catch (Exception e) {
                log.debug("Erro ao obter código do enum: {}", e.getMessage());
            }

            try {
                Method getDescricaoMethod = enumValue.getClass().getMethod("getDescricao");
                Object descricaoObj = getDescricaoMethod.invoke(enumValue);
                if (descricaoObj instanceof String) {
                    descricao = (String) descricaoObj;
                }
            } catch (NoSuchMethodException e) {

            } catch (Exception e) {
                log.debug("Erro ao obter descrição do enum: {}", e.getMessage());
            }

            return EnumItemResponse.builder()
                    .nome(nome)
                    .codigo(codigo)
                    .descricao(descricao)
                    .build();

        } catch (Exception e) {
            log.warn("Erro ao extrair item do enum: {}", e.getMessage());
            return null;
        }
    }

    private String formatarNomeEnum(String nomeClasse) {

        String nome = nomeClasse;
        if (nome.endsWith("Enum")) {
            nome = nome.substring(0, nome.length() - 4);
        }

        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < nome.length(); i++) {
            char c = nome.charAt(i);
            if (i > 0 && Character.isUpperCase(c)) {
                resultado.append(' ');
            }
            resultado.append(c);
        }

        return resultado.toString();
    }

    @Override
    public List<String> listarNomesEnums() {
        log.debug("Listando nomes de todos os enums");

        List<String> nomes = new ArrayList<>();
        Set<Class<?>> enumClasses = getEnumClasses();

        for (Class<?> enumClass : enumClasses) {
            nomes.add(enumClass.getSimpleName());
        }

        nomes.sort(String::compareTo);
        return nomes;
    }

    @Override
    public EnumInfoResponse obterEnumPorNome(String nomeEnum) {
        log.debug("Buscando enum por nome: {}", nomeEnum);

        if (nomeEnum == null || nomeEnum.trim().isEmpty()) {
            log.warn("Tentativa de buscar enum com nome vazio");
            throw new BadRequestException("Nome do enum não pode ser vazio");
        }

        String nomeNormalizado = nomeEnum.trim();
        if (!nomeNormalizado.endsWith("Enum")) {
            nomeNormalizado = nomeNormalizado + "Enum";
        }

        try {
            Class<?> enumClass = Class.forName(ENUMS_PACKAGE + "." + nomeNormalizado);
            if (enumClass.isEnum()) {
                return processarEnum(enumClass);
            } else {
                log.warn("Classe encontrada não é um enum. Nome: {}", nomeNormalizado);
                throw new BadRequestException("Classe encontrada não é um enum: " + nomeNormalizado);
            }
        } catch (ClassNotFoundException e) {
            log.warn("Enum não encontrado. Nome: {}", nomeNormalizado);
            throw new BadRequestException("Enum não encontrado: " + nomeNormalizado);
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao buscar enum. Nome: {}, Erro: {}", nomeNormalizado, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao buscar enum. Nome: {}, Exception: {}", nomeNormalizado, e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao buscar enum: " + nomeNormalizado, e);
        }
    }
}
