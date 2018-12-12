# -*- coding: utf-8 -*-
"""
Created on Tue Dec 11 12:22:09 2018

@author: kosmo30
"""

#!/usr/bin/env python3
import sqlite3
import csv
import sys

input_file = "./Data/supplier_data.csv"

con = sqlite3.connect('./Data/Supplier.db')
#c = con.cursor()
create_table = """create table if not exists suppliers
                (supplier_name varchar(20),
                invoice_number varchar(20),
                part_number varchar(20),
                cost float,
                purchase_date date);"""
con.execute(create_table)
con.commit()

f_r = csv.reader(open(input_file, 'r'), delimiter=',')
head = next(f_r, None)

for row in f_r:
    data = []
    print(row)
    for c_index in range(len(head)):
        data.append(row[c_index])
    print(data)
    con.execute("insert into suppliers values(?,?,?,?,?)", row)
con.commit()
print("="*50)
output = con.execute("select * from suppliers")
rows = output.fetchall()

for row in rows:
    output = []
    for c_index in range(len(row)):
        output.append(str(row[c_index]))
    print(output)




    
    
    
    
    
    

