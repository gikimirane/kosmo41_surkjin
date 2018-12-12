# -*- coding: utf-8 -*-
"""
Created on Mon Dec 10 12:51:34 2018

@author: kosmo30
"""

#!/usr/bin/env python3
import pandas as pd
import sys

infile = "./Data/01_data.csv"
outfile = "./Output/pout02.csv"

data_frame = pd.read_csv(infile)
print(data_frame)
data_frame.to_csv(outfile, index=False)