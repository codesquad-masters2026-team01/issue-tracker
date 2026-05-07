-- 기존 테이블이 있다면 삭제 (초기화용)
DROP TABLE IF EXISTS labels;

-- 라벨 테이블 생성 (전략 2: 대리키 적용)
CREATE TABLE labels (
                        id               BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name             VARCHAR(50) NOT NULL,
                        description      VARCHAR(255),
                        text_color       VARCHAR(7),
                        background_color VARCHAR(7),
                        deleted_at       DATETIME DEFAULT NULL
);

-- 테스트용 데이터 삽입
INSERT INTO labels (name, description, text_color, background_color)
VALUES ('bug', '서비스 결함 수정', '#ffffff', '#ff0000');