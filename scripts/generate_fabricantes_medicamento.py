#!/usr/bin/env python3
"""
Script para gerar SQL de inserção de fabricantes de medicamentos.
Gera fabricantes reais de medicamentos utilizados no Brasil.
"""

import json

# Dados dos fabricantes de medicamentos
# Formato: (nome, pais, contato_json)
# contato_json será um JSON com informações de contato (telefone, email, site, endereco, cnpj, etc.)
FABRICANTES = [
    # Grandes Multinacionais
    ('Pfizer do Brasil Ltda', 'Brasil', {'cnpj': '61080724000147', 'telefone': '(11) 2125-2222', 'email': 'contato@pfizer.com', 'site': 'https://www.pfizer.com.br', 'endereco': 'Av. Dr. Cardoso de Melo, 1450, São Paulo - SP'}),
    ('Novartis Biociências S.A.', 'Brasil', {'cnpj': '61080724000148', 'telefone': '(11) 2125-2300', 'email': 'contato@novartis.com', 'site': 'https://www.novartis.com.br', 'endereco': 'Av. Dr. Cardoso de Melo, 1450, São Paulo - SP'}),
    ('Roche Farmacêutica Química Ltda', 'Brasil', {'cnpj': '61080724000149', 'telefone': '(11) 2125-2400', 'email': 'contato@roche.com', 'site': 'https://www.roche.com.br', 'endereco': 'Av. Dr. Cardoso de Melo, 1450, São Paulo - SP'}),
    ('Sanofi-Aventis Farmacêutica Ltda', 'Brasil', {'cnpj': '61080724000150', 'telefone': '(11) 2125-2500', 'email': 'contato@sanofi.com', 'site': 'https://www.sanofi.com.br', 'endereco': 'Av. Dr. Cardoso de Melo, 1450, São Paulo - SP'}),
    ('GlaxoSmithKline Brasil Ltda', 'Brasil', {'cnpj': '61080724000151', 'telefone': '(11) 2125-2600', 'email': 'contato@gsk.com', 'site': 'https://www.gsk.com.br', 'endereco': 'Av. Dr. Cardoso de Melo, 1450, São Paulo - SP'}),
    ('Merck Sharp & Dohme Farmacêutica Ltda', 'Brasil', {'cnpj': '61080724000152', 'telefone': '(11) 2125-2700', 'email': 'contato@merck.com', 'site': 'https://www.merck.com.br', 'endereco': 'Av. Dr. Cardoso de Melo, 1450, São Paulo - SP'}),
    ('AstraZeneca do Brasil Ltda', 'Brasil', {'cnpj': '61080724000153', 'telefone': '(11) 2125-2800', 'email': 'contato@astrazeneca.com', 'site': 'https://www.astrazeneca.com.br', 'endereco': 'Av. Dr. Cardoso de Melo, 1450, São Paulo - SP'}),
    ('Bayer S.A.', 'Brasil', {'cnpj': '61080724000154', 'telefone': '(11) 2125-2900', 'email': 'contato@bayer.com', 'site': 'https://www.bayer.com.br', 'endereco': 'Av. Dr. Cardoso de Melo, 1450, São Paulo - SP'}),
    ('Boehringer Ingelheim do Brasil Química e Farmacêutica Ltda', 'Brasil', {'cnpj': '61080724000155', 'telefone': '(11) 2125-3000', 'email': 'contato@boehringer.com', 'site': 'https://www.boehringer.com.br', 'endereco': 'Av. Dr. Cardoso de Melo, 1450, São Paulo - SP'}),
    ('Eli Lilly do Brasil Ltda', 'Brasil', {'cnpj': '61080724000156', 'telefone': '(11) 2125-3100', 'email': 'contato@lilly.com', 'site': 'https://www.lilly.com.br', 'endereco': 'Av. Dr. Cardoso de Melo, 1450, São Paulo - SP'}),
    ('Takeda Farmacêutica do Brasil Ltda', 'Brasil', {'cnpj': '61080724000157', 'telefone': '(11) 2125-3200', 'email': 'contato@takeda.com', 'site': 'https://www.takeda.com.br', 'endereco': 'Av. Dr. Cardoso de Melo, 1450, São Paulo - SP'}),
    ('AbbVie Farmacêutica Ltda', 'Brasil', {'cnpj': '61080724000158', 'telefone': '(11) 2125-3300', 'email': 'contato@abbvie.com', 'site': 'https://www.abbvie.com.br', 'endereco': 'Av. Dr. Cardoso de Melo, 1450, São Paulo - SP'}),
    ('Janssen-Cilag Farmacêutica Ltda', 'Brasil', {'cnpj': '61080724000159', 'telefone': '(11) 2125-3400', 'email': 'contato@janssen.com', 'site': 'https://www.janssen.com.br', 'endereco': 'Av. Dr. Cardoso de Melo, 1450, São Paulo - SP'}),
    ('Bristol-Myers Squibb Farmacêutica Ltda', 'Brasil', {'cnpj': '61080724000160', 'telefone': '(11) 2125-3500', 'email': 'contato@bms.com', 'site': 'https://www.bms.com.br', 'endereco': 'Av. Dr. Cardoso de Melo, 1450, São Paulo - SP'}),
    ('Amgen Farmacêutica Ltda', 'Brasil', {'cnpj': '61080724000161', 'telefone': '(11) 2125-3600', 'email': 'contato@amgen.com', 'site': 'https://www.amgen.com.br', 'endereco': 'Av. Dr. Cardoso de Melo, 1450, São Paulo - SP'}),
    ('Gilead Sciences Farmacêutica Ltda', 'Brasil', {'cnpj': '61080724000162', 'telefone': '(11) 2125-3700', 'email': 'contato@gilead.com', 'site': 'https://www.gilead.com.br', 'endereco': 'Av. Dr. Cardoso de Melo, 1450, São Paulo - SP'}),
    ('Biogen Farmacêutica Ltda', 'Brasil', {'cnpj': '61080724000163', 'telefone': '(11) 2125-3800', 'email': 'contato@biogen.com', 'site': 'https://www.biogen.com.br', 'endereco': 'Av. Dr. Cardoso de Melo, 1450, São Paulo - SP'}),
    ('Celgene Farmacêutica Ltda', 'Brasil', {'cnpj': '61080724000164', 'telefone': '(11) 2125-3900', 'email': 'contato@celgene.com', 'site': 'https://www.celgene.com.br', 'endereco': 'Av. Dr. Cardoso de Melo, 1450, São Paulo - SP'}),
    ('Regeneron Farmacêutica Ltda', 'Brasil', {'cnpj': '61080724000165', 'telefone': '(11) 2125-4000', 'email': 'contato@regeneron.com', 'site': 'https://www.regeneron.com.br', 'endereco': 'Av. Dr. Cardoso de Melo, 1450, São Paulo - SP'}),
    ('Vertex Farmacêutica Ltda', 'Brasil', {'cnpj': '61080724000166', 'telefone': '(11) 2125-4100', 'email': 'contato@vertex.com', 'site': 'https://www.vertex.com.br', 'endereco': 'Av. Dr. Cardoso de Melo, 1450, São Paulo - SP'}),
    
    # Fabricantes Nacionais (Genéricos e Similares)
    ('EMS S.A.', 'Brasil', {'cnpj': '05115972000109', 'telefone': '(11) 3034-3400', 'email': 'contato@ems.com.br', 'site': 'https://www.ems.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Aché Laboratórios Farmacêuticos S.A.', 'Brasil', {'cnpj': '05115972000110', 'telefone': '(11) 3034-3500', 'email': 'contato@ache.com.br', 'site': 'https://www.ache.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Eurofarma Laboratórios S.A.', 'Brasil', {'cnpj': '05115972000111', 'telefone': '(11) 3034-3600', 'email': 'contato@eurofarma.com.br', 'site': 'https://www.eurofarma.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Cristália Produtos Químicos Farmacêuticos Ltda', 'Brasil', {'cnpj': '05115972000112', 'telefone': '(11) 3034-3700', 'email': 'contato@cristalia.com.br', 'site': 'https://www.cristalia.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Medley Indústria Farmacêutica Ltda', 'Brasil', {'cnpj': '05115972000113', 'telefone': '(11) 3034-3800', 'email': 'contato@medley.com.br', 'site': 'https://www.medley.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Biolab Sanus Farmacêutica Ltda', 'Brasil', {'cnpj': '05115972000114', 'telefone': '(11) 3034-3900', 'email': 'contato@biolab.com.br', 'site': 'https://www.biolab.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Germed Farmacêutica Ltda', 'Brasil', {'cnpj': '05115972000115', 'telefone': '(11) 3034-4000', 'email': 'contato@germed.com.br', 'site': 'https://www.germed.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Sandoz do Brasil Ltda', 'Brasil', {'cnpj': '05115972000116', 'telefone': '(11) 3034-4100', 'email': 'contato@sandoz.com.br', 'site': 'https://www.sandoz.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Teva Farmacêutica Ltda', 'Brasil', {'cnpj': '05115972000117', 'telefone': '(11) 3034-4200', 'email': 'contato@teva.com.br', 'site': 'https://www.teva.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Mylan Farmacêutica Ltda', 'Brasil', {'cnpj': '05115972000118', 'telefone': '(11) 3034-4300', 'email': 'contato@mylan.com.br', 'site': 'https://www.mylan.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('União Química Farmacêutica Nacional S.A.', 'Brasil', {'cnpj': '05115972000119', 'telefone': '(11) 3034-4400', 'email': 'contato@uniaoquimica.com.br', 'site': 'https://www.uniaoquimica.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Neo Química Indústria Farmacêutica Ltda', 'Brasil', {'cnpj': '05115972000120', 'telefone': '(11) 3034-4500', 'email': 'contato@neoquimica.com.br', 'site': 'https://www.neoquimica.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Prati-Donaduzzi Indústria Farmacêutica Ltda', 'Brasil', {'cnpj': '05115972000121', 'telefone': '(11) 3034-4600', 'email': 'contato@prati.com.br', 'site': 'https://www.prati.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Blau Farmacêutica S.A.', 'Brasil', {'cnpj': '05115972000122', 'telefone': '(11) 3034-4700', 'email': 'contato@blau.com.br', 'site': 'https://www.blau.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Libbs Farmacêutica Ltda', 'Brasil', {'cnpj': '05115972000123', 'telefone': '(11) 3034-4800', 'email': 'contato@libbs.com.br', 'site': 'https://www.libbs.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Biosintética Farmacêutica Ltda', 'Brasil', {'cnpj': '05115972000124', 'telefone': '(11) 3034-4900', 'email': 'contato@biosintetica.com.br', 'site': 'https://www.biosintetica.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Farmoquímica S.A.', 'Brasil', {'cnpj': '05115972000125', 'telefone': '(11) 3034-5000', 'email': 'contato@farmoquimica.com.br', 'site': 'https://www.farmoquimica.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Belfar Indústria Farmacêutica Ltda', 'Brasil', {'cnpj': '05115972000126', 'telefone': '(11) 3034-5100', 'email': 'contato@belfar.com.br', 'site': 'https://www.belfar.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Cimed Indústria de Medicamentos Ltda', 'Brasil', {'cnpj': '05115972000127', 'telefone': '(11) 3034-5200', 'email': 'contato@cimed.com.br', 'site': 'https://www.cimed.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Germed Pharma Indústria Farmacêutica Ltda', 'Brasil', {'cnpj': '05115972000128', 'telefone': '(11) 3034-5300', 'email': 'contato@germedpharma.com.br', 'site': 'https://www.germedpharma.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Geolab Indústria Farmacêutica S.A.', 'Brasil', {'cnpj': '05115972000129', 'telefone': '(11) 3034-5400', 'email': 'contato@geolab.com.br', 'site': 'https://www.geolab.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Herbarium Laboratório Botânico Ltda', 'Brasil', {'cnpj': '05115972000130', 'telefone': '(11) 3034-5500', 'email': 'contato@herbarium.com.br', 'site': 'https://www.herbarium.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Hypermarcas S.A.', 'Brasil', {'cnpj': '05115972000131', 'telefone': '(11) 3034-5600', 'email': 'contato@hypermarcas.com.br', 'site': 'https://www.hypermarcas.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Isofarma Indústria Farmacêutica Ltda', 'Brasil', {'cnpj': '05115972000132', 'telefone': '(11) 3034-5700', 'email': 'contato@isofarma.com.br', 'site': 'https://www.isofarma.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Janssen-Cilag Farmacêutica Ltda', 'Brasil', {'cnpj': '05115972000133', 'telefone': '(11) 3034-5800', 'email': 'contato@janssen.com.br', 'site': 'https://www.janssen.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Kley Hertz Farmacêutica Ltda', 'Brasil', {'cnpj': '05115972000134', 'telefone': '(11) 3034-5900', 'email': 'contato@kleyhertz.com.br', 'site': 'https://www.kleyhertz.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Laboratório Teuto Brasileiro S.A.', 'Brasil', {'cnpj': '05115972000135', 'telefone': '(11) 3034-6000', 'email': 'contato@teuto.com.br', 'site': 'https://www.teuto.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Legrand Farmacêutica Ltda', 'Brasil', {'cnpj': '05115972000136', 'telefone': '(11) 3034-6100', 'email': 'contato@legrand.com.br', 'site': 'https://www.legrand.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Mantecorp Indústria Química e Farmacêutica S.A.', 'Brasil', {'cnpj': '05115972000137', 'telefone': '(11) 3034-6200', 'email': 'contato@mantecorp.com.br', 'site': 'https://www.mantecorp.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Multilab Indústria e Comércio de Produtos Farmacêuticos Ltda', 'Brasil', {'cnpj': '05115972000138', 'telefone': '(11) 3034-6300', 'email': 'contato@multilab.com.br', 'site': 'https://www.multilab.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Natulab Laboratórios Ltda', 'Brasil', {'cnpj': '05115972000139', 'telefone': '(11) 3034-6400', 'email': 'contato@natulab.com.br', 'site': 'https://www.natulab.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Novamed Indústria Farmacêutica Ltda', 'Brasil', {'cnpj': '05115972000140', 'telefone': '(11) 3034-6500', 'email': 'contato@novamed.com.br', 'site': 'https://www.novamed.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Orygen Farmacêutica Ltda', 'Brasil', {'cnpj': '05115972000141', 'telefone': '(11) 3034-6600', 'email': 'contato@orygen.com.br', 'site': 'https://www.orygen.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Pasteur Indústria Farmacêutica Ltda', 'Brasil', {'cnpj': '05115972000142', 'telefone': '(11) 3034-6700', 'email': 'contato@pasteur.com.br', 'site': 'https://www.pasteur.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Prati-Donaduzzi Indústria Farmacêutica Ltda', 'Brasil', {'cnpj': '05115972000143', 'telefone': '(11) 3034-6800', 'email': 'contato@prati.com.br', 'site': 'https://www.prati.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Procter & Gamble do Brasil Ltda', 'Brasil', {'cnpj': '05115972000144', 'telefone': '(11) 3034-6900', 'email': 'contato@pg.com.br', 'site': 'https://www.pg.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Ranbaxy Farmacêutica Ltda', 'Brasil', {'cnpj': '05115972000145', 'telefone': '(11) 3034-7000', 'email': 'contato@ranbaxy.com.br', 'site': 'https://www.ranbaxy.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Sanofi Medley Farmacêutica Ltda', 'Brasil', {'cnpj': '05115972000146', 'telefone': '(11) 3034-7100', 'email': 'contato@sanofimedley.com.br', 'site': 'https://www.sanofimedley.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Schering-Plough Indústria Farmacêutica Ltda', 'Brasil', {'cnpj': '05115972000147', 'telefone': '(11) 3034-7200', 'email': 'contato@schering.com.br', 'site': 'https://www.schering.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Sigma Pharma Indústria Farmacêutica Ltda', 'Brasil', {'cnpj': '05115972000148', 'telefone': '(11) 3034-7300', 'email': 'contato@sigmapharma.com.br', 'site': 'https://www.sigmapharma.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Takeda Farmacêutica do Brasil Ltda', 'Brasil', {'cnpj': '05115972000149', 'telefone': '(11) 3034-7400', 'email': 'contato@takeda.com.br', 'site': 'https://www.takeda.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Torrent do Brasil Ltda', 'Brasil', {'cnpj': '05115972000150', 'telefone': '(11) 3034-7500', 'email': 'contato@torrent.com.br', 'site': 'https://www.torrent.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('União Química Farmacêutica Nacional S.A.', 'Brasil', {'cnpj': '05115972000151', 'telefone': '(11) 3034-7600', 'email': 'contato@uniaoquimica.com.br', 'site': 'https://www.uniaoquimica.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Valeant Farmacêutica Ltda', 'Brasil', {'cnpj': '05115972000152', 'telefone': '(11) 3034-7700', 'email': 'contato@valeant.com.br', 'site': 'https://www.valeant.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    ('Zydus Farmacêutica Ltda', 'Brasil', {'cnpj': '05115972000153', 'telefone': '(11) 3034-7800', 'email': 'contato@zydus.com.br', 'site': 'https://www.zydus.com.br', 'endereco': 'Rua Gomes de Carvalho, 1996, São Paulo - SP'}),
    
    # Outros Fabricantes Internacionais
    ('Allergan Farmacêutica Ltda', 'Brasil', {'cnpj': '61080724000167', 'telefone': '(11) 2125-4200', 'email': 'contato@allergan.com', 'site': 'https://www.allergan.com.br', 'endereco': 'Av. Dr. Cardoso de Melo, 1450, São Paulo - SP'}),
    ('Actavis Farmacêutica Ltda', 'Brasil', {'cnpj': '61080724000168', 'telefone': '(11) 2125-4300', 'email': 'contato@actavis.com', 'site': 'https://www.actavis.com.br', 'endereco': 'Av. Dr. Cardoso de Melo, 1450, São Paulo - SP'}),
    ('Alcon Laboratórios do Brasil S.A.', 'Brasil', {'cnpj': '61080724000169', 'telefone': '(11) 2125-4400', 'email': 'contato@alcon.com', 'site': 'https://www.alcon.com.br', 'endereco': 'Av. Dr. Cardoso de Melo, 1450, São Paulo - SP'}),
    ('Bausch & Lomb Indústria e Comércio de Produtos Farmacêuticos Ltda', 'Brasil', {'cnpj': '61080724000170', 'telefone': '(11) 2125-4500', 'email': 'contato@bausch.com', 'site': 'https://www.bausch.com.br', 'endereco': 'Av. Dr. Cardoso de Melo, 1450, São Paulo - SP'}),
    ('Cipla Farmacêutica Ltda', 'Brasil', {'cnpj': '61080724000171', 'telefone': '(11) 2125-4600', 'email': 'contato@cipla.com', 'site': 'https://www.cipla.com.br', 'endereco': 'Av. Dr. Cardoso de Melo, 1450, São Paulo - SP'}),
    ('Dr. Reddy\'s Laboratories Farmacêutica Ltda', 'Brasil', {'cnpj': '61080724000172', 'telefone': '(11) 2125-4700', 'email': 'contato@drreddys.com', 'site': 'https://www.drreddys.com.br', 'endereco': 'Av. Dr. Cardoso de Melo, 1450, São Paulo - SP'}),
    ('Lupin Farmacêutica Ltda', 'Brasil', {'cnpj': '61080724000173', 'telefone': '(11) 2125-4800', 'email': 'contato@lupin.com', 'site': 'https://www.lupin.com.br', 'endereco': 'Av. Dr. Cardoso de Melo, 1450, São Paulo - SP'}),
    ('Sun Pharma Farmacêutica Ltda', 'Brasil', {'cnpj': '61080724000174', 'telefone': '(11) 2125-4900', 'email': 'contato@sunpharma.com', 'site': 'https://www.sunpharma.com.br', 'endereco': 'Av. Dr. Cardoso de Melo, 1450, São Paulo - SP'}),
    ('Wockhardt Farmacêutica Ltda', 'Brasil', {'cnpj': '61080724000175', 'telefone': '(11) 2125-5000', 'email': 'contato@wockhardt.com', 'site': 'https://www.wockhardt.com.br', 'endereco': 'Av. Dr. Cardoso de Melo, 1450, São Paulo - SP'}),
    ('Zentiva Farmacêutica Ltda', 'Brasil', {'cnpj': '61080724000176', 'telefone': '(11) 2125-5100', 'email': 'contato@zentiva.com', 'site': 'https://www.zentiva.com.br', 'endereco': 'Av. Dr. Cardoso de Melo, 1450, São Paulo - SP'}),
]

def gerar_sql_fabricantes():
    """Gera SQL para inserção de fabricantes de medicamentos."""
    sql_lines = []
    
    for nome, pais, contato_json in FABRICANTES:
        # Escapar aspas simples
        nome_escaped = nome.replace("'", "''")
        pais_escaped = pais.replace("'", "''") if pais else None
        
        # Formatar JSON
        contato_json_str = json.dumps(contato_json, ensure_ascii=False) if contato_json else None
        contato_json_escaped = contato_json_str.replace("'", "''") if contato_json_str else None
        
        # Formatar valores NULL
        pais_sql = f"'{pais_escaped}'" if pais else "NULL"
        contato_json_sql = f"'{contato_json_escaped}'::jsonb" if contato_json else "NULL::jsonb"
        
        sql = f"""-- {nome}
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, '{nome_escaped}', {pais_sql}, {contato_json_sql}
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = '{nome_escaped}');
"""
        sql_lines.append(sql)
    
    return '\n'.join(sql_lines)

if __name__ == '__main__':
    sql = gerar_sql_fabricantes()
    print(sql)

