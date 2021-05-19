ALTER TABLE ad ADD COLUMN fk_id_provider_statistics INTEGER;

UPDATE ad
    SET ad.fk_id_provider_statistics =
                  (SELECT pr.id_provider_statistics FROM provider_statistics pr, statistics st
                   WHERE pr.fk_id_service_category = ad.fk_id_service_category
                     AND st.fk_id_user = ad.fk_id_user
                     AND st.id_statistics = pr.fk_id_statistics);

ALTER TABLE ad
    ADD CONSTRAINT fk_id_provider_statistics
        FOREIGN KEY (fk_id_provider_statistics)
            REFERENCES provider_statistics(id_provider_statistics);