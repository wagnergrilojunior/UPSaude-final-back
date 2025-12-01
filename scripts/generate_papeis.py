#!/usr/bin/env python3
"""
Script para gerar SQL de inserção de papéis (roles/perfis).
Gera papéis reais utilizados em sistemas de saúde pública no Brasil.
"""

# Dados dos papéis - Formato: (nome, slug, descricao, papel_do_sistema)
PAPEIS = [
    # Papéis Administrativos
    ('Administrador', 'admin', 'Acesso total ao sistema. Pode gerenciar todos os módulos, usuários, configurações e dados.', True),
    ('Administrador de Sistema', 'administrador-sistema', 'Administrador com permissões de configuração e manutenção do sistema.', True),
    ('Gerente Geral', 'gerente-geral', 'Gerente com acesso a todas as áreas do estabelecimento de saúde.', True),
    ('Coordenador Administrativo', 'coordenador-administrativo', 'Coordenador responsável pela gestão administrativa do estabelecimento.', True),
    ('Secretário de Saúde', 'secretario-saude', 'Secretário municipal de saúde com acesso a todos os estabelecimentos.', True),
    ('Diretor de Estabelecimento', 'diretor-estabelecimento', 'Diretor responsável pela gestão de um estabelecimento de saúde.', True),
    ('Coordenador de Gestão', 'coordenador-gestao', 'Coordenador responsável pela gestão operacional do estabelecimento.', True),
    
    # Papéis Médicos
    ('Médico', 'medico', 'Médico com permissão para realizar consultas, prescrições e procedimentos médicos.', True),
    ('Médico Clínico Geral', 'medico-clinico-geral', 'Médico especialista em clínica médica geral.', True),
    ('Médico de Família', 'medico-familia', 'Médico especialista em medicina de família e comunidade (ESF).', True),
    ('Médico Pediatra', 'medico-pediatra', 'Médico especialista em pediatria.', True),
    ('Médico Ginecologista', 'medico-ginecologista', 'Médico especialista em ginecologia e obstetrícia.', True),
    ('Médico Cardiologista', 'medico-cardiologista', 'Médico especialista em cardiologia.', True),
    ('Médico Ortopedista', 'medico-ortopedista', 'Médico especialista em ortopedia.', True),
    ('Médico Dermatologista', 'medico-dermatologista', 'Médico especialista em dermatologia.', True),
    ('Médico Neurologista', 'medico-neurologista', 'Médico especialista em neurologia.', True),
    ('Médico Psiquiatra', 'medico-psiquiatra', 'Médico especialista em psiquiatria.', True),
    ('Médico Oftalmologista', 'medico-oftalmologista', 'Médico especialista em oftalmologia.', True),
    ('Médico Otorrinolaringologista', 'medico-otorrino', 'Médico especialista em otorrinolaringologia.', True),
    ('Médico Urologista', 'medico-urologista', 'Médico especialista em urologia.', True),
    ('Médico Endocrinologista', 'medico-endocrinologista', 'Médico especialista em endocrinologia.', True),
    ('Médico Gastroenterologista', 'medico-gastroenterologista', 'Médico especialista em gastroenterologia.', True),
    ('Médico Pneumologista', 'medico-pneumologista', 'Médico especialista em pneumologia.', True),
    ('Médico Infectologista', 'medico-infectologista', 'Médico especialista em infectologia.', True),
    ('Médico Reumatologista', 'medico-reumatologista', 'Médico especialista em reumatologia.', True),
    ('Médico Anestesiologista', 'medico-anestesiologista', 'Médico especialista em anestesiologia.', True),
    ('Médico Cirurgião Geral', 'medico-cirurgiao-geral', 'Médico especialista em cirurgia geral.', True),
    ('Médico Intensivista', 'medico-intensivista', 'Médico especialista em medicina intensiva (UTI).', True),
    ('Médico de Emergência', 'medico-emergencia', 'Médico especialista em medicina de emergência.', True),
    ('Médico Radiologista', 'medico-radiologista', 'Médico especialista em radiologia e diagnóstico por imagem.', True),
    ('Médico Patologista', 'medico-patologista', 'Médico especialista em patologia.', True),
    ('Médico Legista', 'medico-legista', 'Médico especialista em medicina legal.', True),
    ('Médico do Trabalho', 'medico-trabalho', 'Médico especialista em medicina do trabalho.', True),
    ('Médico Residente', 'medico-residente', 'Médico em programa de residência médica.', False),
    ('Médico Plantonista', 'medico-plantonista', 'Médico em plantão médico.', False),
    
    # Papéis de Enfermagem
    ('Enfermeiro', 'enfermeiro', 'Enfermeiro com permissão para realizar procedimentos de enfermagem e coordenar equipes.', True),
    ('Enfermeiro Coordenador', 'enfermeiro-coordenador', 'Enfermeiro responsável pela coordenação da equipe de enfermagem.', True),
    ('Enfermeiro de Família', 'enfermeiro-familia', 'Enfermeiro especialista em saúde da família (ESF).', True),
    ('Enfermeiro Obstetra', 'enfermeiro-obstetra', 'Enfermeiro especialista em obstetrícia.', True),
    ('Enfermeiro Intensivista', 'enfermeiro-intensivista', 'Enfermeiro especialista em cuidados intensivos.', True),
    ('Enfermeiro de Emergência', 'enfermeiro-emergencia', 'Enfermeiro especialista em emergência e urgência.', True),
    ('Enfermeiro de Saúde Mental', 'enfermeiro-saude-mental', 'Enfermeiro especialista em saúde mental.', True),
    ('Enfermeiro de Saúde Pública', 'enfermeiro-saude-publica', 'Enfermeiro especialista em saúde pública.', True),
    ('Enfermeiro Auditor', 'enfermeiro-auditor', 'Enfermeiro responsável por auditoria em saúde.', True),
    ('Enfermeiro Educador', 'enfermeiro-educador', 'Enfermeiro responsável por educação em saúde.', True),
    ('Técnico de Enfermagem', 'tecnico-enfermagem', 'Técnico de enfermagem com permissão para realizar procedimentos técnicos.', True),
    ('Auxiliar de Enfermagem', 'auxiliar-enfermagem', 'Auxiliar de enfermagem com permissão para realizar cuidados básicos.', True),
    
    # Papéis de Odontologia
    ('Dentista', 'dentista', 'Dentista com permissão para realizar procedimentos odontológicos.', True),
    ('Dentista Clínico Geral', 'dentista-clinico-geral', 'Dentista especialista em clínica geral.', True),
    ('Dentista de Família', 'dentista-familia', 'Dentista especialista em saúde da família.', True),
    ('Dentista Pediatra', 'dentista-pediatra', 'Dentista especialista em odontopediatria.', True),
    ('Dentista Endodontista', 'dentista-endodontista', 'Dentista especialista em endodontia.', True),
    ('Dentista Periodontista', 'dentista-periodontista', 'Dentista especialista em periodontia.', True),
    ('Dentista Ortodontista', 'dentista-ortodontista', 'Dentista especialista em ortodontia.', True),
    ('Dentista Cirurgião Bucomaxilofacial', 'dentista-cirurgiao', 'Dentista especialista em cirurgia bucomaxilofacial.', True),
    ('Auxiliar de Saúde Bucal', 'auxiliar-saude-bucal', 'Auxiliar de saúde bucal com permissão para auxiliar em procedimentos.', True),
    ('Técnico em Saúde Bucal', 'tecnico-saude-bucal', 'Técnico em saúde bucal com permissão para realizar procedimentos técnicos.', True),
    
    # Papéis de Farmácia
    ('Farmacêutico', 'farmaceutico', 'Farmacêutico com permissão para gerenciar medicamentos e prescrições.', True),
    ('Farmacêutico Clínico', 'farmaceutico-clinico', 'Farmacêutico especialista em farmácia clínica.', True),
    ('Farmacêutico Hospitalar', 'farmaceutico-hospitalar', 'Farmacêutico especialista em farmácia hospitalar.', True),
    ('Farmacêutico de Saúde Pública', 'farmaceutico-saude-publica', 'Farmacêutico especialista em saúde pública.', True),
    ('Auxiliar de Farmácia', 'auxiliar-farmacia', 'Auxiliar de farmácia com permissão para auxiliar em dispensação.', True),
    ('Técnico em Farmácia', 'tecnico-farmacia', 'Técnico em farmácia com permissão para realizar procedimentos técnicos.', True),
    
    # Papéis de Nutrição
    ('Nutricionista', 'nutricionista', 'Nutricionista com permissão para realizar avaliações nutricionais e prescrições dietéticas.', True),
    ('Nutricionista Clínico', 'nutricionista-clinico', 'Nutricionista especialista em nutrição clínica.', True),
    ('Nutricionista de Saúde Pública', 'nutricionista-saude-publica', 'Nutricionista especialista em saúde pública.', True),
    ('Nutricionista Hospitalar', 'nutricionista-hospitalar', 'Nutricionista especialista em nutrição hospitalar.', True),
    
    # Papéis de Psicologia
    ('Psicólogo', 'psicologo', 'Psicólogo com permissão para realizar atendimentos psicológicos e avaliações.', True),
    ('Psicólogo Clínico', 'psicologo-clinico', 'Psicólogo especialista em psicologia clínica.', True),
    ('Psicólogo de Saúde Pública', 'psicologo-saude-publica', 'Psicólogo especialista em saúde pública.', True),
    ('Psicólogo Hospitalar', 'psicologo-hospitalar', 'Psicólogo especialista em psicologia hospitalar.', True),
    ('Psicólogo de Saúde Mental', 'psicologo-saude-mental', 'Psicólogo especialista em saúde mental.', True),
    
    # Papéis de Fisioterapia
    ('Fisioterapeuta', 'fisioterapeuta', 'Fisioterapeuta com permissão para realizar avaliações e tratamentos fisioterapêuticos.', True),
    ('Fisioterapeuta Clínico', 'fisioterapeuta-clinico', 'Fisioterapeuta especialista em fisioterapia clínica.', True),
    ('Fisioterapeuta Hospitalar', 'fisioterapeuta-hospitalar', 'Fisioterapeuta especialista em fisioterapia hospitalar.', True),
    ('Fisioterapeuta de Saúde Pública', 'fisioterapeuta-saude-publica', 'Fisioterapeuta especialista em saúde pública.', True),
    
    # Papéis de Fonoaudiologia
    ('Fonoaudiólogo', 'fonoaudiologo', 'Fonoaudiólogo com permissão para realizar avaliações e tratamentos fonoaudiológicos.', True),
    ('Fonoaudiólogo Clínico', 'fonoaudiologo-clinico', 'Fonoaudiólogo especialista em fonoaudiologia clínica.', True),
    ('Fonoaudiólogo Hospitalar', 'fonoaudiologo-hospitalar', 'Fonoaudiólogo especialista em fonoaudiologia hospitalar.', True),
    
    # Papéis de Terapia Ocupacional
    ('Terapeuta Ocupacional', 'terapeuta-ocupacional', 'Terapeuta ocupacional com permissão para realizar avaliações e tratamentos.', True),
    ('Terapeuta Ocupacional Clínico', 'terapeuta-ocupacional-clinico', 'Terapeuta ocupacional especialista em terapia ocupacional clínica.', True),
    ('Terapeuta Ocupacional Hospitalar', 'terapeuta-ocupacional-hospitalar', 'Terapeuta ocupacional especialista em terapia ocupacional hospitalar.', True),
    
    # Papéis de Serviço Social
    ('Assistente Social', 'assistente-social', 'Assistente social com permissão para realizar atendimentos e encaminhamentos sociais.', True),
    ('Assistente Social de Saúde', 'assistente-social-saude', 'Assistente social especialista em saúde.', True),
    ('Assistente Social de Saúde Mental', 'assistente-social-saude-mental', 'Assistente social especialista em saúde mental.', True),
    
    # Papéis Administrativos e Operacionais
    ('Recepcionista', 'recepcionista', 'Recepcionista com permissão para realizar agendamentos e atendimento ao público.', True),
    ('Atendente', 'atendente', 'Atendente com permissão para realizar atendimento ao público.', True),
    ('Auxiliar Administrativo', 'auxiliar-administrativo', 'Auxiliar administrativo com permissão para realizar tarefas administrativas.', True),
    ('Secretário', 'secretario', 'Secretário com permissão para realizar tarefas administrativas e de apoio.', True),
    ('Digitador', 'digitador', 'Digitador com permissão para realizar digitação de dados.', True),
    ('Operador de Sistema', 'operador-sistema', 'Operador de sistema com permissão para operar sistemas de informação.', True),
    
    # Papéis de Laboratório
    ('Biomédico', 'biomedico', 'Biomédico com permissão para realizar análises laboratoriais.', True),
    ('Técnico em Análises Clínicas', 'tecnico-analises-clinicas', 'Técnico em análises clínicas com permissão para realizar exames laboratoriais.', True),
    ('Auxiliar de Laboratório', 'auxiliar-laboratorio', 'Auxiliar de laboratório com permissão para auxiliar em exames.', True),
    
    # Papéis de Imagem
    ('Técnico em Radiologia', 'tecnico-radiologia', 'Técnico em radiologia com permissão para realizar exames de imagem.', True),
    ('Técnico em Enfermagem do Trabalho', 'tecnico-enfermagem-trabalho', 'Técnico em enfermagem do trabalho.', True),
    
    # Papéis de Gestão e Coordenação
    ('Coordenador de APS', 'coordenador-aps', 'Coordenador de Atenção Primária à Saúde.', True),
    ('Coordenador de ESF', 'coordenador-esf', 'Coordenador de Estratégia Saúde da Família.', True),
    ('Coordenador de NASF', 'coordenador-nasf', 'Coordenador de Núcleo de Apoio à Saúde da Família.', True),
    ('Coordenador de Vigilância', 'coordenador-vigilancia', 'Coordenador de Vigilância em Saúde.', True),
    ('Coordenador de Epidemiologia', 'coordenador-epidemiologia', 'Coordenador de Epidemiologia.', True),
    ('Coordenador de Imunização', 'coordenador-imunizacao', 'Coordenador de Imunização.', True),
    ('Coordenador de Farmácia', 'coordenador-farmacia', 'Coordenador de Farmácia.', True),
    ('Coordenador de Enfermagem', 'coordenador-enfermagem', 'Coordenador de Enfermagem.', True),
    ('Coordenador de Médicos', 'coordenador-medicos', 'Coordenador de Médicos.', True),
    
    # Papéis de Vigilância
    ('Agente de Endemias', 'agente-endemias', 'Agente de combate a endemias com permissão para realizar atividades de campo.', True),
    ('Agente Comunitário de Saúde', 'agente-comunitario-saude', 'Agente comunitário de saúde com permissão para realizar visitas domiciliares.', True),
    ('Vigilante Sanitário', 'vigilante-sanitario', 'Vigilante sanitário com permissão para realizar inspeções sanitárias.', True),
    ('Epidemiologista', 'epidemiologista', 'Epidemiologista com permissão para realizar análises epidemiológicas.', True),
    
    # Papéis de Suporte
    ('Usuário Básico', 'usuario-basico', 'Usuário com permissões básicas de visualização.', True),
    ('Usuário Leitura', 'usuario-leitura', 'Usuário com permissão apenas para leitura de dados.', True),
    ('Estagiário', 'estagiario', 'Estagiário com permissões limitadas para aprendizado.', False),
    ('Voluntário', 'voluntario', 'Voluntário com permissões limitadas.', False),
    
    # Papéis Especiais
    ('Auditor', 'auditor', 'Auditor com permissão para realizar auditorias em saúde.', True),
    ('Supervisor', 'supervisor', 'Supervisor com permissão para supervisionar equipes e processos.', True),
    ('Educador em Saúde', 'educador-saude', 'Educador em saúde com permissão para realizar atividades educativas.', True),
    ('Pesquisador', 'pesquisador', 'Pesquisador com permissão para acessar dados para pesquisa.', False),
    ('Analista de Dados', 'analista-dados', 'Analista de dados com permissão para análise de informações de saúde.', True),
    ('Gestor de Informação', 'gestor-informacao', 'Gestor de informação em saúde com permissão para gerenciar dados.', True),
]

