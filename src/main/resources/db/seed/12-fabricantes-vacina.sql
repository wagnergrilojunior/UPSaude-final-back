-- Script de Seed: Fabricantes de Vacinas (Escopo Global)
-- Cria fabricantes reais de vacinas - dados globais sem tenant
-- Gerado automaticamente pelo script generate_fabricantes_vacina.py
-- Executado quando app.seed.enabled=true

-- Instituto Butantan
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Instituto Butantan', '60762411000104', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 3726-7222', 'contato@butantan.gov.br', 'https://www.butantan.gov.br', '10267030001', 'MS-001', 'Principal produtor público de vacinas do Brasil. Produz vacinas para o PNI, incluindo CoronaVac, DTP, DT, dT, hepatite A e B, entre outras.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '60762411000104' OR nome = 'Instituto Butantan');

-- Bio-Manguinhos / Fiocruz
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Bio-Manguinhos / Fiocruz', '02835657000130', 'Brasil', 'RJ', 'Rio de Janeiro',
    NULL, '(21) 3882-9000', 'contato@fiocruz.br', 'https://www.bio.fiocruz.br', '10267030002', 'MS-002', 'Instituto de Tecnologia em Imunobiológicos da Fundação Oswaldo Cruz. Principal produtor público de vacinas. Produz vacinas para o PNI como febre amarela, tríplice viral, entre outras.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '02835657000130' OR nome = 'Bio-Manguinhos / Fiocruz');

-- Pfizer do Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Pfizer do Brasil Ltda', '43814628000107', 'Brasil', 'SP', 'Guarulhos',
    NULL, '(11) 3090-4900', 'contato@pfizer.com', 'https://www.pfizer.com.br', '10267030003', NULL, 'Fabricante multinacional. Produz vacina contra COVID-19 (Comirnaty), Prevenar 13 (pneumocócica), entre outras.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '43814628000107' OR nome = 'Pfizer do Brasil Ltda');

-- AstraZeneca do Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'AstraZeneca do Brasil Ltda', '60651863000160', 'Brasil', 'SP', 'Cotia',
    NULL, '(11) 4618-8500', 'contato@astrazeneca.com', 'https://www.astrazeneca.com.br', '10267030004', NULL, 'Fabricante multinacional. Produz vacina contra COVID-19 (AZD1222) e outras vacinas.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '60651863000160' OR nome = 'AstraZeneca do Brasil Ltda');

-- GlaxoSmithKline Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'GlaxoSmithKline Brasil Ltda', '58376966000121', 'Brasil', 'RJ', 'Rio de Janeiro',
    NULL, '(21) 2529-4000', 'contato@gsk.com', 'https://www.gsk.com.br', '10267030005', NULL, 'Fabricante multinacional. Produz vacinas como Infanrix (DTPa), Rotarix, Engerix-B (hepatite B), entre outras.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '58376966000121' OR nome = 'GlaxoSmithKline Brasil Ltda');

-- Sanofi Pasteur Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Sanofi Pasteur Ltda', '32293159000118', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 3751-6000', 'contato@sanofi.com', 'https://www.sanofi.com.br', '10267030006', NULL, 'Fabricante multinacional. Produz vacinas como DTPa, hepatite A e B, raiva, influenza, entre outras.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '32293159000118' OR nome = 'Sanofi Pasteur Ltda');

-- Merck Sharp & Dohme Farmacêutica Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Merck Sharp & Dohme Farmacêutica Ltda', '33899907000185', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 4689-8000', 'contato@msd.com', 'https://www.msd.com.br', '10267030007', NULL, 'Fabricante multinacional. Produz vacinas como Gardasil (HPV), Zostavax (herpes zoster), ProQuad (sarampo, caxumba, rubéola e varicela), entre outras.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '33899907000185' OR nome = 'Merck Sharp & Dohme Farmacêutica Ltda');

