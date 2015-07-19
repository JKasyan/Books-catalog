CREATE DATABASE `catalog`; /*!40100 DEFAULT CHARACTER SET utf8 */

CREATE TABLE `users` (
`id_user` int(10) unsigned NOT NULL AUTO_INCREMENT,
`email` varchar(45) NOT NULL,
`password` varchar(255) NOT NULL,
`enabled` tinyint(4) NOT NULL DEFAULT \'1\',
PRIMARY KEY (`id_user`)\n) 
ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

CREATE TABLE `user_roles` (
  `id_user_role` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_user` int(10) unsigned NOT NULL,
  `role` varchar(45) NOT NULL DEFAULT ''ROLE_USER'',
  PRIMARY KEY (`id_user_role`),
  KEY `fk_users_user_roles_idx` (`id_user`),
  CONSTRAINT `fk_users_user_roles` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE `authors` (
  `id_author` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `second_name` varchar(45) NOT NULL,
  PRIMARY KEY (`id_author`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

CREATE TABLE `books` (
  `id_book` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `short_desc` varchar(45) DEFAULT NULL,
  `date_publ` varchar(4) NOT NULL,
  `title` varchar(45) NOT NULL,
  PRIMARY KEY (`id_book`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

CREATE TABLE `books_authors` (
  `id_author` int(10) unsigned NOT NULL,
  `id_book` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id_author`,`id_book`),
  KEY `fk_id_book_idx` (`id_book`),
  CONSTRAINT `fk_id_author` FOREIGN KEY (`id_author`) REFERENCES `authors` (`id_author`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_id_book` FOREIGN KEY (`id_book`) REFERENCES `books` (`id_book`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#Inserting in tables
#Encoded password = 123456
INSERT INTO users (id_user, email, password) VALUES ( 1, 'admin@mail.com', '$2a$10$8ioisWS.rQc9LraV4ohNTOKXpwn1/64VrEtAmgi.SdTC05JwcmgAO');

INSERT INTO user_roles (id_user, role) VALUES (1, 'ROLE_ADMIN');



