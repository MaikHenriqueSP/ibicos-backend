ALTER TABLE statistics
    DROP COLUMN views_counter;

CREATE TABLE IF NOT EXISTS customer_statistics (
    id_customer_statistics INT PRIMARY KEY AUTO_INCREMENT,
    fk_id_statistics INT,
    FOREIGN KEY (fk_id_statistics) REFERENCES statistics(id_statistics)
);
