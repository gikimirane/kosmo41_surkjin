# -*- coding: utf-8 -*-
"""
Created on Mon Dec 10 11:07:31 2018

@author: kosmo30
"""

my_l = [0,1,2,3,4,5,6,7,8,9]

max_index = len(my_l)

out_file = input("File Name : ")
f = open(out_file, 'a')

for i in range(len(my_l)):
    if i < max_index-1:
        f.write(str(my_l[i])+',')
    else:
        f.write(str(my_l[i])+'\n')
f.close()
print("Output appended to file")

my_l = ['0','1','2','3','4','5','6','7','8','9']

max_index = len(my_l)
my_str = ",".join(my_l)
print(my_str, type(my_str))

out_file = input("File Name : ")
f = open(out_file, 'a')

f.writelines(my_str)
#f.writelines(my_str+'\n')
f.close()
print("Output appended to file")