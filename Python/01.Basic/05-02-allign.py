# -*- coding: utf-8 -*-
"""
Created on Fri Dec  7 12:06:46 2018

@author: kosmo30
"""

# format 문자열
print("{0}+{1}".format("Hello","홍길동"))
print("="*30)

# 좌측정렬
x1 = "{0:<10}".format("hi")
print("[",x1,"]")

# 우측정렬
x2 = "{0:>10}".format("hi")
print("[",x2,"]")

# 중앙정렬
x3 = "{0:^10}".format("hi")
print("[",x3,"]")

# 채우기
x4 = "{0:*>10}".format("hi")
print("[",x4,"]")

# 소숫점 표현
y = 3.14159
y1 = "{0:0.2f}".format(y)
print("[",y1,"]")
y2 = "{0:10.2f}".format(y)
print("[",y2,"]")
y3 = "{0:*>10.2f}".format(y)
print("[",y3,"]")




