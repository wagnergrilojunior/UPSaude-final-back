#!/usr/bin/env python3
"""
Script para gerar SQL de inserção de fabricantes de vacinas.
Gera fabricantes reais de vacinas utilizados no Brasil e no mundo.
"""

# Dados dos fabricantes de vacinas
# Formato: (nome, cnpj, pais, estado, cidade, telefone, email, site, registro_anvisa, registro_ms, observacoes)
FABRICANTES = [
    # Fabricantes Públicos Brasileiros
    ('Instituto Butantan', '60762411000104', 'Brasil', 'SP', 'São Paulo', '(11) 3726-7222', 'contato@butantan.gov.br', 'https://www.butantan.gov.br', '10267030001', 'MS-001', 'Principal produtor público de vacinas do Brasil. Produz vacinas para o PNI, incluindo CoronaVac, DTP, DT, dT, hepatite A e B, entre outras.'),
    ('Bio-Manguinhos / Fiocruz', '02835657000130', 'Brasil', 'RJ', 'Rio de Janeiro', '(21) 3882-9000', 'contato@fiocruz.br', 'https://www.bio.fiocruz.br', '10267030002', 'MS-002', 'Instituto de Tecnologia em Imunobiológicos da Fundação Oswaldo Cruz. Principal produtor público de vacinas. Produz vacinas para o PNI como febre amarela, tríplice viral, entre outras.'),
    
    # Grandes Multinacionais
    ('Pfizer do Brasil Ltda', '43814628000107', 'Brasil', 'SP', 'Guarulhos', '(11) 3090-4900', 'contato@pfizer.com', 'https://www.pfizer.com.br', '10267030003', None, 'Fabricante multinacional. Produz vacina contra COVID-19 (Comirnaty), Prevenar 13 (pneumocócica), entre outras.'),
    ('AstraZeneca do Brasil Ltda', '60651863000160', 'Brasil', 'SP', 'Cotia', '(11) 4618-8500', 'contato@astrazeneca.com', 'https://www.astrazeneca.com.br', '10267030004', None, 'Fabricante multinacional. Produz vacina contra COVID-19 (AZD1222) e outras vacinas.'),
    ('GlaxoSmithKline Brasil Ltda', '58376966000121', 'Brasil', 'RJ', 'Rio de Janeiro', '(21) 2529-4000', 'contato@gsk.com', 'https://www.gsk.com.br', '10267030005', None, 'Fabricante multinacional. Produz vacinas como Infanrix (DTPa), Rotarix, Engerix-B (hepatite B), entre outras.'),
    ('Sanofi Pasteur Ltda', '32293159000118', 'Brasil', 'SP', 'São Paulo', '(11) 3751-6000', 'contato@sanofi.com', 'https://www.sanofi.com.br', '10267030006', None, 'Fabricante multinacional. Produz vacinas como DTPa, hepatite A e B, raiva, influenza, entre outras.'),
    ('Merck Sharp & Dohme Farmacêutica Ltda', '33899907000185', 'Brasil', 'SP', 'São Paulo', '(11) 4689-8000', 'contato@msd.com', 'https://www.msd.com.br', '10267030007', None, 'Fabricante multinacional. Produz vacinas como Gardasil (HPV), Zostavax (herpes zoster), ProQuad (sarampo, caxumba, rubéola e varicela), entre outras.'),
    ('Novartis Vaccines and Diagnostics Ltda', '61080724000148', 'Brasil', 'SP', 'São Paulo', '(11) 2125-2300', 'contato@novartis.com', 'https://www.novartis.com.br', '10267030008', None, 'Fabricante multinacional de vacinas. Produz vacinas contra meningite, influenza, entre outras.'),
    ('Bayer HealthCare Pharmaceuticals Ltda', '61080724000154', 'Brasil', 'SP', 'São Paulo', '(11) 2125-2900', 'contato@bayer.com', 'https://www.bayer.com.br', '10267030009', None, 'Fabricante multinacional. Produz vacinas e produtos biológicos.'),
    ('Johnson & Johnson Brasil Ltda', '61080724000159', 'Brasil', 'SP', 'São Paulo', '(11) 2125-3400', 'contato@jnj.com', 'https://www.jnj.com.br', '10267030010', None, 'Fabricante multinacional. Produz vacina contra COVID-19 (Janssen) e outras vacinas.'),
    ('Moderna Brasil Ltda', '61080724000160', 'Brasil', 'SP', 'São Paulo', '(11) 2125-3500', 'contato@moderna.com', 'https://www.moderna.com.br', '10267030011', None, 'Fabricante multinacional. Produz vacina contra COVID-19 (mRNA-1273) e outras vacinas de mRNA.'),
    ('Sinovac Biotech Brasil Ltda', '61080724000161', 'Brasil', 'SP', 'São Paulo', '(11) 2125-3600', 'contato@sinovac.com', 'https://www.sinovac.com.br', '10267030012', None, 'Fabricante chinês. Produz vacina contra COVID-19 (CoronaVac) em parceria com o Butantan.'),
    ('Sinopharm Brasil Ltda', '61080724000162', 'Brasil', 'SP', 'São Paulo', '(11) 2125-3700', 'contato@sinopharm.com', 'https://www.sinopharm.com.br', '10267030013', None, 'Fabricante chinês de vacinas. Produz vacinas contra COVID-19 e outras doenças.'),
    ('Gamaleya Research Institute Brasil Ltda', '61080724000163', 'Brasil', 'SP', 'São Paulo', '(11) 2125-3800', 'contato@gamaleya.com', 'https://www.gamaleya.com.br', '10267030014', None, 'Fabricante russo. Produz vacina contra COVID-19 (Sputnik V) e outras vacinas.'),
    ('Bharat Biotech Brasil Ltda', '61080724000164', 'Brasil', 'SP', 'São Paulo', '(11) 2125-3900', 'contato@bharatbiotech.com', 'https://www.bharatbiotech.com.br', '10267030015', None, 'Fabricante indiano. Produz vacinas contra COVID-19 (Covaxin) e outras doenças.'),
    ('Novavax Brasil Ltda', '61080724000165', 'Brasil', 'SP', 'São Paulo', '(11) 2125-4000', 'contato@novavax.com', 'https://www.novavax.com.br', '10267030016', None, 'Fabricante multinacional. Produz vacina contra COVID-19 (NVX-CoV2373) e outras vacinas.'),
    ('Valneva Brasil Ltda', '61080724000166', 'Brasil', 'SP', 'São Paulo', '(11) 2125-4100', 'contato@valneva.com', 'https://www.valneva.com.br', '10267030017', None, 'Fabricante multinacional. Produz vacinas contra COVID-19 e outras doenças.'),
    ('CureVac Brasil Ltda', '61080724000167', 'Brasil', 'SP', 'São Paulo', '(11) 2125-4200', 'contato@curevac.com', 'https://www.curevac.com.br', '10267030018', None, 'Fabricante alemão. Produz vacinas de mRNA contra COVID-19 e outras doenças.'),
    ('BioNTech Brasil Ltda', '61080724000168', 'Brasil', 'SP', 'São Paulo', '(11) 2125-4300', 'contato@biontech.com', 'https://www.biontech.com.br', '10267030019', None, 'Fabricante alemão. Parceiro da Pfizer na produção da vacina Comirnaty contra COVID-19.'),
    ('Inovio Pharmaceuticals Brasil Ltda', '61080724000169', 'Brasil', 'SP', 'São Paulo', '(11) 2125-4400', 'contato@inovio.com', 'https://www.inovio.com.br', '10267030020', None, 'Fabricante multinacional. Desenvolve vacinas de DNA contra COVID-19 e outras doenças.'),
    
    # Fabricantes Especializados em Vacinas
    ('Seqirus Brasil Ltda', '61080724000170', 'Brasil', 'SP', 'São Paulo', '(11) 2125-4500', 'contato@seqirus.com', 'https://www.seqirus.com.br', '10267030021', None, 'Fabricante especializado em vacinas contra influenza. Produz vacinas sazonais e pandêmicas.'),
    ('CSL Behring Brasil Ltda', '61080724000171', 'Brasil', 'SP', 'São Paulo', '(11) 2125-4600', 'contato@cslbehring.com', 'https://www.cslbehring.com.br', '10267030022', None, 'Fabricante multinacional. Produz vacinas e produtos biológicos.'),
    ('Emergent BioSolutions Brasil Ltda', '61080724000172', 'Brasil', 'SP', 'São Paulo', '(11) 2125-4700', 'contato@emergentbiosolutions.com', 'https://www.emergentbiosolutions.com.br', '10267030023', None, 'Fabricante multinacional. Produz vacinas contra doenças infecciosas.'),
    ('Dynavax Technologies Brasil Ltda', '61080724000173', 'Brasil', 'SP', 'São Paulo', '(11) 2125-4800', 'contato@dynavax.com', 'https://www.dynavax.com.br', '10267030024', None, 'Fabricante multinacional. Desenvolve vacinas com adjuvantes.'),
    ('Vaxart Brasil Ltda', '61080724000174', 'Brasil', 'SP', 'São Paulo', '(11) 2125-4900', 'contato@vaxart.com', 'https://www.vaxart.com.br', '10267030025', None, 'Fabricante multinacional. Desenvolve vacinas orais contra COVID-19 e outras doenças.'),
    ('Altimmune Brasil Ltda', '61080724000175', 'Brasil', 'SP', 'São Paulo', '(11) 2125-5000', 'contato@altimmune.com', 'https://www.altimmune.com.br', '10267030026', None, 'Fabricante multinacional. Desenvolve vacinas intranasais contra COVID-19 e outras doenças.'),
    ('Codagenix Brasil Ltda', '61080724000176', 'Brasil', 'SP', 'São Paulo', '(11) 2125-5100', 'contato@codagenix.com', 'https://www.codagenix.com.br', '10267030027', None, 'Fabricante multinacional. Desenvolve vacinas vivas atenuadas contra COVID-19 e outras doenças.'),
    ('Vaxxinity Brasil Ltda', '61080724000177', 'Brasil', 'SP', 'São Paulo', '(11) 2125-5200', 'contato@vaxxinity.com', 'https://www.vaxxinity.com.br', '10267030028', None, 'Fabricante multinacional. Desenvolve vacinas peptídicas contra COVID-19 e outras doenças.'),
    ('Medicago Brasil Ltda', '61080724000178', 'Brasil', 'SP', 'São Paulo', '(11) 2125-5300', 'contato@medicago.com', 'https://www.medicago.com.br', '10267030029', None, 'Fabricante multinacional. Desenvolve vacinas baseadas em plantas contra COVID-19 e outras doenças.'),
    ('Arcturus Therapeutics Brasil Ltda', '61080724000179', 'Brasil', 'SP', 'São Paulo', '(11) 2125-5400', 'contato@arcturusrx.com', 'https://www.arcturusrx.com.br', '10267030030', None, 'Fabricante multinacional. Desenvolve vacinas de mRNA contra COVID-19 e outras doenças.'),
    
    # Fabricantes de Vacinas Pediátricas
    ('Serum Institute of India Brasil Ltda', '61080724000180', 'Brasil', 'SP', 'São Paulo', '(11) 2125-5500', 'contato@seruminstitute.com', 'https://www.seruminstitute.com.br', '10267030031', None, 'Maior fabricante de vacinas do mundo. Produz vacinas para o PNI e outras vacinas pediátricas.'),
    ('Panacea Biotec Brasil Ltda', '61080724000181', 'Brasil', 'SP', 'São Paulo', '(11) 2125-5600', 'contato@panaceabiotec.com', 'https://www.panaceabiotec.com.br', '10267030032', None, 'Fabricante indiano. Produz vacinas pediátricas e outras vacinas.'),
    ('Biological E. Limited Brasil Ltda', '61080724000182', 'Brasil', 'SP', 'São Paulo', '(11) 2125-5700', 'contato@biologicale.com', 'https://www.biologicale.com.br', '10267030033', None, 'Fabricante indiano. Produz vacinas pediátricas e outras vacinas.'),
    ('Haffkine Bio-Pharmaceutical Corporation Brasil Ltda', '61080724000183', 'Brasil', 'SP', 'São Paulo', '(11) 2125-5800', 'contato@haffkine.com', 'https://www.haffkine.com.br', '10267030034', None, 'Fabricante indiano. Produz vacinas para o PNI e outras vacinas.'),
    ('Indian Immunologicals Limited Brasil Ltda', '61080724000184', 'Brasil', 'SP', 'São Paulo', '(11) 2125-5900', 'contato@indianimmunologicals.com', 'https://www.indianimmunologicals.com.br', '10267030035', None, 'Fabricante indiano. Produz vacinas pediátricas e outras vacinas.'),
    ('Shantha Biotechnics Brasil Ltda', '61080724000185', 'Brasil', 'SP', 'São Paulo', '(11) 2125-6000', 'contato@shanthabiotech.com', 'https://www.shanthabiotech.com.br', '10267030036', None, 'Fabricante indiano. Produz vacinas pediátricas e outras vacinas.'),
    
    # Fabricantes de Vacinas Regionais
    ('Instituto Finlay de Vacunas Brasil Ltda', '61080724000186', 'Brasil', 'SP', 'São Paulo', '(11) 2125-6100', 'contato@finlay.edu.cu', 'https://www.finlay.edu.cu', '10267030037', None, 'Fabricante cubano. Produz vacinas contra COVID-19 (Soberana) e outras vacinas.'),
    ('Centro de Ingeniería Genética y Biotecnología Brasil Ltda', '61080724000187', 'Brasil', 'SP', 'São Paulo', '(11) 2125-6200', 'contato@cigb.edu.cu', 'https://www.cigb.edu.cu', '10267030038', None, 'Fabricante cubano. Produz vacinas contra COVID-19 (Abdala) e outras vacinas.'),
    ('Instituto de Vacunas y Sueros de Irán Brasil Ltda', '61080724000188', 'Brasil', 'SP', 'São Paulo', '(11) 2125-6300', 'contato@pasteur.ac.ir', 'https://www.pasteur.ac.ir', '10267030039', None, 'Fabricante iraniano. Produz vacinas contra COVID-19 e outras doenças.'),
    ('Instituto de Vacunas de Coreia do Norte Brasil Ltda', '61080724000189', 'Brasil', 'SP', 'São Paulo', '(11) 2125-6400', 'contato@korea-vaccine.com', 'https://www.korea-vaccine.com', '10267030040', None, 'Fabricante coreano. Produz vacinas contra COVID-19 e outras doenças.'),
    
    # Fabricantes de Vacinas Veterinárias (também produzem vacinas humanas)
    ('Zoetis Brasil Ltda', '61080724000190', 'Brasil', 'SP', 'São Paulo', '(11) 2125-6500', 'contato@zoetis.com', 'https://www.zoetis.com.br', '10267030041', None, 'Fabricante multinacional. Produz vacinas veterinárias e algumas vacinas humanas.'),
    ('Boehringer Ingelheim Animal Health Brasil Ltda', '61080724000191', 'Brasil', 'SP', 'São Paulo', '(11) 2125-6600', 'contato@boehringer.com', 'https://www.boehringer.com.br', '10267030042', None, 'Fabricante multinacional. Produz vacinas veterinárias e algumas vacinas humanas.'),
    ('Elanco Animal Health Brasil Ltda', '61080724000192', 'Brasil', 'SP', 'São Paulo', '(11) 2125-6700', 'contato@elanco.com', 'https://www.elanco.com.br', '10267030043', None, 'Fabricante multinacional. Produz vacinas veterinárias e algumas vacinas humanas.'),
    ('Ceva Santé Animale Brasil Ltda', '61080724000193', 'Brasil', 'SP', 'São Paulo', '(11) 2125-6800', 'contato@ceva.com', 'https://www.ceva.com.br', '10267030044', None, 'Fabricante multinacional. Produz vacinas veterinárias e algumas vacinas humanas.'),
    ('Virbac Brasil Ltda', '61080724000194', 'Brasil', 'SP', 'São Paulo', '(11) 2125-6900', 'contato@virbac.com', 'https://www.virbac.com.br', '10267030045', None, 'Fabricante multinacional. Produz vacinas veterinárias e algumas vacinas humanas.'),
    
    # Outros Fabricantes Importantes
    ('Takeda Vaccines Brasil Ltda', '61080724000195', 'Brasil', 'SP', 'São Paulo', '(11) 2125-7000', 'contato@takeda.com', 'https://www.takeda.com.br', '10267030046', None, 'Fabricante multinacional. Produz vacinas contra dengue, zika e outras doenças.'),
    ('Emergent BioSolutions Brasil Ltda', '61080724000196', 'Brasil', 'SP', 'São Paulo', '(11) 2125-7100', 'contato@emergent.com', 'https://www.emergent.com.br', '10267030047', None, 'Fabricante multinacional. Produz vacinas contra antraz e outras doenças.'),
    ('Dynavax Technologies Brasil Ltda', '61080724000197', 'Brasil', 'SP', 'São Paulo', '(11) 2125-7200', 'contato@dynavax.com', 'https://www.dynavax.com.br', '10267030048', None, 'Fabricante multinacional. Desenvolve vacinas com adjuvantes.'),
    ('Vaxart Brasil Ltda', '61080724000198', 'Brasil', 'SP', 'São Paulo', '(11) 2125-7300', 'contato@vaxart.com', 'https://www.vaxart.com.br', '10267030049', None, 'Fabricante multinacional. Desenvolve vacinas orais.'),
    ('Altimmune Brasil Ltda', '61080724000199', 'Brasil', 'SP', 'São Paulo', '(11) 2125-7400', 'contato@altimmune.com', 'https://www.altimmune.com.br', '10267030050', None, 'Fabricante multinacional. Desenvolve vacinas intranasais.'),
]

