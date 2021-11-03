# Squiggle

Squiggle is a simple query maker for Java. Simple enough for someone to make a simple query without the need to write a query in raw String .

I took this source code from https://code.google.com/archive/p/squiggle-sql/ whose author is <a href="joe@truemesh.com">Joe Walnes</a> so credits to him.


## Objective
Im planning on giving maintenance to it, and developing it even further since I believe its a useful project not to be left aside. The purpose of this is to create a tool optimized to create queries, thats it, thats the tweet (?. Haha okay..., jokes aside, its not an ORM to handle objects within databases, though im looking forward to a multi-DB release. I'll try to update it most of the time, any suggestion or improvement you see worth it, be my guest to suggest in the issue section or even more, fork it and PR later.


## What we have
It's purpose actually was just to make SELECT queries, you can also make JOINs with other tables, condition columns with WHEREs and order them with GROUP BYs. 
The version I took is the 1.4.0. So... next release will be 1.5.0 to make differences with the original repo.

## Looking Forward
In the future, I hope it can be used to make INSERTs, UPDATEs, DELETEs and also be "intelligent" enough to optimize the query made.

## TODOs
1. SELECT
2. INSERT
3. UPDATE
4. DELETE
5. GROUP BY
6. CREATE TABLE
7. CREATE VIEW
8. CREATE COMMIT
9. CREATE TRANSACTION
10. TRIGGERS
