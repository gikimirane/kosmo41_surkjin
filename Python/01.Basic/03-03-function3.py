# -*- coding: utf-8 -*-
"""
Created on Thu Dec  6 15:15:05 2018

@author: kosmo30
"""

import math
# 결과값이 있는 함수

def getSquare(num):
    result = num * num
    return result

def getTriangleArea(a, h):
    return a*h/2

def getSqrt(num):
    return math.sqrt(num)

x1 = 4
x2 = getSquare(x1)

print(x1, x2)

print(getTriangleArea(3,4))

print(getSqrt(4))
print(getSqrt(9))