#coding:utf-8
import numpy as np

def affine_forward(x, w, b):   
    """    
    Computes the forward pass for an affine (fully-connected) layer. 
    The input x has shape (N, d_1, ..., d_k) and contains a minibatch of N   
    examples, where each example x[i] has shape (d_1, ..., d_k). We will    
    reshape each input into a vector of dimension D = d_1 * ... * d_k, and    
    then transform it to an output vector of dimension M.    
    Inputs:    
    - x: A numpy array containing input data, of shape (N, d_1, ..., d_k)    
    - w: A numpy array of weights, of shape (D, M)    
    - b: A numpy array of biases, of shape (M,)   
    Returns a tuple of:    
    - out: output, of shape (N, M)    
    - cache: (x, w, b)   
    """
    out = None
    # Reshape x into rows
    N = x.shape[0]
    x_row = x.reshape(N, -1)         # (N,D)
    out = np.dot(x_row, w) + b       # (N,M)
    cache = (x, w, b)

    return out, cache

def affine_backward(dout, cache):   
    """    
    Computes the backward pass for an affine layer.    
    Inputs:    
    - dout: Upstream derivative, of shape (N, M)    
    - cache: Tuple of: 
    - x: Input data, of shape (N, d_1, ... d_k)    
    - w: Weights, of shape (D, M)    
    Returns a tuple of:   
    - dx: Gradient with respect to x, of shape (N, d1, ..., d_k)    
    - dw: Gradient with respect to w, of shape (D, M) 
    - db: Gradient with respect to b, of shape (M,)    
    """    
    x, w, b = cache    
    dx, dw, db = None, None, None   
    dx = np.dot(dout, w.T)                       # (N,D)    
    dx = np.reshape(dx, x.shape)                 # (N,d1,...,d_k)   
    x_row = x.reshape(x.shape[0], -1)            # (N,D)    
    dw = np.dot(x_row.T, dout)                   # (D,M)    
    db = np.sum(dout, axis=0, keepdims=True)     # (1,M)    

    return dx, dw, db

def relu_forward(x):   
    """    
    Computes the forward pass for a layer of rectified linear units (ReLUs).    
    Input:    
    - x: Inputs, of any shape    
    Returns a tuple of:    
    - out: Output, of the same shape as x    
    - cache: x    
    """   
    out = None    
    out = ReLU(x)    
    cache = x    

    return out, cache

def relu_backward(dout, cache):   
    """  
    Computes the backward pass for a layer of rectified linear units (ReLUs).   
    Input:    
    - dout: Upstream derivatives, of any shape    
    - cache: Input x, of same shape as dout    
    Returns:    
    - dx: Gradient with respect to x    
    """    
    dx, x = None, cache    
    dx = dout    
    dx[x <= 0] = 0    

    return dx

def svm_loss(x, y):   
    """    
    Computes the loss and gradient using for multiclass SVM classification.    
    Inputs:    
    - x: Input data, of shape (N, C) where x[i, j] is the score for the jth class         
         for the ith input.    
    - y: Vector of labels, of shape (N,) where y[i] is the label for x[i] and         
         0 <= y[i] < C   
    Returns a tuple of:    
    - loss: Scalar giving the loss   
    - dx: Gradient of the loss with respect to x    
    """    
    N = x.shape[0]   
    correct_class_scores = x[np.arange(N), y]    
    margins = np.maximum(0, x - correct_class_scores[:, np.newaxis] + 1.0)    
    margins[np.arange(N), y] = 0   
    loss = np.sum(margins) / N   
    num_pos = np.sum(margins > 0, axis=1)    
    dx = np.zeros_like(x)   
    dx[margins > 0] = 1    
    dx[np.arange(N), y] -= num_pos    
    dx /= N    

    return loss, dx

def softmax_loss(x, y):    
    """    
    Computes the loss and gradient for softmax classification.    Inputs:    
    - x: Input data, of shape (N, C) where x[i, j] is the score for the jth class         
    for the ith input.    
    - y: Vector of labels, of shape (N,) where y[i] is the label for x[i] and         
         0 <= y[i] < C   
    Returns a tuple of:    
    - loss: Scalar giving the loss    
    - dx: Gradient of the loss with respect to x   
    """    
    probs = np.exp(x - np.max(x, axis=1, keepdims=True))    
    probs /= np.sum(probs, axis=1, keepdims=True)    
    N = x.shape[0]   
    loss = -np.sum(np.log(probs[np.arange(N), y])) / N    
    dx = probs.copy()    
    dx[np.arange(N), y] -= 1    
    dx /= N    

    return loss, dx

def ReLU(x):    
    """ReLU non-linearity."""    
    return np.maximum(0, x)
