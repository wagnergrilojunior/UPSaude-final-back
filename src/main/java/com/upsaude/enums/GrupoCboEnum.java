package com.upsaude.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Enum que representa os grupos principais de ocupações da Classificação Brasileira de Ocupações (CBO).
 * Organizado por Grande Grupo e famílias ocupacionais relevantes.
 */
@Getter
public enum GrupoCboEnum {

    // ========== GRANDE GRUPO 1 - Membros das Forças Armadas, Policiais e Bombeiros Militares ==========
    FORCAS_ARMADAS(
            "FORCAS_ARMADAS",
            "Forças Armadas, Policiais e Bombeiros Militares",
            Arrays.asList("1"),
            "Ocupações relacionadas às Forças Armadas, Polícia Militar e Bombeiros Militares"
    ),

    // ========== GRANDE GRUPO 2 - Profissionais das Ciências e das Artes ==========
    
    // Subgrupo 22 - Profissionais das Ciências Biológicas, da Saúde e Afins
    MEDICOS(
            "MEDICOS",
            "Especialidades Médicas",
            Arrays.asList("225"),
            "Ocupações relacionadas a médicos e especialidades médicas"
    ),
    
    ENFERMAGEM(
            "ENFERMAGEM",
            "Enfermagem",
            Arrays.asList("2235"),
            "Ocupações relacionadas a enfermeiros, técnicos e auxiliares de enfermagem"
    ),
    
    ODONTOLOGIA(
            "ODONTOLOGIA",
            "Odontologia",
            Arrays.asList("2232"),
            "Ocupações relacionadas a cirurgiões-dentistas e especialistas em odontologia"
    ),
    
    PSICOLOGIA(
            "PSICOLOGIA",
            "Psicologia",
            Arrays.asList("2515"),
            "Ocupações relacionadas a psicólogos e psicanalistas"
    ),
    
    FISIOTERAPIA(
            "FISIOTERAPIA",
            "Fisioterapia",
            Arrays.asList("2236"),
            "Ocupações relacionadas a fisioterapeutas"
    ),
    
    FARMACIA(
            "FARMACIA",
            "Farmácia",
            Arrays.asList("2234"),
            "Ocupações relacionadas a farmacêuticos"
    ),
    
    NUTRICAO(
            "NUTRICAO",
            "Nutrição",
            Arrays.asList("2237"),
            "Ocupações relacionadas a nutricionistas"
    ),
    
    FONOAUDIOLOGIA(
            "FONOAUDIOLOGIA",
            "Fonoaudiologia",
            Arrays.asList("2238"),
            "Ocupações relacionadas a fonoaudiólogos"
    ),
    
    TERAPIA_OCUPACIONAL(
            "TERAPIA_OCUPACIONAL",
            "Terapia Ocupacional",
            Arrays.asList("2239"),
            "Ocupações relacionadas a terapeutas ocupacionais"
    ),
    
    BIOMEDICINA(
            "BIOMEDICINA",
            "Biomedicina",
            Arrays.asList("2211"),
            "Ocupações relacionadas a biomédicos"
    ),
    
    SERVICO_SOCIAL(
            "SERVICO_SOCIAL",
            "Serviço Social",
            Arrays.asList("2512"),
            "Ocupações relacionadas a assistentes sociais"
    ),
    
    VETERINARIA(
            "VETERINARIA",
            "Veterinária",
            Arrays.asList("2233"),
            "Ocupações relacionadas a médicos veterinários e zootecnistas"
    ),
    
    OUTROS_PROFISSIONAIS_SAUDE(
            "OUTROS_PROFISSIONAIS_SAUDE",
            "Outros Profissionais de Saúde",
            Arrays.asList("2231", "2241", "2242", "2243", "2244", "2245"),
            "Outras ocupações do setor de saúde não categorizadas especificamente"
    ),
    
    // Subgrupo 21 - Profissionais das Ciências Exatas, Físicas e Engenharias
    PROFISSIONAIS_CIENCIAS_EXATAS(
            "PROFISSIONAIS_CIENCIAS_EXATAS",
            "Profissionais das Ciências Exatas e Engenharias",
            Arrays.asList("211", "212", "213", "214", "215", "216", "217", "218", "219", "2212", "2213", "2214", "2215"),
            "Ocupações relacionadas a engenheiros, físicos, químicos, matemáticos e profissionais das ciências exatas"
    ),
    
