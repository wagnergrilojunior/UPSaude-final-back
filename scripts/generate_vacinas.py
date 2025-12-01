#!/usr/bin/env python3
"""
Script para gerar SQL de inserção de vacinas.
Gera vacinas reais do PNI (Programa Nacional de Imunizações) do Brasil.
"""

# Mapeamento de enums
TIPO_VACINA = {
    "BCG": 1, "HEPATITE_B": 2, "PENTAVALENTE": 3, "DTP": 4, "DTPA": 5, "DT": 6,
    "DTPA_VIP": 7, "VIP": 8, "VOP": 9, "ROTAVIRUS": 10, "MENINGOCOCICA_C": 11,
    "MENINGOCOCICA_ACWY": 12, "MENINGOCOCICA_B": 13, "PNEUMOCOCICA_10": 14,
    "PNEUMOCOCICA_13": 15, "PNEUMOCOCICA_23": 16, "FEBRE_AMARELA": 17,
    "TRIPLICE_VIRAL": 18, "TETRA_VIRAL": 19, "VARICELA": 20, "HEPATITE_A": 21,
    "HPV_QUADRIVALENTE": 22, "HPV_BIVALENTE": 23, "DENGUE": 24,
    "DUPLA_ADULTO": 30, "HEPATITE_B_ADULTO": 31, "FEBRE_AMARELA_ADULTO": 32,
    "TRIPLICE_VIRAL_ADULTO": 33, "PNEUMOCOCICA_23_ADULTO": 34, "INFLUENZA": 35,
    "HERPES_ZOSTER": 36, "RABICA": 40, "ANTIRRABICA": 41, "ANTITETANICA": 42,
    "ANTIDIFTERICA": 43, "TETANO_ACIDENTAL": 44, "HEPATITE_A_E_B": 45,
    "COVID_19": 50, "COVID_19_CORONAVAC": 51, "COVID_19_ASTRAZENECA": 52,
    "COVID_19_PFIZER": 53, "COVID_19_JANSSEN": 54, "COVID_19_BUTANVAC": 55,
    "FEBRE_TIFOIDE": 60, "COLERA": 61, "ENCEFALITE_JAPONESA": 62,
    "MENINGOCOCICA_ACWY_VIAGEM": 63, "OUTRA": 99
}

VIA_ADMINISTRACAO = {
    "ORAL": 1, "SUBLINGUAL": 2, "RETAL": 3, "VAGINAL": 4, "TOPICA": 5,
    "TRANSDERMICA": 6, "INTRAMUSCULAR": 7, "INTRAVENOSA": 8, "SUBCUTANEA": 9,
    "INTRADERMICA": 10, "INTRATECAL": 11, "INTRAPERITONEAL": 12,
    "INTRACARDIACA": 13, "INTRAAORTICA": 14, "INTRAPULMONAR": 15,
    "NASAL": 16, "OFTALMICA": 17, "OTICA": 18, "INALATORIA": 19,
    "ENDOTRAQUEAL": 20, "SUBCUTANEA_PROFUNDA": 21, "INTRADERMICA_MULTIPUNCAO": 22,
    "OUTRA": 99
}

UNIDADE_MEDIDA = {
    "MILIGRAMA": 1, "GRAMA": 2, "MICROGRAMA": 3, "MILILITRO": 4, "LITRO": 5,
    "UNIDADE": 6, "COMPRIMIDO": 7, "CAPSULA": 8, "AMPOLA": 9, "FRASCO": 10,
    "GOTA": 11, "APLICACAO": 12, "DOSE": 13, "OUTROS": 99
}

STATUS_ATIVO = {"ATIVO": 1, "SUSPENSO": 2, "INATIVO": 3}

