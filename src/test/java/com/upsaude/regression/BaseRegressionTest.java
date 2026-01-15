package com.upsaude.regression;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Sql(
        scripts = {
                "classpath:sql/financeiro_schema_patch.sql",
                "classpath:db/migration/V20260114200000__add_campos_fechamento_competencia_financeira.sql",
                "classpath:db/migration/V20260115000000__add_usuario_rastreabilidade_financeiro.sql",
                "classpath:db/migration/V20260115000001__add_travado_por_lancamento_financeiro.sql"
        },
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS,
        config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
)
@Transactional
public abstract class BaseRegressionTest {
}
