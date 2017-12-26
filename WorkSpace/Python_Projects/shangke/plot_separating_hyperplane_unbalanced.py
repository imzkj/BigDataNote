#!/usr/bin/python
# -*- coding:utf-8 -*-

"""
=================================================
SVM: Separating hyperplane for unbalanced classes
=================================================

Find the optimal separating hyperplane using an SVC for classes that
are unbalanced.

We first find the separating plane with a plain SVC and then plot
(dashed) the separating hyperplane with automatically correction for
unbalanced classes.

.. currentmodule:: sklearn.linear_model

.. note::

    This example will also work by replacing ``SVC(kernel="linear")``
    with ``SGDClassifier(loss="hinge")``. Setting the ``loss`` parameter
    of the :class:`SGDClassifier` equal to ``hinge`` will yield behaviour
    such as that of a SVC with a linear kernel.

    For example try instead of the ``SVC``::

        clf = SGDClassifier(n_iter=100, alpha=0.01)

"""
print(__doc__)

import numpy as np
import matplotlib.pyplot as plt
from sklearn import svm
import matplotlib.colors
import matplotlib.pyplot as plt
from sklearn.metrics import accuracy_score, precision_score, recall_score, f1_score, fbeta_score
import warnings
#from sklearn.linear_model import SGDClassifier

# we create 40 separable points
rng = np.random.RandomState(0)
n_samples_1 = 1000
n_samples_2 = 100
x = np.r_[1.5 * rng.randn(n_samples_1, 2),
          0.5 * rng.randn(n_samples_2, 2) + [2, 2]]
y = [-1] * (n_samples_1) + [1] * (n_samples_2)

print 'X,y',x,y
# #fit the model and get the separating hyperplane
# clf = svm.SVC(kernel='linear', C=10.0)
# clf.fit(x, y)
print '=================================================='
clfs = [svm.SVC(C=1, kernel='linear'),
           svm.SVC(C=100, kernel='linear'),
           svm.SVC(C=100, kernel='rbf', gamma=10, class_weight={-1: 1, 1: 0.5}),#类别的权重，字典形式传递。设置第几类的参数C为weight*C
           svm.SVC(C=0.8, kernel='rbf', gamma=0.5, class_weight={-1: 1, 1: 10})]
titles = 'kernel=linear', 'kernel=linearandWeight=50', 'kernel=rbf, W=0.5', 'kernel=rbf, W=10'

x1_min, x1_max = x[:, 0].min(), x[:, 0].max()  # the range of zero col
x2_min, x2_max = x[:, 1].min(), x[:, 1].max()  # the range of one col
x1, x2 = np.mgrid[x1_min:x1_max:500j, x2_min:x2_max:500j]  # 生成网格采样点
grid_test = np.stack((x1.flat, x2.flat), axis=1)  # 测试点

cm_light = matplotlib.colors.ListedColormap(['#77E0A0', '#FF8080'])
cm_dark = matplotlib.colors.ListedColormap(['g', 'r'])
matplotlib.rcParams['font.sans-serif'] = [u'SimHei']
matplotlib.rcParams['axes.unicode_minus'] = False
plt.figure(figsize=(10, 8), facecolor='w')
for i, clf in enumerate(clfs):
    clf.fit(x, y)

    y_hat = clf.predict(x)

    print i+1, 'times：'
    print 'accuracy_score：\t', accuracy_score(y, y_hat)
    print ' precision_score ：\t', precision_score(y, y_hat, pos_label=1)
    print 'recall_score：\t', recall_score(y, y_hat, pos_label=1)
    print 'F1Score：\t', f1_score(y, y_hat, pos_label=1)
    print


    # 画图
    plt.subplot(2, 2, i+1)
    grid_hat = clf.predict(grid_test)
    grid_hat = grid_hat.reshape(x1.shape)
    plt.pcolormesh(x1, x2, grid_hat, cmap=cm_light, alpha=0.8)
    plt.scatter(x[:, 0], x[:, 1], c=y, edgecolors='k', cmap=cm_dark)      # 样本的显示
    plt.xlim(x1_min, x1_max)
    plt.ylim(x2_min, x2_max)
    plt.title(titles[i])
    plt.grid()
plt.suptitle('unbalanced data', fontsize=18)
plt.tight_layout(1.5)
plt.subplots_adjust(top=0.92)
plt.show()
print '================================================================='
# w = clf.coef_[0]
# a = -w[0] / w[1]
# xx = np.linspace(-5, 5)
# yy = a * xx - clf.intercept_[0] / w[1]
#
#
# # get the separating hyperplane using weighted classes
# wclf = svm.SVC(kernel='linear', class_weight={1: 10})
# wclf.fit(x, y)
#
# ww = wclf.coef_[0]
# wa = -ww[0] / ww[1]
# wyy = wa * xx - wclf.intercept_[0] / ww[1]
#
# # plot separating hyperplanes and samples
# h0 = plt.plot(xx, yy, 'k-', label='no weights')
# h1 = plt.plot(xx, wyy, 'k--', label='with weights')
# plt.scatter(x[:, 0], x[:, 1], c=y, cmap=plt.cm.Paired)
# plt.legend()
#
# plt.axis('tight')
# plt.show()
# 准确率(Precision)=  系统检索到的相关文件 / 系统所有检索到的文件总数；；；
# 亦即等于预测为真实正例除以所有被预测为正例样本的个数
# 正确率 = 正确识别的个体总数 /  识别出的个体总数
# 召回率 = 正确识别的个体总数 /  测试集中存在的个体总数
# F值  = 正确率 * 召回率 * 2 / (正确率 + 召回率)
# 不妨举这样一个例子：某池塘有1400条鲤鱼，300只虾，300只鳖。现在以捕鲤鱼为目的。Seaeagle撒一大网，逮着了700条鲤鱼，200只虾，100只鳖。那么，这些指标分别如下：
# 正确率 = 700 / (700 + 200 + 100) = 70%
# 召回率 = 700 / 1400 = 50%
# F值 = 70% * 50% * 2 / (70% + 50%) = 58.3%
#         不妨看看如果Seaeagle把池子里的所有的鲤鱼、虾和鳖都一网打尽，这些指标又有何变化：
# 正确率 = 1400 / (1400 + 300 + 300) = 70%
# 召回率 = 1400 / 1400 = 100%
# F值 = 70% * 100% * 2 / (70% + 100%) =82.35%