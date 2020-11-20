ALTER TABLE `user` 
	ADD CONSTRAINT user_email_unique UNIQUE (email),
	ADD COLUMN validation_token VARCHAR(64),
	ADD COLUMN is_account_confirmed BOOL,
	ADD COLUMN account_recovery_token VARCHAR(64);


