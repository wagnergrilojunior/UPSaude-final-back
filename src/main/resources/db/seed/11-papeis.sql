-- Script de Seed: Papéis (Escopo Global)
-- Cria catálogo de papéis/perfis do sistema - dados globais sem tenant
-- Gerado automaticamente pelo script generate_papeis.py
-- Executado quando app.seed.enabled=true

-- Administrador
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Administrador', 'admin', 'Acesso total ao sistema. Pode gerenciar todos os módulos, usuários, configurações e dados.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'admin' OR nome = 'Administrador');

-- Administrador de Sistema
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Administrador de Sistema', 'administrador-sistema', 'Administrador com permissões de configuração e manutenção do sistema.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'administrador-sistema' OR nome = 'Administrador de Sistema');

-- Gerente Geral
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Gerente Geral', 'gerente-geral', 'Gerente com acesso a todas as áreas do estabelecimento de saúde.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'gerente-geral' OR nome = 'Gerente Geral');

-- Coordenador Administrativo
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Coordenador Administrativo', 'coordenador-administrativo', 'Coordenador responsável pela gestão administrativa do estabelecimento.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'coordenador-administrativo' OR nome = 'Coordenador Administrativo');

-- Secretário de Saúde
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Secretário de Saúde', 'secretario-saude', 'Secretário municipal de saúde com acesso a todos os estabelecimentos.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'secretario-saude' OR nome = 'Secretário de Saúde');

-- Diretor de Estabelecimento
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Diretor de Estabelecimento', 'diretor-estabelecimento', 'Diretor responsável pela gestão de um estabelecimento de saúde.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'diretor-estabelecimento' OR nome = 'Diretor de Estabelecimento');

-- Coordenador de Gestão
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Coordenador de Gestão', 'coordenador-gestao', 'Coordenador responsável pela gestão operacional do estabelecimento.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'coordenador-gestao' OR nome = 'Coordenador de Gestão');

-- Médico
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Médico', 'medico', 'Médico com permissão para realizar consultas, prescrições e procedimentos médicos.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'medico' OR nome = 'Médico');

-- Médico Clínico Geral
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Médico Clínico Geral', 'medico-clinico-geral', 'Médico especialista em clínica médica geral.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'medico-clinico-geral' OR nome = 'Médico Clínico Geral');

-- Médico de Família
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Médico de Família', 'medico-familia', 'Médico especialista em medicina de família e comunidade (ESF).', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'medico-familia' OR nome = 'Médico de Família');

-- Médico Pediatra
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Médico Pediatra', 'medico-pediatra', 'Médico especialista em pediatria.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'medico-pediatra' OR nome = 'Médico Pediatra');

-- Médico Ginecologista
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Médico Ginecologista', 'medico-ginecologista', 'Médico especialista em ginecologia e obstetrícia.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'medico-ginecologista' OR nome = 'Médico Ginecologista');

-- Médico Cardiologista
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Médico Cardiologista', 'medico-cardiologista', 'Médico especialista em cardiologia.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'medico-cardiologista' OR nome = 'Médico Cardiologista');

-- Médico Ortopedista
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Médico Ortopedista', 'medico-ortopedista', 'Médico especialista em ortopedia.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'medico-ortopedista' OR nome = 'Médico Ortopedista');

-- Médico Dermatologista
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Médico Dermatologista', 'medico-dermatologista', 'Médico especialista em dermatologia.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'medico-dermatologista' OR nome = 'Médico Dermatologista');

-- Médico Neurologista
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Médico Neurologista', 'medico-neurologista', 'Médico especialista em neurologia.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'medico-neurologista' OR nome = 'Médico Neurologista');

-- Médico Psiquiatra
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Médico Psiquiatra', 'medico-psiquiatra', 'Médico especialista em psiquiatria.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'medico-psiquiatra' OR nome = 'Médico Psiquiatra');

-- Médico Oftalmologista
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Médico Oftalmologista', 'medico-oftalmologista', 'Médico especialista em oftalmologia.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'medico-oftalmologista' OR nome = 'Médico Oftalmologista');

-- Médico Otorrinolaringologista
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Médico Otorrinolaringologista', 'medico-otorrino', 'Médico especialista em otorrinolaringologia.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'medico-otorrino' OR nome = 'Médico Otorrinolaringologista');

-- Médico Urologista
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Médico Urologista', 'medico-urologista', 'Médico especialista em urologia.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'medico-urologista' OR nome = 'Médico Urologista');

