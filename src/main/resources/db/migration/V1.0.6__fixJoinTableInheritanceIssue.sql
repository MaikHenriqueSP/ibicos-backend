
UPDATE customer_statistics cs SET cs.id_customer_statistics = cs.fk_id_statistics;

ALTER TABLE customer_statistics DROP FOREIGN KEY `customer_statistics_ibfk_1`;

ALTER TABLE customer_statistics
    ADD FOREIGN KEY fk_id_statistics(id_customer_statistics)
    REFERENCES statistics(id_statistics);

ALTER TABLE customer_statistics DROP COLUMN fk_id_statistics;