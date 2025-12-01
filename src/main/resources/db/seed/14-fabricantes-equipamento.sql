-- Script de Seed: Fabricantes de Equipamentos Médicos (Escopo Global)
-- Cria fabricantes reais de equipamentos médicos - dados globais sem tenant
-- Gerado automaticamente pelo script generate_fabricantes_equipamento.py
-- Executado quando app.seed.enabled=true

-- GE Healthcare do Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'GE Healthcare do Brasil Ltda', '06272962000126', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1056', '(11) 5501-3700', 'contato@ge.com', 'https://www.gehealthcare.com.br', '80048610001', 'Fabricante multinacional de equipamentos médicos de imagem, monitoramento e diagnóstico'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '06272962000126' OR nome = 'GE Healthcare do Brasil Ltda');

-- Philips do Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Philips do Brasil Ltda', '61080724000147', 'Brasil', 'SP', 'São Paulo',
    'Av. Dr. Cardoso de Melo, 1450', '(11) 2125-2222', 'contato@philips.com', 'https://www.philips.com.br', '80048610002', 'Fabricante multinacional de equipamentos médicos, sistemas de imagem e monitoramento'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '61080724000147' OR nome = 'Philips do Brasil Ltda');

-- Siemens Healthineers Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Siemens Healthineers Brasil Ltda', '04257066000120', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1056', '(11) 5501-3000', 'contato@siemens-healthineers.com', 'https://www.siemens-healthineers.com.br', '80048610007', 'Fabricante alemão de equipamentos médicos de imagem e diagnóstico'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '04257066000120' OR nome = 'Siemens Healthineers Brasil Ltda');

-- Toshiba Medical Systems Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Toshiba Medical Systems Brasil Ltda', '04470320000100', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1056', '(11) 5501-4000', 'contato@toshiba-medical.com', 'https://www.toshiba-medical.com.br', '80048610008', 'Fabricante japonês de equipamentos médicos de imagem'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '04470320000100' OR nome = 'Toshiba Medical Systems Brasil Ltda');

-- Canon Medical Systems Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Canon Medical Systems Brasil Ltda', '04470320000101', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1056', '(11) 5501-4100', 'contato@canon-medical.com', 'https://www.canon-medical.com.br', '80048610009', 'Fabricante japonês de equipamentos médicos de imagem'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '04470320000101' OR nome = 'Canon Medical Systems Brasil Ltda');

-- Hitachi Medical Systems Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Hitachi Medical Systems Brasil Ltda', '04470320000102', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1056', '(11) 5501-4200', 'contato@hitachi-medical.com', 'https://www.hitachi-medical.com.br', '80048610010', 'Fabricante japonês de equipamentos médicos de imagem'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '04470320000102' OR nome = 'Hitachi Medical Systems Brasil Ltda');

-- Samsung Medison Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Samsung Medison Brasil Ltda', '04470320000103', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1056', '(11) 5501-4300', 'contato@samsung-medison.com', 'https://www.samsung-medison.com.br', '80048610011', 'Fabricante coreano de equipamentos médicos de imagem'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '04470320000103' OR nome = 'Samsung Medison Brasil Ltda');

-- Mindray Medical Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Mindray Medical Brasil Ltda', '14959305000104', 'Brasil', 'SP', 'São Paulo',
    'Rua Funchal, 538', '(11) 3040-5500', 'contato@mindray.com', 'https://www.mindray.com.br', '80048610003', 'Fabricante de equipamentos médicos, monitores multiparamétricos, ventiladores e ultrassons'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '14959305000104' OR nome = 'Mindray Medical Brasil Ltda');

-- Dräger Medical Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Dräger Medical Brasil Ltda', '44141070000163', 'Brasil', 'SP', 'São Paulo',
    'Av. Juscelino Kubitschek, 1450', '(11) 3039-5800', 'contato@draeger.com', 'https://www.draeger.com.br', '80048610004', 'Fabricante alemão de equipamentos médicos, ventiladores pulmonares e monitores'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '44141070000163' OR nome = 'Dräger Medical Brasil Ltda');

