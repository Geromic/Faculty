use StackOverflow

/*delete from all tables*/
EXEC sp_MSForEachTable 'DISABLE TRIGGER ALL ON ?'
GO
EXEC sp_MSForEachTable 'ALTER TABLE ? NOCHECK CONSTRAINT ALL'
GO
EXEC sp_MSForEachTable 'DELETE FROM ?'
GO
EXEC sp_MSForEachTable 'ALTER TABLE ? CHECK CONSTRAINT ALL'
GO
EXEC sp_MSForEachTable 'ENABLE TRIGGER ALL ON ?'
GO

/*Insert companies*/
insert into Companies
values('BitDefender')

insert into Companies
values('AAA')

insert into Companies
values('Microsoft')

insert into Companies
values('Samsung')


/*Insert work roles*/
insert into WRole
values('Software developer', 'basic desc')

insert into WRole
values('Code-monkey', 'basic desc')

insert into WRole
values('Tester', 'basic desc')

insert into WRole
values('Project manager', 'basic desc')

insert into WRole
values('Engineer', 'basic desc')

/*Insert skills*/
insert into Skills
values('C/C++')

insert into Skills
values('Java')

insert into Skills
values('Python')

insert into Skills
values('HTML')

/*Insert groups*/
insert into Groups
values(1,0)
insert into Groups
values(2,0)
insert into Groups
values(3,0)
go


CREATE OR ALTER TRIGGER insert_count ON Users
AFTER INSERT
AS 
BEGIN
UPDATE Groups SET NoPpl = NoPpl +1 FROM Inserted WHERE Groups.Gid = inserted.Gid; 
END;
GO

CREATE OR ALTER TRIGGER delete_count ON Users 
AFTER DELETE 
AS 
BEGIN 
UPDATE Groups SET NoPpl = NoPpl - 1 FROM Deleted WHERE Groups.Gid = deleted.Gid; 
END;

select * from Groups

/*Insert users*/
insert into Users
values(1, 'Miclea George', 'georgemiclea1@gmail.com', '1900-7-15', 1)

insert into Users
values(2, 'Silviu Gherman', 'djskarnav@yahoo.ro', '1945-1-1', 2)

insert into Users
values(3, 'Mos Craciun', 'moscraciun420@hotmail.com', '1420-1-1', 2)

insert into Users
values(4, 'Cristescu Andreea', 'andrecris@gmail.com', '1999-5-9', 1)

insert into Users
values(5, 'Andrei Mihai', 'mandrei99@yahoo.ro', '1900-4-4', 3)

insert into Users
values(6, 'The One', 'idkwhattowrite', '2-12-25', 3)

/*bad insert
insert into Users
values(1, 'Bogdan', 'dcsc', '11111', 10, 10, 10)*/

/*Insert user jobs*/
insert into UserJobs
values(1, 'BitDefender', 'Software developer')

insert into UserJobs
values(2, 'Samsung', 'Engineer')

insert into UserJobs
values(3, 'AAA', 'Project manager')

insert into UserJobs
values(1, 'AAA', 'Project manager')

insert into UserJobs
values(4, 'Microsoft', 'Code-monkey')

/*Insert UserSkills*/
insert into UserSkills
values(1,'C/C++')
insert into UserSkills
values(1,'Python')
insert into UserSkills
values(1,'Java')
insert into UserSkills
values(2,'C/C++')
insert into UserSkills
values(3,'HTML')
insert into UserSkills
values(3,'Python')
insert into UserSkills
values(4,'Java')
/*insert into UserSkills
values(4,'C/C++')*/
insert into UserSkills
values(5,'Java')

/*Insert Badges*/
insert into Badges
values('Elite','basic desc')
insert into Badges
values('Newcomer','basic desc')
insert into Badges
values('Veteran','basic desc')
insert into Badges
values('Helpful', 'Most voted answer on at least 100 questions')

/*Insert UserBadges*/
insert into UserBadges
values(6, 'Newcomer')

insert into UserBadges
values(1, 'Veteran')

insert into UserBadges
values(1, 'Helpful')

insert into UserBadges
values(2, 'Veteran')

insert into UserBadges
values(3, 'Elite')

/*Update values*/
update Users
set Dob = '1999-7-15'
where Name = 'Miclea George'

update Users
set Name = 'The Prophet'
where Dob = '1-12-25'

update Badges
set BDesc = 'Welcome badge'
where Bid = 'Newcomer'

declare @noPpl int

select @noPpl = count(*) from Users U where U.Gid = 1  
update Groups
set NoPpl = @noPpl
where Gid = 1

select @noPpl = count(*) from Users U where U.Gid = 2
update Groups
set NoPpl = @noPpl
where Gid = 2

select @noPpl = count(*) from Users U where U.Gid = 3
update Groups
set NoPpl = @noPpl
where Gid = 3

update Badges
set BDesc = 'Desc for badge ' + Bid
where BDesc = 'basic desc'


/*Delete values*/
delete from UserSkills
where Uid = 5 or Sid = 'C/C++'

delete from Users
where year(Dob) < 100 or Email not like '%@%'

/*a. 2 queries with the union operation; use UNION [ALL] and OR;*/

-- select users which have a gmail address or which were born in january
select U1.Uid, U1.Name
from Users U1
where U1.Email like '%gmail%'
union all
select U2.Uid, U2.Name
from Users U2
where month(U2.Dob) = 1