# 卷积前向传播
def conv_forward_naive(x, w, b, conv_param):
    stride, pad = conv_param['stride'], conv_param['pad']
    # 输入数据
    N, C, H, W = x.shape
    # filter数据
    F, C, HH, WW = w.shape
    # 输入值加上n圈0
    x_padded = np.pad(x, ((0, 0), (0, 0), (pad, pad), (pad, pad)), mode='constant')
    # 计算经过卷积后输出的矩阵大小
    H_new = 1 + (H + 2 * pad - HH) / stride
    W_new = 1 + (W + 2 * pad - WW) / stride
    s = stride
    # N图像个数，F特征图个数，H_new, W_new特征图长宽
    out = np.zeros((N, F, H_new, W_new))

    # 循环遍历，对N个图像进行卷积操作
    for i in xrange(N):       # ith image
        # 不同filter对图像进行遍历得到F个特征图
        for f in xrange(F):   # fth filter
            # 得到输出特征图里面的每一个值
            for j in xrange(H_new):            
                for k in xrange(W_new):   
                    #print x_padded[i, :, j*s:HH+j*s, k*s:WW+k*s].shape
                    #print w[f].shape  
                    #print b.shape  
                    #print np.sum((x_padded[i, :, j*s:HH+j*s, k*s:WW+k*s] * w[f]))
                    # i第i个图像；：取该图像的所有通道；j*s:HH+j*s, k*s:WW+k*s进行计算的区域大小，对应filter
                    # 乘以filter对应的权重w[f]，最后加上正则化惩罚项b[f]
                    out[i, f, j, k] = np.sum(x_padded[i, :, j*s:HH+j*s, k*s:WW+k*s] * w[f]) + b[f]

    cache = (x, w, b, conv_param)

    return out, cache

# 卷积反向传播
def conv_backward_naive(dout, cache):
    #print '1111'
    x, w, b, conv_param = cache
    pad = conv_param['pad']
    stride = conv_param['stride']
    F, C, HH, WW = w.shape
    N, C, H, W = x.shape
    H_new = 1 + (H + 2 * pad - HH) / stride
    W_new = 1 + (W + 2 * pad - WW) / stride

    dx = np.zeros_like(x)
    dw = np.zeros_like(w)
    db = np.zeros_like(b)

    s = stride
    x_padded = np.pad(x, ((0, 0), (0, 0), (pad, pad), (pad, pad)), 'constant')
    dx_padded = np.pad(dx, ((0, 0), (0, 0), (pad, pad), (pad, pad)), 'constant')

    for i in xrange(N):       # ith image    
        for f in xrange(F):   # fth filter        
            for j in xrange(H_new):            
                for k in xrange(W_new):
                    # 找到dout对应的window的区域
                    window = x_padded[i, :, j*s:HH+j*s, k*s:WW+k*s]
                    # dout为上一层传下来的值
                    db[f] += dout[i, f, j, k]                
                    dw[f] += window * dout[i, f, j, k]                
                    dx_padded[i, :, j*s:HH+j*s, k*s:WW+k*s] += w[f] * dout[i, f, j, k]

    # Unpad
    dx = dx_padded[:, :, pad:pad+H, pad:pad+W]

    return dx, dw, db
# 池化层前向传播
def max_pool_forward_naive(x, pool_param):
    HH, WW = pool_param['pool_height'], pool_param['pool_width']
    s = pool_param['stride']
    N, C, H, W = x.shape
    H_new = 1 + (H - HH) / s
    W_new = 1 + (W - WW) / s
    out = np.zeros((N, C, H_new, W_new))
    for i in xrange(N):    
        for j in xrange(C):        
            for k in xrange(H_new):            
                for l in xrange(W_new):                
                    window = x[i, j, k*s:HH+k*s, l*s:WW+l*s]
                    # 取window该区域最大值
                    out[i, j, k, l] = np.max(window)

    cache = (x, pool_param)

    return out, cache

# 池化层反向传播
def max_pool_backward_naive(dout, cache):
    x, pool_param = cache
    HH, WW = pool_param['pool_height'], pool_param['pool_width']
    s = pool_param['stride']
    N, C, H, W = x.shape
    H_new = 1 + (H - HH) / s
    W_new = 1 + (W - WW) / s
    dx = np.zeros_like(x)
    for i in xrange(N):    
        for j in xrange(C):        
            for k in xrange(H_new):            
                for l in xrange(W_new):                
                    window = x[i, j, k*s:HH+k*s, l*s:WW+l*s]                
                    m = np.max(window)
                    # 使原来上一层的窗口最大值等于还是该值其他位置置位0,乘以梯度
                    dx[i, j, k*s:HH+k*s, l*s:WW+l*s] = (window == m) * dout[i, j, k, l]

    return dx