create database multiplayersnake;

use multiplayersnake;

create table player(
	idPlayer int  auto_increment primary key not null,
	usernamePlayer char(100) unique not null,
	passwordPlayer char(100) not null,
	statusPlayer int not null,
	score int,
    highScore int
);

drop table player ;

select * from player;

delete player from player
