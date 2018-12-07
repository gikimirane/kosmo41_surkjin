# -*- coding: utf-8 -*-
"""
Created on Thu Dec  6 13:02:34 2018

@author: kosmo30
"""

# 속으로 20초 맞추기
import time

input("엔터를 누르고 20초를 셉니다.")
start = time.time()
input("20초후에 엔터를 다시 누릅니다.")
end = time.time()

et = end-start

print("\n실제 시간 :", et, "초")
print("차이 :", abs(et-20), "초")
print(time.localtime(time.time()))