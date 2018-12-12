# -*- coding: utf-8 -*-
"""
Created on Mon Dec 10 14:47:28 2018

@author: kosmo30
"""

#!/usr/bin/env python3
import pandas as pd
import sys

infile = "./Data/01_data.csv"
outfile = "./Output/pout05.csv"

data_frame = pd.read_csv(infile)

data_frame_column_in_index = data_frame.iloc[:, [0,3]]
data_frame_column_in_index.to_csv(outfile, index=False)