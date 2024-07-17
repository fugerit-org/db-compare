package org.fugerit.java.db.compare.diff;

import lombok.Getter;
import org.fugerit.java.core.db.metadata.ColumnModel;

public class ColumnDiff {

    public ColumnDiff(String name, ColumnModel sourceColumn, ColumnModel targetColumn) {
        this.name = name;
        this.sourceColumn = sourceColumn;
        this.targetColumn = targetColumn;
    }

    @Getter
    private String name;

    @Getter
    private ColumnModel sourceColumn;

    @Getter
    private ColumnModel targetColumn;

    public boolean isSourceExists() {
        return this.getSourceColumn() != null;
    }

    public boolean isTargetExists() {
        return this.getTargetColumn() != null;
    }

    public boolean isSourceTargetEqual() {
        return this.isSourceExists() && this.isTargetExists() && this.getSourceColumn().getTypeSql() == this.getTargetColumn().getTypeSql();
    }

}

