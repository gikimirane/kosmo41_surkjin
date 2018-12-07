# -*- coding: utf-8 -*-
"""
Created on Fri Dec  7 11:06:32 2018

@author: kosmo30
"""

# 집합연산
A = {1,2,3,4}
B = {3,4,5,6}

print(A)
print(B)

print(4 in A)
print(6 in A)
print(len(A))

print(A | B)
print(A & B)
print(A - B)

print("=" * 30)

C = {x for x in range(1, 11)}
D = {x for x in range(1, 11) if x%3 == 0}

print(C)
print(D)

print(C < D)
print(C > D)
print(C == D)
