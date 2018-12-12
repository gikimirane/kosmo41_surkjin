# -*- coding: utf-8 -*-
"""
Created on Mon Dec 10 14:40:56 2018

@author: kosmo30
"""

#!/usr/bin/env python3
import pandas as pd
import sys

infile = "./Data/01_data.csv"
outfile = "./Output/pout04.csv"

data_frame = pd.read_csv(infile)

important_dates = ['1/20/14', '1/30/14']

data_frame_value_in_set = data_frame.loc[data_frame['Purchase Date']\
    .isin(important_dates), :]
data_frame_value_in_set.to_csv(outfile, index=False)