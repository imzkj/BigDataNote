#!/usr/bin/python
# -*- coding:utf-8 -*-

"""
================================
SVM Exercise
================================

A tutorial exercise for using different SVM kernels.

This exercise is used in the :ref:`using_kernels_tut` part of the
:ref:`supervised_learning_tut` section of the :ref:`stat_learn_tut_index`.
"""
print(__doc__)


import numpy as np
import matplotlib.pyplot as plt
from sklearn import datasets, svm

iris = datasets.load_iris()
X = iris.data
y = iris.target

print X,y

X = X[y != 0, :2]
y = y[y != 0]
print X,y

n_sample = len(X)
print 'n_sample',n_sample
#产生不重复的
np.random.seed(0)
order = np.random.permutation(n_sample)  #产生100个随机的数
print order
X = X[order]
y = y[order].astype(np.float)
print X,y

X_train = X[:n_sample]
y_train = y[: n_sample]
X_test = X[n_sample:]
y_test = y[n_sample:]
print 'X_train,X_test',X_train,X_test
# fit the model
for fig_num, kernel in enumerate(('linear', 'rbf', 'poly')):
    print 'fig_num',fig_num
    clf = svm.SVC(kernel=kernel, gamma=50)
    clf.fit(X_train, y_train)

    plt.figure(fig_num) #绘画的窗口，独立显示
    plt.clf()
    plt.scatter(X[:, 0], X[:, 1], c=y, zorder=10, cmap=plt.cm.Paired)

    # Circle out the test data
    plt.scatter(X_test[:, 0], X_test[:, 1], s=80, facecolors='none', zorder=10)

    plt.axis('tight')
    x_min = X[:, 0].min()
    x_max = X[:, 0].max()
    y_min = X[:, 1].min()
    y_max = X[:, 1].max()

    XX, YY = np.mgrid[x_min:x_max:200j, y_min:y_max:200j]
    Z = clf.decision_function(np.c_[XX.ravel(), YY.ravel()])

    # Put the result into a color plot
    Z = Z.reshape(XX.shape)
    plt.pcolormesh(XX, YY, Z > 0, cmap=plt.cm.Paired)
    plt.contour(XX, YY, Z, colors=['k', 'k', 'k'], linestyles=['--', '-', '--'],
                levels=[-.5, 0, .5])
    #‘b’	blue
    # ‘g’	green
    # ‘r’	red
    # ‘c’	cyan
    # ‘m’	magenta
    # ‘y’	yellow
    # ‘k’	black
    # ‘w’	white
    plt.title(kernel)
plt.show()
