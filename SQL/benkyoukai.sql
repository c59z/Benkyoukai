SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `title` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章内容',
  `summary` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文章摘要(子标题)',
  `category_tags` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属分类id(可以多个，使用\",\"分割)',
  `thumbnail` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '缩略图',
  `is_top` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '是否置顶（0否，1是）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '状态（0已发布，1草稿）',
  `view_count` bigint NULL DEFAULT 0 COMMENT '访问量',
  `like_count` bigint NULL DEFAULT 0 COMMENT '点赞量',
  `create_by` bigint NOT NULL COMMENT '文章创建者id',
  `create_time` datetime(0) NOT NULL COMMENT '创建文章的时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改文章的时间',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文章表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (1, '关于本站', '# 关于本站\n\nHello\nこんにちは！\n你好\n\n该项目创建的目的是为了学习日语！大家可以在这里分享讨论学习中出现的问题，也可以作为学习日语的小工具！\n\n项目依赖（包括但不限于）：\n## 前端\n- Vue\n- Vue-router\n- Bootstrap\n- i18n\n- Vuex\n- axios\n\n## 后端\n- SpringBoot\n- Spring Security\n- Mybatis\n- jwt\n- redis\n', NULL, '其他', 'http://localhost:7777/img/435124331.jpg', '0', '1', 324, 1, 1, '2024-08-16 21:57:12', NULL, 0);
INSERT INTO `article` VALUES (9, '关于如何在本网站发送图片', '# 关于如何在本网站发送图片\n\n大伙应该发现了，该网站不想百度贴吧那样能够直接发送图片，在本网站发送图片需要先将图片上传到任意图床\nQ：什么叫图床？\nA：可以把它理解成Google Drive（Google ドライブ）或者百度网盘一样的东西，不过图床只能上传图片，上传之后可以复制它的图片链接直接粘贴到该网站的编辑栏中即可！\n\n格式为：![图片名称，可以自定义或者不写](图片地址)\n\n推荐几个图床，绝对不是打广告！我没收钱\n- SM.MS https://sm.ms/\n- https://imgloc.com/', NULL, '其他,技巧', 'http://localhost:7777/img/435124331.jpg', '0', '0', 0, 0, 1, '2024-08-10 21:05:49', NULL, 0);
INSERT INTO `article` VALUES (10, '本网站支持Makedown语法', '如果您不知道Makedown语法是什么也没关系，直接输入向发布的内容即可！', NULL, '其他', 'http://localhost:7777/img/435124331.jpg', '0', '1', 4, 1, 1, '2024-08-10 21:16:01', NULL, 0);
INSERT INTO `article` VALUES (11, '图片发布测试!', 'PixivID : 89478080\n![89478080.jpg](https://s2.loli.net/2022/05/15/NYV3RZxCQqE1t9b.jpg)\n\nPixivID : 20985074\n![20985074_p0.jpg](https://s2.loli.net/2022/01/24/lYEGKZt5wxhUuzX.jpg)\n\nPixivID : 70060680\n![70060680_p0.png](https://s2.loli.net/2022/11/13/ijZnIxDaFWfeEBY.png)', NULL, '其他', 'http://localhost:7777/img/435124331.jpg', '0', '1', 17, 1, 1, '2024-08-09 21:54:44', NULL, 0);
INSERT INTO `article` VALUES (12, '使用Postman修改文章测试', '# ゆるー', '之后需要删除该文章', '测试', 'http://localhost:7777/img/435124331.jpg', '0', '1', 11, 0, 1, '2024-08-10 21:17:33', '2024-08-01 16:53:31', 0);
INSERT INTO `article` VALUES (13, '这是一个本来就被删除的文章', '测试', '会删除的', '测试', 'http://localhost:7777/img/435124331.jpg', '0', '1', 0, 0, 1, '2024-08-09 21:54:48', NULL, 1);
INSERT INTO `article` VALUES (14, '使用前端发布第一篇文章!', '看看图片能不能正常显示\n![wallhavenzmdv1y.jpg](http://localhost:7777/img/1_1723987784710.jpg)', '这是使用前端发送的post请求', '测试,', 'http://localhost:7777/img/1_1723987784710.jpg', '0', '1', 43, 1, 1, '2024-08-18 21:29:54', '2024-08-18 21:58:48', 0);

-- ----------------------------
-- Table structure for articlelike
-- ----------------------------
DROP TABLE IF EXISTS `articlelike`;
CREATE TABLE `articlelike`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `article_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `create_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `state` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `article_id`(`article_id`, `user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of articlelike
-- ----------------------------
INSERT INTO `articlelike` VALUES (1, 1, 1, '2024-08-17 22:49:00', '1');
INSERT INTO `articlelike` VALUES (13, 11, 1, '2024-08-20 20:13:40', '1');
INSERT INTO `articlelike` VALUES (24, 14, 1, '2024-08-20 20:14:02', '1');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `article_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `create_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `parent_id` bigint NULL DEFAULT NULL,
  `state` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, 1, 1, '评论测试！', '2024-08-02 21:03:14', NULL, '1');
INSERT INTO `comment` VALUES (2, 1, 1, '评论测试！', '2024-08-02 21:03:18', NULL, '1');
INSERT INTO `comment` VALUES (3, 1, 1, '我要回复你这个\" 评论测试! \"！', '2024-08-02 21:41:02', 1, '1');
INSERT INTO `comment` VALUES (4, 1, 1, '我要回复你这个\" 评论测试! \"！', '2024-08-02 21:41:20', 2, '1');
INSERT INTO `comment` VALUES (5, 1, 1, '我要回复你这个 \"我要回复你这个\" 评论测试! \"！\"！', '2024-08-02 21:42:44', 3, '1');
INSERT INTO `comment` VALUES (6, 1, 1, '啦啦啦啦，这是我使用前端发送的第一个评论!', '2024-08-19 22:28:32', NULL, '1');
INSERT INTO `comment` VALUES (9, 1, 1, '这是我使用web端发送的第一个回复！', '2024-08-20 21:07:35', 6, '1');
INSERT INTO `comment` VALUES (20, 1, 1, '勝つさ', '2024-08-20 21:55:24', 9, '1');
INSERT INTO `comment` VALUES (21, 12, 1, 'ゆるーーーーー', '2024-08-21 21:12:15', NULL, '1');

-- ----------------------------
-- Table structure for commentlike
-- ----------------------------
DROP TABLE IF EXISTS `commentlike`;
CREATE TABLE `commentlike`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `comment_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `create_time` datetime(0) NOT NULL,
  `state` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `comment_id`(`comment_id`, `user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of commentlike
-- ----------------------------
INSERT INTO `commentlike` VALUES (1, 1, 1, '2024-08-02 21:28:47', '1');
INSERT INTO `commentlike` VALUES (2, 2, 1, '2024-08-02 21:28:59', '0');
INSERT INTO `commentlike` VALUES (3, 5, 1, '2024-08-02 21:46:19', '1');

-- ----------------------------
-- Table structure for kaiwa
-- ----------------------------
DROP TABLE IF EXISTS `kaiwa`;
CREATE TABLE `kaiwa`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `AudioRootDirPath` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `parentId` int NULL DEFAULT NULL,
  `isFile` int NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 129 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of kaiwa
-- ----------------------------
INSERT INTO `kaiwa` VALUES (1, '新・シャドーイング 日本語を話そう! 初~中級編 [英語・中国語・韓国語訳版]', '本书面向的是初级-中级的日语学习者。由6个单元组成。各个单元的难易度将会逐渐攀升。会话的脚本包含了：日常会话（和朋友、家人、店员、在打工的地方）、商务会话（和同事上司、交易对象等）、长对话、独白这些会话形式，请结合自己的学习水平和需求来进行练习。', 'https://s2.loli.net/2024/06/28/zXVj1bUK3tlcJZO.png', '/1', NULL, 0);
INSERT INTO `kaiwa` VALUES (2, 'シャドーイング日本語を話そう! 中~上級編 [英語・中国語・韓国語訳版]', 'リアルな日本語が自然に身につくシャドーイング教材が大幅改訂。各ユニットの会話が増え、音声はWEBダウンロードに。 ■改訂のおもなポイント ・各ユニットの会話が増え、より充実した内容に ・音声は付属CDからWEBダウンロード(MP3)に ・中級から上級(N5~N3)までの文法を網羅、無理なく段階的に学べる ・時代に合ったテーマ、トピック、シチュエーションに ■扱う表現や状況・場面がさらに充実 ・新しい語彙のほか、略語、カタカナ語、若者言葉、慣用句、ことわざなども ・家族、友達、店員、医者、同僚、上司など様々な対人関係を用意 ・親密度によって分けられているので、必要な状況・場面に応じて練習できる ・気軽な雑談会話からビジネス場面もあり、面接やプレゼンのトレーニングにも', 'https://s2.loli.net/2024/06/28/peubmNErIDA5cRj.png', '/2', NULL, 0);
INSERT INTO `kaiwa` VALUES (3, 'Unit1', NULL, NULL, '/1/Unit1', 1, 0);
INSERT INTO `kaiwa` VALUES (4, 'Unit2', NULL, NULL, '/1/Unit2', 1, 0);
INSERT INTO `kaiwa` VALUES (5, 'Unit3', NULL, NULL, '/1/Unit3', 1, 0);
INSERT INTO `kaiwa` VALUES (6, 'Unit4', NULL, NULL, '/1/Unit4', 1, 0);
INSERT INTO `kaiwa` VALUES (7, 'Unit5', NULL, NULL, '/1/Unit5', 1, 0);
INSERT INTO `kaiwa` VALUES (8, 'Unit6', NULL, NULL, '/1/Unit6', 1, 0);
INSERT INTO `kaiwa` VALUES (9, '01.mp3', NULL, NULL, '/1/Unit1/01.mp3', 3, 1);
INSERT INTO `kaiwa` VALUES (10, '02.mp3', NULL, NULL, '/1/Unit1/02.mp3', 3, 1);
INSERT INTO `kaiwa` VALUES (11, '03.mp3', NULL, NULL, '/1/Unit1/03.mp3', 3, 1);
INSERT INTO `kaiwa` VALUES (12, '04.mp3', NULL, NULL, '/1/Unit1/04.mp3', 3, 1);
INSERT INTO `kaiwa` VALUES (13, '05.mp3', NULL, NULL, '/1/Unit1/05.mp3', 3, 1);
INSERT INTO `kaiwa` VALUES (14, '06.mp4', NULL, NULL, '/1/Unit1/06.mp3', 3, 1);
INSERT INTO `kaiwa` VALUES (15, '07.mp3', NULL, NULL, '/1/Unit1/07.mp3', 3, 1);
INSERT INTO `kaiwa` VALUES (16, '08.mp3', NULL, NULL, '/1/Unit1/08.mp3', 3, 1);
INSERT INTO `kaiwa` VALUES (17, '09.mp3', NULL, NULL, '/1/Unit1/09.mp3', 3, 1);
INSERT INTO `kaiwa` VALUES (18, '10.mp3', NULL, NULL, '/1/Unit1/10.mp3', 3, 1);
INSERT INTO `kaiwa` VALUES (19, '11.mp3', NULL, NULL, '/1/Unit2/11.mp3', 4, 1);
INSERT INTO `kaiwa` VALUES (20, '12.mp3', NULL, NULL, '/1/Unit2/12.mp3', 4, 1);
INSERT INTO `kaiwa` VALUES (21, '13.mp3', NULL, NULL, '/1/Unit2/13.mp3', 4, 1);
INSERT INTO `kaiwa` VALUES (22, '14.mp3', NULL, NULL, '/1/Unit2/14.mp3', 4, 1);
INSERT INTO `kaiwa` VALUES (23, '15.mp3', NULL, NULL, '/1/Unit2/15.mp3', 4, 1);
INSERT INTO `kaiwa` VALUES (24, '16.mp3', NULL, NULL, '/1/Unit2/16.mp3', 4, 1);
INSERT INTO `kaiwa` VALUES (25, '17.mp3', NULL, NULL, '/1/Unit2/17.mp3', 4, 1);
INSERT INTO `kaiwa` VALUES (26, '18.mp3', NULL, NULL, '/1/Unit2/18.mp3', 4, 1);
INSERT INTO `kaiwa` VALUES (27, '19.mp3', NULL, NULL, '/1/Unit2/19.mp3', 4, 1);
INSERT INTO `kaiwa` VALUES (28, '20.mp3', NULL, NULL, '/1/Unit2/20.mp3', 4, 1);
INSERT INTO `kaiwa` VALUES (29, '21.mp3', NULL, NULL, '/1/Unit3/21.mp3', 5, 1);
INSERT INTO `kaiwa` VALUES (30, '22.mp3', NULL, NULL, '/1/Unit3/22.mp3', 5, 1);
INSERT INTO `kaiwa` VALUES (31, '23.mp3', NULL, NULL, '/1/Unit3/23.mp3', 5, 1);
INSERT INTO `kaiwa` VALUES (32, '24.mp3', NULL, NULL, '/1/Unit3/24.mp3', 5, 1);
INSERT INTO `kaiwa` VALUES (33, '25.mp3', NULL, NULL, '/1/Unit3/25.mp3', 5, 1);
INSERT INTO `kaiwa` VALUES (34, '26.mp3', NULL, NULL, '/1/Unit3/26.mp3', 5, 1);
INSERT INTO `kaiwa` VALUES (35, '27.mp3', NULL, NULL, '/1/Unit3/27.mp3', 5, 1);
INSERT INTO `kaiwa` VALUES (36, '28.mp3', NULL, NULL, '/1/Unit3/28.mp3', 5, 1);
INSERT INTO `kaiwa` VALUES (37, '29.mp3', NULL, NULL, '/1/Unit3/29.mp3', 5, 1);
INSERT INTO `kaiwa` VALUES (38, '30.mp3', NULL, NULL, '/1/Unit3/30.mp3', 5, 1);
INSERT INTO `kaiwa` VALUES (39, '31.mp3', NULL, NULL, '/1/Unit4/31.mp3', 6, 1);
INSERT INTO `kaiwa` VALUES (40, '32.mp3', NULL, NULL, '/1/Unit4/32.mp3', 6, 1);
INSERT INTO `kaiwa` VALUES (41, '33.mp3', NULL, NULL, '/1/Unit4/33.mp3', 6, 1);
INSERT INTO `kaiwa` VALUES (42, '34.mp3', NULL, NULL, '/1/Unit4/34.mp3', 6, 1);
INSERT INTO `kaiwa` VALUES (43, '35.mp3', NULL, NULL, '/1/Unit4/35.mp3', 6, 1);
INSERT INTO `kaiwa` VALUES (44, '36.mp3', NULL, NULL, '/1/Unit4/36.mp3', 6, 1);
INSERT INTO `kaiwa` VALUES (45, '37.mp3', NULL, NULL, '/1/Unit4/37.mp3', 6, 1);
INSERT INTO `kaiwa` VALUES (46, '38.mp3', NULL, NULL, '/1/Unit4/38.mp3', 6, 1);
INSERT INTO `kaiwa` VALUES (47, '39.mp3', NULL, NULL, '/1/Unit4/39.mp3', 6, 1);
INSERT INTO `kaiwa` VALUES (48, '40.mp3', NULL, NULL, '/1/Unit4/40.mp3', 6, 1);
INSERT INTO `kaiwa` VALUES (49, '41.mp3', NULL, NULL, '/1/Unit5/41.mp3', 7, 1);
INSERT INTO `kaiwa` VALUES (50, '42.mp3', NULL, NULL, '/1/Unit5/42.mp3', 7, 1);
INSERT INTO `kaiwa` VALUES (51, '43.mp3', NULL, NULL, '/1/Unit5/43.mp3', 7, 1);
INSERT INTO `kaiwa` VALUES (52, '44.mp3', NULL, NULL, '/1/Unit5/44.mp3', 7, 1);
INSERT INTO `kaiwa` VALUES (53, '45.mp3', NULL, NULL, '/1/Unit5/45.mp3', 7, 1);
INSERT INTO `kaiwa` VALUES (54, '46.mp3', NULL, NULL, '/1/Unit5/46.mp3', 7, 1);
INSERT INTO `kaiwa` VALUES (55, '47.mp3', NULL, NULL, '/1/Unit5/47.mp3', 7, 1);
INSERT INTO `kaiwa` VALUES (56, '48.mp3', NULL, NULL, '/1/Unit5/48.mp3', 7, 1);
INSERT INTO `kaiwa` VALUES (57, '49.mp3', NULL, NULL, '/1/Unit5/49.mp3', 7, 1);
INSERT INTO `kaiwa` VALUES (58, '50.mp3', NULL, NULL, '/1/Unit5/50.mp3', 7, 1);
INSERT INTO `kaiwa` VALUES (59, '51.mp3', NULL, NULL, '/1/Unit6/51.mp3', 8, 1);
INSERT INTO `kaiwa` VALUES (60, '52.mp3', NULL, NULL, '/1/Unit6/52.mp3', 8, 1);
INSERT INTO `kaiwa` VALUES (61, '53.mp3', NULL, NULL, '/1/Unit6/53.mp3', 8, 1);
INSERT INTO `kaiwa` VALUES (62, '54.mp3', NULL, NULL, '/1/Unit6/54.mp3', 8, 1);
INSERT INTO `kaiwa` VALUES (63, '中上級編_disc1', NULL, NULL, '/2/disc1', 2, 0);
INSERT INTO `kaiwa` VALUES (64, '中上級編_disc2', NULL, NULL, '/2/disc2', 2, 0);
INSERT INTO `kaiwa` VALUES (65, '01 01　タイトル', NULL, NULL, '/2/disc1/01.mp3', 63, 1);
INSERT INTO `kaiwa` VALUES (66, '02 Unit 1 Section 1 家族、夫婦、恋人との会話　(中級)', NULL, NULL, '/2/disc1/02.mp3', 63, 1);
INSERT INTO `kaiwa` VALUES (67, '02 Unit 1 Section 1 家族、夫婦、恋人との会話　(中級)', NULL, NULL, '/2/disc1/03.mp3', 63, 1);
INSERT INTO `kaiwa` VALUES (68, '02 Unit 1 Section 1 家族、夫婦、恋人との会話　(中級)', NULL, NULL, '/2/disc1/04.mp3', 63, 1);
INSERT INTO `kaiwa` VALUES (69, '02 Unit 1 Section 1 家族、夫婦、恋人との会話　(上級)', NULL, NULL, '/2/disc1/05.mp3', 63, 1);
INSERT INTO `kaiwa` VALUES (70, '02 Unit 1 Section 1 家族、夫婦、恋人との会話　(上級)', NULL, NULL, '/2/disc1/06.mp3', 63, 1);
INSERT INTO `kaiwa` VALUES (71, '02 Unit 1 Section 1 家族、夫婦、恋人との会話　(上級)', NULL, NULL, '/2/disc1/07.mp3', 63, 1);
INSERT INTO `kaiwa` VALUES (72, '02 Unit 1 Section 1 家族、夫婦、恋人との会話　(上級)', NULL, NULL, '/2/disc1/08.mp3', 63, 1);
INSERT INTO `kaiwa` VALUES (73, '02 Unit 1 Section 1 家族、夫婦、恋人との会話　(上級)', NULL, NULL, '/2/disc1/09.mp3', 63, 1);
INSERT INTO `kaiwa` VALUES (74, '10 Unit 2 Section 1 親しい友人との会話　(中級)', NULL, NULL, '/2/disc1/10.mp3', 63, 1);
INSERT INTO `kaiwa` VALUES (75, '11 Unit 2 Section 1 親しい友人との会話　(中級)', NULL, NULL, '/2/disc1/11.mp3', 63, 1);
INSERT INTO `kaiwa` VALUES (76, '12 Unit 2 Section 1 親しい友人との会話　(中級)', NULL, NULL, '/2/disc1/12.mp3', 63, 1);
INSERT INTO `kaiwa` VALUES (77, '13 Unit 2 Section 1 親しい友人との会話　(中級)', NULL, NULL, '/2/disc1/13.mp3', 63, 1);
INSERT INTO `kaiwa` VALUES (78, '14 Unit 2 Section 2 親しい友人との会話　(上級)', NULL, NULL, '/2/disc1/14.mp3', 63, 1);
INSERT INTO `kaiwa` VALUES (79, '15 Unit 2 Section 2 親しい友人との会話　(上級)', NULL, NULL, '/2/disc1/15.mp3', 63, 1);
INSERT INTO `kaiwa` VALUES (80, '16 Unit 2 Section 2 親しい友人との会話　(上級)', NULL, NULL, '/2/disc1/16.mp3', 63, 1);
INSERT INTO `kaiwa` VALUES (81, '17 Unit 2 Section 2 親しい友人との会話　(上級)', NULL, NULL, '/2/disc1/17.mp3', 63, 1);
INSERT INTO `kaiwa` VALUES (82, '18 Unit 2 Section 2 親しい友人との会話　(上級)', NULL, NULL, '/2/disc1/18.mp3', 63, 1);
INSERT INTO `kaiwa` VALUES (83, '19 Unit 3 Section 1 知人や近所の人などとの会話　(中級)', NULL, NULL, '/2/disc1/19.mp3', 63, 1);
INSERT INTO `kaiwa` VALUES (84, '20 Unit 3 Section 1 知人や近所の人などとの会話　(中級)', NULL, NULL, '/2/disc1/20.mp3', 63, 1);
INSERT INTO `kaiwa` VALUES (85, '21 Unit 3 Section 1 知人や近所の人などとの会話　(中級)', NULL, NULL, '/2/disc1/21.mp3', 63, 1);
INSERT INTO `kaiwa` VALUES (86, '22 Unit 3 Section 2 知人や近所の人などとの会話　(上級)', NULL, NULL, '/2/disc1/22.mp3', 63, 1);
INSERT INTO `kaiwa` VALUES (87, '23 Unit 3 Section 2 知人や近所の人などとの会話　(上級)', NULL, NULL, '/2/disc1/23.mp3', 63, 1);
INSERT INTO `kaiwa` VALUES (88, '24 Unit 3 Section 2 知人や近所の人などとの会話　(上級)', NULL, NULL, '/2/disc1/24.mp3', 63, 1);
INSERT INTO `kaiwa` VALUES (89, '25 Unit 3 Section 2 知人や近所の人などとの会話　(上級)', NULL, NULL, '/2/disc1/25.mp3', 63, 1);
INSERT INTO `kaiwa` VALUES (90, '26 Unit 3 Section 2 知人や近所の人などとの会話　(上級)', NULL, NULL, '/2/disc1/26.mp3', 63, 1);
INSERT INTO `kaiwa` VALUES (91, '27 Unit 3 Section 2 知人や近所の人などとの会話　(上級)', NULL, NULL, '/2/disc1/27.mp3', 63, 1);
INSERT INTO `kaiwa` VALUES (92, '28 Unit 4 Section 1 医者や店員などとの会話　(中級)', NULL, NULL, '/2/disc1/28.mp3', 63, 1);
INSERT INTO `kaiwa` VALUES (93, '29 Unit 4 Section 1 医者や店員などとの会話　(中級)', NULL, NULL, '/2/disc1/29.mp3', 63, 1);
INSERT INTO `kaiwa` VALUES (94, '30 Unit 4 Section 2 医者や店員などとの会話　(上級)', NULL, NULL, '/2/disc1/30.mp3', 63, 1);
INSERT INTO `kaiwa` VALUES (95, '31 Unit 4 Section 2 医者や店員などとの会話　(上級)', NULL, NULL, '/2/disc1/31.mp3', 63, 1);
INSERT INTO `kaiwa` VALUES (96, '32 Unit 4 Section 2 医者や店員などとの会話　(上級)', NULL, NULL, '/2/disc1/32.mp3', 63, 1);
INSERT INTO `kaiwa` VALUES (97, '33 Unit 4 Section 2 医者や店員などとの会話　(上級)', NULL, NULL, '/2/disc1/33.mp3', 63, 1);
INSERT INTO `kaiwa` VALUES (98, '01 Disk 2 タイトル', NULL, NULL, '/2/disc2/01.mp3', 64, 1);
INSERT INTO `kaiwa` VALUES (99, '02 Unit 5 Section 1 同僚との会話　(中級)', NULL, NULL, '/2/disc2/02.mp3', 64, 1);
INSERT INTO `kaiwa` VALUES (100, '03 Unit 5 Section 1 同僚との会話　(中級)', NULL, NULL, '/2/disc2/03.mp3', 64, 1);
INSERT INTO `kaiwa` VALUES (101, '04 Unit 5 Section 1 同僚との会話　(中級)', NULL, NULL, '/2/disc2/04.mp3', 64, 1);
INSERT INTO `kaiwa` VALUES (102, '05 Unit 5 Section 1 同僚との会話　(中級)', NULL, NULL, '/2/disc2/05.mp3', 64, 1);
INSERT INTO `kaiwa` VALUES (103, '06 Unit 5 Section 2 同僚との会話　(上級)', NULL, NULL, '/2/disc2/06.mp3', 64, 1);
INSERT INTO `kaiwa` VALUES (104, '07 Unit 5 Section 2 同僚との会話　(上級)', NULL, NULL, '/2/disc2/07.mp3', 64, 1);
INSERT INTO `kaiwa` VALUES (105, '08 Unit 5 Section 2 同僚との会話　(上級)', NULL, NULL, '/2/disc2/08.mp3', 64, 1);
INSERT INTO `kaiwa` VALUES (106, '09 Unit 5 Section 2 同僚との会話　(上級)', NULL, NULL, '/2/disc2/09.mp3', 64, 1);
INSERT INTO `kaiwa` VALUES (107, '10 Unit 5 Section 2 同僚との会話　(上級)', NULL, NULL, '/2/disc2/10.mp3', 64, 1);
INSERT INTO `kaiwa` VALUES (108, '11 Unit 5 Section 2 同僚との会話　(上級)', NULL, NULL, '/2/disc2/11.mp3', 64, 1);
INSERT INTO `kaiwa` VALUES (109, '12 Unit 6 Section 1 上司や部下との会話　(中級)', NULL, NULL, '/2/disc2/12.mp3', 64, 1);
INSERT INTO `kaiwa` VALUES (110, '13 Unit 6 Section 1 上司や部下との会話　(中級)', NULL, NULL, '/2/disc2/13.mp3', 64, 1);
INSERT INTO `kaiwa` VALUES (111, '14 Unit 6 Section 1 上司や部下との会話　(中級)', NULL, NULL, '/2/disc2/14.mp3', 64, 1);
INSERT INTO `kaiwa` VALUES (112, '15 Unit 6 Section 2 上司や部下との会話　(上級)', NULL, NULL, '/2/disc2/15.mp3', 64, 1);
INSERT INTO `kaiwa` VALUES (113, '16 Unit 6 Section 2 上司や部下との会話　(上級)', NULL, NULL, '/2/disc2/16.mp3', 64, 1);
INSERT INTO `kaiwa` VALUES (114, '17 Unit 6 Section 2 上司や部下との会話　(上級)', NULL, NULL, '/2/disc2/17.mp3', 64, 1);
INSERT INTO `kaiwa` VALUES (115, '18 Unit 6 Section 2 上司や部下との会話　(上級)', NULL, NULL, '/2/disc2/18.mp3', 64, 1);
INSERT INTO `kaiwa` VALUES (116, '19 Unit 7 Section 1 社外の人や面接官などとの会話　(中級)', NULL, NULL, '/2/disc2/19.mp3', 64, 1);
INSERT INTO `kaiwa` VALUES (117, '20 Unit 7 Section 1 社外の人や面接官などとの会話　(中級)', NULL, NULL, '/2/disc2/20.mp3', 64, 1);
INSERT INTO `kaiwa` VALUES (118, '21 Unit 7 Section 1 社外の人や面接官などとの会話　(中級)', NULL, NULL, '/2/disc2/21.mp3', 64, 1);
INSERT INTO `kaiwa` VALUES (119, '22 Unit 7 Section 2 社外の人や面接官などとの会話　(上級)', NULL, NULL, '/2/disc2/22.mp3', 64, 1);
INSERT INTO `kaiwa` VALUES (120, '23 Unit 7 Section 2 社外の人や面接官などとの会話　(上級)', NULL, NULL, '/2/disc2/23.mp3', 64, 1);
INSERT INTO `kaiwa` VALUES (121, '24 Unit 8 Section 1 長い会話・スピーチなど　(上級)', NULL, NULL, '/2/disc2/24.mp3', 64, 1);
INSERT INTO `kaiwa` VALUES (122, '25 Unit 8 Section 2 長い会話・スピーチなど　(上級)', NULL, NULL, '/2/disc2/25.mp3', 64, 1);
INSERT INTO `kaiwa` VALUES (123, '26 Unit 8 Section 3 長い会話・スピーチなど　(上級)', NULL, NULL, '/2/disc2/26.mp3', 64, 1);
INSERT INTO `kaiwa` VALUES (124, '27 Unit 8 Section 4 長い会話・スピーチなど　(上級)', NULL, NULL, '/2/disc2/27.mp3', 64, 1);
INSERT INTO `kaiwa` VALUES (125, '28 Unit 8 Section 5 長い会話・スピーチなど　(上級)', NULL, NULL, '/2/disc2/28.mp3', 64, 1);
INSERT INTO `kaiwa` VALUES (126, '29 Unit 8 Section 6 長い会話・スピーチなど　(上級)', NULL, NULL, '/2/disc2/29.mp3', 64, 1);
INSERT INTO `kaiwa` VALUES (127, '30 Unit 8 Section 7 長い会話・スピーチなど　(上級)', NULL, NULL, '/2/disc2/30.mp3', 64, 1);
INSERT INTO `kaiwa` VALUES (128, '31 Unit 8 Section 8 長い会話・スピーチなど　(上級)', NULL, NULL, '/2/disc2/31.mp3', 64, 1);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `perm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sys_menu_id_uindex`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '查看', 'sys:view');
INSERT INTO `sys_menu` VALUES (2, '发表', 'sys:posted');
INSERT INTO `sys_menu` VALUES (3, '点赞', 'sys:like');
INSERT INTO `sys_menu` VALUES (5, '删除', 'sys:delete');
INSERT INTO `sys_menu` VALUES (6, '超级管理员', 'sys:root');

-- ----------------------------
-- Table structure for sys_menu_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_role`;
CREATE TABLE `sys_menu_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` int NOT NULL,
  `menu_id` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sys_menu_role_id_uindex`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu_role
-- ----------------------------
INSERT INTO `sys_menu_role` VALUES (1, 1, 1);
INSERT INTO `sys_menu_role` VALUES (2, 1, 2);
INSERT INTO `sys_menu_role` VALUES (3, 1, 3);
INSERT INTO `sys_menu_role` VALUES (5, 1, 5);
INSERT INTO `sys_menu_role` VALUES (6, 1, 6);
INSERT INTO `sys_menu_role` VALUES (7, 2, 1);
INSERT INTO `sys_menu_role` VALUES (8, 2, 2);
INSERT INTO `sys_menu_role` VALUES (9, 2, 3);
INSERT INTO `sys_menu_role` VALUES (11, 2, 5);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色的标识符',
  `remark` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '说明该身份可以做什么事情',
  `createDate` date NOT NULL,
  `updated` date NOT NULL,
  `state` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sys_role_id_uindex`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', '拥有管理该网站的一切权限', '2022-11-14', '2022-11-14', 1);
INSERT INTO `sys_role` VALUES (2, '普通用户', 'normal', '普通用户', '2022-11-14', '2022-11-14', 1);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `birthday` date NULL DEFAULT NULL,
  `sex` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `avatar` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_id` int NOT NULL,
  `state` int NOT NULL DEFAULT 1,
  `createDate` datetime(0) NULL DEFAULT NULL,
  `updateDate` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sys_user_id_uindex`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'yuki', 'ゆき', '$2a$10$Mwpvk2k2dU/8gxt2Et7Yn.4n70G76TYJ.ijzjU0xGDk17wa1kMayC', '2004-03-25', '男', 'bzh@gmail.com', 'http://localhost:7777/img/avatar/1_1723820783923.jpg', 1, 1, '2022-11-14 00:00:00', '2024-08-16 23:06:26');
INSERT INTO `sys_user` VALUES (3, 'user', NULL, '$2a$10$e2lIQjAnUqz2B2Zrp2arYu/mr94/UU2pgxtMl3uKklKjSdi8UhMN6', '2022-11-13', NULL, 'user@163.com', 'https://s2.loli.net/2022/11/03/ZbjQJeDhzENP9Os.png', 2, 1, '2022-11-14 00:00:00', NULL);
INSERT INTO `sys_user` VALUES (10, '测试用户2', NULL, '$2a$10$OV39d0nqK1/tyJA4uPuO.eSTMR/oymqbgD6Cij8WGHb0Y1Tdi7nm6', NULL, NULL, NULL, NULL, 2, 1, NULL, NULL);
INSERT INTO `sys_user` VALUES (11, 'yuru', NULL, '$2a$10$9FoS9v4u2oVRbbE6wBxEXusVsAiYnkuFa0zX3/n7b4CSZrYQldIg.', NULL, NULL, NULL, NULL, 2, 1, NULL, NULL);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sys_user_role_id_uindex`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1, 1);
INSERT INTO `sys_user_role` VALUES (2, 3, 2);
INSERT INTO `sys_user_role` VALUES (3, 10, 2);
INSERT INTO `sys_user_role` VALUES (4, 11, 2);

-- ----------------------------
-- Table structure for userfavorite
-- ----------------------------
DROP TABLE IF EXISTS `userfavorite`;
CREATE TABLE `userfavorite`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `article_id` bigint NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of userfavorite
-- ----------------------------
INSERT INTO `userfavorite` VALUES (5, 1, 9, '2024-08-03 19:35:16');
INSERT INTO `userfavorite` VALUES (21, 1, 1, '2024-08-17 22:49:04');
INSERT INTO `userfavorite` VALUES (23, 1, 11, '2024-08-17 23:01:23');
INSERT INTO `userfavorite` VALUES (24, 1, 14, '2024-08-18 22:03:39');
INSERT INTO `userfavorite` VALUES (26, 1, 10, '2024-08-20 20:08:07');

-- ----------------------------
-- Triggers structure for table sys_user
-- ----------------------------
DROP TRIGGER IF EXISTS `after_user_insert`;
delimiter ;;
CREATE TRIGGER `after_user_insert` AFTER INSERT ON `sys_user` FOR EACH ROW BEGIN
    DECLARE userId INT;
    DECLARE roleId INT;
    
    SET userId = NEW.id;
    SET roleId = NEW.role_id;
    
    INSERT INTO sys_user_role (user_id, role_id) VALUES (userId, roleId);
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
