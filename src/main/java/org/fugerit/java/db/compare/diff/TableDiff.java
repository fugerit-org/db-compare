package org.fugerit.java.db.compare.diff;

import lombok.Getter;
import org.fugerit.java.core.db.metadata.ColumnModel;
import org.fugerit.java.core.db.metadata.TableModel;
import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.db.compare.DBCompareConfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class TableDiff {

    private DBCompareConfig config;

    @Getter
    private TableModel sourceTable;

    @Getter
    private TableModel targetTable;

    public TableDiff(DBCompareConfig config, TableModel sourceTable, TableModel targetTable) {
        this.config = config;
        this.sourceTable = sourceTable;
        this.targetTable = targetTable;
    }

    public boolean isSourceExists() {
        return this.getSourceTable() != null;
    }

    public boolean isTargetExists() {
        return this.getTargetTable() != null;
    }

    public Collection<ColumnDiff> getColumnDiffs() {
        List<ColumnDiff> columnDiffs = new ArrayList<>();
        LinkedHashMap<String, ColumnModel> cols1 = new LinkedHashMap<>();
        LinkedHashMap<String, ColumnModel> cols2 = new LinkedHashMap<>();
        SafeFunction.applyIfNotNull( this.getSourceTable(), () -> this.getSourceTable().getColumnList().stream().forEach( c -> cols1.put( c.getName(), c ) ) );
        SafeFunction.applyIfNotNull( this.getTargetTable(), () -> this.getTargetTable().getColumnList().stream().forEach( c -> cols2.put( c.getName(), c ) ) );
        // iterate remaining table columns
        for ( ColumnModel col1 : cols1.values() ) {
            ColumnModel col2 = cols2.get( col1.getName() );
            columnDiffs.add( new ColumnDiff( col1.getName(), config, col1, col2 ) );
            cols2.remove( col1.getName() );
        }
        // iterate remaining target table columns
        for ( ColumnModel col2 : cols2.values() ) {
            columnDiffs.add( new ColumnDiff( col2.getName(), config, null, col2 ) );
        }
        return columnDiffs;
    }

    public Collection<ColumnDiff> getColumnDiffsExcludeEquals() {
        return this.getColumnDiffs().stream().filter(cd -> !cd.isSourceExists() || !cd.isTargetExists() || !cd.isSourceTargetEqual() ).collect(Collectors.toList());
    }

    public String getTableName() {
        if ( this.getSourceTable() != null ) {
            return this.getSourceTable().getName();
        } else if ( this.getTargetTable() != null ) {
            return this.getTargetTable().getName();
        } else {
            return null;
        }
    }

}