-- select users which have C/C++ or Python skills
select distinct U1.Uid, U1.Name, U1.Email
from Users U1
where U1.Uid in (select S.Uid from UserSkills S where S.Sid = 'C/C++')
union all
select U2.Uid, U2.Name, U2.Email
from Users U2
where U2.Uid in (select S.Uid from UserSkills S where S.Sid = 'Python')

/*b. 2 queries with the intersection operation; use INTERSECT and IN;*/

--select users who know more than 2 programming languages and work for BitDefender
select U1.Uid, U1.Name, U1.Email
from Users U1 
where (select count(*) from UserSkills U where U.Uid = U1.Uid) >= 2
intersect
select U2.Uid, U2.Name, U2.Email
from Users U2
where U2.Uid in (select UJ.Uid from UserJobs UJ where UJ.Cid = 'BitDefender')

--select newcommers who don't know any programming languages
select U1.Uid, U1.Name, U1.Email
from Users U1 
where (select count(*) from UserSkills U where U.Uid = U1.Uid) = 0
intersect
select U2.Uid, U2.Name, U2.Email
from Users U2
where U2.Uid in (select UB.Uid from UserBadges UB where UB.Bid = 'Newcomer')


/*c. 2 queries with the difference operation; use EXCEPT and NOT IN;*/

-- select users who know 2 or more programming languages except those who know C/C++
select U1.Uid, U1.Name, U1.Email
from Users U1
where (select count(*) from UserSkills U where U.Uid = U1.Uid) >= 2
except
select U2.Uid, U2.Name, U2.Email
from Users U2
where U2.Uid in (select US.Uid from UserSkills US where US.Sid = 'C/C++')

-- select users who know C/C++ except those who have no job
select U1.Uid, U1.Name, U1.Email
from Users U1
where U1.Uid in (select US.Uid from UserSkills US where US.Sid = 'C/C++')
except
select U2.Uid, U2.Name, U2.Email
from Users U2
where U2.Uid not in (select UJ.Uid from UserJobs UJ)


/*d. 4 queries with INNER JOIN, LEFT JOIN, RIGHT JOIN, and FULL JOIN; one query will join at least 3 tables,
while another one will join at least two many-to-many relationships;*/

select * from Users U
inner join UserSkills US
on U.Uid = US.Uid

select * from Users U
left join UserJobs US
on U.Uid = US.Uid

select * from Users U
right join UserSkills US
on U.Uid = US.Uid

select * from Users U
full join UserSkills US
on U.Uid = US.Uid

select * from Companies C
inner join UserJobs UJ
on C.Cid = UJ.Cid
inner join Users U
on UJ.Uid = U.Uid
order by C.Cid


/*e. 2 queries using the IN operator to introduce a subquery in the WHERE clause; in at least one query,
the subquery should include a subquery in its own WHERE clause;*/

-- select all veteran users
select U.Uid, U.Name, U.Email
from Users U
where U.Uid in (select UB.Uid from UserBadges UB where UB.Bid = 'Veteran')

-- select all companies who have users that know C/C++
select * from Companies C
where C.Cid in (
	select UJ.Cid from UserJobs UJ 
	where UJ.Uid in (
		select U.Uid from Users U
		where U.Uid in (
			select US.Uid from UserSkills US where US.Sid = 'C/C++'
		)
	)
)

/*f. 2 queries using the EXISTS operator to introduce a subquery in the WHERE clause;*/
select * from Users
where exists(
	select * from UserBadges B where B.Bid = 'Javascript'
)

select * from Companies C
where exists(
	select * from Users U where year(U.Dob) = 1999
)

/*g. 2 queries with a subquery in the FROM clause;*/                  

--select users that have any skills
select distinct U.Uid, U.Name, U.Email from (
	select U1.Uid, U1.Name, U1.Email from Users U1
	inner join UserSkills US
	on U1.Uid = US.Uid
) U

select distinct C.Cid from (
	select C1.Cid from Companies C1
	where (select count(*) from UserJobs UJ where C1.Cid = UJ.Cid) >= 2
) C where exists(select * from UserJobs UJ where UJ.WPid = 'Code-monkey')

/*h. 4 queries with the GROUP BY clause, 3 of which also contain the HAVING clause;*/

select U.Name, U.Dob, count(*) as 'Job count' from (
	select U1.Uid, U1.Name, U1.Email, U1.Dob from Users U1
	inner join UserSkills US
	on U1.Uid = US.Uid
) U 
group by U.Dob, U.Name
having year(U.Dob) >= 1900

select C.Cid, count(*) as 'Employee count' from(
	select C1.Cid from Companies C1
	inner join UserJobs UJ
	on C1.Cid = UJ.Cid
) C
group by C.Cid

--view the dob of the oldest employee from each company having a gmail account
select C.Cid, U.Email, min(U.Dob) as 'Oldest date' from 
	Companies C
	inner join UserJobs UJ
	on C.Cid = UJ.Cid
	inner join Users U
	on UJ.Uid = U.Uid
group by C.Cid, U.Email
having U.Email like '%@gmail%'

--view the users and the count of his badges if the id of the gruop is an odd number
select U.Name, U.Gid, count(*) as 'Badge count' from (
	select U1.Uid, U1.Name, U1.Email, U1.Gid from Users U1
	inner join UserBadges UB
	on U1.Uid = UB.Uid
) U 
group by U.Gid, U.Name
having U.Gid%2 != 0 

/*i. 4 queries using ANY and ALL to introduce a subquery in the WHERE clause; 
2 of them should be rewritten with aggregation operators, 
while the other 2 should also be expressed with [NOT] IN.*/






