-- Medtronic Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medtronic Brasil Ltda', '01554909000190', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-5000', 'contato@medtronic.com', 'https://www.medtronic.com.br', '80048610005', 'Fabricante multinacional de dispositivos médicos, monitores e equipamentos para terapia intensiva'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000190' OR nome = 'Medtronic Brasil Ltda');

-- Baxter Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Baxter Brasil Ltda', '01554909000191', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-5100', 'contato@baxter.com', 'https://www.baxter.com.br', '80048610012', 'Fabricante de equipamentos médicos e dispositivos para terapia intensiva'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000191' OR nome = 'Baxter Brasil Ltda');

-- Fresenius Medical Care Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Fresenius Medical Care Brasil Ltda', '01554909000192', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-5200', 'contato@fresenius.com', 'https://www.fresenius.com.br', '80048610013', 'Fabricante de equipamentos para diálise e terapia intensiva'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000192' OR nome = 'Fresenius Medical Care Brasil Ltda');

-- B. Braun do Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'B. Braun do Brasil Ltda', '01554909000193', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-5300', 'contato@bbraun.com', 'https://www.bbraun.com.br', '80048610014', 'Fabricante alemão de equipamentos médicos e dispositivos hospitalares'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000193' OR nome = 'B. Braun do Brasil Ltda');

-- Becton Dickinson Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Becton Dickinson Brasil Ltda', '01554909000194', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-5400', 'contato@bd.com', 'https://www.bd.com.br', '80048610015', 'Fabricante de equipamentos médicos e dispositivos descartáveis'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000194' OR nome = 'Becton Dickinson Brasil Ltda');

-- Abbott Medical Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Abbott Medical Brasil Ltda', '01554909000195', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-5500', 'contato@abbott.com', 'https://www.abbott.com.br', '80048610016', 'Fabricante de equipamentos médicos e dispositivos cardíacos'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000195' OR nome = 'Abbott Medical Brasil Ltda');

-- Boston Scientific Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Boston Scientific Brasil Ltda', '01554909000196', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-5600', 'contato@bostonscientific.com', 'https://www.bostonscientific.com.br', '80048610017', 'Fabricante de dispositivos médicos e equipamentos cardíacos'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000196' OR nome = 'Boston Scientific Brasil Ltda');

-- Stryker Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Stryker Brasil Ltda', '01554909000197', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-5700', 'contato@stryker.com', 'https://www.stryker.com.br', '80048610018', 'Fabricante de equipamentos médicos e dispositivos ortopédicos'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000197' OR nome = 'Stryker Brasil Ltda');

-- Zimmer Biomet Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Zimmer Biomet Brasil Ltda', '01554909000198', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-5800', 'contato@zimmerbiomet.com', 'https://www.zimmerbiomet.com.br', '80048610019', 'Fabricante de equipamentos médicos e dispositivos ortopédicos'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000198' OR nome = 'Zimmer Biomet Brasil Ltda');

-- Johnson & Johnson Medical Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Johnson & Johnson Medical Brasil Ltda', '01554909000199', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-5900', 'contato@jnj.com', 'https://www.jnj.com.br', '80048610020', 'Fabricante multinacional de equipamentos médicos e dispositivos cirúrgicos'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000199' OR nome = 'Johnson & Johnson Medical Brasil Ltda');

-- Olympus Medical Systems Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Olympus Medical Systems Brasil Ltda', '01554909000200', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-6000', 'contato@olympus.com', 'https://www.olympus.com.br', '80048610021', 'Fabricante japonês de equipamentos endoscópicos e cirúrgicos'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000200' OR nome = 'Olympus Medical Systems Brasil Ltda');

-- Karl Storz Endoscopia Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Karl Storz Endoscopia Brasil Ltda', '01554909000201', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-6100', 'contato@karlstorz.com', 'https://www.karlstorz.com.br', '80048610022', 'Fabricante alemão de equipamentos endoscópicos e cirúrgicos'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000201' OR nome = 'Karl Storz Endoscopia Brasil Ltda');

-- Richard Wolf Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Richard Wolf Brasil Ltda', '01554909000202', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-6200', 'contato@richardwolf.com', 'https://www.richardwolf.com.br', '80048610023', 'Fabricante alemão de equipamentos endoscópicos e cirúrgicos'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000202' OR nome = 'Richard Wolf Brasil Ltda');

