package com.upsaude.service.impl.job.jdbc;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Writer genérico para persistência em batch via JDBC, com suporte a INSERT e UPSERT (Postgres).
 *
 * Estratégia:
 * - Descobre tabela/colunas via anotações JPA (@Table/@Column/@JoinColumn).
 * - Ignora colunas de auditoria/base (id, criado_em, atualizado_em, ativo).
 * - Para @JoinColumn (ManyToOne), persiste o ID do objeto referenciado (getId()) quando insertable/updatable.
 * - Para UPSERT, usa o primeiro @UniqueConstraint definido na @Table (quando existir).
 *
 * Observação: para entidades sem unique constraint, use insertBatch().
 */
/**
 * Writer genérico para persistência em batch via JDBC, com suporte a INSERT e UPSERT (Postgres).
 * 
 * USO EXCLUSIVO JOB - NÃO USAR NA API
 * 
 * Usa jobDataSource para garantir isolamento de conexões.
 */
@Slf4j
@Service
public class JdbcEntityBatchWriter {

    private static final Set<String> BASE_COLUMNS = Set.of("id", "criado_em", "atualizado_em", "ativo");

    // USO EXCLUSIVO JOB - JdbcTemplate criado a partir do jobDataSource
    private final JdbcTemplate jdbcTemplate;

    /**
     * Construtor: cria JdbcTemplate a partir do jobDataSource
     */
    public JdbcEntityBatchWriter(@Qualifier("jobDataSource") DataSource jobDataSource) {
        this.jdbcTemplate = new JdbcTemplate(jobDataSource);
        log.info("JdbcEntityBatchWriter inicializado com jobDataSource");
    }

    private final Map<Class<?>, EntityMeta> metaCache = new ConcurrentHashMap<>();

    public <T> void insertBatch(List<T> entities) {
        if (entities == null || entities.isEmpty()) return;
        EntityMeta meta = metaFor(entities.get(0).getClass());
        String sql = meta.insertSql;

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                T entity = entities.get(i);
                meta.bind(ps, entity);
            }

