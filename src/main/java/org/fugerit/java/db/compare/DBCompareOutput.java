package org.fugerit.java.db.compare;

import lombok.Getter;
import org.fugerit.java.db.compare.diff.TableDiff;

import java.util.ArrayList;
import java.util.Collection;

public class DBCompareOutput {

    public DBCompareOutput() {
        this.tableDiffs = new ArrayList<>();
    }

    @Getter
    private Collection<TableDiff> tableDiffs;

}
