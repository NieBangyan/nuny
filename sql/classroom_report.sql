-- Additive migration: preserves existing classroom records.
CREATE TABLE IF NOT EXISTS classroom_student (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  student_name VARCHAR(50) NOT NULL,
  seat_row INT NOT NULL,
  UNIQUE KEY uk_classroom_student_name (student_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS student_practice_records (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  student_name VARCHAR(50) NOT NULL,
  is_completed INT NOT NULL DEFAULT 0,
  practice_date DATE NOT NULL,
  remark VARCHAR(500),
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  KEY idx_practice_student_date (student_name, practice_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
