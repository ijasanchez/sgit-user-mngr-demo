DROP TABLE IF EXISTS users;

CREATE TABLE users 
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    email varchar(255), 
    first_name varchar(255) NOT NULL, 
    last_name varchar(255) NOT NULL    
);

INSERT INTO users(email, first_name, last_name) VALUES
('rigura@gmail.com', 'Rigoberto', 'Uran'),
('luchera@gmail.com', 'Lucho', 'Herrera');