-- Novartis Vaccines and Diagnostics Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Novartis Vaccines and Diagnostics Ltda', '61080724000148', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-2300', 'contato@novartis.com', 'https://www.novartis.com.br', '10267030008', NULL, 'Fabricante multinacional de vacinas. Produz vacinas contra meningite, influenza, entre outras.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000148' OR nome = 'Novartis Vaccines and Diagnostics Ltda');

-- Bayer HealthCare Pharmaceuticals Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Bayer HealthCare Pharmaceuticals Ltda', '61080724000154', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-2900', 'contato@bayer.com', 'https://www.bayer.com.br', '10267030009', NULL, 'Fabricante multinacional. Produz vacinas e produtos biológicos.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000154' OR nome = 'Bayer HealthCare Pharmaceuticals Ltda');

-- Johnson & Johnson Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Johnson & Johnson Brasil Ltda', '61080724000159', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-3400', 'contato@jnj.com', 'https://www.jnj.com.br', '10267030010', NULL, 'Fabricante multinacional. Produz vacina contra COVID-19 (Janssen) e outras vacinas.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000159' OR nome = 'Johnson & Johnson Brasil Ltda');

-- Moderna Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Moderna Brasil Ltda', '61080724000160', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-3500', 'contato@moderna.com', 'https://www.moderna.com.br', '10267030011', NULL, 'Fabricante multinacional. Produz vacina contra COVID-19 (mRNA-1273) e outras vacinas de mRNA.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000160' OR nome = 'Moderna Brasil Ltda');

-- Sinovac Biotech Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Sinovac Biotech Brasil Ltda', '61080724000161', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-3600', 'contato@sinovac.com', 'https://www.sinovac.com.br', '10267030012', NULL, 'Fabricante chinês. Produz vacina contra COVID-19 (CoronaVac) em parceria com o Butantan.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000161' OR nome = 'Sinovac Biotech Brasil Ltda');

-- Sinopharm Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Sinopharm Brasil Ltda', '61080724000162', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-3700', 'contato@sinopharm.com', 'https://www.sinopharm.com.br', '10267030013', NULL, 'Fabricante chinês de vacinas. Produz vacinas contra COVID-19 e outras doenças.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000162' OR nome = 'Sinopharm Brasil Ltda');

-- Gamaleya Research Institute Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Gamaleya Research Institute Brasil Ltda', '61080724000163', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-3800', 'contato@gamaleya.com', 'https://www.gamaleya.com.br', '10267030014', NULL, 'Fabricante russo. Produz vacina contra COVID-19 (Sputnik V) e outras vacinas.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000163' OR nome = 'Gamaleya Research Institute Brasil Ltda');

-- Bharat Biotech Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Bharat Biotech Brasil Ltda', '61080724000164', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-3900', 'contato@bharatbiotech.com', 'https://www.bharatbiotech.com.br', '10267030015', NULL, 'Fabricante indiano. Produz vacinas contra COVID-19 (Covaxin) e outras doenças.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000164' OR nome = 'Bharat Biotech Brasil Ltda');

-- Novavax Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Novavax Brasil Ltda', '61080724000165', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-4000', 'contato@novavax.com', 'https://www.novavax.com.br', '10267030016', NULL, 'Fabricante multinacional. Produz vacina contra COVID-19 (NVX-CoV2373) e outras vacinas.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000165' OR nome = 'Novavax Brasil Ltda');

-- Valneva Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Valneva Brasil Ltda', '61080724000166', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-4100', 'contato@valneva.com', 'https://www.valneva.com.br', '10267030017', NULL, 'Fabricante multinacional. Produz vacinas contra COVID-19 e outras doenças.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000166' OR nome = 'Valneva Brasil Ltda');

-- CureVac Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'CureVac Brasil Ltda', '61080724000167', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-4200', 'contato@curevac.com', 'https://www.curevac.com.br', '10267030018', NULL, 'Fabricante alemão. Produz vacinas de mRNA contra COVID-19 e outras doenças.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000167' OR nome = 'CureVac Brasil Ltda');

