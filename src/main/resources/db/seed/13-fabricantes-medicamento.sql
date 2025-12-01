-- Script de Seed: Fabricantes de Medicamentos (Escopo Global)
-- Cria fabricantes reais de medicamentos - dados globais sem tenant
-- Gerado automaticamente pelo script generate_fabricantes_medicamento.py
-- Executado quando app.seed.enabled=true

-- Pfizer do Brasil Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Pfizer do Brasil Ltda', 'Brasil', '{"cnpj": "61080724000147", "telefone": "(11) 2125-2222", "email": "contato@pfizer.com", "site": "https://www.pfizer.com.br", "endereco": "Av. Dr. Cardoso de Melo, 1450, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Pfizer do Brasil Ltda');

-- Novartis Biociências S.A.
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Novartis Biociências S.A.', 'Brasil', '{"cnpj": "61080724000148", "telefone": "(11) 2125-2300", "email": "contato@novartis.com", "site": "https://www.novartis.com.br", "endereco": "Av. Dr. Cardoso de Melo, 1450, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Novartis Biociências S.A.');

-- Roche Farmacêutica Química Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Roche Farmacêutica Química Ltda', 'Brasil', '{"cnpj": "61080724000149", "telefone": "(11) 2125-2400", "email": "contato@roche.com", "site": "https://www.roche.com.br", "endereco": "Av. Dr. Cardoso de Melo, 1450, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Roche Farmacêutica Química Ltda');

-- Sanofi-Aventis Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Sanofi-Aventis Farmacêutica Ltda', 'Brasil', '{"cnpj": "61080724000150", "telefone": "(11) 2125-2500", "email": "contato@sanofi.com", "site": "https://www.sanofi.com.br", "endereco": "Av. Dr. Cardoso de Melo, 1450, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Sanofi-Aventis Farmacêutica Ltda');

-- GlaxoSmithKline Brasil Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'GlaxoSmithKline Brasil Ltda', 'Brasil', '{"cnpj": "61080724000151", "telefone": "(11) 2125-2600", "email": "contato@gsk.com", "site": "https://www.gsk.com.br", "endereco": "Av. Dr. Cardoso de Melo, 1450, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'GlaxoSmithKline Brasil Ltda');

-- Merck Sharp & Dohme Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Merck Sharp & Dohme Farmacêutica Ltda', 'Brasil', '{"cnpj": "61080724000152", "telefone": "(11) 2125-2700", "email": "contato@merck.com", "site": "https://www.merck.com.br", "endereco": "Av. Dr. Cardoso de Melo, 1450, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Merck Sharp & Dohme Farmacêutica Ltda');

-- AstraZeneca do Brasil Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'AstraZeneca do Brasil Ltda', 'Brasil', '{"cnpj": "61080724000153", "telefone": "(11) 2125-2800", "email": "contato@astrazeneca.com", "site": "https://www.astrazeneca.com.br", "endereco": "Av. Dr. Cardoso de Melo, 1450, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'AstraZeneca do Brasil Ltda');

-- Bayer S.A.
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Bayer S.A.', 'Brasil', '{"cnpj": "61080724000154", "telefone": "(11) 2125-2900", "email": "contato@bayer.com", "site": "https://www.bayer.com.br", "endereco": "Av. Dr. Cardoso de Melo, 1450, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Bayer S.A.');

-- Boehringer Ingelheim do Brasil Química e Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Boehringer Ingelheim do Brasil Química e Farmacêutica Ltda', 'Brasil', '{"cnpj": "61080724000155", "telefone": "(11) 2125-3000", "email": "contato@boehringer.com", "site": "https://www.boehringer.com.br", "endereco": "Av. Dr. Cardoso de Melo, 1450, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Boehringer Ingelheim do Brasil Química e Farmacêutica Ltda');

-- Eli Lilly do Brasil Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Eli Lilly do Brasil Ltda', 'Brasil', '{"cnpj": "61080724000156", "telefone": "(11) 2125-3100", "email": "contato@lilly.com", "site": "https://www.lilly.com.br", "endereco": "Av. Dr. Cardoso de Melo, 1450, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Eli Lilly do Brasil Ltda');

-- Takeda Farmacêutica do Brasil Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Takeda Farmacêutica do Brasil Ltda', 'Brasil', '{"cnpj": "61080724000157", "telefone": "(11) 2125-3200", "email": "contato@takeda.com", "site": "https://www.takeda.com.br", "endereco": "Av. Dr. Cardoso de Melo, 1450, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Takeda Farmacêutica do Brasil Ltda');

-- AbbVie Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'AbbVie Farmacêutica Ltda', 'Brasil', '{"cnpj": "61080724000158", "telefone": "(11) 2125-3300", "email": "contato@abbvie.com", "site": "https://www.abbvie.com.br", "endereco": "Av. Dr. Cardoso de Melo, 1450, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'AbbVie Farmacêutica Ltda');

-- Janssen-Cilag Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Janssen-Cilag Farmacêutica Ltda', 'Brasil', '{"cnpj": "61080724000159", "telefone": "(11) 2125-3400", "email": "contato@janssen.com", "site": "https://www.janssen.com.br", "endereco": "Av. Dr. Cardoso de Melo, 1450, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Janssen-Cilag Farmacêutica Ltda');

-- Bristol-Myers Squibb Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Bristol-Myers Squibb Farmacêutica Ltda', 'Brasil', '{"cnpj": "61080724000160", "telefone": "(11) 2125-3500", "email": "contato@bms.com", "site": "https://www.bms.com.br", "endereco": "Av. Dr. Cardoso de Melo, 1450, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Bristol-Myers Squibb Farmacêutica Ltda');

-- Amgen Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Amgen Farmacêutica Ltda', 'Brasil', '{"cnpj": "61080724000161", "telefone": "(11) 2125-3600", "email": "contato@amgen.com", "site": "https://www.amgen.com.br", "endereco": "Av. Dr. Cardoso de Melo, 1450, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Amgen Farmacêutica Ltda');

-- Gilead Sciences Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Gilead Sciences Farmacêutica Ltda', 'Brasil', '{"cnpj": "61080724000162", "telefone": "(11) 2125-3700", "email": "contato@gilead.com", "site": "https://www.gilead.com.br", "endereco": "Av. Dr. Cardoso de Melo, 1450, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Gilead Sciences Farmacêutica Ltda');

-- Biogen Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Biogen Farmacêutica Ltda', 'Brasil', '{"cnpj": "61080724000163", "telefone": "(11) 2125-3800", "email": "contato@biogen.com", "site": "https://www.biogen.com.br", "endereco": "Av. Dr. Cardoso de Melo, 1450, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Biogen Farmacêutica Ltda');

-- Celgene Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Celgene Farmacêutica Ltda', 'Brasil', '{"cnpj": "61080724000164", "telefone": "(11) 2125-3900", "email": "contato@celgene.com", "site": "https://www.celgene.com.br", "endereco": "Av. Dr. Cardoso de Melo, 1450, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Celgene Farmacêutica Ltda');

-- Regeneron Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Regeneron Farmacêutica Ltda', 'Brasil', '{"cnpj": "61080724000165", "telefone": "(11) 2125-4000", "email": "contato@regeneron.com", "site": "https://www.regeneron.com.br", "endereco": "Av. Dr. Cardoso de Melo, 1450, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Regeneron Farmacêutica Ltda');

-- Vertex Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Vertex Farmacêutica Ltda', 'Brasil', '{"cnpj": "61080724000166", "telefone": "(11) 2125-4100", "email": "contato@vertex.com", "site": "https://www.vertex.com.br", "endereco": "Av. Dr. Cardoso de Melo, 1450, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Vertex Farmacêutica Ltda');

-- EMS S.A.
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'EMS S.A.', 'Brasil', '{"cnpj": "05115972000109", "telefone": "(11) 3034-3400", "email": "contato@ems.com.br", "site": "https://www.ems.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'EMS S.A.');

-- Aché Laboratórios Farmacêuticos S.A.
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Aché Laboratórios Farmacêuticos S.A.', 'Brasil', '{"cnpj": "05115972000110", "telefone": "(11) 3034-3500", "email": "contato@ache.com.br", "site": "https://www.ache.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Aché Laboratórios Farmacêuticos S.A.');

-- Eurofarma Laboratórios S.A.
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Eurofarma Laboratórios S.A.', 'Brasil', '{"cnpj": "05115972000111", "telefone": "(11) 3034-3600", "email": "contato@eurofarma.com.br", "site": "https://www.eurofarma.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Eurofarma Laboratórios S.A.');

-- Cristália Produtos Químicos Farmacêuticos Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Cristália Produtos Químicos Farmacêuticos Ltda', 'Brasil', '{"cnpj": "05115972000112", "telefone": "(11) 3034-3700", "email": "contato@cristalia.com.br", "site": "https://www.cristalia.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Cristália Produtos Químicos Farmacêuticos Ltda');

-- Medley Indústria Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Medley Indústria Farmacêutica Ltda', 'Brasil', '{"cnpj": "05115972000113", "telefone": "(11) 3034-3800", "email": "contato@medley.com.br", "site": "https://www.medley.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Medley Indústria Farmacêutica Ltda');

-- Biolab Sanus Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Biolab Sanus Farmacêutica Ltda', 'Brasil', '{"cnpj": "05115972000114", "telefone": "(11) 3034-3900", "email": "contato@biolab.com.br", "site": "https://www.biolab.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Biolab Sanus Farmacêutica Ltda');

-- Germed Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Germed Farmacêutica Ltda', 'Brasil', '{"cnpj": "05115972000115", "telefone": "(11) 3034-4000", "email": "contato@germed.com.br", "site": "https://www.germed.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Germed Farmacêutica Ltda');

-- Sandoz do Brasil Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Sandoz do Brasil Ltda', 'Brasil', '{"cnpj": "05115972000116", "telefone": "(11) 3034-4100", "email": "contato@sandoz.com.br", "site": "https://www.sandoz.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Sandoz do Brasil Ltda');

-- Teva Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Teva Farmacêutica Ltda', 'Brasil', '{"cnpj": "05115972000117", "telefone": "(11) 3034-4200", "email": "contato@teva.com.br", "site": "https://www.teva.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Teva Farmacêutica Ltda');

-- Mylan Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Mylan Farmacêutica Ltda', 'Brasil', '{"cnpj": "05115972000118", "telefone": "(11) 3034-4300", "email": "contato@mylan.com.br", "site": "https://www.mylan.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Mylan Farmacêutica Ltda');

-- União Química Farmacêutica Nacional S.A.
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'União Química Farmacêutica Nacional S.A.', 'Brasil', '{"cnpj": "05115972000119", "telefone": "(11) 3034-4400", "email": "contato@uniaoquimica.com.br", "site": "https://www.uniaoquimica.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'União Química Farmacêutica Nacional S.A.');

-- Neo Química Indústria Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Neo Química Indústria Farmacêutica Ltda', 'Brasil', '{"cnpj": "05115972000120", "telefone": "(11) 3034-4500", "email": "contato@neoquimica.com.br", "site": "https://www.neoquimica.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Neo Química Indústria Farmacêutica Ltda');

-- Prati-Donaduzzi Indústria Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Prati-Donaduzzi Indústria Farmacêutica Ltda', 'Brasil', '{"cnpj": "05115972000121", "telefone": "(11) 3034-4600", "email": "contato@prati.com.br", "site": "https://www.prati.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Prati-Donaduzzi Indústria Farmacêutica Ltda');

-- Blau Farmacêutica S.A.
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Blau Farmacêutica S.A.', 'Brasil', '{"cnpj": "05115972000122", "telefone": "(11) 3034-4700", "email": "contato@blau.com.br", "site": "https://www.blau.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Blau Farmacêutica S.A.');

-- Libbs Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Libbs Farmacêutica Ltda', 'Brasil', '{"cnpj": "05115972000123", "telefone": "(11) 3034-4800", "email": "contato@libbs.com.br", "site": "https://www.libbs.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Libbs Farmacêutica Ltda');

-- Biosintética Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Biosintética Farmacêutica Ltda', 'Brasil', '{"cnpj": "05115972000124", "telefone": "(11) 3034-4900", "email": "contato@biosintetica.com.br", "site": "https://www.biosintetica.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Biosintética Farmacêutica Ltda');

-- Farmoquímica S.A.
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Farmoquímica S.A.', 'Brasil', '{"cnpj": "05115972000125", "telefone": "(11) 3034-5000", "email": "contato@farmoquimica.com.br", "site": "https://www.farmoquimica.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Farmoquímica S.A.');

-- Belfar Indústria Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Belfar Indústria Farmacêutica Ltda', 'Brasil', '{"cnpj": "05115972000126", "telefone": "(11) 3034-5100", "email": "contato@belfar.com.br", "site": "https://www.belfar.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Belfar Indústria Farmacêutica Ltda');

-- Cimed Indústria de Medicamentos Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Cimed Indústria de Medicamentos Ltda', 'Brasil', '{"cnpj": "05115972000127", "telefone": "(11) 3034-5200", "email": "contato@cimed.com.br", "site": "https://www.cimed.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Cimed Indústria de Medicamentos Ltda');

-- Germed Pharma Indústria Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Germed Pharma Indústria Farmacêutica Ltda', 'Brasil', '{"cnpj": "05115972000128", "telefone": "(11) 3034-5300", "email": "contato@germedpharma.com.br", "site": "https://www.germedpharma.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Germed Pharma Indústria Farmacêutica Ltda');

-- Geolab Indústria Farmacêutica S.A.
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Geolab Indústria Farmacêutica S.A.', 'Brasil', '{"cnpj": "05115972000129", "telefone": "(11) 3034-5400", "email": "contato@geolab.com.br", "site": "https://www.geolab.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Geolab Indústria Farmacêutica S.A.');

-- Herbarium Laboratório Botânico Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Herbarium Laboratório Botânico Ltda', 'Brasil', '{"cnpj": "05115972000130", "telefone": "(11) 3034-5500", "email": "contato@herbarium.com.br", "site": "https://www.herbarium.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Herbarium Laboratório Botânico Ltda');

-- Hypermarcas S.A.
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Hypermarcas S.A.', 'Brasil', '{"cnpj": "05115972000131", "telefone": "(11) 3034-5600", "email": "contato@hypermarcas.com.br", "site": "https://www.hypermarcas.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Hypermarcas S.A.');

-- Isofarma Indústria Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Isofarma Indústria Farmacêutica Ltda', 'Brasil', '{"cnpj": "05115972000132", "telefone": "(11) 3034-5700", "email": "contato@isofarma.com.br", "site": "https://www.isofarma.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Isofarma Indústria Farmacêutica Ltda');

-- Janssen-Cilag Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Janssen-Cilag Farmacêutica Ltda', 'Brasil', '{"cnpj": "05115972000133", "telefone": "(11) 3034-5800", "email": "contato@janssen.com.br", "site": "https://www.janssen.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Janssen-Cilag Farmacêutica Ltda');

-- Kley Hertz Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Kley Hertz Farmacêutica Ltda', 'Brasil', '{"cnpj": "05115972000134", "telefone": "(11) 3034-5900", "email": "contato@kleyhertz.com.br", "site": "https://www.kleyhertz.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Kley Hertz Farmacêutica Ltda');

-- Laboratório Teuto Brasileiro S.A.
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Laboratório Teuto Brasileiro S.A.', 'Brasil', '{"cnpj": "05115972000135", "telefone": "(11) 3034-6000", "email": "contato@teuto.com.br", "site": "https://www.teuto.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Laboratório Teuto Brasileiro S.A.');

-- Legrand Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Legrand Farmacêutica Ltda', 'Brasil', '{"cnpj": "05115972000136", "telefone": "(11) 3034-6100", "email": "contato@legrand.com.br", "site": "https://www.legrand.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Legrand Farmacêutica Ltda');

-- Mantecorp Indústria Química e Farmacêutica S.A.
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Mantecorp Indústria Química e Farmacêutica S.A.', 'Brasil', '{"cnpj": "05115972000137", "telefone": "(11) 3034-6200", "email": "contato@mantecorp.com.br", "site": "https://www.mantecorp.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Mantecorp Indústria Química e Farmacêutica S.A.');

-- Multilab Indústria e Comércio de Produtos Farmacêuticos Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Multilab Indústria e Comércio de Produtos Farmacêuticos Ltda', 'Brasil', '{"cnpj": "05115972000138", "telefone": "(11) 3034-6300", "email": "contato@multilab.com.br", "site": "https://www.multilab.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Multilab Indústria e Comércio de Produtos Farmacêuticos Ltda');

-- Natulab Laboratórios Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Natulab Laboratórios Ltda', 'Brasil', '{"cnpj": "05115972000139", "telefone": "(11) 3034-6400", "email": "contato@natulab.com.br", "site": "https://www.natulab.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Natulab Laboratórios Ltda');

-- Novamed Indústria Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Novamed Indústria Farmacêutica Ltda', 'Brasil', '{"cnpj": "05115972000140", "telefone": "(11) 3034-6500", "email": "contato@novamed.com.br", "site": "https://www.novamed.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Novamed Indústria Farmacêutica Ltda');

-- Orygen Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Orygen Farmacêutica Ltda', 'Brasil', '{"cnpj": "05115972000141", "telefone": "(11) 3034-6600", "email": "contato@orygen.com.br", "site": "https://www.orygen.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Orygen Farmacêutica Ltda');

-- Pasteur Indústria Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Pasteur Indústria Farmacêutica Ltda', 'Brasil', '{"cnpj": "05115972000142", "telefone": "(11) 3034-6700", "email": "contato@pasteur.com.br", "site": "https://www.pasteur.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Pasteur Indústria Farmacêutica Ltda');

-- Prati-Donaduzzi Indústria Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Prati-Donaduzzi Indústria Farmacêutica Ltda', 'Brasil', '{"cnpj": "05115972000143", "telefone": "(11) 3034-6800", "email": "contato@prati.com.br", "site": "https://www.prati.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Prati-Donaduzzi Indústria Farmacêutica Ltda');

-- Procter & Gamble do Brasil Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Procter & Gamble do Brasil Ltda', 'Brasil', '{"cnpj": "05115972000144", "telefone": "(11) 3034-6900", "email": "contato@pg.com.br", "site": "https://www.pg.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Procter & Gamble do Brasil Ltda');

-- Ranbaxy Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Ranbaxy Farmacêutica Ltda', 'Brasil', '{"cnpj": "05115972000145", "telefone": "(11) 3034-7000", "email": "contato@ranbaxy.com.br", "site": "https://www.ranbaxy.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Ranbaxy Farmacêutica Ltda');

-- Sanofi Medley Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Sanofi Medley Farmacêutica Ltda', 'Brasil', '{"cnpj": "05115972000146", "telefone": "(11) 3034-7100", "email": "contato@sanofimedley.com.br", "site": "https://www.sanofimedley.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Sanofi Medley Farmacêutica Ltda');

-- Schering-Plough Indústria Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Schering-Plough Indústria Farmacêutica Ltda', 'Brasil', '{"cnpj": "05115972000147", "telefone": "(11) 3034-7200", "email": "contato@schering.com.br", "site": "https://www.schering.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Schering-Plough Indústria Farmacêutica Ltda');

-- Sigma Pharma Indústria Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Sigma Pharma Indústria Farmacêutica Ltda', 'Brasil', '{"cnpj": "05115972000148", "telefone": "(11) 3034-7300", "email": "contato@sigmapharma.com.br", "site": "https://www.sigmapharma.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Sigma Pharma Indústria Farmacêutica Ltda');

-- Takeda Farmacêutica do Brasil Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Takeda Farmacêutica do Brasil Ltda', 'Brasil', '{"cnpj": "05115972000149", "telefone": "(11) 3034-7400", "email": "contato@takeda.com.br", "site": "https://www.takeda.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Takeda Farmacêutica do Brasil Ltda');

-- Torrent do Brasil Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Torrent do Brasil Ltda', 'Brasil', '{"cnpj": "05115972000150", "telefone": "(11) 3034-7500", "email": "contato@torrent.com.br", "site": "https://www.torrent.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Torrent do Brasil Ltda');

-- União Química Farmacêutica Nacional S.A.
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'União Química Farmacêutica Nacional S.A.', 'Brasil', '{"cnpj": "05115972000151", "telefone": "(11) 3034-7600", "email": "contato@uniaoquimica.com.br", "site": "https://www.uniaoquimica.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'União Química Farmacêutica Nacional S.A.');

-- Valeant Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Valeant Farmacêutica Ltda', 'Brasil', '{"cnpj": "05115972000152", "telefone": "(11) 3034-7700", "email": "contato@valeant.com.br", "site": "https://www.valeant.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Valeant Farmacêutica Ltda');

-- Zydus Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Zydus Farmacêutica Ltda', 'Brasil', '{"cnpj": "05115972000153", "telefone": "(11) 3034-7800", "email": "contato@zydus.com.br", "site": "https://www.zydus.com.br", "endereco": "Rua Gomes de Carvalho, 1996, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Zydus Farmacêutica Ltda');

-- Allergan Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Allergan Farmacêutica Ltda', 'Brasil', '{"cnpj": "61080724000167", "telefone": "(11) 2125-4200", "email": "contato@allergan.com", "site": "https://www.allergan.com.br", "endereco": "Av. Dr. Cardoso de Melo, 1450, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Allergan Farmacêutica Ltda');

-- Actavis Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Actavis Farmacêutica Ltda', 'Brasil', '{"cnpj": "61080724000168", "telefone": "(11) 2125-4300", "email": "contato@actavis.com", "site": "https://www.actavis.com.br", "endereco": "Av. Dr. Cardoso de Melo, 1450, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Actavis Farmacêutica Ltda');

-- Alcon Laboratórios do Brasil S.A.
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Alcon Laboratórios do Brasil S.A.', 'Brasil', '{"cnpj": "61080724000169", "telefone": "(11) 2125-4400", "email": "contato@alcon.com", "site": "https://www.alcon.com.br", "endereco": "Av. Dr. Cardoso de Melo, 1450, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Alcon Laboratórios do Brasil S.A.');

-- Bausch & Lomb Indústria e Comércio de Produtos Farmacêuticos Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Bausch & Lomb Indústria e Comércio de Produtos Farmacêuticos Ltda', 'Brasil', '{"cnpj": "61080724000170", "telefone": "(11) 2125-4500", "email": "contato@bausch.com", "site": "https://www.bausch.com.br", "endereco": "Av. Dr. Cardoso de Melo, 1450, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Bausch & Lomb Indústria e Comércio de Produtos Farmacêuticos Ltda');

-- Cipla Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Cipla Farmacêutica Ltda', 'Brasil', '{"cnpj": "61080724000171", "telefone": "(11) 2125-4600", "email": "contato@cipla.com", "site": "https://www.cipla.com.br", "endereco": "Av. Dr. Cardoso de Melo, 1450, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Cipla Farmacêutica Ltda');

-- Dr. Reddy's Laboratories Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Dr. Reddy''s Laboratories Farmacêutica Ltda', 'Brasil', '{"cnpj": "61080724000172", "telefone": "(11) 2125-4700", "email": "contato@drreddys.com", "site": "https://www.drreddys.com.br", "endereco": "Av. Dr. Cardoso de Melo, 1450, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Dr. Reddy''s Laboratories Farmacêutica Ltda');

-- Lupin Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Lupin Farmacêutica Ltda', 'Brasil', '{"cnpj": "61080724000173", "telefone": "(11) 2125-4800", "email": "contato@lupin.com", "site": "https://www.lupin.com.br", "endereco": "Av. Dr. Cardoso de Melo, 1450, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Lupin Farmacêutica Ltda');

-- Sun Pharma Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Sun Pharma Farmacêutica Ltda', 'Brasil', '{"cnpj": "61080724000174", "telefone": "(11) 2125-4900", "email": "contato@sunpharma.com", "site": "https://www.sunpharma.com.br", "endereco": "Av. Dr. Cardoso de Melo, 1450, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Sun Pharma Farmacêutica Ltda');

-- Wockhardt Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Wockhardt Farmacêutica Ltda', 'Brasil', '{"cnpj": "61080724000175", "telefone": "(11) 2125-5000", "email": "contato@wockhardt.com", "site": "https://www.wockhardt.com.br", "endereco": "Av. Dr. Cardoso de Melo, 1450, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Wockhardt Farmacêutica Ltda');

-- Zentiva Farmacêutica Ltda
INSERT INTO public.fabricantes_medicamento (
    id, criado_em, atualizado_em, ativo, nome, pais, contato_json
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Zentiva Farmacêutica Ltda', 'Brasil', '{"cnpj": "61080724000176", "telefone": "(11) 2125-5100", "email": "contato@zentiva.com", "site": "https://www.zentiva.com.br", "endereco": "Av. Dr. Cardoso de Melo, 1450, São Paulo - SP"}'::jsonb
WHERE NOT EXISTS (SELECT 1 FROM public.fabricantes_medicamento WHERE nome = 'Zentiva Farmacêutica Ltda');


-- ========== FIM DO SCRIPT ==========
