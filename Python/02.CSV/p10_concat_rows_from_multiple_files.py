# -*- coding: utf-8 -*-
"""
Created on Tue Dec 11 10:55:47 2018

@author: kosmo30
"""

#!/usr/bin/env python3
import pandas as pd
import sys
import glob
import os

input_path = "./Data"
output_file = "./Output/pout10.csv"

all_files = glob.glob(os.path.join(input_path, '*sales*'))

all_data_frames = []
for files in all_files:
    data_frame = pd.read_csv(files, index_col=None)
    all_data_frames.append(data_frame)
data_frame_concat = pd.concat(all_data_frames, axis=0, ignore_index=True)
data_frame_concat.to_csv(output_file, index=False)

