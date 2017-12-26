#!/usr/bin/python
# -*- coding:utf-8 -*-


import pandas as pd
from sklearn import svm
from sklearn import cross_validation

df = pd.read_csv("piaofang_29192633.csv")
clf = svm.SVC(C=10)

X = df[[i for i in range(72)]]
y = df['72']

scores = cross_validation.cross_val_score(clf, X, y, cv=3)
print(scores.mean())