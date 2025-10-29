CREATE DATABASE school;

\c school

CREATE TABLE IF NOT EXISTS students (
	student_id INT GENERATED ALWAYS AS IDENTITY,
	first_name VARCHAR(50) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	enrollment_date DATE,
	PRIMARY KEY (student_id)
);
    
INSERT INTO students
(first_name, last_name, email, enrollment_date) VALUES
('John', 'Doe', 'john.doe@example.com', '2023-09-01'),
('Jane', 'Smith', 'jane.smith@example.com', '2023-09-01'),
('Jim', 'Beam', 'jim.beam@example.com', '2023-09-02');
    