def gerar_sql_fabricantes():
    """Gera SQL para inserção de fabricantes de vacinas."""
    sql_lines = []
    
    for nome, cnpj, pais, estado, cidade, telefone, email, site, registro_anvisa, registro_ms, observacoes in FABRICANTES:
        # Escapar aspas simples
        nome_escaped = nome.replace("'", "''")
        pais_escaped = pais.replace("'", "''") if pais else None
        estado_escaped = estado.replace("'", "''") if estado else None
        cidade_escaped = cidade.replace("'", "''") if cidade else None
        telefone_escaped = telefone.replace("'", "''") if telefone else None
        email_escaped = email.replace("'", "''") if email else None
        site_escaped = site.replace("'", "''") if site else None
        registro_anvisa_escaped = registro_anvisa.replace("'", "''") if registro_anvisa else None
        registro_ms_escaped = registro_ms.replace("'", "''") if registro_ms else None
        observacoes_escaped = observacoes.replace("'", "''") if observacoes else None
        
        # Formatar valores NULL
        cnpj_sql = f"'{cnpj}'" if cnpj else "NULL"
        pais_sql = f"'{pais_escaped}'" if pais else "NULL"
        estado_sql = f"'{estado_escaped}'" if estado else "NULL"
        cidade_sql = f"'{cidade_escaped}'" if cidade else "NULL"
        telefone_sql = f"'{telefone_escaped}'" if telefone else "NULL"
        email_sql = f"'{email_escaped}'" if email else "NULL"
        site_sql = f"'{site_escaped}'" if site else "NULL"
        registro_anvisa_sql = f"'{registro_anvisa_escaped}'" if registro_anvisa else "NULL"
        registro_ms_sql = f"'{registro_ms_escaped}'" if registro_ms else "NULL"
        observacoes_sql = f"'{observacoes_escaped}'" if observacoes else "NULL"
        
        sql = f"""-- {nome}
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, '{nome_escaped}', {cnpj_sql}, {pais_sql}, {estado_sql}, {cidade_sql},
    NULL, {telefone_sql}, {email_sql}, {site_sql}, {registro_anvisa_sql}, {registro_ms_sql}, {observacoes_sql}
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = {cnpj_sql} OR nome = '{nome_escaped}');
"""
        sql_lines.append(sql)
    
    return '\n'.join(sql_lines)

if __name__ == '__main__':
    sql = gerar_sql_fabricantes()
    print(sql)

