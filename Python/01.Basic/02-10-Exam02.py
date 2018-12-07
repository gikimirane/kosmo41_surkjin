# -*- coding: utf-8 -*-
"""
Created on Thu Dec  6 14:52:53 2018

@author: kosmo30
"""

# 숫자 추출하여 맞히는 게임

import random

x = random.randint(1, 30)
ins = 0

while x != int(ins):
    ins = input("숫자 입력(1~30) :")
    if x == int(ins):
        print(x, "정답입니다")
    elif x > int(ins):
        print("정답보다 작습니다")
    else:
        print("정답보다 큽니다")