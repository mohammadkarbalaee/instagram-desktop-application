CREATE TABLE likes
(
    like_id_number INT         NOT NULL AUTO_INCREMENT,
    liker     VARCHAR (50) NOT NULL ,
    post   VARCHAR (50) NOT NULL,
    PRIMARY KEY (like_id_number)
)