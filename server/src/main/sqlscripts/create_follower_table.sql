CREATE TABLE followers
(
    user_id_number INT         NOT NULL AUTO_INCREMENT,
    username     VARCHAR (50) NOT NULL ,
    follower   VARCHAR (50) NOT NULL,
    PRIMARY KEY (user_id_number)
)