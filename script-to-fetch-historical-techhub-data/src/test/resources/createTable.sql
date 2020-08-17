CREATE TABLE IF NOT EXISTS knolder(
id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
full_name VARCHAR(100) NOT NULL,
wordpress_id VARCHAR(100) UNIQUE,
email_id VARCHAR(100) UNIQUE,
active_status BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS techhub(
id VARCHAR(255) PRIMARY KEY,
email_id VARCHAR(100) NOT NULL,
uploaded_on TIMESTAMP NOT NULL,
title VARCHAR(255) NOT NULL
);
