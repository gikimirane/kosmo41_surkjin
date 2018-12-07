# -*- coding: utf-8 -*-
"""
Created on Fri Dec  7 11:39:09 2018

@author: kosmo30
"""

# 평균, 분산, 표준편차
import math

d = [1, 2, 3, 4, 5]
print(d)

mean = sum(d) / len(d)

vsum = 0
for x in d:
    vsum += (x-mean)**2
var = vsum / len(d)
print(var)

std = math.sqrt(var)
print(std)