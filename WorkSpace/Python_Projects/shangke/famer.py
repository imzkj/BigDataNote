# !/usr/bin/python
# -*- coding:utf-8 -*-


import numpy as np
import pylab as pl
from sklearn import svm
from sklearn import linear_model
from sklearn import tree
import pandas as pd


def plot_results_with_hyperplane(clf, clf_name, df, plt_nmbr):
    x_min, x_max = df.x.min() - .5, df.x.max() + .5
    y_min, y_max = df.y.min() - .5, df.y.max() + .5

    # step between points. i.e. [0, 0.02, 0.04, ...]
    step = .02
    # to plot the boundary, we're going to create a matrix of every possible point
    # then label each point as a wolf or cow using our classifier
    #np.arange(x_min, x_max, step)1，2，0.5  1,1.5
    xx, yy = np.meshgrid(np.arange(x_min, x_max, step),
                         np.arange(y_min, y_max, step))
    # >> > np.c_[a, b]
    # array([[1, 5],
    #        [2, 2],
    #        [3, 5]])
    Z = clf.predict(np.c_[xx.ravel(), yy.ravel()])


    # this gets our predictions back into a matrix
    Z = Z.reshape(xx.shape)

    # create a subplot (we're going to have more than 1 plot on a given image)
    pl.subplot(2, 2, plt_nmbr)

    # plot the boundaries
    pl.pcolormesh(xx, yy, Z>0, cmap=pl.cm.Paired)


    # plot the wolves and cows
    #animal.unique()只有两种，一个是wolves，另外一个是cows
    for animal in df.animal.unique():
        pl.scatter(df[df.animal == animal].x,
                   df[df.animal == animal].y,
                   marker=animal,
                   label="cows" if animal == "x" else "wolves",
                   color='black',
                   # c=df.animal_type, cmap=pl.cm.Paired)
                   )
    pl.title(clf_name)
    pl.legend(loc="best")


data = open("cows_and_wolves.txt").read()#读取全部
#read是读取所有的文件，每个row代表每一行
data = [row.split('\t') for row in data.strip().split('\n')]

animals = []
# list1 = ["这", "是", "一个", "测试"]
# for index, item in enumerate(list1):
#     print index, item
# >>>
# 0 这
# 1 是
# 2 一个
# 3 测试
#把每一个动物的坐标和动物的属性写在animal这个list里面
for y, row in enumerate(data):
    for x, item in enumerate(row):
        # x's are cows, o's are wolves
        if item in ['o', 'x']:
            animals.append([x, y, item])

print animals

df = pd.DataFrame(animals, columns=["x", "y", "animal"])
print 'pd.DataFrame',df
df['animal_type'] = df.animal.apply(lambda x: 0 if x == "x" else 1)
print 'df[animal_type]',df['animal_type']

# train using the x and y position coordiantes
train_cols = ["x", "y"]

clfs = {
    "SVM": svm.SVC(),
    "Logistic": linear_model.LogisticRegression(),
    "Decision Tree": tree.DecisionTreeClassifier(),
}

plt_nmbr = 1
for clf_name, clf in clfs.iteritems():
    clf.fit(df[train_cols], df.animal_type)
    plot_results_with_hyperplane(clf, clf_name, df, plt_nmbr)
    plt_nmbr += 1
pl.show()