-- Médico Endocrinologista
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Médico Endocrinologista', 'medico-endocrinologista', 'Médico especialista em endocrinologia.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'medico-endocrinologista' OR nome = 'Médico Endocrinologista');

-- Médico Gastroenterologista
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Médico Gastroenterologista', 'medico-gastroenterologista', 'Médico especialista em gastroenterologia.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'medico-gastroenterologista' OR nome = 'Médico Gastroenterologista');

-- Médico Pneumologista
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Médico Pneumologista', 'medico-pneumologista', 'Médico especialista em pneumologia.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'medico-pneumologista' OR nome = 'Médico Pneumologista');

-- Médico Infectologista
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Médico Infectologista', 'medico-infectologista', 'Médico especialista em infectologia.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'medico-infectologista' OR nome = 'Médico Infectologista');

-- Médico Reumatologista
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Médico Reumatologista', 'medico-reumatologista', 'Médico especialista em reumatologia.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'medico-reumatologista' OR nome = 'Médico Reumatologista');

-- Médico Anestesiologista
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Médico Anestesiologista', 'medico-anestesiologista', 'Médico especialista em anestesiologia.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'medico-anestesiologista' OR nome = 'Médico Anestesiologista');

-- Médico Cirurgião Geral
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Médico Cirurgião Geral', 'medico-cirurgiao-geral', 'Médico especialista em cirurgia geral.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'medico-cirurgiao-geral' OR nome = 'Médico Cirurgião Geral');

-- Médico Intensivista
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Médico Intensivista', 'medico-intensivista', 'Médico especialista em medicina intensiva (UTI).', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'medico-intensivista' OR nome = 'Médico Intensivista');

-- Médico de Emergência
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Médico de Emergência', 'medico-emergencia', 'Médico especialista em medicina de emergência.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'medico-emergencia' OR nome = 'Médico de Emergência');

-- Médico Radiologista
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Médico Radiologista', 'medico-radiologista', 'Médico especialista em radiologia e diagnóstico por imagem.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'medico-radiologista' OR nome = 'Médico Radiologista');

-- Médico Patologista
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Médico Patologista', 'medico-patologista', 'Médico especialista em patologia.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'medico-patologista' OR nome = 'Médico Patologista');

-- Médico Legista
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Médico Legista', 'medico-legista', 'Médico especialista em medicina legal.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'medico-legista' OR nome = 'Médico Legista');

-- Médico do Trabalho
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Médico do Trabalho', 'medico-trabalho', 'Médico especialista em medicina do trabalho.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'medico-trabalho' OR nome = 'Médico do Trabalho');

-- Médico Residente
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Médico Residente', 'medico-residente', 'Médico em programa de residência médica.', false
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'medico-residente' OR nome = 'Médico Residente');

-- Médico Plantonista
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Médico Plantonista', 'medico-plantonista', 'Médico em plantão médico.', false
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'medico-plantonista' OR nome = 'Médico Plantonista');

-- Enfermeiro
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Enfermeiro', 'enfermeiro', 'Enfermeiro com permissão para realizar procedimentos de enfermagem e coordenar equipes.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'enfermeiro' OR nome = 'Enfermeiro');

-- Enfermeiro Coordenador
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Enfermeiro Coordenador', 'enfermeiro-coordenador', 'Enfermeiro responsável pela coordenação da equipe de enfermagem.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'enfermeiro-coordenador' OR nome = 'Enfermeiro Coordenador');

-- Enfermeiro de Família
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Enfermeiro de Família', 'enfermeiro-familia', 'Enfermeiro especialista em saúde da família (ESF).', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'enfermeiro-familia' OR nome = 'Enfermeiro de Família');

-- Enfermeiro Obstetra
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Enfermeiro Obstetra', 'enfermeiro-obstetra', 'Enfermeiro especialista em obstetrícia.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'enfermeiro-obstetra' OR nome = 'Enfermeiro Obstetra');

-- Enfermeiro Intensivista
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Enfermeiro Intensivista', 'enfermeiro-intensivista', 'Enfermeiro especialista em cuidados intensivos.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'enfermeiro-intensivista' OR nome = 'Enfermeiro Intensivista');

-- Enfermeiro de Emergência
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Enfermeiro de Emergência', 'enfermeiro-emergencia', 'Enfermeiro especialista em emergência e urgência.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'enfermeiro-emergencia' OR nome = 'Enfermeiro de Emergência');

-- Enfermeiro de Saúde Mental
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Enfermeiro de Saúde Mental', 'enfermeiro-saude-mental', 'Enfermeiro especialista em saúde mental.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'enfermeiro-saude-mental' OR nome = 'Enfermeiro de Saúde Mental');

