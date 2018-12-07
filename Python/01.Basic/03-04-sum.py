# -*- coding: utf-8 -*-
"""
Created on Fri Dec  7 09:42:22 2018

@author: kosmo30
"""

def getSum(n):
    sum = 0
    for i in range(n+1):
        sum += i
    return sum

x = input("숫자를 입력하세요 : ")

s = getSum(int(x))
print(x, "까지 sum: ", s)