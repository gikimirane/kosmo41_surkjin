# -*- coding: utf-8 -*-
"""
Created on Mon Dec 10 14:29:49 2018

@author: kosmo30
"""

#!/usr/bin/env python3
import csv
import sys

infile = "./Data/01_data.csv"
outfile = "./Output/out04.csv"

important_dates = ['1/20/14', '1/30/14']

with open(infile,'r', newline='') as csv_in_file:
    with open(outfile, 'w', newline='') as csv_out_file:
        f_r = csv.reader(csv_in_file)
        f_w = csv.writer(csv_out_file)
        header = next(f_r)
        f_w.writerow(header)
        for row in f_r:
            a_date = row[4]
            if a_date in important_dates:
                f_w.writerow(row)
                
print(type(important_dates[0]))