-- Enfermeiro de Saúde Pública
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Enfermeiro de Saúde Pública', 'enfermeiro-saude-publica', 'Enfermeiro especialista em saúde pública.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'enfermeiro-saude-publica' OR nome = 'Enfermeiro de Saúde Pública');

-- Enfermeiro Auditor
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Enfermeiro Auditor', 'enfermeiro-auditor', 'Enfermeiro responsável por auditoria em saúde.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'enfermeiro-auditor' OR nome = 'Enfermeiro Auditor');

-- Enfermeiro Educador
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Enfermeiro Educador', 'enfermeiro-educador', 'Enfermeiro responsável por educação em saúde.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'enfermeiro-educador' OR nome = 'Enfermeiro Educador');

-- Técnico de Enfermagem
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Técnico de Enfermagem', 'tecnico-enfermagem', 'Técnico de enfermagem com permissão para realizar procedimentos técnicos.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'tecnico-enfermagem' OR nome = 'Técnico de Enfermagem');

-- Auxiliar de Enfermagem
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Auxiliar de Enfermagem', 'auxiliar-enfermagem', 'Auxiliar de enfermagem com permissão para realizar cuidados básicos.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'auxiliar-enfermagem' OR nome = 'Auxiliar de Enfermagem');

-- Dentista
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Dentista', 'dentista', 'Dentista com permissão para realizar procedimentos odontológicos.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'dentista' OR nome = 'Dentista');

-- Dentista Clínico Geral
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Dentista Clínico Geral', 'dentista-clinico-geral', 'Dentista especialista em clínica geral.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'dentista-clinico-geral' OR nome = 'Dentista Clínico Geral');

-- Dentista de Família
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Dentista de Família', 'dentista-familia', 'Dentista especialista em saúde da família.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'dentista-familia' OR nome = 'Dentista de Família');

-- Dentista Pediatra
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Dentista Pediatra', 'dentista-pediatra', 'Dentista especialista em odontopediatria.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'dentista-pediatra' OR nome = 'Dentista Pediatra');

-- Dentista Endodontista
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Dentista Endodontista', 'dentista-endodontista', 'Dentista especialista em endodontia.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'dentista-endodontista' OR nome = 'Dentista Endodontista');

-- Dentista Periodontista
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Dentista Periodontista', 'dentista-periodontista', 'Dentista especialista em periodontia.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'dentista-periodontista' OR nome = 'Dentista Periodontista');

-- Dentista Ortodontista
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Dentista Ortodontista', 'dentista-ortodontista', 'Dentista especialista em ortodontia.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'dentista-ortodontista' OR nome = 'Dentista Ortodontista');

-- Dentista Cirurgião Bucomaxilofacial
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Dentista Cirurgião Bucomaxilofacial', 'dentista-cirurgiao', 'Dentista especialista em cirurgia bucomaxilofacial.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'dentista-cirurgiao' OR nome = 'Dentista Cirurgião Bucomaxilofacial');

-- Auxiliar de Saúde Bucal
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Auxiliar de Saúde Bucal', 'auxiliar-saude-bucal', 'Auxiliar de saúde bucal com permissão para auxiliar em procedimentos.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'auxiliar-saude-bucal' OR nome = 'Auxiliar de Saúde Bucal');

-- Técnico em Saúde Bucal
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Técnico em Saúde Bucal', 'tecnico-saude-bucal', 'Técnico em saúde bucal com permissão para realizar procedimentos técnicos.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'tecnico-saude-bucal' OR nome = 'Técnico em Saúde Bucal');

-- Farmacêutico
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Farmacêutico', 'farmaceutico', 'Farmacêutico com permissão para gerenciar medicamentos e prescrições.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'farmaceutico' OR nome = 'Farmacêutico');

-- Farmacêutico Clínico
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Farmacêutico Clínico', 'farmaceutico-clinico', 'Farmacêutico especialista em farmácia clínica.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'farmaceutico-clinico' OR nome = 'Farmacêutico Clínico');

-- Farmacêutico Hospitalar
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Farmacêutico Hospitalar', 'farmaceutico-hospitalar', 'Farmacêutico especialista em farmácia hospitalar.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'farmaceutico-hospitalar' OR nome = 'Farmacêutico Hospitalar');

