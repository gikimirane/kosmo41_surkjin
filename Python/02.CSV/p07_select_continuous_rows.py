# -*- coding: utf-8 -*-
"""
Created on Tue Dec 11 09:49:43 2018

@author: kosmo30
"""

#!/usr/bin/env python3
import pandas as pd
import sys

infile = "./Data/02_data_unnecessary_header_footer.csv"
outfile = "./Output/pout07.csv"

data_frame = pd.read_csv(infile, header=None)

data_frame = data_frame.drop([0,1,2,16,17,18])
data_frame.columns = data_frame.iloc[0]
data_frame = data_frame.reindex(data_frame.index.drop(3))
data_frame.to_csv(outfile, index=False)