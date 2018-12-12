# -*- coding: utf-8 -*-
"""
Created on Mon Dec 10 15:05:49 2018

@author: kosmo30
"""

#!/usr/bin/env python3
import pandas as pd
import sys

infile = "./Data/01_data.csv"
outfile = "./Output/pout06.csv"

data_frame = pd.read_csv(infile)

data_frame_column_in_index = data_frame.loc[:, ['Invoice Number', 'Purchase Date']]
data_frame_column_in_index.to_csv(outfile, index=False)