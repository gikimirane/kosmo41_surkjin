# -*- coding: utf-8 -*-
"""
Created on Mon Dec 10 12:37:07 2018

@author: kosmo30
"""
#!/usr/bin/env python3
import csv
import sys

infile = "./Data/01_data.csv"
outfile = "./Output/out02.csv"

with open(infile,'r', newline='') as csv_in_file:
    with open(outfile, 'w', newline='') as csv_out_file:
        f_r = csv.reader(csv_in_file, delimiter=',')
        f_w = csv.writer(csv_out_file, delimiter=',')
        for row in f_r:
            f_w.writerow(row)