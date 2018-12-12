# -*- coding: utf-8 -*-
"""
Created on Mon Dec 10 11:01:22 2018

@author: kosmo30
"""

import sys

my_l = ['동', '해', '물', '과', '백', '두', '산', '이',
        '마', '르', '고', '닳', '도', '록']

max_index = len(my_l)

out_file = input("File Name : ")
f = open(out_file, 'w')

for i in range(len(my_l)):
    if i < max_index-1:
        f.write(my_l[i]+'\t')
    else:
        f.write(my_l[i]+'\n')
f.close()
print("Output written to file")