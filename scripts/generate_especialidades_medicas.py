#!/usr/bin/env python3
"""
Script para gerar SQL de inserção de especialidades médicas.
Gera especialidades médicas reconhecidas pelo Conselho Federal de Medicina (CFM).
"""

# Dados das especialidades médicas reconhecidas pelo CFM
# Formato: (nome, codigo, nome_cientifico, tipo_especialidade, codigo_cfm, codigo_cnes, 
#           area_atuacao, subarea, requer_residencia, requer_titulo_especialista,
#           descricao, area_atuacao_descricao, requisitos_formacao, observacoes)
ESPECIALIDADES = [
    # Especialidades Básicas
    ('Clínica Médica', '01', None, 1, '01', '01', 'Clínica', 'Medicina Geral', False, False,
     'Especialidade médica focada no atendimento de adultos, diagnóstico e tratamento de doenças não cirúrgicas',
     'Atendimento ambulatorial e hospitalar de pacientes adultos, com ênfase em medicina preventiva e tratamento de doenças clínicas',
     'Residência médica em Clínica Médica ou título de especialista pelo CFM',
     'Especialidade base da medicina. Base para outras especialidades médicas.'),
    
    ('Cirurgia Geral', '02', None, 2, '02', '02', 'Cirúrgica', 'Cirurgia Geral', True, True,
     'Especialidade médica que realiza procedimentos cirúrgicos em diversas áreas do corpo',
     'Cirurgias abdominais, de emergência, procedimentos cirúrgicos gerais, trauma',
     'Residência médica em Cirurgia Geral (3 anos) e título de especialista pelo CFM',
     'Base para outras especialidades cirúrgicas.'),
    
    ('Pediatria', '03', None, 3, '03', '03', 'Clínica', 'Pediatria Geral', True, True,
     'Especialidade médica dedicada ao cuidado da saúde de crianças e adolescentes do nascimento até os 18 anos',
     'Atendimento pediátrico preventivo e curativo, acompanhamento do crescimento e desenvolvimento, vacinação, puericultura',
     'Residência médica em Pediatria (3 anos) e título de especialista pelo CFM',
     'Fundamental para saúde infantil. Integrada ao Programa Saúde da Família (PSF).'),
    
    ('Ginecologia e Obstetrícia', '04', None, 4, '04', '04', 'Clínica e Cirúrgica', 'Saúde da Mulher', True, True,
     'Especialidade médica que trata da saúde da mulher, gestação, parto e puerpério',
     'Pré-natal, parto, puerpério, saúde reprodutiva, planejamento familiar, prevenção de câncer ginecológico',
     'Residência médica em Ginecologia e Obstetrícia (3 anos) e título de especialista pelo CFM',
     'Fundamental para saúde da mulher e redução da mortalidade materna e infantil.'),
    
    ('Ortopedia', '05', None, 5, '05', '05', 'Cirúrgica', 'Ortopedia e Traumatologia', True, True,
     'Especialidade médica que trata de doenças e lesões do sistema musculoesquelético',
     'Traumatologia, cirurgia ortopédica, tratamento de fraturas, doenças ósseas e articulares',
     'Residência médica em Ortopedia (3 anos) e título de especialista pelo CFM',
     'Alta demanda no SUS devido a acidentes e traumas.'),
    
    ('Cardiologia', '06', None, 6, '06', '06', 'Clínica', 'Cardiologia Clínica', True, True,
     'Especialidade médica que trata das doenças do coração e sistema circulatório',
     'Prevenção, diagnóstico e tratamento de doenças cardiovasculares, hipertensão arterial, cardiopatias',
     'Residência médica em Clínica Médica (2 anos) + Residência em Cardiologia (2 anos) ou título de especialista pelo CFM',
     'Especialidade de alta demanda no SUS. Principal causa de mortalidade no Brasil.'),
    
    ('Dermatologia', '07', None, 7, '07', '07', 'Clínica', 'Dermatologia Clínica', True, True,
     'Especialidade médica que trata doenças da pele, cabelos e unhas',
     'Diagnóstico e tratamento de doenças dermatológicas, câncer de pele, estética médica',
     'Residência médica em Dermatologia (3 anos) ou Clínica Médica (2 anos) + especialização em Dermatologia e título de especialista pelo CFM',
     'Alta demanda ambulatorial no SUS.'),
    
    ('Oftalmologia', '08', None, 8, '08', '08', 'Clínica e Cirúrgica', 'Oftalmologia Geral', True, True,
     'Especialidade médica que trata doenças dos olhos e sistema visual',
     'Prevenção, diagnóstico e tratamento de doenças oculares, cirurgias oftalmológicas, correção de erros refrativos',
     'Residência médica em Oftalmologia (3 anos) e título de especialista pelo CFM',
     'Alta demanda no SUS. Fundamental para prevenção da cegueira.'),
    
    ('Otorrinolaringologia', '09', None, 9, '09', '09', 'Clínica e Cirúrgica', 'Otorrinolaringologia Geral', True, True,
     'Especialidade médica que trata doenças do ouvido, nariz e garganta',
     'Diagnóstico e tratamento de doenças do ouvido, nariz, seios paranasais, faringe e laringe',
     'Residência médica em Otorrinolaringologia (3 anos) e título de especialista pelo CFM',
     'Alta demanda ambulatorial no SUS.'),
    
    ('Psiquiatria', '10', None, 10, '10', '10', 'Clínica', 'Psiquiatria Geral', True, True,
     'Especialidade médica que trata transtornos mentais e comportamentais',
     'Diagnóstico e tratamento de transtornos mentais, psicoterapia, prescrição de psicofármacos',
     'Residência médica em Psiquiatria (3 anos) e título de especialista pelo CFM',
     'Alta demanda no SUS. Fundamental para saúde mental.'),
    
    ('Neurologia', '11', None, 11, '11', '11', 'Clínica', 'Neurologia Clínica', True, True,
     'Especialidade médica que trata doenças do sistema nervoso',
     'Diagnóstico e tratamento de doenças neurológicas, epilepsia, doenças cerebrovasculares, demências',
     'Residência médica em Neurologia (3 anos) e título de especialista pelo CFM',
     'Alta demanda no SUS.'),
    
    ('Urologia', '12', None, 12, '12', '12', 'Clínica e Cirúrgica', 'Urologia Geral', True, True,
     'Especialidade médica que trata doenças do trato urinário e sistema reprodutor masculino',
     'Diagnóstico e tratamento de doenças urológicas, cirurgias urológicas, andrologia',
     'Residência médica em Urologia (3 anos) e título de especialista pelo CFM',
     'Alta demanda no SUS.'),
    
    ('Anestesiologia', '13', None, 13, '13', '13', 'Perioperatória', 'Anestesia e Terapia Intensiva', True, True,
     'Especialidade médica que administra anestesia e monitora pacientes durante cirurgias',
     'Anestesia geral e regional, medicina perioperatória, terapia intensiva, tratamento da dor',
     'Residência médica em Anestesiologia (3 anos) e título de especialista pelo CFM',
     'Essencial para realização de procedimentos cirúrgicos.'),
    
    ('Radiologia', '14', None, 14, '14', '14', 'Diagnóstica', 'Radiologia Geral', True, True,
     'Especialidade médica que utiliza métodos de imagem para diagnóstico',
     'Radiologia convencional, tomografia computadorizada, ressonância magnética, ultrassonografia',
     'Residência médica em Radiologia (3 anos) e título de especialista pelo CFM',
     'Fundamental para diagnóstico médico.'),
    
    ('Patologia', '15', None, 15, '15', '15', 'Diagnóstica', 'Patologia Geral', True, True,
     'Especialidade médica que estuda doenças através de análise de tecidos e células',
     'Anatomia patológica, citopatologia, patologia clínica, necropsia',
     'Residência médica em Patologia (3 anos) e título de especialista pelo CFM',
     'Fundamental para diagnóstico de câncer e outras doenças.'),
    
    ('Medicina de Família e Comunidade', '16', None, 16, '16', '16', 'Clínica', 'Atenção Primária', True, True,
     'Especialidade médica focada na atenção primária à saúde, cuidado continuado e integral da família',
     'Atuação em unidades básicas de saúde (UBS), estratégia saúde da família (ESF), atenção primária, promoção e prevenção',
     'Residência médica em Medicina de Família e Comunidade (2 anos) e título de especialista pelo CFM',
     'Especialidade estratégica do SUS. Base do Programa Saúde da Família (PSF).'),
    
    ('Medicina de Urgência', '17', None, 17, '17', '17', 'Emergência', 'Urgência e Emergência', True, True,
     'Especialidade médica dedicada ao atendimento de situações de urgência e emergência',
     'Atendimento em pronto-socorro, UPA, SAMU, triagem, estabilização de pacientes graves',
     'Residência médica em Medicina de Urgência (2 anos) e título de especialista pelo CFM',
     'Fundamental para UPAs e pronto-socorros. Alta demanda no SUS.'),
    
    ('Medicina Intensiva', '18', None, 18, '18', '18', 'Intensiva', 'Terapia Intensiva', True, True,
     'Especialidade médica dedicada ao cuidado de pacientes críticos em unidades de terapia intensiva',
     'Cuidado de pacientes graves, suporte avançado de vida, monitorização hemodinâmica, ventilação mecânica',
     'Residência médica em Medicina Intensiva (2 anos) ou Anestesiologia/Clínica Médica + especialização e título de especialista pelo CFM',
     'Fundamental para unidades de terapia intensiva (UTI).'),
    
    ('Medicina Preventiva', '19', None, 19, '19', '19', 'Preventiva', 'Medicina Preventiva', True, True,
     'Especialidade médica focada na prevenção de doenças e promoção da saúde',
     'Epidemiologia, saúde pública, medicina preventiva, programas de saúde',
     'Residência médica em Medicina Preventiva (2 anos) e título de especialista pelo CFM',
     'Fundamental para saúde pública.'),
    
    ('Medicina do Trabalho', '20', None, 20, '20', '20', 'Preventiva', 'Medicina do Trabalho', True, True,
     'Especialidade médica que trata da saúde do trabalhador',
     'Medicina ocupacional, perícia médica trabalhista, prevenção de acidentes de trabalho',
     'Residência médica em Medicina do Trabalho (2 anos) ou especialização e título de especialista pelo CFM',
     'Fundamental para saúde ocupacional.'),
    
    ('Medicina Legal', '21', None, 21, '21', '21', 'Pericial', 'Medicina Legal', True, True,
     'Especialidade médica que aplica conhecimentos médicos ao direito',
     'Perícia médica, medicina forense, tanatologia, toxicologia forense',
     'Residência médica em Medicina Legal (2 anos) e título de especialista pelo CFM',
     'Fundamental para perícias médicas e judiciais.'),
    
    ('Medicina Esportiva', '22', None, 22, '22', '22', 'Clínica', 'Medicina Esportiva', True, True,
     'Especialidade médica que trata da saúde de atletas e praticantes de atividade física',
     'Medicina do esporte, fisiologia do exercício, prevenção de lesões esportivas',
     'Residência médica em Medicina Esportiva (2 anos) ou especialização e título de especialista pelo CFM',
     'Crescente demanda no Brasil.'),
    
    ('Geriatria', '23', None, 23, '23', '23', 'Clínica', 'Geriatria', True, True,
     'Especialidade médica que trata da saúde do idoso',
     'Cuidado do idoso, doenças geriátricas, gerontologia, cuidados paliativos',
     'Residência médica em Geriatria (2 anos) ou Clínica Médica + especialização e título de especialista pelo CFM',
     'Crescente demanda devido ao envelhecimento populacional.'),
    
    ('Endocrinologia', '24', None, 24, '24', '24', 'Clínica', 'Endocrinologia Clínica', True, True,
     'Especialidade médica que trata doenças do sistema endócrino e metabolismo',
     'Diabetes, doenças da tireóide, obesidade, distúrbios hormonais, metabolismo',
     'Residência médica em Clínica Médica (2 anos) + Residência em Endocrinologia (2 anos) ou título de especialista pelo CFM',
     'Alta demanda no SUS. Diabetes é epidemia no Brasil.'),
    
    ('Gastroenterologia', '25', None, 25, '25', '25', 'Clínica', 'Gastroenterologia Clínica', True, True,
     'Especialidade médica que trata doenças do aparelho digestivo',
     'Doenças do esôfago, estômago, intestinos, fígado, pâncreas, endoscopia digestiva',
     'Residência médica em Clínica Médica (2 anos) + Residência em Gastroenterologia (2 anos) ou título de especialista pelo CFM',
     'Alta demanda no SUS.'),
    
    ('Pneumologia', '26', None, 26, '26', '26', 'Clínica', 'Pneumologia Clínica', True, True,
     'Especialidade médica que trata doenças do aparelho respiratório',
     'Asma, DPOC, pneumonia, tuberculose, doenças pulmonares intersticiais',
     'Residência médica em Clínica Médica (2 anos) + Residência em Pneumologia (2 anos) ou título de especialista pelo CFM',
     'Alta demanda no SUS.'),
    
    ('Nefrologia', '27', None, 27, '27', '27', 'Clínica', 'Nefrologia Clínica', True, True,
     'Especialidade médica que trata doenças dos rins',
     'Insuficiência renal, diálise, transplante renal, doenças renais crônicas',
     'Residência médica em Clínica Médica (2 anos) + Residência em Nefrologia (2 anos) ou título de especialista pelo CFM',
     'Alta demanda no SUS.'),
    
    ('Hematologia', '28', None, 28, '28', '28', 'Clínica', 'Hematologia Clínica', True, True,
     'Especialidade médica que trata doenças do sangue e órgãos hematopoéticos',
     'Anemias, leucemias, linfomas, distúrbios de coagulação, transplante de medula óssea',
     'Residência médica em Clínica Médica (2 anos) + Residência em Hematologia (2 anos) ou título de especialista pelo CFM',
     'Alta demanda no SUS.'),
    
    ('Oncologia', '29', None, 29, '29', '29', 'Clínica', 'Oncologia Clínica', True, True,
     'Especialidade médica que trata câncer',
     'Diagnóstico e tratamento de câncer, quimioterapia, cuidados paliativos oncológicos',
     'Residência médica em Clínica Médica (2 anos) + Residência em Oncologia (2 anos) ou título de especialista pelo CFM',
     'Alta demanda no SUS. Câncer é segunda causa de morte no Brasil.'),
    
    ('Reumatologia', '30', None, 30, '30', '30', 'Clínica', 'Reumatologia Clínica', True, True,
     'Especialidade médica que trata doenças reumáticas e autoimunes',
     'Artrite reumatóide, lúpus, fibromialgia, osteoporose, doenças autoimunes',
     'Residência médica em Clínica Médica (2 anos) + Residência em Reumatologia (2 anos) ou título de especialista pelo CFM',
     'Alta demanda no SUS.'),
    
    ('Infectologia', '31', None, 31, '31', '31', 'Clínica', 'Infectologia Clínica', True, True,
     'Especialidade médica que trata doenças infecciosas',
     'HIV/AIDS, tuberculose, hepatites, infecções hospitalares, doenças tropicais',
     'Residência médica em Clínica Médica (2 anos) + Residência em Infectologia (2 anos) ou título de especialista pelo CFM',
     'Alta demanda no SUS.'),
    

    # Subespecialidades e Áreas de Atuação
    
    # Cirurgias Especializadas
    ('Cirurgia Plástica', '33', None, 99, '33', '33', 'Cirúrgica', 'Cirurgia Plástica', True, True,
     'Especialidade médica que realiza cirurgias reparadoras e estéticas',
     'Cirurgia plástica reparadora, cirurgia estética, queimaduras, reconstrução',
     'Residência médica em Cirurgia Geral (3 anos) + Residência em Cirurgia Plástica (3 anos) e título de especialista pelo CFM',
     'Alta demanda no Brasil.'),
    
    ('Cirurgia Cardiovascular', '34', None, 99, '34', '34', 'Cirúrgica', 'Cirurgia Cardiovascular', True, True,
     'Especialidade médica que realiza cirurgias do coração e grandes vasos',
     'Cirurgia cardíaca, cirurgia de grandes vasos, transplante cardíaco',
     'Residência médica em Cirurgia Geral (3 anos) + Residência em Cirurgia Cardiovascular (3 anos) e título de especialista pelo CFM',
     'Alta complexidade.'),
    
    ('Cirurgia Torácica', '35', None, 99, '35', '35', 'Cirúrgica', 'Cirurgia Torácica', True, True,
     'Especialidade médica que realiza cirurgias do tórax',
     'Cirurgia pulmonar, cirurgia de mediastino, cirurgia de parede torácica',
     'Residência médica em Cirurgia Geral (3 anos) + Residência em Cirurgia Torácica (2 anos) e título de especialista pelo CFM',
     'Alta complexidade.'),
    
    ('Cirurgia do Aparelho Digestivo', '36', None, 99, '36', '36', 'Cirúrgica', 'Cirurgia Digestiva', True, True,
     'Especialidade médica que realiza cirurgias do aparelho digestivo',
     'Cirurgia de esôfago, estômago, intestinos, fígado, pâncreas, cirurgia bariátrica',
     'Residência médica em Cirurgia Geral (3 anos) + Residência em Cirurgia do Aparelho Digestivo (2 anos) e título de especialista pelo CFM',
     'Alta demanda no SUS.'),
    
    ('Cirurgia Vascular', '37', None, 99, '37', '37', 'Cirúrgica', 'Cirurgia Vascular', True, True,
     'Especialidade médica que realiza cirurgias de vasos sanguíneos',
     'Cirurgia arterial, venosa, tratamento de varizes, aneurismas',
     'Residência médica em Cirurgia Geral (3 anos) + Residência em Cirurgia Vascular (2 anos) e título de especialista pelo CFM',
     'Alta demanda no SUS.'),
    
    ('Neurocirurgia', '38', None, 99, '38', '38', 'Cirúrgica', 'Neurocirurgia', True, True,
     'Especialidade médica que realiza cirurgias do sistema nervoso',
     'Cirurgia de cérebro, medula espinhal, nervos periféricos, trauma cranioencefálico',
     'Residência médica em Cirurgia Geral (3 anos) + Residência em Neurocirurgia (4 anos) e título de especialista pelo CFM',
     'Alta complexidade.'),
    
    ('Cirurgia Pediátrica', '39', None, 99, '39', '39', 'Cirúrgica', 'Cirurgia Pediátrica', True, True,
     'Especialidade médica que realiza cirurgias em crianças',
     'Cirurgia geral pediátrica, cirurgia neonatal, malformações congênitas',
     'Residência médica em Cirurgia Geral (3 anos) + Residência em Cirurgia Pediátrica (2 anos) e título de especialista pelo CFM',
     'Alta complexidade.'),
    
    ('Cirurgia de Cabeça e Pescoço', '40', None, 99, '40', '40', 'Cirúrgica', 'Cirurgia de Cabeça e Pescoço', True, True,
     'Especialidade médica que realiza cirurgias de cabeça e pescoço',
     'Cirurgia de tireóide, paratireóide, tumores de cabeça e pescoço',
     'Residência médica em Cirurgia Geral (3 anos) + Residência em Cirurgia de Cabeça e Pescoço (2 anos) e título de especialista pelo CFM',
     'Alta demanda no SUS.'),
    
    # Especialidades Clínicas Adicionais
    ('Angiologia', '41', None, 99, '41', '41', 'Clínica', 'Angiologia', True, True,
     'Especialidade médica que trata doenças vasculares',
     'Doenças arteriais, venosas, linfáticas, tratamento clínico de varizes',
     'Residência médica em Clínica Médica (2 anos) + Residência em Angiologia (2 anos) ou título de especialista pelo CFM',
     'Alta demanda no SUS.'),
    
    ('Mastologia', '42', None, 99, '42', '42', 'Clínica e Cirúrgica', 'Mastologia', True, True,
     'Especialidade médica que trata doenças da mama',
     'Prevenção, diagnóstico e tratamento de doenças da mama, câncer de mama',
     'Residência médica em Ginecologia e Obstetrícia ou Cirurgia Geral (3 anos) + Residência em Mastologia (2 anos) e título de especialista pelo CFM',
     'Alta demanda no SUS.'),
    
    ('Coloproctologia', '43', None, 99, '43', '43', 'Clínica e Cirúrgica', 'Coloproctologia', True, True,
     'Especialidade médica que trata doenças do cólon, reto e ânus',
     'Doenças do intestino grosso, reto, ânus, cirurgia colorretal',
     'Residência médica em Cirurgia Geral (3 anos) + Residência em Coloproctologia (2 anos) e título de especialista pelo CFM',
     'Alta demanda no SUS.'),
    
    ('Cirurgia Oncológica', '44', None, 99, '44', '44', 'Cirúrgica', 'Cirurgia Oncológica', True, True,
     'Especialidade médica que realiza cirurgias para tratamento de câncer',
     'Cirurgia oncológica, tratamento cirúrgico de tumores',
     'Residência médica em Cirurgia Geral (3 anos) + Residência em Cirurgia Oncológica (2 anos) e título de especialista pelo CFM',
     'Alta demanda no SUS.'),
    
    ('Oncologia Clínica', '45', None, 99, '45', '45', 'Clínica', 'Oncologia Clínica', True, True,
     'Especialidade médica que trata câncer com métodos clínicos',
     'Quimioterapia, imunoterapia, tratamento clínico de câncer',
     'Residência médica em Clínica Médica (2 anos) + Residência em Oncologia Clínica (2 anos) e título de especialista pelo CFM',
     'Alta demanda no SUS.'),
    
    ('Radioterapia', '46', None, 99, '46', '46', 'Terapêutica', 'Radioterapia', True, True,
     'Especialidade médica que utiliza radiação para tratamento de câncer',
     'Radioterapia, tratamento radioterápico de tumores',
     'Residência médica em Radioterapia (3 anos) e título de especialista pelo CFM',
     'Alta demanda no SUS.'),
    
    ('Medicina Nuclear', '47', None, 99, '47', '47', 'Diagnóstica e Terapêutica', 'Medicina Nuclear', True, True,
     'Especialidade médica que utiliza materiais radioativos para diagnóstico e tratamento',
     'Cintilografia, PET scan, tratamento com radioisótopos',
     'Residência médica em Medicina Nuclear (3 anos) e título de especialista pelo CFM',
     'Alta complexidade.'),
    
    ('Angiorradiologia e Cirurgia Endovascular', '48', None, 99, '48', '48', 'Diagnóstica e Cirúrgica', 'Angiorradiologia', True, True,
     'Especialidade médica que realiza procedimentos endovasculares',
     'Angioplastia, embolização, procedimentos endovasculares',
     'Residência médica em Radiologia ou Cirurgia Vascular (3 anos) + especialização e título de especialista pelo CFM',
     'Alta complexidade.'),
    
    ('Radiologia Intervencionista', '49', None, 99, '49', '49', 'Diagnóstica e Cirúrgica', 'Radiologia Intervencionista', True, True,
     'Especialidade médica que realiza procedimentos guiados por imagem',
     'Procedimentos intervencionistas, biópsias guiadas, drenagens',
     'Residência médica em Radiologia (3 anos) + especialização e título de especialista pelo CFM',
     'Alta complexidade.'),
    
    # Especialidades em Pediatria
    ('Pediatria Intensiva', '50', None, 99, '50', '50', 'Intensiva', 'Pediatria Intensiva', True, True,
     'Especialidade médica que trata crianças críticas em UTI pediátrica',
     'Cuidado de crianças graves, UTI pediátrica, suporte avançado de vida pediátrico',
     'Residência médica em Pediatria (3 anos) + Residência em Terapia Intensiva Pediátrica (2 anos) e título de especialista pelo CFM',
     'Alta complexidade.'),
    
    ('Neonatologia', '51', None, 99, '51', '51', 'Intensiva', 'Neonatologia', True, True,
     'Especialidade médica que trata recém-nascidos',
     'Cuidado de recém-nascidos, UTI neonatal, prematuridade',
     'Residência médica em Pediatria (3 anos) + Residência em Neonatologia (2 anos) e título de especialista pelo CFM',
     'Fundamental para redução da mortalidade neonatal.'),
    
    ('Cardiologia Pediátrica', '52', None, 99, '52', '52', 'Clínica', 'Cardiologia Pediátrica', True, True,
     'Especialidade médica que trata doenças cardíacas em crianças',
     'Cardiopatias congênitas, doenças cardíacas pediátricas',
     'Residência médica em Pediatria (3 anos) + Residência em Cardiologia Pediátrica (2 anos) e título de especialista pelo CFM',
     'Alta complexidade.'),
    
    ('Neurologia Pediátrica', '53', None, 99, '53', '53', 'Clínica', 'Neurologia Pediátrica', True, True,
     'Especialidade médica que trata doenças neurológicas em crianças',
     'Epilepsia pediátrica, doenças neurológicas infantis, paralisia cerebral',
     'Residência médica em Pediatria (3 anos) + Residência em Neurologia Pediátrica (2 anos) e título de especialista pelo CFM',
     'Alta demanda no SUS.'),
    
    ('Oncologia Pediátrica', '54', None, 99, '54', '54', 'Clínica', 'Oncologia Pediátrica', True, True,
     'Especialidade médica que trata câncer em crianças',
     'Leucemias pediátricas, tumores pediátricos, tratamento oncológico infantil',
     'Residência médica em Pediatria (3 anos) + Residência em Oncologia Pediátrica (2 anos) e título de especialista pelo CFM',
     'Alta complexidade.'),
    
    # Especialidades em Ginecologia
    ('Reprodução Humana', '55', None, 99, '55', '55', 'Clínica', 'Reprodução Humana', True, True,
     'Especialidade médica que trata infertilidade e reprodução assistida',
     'Infertilidade, reprodução assistida, fertilização in vitro',
     'Residência médica em Ginecologia e Obstetrícia (3 anos) + especialização e título de especialista pelo CFM',
     'Crescente demanda no Brasil.'),
    
    ('Ultrassonografia em Ginecologia e Obstetrícia', '56', None, 99, '56', '56', 'Diagnóstica', 'Ultrassonografia GO', True, True,
     'Especialidade médica que realiza ultrassonografia ginecológica e obstétrica',
     'Ultrassonografia obstétrica, ginecológica, morfológica',
     'Residência médica em Ginecologia e Obstetrícia (3 anos) + especialização e título de especialista pelo CFM',
     'Alta demanda no SUS.'),
    
    # Especialidades em Psiquiatria
    ('Psiquiatria da Infância e Adolescência', '57', None, 99, '57', '57', 'Clínica', 'Psiquiatria Infantil', True, True,
     'Especialidade médica que trata transtornos mentais em crianças e adolescentes',
     'Transtornos mentais infantis, autismo, TDAH, transtornos de desenvolvimento',
     'Residência médica em Psiquiatria (3 anos) + Residência em Psiquiatria da Infância e Adolescência (2 anos) e título de especialista pelo CFM',
     'Alta demanda no SUS.'),
    
    ('Psiquiatria Forense', '58', None, 99, '58', '58', 'Pericial', 'Psiquiatria Forense', True, True,
     'Especialidade médica que aplica psiquiatria ao direito',
     'Perícia psiquiátrica, avaliação de capacidade, imputabilidade',
     'Residência médica em Psiquiatria (3 anos) + especialização e título de especialista pelo CFM',
     'Fundamental para perícias judiciais.'),
    
    # Especialidades em Neurologia
    ('Neuropediatria', '59', None, 99, '59', '59', 'Clínica', 'Neuropediatria', True, True,
     'Especialidade médica que trata doenças neurológicas em crianças',
     'Epilepsia pediátrica, doenças neurológicas infantis',
     'Residência médica em Pediatria (3 anos) + Residência em Neurologia Pediátrica (2 anos) e título de especialista pelo CFM',
     'Alta demanda no SUS.'),
    
    # Especialidades em Oftalmologia
    ('Oftalmopediatria', '60', None, 99, '60', '60', 'Clínica', 'Oftalmopediatria', True, True,
     'Especialidade médica que trata doenças oculares em crianças',
     'Estrabismo, ambliopia, doenças oculares pediátricas',
     'Residência médica em Oftalmologia (3 anos) + especialização e título de especialista pelo CFM',
     'Alta demanda no SUS.'),
    
    # Especialidades em Otorrinolaringologia
    ('Otorrinolaringologia Pediátrica', '61', None, 99, '61', '61', 'Clínica e Cirúrgica', 'ORL Pediátrica', True, True,
     'Especialidade médica que trata doenças de ouvido, nariz e garganta em crianças',
     'Adenoidectomia, amigdalectomia, otites, doenças ORL pediátricas',
     'Residência médica em Otorrinolaringologia (3 anos) + especialização e título de especialista pelo CFM',
     'Alta demanda no SUS.'),
    
    # Especialidades em Urologia
    ('Urologia Pediátrica', '62', None, 99, '62', '62', 'Clínica e Cirúrgica', 'Urologia Pediátrica', True, True,
     'Especialidade médica que trata doenças urológicas em crianças',
     'Malformações urológicas, infecções urinárias pediátricas',
     'Residência médica em Urologia (3 anos) + especialização e título de especialista pelo CFM',
     'Alta demanda no SUS.'),
    
    # Especialidades em Ortopedia
    ('Ortopedia Pediátrica', '63', None, 99, '63', '63', 'Cirúrgica', 'Ortopedia Pediátrica', True, True,
     'Especialidade médica que trata doenças ortopédicas em crianças',
     'Malformações congênitas, doenças ortopédicas pediátricas',
     'Residência médica em Ortopedia (3 anos) + especialização e título de especialista pelo CFM',
     'Alta demanda no SUS.'),
    
    ('Traumatologia e Ortopedia', '64', None, 99, '64', '64', 'Cirúrgica', 'Traumatologia', True, True,
     'Especialidade médica que trata traumas e lesões ortopédicas',
     'Traumatologia, tratamento de fraturas, lesões esportivas',
     'Residência médica em Ortopedia (3 anos) e título de especialista pelo CFM',
     'Alta demanda no SUS.'),
    
    # Especialidades em Anestesiologia
    ('Anestesiologia Pediátrica', '65', None, 99, '65', '65', 'Perioperatória', 'Anestesia Pediátrica', True, True,
     'Especialidade médica que administra anestesia em crianças',
     'Anestesia pediátrica, anestesia neonatal',
     'Residência médica em Anestesiologia (3 anos) + especialização e título de especialista pelo CFM',
     'Alta complexidade.'),
    
    ('Medicina da Dor', '66', None, 99, '66', '66', 'Clínica', 'Medicina da Dor', True, True,
     'Especialidade médica que trata dor crônica e aguda',
     'Tratamento da dor, clínica da dor, dor crônica',
     'Residência médica em Anestesiologia ou Clínica Médica (3 anos) + especialização e título de especialista pelo CFM',
     'Crescente demanda no Brasil.'),
    
    # Especialidades em Radiologia
    ('Ultrassonografia', '67', None, 99, '67', '67', 'Diagnóstica', 'Ultrassonografia', True, True,
     'Especialidade médica que realiza exames de ultrassom',
     'Ultrassonografia geral, obstétrica, doppler, ecocardiografia',
     'Residência médica em Radiologia (3 anos) + especialização e título de especialista pelo CFM',
     'Alta demanda no SUS.'),
    
    ('Ressonância Magnética', '68', None, 99, '68', '68', 'Diagnóstica', 'Ressonância Magnética', True, True,
     'Especialidade médica que realiza exames de ressonância magnética',
     'Ressonância magnética, neuroimagem, imagens por ressonância',
     'Residência médica em Radiologia (3 anos) + especialização e título de especialista pelo CFM',
     'Alta complexidade.'),
    
    ('Tomografia Computadorizada', '69', None, 99, '69', '69', 'Diagnóstica', 'Tomografia Computadorizada', True, True,
     'Especialidade médica que realiza exames de tomografia computadorizada',
     'Tomografia computadorizada, TC, imagens tomográficas',
     'Residência médica em Radiologia (3 anos) + especialização e título de especialista pelo CFM',
     'Alta demanda no SUS.'),
    
    # Especialidades em Patologia
    ('Anatomia Patológica', '70', None, 99, '70', '70', 'Diagnóstica', 'Anatomia Patológica', True, True,
     'Especialidade médica que estuda doenças através de análise de tecidos',
     'Anatomia patológica, biópsias, diagnóstico histopatológico',
     'Residência médica em Patologia (3 anos) e título de especialista pelo CFM',
     'Fundamental para diagnóstico de câncer.'),
    
    ('Citopatologia', '71', None, 99, '71', '71', 'Diagnóstica', 'Citopatologia', True, True,
     'Especialidade médica que estuda doenças através de análise de células',
     'Citopatologia, Papanicolau, citologia',
     'Residência médica em Patologia (3 anos) + especialização e título de especialista pelo CFM',
     'Fundamental para prevenção de câncer de colo do útero.'),
    
    # Especialidades Adicionais
    ('Acupuntura', '72', None, 99, '72', '72', 'Terapêutica', 'Acupuntura', False, True,
     'Especialidade médica que utiliza acupuntura para tratamento',
     'Acupuntura médica, tratamento com agulhas',
     'Especialização em Acupuntura e título de especialista pelo CFM',
     'Crescente demanda no Brasil.'),
    
    ('Homeopatia', '73', None, 99, '73', '73', 'Terapêutica', 'Homeopatia', False, True,
     'Especialidade médica que utiliza homeopatia para tratamento',
     'Homeopatia médica, tratamento homeopático',
     'Especialização em Homeopatia e título de especialista pelo CFM',
     'Reconhecida pelo CFM.'),
    
    ('Medicina Paliativa', '74', None, 99, '74', '74', 'Clínica', 'Cuidados Paliativos', True, True,
     'Especialidade médica que trata pacientes com doenças graves e terminais',
     'Cuidados paliativos, tratamento da dor, qualidade de vida',
     'Residência médica em Clínica Médica ou especialização e título de especialista pelo CFM',
     'Crescente demanda no Brasil.'),
    
    ('Medicina Aeroespacial', '75', None, 99, '75', '75', 'Preventiva', 'Medicina Aeroespacial', True, True,
     'Especialidade médica que trata saúde de tripulantes e passageiros',
     'Medicina aeronáutica, medicina espacial, perícia aeronáutica',
     'Especialização em Medicina Aeroespacial e título de especialista pelo CFM',
     'Especialidade específica.'),
    
    ('Medicina de Tráfego', '76', None, 99, '76', '76', 'Preventiva', 'Medicina de Tráfego', True, True,
     'Especialidade médica que trata saúde de condutores',
     'Perícia médica para CNH, medicina de tráfego',
     'Especialização em Medicina de Tráfego e título de especialista pelo CFM',
     'Alta demanda no Brasil.'),
    
    ('Medicina Física e Reabilitação', '77', None, 99, '77', '77', 'Terapêutica', 'Fisiatria', True, True,
     'Especialidade médica que trata reabilitação e medicina física',
     'Reabilitação, medicina física, fisiatria',
     'Residência médica em Medicina Física e Reabilitação (3 anos) e título de especialista pelo CFM',
     'Alta demanda no SUS.'),
    
    ('Fisiatria', '78', None, 99, '78', '78', 'Terapêutica', 'Fisiatria', True, True,
     'Especialidade médica que trata reabilitação física',
     'Reabilitação física, medicina física',
     'Residência médica em Medicina Física e Reabilitação (3 anos) e título de especialista pelo CFM',
     'Alta demanda no SUS.'),
    
    ('Genética Médica', '79', None, 99, '79', '79', 'Clínica', 'Genética Médica', True, True,
     'Especialidade médica que trata doenças genéticas',
     'Doenças genéticas, aconselhamento genético, genética médica',
     'Residência médica em Genética Médica (3 anos) e título de especialista pelo CFM',
     'Crescente demanda no Brasil.'),
    
    ('Imunologia Clínica', '80', None, 99, '80', '80', 'Clínica', 'Imunologia Clínica', True, True,
     'Especialidade médica que trata doenças imunológicas',
     'Doenças autoimunes, imunodeficiências, alergias',
     'Residência médica em Clínica Médica (2 anos) + Residência em Imunologia (2 anos) ou título de especialista pelo CFM',
     'Alta demanda no SUS.'),
    
    ('Medicina Estética', '81', None, 99, '81', '81', 'Estética', 'Medicina Estética', True, True,
     'Especialidade médica que trata estética e beleza',
     'Medicina estética, procedimentos estéticos, rejuvenescimento',
     'Especialização em Medicina Estética e título de especialista pelo CFM',
     'Crescente demanda no Brasil.'),
    
    ('Medicina do Sono', '82', None, 99, '82', '82', 'Clínica', 'Medicina do Sono', True, True,
     'Especialidade médica que trata distúrbios do sono',
     'Apneia do sono, insônia, distúrbios do sono',
     'Residência médica em Clínica Médica, Neurologia ou Pneumologia (2 anos) + especialização e título de especialista pelo CFM',
     'Crescente demanda no Brasil.'),
    
    ('Medicina de Emergência', '83', None, 99, '83', '83', 'Emergência', 'Medicina de Emergência', True, True,
     'Especialidade médica dedicada ao atendimento de emergências',
     'Atendimento de emergência, pronto-socorro, SAMU',
     'Residência médica em Medicina de Urgência (2 anos) e título de especialista pelo CFM',
     'Alta demanda no SUS.'),
    
    ('Toxicologia Médica', '84', None, 99, '84', '84', 'Clínica', 'Toxicologia Médica', True, True,
     'Especialidade médica que trata intoxicações',
     'Intoxicações, envenenamentos, toxicologia clínica',
     'Residência médica em Clínica Médica (2 anos) + especialização e título de especialista pelo CFM',
     'Alta demanda no SUS.'),
    
    ('Hepatologia', '85', None, 99, '85', '85', 'Clínica', 'Hepatologia', True, True,
     'Especialidade médica que trata doenças do fígado',
     'Hepatites, cirrose, doenças hepáticas',
     'Residência médica em Gastroenterologia (2 anos) + especialização e título de especialista pelo CFM',
     'Alta demanda no SUS.'),
    
    ('Proctologia', '86', None, 99, '86', '86', 'Clínica e Cirúrgica', 'Proctologia', True, True,
     'Especialidade médica que trata doenças do reto e ânus',
     'Doenças do reto, ânus, hemorroidas',
     'Residência médica em Cirurgia Geral (3 anos) + especialização e título de especialista pelo CFM',
     'Alta demanda no SUS.'),
    
    ('Flebologia', '87', None, 99, '87', '87', 'Clínica', 'Flebologia', True, True,
     'Especialidade médica que trata doenças venosas',
     'Varizes, insuficiência venosa, flebologia',
     'Residência médica em Angiologia ou Cirurgia Vascular (2 anos) + especialização e título de especialista pelo CFM',
     'Alta demanda no SUS.'),
    
    ('Linfologia', '88', None, 99, '88', '88', 'Clínica', 'Linfologia', True, True,
     'Especialidade médica que trata doenças do sistema linfático',
     'Linfedema, doenças linfáticas',
     'Residência médica em Angiologia (2 anos) + especialização e título de especialista pelo CFM',
     'Especialidade específica.'),
    
    ('Medicina Hiperbárica', '89', None, 99, '89', '89', 'Terapêutica', 'Medicina Hiperbárica', True, True,
     'Especialidade médica que utiliza oxigenoterapia hiperbárica',
     'Oxigenoterapia hiperbárica, tratamento em câmaras hiperbáricas',
     'Especialização em Medicina Hiperbárica e título de especialista pelo CFM',
     'Especialidade específica.'),
    
    ('Medicina Tropical', '90', None, 99, '90', '90', 'Clínica', 'Medicina Tropical', True, True,
     'Especialidade médica que trata doenças tropicais',
     'Doenças tropicais, doenças infecciosas tropicais',
     'Residência médica em Infectologia (2 anos) + especialização e título de especialista pelo CFM',
     'Importante para região Norte do Brasil.'),
    
    ('Medicina de Viagem', '91', None, 99, '91', '91', 'Preventiva', 'Medicina de Viagem', True, True,
     'Especialidade médica que trata saúde de viajantes',
     'Medicina de viagem, saúde do viajante, vacinação para viagem',
     'Especialização em Medicina de Viagem e título de especialista pelo CFM',
     'Crescente demanda no Brasil.'),
    
    ('Medicina do Adolescente', '92', None, 99, '92', '92', 'Clínica', 'Medicina do Adolescente', True, True,
     'Especialidade médica que trata saúde de adolescentes',
     'Saúde do adolescente, medicina adolescente',
     'Residência médica em Pediatria ou Clínica Médica (2 anos) + especialização e título de especialista pelo CFM',
     'Crescente demanda no Brasil.'),
    
    ('Medicina Fetal', '93', None, 99, '93', '93', 'Clínica', 'Medicina Fetal', True, True,
     'Especialidade médica que trata saúde fetal',
     'Medicina fetal, diagnóstico pré-natal, tratamento fetal',
     'Residência médica em Ginecologia e Obstetrícia (3 anos) + especialização e título de especialista pelo CFM',
     'Alta complexidade.'),
    
    ('Medicina Perinatal', '94', None, 99, '94', '94', 'Clínica', 'Medicina Perinatal', True, True,
     'Especialidade médica que trata saúde perinatal',
     'Medicina perinatal, período perinatal',
     'Residência médica em Ginecologia e Obstetrícia (3 anos) + especialização e título de especialista pelo CFM',
     'Fundamental para redução da mortalidade perinatal.'),
    
    ('Medicina Fetal e Perinatal', '95', None, 99, '95', '95', 'Clínica', 'Medicina Fetal e Perinatal', True, True,
     'Especialidade médica que trata saúde fetal e perinatal',
     'Medicina fetal e perinatal, diagnóstico e tratamento pré-natal',
     'Residência médica em Ginecologia e Obstetrícia (3 anos) + especialização e título de especialista pelo CFM',
     'Alta complexidade.'),
    
    ('Medicina de Longevidade', '96', None, 99, '96', '96', 'Clínica', 'Medicina de Longevidade', True, True,
     'Especialidade médica focada em longevidade e envelhecimento saudável',
     'Medicina antienvelhecimento, longevidade, envelhecimento saudável',
     'Especialização em Medicina de Longevidade e título de especialista pelo CFM',
     'Crescente demanda no Brasil.'),
    
    ('Medicina Integrativa', '97', None, 99, '97', '97', 'Clínica', 'Medicina Integrativa', True, True,
     'Especialidade médica que integra medicina convencional e complementar',
     'Medicina integrativa, medicina complementar',
     'Especialização em Medicina Integrativa e título de especialista pelo CFM',
     'Crescente demanda no Brasil.'),
    
    ('Medicina de Precisão', '98', None, 99, '98', '98', 'Clínica', 'Medicina de Precisão', True, True,
     'Especialidade médica que utiliza genética e biomarcadores para tratamento personalizado',
     'Medicina personalizada, farmacogenética, medicina de precisão',
     'Especialização em Medicina de Precisão e título de especialista pelo CFM',
     'Especialidade emergente.'),
]

