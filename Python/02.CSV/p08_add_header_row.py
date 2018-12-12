# -*- coding: utf-8 -*-
"""
Created on Tue Dec 11 10:02:18 2018

@author: kosmo30
"""

#!/usr/bin/env python3
import pandas as pd
import sys

#in_file = sys.argv[1]
#out_file = sys.argv[2]

infile = "./Data/03_data_no_header_row.csv"
outfile = "./Output/pout08.csv"

h_list = ['Supplier Name','Invoice Number','Part Number','Cost','Purchase Date']

data_frame = pd.read_csv(infile, header=None, names=h_list)
data_frame.to_csv(outfile, index=False)