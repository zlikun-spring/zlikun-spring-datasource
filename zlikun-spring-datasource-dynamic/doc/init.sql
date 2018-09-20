-- 创建测试库
CREATE DATABASE IF NOT EXISTS `test` DEFAULT CHARACTER SET utf8;

-- 创建测试表
CREATE TABLE IF NOT EXISTS `test`.`tbl_user` (
	`id` BIGINT PRIMARY KEY AUTO_INCREMENT,
	`username` VARCHAR(64) NOT NULL,
	`password` CHAR(32) NOT NULL,
	`create_time` DATETIME NOT NULL
);

-- 插入数据
INSERT INTO `test`.`tbl_user` (`username`, `password`, `create_time`) VALUES ('zlikun', MD5('123456'), NOW());