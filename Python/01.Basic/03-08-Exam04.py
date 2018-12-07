# -*- coding: utf-8 -*-
"""
Created on Fri Dec  7 10:26:49 2018

@author: kosmo30
"""
import time
import random

x = ["apple", "snake", "orion", "wolf", "fox"]

print("[타자게임] 준비되면 엔터를 입력하세요")
input()
start = time.time()
suss = 0
fail = 0
while suss < 5:
    print("\n문제", suss+1)
    s = random.choice(x)
    print(s)
    
    ins = input()
   
    if s == ins:
        print("통과")
        suss += 1
    else:
        print("오류")
        fail += 1

end = time.time()
et = end-start
print("\n타자시간 :", format(et, ".2f"), "초")
print("통과 :", suss, "실패 :", fail)