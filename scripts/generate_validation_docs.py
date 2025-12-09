#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Script para gerar documentação de validações para o front-end
analisando todas as classes Request e Embeddable do projeto.
"""

import re
from pathlib import Path
from typing import Dict, List

# Mapeamento de nomes de entidades para nomes mais legíveis
ENTITY_NAMES = {
    'MedicosRequest': 'Médicos',
    'PacienteRequest': 'Pacientes',
    'EnderecoRequest': 'Endereço',
    'ConvenioRequest': 'Convênio',
    'LoginRequest': 'Login',
    'UserRequest': 'Usuário',
    'AlergiasRequest': 'Alergias',
    'AtendimentoRequest': 'Atendimento',
    'ConsultasRequest': 'Consultas',
    'ExamesRequest': 'Exames',
    'VacinasRequest': 'Vacinas',
    'MedicacaoRequest': 'Medicação',
    'DoencasRequest': 'Doenças',
    'EstabelecimentosRequest': 'Estabelecimentos',
    'ProfissionaisSaudeRequest': 'Profissionais de Saúde',
    'TenantRequest': 'Tenant',
    'UsuariosSistemaRequest': 'Usuários do Sistema',
    'VacinacoesRequest': 'Vacinações',
    'CuidadosEnfermagemRequest': 'Cuidados de Enfermagem',
    'EspecialidadesMedicasRequest': 'Especialidades Médicas',
    'DoencasPacienteRequest': 'Doenças do Paciente',
    'AlergiasPacienteRequest': 'Alergias do Paciente',
    'ProntuariosRequest': 'Prontuários',
    'ReceitasMedicasRequest': 'Receitas Médicas',
    'TratamentosOdontologicosRequest': 'Tratamentos Odontológicos',
    'ProcedimentosOdontologicosRequest': 'Procedimentos Odontológicos',
    'VisitasDomiciliaresRequest': 'Visitas Domiciliares',
    'AgendamentoRequest': 'Agendamento',
    'CirurgiaRequest': 'Cirurgia',
    'EquipeSaudeRequest': 'Equipe de Saúde',
    'EducacaoSaudeRequest': 'Educação em Saúde',
    'AcaoPromocaoPrevencaoRequest': 'Ação Promoção e Prevenção',
    'NotificacaoRequest': 'Notificação',
    'LogsAuditoriaRequest': 'Logs de Auditoria',
    'CidadesRequest': 'Cidades',
    'EstadosRequest': 'Estados',
    'DepartamentosRequest': 'Departamentos',
    'FabricantesVacinaRequest': 'Fabricantes de Vacina',
    'CidDoencasRequest': 'CID Doenças',
    'CheckInAtendimentoRequest': 'Check-in Atendimento',
    'EquipeCirurgicaRequest': 'Equipe Cirúrgica',
    'ProcedimentoCirurgicoRequest': 'Procedimento Cirúrgico',
    'MedicacaoPacienteSimplificadoRequest': 'Medicação Paciente Simplificado',
    'DoencasPacienteSimplificadoRequest': 'Doenças Paciente Simplificado',
    'DeficienciasPacienteSimplificadoRequest': 'Deficiências Paciente Simplificado',
    'AlergiasPacienteSimplificadoRequest': 'Alergias Paciente Simplificado',
}

def extract_field_info(lines: List[str], field_start_idx: int) -> Dict:
    """Extrai informações de validação de um campo."""
    field_info = {
        'name': '',
        'type': '',
        'required': False,
        'validations': [],
        'is_enum': False,
        'mask': '',
        'description': ''
    }
    
    # Encontrar a linha do campo
    field_line = lines[field_start_idx]
    
    # Extrair tipo e nome do campo
    # Padrão: private Tipo nome;
    # ou: private Tipo<Generico> nome;
    type_pattern = r'private\s+([\w\.]+(?:<[^>]+>)?)\s+(\w+);'
    match = re.search(type_pattern, field_line)
    if not match:
        return None
    
    field_info['type'] = match.group(1)
    field_info['name'] = match.group(2)
    
    # Verificar se é enum
    if 'Enum' in field_info['type']:
        field_info['is_enum'] = True
    
    # Verificar se é List ou Set
    if field_info['type'].startswith('List') or field_info['type'].startswith('Set'):
        inner_type_match = re.search(r'<([^>]+)>', field_info['type'])
        if inner_type_match:
            inner_type = inner_type_match.group(1)
            field_info['type'] = f"Lista de {inner_type}"
        else:
            field_info['type'] = f"Lista de {field_info['type']}"
    
    # Procurar anotações antes do campo (até 10 linhas anteriores)
    annotations_text = []
    i = field_start_idx - 1
    while i >= 0 and i >= field_start_idx - 15:
        line = lines[i].strip()
        if not line:
            i -= 1
            continue
        if line.startswith('@'):
            # Pode ser multi-linha
            ann_text = line
            j = i - 1
            while j >= 0 and not lines[j].strip().startswith('@') and not lines[j].strip().startswith('private'):
                if lines[j].strip():
                    ann_text = lines[j].strip() + ' ' + ann_text
                j -= 1
            annotations_text.append(ann_text)
        elif line.startswith('*') and not line.startswith('**'):
            # Comentário JavaDoc
            desc_match = re.search(r'\*\s*(.+)', line)
            if desc_match and not field_info['description']:
                desc = desc_match.group(1).strip()
                if desc and not desc.startswith('@'):
                    field_info['description'] = desc
        elif line.startswith('/**') or line.startswith('//'):
            break
        i -= 1
    
    # Processar anotações
    for ann in reversed(annotations_text):
        if '@NotBlank' in ann:
            field_info['required'] = True
            msg_match = re.search(r'message\s*=\s*"([^"]+)"', ann)
            if msg_match:
                field_info['validations'].append(f'@NotBlank')
        elif '@NotNull' in ann:
            field_info['required'] = True
            msg_match = re.search(r'message\s*=\s*"([^"]+)"', ann)
            if msg_match:
                field_info['validations'].append(f'@NotNull')
        elif '@Pattern' in ann:
            pattern_match = re.search(r'regexp\s*=\s*"([^"]+)"', ann)
            if pattern_match:
                regex = pattern_match.group(1)
                # Simplificar regex para exibição
                regex_display = regex.replace('\\d', 'dígitos').replace('\\w', 'caracteres')
                field_info['validations'].append(f'@Pattern')
                # Detectar máscaras conhecidas
                if '\\d{11}' in regex and 'cpf' in field_info['name'].lower():
                    field_info['mask'] = '000.000.000-00'
                elif '\\d{14}' in regex and 'cnpj' in field_info['name'].lower():
                    field_info['mask'] = '00.000.000/0000-00'
                elif '\\d{8}' in regex and 'cep' in field_info['name'].lower():
                    field_info['mask'] = '00000-000'
                elif '\\d{10,11}' in regex and ('telefone' in field_info['name'].lower() or 'whatsapp' in field_info['name'].lower() or 'fax' in field_info['name'].lower()):
                    field_info['mask'] = '(00) 00000-0000'
                elif '\\d{4,10}' in regex and 'crm' in field_info['name'].lower():
                    field_info['mask'] = '—'
                elif '\\d{15}' in regex and 'cns' in field_info['name'].lower():
                    field_info['mask'] = '—'
        elif '@Email' in ann:
            field_info['validations'].append('@Email')
            if not field_info['mask']:
                field_info['mask'] = 'email'
        elif '@Size' in ann:
            max_match = re.search(r'max\s*=\s*(\d+)', ann)
            min_match = re.search(r'min\s*=\s*(\d+)', ann)
            if max_match and min_match:
                field_info['validations'].append(f'@Size(min={min_match.group(1)}, max={max_match.group(1)})')
            elif max_match:
                field_info['validations'].append(f'@Size(max={max_match.group(1)})')
            elif min_match:
                field_info['validations'].append(f'@Size(min={min_match.group(1)})')
        elif '@Min' in ann:
            min_match = re.search(r'value\s*=\s*(\d+)', ann)
            if min_match:
                field_info['validations'].append(f'@Min({min_match.group(1)})')
        elif '@Max' in ann:
            max_match = re.search(r'value\s*=\s*(\d+)', ann)
            if max_match:
                field_info['validations'].append(f'@Max({max_match.group(1)})')
    
    # Aplicar máscaras conhecidas baseadas no nome do campo
    if not field_info['mask']:
        name_lower = field_info['name'].lower()
        if 'cpf' in name_lower:
            field_info['mask'] = '000.000.000-00'
        elif 'cnpj' in name_lower:
            field_info['mask'] = '00.000.000/0000-00'
        elif 'cep' in name_lower:
            field_info['mask'] = '00000-000'
        elif 'telefone' in name_lower or 'whatsapp' in name_lower or 'fax' in name_lower:
            field_info['mask'] = '(00) 00000-0000'
        elif 'email' in name_lower:
            field_info['mask'] = 'email'
        elif 'crm' in name_lower:
            field_info['mask'] = '—'
        elif 'cns' in name_lower:
            field_info['mask'] = '—'
        else:
            field_info['mask'] = '—'
    
    # Gerar descrição se não tiver
    if not field_info['description']:
        # Converter camelCase para descrição legível
        desc = re.sub(r'([A-Z])', r' \1', field_info['name'])
        desc = desc.lower().strip()
        field_info['description'] = desc.capitalize()
    
    return field_info

def parse_request_file(file_path: Path) -> Dict:
    """Parse um arquivo Request e retorna informações estruturadas."""
    with open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()
        lines = content.split('\n')
    
    class_name_match = re.search(r'public\s+class\s+(\w+Request)', content)
    if not class_name_match:
        return None
    
    class_name = class_name_match.group(1)
    
    # Encontrar todos os campos privados
    fields = []
    for i, line in enumerate(lines):
        if re.search(r'private\s+\w', line) and ';' in line:
            field_info = extract_field_info(lines, i)
            if field_info and field_info['name']:
                fields.append(field_info)
    
    # Verificar se usa @Valid com embeddables
    embeddables = []
    # Procurar padrão: @Valid seguido de private TipoRequest nome;
    for i, line in enumerate(lines):
        if '@Valid' in line:
            # Procurar próxima linha com private
            j = i + 1
            while j < len(lines) and not lines[j].strip().startswith('private'):
                j += 1
            if j < len(lines):
                embeddable_match = re.search(r'private\s+(\w+Request)\s+(\w+);', lines[j])
                if embeddable_match:
                    embeddable_type = embeddable_match.group(1)
                    embeddable_name = embeddable_match.group(2)
                    embeddables.append((embeddable_type, embeddable_name))
    
    return {
        'class_name': class_name,
        'fields': fields,
        'embeddables': embeddables,
        'file_path': str(file_path)
    }

def generate_markdown_table(fields: List[Dict]) -> str:
    """Gera tabela markdown para os campos."""
    lines = ['| Campo | Tipo | Obrigatório | Validações | Enum | Máscara Sugerida | Descrição |']
    lines.append('|-------|------|-------------|------------|------|------------------|-----------|')
    
    for field in fields:
        campo = field['name']
        tipo = field['type']
        obrigatorio = 'Sim' if field['required'] else ('Não (Validar apenas se preenchido)' if field['validations'] else 'Não')
        validacoes = ', '.join(field['validations']) if field['validations'] else '—'
        enum = 'Sim' if field['is_enum'] else 'Não'
        mascara = field['mask'] if field['mask'] else '—'
        descricao = field['description'] if field['description'] else campo
        
        lines.append(f'| {campo} | {tipo} | {obrigatorio} | {validacoes} | {enum} | {mascara} | {descricao} |')
    
    return '\n'.join(lines)

def main():
    base_path = Path('/Users/wagnergrilo/Desktop/WGB/sistemas/UPSaude/code/UPSaude-back/src/main/java/com/upsaude/api/request')
    output_path = Path('/Users/wagnergrilo/Desktop/WGB/sistemas/UPSaude/code/UPSaude-back/docs/validations/validation-rules.md')
    
    # Encontrar todos os arquivos Request
    request_files = list(base_path.glob('*.java'))
    embeddable_files = list((base_path / 'embeddable').glob('*.java'))
    
    # Filtrar deserializers
    request_files = [f for f in request_files if 'Deserializer' not in f.name]
    embeddable_files = [f for f in embeddable_files if 'Deserializer' not in f.name]
    
    # Processar arquivos
    request_classes = {}
    embeddable_classes = {}
    
    print(f"Processando {len(request_files)} arquivos Request...")
    for file_path in request_files:
        info = parse_request_file(file_path)
        if info:
            request_classes[info['class_name']] = info
            print(f"  - {info['class_name']}: {len(info['fields'])} campos")
    
    print(f"Processando {len(embeddable_files)} arquivos Embeddable...")
    for file_path in embeddable_files:
        info = parse_request_file(file_path)
        if info:
            embeddable_classes[info['class_name']] = info
            print(f"  - {info['class_name']}: {len(info['fields'])} campos")
    
    # Gerar markdown
    markdown_lines = ['# Regras de Validação - Front-end\n']
    markdown_lines.append('Este documento contém todas as regras de validação das classes Request e Embeddable do projeto UPSaude-back.\n')
    
    # Índice
    markdown_lines.append('## Índice\n')
    index_items = []
    for class_name in sorted(request_classes.keys()):
        entity_name = ENTITY_NAMES.get(class_name, class_name.replace('Request', ''))
        anchor = entity_name.lower().replace(' ', '-').replace('ç', 'c').replace('ã', 'a').replace('õ', 'o').replace('é', 'e').replace('ê', 'e')
        index_items.append(f'- [{entity_name}](#{anchor})')
    
    # Adicionar embeddables ao índice
    for class_name in sorted(embeddable_classes.keys()):
        entity_name = class_name.replace('Request', '')
        anchor = entity_name.lower().replace(' ', '-').replace('ç', 'c').replace('ã', 'a').replace('õ', 'o').replace('é', 'e').replace('ê', 'e')
        index_items.append(f'- [{entity_name}](#{anchor})')
    
    markdown_lines.extend(index_items)
    markdown_lines.append('')
    
    # Gerar seções para cada Request
    for class_name in sorted(request_classes.keys()):
        info = request_classes[class_name]
        entity_name = ENTITY_NAMES.get(class_name, class_name.replace('Request', ''))
        
        markdown_lines.append(f'## {entity_name}\n')
        markdown_lines.append(f'### Classe Request: {class_name}\n')
        markdown_lines.append(generate_markdown_table(info['fields']))
        markdown_lines.append('')
        
        # Adicionar subseções para embeddables
        if info['embeddables']:
            for embeddable_type, embeddable_name in info['embeddables']:
                if embeddable_type in embeddable_classes:
                    embeddable_info = embeddable_classes[embeddable_type]
                    markdown_lines.append(f'#### {embeddable_type} ({embeddable_name})\n')
                    markdown_lines.append(generate_markdown_table(embeddable_info['fields']))
                    markdown_lines.append('')
    
    # Adicionar seções para embeddables não referenciados
    referenced_embeddables = set()
    for info in request_classes.values():
        for embeddable_type, _ in info['embeddables']:
            referenced_embeddables.add(embeddable_type)
    
    for class_name in sorted(embeddable_classes.keys()):
        if class_name not in referenced_embeddables:
            info = embeddable_classes[class_name]
            entity_name = class_name.replace('Request', '')
            markdown_lines.append(f'## {entity_name}\n')
            markdown_lines.append(f'### Classe Request: {class_name}\n')
            markdown_lines.append(generate_markdown_table(info['fields']))
            markdown_lines.append('')
    
    # Escrever arquivo
    output_path.parent.mkdir(parents=True, exist_ok=True)
    with open(output_path, 'w', encoding='utf-8') as f:
        f.write('\n'.join(markdown_lines))
    
    print(f"\nDocumentação gerada em: {output_path}")
    print(f"Total: {len(request_classes)} classes Request, {len(embeddable_classes)} classes Embeddable")

if __name__ == '__main__':
    main()
