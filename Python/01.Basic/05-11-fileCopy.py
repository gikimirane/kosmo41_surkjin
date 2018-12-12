# -*- coding: utf-8 -*-
"""
Created on Mon Dec 10 11:42:03 2018

@author: kosmo30
"""

infile = input("input file name : ")
outfile = input("output file name : ")

with open(infile,'r', newline='') as inputfile:
    with open(outfile, 'w') as outputfile:
        for line in inputfile:
            outputfile.write(line)