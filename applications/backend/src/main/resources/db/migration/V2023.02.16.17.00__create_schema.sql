CREATE TABLE IF NOT EXISTS application (
    application_id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255) NOT NULL,
    salutation VARCHAR(255),
    number_of_person INT,
    wbs_present BOOLEAN DEFAULT false,
    earliest_move_in_date DATE,
    pets BOOLEAN DEFAULT false,
    status VARCHAR(255),
    applicant_comment TEXT,
    user_comment TEXT,
    creation_source VARCHAR(255),
    property_id BIGINT,
    creation_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modification_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);