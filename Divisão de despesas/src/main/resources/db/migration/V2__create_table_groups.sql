CREATE TABLE IF NOT EXISTS groups (
                                      id BIGINT NOT NULL AUTO_INCREMENT,
                                      name VARCHAR(80) NOT NULL,
    created_by BIGINT NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_in TIMESTAMP NOT NULL,

    CONSTRAINT pk_groups PRIMARY KEY (id),

    CONSTRAINT fk_groups_created_by
    FOREIGN KEY (created_by)
    REFERENCES user(id)
    );

CREATE INDEX idx_groups_created_by
    ON groups(created_by);