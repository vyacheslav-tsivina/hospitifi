CREATE TABLE `EMPLOYEES` ( `ID` INTEGER PRIMARY KEY AUTOINCREMENT, `NAME` TEXT, `POSITION` TEXT );
CREATE TABLE `EMPLOYEES_WORK_HOURS` ( `EMPLOYEE_ID` INTEGER, `DAY` TEXT, `START_TIME` TEXT, `END_TIME` TEXT );
CREATE TABLE `OCCUPATION` ( `ID` INTEGER, `ROOM_ID` INTEGER, `ADULTS` INTEGER, `CHILDREN` INTEGER, `GUEST_NAME` TEXT, `DATE_START` TEXT, `DATE_END` TEXT, `BREAKFAST_INCLUDED` INTEGER, `RATE` INTEGER );
CREATE TABLE `RATES` ( `RATE_CATEGORY` INTEGER, `BEDS` INTEGER, `BED_TYPE` TEXT, `DATE_START` TEXT, `DATE_END` TEXT, PRIMARY KEY(`RATE_CATEGORY`,`DATE_START`) );
CREATE TABLE "RESERVATION" ( `ID` INTEGER PRIMARY KEY AUTOINCREMENT, `ROOM_ID` INTEGER, `CHECK_IN` TEXT, `CHECK_OUT` TEXT, `GUEST_NAME` TEXT, `CANCELED` INTEGER );
CREATE TABLE `ROOM` ( `ID` INTEGER PRIMARY KEY AUTOINCREMENT, `NUMBER` TEXT UNIQUE, `BEDS` INTEGER, `BEDS_TYPE` TEXT, `SAFE` INTEGER, `BATH` INTEGER, `RATE_CATEGORY` INTEGER );
CREATE TABLE `USERS` ( `ID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `LOGIN` TEXT NOT NULL UNIQUE, `PASSWORD` TEXT NOT NULL, `EMAIL` TEXT, `ROLE` TEXT NOT NULL );