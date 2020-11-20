ALTER TABLE `evaluate`
	CHANGE customer_evaluatad customer_evaluated BOOLEAN,
	ADD COLUMN fk_id_service_category INT(11),
        ADD CONSTRAINT fk_id_service_category 
			FOREIGN KEY(fk_id_service_category) REFERENCES service_category(id_service_category);
