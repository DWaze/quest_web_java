to run use this:

sudo docker run --detach --name=mysql --env="MYSQL_ROOT_PASSWORD=password" --publish 3306:3306 mysql

then run this to create user and databse:

CREATE USER 'application'@'%' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON * . * TO 'application'@'%';
FLUSH PRIVILEGES;

Then run this to create database:

CREATE DATABASE quest_web;