def gerar_slug(nome):
    """Gera um slug a partir do nome."""
    slug = nome.lower()
    # Substituir espaços e caracteres especiais
    slug = slug.replace(' ', '-')
    slug = slug.replace('á', 'a').replace('à', 'a').replace('â', 'a').replace('ã', 'a')
    slug = slug.replace('é', 'e').replace('ê', 'e')
    slug = slug.replace('í', 'i').replace('î', 'i')
    slug = slug.replace('ó', 'o').replace('ô', 'o').replace('õ', 'o')
    slug = slug.replace('ú', 'u').replace('û', 'u')
    slug = slug.replace('ç', 'c')
    # Remover caracteres especiais
    import re
    slug = re.sub(r'[^a-z0-9-]', '', slug)
    # Remover hífens duplicados
    slug = re.sub(r'-+', '-', slug)
    return slug.strip('-')

def escapar(texto):
    """Escapa aspas simples para SQL."""
    return texto.replace("'", "''") if texto else None

def gerar_sql():
    sql_lines = []
    for nome, slug, descricao, papel_sistema in PAPEIS:
        nome_esc = escapar(nome)
        slug_esc = escapar(slug)
        desc_esc = escapar(descricao)
        
        sql = f"""-- {nome}
INSERT INTO public.papeis (
    id, criado_em, atualizado_em, ativo, nome, slug, descricao, papel_do_sistema
)
SELECT 
    gen_random_uuid(), NOW(), NOW(), true, '{nome_esc}', '{slug_esc}', '{desc_esc}', {str(papel_sistema).lower()}
WHERE NOT EXISTS (SELECT 1 FROM public.papeis WHERE slug = '{slug_esc}' OR nome = '{nome_esc}');
"""
        sql_lines.append(sql)
    return '\n'.join(sql_lines)

if __name__ == '__main__':
    print(gerar_sql())

