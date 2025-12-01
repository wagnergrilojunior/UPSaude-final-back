-- Script gerado automaticamente para inserção de medicamentos
-- Gerado em: 2025-12-01 12:18:22

DO $$
DECLARE
    fabricante_uuid UUID;
BEGIN

    -- Tylenol (Paracetamol)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Paracetamol', 'Tylenol', 'Paracetamol', 'MED-0001', '864842', 'ANVISA-49678162062', '336471', '94619212',
        '500mg', 1, 1, 500.0, 'mg', '1 comprimido a cada 6-8 horas', 'Tomar com água',
        1, 1, 'Genérico', NULL, 'A02AA01',
        '49678162062', '2024-01-07', '2029-10-19', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        8.0, 25.0, false, false, NULL, 7, 'Manter na embalagem original. Não expor ao sol.',
        'Analgésico e antipirético', 'Dor e febre', 'Não exceder 4g/dia'
    )
    ON CONFLICT DO NOTHING;

    -- Novalgina (Dipirona)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Dipirona', 'Novalgina', 'Dipirona sódica', 'MED-0002', '801817', 'ANVISA-46470804849', '588590', '11256265',
        '500mg', 1, 1, 500.0, 'mg', '1 comprimido a cada 6-8 horas', 'Tomar com água',
        1, 1, 'Genérico', NULL, 'N02BB02',
        '46470804849', '2021-11-10', '2028-01-17', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        NULL, 30.0, true, true, 'Armazenar em local seco e arejado, protegido da luz.', 30, 'Manter longe do alcance de crianças.',
        'Analgésico e antipirético', 'Dor e febre', 'Pode causar agranulocitose'
    )
    ON CONFLICT DO NOTHING;

    -- Advil (Ibuprofeno)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Ibuprofeno', 'Advil', 'Ibuprofeno', 'MED-0003', '878455', 'ANVISA-57007345814', '710843', '63894224',
        '400mg', 1, 1, 400.0, 'mg', '1 comprimido a cada 8 horas', 'Tomar com alimentos',
        2, 1, 'Genérico', NULL, 'M01AE01',
        '57007345814', '2022-09-16', '2029-09-08', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        2.0, 30.0, true, false, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', NULL, NULL,
        'Anti-inflamatório não esteroidal', 'Dor, inflamação e febre', 'Evitar uso prolongado'
    )
    ON CONFLICT DO NOTHING;

    -- Aspirina (Ácido Acetilsalicílico)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Ácido Acetilsalicílico', 'Aspirina', 'Ácido acetilsalicílico', 'MED-0004', '222634', 'ANVISA-75471943948', '390581', '50699088',
        '100mg', 1, 1, 100.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        1, 1, 'Genérico', NULL, 'B01AC06',
        '75471943948', '2022-12-11', '2027-07-31', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', NULL,
        8.0, 15.0, false, true, 'Manter em geladeira (2-8°C). Não congelar.', 60, 'Manter longe do alcance de crianças.',
        'Antiagregante plaquetário', 'Prevenção cardiovascular', 'Evitar em crianças'
    )
    ON CONFLICT DO NOTHING;

    -- Amoxil (Amoxicilina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Amoxicilina', 'Amoxil', 'Amoxicilina', 'MED-0005', '805826', 'ANVISA-59378193523', '114996', '63809025',
        '500mg', 1, 1, 500.0, 'mg', '1 comprimido a cada 8 horas', 'Tomar com água',
        3, 1, 'Genérico', NULL, 'J01CA04',
        '59378193523', '2018-12-18', '2028-05-18', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar tontura. Evitar atividades que requerem atenção.',
        NULL, 15.0, false, false, 'Armazenar em local seco e arejado, protegido da luz.', 30, 'Manter na embalagem original. Não expor ao sol.',
        'Antibiótico beta-lactâmico', 'Infecções bacterianas', 'Completar tratamento'
    )
    ON CONFLICT DO NOTHING;

    -- Zitromax (Azitromicina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Azitromicina', 'Zitromax', 'Azitromicina di-hidratada', 'MED-0006', '492330', 'ANVISA-68565474954', '486775', '27085239',
        '500mg', 1, 1, 500.0, 'mg', '1 comprimido ao dia por 3 dias', 'Tomar em jejum',
        3, 1, 'Genérico', NULL, 'J01FA10',
        '68565474954', '2018-01-14', '2030-07-20', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        15.0, 15.0, false, true, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 90, NULL,
        'Antibiótico macrolídeo', 'Infecções respiratórias', 'Tomar 1h antes ou 2h após refeições'
    )
    ON CONFLICT DO NOTHING;

    -- Keflex (Cefalexina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Cefalexina', 'Keflex', 'Cefalexina monoidratada', 'MED-0007', '325239', 'ANVISA-68403314095', '272742', '96046480',
        '500mg', 2, 1, 500.0, 'mg', '1 cápsula a cada 6 horas', 'Tomar com água',
        3, 1, 'Genérico', NULL, 'J01DB01',
        '68403314095', '2021-08-02', '2027-10-25', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar tontura. Evitar atividades que requerem atenção.',
        15.0, 8.0, true, false, 'Armazenar em local seco e arejado, protegido da luz.', 7, NULL,
        'Antibiótico cefalosporina', 'Infecções bacterianas', 'Completar tratamento'
    )
    ON CONFLICT DO NOTHING;

    -- Cipro (Ciprofloxacino)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Ciprofloxacino', 'Cipro', 'Ciprofloxacino cloridrato', 'MED-0008', '612949', 'ANVISA-65728591523', '545845', '66592436',
        '500mg', 1, 1, 500.0, 'mg', '1 comprimido a cada 12 horas', 'Tomar com água',
        3, 1, 'Genérico', NULL, 'J01MA02',
        '65728591523', '2022-08-25', '2030-04-16', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        8.0, 8.0, false, false, 'Manter em geladeira (2-8°C). Não congelar.', 7, 'Manter na embalagem original. Não expor ao sol.',
        'Antibiótico fluoroquinolona', 'Infecções bacterianas', 'Evitar exposição solar'
    )
    ON CONFLICT DO NOTHING;

    -- Voltaren (Diclofenaco Sódico)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Diclofenaco Sódico', 'Voltaren', 'Diclofenaco sódico', 'MED-0009', '380465', 'ANVISA-99757543967', '445887', '36926679',
        '50mg', 1, 1, 50.0, 'mg', '1 comprimido a cada 8 horas', 'Tomar com alimentos',
        2, 1, 'Genérico', NULL, 'M01AB05',
        '99757543967', '2023-02-25', '2026-12-09', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', NULL,
        8.0, NULL, true, true, NULL, 60, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Anti-inflamatório não esteroidal', 'Dor e inflamação', 'Evitar uso prolongado'
    )
    ON CONFLICT DO NOTHING;

    -- Flanax (Naproxeno)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Naproxeno', 'Flanax', 'Naproxeno sódico', 'MED-0010', '240407', 'ANVISA-15664642514', '666553', '31746303',
        '550mg', 1, 1, 550.0, 'mg', '1 comprimido a cada 12 horas', 'Tomar com alimentos',
        2, 1, 'Genérico', NULL, 'M01AE02',
        '15664642514', '2017-02-14', '2028-11-24', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        15.0, 25.0, true, false, 'Armazenar em local seco e arejado, protegido da luz.', 7, 'Manter longe do alcance de crianças.',
        'Anti-inflamatório não esteroidal', 'Dor e inflamação', 'Evitar uso prolongado'
    )
    ON CONFLICT DO NOTHING;

    -- Profenid (Cetoprofeno)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Cetoprofeno', 'Profenid', 'Cetoprofeno', 'MED-0011', '599327', 'ANVISA-84267695092', '133636', '10998042',
        '100mg', 1, 1, 100.0, 'mg', '1 comprimido a cada 12 horas', 'Tomar com alimentos',
        2, 1, 'Genérico', NULL, 'M01AE03',
        '84267695092', '2023-05-18', '2030-11-08', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar tontura. Evitar atividades que requerem atenção.',
        8.0, 15.0, false, false, NULL, 14, 'Manter na embalagem original. Não expor ao sol.',
        'Anti-inflamatório não esteroidal', 'Dor e inflamação', 'Evitar uso prolongado'
    )
    ON CONFLICT DO NOTHING;

    -- Claritin (Loratadina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Loratadina', 'Claritin', 'Loratadina', 'MED-0012', '607680', 'ANVISA-12284904628', '196600', '36056032',
        '10mg', 1, 1, 10.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        7, 1, 'Genérico', NULL, 'R06AX13',
        '12284904628', '2019-05-28', '2030-10-23', 1, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', NULL,
        8.0, NULL, true, true, NULL, 14, 'Manter longe do alcance de crianças.',
        'Antihistamínico', 'Rinite alérgica, urticária', 'Não causa sonolência'
    )
    ON CONFLICT DO NOTHING;

    -- Desalex (Desloratadina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Desloratadina', 'Desalex', 'Desloratadina', 'MED-0013', '978174', 'ANVISA-66016355353', '177659', '45817680',
        '5mg', 1, 1, 5.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        7, 1, 'Genérico', NULL, 'R06AX27',
        '66016355353', '2021-10-06', '2029-04-19', 1, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        2.0, NULL, true, false, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 7, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Antihistamínico', 'Rinite alérgica, urticária', 'Não causa sonolência'
    )
    ON CONFLICT DO NOTHING;

    -- Zyrtec (Cetirizina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Cetirizina', 'Zyrtec', 'Cetirizina dicloridrato', 'MED-0014', '974304', 'ANVISA-95882937710', '174909', '71563193',
        '10mg', 1, 1, 10.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        7, 1, 'Genérico', NULL, 'R06AE07',
        '95882937710', '2018-10-09', '2028-08-13', 1, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        8.0, 30.0, false, true, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 7, 'Manter longe do alcance de crianças.',
        'Antihistamínico', 'Rinite alérgica, urticária', 'Pode causar sonolência'
    )
    ON CONFLICT DO NOTHING;

    -- Aerolin (Salbutamol)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Salbutamol', 'Aerolin', 'Salbutamol sulfato', 'MED-0015', '861359', 'ANVISA-96262425207', '602567', '44577560',
        '100mcg/dose', 13, 19, 100.0, 'mcg', '2 jatos a cada 4-6 horas', 'Agitar antes de usar',
        8, 13, 'Genérico', NULL, 'R03AC02',
        '96262425207', '2024-04-11', '2027-07-08', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        8.0, 8.0, true, true, 'Armazenar em local seco e arejado, protegido da luz.', 60, 'Manter longe do alcance de crianças.',
        'Broncodilatador', 'Asma, bronquite', 'Uso em crises'
    )
    ON CONFLICT DO NOTHING;

    -- Atrovent (Brometo de Ipratrópio)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Brometo de Ipratrópio', 'Atrovent', 'Ipratrópio brometo', 'MED-0016', '556856', 'ANVISA-14788266961', '529184', '97380042',
        '20mcg/dose', 13, 19, 20.0, 'mcg', '2 jatos a cada 6 horas', 'Agitar antes de usar',
        8, 13, 'Genérico', NULL, 'R03BB01',
        '14788266961', '2021-09-28', '2030-07-24', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        2.0, NULL, true, true, 'Armazenar em local seco e arejado, protegido da luz.', 90, 'Manter longe do alcance de crianças.',
        'Broncodilatador anticolinérgico', 'Asma, bronquite', 'Uso em crises'
    )
    ON CONFLICT DO NOTHING;

    -- Meticorten (Prednisona)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Prednisona', 'Meticorten', 'Prednisona', 'MED-0017', '478339', 'ANVISA-92143727463', '418229', '35834803',
        '20mg', 1, 1, 20.0, 'mg', 'Conforme prescrição médica', 'Tomar com alimentos',
        9, 1, 'Genérico', NULL, 'H02AB07',
        '92143727463', '2020-05-18', '2030-03-16', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        NULL, 30.0, false, true, 'Manter em geladeira (2-8°C). Não congelar.', 7, 'Manter na embalagem original. Não expor ao sol.',
        'Corticoide sistêmico', 'Inflamações, alergias', 'Não suspender abruptamente'
    )
    ON CONFLICT DO NOTHING;

    -- Decadron (Dexametasona)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Dexametasona', 'Decadron', 'Dexametasona', 'MED-0018', '894032', 'ANVISA-45772394645', '985512', '27623816',
        '4mg', 1, 1, 4.0, 'mg', 'Conforme prescrição médica', 'Tomar com alimentos',
        9, 1, 'Genérico', NULL, 'H02AB02',
        '45772394645', '2019-09-10', '2028-08-06', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        NULL, 8.0, false, true, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 60, 'Manter na embalagem original. Não expor ao sol.',
        'Corticoide sistêmico', 'Inflamações, alergias', 'Não suspender abruptamente'
    )
    ON CONFLICT DO NOTHING;

    -- Clenil (Beclometasona)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Beclometasona', 'Clenil', 'Beclometasona dipropionato', 'MED-0019', '872056', 'ANVISA-80099349714', '453927', '25860937',
        '250mcg/dose', 13, 19, 250.0, 'mcg', '2 jatos 2x ao dia', 'Agitar antes de usar',
        9, 13, 'Genérico', NULL, 'R03BA01',
        '80099349714', '2016-11-15', '2030-11-17', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        NULL, 25.0, false, false, 'Armazenar em local seco e arejado, protegido da luz.', 90, 'Manter longe do alcance de crianças.',
        'Corticoide inalatório', 'Asma', 'Enxaguar boca após uso'
    )
    ON CONFLICT DO NOTHING;

    -- Zoloft (Sertralina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Sertralina', 'Zoloft', 'Sertralina cloridrato', 'MED-0020', '369175', 'ANVISA-37429788568', '528691', '36397111',
        '50mg', 1, 1, 50.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        10, 1, 'Genérico', NULL, 'N06AB06',
        '37429788568', '2024-10-28', '2028-11-25', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        NULL, NULL, false, false, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 90, 'Manter na embalagem original. Não expor ao sol.',
        'Antidepressivo ISRS', 'Depressão, ansiedade', 'Efeito após 2-4 semanas'
    )
    ON CONFLICT DO NOTHING;

    -- Prozac (Fluoxetina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Fluoxetina', 'Prozac', 'Fluoxetina cloridrato', 'MED-0021', '480653', 'ANVISA-91739761260', '285856', '21492180',
        '20mg', 2, 1, 20.0, 'mg', '1 cápsula ao dia', 'Tomar com água',
        10, 1, 'Genérico', NULL, 'N06AB03',
        '91739761260', '2022-07-03', '2029-05-11', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar tontura. Evitar atividades que requerem atenção.',
        8.0, 15.0, false, false, 'Armazenar em local seco e arejado, protegido da luz.', 7, NULL,
        'Antidepressivo ISRS', 'Depressão, ansiedade', 'Efeito após 2-4 semanas'
    )
    ON CONFLICT DO NOTHING;

    -- Tryptanol (Amitriptilina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Amitriptilina', 'Tryptanol', 'Amitriptilina cloridrato', 'MED-0022', '513705', 'ANVISA-98896766586', '620510', '22999018',
        '25mg', 1, 1, 25.0, 'mg', '1 comprimido ao deitar', 'Tomar com água',
        10, 1, 'Genérico', NULL, 'N06AA09',
        '98896766586', '2020-11-24', '2030-11-28', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        2.0, NULL, true, true, 'Manter em geladeira (2-8°C). Não congelar.', 30, NULL,
        'Antidepressivo tricíclico', 'Depressão, dor crônica', 'Pode causar sonolência'
    )
    ON CONFLICT DO NOTHING;

    -- Valium (Diazepam)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Diazepam', 'Valium', 'Diazepam', 'MED-0023', '871198', 'ANVISA-19450612607', '704865', '45806284',
        '10mg', 1, 1, 10.0, 'mg', 'Conforme prescrição médica', 'Tomar com água',
        11, 1, 'Genérico', NULL, 'N05BA01',
        '19450612607', '2024-09-26', '2028-06-10', 4, true, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', NULL,
        2.0, NULL, true, true, NULL, 7, 'Manter na embalagem original. Não expor ao sol.',
        'Ansiolítico benzodiazepínico', 'Ansiedade, insônia', 'Pode causar dependência'
    )
    ON CONFLICT DO NOTHING;

    -- Frontal (Alprazolam)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Alprazolam', 'Frontal', 'Alprazolam', 'MED-0024', '360071', 'ANVISA-68701196018', '321922', '14343133',
        '0,5mg', 1, 1, 0.5, 'mg', 'Conforme prescrição médica', 'Tomar com água',
        11, 1, 'Genérico', NULL, 'N05BA12',
        '68701196018', '2018-08-23', '2029-08-06', 4, true, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        NULL, 8.0, true, false, NULL, 14, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Ansiolítico benzodiazepínico', 'Ansiedade, pânico', 'Pode causar dependência'
    )
    ON CONFLICT DO NOTHING;

    -- Rivotril (Clonazepam)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Clonazepam', 'Rivotril', 'Clonazepam', 'MED-0025', '453076', 'ANVISA-84990193856', '498863', '48221627',
        '2mg', 1, 1, 2.0, 'mg', 'Conforme prescrição médica', 'Tomar com água',
        11, 1, 'Genérico', NULL, 'N03AE01',
        '84990193856', '2021-12-08', '2027-07-09', 4, true, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', NULL,
        8.0, 15.0, true, false, NULL, 90, NULL,
        'Ansiolítico e anticonvulsivante', 'Ansiedade, convulsões', 'Pode causar dependência'
    )
    ON CONFLICT DO NOTHING;

    -- Losec (Omeprazol)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Omeprazol', 'Losec', 'Omeprazol', 'MED-0026', '178447', 'ANVISA-50193328432', '257718', '42767086',
        '20mg', 2, 1, 20.0, 'mg', '1 cápsula pela manhã em jejum', 'Tomar 30min antes do café',
        14, 1, 'Genérico', NULL, 'A02BC01',
        '50193328432', '2022-11-26', '2027-10-04', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar tontura. Evitar atividades que requerem atenção.',
        15.0, 30.0, true, true, 'Armazenar em local seco e arejado, protegido da luz.', 14, 'Manter longe do alcance de crianças.',
        'Inibidor de bomba de prótons', 'Gastrite, úlcera', 'Tomar em jejum'
    )
    ON CONFLICT DO NOTHING;

    -- Pantozol (Pantoprazol)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Pantoprazol', 'Pantozol', 'Pantoprazol sódico', 'MED-0027', '785232', 'ANVISA-77175035428', '728378', '72096970',
        '40mg', 1, 1, 40.0, 'mg', '1 comprimido pela manhã', 'Tomar com água',
        14, 1, 'Genérico', NULL, 'A02BC02',
        '77175035428', '2016-08-04', '2028-04-30', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', NULL,
        2.0, 25.0, true, true, 'Manter em geladeira (2-8°C). Não congelar.', 7, NULL,
        'Inibidor de bomba de prótons', 'Gastrite, úlcera', 'Tomar em jejum'
    )
    ON CONFLICT DO NOTHING;

    -- Antak (Ranitidina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Ranitidina', 'Antak', 'Ranitidina cloridrato', 'MED-0028', '338280', 'ANVISA-14136851822', '413298', '17827563',
        '150mg', 1, 1, 150.0, 'mg', '1 comprimido 2x ao dia', 'Tomar com água',
        14, 1, 'Genérico', NULL, 'A02BA02',
        '14136851822', '2018-10-31', '2027-08-29', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar tontura. Evitar atividades que requerem atenção.',
        15.0, 8.0, true, true, 'Armazenar em local seco e arejado, protegido da luz.', 30, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Bloqueador H2', 'Gastrite, úlcera', 'Tomar antes das refeições'
    )
    ON CONFLICT DO NOTHING;

    -- Lactulona (Lactulose)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Lactulose', 'Lactulona', 'Lactulose', 'MED-0029', '770026', 'ANVISA-57041013748', '870018', '75535778',
        '10g/15ml', 4, 1, 10.0, 'g', '15-30ml ao deitar', 'Tomar com água',
        15, 5, 'Genérico', NULL, 'A06AD11',
        '57041013748', '2019-11-29', '2030-09-23', 1, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        15.0, 30.0, false, false, 'Armazenar em local seco e arejado, protegido da luz.', 14, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Laxante osmótico', 'Constipação', 'Efeito após 24-48h'
    )
    ON CONFLICT DO NOTHING;

    -- Dulcolax (Bisacodil)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Bisacodil', 'Dulcolax', 'Bisacodil', 'MED-0030', '233232', 'ANVISA-17007363061', '899802', '44359975',
        '5mg', 1, 1, 5.0, 'mg', '1-2 comprimidos ao deitar', 'Tomar com água',
        15, 1, 'Genérico', NULL, 'A06AB02',
        '17007363061', '2023-11-18', '2027-03-31', 1, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        NULL, 15.0, true, false, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 7, 'Manter longe do alcance de crianças.',
        'Laxante estimulante', 'Constipação', 'Efeito após 6-12h'
    )
    ON CONFLICT DO NOTHING;

    -- Imodium (Loperamida)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Loperamida', 'Imodium', 'Loperamida cloridrato', 'MED-0031', '117061', 'ANVISA-37132472598', '481308', '95758150',
        '2mg', 1, 1, 2.0, 'mg', '2 comprimidos após primeira evacuação', 'Tomar com água',
        16, 1, 'Genérico', NULL, 'A07DA03',
        '37132472598', '2024-02-04', '2030-08-25', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        15.0, NULL, true, false, 'Manter em geladeira (2-8°C). Não congelar.', 90, NULL,
        'Antidiarreico', 'Diarreia aguda', 'Não usar em crianças <2 anos'
    )
    ON CONFLICT DO NOTHING;

    -- Addera D3 (Vitamina D3)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Vitamina D3', 'Addera D3', 'Colecalciferol', 'MED-0032', '790704', 'ANVISA-17710310348', '667997', '56178360',
        '2000UI', 2, 1, 2000.0, 'UI', '1 cápsula ao dia', 'Tomar com alimentos',
        17, 1, 'Genérico', NULL, 'A11CC05',
        '17710310348', '2016-07-14', '2027-12-20', 1, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        2.0, 15.0, false, true, 'Manter em geladeira (2-8°C). Não congelar.', 30, NULL,
        'Vitamina D', 'Deficiência de vitamina D', 'Tomar com alimentos gordurosos'
    )
    ON CONFLICT DO NOTHING;

    -- Folacin (Ácido Fólico)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Ácido Fólico', 'Folacin', 'Ácido fólico', 'MED-0033', '801589', 'ANVISA-82339923220', '731268', '81895454',
        '5mg', 1, 1, 5.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        17, 1, 'Genérico', NULL, 'B03BB01',
        '82339923220', '2018-04-05', '2030-08-12', 1, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        8.0, 8.0, true, true, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 30, 'Manter na embalagem original. Não expor ao sol.',
        'Vitamina B9', 'Anemia, gestação', 'Importante na gestação'
    )
    ON CONFLICT DO NOTHING;

    -- Cianocobalamina (Vitamina B12)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Vitamina B12', 'Cianocobalamina', 'Cianocobalamina', 'MED-0034', '559879', 'ANVISA-78168248017', '787752', '85308650',
        '1000mcg', 8, 1, 1000.0, 'mcg', '1 ampola IM mensal', 'Aplicar via intramuscular',
        17, 8, 'Genérico', NULL, 'B03BA01',
        '78168248017', '2016-02-20', '2028-08-08', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', NULL,
        NULL, 15.0, true, false, 'Manter em geladeira (2-8°C). Não congelar.', 7, NULL,
        'Vitamina B12', 'Anemia perniciosa', 'Aplicação intramuscular'
    )
    ON CONFLICT DO NOTHING;

    -- Puran T4 (Levotiroxina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Levotiroxina', 'Puran T4', 'Levotiroxina sódica', 'MED-0035', '539188', 'ANVISA-32408583490', '214459', '62794513',
        '50mcg', 1, 1, 50.0, 'mcg', '1 comprimido em jejum', 'Tomar 30min antes do café',
        19, 1, 'Genérico', NULL, 'H03AA01',
        '32408583490', '2019-03-24', '2028-01-31', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        2.0, 30.0, true, true, 'Manter em geladeira (2-8°C). Não congelar.', 7, 'Manter longe do alcance de crianças.',
        'Hormônio tireoidiano', 'Hipotireoidismo', 'Tomar sempre em jejum'
    )
    ON CONFLICT DO NOTHING;

    -- Glifage (Metformina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Metformina', 'Glifage', 'Metformina cloridrato', 'MED-0036', '491722', 'ANVISA-13950779137', '415961', '71297475',
        '500mg', 1, 1, 500.0, 'mg', '1 comprimido 2x ao dia', 'Tomar com alimentos',
        20, 1, 'Genérico', NULL, 'A10BA02',
        '13950779137', '2016-10-25', '2027-12-30', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        2.0, NULL, false, true, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 30, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Antidiabético', 'Diabetes tipo 2', 'Pode causar desconforto gastrointestinal'
    )
    ON CONFLICT DO NOTHING;

    -- Daonil (Glibenclamida)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Glibenclamida', 'Daonil', 'Glibenclamida', 'MED-0037', '468671', 'ANVISA-22496072483', '309715', '87006883',
        '5mg', 1, 1, 5.0, 'mg', '1 comprimido antes do café', 'Tomar antes das refeições',
        20, 1, 'Genérico', NULL, 'A10BB01',
        '22496072483', '2016-04-19', '2027-04-22', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', NULL,
        15.0, NULL, true, true, NULL, NULL, NULL,
        'Antidiabético', 'Diabetes tipo 2', 'Risco de hipoglicemia'
    )
    ON CONFLICT DO NOTHING;

    -- Cozaar (Losartana)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Losartana', 'Cozaar', 'Losartana potássica', 'MED-0038', '860236', 'ANVISA-46368771244', '514859', '73176143',
        '50mg', 1, 1, 50.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        21, 1, 'Genérico', NULL, 'C09CA01',
        '46368771244', '2024-01-25', '2029-01-11', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        15.0, 15.0, false, true, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 30, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Anti-hipertensivo', 'Hipertensão arterial', 'Tomar sempre no mesmo horário'
    )
    ON CONFLICT DO NOTHING;

    -- Norvasc (Amlodipina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Amlodipina', 'Norvasc', 'Amlodipina besilato', 'MED-0039', '980741', 'ANVISA-16224009798', '677274', '51804572',
        '5mg', 1, 1, 5.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        21, 1, 'Genérico', NULL, 'C08CA01',
        '16224009798', '2018-05-09', '2030-03-24', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        2.0, 15.0, false, false, NULL, NULL, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Bloqueador de canais de cálcio', 'Hipertensão arterial', 'Pode causar edema'
    )
    ON CONFLICT DO NOTHING;

    -- Renitec (Enalapril)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Enalapril', 'Renitec', 'Enalapril maleato', 'MED-0040', '954351', 'ANVISA-26130441146', '856884', '39835832',
        '10mg', 1, 1, 10.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        21, 1, 'Genérico', NULL, 'C09AA02',
        '26130441146', '2019-12-04', '2028-10-01', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, NULL,
        2.0, 15.0, false, false, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 14, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Inibidor de ECA', 'Hipertensão arterial', 'Pode causar tosse seca'
    )
    ON CONFLICT DO NOTHING;

    -- Inderal (Propranolol)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Propranolol', 'Inderal', 'Propranolol cloridrato', 'MED-0041', '390380', 'ANVISA-78016512622', '256616', '73386433',
        '40mg', 1, 1, 40.0, 'mg', '1 comprimido 2x ao dia', 'Tomar com água',
        21, 1, 'Genérico', NULL, 'C07AA05',
        '78016512622', '2022-01-23', '2027-04-19', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        15.0, 15.0, true, false, NULL, 14, 'Manter na embalagem original. Não expor ao sol.',
        'Betabloqueador', 'Hipertensão, arritmias', 'Não suspender abruptamente'
    )
    ON CONFLICT DO NOTHING;

    -- Clorana (Hidroclorotiazida)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Hidroclorotiazida', 'Clorana', 'Hidroclorotiazida', 'MED-0042', '494879', 'ANVISA-20872284123', '656002', '50769546',
        '25mg', 1, 1, 25.0, 'mg', '1 comprimido pela manhã', 'Tomar com água',
        22, 1, 'Genérico', NULL, 'C03AA03',
        '20872284123', '2022-03-18', '2027-04-18', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, NULL,
        NULL, 8.0, true, true, NULL, 14, 'Manter na embalagem original. Não expor ao sol.',
        'Diurético tiazídico', 'Hipertensão, edema', 'Pode causar hipocalemia'
    )
    ON CONFLICT DO NOTHING;

    -- Lasix (Furosemida)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Furosemida', 'Lasix', 'Furosemida', 'MED-0043', '597642', 'ANVISA-92311381358', '833987', '23400003',
        '40mg', 1, 1, 40.0, 'mg', '1 comprimido pela manhã', 'Tomar com água',
        22, 1, 'Genérico', NULL, 'C03CA01',
        '92311381358', '2017-09-24', '2027-07-22', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        15.0, 30.0, false, true, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 7, NULL,
        'Diurético de alça', 'Edema, insuficiência cardíaca', 'Pode causar hipocalemia'
    )
    ON CONFLICT DO NOTHING;

    -- Marevan (Varfarina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Varfarina', 'Marevan', 'Varfarina sódica', 'MED-0044', '264133', 'ANVISA-25173290605', '288985', '14644211',
        '5mg', 1, 1, 5.0, 'mg', 'Conforme prescrição médica', 'Tomar sempre no mesmo horário',
        24, 1, 'Genérico', NULL, 'B01AA03',
        '25173290605', '2016-09-30', '2030-05-25', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        NULL, 15.0, false, true, NULL, 30, 'Manter na embalagem original. Não expor ao sol.',
        'Anticoagulante', 'Prevenção de trombose', 'Monitorar INR regularmente'
    )
    ON CONFLICT DO NOTHING;

    -- Aspirina Prevent (AAS)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'AAS', 'Aspirina Prevent', 'Ácido acetilsalicílico', 'MED-0045', '961560', 'ANVISA-20561542623', '656086', '19189546',
        '100mg', 1, 1, 100.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        24, 1, 'Genérico', NULL, 'B01AC06',
        '20561542623', '2023-07-21', '2029-05-03', 1, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', NULL,
        NULL, NULL, false, true, 'Armazenar em local seco e arejado, protegido da luz.', NULL, NULL,
        'Antiagregante plaquetário', 'Prevenção cardiovascular', 'Evitar em crianças'
    )
    ON CONFLICT DO NOTHING;

    -- Hidantal (Fenitoína)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Fenitoína', 'Hidantal', 'Fenitoína sódica', 'MED-0046', '502231', 'ANVISA-85491911791', '269302', '18110338',
        '100mg', 1, 1, 100.0, 'mg', 'Conforme prescrição médica', 'Tomar com água',
        13, 1, 'Genérico', NULL, 'N03AB02',
        '85491911791', '2017-08-29', '2030-08-30', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        8.0, 15.0, true, true, NULL, 60, NULL,
        'Anticonvulsivante', 'Epilepsia', 'Monitorar níveis séricos'
    )
    ON CONFLICT DO NOTHING;

    -- Tegretol (Carbamazepina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Carbamazepina', 'Tegretol', 'Carbamazepina', 'MED-0047', '566613', 'ANVISA-33656668000', '130997', '80475853',
        '200mg', 1, 1, 200.0, 'mg', 'Conforme prescrição médica', 'Tomar com água',
        13, 1, 'Genérico', NULL, 'N03AF01',
        '33656668000', '2016-03-26', '2029-06-12', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        NULL, 8.0, false, false, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 90, NULL,
        'Anticonvulsivante', 'Epilepsia, neuralgia', 'Monitorar hemograma'
    )
    ON CONFLICT DO NOTHING;

    -- Zoltec (Fluconazol)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Fluconazol', 'Zoltec', 'Fluconazol', 'MED-0048', '880931', 'ANVISA-19058862570', '256119', '54186857',
        '150mg', 1, 1, 150.0, 'mg', '1 comprimido em dose única', 'Tomar com água',
        5, 1, 'Genérico', NULL, 'J02AC01',
        '19058862570', '2019-12-15', '2027-06-05', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', NULL,
        2.0, 30.0, true, true, NULL, 90, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Antifúngico', 'Candidíase', 'Dose única para candidíase'
    )
    ON CONFLICT DO NOTHING;

    -- Micostatin (Nistatina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Nistatina', 'Micostatin', 'Nistatina', 'MED-0049', '503793', 'ANVISA-13103222798', '123187', '16255294',
        '100.000UI/ml', 4, 1, 100000.0, 'UI', '4-6ml 4x ao dia', 'Manter na boca antes de engolir',
        5, 6, 'Genérico', NULL, 'A07AA02',
        '13103222798', '2024-04-25', '2027-08-16', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, NULL,
        8.0, 8.0, false, true, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 30, 'Manter longe do alcance de crianças.',
        'Antifúngico', 'Candidíase oral', 'Manter na boca'
    )
    ON CONFLICT DO NOTHING;

    -- Zovirax (Aciclovir)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Aciclovir', 'Zovirax', 'Aciclovir', 'MED-0050', '776981', 'ANVISA-16420483827', '173178', '45345582',
        '200mg', 1, 1, 200.0, 'mg', '1 comprimido 5x ao dia', 'Tomar com água',
        4, 1, 'Genérico', NULL, 'J05AB01',
        '16420483827', '2017-04-11', '2029-05-22', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', NULL,
        8.0, 15.0, false, false, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 14, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Antiviral', 'Herpes simples, zoster', 'Iniciar precocemente'
    )
    ON CONFLICT DO NOTHING;

    -- Tamiflu (Oseltamivir)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Oseltamivir', 'Tamiflu', 'Oseltamivir fosfato', 'MED-0051', '587551', 'ANVISA-82071182833', '503375', '78617283',
        '75mg', 2, 1, 75.0, 'mg', '1 cápsula 2x ao dia por 5 dias', 'Tomar com água',
        4, 1, 'Genérico', NULL, 'J05AH02',
        '82071182833', '2022-05-30', '2027-12-18', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        15.0, 15.0, true, false, NULL, 60, 'Manter longe do alcance de crianças.',
        'Antiviral', 'Influenza', 'Iniciar nas primeiras 48h'
    )
    ON CONFLICT DO NOTHING;

    -- Zentel (Albendazol)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Albendazol', 'Zentel', 'Albendazol', 'MED-0052', '216452', 'ANVISA-85899080701', '371800', '45437277',
        '400mg', 1, 1, 400.0, 'mg', '1 comprimido em dose única', 'Tomar com alimentos',
        6, 1, 'Genérico', NULL, 'P02CA03',
        '85899080701', '2021-11-28', '2027-10-13', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        8.0, 30.0, true, false, 'Manter em geladeira (2-8°C). Não congelar.', 7, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Antiparasitário', 'Verminoses', 'Dose única'
    )
    ON CONFLICT DO NOTHING;

    -- Vermox (Mebendazol)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Mebendazol', 'Vermox', 'Mebendazol', 'MED-0053', '631980', 'ANVISA-65620454021', '454368', '68406105',
        '100mg', 1, 1, 100.0, 'mg', '1 comprimido 2x ao dia por 3 dias', 'Tomar com alimentos',
        6, 1, 'Genérico', NULL, 'P02CA01',
        '65620454021', '2017-09-07', '2029-02-12', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        NULL, 15.0, true, true, 'Manter em geladeira (2-8°C). Não congelar.', 90, 'Manter na embalagem original. Não expor ao sol.',
        'Antiparasitário', 'Verminoses', 'Tomar com alimentos'
    )
    ON CONFLICT DO NOTHING;

    -- Dorflex (Dorflex)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Dorflex', 'Dorflex', 'Cafeína + Carisoprodol + Dipirona', 'MED-0054', '244809', 'ANVISA-41864013407', '425969', '44476698',
        '1 comprimido', 1, 1, 1.0, 'comp', '1 comprimido a cada 8 horas', 'Tomar com água',
        1, 1, 'Genérico', NULL, 'M03BA03',
        '41864013407', '2023-12-29', '2027-11-16', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, NULL,
        15.0, NULL, false, true, 'Manter em geladeira (2-8°C). Não congelar.', 90, NULL,
        'Relaxante muscular + Analgésico', 'Dor muscular, tensão', 'Pode causar sonolência'
    )
    ON CONFLICT DO NOTHING;

    -- Neosaldina (Neosaldina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Neosaldina', 'Neosaldina', 'Dipirona + Cafeína + Mucato de Isometepteno', 'MED-0055', '207060', 'ANVISA-30123508747', '384320', '71130080',
        '30 gotas', 11, 1, 30.0, 'gota', '30 gotas a cada 6 horas', 'Tomar com água',
        1, 6, 'Genérico', NULL, 'N02BB02',
        '30123508747', '2023-06-07', '2028-04-11', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar tontura. Evitar atividades que requerem atenção.',
        8.0, NULL, true, true, NULL, 60, NULL,
        'Analgésico', 'Dor de cabeça, enxaqueca', 'Não exceder 120 gotas/dia'
    )
    ON CONFLICT DO NOTHING;

    -- Buscopan (Buscopan)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Buscopan', 'Buscopan', 'Butilescopolamina brometo', 'MED-0056', '205740', 'ANVISA-27340746971', '954748', '65356130',
        '10mg', 1, 1, 10.0, 'mg', '1 comprimido a cada 8 horas', 'Tomar com água',
        14, 1, 'Genérico', NULL, 'A03BB01',
        '27340746971', '2016-01-14', '2030-03-13', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar tontura. Evitar atividades que requerem atenção.',
        8.0, 25.0, true, false, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 30, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Antiespasmódico', 'Cólica intestinal, biliar', 'Alivio rápido de cólicas'
    )
    ON CONFLICT DO NOTHING;

    -- Torsilax (Torsilax)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Torsilax', 'Torsilax', 'Ciclobenzaprina + Dipirona + Cafeína', 'MED-0057', '664824', 'ANVISA-98095888851', '363787', '98230705',
        '1 comprimido', 1, 1, 1.0, 'comp', '1 comprimido a cada 8 horas', 'Tomar com água',
        1, 1, 'Genérico', NULL, 'M03BX01',
        '98095888851', '2017-05-07', '2028-08-04', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        8.0, 25.0, true, true, 'Armazenar em local seco e arejado, protegido da luz.', 90, 'Manter longe do alcance de crianças.',
        'Relaxante muscular + Analgésico', 'Dor muscular, lombalgia', 'Pode causar sonolência'
    )
    ON CONFLICT DO NOTHING;

    -- Tylenol 750mg (Paracetamol)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Paracetamol', 'Tylenol 750mg', 'Paracetamol', 'MED-0058', '305060', 'ANVISA-63055200561', '904111', '16681691',
        '750mg', 1, 1, 750.0, 'mg', '1 comprimido a cada 6-8 horas', 'Tomar com água',
        1, 1, 'Genérico', NULL, 'A02AA01',
        '63055200561', '2024-09-24', '2027-04-17', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', NULL,
        NULL, NULL, false, true, NULL, NULL, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Analgésico e antipirético', 'Dor e febre', 'Não exceder 4g/dia'
    )
    ON CONFLICT DO NOTHING;

    -- Tylenol Gotas (Paracetamol)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Paracetamol', 'Tylenol Gotas', 'Paracetamol', 'MED-0059', '279389', 'ANVISA-33959246878', '615069', '77053036',
        '200mg/ml', 4, 1, 200.0, 'mg', 'Conforme peso', 'Tomar com água',
        1, 6, 'Genérico', NULL, 'A02AA01',
        '33959246878', '2016-09-08', '2028-06-09', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        8.0, 30.0, true, true, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 90, NULL,
        'Analgésico e antipirético', 'Dor e febre em crianças', 'Não exceder 4g/dia'
    )
    ON CONFLICT DO NOTHING;

    -- Tylenol Xarope (Paracetamol)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Paracetamol', 'Tylenol Xarope', 'Paracetamol', 'MED-0060', '215800', 'ANVISA-86173739047', '427563', '32747460',
        '32mg/ml', 4, 1, 32.0, 'mg', 'Conforme peso', 'Tomar com água',
        1, 5, 'Genérico', NULL, 'A02AA01',
        '86173739047', '2019-11-26', '2029-11-21', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar tontura. Evitar atividades que requerem atenção.',
        8.0, 30.0, true, true, 'Armazenar em local seco e arejado, protegido da luz.', 7, 'Manter na embalagem original. Não expor ao sol.',
        'Analgésico e antipirético', 'Dor e febre em crianças', 'Não exceder 4g/dia'
    )
    ON CONFLICT DO NOTHING;

    -- Amoxil 250mg (Amoxicilina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Amoxicilina', 'Amoxil 250mg', 'Amoxicilina', 'MED-0061', '530019', 'ANVISA-81035730826', '632089', '78889391',
        '250mg', 1, 1, 250.0, 'mg', '1 comprimido a cada 8 horas', 'Tomar com água',
        3, 1, 'Genérico', NULL, 'J01CA04',
        '81035730826', '2023-05-24', '2030-07-04', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', NULL,
        15.0, 30.0, true, false, NULL, 60, 'Manter longe do alcance de crianças.',
        'Antibiótico beta-lactâmico', 'Infecções bacterianas', 'Completar tratamento'
    )
    ON CONFLICT DO NOTHING;

    -- Amoxil Suspensão (Amoxicilina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Amoxicilina', 'Amoxil Suspensão', 'Amoxicilina', 'MED-0062', '629009', 'ANVISA-89377203189', '746109', '59842651',
        '250mg/5ml', 4, 1, 250.0, 'mg', 'Conforme peso', 'Agitar antes de usar',
        3, 4, 'Genérico', NULL, 'J01CA04',
        '89377203189', '2017-11-02', '2029-12-23', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        NULL, 8.0, false, true, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 60, NULL,
        'Antibiótico beta-lactâmico', 'Infecções bacterianas em crianças', 'Completar tratamento'
    )
    ON CONFLICT DO NOTHING;

    -- Augmentin (Amoxicilina + Ácido Clavulânico)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Amoxicilina + Ácido Clavulânico', 'Augmentin', 'Amoxicilina + Ácido clavulânico', 'MED-0063', '329761', 'ANVISA-66118341667', '421732', '14493810',
        '500mg+125mg', 1, 1, 500.0, 'mg', '1 comprimido a cada 8 horas', 'Tomar com alimentos',
        3, 1, 'Genérico', NULL, 'J01CR02',
        '66118341667', '2018-07-23', '2030-05-08', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        8.0, 15.0, false, false, NULL, 14, 'Manter na embalagem original. Não expor ao sol.',
        'Antibiótico beta-lactâmico', 'Infecções bacterianas resistentes', 'Completar tratamento'
    )
    ON CONFLICT DO NOTHING;

    -- Advil 200mg (Ibuprofeno)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Ibuprofeno', 'Advil 200mg', 'Ibuprofeno', 'MED-0064', '194200', 'ANVISA-86930837696', '757385', '19506606',
        '200mg', 1, 1, 200.0, 'mg', '1 comprimido a cada 8 horas', 'Tomar com alimentos',
        2, 1, 'Genérico', NULL, 'M01AE01',
        '86930837696', '2017-01-14', '2030-01-13', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar tontura. Evitar atividades que requerem atenção.',
        8.0, 25.0, false, false, NULL, 60, NULL,
        'Anti-inflamatório não esteroidal', 'Dor, inflamação e febre', 'Evitar uso prolongado'
    )
    ON CONFLICT DO NOTHING;

    -- Advil Suspensão (Ibuprofeno)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Ibuprofeno', 'Advil Suspensão', 'Ibuprofeno', 'MED-0065', '485668', 'ANVISA-36361849639', '424744', '70166072',
        '100mg/5ml', 4, 1, 100.0, 'mg', 'Conforme peso', 'Agitar antes de usar',
        2, 4, 'Genérico', NULL, 'M01AE01',
        '36361849639', '2017-09-29', '2029-07-25', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        8.0, 25.0, true, true, 'Armazenar em local seco e arejado, protegido da luz.', 60, 'Manter longe do alcance de crianças.',
        'Anti-inflamatório não esteroidal', 'Dor e febre em crianças', 'Evitar uso prolongado'
    )
    ON CONFLICT DO NOTHING;

    -- Losec 40mg (Omeprazol)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Omeprazol', 'Losec 40mg', 'Omeprazol', 'MED-0066', '718842', 'ANVISA-59822651036', '564262', '62699586',
        '40mg', 2, 1, 40.0, 'mg', '1 cápsula pela manhã em jejum', 'Tomar 30min antes do café',
        14, 1, 'Genérico', NULL, 'A02BC01',
        '59822651036', '2020-10-02', '2027-11-04', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        2.0, 8.0, false, true, NULL, 90, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Inibidor de bomba de prótons', 'Gastrite, úlcera', 'Tomar em jejum'
    )
    ON CONFLICT DO NOTHING;

    -- Cozaar 25mg (Losartana)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Losartana', 'Cozaar 25mg', 'Losartana potássica', 'MED-0067', '835516', 'ANVISA-23134873803', '365863', '94397076',
        '25mg', 1, 1, 25.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        21, 1, 'Genérico', NULL, 'C09CA01',
        '23134873803', '2018-05-08', '2030-01-31', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        2.0, 30.0, true, false, 'Manter em geladeira (2-8°C). Não congelar.', 7, NULL,
        'Anti-hipertensivo', 'Hipertensão arterial', 'Tomar sempre no mesmo horário'
    )
    ON CONFLICT DO NOTHING;

    -- Cozaar 100mg (Losartana)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Losartana', 'Cozaar 100mg', 'Losartana potássica', 'MED-0068', '552175', 'ANVISA-91025774025', '204765', '20136659',
        '100mg', 1, 1, 100.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        21, 1, 'Genérico', NULL, 'C09CA01',
        '91025774025', '2022-01-28', '2028-05-31', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        2.0, 15.0, true, true, 'Armazenar em local seco e arejado, protegido da luz.', NULL, 'Manter na embalagem original. Não expor ao sol.',
        'Anti-hipertensivo', 'Hipertensão arterial', 'Tomar sempre no mesmo horário'
    )
    ON CONFLICT DO NOTHING;

    -- Glifage 850mg (Metformina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Metformina', 'Glifage 850mg', 'Metformina cloridrato', 'MED-0069', '863804', 'ANVISA-58223326188', '594787', '75416452',
        '850mg', 1, 1, 850.0, 'mg', '1 comprimido 2x ao dia', 'Tomar com alimentos',
        20, 1, 'Genérico', NULL, 'A10BA02',
        '58223326188', '2020-04-08', '2030-10-14', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, NULL,
        8.0, NULL, true, false, 'Armazenar em local seco e arejado, protegido da luz.', 90, NULL,
        'Antidiabético', 'Diabetes tipo 2', 'Pode causar desconforto gastrointestinal'
    )
    ON CONFLICT DO NOTHING;

    -- Glifage 1000mg (Metformina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Metformina', 'Glifage 1000mg', 'Metformina cloridrato', 'MED-0070', '854217', 'ANVISA-14108163127', '404664', '10186929',
        '1000mg', 1, 1, 1000.0, 'mg', '1 comprimido 2x ao dia', 'Tomar com alimentos',
        20, 1, 'Genérico', NULL, 'A10BA02',
        '14108163127', '2024-04-08', '2028-05-12', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, NULL,
        2.0, 25.0, true, true, NULL, 7, NULL,
        'Antidiabético', 'Diabetes tipo 2', 'Pode causar desconforto gastrointestinal'
    )
    ON CONFLICT DO NOTHING;

    -- Dorflex Gotas (Dorflex)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Dorflex', 'Dorflex Gotas', 'Cafeína + Carisoprodol + Dipirona', 'MED-0071', '604653', 'ANVISA-43172519243', '736040', '47223140',
        '20 gotas', 11, 1, 20.0, 'gota', '20 gotas a cada 8 horas', 'Tomar com água',
        1, 6, 'Genérico', NULL, 'M03BA03',
        '43172519243', '2016-02-14', '2030-04-02', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', NULL,
        8.0, NULL, false, false, NULL, 14, NULL,
        'Relaxante muscular + Analgésico', 'Dor muscular, tensão', 'Pode causar sonolência'
    )
    ON CONFLICT DO NOTHING;

    -- Buscopan Composto (Buscopan)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Buscopan', 'Buscopan Composto', 'Butilescopolamina + Dipirona', 'MED-0072', '317731', 'ANVISA-99710643209', '872459', '19524329',
        '1 comprimido', 1, 1, 1.0, 'comp', '1 comprimido a cada 8 horas', 'Tomar com água',
        1, 1, 'Genérico', NULL, 'A03BB01',
        '99710643209', '2023-12-18', '2028-02-10', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        8.0, 15.0, false, false, 'Manter em geladeira (2-8°C). Não congelar.', 60, 'Manter longe do alcance de crianças.',
        'Antiespasmódico + Analgésico', 'Cólica com dor', 'Alivio rápido de cólicas'
    )
    ON CONFLICT DO NOTHING;

    -- Rocephin (Ceftriaxona)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Ceftriaxona', 'Rocephin', 'Ceftriaxona sódica', 'MED-0073', '358976', 'ANVISA-16344670567', '678294', '86661542',
        '1g', 8, 7, 1000.0, 'mg', '1 ampola IM/IV a cada 24h', 'Aplicar via intramuscular ou intravenosa',
        3, 8, 'Genérico', NULL, 'J01DD04',
        '16344670567', '2018-07-28', '2029-03-21', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        8.0, 8.0, true, false, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 14, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Antibiótico cefalosporina', 'Infecções bacterianas graves', 'Aplicação parenteral'
    )
    ON CONFLICT DO NOTHING;

    -- Klacid (Claritromicina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Claritromicina', 'Klacid', 'Claritromicina', 'MED-0074', '879597', 'ANVISA-15452742347', '918861', '84733815',
        '500mg', 1, 1, 500.0, 'mg', '1 comprimido 2x ao dia', 'Tomar com alimentos',
        3, 1, 'Genérico', NULL, 'J01FA09',
        '15452742347', '2017-02-06', '2028-02-05', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        15.0, 15.0, false, true, 'Manter em geladeira (2-8°C). Não congelar.', 7, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Antibiótico macrolídeo', 'Infecções respiratórias', 'Tomar com alimentos'
    )
    ON CONFLICT DO NOTHING;

    -- Vibramicina (Doxiciclina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Doxiciclina', 'Vibramicina', 'Doxiciclina hiclato', 'MED-0075', '807631', 'ANVISA-73397942716', '639761', '46551183',
        '100mg', 1, 1, 100.0, 'mg', '1 comprimido 2x ao dia', 'Tomar com água',
        3, 1, 'Genérico', NULL, 'J01AA02',
        '73397942716', '2023-11-19', '2030-02-27', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        15.0, NULL, true, false, 'Manter em geladeira (2-8°C). Não congelar.', 14, 'Manter longe do alcance de crianças.',
        'Antibiótico tetraciclina', 'Infecções bacterianas', 'Evitar exposição solar'
    )
    ON CONFLICT DO NOTHING;

    -- Nisulid (Nimesulida)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Nimesulida', 'Nisulid', 'Nimesulida', 'MED-0076', '666479', 'ANVISA-17847493847', '715625', '32024235',
        '100mg', 1, 1, 100.0, 'mg', '1 comprimido 2x ao dia', 'Tomar com alimentos',
        2, 1, 'Genérico', NULL, 'M01AX17',
        '17847493847', '2024-01-03', '2027-03-03', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        8.0, 15.0, true, true, 'Manter em geladeira (2-8°C). Não congelar.', 60, NULL,
        'Anti-inflamatório não esteroidal', 'Dor e inflamação', 'Evitar uso prolongado'
    )
    ON CONFLICT DO NOTHING;

    -- Tilatil (Tenoxicam)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Tenoxicam', 'Tilatil', 'Tenoxicam', 'MED-0077', '206003', 'ANVISA-17139810067', '802278', '86447808',
        '20mg', 1, 1, 20.0, 'mg', '1 comprimido ao dia', 'Tomar com alimentos',
        2, 1, 'Genérico', NULL, 'M01AC02',
        '17139810067', '2019-09-15', '2030-11-29', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', NULL,
        8.0, NULL, false, true, 'Manter em geladeira (2-8°C). Não congelar.', 60, 'Manter longe do alcance de crianças.',
        'Anti-inflamatório não esteroidal', 'Dor e inflamação', 'Evitar uso prolongado'
    )
    ON CONFLICT DO NOTHING;

    -- Capoten (Captopril)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Captopril', 'Capoten', 'Captopril', 'MED-0078', '630290', 'ANVISA-51722374946', '928833', '38601704',
        '25mg', 1, 1, 25.0, 'mg', '1 comprimido 2-3x ao dia', 'Tomar com água',
        21, 1, 'Genérico', NULL, 'C09AA01',
        '51722374946', '2020-11-27', '2027-10-14', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, NULL,
        2.0, 8.0, true, false, 'Armazenar em local seco e arejado, protegido da luz.', 14, 'Manter na embalagem original. Não expor ao sol.',
        'Inibidor de ECA', 'Hipertensão arterial', 'Pode causar tosse seca'
    )
    ON CONFLICT DO NOTHING;

    -- Tenormin (Atenolol)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Atenolol', 'Tenormin', 'Atenolol', 'MED-0079', '172261', 'ANVISA-89601317427', '292657', '34702113',
        '50mg', 1, 1, 50.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        21, 1, 'Genérico', NULL, 'C07AB03',
        '89601317427', '2016-12-28', '2030-04-18', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        15.0, 15.0, false, false, 'Armazenar em local seco e arejado, protegido da luz.', 14, NULL,
        'Betabloqueador', 'Hipertensão, arritmias', 'Não suspender abruptamente'
    )
    ON CONFLICT DO NOTHING;

    -- Dilatrend (Carvedilol)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Carvedilol', 'Dilatrend', 'Carvedilol', 'MED-0080', '284286', 'ANVISA-77169576867', '811199', '86624141',
        '25mg', 1, 1, 25.0, 'mg', '1 comprimido 2x ao dia', 'Tomar com água',
        21, 1, 'Genérico', NULL, 'C07AG02',
        '77169576867', '2018-01-09', '2027-06-16', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        NULL, 30.0, true, false, NULL, 7, NULL,
        'Betabloqueador', 'Hipertensão, insuficiência cardíaca', 'Não suspender abruptamente'
    )
    ON CONFLICT DO NOTHING;

    -- Diamicron (Gliclazida)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Gliclazida', 'Diamicron', 'Gliclazida', 'MED-0081', '162586', 'ANVISA-94366116836', '386948', '83979345',
        '80mg', 1, 1, 80.0, 'mg', '1 comprimido antes do café', 'Tomar antes das refeições',
        20, 1, 'Genérico', NULL, 'A10BB09',
        '94366116836', '2024-07-22', '2028-05-26', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar tontura. Evitar atividades que requerem atenção.',
        2.0, 25.0, true, true, NULL, 90, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Antidiabético', 'Diabetes tipo 2', 'Risco de hipoglicemia'
    )
    ON CONFLICT DO NOTHING;

    -- Actos (Pioglitazona)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Pioglitazona', 'Actos', 'Pioglitazona cloridrato', 'MED-0082', '653209', 'ANVISA-69833544469', '344648', '93397568',
        '30mg', 1, 1, 30.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        20, 1, 'Genérico', NULL, 'A10BG03',
        '69833544469', '2024-02-13', '2027-05-28', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        NULL, 15.0, false, false, 'Armazenar em local seco e arejado, protegido da luz.', 7, NULL,
        'Antidiabético', 'Diabetes tipo 2', 'Pode causar ganho de peso'
    )
    ON CONFLICT DO NOTHING;

    -- Cipramil (Citalopram)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Citalopram', 'Cipramil', 'Citalopram bromidrato', 'MED-0083', '431820', 'ANVISA-71955212673', '958549', '63972561',
        '20mg', 1, 1, 20.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        10, 1, 'Genérico', NULL, 'N06AB04',
        '71955212673', '2019-02-28', '2030-03-03', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar tontura. Evitar atividades que requerem atenção.',
        NULL, 30.0, false, false, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', NULL, 'Manter na embalagem original. Não expor ao sol.',
        'Antidepressivo ISRS', 'Depressão, ansiedade', 'Efeito após 2-4 semanas'
    )
    ON CONFLICT DO NOTHING;

    -- Efexor (Venlafaxina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Venlafaxina', 'Efexor', 'Venlafaxina cloridrato', 'MED-0084', '615779', 'ANVISA-71310102656', '929172', '72189845',
        '75mg', 2, 1, 75.0, 'mg', '1 cápsula ao dia', 'Tomar com água',
        10, 1, 'Genérico', NULL, 'N06AX16',
        '71310102656', '2017-03-17', '2028-11-27', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        2.0, 25.0, true, true, NULL, 14, 'Manter longe do alcance de crianças.',
        'Antidepressivo', 'Depressão, ansiedade', 'Efeito após 2-4 semanas'
    )
    ON CONFLICT DO NOTHING;

    -- Lexotan (Bromazepam)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Bromazepam', 'Lexotan', 'Bromazepam', 'MED-0085', '686531', 'ANVISA-79159830069', '574083', '28510051',
        '6mg', 1, 1, 6.0, 'mg', 'Conforme prescrição médica', 'Tomar com água',
        11, 1, 'Genérico', NULL, 'N05BA08',
        '79159830069', '2016-05-19', '2028-04-24', 4, true, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        8.0, 30.0, false, true, NULL, 14, 'Manter na embalagem original. Não expor ao sol.',
        'Ansiolítico benzodiazepínico', 'Ansiedade', 'Pode causar dependência'
    )
    ON CONFLICT DO NOTHING;

    -- Lorax (Lorazepam)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Lorazepam', 'Lorax', 'Lorazepam', 'MED-0086', '512185', 'ANVISA-35388741014', '244428', '49746046',
        '2mg', 1, 1, 2.0, 'mg', 'Conforme prescrição médica', 'Tomar com água',
        11, 1, 'Genérico', NULL, 'N05BA06',
        '35388741014', '2017-01-08', '2029-03-05', 4, true, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        15.0, 15.0, false, true, 'Manter em geladeira (2-8°C). Não congelar.', 90, 'Manter na embalagem original. Não expor ao sol.',
        'Ansiolítico benzodiazepínico', 'Ansiedade, insônia', 'Pode causar dependência'
    )
    ON CONFLICT DO NOTHING;

    -- Cortisona (Hidrocortisona)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Hidrocortisona', 'Cortisona', 'Hidrocortisona', 'MED-0087', '661550', 'ANVISA-96116173034', '672177', '24368901',
        '20mg', 1, 1, 20.0, 'mg', 'Conforme prescrição médica', 'Tomar com alimentos',
        9, 1, 'Genérico', NULL, 'H02AB09',
        '96116173034', '2023-12-06', '2030-11-09', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        2.0, 15.0, false, false, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 14, NULL,
        'Corticoide sistêmico', 'Inflamações, alergias', 'Não suspender abruptamente'
    )
    ON CONFLICT DO NOTHING;

    -- Celestone (Betametasona)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Betametasona', 'Celestone', 'Betametasona', 'MED-0088', '832846', 'ANVISA-58301846538', '687233', '37517046',
        '0,5mg', 1, 1, 0.5, 'mg', 'Conforme prescrição médica', 'Tomar com alimentos',
        9, 1, 'Genérico', NULL, 'H02AB01',
        '58301846538', '2016-01-09', '2027-12-26', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        2.0, 15.0, false, true, 'Armazenar em local seco e arejado, protegido da luz.', 30, NULL,
        'Corticoide sistêmico', 'Inflamações, alergias', 'Não suspender abruptamente'
    )
    ON CONFLICT DO NOTHING;

    -- Redoxon (Vitamina C)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Vitamina C', 'Redoxon', 'Ácido ascórbico', 'MED-0089', '967189', 'ANVISA-80824404706', '758357', '42089763',
        '1g', 1, 1, 1000.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        17, 1, 'Genérico', NULL, 'A11GA01',
        '80824404706', '2024-09-13', '2029-10-30', 1, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        NULL, 8.0, true, true, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 90, 'Manter na embalagem original. Não expor ao sol.',
        'Vitamina C', 'Deficiência de vitamina C', 'Tomar com água'
    )
    ON CONFLICT DO NOTHING;

    -- Ephynal (Vitamina E)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Vitamina E', 'Ephynal', 'Alfa-tocoferol', 'MED-0090', '539041', 'ANVISA-50636500779', '569444', '75418423',
        '400UI', 2, 1, 400.0, 'UI', '1 cápsula ao dia', 'Tomar com alimentos',
        17, 1, 'Genérico', NULL, 'A11HA03',
        '50636500779', '2019-08-15', '2027-11-15', 1, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        2.0, 8.0, false, true, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 7, 'Manter longe do alcance de crianças.',
        'Vitamina E', 'Antioxidante', 'Tomar com alimentos gordurosos'
    )
    ON CONFLICT DO NOTHING;

    -- Ferronil (Ferro)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Ferro', 'Ferronil', 'Sulfato ferroso', 'MED-0091', '673617', 'ANVISA-25052459966', '483181', '27302294',
        '40mg', 1, 1, 40.0, 'mg', '1 comprimido ao dia', 'Tomar em jejum',
        18, 1, 'Genérico', NULL, 'B03AA07',
        '25052459966', '2020-08-04', '2027-10-01', 1, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        2.0, 8.0, true, false, 'Armazenar em local seco e arejado, protegido da luz.', 14, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Suplemento de ferro', 'Anemia ferropriva', 'Tomar em jejum'
    )
    ON CONFLICT DO NOTHING;

    -- Calcitran (Cálcio)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Cálcio', 'Calcitran', 'Carbonato de cálcio', 'MED-0092', '585047', 'ANVISA-35038781620', '937204', '97079982',
        '500mg', 1, 1, 500.0, 'mg', '1 comprimido 2x ao dia', 'Tomar com alimentos',
        18, 1, 'Genérico', NULL, 'A12AA04',
        '35038781620', '2023-08-12', '2028-10-28', 1, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, NULL,
        NULL, 15.0, true, true, 'Manter em geladeira (2-8°C). Não congelar.', 14, NULL,
        'Suplemento de cálcio', 'Osteoporose', 'Tomar com alimentos'
    )
    ON CONFLICT DO NOTHING;

    -- Voltaren Emulgel (Diclofenaco Gel)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Diclofenaco Gel', 'Voltaren Emulgel', 'Diclofenaco dietilamônio', 'MED-0093', '788571', 'ANVISA-84984243833', '648768', '17429048',
        '1%', 11, 5, 1.0, '%', 'Aplicar 3-4x ao dia', 'Aplicar na área afetada',
        2, 11, 'Genérico', NULL, 'M02AA15',
        '84984243833', '2016-04-02', '2029-10-30', 1, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        15.0, 15.0, true, false, 'Manter em geladeira (2-8°C). Não congelar.', 90, NULL,
        'Anti-inflamatório tópico', 'Dor e inflamação local', 'Aplicar na área afetada'
    )
    ON CONFLICT DO NOTHING;

    -- Pielotopic (Hidrocortisona Creme)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Hidrocortisona Creme', 'Pielotopic', 'Hidrocortisona', 'MED-0094', '321206', 'ANVISA-39344691005', '384753', '52993208',
        '1%', 9, 5, 1.0, '%', 'Aplicar 2x ao dia', 'Aplicar na área afetada',
        9, 9, 'Genérico', NULL, 'D07AA02',
        '39344691005', '2020-07-20', '2027-02-23', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        15.0, 30.0, false, true, 'Manter em geladeira (2-8°C). Não congelar.', 14, NULL,
        'Corticoide tópico', 'Dermatites, alergias', 'Aplicar na área afetada'
    )
    ON CONFLICT DO NOTHING;

    -- Daktarin (Miconazol Creme)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Miconazol Creme', 'Daktarin', 'Miconazol nitrato', 'MED-0095', '445266', 'ANVISA-19256701509', '158790', '58031998',
        '2%', 9, 5, 2.0, '%', 'Aplicar 2x ao dia', 'Aplicar na área afetada',
        5, 9, 'Genérico', NULL, 'D01AC02',
        '19256701509', '2018-02-28', '2028-07-25', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        15.0, 30.0, false, false, NULL, 30, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Antifúngico tópico', 'Micoses', 'Aplicar na área afetada'
    )
    ON CONFLICT DO NOTHING;

    -- Maxidex (Dexametasona Colírio)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Dexametasona Colírio', 'Maxidex', 'Dexametasona', 'MED-0096', '906342', 'ANVISA-83179966947', '416537', '35345524',
        '0,1%', 11, 17, 0.1, '%', '1-2 gotas 4x ao dia', 'Aplicar no olho',
        9, 17, 'Genérico', NULL, 'S01BA01',
        '83179966947', '2023-08-10', '2028-03-05', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        15.0, 8.0, true, false, 'Manter em geladeira (2-8°C). Não congelar.', 60, 'Manter longe do alcance de crianças.',
        'Corticoide oftálmico', 'Inflamação ocular', 'Aplicar no olho'
    )
    ON CONFLICT DO NOTHING;

    -- Tobrex (Tobramicina Colírio)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Tobramicina Colírio', 'Tobrex', 'Tobramicina', 'MED-0097', '399835', 'ANVISA-42845730546', '974370', '88950297',
        '0,3%', 11, 17, 0.3, '%', '1-2 gotas 4x ao dia', 'Aplicar no olho',
        3, 17, 'Genérico', NULL, 'S01AA12',
        '42845730546', '2021-04-13', '2028-04-24', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', NULL,
        2.0, 15.0, false, false, 'Manter em geladeira (2-8°C). Não congelar.', 90, 'Manter longe do alcance de crianças.',
        'Antibiótico oftálmico', 'Infecção ocular', 'Aplicar no olho'
    )
    ON CONFLICT DO NOTHING;

    -- Decadron Injetável (Dexametasona Injetável)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Dexametasona Injetável', 'Decadron Injetável', 'Dexametasona fosfato', 'MED-0098', '456979', 'ANVISA-66541385834', '256552', '96576506',
        '4mg/ml', 8, 7, 4.0, 'mg', 'Conforme prescrição médica', 'Aplicar via intramuscular',
        9, 8, 'Genérico', NULL, 'H02AB02',
        '66541385834', '2021-11-04', '2030-04-09', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        8.0, 30.0, true, true, 'Manter em geladeira (2-8°C). Não congelar.', 14, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Corticoide injetável', 'Inflamações, alergias', 'Aplicação parenteral'
    )
    ON CONFLICT DO NOTHING;

    -- Voltaren Injetável (Diclofenaco Injetável)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Diclofenaco Injetável', 'Voltaren Injetável', 'Diclofenaco sódico', 'MED-0099', '932330', 'ANVISA-41254279943', '253002', '66045551',
        '75mg/3ml', 8, 7, 75.0, 'mg', '1 ampola IM a cada 12h', 'Aplicar via intramuscular',
        2, 8, 'Genérico', NULL, 'M01AB05',
        '41254279943', '2020-09-12', '2029-11-03', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        2.0, NULL, true, true, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', NULL, 'Manter longe do alcance de crianças.',
        'Anti-inflamatório injetável', 'Dor e inflamação', 'Aplicação parenteral'
    )
    ON CONFLICT DO NOTHING;

    -- Zoloft 100mg (Sertralina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Sertralina', 'Zoloft 100mg', 'Sertralina cloridrato', 'MED-0100', '114336', 'ANVISA-68460833329', '203904', '95298081',
        '100mg', 1, 1, 100.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        10, 1, 'Genérico', NULL, 'N06AB06',
        '68460833329', '2021-08-30', '2026-12-12', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', NULL,
        NULL, 30.0, true, true, 'Manter em geladeira (2-8°C). Não congelar.', 60, NULL,
        'Antidepressivo ISRS', 'Depressão, ansiedade', 'Efeito após 2-4 semanas'
    )
    ON CONFLICT DO NOTHING;

    -- Prozac 40mg (Fluoxetina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Fluoxetina', 'Prozac 40mg', 'Fluoxetina cloridrato', 'MED-0101', '927060', 'ANVISA-83488464983', '519748', '77405949',
        '40mg', 2, 1, 40.0, 'mg', '1 cápsula ao dia', 'Tomar com água',
        10, 1, 'Genérico', NULL, 'N06AB03',
        '83488464983', '2020-12-29', '2026-12-29', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', NULL,
        NULL, 25.0, true, false, 'Armazenar em local seco e arejado, protegido da luz.', 60, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Antidepressivo ISRS', 'Depressão, ansiedade', 'Efeito após 2-4 semanas'
    )
    ON CONFLICT DO NOTHING;

    -- Tryptanol 50mg (Amitriptilina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Amitriptilina', 'Tryptanol 50mg', 'Amitriptilina cloridrato', 'MED-0102', '175337', 'ANVISA-68053594635', '150600', '45390300',
        '50mg', 1, 1, 50.0, 'mg', '1 comprimido ao deitar', 'Tomar com água',
        10, 1, 'Genérico', NULL, 'N06AA09',
        '68053594635', '2019-04-13', '2028-02-09', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        NULL, 25.0, true, false, 'Manter em geladeira (2-8°C). Não congelar.', 60, NULL,
        'Antidepressivo tricíclico', 'Depressão, dor crônica', 'Pode causar sonolência'
    )
    ON CONFLICT DO NOTHING;

    -- Valium 5mg (Diazepam)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Diazepam', 'Valium 5mg', 'Diazepam', 'MED-0103', '672980', 'ANVISA-75816328770', '207510', '85741212',
        '5mg', 1, 1, 5.0, 'mg', 'Conforme prescrição médica', 'Tomar com água',
        11, 1, 'Genérico', NULL, 'N05BA01',
        '75816328770', '2022-05-09', '2029-07-14', 4, true, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', NULL,
        8.0, 15.0, false, true, NULL, 14, 'Manter longe do alcance de crianças.',
        'Ansiolítico benzodiazepínico', 'Ansiedade, insônia', 'Pode causar dependência'
    )
    ON CONFLICT DO NOTHING;

    -- Frontal 1mg (Alprazolam)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Alprazolam', 'Frontal 1mg', 'Alprazolam', 'MED-0104', '578701', 'ANVISA-86666347339', '239965', '88632080',
        '1mg', 1, 1, 1.0, 'mg', 'Conforme prescrição médica', 'Tomar com água',
        11, 1, 'Genérico', NULL, 'N05BA12',
        '86666347339', '2018-08-15', '2030-06-15', 4, true, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        8.0, 8.0, false, true, NULL, 60, NULL,
        'Ansiolítico benzodiazepínico', 'Ansiedade, pânico', 'Pode causar dependência'
    )
    ON CONFLICT DO NOTHING;

    -- Rivotril 0,5mg (Clonazepam)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Clonazepam', 'Rivotril 0,5mg', 'Clonazepam', 'MED-0105', '754682', 'ANVISA-52863399288', '344000', '57800992',
        '0,5mg', 1, 1, 0.5, 'mg', 'Conforme prescrição médica', 'Tomar com água',
        11, 1, 'Genérico', NULL, 'N03AE01',
        '52863399288', '2023-09-26', '2027-10-14', 4, true, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar tontura. Evitar atividades que requerem atenção.',
        15.0, NULL, true, true, NULL, 30, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Ansiolítico e anticonvulsivante', 'Ansiedade, convulsões', 'Pode causar dependência'
    )
    ON CONFLICT DO NOTHING;

    -- Losec 10mg (Omeprazol)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Omeprazol', 'Losec 10mg', 'Omeprazol', 'MED-0106', '571094', 'ANVISA-86947134964', '134665', '46840383',
        '10mg', 2, 1, 10.0, 'mg', '1 cápsula pela manhã em jejum', 'Tomar 30min antes do café',
        14, 1, 'Genérico', NULL, 'A02BC01',
        '86947134964', '2020-11-30', '2030-10-27', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', NULL,
        2.0, 8.0, false, false, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 60, 'Manter na embalagem original. Não expor ao sol.',
        'Inibidor de bomba de prótons', 'Gastrite, úlcera', 'Tomar em jejum'
    )
    ON CONFLICT DO NOTHING;

    -- Pantozol 20mg (Pantoprazol)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Pantoprazol', 'Pantozol 20mg', 'Pantoprazol sódico', 'MED-0107', '449819', 'ANVISA-49102947854', '729266', '56707119',
        '20mg', 1, 1, 20.0, 'mg', '1 comprimido pela manhã', 'Tomar com água',
        14, 1, 'Genérico', NULL, 'A02BC02',
        '49102947854', '2020-11-25', '2030-09-19', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        8.0, 25.0, true, true, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 90, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Inibidor de bomba de prótons', 'Gastrite, úlcera', 'Tomar em jejum'
    )
    ON CONFLICT DO NOTHING;

    -- Nexium (Esomeprazol)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Esomeprazol', 'Nexium', 'Esomeprazol magnésico', 'MED-0108', '959387', 'ANVISA-20155423970', '353816', '24088449',
        '40mg', 2, 1, 40.0, 'mg', '1 cápsula pela manhã em jejum', 'Tomar 30min antes do café',
        14, 1, 'Genérico', NULL, 'A02BC05',
        '20155423970', '2020-08-27', '2029-11-02', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, NULL,
        NULL, NULL, true, false, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', NULL, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Inibidor de bomba de prótons', 'Gastrite, úlcera', 'Tomar em jejum'
    )
    ON CONFLICT DO NOTHING;

    -- Lanzul (Lansoprazol)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Lansoprazol', 'Lanzul', 'Lansoprazol', 'MED-0109', '697184', 'ANVISA-61560728807', '896770', '77235903',
        '30mg', 2, 1, 30.0, 'mg', '1 cápsula pela manhã em jejum', 'Tomar 30min antes do café',
        14, 1, 'Genérico', NULL, 'A02BC03',
        '61560728807', '2020-11-20', '2027-10-29', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        NULL, 15.0, true, false, 'Manter em geladeira (2-8°C). Não congelar.', 30, 'Manter na embalagem original. Não expor ao sol.',
        'Inibidor de bomba de prótons', 'Gastrite, úlcera', 'Tomar em jejum'
    )
    ON CONFLICT DO NOTHING;

    -- Antak 300mg (Ranitidina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Ranitidina', 'Antak 300mg', 'Ranitidina cloridrato', 'MED-0110', '990282', 'ANVISA-80595447676', '218310', '89998222',
        '300mg', 1, 1, 300.0, 'mg', '1 comprimido ao deitar', 'Tomar com água',
        14, 1, 'Genérico', NULL, 'A02BA02',
        '80595447676', '2015-12-10', '2029-05-08', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        8.0, 25.0, true, false, NULL, NULL, 'Manter na embalagem original. Não expor ao sol.',
        'Bloqueador H2', 'Gastrite, úlcera', 'Tomar antes das refeições'
    )
    ON CONFLICT DO NOTHING;

    -- Pepcid (Famotidina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Famotidina', 'Pepcid', 'Famotidina', 'MED-0111', '772931', 'ANVISA-45478316722', '924578', '30775199',
        '40mg', 1, 1, 40.0, 'mg', '1 comprimido ao deitar', 'Tomar com água',
        14, 1, 'Genérico', NULL, 'A02BA03',
        '45478316722', '2020-05-22', '2029-01-10', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        15.0, 30.0, true, true, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', NULL, 'Manter longe do alcance de crianças.',
        'Bloqueador H2', 'Gastrite, úlcera', 'Tomar antes das refeições'
    )
    ON CONFLICT DO NOTHING;

    -- Cozaar 25mg (Losartana)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Losartana', 'Cozaar 25mg', 'Losartana potássica', 'MED-0112', '845699', 'ANVISA-51073729864', '324968', '86162443',
        '25mg', 1, 1, 25.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        21, 1, 'Genérico', NULL, 'C09CA01',
        '51073729864', '2021-08-20', '2027-04-27', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        8.0, NULL, false, true, NULL, 60, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Anti-hipertensivo', 'Hipertensão arterial', 'Tomar sempre no mesmo horário'
    )
    ON CONFLICT DO NOTHING;

    -- Cozaar 100mg (Losartana)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Losartana', 'Cozaar 100mg', 'Losartana potássica', 'MED-0113', '468207', 'ANVISA-45289025633', '593686', '21190503',
        '100mg', 1, 1, 100.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        21, 1, 'Genérico', NULL, 'C09CA01',
        '45289025633', '2017-06-17', '2029-06-04', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        2.0, NULL, true, true, 'Manter em geladeira (2-8°C). Não congelar.', NULL, NULL,
        'Anti-hipertensivo', 'Hipertensão arterial', 'Tomar sempre no mesmo horário'
    )
    ON CONFLICT DO NOTHING;

    -- Diovan (Valsartana)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Valsartana', 'Diovan', 'Valsartana', 'MED-0114', '828350', 'ANVISA-59512456241', '666847', '90316148',
        '160mg', 1, 1, 160.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        21, 1, 'Genérico', NULL, 'C09CA03',
        '59512456241', '2022-12-15', '2030-10-01', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, NULL,
        8.0, 30.0, false, true, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 14, 'Manter na embalagem original. Não expor ao sol.',
        'Anti-hipertensivo', 'Hipertensão arterial', 'Tomar sempre no mesmo horário'
    )
    ON CONFLICT DO NOTHING;

    -- Aprovel (Irbesartana)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Irbesartana', 'Aprovel', 'Irbesartana', 'MED-0115', '908734', 'ANVISA-60954788927', '582028', '63803679',
        '150mg', 1, 1, 150.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        21, 1, 'Genérico', NULL, 'C09CA04',
        '60954788927', '2024-07-01', '2030-01-17', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        NULL, 30.0, false, true, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 7, NULL,
        'Anti-hipertensivo', 'Hipertensão arterial', 'Tomar sempre no mesmo horário'
    )
    ON CONFLICT DO NOTHING;

    -- Micardis (Telmisartana)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Telmisartana', 'Micardis', 'Telmisartana', 'MED-0116', '944240', 'ANVISA-66909325418', '743452', '27405959',
        '80mg', 1, 1, 80.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        21, 1, 'Genérico', NULL, 'C09CA07',
        '66909325418', '2017-03-10', '2027-11-27', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', NULL,
        2.0, NULL, false, false, 'Manter em geladeira (2-8°C). Não congelar.', 7, 'Manter longe do alcance de crianças.',
        'Anti-hipertensivo', 'Hipertensão arterial', 'Tomar sempre no mesmo horário'
    )
    ON CONFLICT DO NOTHING;

    -- Norvasc 10mg (Amlodipina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Amlodipina', 'Norvasc 10mg', 'Amlodipina besilato', 'MED-0117', '840891', 'ANVISA-84020035251', '154567', '91934135',
        '10mg', 1, 1, 10.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        21, 1, 'Genérico', NULL, 'C08CA01',
        '84020035251', '2020-03-19', '2029-03-22', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, NULL,
        15.0, 15.0, false, true, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 7, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Bloqueador de canais de cálcio', 'Hipertensão arterial', 'Pode causar edema'
    )
    ON CONFLICT DO NOTHING;

    -- Adalat (Nifedipina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Nifedipina', 'Adalat', 'Nifedipina', 'MED-0118', '627139', 'ANVISA-24455434841', '242137', '35590781',
        '20mg', 1, 1, 20.0, 'mg', '1 comprimido 2x ao dia', 'Tomar com água',
        21, 1, 'Genérico', NULL, 'C08CA05',
        '24455434841', '2019-07-05', '2028-05-05', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, NULL,
        NULL, 8.0, false, true, 'Manter em geladeira (2-8°C). Não congelar.', 14, 'Manter longe do alcance de crianças.',
        'Bloqueador de canais de cálcio', 'Hipertensão arterial', 'Pode causar cefaleia'
    )
    ON CONFLICT DO NOTHING;

    -- Isoptin (Verapamil)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Verapamil', 'Isoptin', 'Verapamil cloridrato', 'MED-0119', '668081', 'ANVISA-41346134513', '578357', '72449408',
        '80mg', 1, 1, 80.0, 'mg', '1 comprimido 3x ao dia', 'Tomar com água',
        21, 1, 'Genérico', NULL, 'C08DA01',
        '41346134513', '2022-10-26', '2030-09-16', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        2.0, NULL, true, true, NULL, 30, 'Manter na embalagem original. Não expor ao sol.',
        'Bloqueador de canais de cálcio', 'Hipertensão, arritmias', 'Pode causar constipação'
    )
    ON CONFLICT DO NOTHING;

    -- Renitec 5mg (Enalapril)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Enalapril', 'Renitec 5mg', 'Enalapril maleato', 'MED-0120', '384277', 'ANVISA-78366996466', '761056', '57654757',
        '5mg', 1, 1, 5.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        21, 1, 'Genérico', NULL, 'C09AA02',
        '78366996466', '2018-08-17', '2028-03-28', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        NULL, NULL, true, false, NULL, 7, 'Manter na embalagem original. Não expor ao sol.',
        'Inibidor de ECA', 'Hipertensão arterial', 'Pode causar tosse seca'
    )
    ON CONFLICT DO NOTHING;

    -- Renitec 20mg (Enalapril)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Enalapril', 'Renitec 20mg', 'Enalapril maleato', 'MED-0121', '776462', 'ANVISA-55350658545', '779813', '25001210',
        '20mg', 1, 1, 20.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        21, 1, 'Genérico', NULL, 'C09AA02',
        '55350658545', '2016-10-30', '2027-05-03', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        8.0, 8.0, true, false, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 7, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Inibidor de ECA', 'Hipertensão arterial', 'Pode causar tosse seca'
    )
    ON CONFLICT DO NOTHING;

    -- Zestril (Lisinopril)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Lisinopril', 'Zestril', 'Lisinopril', 'MED-0122', '681266', 'ANVISA-55804070976', '767863', '32067284',
        '10mg', 1, 1, 10.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        21, 1, 'Genérico', NULL, 'C09AA03',
        '55804070976', '2019-06-19', '2028-03-04', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        2.0, 8.0, true, true, NULL, NULL, NULL,
        'Inibidor de ECA', 'Hipertensão arterial', 'Pode causar tosse seca'
    )
    ON CONFLICT DO NOTHING;

    -- Tritace (Ramipril)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Ramipril', 'Tritace', 'Ramipril', 'MED-0123', '256917', 'ANVISA-69996436283', '730705', '29180127',
        '5mg', 2, 1, 5.0, 'mg', '1 cápsula ao dia', 'Tomar com água',
        21, 1, 'Genérico', NULL, 'C09AA05',
        '69996436283', '2017-08-31', '2027-12-25', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        15.0, NULL, true, false, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 7, 'Manter longe do alcance de crianças.',
        'Inibidor de ECA', 'Hipertensão arterial', 'Pode causar tosse seca'
    )
    ON CONFLICT DO NOTHING;

    -- Inderal 20mg (Propranolol)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Propranolol', 'Inderal 20mg', 'Propranolol cloridrato', 'MED-0124', '630339', 'ANVISA-63595059552', '466281', '34843623',
        '20mg', 1, 1, 20.0, 'mg', '1 comprimido 2x ao dia', 'Tomar com água',
        21, 1, 'Genérico', NULL, 'C07AA05',
        '63595059552', '2021-08-19', '2028-09-04', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', NULL,
        2.0, 8.0, false, false, 'Armazenar em local seco e arejado, protegido da luz.', NULL, 'Manter na embalagem original. Não expor ao sol.',
        'Betabloqueador', 'Hipertensão, arritmias', 'Não suspender abruptamente'
    )
    ON CONFLICT DO NOTHING;

    -- Inderal 80mg (Propranolol)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Propranolol', 'Inderal 80mg', 'Propranolol cloridrato', 'MED-0125', '810995', 'ANVISA-38452184248', '149277', '37035689',
        '80mg', 1, 1, 80.0, 'mg', '1 comprimido 2x ao dia', 'Tomar com água',
        21, 1, 'Genérico', NULL, 'C07AA05',
        '38452184248', '2016-05-29', '2028-09-29', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', NULL,
        2.0, 15.0, true, false, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 30, NULL,
        'Betabloqueador', 'Hipertensão, arritmias', 'Não suspender abruptamente'
    )
    ON CONFLICT DO NOTHING;

    -- Seloken (Metoprolol)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Metoprolol', 'Seloken', 'Metoprolol tartarato', 'MED-0126', '195739', 'ANVISA-41144644915', '376268', '37845175',
        '50mg', 1, 1, 50.0, 'mg', '1 comprimido 2x ao dia', 'Tomar com água',
        21, 1, 'Genérico', NULL, 'C07AB02',
        '41144644915', '2016-10-15', '2028-06-24', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', NULL,
        2.0, 30.0, true, false, NULL, 7, 'Manter na embalagem original. Não expor ao sol.',
        'Betabloqueador', 'Hipertensão, arritmias', 'Não suspender abruptamente'
    )
    ON CONFLICT DO NOTHING;

    -- Concor (Bisoprolol)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Bisoprolol', 'Concor', 'Bisoprolol fumarato', 'MED-0127', '618550', 'ANVISA-41859318832', '532218', '21150644',
        '5mg', 1, 1, 5.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        21, 1, 'Genérico', NULL, 'C07AB07',
        '41859318832', '2018-03-28', '2030-04-16', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar tontura. Evitar atividades que requerem atenção.',
        2.0, 15.0, false, true, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 30, NULL,
        'Betabloqueador', 'Hipertensão, arritmias', 'Não suspender abruptamente'
    )
    ON CONFLICT DO NOTHING;

    -- Nebilet (Nebivolol)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Nebivolol', 'Nebilet', 'Nebivolol cloridrato', 'MED-0128', '522941', 'ANVISA-60015262423', '259339', '30178006',
        '5mg', 1, 1, 5.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        21, 1, 'Genérico', NULL, 'C07AB12',
        '60015262423', '2021-04-01', '2028-03-12', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        NULL, 30.0, true, true, NULL, 30, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Betabloqueador', 'Hipertensão, arritmias', 'Não suspender abruptamente'
    )
    ON CONFLICT DO NOTHING;

    -- Clorana 12,5mg (Hidroclorotiazida)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Hidroclorotiazida', 'Clorana 12,5mg', 'Hidroclorotiazida', 'MED-0129', '698568', 'ANVISA-54462528491', '982856', '72447740',
        '12,5mg', 1, 1, 12.5, 'mg', '1 comprimido pela manhã', 'Tomar com água',
        22, 1, 'Genérico', NULL, 'C03AA03',
        '54462528491', '2021-02-27', '2030-03-09', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        2.0, NULL, true, true, 'Manter em geladeira (2-8°C). Não congelar.', 14, 'Manter longe do alcance de crianças.',
        'Diurético tiazídico', 'Hipertensão, edema', 'Pode causar hipocalemia'
    )
    ON CONFLICT DO NOTHING;

    -- Lasix 20mg (Furosemida)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Furosemida', 'Lasix 20mg', 'Furosemida', 'MED-0130', '733665', 'ANVISA-23021814353', '215082', '80432059',
        '20mg', 1, 1, 20.0, 'mg', '1 comprimido pela manhã', 'Tomar com água',
        22, 1, 'Genérico', NULL, 'C03CA01',
        '23021814353', '2021-10-21', '2030-07-15', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', NULL,
        15.0, 15.0, true, true, NULL, NULL, 'Manter longe do alcance de crianças.',
        'Diurético de alça', 'Edema, insuficiência cardíaca', 'Pode causar hipocalemia'
    )
    ON CONFLICT DO NOTHING;

    -- Lasix 80mg (Furosemida)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Furosemida', 'Lasix 80mg', 'Furosemida', 'MED-0131', '149542', 'ANVISA-61950864629', '579490', '35864612',
        '80mg', 1, 1, 80.0, 'mg', '1 comprimido pela manhã', 'Tomar com água',
        22, 1, 'Genérico', NULL, 'C03CA01',
        '61950864629', '2019-01-17', '2028-03-02', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        8.0, NULL, false, false, NULL, 60, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Diurético de alça', 'Edema, insuficiência cardíaca', 'Pode causar hipocalemia'
    )
    ON CONFLICT DO NOTHING;

    -- Aldactone (Espironolactona)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Espironolactona', 'Aldactone', 'Espironolactona', 'MED-0132', '516484', 'ANVISA-42624196153', '934163', '30959961',
        '25mg', 1, 1, 25.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        22, 1, 'Genérico', NULL, 'C03DA01',
        '42624196153', '2017-01-08', '2027-12-19', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        2.0, 30.0, false, false, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 14, NULL,
        'Diurético poupador de potássio', 'Edema, hipertensão', 'Pode causar hipercalemia'
    )
    ON CONFLICT DO NOTHING;

    -- Glifage 850mg (Metformina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Metformina', 'Glifage 850mg', 'Metformina cloridrato', 'MED-0133', '957890', 'ANVISA-46433011937', '482358', '53812288',
        '850mg', 1, 1, 850.0, 'mg', '1 comprimido 2x ao dia', 'Tomar com alimentos',
        20, 1, 'Genérico', NULL, 'A10BA02',
        '46433011937', '2020-01-03', '2028-09-25', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        8.0, 15.0, false, true, 'Armazenar em local seco e arejado, protegido da luz.', 14, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Antidiabético', 'Diabetes tipo 2', 'Pode causar desconforto gastrointestinal'
    )
    ON CONFLICT DO NOTHING;

    -- Glifage 1000mg (Metformina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Metformina', 'Glifage 1000mg', 'Metformina cloridrato', 'MED-0134', '624245', 'ANVISA-61774222048', '694028', '36647117',
        '1000mg', 1, 1, 1000.0, 'mg', '1 comprimido 2x ao dia', 'Tomar com alimentos',
        20, 1, 'Genérico', NULL, 'A10BA02',
        '61774222048', '2018-07-15', '2028-05-16', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', NULL,
        15.0, 30.0, false, false, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', NULL, NULL,
        'Antidiabético', 'Diabetes tipo 2', 'Pode causar desconforto gastrointestinal'
    )
    ON CONFLICT DO NOTHING;

    -- Daonil 2,5mg (Glibenclamida)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Glibenclamida', 'Daonil 2,5mg', 'Glibenclamida', 'MED-0135', '908875', 'ANVISA-30779820599', '693374', '67619777',
        '2,5mg', 1, 1, 2.5, 'mg', '1 comprimido antes do café', 'Tomar antes das refeições',
        20, 1, 'Genérico', NULL, 'A10BB01',
        '30779820599', '2024-02-01', '2030-02-26', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        8.0, 30.0, true, true, NULL, 60, 'Manter longe do alcance de crianças.',
        'Antidiabético', 'Diabetes tipo 2', 'Risco de hipoglicemia'
    )
    ON CONFLICT DO NOTHING;

    -- Diamicron 60mg (Gliclazida)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Gliclazida', 'Diamicron 60mg', 'Gliclazida', 'MED-0136', '292691', 'ANVISA-54868315158', '532643', '14785664',
        '60mg', 1, 1, 60.0, 'mg', '1 comprimido antes do café', 'Tomar antes das refeições',
        20, 1, 'Genérico', NULL, 'A10BB09',
        '54868315158', '2020-12-16', '2029-01-17', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        NULL, 30.0, true, false, NULL, 90, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Antidiabético', 'Diabetes tipo 2', 'Risco de hipoglicemia'
    )
    ON CONFLICT DO NOTHING;

    -- Amaryl (Glimepirida)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Glimepirida', 'Amaryl', 'Glimepirida', 'MED-0137', '565690', 'ANVISA-74806580523', '122650', '77903501',
        '2mg', 1, 1, 2.0, 'mg', '1 comprimido antes do café', 'Tomar antes das refeições',
        20, 1, 'Genérico', NULL, 'A10BB12',
        '74806580523', '2023-09-02', '2029-01-16', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        8.0, 25.0, false, false, 'Armazenar em local seco e arejado, protegido da luz.', 90, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Antidiabético', 'Diabetes tipo 2', 'Risco de hipoglicemia'
    )
    ON CONFLICT DO NOTHING;

    -- Actos 15mg (Pioglitazona)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Pioglitazona', 'Actos 15mg', 'Pioglitazona cloridrato', 'MED-0138', '681386', 'ANVISA-47033761642', '524051', '35863953',
        '15mg', 1, 1, 15.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        20, 1, 'Genérico', NULL, 'A10BG03',
        '47033761642', '2021-12-21', '2030-09-30', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        NULL, 15.0, false, true, 'Armazenar em local seco e arejado, protegido da luz.', NULL, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Antidiabético', 'Diabetes tipo 2', 'Pode causar ganho de peso'
    )
    ON CONFLICT DO NOTHING;

    -- Januvia (Sitagliptina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Sitagliptina', 'Januvia', 'Sitagliptina fosfato', 'MED-0139', '136911', 'ANVISA-50557104030', '783236', '86241394',
        '100mg', 1, 1, 100.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        20, 1, 'Genérico', NULL, 'A10BH01',
        '50557104030', '2021-02-28', '2028-08-29', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        15.0, 30.0, true, false, 'Armazenar em local seco e arejado, protegido da luz.', 60, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Antidiabético', 'Diabetes tipo 2', 'Tomar com ou sem alimentos'
    )
    ON CONFLICT DO NOTHING;

    -- Puran T4 25mcg (Levotiroxina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Levotiroxina', 'Puran T4 25mcg', 'Levotiroxina sódica', 'MED-0140', '471399', 'ANVISA-40059035762', '305475', '60161739',
        '25mcg', 1, 1, 25.0, 'mcg', '1 comprimido em jejum', 'Tomar 30min antes do café',
        19, 1, 'Genérico', NULL, 'H03AA01',
        '40059035762', '2019-06-21', '2029-04-24', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, NULL,
        8.0, NULL, true, false, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 90, NULL,
        'Hormônio tireoidiano', 'Hipotireoidismo', 'Tomar sempre em jejum'
    )
    ON CONFLICT DO NOTHING;

    -- Puran T4 75mcg (Levotiroxina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Levotiroxina', 'Puran T4 75mcg', 'Levotiroxina sódica', 'MED-0141', '401447', 'ANVISA-68790899219', '363528', '63458216',
        '75mcg', 1, 1, 75.0, 'mcg', '1 comprimido em jejum', 'Tomar 30min antes do café',
        19, 1, 'Genérico', NULL, 'H03AA01',
        '68790899219', '2024-02-22', '2028-12-08', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', NULL,
        15.0, 30.0, true, true, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 7, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Hormônio tireoidiano', 'Hipotireoidismo', 'Tomar sempre em jejum'
    )
    ON CONFLICT DO NOTHING;

    -- Puran T4 100mcg (Levotiroxina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Levotiroxina', 'Puran T4 100mcg', 'Levotiroxina sódica', 'MED-0142', '749144', 'ANVISA-85300388133', '276756', '14278394',
        '100mcg', 1, 1, 100.0, 'mcg', '1 comprimido em jejum', 'Tomar 30min antes do café',
        19, 1, 'Genérico', NULL, 'H03AA01',
        '85300388133', '2019-11-24', '2027-03-11', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        15.0, 15.0, true, false, 'Manter em geladeira (2-8°C). Não congelar.', NULL, 'Manter na embalagem original. Não expor ao sol.',
        'Hormônio tireoidiano', 'Hipotireoidismo', 'Tomar sempre em jejum'
    )
    ON CONFLICT DO NOTHING;

    -- Puran T4 125mcg (Levotiroxina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Levotiroxina', 'Puran T4 125mcg', 'Levotiroxina sódica', 'MED-0143', '860586', 'ANVISA-49677748606', '576995', '39238591',
        '125mcg', 1, 1, 125.0, 'mcg', '1 comprimido em jejum', 'Tomar 30min antes do café',
        19, 1, 'Genérico', NULL, 'H03AA01',
        '49677748606', '2016-06-19', '2027-12-01', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        15.0, 30.0, true, true, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 90, 'Manter longe do alcance de crianças.',
        'Hormônio tireoidiano', 'Hipotireoidismo', 'Tomar sempre em jejum'
    )
    ON CONFLICT DO NOTHING;

    -- Puran T4 150mcg (Levotiroxina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Levotiroxina', 'Puran T4 150mcg', 'Levotiroxina sódica', 'MED-0144', '143702', 'ANVISA-28175489552', '229112', '28388707',
        '150mcg', 1, 1, 150.0, 'mcg', '1 comprimido em jejum', 'Tomar 30min antes do café',
        19, 1, 'Genérico', NULL, 'H03AA01',
        '28175489552', '2022-09-27', '2030-10-24', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar tontura. Evitar atividades que requerem atenção.',
        8.0, 30.0, false, true, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 60, 'Manter longe do alcance de crianças.',
        'Hormônio tireoidiano', 'Hipotireoidismo', 'Tomar sempre em jejum'
    )
    ON CONFLICT DO NOTHING;

    -- Meticorten 5mg (Prednisona)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Prednisona', 'Meticorten 5mg', 'Prednisona', 'MED-0145', '229542', 'ANVISA-20548032849', '728113', '19513012',
        '5mg', 1, 1, 5.0, 'mg', 'Conforme prescrição médica', 'Tomar com alimentos',
        9, 1, 'Genérico', NULL, 'H02AB07',
        '20548032849', '2021-08-20', '2029-01-19', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        NULL, 30.0, true, false, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 60, 'Manter na embalagem original. Não expor ao sol.',
        'Corticoide sistêmico', 'Inflamações, alergias', 'Não suspender abruptamente'
    )
    ON CONFLICT DO NOTHING;

    -- Meticorten 10mg (Prednisona)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Prednisona', 'Meticorten 10mg', 'Prednisona', 'MED-0146', '937510', 'ANVISA-84691563120', '141940', '96294765',
        '10mg', 1, 1, 10.0, 'mg', 'Conforme prescrição médica', 'Tomar com alimentos',
        9, 1, 'Genérico', NULL, 'H02AB07',
        '84691563120', '2019-09-04', '2029-05-17', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        NULL, 25.0, true, false, 'Manter em geladeira (2-8°C). Não congelar.', 90, NULL,
        'Corticoide sistêmico', 'Inflamações, alergias', 'Não suspender abruptamente'
    )
    ON CONFLICT DO NOTHING;

    -- Meticorten 40mg (Prednisona)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Prednisona', 'Meticorten 40mg', 'Prednisona', 'MED-0147', '490034', 'ANVISA-73536767001', '827915', '84409466',
        '40mg', 1, 1, 40.0, 'mg', 'Conforme prescrição médica', 'Tomar com alimentos',
        9, 1, 'Genérico', NULL, 'H02AB07',
        '73536767001', '2016-02-23', '2028-11-12', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        NULL, 25.0, true, false, 'Armazenar em local seco e arejado, protegido da luz.', NULL, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Corticoide sistêmico', 'Inflamações, alergias', 'Não suspender abruptamente'
    )
    ON CONFLICT DO NOTHING;

    -- Decadron 2mg (Dexametasona)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Dexametasona', 'Decadron 2mg', 'Dexametasona', 'MED-0148', '368931', 'ANVISA-43498224503', '292737', '83045011',
        '2mg', 1, 1, 2.0, 'mg', 'Conforme prescrição médica', 'Tomar com alimentos',
        9, 1, 'Genérico', NULL, 'H02AB02',
        '43498224503', '2021-03-24', '2028-04-20', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        8.0, 8.0, false, false, NULL, 7, 'Manter longe do alcance de crianças.',
        'Corticoide sistêmico', 'Inflamações, alergias', 'Não suspender abruptamente'
    )
    ON CONFLICT DO NOTHING;

    -- Decadron 8mg (Dexametasona)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Dexametasona', 'Decadron 8mg', 'Dexametasona', 'MED-0149', '842744', 'ANVISA-78062325023', '824467', '70121629',
        '8mg', 1, 1, 8.0, 'mg', 'Conforme prescrição médica', 'Tomar com alimentos',
        9, 1, 'Genérico', NULL, 'H02AB02',
        '78062325023', '2016-03-23', '2030-10-26', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', NULL,
        2.0, 8.0, false, true, 'Armazenar em local seco e arejado, protegido da luz.', 30, 'Manter longe do alcance de crianças.',
        'Corticoide sistêmico', 'Inflamações, alergias', 'Não suspender abruptamente'
    )
    ON CONFLICT DO NOTHING;

    -- Cortisona 10mg (Hidrocortisona)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Hidrocortisona', 'Cortisona 10mg', 'Hidrocortisona', 'MED-0150', '351179', 'ANVISA-54336509495', '981780', '80126395',
        '10mg', 1, 1, 10.0, 'mg', 'Conforme prescrição médica', 'Tomar com alimentos',
        9, 1, 'Genérico', NULL, 'H02AB09',
        '54336509495', '2020-05-22', '2030-06-20', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        8.0, 25.0, false, true, 'Manter em geladeira (2-8°C). Não congelar.', NULL, NULL,
        'Corticoide sistêmico', 'Inflamações, alergias', 'Não suspender abruptamente'
    )
    ON CONFLICT DO NOTHING;

    -- Celestone 1mg (Betametasona)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Betametasona', 'Celestone 1mg', 'Betametasona', 'MED-0151', '805695', 'ANVISA-89148910280', '937734', '88813009',
        '1mg', 1, 1, 1.0, 'mg', 'Conforme prescrição médica', 'Tomar com alimentos',
        9, 1, 'Genérico', NULL, 'H02AB01',
        '89148910280', '2018-03-30', '2027-03-22', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        2.0, NULL, false, false, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 7, 'Manter na embalagem original. Não expor ao sol.',
        'Corticoide sistêmico', 'Inflamações, alergias', 'Não suspender abruptamente'
    )
    ON CONFLICT DO NOTHING;

    -- Tylenol 750mg (Paracetamol)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Paracetamol', 'Tylenol 750mg', 'Paracetamol', 'MED-0152', '692526', 'ANVISA-97212281049', '332670', '47111393',
        '750mg', 1, 1, 750.0, 'mg', '1 comprimido a cada 6-8 horas', 'Tomar com água',
        1, 1, 'Genérico', NULL, 'A02AA01',
        '97212281049', '2022-06-18', '2027-03-22', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        2.0, 15.0, true, true, 'Armazenar em local seco e arejado, protegido da luz.', 60, 'Manter na embalagem original. Não expor ao sol.',
        'Analgésico e antipirético', 'Dor e febre', 'Não exceder 4g/dia'
    )
    ON CONFLICT DO NOTHING;

    -- Tylenol 1g (Paracetamol)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Paracetamol', 'Tylenol 1g', 'Paracetamol', 'MED-0153', '975589', 'ANVISA-42764092864', '724307', '21664323',
        '1g', 1, 1, 1000.0, 'mg', '1 comprimido a cada 6-8 horas', 'Tomar com água',
        1, 1, 'Genérico', NULL, 'A02AA01',
        '42764092864', '2020-09-16', '2027-08-08', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, NULL,
        2.0, 15.0, true, false, 'Armazenar em local seco e arejado, protegido da luz.', 90, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Analgésico e antipirético', 'Dor e febre', 'Não exceder 4g/dia'
    )
    ON CONFLICT DO NOTHING;

    -- Novalgina 1g (Dipirona)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Dipirona', 'Novalgina 1g', 'Dipirona sódica', 'MED-0154', '182861', 'ANVISA-97055532343', '619040', '43758838',
        '1g', 1, 1, 1000.0, 'mg', '1 comprimido a cada 6-8 horas', 'Tomar com água',
        1, 1, 'Genérico', NULL, 'N02BB02',
        '97055532343', '2023-04-25', '2030-08-07', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        2.0, NULL, false, true, NULL, 30, 'Manter longe do alcance de crianças.',
        'Analgésico e antipirético', 'Dor e febre', 'Pode causar agranulocitose'
    )
    ON CONFLICT DO NOTHING;

    -- Advil 200mg (Ibuprofeno)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Ibuprofeno', 'Advil 200mg', 'Ibuprofeno', 'MED-0155', '267462', 'ANVISA-56589514324', '116851', '55792490',
        '200mg', 1, 1, 200.0, 'mg', '1 comprimido a cada 8 horas', 'Tomar com alimentos',
        2, 1, 'Genérico', NULL, 'M01AE01',
        '56589514324', '2024-06-07', '2030-11-21', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        15.0, NULL, false, true, 'Manter em geladeira (2-8°C). Não congelar.', 60, 'Manter longe do alcance de crianças.',
        'Anti-inflamatório não esteroidal', 'Dor, inflamação e febre', 'Evitar uso prolongado'
    )
    ON CONFLICT DO NOTHING;

    -- Advil 600mg (Ibuprofeno)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Ibuprofeno', 'Advil 600mg', 'Ibuprofeno', 'MED-0156', '908221', 'ANVISA-53010109196', '860496', '78739068',
        '600mg', 1, 1, 600.0, 'mg', '1 comprimido a cada 8 horas', 'Tomar com alimentos',
        2, 1, 'Genérico', NULL, 'M01AE01',
        '53010109196', '2019-10-27', '2028-01-31', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        2.0, 30.0, true, true, 'Manter em geladeira (2-8°C). Não congelar.', 7, NULL,
        'Anti-inflamatório não esteroidal', 'Dor, inflamação e febre', 'Evitar uso prolongado'
    )
    ON CONFLICT DO NOTHING;

    -- Voltaren 25mg (Diclofenaco Sódico)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Diclofenaco Sódico', 'Voltaren 25mg', 'Diclofenaco sódico', 'MED-0157', '196571', 'ANVISA-71048161986', '318971', '40142409',
        '25mg', 1, 1, 25.0, 'mg', '1 comprimido a cada 8 horas', 'Tomar com alimentos',
        2, 1, 'Genérico', NULL, 'M01AB05',
        '71048161986', '2016-04-01', '2027-04-25', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        2.0, NULL, true, false, NULL, 60, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Anti-inflamatório não esteroidal', 'Dor e inflamação', 'Evitar uso prolongado'
    )
    ON CONFLICT DO NOTHING;

    -- Voltaren 100mg (Diclofenaco Sódico)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Diclofenaco Sódico', 'Voltaren 100mg', 'Diclofenaco sódico', 'MED-0158', '478153', 'ANVISA-59490705157', '673274', '55528728',
        '100mg', 1, 1, 100.0, 'mg', '1 comprimido a cada 12 horas', 'Tomar com alimentos',
        2, 1, 'Genérico', NULL, 'M01AB05',
        '59490705157', '2017-04-06', '2028-06-23', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar tontura. Evitar atividades que requerem atenção.',
        8.0, 30.0, false, false, NULL, NULL, NULL,
        'Anti-inflamatório não esteroidal', 'Dor e inflamação', 'Evitar uso prolongado'
    )
    ON CONFLICT DO NOTHING;

    -- Flanax 250mg (Naproxeno)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Naproxeno', 'Flanax 250mg', 'Naproxeno sódico', 'MED-0159', '562485', 'ANVISA-59192279089', '498451', '80985855',
        '250mg', 1, 1, 250.0, 'mg', '1 comprimido a cada 12 horas', 'Tomar com alimentos',
        2, 1, 'Genérico', NULL, 'M01AE02',
        '59192279089', '2024-01-12', '2029-01-09', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        8.0, 8.0, false, true, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 60, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Anti-inflamatório não esteroidal', 'Dor e inflamação', 'Evitar uso prolongado'
    )
    ON CONFLICT DO NOTHING;

    -- Profenid 50mg (Cetoprofeno)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Cetoprofeno', 'Profenid 50mg', 'Cetoprofeno', 'MED-0160', '373756', 'ANVISA-61787974949', '656646', '56304669',
        '50mg', 1, 1, 50.0, 'mg', '1 comprimido a cada 12 horas', 'Tomar com alimentos',
        2, 1, 'Genérico', NULL, 'M01AE03',
        '61787974949', '2020-06-08', '2029-02-05', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        2.0, 15.0, false, true, 'Manter em geladeira (2-8°C). Não congelar.', 90, NULL,
        'Anti-inflamatório não esteroidal', 'Dor e inflamação', 'Evitar uso prolongado'
    )
    ON CONFLICT DO NOTHING;

    -- Nisulid 100mg (Nimesulida)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Nimesulida', 'Nisulid 100mg', 'Nimesulida', 'MED-0161', '519223', 'ANVISA-74883302368', '453142', '17056807',
        '100mg', 1, 1, 100.0, 'mg', '1 comprimido 2x ao dia', 'Tomar com alimentos',
        2, 1, 'Genérico', NULL, 'M01AX17',
        '74883302368', '2020-06-26', '2030-08-18', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        15.0, 8.0, true, false, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 14, 'Manter longe do alcance de crianças.',
        'Anti-inflamatório não esteroidal', 'Dor e inflamação', 'Evitar uso prolongado'
    )
    ON CONFLICT DO NOTHING;

    -- Tilatil 20mg (Tenoxicam)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Tenoxicam', 'Tilatil 20mg', 'Tenoxicam', 'MED-0162', '773820', 'ANVISA-82411847956', '130088', '73877098',
        '20mg', 1, 1, 20.0, 'mg', '1 comprimido ao dia', 'Tomar com alimentos',
        2, 1, 'Genérico', NULL, 'M01AC02',
        '82411847956', '2019-05-05', '2027-03-14', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar tontura. Evitar atividades que requerem atenção.',
        15.0, 8.0, false, false, 'Armazenar em local seco e arejado, protegido da luz.', 7, 'Manter longe do alcance de crianças.',
        'Anti-inflamatório não esteroidal', 'Dor e inflamação', 'Evitar uso prolongado'
    )
    ON CONFLICT DO NOTHING;

    -- Amoxil 250mg (Amoxicilina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Amoxicilina', 'Amoxil 250mg', 'Amoxicilina', 'MED-0163', '616237', 'ANVISA-36918157588', '504958', '33078501',
        '250mg', 1, 1, 250.0, 'mg', '1 comprimido a cada 8 horas', 'Tomar com água',
        3, 1, 'Genérico', NULL, 'J01CA04',
        '36918157588', '2021-06-24', '2027-02-05', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar tontura. Evitar atividades que requerem atenção.',
        2.0, 8.0, false, false, 'Armazenar em local seco e arejado, protegido da luz.', 90, NULL,
        'Antibiótico beta-lactâmico', 'Infecções bacterianas', 'Completar tratamento'
    )
    ON CONFLICT DO NOTHING;

    -- Amoxil 875mg (Amoxicilina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Amoxicilina', 'Amoxil 875mg', 'Amoxicilina', 'MED-0164', '889060', 'ANVISA-27898065651', '531403', '58357998',
        '875mg', 1, 1, 875.0, 'mg', '1 comprimido a cada 12 horas', 'Tomar com água',
        3, 1, 'Genérico', NULL, 'J01CA04',
        '27898065651', '2017-06-06', '2029-04-24', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        NULL, 8.0, true, false, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 60, 'Manter longe do alcance de crianças.',
        'Antibiótico beta-lactâmico', 'Infecções bacterianas', 'Completar tratamento'
    )
    ON CONFLICT DO NOTHING;

    -- Augmentin 250mg+125mg (Amoxicilina + Ácido Clavulânico)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Amoxicilina + Ácido Clavulânico', 'Augmentin 250mg+125mg', 'Amoxicilina + Ácido clavulânico', 'MED-0165', '317376', 'ANVISA-95904075604', '858481', '27953279',
        '250mg+125mg', 1, 1, 250.0, 'mg', '1 comprimido a cada 8 horas', 'Tomar com alimentos',
        3, 1, 'Genérico', NULL, 'J01CR02',
        '95904075604', '2023-12-21', '2030-05-06', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', NULL,
        15.0, 25.0, true, false, NULL, 60, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Antibiótico beta-lactâmico', 'Infecções bacterianas resistentes', 'Completar tratamento'
    )
    ON CONFLICT DO NOTHING;

    -- Augmentin 875mg+125mg (Amoxicilina + Ácido Clavulânico)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Amoxicilina + Ácido Clavulânico', 'Augmentin 875mg+125mg', 'Amoxicilina + Ácido clavulânico', 'MED-0166', '308976', 'ANVISA-75987559645', '621764', '75960383',
        '875mg+125mg', 1, 1, 875.0, 'mg', '1 comprimido a cada 12 horas', 'Tomar com alimentos',
        3, 1, 'Genérico', NULL, 'J01CR02',
        '75987559645', '2019-12-30', '2028-03-23', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        8.0, 30.0, false, true, NULL, 60, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Antibiótico beta-lactâmico', 'Infecções bacterianas resistentes', 'Completar tratamento'
    )
    ON CONFLICT DO NOTHING;

    -- Zitromax 250mg (Azitromicina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Azitromicina', 'Zitromax 250mg', 'Azitromicina di-hidratada', 'MED-0167', '622514', 'ANVISA-61145065035', '407541', '50754697',
        '250mg', 1, 1, 250.0, 'mg', '1 comprimido ao dia por 3 dias', 'Tomar em jejum',
        3, 1, 'Genérico', NULL, 'J01FA10',
        '61145065035', '2016-12-18', '2027-07-25', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        15.0, 25.0, true, true, NULL, 30, 'Manter na embalagem original. Não expor ao sol.',
        'Antibiótico macrolídeo', 'Infecções respiratórias', 'Tomar 1h antes ou 2h após refeições'
    )
    ON CONFLICT DO NOTHING;

    -- Keflex 250mg (Cefalexina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Cefalexina', 'Keflex 250mg', 'Cefalexina monoidratada', 'MED-0168', '679959', 'ANVISA-99110679007', '341255', '50045789',
        '250mg', 2, 1, 250.0, 'mg', '1 cápsula a cada 6 horas', 'Tomar com água',
        3, 1, 'Genérico', NULL, 'J01DB01',
        '99110679007', '2016-04-21', '2030-09-02', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        NULL, NULL, false, true, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 60, NULL,
        'Antibiótico cefalosporina', 'Infecções bacterianas', 'Completar tratamento'
    )
    ON CONFLICT DO NOTHING;

    -- Keflex 1g (Cefalexina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Cefalexina', 'Keflex 1g', 'Cefalexina monoidratada', 'MED-0169', '654878', 'ANVISA-30247921756', '165866', '25867040',
        '1g', 2, 1, 1000.0, 'mg', '1 cápsula a cada 12 horas', 'Tomar com água',
        3, 1, 'Genérico', NULL, 'J01DB01',
        '30247921756', '2022-07-28', '2028-01-20', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        2.0, 8.0, false, true, 'Armazenar em local seco e arejado, protegido da luz.', NULL, NULL,
        'Antibiótico cefalosporina', 'Infecções bacterianas', 'Completar tratamento'
    )
    ON CONFLICT DO NOTHING;

    -- Cipro 250mg (Ciprofloxacino)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Ciprofloxacino', 'Cipro 250mg', 'Ciprofloxacino cloridrato', 'MED-0170', '766605', 'ANVISA-27223793792', '979511', '98898847',
        '250mg', 1, 1, 250.0, 'mg', '1 comprimido 2x ao dia', 'Tomar com água',
        3, 1, 'Genérico', NULL, 'J01MA02',
        '27223793792', '2023-12-04', '2027-05-20', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        15.0, NULL, false, true, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 30, NULL,
        'Antibiótico fluoroquinolona', 'Infecções bacterianas', 'Evitar exposição solar'
    )
    ON CONFLICT DO NOTHING;

    -- Klacid 250mg (Claritromicina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Claritromicina', 'Klacid 250mg', 'Claritromicina', 'MED-0171', '932177', 'ANVISA-35957537737', '300080', '17999971',
        '250mg', 1, 1, 250.0, 'mg', '1 comprimido 2x ao dia', 'Tomar com alimentos',
        3, 1, 'Genérico', NULL, 'J01FA09',
        '35957537737', '2021-10-24', '2028-12-12', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        15.0, 15.0, true, false, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 30, 'Manter na embalagem original. Não expor ao sol.',
        'Antibiótico macrolídeo', 'Infecções respiratórias', 'Tomar com alimentos'
    )
    ON CONFLICT DO NOTHING;

    -- Vibramicina 100mg (Doxiciclina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Doxiciclina', 'Vibramicina 100mg', 'Doxiciclina hiclato', 'MED-0172', '645515', 'ANVISA-87176244557', '763251', '59471447',
        '100mg', 1, 1, 100.0, 'mg', '1 comprimido 2x ao dia', 'Tomar com água',
        3, 1, 'Genérico', NULL, 'J01AA02',
        '87176244557', '2019-07-02', '2030-02-23', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar tontura. Evitar atividades que requerem atenção.',
        2.0, 25.0, false, false, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 7, 'Manter na embalagem original. Não expor ao sol.',
        'Antibiótico tetraciclina', 'Infecções bacterianas', 'Evitar exposição solar'
    )
    ON CONFLICT DO NOTHING;

    -- Rocephin 500mg (Ceftriaxona)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Ceftriaxona', 'Rocephin 500mg', 'Ceftriaxona sódica', 'MED-0173', '685958', 'ANVISA-23083121505', '565370', '48630279',
        '500mg', 8, 7, 500.0, 'mg', '1 ampola IM/IV a cada 24h', 'Aplicar via intramuscular ou intravenosa',
        3, 8, 'Genérico', NULL, 'J01DD04',
        '23083121505', '2020-12-13', '2029-09-03', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', NULL,
        NULL, NULL, false, false, 'Armazenar em local seco e arejado, protegido da luz.', NULL, 'Manter longe do alcance de crianças.',
        'Antibiótico cefalosporina', 'Infecções bacterianas graves', 'Aplicação parenteral'
    )
    ON CONFLICT DO NOTHING;

    -- Rocephin 2g (Ceftriaxona)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Ceftriaxona', 'Rocephin 2g', 'Ceftriaxona sódica', 'MED-0174', '577462', 'ANVISA-19633692871', '129987', '97838208',
        '2g', 8, 7, 2000.0, 'mg', '1 ampola IM/IV a cada 24h', 'Aplicar via intramuscular ou intravenosa',
        3, 8, 'Genérico', NULL, 'J01DD04',
        '19633692871', '2017-06-15', '2030-06-20', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        NULL, 25.0, true, true, NULL, 90, 'Manter longe do alcance de crianças.',
        'Antibiótico cefalosporina', 'Infecções bacterianas graves', 'Aplicação parenteral'
    )
    ON CONFLICT DO NOTHING;

    -- Claritin 10mg (Loratadina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Loratadina', 'Claritin 10mg', 'Loratadina', 'MED-0175', '343556', 'ANVISA-81000334810', '759131', '16648019',
        '10mg', 1, 1, 10.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        7, 1, 'Genérico', NULL, 'R06AX13',
        '81000334810', '2016-12-17', '2030-08-22', 1, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        2.0, 25.0, true, true, 'Manter em geladeira (2-8°C). Não congelar.', NULL, NULL,
        'Antihistamínico', 'Rinite alérgica, urticária', 'Não causa sonolência'
    )
    ON CONFLICT DO NOTHING;

    -- Desalex 5mg (Desloratadina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Desloratadina', 'Desalex 5mg', 'Desloratadina', 'MED-0176', '504985', 'ANVISA-85050793097', '453102', '87665492',
        '5mg', 1, 1, 5.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        7, 1, 'Genérico', NULL, 'R06AX27',
        '85050793097', '2019-05-11', '2029-03-09', 1, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        2.0, 15.0, true, true, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 60, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Antihistamínico', 'Rinite alérgica, urticária', 'Não causa sonolência'
    )
    ON CONFLICT DO NOTHING;

    -- Zyrtec 10mg (Cetirizina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Cetirizina', 'Zyrtec 10mg', 'Cetirizina dicloridrato', 'MED-0177', '166187', 'ANVISA-44360037434', '925351', '89879701',
        '10mg', 1, 1, 10.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        7, 1, 'Genérico', NULL, 'R06AE07',
        '44360037434', '2023-07-28', '2028-01-27', 1, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        NULL, 8.0, false, false, 'Manter em geladeira (2-8°C). Não congelar.', 60, 'Manter na embalagem original. Não expor ao sol.',
        'Antihistamínico', 'Rinite alérgica, urticária', 'Pode causar sonolência'
    )
    ON CONFLICT DO NOTHING;

    -- Allegra (Fexofenadina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Fexofenadina', 'Allegra', 'Fexofenadina cloridrato', 'MED-0178', '571276', 'ANVISA-64407042786', '181897', '47764595',
        '120mg', 1, 1, 120.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        7, 1, 'Genérico', NULL, 'R06AX26',
        '64407042786', '2021-09-24', '2030-07-18', 1, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        2.0, 8.0, false, false, 'Armazenar em local seco e arejado, protegido da luz.', 30, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Antihistamínico', 'Rinite alérgica, urticária', 'Não causa sonolência'
    )
    ON CONFLICT DO NOTHING;

    -- Aerolin 100mcg (Salbutamol)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Salbutamol', 'Aerolin 100mcg', 'Salbutamol sulfato', 'MED-0179', '486478', 'ANVISA-55506822964', '669489', '63885290',
        '100mcg/dose', 13, 19, 100.0, 'mcg', '2 jatos a cada 4-6 horas', 'Agitar antes de usar',
        8, 13, 'Genérico', NULL, 'R03AC02',
        '55506822964', '2022-04-24', '2027-08-10', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        15.0, 15.0, false, true, NULL, 30, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Broncodilatador', 'Asma, bronquite', 'Uso em crises'
    )
    ON CONFLICT DO NOTHING;

    -- Atrovent 20mcg (Brometo de Ipratrópio)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Brometo de Ipratrópio', 'Atrovent 20mcg', 'Ipratrópio brometo', 'MED-0180', '353671', 'ANVISA-67302923896', '762831', '72255777',
        '20mcg/dose', 13, 19, 20.0, 'mcg', '2 jatos a cada 6 horas', 'Agitar antes de usar',
        8, 13, 'Genérico', NULL, 'R03BB01',
        '67302923896', '2023-07-11', '2029-07-19', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', NULL,
        15.0, 15.0, false, false, 'Manter em geladeira (2-8°C). Não congelar.', NULL, 'Manter longe do alcance de crianças.',
        'Broncodilatador anticolinérgico', 'Asma, bronquite', 'Uso em crises'
    )
    ON CONFLICT DO NOTHING;

    -- Foradil (Formoterol)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Formoterol', 'Foradil', 'Formoterol fumarato', 'MED-0181', '666987', 'ANVISA-80183901400', '209624', '90530085',
        '12mcg/dose', 13, 19, 12.0, 'mcg', '2 jatos 2x ao dia', 'Agitar antes de usar',
        8, 13, 'Genérico', NULL, 'R03AC13',
        '80183901400', '2017-07-23', '2029-10-11', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        15.0, 30.0, true, false, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 30, 'Manter na embalagem original. Não expor ao sol.',
        'Broncodilatador', 'Asma, bronquite', 'Uso contínuo'
    )
    ON CONFLICT DO NOTHING;

    -- Serevent (Salmeterol)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Salmeterol', 'Serevent', 'Salmeterol xinafoato', 'MED-0182', '742799', 'ANVISA-73832746970', '352928', '92367980',
        '50mcg/dose', 13, 19, 50.0, 'mcg', '2 jatos 2x ao dia', 'Agitar antes de usar',
        8, 13, 'Genérico', NULL, 'R03AC12',
        '73832746970', '2018-05-07', '2028-02-21', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', NULL,
        15.0, 8.0, false, true, NULL, 60, 'Manter na embalagem original. Não expor ao sol.',
        'Broncodilatador', 'Asma, bronquite', 'Uso contínuo'
    )
    ON CONFLICT DO NOTHING;

    -- Clenil 250mcg (Beclometasona)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Beclometasona', 'Clenil 250mcg', 'Beclometasona dipropionato', 'MED-0183', '495609', 'ANVISA-30850955706', '922318', '76012654',
        '250mcg/dose', 13, 19, 250.0, 'mcg', '2 jatos 2x ao dia', 'Agitar antes de usar',
        9, 13, 'Genérico', NULL, 'R03BA01',
        '30850955706', '2024-05-05', '2028-07-11', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        2.0, 8.0, false, true, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 14, 'Manter na embalagem original. Não expor ao sol.',
        'Corticoide inalatório', 'Asma', 'Enxaguar boca após uso'
    )
    ON CONFLICT DO NOTHING;

    -- Pulmicort (Budesonida)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Budesonida', 'Pulmicort', 'Budesonida', 'MED-0184', '522111', 'ANVISA-12570255378', '106621', '69651682',
        '200mcg/dose', 13, 19, 200.0, 'mcg', '2 jatos 2x ao dia', 'Agitar antes de usar',
        9, 13, 'Genérico', NULL, 'R03BA02',
        '12570255378', '2016-09-16', '2030-10-18', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        NULL, 25.0, true, false, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 90, NULL,
        'Corticoide inalatório', 'Asma', 'Enxaguar boca após uso'
    )
    ON CONFLICT DO NOTHING;

    -- Flixotide (Fluticasona)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Fluticasona', 'Flixotide', 'Fluticasona propionato', 'MED-0185', '842813', 'ANVISA-45612206085', '452264', '58633352',
        '250mcg/dose', 13, 19, 250.0, 'mcg', '2 jatos 2x ao dia', 'Agitar antes de usar',
        9, 13, 'Genérico', NULL, 'R03BA05',
        '45612206085', '2021-02-01', '2030-04-03', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        NULL, 25.0, true, false, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 90, 'Manter na embalagem original. Não expor ao sol.',
        'Corticoide inalatório', 'Asma', 'Enxaguar boca após uso'
    )
    ON CONFLICT DO NOTHING;

    -- Marevan 2,5mg (Varfarina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Varfarina', 'Marevan 2,5mg', 'Varfarina sódica', 'MED-0186', '810783', 'ANVISA-81715091956', '825772', '49646053',
        '2,5mg', 1, 1, 2.5, 'mg', 'Conforme prescrição médica', 'Tomar sempre no mesmo horário',
        24, 1, 'Genérico', NULL, 'B01AA03',
        '81715091956', '2022-05-12', '2030-05-26', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        NULL, 8.0, true, true, NULL, 30, 'Manter longe do alcance de crianças.',
        'Anticoagulante', 'Prevenção de trombose', 'Monitorar INR regularmente'
    )
    ON CONFLICT DO NOTHING;

    -- Marevan 10mg (Varfarina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Varfarina', 'Marevan 10mg', 'Varfarina sódica', 'MED-0187', '695696', 'ANVISA-66909806764', '338325', '53304235',
        '10mg', 1, 1, 10.0, 'mg', 'Conforme prescrição médica', 'Tomar sempre no mesmo horário',
        24, 1, 'Genérico', NULL, 'B01AA03',
        '66909806764', '2021-12-01', '2027-05-19', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', NULL,
        NULL, NULL, false, true, NULL, 30, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Anticoagulante', 'Prevenção de trombose', 'Monitorar INR regularmente'
    )
    ON CONFLICT DO NOTHING;

    -- Aspirina 100mg (Ácido Acetilsalicílico)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Ácido Acetilsalicílico', 'Aspirina 100mg', 'Ácido acetilsalicílico', 'MED-0188', '886823', 'ANVISA-87401769797', '336727', '83712686',
        '100mg', 1, 1, 100.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        24, 1, 'Genérico', NULL, 'B01AC06',
        '87401769797', '2022-01-05', '2027-05-12', 1, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', NULL,
        8.0, 30.0, true, false, NULL, 60, 'Manter longe do alcance de crianças.',
        'Antiagregante plaquetário', 'Prevenção cardiovascular', 'Evitar em crianças'
    )
    ON CONFLICT DO NOTHING;

    -- Plavix (Clopidogrel)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Clopidogrel', 'Plavix', 'Clopidogrel bisulfato', 'MED-0189', '542734', 'ANVISA-46652896779', '581064', '89725594',
        '75mg', 1, 1, 75.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        24, 1, 'Genérico', NULL, 'B01AC04',
        '46652896779', '2021-04-01', '2030-10-07', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        8.0, 30.0, true, true, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 30, NULL,
        'Antiagregante plaquetário', 'Prevenção cardiovascular', 'Tomar com água'
    )
    ON CONFLICT DO NOTHING;

    -- Hidantal 100mg (Fenitoína)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Fenitoína', 'Hidantal 100mg', 'Fenitoína sódica', 'MED-0190', '607923', 'ANVISA-10384272830', '243930', '77738278',
        '100mg', 1, 1, 100.0, 'mg', 'Conforme prescrição médica', 'Tomar com água',
        13, 1, 'Genérico', NULL, 'N03AB02',
        '10384272830', '2023-04-28', '2030-10-23', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        8.0, NULL, true, true, 'Armazenar em local seco e arejado, protegido da luz.', 14, 'Manter longe do alcance de crianças.',
        'Anticonvulsivante', 'Epilepsia', 'Monitorar níveis séricos'
    )
    ON CONFLICT DO NOTHING;

    -- Tegretol 200mg (Carbamazepina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Carbamazepina', 'Tegretol 200mg', 'Carbamazepina', 'MED-0191', '610127', 'ANVISA-70244820556', '762814', '83221602',
        '200mg', 1, 1, 200.0, 'mg', 'Conforme prescrição médica', 'Tomar com água',
        13, 1, 'Genérico', NULL, 'N03AF01',
        '70244820556', '2020-01-22', '2030-02-20', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        8.0, 30.0, false, true, 'Manter em geladeira (2-8°C). Não congelar.', 60, 'Manter longe do alcance de crianças.',
        'Anticonvulsivante', 'Epilepsia, neuralgia', 'Monitorar hemograma'
    )
    ON CONFLICT DO NOTHING;

    -- Depakene (Ácido Valpróico)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Ácido Valpróico', 'Depakene', 'Ácido valpróico', 'MED-0192', '914509', 'ANVISA-68562337382', '202014', '48529455',
        '250mg', 1, 1, 250.0, 'mg', 'Conforme prescrição médica', 'Tomar com água',
        13, 1, 'Genérico', NULL, 'N03AG01',
        '68562337382', '2017-09-12', '2029-01-09', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, NULL,
        15.0, 25.0, true, true, NULL, 30, 'Manter na embalagem original. Não expor ao sol.',
        'Anticonvulsivante', 'Epilepsia', 'Monitorar função hepática'
    )
    ON CONFLICT DO NOTHING;

    -- Topamax (Topiramato)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Topiramato', 'Topamax', 'Topiramato', 'MED-0193', '138077', 'ANVISA-11963003392', '284391', '56036332',
        '25mg', 1, 1, 25.0, 'mg', 'Conforme prescrição médica', 'Tomar com água',
        13, 1, 'Genérico', NULL, 'N03AX11',
        '11963003392', '2024-01-08', '2029-11-21', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        2.0, 25.0, false, true, 'Manter em geladeira (2-8°C). Não congelar.', 14, NULL,
        'Anticonvulsivante', 'Epilepsia, enxaqueca', 'Pode causar perda de peso'
    )
    ON CONFLICT DO NOTHING;

    -- Neurontin (Gabapentina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Gabapentina', 'Neurontin', 'Gabapentina', 'MED-0194', '662477', 'ANVISA-56085369338', '409070', '59356550',
        '300mg', 1, 1, 300.0, 'mg', 'Conforme prescrição médica', 'Tomar com água',
        13, 1, 'Genérico', NULL, 'N03AX12',
        '56085369338', '2021-03-23', '2029-04-27', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        2.0, 15.0, true, true, 'Manter em geladeira (2-8°C). Não congelar.', 30, NULL,
        'Anticonvulsivante', 'Epilepsia, neuralgia', 'Pode causar sonolência'
    )
    ON CONFLICT DO NOTHING;

    -- Lyrica (Pregabalina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Pregabalina', 'Lyrica', 'Pregabalina', 'MED-0195', '663484', 'ANVISA-17250793207', '575170', '49409349',
        '75mg', 1, 1, 75.0, 'mg', 'Conforme prescrição médica', 'Tomar com água',
        13, 1, 'Genérico', NULL, 'N03AX16',
        '17250793207', '2017-09-09', '2030-04-17', 3, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, NULL,
        8.0, 15.0, false, true, 'Armazenar em local seco e arejado, protegido da luz.', NULL, 'Manter longe do alcance de crianças.',
        'Anticonvulsivante', 'Epilepsia, neuralgia', 'Pode causar sonolência'
    )
    ON CONFLICT DO NOTHING;

    -- Zoltec 100mg (Fluconazol)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Fluconazol', 'Zoltec 100mg', 'Fluconazol', 'MED-0196', '230845', 'ANVISA-90905474852', '427492', '26894979',
        '100mg', 1, 1, 100.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        5, 1, 'Genérico', NULL, 'J02AC01',
        '90905474852', '2016-09-03', '2030-03-31', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        NULL, NULL, false, false, NULL, 90, 'Manter na embalagem original. Não expor ao sol.',
        'Antifúngico', 'Candidíase', 'Tomar com água'
    )
    ON CONFLICT DO NOTHING;

    -- Zoltec 200mg (Fluconazol)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Fluconazol', 'Zoltec 200mg', 'Fluconazol', 'MED-0197', '135454', 'ANVISA-74316824556', '815014', '23105059',
        '200mg', 1, 1, 200.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        5, 1, 'Genérico', NULL, 'J02AC01',
        '74316824556', '2016-07-04', '2028-12-25', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', NULL,
        NULL, 15.0, false, true, 'Manter em geladeira (2-8°C). Não congelar.', 90, NULL,
        'Antifúngico', 'Candidíase', 'Tomar com água'
    )
    ON CONFLICT DO NOTHING;

    -- Sporanox (Itraconazol)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Itraconazol', 'Sporanox', 'Itraconazol', 'MED-0198', '733706', 'ANVISA-77170888213', '482709', '91751017',
        '100mg', 2, 1, 100.0, 'mg', '1 cápsula 2x ao dia', 'Tomar com alimentos',
        5, 1, 'Genérico', NULL, 'J02AC02',
        '77170888213', '2016-10-01', '2030-05-21', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', NULL,
        8.0, 30.0, true, true, 'Manter em geladeira (2-8°C). Não congelar.', NULL, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Antifúngico', 'Micoses sistêmicas', 'Tomar com alimentos'
    )
    ON CONFLICT DO NOTHING;

    -- Nizoral (Cetoconazol)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Cetoconazol', 'Nizoral', 'Cetoconazol', 'MED-0199', '877170', 'ANVISA-83916285031', '110809', '16109077',
        '200mg', 1, 1, 200.0, 'mg', '1 comprimido ao dia', 'Tomar com alimentos',
        5, 1, 'Genérico', NULL, 'J02AB02',
        '83916285031', '2018-02-27', '2028-10-20', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        NULL, NULL, false, false, NULL, 7, 'Manter na embalagem original. Não expor ao sol.',
        'Antifúngico', 'Micoses sistêmicas', 'Tomar com alimentos'
    )
    ON CONFLICT DO NOTHING;

    -- Micostatin Suspensão (Nistatina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Nistatina', 'Micostatin Suspensão', 'Nistatina', 'MED-0200', '121725', 'ANVISA-50052402919', '318009', '48749329',
        '100.000UI/ml', 4, 1, 100000.0, 'UI', '4-6ml 4x ao dia', 'Manter na boca antes de engolir',
        5, 4, 'Genérico', NULL, 'A07AA02',
        '50052402919', '2024-02-22', '2030-08-24', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        8.0, 8.0, false, false, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 14, NULL,
        'Antifúngico', 'Candidíase oral', 'Manter na boca'
    )
    ON CONFLICT DO NOTHING;

    -- Zovirax 400mg (Aciclovir)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Aciclovir', 'Zovirax 400mg', 'Aciclovir', 'MED-0201', '797564', 'ANVISA-20743507611', '579116', '19187222',
        '400mg', 1, 1, 400.0, 'mg', '1 comprimido 3x ao dia', 'Tomar com água',
        4, 1, 'Genérico', NULL, 'J05AB01',
        '20743507611', '2021-01-31', '2029-01-12', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        15.0, 15.0, false, false, 'Armazenar em local seco e arejado, protegido da luz.', 60, 'Manter longe do alcance de crianças.',
        'Antiviral', 'Herpes simples, zoster', 'Iniciar precocemente'
    )
    ON CONFLICT DO NOTHING;

    -- Zovirax Pomada (Aciclovir)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Aciclovir', 'Zovirax Pomada', 'Aciclovir', 'MED-0202', '156932', 'ANVISA-76935946357', '242910', '49350898',
        '5%', 10, 5, 5.0, '%', 'Aplicar 5x ao dia', 'Aplicar na área afetada',
        4, 10, 'Genérico', NULL, 'J05AB01',
        '76935946357', '2020-12-23', '2029-10-08', 1, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        2.0, 25.0, false, false, 'Armazenar em local seco e arejado, protegido da luz.', 60, 'Manter longe do alcance de crianças.',
        'Antiviral tópico', 'Herpes labial', 'Aplicar na área afetada'
    )
    ON CONFLICT DO NOTHING;

    -- Valtrex (Valaciclovir)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Valaciclovir', 'Valtrex', 'Valaciclovir cloridrato', 'MED-0203', '581383', 'ANVISA-49078547061', '681249', '42810197',
        '500mg', 1, 1, 500.0, 'mg', '1 comprimido 2x ao dia', 'Tomar com água',
        4, 1, 'Genérico', NULL, 'J05AB11',
        '49078547061', '2023-03-28', '2029-08-25', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        2.0, 8.0, true, true, NULL, 14, NULL,
        'Antiviral', 'Herpes simples, zoster', 'Iniciar precocemente'
    )
    ON CONFLICT DO NOTHING;

    -- Tamiflu 75mg (Oseltamivir)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Oseltamivir', 'Tamiflu 75mg', 'Oseltamivir fosfato', 'MED-0204', '881889', 'ANVISA-55403641610', '108176', '31233005',
        '75mg', 2, 1, 75.0, 'mg', '1 cápsula 2x ao dia por 5 dias', 'Tomar com água',
        4, 1, 'Genérico', NULL, 'J05AH02',
        '55403641610', '2018-04-26', '2029-06-01', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, NULL,
        NULL, NULL, true, false, NULL, 90, NULL,
        'Antiviral', 'Influenza', 'Iniciar nas primeiras 48h'
    )
    ON CONFLICT DO NOTHING;

    -- Zentel 400mg (Albendazol)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Albendazol', 'Zentel 400mg', 'Albendazol', 'MED-0205', '513872', 'ANVISA-21343239100', '714082', '98422358',
        '400mg', 1, 1, 400.0, 'mg', '1 comprimido em dose única', 'Tomar com alimentos',
        6, 1, 'Genérico', NULL, 'P02CA03',
        '21343239100', '2019-08-16', '2027-10-17', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', NULL,
        15.0, 8.0, false, false, 'Manter em geladeira (2-8°C). Não congelar.', 7, 'Manter na embalagem original. Não expor ao sol.',
        'Antiparasitário', 'Verminoses', 'Dose única'
    )
    ON CONFLICT DO NOTHING;

    -- Vermox 100mg (Mebendazol)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Mebendazol', 'Vermox 100mg', 'Mebendazol', 'MED-0206', '168237', 'ANVISA-32334818396', '958424', '30470972',
        '100mg', 1, 1, 100.0, 'mg', '1 comprimido 2x ao dia por 3 dias', 'Tomar com alimentos',
        6, 1, 'Genérico', NULL, 'P02CA01',
        '32334818396', '2018-02-26', '2029-03-05', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        2.0, NULL, false, false, NULL, NULL, 'Manter na embalagem original. Não expor ao sol.',
        'Antiparasitário', 'Verminoses', 'Tomar com alimentos'
    )
    ON CONFLICT DO NOTHING;

    -- Cesol (Praziquantel)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Praziquantel', 'Cesol', 'Praziquantel', 'MED-0207', '944592', 'ANVISA-65446941073', '423862', '65429839',
        '600mg', 1, 1, 600.0, 'mg', 'Conforme prescrição médica', 'Tomar com alimentos',
        6, 1, 'Genérico', NULL, 'P02BA01',
        '65446941073', '2023-01-05', '2028-02-14', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        15.0, NULL, false, false, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 90, NULL,
        'Antiparasitário', 'Esquistossomose', 'Tomar com alimentos'
    )
    ON CONFLICT DO NOTHING;

    -- Ivermec (Ivermectina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Ivermectina', 'Ivermec', 'Ivermectina', 'MED-0208', '904386', 'ANVISA-12553875551', '508904', '45764820',
        '6mg', 1, 1, 6.0, 'mg', 'Conforme prescrição médica', 'Tomar com água',
        6, 1, 'Genérico', NULL, 'P02CF01',
        '12553875551', '2024-08-19', '2028-11-01', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        2.0, 25.0, true, true, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 90, 'Manter longe do alcance de crianças.',
        'Antiparasitário', 'Escabiose, pediculose', 'Tomar com água'
    )
    ON CONFLICT DO NOTHING;

    -- Addera D3 2000UI (Vitamina D3)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Vitamina D3', 'Addera D3 2000UI', 'Colecalciferol', 'MED-0209', '586498', 'ANVISA-87002992921', '128969', '20535753',
        '2000UI', 2, 1, 2000.0, 'UI', '1 cápsula ao dia', 'Tomar com alimentos',
        17, 1, 'Genérico', NULL, 'A11CC05',
        '87002992921', '2017-08-19', '2029-03-19', 1, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', NULL,
        2.0, 15.0, false, true, NULL, 14, 'Manter longe do alcance de crianças.',
        'Vitamina D', 'Deficiência de vitamina D', 'Tomar com alimentos gordurosos'
    )
    ON CONFLICT DO NOTHING;

    -- Addera D3 4000UI (Vitamina D3)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Vitamina D3', 'Addera D3 4000UI', 'Colecalciferol', 'MED-0210', '103251', 'ANVISA-13871907004', '139745', '68666752',
        '4000UI', 2, 1, 4000.0, 'UI', '1 cápsula ao dia', 'Tomar com alimentos',
        17, 1, 'Genérico', NULL, 'A11CC05',
        '13871907004', '2018-05-05', '2030-01-21', 1, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', NULL,
        2.0, NULL, true, false, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 30, 'Manter longe do alcance de crianças.',
        'Vitamina D', 'Deficiência de vitamina D', 'Tomar com alimentos gordurosos'
    )
    ON CONFLICT DO NOTHING;

    -- Folacin 5mg (Ácido Fólico)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Ácido Fólico', 'Folacin 5mg', 'Ácido fólico', 'MED-0211', '364191', 'ANVISA-80659679253', '203092', '31583655',
        '5mg', 1, 1, 5.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        17, 1, 'Genérico', NULL, 'B03BB01',
        '80659679253', '2022-04-15', '2029-05-26', 1, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        15.0, 8.0, false, true, 'Armazenar em local seco e arejado, protegido da luz.', 60, 'Manter longe do alcance de crianças.',
        'Vitamina B9', 'Anemia, gestação', 'Importante na gestação'
    )
    ON CONFLICT DO NOTHING;

    -- Cianocobalamina 1000mcg (Vitamina B12)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Vitamina B12', 'Cianocobalamina 1000mcg', 'Cianocobalamina', 'MED-0212', '455254', 'ANVISA-69585621330', '796821', '46577227',
        '1000mcg', 8, 1, 1000.0, 'mcg', '1 ampola IM mensal', 'Aplicar via intramuscular',
        17, 8, 'Genérico', NULL, 'B03BA01',
        '69585621330', '2022-12-16', '2030-04-04', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', NULL,
        2.0, 15.0, false, false, 'Manter em geladeira (2-8°C). Não congelar.', 90, NULL,
        'Vitamina B12', 'Anemia perniciosa', 'Aplicação intramuscular'
    )
    ON CONFLICT DO NOTHING;

    -- Redoxon 1g (Vitamina C)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Vitamina C', 'Redoxon 1g', 'Ácido ascórbico', 'MED-0213', '539648', 'ANVISA-20764801499', '142196', '18860694',
        '1g', 1, 1, 1000.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        17, 1, 'Genérico', NULL, 'A11GA01',
        '20764801499', '2017-03-11', '2027-09-15', 1, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        NULL, 8.0, false, false, 'Manter em geladeira (2-8°C). Não congelar.', 7, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Vitamina C', 'Deficiência de vitamina C', 'Tomar com água'
    )
    ON CONFLICT DO NOTHING;

    -- Ephynal 400UI (Vitamina E)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Vitamina E', 'Ephynal 400UI', 'Alfa-tocoferol', 'MED-0214', '223989', 'ANVISA-22633275316', '610390', '70517282',
        '400UI', 2, 1, 400.0, 'UI', '1 cápsula ao dia', 'Tomar com alimentos',
        17, 1, 'Genérico', NULL, 'A11HA03',
        '22633275316', '2022-01-14', '2030-07-29', 1, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        2.0, 15.0, true, true, NULL, 60, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Vitamina E', 'Antioxidante', 'Tomar com alimentos gordurosos'
    )
    ON CONFLICT DO NOTHING;

    -- Ferronil 40mg (Ferro)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Ferro', 'Ferronil 40mg', 'Sulfato ferroso', 'MED-0215', '557827', 'ANVISA-46475832208', '877324', '99906549',
        '40mg', 1, 1, 40.0, 'mg', '1 comprimido ao dia', 'Tomar em jejum',
        18, 1, 'Genérico', NULL, 'B03AA07',
        '46475832208', '2019-12-18', '2028-05-07', 1, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        NULL, 30.0, true, true, 'Manter em geladeira (2-8°C). Não congelar.', NULL, NULL,
        'Suplemento de ferro', 'Anemia ferropriva', 'Tomar em jejum'
    )
    ON CONFLICT DO NOTHING;

    -- Calcitran 500mg (Cálcio)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Cálcio', 'Calcitran 500mg', 'Carbonato de cálcio', 'MED-0216', '684484', 'ANVISA-98633302245', '596499', '78275204',
        '500mg', 1, 1, 500.0, 'mg', '1 comprimido 2x ao dia', 'Tomar com alimentos',
        18, 1, 'Genérico', NULL, 'A12AA04',
        '98633302245', '2022-08-30', '2029-05-28', 1, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', NULL,
        15.0, 8.0, true, false, 'Manter em geladeira (2-8°C). Não congelar.', 90, 'Após abertura, conservar em geladeira e usar dentro do prazo indicado.',
        'Suplemento de cálcio', 'Osteoporose', 'Tomar com alimentos'
    )
    ON CONFLICT DO NOTHING;

    -- Zincoquel (Zinco)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Zinco', 'Zincoquel', 'Sulfato de zinco', 'MED-0217', '178548', 'ANVISA-57182678228', '721990', '22005209',
        '20mg', 1, 1, 20.0, 'mg', '1 comprimido ao dia', 'Tomar com água',
        18, 1, 'Genérico', NULL, 'A12CB01',
        '57182678228', '2020-12-23', '2028-09-16', 1, false, false, false, false, false,
        NULL, NULL, true, true, true, true, NULL, 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        2.0, 15.0, true, false, 'Manter em geladeira (2-8°C). Não congelar.', 14, 'Manter na embalagem original. Não expor ao sol.',
        'Suplemento de zinco', 'Deficiência de zinco', 'Tomar com água'
    )
    ON CONFLICT DO NOTHING;

    -- Magnésio B6 (Magnésio)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Magnésio', 'Magnésio B6', 'Magnésio + Vitamina B6', 'MED-0218', '247909', 'ANVISA-77780487241', '720733', '95978306',
        '1 comprimido', 1, 1, 1.0, 'comp', '1 comprimido ao dia', 'Tomar com água',
        18, 1, 'Genérico', NULL, 'A12CC30',
        '77780487241', '2024-03-26', '2029-07-10', 1, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar tontura. Evitar atividades que requerem atenção.',
        8.0, 30.0, true, true, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 90, NULL,
        'Suplemento de magnésio', 'Deficiência de magnésio', 'Tomar com água'
    )
    ON CONFLICT DO NOTHING;

    -- Lactulona 10g (Lactulose)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Lactulose', 'Lactulona 10g', 'Lactulose', 'MED-0219', '810122', 'ANVISA-57645015808', '945449', '62462688',
        '10g/15ml', 4, 1, 10.0, 'g', '15-30ml ao deitar', 'Tomar com água',
        15, 5, 'Genérico', NULL, 'A06AD11',
        '57645015808', '2024-09-12', '2029-05-22', 1, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        8.0, NULL, false, false, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', NULL, NULL,
        'Laxante osmótico', 'Constipação', 'Efeito após 24-48h'
    )
    ON CONFLICT DO NOTHING;

    -- Dulcolax 5mg (Bisacodil)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Bisacodil', 'Dulcolax 5mg', 'Bisacodil', 'MED-0220', '331674', 'ANVISA-68266276772', '339559', '43540824',
        '5mg', 1, 1, 5.0, 'mg', '1-2 comprimidos ao deitar', 'Tomar com água',
        15, 1, 'Genérico', NULL, 'A06AB02',
        '68266276772', '2021-12-12', '2027-08-18', 1, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Interage com antiácidos. Tomar com intervalo de 2 horas.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        8.0, 30.0, false, false, 'Armazenar em local seco e arejado, protegido da luz.', 30, NULL,
        'Laxante estimulante', 'Constipação', 'Efeito após 6-12h'
    )
    ON CONFLICT DO NOTHING;

    -- Imodium 2mg (Loperamida)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Loperamida', 'Imodium 2mg', 'Loperamida cloridrato', 'MED-0221', '359033', 'ANVISA-12783673012', '783274', '25554545',
        '2mg', 1, 1, 2.0, 'mg', '2 comprimidos após primeira evacuação', 'Tomar com água',
        16, 1, 'Genérico', NULL, 'A07DA03',
        '12783673012', '2023-05-15', '2027-01-24', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        2.0, NULL, false, false, 'Armazenar em local seco e arejado, protegido da luz.', NULL, 'Manter longe do alcance de crianças.',
        'Antidiarreico', 'Diarreia aguda', 'Não usar em crianças <2 anos'
    )
    ON CONFLICT DO NOTHING;

    -- Tiorfan (Racecadotrilo)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Racecadotrilo', 'Tiorfan', 'Racecadotrilo', 'MED-0222', '267553', 'ANVISA-46578631070', '169695', '11728295',
        '100mg', 2, 1, 100.0, 'mg', '1 cápsula 3x ao dia', 'Tomar com água',
        16, 1, 'Genérico', NULL, 'A07XA04',
        '46578631070', '2021-04-19', '2028-07-19', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', NULL,
        8.0, 30.0, true, true, 'Manter em geladeira (2-8°C). Não congelar.', 60, NULL,
        'Antidiarreico', 'Diarreia aguda', 'Tomar com água'
    )
    ON CONFLICT DO NOTHING;

    -- Dorflex Gotas (Dorflex)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Dorflex', 'Dorflex Gotas', 'Cafeína + Carisoprodol + Dipirona', 'MED-0223', '466898', 'ANVISA-63151798816', '705518', '57657527',
        '20 gotas', 11, 1, 20.0, 'gota', '20 gotas a cada 8 horas', 'Tomar com água',
        1, 6, 'Genérico', NULL, 'M03BA03',
        '63151798816', '2016-07-20', '2028-06-28', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar náusea, vômito ou diarreia. Em caso de persistência, consultar médico.',
        8.0, NULL, false, false, 'Armazenar em local seco e arejado, protegido da luz.', 7, 'Manter na embalagem original. Não expor ao sol.',
        'Relaxante muscular + Analgésico', 'Dor muscular, tensão', 'Pode causar sonolência'
    )
    ON CONFLICT DO NOTHING;

    -- Buscopan Composto (Buscopan)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Buscopan', 'Buscopan Composto', 'Butilescopolamina + Dipirona', 'MED-0224', '276279', 'ANVISA-22039581904', '785284', '63008065',
        '1 comprimido', 1, 1, 1.0, 'comp', '1 comprimido a cada 8 horas', 'Tomar com água',
        1, 1, 'Genérico', NULL, 'A03BB01',
        '22039581904', '2023-09-25', '2028-07-24', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', 'Pode causar sonolência. Evitar dirigir ou operar máquinas.',
        15.0, 30.0, true, false, 'Manter em geladeira (2-8°C). Não congelar.', 7, 'Manter longe do alcance de crianças.',
        'Antiespasmódico + Analgésico', 'Cólica com dor', 'Alivio rápido de cólicas'
    )
    ON CONFLICT DO NOTHING;

    -- Neosaldina Gotas (Neosaldina)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Neosaldina', 'Neosaldina Gotas', 'Dipirona + Cafeína + Mucato de Isometepteno', 'MED-0225', '740477', 'ANVISA-11236675628', '389010', '39967800',
        '20 gotas', 11, 1, 20.0, 'gota', '20 gotas a cada 6 horas', 'Tomar com água',
        1, 6, 'Genérico', NULL, 'N02BB02',
        '11236675628', '2023-10-10', '2030-07-03', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode interagir com anticoagulantes. Evitar uso concomitante com álcool.', NULL,
        2.0, 25.0, true, false, 'Manter em temperatura ambiente (15-30°C). Proteger da luz e umidade.', 90, NULL,
        'Analgésico', 'Dor de cabeça, enxaqueca', 'Não exceder 120 gotas/dia'
    )
    ON CONFLICT DO NOTHING;

    -- Torsilax Gotas (Torsilax)
    SELECT id INTO fabricante_uuid FROM public.fabricantes_medicamento ORDER BY RANDOM() LIMIT 1;
    
    INSERT INTO public.medicacoes (
        id, ativo, criado_em, atualizado_em,
        fabricante_id,
        principio_ativo, nome_comercial, nome_generico, codigo_interno, catmat_codigo, codigo_anvisa, codigo_tuss, codigo_sigtap,
        dosagem, unidade_medida, via_administracao, concentracao, unidade_concentracao, posologia_padrao, instrucoes_uso,
        classe_terapeutica, forma_farmaceutica, categoria, subcategoria, codigo_atc,
        registro_anvisa, data_registro_anvisa, data_validade_registro_anvisa, tipo_controle, receita_obrigatoria, controlado, uso_continuo, medicamento_especial, medicamento_excepcional,
        contraindicacoes, precaucoes, gestante_pode, lactante_pode, crianca_pode, idoso_pode, interacoes_medicamentosas, efeitos_colaterais,
        temperatura_conservacao_min, temperatura_conservacao_max, proteger_luz, proteger_umidade, condicoes_armazenamento, validade_apos_abertura_dias, instrucoes_conservacao,
        descricao, indicacoes, observacoes
    ) VALUES (
        gen_random_uuid(), true, NOW(), NOW(),
        fabricante_uuid,
        'Torsilax', 'Torsilax Gotas', 'Ciclobenzaprina + Dipirona + Cafeína', 'MED-0226', '428566', 'ANVISA-92898811302', '404343', '50184971',
        '20 gotas', 11, 1, 20.0, 'gota', '20 gotas a cada 8 horas', 'Tomar com água',
        1, 6, 'Genérico', NULL, 'M03BX01',
        '92898811302', '2024-02-02', '2027-07-21', 2, false, false, false, false, false,
        NULL, NULL, true, true, true, true, 'Pode potencializar efeitos de outros medicamentos. Consultar médico.', NULL,
        2.0, 15.0, true, true, 'Manter em geladeira (2-8°C). Não congelar.', NULL, 'Manter longe do alcance de crianças.',
        'Relaxante muscular + Analgésico', 'Dor muscular, lombalgia', 'Pode causar sonolência'
    )
    ON CONFLICT DO NOTHING;

END $$;
