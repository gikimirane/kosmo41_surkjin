# -*- coding: utf-8 -*-
"""
Created on Tue Dec 11 09:57:25 2018

@author: kosmo30
"""

#!/usr/bin/env python3
import csv
import sys

infile = "./Data/03_data_no_header_row.csv"
outfile = "./Output/out08.csv"

row_cnt = 0
with open(infile,'r', newline='') as csv_in_file:
    with open(outfile, 'w', newline='') as csv_out_file:
        f_r = csv.reader(csv_in_file)
        f_w = csv.writer(csv_out_file)
        h_list = ['Supplier Name','Invoice Number','Part Number','Cost','Purchase Date']
        f_w.writerow(h_list)
        for row in f_r:
            f_w.writerow(row)