# -*- coding: utf-8 -*-
"""
Created on Mon Dec 10 10:27:57 2018

@author: kosmo30
"""
import sys
import glob
import os



# input_file = sys.argv[1]
input_file = input("File Nmae: ")

filereader = open(input_file, 'r')

for row in filereader:
    print(row.strip())
filereader.close

print("="*30)

in_file = input("file name : ")

with open(input_file, 'r', newline='') as f:
        for row in f:
            print(row.strip())
        
print("="*30)

inputPath = input("input Path :")

for input_file in glob.glob(os.path.join(inputPath, '*.txt')):
    with open(input_file, 'r', newline='') as f:
        for row in f:
            print(row.strip())
        print("*"*30)