package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum TipoEquipamentoEnum {

    RAIOS_X(1, "Raio-X"),
    TOMOGRAFIA_COMPUTADORIZADA(2, "Tomografia Computadorizada"),
    RESSONANCIA_MAGNETICA(3, "Ressonância Magnética"),
    ULTRASSOM(4, "Ultrassom"),
    MAMOGRAFIA(5, "Mamografia"),
    DENSITOMETRIA_OSSEA(6, "Densitometria Óssea"),
    ECOCARDIOGRAMA(7, "Ecocardiograma"),
    DOPPLER(8, "Doppler"),
    FLUOROSCOPIA(9, "Fluoroscopia"),
    RADIOGRAFIA_DENTAL(10, "Radiografia Dental"),
    PET_SCAN(11, "PET Scan"),
    CINTILOGRAFIA(12, "Cintilografia"),

    ANALISADOR_BIOQUIMICO(20, "Analisador Bioquímico"),
    HEMATOLOGIA(21, "Equipamento de Hematologia"),
    URINALISE(22, "Equipamento de Urinálise"),
    SOROLOGIA(23, "Equipamento de Sorologia"),
    MICROSCOPIO(24, "Microscópio"),
    CENTRIFUGA(25, "Centrífuga"),
    ESTUFA(26, "Estufa"),
    AUTOCLAVE(27, "Autoclave"),
    BALANCA_PRECISAO(28, "Balança de Precisão"),
    REFRIGERADOR_LABORATORIO(29, "Refrigerador de Laboratório"),
    FREEZER_LABORATORIO(30, "Freezer de Laboratório"),

    MONITOR_CARDIACO(40, "Monitor Cardíaco"),
    MONITOR_PRESSAO_ARTERIAL(41, "Monitor de Pressão Arterial"),
    OXIMETRO_PULSO(42, "Oxímetro de Pulso"),
    ELETROCARDIOGRAFO(43, "Eletrocardiógrafo"),
    HOLTER(44, "Holter"),
    MAPA(45, "MAPA - Monitorização Ambulatorial da Pressão Arterial"),
    ELETROENCEFALOGRAFO(46, "Eletroencefalógrafo"),
    POLISSONOGRAFIA(47, "Polissonografia"),

    VENTILADOR_MECANICO(60, "Ventilador Mecânico"),
    CPAP(61, "CPAP - Pressão Positiva Contínua"),
    BIPAP(62, "BiPAP - Pressão Positiva Bifásica"),
    NEBULIZADOR(63, "Nebulizador"),
    ASPIRADOR_SECRECOES(64, "Aspirador de Secreções"),
    OXIGENOTERAPIA(65, "Equipamento de Oxigenoterapia"),
    RESPIROMETRO(66, "Respirometro"),

    MESA_CIRURGICA(80, "Mesa Cirúrgica"),
    LAMPADA_CIRURGICA(81, "Lâmpada Cirúrgica"),
    BISTURI_ELETRICO(82, "Bisturi Elétrico"),
    ELECTROCAUTERIO(83, "Eletrocautério"),
    ENDOSCOPIO(84, "Endoscópio"),
    LAPAROSCOPIO(85, "Laparoscópio"),
    ARTROSCOPIO(86, "Artroscópio"),
    COLONOSCOPIO(87, "Colonoscópio"),
    BRONCOSCOPIO(88, "Broncoscópio"),
    DESFIBRILADOR(89, "Desfibrilador"),
    CARDIOVERSOR(90, "Cardioversor"),
    BOMBA_INFUSAO(91, "Bomba de Infusão"),
    BOMBA_SERINGA(92, "Bomba de Seringa"),
    ELETROBISTURI(93, "Eletrobisturi"),

    FISIOTERAPIA_ELETROTERAPIA(110, "Equipamento de Eletroterapia"),
    ULTRASSOM_TERAPEUTICO(111, "Ultrassom Terapêutico"),
    LASER_TERAPEUTICO(112, "Laser Terapêutico"),
    MAGNETOTERAPIA(113, "Magnetoterapia"),
    CRIOTERAPIA(114, "Crioterapia"),
    PARAFINA(115, "Equipamento de Parafina"),
    BANHO_MARIA(116, "Banho Maria"),
    MESA_FISIOTERAPIA(117, "Mesa de Fisioterapia"),
    BICICLETA_ERGOMETRICA(118, "Bicicleta Ergométrica"),
    ESTEIRA_ERGOMETRICA(119, "Esteira Ergométrica"),

    CADEIRA_ODONTOLOGICA(130, "Cadeira Odontológica"),
    MOTOR_ODONTOLOGICO(131, "Motor Odontológico"),
    APARELHO_RAIO_X_ODONTOLOGICO(132, "Aparelho de Raio-X Odontológico"),
    AUTOCLAVE_ODONTOLOGICO(133, "Autoclave Odontológico"),
    ULTRASSOM_ODONTOLOGICO(134, "Ultrassom Odontológico"),
    LASER_ODONTOLOGICO(135, "Laser Odontológico"),

    AUTOCLAVE_ESTERILIZACAO(150, "Autoclave para Esterilização"),
    ESTUFA_ESTERILIZACAO(151, "Estufa para Esterilização"),
    LAVADORA_DESINFETADORA(152, "Lavadora Desinfetadora"),
    ULTRASSOM_LIMPEZA(153, "Ultrassom para Limpeza"),

    MACA(170, "Maca"),
    CADEIRA_RODAS(171, "Cadeira de Rodas"),
    BERCO(172, "Berço"),
    INCUBADORA(173, "Incubadora"),
    FOTOTERAPIA(174, "Fototerapia"),
    BANHO_MARIA_INFANTIL(175, "Banho Maria Infantil"),
    BALANCA_BEBE(176, "Balança de Bebê"),
    ESTETOSCOPIO(177, "Estetoscópio"),
    OTOSCOPIO(178, "Otoscópio"),
    OFTALMOSCOPIO(179, "Oftalmoscópio"),
    LARINGOSCOPIO(180, "Laringoscópio"),

    OUTRO(999, "Outro");

    private final Integer codigo;
    private final String descricao;

    TipoEquipamentoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoEquipamentoEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static TipoEquipamentoEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