            @Override
            public int getBatchSize() {
                return entities.size();
            }
        });
    }

    public <T> void upsertBatch(List<T> entities) {
        if (entities == null || entities.isEmpty()) return;
        EntityMeta meta = metaFor(entities.get(0).getClass());
        if (!meta.hasConflictKey()) {
            insertBatch(entities);
            return;
        }
        String sql = meta.upsertSql;

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                T entity = entities.get(i);
                meta.bind(ps, entity);
            }

            @Override
            public int getBatchSize() {
                return entities.size();
            }
        });
    }

    private EntityMeta metaFor(Class<?> clazz) {
        return metaCache.computeIfAbsent(clazz, this::buildMeta);
    }

    private EntityMeta buildMeta(Class<?> clazz) {
        Table table = clazz.getAnnotation(Table.class);
        if (table == null) {
            throw new IllegalArgumentException("Classe sem @Table: " + clazz.getName());
        }

        String schema = (table.schema() != null && !table.schema().isBlank()) ? table.schema() : "public";
        String tableName = table.name();
        if (tableName == null || tableName.isBlank()) {
            throw new IllegalArgumentException("Tabela inválida em @Table para: " + clazz.getName());
        }

        List<ColumnBinding> bindings = new ArrayList<>();
        for (Field f : allFields(clazz)) {
            Column col = f.getAnnotation(Column.class);
            if (col != null) {
                String colName = col.name();
                if (colName != null && !colName.isBlank() && !BASE_COLUMNS.contains(colName)) {
                    f.setAccessible(true);
                    bindings.add(new ColumnBinding(colName, f, BindingKind.DIRECT_FIELD));
                }
                continue;
            }

            JoinColumn join = f.getAnnotation(JoinColumn.class);
            if (join != null) {
                // respeita insertable/updatable (evita colunas computadas)
                if (!join.insertable() && !join.updatable()) continue;

                String colName = join.name();
                if (colName != null && !colName.isBlank() && !BASE_COLUMNS.contains(colName)) {
                    f.setAccessible(true);
                    bindings.add(new ColumnBinding(colName, f, BindingKind.JOIN_ENTITY_ID));
                }
            }
        }

        if (bindings.isEmpty()) {
            throw new IllegalArgumentException("Nenhuma coluna bindável encontrada para: " + clazz.getName());
        }

        // conflito: usa o primeiro unique constraint definido na entidade
        List<String> conflictCols = new ArrayList<>();
        if (table.uniqueConstraints() != null && table.uniqueConstraints().length > 0) {
            var uc = table.uniqueConstraints()[0];
            if (uc.columnNames() != null) {
                for (String c : uc.columnNames()) {
                    if (c != null && !c.isBlank()) conflictCols.add(c);
                }
            }
        }

        String qualified = schema + "." + tableName;
        String colsCsv = String.join(", ", bindings.stream().map(b -> b.columnName).toList());
        String placeholders = String.join(", ", Collections.nCopies(bindings.size(), "?"));
        String insertSql = "INSERT INTO " + qualified + " (" + colsCsv + ") VALUES (" + placeholders + ")";

        String upsertSql = null;
        if (!conflictCols.isEmpty()) {
            // Atualiza todas as colunas exceto as de conflito (mantém chave natural intacta)
            Set<String> conflictSet = new HashSet<>(conflictCols);
            List<String> updates = new ArrayList<>();
            for (ColumnBinding b : bindings) {
                if (conflictSet.contains(b.columnName)) continue;
                updates.add(b.columnName + " = EXCLUDED." + b.columnName);
            }
            String updateSql = updates.isEmpty() ? "DO NOTHING" : ("DO UPDATE SET " + String.join(", ", updates));
            upsertSql = insertSql + " ON CONFLICT (" + String.join(", ", conflictCols) + ") " + updateSql;
        }

        return new EntityMeta(clazz, qualified, bindings, conflictCols, insertSql, upsertSql);
    }

    private List<Field> allFields(Class<?> clazz) {
        List<Field> out = new ArrayList<>();
        Class<?> c = clazz;
        while (c != null && c != Object.class) {
            out.addAll(Arrays.asList(c.getDeclaredFields()));
            c = c.getSuperclass();
        }
        return out;
    }

    private enum BindingKind { DIRECT_FIELD, JOIN_ENTITY_ID }

    private static final class ColumnBinding {
        final String columnName;
        final Field field;
        final BindingKind kind;

        ColumnBinding(String columnName, Field field, BindingKind kind) {
            this.columnName = columnName;
            this.field = field;
            this.kind = kind;
        }
    }

    private static final class EntityMeta {
        final Class<?> clazz;
        final List<ColumnBinding> bindings;
        final List<String> conflictColumns;
        final String insertSql;
        final String upsertSql;

        EntityMeta(Class<?> clazz,
                   String qualifiedTable,
                   List<ColumnBinding> bindings,
                   List<String> conflictColumns,
                   String insertSql,
                   String upsertSql) {
            this.clazz = clazz;
            this.bindings = bindings;
            this.conflictColumns = conflictColumns;
            this.insertSql = insertSql;
            this.upsertSql = upsertSql;
        }

        boolean hasConflictKey() {
            return conflictColumns != null && !conflictColumns.isEmpty() && upsertSql != null;
        }

        <T> void bind(PreparedStatement ps, T entity) throws SQLException {
            for (int idx = 0; idx < bindings.size(); idx++) {
                ColumnBinding b = bindings.get(idx);
                Object value = null;
                try {
                    Object raw = b.field.get(entity);
                    if (b.kind == BindingKind.DIRECT_FIELD) {
                        value = raw;
                    } else if (b.kind == BindingKind.JOIN_ENTITY_ID) {
                        if (raw == null) {
                            value = null;
                        } else {
                            // tenta getId() via reflexão
                            try {
                                value = raw.getClass().getMethod("getId").invoke(raw);
                            } catch (Exception ex) {
                                // fallback: campo "id"
                                Field idField = raw.getClass().getDeclaredField("id");
                                idField.setAccessible(true);
                                value = idField.get(raw);
                            }
                        }
                    }
                } catch (Exception e) {
                    log.warn("Falha ao obter valor para coluna {} ({}): {}", b.columnName, clazz.getSimpleName(), e.getMessage());
                }
                ps.setObject(idx + 1, value);
            }
        }
    }
}


