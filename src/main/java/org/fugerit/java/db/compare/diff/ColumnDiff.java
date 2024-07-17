package org.fugerit.java.db.compare.diff;

import lombok.Getter;
import org.fugerit.java.core.db.metadata.ColumnModel;
import org.fugerit.java.db.compare.DBCompareConfig;

import java.util.Comparator;

public class ColumnDiff {

    public ColumnDiff(String name, DBCompareConfig config, ColumnModel sourceColumn, ColumnModel targetColumn) {
        this.name = name;
        this.config = config;
        this.sourceColumn = sourceColumn;
        this.targetColumn = targetColumn;
        if ( DBCompareConfig.COLUMN_COMPARE_MODE_JAVATYPE.equals( this.config.getColumnCompareMode() ) ) {
            this.comparator = new Comparator<ColumnModel>() {
                @Override
                public int compare(ColumnModel o1, ColumnModel o2) {
                    return o1.getTypeSql()-o2.getTypeSql();
                }
            };
        } else {
            this.comparator = new Comparator<ColumnModel>() {
                @Override
                public int compare(ColumnModel o1, ColumnModel o2) {
                    return o1.getJavaType().compareTo( o2.getJavaType() );
                }
            };
        }
    }

    private DBCompareConfig config;

    @Getter
    private String name;

    @Getter
    private ColumnModel sourceColumn;

    @Getter
    private ColumnModel targetColumn;

    private Comparator<ColumnModel> comparator;

    public boolean isSourceExists() {
        return this.getSourceColumn() != null;
    }

    public boolean isTargetExists() {
        return this.getTargetColumn() != null;
    }

    public boolean isSourceTargetEqual() {
        return this.isSourceExists() && this.isTargetExists() && this.comparator.compare( this.getSourceColumn(), this.getTargetColumn() ) == 0;
    }

}

