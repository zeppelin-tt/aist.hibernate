package aist.demo.hibernate.types;

import org.hibernate.dialect.PostgreSQL95Dialect;

import java.sql.Types;

public class ExtendedPostgreSQLDialect extends PostgreSQL95Dialect {

    public ExtendedPostgreSQLDialect() {
        this.registerColumnType(Types.OTHER, "interval");
        this.registerColumnType(Types.JAVA_OBJECT, "jsonb");
    }

}
