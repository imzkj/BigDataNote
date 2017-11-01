# coding:utf-8
# 梯度下降
import matplotlib.pyplot as plt
import numpy as np
from mpl_toolkits.mplot3d import Axes3D


# 解决中文乱码
def show_word():
	from pylab import mpl
	mpl.rcParams['font.sans-serif'] = 'FangSong'  # 指定默认字体
	mpl.rcParams['axes.unicode_minus'] = False

show_word()

## 原函数
def f(x, y):
	return x ** 2 + y ** 2

## 偏函数
def h(t):
	return 2 * t

X = []
Y = []
Z = []

x = 2
y = 2
f_change = x ** 2 + y ** 2
f_current = f(x, y)
step = 0.1
X.append(x)
Y.append(y)
Z.append(f_current)
# 1e-10代表科学技术法，即1乘以10的-10次方
while f_change > 1e-10:
	x = x - step * h(x)
	y = y - step * h(y)
	f_change = f_current - f(x, y)
	f_current = f(x, y)
	X.append(x)
	Y.append(y)
	Z.append(f_current)
print(u"最终结果为：", (x, y))

fig = plt.figure()
ax = Axes3D(fig)
X2 = np.arange(-2, 2, 0.2)
Y2 = np.arange(-2, 2, 0.2)
X2, Y2 = np.meshgrid(X2, Y2)
Z2 = X2 ** 2 + Y2 ** 2
ax.plot_surface(X2, Y2, Z2, rstride=1, cstride=1, cmap="rainbow")
ax.plot(X, Y, Z, 'ro-')
ax.set_title(u"梯度下降法求解，最终解为：x=%.2f, y=%.2f, z=%.2f" % (x, y, f_current))
plt.show()