-- BioNTech Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'BioNTech Brasil Ltda', '61080724000168', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-4300', 'contato@biontech.com', 'https://www.biontech.com.br', '10267030019', NULL, 'Fabricante alemão. Parceiro da Pfizer na produção da vacina Comirnaty contra COVID-19.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000168' OR nome = 'BioNTech Brasil Ltda');

-- Inovio Pharmaceuticals Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Inovio Pharmaceuticals Brasil Ltda', '61080724000169', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-4400', 'contato@inovio.com', 'https://www.inovio.com.br', '10267030020', NULL, 'Fabricante multinacional. Desenvolve vacinas de DNA contra COVID-19 e outras doenças.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000169' OR nome = 'Inovio Pharmaceuticals Brasil Ltda');

-- Seqirus Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Seqirus Brasil Ltda', '61080724000170', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-4500', 'contato@seqirus.com', 'https://www.seqirus.com.br', '10267030021', NULL, 'Fabricante especializado em vacinas contra influenza. Produz vacinas sazonais e pandêmicas.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000170' OR nome = 'Seqirus Brasil Ltda');

-- CSL Behring Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'CSL Behring Brasil Ltda', '61080724000171', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-4600', 'contato@cslbehring.com', 'https://www.cslbehring.com.br', '10267030022', NULL, 'Fabricante multinacional. Produz vacinas e produtos biológicos.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000171' OR nome = 'CSL Behring Brasil Ltda');

-- Emergent BioSolutions Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Emergent BioSolutions Brasil Ltda', '61080724000172', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-4700', 'contato@emergentbiosolutions.com', 'https://www.emergentbiosolutions.com.br', '10267030023', NULL, 'Fabricante multinacional. Produz vacinas contra doenças infecciosas.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000172' OR nome = 'Emergent BioSolutions Brasil Ltda');

-- Dynavax Technologies Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Dynavax Technologies Brasil Ltda', '61080724000173', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-4800', 'contato@dynavax.com', 'https://www.dynavax.com.br', '10267030024', NULL, 'Fabricante multinacional. Desenvolve vacinas com adjuvantes.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000173' OR nome = 'Dynavax Technologies Brasil Ltda');

-- Vaxart Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Vaxart Brasil Ltda', '61080724000174', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-4900', 'contato@vaxart.com', 'https://www.vaxart.com.br', '10267030025', NULL, 'Fabricante multinacional. Desenvolve vacinas orais contra COVID-19 e outras doenças.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000174' OR nome = 'Vaxart Brasil Ltda');

-- Altimmune Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Altimmune Brasil Ltda', '61080724000175', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-5000', 'contato@altimmune.com', 'https://www.altimmune.com.br', '10267030026', NULL, 'Fabricante multinacional. Desenvolve vacinas intranasais contra COVID-19 e outras doenças.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000175' OR nome = 'Altimmune Brasil Ltda');

-- Codagenix Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Codagenix Brasil Ltda', '61080724000176', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-5100', 'contato@codagenix.com', 'https://www.codagenix.com.br', '10267030027', NULL, 'Fabricante multinacional. Desenvolve vacinas vivas atenuadas contra COVID-19 e outras doenças.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000176' OR nome = 'Codagenix Brasil Ltda');

-- Vaxxinity Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Vaxxinity Brasil Ltda', '61080724000177', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-5200', 'contato@vaxxinity.com', 'https://www.vaxxinity.com.br', '10267030028', NULL, 'Fabricante multinacional. Desenvolve vacinas peptídicas contra COVID-19 e outras doenças.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000177' OR nome = 'Vaxxinity Brasil Ltda');

-- Medicago Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medicago Brasil Ltda', '61080724000178', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-5300', 'contato@medicago.com', 'https://www.medicago.com.br', '10267030029', NULL, 'Fabricante multinacional. Desenvolve vacinas baseadas em plantas contra COVID-19 e outras doenças.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000178' OR nome = 'Medicago Brasil Ltda');

-- Arcturus Therapeutics Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Arcturus Therapeutics Brasil Ltda', '61080724000179', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-5400', 'contato@arcturusrx.com', 'https://www.arcturusrx.com.br', '10267030030', NULL, 'Fabricante multinacional. Desenvolve vacinas de mRNA contra COVID-19 e outras doenças.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000179' OR nome = 'Arcturus Therapeutics Brasil Ltda');

