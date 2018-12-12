# -*- coding: utf-8 -*-
"""
Created on Mon Dec 10 14:46:45 2018

@author: kosmo30
"""

#!/usr/bin/env python3
import csv
import sys

infile = "./Data/01_data.csv"
outfile = "./Output/out05.csv"

my_columns = [0, 3]

with open(infile,'r', newline='') as csv_in_file:
    with open(outfile, 'w', newline='') as csv_out_file:
        f_r = csv.reader(csv_in_file)
        f_w = csv.writer(csv_out_file)
        for row in f_r:
            row_out = []
            for idx_v in my_columns:
                row_out.append(row[idx_v])
            f_w.writerow(row_out)