    // Subgrupo 23 - Profissionais do Ensino
    PROFISSIONAIS_ENSINO(
            "PROFISSIONAIS_ENSINO",
            "Profissionais do Ensino",
            Arrays.asList("231", "232", "233", "234", "235", "236", "237", "238", "239"),
            "Ocupações relacionadas a professores e profissionais do ensino"
    ),
    
    // Subgrupo 24 - Profissionais do Direito
    PROFISSIONAIS_DIREITO(
            "PROFISSIONAIS_DIREITO",
            "Profissionais do Direito",
            Arrays.asList("241"),
            "Ocupações relacionadas a advogados, juízes e profissionais do direito"
    ),
    
    // Subgrupo 25 - Profissionais das Ciências Humanas e Sociais
    PROFISSIONAIS_CIENCIAS_HUMANAS(
            "PROFISSIONAIS_CIENCIAS_HUMANAS",
            "Profissionais das Ciências Humanas e Sociais",
            Arrays.asList("2511", "2513", "2514", "2516", "2517", "2518", "2519", "2521", "2522", "2523", "2524", "2525", "2526", "2527"),
            "Ocupações relacionadas a economistas, administradores, contadores, sociólogos e profissionais das ciências humanas"
    ),
    
    // Subgrupo 26 - Profissionais das Artes e Design
    PROFISSIONAIS_ARTES(
            "PROFISSIONAIS_ARTES",
            "Profissionais das Artes e Design",
            Arrays.asList("261", "262", "263", "264", "265", "266", "267", "268", "269"),
            "Ocupações relacionadas a artistas, designers, jornalistas e profissionais das artes"
    ),

    // ========== GRANDE GRUPO 3 - Técnicos de Nível Médio ==========
    
    TECNICOS_SAUDE(
            "TECNICOS_SAUDE",
            "Técnicos de Nível Médio em Saúde",
            Arrays.asList("3221", "3222", "3223", "3224", "3225", "3226", "3227"),
            "Ocupações relacionadas a técnicos e auxiliares de enfermagem, radiologia, laboratório e outras áreas da saúde"
    ),
    
    TECNICOS_ADMINISTRATIVOS(
            "TECNICOS_ADMINISTRATIVOS",
            "Técnicos Administrativos",
            Arrays.asList("311", "312", "313", "314", "315", "316", "317", "318", "319"),
            "Ocupações relacionadas a técnicos administrativos, contábeis, financeiros e comerciais"
    ),
    
    TECNICOS_CIENCIAS_EXATAS(
            "TECNICOS_CIENCIAS_EXATAS",
            "Técnicos em Ciências Exatas e Engenharias",
            Arrays.asList("321", "323", "324", "325", "326", "327", "328", "329"),
            "Ocupações relacionadas a técnicos em engenharia, química, física e áreas técnicas"
    ),
    
    OUTROS_TECNICOS(
            "OUTROS_TECNICOS",
            "Outros Técnicos de Nível Médio",
            Arrays.asList("331", "332", "333", "334", "335", "336", "337", "338", "339", "341", "342", "343", "344", "345", "346", "347", "348", "349"),
            "Outras ocupações técnicas de nível médio não categorizadas especificamente"
    ),

    // ========== GRANDE GRUPO 4 - Trabalhadores de Serviços Administrativos ==========
    
    ADMINISTRATIVOS(
            "ADMINISTRATIVOS",
            "Trabalhadores de Serviços Administrativos",
            Arrays.asList("41", "42"),
            "Ocupações relacionadas a trabalhadores administrativos, recepção, secretariado e serviços de escritório"
    ),

    // ========== GRANDE GRUPO 5 - Trabalhadores dos Serviços, Vendedores do Comércio ==========
    
    SERVICOS_GERAIS(
            "SERVICOS_GERAIS",
            "Trabalhadores dos Serviços Gerais",
            Arrays.asList("51", "52", "53", "54", "55", "56", "57", "58", "59"),
            "Ocupações relacionadas a trabalhadores de serviços gerais, limpeza, segurança, alimentação e vendas"
    ),
    
    VENDEDORES(
            "VENDEDORES",
            "Vendedores do Comércio",
            Arrays.asList("52"),
            "Ocupações relacionadas a vendedores, representantes comerciais e trabalhadores do comércio"
    ),