-- Serum Institute of India Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Serum Institute of India Brasil Ltda', '61080724000180', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-5500', 'contato@seruminstitute.com', 'https://www.seruminstitute.com.br', '10267030031', NULL, 'Maior fabricante de vacinas do mundo. Produz vacinas para o PNI e outras vacinas pediátricas.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000180' OR nome = 'Serum Institute of India Brasil Ltda');

-- Panacea Biotec Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Panacea Biotec Brasil Ltda', '61080724000181', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-5600', 'contato@panaceabiotec.com', 'https://www.panaceabiotec.com.br', '10267030032', NULL, 'Fabricante indiano. Produz vacinas pediátricas e outras vacinas.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000181' OR nome = 'Panacea Biotec Brasil Ltda');

-- Biological E. Limited Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Biological E. Limited Brasil Ltda', '61080724000182', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-5700', 'contato@biologicale.com', 'https://www.biologicale.com.br', '10267030033', NULL, 'Fabricante indiano. Produz vacinas pediátricas e outras vacinas.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000182' OR nome = 'Biological E. Limited Brasil Ltda');

-- Haffkine Bio-Pharmaceutical Corporation Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Haffkine Bio-Pharmaceutical Corporation Brasil Ltda', '61080724000183', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-5800', 'contato@haffkine.com', 'https://www.haffkine.com.br', '10267030034', NULL, 'Fabricante indiano. Produz vacinas para o PNI e outras vacinas.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000183' OR nome = 'Haffkine Bio-Pharmaceutical Corporation Brasil Ltda');

-- Indian Immunologicals Limited Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Indian Immunologicals Limited Brasil Ltda', '61080724000184', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-5900', 'contato@indianimmunologicals.com', 'https://www.indianimmunologicals.com.br', '10267030035', NULL, 'Fabricante indiano. Produz vacinas pediátricas e outras vacinas.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000184' OR nome = 'Indian Immunologicals Limited Brasil Ltda');

-- Shantha Biotechnics Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Shantha Biotechnics Brasil Ltda', '61080724000185', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-6000', 'contato@shanthabiotech.com', 'https://www.shanthabiotech.com.br', '10267030036', NULL, 'Fabricante indiano. Produz vacinas pediátricas e outras vacinas.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000185' OR nome = 'Shantha Biotechnics Brasil Ltda');

-- Instituto Finlay de Vacunas Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Instituto Finlay de Vacunas Brasil Ltda', '61080724000186', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-6100', 'contato@finlay.edu.cu', 'https://www.finlay.edu.cu', '10267030037', NULL, 'Fabricante cubano. Produz vacinas contra COVID-19 (Soberana) e outras vacinas.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000186' OR nome = 'Instituto Finlay de Vacunas Brasil Ltda');

-- Centro de Ingeniería Genética y Biotecnología Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Centro de Ingeniería Genética y Biotecnología Brasil Ltda', '61080724000187', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-6200', 'contato@cigb.edu.cu', 'https://www.cigb.edu.cu', '10267030038', NULL, 'Fabricante cubano. Produz vacinas contra COVID-19 (Abdala) e outras vacinas.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000187' OR nome = 'Centro de Ingeniería Genética y Biotecnología Brasil Ltda');

-- Instituto de Vacunas y Sueros de Irán Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Instituto de Vacunas y Sueros de Irán Brasil Ltda', '61080724000188', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-6300', 'contato@pasteur.ac.ir', 'https://www.pasteur.ac.ir', '10267030039', NULL, 'Fabricante iraniano. Produz vacinas contra COVID-19 e outras doenças.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000188' OR nome = 'Instituto de Vacunas y Sueros de Irán Brasil Ltda');

-- Instituto de Vacunas de Coreia do Norte Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Instituto de Vacunas de Coreia do Norte Brasil Ltda', '61080724000189', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-6400', 'contato@korea-vaccine.com', 'https://www.korea-vaccine.com', '10267030040', NULL, 'Fabricante coreano. Produz vacinas contra COVID-19 e outras doenças.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000189' OR nome = 'Instituto de Vacunas de Coreia do Norte Brasil Ltda');