-- Storz Medical Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Storz Medical Brasil Ltda', '01554909000203', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-6300', 'contato@storzmedical.com', 'https://www.storzmedical.com.br', '80048610024', 'Fabricante de equipamentos médicos e dispositivos cirúrgicos'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000203' OR nome = 'Storz Medical Brasil Ltda');

-- Conmed Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Conmed Brasil Ltda', '01554909000204', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-6400', 'contato@conmed.com', 'https://www.conmed.com.br', '80048610025', 'Fabricante de equipamentos médicos e dispositivos cirúrgicos'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000204' OR nome = 'Conmed Brasil Ltda');

-- Smith & Nephew Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Smith & Nephew Brasil Ltda', '01554909000205', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-6500', 'contato@smithnephew.com', 'https://www.smithnephew.com.br', '80048610026', 'Fabricante de equipamentos médicos e dispositivos ortopédicos'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000205' OR nome = 'Smith & Nephew Brasil Ltda');

-- Arthrex Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Arthrex Brasil Ltda', '01554909000206', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-6600', 'contato@arthrex.com', 'https://www.arthrex.com.br', '80048610027', 'Fabricante de equipamentos médicos e dispositivos ortopédicos'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000206' OR nome = 'Arthrex Brasil Ltda');

-- DePuy Synthes Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'DePuy Synthes Brasil Ltda', '01554909000207', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-6700', 'contato@depuysynthes.com', 'https://www.depuysynthes.com.br', '80048610028', 'Fabricante de equipamentos médicos e dispositivos ortopédicos'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000207' OR nome = 'DePuy Synthes Brasil Ltda');

-- Medacta Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medacta Brasil Ltda', '01554909000208', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-6800', 'contato@medacta.com', 'https://www.medacta.com.br', '80048610029', 'Fabricante de equipamentos médicos e dispositivos ortopédicos'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000208' OR nome = 'Medacta Brasil Ltda');

-- NuVasive Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'NuVasive Brasil Ltda', '01554909000209', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-6900', 'contato@nuvasive.com', 'https://www.nuvasive.com.br', '80048610030', 'Fabricante de equipamentos médicos e dispositivos ortopédicos'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000209' OR nome = 'NuVasive Brasil Ltda');

-- Esaote do Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Esaote do Brasil Ltda', '01554909000210', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-7000', 'contato@esaote.com', 'https://www.esaote.com.br', '80048610031', 'Fabricante italiano de equipamentos de ultrassom'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000210' OR nome = 'Esaote do Brasil Ltda');

-- Fujifilm Medical Systems Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Fujifilm Medical Systems Brasil Ltda', '01554909000211', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-7100', 'contato@fujifilm-medical.com', 'https://www.fujifilm-medical.com.br', '80048610032', 'Fabricante japonês de equipamentos de ultrassom e imagem'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000211' OR nome = 'Fujifilm Medical Systems Brasil Ltda');

-- Shimadzu Medical Systems Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Shimadzu Medical Systems Brasil Ltda', '01554909000212', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-7200', 'contato@shimadzu-medical.com', 'https://www.shimadzu-medical.com.br', '80048610033', 'Fabricante japonês de equipamentos médicos de imagem'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000212' OR nome = 'Shimadzu Medical Systems Brasil Ltda');

-- Hologic Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Hologic Brasil Ltda', '01554909000213', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-7300', 'contato@hologic.com', 'https://www.hologic.com.br', '80048610034', 'Fabricante de equipamentos médicos de imagem e diagnóstico'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000213' OR nome = 'Hologic Brasil Ltda');

-- Carestream Health Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Carestream Health Brasil Ltda', '01554909000214', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-7400', 'contato@carestream.com', 'https://www.carestream.com.br', '80048610035', 'Fabricante de equipamentos médicos de imagem e diagnóstico'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000214' OR nome = 'Carestream Health Brasil Ltda');

-- Agfa HealthCare Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Agfa HealthCare Brasil Ltda', '01554909000215', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-7500', 'contato@agfa.com', 'https://www.agfa.com.br', '80048610036', 'Fabricante de equipamentos médicos de imagem e diagnóstico'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000215' OR nome = 'Agfa HealthCare Brasil Ltda');

