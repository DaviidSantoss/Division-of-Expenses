CREATE TABLE IF NOT EXISTS groupofmembers (
                                              id BIGINT NOT NULL AUTO_INCREMENT,
                                              groups BIGINT NOT NULL,
                                              user BIGINT NOT NULL,
                                              entered TIMESTAMP NOT NULL,

                                              ```
                                              CONSTRAINT pk_groupofmembers PRIMARY KEY (id),

                                              CONSTRAINT fk_groupofmembers_group
                                                  FOREIGN KEY (groups)
                                                  REFERENCES groups(id),

                                              CONSTRAINT fk_groupofmembers_user
                                                  FOREIGN KEY (user)
                                                  REFERENCES user(id),

                                              CONSTRAINT uk_group_member
                                                  UNIQUE (groups, user)
                                              ```

);
