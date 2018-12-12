# -*- coding: utf-8 -*-
"""
Created on Mon Dec 10 09:34:12 2018

@author: kosmo30
"""

empty_dic = {}

dic1 = {'a':3, 'c':2, 'b':1}
print(dic1)
print(len(dic1))

dic2 ={'x':'printer', 'y':5, 'z':['star','circle',9]}
print(dic2)
print(len(dic2))

print("="*30)

print(dic1['c'])

dic3 = dic1.copy();
print(dic3)

print(dic1.keys())
print(dic1.values())
print(dic1.items())

if 'y' in dic2:
    print("'y' is a key in dic2.", dic2)

if 'c' not in dic2:
    print("'c' is noy a key in dic2.", dic2)
    
print(dic1.get('c'))
print(dic1.get('d'))
print(dic1.get('d', 'Not in dic1'))

print("="*30)

dic4 = sorted(dic3.items(), key=lambda item0: item0[0])
print(dic4)

dic5 = sorted(dic3.items(), key=lambda item: item[1])
print(dic5)