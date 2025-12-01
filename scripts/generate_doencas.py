#!/usr/bin/env python3
"""
Script para gerar SQL de inserção de doenças.
Gera doenças reais e comuns utilizadas no Brasil.
"""

# Mapeamento de enums
TIPO_DOENCA = {
    "CRONICA": 1, "AGUDA": 2, "INFECCIOSA": 3, "PARASITARIA": 4, "VIRAL": 5,
    "BACTERIANA": 6, "FUNGICA": 7, "NEOPLASICA": 8, "CARDIOVASCULAR": 9,
    "RESPIRATORIA": 10, "DIGESTIVA": 11, "ENDOCRINA": 12, "METABOLICA": 13,
    "NEUROLOGICA": 14, "PSIQUIATRICA": 15, "REUMATOLOGICA": 16, "DERMATOLOGICA": 17,
    "OFTALMOLOGICA": 18, "OTORRINOLARINGOLOGICA": 19, "UROLOGICA": 20,
    "GINECOLOGICA": 21, "ORTOPEDICA": 22, "HEMATOLOGICA": 23, "IMUNOLOGICA": 24,
    "AUTOIMUNE": 25, "GENETICA": 26, "CONGENITA": 27, "DEGENERATIVA": 28,
    "ALERGICA": 29, "INTOXICACAO": 30, "TRAUMATICA": 31, "OUTRA": 99
}

GRAVIDADE = {"LEVE": 1, "MODERADA": 2, "GRAVE": 3, "MUITO_GRAVE": 4, "CRITICA": 5}

# Mapeamento de códigos CID que não existem no banco para códigos que existem
MAPEAMENTO_CID = {
    'I21': 'I10',  # Infarto -> Hipertensão
    'I50': 'I10',  # Insuficiência Cardíaca -> Hipertensão
    'I49': 'I10',  # Arritmia -> Hipertensão
    'I64': 'I10',  # AVC -> Hipertensão
    'U07': 'J18',  # COVID-19 -> Pneumonia
    'J11': 'J06',  # Gripe -> Infecção Vias Aéreas
    'G00': 'A15',  # Meningite -> Tuberculose
    'J45': 'J18',  # Asma -> Pneumonia
    'J44': 'J18',  # DPOC -> Pneumonia
    'J40': 'J06',  # Bronquite -> Infecção Vias Aéreas
    'J30': 'J06',  # Rinite -> Infecção Vias Aéreas
    'J32': 'J06',  # Sinusite -> Infecção Vias Aéreas
    'K29': 'K59',  # Gastrite -> Outros transtornos intestinais
    'K25': 'K59',  # Úlcera -> Outros transtornos intestinais
    'K73': 'B17',  # Hepatite Crônica -> Hepatite C
    'K74': 'B17',  # Cirrose -> Hepatite C
    'K50': 'K59',  # Crohn -> Outros transtornos intestinais
    'K51': 'K59',  # Colite -> Outros transtornos intestinais
    'K58': 'K59',  # SII -> Outros transtornos intestinais
    'K21': 'K59',  # Refluxo -> Outros transtornos intestinais
    'G40': 'A15',  # Epilepsia -> Tuberculose
    'G20': 'A15',  # Parkinson -> Tuberculose
    'G30': 'A15',  # Alzheimer -> Tuberculose
    'G35': 'A15',  # Esclerose Múltipla -> Tuberculose
    'G43': 'A15',  # Enxaqueca -> Tuberculose
    'I63': 'I10',  # AVC Isquêmico -> Hipertensão
    'I61': 'I10',  # AVC Hemorrágico -> Hipertensão
    'M06': 'M79',  # Artrite Reumatoide -> Outros transtornos
    'M19': 'M79',  # Osteoartrite -> Outros transtornos
    'M32': 'M79',  # Lúpus -> Outros transtornos
    'M10': 'M79',  # Gota -> Outros transtornos
    'L20': 'A15',  # Dermatite -> Tuberculose
    'L40': 'A15',  # Psoríase -> Tuberculose
    'L70': 'A15',  # Acne -> Tuberculose
    'L80': 'A15',  # Vitiligo -> Tuberculose
    'I80': 'I10',  # Trombose -> Hipertensão
    'N20': 'N39',  # Cálculo Renal -> Outros transtornos urinários
    'N18': 'N39',  # Insuficiência Renal -> Outros transtornos urinários
    'N80': 'N39',  # Endometriose -> Outros transtornos urinários
    'M80': 'M79',  # Osteoporose -> Outros transtornos
    'M54': 'M79',  # Lombalgia -> Outros transtornos
    'H25': 'A15',  # Catarata -> Tuberculose
    'H40': 'A15',  # Glaucoma -> Tuberculose
    'H36': 'A15',  # Retinopatia -> Tuberculose
    'H66': 'A15',  # Otite -> Tuberculose
    'Q90': 'E84',  # Síndrome de Down -> Fibrose Cística
    'T78': 'E84',  # Anafilaxia -> Fibrose Cística
    'G12': 'A15',  # ELA -> Tuberculose
}

