DROP DATABASE IF EXISTS MapDB;
CREATE DATABASE MapDB;

DROP USER IF EXISTS 'MapUser'@'localhost';
CREATE USER 'MapUser'@'localhost' IDENTIFIED BY 'map'; 
GRANT ALL PRIVILEGES ON MapDB.* TO 'MapUser'@'localhost'; 
FLUSH PRIVILEGES;

USE MapDB;

CREATE TABLE playtennis (
    outlook varchar(10),
    temperature float(5,2),
    umidity varchar(10),
    wind varchar(10),
    play varchar(10)
); 

INSERT INTO playtennis VALUES('sunny', 30.3, 'high', 'weak', 'no');
INSERT INTO playtennis VALUES('sunny', 30.3, 'high', 'strong', 'no');
INSERT INTO playtennis VALUES('overcast', 30.0, 'high', 'weak', 'yes');
INSERT INTO playtennis VALUES('rain', 13.0, 'high', 'weak', 'yes');
INSERT INTO playtennis VALUES('rain', 0.0, 'normal', 'weak', 'yes');
INSERT INTO playtennis VALUES('rain', 0.0, 'normal', 'strong', 'no');
INSERT INTO playtennis VALUES('overcast', 0.1, 'normal', 'strong', 'yes');
INSERT INTO playtennis VALUES('sunny', 13.0, 'high', 'weak', 'no');
INSERT INTO playtennis VALUES('sunny', 0.1, 'normal', 'weak', 'yes');
INSERT INTO playtennis VALUES('rain', 12.0, 'normal', 'weak', 'yes');
INSERT INTO playtennis VALUES('sunny', 12.5, 'normal', 'strong', 'yes');
INSERT INTO playtennis VALUES('overcast', 12.5, 'high', 'strong', 'yes');
INSERT INTO playtennis VALUES('overcast', 29.21, 'normal', 'weak', 'yes');
INSERT INTO playtennis VALUES('rain', 12.5, 'high', 'strong', 'no');