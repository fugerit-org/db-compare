package org.fugerit.java.db.compare;

import lombok.Getter;
import lombok.Setter;

public class DBCompareConfig {

    public static final String COLUMN_COMPARE_MODE_JAVATYPE = "java-type";

    public static final String COLUMN_COMPARE_MODE_SQLTYPE = "sql-type";

    public static final String COLUMN_COMPARE_MODE_DEFAULT = COLUMN_COMPARE_MODE_SQLTYPE;

    @Getter @Setter
    private String columnCompareMode;

}
