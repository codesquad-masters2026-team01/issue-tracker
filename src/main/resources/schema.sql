-- 외래 키 제크 잠시 해제 (테이블 삭제 순서 상관없이 깔끔하게 지우기 위함)
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `issue_labels`;
DROP TABLE IF EXISTS `issue_managers`;
DROP TABLE IF EXISTS `attachments`;
DROP TABLE IF EXISTS `comments`;
DROP TABLE IF EXISTS `issues`;
DROP TABLE IF EXISTS `labels`;
DROP TABLE IF EXISTS `milestones`;
DROP TABLE IF EXISTS `members`;
SET FOREIGN_KEY_CHECKS = 1;

-- 1. 회원 정보
CREATE TABLE `members` (
                           `id` bigint PRIMARY KEY AUTO_INCREMENT,
                           `user_id` varchar(50) UNIQUE NOT NULL,
                           `name` varchar(50) NOT NULL,
                           `password` varchar(255) NOT NULL,
                           `email` varchar(100) NOT NULL,
                           `deleted_at` datetime DEFAULT NULL -- 기본값 NULL로 수정
);

-- 2. 마일스톤
CREATE TABLE `milestones` (
                              `id` bigint PRIMARY KEY AUTO_INCREMENT,
                              `name` varchar(100) NOT NULL,
                              `completion_date` date,
                              `description` varchar(255),
                              `is_opened` boolean DEFAULT true,
                              `deleted_at` datetime DEFAULT NULL -- 기본값 NULL로 수정
);

-- 3. 라벨
CREATE TABLE `labels` (
                          `id` bigint PRIMARY KEY AUTO_INCREMENT,
                          `name` varchar(50) NOT NULL,
                          `description` varchar(255),
                          `text_color` char(7) NOT NULL,
                          `background_color` char(7) NOT NULL,
                          `deleted_at` datetime DEFAULT NULL -- 삭제 관리 위해 추가
);

-- 4. 이슈
CREATE TABLE `issues` (
                          `id` bigint PRIMARY KEY AUTO_INCREMENT,
                          `title` varchar(255) NOT NULL,
                          `contents` text NOT NULL,
                          `milestone_id` bigint,
                          `author_id` bigint NOT NULL,
                          `is_opened` boolean DEFAULT true,
                          `created_at` datetime DEFAULT (now()),
                          `deleted_at` datetime DEFAULT NULL -- 기본값 NULL로 수정
);

-- 5. 댓글
CREATE TABLE `comments` (
                            `id` bigint PRIMARY KEY AUTO_INCREMENT,
                            `issue_id` bigint NOT NULL,
                            `author_id` bigint NOT NULL,
                            `contents` text NOT NULL,
                            `created_at` datetime DEFAULT (now()),
                            `deleted_at` datetime DEFAULT NULL -- 삭제 관리 위해 추가
);

-- 6. 첨부파일
CREATE TABLE `attachments` (
                               `id` bigint PRIMARY KEY AUTO_INCREMENT,
                               `issue_id` bigint,
                               `comment_id` bigint,
                               `file_name` varchar(255) NOT NULL,
                               `file_url` varchar(512) NOT NULL,
                               `file_size` bigint,
                               `content_type` varchar(50),
                               `created_at` datetime DEFAULT (now())
);

-- 7. 이슈 담당자 (연결 테이블 - 전략 2 적용)
CREATE TABLE `issue_managers` (
                                  `id` bigint PRIMARY KEY AUTO_INCREMENT, -- 대리키
                                  `issue_id` bigint NOT NULL,
                                  `member_id` bigint NOT NULL
);

-- 8. 이슈 라벨 (연결 테이블 - 전략 2 적용)
CREATE TABLE `issue_labels` (
                                `id` bigint PRIMARY KEY AUTO_INCREMENT, -- 대리키
                                `issue_id` bigint NOT NULL,
                                `label_id` bigint NOT NULL
);

-- 유니크 인덱스 (중복 할당 방지)
CREATE UNIQUE INDEX `issue_managers_index_0` ON `issue_managers` (`issue_id`, `member_id`);
CREATE UNIQUE INDEX `issue_labels_index_1` ON `issue_labels` (`issue_id`, `label_id`);

-- 외래 키 설정
ALTER TABLE `issues` ADD FOREIGN KEY (`author_id`) REFERENCES `members` (`id`);
ALTER TABLE `issues` ADD FOREIGN KEY (`milestone_id`) REFERENCES `milestones` (`id`);
ALTER TABLE `comments` ADD FOREIGN KEY (`author_id`) REFERENCES `members` (`id`);
ALTER TABLE `comments` ADD FOREIGN KEY (`issue_id`) REFERENCES `issues` (`id`) ON DELETE CASCADE;
ALTER TABLE `attachments` ADD FOREIGN KEY (`issue_id`) REFERENCES `issues` (`id`) ON DELETE CASCADE;
ALTER TABLE `attachments` ADD FOREIGN KEY (`comment_id`) REFERENCES `comments` (`id`) ON DELETE CASCADE;
ALTER TABLE `issue_managers` ADD FOREIGN KEY (`issue_id`) REFERENCES `issues` (`id`) ON DELETE CASCADE;
ALTER TABLE `issue_managers` ADD FOREIGN KEY (`member_id`) REFERENCES `members` (`id`);
ALTER TABLE `issue_labels` ADD FOREIGN KEY (`issue_id`) REFERENCES `issues` (`id`) ON DELETE CASCADE;
ALTER TABLE `issue_labels` ADD FOREIGN KEY (`label_id`) REFERENCES `labels` (`id`);