-- Farmacêutico de Saúde Pública
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Farmacêutico de Saúde Pública', 'farmaceutico-saude-publica', 'Farmacêutico especialista em saúde pública.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'farmaceutico-saude-publica' OR nome = 'Farmacêutico de Saúde Pública');

-- Auxiliar de Farmácia
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Auxiliar de Farmácia', 'auxiliar-farmacia', 'Auxiliar de farmácia com permissão para auxiliar em dispensação.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'auxiliar-farmacia' OR nome = 'Auxiliar de Farmácia');

-- Técnico em Farmácia
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Técnico em Farmácia', 'tecnico-farmacia', 'Técnico em farmácia com permissão para realizar procedimentos técnicos.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'tecnico-farmacia' OR nome = 'Técnico em Farmácia');

-- Nutricionista
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Nutricionista', 'nutricionista', 'Nutricionista com permissão para realizar avaliações nutricionais e prescrições dietéticas.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'nutricionista' OR nome = 'Nutricionista');

-- Nutricionista Clínico
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Nutricionista Clínico', 'nutricionista-clinico', 'Nutricionista especialista em nutrição clínica.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'nutricionista-clinico' OR nome = 'Nutricionista Clínico');

-- Nutricionista de Saúde Pública
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Nutricionista de Saúde Pública', 'nutricionista-saude-publica', 'Nutricionista especialista em saúde pública.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'nutricionista-saude-publica' OR nome = 'Nutricionista de Saúde Pública');

-- Nutricionista Hospitalar
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Nutricionista Hospitalar', 'nutricionista-hospitalar', 'Nutricionista especialista em nutrição hospitalar.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'nutricionista-hospitalar' OR nome = 'Nutricionista Hospitalar');

-- Psicólogo
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Psicólogo', 'psicologo', 'Psicólogo com permissão para realizar atendimentos psicológicos e avaliações.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'psicologo' OR nome = 'Psicólogo');

-- Psicólogo Clínico
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Psicólogo Clínico', 'psicologo-clinico', 'Psicólogo especialista em psicologia clínica.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'psicologo-clinico' OR nome = 'Psicólogo Clínico');

-- Psicólogo de Saúde Pública
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Psicólogo de Saúde Pública', 'psicologo-saude-publica', 'Psicólogo especialista em saúde pública.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'psicologo-saude-publica' OR nome = 'Psicólogo de Saúde Pública');

-- Psicólogo Hospitalar
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Psicólogo Hospitalar', 'psicologo-hospitalar', 'Psicólogo especialista em psicologia hospitalar.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'psicologo-hospitalar' OR nome = 'Psicólogo Hospitalar');

-- Psicólogo de Saúde Mental
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Psicólogo de Saúde Mental', 'psicologo-saude-mental', 'Psicólogo especialista em saúde mental.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'psicologo-saude-mental' OR nome = 'Psicólogo de Saúde Mental');

-- Fisioterapeuta
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Fisioterapeuta', 'fisioterapeuta', 'Fisioterapeuta com permissão para realizar avaliações e tratamentos fisioterapêuticos.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'fisioterapeuta' OR nome = 'Fisioterapeuta');

-- Fisioterapeuta Clínico
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Fisioterapeuta Clínico', 'fisioterapeuta-clinico', 'Fisioterapeuta especialista em fisioterapia clínica.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'fisioterapeuta-clinico' OR nome = 'Fisioterapeuta Clínico');

-- Fisioterapeuta Hospitalar
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Fisioterapeuta Hospitalar', 'fisioterapeuta-hospitalar', 'Fisioterapeuta especialista em fisioterapia hospitalar.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'fisioterapeuta-hospitalar' OR nome = 'Fisioterapeuta Hospitalar');

-- Fisioterapeuta de Saúde Pública
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Fisioterapeuta de Saúde Pública', 'fisioterapeuta-saude-publica', 'Fisioterapeuta especialista em saúde pública.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'fisioterapeuta-saude-publica' OR nome = 'Fisioterapeuta de Saúde Pública');

-- Fonoaudiólogo
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Fonoaudiólogo', 'fonoaudiologo', 'Fonoaudiólogo com permissão para realizar avaliações e tratamentos fonoaudiológicos.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'fonoaudiologo' OR nome = 'Fonoaudiólogo');

-- Fonoaudiólogo Clínico
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Fonoaudiólogo Clínico', 'fonoaudiologo-clinico', 'Fonoaudiólogo especialista em fonoaudiologia clínica.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'fonoaudiologo-clinico' OR nome = 'Fonoaudiólogo Clínico');

