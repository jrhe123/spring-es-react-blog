Create table spring_es_react_blog.`t_blog` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
    `title` varchar(255) DEFAULT NULL,
    `author` varchar(255) DEFAULT NULL,
    `content` mediumtext,
    `create_time` datetime DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    INDEX index_title_author (title, author),
    FULLTEXT INDEX index_content (content)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;

select * from t_blog where title like "%spring%" or content like "%spring%";