# -*- coding: utf-8 -*-
"""
Created on Mon Dec 10 12:58:55 2018

@author: kosmo30
"""

#!/usr/bin/env python3
import csv
import sys

infile = "./Data/01_data.csv"
outfile = "./Output/out03.csv"

with open(infile,'r', newline='') as csv_in_file:
    with open(outfile, 'w', newline='') as csv_out_file:
        f_r = csv.reader(csv_in_file)
        f_w = csv.writer(csv_out_file)
        header = next(f_r)
        f_w.writerow(header)
        for row in f_r:
            supplier = str(row[0]).strip()
            cost = str(row[3]).strip('$').replace(',','')
            if supplier == 'Supplier Z' or float(cost) > 600.0:
                f_w.writerow(row)