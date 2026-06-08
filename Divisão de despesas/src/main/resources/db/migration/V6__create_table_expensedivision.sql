CREATE TABLE IF NOT EXISTS expensedivision (
                                               id BIGINT NOT NULL AUTO_INCREMENT,
                                               cost BIGINT NOT NULL,
                                               user BIGINT NOT NULL,
                                               value_expense DECIMAL(10,2) NOT NULL,

    ```
    CONSTRAINT pk_expensedivision PRIMARY KEY (id),

    CONSTRAINT fk_expensedivision_cost
        FOREIGN KEY (cost)
        REFERENCES cost(id),

    CONSTRAINT fk_expensedivision_user
        FOREIGN KEY (user)
        REFERENCES user(id),

    CONSTRAINT uk_expensedivision_cost_user
        UNIQUE (cost, user)
    ```

    );

CREATE INDEX idx_expensedivision_cost
    ON expensedivision(cost);

CREATE INDEX idx_expensedivision_user
    ON expensedivision(user);
