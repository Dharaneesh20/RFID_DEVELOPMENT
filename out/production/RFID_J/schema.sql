CREATE TABLE admins (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    card_number VARCHAR(50) UNIQUE NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    department VARCHAR(50),
    role VARCHAR(10) NOT NULL DEFAULT 'user', -- 'user' or 'admin'
    enabled BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE attendance (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id),
    date DATE NOT NULL,
    login_time TIMESTAMP,
    logout_time TIMESTAMP,
    status VARCHAR(10) NOT NULL DEFAULT 'absent' -- 'present', 'absent', 'partial'
);

-- Sample admin (password: admin123, hash with BCrypt)
INSERT INTO admins (username, password_hash) VALUES (
    'admin',
    '$2a$10$wH6vQwQwQwQwQwQwQwQwQeQwQwQwQwQwQwQwQwQwQwQwQwQwQwQwQwQw' -- BCrypt hash for 'rsdh'
)
ON CONFLICT (username) DO UPDATE SET password_hash = EXCLUDED.password_hash;
