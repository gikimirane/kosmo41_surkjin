# -*- coding: utf-8 -*-
"""
Created on Fri Dec  7 09:58:59 2018

@author: kosmo30
"""

import random

def make_q():
    a = random.randint(1, 40)
    b = random.randint(1, 20)
    op = random.randint(1, 3)
    
    q = str(a)
    
    if op == 1:
        q += " + "
    elif op == 2:
        q += " - "
    else:
        q += " * "
    
    q += str(b)
    
    return q

sc1 = 0
sc2 = 0

for x in range(5):
    q = make_q()
    print("\n", q)
    ans = input(" = ")
    r = int(ans)
    
    if eval(q) == r:
        print("정답입니다")
        sc1 += 1
    else:
        print("오답입니다")
        sc2 += 1
        
if sc2 == 0:
    print("모든 문제를 맞췄습니다")
else:
    print("정답 : ", sc1, "오답 : ", sc2)