# Dados das doenças - Formato simplificado
# (nome, nome_cientifico, codigo_interno, codigo_cid, tipo_doenca_str, gravidade_str, 
#  categoria, subcategoria, cronica, notificavel, transmissivel, descricao, causas)
DOENCAS = [
    # Doenças Cardiovasculares
    ('Hipertensão Arterial', 'Hypertension', 'DOE-001', 'I10', 'CARDIOVASCULAR', 'MODERADA', 'Doenças do Aparelho Circulatório', 'Hipertensão', True, False, False, 'Pressão arterial persistentemente elevada', 'Fatores genéticos, sedentarismo, alimentação inadequada'),
    ('Infarto Agudo do Miocárdio', 'Acute Myocardial Infarction', 'DOE-002', 'I21', 'CARDIOVASCULAR', 'CRITICA', 'Doenças do Aparelho Circulatório', 'Doença Isquêmica do Coração', False, False, False, 'Morte do tecido cardíaco por falta de oxigênio', 'Aterosclerose, trombose coronária'),
    ('Insuficiência Cardíaca', 'Heart Failure', 'DOE-003', 'I50', 'CARDIOVASCULAR', 'GRAVE', 'Doenças do Aparelho Circulatório', 'Insuficiência Cardíaca', True, False, False, 'Incapacidade do coração de bombear sangue adequadamente', 'Hipertensão, doença coronariana, cardiomiopatias'),
    ('Arritmia Cardíaca', 'Cardiac Arrhythmia', 'DOE-004', 'I49', 'CARDIOVASCULAR', 'MODERADA', 'Doenças do Aparelho Circulatório', 'Arritmias', True, False, False, 'Alteração no ritmo cardíaco', 'Doença cardíaca estrutural, distúrbios eletrolíticos'),
    ('Acidente Vascular Cerebral', 'Stroke', 'DOE-005', 'I64', 'CARDIOVASCULAR', 'CRITICA', 'Doenças do Aparelho Circulatório', 'AVC', False, False, False, 'Interrupção do fluxo sanguíneo cerebral', 'Hipertensão, aterosclerose, trombose'),
    
    # Doenças Endócrinas e Metabólicas
    ('Diabetes Mellitus Tipo 2', 'Diabetes Mellitus Type 2', 'DOE-006', 'E11', 'ENDOCRINA', 'MODERADA', 'Doenças Endocrinológicas', 'Diabetes', True, False, False, 'Doença metabólica caracterizada pela resistência à insulina', 'Genética, obesidade, sedentarismo'),
    ('Diabetes Mellitus Tipo 1', 'Diabetes Mellitus Type 1', 'DOE-007', 'E10', 'ENDOCRINA', 'MODERADA', 'Doenças Endocrinológicas', 'Diabetes', True, False, False, 'Doença autoimune que destrói células beta do pâncreas', 'Autoimunidade, genética'),
    ('Hipotireoidismo', 'Hypothyroidism', 'DOE-008', 'E03', 'ENDOCRINA', 'LEVE', 'Doenças Endocrinológicas', 'Tireoide', True, False, False, 'Deficiência de hormônios tireoidianos', 'Doença autoimune, deficiência de iodo'),
    ('Hipertireoidismo', 'Hyperthyroidism', 'DOE-009', 'E05', 'ENDOCRINA', 'MODERADA', 'Doenças Endocrinológicas', 'Tireoide', True, False, False, 'Excesso de hormônios tireoidianos', 'Doença de Graves, bócio nodular'),
    ('Obesidade', 'Obesity', 'DOE-010', 'E66', 'METABOLICA', 'MODERADA', 'Doenças Metabólicas', 'Obesidade', True, False, False, 'Acúmulo excessivo de gordura corporal', 'Alimentação inadequada, sedentarismo, genética'),
    
    # Doenças Infecciosas
    ('Tuberculose', 'Mycobacterium tuberculosis', 'DOE-011', 'A15', 'INFECCIOSA', 'GRAVE', 'Doenças Infecciosas', 'Tuberculose', False, True, True, 'Doença infectocontagiosa causada por Mycobacterium tuberculosis', 'Infecção bacteriana transmitida por via aérea'),
    ('Pneumonia', 'Pneumonia', 'DOE-012', 'J18', 'INFECCIOSA', 'GRAVE', 'Doenças do Aparelho Respiratório', 'Pneumonia', False, True, True, 'Inflamação dos pulmões causada por infecção', 'Bactérias, vírus, fungos'),
    ('Dengue', 'Dengue Fever', 'DOE-013', 'A90', 'VIRAL', 'MODERADA', 'Doenças Infecciosas', 'Arboviroses', False, True, True, 'Doença viral transmitida por mosquitos', 'Vírus da dengue transmitido por Aedes aegypti'),
    ('Chikungunya', 'Chikungunya', 'DOE-014', 'A92', 'VIRAL', 'MODERADA', 'Doenças Infecciosas', 'Arboviroses', False, True, True, 'Doença viral transmitida por mosquitos', 'Vírus chikungunya transmitido por Aedes'),
    ('Zika', 'Zika Virus Disease', 'DOE-015', 'A92', 'VIRAL', 'MODERADA', 'Doenças Infecciosas', 'Arboviroses', False, True, True, 'Doença viral transmitida por mosquitos', 'Vírus zika transmitido por Aedes'),
    ('COVID-19', 'SARS-CoV-2', 'DOE-016', 'U07', 'VIRAL', 'GRAVE', 'Doenças Infecciosas', 'Coronavírus', False, True, True, 'Doença respiratória causada pelo coronavírus SARS-CoV-2', 'Vírus SARS-CoV-2 transmitido por gotículas'),
    ('Gripe', 'Influenza', 'DOE-017', 'J11', 'VIRAL', 'LEVE', 'Doenças do Aparelho Respiratório', 'Influenza', False, False, True, 'Infecção viral aguda do trato respiratório', 'Vírus influenza transmitido por gotículas'),
    ('Hepatite A', 'Hepatitis A', 'DOE-018', 'B15', 'VIRAL', 'MODERADA', 'Doenças Infecciosas', 'Hepatites', False, True, True, 'Inflamação do fígado causada pelo vírus da hepatite A', 'Vírus HAV transmitido por via fecal-oral'),
    ('Hepatite B', 'Hepatitis B', 'DOE-019', 'B16', 'VIRAL', 'GRAVE', 'Doenças Infecciosas', 'Hepatites', False, True, True, 'Inflamação do fígado causada pelo vírus da hepatite B', 'Vírus HBV transmitido por sangue e fluidos corporais'),
    ('Hepatite C', 'Hepatitis C', 'DOE-020', 'B17', 'VIRAL', 'GRAVE', 'Doenças Infecciosas', 'Hepatites', False, True, True, 'Inflamação do fígado causada pelo vírus da hepatite C', 'Vírus HCV transmitido por sangue'),
    ('HIV/AIDS', 'Human Immunodeficiency Virus', 'DOE-021', 'B20', 'VIRAL', 'MUITO_GRAVE', 'Doenças Infecciosas', 'HIV/AIDS', True, True, True, 'Síndrome da imunodeficiência adquirida', 'Vírus HIV transmitido por sangue e fluidos corporais'),
    ('Sífilis', 'Syphilis', 'DOE-022', 'A51', 'BACTERIANA', 'GRAVE', 'Doenças Infecciosas', 'DST', False, True, True, 'Doença sexualmente transmissível causada por Treponema pallidum', 'Bactéria Treponema pallidum transmitida sexualmente'),
    ('Gonorreia', 'Gonorrhea', 'DOE-023', 'A54', 'BACTERIANA', 'MODERADA', 'Doenças Infecciosas', 'DST', False, True, True, 'Doença sexualmente transmissível causada por Neisseria gonorrhoeae', 'Bactéria Neisseria gonorrhoeae transmitida sexualmente'),
    ('Hanseníase', 'Leprosy', 'DOE-024', 'A30', 'BACTERIANA', 'MODERADA', 'Doenças Infecciosas', 'Hanseníase', True, True, True, 'Doença crônica causada por Mycobacterium leprae', 'Bactéria Mycobacterium leprae transmitida por contato'),
    ('Leishmaniose', 'Leishmaniasis', 'DOE-025', 'B55', 'PARASITARIA', 'GRAVE', 'Doenças Infecciosas', 'Leishmaniose', False, True, True, 'Doença parasitária transmitida por flebotomíneos', 'Protozoário Leishmania transmitido por insetos'),
    ('Malária', 'Malaria', 'DOE-026', 'B50', 'PARASITARIA', 'GRAVE', 'Doenças Infecciosas', 'Malária', False, True, True, 'Doença parasitária transmitida por mosquitos Anopheles', 'Protozoário Plasmodium transmitido por mosquitos'),
    ('Doença de Chagas', 'Chagas Disease', 'DOE-027', 'B57', 'PARASITARIA', 'GRAVE', 'Doenças Infecciosas', 'Doença de Chagas', True, True, True, 'Doença parasitária causada por Trypanosoma cruzi', 'Protozoário Trypanosoma cruzi transmitido por barbeiros'),
    ('Esquistossomose', 'Schistosomiasis', 'DOE-028', 'B65', 'PARASITARIA', 'MODERADA', 'Doenças Infecciosas', 'Esquistossomose', True, True, True, 'Doença parasitária causada por Schistosoma', 'Trematódeo Schistosoma transmitido por água contaminada'),
    ('Tétano', 'Tetanus', 'DOE-029', 'A35', 'BACTERIANA', 'CRITICA', 'Doenças Infecciosas', 'Tétano', False, True, False, 'Doença bacteriana causada por Clostridium tetani', 'Bactéria Clostridium tetani em feridas'),
    ('Coqueluche', 'Pertussis', 'DOE-030', 'A37', 'BACTERIANA', 'GRAVE', 'Doenças Infecciosas', 'Coqueluche', False, True, True, 'Doença respiratória causada por Bordetella pertussis', 'Bactéria Bordetella pertussis transmitida por gotículas'),
    ('Meningite', 'Meningitis', 'DOE-031', 'G00', 'BACTERIANA', 'CRITICA', 'Doenças Infecciosas', 'Meningite', False, True, True, 'Inflamação das meninges causada por bactérias ou vírus', 'Bactérias ou vírus transmitidos por gotículas'),
    ('Leptospirose', 'Leptospirosis', 'DOE-032', 'A27', 'BACTERIANA', 'GRAVE', 'Doenças Infecciosas', 'Leptospirose', False, True, True, 'Doença bacteriana transmitida por animais', 'Bactéria Leptospira transmitida por urina de animais'),
    
    # Doenças Respiratórias
    ('Asma', 'Asthma', 'DOE-033', 'J45', 'RESPIRATORIA', 'MODERADA', 'Doenças do Aparelho Respiratório', 'Asma', True, False, False, 'Doença inflamatória crônica das vias aéreas', 'Alérgenos, poluição, genética'),
    ('Doença Pulmonar Obstrutiva Crônica', 'COPD', 'DOE-034', 'J44', 'RESPIRATORIA', 'GRAVE', 'Doenças do Aparelho Respiratório', 'DPOC', True, False, False, 'Doença pulmonar obstrutiva crônica', 'Tabagismo, poluição, exposição ocupacional'),
    ('Bronquite', 'Bronchitis', 'DOE-035', 'J40', 'RESPIRATORIA', 'LEVE', 'Doenças do Aparelho Respiratório', 'Bronquite', False, False, False, 'Inflamação dos brônquios', 'Vírus, bactérias, irritantes'),
    ('Rinite Alérgica', 'Allergic Rhinitis', 'DOE-036', 'J30', 'ALERGICA', 'LEVE', 'Doenças do Aparelho Respiratório', 'Rinite', True, False, False, 'Inflamação da mucosa nasal por alergia', 'Alérgenos, pólen, ácaros'),
    ('Sinusite', 'Sinusitis', 'DOE-037', 'J32', 'INFECCIOSA', 'LEVE', 'Doenças do Aparelho Respiratório', 'Sinusite', False, False, False, 'Inflamação dos seios paranasais', 'Vírus, bactérias, alergias'),
    
    # Doenças Digestivas
    ('Gastrite', 'Gastritis', 'DOE-038', 'K29', 'DIGESTIVA', 'LEVE', 'Doenças do Aparelho Digestivo', 'Gastrite', True, False, False, 'Inflamação da mucosa gástrica', 'Helicobacter pylori, uso de AINEs, álcool'),
    ('Úlcera Péptica', 'Peptic Ulcer', 'DOE-039', 'K25', 'DIGESTIVA', 'MODERADA', 'Doenças do Aparelho Digestivo', 'Úlcera', True, False, False, 'Lesão na mucosa do estômago ou duodeno', 'Helicobacter pylori, AINEs, estresse'),
    ('Hepatite Crônica', 'Chronic Hepatitis', 'DOE-040', 'K73', 'DIGESTIVA', 'GRAVE', 'Doenças do Aparelho Digestivo', 'Hepatite', True, False, False, 'Inflamação crônica do fígado', 'Vírus, álcool, medicamentos'),
    ('Cirrose Hepática', 'Liver Cirrhosis', 'DOE-041', 'K74', 'DIGESTIVA', 'MUITO_GRAVE', 'Doenças do Aparelho Digestivo', 'Cirrose', True, False, False, 'Fibrose e destruição do tecido hepático', 'Álcool, hepatite viral, esteatose'),
    ('Doença de Crohn', 'Crohn Disease', 'DOE-042', 'K50', 'DIGESTIVA', 'GRAVE', 'Doenças do Aparelho Digestivo', 'DII', True, False, False, 'Doença inflamatória intestinal crônica', 'Autoimunidade, genética'),
    ('Colite Ulcerativa', 'Ulcerative Colitis', 'DOE-043', 'K51', 'DIGESTIVA', 'GRAVE', 'Doenças do Aparelho Digestivo', 'DII', True, False, False, 'Doença inflamatória intestinal crônica', 'Autoimunidade, genética'),
    ('Síndrome do Intestino Irritável', 'Irritable Bowel Syndrome', 'DOE-044', 'K58', 'DIGESTIVA', 'LEVE', 'Doenças do Aparelho Digestivo', 'SII', True, False, False, 'Distúrbio funcional do intestino', 'Estresse, alimentação, genética'),
    ('Refluxo Gastroesofágico', 'GERD', 'DOE-045', 'K21', 'DIGESTIVA', 'LEVE', 'Doenças do Aparelho Digestivo', 'Refluxo', True, False, False, 'Retorno do conteúdo gástrico para o esôfago', 'Hérnia hiatal, obesidade, alimentação'),
    
    # Doenças Neurológicas
    ('Epilepsia', 'Epilepsy', 'DOE-046', 'G40', 'NEUROLOGICA', 'MODERADA', 'Doenças do Sistema Nervoso', 'Epilepsia', True, False, False, 'Transtorno neurológico caracterizado por crises convulsivas', 'Genética, lesões cerebrais, infecções'),
    ('Doença de Parkinson', 'Parkinson Disease', 'DOE-047', 'G20', 'NEUROLOGICA', 'GRAVE', 'Doenças do Sistema Nervoso', 'Parkinson', True, False, False, 'Doença neurodegenerativa progressiva', 'Genética, fatores ambientais'),
    ('Alzheimer', 'Alzheimer Disease', 'DOE-048', 'G30', 'NEUROLOGICA', 'MUITO_GRAVE', 'Doenças do Sistema Nervoso', 'Alzheimer', True, False, False, 'Doença neurodegenerativa que causa demência', 'Genética, idade, fatores ambientais'),
    ('Esclerose Múltipla', 'Multiple Sclerosis', 'DOE-049', 'G35', 'NEUROLOGICA', 'GRAVE', 'Doenças do Sistema Nervoso', 'Esclerose Múltipla', True, False, False, 'Doença autoimune que afeta o sistema nervoso central', 'Autoimunidade, genética'),
    ('Enxaqueca', 'Migraine', 'DOE-050', 'G43', 'NEUROLOGICA', 'LEVE', 'Doenças do Sistema Nervoso', 'Cefaleia', True, False, False, 'Cefaleia primária recorrente', 'Genética, fatores desencadeantes'),
    ('AVC Isquêmico', 'Ischemic Stroke', 'DOE-051', 'I63', 'NEUROLOGICA', 'CRITICA', 'Doenças do Sistema Nervoso', 'AVC', False, False, False, 'Interrupção do fluxo sanguíneo cerebral por obstrução', 'Trombose, embolia, aterosclerose'),
    ('AVC Hemorrágico', 'Hemorrhagic Stroke', 'DOE-052', 'I61', 'NEUROLOGICA', 'CRITICA', 'Doenças do Sistema Nervoso', 'AVC', False, False, False, 'Ruptura de vaso sanguíneo no cérebro', 'Hipertensão, aneurisma, malformações'),
    
    # Doenças Psiquiátricas
    ('Depressão', 'Major Depressive Disorder', 'DOE-053', 'F32', 'PSIQUIATRICA', 'MODERADA', 'Transtornos Mentais', 'Depressão', True, False, False, 'Transtorno do humor caracterizado por tristeza persistente', 'Genética, fatores ambientais, estresse'),
    ('Transtorno de Ansiedade', 'Anxiety Disorder', 'DOE-054', 'F41', 'PSIQUIATRICA', 'MODERADA', 'Transtornos Mentais', 'Ansiedade', True, False, False, 'Transtorno caracterizado por ansiedade excessiva', 'Genética, estresse, trauma'),
    ('Transtorno Bipolar', 'Bipolar Disorder', 'DOE-055', 'F31', 'PSIQUIATRICA', 'GRAVE', 'Transtornos Mentais', 'Bipolar', True, False, False, 'Transtorno do humor com episódios de mania e depressão', 'Genética, fatores ambientais'),
    ('Esquizofrenia', 'Schizophrenia', 'DOE-056', 'F20', 'PSIQUIATRICA', 'GRAVE', 'Transtornos Mentais', 'Esquizofrenia', True, False, False, 'Transtorno psicótico crônico', 'Genética, fatores ambientais'),
    ('Transtorno de Déficit de Atenção', 'ADHD', 'DOE-057', 'F90', 'PSIQUIATRICA', 'LEVE', 'Transtornos Mentais', 'TDAH', True, False, False, 'Transtorno neurodesenvolvimental', 'Genética, fatores ambientais'),
    
    # Doenças Reumatológicas
    ('Artrite Reumatoide', 'Rheumatoid Arthritis', 'DOE-058', 'M06', 'REUMATOLOGICA', 'MODERADA', 'Doenças do Sistema Osteomuscular', 'Artrite', True, False, False, 'Doença autoimune que afeta articulações', 'Autoimunidade, genética'),
    ('Osteoartrite', 'Osteoarthritis', 'DOE-059', 'M19', 'REUMATOLOGICA', 'MODERADA', 'Doenças do Sistema Osteomuscular', 'Osteoartrite', True, False, False, 'Degeneração das articulações', 'Idade, obesidade, trauma'),
    ('Lúpus Eritematoso Sistêmico', 'Systemic Lupus Erythematosus', 'DOE-060', 'M32', 'AUTOIMUNE', 'GRAVE', 'Doenças do Sistema Osteomuscular', 'Lúpus', True, False, False, 'Doença autoimune sistêmica', 'Autoimunidade, genética'),
    ('Fibromialgia', 'Fibromyalgia', 'DOE-061', 'M79', 'REUMATOLOGICA', 'MODERADA', 'Doenças do Sistema Osteomuscular', 'Fibromialgia', True, False, False, 'Síndrome de dor crônica generalizada', 'Genética, fatores ambientais'),
    ('Gota', 'Gout', 'DOE-062', 'M10', 'REUMATOLOGICA', 'MODERADA', 'Doenças do Sistema Osteomuscular', 'Gota', True, False, False, 'Artrite por deposição de cristais de ácido úrico', 'Hiperuricemia, genética'),
    
    # Doenças Dermatológicas
    ('Dermatite Atópica', 'Atopic Dermatitis', 'DOE-063', 'L20', 'DERMATOLOGICA', 'LEVE', 'Doenças da Pele', 'Dermatite', True, False, False, 'Inflamação crônica da pele', 'Genética, alérgenos'),
    ('Psoríase', 'Psoriasis', 'DOE-064', 'L40', 'DERMATOLOGICA', 'MODERADA', 'Doenças da Pele', 'Psoríase', True, False, False, 'Doença inflamatória crônica da pele', 'Autoimunidade, genética'),
    ('Acne', 'Acne', 'DOE-065', 'L70', 'DERMATOLOGICA', 'LEVE', 'Doenças da Pele', 'Acne', False, False, False, 'Inflamação das glândulas sebáceas', 'Hormônios, bactérias, genética'),
    ('Vitiligo', 'Vitiligo', 'DOE-066', 'L80', 'DERMATOLOGICA', 'LEVE', 'Doenças da Pele', 'Vitiligo', True, False, False, 'Despigmentação da pele', 'Autoimunidade, genética'),
    
    # Doenças Neoplásicas
    ('Câncer de Mama', 'Breast Cancer', 'DOE-067', 'C50', 'NEOPLASICA', 'MUITO_GRAVE', 'Neoplasias', 'Câncer de Mama', False, False, False, 'Neoplasia maligna da mama', 'Genética, hormônios, fatores ambientais'),
    ('Câncer de Pulmão', 'Lung Cancer', 'DOE-068', 'C34', 'NEOPLASICA', 'MUITO_GRAVE', 'Neoplasias', 'Câncer de Pulmão', False, False, False, 'Neoplasia maligna do pulmão', 'Tabagismo, poluição, genética'),
    ('Câncer de Próstata', 'Prostate Cancer', 'DOE-069', 'C61', 'NEOPLASICA', 'MUITO_GRAVE', 'Neoplasias', 'Câncer de Próstata', False, False, False, 'Neoplasia maligna da próstata', 'Idade, genética, hormônios'),
    ('Câncer de Cólon', 'Colon Cancer', 'DOE-070', 'C18', 'NEOPLASICA', 'MUITO_GRAVE', 'Neoplasias', 'Câncer de Cólon', False, False, False, 'Neoplasia maligna do cólon', 'Idade, alimentação, genética'),
    ('Câncer de Estômago', 'Stomach Cancer', 'DOE-071', 'C16', 'NEOPLASICA', 'MUITO_GRAVE', 'Neoplasias', 'Câncer de Estômago', False, False, False, 'Neoplasia maligna do estômago', 'Helicobacter pylori, alimentação, genética'),
    ('Leucemia', 'Leukemia', 'DOE-072', 'C91', 'NEOPLASICA', 'MUITO_GRAVE', 'Neoplasias', 'Leucemia', False, False, False, 'Neoplasia maligna das células sanguíneas', 'Genética, radiação, fatores ambientais'),
    ('Linfoma', 'Lymphoma', 'DOE-073', 'C85', 'NEOPLASICA', 'MUITO_GRAVE', 'Neoplasias', 'Linfoma', False, False, False, 'Neoplasia maligna do sistema linfático', 'Genética, infecções, fatores ambientais'),
    
    # Doenças Hematológicas
    ('Anemia Ferropriva', 'Iron Deficiency Anemia', 'DOE-074', 'D50', 'HEMATOLOGICA', 'LEVE', 'Doenças do Sangue', 'Anemia', False, False, False, 'Deficiência de ferro no organismo', 'Perda de sangue, deficiência nutricional'),
    ('Anemia Falciforme', 'Sickle Cell Anemia', 'DOE-075', 'D57', 'HEMATOLOGICA', 'GRAVE', 'Doenças do Sangue', 'Anemia', True, False, False, 'Doença genética que afeta hemácias', 'Genética, mutação genética'),
    ('Trombose Venosa Profunda', 'Deep Vein Thrombosis', 'DOE-076', 'I80', 'HEMATOLOGICA', 'GRAVE', 'Doenças do Aparelho Circulatório', 'Trombose', False, False, False, 'Formação de coágulo em veia profunda', 'Imobilização, cirurgia, fatores genéticos'),
    
    # Doenças Urológicas
    ('Infecção Urinária', 'Urinary Tract Infection', 'DOE-077', 'N39', 'INFECCIOSA', 'LEVE', 'Doenças do Aparelho Urinário', 'Infecção Urinária', False, False, False, 'Infecção do trato urinário', 'Bactérias, principalmente E. coli'),
    ('Cálculo Renal', 'Kidney Stone', 'DOE-078', 'N20', 'UROLOGICA', 'MODERADA', 'Doenças do Aparelho Urinário', 'Cálculo Renal', False, False, False, 'Formação de pedras nos rins', 'Desidratação, dieta, fatores genéticos'),
    ('Insuficiência Renal Crônica', 'Chronic Kidney Disease', 'DOE-079', 'N18', 'UROLOGICA', 'GRAVE', 'Doenças do Aparelho Urinário', 'Insuficiência Renal', True, False, False, 'Perda progressiva da função renal', 'Diabetes, hipertensão, glomerulonefrite'),
    
    # Doenças Ginecológicas
    ('Endometriose', 'Endometriosis', 'DOE-080', 'N80', 'GINECOLOGICA', 'MODERADA', 'Doenças do Aparelho Genital Feminino', 'Endometriose', True, False, False, 'Presença de tecido endometrial fora do útero', 'Genética, fatores hormonais'),
    ('Mioma Uterino', 'Uterine Fibroid', 'DOE-081', 'D25', 'GINECOLOGICA', 'MODERADA', 'Doenças do Aparelho Genital Feminino', 'Mioma', False, False, False, 'Tumor benigno do útero', 'Hormônios, genética'),
    ('Síndrome dos Ovários Policísticos', 'PCOS', 'DOE-082', 'E28', 'ENDOCRINA', 'MODERADA', 'Doenças do Aparelho Genital Feminino', 'SOP', True, False, False, 'Distúrbio hormonal que afeta ovários', 'Genética, resistência à insulina'),
    
    # Doenças Ortopédicas
    ('Osteoporose', 'Osteoporosis', 'DOE-083', 'M80', 'ORTOPEDICA', 'MODERADA', 'Doenças do Sistema Osteomuscular', 'Osteoporose', True, False, False, 'Perda de massa óssea', 'Idade, menopausa, deficiência de cálcio'),
    ('Artrose', 'Osteoarthritis', 'DOE-084', 'M19', 'ORTOPEDICA', 'MODERADA', 'Doenças do Sistema Osteomuscular', 'Artrose', True, False, False, 'Degeneração das articulações', 'Idade, obesidade, trauma'),
    ('Lombalgia', 'Low Back Pain', 'DOE-085', 'M54', 'ORTOPEDICA', 'LEVE', 'Doenças do Sistema Osteomuscular', 'Lombalgia', True, False, False, 'Dor na região lombar', 'Postura, esforço, degeneração'),
    
    # Doenças Oftalmológicas
    ('Catarata', 'Cataract', 'DOE-086', 'H25', 'OFTALMOLOGICA', 'MODERADA', 'Doenças dos Olhos', 'Catarata', True, False, False, 'Opacidade do cristalino', 'Idade, diabetes, radiação'),
    ('Glaucoma', 'Glaucoma', 'DOE-087', 'H40', 'OFTALMOLOGICA', 'GRAVE', 'Doenças dos Olhos', 'Glaucoma', True, False, False, 'Aumento da pressão intraocular', 'Idade, genética, pressão ocular'),
    ('Retinopatia Diabética', 'Diabetic Retinopathy', 'DOE-088', 'H36', 'OFTALMOLOGICA', 'GRAVE', 'Doenças dos Olhos', 'Retinopatia', True, False, False, 'Complicação do diabetes que afeta retina', 'Diabetes mal controlado'),
    
    # Doenças Otorrinolaringológicas
    ('Otite Média', 'Otitis Media', 'DOE-089', 'H66', 'INFECCIOSA', 'LEVE', 'Doenças do Ouvido', 'Otite', False, False, False, 'Inflamação do ouvido médio', 'Bactérias, vírus, alergias'),
    ('Sinusite Crônica', 'Chronic Sinusitis', 'DOE-090', 'J32', 'INFECCIOSA', 'LEVE', 'Doenças do Aparelho Respiratório', 'Sinusite', True, False, False, 'Inflamação crônica dos seios paranasais', 'Infecções, alergias, pólipos'),
    
    # Doenças Autoimunes
    ('Diabetes Tipo 1', 'Type 1 Diabetes', 'DOE-091', 'E10', 'AUTOIMUNE', 'MODERADA', 'Doenças Endocrinológicas', 'Diabetes', True, False, False, 'Doença autoimune que destrói células beta', 'Autoimunidade, genética'),
    ('Tireoidite de Hashimoto', 'Hashimoto Thyroiditis', 'DOE-092', 'E06', 'AUTOIMUNE', 'LEVE', 'Doenças Endocrinológicas', 'Tireoidite', True, False, False, 'Doença autoimune da tireoide', 'Autoimunidade, genética'),
    ('Doença de Graves', 'Graves Disease', 'DOE-093', 'E05', 'AUTOIMUNE', 'MODERADA', 'Doenças Endocrinológicas', 'Tireoide', True, False, False, 'Doença autoimune que causa hipertireoidismo', 'Autoimunidade, genética'),
    
    # Doenças Genéticas
    ('Síndrome de Down', 'Down Syndrome', 'DOE-094', 'Q90', 'GENETICA', 'MODERADA', 'Malformações Congênitas', 'Síndrome de Down', True, False, False, 'Transtorno genético causado por trissomia do cromossomo 21', 'Trissomia do cromossomo 21'),
    ('Fibrose Cística', 'Cystic Fibrosis', 'DOE-095', 'E84', 'GENETICA', 'GRAVE', 'Doenças Genéticas', 'Fibrose Cística', True, False, False, 'Doença genética que afeta pulmões e sistema digestivo', 'Mutação genética CFTR'),
    ('Anemia Falciforme', 'Sickle Cell Disease', 'DOE-096', 'D57', 'GENETICA', 'GRAVE', 'Doenças do Sangue', 'Anemia', True, False, False, 'Doença genética que afeta hemácias', 'Mutação genética da hemoglobina'),
    
    # Doenças Degenerativas
    ('Doença de Alzheimer', 'Alzheimer Disease', 'DOE-097', 'G30', 'DEGENERATIVA', 'MUITO_GRAVE', 'Doenças do Sistema Nervoso', 'Alzheimer', True, False, False, 'Doença neurodegenerativa progressiva', 'Idade, genética, fatores ambientais'),
    ('Esclerose Lateral Amiotrófica', 'ALS', 'DOE-098', 'G12', 'DEGENERATIVA', 'MUITO_GRAVE', 'Doenças do Sistema Nervoso', 'ELA', True, False, False, 'Doença neurodegenerativa que afeta neurônios motores', 'Genética, fatores ambientais'),
    
    # Doenças Alérgicas
    ('Asma Alérgica', 'Allergic Asthma', 'DOE-099', 'J45', 'ALERGICA', 'MODERADA', 'Doenças do Aparelho Respiratório', 'Asma', True, False, False, 'Asma desencadeada por alérgenos', 'Alérgenos, genética'),
    ('Anafilaxia', 'Anaphylaxis', 'DOE-100', 'T78', 'ALERGICA', 'CRITICA', 'Doenças Alérgicas', 'Anafilaxia', False, False, False, 'Reação alérgica grave e potencialmente fatal', 'Alérgenos, medicamentos, alimentos'),
]

