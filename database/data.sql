CREATE TABLE dogs( 
	id INT GENERATED ALWAYS AS IDENTITY,
	name varchar(40) NOT NULL,
	old integer NOT NULL,
	race varchar(40) NOT NULL,
	owner varchar(40) NOT NULL,
	PRIMARY KEY(id)
);