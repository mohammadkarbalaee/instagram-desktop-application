CREATE TABLE followings
(
    user_id_number INT         NOT NULL AUTO_INCREMENT,
    username     VARCHAR (50) NOT NULL ,
    following    VARCHAR (50) NOT NULL,
    PRIMARY KEY (user_id_number)
)