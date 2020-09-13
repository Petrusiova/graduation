Graduation project

What is that project?

It is a back-end model of "voting for restaurant" system.  
This system contains 2 types of users: average users and admins - users with additional rights.
Each admin can add a restaurant and a restaurant's daily-menu and each user can vote for the preferable restaurant for visit.
Only one vote counted per user
If user votes again the same day:
If it is before 11:00 we asume that he changed his mind.
If it is after 11:00 then it is too late, vote can't be changed

What tools were used?

- Java 11
- Spring framework
- Maven
- JPA(Hibernate)
- REST
- Stream API
- HSQLDB

How could you start using it?

Please clone that project from github (https://github.com/Petrusiova/graduation) and use it!
After cloning this project you need to check dependencies and Maven lifecylce.

How could you test it?

Please run initDB_hsql.sql and populateDB.sql
and after it run maven - test and ensure that tests built with success.
If you have some problems - please write me an email: petrusiovaolga@gmail.com

Gratitude to JavaOps team (https://javaops.ru) for internship in java developing and helping me during my way to java developer.