-- Fonoaudiólogo Hospitalar
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Fonoaudiólogo Hospitalar', 'fonoaudiologo-hospitalar', 'Fonoaudiólogo especialista em fonoaudiologia hospitalar.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'fonoaudiologo-hospitalar' OR nome = 'Fonoaudiólogo Hospitalar');

-- Terapeuta Ocupacional
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Terapeuta Ocupacional', 'terapeuta-ocupacional', 'Terapeuta ocupacional com permissão para realizar avaliações e tratamentos.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'terapeuta-ocupacional' OR nome = 'Terapeuta Ocupacional');

-- Terapeuta Ocupacional Clínico
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Terapeuta Ocupacional Clínico', 'terapeuta-ocupacional-clinico', 'Terapeuta ocupacional especialista em terapia ocupacional clínica.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'terapeuta-ocupacional-clinico' OR nome = 'Terapeuta Ocupacional Clínico');

-- Terapeuta Ocupacional Hospitalar
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Terapeuta Ocupacional Hospitalar', 'terapeuta-ocupacional-hospitalar', 'Terapeuta ocupacional especialista em terapia ocupacional hospitalar.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'terapeuta-ocupacional-hospitalar' OR nome = 'Terapeuta Ocupacional Hospitalar');

-- Assistente Social
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Assistente Social', 'assistente-social', 'Assistente social com permissão para realizar atendimentos e encaminhamentos sociais.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'assistente-social' OR nome = 'Assistente Social');

-- Assistente Social de Saúde
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Assistente Social de Saúde', 'assistente-social-saude', 'Assistente social especialista em saúde.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'assistente-social-saude' OR nome = 'Assistente Social de Saúde');

-- Assistente Social de Saúde Mental
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Assistente Social de Saúde Mental', 'assistente-social-saude-mental', 'Assistente social especialista em saúde mental.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'assistente-social-saude-mental' OR nome = 'Assistente Social de Saúde Mental');

-- Recepcionista
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Recepcionista', 'recepcionista', 'Recepcionista com permissão para realizar agendamentos e atendimento ao público.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'recepcionista' OR nome = 'Recepcionista');

-- Atendente
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Atendente', 'atendente', 'Atendente com permissão para realizar atendimento ao público.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'atendente' OR nome = 'Atendente');

-- Auxiliar Administrativo
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Auxiliar Administrativo', 'auxiliar-administrativo', 'Auxiliar administrativo com permissão para realizar tarefas administrativas.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'auxiliar-administrativo' OR nome = 'Auxiliar Administrativo');

-- Secretário
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Secretário', 'secretario', 'Secretário com permissão para realizar tarefas administrativas e de apoio.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'secretario' OR nome = 'Secretário');

-- Digitador
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Digitador', 'digitador', 'Digitador com permissão para realizar digitação de dados.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'digitador' OR nome = 'Digitador');

-- Operador de Sistema
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Operador de Sistema', 'operador-sistema', 'Operador de sistema com permissão para operar sistemas de informação.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'operador-sistema' OR nome = 'Operador de Sistema');

-- Biomédico
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Biomédico', 'biomedico', 'Biomédico com permissão para realizar análises laboratoriais.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'biomedico' OR nome = 'Biomédico');

-- Técnico em Análises Clínicas
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Técnico em Análises Clínicas', 'tecnico-analises-clinicas', 'Técnico em análises clínicas com permissão para realizar exames laboratoriais.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'tecnico-analises-clinicas' OR nome = 'Técnico em Análises Clínicas');

-- Auxiliar de Laboratório
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Auxiliar de Laboratório', 'auxiliar-laboratorio', 'Auxiliar de laboratório com permissão para auxiliar em exames.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'auxiliar-laboratorio' OR nome = 'Auxiliar de Laboratório');

-- Técnico em Radiologia
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Técnico em Radiologia', 'tecnico-radiologia', 'Técnico em radiologia com permissão para realizar exames de imagem.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'tecnico-radiologia' OR nome = 'Técnico em Radiologia');

-- Técnico em Enfermagem do Trabalho
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Técnico em Enfermagem do Trabalho', 'tecnico-enfermagem-trabalho', 'Técnico em enfermagem do trabalho.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'tecnico-enfermagem-trabalho' OR nome = 'Técnico em Enfermagem do Trabalho');

-- Coordenador de APS
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Coordenador de APS', 'coordenador-aps', 'Coordenador de Atenção Primária à Saúde.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'coordenador-aps' OR nome = 'Coordenador de APS');

