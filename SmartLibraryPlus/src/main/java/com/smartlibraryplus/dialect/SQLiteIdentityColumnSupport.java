package com.smartlibraryplus.dialect;

import org.hibernate.dialect.identity.IdentityColumnSupportImpl;

public class SQLiteIdentityColumnSupport extends IdentityColumnSupportImpl {
    @Override
    public boolean supportsIdentityColumns() {
        return true;
    }

    @Override
    public String getIdentitySelectString(String table, String column, int type) {
        return "select last_insert_rowid()";
    }

    @Override
    public String getIdentityColumnString(int type) {
        // SQLite requires the column to be declared as INTEGER PRIMARY KEY for autoincrement behavior
        return "integer primary key autoincrement";
    }

    @Override
    public boolean hasDataTypeInIdentityColumn() {
        return false;
    }
}
