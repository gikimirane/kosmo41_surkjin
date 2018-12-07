# -*- coding: utf-8 -*-
"""
Created on Fri Dec  7 14:32:12 2018

@author: kosmo30
"""

# 리스트
list1 = [1, 2, 3, 3, 4, 5]

print(list1)
print(len(list1))
print(max(list1))
print(min(list1))
print(list1.count(3))

print("="*30)
# 인덱스 사용하기
print(list1[1])
print(list1[-1])

print("="*30)
# 리스트 분할하기
print(list1[0:2])

print("="*30)
# 리스트 복사하기
list2 = list1        # 참조
list3 = list1[:]     # 복사
list1[0] = 9
print(list2)
print(list3)

print("="*30)
# 리스트 병합하기
list4 = ["홍길동","전우치", "손오공"]
list5 = list1 + list4
print(list5)

print("="*30)
# in 과 not in
a = 2 in list1
print(a)

if 2 in list1:
    print("2 is in List", list1)
    
b = 6 not in list1
print(b)

if 6 not in list1:
    print("6 is not in List", list1)

print("="*30)

list1.append(6)
list1.append(7)
print(list1)

list1.pop()
print(list1)

list1.remove(3)
print(list1)

print("="*30)

list1.insert(0, 1)
print(list1[1:2])
list1[1:2] = ['a', 'b', 'c']
print(list1)
list1[1:4] = []
print(list1)
del list1[5]
print(list1)

print("="*30)
list6 = list1[:]
list6.reverse()
print("reverse: ", list6)

list7 = list1[:]
print("list7: ", list7)
list7.sort()
print("sort: ", list7)

#sorted 함수
my_list = [[1,2,3,4],[4,3,2,1],[2,4,1,3]]
list8 = sorted(my_list, key=lambda index_value: index_value[0])
print(list8)
list8 = sorted(my_list, key=lambda index_value: index_value[1])
print(list8)

list8 = sorted(my_list, key=lambda index_value: index_value[2])
print(list8)

list8 = sorted(my_list, key=lambda index_value: index_value[3])
print(list8)