# Dados das vacinas - Formato simplificado
# (nome, nome_comercial, codigo_interno, codigo_pni, codigo_sus, registro_anvisa,
#  tipo_vacina_str, categoria, grupo_alvo, fabricante_nome, via_admin_str, unidade_medida_str,
#  numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
#  local_aplicacao, idade_min_dias, idade_max_dias, temp_min, temp_max, tipo_conservacao,
#  proteger_luz, agitar_antes_uso, status_str, calendario_basico, calendario_campanha, obrigatoria,
#  gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes)
VACINAS = [
    # Vacinas do Calendário Básico de Crianças
    ('BCG', 'BCG', 'VAC-001', 'PNI-001', 'SUS-001', '10101010101', 'BCG', 'Rotina', 'Crianças', 'Instituto Butantan', 'INTRADERMICA', 'DOSE', 1, None, False, None, 0.1, 'Braço direito', 0, None, 2.0, 8.0, 'Refrigerada (2-8°C)', False, False, 'ATIVO', True, False, True, False, False, False, True, 'Vacina contra tuberculose. Protege contra formas graves da doença, especialmente meningite tuberculosa e tuberculose miliar.', 'Aplicar ao nascer ou o mais precocemente possível, preferencialmente na maternidade.'),    ('Hepatite B', 'Hepatite B Recombinante', 'VAC-002', 'PNI-002', 'SUS-002', '10101010102', 'HEPATITE_B', 'Rotina', 'Crianças', 'Instituto Butantan', 'INTRAMUSCULAR', 'DOSE', 3, 30, False, None, 0.5, 'Coxa (lateral)', 0, None, 2.0, 8.0, 'Refrigerada (2-8°C)', True, False, 'ATIVO', True, False, True, False, False, False, True, 'Vacina contra hepatite B. Protege contra infecção pelo vírus da hepatite B.', 'Aplicar ao nascer (preferencialmente nas primeiras 12 horas de vida), 1 mês e 6 meses de idade.'),    ('Pentavalente', 'DTP + Hib + Hepatite B', 'VAC-003', 'PNI-003', 'SUS-003', '10101010103', 'PENTAVALENTE', 'Rotina', 'Crianças', 'Instituto Butantan', 'INTRAMUSCULAR', 'DOSE', 3, 60, False, None, 0.5, 'Coxa (lateral)', 60, None, 2.0, 8.0, 'Refrigerada (2-8°C)', True, True, 'ATIVO', True, False, True, False, False, False, True, 'Vacina combinada contra difteria, tétano, coqueluche, Haemophilus influenzae tipo b e hepatite B.', 'Aplicar aos 2, 4 e 6 meses de idade.'),    ('VIP - Poliomielite Inativada', 'VIP', 'VAC-004', 'PNI-004', 'SUS-004', '10101010104', 'VIP', 'Rotina', 'Crianças', 'Instituto Butantan', 'INTRAMUSCULAR', 'DOSE', 3, 60, True, 4, 0.5, 'Coxa (lateral)', 60, None, 2.0, 8.0, 'Refrigerada (2-8°C)', True, True, 'ATIVO', True, False, True, False, False, False, True, 'Vacina inativada contra poliomielite. Protege contra paralisia infantil.', 'Aplicar aos 2, 4 e 6 meses de idade. Reforço aos 15 meses e 4 anos.'),    ('VOP - Poliomielite Oral', 'Sabin', 'VAC-005', 'PNI-005', 'SUS-005', '10101010105', 'VOP', 'Rotina', 'Crianças', 'Bio-Manguinhos / Fiocruz', 'ORAL', 'DOSE', 3, 60, True, 4, 0.1, 'Oral', 60, None, -15.0, -15.0, 'Congelada (-15°C)', False, False, 'ATIVO', True, False, True, False, False, False, True, 'Vacina oral contra poliomielite. Protege contra paralisia infantil.', 'Aplicar aos 2, 4 e 6 meses de idade. Reforço aos 15 meses e 4 anos.'),    ('Rotavírus Humano', 'Rotavírus Monovalente', 'VAC-006', 'PNI-006', 'SUS-006', '10101010106', 'ROTAVIRUS', 'Rotina', 'Crianças', 'GlaxoSmithKline Brasil Ltda', 'ORAL', 'DOSE', 2, 60, False, None, 1.5, 'Oral', 60, 210, 2.0, 8.0, 'Refrigerada (2-8°C)', True, False, 'ATIVO', True, False, True, False, False, False, True, 'Vacina contra rotavírus. Protege contra gastroenterite causada por rotavírus.', 'Aplicar aos 2 e 4 meses de idade. Primeira dose até 3 meses e 15 dias, última dose até 7 meses e 29 dias.'),    ('Pneumocócica 10-valente', 'Pneumo 10', 'VAC-007', 'PNI-007', 'SUS-007', '10101010107', 'PNEUMOCOCICA_10', 'Rotina', 'Crianças', 'GlaxoSmithKline Brasil Ltda', 'INTRAMUSCULAR', 'DOSE', 2, 60, True, 12, 0.5, 'Coxa (lateral)', 60, None, 2.0, 8.0, 'Refrigerada (2-8°C)', True, True, 'ATIVO', True, False, True, False, False, False, True, 'Vacina contra doenças pneumocócicas (pneumonia, meningite, otite).', 'Aplicar aos 2 e 4 meses de idade. Reforço aos 12 meses.'),    ('Meningocócica C', 'Meningo C', 'VAC-008', 'PNI-008', 'SUS-008', '10101010108', 'MENINGOCOCICA_C', 'Rotina', 'Crianças', 'GlaxoSmithKline Brasil Ltda', 'INTRAMUSCULAR', 'DOSE', 2, 60, True, 12, 0.5, 'Coxa (lateral)', 90, None, 2.0, 8.0, 'Refrigerada (2-8°C)', True, True, 'ATIVO', True, False, True, False, False, False, True, 'Vacina contra meningite meningocócica tipo C.', 'Aplicar aos 3 e 5 meses de idade. Reforço aos 12 meses.'),    ('Febre Amarela', 'Febre Amarela', 'VAC-009', 'PNI-009', 'SUS-009', '10101010109', 'FEBRE_AMARELA', 'Rotina', 'Crianças e Adultos', 'Instituto Butantan', 'SUBCUTANEA', 'DOSE', 1, None, True, 10, 0.5, 'Braço direito', 270, None, 2.0, 8.0, 'Refrigerada (2-8°C)', True, True, 'ATIVO', True, False, True, False, False, False, True, 'Vacina contra febre amarela. Protege contra a febre amarela silvestre e urbana.', 'Aplicar aos 9 meses de idade. Reforço aos 4 anos e a cada 10 anos.'),    ('Tríplice Viral', 'Sarampo, Caxumba e Rubéola', 'VAC-010', 'PNI-010', 'SUS-010', '10101010110', 'TRIPLICE_VIRAL', 'Rotina', 'Crianças', 'Instituto Butantan', 'SUBCUTANEA', 'DOSE', 2, 90, False, None, 0.5, 'Braço direito', 360, None, 2.0, 8.0, 'Refrigerada (2-8°C)', True, True, 'ATIVO', True, False, True, False, False, False, True, 'Vacina contra sarampo, caxumba e rubéola.', 'Aplicar aos 12 meses e 15 meses de idade.'),    ('Tetra Viral', 'Sarampo, Caxumba, Rubéola e Varicela', 'VAC-011', 'PNI-011', 'SUS-011', '10101010111', 'TETRA_VIRAL', 'Rotina', 'Crianças', 'GlaxoSmithKline Brasil Ltda', 'SUBCUTANEA', 'DOSE', 1, None, False, None, 0.5, 'Braço direito', 450, None, 2.0, 8.0, 'Refrigerada (2-8°C)', True, True, 'ATIVO', True, False, True, False, False, False, True, 'Vacina combinada contra sarampo, caxumba, rubéola e varicela.', 'Aplicar aos 15 meses de idade, substituindo a segunda dose da tríplice viral.'),    ('Varicela', 'Varicela', 'VAC-012', 'PNI-012', 'SUS-012', '10101010112', 'VARICELA', 'Rotina', 'Crianças', 'GlaxoSmithKline Brasil Ltda', 'SUBCUTANEA', 'DOSE', 1, None, False, None, 0.5, 'Braço direito', 450, None, 2.0, 8.0, 'Refrigerada (2-8°C)', True, True, 'ATIVO', True, False, True, False, False, False, True, 'Vacina contra varicela (catapora).', 'Aplicar aos 15 meses de idade, se não recebeu a tetra viral.'),    ('Hepatite A', 'Hepatite A', 'VAC-013', 'PNI-013', 'SUS-013', '10101010113', 'HEPATITE_A', 'Rotina', 'Crianças', 'Instituto Butantan', 'INTRAMUSCULAR', 'DOSE', 1, None, False, None, 0.5, 'Braço direito', 540, None, 2.0, 8.0, 'Refrigerada (2-8°C)', True, True, 'ATIVO', True, False, True, False, False, False, True, 'Vacina contra hepatite A. Protege contra infecção pelo vírus da hepatite A.', 'Aplicar aos 15 meses de idade.'),    ('HPV Quadrivalente', 'HPV 4', 'VAC-014', 'PNI-014', 'SUS-014', '10101010114', 'HPV_QUADRIVALENTE', 'Rotina', 'Adolescentes', 'Merck Sharp & Dohme', 'INTRAMUSCULAR', 'DOSE', 2, 180, False, None, 0.5, 'Braço direito', 3285, 5110, 2.0, 8.0, 'Refrigerada (2-8°C)', True, True, 'ATIVO', True, False, True, False, False, False, True, 'Vacina contra papilomavírus humano (tipos 6, 11, 16 e 18). Protege contra câncer de colo do útero e verrugas genitais.', 'Aplicar em meninas de 9 a 14 anos e meninos de 11 a 14 anos. Duas doses com intervalo de 6 meses.'),    ('DTP', 'Difteria, Tétano e Coqueluche', 'VAC-015', 'PNI-015', 'SUS-015', '10101010115', 'DTP', 'Rotina', 'Crianças', 'Instituto Butantan', 'INTRAMUSCULAR', 'DOSE', 1, None, True, 4, 0.5, 'Coxa (lateral)', 540, None, 2.0, 8.0, 'Refrigerada (2-8°C)', True, True, 'ATIVO', True, False, True, False, False, False, True, 'Vacina contra difteria, tétano e coqueluche. Reforço da pentavalente.', 'Aplicar aos 15 meses e 4 anos de idade.'),    ('Dengue', 'Dengvaxia', 'VAC-016', 'PNI-016', 'SUS-016', '10101010116', 'DENGUE', 'Especial', 'Crianças e Adultos', 'Sanofi Pasteur Ltda', 'SUBCUTANEA', 'DOSE', 3, 180, False, None, 0.5, 'Braço direito', 3285, None, 2.0, 8.0, 'Refrigerada (2-8°C)', True, True, 'ATIVO', False, False, False, False, False, False, True, 'Vacina contra dengue. Protege contra os 4 sorotipos do vírus da dengue.', 'Aplicar em pessoas de 9 a 45 anos que já tiveram dengue confirmada. Três doses com intervalo de 6 meses.'),    
    # Vacinas para Adultos e Idosos
    ('Dupla Adulto', 'DT - Difteria e Tétano', 'VAC-017', 'PNI-017', 'SUS-017', '10101010117', 'DUPLA_ADULTO', 'Rotina', 'Adultos', 'Instituto Butantan', 'INTRAMUSCULAR', 'DOSE', 1, None, True, 10, 0.5, 'Braço direito', 5475, None, 2.0, 8.0, 'Refrigerada (2-8°C)', True, True, 'ATIVO', True, False, True, False, False, False, True, 'Vacina contra difteria e tétano para adultos.', 'Aplicar a cada 10 anos como reforço.'),    ('Hepatite B Adulto', 'Hepatite B Recombinante', 'VAC-018', 'PNI-018', 'SUS-018', '10101010118', 'HEPATITE_B_ADULTO', 'Rotina', 'Adultos', 'Instituto Butantan', 'INTRAMUSCULAR', 'DOSE', 3, 30, False, None, 1.0, 'Braço direito', 5475, None, 2.0, 8.0, 'Refrigerada (2-8°C)', True, True, 'ATIVO', True, False, True, False, False, False, True, 'Vacina contra hepatite B para adultos não vacinados na infância.', 'Aplicar três doses com intervalo de 1 mês entre a primeira e segunda, e 6 meses entre a primeira e terceira.'),    ('Febre Amarela Adulto', 'Febre Amarela', 'VAC-019', 'PNI-019', 'SUS-019', '10101010119', 'FEBRE_AMARELA_ADULTO', 'Rotina', 'Adultos', 'Instituto Butantan', 'SUBCUTANEA', 'DOSE', 1, None, True, 10, 0.5, 'Braço direito', 5475, None, 2.0, 8.0, 'Refrigerada (2-8°C)', True, True, 'ATIVO', True, False, True, False, False, False, True, 'Vacina contra febre amarela para adultos. Reforço a cada 10 anos.', 'Aplicar em áreas de risco ou para viagens. Reforço a cada 10 anos.'),    ('Tríplice Viral Adulto', 'Sarampo, Caxumba e Rubéola', 'VAC-020', 'PNI-020', 'SUS-020', '10101010120', 'TRIPLICE_VIRAL_ADULTO', 'Rotina', 'Adultos', 'Instituto Butantan', 'SUBCUTANEA', 'DOSE', 1, None, False, None, 0.5, 'Braço direito', 5475, None, 2.0, 8.0, 'Refrigerada (2-8°C)', True, True, 'ATIVO', True, False, True, False, False, False, True, 'Vacina contra sarampo, caxumba e rubéola para adultos não vacinados.', 'Aplicar em adultos não vacinados ou com esquema incompleto.'),    ('Pneumocócica 23-valente', 'Pneumo 23', 'VAC-021', 'PNI-021', 'SUS-021', '10101010121', 'PNEUMOCOCICA_23_ADULTO', 'Rotina', 'Idosos', 'Merck Sharp & Dohme', 'INTRAMUSCULAR', 'DOSE', 1, None, True, 5, 0.5, 'Braço direito', 18250, None, 2.0, 8.0, 'Refrigerada (2-8°C)', True, True, 'ATIVO', True, False, True, False, False, False, True, 'Vacina contra doenças pneumocócicas para idosos e grupos de risco.', 'Aplicar em idosos a partir de 60 anos e grupos de risco. Reforço após 5 anos.'),    ('Influenza', 'Gripe', 'VAC-022', 'PNI-022', 'SUS-022', '10101010122', 'INFLUENZA', 'Campanha', 'Todas as idades', 'Instituto Butantan', 'INTRAMUSCULAR', 'DOSE', 1, None, True, 1, 0.5, 'Braço direito', 180, None, 2.0, 8.0, 'Refrigerada (2-8°C)', True, True, 'ATIVO', True, False, True, False, False, False, True, 'Vacina contra influenza (gripe). Composição atualizada anualmente.', 'Aplicar anualmente em campanhas de vacinação. Prioridade para grupos de risco.'),
    ('Herpes Zoster', 'Herpes Zoster', 'VAC-023', 'PNI-023', 'SUS-023', '10101010123', 'HERPES_ZOSTER', 'Especial', 'Idosos', 'GlaxoSmithKline Brasil Ltda', 'INTRAMUSCULAR', 'DOSE', 1, None, False, None, 0.5, 'Braço direito', 18250, None, 2.0, 8.0, 'Refrigerada (2-8°C)', True, True, 'ATIVO', False, False, False, False, False, False, True, 'Vacina contra herpes zoster (cobreiro). Reduz risco de neuralgia pós-herpética.', 'Aplicar em idosos a partir de 50 anos, especialmente a partir de 60 anos.'),    
    # Vacinas Especiais
    ('Raiva', 'Antirrábica', 'VAC-024', 'PNI-024', 'SUS-024', '10101010124', 'RABICA', 'Especial', 'Todas as idades', 'Instituto Butantan', 'INTRAMUSCULAR', 'DOSE', 4, 3, False, None, 1.0, 'Braço direito', 0, None, 2.0, 8.0, 'Refrigerada (2-8°C)', True, True, 'ATIVO', False, False, False, False, False, False, True, 'Vacina contra raiva. Usada em profilaxia pré e pós-exposição.', 'Aplicar em casos de exposição a animais suspeitos de raiva ou para profilaxia pré-exposição em grupos de risco.'),    ('Antitetânica', 'Toxoide Tetânico', 'VAC-025', 'PNI-025', 'SUS-025', '10101010125', 'ANTITETANICA', 'Especial', 'Todas as idades', 'Instituto Butantan', 'INTRAMUSCULAR', 'DOSE', 1, None, False, None, 0.5, 'Braço direito', 0, None, 2.0, 8.0, 'Refrigerada (2-8°C)', True, True, 'ATIVO', False, False, False, False, False, False, True, 'Vacina contra tétano. Usada em casos de ferimentos com risco de tétano.', 'Aplicar em casos de ferimentos com risco de tétano, especialmente se última dose há mais de 5 anos.'),    ('Hepatite A e B Combinada', 'Twinrix', 'VAC-026', 'PNI-026', 'SUS-026', '10101010126', 'HEPATITE_A_E_B', 'Especial', 'Adultos', 'GlaxoSmithKline Brasil Ltda', 'INTRAMUSCULAR', 'DOSE', 3, 30, False, None, 1.0, 'Braço direito', 5475, None, 2.0, 8.0, 'Refrigerada (2-8°C)', True, True, 'ATIVO', False, False, False, False, False, False, True, 'Vacina combinada contra hepatite A e B para adultos.', 'Aplicar em adultos não vacinados contra hepatite A e B. Três doses com intervalo de 1 mês entre primeira e segunda, e 6 meses entre primeira e terceira.'),    
    # Vacinas COVID-19
    ('COVID-19 - Coronavac', 'Coronavac', 'VAC-027', 'PNI-027', 'SUS-027', '10101010127', 'COVID_19_CORONAVAC', 'Campanha', 'Todas as idades', 'Instituto Butantan', 'INTRAMUSCULAR', 'DOSE', 2, 28, True, 6, 0.5, 'Braço direito', 0, None, 2.0, 8.0, 'Refrigerada (2-8°C)', True, True, 'ATIVO', True, False, True, False, False, False, True, 'Vacina contra COVID-19 (Coronavac). Vacina de vírus inativado.', 'Aplicar duas doses com intervalo de 28 dias. Reforço após 6 meses.'),
    ('COVID-19 - AstraZeneca', 'Covishield', 'VAC-028', 'PNI-028', 'SUS-028', '10101010128', 'COVID_19_ASTRAZENECA', 'Campanha', 'Todas as idades', 'AstraZeneca do Brasil', 'INTRAMUSCULAR', 'DOSE', 2, 84, True, 6, 0.5, 'Braço direito', 0, None, 2.0, 8.0, 'Refrigerada (2-8°C)', True, True, 'ATIVO', True, False, True, False, False, False, True, 'Vacina contra COVID-19 (AstraZeneca). Vacina de vetor viral.', 'Aplicar duas doses com intervalo de 12 semanas. Reforço após 6 meses.'),
    ('COVID-19 - Pfizer', 'Comirnaty', 'VAC-029', 'PNI-029', 'SUS-029', '10101010129', 'COVID_19_PFIZER', 'Campanha', 'Todas as idades', 'Pfizer do Brasil', 'INTRAMUSCULAR', 'DOSE', 2, 21, True, 6, 0.3, 'Braço direito', 0, None, -80.0, -60.0, 'Ultra-congelada (-80°C a -60°C)', True, True, 'ATIVO', True, False, True, False, False, False, True, 'Vacina contra COVID-19 (Pfizer). Vacina de RNA mensageiro.', 'Aplicar duas doses com intervalo de 21 dias. Reforço após 6 meses. Após descongelamento, conservar entre 2-8°C por até 5 dias.'),
    ('COVID-19 - Janssen', 'Janssen', 'VAC-030', 'PNI-030', 'SUS-030', '10101010130', 'COVID_19_JANSSEN', 'Campanha', 'Todas as idades', 'Janssen-Cilag Farmacêutica Ltda', 'INTRAMUSCULAR', 'DOSE', 1, None, True, 6, 0.5, 'Braço direito', 0, None, 2.0, 8.0, 'Refrigerada (2-8°C)', True, True, 'ATIVO', True, False, True, False, False, False, True, 'Vacina contra COVID-19 (Janssen). Vacina de dose única com vetor viral.', 'Aplicar dose única. Reforço após 6 meses.'),
    ('COVID-19 - Butanvac', 'Butanvac', 'VAC-031', 'PNI-031', 'SUS-031', '10101010131', 'COVID_19_BUTANVAC', 'Campanha', 'Todas as idades', 'Instituto Butantan', 'INTRAMUSCULAR', 'DOSE', 2, 28, True, 6, 0.5, 'Braço direito', 0, None, 2.0, 8.0, 'Refrigerada (2-8°C)', True, True, 'ATIVO', True, False, True, False, False, False, True, 'Vacina contra COVID-19 (Butanvac). Vacina de vírus inativado desenvolvida pelo Butantan.', 'Aplicar duas doses com intervalo de 28 dias. Reforço após 6 meses.'),
    
    # Vacinas para Viagens
    ('Febre Tifoide', 'Typhim Vi', 'VAC-032', 'PNI-032', 'SUS-032', '10101010132', 'FEBRE_TIFOIDE', 'Viagem', 'Viajantes', 'Sanofi Pasteur Ltda', 'INTRAMUSCULAR', 'DOSE', 1, None, True, 3, 0.5, 'Braço direito', 0, None, 2.0, 8.0, 'Refrigerada (2-8°C)', True, True, 'ATIVO', False, False, False, False, False, False, True, 'Vacina contra febre tifoide. Recomendada para viajantes para áreas endêmicas.', 'Aplicar antes de viagens para áreas endêmicas. Reforço a cada 3 anos.'),    ('Cólera', 'Dukoral', 'VAC-033', 'PNI-033', 'SUS-033', '10101010133', 'COLERA', 'Viagem', 'Viajantes', 'Valneva', 'ORAL', 'DOSE', 2, 14, True, 2, 3.0, 'Oral', 0, None, 2.0, 8.0, 'Refrigerada (2-8°C)', True, False, 'ATIVO', False, False, False, False, False, False, True, 'Vacina contra cólera. Recomendada para viajantes para áreas endêmicas.', 'Aplicar antes de viagens para áreas endêmicas. Duas doses com intervalo de 14 dias. Reforço a cada 2 anos.'),    ('Encefalite Japonesa', 'Ixiaro', 'VAC-034', 'PNI-034', 'SUS-034', '10101010134', 'ENCEFALITE_JAPONESA', 'Viagem', 'Viajantes', 'Valneva', 'INTRAMUSCULAR', 'DOSE', 2, 28, True, 1, 0.5, 'Braço direito', 0, None, 2.0, 8.0, 'Refrigerada (2-8°C)', True, True, 'ATIVO', False, False, False, False, False, False, True, 'Vacina contra encefalite japonesa. Recomendada para viajantes para Ásia.', 'Aplicar antes de viagens para áreas endêmicas na Ásia. Duas doses com intervalo de 28 dias. Reforço após 1 ano.'),    ('Meningocócica ACWY', 'Menveo', 'VAC-035', 'PNI-035', 'SUS-035', '10101010135', 'MENINGOCOCICA_ACWY_VIAGEM', 'Viagem', 'Viajantes', 'GlaxoSmithKline Brasil Ltda', 'INTRAMUSCULAR', 'DOSE', 1, None, True, 5, 0.5, 'Braço direito', 0, None, 2.0, 8.0, 'Refrigerada (2-8°C)', True, True, 'ATIVO', False, False, False, False, False, False, True, 'Vacina contra meningite meningocócica tipos A, C, W e Y. Recomendada para viajantes.', 'Aplicar antes de viagens para áreas endêmicas (cinturão da meningite na África, peregrinação a Meca). Reforço a cada 5 anos.'),    
    # Vacinas Adicionais
    ('Pneumocócica 13-valente', 'Prevenar 13', 'VAC-036', 'PNI-036', 'SUS-036', '10101010136', 'PNEUMOCOCICA_13', 'Especial', 'Crianças e Adultos', 'Pfizer do Brasil', 'INTRAMUSCULAR', 'DOSE', 1, None, False, None, 0.5, 'Braço direito', 0, None, 2.0, 8.0, 'Refrigerada (2-8°C)', True, True, 'ATIVO', False, False, False, False, False, False, True, 'Vacina contra doenças pneumocócicas 13-valente. Usada em grupos de risco.', 'Aplicar em crianças não vacinadas com pneumo 10 e em grupos de risco.'),    ('Meningocócica B', 'Bexsero', 'VAC-037', 'PNI-037', 'SUS-037', '10101010137', 'MENINGOCOCICA_B', 'Especial', 'Crianças e Adultos', 'GlaxoSmithKline Brasil Ltda', 'INTRAMUSCULAR', 'DOSE', 2, 60, False, None, 0.5, 'Braço direito', 0, None, 2.0, 8.0, 'Refrigerada (2-8°C)', True, True, 'ATIVO', False, False, False, False, False, False, True, 'Vacina contra meningite meningocócica tipo B.', 'Aplicar em grupos de risco ou em surtos. Duas doses com intervalo de 2 meses.'),    ('HPV Bivalente', 'Cervarix', 'VAC-038', 'PNI-038', 'SUS-038', '10101010138', 'HPV_BIVALENTE', 'Especial', 'Adolescentes e Adultos', 'GlaxoSmithKline Brasil Ltda', 'INTRAMUSCULAR', 'DOSE', 2, 180, False, None, 0.5, 'Braço direito', 3285, None, 2.0, 8.0, 'Refrigerada (2-8°C)', True, True, 'ATIVO', False, False, False, False, False, False, True, 'Vacina contra papilomavírus humano (tipos 16 e 18). Protege contra câncer de colo do útero.', 'Aplicar em meninas e mulheres de 9 a 45 anos. Duas doses com intervalo de 6 meses.'),    ('DTPa - Difteria, Tétano e Coqueluche Acelular', 'DTPa', 'VAC-039', 'PNI-039', 'SUS-039', '10101010139', 'DTPA', 'Especial', 'Crianças e Adultos', 'GlaxoSmithKline Brasil Ltda', 'INTRAMUSCULAR', 'DOSE', 1, None, True, 10, 0.5, 'Braço direito', 0, None, 2.0, 8.0, 'Refrigerada (2-8°C)', True, True, 'ATIVO', False, False, False, False, False, False, True, 'Vacina acelular contra difteria, tétano e coqueluche. Menos reatogênica que DTP.', 'Aplicar como alternativa à DTP em pessoas com reações adversas. Reforço a cada 10 anos.'),    ('DTPa + VIP', 'DTPa + VIP', 'VAC-040', 'PNI-040', 'SUS-040', '10101010140', 'DTPA_VIP', 'Especial', 'Crianças', 'GlaxoSmithKline Brasil Ltda', 'INTRAMUSCULAR', 'DOSE', 1, None, True, 4, 0.5, 'Coxa (lateral)', 540, None, 2.0, 8.0, 'Refrigerada (2-8°C)', True, True, 'ATIVO', False, False, False, False, False, False, True, 'Vacina combinada DTPa + VIP. Reforço da pentavalente com componente acelular.', 'Aplicar aos 15 meses e 4 anos de idade como reforço.'),]

