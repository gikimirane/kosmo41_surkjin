# -*- coding: utf-8 -*-
"""
Created on Fri Dec  7 13:00:37 2018

@author: kosmo30
"""

from datetime import date, time, datetime, timedelta

print(date.today())
print(date.today().year)
print(date.today().month)
print(date.today().day)

print("="*30)

print(datetime.today())
print(datetime.today().year)
print(datetime.today().month)
print(datetime.today().day)
print(datetime.today().hour)
print(datetime.today().minute)
print(datetime.today().second)
print(datetime.today().microsecond)

print("="*30)

one_day = timedelta(days=-1)
yesterday = date.today() + one_day
print(yesterday)
print(date.today() - yesterday)

one_day = timedelta(-1)
yesterday = date.today() - one_day
print(yesterday)
print(date.today() - yesterday)

print("="*30)

today = date.today()
print(today.strftime("%m/%d/%Y"))
print(today.strftime("%b %d %Y"))
print(today.strftime("%B %d %Y"))
print(today.strftime("%Y-%m-%d"))

print("="*30)
date1 = "2018-11-27"
print(datetime.strptime(date1, "%Y-%m-%d"))
print(datetime.date(datetime.strptime(date1, "%Y-%m-%d")))






