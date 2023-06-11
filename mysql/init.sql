Create table spring_es_react_blog.`t_blog` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
    `title` varchar(255) DEFAULT NULL,
    `author` varchar(255) DEFAULT NULL,
    `content` text,
    `create_time` datetime(6) DEFAULT NULL,
    `update_time` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id`),
    INDEX index_title_author (title, author),
    FULLTEXT INDEX index_content (content)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;

select * from t_blog where title like "%spring%" or content like "%spring%";

ALTER TABLE `spring_es_react_blog`.`t_blog` 
ADD FULLTEXT INDEX `index_title_author_content` (`title`, `author`, `content`) VISIBLE;
;

explain SELECT * FROM spring_es_react_blog.t_blog
	where title like "%title%"
    or author like "%title%"
    or content like "%title%"
    order by create_time desc;