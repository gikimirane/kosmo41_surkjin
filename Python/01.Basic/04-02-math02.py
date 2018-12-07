# -*- coding: utf-8 -*-
"""
Created on Fri Dec  7 11:30:19 2018

@author: kosmo30
"""

# 소인수분해
x = int(input("? "))

d = 2

while d <= x:
    if x%d == 0:
        print(d)
        x /= d
    else:
        d += 1
