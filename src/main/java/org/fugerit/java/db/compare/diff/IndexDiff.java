package org.fugerit.java.db.compare.diff;

import lombok.Getter;
import org.fugerit.java.core.db.metadata.ColumnModel;
import org.fugerit.java.core.db.metadata.IndexModel;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.db.compare.DBCompareConfig;

import java.util.Comparator;
import java.util.stream.Collectors;

public class IndexDiff {

    public IndexDiff(String name, DBCompareConfig config, IndexModel sourceColumn, IndexModel targetColumn) {
        this.name = name;
        this.config = config;
        this.sourceIndex = sourceIndex;
        this.targetIndex = targetIndex;
        this.comparator = ( e1, e2 ) -> StringUtils.concat( ",", e1.getColumnList().stream().map( c -> c.getName() ).collect(Collectors.toList()) )
                .compareTo( StringUtils.concat( ",", e2.getColumnList().stream().map( c -> c.getName() ).collect(Collectors.toList()) ) );
    }

    private DBCompareConfig config;

    @Getter
    private String name;

    @Getter
    private IndexModel sourceIndex;

    @Getter
    private IndexModel targetIndex;

    private Comparator<IndexModel> comparator;

    public boolean isSourceExists() {
        return this.getSourceIndex() != null;
    }

    public boolean isTargetExists() {
        return this.getTargetIndex() != null;
    }

    public boolean isSourceTargetEqual() {
        return this.isSourceExists() && this.isTargetExists() && this.comparator.compare( this.getSourceIndex(), this.getTargetIndex() ) == 0;
    }

}

