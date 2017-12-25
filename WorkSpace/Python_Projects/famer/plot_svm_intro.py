#!/usr/bin/python
# -*- coding:utf-8 -*-

# SVC参数解释
# （1）C: 目标函数的惩罚系数C，用来平衡分类间隔margin和错分样本的，default C = 1.0；
# （2）kernel：参数选择有RBF, Linear, Poly, Sigmoid, 默认的是"RBF";
# （3）degree：if you choose 'Poly' in param 2, this is effective, degree决定了多项式的最高次幂；
# （4）gamma：核函数的系数('Poly', 'RBF' and 'Sigmoid'), 默认是gamma = 1 / n_features;
# （5）coef0：核函数中的独立项，'RBF' and 'Poly'有效；
# （6）probablity: 可能性估计是否使用(true or false)；
# （7）shrinking：是否进行启发式；
# （8）tol（default = 1e - 3）: svm结束标准的精度;
# （9）cache_size: 制定训练所需要的内存（以MB为单位）；
# （10）class_weight: 每个类所占据的权重，不同的类设置不同的惩罚参数C, 缺省的话自适应；
# （11）verbose: 跟多线程有关，不大明白啥意思具体；
# （12）max_iter: 最大迭代次数，default = 1， if max_iter = -1, no limited;
# （13）decision_function_shape ： ‘ovo’ 一对一, ‘ovr’ 多对多  or None 无, default=None
# （14）random_state ：用于概率估计的数据重排时的伪随机数生成器的种子。
#  ps：7,8,9一般不考虑。

import numpy as np
from sklearn import svm
from sklearn.model_selection import train_test_split
import matplotlib as mpl
import matplotlib.pyplot as plt


def iris_type(s):
    it = {'Iris-setosa': 0, 'Iris-versicolor': 1, 'Iris-virginica': 2}
    return it[s]


# 'sepal length', 'sepal width', 'petal length', 'petal width'
iris_feature = u'花萼长度', u'花萼宽度', u'花瓣长度', u'花瓣宽度'


def show_accuracy(a, b, tip):
    acc = a.ravel() == b.ravel()
    print tip + 'accuracy：', np.mean(acc)


if __name__ == "__main__":
#     dtype：数据类型。eg：float、str等。
# 　　delimiter：分隔符。eg：‘，’。
# 　　converters：将数据列与转换函数进行映射的字典。eg：{1:fun}，含义是将第2列对应转换函数进行转换。
# 　　usecols：选取数据的列。

    path = 'iris.data'  # 数据文件路径
    data = np.loadtxt(path, dtype=float, delimiter=',', converters={4: iris_type}) #将列号映射到将该列转换为float的函数的字典
    print 'data:',data
    x, y = np.split(data, (4,), axis=1) #行
    x = x[:, :2]
    x_train, x_test, y_train, y_test = train_test_split(x, y, random_state=1, train_size=0.6)
#     train_data：所要划分的样本特征集
# 　　train_target：所要划分的样本结果
# 　　test_size：样本占比，如果是整数的话就是样本的数量
# 　　random_state：是随机数的种子。
    print 'x,y',x,y
    # 分类器
    # clf = svm.SVC(C=0.1, kernel='linear', decision_function_shape='ovr')
    clf = svm.SVC(C=0.8, kernel='rbf', gamma=20, decision_function_shape='ovr')
    #x = np.array([[1, 2], [3, 4]])
    #x.ravel()  array([1, 2, 3, 4])
    clf.fit(x_train, y_train.ravel())
# kernel='linear'时，为线性核，C越大分类效果越好，但有可能会过拟合（defaul C=1）。
# 　　 kernel='rbf'时（default），为高斯核，gamma值越小，分类界面越连续；gamma值越大，分类界面越“散”，分类效果越好，但有可能会过拟合。
# 　　decision_function_shape='ovr'时，为one v rest，即一个类别与其他类别进行划分，
# 　　decision_function_shape='ovo'时，为one v one，即将类别两两之间进行划分，用二分类的方法模拟多分类的结果。
    # 准确率
    print clf.score(x_train, y_train)  # 精度
    y_hat = clf.predict(x_train)
    show_accuracy(y_hat, y_train, 'trainingdata')
    print clf.score(x_test, y_test)
    y_hat = clf.predict(x_test)
    show_accuracy(y_hat, y_test, 'testdata')

    # 画图
    # x1_min, x1_max = x[:, 0].min(), x[:, 0].max()  # 第0列的范围
    # x2_min, x2_max = x[:, 1].min(), x[:, 1].max()  # 第1列的范围
    # x1, x2 = np.mgrid[x1_min:x1_max:500j, x2_min:x2_max:500j]  # 生成网格采样点
    # x = -3:1:3;
    # y = -2:1:2;
    #[X, Y] = meshgrid(x, y);
    # 这里meshigrid（x，y）的作用是产生一个以向量x为行，向量y为列的矩阵，而x是从 - 3
    # 开始到3，每间隔1记下一个数据，并把这些数据集成矩阵X；同理y则是从 - 2
    # 到2，每间隔1记下一个数据，并集成矩阵Y

    #对数组中每个元素都进行处理，可以使用flat属性，该属性是一个数组元素迭代器：
    #grid_test = np.stack((x1.flat, x2.flat), axis=1)  # 测试点 axis=0，即沿着第一维连接两个数组，我们可以理解成把每一个数组看做一行，然后一行一行排下来

    #print 'grid_test:',grid_test,len(grid_test)
    #Z = clf.decision_function(grid_test)    # 样本到决策面的距离
    #print Z
    #grid_hat = clf.predict(grid_test)       # 预测分类值
    #print grid_hat
    #grid_hat = grid_hat.reshape(x1.shape)  # 使之与输入的形状相同
    mpl.rcParams['font.sans-serif'] = [u'SimHei']   #指定默认字体
    mpl.rcParams['axes.unicode_minus'] = False

    cm_light = mpl.colors.ListedColormap(['#A0FFA0', '#FFA0A0', '#A0A0FF'])
    cm_dark = mpl.colors.ListedColormap(['g', 'r', 'b'])
    x1_min, x1_max = x[:, 0].min(), x[:, 0].max()  # 第0列的范围
    x2_min, x2_max = x[:, 1].min(), x[:, 1].max()  # 第1列的范围
    x1, x2 = np.mgrid[x1_min:x1_max:500j, x2_min:x2_max:500j]  # 生成网格采样点
    grid_test = np.stack((x1.flat, x2.flat), axis=1)  # #
    Z = clf.decision_function(grid_test)    # 样本到决策面的距离
    print Z
    grid_hat = clf.predict(grid_test)       # 预测分类值
    print grid_hat
    grid_hat = grid_hat.reshape(x1.shape)  # 使之与输入的形状相同
    mpl.rcParams['font.sans-serif'] = [u'SimHei']   #指定默认字体
    mpl.rcParams['axes.unicode_minus'] = False

    plt.pcolormesh(x1, x2, grid_hat, cmap=cm_light)

    plt.scatter(x[:, 0], x[:, 1], c=y, edgecolors='k', s=50, cmap=cm_dark)      # 样本
    plt.scatter(x_test[:, 0], x_test[:, 1], s=120, facecolors='none', zorder=10)     # 圈中测试集样本
    plt.xlabel(iris_feature[0], fontsize=13)
    plt.ylabel(iris_feature[1], fontsize=13)
    plt.xlim(x1_min, x1_max)
    plt.ylim(x2_min, x2_max)
    plt.title(u'iris', fontsize=15)
    plt.grid()
    plt.show()
