package org.fugerit.java.db.compare;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.util.MapEntry;
import org.fugerit.java.db.compare.diff.ColumnDiff;

import java.io.Writer;
import java.util.*;

@Slf4j
public class DBCompareUtils {

    private DBCompareUtils() {}

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static LinkedHashMap<String, Object> create(String tag, String value, MapEntry<String, String> ...atts ) {
        LinkedHashMap<String, Object> json = new LinkedHashMap<>();
        json.put( "_t", tag );
        if ( value != null ) {
            json.put( "_v", value );
        }
        for ( MapEntry<String, String> att : atts ) {
            json.put( att.getKey(), att.getValue() );
        }
        return json;
    }

    private static LinkedHashMap<String, Object> createCell( String value ) {
        LinkedHashMap<String, Object> cell = create( "cell", null );
        cell.put( "_e", Arrays.asList( create( "para", value ) ) );
        return cell;
    }

    // experimental
    public static void toJsonDoc(DBCompareOutput output, boolean includeEquals, Writer writer) {
        SafeFunction.apply(  () -> {
            LinkedHashMap<String, Object> doc = create( "doc", null );
            LinkedHashMap<String, Object> meta = create( "meta", null );
            LinkedHashMap<String, Object> body = create( "body", null );
            doc.put( "_e", Arrays.asList( meta, body ) );
            List<LinkedHashMap<String, Object>> infos = Arrays.asList(  create( "info", "data-table", new MapEntry<>( "name", "csv-table-id" )  ) );
            meta.put( "_e", infos );
            // data table
            LinkedHashMap<String, Object> outputTable = create( "table", null, new MapEntry<>( "id", "data-table" ), new MapEntry<>( "columns", "5" ), new MapEntry<>( "colwidths", "15;15;15;15;40" ) );
            body.put( "_e", Arrays.asList( outputTable ) );
            List<LinkedHashMap<String, Object>> rows = new ArrayList<>();
            // header row
            LinkedHashMap<String, Object> headerRow = create( "table", null, new MapEntry<>( "header", "true" ) );
            headerRow.put( "_e", Arrays.asList( createCell( "Source table" ), createCell( "Source column" ),
                    createCell( "Target table" ), createCell( "Target column" ), createCell( "Note" ) ) );
            rows.add( headerRow );
            // data rows

            outputTable.put( "_e", rows );
            MAPPER.writerWithDefaultPrettyPrinter().writeValue( writer, doc );
        });
    }

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
