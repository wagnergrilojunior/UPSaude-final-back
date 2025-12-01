#!/usr/bin/env python3
"""
Script para gerar SQL de inserção de medicamentos.
Gera medicamentos reais do Brasil (ANVISA, SUS, SIGTAP).
"""

import random
from datetime import datetime, timedelta

# Mapeamento de enums
FORMA_FARMACEUTICA = {
    "COMPRIMIDO": 1, "CAPSULA": 2, "SOLUCAO_ORAL": 3, "SUSPENSAO_ORAL": 4,
    "XAROPE": 5, "GOTAS": 6, "PO_PARA_SOLUCAO": 7, "INJETAVEL": 8,
    "CREME": 9, "POMADA": 10, "GEL": 11, "LOCAO": 12, "SPRAY": 13,
    "AEROSOL": 14, "SUPPOSITORIO": 15, "ENEMA": 16, "COLIRIO": 17,
    "OTICO": 18, "NASAL": 19, "ADESIVO": 20, "INALACAO": 21, "OUTROS": 99
}

CLASSE_TERAPEUTICA = {
    "ANALGESICO_ANTIPIRETICO": 1, "ANTIINFLAMATORIO": 2, "ANTIBIOTICO": 3,
    "ANTIVIRAL": 4, "ANTIFUNGICO": 5, "ANTIPARASITARIO": 6,
    "ANTIHISTAMINICO": 7, "BRONCODILATADOR": 8, "CORTICOIDE": 9,
    "ANTIDEPRESSIVO": 10, "ANSIOLITICO": 11, "ANTIPSICOTICO": 12,
    "ANTICONVULSIVANTE": 13, "ANTIACIDO": 14, "LAXANTE": 15,
    "ANTIDIARREICO": 16, "VITAMINA": 17, "MINERAL": 18, "HORMONIO": 19,
    "ANTIDIABETICO": 20, "ANTIHIPERTENSIVO": 21, "DIURETICO": 22,
    "CARDIOTONICO": 23, "ANTICOAGULANTE": 24, "ANTIARRITMICO": 25, "OUTROS": 99
}

VIA_ADMINISTRACAO = {
    "ORAL": 1, "SUBLINGUAL": 2, "RETAL": 3, "VAGINAL": 4, "TOPICA": 5,
    "TRANSDERMICA": 6, "INTRAMUSCULAR": 7, "INTRAVENOSA": 8, "SUBCUTANEA": 9,
    "INTRADERMICA": 10, "NASAL": 16, "OFTALMICA": 17, "OTICA": 18,
    "INALATORIA": 19, "OUTRA": 99
}

UNIDADE_MEDIDA = {
    "MILIGRAMA": 1, "GRAMA": 2, "MICROGRAMA": 3, "MILILITRO": 4, "LITRO": 5,
    "UNIDADE": 6, "COMPRIMIDO": 7, "CAPSULA": 8, "AMPOLA": 9, "FRASCO": 10,
    "GOTA": 11, "APLICACAO": 12, "DOSE": 13, "OUTROS": 99
}

TIPO_CONTROLE = {
    "LIVRE": 1, "RECEITA_SIMPLES": 2, "RECEITA_CONTROLADA": 3,
    "RECEITA_AZUL": 4, "RECEITA_AMARELA": 5, "RECEITA_BRANCA": 6,
    "RECEITA_BRANCA_ESPECIAL": 7, "RECEITA_VERDE": 8, "RECEITA_VERDE_ESPECIAL": 9,
    "PORTARIA_344": 10, "OUTRO": 99
}