def escapar(texto):
    """Escapa aspas simples para SQL."""
    return texto.replace("'", "''") if texto else None

def gerar_sql():
    sql_lines = []
    for (nome, nome_comercial, cod_int, cod_pni, cod_sus, reg_anvisa, tipo_str, categoria,
         grupo_alvo, fabricante_nome, via_str, unidade_str, num_doses, intervalo_doses,
         dose_reforco, intervalo_reforco, dose_ml, local_aplic, idade_min_dias, idade_max_dias,
         temp_min, temp_max, tipo_cons, proteger_luz, agitar, status_str, cal_basico, cal_campanha, obrigatoria,
         gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, desc, indicacoes) in VACINAS:
        
        nome_esc = escapar(nome)
        nome_com_esc = escapar(nome_comercial)
        cod_int_esc = escapar(cod_int)
        cod_pni_esc = escapar(cod_pni)
        cod_sus_esc = escapar(cod_sus)
        reg_anvisa_esc = escapar(reg_anvisa)
        categoria_esc = escapar(categoria)
        grupo_alvo_esc = escapar(grupo_alvo)
        fabricante_esc = escapar(fabricante_nome)
        local_aplic_esc = escapar(local_aplic)
        tipo_cons_esc = escapar(tipo_cons)
        desc_esc = escapar(desc)
        indicacoes_esc = escapar(indicacoes)
        
        tipo_int = TIPO_VACINA.get(tipo_str, 99)
        via_int = VIA_ADMINISTRACAO.get(via_str, 99)
        unidade_int = UNIDADE_MEDIDA.get(unidade_str, 13)
        status_int = STATUS_ATIVO.get(status_str, 1)
        
        sql = f"""-- {nome}
INSERT INTO public.vacinas (
    id, criado_em, atualizado_em, ativo, nome, nome_comercial, codigo_interno,
    codigo_pni, codigo_sus, registro_anvisa, tipo, categoria, grupo_alvo,
    fabricante_id, via_administracao, unidade_medida, status, disponivel_uso,
    numero_doses, intervalo_doses_dias, dose_reforco, intervalo_reforco_anos, dose_ml,
    local_aplicacao_padrao, idade_minima_dias, idade_maxima_dias,
    temperatura_conservacao_min, temperatura_conservacao_max, tipo_conservacao,
    proteger_luz, agitar_antes_uso, calendario_basico, calendario_campanha, obrigatoria,
    gestante_pode, lactante_pode, imunocomprometido_pode, sincronizar_pni, descricao, indicacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, '{nome_esc}', '{nome_com_esc}', '{cod_int_esc}',
    '{cod_pni_esc}', '{cod_sus_esc}', '{reg_anvisa_esc}', {tipo_int}, '{categoria_esc}', '{grupo_alvo_esc}',
    (SELECT id FROM public.fabricantes_vacina WHERE nome = '{fabricante_esc}' LIMIT 1),
    {via_int}, {unidade_int}, {status_int}, true,
    {num_doses}, {f'{intervalo_doses}' if intervalo_doses else 'NULL'}, {str(dose_reforco).lower()},
    {f'{intervalo_reforco}' if intervalo_reforco else 'NULL'}, {dose_ml},
    '{local_aplic_esc}', {f'{idade_min_dias}' if idade_min_dias else 'NULL'}, {f'{idade_max_dias}' if idade_max_dias else 'NULL'},
    {temp_min}, {temp_max}, '{tipo_cons_esc}',
    {str(proteger_luz).lower()}, {str(agitar).lower()}, {str(cal_basico).lower()}, {str(cal_campanha).lower()}, {str(obrigatoria).lower()},
    {str(gestante_pode).lower()}, {str(lactante_pode).lower()}, {str(imunocomprometido_pode).lower()}, {str(sincronizar_pni).lower()}, '{desc_esc}', '{indicacoes_esc}'
WHERE NOT EXISTS (SELECT 1 FROM public.vacinas WHERE codigo_pni = '{cod_pni_esc}' OR codigo_sus = '{cod_sus_esc}' OR nome = '{nome_esc}');
"""
        sql_lines.append(sql)
    return '\n'.join(sql_lines)

if __name__ == '__main__':
    print(gerar_sql())

