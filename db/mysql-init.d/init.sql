CREATE USER 'root'@'%' IDENTIFIED BY 'root';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION;

CREATE DATABASE ideacloud;

CREATE TABLE IF NOT EXISTS `ideacloud`.`users` (
`user_id` bigint NOT NULL AUTO_INCREMENT,
`created` datetime NOT NULL,
`updated` datetime NOT NULL,
`email` varchar(255) NOT NULL,
`name` varchar(50) NOT NULL,
`password` varchar(255) NOT NULL,
`role` varchar(20) NOT NULL,
PRIMARY KEY (`user_id`) USING BTREE,
UNIQUE KEY `email` (`email`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

CREATE TABLE IF NOT EXISTS `ideacloud`.`access_tokens` (
`access_token_id` bigint NOT NULL AUTO_INCREMENT,
`created` datetime NOT NULL,
`updated` datetime NOT NULL,
`token` varchar(255) NOT NULL,
`userId` bigint NOT NULL,
PRIMARY KEY (`access_token_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;