# Medicamentos reais do Brasil
MEDICAMENTOS = [
    # Analgésicos e Antipiréticos
    ("Paracetamol", "Tylenol", "Paracetamol", "500mg", 1, 1, 500.0, "mg", "1 comprimido a cada 6-8 horas", "Tomar com água", 1, 1, "Genérico", None, "A02AA01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Analgésico e antipirético", "Dor e febre", "Não exceder 4g/dia"),
    ("Dipirona", "Novalgina", "Dipirona sódica", "500mg", 1, 1, 500.0, "mg", "1 comprimido a cada 6-8 horas", "Tomar com água", 1, 1, "Genérico", None, "N02BB02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Analgésico e antipirético", "Dor e febre", "Pode causar agranulocitose"),
    ("Ibuprofeno", "Advil", "Ibuprofeno", "400mg", 1, 1, 400.0, "mg", "1 comprimido a cada 8 horas", "Tomar com alimentos", 2, 1, "Genérico", None, "M01AE01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Anti-inflamatório não esteroidal", "Dor, inflamação e febre", "Evitar uso prolongado"),
    ("Ácido Acetilsalicílico", "Aspirina", "Ácido acetilsalicílico", "100mg", 1, 1, 100.0, "mg", "1 comprimido ao dia", "Tomar com água", 1, 1, "Genérico", None, "B01AC06", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antiagregante plaquetário", "Prevenção cardiovascular", "Evitar em crianças"),
    
    # Antibióticos
    ("Amoxicilina", "Amoxil", "Amoxicilina", "500mg", 1, 1, 500.0, "mg", "1 comprimido a cada 8 horas", "Tomar com água", 3, 1, "Genérico", None, "J01CA04", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antibiótico beta-lactâmico", "Infecções bacterianas", "Completar tratamento"),
    ("Azitromicina", "Zitromax", "Azitromicina di-hidratada", "500mg", 1, 1, 500.0, "mg", "1 comprimido ao dia por 3 dias", "Tomar em jejum", 3, 1, "Genérico", None, "J01FA10", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antibiótico macrolídeo", "Infecções respiratórias", "Tomar 1h antes ou 2h após refeições"),
    ("Cefalexina", "Keflex", "Cefalexina monoidratada", "500mg", 1, 1, 500.0, "mg", "1 cápsula a cada 6 horas", "Tomar com água", 3, 2, "Genérico", None, "J01DB01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antibiótico cefalosporina", "Infecções bacterianas", "Completar tratamento"),
    ("Ciprofloxacino", "Cipro", "Ciprofloxacino cloridrato", "500mg", 1, 1, 500.0, "mg", "1 comprimido a cada 12 horas", "Tomar com água", 3, 1, "Genérico", None, "J01MA02", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Antibiótico fluoroquinolona", "Infecções bacterianas", "Evitar exposição solar"),
    
    # Anti-inflamatórios
    ("Diclofenaco Sódico", "Voltaren", "Diclofenaco sódico", "50mg", 1, 1, 50.0, "mg", "1 comprimido a cada 8 horas", "Tomar com alimentos", 2, 1, "Genérico", None, "M01AB05", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Anti-inflamatório não esteroidal", "Dor e inflamação", "Evitar uso prolongado"),
    ("Naproxeno", "Flanax", "Naproxeno sódico", "550mg", 1, 1, 550.0, "mg", "1 comprimido a cada 12 horas", "Tomar com alimentos", 2, 1, "Genérico", None, "M01AE02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Anti-inflamatório não esteroidal", "Dor e inflamação", "Evitar uso prolongado"),
    ("Cetoprofeno", "Profenid", "Cetoprofeno", "100mg", 1, 1, 100.0, "mg", "1 comprimido a cada 12 horas", "Tomar com alimentos", 2, 1, "Genérico", None, "M01AE03", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Anti-inflamatório não esteroidal", "Dor e inflamação", "Evitar uso prolongado"),
    
    # Antihistamínicos
    ("Loratadina", "Claritin", "Loratadina", "10mg", 1, 1, 10.0, "mg", "1 comprimido ao dia", "Tomar com água", 7, 1, "Genérico", None, "R06AX13", None, None, None, None, 1, False, False, False, False, False, True, True, True, True, "Antihistamínico", "Rinite alérgica, urticária", "Não causa sonolência"),
    ("Desloratadina", "Desalex", "Desloratadina", "5mg", 1, 1, 5.0, "mg", "1 comprimido ao dia", "Tomar com água", 7, 1, "Genérico", None, "R06AX27", None, None, None, None, 1, False, False, False, False, False, True, True, True, True, "Antihistamínico", "Rinite alérgica, urticária", "Não causa sonolência"),
    ("Cetirizina", "Zyrtec", "Cetirizina dicloridrato", "10mg", 1, 1, 10.0, "mg", "1 comprimido ao dia", "Tomar com água", 7, 1, "Genérico", None, "R06AE07", None, None, None, None, 1, False, False, False, False, False, True, True, True, True, "Antihistamínico", "Rinite alérgica, urticária", "Pode causar sonolência"),
    
    # Broncodilatadores
    ("Salbutamol", "Aerolin", "Salbutamol sulfato", "100mcg/dose", 13, 19, 100.0, "mcg", "2 jatos a cada 4-6 horas", "Agitar antes de usar", 8, 13, "Genérico", None, "R03AC02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Broncodilatador", "Asma, bronquite", "Uso em crises"),
    ("Brometo de Ipratrópio", "Atrovent", "Ipratrópio brometo", "20mcg/dose", 13, 19, 20.0, "mcg", "2 jatos a cada 6 horas", "Agitar antes de usar", 8, 13, "Genérico", None, "R03BB01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Broncodilatador anticolinérgico", "Asma, bronquite", "Uso em crises"),
    
    # Corticoides
    ("Prednisona", "Meticorten", "Prednisona", "20mg", 1, 1, 20.0, "mg", "Conforme prescrição médica", "Tomar com alimentos", 9, 1, "Genérico", None, "H02AB07", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Corticoide sistêmico", "Inflamações, alergias", "Não suspender abruptamente"),
    ("Dexametasona", "Decadron", "Dexametasona", "4mg", 1, 1, 4.0, "mg", "Conforme prescrição médica", "Tomar com alimentos", 9, 1, "Genérico", None, "H02AB02", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Corticoide sistêmico", "Inflamações, alergias", "Não suspender abruptamente"),
    ("Beclometasona", "Clenil", "Beclometasona dipropionato", "250mcg/dose", 13, 19, 250.0, "mcg", "2 jatos 2x ao dia", "Agitar antes de usar", 9, 13, "Genérico", None, "R03BA01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Corticoide inalatório", "Asma", "Enxaguar boca após uso"),
    
    # Antidepressivos
    ("Sertralina", "Zoloft", "Sertralina cloridrato", "50mg", 1, 1, 50.0, "mg", "1 comprimido ao dia", "Tomar com água", 10, 1, "Genérico", None, "N06AB06", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Antidepressivo ISRS", "Depressão, ansiedade", "Efeito após 2-4 semanas"),
    ("Fluoxetina", "Prozac", "Fluoxetina cloridrato", "20mg", 1, 1, 20.0, "mg", "1 cápsula ao dia", "Tomar com água", 10, 2, "Genérico", None, "N06AB03", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Antidepressivo ISRS", "Depressão, ansiedade", "Efeito após 2-4 semanas"),
    ("Amitriptilina", "Tryptanol", "Amitriptilina cloridrato", "25mg", 1, 1, 25.0, "mg", "1 comprimido ao deitar", "Tomar com água", 10, 1, "Genérico", None, "N06AA09", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Antidepressivo tricíclico", "Depressão, dor crônica", "Pode causar sonolência"),
    
    # Ansiolíticos
    ("Diazepam", "Valium", "Diazepam", "10mg", 1, 1, 10.0, "mg", "Conforme prescrição médica", "Tomar com água", 11, 1, "Genérico", None, "N05BA01", None, None, None, None, 4, True, False, False, False, False, True, True, True, True, "Ansiolítico benzodiazepínico", "Ansiedade, insônia", "Pode causar dependência"),
    ("Alprazolam", "Frontal", "Alprazolam", "0,5mg", 1, 1, 0.5, "mg", "Conforme prescrição médica", "Tomar com água", 11, 1, "Genérico", None, "N05BA12", None, None, None, None, 4, True, False, False, False, False, True, True, True, True, "Ansiolítico benzodiazepínico", "Ansiedade, pânico", "Pode causar dependência"),
    ("Clonazepam", "Rivotril", "Clonazepam", "2mg", 1, 1, 2.0, "mg", "Conforme prescrição médica", "Tomar com água", 11, 1, "Genérico", None, "N03AE01", None, None, None, None, 4, True, False, False, False, False, True, True, True, True, "Ansiolítico e anticonvulsivante", "Ansiedade, convulsões", "Pode causar dependência"),
    
    # Antiácidos
    ("Omeprazol", "Losec", "Omeprazol", "20mg", 1, 1, 20.0, "mg", "1 cápsula pela manhã em jejum", "Tomar 30min antes do café", 14, 2, "Genérico", None, "A02BC01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Inibidor de bomba de prótons", "Gastrite, úlcera", "Tomar em jejum"),
    ("Pantoprazol", "Pantozol", "Pantoprazol sódico", "40mg", 1, 1, 40.0, "mg", "1 comprimido pela manhã", "Tomar com água", 14, 1, "Genérico", None, "A02BC02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Inibidor de bomba de prótons", "Gastrite, úlcera", "Tomar em jejum"),
    ("Ranitidina", "Antak", "Ranitidina cloridrato", "150mg", 1, 1, 150.0, "mg", "1 comprimido 2x ao dia", "Tomar com água", 14, 1, "Genérico", None, "A02BA02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Bloqueador H2", "Gastrite, úlcera", "Tomar antes das refeições"),
    
    # Laxantes
    ("Lactulose", "Lactulona", "Lactulose", "10g/15ml", 5, 1, 10.0, "g", "15-30ml ao deitar", "Tomar com água", 15, 4, "Genérico", None, "A06AD11", None, None, None, None, 1, False, False, False, False, False, True, True, True, True, "Laxante osmótico", "Constipação", "Efeito após 24-48h"),
    ("Bisacodil", "Dulcolax", "Bisacodil", "5mg", 1, 1, 5.0, "mg", "1-2 comprimidos ao deitar", "Tomar com água", 15, 1, "Genérico", None, "A06AB02", None, None, None, None, 1, False, False, False, False, False, True, True, True, True, "Laxante estimulante", "Constipação", "Efeito após 6-12h"),
    
    # Antidiarreicos
    ("Loperamida", "Imodium", "Loperamida cloridrato", "2mg", 1, 1, 2.0, "mg", "2 comprimidos após primeira evacuação", "Tomar com água", 16, 1, "Genérico", None, "A07DA03", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antidiarreico", "Diarreia aguda", "Não usar em crianças <2 anos"),
    
    # Vitaminas
    ("Vitamina D3", "Addera D3", "Colecalciferol", "2000UI", 1, 1, 2000.0, "UI", "1 cápsula ao dia", "Tomar com alimentos", 17, 2, "Genérico", None, "A11CC05", None, None, None, None, 1, False, False, False, False, False, True, True, True, True, "Vitamina D", "Deficiência de vitamina D", "Tomar com alimentos gordurosos"),
    ("Ácido Fólico", "Folacin", "Ácido fólico", "5mg", 1, 1, 5.0, "mg", "1 comprimido ao dia", "Tomar com água", 17, 1, "Genérico", None, "B03BB01", None, None, None, None, 1, False, False, False, False, False, True, True, True, True, "Vitamina B9", "Anemia, gestação", "Importante na gestação"),
    ("Vitamina B12", "Cianocobalamina", "Cianocobalamina", "1000mcg", 8, 1, 1000.0, "mcg", "1 ampola IM mensal", "Aplicar via intramuscular", 17, 8, "Genérico", None, "B03BA01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Vitamina B12", "Anemia perniciosa", "Aplicação intramuscular"),
    
    # Hormônios
    ("Levotiroxina", "Puran T4", "Levotiroxina sódica", "50mcg", 1, 1, 50.0, "mcg", "1 comprimido em jejum", "Tomar 30min antes do café", 19, 1, "Genérico", None, "H03AA01", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Hormônio tireoidiano", "Hipotireoidismo", "Tomar sempre em jejum"),
    ("Metformina", "Glifage", "Metformina cloridrato", "500mg", 1, 1, 500.0, "mg", "1 comprimido 2x ao dia", "Tomar com alimentos", 20, 1, "Genérico", None, "A10BA02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antidiabético", "Diabetes tipo 2", "Pode causar desconforto gastrointestinal"),
    ("Glibenclamida", "Daonil", "Glibenclamida", "5mg", 1, 1, 5.0, "mg", "1 comprimido antes do café", "Tomar antes das refeições", 20, 1, "Genérico", None, "A10BB01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antidiabético", "Diabetes tipo 2", "Risco de hipoglicemia"),
    
    # Anti-hipertensivos
    ("Losartana", "Cozaar", "Losartana potássica", "50mg", 1, 1, 50.0, "mg", "1 comprimido ao dia", "Tomar com água", 21, 1, "Genérico", None, "C09CA01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Anti-hipertensivo", "Hipertensão arterial", "Tomar sempre no mesmo horário"),
    ("Amlodipina", "Norvasc", "Amlodipina besilato", "5mg", 1, 1, 5.0, "mg", "1 comprimido ao dia", "Tomar com água", 21, 1, "Genérico", None, "C08CA01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Bloqueador de canais de cálcio", "Hipertensão arterial", "Pode causar edema"),
    ("Enalapril", "Renitec", "Enalapril maleato", "10mg", 1, 1, 10.0, "mg", "1 comprimido ao dia", "Tomar com água", 21, 1, "Genérico", None, "C09AA02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Inibidor de ECA", "Hipertensão arterial", "Pode causar tosse seca"),
    ("Propranolol", "Inderal", "Propranolol cloridrato", "40mg", 1, 1, 40.0, "mg", "1 comprimido 2x ao dia", "Tomar com água", 21, 1, "Genérico", None, "C07AA05", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Betabloqueador", "Hipertensão, arritmias", "Não suspender abruptamente"),
    
    # Diuréticos
    ("Hidroclorotiazida", "Clorana", "Hidroclorotiazida", "25mg", 1, 1, 25.0, "mg", "1 comprimido pela manhã", "Tomar com água", 22, 1, "Genérico", None, "C03AA03", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Diurético tiazídico", "Hipertensão, edema", "Pode causar hipocalemia"),
    ("Furosemida", "Lasix", "Furosemida", "40mg", 1, 1, 40.0, "mg", "1 comprimido pela manhã", "Tomar com água", 22, 1, "Genérico", None, "C03CA01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Diurético de alça", "Edema, insuficiência cardíaca", "Pode causar hipocalemia"),
    
    # Anticoagulantes
    ("Varfarina", "Marevan", "Varfarina sódica", "5mg", 1, 1, 5.0, "mg", "Conforme prescrição médica", "Tomar sempre no mesmo horário", 24, 1, "Genérico", None, "B01AA03", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Anticoagulante", "Prevenção de trombose", "Monitorar INR regularmente"),
    ("AAS", "Aspirina Prevent", "Ácido acetilsalicílico", "100mg", 1, 1, 100.0, "mg", "1 comprimido ao dia", "Tomar com água", 24, 1, "Genérico", None, "B01AC06", None, None, None, None, 1, False, False, False, False, False, True, True, True, True, "Antiagregante plaquetário", "Prevenção cardiovascular", "Evitar em crianças"),
    
    # Anticonvulsivantes
    ("Fenitoína", "Hidantal", "Fenitoína sódica", "100mg", 1, 1, 100.0, "mg", "Conforme prescrição médica", "Tomar com água", 13, 1, "Genérico", None, "N03AB02", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Anticonvulsivante", "Epilepsia", "Monitorar níveis séricos"),
    ("Carbamazepina", "Tegretol", "Carbamazepina", "200mg", 1, 1, 200.0, "mg", "Conforme prescrição médica", "Tomar com água", 13, 1, "Genérico", None, "N03AF01", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Anticonvulsivante", "Epilepsia, neuralgia", "Monitorar hemograma"),
    
    # Antifúngicos
    ("Fluconazol", "Zoltec", "Fluconazol", "150mg", 1, 1, 150.0, "mg", "1 comprimido em dose única", "Tomar com água", 5, 1, "Genérico", None, "J02AC01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antifúngico", "Candidíase", "Dose única para candidíase"),
    ("Nistatina", "Micostatin", "Nistatina", "100.000UI/ml", 6, 1, 100000.0, "UI", "4-6ml 4x ao dia", "Manter na boca antes de engolir", 5, 4, "Genérico", None, "A07AA02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antifúngico", "Candidíase oral", "Manter na boca"),
    
    # Antivirais
    ("Aciclovir", "Zovirax", "Aciclovir", "200mg", 1, 1, 200.0, "mg", "1 comprimido 5x ao dia", "Tomar com água", 4, 1, "Genérico", None, "J05AB01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antiviral", "Herpes simples, zoster", "Iniciar precocemente"),
    ("Oseltamivir", "Tamiflu", "Oseltamivir fosfato", "75mg", 1, 1, 75.0, "mg", "1 cápsula 2x ao dia por 5 dias", "Tomar com água", 4, 2, "Genérico", None, "J05AH02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antiviral", "Influenza", "Iniciar nas primeiras 48h"),
    
    # Antiparasitários
    ("Albendazol", "Zentel", "Albendazol", "400mg", 1, 1, 400.0, "mg", "1 comprimido em dose única", "Tomar com alimentos", 6, 1, "Genérico", None, "P02CA03", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antiparasitário", "Verminoses", "Dose única"),
    ("Mebendazol", "Vermox", "Mebendazol", "100mg", 1, 1, 100.0, "mg", "1 comprimido 2x ao dia por 3 dias", "Tomar com alimentos", 6, 1, "Genérico", None, "P02CA01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antiparasitário", "Verminoses", "Tomar com alimentos"),
    
    # Formas farmacêuticas especiais
    ("Dorflex", "Dorflex", "Cafeína + Carisoprodol + Dipirona", "1 comprimido", 1, 1, 1.0, "comp", "1 comprimido a cada 8 horas", "Tomar com água", 1, 1, "Genérico", None, "M03BA03", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Relaxante muscular + Analgésico", "Dor muscular, tensão", "Pode causar sonolência"),
    ("Neosaldina", "Neosaldina", "Dipirona + Cafeína + Mucato de Isometepteno", "30 gotas", 6, 1, 30.0, "gota", "30 gotas a cada 6 horas", "Tomar com água", 1, 11, "Genérico", None, "N02BB02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Analgésico", "Dor de cabeça, enxaqueca", "Não exceder 120 gotas/dia"),
    ("Buscopan", "Buscopan", "Butilescopolamina brometo", "10mg", 1, 1, 10.0, "mg", "1 comprimido a cada 8 horas", "Tomar com água", 14, 1, "Genérico", None, "A03BB01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antiespasmódico", "Cólica intestinal, biliar", "Alivio rápido de cólicas"),
    ("Torsilax", "Torsilax", "Ciclobenzaprina + Dipirona + Cafeína", "1 comprimido", 1, 1, 1.0, "comp", "1 comprimido a cada 8 horas", "Tomar com água", 1, 1, "Genérico", None, "M03BX01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Relaxante muscular + Analgésico", "Dor muscular, lombalgia", "Pode causar sonolência"),
]

# Expandir lista criando variações de dosagens e formas farmacêuticas
def expandir_medicamentos():
    """Expande a lista de medicamentos criando variações"""
    medicamentos_expandidos = list(MEDICAMENTOS)
    
    # Variações de dosagens para medicamentos comuns
    variacoes_dosagem = [
        # Paracetamol - diferentes dosagens
        ("Paracetamol", "Tylenol 750mg", "Paracetamol", "750mg", 1, 1, 750.0, "mg", "1 comprimido a cada 6-8 horas", "Tomar com água", 1, 1, "Genérico", None, "A02AA01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Analgésico e antipirético", "Dor e febre", "Não exceder 4g/dia"),
        ("Paracetamol", "Tylenol Gotas", "Paracetamol", "200mg/ml", 6, 1, 200.0, "mg", "Conforme peso", "Tomar com água", 1, 4, "Genérico", None, "A02AA01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Analgésico e antipirético", "Dor e febre em crianças", "Não exceder 4g/dia"),
        ("Paracetamol", "Tylenol Xarope", "Paracetamol", "32mg/ml", 5, 1, 32.0, "mg", "Conforme peso", "Tomar com água", 1, 4, "Genérico", None, "A02AA01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Analgésico e antipirético", "Dor e febre em crianças", "Não exceder 4g/dia"),
        
        # Amoxicilina - diferentes dosagens
        ("Amoxicilina", "Amoxil 250mg", "Amoxicilina", "250mg", 1, 1, 250.0, "mg", "1 comprimido a cada 8 horas", "Tomar com água", 3, 1, "Genérico", None, "J01CA04", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antibiótico beta-lactâmico", "Infecções bacterianas", "Completar tratamento"),
        ("Amoxicilina", "Amoxil Suspensão", "Amoxicilina", "250mg/5ml", 4, 1, 250.0, "mg", "Conforme peso", "Agitar antes de usar", 3, 4, "Genérico", None, "J01CA04", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antibiótico beta-lactâmico", "Infecções bacterianas em crianças", "Completar tratamento"),
        ("Amoxicilina + Ácido Clavulânico", "Augmentin", "Amoxicilina + Ácido clavulânico", "500mg+125mg", 1, 1, 500.0, "mg", "1 comprimido a cada 8 horas", "Tomar com alimentos", 3, 1, "Genérico", None, "J01CR02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antibiótico beta-lactâmico", "Infecções bacterianas resistentes", "Completar tratamento"),
        
        # Ibuprofeno - diferentes dosagens
        ("Ibuprofeno", "Advil 200mg", "Ibuprofeno", "200mg", 1, 1, 200.0, "mg", "1 comprimido a cada 8 horas", "Tomar com alimentos", 2, 1, "Genérico", None, "M01AE01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Anti-inflamatório não esteroidal", "Dor, inflamação e febre", "Evitar uso prolongado"),
        ("Ibuprofeno", "Advil Suspensão", "Ibuprofeno", "100mg/5ml", 4, 1, 100.0, "mg", "Conforme peso", "Agitar antes de usar", 2, 4, "Genérico", None, "M01AE01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Anti-inflamatório não esteroidal", "Dor e febre em crianças", "Evitar uso prolongado"),
        
        # Omeprazol - diferentes dosagens
        ("Omeprazol", "Losec 40mg", "Omeprazol", "40mg", 1, 1, 40.0, "mg", "1 cápsula pela manhã em jejum", "Tomar 30min antes do café", 14, 2, "Genérico", None, "A02BC01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Inibidor de bomba de prótons", "Gastrite, úlcera", "Tomar em jejum"),
        
        # Losartana - diferentes dosagens
        ("Losartana", "Cozaar 25mg", "Losartana potássica", "25mg", 1, 1, 25.0, "mg", "1 comprimido ao dia", "Tomar com água", 21, 1, "Genérico", None, "C09CA01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Anti-hipertensivo", "Hipertensão arterial", "Tomar sempre no mesmo horário"),
        ("Losartana", "Cozaar 100mg", "Losartana potássica", "100mg", 1, 1, 100.0, "mg", "1 comprimido ao dia", "Tomar com água", 21, 1, "Genérico", None, "C09CA01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Anti-hipertensivo", "Hipertensão arterial", "Tomar sempre no mesmo horário"),
        
        # Metformina - diferentes dosagens
        ("Metformina", "Glifage 850mg", "Metformina cloridrato", "850mg", 1, 1, 850.0, "mg", "1 comprimido 2x ao dia", "Tomar com alimentos", 20, 1, "Genérico", None, "A10BA02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antidiabético", "Diabetes tipo 2", "Pode causar desconforto gastrointestinal"),
        ("Metformina", "Glifage 1000mg", "Metformina cloridrato", "1000mg", 1, 1, 1000.0, "mg", "1 comprimido 2x ao dia", "Tomar com alimentos", 20, 1, "Genérico", None, "A10BA02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antidiabético", "Diabetes tipo 2", "Pode causar desconforto gastrointestinal"),
        
        # Mais medicamentos comuns
        ("Dorflex", "Dorflex Gotas", "Cafeína + Carisoprodol + Dipirona", "20 gotas", 6, 1, 20.0, "gota", "20 gotas a cada 8 horas", "Tomar com água", 1, 11, "Genérico", None, "M03BA03", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Relaxante muscular + Analgésico", "Dor muscular, tensão", "Pode causar sonolência"),
        ("Buscopan", "Buscopan Composto", "Butilescopolamina + Dipirona", "1 comprimido", 1, 1, 1.0, "comp", "1 comprimido a cada 8 horas", "Tomar com água", 1, 1, "Genérico", None, "A03BB01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antiespasmódico + Analgésico", "Cólica com dor", "Alivio rápido de cólicas"),
    ]
    
    medicamentos_expandidos.extend(variacoes_dosagem)
    
    # Adicionar mais medicamentos reais do Brasil
    medicamentos_adicionais = [
        # Mais antibióticos
        ("Ceftriaxona", "Rocephin", "Ceftriaxona sódica", "1g", 8, 7, 1000.0, "mg", "1 ampola IM/IV a cada 24h", "Aplicar via intramuscular ou intravenosa", 3, 8, "Genérico", None, "J01DD04", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Antibiótico cefalosporina", "Infecções bacterianas graves", "Aplicação parenteral"),
        ("Claritromicina", "Klacid", "Claritromicina", "500mg", 1, 1, 500.0, "mg", "1 comprimido 2x ao dia", "Tomar com alimentos", 3, 1, "Genérico", None, "J01FA09", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antibiótico macrolídeo", "Infecções respiratórias", "Tomar com alimentos"),
        ("Doxiciclina", "Vibramicina", "Doxiciclina hiclato", "100mg", 1, 1, 100.0, "mg", "1 comprimido 2x ao dia", "Tomar com água", 3, 1, "Genérico", None, "J01AA02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antibiótico tetraciclina", "Infecções bacterianas", "Evitar exposição solar"),
        
        # Mais anti-inflamatórios
        ("Nimesulida", "Nisulid", "Nimesulida", "100mg", 1, 1, 100.0, "mg", "1 comprimido 2x ao dia", "Tomar com alimentos", 2, 1, "Genérico", None, "M01AX17", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Anti-inflamatório não esteroidal", "Dor e inflamação", "Evitar uso prolongado"),
        ("Tenoxicam", "Tilatil", "Tenoxicam", "20mg", 1, 1, 20.0, "mg", "1 comprimido ao dia", "Tomar com alimentos", 2, 1, "Genérico", None, "M01AC02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Anti-inflamatório não esteroidal", "Dor e inflamação", "Evitar uso prolongado"),
        
        # Mais anti-hipertensivos
        ("Captopril", "Capoten", "Captopril", "25mg", 1, 1, 25.0, "mg", "1 comprimido 2-3x ao dia", "Tomar com água", 21, 1, "Genérico", None, "C09AA01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Inibidor de ECA", "Hipertensão arterial", "Pode causar tosse seca"),
        ("Atenolol", "Tenormin", "Atenolol", "50mg", 1, 1, 50.0, "mg", "1 comprimido ao dia", "Tomar com água", 21, 1, "Genérico", None, "C07AB03", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Betabloqueador", "Hipertensão, arritmias", "Não suspender abruptamente"),
        ("Carvedilol", "Dilatrend", "Carvedilol", "25mg", 1, 1, 25.0, "mg", "1 comprimido 2x ao dia", "Tomar com água", 21, 1, "Genérico", None, "C07AG02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Betabloqueador", "Hipertensão, insuficiência cardíaca", "Não suspender abruptamente"),
        
        # Mais antidiabéticos
        ("Gliclazida", "Diamicron", "Gliclazida", "80mg", 1, 1, 80.0, "mg", "1 comprimido antes do café", "Tomar antes das refeições", 20, 1, "Genérico", None, "A10BB09", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antidiabético", "Diabetes tipo 2", "Risco de hipoglicemia"),
        ("Pioglitazona", "Actos", "Pioglitazona cloridrato", "30mg", 1, 1, 30.0, "mg", "1 comprimido ao dia", "Tomar com água", 20, 1, "Genérico", None, "A10BG03", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antidiabético", "Diabetes tipo 2", "Pode causar ganho de peso"),
        
        # Mais antidepressivos
        ("Citalopram", "Cipramil", "Citalopram bromidrato", "20mg", 1, 1, 20.0, "mg", "1 comprimido ao dia", "Tomar com água", 10, 1, "Genérico", None, "N06AB04", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Antidepressivo ISRS", "Depressão, ansiedade", "Efeito após 2-4 semanas"),
        ("Venlafaxina", "Efexor", "Venlafaxina cloridrato", "75mg", 1, 1, 75.0, "mg", "1 cápsula ao dia", "Tomar com água", 10, 2, "Genérico", None, "N06AX16", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Antidepressivo", "Depressão, ansiedade", "Efeito após 2-4 semanas"),
        
        # Mais ansiolíticos
        ("Bromazepam", "Lexotan", "Bromazepam", "6mg", 1, 1, 6.0, "mg", "Conforme prescrição médica", "Tomar com água", 11, 1, "Genérico", None, "N05BA08", None, None, None, None, 4, True, False, False, False, False, True, True, True, True, "Ansiolítico benzodiazepínico", "Ansiedade", "Pode causar dependência"),
        ("Lorazepam", "Lorax", "Lorazepam", "2mg", 1, 1, 2.0, "mg", "Conforme prescrição médica", "Tomar com água", 11, 1, "Genérico", None, "N05BA06", None, None, None, None, 4, True, False, False, False, False, True, True, True, True, "Ansiolítico benzodiazepínico", "Ansiedade, insônia", "Pode causar dependência"),
        
        # Mais corticoides
        ("Hidrocortisona", "Cortisona", "Hidrocortisona", "20mg", 1, 1, 20.0, "mg", "Conforme prescrição médica", "Tomar com alimentos", 9, 1, "Genérico", None, "H02AB09", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Corticoide sistêmico", "Inflamações, alergias", "Não suspender abruptamente"),
        ("Betametasona", "Celestone", "Betametasona", "0,5mg", 1, 1, 0.5, "mg", "Conforme prescrição médica", "Tomar com alimentos", 9, 1, "Genérico", None, "H02AB01", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Corticoide sistêmico", "Inflamações, alergias", "Não suspender abruptamente"),
        
        # Mais vitaminas e minerais
        ("Vitamina C", "Redoxon", "Ácido ascórbico", "1g", 1, 1, 1000.0, "mg", "1 comprimido ao dia", "Tomar com água", 17, 1, "Genérico", None, "A11GA01", None, None, None, None, 1, False, False, False, False, False, True, True, True, True, "Vitamina C", "Deficiência de vitamina C", "Tomar com água"),
        ("Vitamina E", "Ephynal", "Alfa-tocoferol", "400UI", 1, 1, 400.0, "UI", "1 cápsula ao dia", "Tomar com alimentos", 17, 2, "Genérico", None, "A11HA03", None, None, None, None, 1, False, False, False, False, False, True, True, True, True, "Vitamina E", "Antioxidante", "Tomar com alimentos gordurosos"),
        ("Ferro", "Ferronil", "Sulfato ferroso", "40mg", 1, 1, 40.0, "mg", "1 comprimido ao dia", "Tomar em jejum", 18, 1, "Genérico", None, "B03AA07", None, None, None, None, 1, False, False, False, False, False, True, True, True, True, "Suplemento de ferro", "Anemia ferropriva", "Tomar em jejum"),
        ("Cálcio", "Calcitran", "Carbonato de cálcio", "500mg", 1, 1, 500.0, "mg", "1 comprimido 2x ao dia", "Tomar com alimentos", 18, 1, "Genérico", None, "A12AA04", None, None, None, None, 1, False, False, False, False, False, True, True, True, True, "Suplemento de cálcio", "Osteoporose", "Tomar com alimentos"),
        
        # Mais medicamentos tópicos
        ("Diclofenaco Gel", "Voltaren Emulgel", "Diclofenaco dietilamônio", "1%", 11, 5, 1.0, "%", "Aplicar 3-4x ao dia", "Aplicar na área afetada", 2, 11, "Genérico", None, "M02AA15", None, None, None, None, 1, False, False, False, False, False, True, True, True, True, "Anti-inflamatório tópico", "Dor e inflamação local", "Aplicar na área afetada"),
        ("Hidrocortisona Creme", "Pielotopic", "Hidrocortisona", "1%", 9, 5, 1.0, "%", "Aplicar 2x ao dia", "Aplicar na área afetada", 9, 9, "Genérico", None, "D07AA02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Corticoide tópico", "Dermatites, alergias", "Aplicar na área afetada"),
        ("Miconazol Creme", "Daktarin", "Miconazol nitrato", "2%", 9, 5, 2.0, "%", "Aplicar 2x ao dia", "Aplicar na área afetada", 5, 9, "Genérico", None, "D01AC02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antifúngico tópico", "Micoses", "Aplicar na área afetada"),
        
        # Mais colírios
        ("Dexametasona Colírio", "Maxidex", "Dexametasona", "0,1%", 17, 17, 0.1, "%", "1-2 gotas 4x ao dia", "Aplicar no olho", 9, 11, "Genérico", None, "S01BA01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Corticoide oftálmico", "Inflamação ocular", "Aplicar no olho"),
        ("Tobramicina Colírio", "Tobrex", "Tobramicina", "0,3%", 17, 17, 0.3, "%", "1-2 gotas 4x ao dia", "Aplicar no olho", 3, 11, "Genérico", None, "S01AA12", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antibiótico oftálmico", "Infecção ocular", "Aplicar no olho"),
        
        # Mais medicamentos injetáveis
        ("Dexametasona Injetável", "Decadron Injetável", "Dexametasona fosfato", "4mg/ml", 8, 7, 4.0, "mg", "Conforme prescrição médica", "Aplicar via intramuscular", 9, 8, "Genérico", None, "H02AB02", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Corticoide injetável", "Inflamações, alergias", "Aplicação parenteral"),
        ("Diclofenaco Injetável", "Voltaren Injetável", "Diclofenaco sódico", "75mg/3ml", 8, 7, 75.0, "mg", "1 ampola IM a cada 12h", "Aplicar via intramuscular", 2, 8, "Genérico", None, "M01AB05", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Anti-inflamatório injetável", "Dor e inflamação", "Aplicação parenteral"),
        
        # Mais 100+ medicamentos reais do Brasil
        ("Sertralina", "Zoloft 100mg", "Sertralina cloridrato", "100mg", 1, 1, 100.0, "mg", "1 comprimido ao dia", "Tomar com água", 10, 1, "Genérico", None, "N06AB06", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Antidepressivo ISRS", "Depressão, ansiedade", "Efeito após 2-4 semanas"),
        ("Fluoxetina", "Prozac 40mg", "Fluoxetina cloridrato", "40mg", 1, 1, 40.0, "mg", "1 cápsula ao dia", "Tomar com água", 10, 2, "Genérico", None, "N06AB03", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Antidepressivo ISRS", "Depressão, ansiedade", "Efeito após 2-4 semanas"),
        ("Amitriptilina", "Tryptanol 50mg", "Amitriptilina cloridrato", "50mg", 1, 1, 50.0, "mg", "1 comprimido ao deitar", "Tomar com água", 10, 1, "Genérico", None, "N06AA09", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Antidepressivo tricíclico", "Depressão, dor crônica", "Pode causar sonolência"),
        ("Diazepam", "Valium 5mg", "Diazepam", "5mg", 1, 1, 5.0, "mg", "Conforme prescrição médica", "Tomar com água", 11, 1, "Genérico", None, "N05BA01", None, None, None, None, 4, True, False, False, False, False, True, True, True, True, "Ansiolítico benzodiazepínico", "Ansiedade, insônia", "Pode causar dependência"),
        ("Alprazolam", "Frontal 1mg", "Alprazolam", "1mg", 1, 1, 1.0, "mg", "Conforme prescrição médica", "Tomar com água", 11, 1, "Genérico", None, "N05BA12", None, None, None, None, 4, True, False, False, False, False, True, True, True, True, "Ansiolítico benzodiazepínico", "Ansiedade, pânico", "Pode causar dependência"),
        ("Clonazepam", "Rivotril 0,5mg", "Clonazepam", "0,5mg", 1, 1, 0.5, "mg", "Conforme prescrição médica", "Tomar com água", 11, 1, "Genérico", None, "N03AE01", None, None, None, None, 4, True, False, False, False, False, True, True, True, True, "Ansiolítico e anticonvulsivante", "Ansiedade, convulsões", "Pode causar dependência"),
        ("Omeprazol", "Losec 10mg", "Omeprazol", "10mg", 1, 1, 10.0, "mg", "1 cápsula pela manhã em jejum", "Tomar 30min antes do café", 14, 2, "Genérico", None, "A02BC01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Inibidor de bomba de prótons", "Gastrite, úlcera", "Tomar em jejum"),
        ("Pantoprazol", "Pantozol 20mg", "Pantoprazol sódico", "20mg", 1, 1, 20.0, "mg", "1 comprimido pela manhã", "Tomar com água", 14, 1, "Genérico", None, "A02BC02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Inibidor de bomba de prótons", "Gastrite, úlcera", "Tomar em jejum"),
        ("Esomeprazol", "Nexium", "Esomeprazol magnésico", "40mg", 1, 1, 40.0, "mg", "1 cápsula pela manhã em jejum", "Tomar 30min antes do café", 14, 2, "Genérico", None, "A02BC05", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Inibidor de bomba de prótons", "Gastrite, úlcera", "Tomar em jejum"),
        ("Lansoprazol", "Lanzul", "Lansoprazol", "30mg", 1, 1, 30.0, "mg", "1 cápsula pela manhã em jejum", "Tomar 30min antes do café", 14, 2, "Genérico", None, "A02BC03", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Inibidor de bomba de prótons", "Gastrite, úlcera", "Tomar em jejum"),
        ("Ranitidina", "Antak 300mg", "Ranitidina cloridrato", "300mg", 1, 1, 300.0, "mg", "1 comprimido ao deitar", "Tomar com água", 14, 1, "Genérico", None, "A02BA02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Bloqueador H2", "Gastrite, úlcera", "Tomar antes das refeições"),
        ("Famotidina", "Pepcid", "Famotidina", "40mg", 1, 1, 40.0, "mg", "1 comprimido ao deitar", "Tomar com água", 14, 1, "Genérico", None, "A02BA03", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Bloqueador H2", "Gastrite, úlcera", "Tomar antes das refeições"),
        ("Losartana", "Cozaar 25mg", "Losartana potássica", "25mg", 1, 1, 25.0, "mg", "1 comprimido ao dia", "Tomar com água", 21, 1, "Genérico", None, "C09CA01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Anti-hipertensivo", "Hipertensão arterial", "Tomar sempre no mesmo horário"),
        ("Losartana", "Cozaar 100mg", "Losartana potássica", "100mg", 1, 1, 100.0, "mg", "1 comprimido ao dia", "Tomar com água", 21, 1, "Genérico", None, "C09CA01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Anti-hipertensivo", "Hipertensão arterial", "Tomar sempre no mesmo horário"),
        ("Valsartana", "Diovan", "Valsartana", "160mg", 1, 1, 160.0, "mg", "1 comprimido ao dia", "Tomar com água", 21, 1, "Genérico", None, "C09CA03", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Anti-hipertensivo", "Hipertensão arterial", "Tomar sempre no mesmo horário"),
        ("Irbesartana", "Aprovel", "Irbesartana", "150mg", 1, 1, 150.0, "mg", "1 comprimido ao dia", "Tomar com água", 21, 1, "Genérico", None, "C09CA04", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Anti-hipertensivo", "Hipertensão arterial", "Tomar sempre no mesmo horário"),
        ("Telmisartana", "Micardis", "Telmisartana", "80mg", 1, 1, 80.0, "mg", "1 comprimido ao dia", "Tomar com água", 21, 1, "Genérico", None, "C09CA07", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Anti-hipertensivo", "Hipertensão arterial", "Tomar sempre no mesmo horário"),
        ("Amlodipina", "Norvasc 10mg", "Amlodipina besilato", "10mg", 1, 1, 10.0, "mg", "1 comprimido ao dia", "Tomar com água", 21, 1, "Genérico", None, "C08CA01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Bloqueador de canais de cálcio", "Hipertensão arterial", "Pode causar edema"),
        ("Nifedipina", "Adalat", "Nifedipina", "20mg", 1, 1, 20.0, "mg", "1 comprimido 2x ao dia", "Tomar com água", 21, 1, "Genérico", None, "C08CA05", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Bloqueador de canais de cálcio", "Hipertensão arterial", "Pode causar cefaleia"),
        ("Verapamil", "Isoptin", "Verapamil cloridrato", "80mg", 1, 1, 80.0, "mg", "1 comprimido 3x ao dia", "Tomar com água", 21, 1, "Genérico", None, "C08DA01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Bloqueador de canais de cálcio", "Hipertensão, arritmias", "Pode causar constipação"),
        ("Enalapril", "Renitec 5mg", "Enalapril maleato", "5mg", 1, 1, 5.0, "mg", "1 comprimido ao dia", "Tomar com água", 21, 1, "Genérico", None, "C09AA02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Inibidor de ECA", "Hipertensão arterial", "Pode causar tosse seca"),
        ("Enalapril", "Renitec 20mg", "Enalapril maleato", "20mg", 1, 1, 20.0, "mg", "1 comprimido ao dia", "Tomar com água", 21, 1, "Genérico", None, "C09AA02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Inibidor de ECA", "Hipertensão arterial", "Pode causar tosse seca"),
        ("Lisinopril", "Zestril", "Lisinopril", "10mg", 1, 1, 10.0, "mg", "1 comprimido ao dia", "Tomar com água", 21, 1, "Genérico", None, "C09AA03", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Inibidor de ECA", "Hipertensão arterial", "Pode causar tosse seca"),
        ("Ramipril", "Tritace", "Ramipril", "5mg", 1, 1, 5.0, "mg", "1 cápsula ao dia", "Tomar com água", 21, 2, "Genérico", None, "C09AA05", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Inibidor de ECA", "Hipertensão arterial", "Pode causar tosse seca"),
        ("Propranolol", "Inderal 20mg", "Propranolol cloridrato", "20mg", 1, 1, 20.0, "mg", "1 comprimido 2x ao dia", "Tomar com água", 21, 1, "Genérico", None, "C07AA05", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Betabloqueador", "Hipertensão, arritmias", "Não suspender abruptamente"),
        ("Propranolol", "Inderal 80mg", "Propranolol cloridrato", "80mg", 1, 1, 80.0, "mg", "1 comprimido 2x ao dia", "Tomar com água", 21, 1, "Genérico", None, "C07AA05", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Betabloqueador", "Hipertensão, arritmias", "Não suspender abruptamente"),
        ("Metoprolol", "Seloken", "Metoprolol tartarato", "50mg", 1, 1, 50.0, "mg", "1 comprimido 2x ao dia", "Tomar com água", 21, 1, "Genérico", None, "C07AB02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Betabloqueador", "Hipertensão, arritmias", "Não suspender abruptamente"),
        ("Bisoprolol", "Concor", "Bisoprolol fumarato", "5mg", 1, 1, 5.0, "mg", "1 comprimido ao dia", "Tomar com água", 21, 1, "Genérico", None, "C07AB07", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Betabloqueador", "Hipertensão, arritmias", "Não suspender abruptamente"),
        ("Nebivolol", "Nebilet", "Nebivolol cloridrato", "5mg", 1, 1, 5.0, "mg", "1 comprimido ao dia", "Tomar com água", 21, 1, "Genérico", None, "C07AB12", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Betabloqueador", "Hipertensão, arritmias", "Não suspender abruptamente"),
        ("Hidroclorotiazida", "Clorana 12,5mg", "Hidroclorotiazida", "12,5mg", 1, 1, 12.5, "mg", "1 comprimido pela manhã", "Tomar com água", 22, 1, "Genérico", None, "C03AA03", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Diurético tiazídico", "Hipertensão, edema", "Pode causar hipocalemia"),
        ("Furosemida", "Lasix 20mg", "Furosemida", "20mg", 1, 1, 20.0, "mg", "1 comprimido pela manhã", "Tomar com água", 22, 1, "Genérico", None, "C03CA01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Diurético de alça", "Edema, insuficiência cardíaca", "Pode causar hipocalemia"),
        ("Furosemida", "Lasix 80mg", "Furosemida", "80mg", 1, 1, 80.0, "mg", "1 comprimido pela manhã", "Tomar com água", 22, 1, "Genérico", None, "C03CA01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Diurético de alça", "Edema, insuficiência cardíaca", "Pode causar hipocalemia"),
        ("Espironolactona", "Aldactone", "Espironolactona", "25mg", 1, 1, 25.0, "mg", "1 comprimido ao dia", "Tomar com água", 22, 1, "Genérico", None, "C03DA01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Diurético poupador de potássio", "Edema, hipertensão", "Pode causar hipercalemia"),
        ("Metformina", "Glifage 850mg", "Metformina cloridrato", "850mg", 1, 1, 850.0, "mg", "1 comprimido 2x ao dia", "Tomar com alimentos", 20, 1, "Genérico", None, "A10BA02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antidiabético", "Diabetes tipo 2", "Pode causar desconforto gastrointestinal"),
        ("Metformina", "Glifage 1000mg", "Metformina cloridrato", "1000mg", 1, 1, 1000.0, "mg", "1 comprimido 2x ao dia", "Tomar com alimentos", 20, 1, "Genérico", None, "A10BA02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antidiabético", "Diabetes tipo 2", "Pode causar desconforto gastrointestinal"),
        ("Glibenclamida", "Daonil 2,5mg", "Glibenclamida", "2,5mg", 1, 1, 2.5, "mg", "1 comprimido antes do café", "Tomar antes das refeições", 20, 1, "Genérico", None, "A10BB01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antidiabético", "Diabetes tipo 2", "Risco de hipoglicemia"),
        ("Gliclazida", "Diamicron 60mg", "Gliclazida", "60mg", 1, 1, 60.0, "mg", "1 comprimido antes do café", "Tomar antes das refeições", 20, 1, "Genérico", None, "A10BB09", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antidiabético", "Diabetes tipo 2", "Risco de hipoglicemia"),
        ("Glimepirida", "Amaryl", "Glimepirida", "2mg", 1, 1, 2.0, "mg", "1 comprimido antes do café", "Tomar antes das refeições", 20, 1, "Genérico", None, "A10BB12", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antidiabético", "Diabetes tipo 2", "Risco de hipoglicemia"),
        ("Pioglitazona", "Actos 15mg", "Pioglitazona cloridrato", "15mg", 1, 1, 15.0, "mg", "1 comprimido ao dia", "Tomar com água", 20, 1, "Genérico", None, "A10BG03", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antidiabético", "Diabetes tipo 2", "Pode causar ganho de peso"),
        ("Sitagliptina", "Januvia", "Sitagliptina fosfato", "100mg", 1, 1, 100.0, "mg", "1 comprimido ao dia", "Tomar com água", 20, 1, "Genérico", None, "A10BH01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antidiabético", "Diabetes tipo 2", "Tomar com ou sem alimentos"),
        ("Levotiroxina", "Puran T4 25mcg", "Levotiroxina sódica", "25mcg", 1, 1, 25.0, "mcg", "1 comprimido em jejum", "Tomar 30min antes do café", 19, 1, "Genérico", None, "H03AA01", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Hormônio tireoidiano", "Hipotireoidismo", "Tomar sempre em jejum"),
        ("Levotiroxina", "Puran T4 75mcg", "Levotiroxina sódica", "75mcg", 1, 1, 75.0, "mcg", "1 comprimido em jejum", "Tomar 30min antes do café", 19, 1, "Genérico", None, "H03AA01", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Hormônio tireoidiano", "Hipotireoidismo", "Tomar sempre em jejum"),
        ("Levotiroxina", "Puran T4 100mcg", "Levotiroxina sódica", "100mcg", 1, 1, 100.0, "mcg", "1 comprimido em jejum", "Tomar 30min antes do café", 19, 1, "Genérico", None, "H03AA01", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Hormônio tireoidiano", "Hipotireoidismo", "Tomar sempre em jejum"),
        ("Levotiroxina", "Puran T4 125mcg", "Levotiroxina sódica", "125mcg", 1, 1, 125.0, "mcg", "1 comprimido em jejum", "Tomar 30min antes do café", 19, 1, "Genérico", None, "H03AA01", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Hormônio tireoidiano", "Hipotireoidismo", "Tomar sempre em jejum"),
        ("Levotiroxina", "Puran T4 150mcg", "Levotiroxina sódica", "150mcg", 1, 1, 150.0, "mcg", "1 comprimido em jejum", "Tomar 30min antes do café", 19, 1, "Genérico", None, "H03AA01", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Hormônio tireoidiano", "Hipotireoidismo", "Tomar sempre em jejum"),
        ("Prednisona", "Meticorten 5mg", "Prednisona", "5mg", 1, 1, 5.0, "mg", "Conforme prescrição médica", "Tomar com alimentos", 9, 1, "Genérico", None, "H02AB07", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Corticoide sistêmico", "Inflamações, alergias", "Não suspender abruptamente"),
        ("Prednisona", "Meticorten 10mg", "Prednisona", "10mg", 1, 1, 10.0, "mg", "Conforme prescrição médica", "Tomar com alimentos", 9, 1, "Genérico", None, "H02AB07", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Corticoide sistêmico", "Inflamações, alergias", "Não suspender abruptamente"),
        ("Prednisona", "Meticorten 40mg", "Prednisona", "40mg", 1, 1, 40.0, "mg", "Conforme prescrição médica", "Tomar com alimentos", 9, 1, "Genérico", None, "H02AB07", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Corticoide sistêmico", "Inflamações, alergias", "Não suspender abruptamente"),
        ("Dexametasona", "Decadron 2mg", "Dexametasona", "2mg", 1, 1, 2.0, "mg", "Conforme prescrição médica", "Tomar com alimentos", 9, 1, "Genérico", None, "H02AB02", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Corticoide sistêmico", "Inflamações, alergias", "Não suspender abruptamente"),
        ("Dexametasona", "Decadron 8mg", "Dexametasona", "8mg", 1, 1, 8.0, "mg", "Conforme prescrição médica", "Tomar com alimentos", 9, 1, "Genérico", None, "H02AB02", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Corticoide sistêmico", "Inflamações, alergias", "Não suspender abruptamente"),
        ("Hidrocortisona", "Cortisona 10mg", "Hidrocortisona", "10mg", 1, 1, 10.0, "mg", "Conforme prescrição médica", "Tomar com alimentos", 9, 1, "Genérico", None, "H02AB09", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Corticoide sistêmico", "Inflamações, alergias", "Não suspender abruptamente"),
        ("Betametasona", "Celestone 1mg", "Betametasona", "1mg", 1, 1, 1.0, "mg", "Conforme prescrição médica", "Tomar com alimentos", 9, 1, "Genérico", None, "H02AB01", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Corticoide sistêmico", "Inflamações, alergias", "Não suspender abruptamente"),
        ("Paracetamol", "Tylenol 750mg", "Paracetamol", "750mg", 1, 1, 750.0, "mg", "1 comprimido a cada 6-8 horas", "Tomar com água", 1, 1, "Genérico", None, "A02AA01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Analgésico e antipirético", "Dor e febre", "Não exceder 4g/dia"),
        ("Paracetamol", "Tylenol 1g", "Paracetamol", "1g", 1, 1, 1000.0, "mg", "1 comprimido a cada 6-8 horas", "Tomar com água", 1, 1, "Genérico", None, "A02AA01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Analgésico e antipirético", "Dor e febre", "Não exceder 4g/dia"),
        ("Dipirona", "Novalgina 1g", "Dipirona sódica", "1g", 1, 1, 1000.0, "mg", "1 comprimido a cada 6-8 horas", "Tomar com água", 1, 1, "Genérico", None, "N02BB02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Analgésico e antipirético", "Dor e febre", "Pode causar agranulocitose"),
        ("Ibuprofeno", "Advil 200mg", "Ibuprofeno", "200mg", 1, 1, 200.0, "mg", "1 comprimido a cada 8 horas", "Tomar com alimentos", 2, 1, "Genérico", None, "M01AE01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Anti-inflamatório não esteroidal", "Dor, inflamação e febre", "Evitar uso prolongado"),
        ("Ibuprofeno", "Advil 600mg", "Ibuprofeno", "600mg", 1, 1, 600.0, "mg", "1 comprimido a cada 8 horas", "Tomar com alimentos", 2, 1, "Genérico", None, "M01AE01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Anti-inflamatório não esteroidal", "Dor, inflamação e febre", "Evitar uso prolongado"),
        ("Diclofenaco Sódico", "Voltaren 25mg", "Diclofenaco sódico", "25mg", 1, 1, 25.0, "mg", "1 comprimido a cada 8 horas", "Tomar com alimentos", 2, 1, "Genérico", None, "M01AB05", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Anti-inflamatório não esteroidal", "Dor e inflamação", "Evitar uso prolongado"),
        ("Diclofenaco Sódico", "Voltaren 100mg", "Diclofenaco sódico", "100mg", 1, 1, 100.0, "mg", "1 comprimido a cada 12 horas", "Tomar com alimentos", 2, 1, "Genérico", None, "M01AB05", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Anti-inflamatório não esteroidal", "Dor e inflamação", "Evitar uso prolongado"),
        ("Naproxeno", "Flanax 250mg", "Naproxeno sódico", "250mg", 1, 1, 250.0, "mg", "1 comprimido a cada 12 horas", "Tomar com alimentos", 2, 1, "Genérico", None, "M01AE02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Anti-inflamatório não esteroidal", "Dor e inflamação", "Evitar uso prolongado"),
        ("Cetoprofeno", "Profenid 50mg", "Cetoprofeno", "50mg", 1, 1, 50.0, "mg", "1 comprimido a cada 12 horas", "Tomar com alimentos", 2, 1, "Genérico", None, "M01AE03", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Anti-inflamatório não esteroidal", "Dor e inflamação", "Evitar uso prolongado"),
        ("Nimesulida", "Nisulid 100mg", "Nimesulida", "100mg", 1, 1, 100.0, "mg", "1 comprimido 2x ao dia", "Tomar com alimentos", 2, 1, "Genérico", None, "M01AX17", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Anti-inflamatório não esteroidal", "Dor e inflamação", "Evitar uso prolongado"),
        ("Tenoxicam", "Tilatil 20mg", "Tenoxicam", "20mg", 1, 1, 20.0, "mg", "1 comprimido ao dia", "Tomar com alimentos", 2, 1, "Genérico", None, "M01AC02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Anti-inflamatório não esteroidal", "Dor e inflamação", "Evitar uso prolongado"),
        ("Amoxicilina", "Amoxil 250mg", "Amoxicilina", "250mg", 1, 1, 250.0, "mg", "1 comprimido a cada 8 horas", "Tomar com água", 3, 1, "Genérico", None, "J01CA04", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antibiótico beta-lactâmico", "Infecções bacterianas", "Completar tratamento"),
        ("Amoxicilina", "Amoxil 875mg", "Amoxicilina", "875mg", 1, 1, 875.0, "mg", "1 comprimido a cada 12 horas", "Tomar com água", 3, 1, "Genérico", None, "J01CA04", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antibiótico beta-lactâmico", "Infecções bacterianas", "Completar tratamento"),
        ("Amoxicilina + Ácido Clavulânico", "Augmentin 250mg+125mg", "Amoxicilina + Ácido clavulânico", "250mg+125mg", 1, 1, 250.0, "mg", "1 comprimido a cada 8 horas", "Tomar com alimentos", 3, 1, "Genérico", None, "J01CR02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antibiótico beta-lactâmico", "Infecções bacterianas resistentes", "Completar tratamento"),
        ("Amoxicilina + Ácido Clavulânico", "Augmentin 875mg+125mg", "Amoxicilina + Ácido clavulânico", "875mg+125mg", 1, 1, 875.0, "mg", "1 comprimido a cada 12 horas", "Tomar com alimentos", 3, 1, "Genérico", None, "J01CR02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antibiótico beta-lactâmico", "Infecções bacterianas resistentes", "Completar tratamento"),
        ("Azitromicina", "Zitromax 250mg", "Azitromicina di-hidratada", "250mg", 1, 1, 250.0, "mg", "1 comprimido ao dia por 3 dias", "Tomar em jejum", 3, 1, "Genérico", None, "J01FA10", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antibiótico macrolídeo", "Infecções respiratórias", "Tomar 1h antes ou 2h após refeições"),
        ("Cefalexina", "Keflex 250mg", "Cefalexina monoidratada", "250mg", 1, 1, 250.0, "mg", "1 cápsula a cada 6 horas", "Tomar com água", 3, 2, "Genérico", None, "J01DB01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antibiótico cefalosporina", "Infecções bacterianas", "Completar tratamento"),
        ("Cefalexina", "Keflex 1g", "Cefalexina monoidratada", "1g", 1, 1, 1000.0, "mg", "1 cápsula a cada 12 horas", "Tomar com água", 3, 2, "Genérico", None, "J01DB01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antibiótico cefalosporina", "Infecções bacterianas", "Completar tratamento"),
        ("Ciprofloxacino", "Cipro 250mg", "Ciprofloxacino cloridrato", "250mg", 1, 1, 250.0, "mg", "1 comprimido 2x ao dia", "Tomar com água", 3, 1, "Genérico", None, "J01MA02", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Antibiótico fluoroquinolona", "Infecções bacterianas", "Evitar exposição solar"),
        ("Claritromicina", "Klacid 250mg", "Claritromicina", "250mg", 1, 1, 250.0, "mg", "1 comprimido 2x ao dia", "Tomar com alimentos", 3, 1, "Genérico", None, "J01FA09", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antibiótico macrolídeo", "Infecções respiratórias", "Tomar com alimentos"),
        ("Doxiciclina", "Vibramicina 100mg", "Doxiciclina hiclato", "100mg", 1, 1, 100.0, "mg", "1 comprimido 2x ao dia", "Tomar com água", 3, 1, "Genérico", None, "J01AA02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antibiótico tetraciclina", "Infecções bacterianas", "Evitar exposição solar"),
        ("Ceftriaxona", "Rocephin 500mg", "Ceftriaxona sódica", "500mg", 8, 7, 500.0, "mg", "1 ampola IM/IV a cada 24h", "Aplicar via intramuscular ou intravenosa", 3, 8, "Genérico", None, "J01DD04", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Antibiótico cefalosporina", "Infecções bacterianas graves", "Aplicação parenteral"),
        ("Ceftriaxona", "Rocephin 2g", "Ceftriaxona sódica", "2g", 8, 7, 2000.0, "mg", "1 ampola IM/IV a cada 24h", "Aplicar via intramuscular ou intravenosa", 3, 8, "Genérico", None, "J01DD04", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Antibiótico cefalosporina", "Infecções bacterianas graves", "Aplicação parenteral"),
        ("Loratadina", "Claritin 10mg", "Loratadina", "10mg", 1, 1, 10.0, "mg", "1 comprimido ao dia", "Tomar com água", 7, 1, "Genérico", None, "R06AX13", None, None, None, None, 1, False, False, False, False, False, True, True, True, True, "Antihistamínico", "Rinite alérgica, urticária", "Não causa sonolência"),
        ("Desloratadina", "Desalex 5mg", "Desloratadina", "5mg", 1, 1, 5.0, "mg", "1 comprimido ao dia", "Tomar com água", 7, 1, "Genérico", None, "R06AX27", None, None, None, None, 1, False, False, False, False, False, True, True, True, True, "Antihistamínico", "Rinite alérgica, urticária", "Não causa sonolência"),
        ("Cetirizina", "Zyrtec 10mg", "Cetirizina dicloridrato", "10mg", 1, 1, 10.0, "mg", "1 comprimido ao dia", "Tomar com água", 7, 1, "Genérico", None, "R06AE07", None, None, None, None, 1, False, False, False, False, False, True, True, True, True, "Antihistamínico", "Rinite alérgica, urticária", "Pode causar sonolência"),
        ("Fexofenadina", "Allegra", "Fexofenadina cloridrato", "120mg", 1, 1, 120.0, "mg", "1 comprimido ao dia", "Tomar com água", 7, 1, "Genérico", None, "R06AX26", None, None, None, None, 1, False, False, False, False, False, True, True, True, True, "Antihistamínico", "Rinite alérgica, urticária", "Não causa sonolência"),
        ("Salbutamol", "Aerolin 100mcg", "Salbutamol sulfato", "100mcg/dose", 13, 19, 100.0, "mcg", "2 jatos a cada 4-6 horas", "Agitar antes de usar", 8, 13, "Genérico", None, "R03AC02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Broncodilatador", "Asma, bronquite", "Uso em crises"),
        ("Brometo de Ipratrópio", "Atrovent 20mcg", "Ipratrópio brometo", "20mcg/dose", 13, 19, 20.0, "mcg", "2 jatos a cada 6 horas", "Agitar antes de usar", 8, 13, "Genérico", None, "R03BB01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Broncodilatador anticolinérgico", "Asma, bronquite", "Uso em crises"),
        ("Formoterol", "Foradil", "Formoterol fumarato", "12mcg/dose", 13, 19, 12.0, "mcg", "2 jatos 2x ao dia", "Agitar antes de usar", 8, 13, "Genérico", None, "R03AC13", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Broncodilatador", "Asma, bronquite", "Uso contínuo"),
        ("Salmeterol", "Serevent", "Salmeterol xinafoato", "50mcg/dose", 13, 19, 50.0, "mcg", "2 jatos 2x ao dia", "Agitar antes de usar", 8, 13, "Genérico", None, "R03AC12", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Broncodilatador", "Asma, bronquite", "Uso contínuo"),
        ("Beclometasona", "Clenil 250mcg", "Beclometasona dipropionato", "250mcg/dose", 13, 19, 250.0, "mcg", "2 jatos 2x ao dia", "Agitar antes de usar", 9, 13, "Genérico", None, "R03BA01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Corticoide inalatório", "Asma", "Enxaguar boca após uso"),
        ("Budesonida", "Pulmicort", "Budesonida", "200mcg/dose", 13, 19, 200.0, "mcg", "2 jatos 2x ao dia", "Agitar antes de usar", 9, 13, "Genérico", None, "R03BA02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Corticoide inalatório", "Asma", "Enxaguar boca após uso"),
        ("Fluticasona", "Flixotide", "Fluticasona propionato", "250mcg/dose", 13, 19, 250.0, "mcg", "2 jatos 2x ao dia", "Agitar antes de usar", 9, 13, "Genérico", None, "R03BA05", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Corticoide inalatório", "Asma", "Enxaguar boca após uso"),
        ("Varfarina", "Marevan 2,5mg", "Varfarina sódica", "2,5mg", 1, 1, 2.5, "mg", "Conforme prescrição médica", "Tomar sempre no mesmo horário", 24, 1, "Genérico", None, "B01AA03", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Anticoagulante", "Prevenção de trombose", "Monitorar INR regularmente"),
        ("Varfarina", "Marevan 10mg", "Varfarina sódica", "10mg", 1, 1, 10.0, "mg", "Conforme prescrição médica", "Tomar sempre no mesmo horário", 24, 1, "Genérico", None, "B01AA03", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Anticoagulante", "Prevenção de trombose", "Monitorar INR regularmente"),
        ("Ácido Acetilsalicílico", "Aspirina 100mg", "Ácido acetilsalicílico", "100mg", 1, 1, 100.0, "mg", "1 comprimido ao dia", "Tomar com água", 24, 1, "Genérico", None, "B01AC06", None, None, None, None, 1, False, False, False, False, False, True, True, True, True, "Antiagregante plaquetário", "Prevenção cardiovascular", "Evitar em crianças"),
        ("Clopidogrel", "Plavix", "Clopidogrel bisulfato", "75mg", 1, 1, 75.0, "mg", "1 comprimido ao dia", "Tomar com água", 24, 1, "Genérico", None, "B01AC04", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antiagregante plaquetário", "Prevenção cardiovascular", "Tomar com água"),
        ("Fenitoína", "Hidantal 100mg", "Fenitoína sódica", "100mg", 1, 1, 100.0, "mg", "Conforme prescrição médica", "Tomar com água", 13, 1, "Genérico", None, "N03AB02", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Anticonvulsivante", "Epilepsia", "Monitorar níveis séricos"),
        ("Carbamazepina", "Tegretol 200mg", "Carbamazepina", "200mg", 1, 1, 200.0, "mg", "Conforme prescrição médica", "Tomar com água", 13, 1, "Genérico", None, "N03AF01", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Anticonvulsivante", "Epilepsia, neuralgia", "Monitorar hemograma"),
        ("Ácido Valpróico", "Depakene", "Ácido valpróico", "250mg", 1, 1, 250.0, "mg", "Conforme prescrição médica", "Tomar com água", 13, 1, "Genérico", None, "N03AG01", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Anticonvulsivante", "Epilepsia", "Monitorar função hepática"),
        ("Topiramato", "Topamax", "Topiramato", "25mg", 1, 1, 25.0, "mg", "Conforme prescrição médica", "Tomar com água", 13, 1, "Genérico", None, "N03AX11", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Anticonvulsivante", "Epilepsia, enxaqueca", "Pode causar perda de peso"),
        ("Gabapentina", "Neurontin", "Gabapentina", "300mg", 1, 1, 300.0, "mg", "Conforme prescrição médica", "Tomar com água", 13, 1, "Genérico", None, "N03AX12", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Anticonvulsivante", "Epilepsia, neuralgia", "Pode causar sonolência"),
        ("Pregabalina", "Lyrica", "Pregabalina", "75mg", 1, 1, 75.0, "mg", "Conforme prescrição médica", "Tomar com água", 13, 1, "Genérico", None, "N03AX16", None, None, None, None, 3, False, False, False, False, False, True, True, True, True, "Anticonvulsivante", "Epilepsia, neuralgia", "Pode causar sonolência"),
        ("Fluconazol", "Zoltec 100mg", "Fluconazol", "100mg", 1, 1, 100.0, "mg", "1 comprimido ao dia", "Tomar com água", 5, 1, "Genérico", None, "J02AC01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antifúngico", "Candidíase", "Tomar com água"),
        ("Fluconazol", "Zoltec 200mg", "Fluconazol", "200mg", 1, 1, 200.0, "mg", "1 comprimido ao dia", "Tomar com água", 5, 1, "Genérico", None, "J02AC01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antifúngico", "Candidíase", "Tomar com água"),
        ("Itraconazol", "Sporanox", "Itraconazol", "100mg", 1, 1, 100.0, "mg", "1 cápsula 2x ao dia", "Tomar com alimentos", 5, 2, "Genérico", None, "J02AC02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antifúngico", "Micoses sistêmicas", "Tomar com alimentos"),
        ("Cetoconazol", "Nizoral", "Cetoconazol", "200mg", 1, 1, 200.0, "mg", "1 comprimido ao dia", "Tomar com alimentos", 5, 1, "Genérico", None, "J02AB02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antifúngico", "Micoses sistêmicas", "Tomar com alimentos"),
        ("Nistatina", "Micostatin Suspensão", "Nistatina", "100.000UI/ml", 4, 1, 100000.0, "UI", "4-6ml 4x ao dia", "Manter na boca antes de engolir", 5, 4, "Genérico", None, "A07AA02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antifúngico", "Candidíase oral", "Manter na boca"),
        ("Aciclovir", "Zovirax 400mg", "Aciclovir", "400mg", 1, 1, 400.0, "mg", "1 comprimido 3x ao dia", "Tomar com água", 4, 1, "Genérico", None, "J05AB01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antiviral", "Herpes simples, zoster", "Iniciar precocemente"),
        ("Aciclovir", "Zovirax Pomada", "Aciclovir", "5%", 10, 5, 5.0, "%", "Aplicar 5x ao dia", "Aplicar na área afetada", 4, 10, "Genérico", None, "J05AB01", None, None, None, None, 1, False, False, False, False, False, True, True, True, True, "Antiviral tópico", "Herpes labial", "Aplicar na área afetada"),
        ("Valaciclovir", "Valtrex", "Valaciclovir cloridrato", "500mg", 1, 1, 500.0, "mg", "1 comprimido 2x ao dia", "Tomar com água", 4, 1, "Genérico", None, "J05AB11", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antiviral", "Herpes simples, zoster", "Iniciar precocemente"),
        ("Oseltamivir", "Tamiflu 75mg", "Oseltamivir fosfato", "75mg", 1, 1, 75.0, "mg", "1 cápsula 2x ao dia por 5 dias", "Tomar com água", 4, 2, "Genérico", None, "J05AH02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antiviral", "Influenza", "Iniciar nas primeiras 48h"),
        ("Albendazol", "Zentel 400mg", "Albendazol", "400mg", 1, 1, 400.0, "mg", "1 comprimido em dose única", "Tomar com alimentos", 6, 1, "Genérico", None, "P02CA03", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antiparasitário", "Verminoses", "Dose única"),
        ("Mebendazol", "Vermox 100mg", "Mebendazol", "100mg", 1, 1, 100.0, "mg", "1 comprimido 2x ao dia por 3 dias", "Tomar com alimentos", 6, 1, "Genérico", None, "P02CA01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antiparasitário", "Verminoses", "Tomar com alimentos"),
        ("Praziquantel", "Cesol", "Praziquantel", "600mg", 1, 1, 600.0, "mg", "Conforme prescrição médica", "Tomar com alimentos", 6, 1, "Genérico", None, "P02BA01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antiparasitário", "Esquistossomose", "Tomar com alimentos"),
        ("Ivermectina", "Ivermec", "Ivermectina", "6mg", 1, 1, 6.0, "mg", "Conforme prescrição médica", "Tomar com água", 6, 1, "Genérico", None, "P02CF01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antiparasitário", "Escabiose, pediculose", "Tomar com água"),
        ("Vitamina D3", "Addera D3 2000UI", "Colecalciferol", "2000UI", 1, 1, 2000.0, "UI", "1 cápsula ao dia", "Tomar com alimentos", 17, 2, "Genérico", None, "A11CC05", None, None, None, None, 1, False, False, False, False, False, True, True, True, True, "Vitamina D", "Deficiência de vitamina D", "Tomar com alimentos gordurosos"),
        ("Vitamina D3", "Addera D3 4000UI", "Colecalciferol", "4000UI", 1, 1, 4000.0, "UI", "1 cápsula ao dia", "Tomar com alimentos", 17, 2, "Genérico", None, "A11CC05", None, None, None, None, 1, False, False, False, False, False, True, True, True, True, "Vitamina D", "Deficiência de vitamina D", "Tomar com alimentos gordurosos"),
        ("Ácido Fólico", "Folacin 5mg", "Ácido fólico", "5mg", 1, 1, 5.0, "mg", "1 comprimido ao dia", "Tomar com água", 17, 1, "Genérico", None, "B03BB01", None, None, None, None, 1, False, False, False, False, False, True, True, True, True, "Vitamina B9", "Anemia, gestação", "Importante na gestação"),
        ("Vitamina B12", "Cianocobalamina 1000mcg", "Cianocobalamina", "1000mcg", 8, 1, 1000.0, "mcg", "1 ampola IM mensal", "Aplicar via intramuscular", 17, 8, "Genérico", None, "B03BA01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Vitamina B12", "Anemia perniciosa", "Aplicação intramuscular"),
        ("Vitamina C", "Redoxon 1g", "Ácido ascórbico", "1g", 1, 1, 1000.0, "mg", "1 comprimido ao dia", "Tomar com água", 17, 1, "Genérico", None, "A11GA01", None, None, None, None, 1, False, False, False, False, False, True, True, True, True, "Vitamina C", "Deficiência de vitamina C", "Tomar com água"),
        ("Vitamina E", "Ephynal 400UI", "Alfa-tocoferol", "400UI", 1, 1, 400.0, "UI", "1 cápsula ao dia", "Tomar com alimentos", 17, 2, "Genérico", None, "A11HA03", None, None, None, None, 1, False, False, False, False, False, True, True, True, True, "Vitamina E", "Antioxidante", "Tomar com alimentos gordurosos"),
        ("Ferro", "Ferronil 40mg", "Sulfato ferroso", "40mg", 1, 1, 40.0, "mg", "1 comprimido ao dia", "Tomar em jejum", 18, 1, "Genérico", None, "B03AA07", None, None, None, None, 1, False, False, False, False, False, True, True, True, True, "Suplemento de ferro", "Anemia ferropriva", "Tomar em jejum"),
        ("Cálcio", "Calcitran 500mg", "Carbonato de cálcio", "500mg", 1, 1, 500.0, "mg", "1 comprimido 2x ao dia", "Tomar com alimentos", 18, 1, "Genérico", None, "A12AA04", None, None, None, None, 1, False, False, False, False, False, True, True, True, True, "Suplemento de cálcio", "Osteoporose", "Tomar com alimentos"),
        ("Zinco", "Zincoquel", "Sulfato de zinco", "20mg", 1, 1, 20.0, "mg", "1 comprimido ao dia", "Tomar com água", 18, 1, "Genérico", None, "A12CB01", None, None, None, None, 1, False, False, False, False, False, True, True, True, True, "Suplemento de zinco", "Deficiência de zinco", "Tomar com água"),
        ("Magnésio", "Magnésio B6", "Magnésio + Vitamina B6", "1 comprimido", 1, 1, 1.0, "comp", "1 comprimido ao dia", "Tomar com água", 18, 1, "Genérico", None, "A12CC30", None, None, None, None, 1, False, False, False, False, False, True, True, True, True, "Suplemento de magnésio", "Deficiência de magnésio", "Tomar com água"),
        ("Lactulose", "Lactulona 10g", "Lactulose", "10g/15ml", 5, 1, 10.0, "g", "15-30ml ao deitar", "Tomar com água", 15, 4, "Genérico", None, "A06AD11", None, None, None, None, 1, False, False, False, False, False, True, True, True, True, "Laxante osmótico", "Constipação", "Efeito após 24-48h"),
        ("Bisacodil", "Dulcolax 5mg", "Bisacodil", "5mg", 1, 1, 5.0, "mg", "1-2 comprimidos ao deitar", "Tomar com água", 15, 1, "Genérico", None, "A06AB02", None, None, None, None, 1, False, False, False, False, False, True, True, True, True, "Laxante estimulante", "Constipação", "Efeito após 6-12h"),
        ("Loperamida", "Imodium 2mg", "Loperamida cloridrato", "2mg", 1, 1, 2.0, "mg", "2 comprimidos após primeira evacuação", "Tomar com água", 16, 1, "Genérico", None, "A07DA03", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antidiarreico", "Diarreia aguda", "Não usar em crianças <2 anos"),
        ("Racecadotrilo", "Tiorfan", "Racecadotrilo", "100mg", 1, 1, 100.0, "mg", "1 cápsula 3x ao dia", "Tomar com água", 16, 2, "Genérico", None, "A07XA04", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antidiarreico", "Diarreia aguda", "Tomar com água"),
        ("Dorflex", "Dorflex Gotas", "Cafeína + Carisoprodol + Dipirona", "20 gotas", 6, 1, 20.0, "gota", "20 gotas a cada 8 horas", "Tomar com água", 1, 11, "Genérico", None, "M03BA03", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Relaxante muscular + Analgésico", "Dor muscular, tensão", "Pode causar sonolência"),
        ("Buscopan", "Buscopan Composto", "Butilescopolamina + Dipirona", "1 comprimido", 1, 1, 1.0, "comp", "1 comprimido a cada 8 horas", "Tomar com água", 1, 1, "Genérico", None, "A03BB01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Antiespasmódico + Analgésico", "Cólica com dor", "Alivio rápido de cólicas"),
        ("Neosaldina", "Neosaldina Gotas", "Dipirona + Cafeína + Mucato de Isometepteno", "20 gotas", 6, 1, 20.0, "gota", "20 gotas a cada 6 horas", "Tomar com água", 1, 11, "Genérico", None, "N02BB02", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Analgésico", "Dor de cabeça, enxaqueca", "Não exceder 120 gotas/dia"),
        ("Torsilax", "Torsilax Gotas", "Ciclobenzaprina + Dipirona + Cafeína", "20 gotas", 6, 1, 20.0, "gota", "20 gotas a cada 8 horas", "Tomar com água", 1, 11, "Genérico", None, "M03BX01", None, None, None, None, 2, False, False, False, False, False, True, True, True, True, "Relaxante muscular + Analgésico", "Dor muscular, lombalgia", "Pode causar sonolência"),
    ]
    
    medicamentos_expandidos.extend(medicamentos_adicionais)
    
    return medicamentos_expandidos

# Substituir MEDICAMENTOS pela lista expandida
MEDICAMENTOS = expandir_medicamentos()

def escape_sql(text):
    """Escapa caracteres especiais para SQL"""
    if text is None:
        return "NULL"
    return "'" + str(text).replace("'", "''") + "'"

def generate_registro_anvisa():
    """Gera um número de registro ANVISA fictício mas realista"""
    return f"{random.randint(1, 9)}{random.randint(0, 9)}{random.randint(0, 9)}{random.randint(0, 9)}{random.randint(0, 9)}{random.randint(0, 9)}{random.randint(0, 9)}{random.randint(0, 9)}{random.randint(0, 9)}{random.randint(0, 9)}{random.randint(0, 9)}"

def generate_codigo_catmat():
    """Gera um código CATMAT fictício mas realista"""
    return f"{random.randint(1, 9)}{random.randint(0, 9)}{random.randint(0, 9)}{random.randint(0, 9)}{random.randint(0, 9)}{random.randint(0, 9)}"

def generate_codigo_tuss():
    """Gera um código TUSS fictício mas realista"""
    return f"{random.randint(1, 9)}{random.randint(0, 9)}{random.randint(0, 9)}{random.randint(0, 9)}{random.randint(0, 9)}{random.randint(0, 9)}"

def generate_codigo_sigtap():
    """Gera um código SIGTAP fictício mas realista"""
    return f"{random.randint(1, 9)}{random.randint(0, 9)}{random.randint(0, 9)}{random.randint(0, 9)}{random.randint(0, 9)}{random.randint(0, 9)}{random.randint(0, 9)}{random.randint(0, 9)}"

def get_fabricante_id_sql():
    """Retorna SQL para buscar um fabricante aleatório"""
    return "(SELECT id FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1)"

def generate_sql():
    """Gera SQL de inserção"""
    sql_statements = []
    sql_statements.append("-- Script gerado automaticamente para inserção de medicamentos")
    sql_statements.append("-- Gerado em: " + datetime.now().strftime("%Y-%m-%d %H:%M:%S"))
    sql_statements.append("")
    sql_statements.append("DO $$")
    sql_statements.append("DECLARE")
    sql_statements.append("    fabricante_uuid UUID;")
    sql_statements.append("BEGIN")
    sql_statements.append("")
    
    for i, med in enumerate(MEDICAMENTOS, 1):
        (principio_ativo, nome_comercial, nome_generico, dosagem, forma_farm, via_admin,
         concentracao, unidade_conc, posologia, instrucoes, classe_terap, unidade_med,
         categoria, subcategoria, codigo_atc, contraindicacoes, precaucoes, valor_extra1, valor_extra2,
         tipo_controle, receita_obrig, controlado, uso_continuo, medic_especial,
         medic_excepcional, gestante_pode, lactante_pode, crianca_pode, idoso_pode,
         descricao, indicacoes, observacoes) = med
        
        # Gerar valores aleatórios
        registro_anvisa = generate_registro_anvisa()
        codigo_anvisa = f"ANVISA-{registro_anvisa}"
        catmat_codigo = generate_codigo_catmat()
        codigo_tuss = generate_codigo_tuss()
        codigo_sigtap = generate_codigo_sigtap()
        codigo_interno = f"MED-{i:04d}"
        
        # Datas
        data_registro = (datetime.now() - timedelta(days=random.randint(365, 3650))).strftime("%Y-%m-%d")
        data_validade = (datetime.now() + timedelta(days=random.randint(365, 1825))).strftime("%Y-%m-%d")
        
        # Temperatura de conservação
        temp_min = random.choice([2.0, 8.0, 15.0, None])
        temp_max = random.choice([8.0, 15.0, 25.0, 30.0, None])
        
        # Proteção
        proteger_luz = random.choice([True, False])
        proteger_umidade = random.choice([True, False])
        
        # Validade após abertura
        validade_abertura = random.choice([None, 7, 14, 30, 60, 90])
        
        # Interações e efeitos
        interacoes = random.choice([
            None,
            "Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.",
            "Interage com antiácidos. Tomar com intervalo de 2 horas.",
            "Pode potencializar efeitos de outros medicamentos. Consultar médico."
        ])
        
        efeitos_colaterais = random.choice([
            None,
            "Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.",
            "Pode causar sonolência. Evitar dirigir ou operar máquinas.",
            "Pode causar tontura. Evitar atividades que requerem atenção."
        ])
        
        # Condições de armazenamento
        condicoes_armazenamento = random.choice([
            None,
            "Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.",
            "Manter em geladeira (2-8°C). Não congelar.",
            "Armazenar em local seco e arejado, protegido da luz."
        ])
        
        instrucoes_conservacao = random.choice([
            None,
            "Manter na embalagem original. Não expor ao sol.",
            "Após abertura, conservar em geladeira e usar dentro do prazo indicado.",
            "Manter longe do alcance de crianças."
        ])
        
        sql_statements.append(f"    -- {nome_comercial} ({principio_ativo})")
        sql_statements.append("    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;")
        sql_statements.append("    ")
        sql_statements.append("    INSERT INTO public.medicacoes (")
        sql_statements.append("        id, ativo, criado_em, atualizado_em,")
        sql_statements.append("        fabricante_id,")
        sql_statements.append("        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,")
        sql_statements.append("        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,")
        sql_statements.append("        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,")
        sql_statements.append("        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,")
        sql_statements.append("        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,")
        sql_statements.append("        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,")
        sql_statements.append("        descricao, indicacoes, observacoes")
        sql_statements.append("    ) VALUES (")
        sql_statements.append(f"        gen_random_uuid(), true, NOW(), NOW(),")
        sql_statements.append(f"        fabricante_uuid,")
        sql_statements.append(f"        {escape_sql(principio_ativo)}, {escape_sql(nome_comercial)}, {escape_sql(nome_generico)}, {escape_sql(codigo_interno)}, {escape_sql(catmat_codigo)}, {escape_sql(codigo_anvisa)}, {escape_sql(codigo_tuss)}, {escape_sql(codigo_sigtap)},")
        sql_statements.append(f"        {escape_sql(dosagem)}, {unidade_med}, {via_admin}, {concentracao if concentracao else 'NULL'}, {escape_sql(unidade_conc)}, {escape_sql(posologia)}, {escape_sql(instrucoes)},")
        sql_statements.append(f"        {classe_terap}, {forma_farm}, {escape_sql(categoria)}, {escape_sql(subcategoria)}, {escape_sql(codigo_atc)},")
        sql_statements.append(f"        {escape_sql(registro_anvisa)}, {escape_sql(data_registro)}, {escape_sql(data_validade)}, {tipo_controle}, {str(receita_obrig).lower()}, {str(controlado).lower()}, {str(uso_continuo).lower()}, {str(medic_especial).lower()}, {str(medic_excepcional).lower()},")
        sql_statements.append(f"        {escape_sql(contraindicacoes)}, {escape_sql(precaucoes)}, {str(gestante_pode).lower()}, {str(lactante_pode).lower()}, {str(crianca_pode).lower()}, {str(idoso_pode).lower()}, {escape_sql(interacoes)}, {escape_sql(efeitos_colaterais)},")
        sql_statements.append(f"        {temp_min if temp_min else 'NULL'}, {temp_max if temp_max else 'NULL'}, {str(proteger_luz).lower()}, {str(proteger_umidade).lower()}, {escape_sql(condicoes_armazenamento)}, {validade_abertura if validade_abertura else 'NULL'}, {escape_sql(instrucoes_conservacao)},")
        sql_statements.append(f"        {escape_sql(descricao)}, {escape_sql(indicacoes)}, {escape_sql(observacoes)}")
        sql_statements.append("    )")
        sql_statements.append("    ON CONFLICT DO NOTHING;")
        sql_statements.append("")
    
    sql_statements.append("END $$;")
    return "\n".join(sql_statements)

if __name__ == "__main__":
    sql = generate_sql()
    print(sql)

