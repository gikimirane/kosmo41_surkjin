# -*- coding: utf-8 -*-
"""
Created on Tue Dec 11 10:48:40 2018

@author: kosmo30
"""

#!/usr/bin/env python3
import csv
import sys
import glob
import os

input_path = "./Data"
output_file = "./Output/out10.csv"

first_file = True
for input_file in glob.glob(os.path.join(input_path, '*sales*')):
    print(os.path.basename(input_file))
    
    with open(input_file,'r', newline='') as csv_in_file:
        with open(output_file, 'a', newline='') as csv_out_file:
            f_r = csv.reader(csv_in_file)
            f_w = csv.writer(csv_out_file)
           
            if first_file:
                for row in f_r:
                    f_w.writerow(row)
                first_file = False
            else:
                head = next(f_r)
                for row in f_r:
                    f_w.writerow(row)