package test.org.fugerit.java.db.compare;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.db.connect.ConnectionFactoryCloseable;
import org.fugerit.java.core.db.connect.ConnectionFactoryImpl;
import org.fugerit.java.core.db.metadata.ColumnModel;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.util.PropsIO;
import org.fugerit.java.db.compare.DBCompareConfig;
import org.fugerit.java.db.compare.DBCompareFacade;
import org.fugerit.java.db.compare.DBCompareOutput;
import org.fugerit.java.db.compare.DBCompareUtils;
import org.fugerit.java.db.compare.diff.ColumnDiff;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.sql.Date;

@Slf4j
class TestDBCompareFacade {

    private DBCompareFacade facade = new DBCompareFacade();

    @Test
    public void test0() {
        ColumnModel c1 = new ColumnModel();
        c1.setJavaType( String.class.getName() );
        ColumnModel c2 = new ColumnModel();
        c2.setJavaType( String.class.getName() );
        ColumnModel c3 = new ColumnModel();
        c3.setJavaType(Date.class.getName());
        DBCompareConfig config = new DBCompareConfig();
        config.setColumnCompareMode( DBCompareConfig.COLUMN_COMPARE_MODE_SQLTYPE );
        ColumnDiff diff1 = new ColumnDiff( "test1", config, c1, c2 );
        ColumnDiff diff2 = new ColumnDiff( "test2", config, c1, c3 );
        Assertions.assertTrue( diff1.isSourceTargetEqual() );
        Assertions.assertFalse( diff2.isSourceTargetEqual() );
    }

    @Test
    void test1() throws Exception {
        try ( InputStream configIs = ClassHelper.loadFromDefaultClassLoader( "config/db-compare-config.yaml" );
              ConnectionFactoryCloseable cf1 = ConnectionFactoryImpl.wrap( ConnectionFactoryImpl.newInstance(PropsIO.loadFromClassLoaderSafe( "config/db1.properties" ) ) );
              ConnectionFactoryCloseable cf2 = ConnectionFactoryImpl.wrap( ConnectionFactoryImpl.newInstance(PropsIO.loadFromClassLoaderSafe( "config/db2.properties" ) ) )) {
            DBCompareConfig config = this.facade.readConfig( configIs );
            String schema = "PUBLIC";
            DBCompareOutput output = this.facade.compare( config, cf1, schema, cf2, schema );
            Assertions.assertNotNull( output );
            // print include equals
            log.info( "\nDiff (include equals)" );
            DBCompareUtils.printDiff( output, true );
            // print exclude equals
            log.info( "\nDiff (exclude equals)" );
            DBCompareUtils.printDiff( output, false );
        }
    }
}