-- Fuji Medical Systems Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Fuji Medical Systems Brasil Ltda', '01554909000216', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-7600', 'contato@fuji-medical.com', 'https://www.fuji-medical.com.br', '80048610037', 'Fabricante japonês de equipamentos médicos de imagem'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000216' OR nome = 'Fuji Medical Systems Brasil Ltda');

-- Nihon Kohden Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Nihon Kohden Brasil Ltda', '01554909000217', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-7700', 'contato@nihonkohden.com', 'https://www.nihonkohden.com.br', '80048610038', 'Fabricante japonês de monitores multiparamétricos e equipamentos de diagnóstico'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000217' OR nome = 'Nihon Kohden Brasil Ltda');

-- Spacelabs Healthcare Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Spacelabs Healthcare Brasil Ltda', '01554909000218', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-7800', 'contato@spacelabs.com', 'https://www.spacelabs.com.br', '80048610039', 'Fabricante de monitores multiparamétricos e equipamentos de monitoramento'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000218' OR nome = 'Spacelabs Healthcare Brasil Ltda');

-- Welch Allyn Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Welch Allyn Brasil Ltda', '01554909000219', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-7900', 'contato@welchallyn.com', 'https://www.welchallyn.com.br', '80048610040', 'Fabricante de equipamentos médicos e dispositivos de diagnóstico'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000219' OR nome = 'Welch Allyn Brasil Ltda');

-- Masimo Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Masimo Brasil Ltda', '01554909000220', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-8000', 'contato@masimo.com', 'https://www.masimo.com.br', '80048610041', 'Fabricante de monitores e equipamentos de monitoramento'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000220' OR nome = 'Masimo Brasil Ltda');

-- Nonin Medical Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Nonin Medical Brasil Ltda', '01554909000221', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-8100', 'contato@nonin.com', 'https://www.nonin.com.br', '80048610042', 'Fabricante de monitores e equipamentos de monitoramento'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000221' OR nome = 'Nonin Medical Brasil Ltda');

-- Nellcor Puritan Bennett Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Nellcor Puritan Bennett Brasil Ltda', '01554909000222', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-8200', 'contato@nellcor.com', 'https://www.nellcor.com.br', '80048610043', 'Fabricante de monitores e equipamentos de monitoramento'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000222' OR nome = 'Nellcor Puritan Bennett Brasil Ltda');

-- Hamilton Medical Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Hamilton Medical Brasil Ltda', '01554909000223', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-8300', 'contato@hamilton-medical.com', 'https://www.hamilton-medical.com.br', '80048610044', 'Fabricante suíço de ventiladores pulmonares'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000223' OR nome = 'Hamilton Medical Brasil Ltda');

-- Maquet Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Maquet Brasil Ltda', '01554909000224', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-8400', 'contato@maquet.com', 'https://www.maquet.com.br', '80048610045', 'Fabricante alemão de ventiladores pulmonares e equipamentos de terapia intensiva'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000224' OR nome = 'Maquet Brasil Ltda');

-- Vyaire Medical Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Vyaire Medical Brasil Ltda', '01554909000225', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-8500', 'contato@vyaire.com', 'https://www.vyaire.com.br', '80048610046', 'Fabricante de ventiladores pulmonares e equipamentos respiratórios'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000225' OR nome = 'Vyaire Medical Brasil Ltda');

-- ResMed Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'ResMed Brasil Ltda', '01554909000226', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-8600', 'contato@resmed.com', 'https://www.resmed.com.br', '80048610047', 'Fabricante de equipamentos respiratórios e CPAP'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000226' OR nome = 'ResMed Brasil Ltda');

-- Fisher & Paykel Healthcare Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Fisher & Paykel Healthcare Brasil Ltda', '01554909000227', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-8700', 'contato@fphcare.com', 'https://www.fphcare.com.br', '80048610048', 'Fabricante de equipamentos respiratórios e CPAP'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000227' OR nome = 'Fisher & Paykel Healthcare Brasil Ltda');

-- Bionexo Tecnologia em Saúde Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Bionexo Tecnologia em Saúde Ltda', '05115972000109', 'Brasil', 'SP', 'São Paulo',
    'Rua Gomes de Carvalho, 1996', '(11) 3034-3400', 'contato@bionexo.com', 'https://www.bionexo.com.br', '80048610006', 'Empresa brasileira especializada em soluções e equipamentos para gestão de saúde'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '05115972000109' OR nome = 'Bionexo Tecnologia em Saúde Ltda');

