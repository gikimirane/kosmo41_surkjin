# -*- coding: utf-8 -*-
"""
Created on Tue Dec 11 12:07:59 2018

@author: kosmo30
"""

#!/usr/bin/env python3
import sqlite3

con = sqlite3.connect(':memory:') 

query = """CREATE TABLE sales
			(customer VARCHAR(20), 
			 product VARCHAR(40),
			 amount FLOAT,
			 date DATE);"""

con.execute(query)
con.commit

data = [('Richard Lucas', 'Notepad', 2.50, '2014-01-02'),
		('Jenny Kim', 'Binder', 4.15, '2014-01-15'),
		('Svetlana Crow', 'Printer', 155.75, '2014-02-03'),
		('Stephen Randolph', 'Computer', 679.40, '2014-02-20')]
statement = "insert into sales values(?, ?, ?, ?)"
con.executemany(statement, data)
con.commit()

cursor = con.execute("select * from sales")
rows = cursor.fetchall()

row_cnt = 0
for row in rows:
    print(row)
    row_cnt += 1
print("Number of row: %d " %(row_cnt))

cursor2 = con.execute("select customer from sales")
rows2 = cursor2.fetchall()

for row2 in rows2:
    print(row2)