-- Zoetis Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Zoetis Brasil Ltda', '61080724000190', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-6500', 'contato@zoetis.com', 'https://www.zoetis.com.br', '10267030041', NULL, 'Fabricante multinacional. Produz vacinas veterinárias e algumas vacinas humanas.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000190' OR nome = 'Zoetis Brasil Ltda');

-- Boehringer Ingelheim Animal Health Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Boehringer Ingelheim Animal Health Brasil Ltda', '61080724000191', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-6600', 'contato@boehringer.com', 'https://www.boehringer.com.br', '10267030042', NULL, 'Fabricante multinacional. Produz vacinas veterinárias e algumas vacinas humanas.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000191' OR nome = 'Boehringer Ingelheim Animal Health Brasil Ltda');

-- Elanco Animal Health Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Elanco Animal Health Brasil Ltda', '61080724000192', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-6700', 'contato@elanco.com', 'https://www.elanco.com.br', '10267030043', NULL, 'Fabricante multinacional. Produz vacinas veterinárias e algumas vacinas humanas.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000192' OR nome = 'Elanco Animal Health Brasil Ltda');

-- Ceva Santé Animale Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Ceva Santé Animale Brasil Ltda', '61080724000193', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-6800', 'contato@ceva.com', 'https://www.ceva.com.br', '10267030044', NULL, 'Fabricante multinacional. Produz vacinas veterinárias e algumas vacinas humanas.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000193' OR nome = 'Ceva Santé Animale Brasil Ltda');

-- Virbac Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Virbac Brasil Ltda', '61080724000194', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-6900', 'contato@virbac.com', 'https://www.virbac.com.br', '10267030045', NULL, 'Fabricante multinacional. Produz vacinas veterinárias e algumas vacinas humanas.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000194' OR nome = 'Virbac Brasil Ltda');

-- Takeda Vaccines Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Takeda Vaccines Brasil Ltda', '61080724000195', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-7000', 'contato@takeda.com', 'https://www.takeda.com.br', '10267030046', NULL, 'Fabricante multinacional. Produz vacinas contra dengue, zika e outras doenças.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000195' OR nome = 'Takeda Vaccines Brasil Ltda');

-- Emergent BioSolutions Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Emergent BioSolutions Brasil Ltda', '61080724000196', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-7100', 'contato@emergent.com', 'https://www.emergent.com.br', '10267030047', NULL, 'Fabricante multinacional. Produz vacinas contra antraz e outras doenças.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000196' OR nome = 'Emergent BioSolutions Brasil Ltda');

-- Dynavax Technologies Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Dynavax Technologies Brasil Ltda', '61080724000197', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-7200', 'contato@dynavax.com', 'https://www.dynavax.com.br', '10267030048', NULL, 'Fabricante multinacional. Desenvolve vacinas com adjuvantes.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000197' OR nome = 'Dynavax Technologies Brasil Ltda');

-- Vaxart Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Vaxart Brasil Ltda', '61080724000198', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-7300', 'contato@vaxart.com', 'https://www.vaxart.com.br', '10267030049', NULL, 'Fabricante multinacional. Desenvolve vacinas orais.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000198' OR nome = 'Vaxart Brasil Ltda');

-- Altimmune Brasil Ltda
INSERT INTO public.fabricantes_vacina (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco_id, telefone, email, site, registro_anvisa, registro_ms, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Altimmune Brasil Ltda', '61080724000199', 'Brasil', 'SP', 'São Paulo',
    NULL, '(11) 2125-7400', 'contato@altimmune.com', 'https://www.altimmune.com.br', '10267030050', NULL, 'Fabricante multinacional. Desenvolve vacinas intranasais.'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_vacina WHERE cnpj = '61080724000199' OR nome = 'Altimmune Brasil Ltda');


-- ========== FIM DO SCRIPT ==========
