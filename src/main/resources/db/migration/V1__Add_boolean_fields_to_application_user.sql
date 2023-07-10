ALTER TABLE application_user
    ADD COLUMN account_non_expired TINYINT(3) DEFAULT TRUE,
    ADD COLUMN account_non_locked TINYINT(3) DEFAULT TRUE,
    ADD COLUMN credentials_non_expired TINYINT(3) DEFAULT TRUE,
    ADD COLUMN enabled TINYINT(3) DEFAULT TRUE;
