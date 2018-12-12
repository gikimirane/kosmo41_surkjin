# -*- coding: utf-8 -*-
"""
Created on Mon Dec 10 09:55:01 2018

@author: kosmo30
"""

# try ~ except

def getMean(nums):
    return sum(nums) / len(nums)

my_list = []

try:
    result =  print(getMean(my_list))
except ZeroDivisionError as e:
    print(e)
    
print("="*30)

try:
    result =  print(getMean(my_list))
except ZeroDivisionError as e:
    print(e)
else:
    print(result)
finally:
    print("The finally block is executed every time")
    
print("="*30)

try:
    print(getMean(my_list))
except Exception as e:
    print(e)