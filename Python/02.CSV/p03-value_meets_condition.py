# -*- coding: utf-8 -*-
"""
Created on Mon Dec 10 13:11:01 2018

@author: kosmo30
"""

#!/usr/bin/env python3
import pandas as pd
import sys

infile = "./Data/01_data.csv"
outfile = "./Output/pout03.csv"

data_frame = pd.read_csv(infile)

data_frame['Cost'] = data_frame['Cost'].str.strip('$').astype(float)
data_frame_value_meets_condition = data_frame.loc[(data_frame['Supplier Name']\
    .str.contains('Z')) | (data_frame['Cost'] > 600.0), :]
data_frame_value_meets_condition.to_csv(outfile, index=False)