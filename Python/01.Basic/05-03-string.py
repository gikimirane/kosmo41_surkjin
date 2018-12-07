# -*- coding: utf-8 -*-
"""
Created on Fri Dec  7 12:30:30 2018

@author: kosmo30
"""

# 문자개수
str = "apple"
print(len(str))
print(str.count('p'))

#위치
print("="*30)
str = "I am a boy. You are a girl."
print(str.find("boy"))
print(str.find("man"))
print(str.index("girl"))
#print(str.index("women"))

#자르기
print("="*30)
str = "Life is too short. You need Python."
print(str[0:4])
print(str[5:7])
print(str[:18])
print(str[19:])

#바꾸기
print("="*30)
print(str.replace("Life", "인생은"))

#나누기
print("="*30)
print(str.split())

str = "a:b:c:d"
print(str.split(":"))
print(str.split(":", 2))

#join
print("="*30)
str_list = ["1","2","3","4","5"]
str1 = ",".join(str_list)
print(str1, type(str1))

#strp
print("="*30)
str = " Remove  unwanted charcters    from this string.\t\t   \n"
print(str)
print(str.lstrip())
print(str.rstrip())
print(str.strip())
print("+-!We are students.-+!/".strip("+-!/"))
