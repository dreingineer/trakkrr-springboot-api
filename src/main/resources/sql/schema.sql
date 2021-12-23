CREATE TABLE IF NOT EXISTS users(
    id BIGINT AUTO_INCREMENT NOT NULL,
    firstName VARCHAR(255) NOT NULL,
    lastName VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    isActive BIT DEFAULT 1,
    userType VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS contactDetails(
    id BIGINT AUTO_INCREMENT NOT NULL,
    contactDetails VARCHAR(255) NOT NULL,
    contactType VARCHAR(255) NOT NULL,
    isPrimary BIT DEFAULT 1,
    userId BIGINT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (userId) REFERENCES users(id)
);