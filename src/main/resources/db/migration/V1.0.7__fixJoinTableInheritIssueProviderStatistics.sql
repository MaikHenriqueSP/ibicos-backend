SET FOREIGN_KEY_CHECKS = 0;

ALTER TABLE provider_statistics MODIFY COLUMN id_provider_statistics INTEGER NOT NULL;
ALTER TABLE provider_statistics
    DROP PRIMARY KEY;

UPDATE provider_statistics pr
    SET pr.id_provider_statistics = pr.fk_id_statistics;

ALTER TABLE provider_statistics
    DROP FOREIGN KEY `provider_statistics_ibfk_1`;

ALTER TABLE provider_statistics
    DROP COLUMN fk_id_statistics;

ALTER TABLE provider_statistics
    ADD FOREIGN KEY fk_id_statistics(id_provider_statistics)
        REFERENCES statistics(id_statistics);

UPDATE ad
    SET ad.fk_id_provider_statistics =
            (SELECT pr.id_provider_statistics FROM provider_statistics pr, statistics st
             WHERE pr.fk_id_service_category = ad.fk_id_service_category
               AND st.fk_id_user = ad.fk_id_user
               AND st.id_statistics = pr.fk_id_statistics);



ALTER TABLE provider_statistics
    ADD PRIMARY KEY (ID);

SET FOREIGN_KEY_CHECKS = 1;