    // ========== GRANDE GRUPO 6 - Trabalhadores Agropecuários, Florestais e da Pesca ==========
    
    AGROPECUARIA(
            "AGROPECUARIA",
            "Trabalhadores Agropecuários, Florestais e da Pesca",
            Arrays.asList("6"),
            "Ocupações relacionadas a trabalhadores rurais, agropecuários, florestais e da pesca"
    ),

    // ========== GRANDE GRUPO 7 - Trabalhadores da Produção de Bens e Serviços Industriais ==========
    
    INDUSTRIA(
            "INDUSTRIA",
            "Trabalhadores da Indústria",
            Arrays.asList("7"),
            "Ocupações relacionadas a trabalhadores da produção industrial, manufatura e transformação"
    ),

    // ========== GRANDE GRUPO 8 - Trabalhadores da Construção e Manutenção ==========
    
    CONSTRUCAO(
            "CONSTRUCAO",
            "Trabalhadores da Construção e Manutenção",
            Arrays.asList("8"),
            "Ocupações relacionadas a trabalhadores da construção civil, manutenção de edifícios e estruturas"
    ),

    // ========== GRANDE GRUPO 9 - Trabalhadores de Manutenção e Reparo ==========
    
    MANUTENCAO_REPARO(
            "MANUTENCAO_REPARO",
            "Trabalhadores de Manutenção e Reparo",
            Arrays.asList("9"),
            "Ocupações relacionadas a trabalhadores de manutenção e reparo de equipamentos e máquinas"
    ),

    // ========== GRANDE GRUPO 0 - Trabalhadores da Produção de Bens e Serviços Artesanais ==========
    
    ARTESANATO(
            "ARTESANATO",
            "Trabalhadores Artesanais",
            Arrays.asList("0"),
            "Ocupações relacionadas a trabalhadores artesanais e produção manual"
    );

    private final String codigo;
    private final String nome;
    private final List<String> prefixos;
    private final String descricao;

    GrupoCboEnum(String codigo, String nome, List<String> prefixos, String descricao) {
        this.codigo = codigo;
        this.nome = nome;
        this.prefixos = prefixos;
        this.descricao = descricao;
    }

    /**
     * Verifica se um código CBO pertence a este grupo.
     * 
     * @param codigoCbo Código CBO a ser verificado (formato: 6 dígitos)
     * @return true se o código pertence ao grupo, false caso contrário
     */
    public boolean pertenceAoGrupo(String codigoCbo) {
        if (codigoCbo == null || codigoCbo.isBlank()) {
            return false;
        }
        
        String codigoLimpo = codigoCbo.trim();
        
        return prefixos.stream()
                .anyMatch(prefixo -> codigoLimpo.startsWith(prefixo));
    }

    /**
     * Busca um grupo pelo código.
     * 
     * @param codigo Código do grupo (ex: "MEDICOS")
     * @return Optional com o grupo encontrado ou vazio se não encontrado
     */
    public static Optional<GrupoCboEnum> fromCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            return Optional.empty();
        }
        
        String codigoUpper = codigo.trim().toUpperCase();
        
        return Arrays.stream(values())
                .filter(grupo -> grupo.codigo.equalsIgnoreCase(codigoUpper))
                .findFirst();
    }

    /**
     * Verifica se um código CBO pertence a algum grupo de saúde.
     * 
     * @param codigoCbo Código CBO a ser verificado
     * @return true se pertence a algum grupo de saúde, false caso contrário
     */
    public static boolean isGrupoSaude(GrupoCboEnum grupo) {
        if (grupo == null) {
            return false;
        }
        
        return grupo == MEDICOS ||
               grupo == ENFERMAGEM ||
               grupo == ODONTOLOGIA ||
               grupo == PSICOLOGIA ||
               grupo == FISIOTERAPIA ||
               grupo == FARMACIA ||
               grupo == NUTRICAO ||
               grupo == FONOAUDIOLOGIA ||
               grupo == TERAPIA_OCUPACIONAL ||
               grupo == BIOMEDICINA ||
               grupo == SERVICO_SOCIAL ||
               grupo == VETERINARIA ||
               grupo == OUTROS_PROFISSIONAIS_SAUDE ||
               grupo == TECNICOS_SAUDE;
    }
}

