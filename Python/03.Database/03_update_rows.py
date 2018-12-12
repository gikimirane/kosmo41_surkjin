# -*- coding: utf-8 -*-
"""
Created on Tue Dec 11 12:51:06 2018

@author: kosmo30
"""

#!/usr/bin/env python3
import sqlite3
import csv
import sys

input_file = "./Data/data_for_updating.csv"

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
for tuple in data:
    print(tuple)
statement = "insert into sales values(?, ?, ?, ?)"
con.executemany(statement, data)
con.commit()
print("="*30)
f_r = csv.reader(open(input_file, 'r'), delimiter=',')
head = next(f_r, None)
for row in f_r:
    data = []
    for c_index in range(len(head)):
        data.append(row[c_index])
    print(data)
    con.execute("update sales set amount=?, date=? where customer=?;", data)
con.commit()
print("="*30)
cursor = con.execute("select * from sales")
rows = cursor.fetchall()
for row in rows:
    output = []
    for c_index in range(len(row)):
        output.append(row[c_index])
    print(output)