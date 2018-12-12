# -*- coding: utf-8 -*-
"""
Created on Tue Dec 11 10:30:27 2018

@author: kosmo30
"""

#!/usr/bin/env python3
import csv
import sys
import glob
import os

#in_path = sys.argv[1]
in_path = "./Data"

file_counter = 0
for input_file in glob.glob(os.path.join(in_path, '*sales*')):
    row_cnt = 0
    with open(input_file,'r', newline='') as csv_in_file:
        f_r = csv.reader(csv_in_file)
        head = next(f_r)
        for row in f_r:
            row_cnt += 1
    print('{0!s}: \t{1:d} rows \t{2:d} columns'.format(\
          os.path.basename(input_file), row_cnt, len(head)))
    print('%s \t %d rows \t %d columns'  %(os.path.basename(input_file), row_cnt, len(head)))
    file_counter += 1

print('number of files: {0:d}'.format(file_counter))
    