# -*- coding: utf-8 -*-
"""
Created on Mon Dec 10 15:03:19 2018

@author: kosmo30
"""

#!/usr/bin/env python3
import csv
import sys

infile = "./Data/01_data.csv"
outfile = "./Output/out06.csv"

my_columns = ["Invoice Number", "Purchase Date"]
my_columns_index = []

with open(infile,'r', newline='') as csv_in_file:
    with open(outfile, 'w', newline='') as csv_out_file:
        f_r = csv.reader(csv_in_file)
        f_w = csv.writer(csv_out_file)
        
        header = next(f_r)
        for ind_v in range(len(header)):
            if header[ind_v] in my_columns:
                my_columns_index.append(ind_v)
        print(my_columns)
        f_w.writerow(my_columns)
        
        for row in f_r:
            row_out = []
            for idx_v in my_columns_index:
                row_out.append(row[idx_v])
            f_w.writerow(row_out)