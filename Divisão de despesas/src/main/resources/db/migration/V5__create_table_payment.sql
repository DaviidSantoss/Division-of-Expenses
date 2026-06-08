CREATE TABLE IF NOT EXISTS payment (
                                       id BIGINT NOT NULL AUTO_INCREMENT,
                                       cost BIGINT NOT NULL,
                                       payer BIGINT NOT NULL,
                                       receiver BIGINT NOT NULL,
                                       value DECIMAL(10,2) NOT NULL,
    paid_in TIMESTAMP NOT NULL,

    ```
    CONSTRAINT pk_payment PRIMARY KEY (id),

    CONSTRAINT fk_payment_cost
        FOREIGN KEY (cost)
        REFERENCES cost(id),

    CONSTRAINT fk_payment_payer
        FOREIGN KEY (payer)
        REFERENCES user(id),

    CONSTRAINT fk_payment_receiver
        FOREIGN KEY (receiver)
        REFERENCES user(id)
    ```

    );

CREATE INDEX idx_payment_cost
    ON payment(cost);

CREATE INDEX idx_payment_payer
    ON payment(payer);

CREATE INDEX idx_payment_receiver
    ON payment(receiver);
