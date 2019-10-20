/*
Navicat MySQL Data Transfer

Source Server         : MyDB
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : qq

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2016-02-06 22:05:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `sex` varchar(1) DEFAULT NULL,
  `birthday` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `live` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `login` varchar(1) CHARACTER SET utf8 DEFAULT NULL,
  `friend` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'dante', '123', '1', '2016-02-06', '广州天河', '1', '2');
INSERT INTO `user` VALUES ('2', 'mmmm', '123', '?', '1900年 1月 1日', '广州越秀', '1', '1');
