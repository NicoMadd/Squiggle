# Squiggle

Squiggle is a simple query maker for Java. Simple enough for someone to make a simple query without the need to write a query in raw String .

I took this source code from https://code.google.com/archive/p/squiggle-sql/ whose author is <a href="joe@truemesh.com">Joe Walnes</a> so credits to him.

## Objective

Im planning on giving maintenance to it, and developing it even further since I believe its a useful project not to be left aside. The purpose of this is to create a tool optimized to create queries, thats it, thats the tweet (?. Haha okay..., jokes aside, its not an ORM to handle objects within databases, though im looking forward to multi-DB compatible releases. I'll try to update it most of the time, any suggestion or improvement you see worth it, be my guest to suggest in the issue section or even more, fork it and PR later.

## What we have

Actually we are on v1.6.9.1 Squiggle can make

-   SELECT
-   INSERT
-   UPDATE
-   DELETE
-   CREATE TABLE
-   CREATE DATABASE
-   JOIN
    -   INNER
    -   LEFT
    -   RIGHT
    -   OUTER
    -   FULL
    -   ON CONDITION BUILDER
-   WHERE
-   GROUP BY
-   ORDER BY
-   CONSTRAINTS
    -   PRIMARY KEY
    -   FOREIGN KEY
    -   AUTO INCREMENT
    -   NULLABLE
    -   NOT NULLABLE
    -   UNIQUE
    -   DEFAULT VALUE
-   FUNCTIONS
    -   SUM
    -   AVERAGE
    -   COUNT

## Looking Forward

In the future, I hope to add other SQL Engines in order to be Oracle, PostgreSQL and MySQL compatible.
Also handle views and more table definitions as well as triggers.

## Parsers

1. SQL SERVER - [X]
2. MySQL - [X]
3. Oracle - [X]
4. PostgreSQL - [ ]

## TODOs

1. SELECT - [X]
2. INSERT - [X]
3. UPDATE - [X]
4. DELETE - [X]
5. GROUP BY - [X]
6. CREATE TABLE - [X]
7. CREATE DATABASE - [X]
8. CREATE TRANSACTION - [X]
9. CREATE COMMIT - [X]
10. CREATE VIEW - [ ]
11. TRIGGERS - [ ]
