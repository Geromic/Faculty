--drop database StackOverflow

create database StackOverflow
go

use StackOverflow
go

create table WRole(
	WPid varchar(50) primary key,
	RDesc varchar(100)
)

create table Companies(
	Cid varchar(50) primary key
)

create table Groups(
	Gid int primary key,
	NoPpl int
)

create table Users(
	Uid int primary key,
	Name varchar(50),
	Surname varchar(50),
	Email varchar(100),
	Dob date,
	Gid int foreign key references Groups(Gid),
)

create table UserJobs(
	Uid int foreign key references Users(Uid),
	Cid varchar(50) foreign key references Companies(Cid),
	WPid varchar(50) foreign key references WRole(WPid),
	constraint UJid primary key (Uid, Cid, WPid)
)

create table Questions(
	Qid int primary key,
	Text varchar(100),
	Uid int foreign key references Users(Uid)
)

create table Tags(
	Tid int primary key,
	Name varchar(100),
	Qid int foreign key references Questions(Qid)
)

create table Answers(
	Aid int primary key,
	Text varchar(100),
	Votes int,
	Qid int foreign key references Questions(Qid),
	Uid int foreign key references Users(Uid)
)

create table Skills(
	Sid varchar(20) primary key
)

create table Badges(
	Bid varchar(20) primary key, 
	BDesc varchar(100)
)


create table UserSkills(
	Uid int foreign key references Users(Uid),
	Sid varchar(20) foreign key references Skills(Sid),
	constraint USid primary key (Uid, Sid)
)


create table UserBadges(
	Uid int foreign key references Users(Uid),
	Bid varchar(20) foreign key references Badges(Bid),
	constraint UBid primary key (Uid, Bid)
)


