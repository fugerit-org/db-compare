CREATE TABLE sql_safe_test (
    ID BIGINT NOT NULL,
    ID_GROUP BIGINT NOT NULL,
    description VARCHAR(128) NOT NULL,
    column_target_only DATE NOT NULL,
    different_type BIGINT NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE table_target_only (
   ID BIGINT NOT NULL,
   PRIMARY KEY (ID)
);