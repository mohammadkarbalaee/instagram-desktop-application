CREATE TABLE comments
(
    comment_id_number INT         NOT NULL AUTO_INCREMENT,
    text     VARCHAR (5000) NOT NULL ,
    author   VARCHAR (50) NOT NULL,
    post   VARCHAR (50) NOT NULL,
    PRIMARY KEY (comment_id_number)
)