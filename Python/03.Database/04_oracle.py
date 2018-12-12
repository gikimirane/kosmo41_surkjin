# -*- coding: utf-8 -*-
"""
Created on Tue Dec 11 13:04:30 2018

@author: kosmo30
"""
#!/usr/bin/env python3

import cx_Oracle as cx

host_name = 'localhost'
oracle_port = 1521
service_name = 'xe'
dns_tns = cx.makedsn(host_name, oracle_port, service_name)
conn = cx.connect('scott', 'tiger', dns_tns)

cursor = conn.cursor()
sql = 'select * from employee_old where job = :job'

for result in cursor.execute(sql, job='MANAGER'):
    print(result)
    
cursor.close()
conn.close()