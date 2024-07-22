CREATE TABLE sql_safe_test (
    ID BIGINT NOT NULL,
    ID_GROUP BIGINT NOT NULL,
    description VARCHAR(512) NOT NULL,
    column_source_only DATE NOT NULL,
    different_type DATE NOT NULL,
    PRIMARY KEY (ID)
);

COMMENT ON TABLE sql_safe_test IS 'first test table';
COMMENT ON COLUMN sql_safe_test.ID IS 'id colum';
COMMENT ON COLUMN sql_safe_test.ID_GROUP IS 'id group column';
COMMENT ON COLUMN sql_safe_test.description IS 'description column';
COMMENT ON COLUMN sql_safe_test.column_source_only IS 'column only on source db column';
COMMENT ON COLUMN sql_safe_test.different_type IS 'column with different type column';

CREATE TABLE table_source_only (
   ID BIGINT NOT NULL,
   PRIMARY KEY (ID)
);

COMMENT ON TABLE table_source_only IS 'second test table';
COMMENT ON COLUMN table_source_only.ID IS 'id colum';

CREATE TABLE table_foreign_key (
    ID BIGINT NOT NULL,
    ID_SQL_SAFE_TEST BIGINT NOT NULL,
    PRIMARY KEY (ID)
);

ALTER TABLE table_foreign_key ADD CONSTRAINT table_foreign_key_fk1 FOREIGN KEY ( ID_SQL_SAFE_TEST ) REFERENCES sql_safe_test ( id );

COMMENT ON TABLE table_foreign_key IS 'third test table';
COMMENT ON COLUMN table_foreign_key.ID IS 'id colum';
COMMENT ON COLUMN table_foreign_key.ID_SQL_SAFE_TEST IS 'ID_SQL_SAFE_TEST colum';