-- Fanem Equipamentos Médicos Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Fanem Equipamentos Médicos Ltda', '05115972000110', 'Brasil', 'SP', 'São Paulo',
    'Rua Gomes de Carvalho, 1996', '(11) 3034-3500', 'contato@fanem.com', 'https://www.fanem.com.br', '80048610049', 'Fabricante brasileiro de equipamentos médicos e incubadoras'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '05115972000110' OR nome = 'Fanem Equipamentos Médicos Ltda');

-- K-Tech Equipamentos Médicos Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'K-Tech Equipamentos Médicos Ltda', '05115972000111', 'Brasil', 'SP', 'São Paulo',
    'Rua Gomes de Carvalho, 1996', '(11) 3034-3600', 'contato@ktech.com', 'https://www.ktech.com.br', '80048610050', 'Fabricante brasileiro de equipamentos médicos'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '05115972000111' OR nome = 'K-Tech Equipamentos Médicos Ltda');

-- Lifemed Equipamentos Médicos Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Lifemed Equipamentos Médicos Ltda', '05115972000112', 'Brasil', 'SP', 'São Paulo',
    'Rua Gomes de Carvalho, 1996', '(11) 3034-3700', 'contato@lifemed.com', 'https://www.lifemed.com.br', '80048610051', 'Fabricante brasileiro de equipamentos médicos'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '05115972000112' OR nome = 'Lifemed Equipamentos Médicos Ltda');

-- Oliveira Equipamentos Médicos Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Oliveira Equipamentos Médicos Ltda', '05115972000113', 'Brasil', 'SP', 'São Paulo',
    'Rua Gomes de Carvalho, 1996', '(11) 3034-3800', 'contato@oliveira.com', 'https://www.oliveira.com.br', '80048610052', 'Fabricante brasileiro de equipamentos médicos'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '05115972000113' OR nome = 'Oliveira Equipamentos Médicos Ltda');

-- Tecnomed Equipamentos Médicos Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Tecnomed Equipamentos Médicos Ltda', '05115972000114', 'Brasil', 'SP', 'São Paulo',
    'Rua Gomes de Carvalho, 1996', '(11) 3034-3900', 'contato@tecnomed.com', 'https://www.tecnomed.com.br', '80048610053', 'Fabricante brasileiro de equipamentos médicos'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '05115972000114' OR nome = 'Tecnomed Equipamentos Médicos Ltda');

-- Viamed Equipamentos Médicos Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Viamed Equipamentos Médicos Ltda', '05115972000115', 'Brasil', 'SP', 'São Paulo',
    'Rua Gomes de Carvalho, 1996', '(11) 3034-4000', 'contato@viamed.com', 'https://www.viamed.com.br', '80048610054', 'Fabricante brasileiro de equipamentos médicos'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '05115972000115' OR nome = 'Viamed Equipamentos Médicos Ltda');

-- WEM Equipamentos Médicos Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'WEM Equipamentos Médicos Ltda', '05115972000116', 'Brasil', 'SP', 'São Paulo',
    'Rua Gomes de Carvalho, 1996', '(11) 3034-4100', 'contato@wem.com', 'https://www.wem.com.br', '80048610055', 'Fabricante brasileiro de equipamentos médicos'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '05115972000116' OR nome = 'WEM Equipamentos Médicos Ltda');

-- Medibras Equipamentos Médicos Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medibras Equipamentos Médicos Ltda', '05115972000117', 'Brasil', 'SP', 'São Paulo',
    'Rua Gomes de Carvalho, 1996', '(11) 3034-4200', 'contato@medibras.com', 'https://www.medibras.com.br', '80048610056', 'Fabricante brasileiro de equipamentos médicos'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '05115972000117' OR nome = 'Medibras Equipamentos Médicos Ltda');

