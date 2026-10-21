
ALTER TABLE groupofmembers
    RENAME COLUMN `groups` TO group_id;

ALTER TABLE groupofmembers
    RENAME COLUMN `user` TO user_id;
