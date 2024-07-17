CREATE TABLE sql_safe_test (
    ID BIGINT NOT NULL,
    ID_GROUP BIGINT NOT NULL,
    description VARCHAR(512) NOT NULL,
    column_source_only DATE NOT NULL,
    different_type DATE NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE table_source_only (
   ID BIGINT NOT NULL,
   PRIMARY KEY (ID)
);