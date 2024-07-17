package org.fugerit.java.db.compare;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.db.compare.diff.ColumnDiff;

import java.util.Collection;

@Slf4j
public class DBCompareUtils {

    private DBCompareUtils() {}

    public static void printDiff( DBCompareOutput output, boolean includeEquals ) {
        if ( output.getTableDiffs().isEmpty() ) {
            log.info( "Source and target have no differences" );
        } else {
            output.getTableDiffs().forEach( td -> {
                Collection<ColumnDiff> columnDiffsExcludeEquals = td.getColumnDiffsExcludeEquals();
                if ( columnDiffsExcludeEquals.isEmpty() ) {
                    log.info( "Table : {} source and target have no differences", td.getTableName() );
                } else {
                    if ( !td.isSourceExists() ) {
                        log.info( "Table : {} source does not exist", td.getTableName() );
                    } else if ( !td.isTargetExists() ) {
                        log.info( "Table : {} target does not exist", td.getTableName() );
                    } else {
                        log.info( "Table : {} have {} different columns", td.getTableName(), columnDiffsExcludeEquals.size() );
                    }
                    td.getColumnDiffs().forEach( c -> {
                        if ( c.isSourceTargetEqual() && includeEquals ) {
                            log.info( "Table : {} column : {} is the same on source and target", td.getTableName(), c.getName() );
                        } else if ( !c.isSourceTargetEqual() ) {
                            if ( c.getSourceColumn() == null ) {
                                log.info( "Table : {} column : {} does not exist on source", td.getTableName(), c.getName() );
                            } else if ( c.getTargetColumn() == null ) {
                                log.info( "Table : {} column : {} does not exist on target", td.getTableName(), c.getName() );
                            } else {
                                log.info( "Table : {} column : {} differs, source column : {}, target column : {}", td.getTableName(), c.getName(), c.getSourceColumn(), c.getTargetColumn() );
                            }

                        }
                    } );
                }
            } );
        }
    }

}