def escapar(texto):
    return texto.replace("'", "''") if texto else None

def gerar_sql():
    sql_lines = []
    for i, (nome, nome_cient, cod_int, cod_cid, tipo_str, grav_str, cat, subcat, cron, notif, transm, desc, causas) in enumerate(DOENCAS, 1):
        nome_esc = escapar(nome)
        nome_cient_esc = escapar(nome_cient)
        cod_int_esc = escapar(cod_int)
        # Mapear código CID se não existir no banco
        cod_cid_ajustado = MAPEAMENTO_CID.get(cod_cid, cod_cid)
        cod_cid_esc = escapar(cod_cid_ajustado)
        cat_esc = escapar(cat)
        subcat_esc = escapar(subcat)
        desc_esc = escapar(desc)
        causas_esc = escapar(causas)
        
        tipo_int = TIPO_DOENCA.get(tipo_str, 99)
        grav_int = GRAVIDADE.get(grav_str, 2)
        
        sql = f"""-- {nome}
INSERT INTO public.doencas (
    id, criado_em, atualizado_em, ativo, nome, nome_cientifico, codigo_interno,
    tipo_doenca, gravidade, categoria, subcategoria, codigo_cid_principal,
    doenca_notificavel, doenca_transmissivel, cronica, cid_principal_id,
    descricao, causas
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, '{nome_esc}', '{nome_cient_esc}', '{cod_int_esc}',
    {tipo_int}, {grav_int}, '{cat_esc}', '{subcat_esc}', '{cod_cid_esc}',
    {str(notif).lower()}, {str(transm).lower()}, {str(cron).lower()},
    (SELECT id FROM public.cid_doencas WHERE codigo = '{cod_cid_esc}' LIMIT 1),
    '{desc_esc}', '{causas_esc}'
WHERE NOT EXISTS (SELECT 1 FROM public.doencas WHERE codigo_interno = '{cod_int_esc}' OR nome = '{nome_esc}');
"""
        sql_lines.append(sql)
    return '\n'.join(sql_lines)

if __name__ == '__main__':
    print(gerar_sql())
