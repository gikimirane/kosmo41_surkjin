# -*- coding: utf-8 -*-
"""
Created on Mon Dec 10 12:09:41 2018

@author: kosmo30
"""

#!/usr/bin/env python3

infile = "./Data/01_data.csv"
outfile = "./Output/out01.csv"

with open(infile,'r') as f_r:
    with open(outfile, 'w') as f_w:
        header = f_r.readline()
        header = header.strip()
        h_list = header.split(',')
        print(h_list)
        f_w.write(','.join(map(str, h_list))+'\n')
        for row in f_r:
            row = row.strip()
            r_list = row.split(',')
            print(r_list)
            f_w.write(','.join(map(str, r_list))+'\n')