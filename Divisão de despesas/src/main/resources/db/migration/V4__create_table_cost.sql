CREATE TABLE IF NOT EXISTS cost (
                                    id BIGINT NOT NULL AUTO_INCREMENT,
                                    groups BIGINT NOT NULL,
                                    paid_for BIGINT NOT NULL,
                                    description VARCHAR(255) NOT NULL,
    total_value DECIMAL(10,2) NOT NULL,
    type VARCHAR(20),
    created_in TIMESTAMP NOT NULL,

    ```
    CONSTRAINT pk_cost PRIMARY KEY (id),

    CONSTRAINT fk_cost_group
        FOREIGN KEY (groups)
        REFERENCES groups(id),

    CONSTRAINT fk_cost_paid_for
        FOREIGN KEY (paid_for)
        REFERENCES user(id)
    ```

    );

CREATE INDEX idx_cost_group
    ON cost(groups);

CREATE INDEX idx_cost_paid_for
    ON cost(paid_for);