-- Coordenador de ESF
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Coordenador de ESF', 'coordenador-esf', 'Coordenador de Estratégia Saúde da Família.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'coordenador-esf' OR nome = 'Coordenador de ESF');

-- Coordenador de NASF
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Coordenador de NASF', 'coordenador-nasf', 'Coordenador de Núcleo de Apoio à Saúde da Família.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'coordenador-nasf' OR nome = 'Coordenador de NASF');

-- Coordenador de Vigilância
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Coordenador de Vigilância', 'coordenador-vigilancia', 'Coordenador de Vigilância em Saúde.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'coordenador-vigilancia' OR nome = 'Coordenador de Vigilância');

-- Coordenador de Epidemiologia
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Coordenador de Epidemiologia', 'coordenador-epidemiologia', 'Coordenador de Epidemiologia.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'coordenador-epidemiologia' OR nome = 'Coordenador de Epidemiologia');

-- Coordenador de Imunização
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Coordenador de Imunização', 'coordenador-imunizacao', 'Coordenador de Imunização.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'coordenador-imunizacao' OR nome = 'Coordenador de Imunização');

-- Coordenador de Farmácia
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Coordenador de Farmácia', 'coordenador-farmacia', 'Coordenador de Farmácia.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'coordenador-farmacia' OR nome = 'Coordenador de Farmácia');

-- Coordenador de Enfermagem
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Coordenador de Enfermagem', 'coordenador-enfermagem', 'Coordenador de Enfermagem.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'coordenador-enfermagem' OR nome = 'Coordenador de Enfermagem');

-- Coordenador de Médicos
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Coordenador de Médicos', 'coordenador-medicos', 'Coordenador de Médicos.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'coordenador-medicos' OR nome = 'Coordenador de Médicos');

-- Agente de Endemias
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Agente de Endemias', 'agente-endemias', 'Agente de combate a endemias com permissão para realizar atividades de campo.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'agente-endemias' OR nome = 'Agente de Endemias');

-- Agente Comunitário de Saúde
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Agente Comunitário de Saúde', 'agente-comunitario-saude', 'Agente comunitário de saúde com permissão para realizar visitas domiciliares.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'agente-comunitario-saude' OR nome = 'Agente Comunitário de Saúde');

-- Vigilante Sanitário
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Vigilante Sanitário', 'vigilante-sanitario', 'Vigilante sanitário com permissão para realizar inspeções sanitárias.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'vigilante-sanitario' OR nome = 'Vigilante Sanitário');

-- Epidemiologista
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Epidemiologista', 'epidemiologista', 'Epidemiologista com permissão para realizar análises epidemiológicas.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'epidemiologista' OR nome = 'Epidemiologista');

-- Usuário Básico
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Usuário Básico', 'usuario-basico', 'Usuário com permissões básicas de visualização.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'usuario-basico' OR nome = 'Usuário Básico');

-- Usuário Leitura
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Usuário Leitura', 'usuario-leitura', 'Usuário com permissão apenas para leitura de dados.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'usuario-leitura' OR nome = 'Usuário Leitura');

-- Estagiário
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Estagiário', 'estagiario', 'Estagiário com permissões limitadas para aprendizado.', false
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'estagiario' OR nome = 'Estagiário');

-- Voluntário
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Voluntário', 'voluntario', 'Voluntário com permissões limitadas.', false
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'voluntario' OR nome = 'Voluntário');

-- Auditor
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Auditor', 'auditor', 'Auditor com permissão para realizar auditorias em saúde.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'auditor' OR nome = 'Auditor');

-- Supervisor
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Supervisor', 'supervisor', 'Supervisor com permissão para supervisionar equipes e processos.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'supervisor' OR nome = 'Supervisor');

-- Educador em Saúde
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Educador em Saúde', 'educador-saude', 'Educador em saúde com permissão para realizar atividades educativas.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'educador-saude' OR nome = 'Educador em Saúde');

-- Pesquisador
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Pesquisador', 'pesquisador', 'Pesquisador com permissão para acessar dados para pesquisa.', false
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'pesquisador' OR nome = 'Pesquisador');

-- Analista de Dados
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Analista de Dados', 'analista-dados', 'Analista de dados com permissão para análise de informações de saúde.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'analista-dados' OR nome = 'Analista de Dados');

-- Gestor de Informação
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, 'Gestor de Informação', 'gestor-informacao', 'Gestor de informação em saúde com permissão para gerenciar dados.', true
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = 'gestor-informacao' OR nome = 'Gestor de Informação');


-- ========== FIM DO SCRIPT ==========