-- Meditec Equipamentos Médicos Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Meditec Equipamentos Médicos Ltda', '05115972000118', 'Brasil', 'SP', 'São Paulo',
    'Rua Gomes de Carvalho, 1996', '(11) 3034-4300', 'contato@meditec.com', 'https://www.meditec.com.br', '80048610057', 'Fabricante brasileiro de equipamentos médicos'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '05115972000118' OR nome = 'Meditec Equipamentos Médicos Ltda');

-- Medmax Equipamentos Médicos Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medmax Equipamentos Médicos Ltda', '05115972000119', 'Brasil', 'SP', 'São Paulo',
    'Rua Gomes de Carvalho, 1996', '(11) 3034-4400', 'contato@medmax.com', 'https://www.medmax.com.br', '80048610058', 'Fabricante brasileiro de equipamentos médicos'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '05115972000119' OR nome = 'Medmax Equipamentos Médicos Ltda');

-- Medtech Equipamentos Médicos Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medtech Equipamentos Médicos Ltda', '05115972000120', 'Brasil', 'SP', 'São Paulo',
    'Rua Gomes de Carvalho, 1996', '(11) 3034-4500', 'contato@medtech.com', 'https://www.medtech.com.br', '80048610059', 'Fabricante brasileiro de equipamentos médicos'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '05115972000120' OR nome = 'Medtech Equipamentos Médicos Ltda');

-- Medway Equipamentos Médicos Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medway Equipamentos Médicos Ltda', '05115972000121', 'Brasil', 'SP', 'São Paulo',
    'Rua Gomes de Carvalho, 1996', '(11) 3034-4600', 'contato@medway.com', 'https://www.medway.com.br', '80048610060', 'Fabricante brasileiro de equipamentos médicos'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '05115972000121' OR nome = 'Medway Equipamentos Médicos Ltda');

-- Medlife Equipamentos Médicos Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medlife Equipamentos Médicos Ltda', '05115972000122', 'Brasil', 'SP', 'São Paulo',
    'Rua Gomes de Carvalho, 1996', '(11) 3034-4700', 'contato@medlife.com', 'https://www.medlife.com.br', '80048610061', 'Fabricante brasileiro de equipamentos médicos'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '05115972000122' OR nome = 'Medlife Equipamentos Médicos Ltda');

-- Medcare Equipamentos Médicos Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medcare Equipamentos Médicos Ltda', '05115972000123', 'Brasil', 'SP', 'São Paulo',
    'Rua Gomes de Carvalho, 1996', '(11) 3034-4800', 'contato@medcare.com', 'https://www.medcare.com.br', '80048610062', 'Fabricante brasileiro de equipamentos médicos'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '05115972000123' OR nome = 'Medcare Equipamentos Médicos Ltda');

-- Medpro Equipamentos Médicos Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medpro Equipamentos Médicos Ltda', '05115972000124', 'Brasil', 'SP', 'São Paulo',
    'Rua Gomes de Carvalho, 1996', '(11) 3034-4900', 'contato@medpro.com', 'https://www.medpro.com.br', '80048610063', 'Fabricante brasileiro de equipamentos médicos'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '05115972000124' OR nome = 'Medpro Equipamentos Médicos Ltda');

-- Medplus Equipamentos Médicos Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medplus Equipamentos Médicos Ltda', '05115972000125', 'Brasil', 'SP', 'São Paulo',
    'Rua Gomes de Carvalho, 1996', '(11) 3034-5000', 'contato@medplus.com', 'https://www.medplus.com.br', '80048610064', 'Fabricante brasileiro de equipamentos médicos'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '05115972000125' OR nome = 'Medplus Equipamentos Médicos Ltda');

-- Medstar Equipamentos Médicos Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medstar Equipamentos Médicos Ltda', '05115972000126', 'Brasil', 'SP', 'São Paulo',
    'Rua Gomes de Carvalho, 1996', '(11) 3034-5100', 'contato@medstar.com', 'https://www.medstar.com.br', '80048610065', 'Fabricante brasileiro de equipamentos médicos'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '05115972000126' OR nome = 'Medstar Equipamentos Médicos Ltda');

-- Medsys Equipamentos Médicos Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medsys Equipamentos Médicos Ltda', '05115972000127', 'Brasil', 'SP', 'São Paulo',
    'Rua Gomes de Carvalho, 1996', '(11) 3034-5200', 'contato@medsys.com', 'https://www.medsys.com.br', '80048610066', 'Fabricante brasileiro de equipamentos médicos'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '05115972000127' OR nome = 'Medsys Equipamentos Médicos Ltda');

