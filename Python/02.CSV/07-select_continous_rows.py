# -*- coding: utf-8 -*-
"""
Created on Tue Dec 11 09:33:51 2018

@author: kosmo30
"""

#!/usr/bin/env python3
import csv
import sys

infile = "./Data/02_data_unnecessary_header_footer.csv"
outfile = "./Output/out07.csv"

row_cnt = 0
with open(infile,'r', newline='') as csv_in_file:
    with open(outfile, 'w', newline='') as csv_out_file:
        f_r = csv.reader(csv_in_file)
        f_w = csv.writer(csv_out_file)
        for row in f_r:
            if row_cnt >=3 and row_cnt <=15:
                f_w.writerow([value.strip() for value in row])
            row_cnt += 1