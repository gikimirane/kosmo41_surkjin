# -*- coding: utf-8 -*-
"""
Created on Tue Dec 11 11:48:57 2018

@author: kosmo30
"""

#!/usr/bin/env python3
import pandas as pd
import sys
import glob
import os

input_path = "./Data"
output_file = "./Output/pout11.csv"

all_files =  glob.glob(os.path.join(input_path, '*sales*'))
all_data_frames = []

for input_file in all_files:
    data_frame = pd.read_csv(input_file, index_col=None)
    
    tot_sales = pd.DataFrame([float(str(value).strip('$').replace(',',''))\
                              for value in data_frame.loc[:, 'Sale Amount']]).sum()
    avg_sales = pd.DataFrame([float(str(value).strip('$').replace(',',''))\
                              for value in data_frame.loc[:, 'Sale Amount']]).mean()
    data = {'file_name': os.path.basename(input_file),
            'total_sales': tot_sales,
            'average_sales': avg_sales}
    all_data_frames.append(pd.DataFrame(data, columns=['file_name', 'totla_sales','average_sales']))
data_frame_concat = pd.concat(all_data_frames, axis=0, ignore_index=True)
data_frame_concat.to_csv(output_file, index=False)