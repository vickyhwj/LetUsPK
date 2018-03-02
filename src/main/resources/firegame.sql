/*
Navicat MySQL Data Transfer

Source Server         : test1
Source Server Version : 50519
Source Host           : localhost:3306
Source Database       : firegame

Target Server Type    : MYSQL
Target Server Version : 50519
File Encoding         : 65001

Date: 2018-03-02 23:26:40
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `authority`
-- ----------------------------
DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(30) DEFAULT NULL,
  `roleId` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ro` (`roleId`),
  CONSTRAINT `fk_roleid` FOREIGN KEY (`roleId`) REFERENCES `role` (`roleId`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of authority
-- ----------------------------

-- ----------------------------
-- Table structure for `message`
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `msgId` int(11) NOT NULL AUTO_INCREMENT,
  `from` varchar(10) DEFAULT NULL,
  `to` varchar(10) DEFAULT NULL,
  `content` varchar(100) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `isRead` int(11) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  PRIMARY KEY (`msgId`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------

-- ----------------------------
-- Table structure for `relationship`
-- ----------------------------
DROP TABLE IF EXISTS `relationship`;
CREATE TABLE `relationship` (
  `userA` varchar(255) DEFAULT NULL,
  `userB` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of relationship
-- ----------------------------

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `roleId` varchar(20) NOT NULL,
  `description` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO role VALUES ('ROLE_ADMIN', 'ROLE_ADMIN');
INSERT INTO role VALUES ('ROLE_USER', 'ROLE_USER');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userid` varchar(10) NOT NULL,
  `password` varchar(10) DEFAULT NULL,
  `win` int(11) DEFAULT NULL,
  `fail` int(11) DEFAULT NULL,
  `last` int(11) DEFAULT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO user VALUES ('1', '1', '0', '0', null);
INSERT INTO user VALUES ('2', '2', '0', '0', null);

-- ----------------------------
-- Table structure for `userrole`
-- ----------------------------
DROP TABLE IF EXISTS `userrole`;
CREATE TABLE `userrole` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` varchar(10) DEFAULT NULL,
  `roleId` varchar(20) DEFAULT NULL,
  `isRun` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_u` (`userId`),
  KEY `fk_r` (`roleId`),
  CONSTRAINT `fk_r` FOREIGN KEY (`roleId`) REFERENCES `role` (`roleId`),
  CONSTRAINT `fk_u` FOREIGN KEY (`userId`) REFERENCES `user` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userrole
-- ----------------------------
INSERT INTO userrole VALUES ('24', '1', 'ROLE_USER', '1');
INSERT INTO userrole VALUES ('25', '2', 'ROLE_USER', '1');

-- ----------------------------
-- Procedure structure for `removeRole`
-- ----------------------------
DROP PROCEDURE IF EXISTS `removeRole`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `removeRole`(in rid varchar(20))
begin
	delete from authority where authority.roleId=rid;
	delete from userrole where userrole.roleId=rid;
	delete from `role`  where roleId=rid;
	
end
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `removeUser`
-- ----------------------------
DROP PROCEDURE IF EXISTS `removeUser`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `removeUser`(in userid varchar(10))
begin
	delete from relationship where userA=userid or userB=userid;
	delete from userrole where userrole.userId=userid;
	delete from message where message.`from`=userid or message.`to`=userid;
	delete from `user` where user.userid=userid;
	
end
;;
DELIMITER ;
