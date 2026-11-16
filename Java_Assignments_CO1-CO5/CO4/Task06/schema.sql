CREATE DATABASE IF NOT EXISTS campus_events;
USE campus_events;
CREATE TABLE IF NOT EXISTS registrations (
 id BIGINT PRIMARY KEY AUTO_INCREMENT,
 student_id VARCHAR(30) NOT NULL,
 event_id BIGINT NOT NULL,
 registered_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 CONSTRAINT uq_student_event UNIQUE(student_id,event_id)
);
