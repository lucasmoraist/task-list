CREATE TABLE IF NOT EXISTS comments (
    id serial PRIMARY KEY,
    text VARCHAR(255) NOT NULL,
    task_id BIGINT,
    CONSTRAINT fk_task FOREIGN KEY (task_id) REFERENCES tasks(id)
);