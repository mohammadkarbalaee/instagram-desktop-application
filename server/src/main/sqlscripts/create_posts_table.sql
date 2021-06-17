CREATE TABLE posts
(
    post_id_number INT         NOT NULL AUTO_INCREMENT,
    owner     VARCHAR (50) NOT NULL ,
    caption   VARCHAR (5000) NOT NULL,
    image_path   VARCHAR (5000) NOT NULL,
    nth   INT NOT NULL,
    PRIMARY KEY (post_id_number)
)