# -*- coding: utf-8 -*-
"""
Created on Fri Dec  7 11:50:22 2018

@author: kosmo30
"""

# for 심층분석

# +2씩 증가
for x in [0,3,5,7,9]:
    print(x)
    
print("="*30)

# 줄어들게
for x in [4,3,2,1,0]:
    print(x)

print("="*30)

# 한 줄로 이어서 출력
for x in range(5):
    print(x, end=" ")
print("\n")

print("="*100)
# 구구단 출력
for x in range(2, 10):
    for y in range(1,10):
        print(x,"X",y,"=",x*y, " ", end=" ")
    print()