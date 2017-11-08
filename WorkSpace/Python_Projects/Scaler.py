#encoding:utf-8
'''''
Created on 2015年10月13日
@author: ZHOUMEIXU204
'''

#sklearn数据标准化，数据标准化有三种
#第一种是Z-Score，或者去除均值和方差缩放
from sklearn import preprocessing
import numpy as np
x=np.array([[1.,-1.,2.],
            [2.,0.,0.],
            [0.,1.,-1.]])
x_scaled=preprocessing.scale(x)
x_scaled.mean(axis=0)
x_scaled.std(axis=0)
# 使用sklearn.preprocessing.StandardScaler类，
# 使用该类的好处在于可以保存训练集中的参数（均值、方差）
# 直接使用其对象转换测试集数据。
scaler=preprocessing.StandardScaler().fit(x)
scaler.mean_
# scaler.std_
scaler.transform(x)  #跟上面的结果是一样的

#第二种是将属性缩放到一个指定范围,也是就是(x-min)/(max-min)
#依赖于preprocessing中的MinMaxScaler类
x_train=np.array([[1.,-1.,2.],
                  [2.,0.,0.],
                  [0.,1.,-1.]])

min_max_scaler=preprocessing.MinMaxScaler()
x_train_minmax=min_max_scaler.fit_transform(x_train)
print(x_train_minmax)
# 当然，在构造类对象的时候也可以直接指定最大最小值的范围：feature_range=(min, max)，此时应用的公式变为：
# x_std=(X-X.min(axis=0))/(X.max(axis=0)-X.min(axis=0))
# x_scaled=X_std/(max-min)+min

#第三种是正则化Normalization

x=np.array([[1.,-1.,2.],
            [2.,0.,0.],
            [0.,1.,-1.]])
x_normalized=preprocessing.normalize(x,norm='l2')
print(x_normalized)

# 可以使用processing.Normalizer()类实现对训练集和测试集的拟合和转换
normalizer=preprocessing.Normalizer().fit(x)
print(normalizer)
normalizer.transform(x)