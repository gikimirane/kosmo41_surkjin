# -*- coding: utf-8 -*-
"""
Created on Tue Dec 11 11:30:31 2018

@author: kosmo30
"""

#!/usr/bin/env python3
import csv
import sys
import glob
import os
import string

input_path = "./Data"
output_file = "./Output/out11.csv"

output_h_list = ['file_name','total_sales','average_sales']

cvs_out_file = open(output_file, 'a', newline='')
f_w = csv.writer(cvs_out_file)
f_w.writerow(output_h_list)

for input_file in glob.glob(os.path.join(input_path, '*sales*')):
   with open(input_file,'r', newline='') as csv_in_file:
        f_r = csv.reader(csv_in_file)
        out_list = []
        out_list.append(os.path.basename(input_file))
        head = next(f_r)
        total_sales = 0.0
        number_of_sales = 0.0
        for row in f_r:
            sales_amount = row[3]
            total_sales += float(str(sales_amount).strip('$').replace(',',''))
            number_of_sales += 1.0
        average_sales = '{0:.2f}'.format(total_sales / number_of_sales)
        out_list.append(total_sales)
        out_list.append(average_sales)
        f_w.writerow(out_list)
        
cvs_out_file.close()