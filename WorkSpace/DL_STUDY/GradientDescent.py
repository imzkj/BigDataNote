#-*- coding: utf-8 -*-

# y=3*x1+4*x2
# BGD(Batch gradient descent)批量梯度下降法：每次迭代使用所有的样本
#用y = Θ1*x1 + Θ2*x2来拟合下面的输入和输出
#input1  1   2   5   4
#input2  4   5   1   2
#output  19  26  19  20
input_x = [[1,4], [2,5], [5,1], [4,2]]  #输入
y = [19,26,19,20]   #输出
theta = [1,1]       #θ参数初始化
loss = 10           #loss先定义一个数，为了进入循环迭代
step_size = 0.01    #步长
eps =0.0001         #精度要求
max_iters = 10000   #最大迭代次数
error =0            #损失值
iter_count = 0      #当前迭代次数

err1=[0,0,0,0]      #求Θ1梯度的中间变量1
err2=[0,0,0,0]      #求Θ2梯度的中间变量2

while( loss > eps and iter_count < max_iters):   #迭代条件
    loss = 0
    err1sum = 0
    err2sum = 0
    for i in range (4):     #每次迭代所有的样本都进行训练
        pred_y = theta[0]*input_x[i][0]+theta[1]*input_x[i][1]  #预测值
        err1[i]=(pred_y-y[i])*input_x[i][0]
        err1sum=err1sum+err1[i]
        err2[i]=(pred_y-y[i])*input_x[i][1]
        err2sum=err2sum+err2[i]
    theta[0] = theta[0] - step_size * err1sum/4  #对应5式
    theta[1] = theta[1] - step_size * err2sum/4  #对应5式
    for i in range (4):
        pred_y = theta[0]*input_x[i][0]+theta[1]*input_x[i][1]   #预测值
        error = (1.0/(2*4))*(pred_y - y[i])**2  #损失值
        loss = loss + error  #总损失值
    iter_count += 1
print ('BGD theta: ',theta )
print ('BGD final loss: ', loss)
print ('BGD iters: ', iter_count)

# SGD（Stochastic Gradient Descent）随机梯度下降法：每次迭代使用一组样本
import random
#用y = Θ1*x1 + Θ2*x2来拟合下面的输入和输出
#input1  1   2   5   4
#input2  4   5   1   2
#output  19  26  19  20
input_x = [[1,4], [2,5], [5,1], [4,2]]  #输入
y = [19,26,19,20]   #输出
theta = [1,1]       #θ参数初始化
loss = 10           #loss先定义一个数，为了进入循环迭代
step_size = 0.01    #步长
eps =0.0001         #精度要求
max_iters = 10000   #最大迭代次数
error =0            #损失值
iter_count = 0      #当前迭代次数

while( loss > eps and iter_count < max_iters):    #迭代条件
    loss = 0
    i = random.randint(0,3)  #每次迭代在input_x中随机选取一组样本进行权重的更新
    pred_y = theta[0]*input_x[i][0]+theta[1]*input_x[i][1] #预测值
    theta[0] = theta[0] - step_size * (pred_y - y[i]) * input_x[i][0]
    theta[1] = theta[1] - step_size * (pred_y - y[i]) * input_x[i][1]
    for i in range (3):
        pred_y = theta[0]*input_x[i][0]+theta[1]*input_x[i][1] #预测值
        error = 0.5*(pred_y - y[i])**2
        loss = loss + error
    iter_count += 1
print ('SGD theta: ',theta )
print ('SGD final loss: ', loss)
print ('SGD iters: ', iter_count)

# MBGD（Mini-batch gradient descent）小批量梯度下降：每次迭代使用b组样本
import random
#用y = Θ1*x1 + Θ2*x2来拟合下面的输入和输出
#input1  1   2   5   4
#input2  4   5   1   2
#output  19  26  19  20
input_x = [[1,4], [2,5], [5,1], [4,2]]  #输入
y = [19,26,19,20]       #输出
theta = [1,1]           #θ参数初始化
loss = 10               #loss先定义一个数，为了进入循环迭代
step_size = 0.01        #步长
eps =0.0001             #精度要求
max_iters = 10000       #最大迭代次数
error =0                #损失值
iter_count = 0          #当前迭代次数
while( loss > eps and iter_count < max_iters):  #迭代条件
    loss = 0
    #这里每次批量选取的是2组样本进行更新，另一个点是随机点+1的相邻点
    i = random.randint(0,3)     #随机抽取一组样本
    j = (i+1)%4                 #抽取另一组样本，j=i+1
    pred_y0 = theta[0]*input_x[i][0]+theta[1]*input_x[i][1]  #预测值1
    pred_y1 = theta[0]*input_x[j][0]+theta[1]*input_x[j][1]  #预测值2
    theta[0] = theta[0] - step_size * (1.0/2) * ((pred_y0 - y[i]) * input_x[i][0]+(pred_y1 - y[j]) * input_x[j][0])  #对应5式
    theta[1] = theta[1] - step_size * (1.0/2) * ((pred_y0 - y[i]) * input_x[i][1]+(pred_y1 - y[j]) * input_x[j][1])  #对应5式
    for i in range (3):
        pred_y = theta[0]*input_x[i][0]+theta[1]*input_x[i][1]     #总预测值
        error = (1.0/(2*2))*(pred_y - y[i])**2                    #损失值
        loss = loss + error       #总损失值
    iter_count += 1
print ('MBGD theta: ',theta )
print ('MBGD final loss: ', loss)
print ('MBGD iters: ', iter_count)