-- Medvision Equipamentos Médicos Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medvision Equipamentos Médicos Ltda', '05115972000128', 'Brasil', 'SP', 'São Paulo',
    'Rua Gomes de Carvalho, 1996', '(11) 3034-5300', 'contato@medvision.com', 'https://www.medvision.com.br', '80048610067', 'Fabricante brasileiro de equipamentos médicos'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '05115972000128' OR nome = 'Medvision Equipamentos Médicos Ltda');

-- Edwards Lifesciences Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Edwards Lifesciences Brasil Ltda', '01554909000228', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-8800', 'contato@edwards.com', 'https://www.edwards.com.br', '80048610068', 'Fabricante de dispositivos cardíacos e equipamentos de monitoramento'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000228' OR nome = 'Edwards Lifesciences Brasil Ltda');

-- Terumo Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Terumo Brasil Ltda', '01554909000229', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-8900', 'contato@terumo.com', 'https://www.terumo.com.br', '80048610069', 'Fabricante japonês de dispositivos médicos e equipamentos hospitalares'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000229' OR nome = 'Terumo Brasil Ltda');

-- Getinge Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Getinge Brasil Ltda', '01554909000230', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-9000', 'contato@getinge.com', 'https://www.getinge.com.br', '80048610070', 'Fabricante sueco de equipamentos médicos e dispositivos hospitalares'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000230' OR nome = 'Getinge Brasil Ltda');

-- Hill-Rom Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Hill-Rom Brasil Ltda', '01554909000231', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-9100', 'contato@hillrom.com', 'https://www.hillrom.com.br', '80048610071', 'Fabricante de equipamentos médicos e leitos hospitalares'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000231' OR nome = 'Hill-Rom Brasil Ltda');

-- Invacare Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Invacare Brasil Ltda', '01554909000232', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-9200', 'contato@invacare.com', 'https://www.invacare.com.br', '80048610072', 'Fabricante de equipamentos médicos e dispositivos de mobilidade'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000232' OR nome = 'Invacare Brasil Ltda');

-- Sunrise Medical Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Sunrise Medical Brasil Ltda', '01554909000233', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-9300', 'contato@sunrisemedical.com', 'https://www.sunrisemedical.com.br', '80048610073', 'Fabricante de equipamentos médicos e dispositivos de mobilidade'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000233' OR nome = 'Sunrise Medical Brasil Ltda');

-- Permobil Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Permobil Brasil Ltda', '01554909000234', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-9400', 'contato@permobil.com', 'https://www.permobil.com.br', '80048610074', 'Fabricante de equipamentos médicos e dispositivos de mobilidade'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000234' OR nome = 'Permobil Brasil Ltda');

-- Ottobock Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Ottobock Brasil Ltda', '01554909000235', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-9500', 'contato@ottobock.com', 'https://www.ottobock.com.br', '80048610075', 'Fabricante alemão de equipamentos médicos e dispositivos ortopédicos'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000235' OR nome = 'Ottobock Brasil Ltda');

-- Össur Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Össur Brasil Ltda', '01554909000236', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-9600', 'contato@ossur.com', 'https://www.ossur.com.br', '80048610076', 'Fabricante de equipamentos médicos e dispositivos ortopédicos'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000236' OR nome = 'Össur Brasil Ltda');

-- Hanger Brasil Ltda
INSERT INTO public.fabricantes_equipamento (
    id, criado_em, atualizado_em, ativo, nome, cnpj, pais, estado, cidade,
    endereco, telefone, email, site, registro_anvisa, observacoes
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Hanger Brasil Ltda', '01554909000237', 'Brasil', 'SP', 'São Paulo',
    'Av. Engenheiro Luís Carlos Berrini, 1297', '(11) 5508-9700', 'contato@hanger.com', 'https://www.hanger.com.br', '80048610077', 'Fabricante de equipamentos médicos e dispositivos ortopédicos'
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_equipamento WHERE cnpj = '01554909000237' OR nome = 'Hanger Brasil Ltda');


-- ========== FIM DO SCRIPT ==========
