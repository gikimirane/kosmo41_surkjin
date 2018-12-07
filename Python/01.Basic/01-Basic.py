# -*- coding: utf-8 -*-
"""
Spyder Editor

This is a temporary script file.
"""
# 기초 -----------------------------
2+4

print(2+4)      # 숫자출력
print("2+4")    # 문자출력

# 연산자 ---------------------------
print(7/4)      #나누기
print(7//4)     #몫
print(7%4)      #나머지

# 변수 -----------------------------
a = 3           #변수선언 및 초기화
a
b = 4
a + b           #계산만하고 출력X
print("a + b =", a+b)
c = a + b
print("c =", c)

# for -------------------------------
for i in range(10):
    print("Number :", i)

# range -----------------------------
list(range(5))       #0,1,2,3,4
list(range(0, 5))    #0,1,2,3,4
list(range(1, 4))    #1,2,3

for i in range(1,4):
    print("Number :", i)




