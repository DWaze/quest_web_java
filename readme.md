to run use this:

docker run --detach --name=[container_name] --env="MYSQL_ROOT_PASSWORD=[my_password]" --publish 3306:3306 mysql


then run this to create user and databse:

CREATE USER 'application'@'%' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON * . * TO 'application'@'%';
FLUSH PRIVILEGES;

Then run this to create database:

CREATE DATABASE quest_web;


