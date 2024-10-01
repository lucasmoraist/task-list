CREATE TABLE IF NOT EXISTS t_comment (
    id serial PRIMARY KEY,
    text TEXT NOT NULL,
    task_id BIGINT NOT NULL
);