def gerar_sql_especialidades():
    """Gera SQL para inserção de especialidades médicas."""
    sql_lines = []
    
    for (nome, codigo, nome_cientifico, tipo_especialidade, codigo_cfm, codigo_cnes,
         area_atuacao, subarea, requer_residencia, requer_titulo_especialista,
         descricao, area_atuacao_descricao, requisitos_formacao, observacoes) in ESPECIALIDADES:
        
        # Escapar aspas simples
        nome_escaped = nome.replace("'", "''")
        descricao_escaped = descricao.replace("'", "''") if descricao else None
        area_atuacao_descricao_escaped = area_atuacao_descricao.replace("'", "''") if area_atuacao_descricao else None
        requisitos_formacao_escaped = requisitos_formacao.replace("'", "''") if requisitos_formacao else None
        observacoes_escaped = observacoes.replace("'", "''") if observacoes else None
        
        # Formatar valores NULL
        nome_cientifico_sql = f"'{nome_cientifico}'" if nome_cientifico else "NULL"
        codigo_sql = f"'{codigo}'" if codigo else "NULL"
        codigo_cfm_sql = f"'{codigo_cfm}'" if codigo_cfm else "NULL"
        codigo_cnes_sql = f"'{codigo_cnes}'" if codigo_cnes else "NULL"
        area_atuacao_sql = f"'{area_atuacao}'" if area_atuacao else "NULL"
        subarea_sql = f"'{subarea}'" if subarea else "NULL"
        descricao_sql = f"'{descricao_escaped}'" if descricao else "NULL"
        area_atuacao_descricao_sql = f"'{area_atuacao_descricao_escaped}'" if area_atuacao_descricao else "NULL"
        requisitos_formacao_sql = f"'{requisitos_formacao_escaped}'" if requisitos_formacao else "NULL"
        observacoes_sql = f"'{observacoes_escaped}'" if observacoes else "NULL"
        
        sql = f"""-- {nome}
INSERT INTO public.especialidades_medicas (
    id, criado_em, atualizado_em, ativo, nome, codigo, nome_cientifico,
    tipo_especialidade, codigo_cfm, codigo_cnes, area_atuacao, subarea,
    requer_residencia, requer_titulo_especialista, descricao,
    area_atuacao_descricao, requisitos_formacao, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, '{nome_escaped}', {codigo_sql}, {nome_cientifico_sql},
    {tipo_especialidade}, {codigo_cfm_sql}, {codigo_cnes_sql}, {area_atuacao_sql}, {subarea_sql},
    {str(requer_residencia).lower()}, {str(requer_titulo_especialista).lower()}, {descricao_sql},
    {area_atuacao_descricao_sql}, {requisitos_formacao_sql}, {observacoes_sql}
WHERE NOT EXISTS (SELECT 1 FROM public.especialidades_medicas WHERE codigo = {codigo_sql} OR nome = '{nome_escaped}');
"""
        sql_lines.append(sql)
    
    return '\n'.join(sql_lines)

if __name__ == '__main__':
    sql = gerar_sql_especialidades()
    print(sql)
