CREATE TABLE IF NOT EXISTS user (
                                    id BIGINT NOT NULL AUTO_INCREMENT,
                                    enabled BOOLEAN NOT NULL DEFAULT TRUE,
                                    name VARCHAR(80) NOT NULL,
    email VARCHAR(80) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_in TIMESTAMP NOT NULL,

    ```
    CONSTRAINT pk_user PRIMARY KEY (id),
    CONSTRAINT uk_user_email UNIQUE (email)
    ```

    );
