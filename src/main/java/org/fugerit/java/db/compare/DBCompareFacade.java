package org.fugerit.java.db.compare;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.db.connect.ConnectionFactory;
import org.fugerit.java.core.db.metadata.ColumnModel;
import org.fugerit.java.core.db.metadata.DataBaseModel;
import org.fugerit.java.core.db.metadata.MetaDataUtils;
import org.fugerit.java.core.db.metadata.TableModel;
import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.db.compare.diff.TableDiff;

import java.io.InputStream;
import java.util.LinkedHashMap;

@Slf4j
public class DBCompareFacade {

    private ObjectMapper mapper = new YAMLMapper();

    public DBCompareConfig readConfig(InputStream is) {
        return SafeFunction.get( () -> mapper.readValue( is, DBCompareConfig.class ) );
    }

    public DBCompareOutput compare(DBCompareConfig config, ConnectionFactory cf1, String schema1, ConnectionFactory cf2, String schema2 ) {
        return SafeFunction.get( () -> {
            DataBaseModel db1 = MetaDataUtils.createModel( cf1, null, schema1 );
            DataBaseModel db2 = MetaDataUtils.createModel( cf2, null, schema2 );
            return compare( config, db1, db2 );
        } );
    }

    public DBCompareOutput compare( DBCompareConfig config, DataBaseModel db1, DataBaseModel db2 ) {
        DBCompareOutput output = new DBCompareOutput();
        db1.getTableList().stream().forEach( tableModel1 -> {
            String tableName = tableModel1.getName();
            TableModel tableModel2 = db2.getTableNameMap().get( tableName );
            output.getTableDiffs().add( new TableDiff( tableModel1, tableModel2 ) );
        } );
        return output;
    }

}
