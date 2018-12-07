# -*- coding: utf-8 -*-
"""
Created on Fri Dec  7 09:52:42 2018

@author: kosmo30
"""

def factorial(n):
    f = 1;
    for i in range(1, n+1):
        f = f * i
    return f

print(factorial(5))